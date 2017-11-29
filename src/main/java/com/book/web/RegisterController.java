package com.book.web;

import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.book.domain.User;
import com.book.server.MailService;
import com.book.server.UserService;
import com.book.util.Result;
import com.book.util.ServiceException;
import com.book.util.ValidTool;
import com.book.valid.RegisterForm;
import com.google.code.kaptcha.servlet.KaptchaExtend;

@Controller
public class RegisterController extends KaptchaExtend{
	@Autowired
	private UserService userService;
	
	@Autowired
	private MailService mailService;
	
	@GetMapping(value="/register")
	public String register() {
		return "register";
	}
	//判断账号的类型
	@RequestMapping(value = "/register/account/detect")
	@ResponseBody
	public Map<String, Object> detect(String account,String ordinary){
		//账号已存在
		if(userService.accountIsExist(account)) {
			return Result.setResult("exist");
		//邮箱注册	
		}else if(ValidTool.isEmail(account)) {
			return Result.setResult("email");
	
		}else if(ValidTool.isordinary(ordinary)) {	
			return Result.setResult("ordinary");
		//格式错误	
		}else {
			return Result.setResult("format_error");
		}
	}
	//显示校验的验证码
	@GetMapping(value = "/reg_vericode.jpg")
	public void captcha(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		super.captcha(req, resp);
	}

	@ResponseBody
	@PostMapping(value = "/register/vcode/send")
	public Map<String, Object> verify(HttpSession session, String account) {
		// 60秒/次限制
		//获取最后一次发生邮件时间
		Date lastTime = (Date) session.getAttribute("regCodelastTime");
		if (lastTime != null) {
			//发生邮箱验证码的间隔小于60秒
			//Data是毫秒来计算的  1秒=1000毫秒
		/*	if ((new Date().getTime() - lastTime.getTime()) / 1000 < 60) {
				throw new ServiceException("vcode", "vcode.send.diff");
			}*/
		} 
		//
		session.setAttribute("regCodelastTime", new Date());
		//
		if (userService.accountIsExist(account)) {
			throw new ServiceException("account", "user.account.exist.error", account);
		} else {
			//获取随机四位数
			String regcode = getFourRandom();
			//通过session保存验证码与账号
			session.setAttribute("regcode", regcode);
			session.setAttribute("regaccount", account);
			//发送验证码
			if (ValidTool.isEmail(account)) {
				String txt = "sbdl 验证码：" + regcode;
				
				mailService.send(account, account, "sbdl", "sbdl 验证码", txt);
				
			//账号格式错误	
			} else {
				throw new ServiceException("account", "user.account.format.error", account);
			}
		}
		return Result.setResult("发送验证码成功。");
	}
	
	private static String getFourRandom() {
//      Random类中实现的随机算法是伪随机，也就是有规则的随机
		Random random = new Random();
//		生成一个随机的int值,生成随机数为0-10000
		String fourRandom = random.nextInt(10000) + "";
		int randLength = fourRandom.length();
//		位数不能小于4
		if (randLength < 4) {
			for (int i = 1; i <= 4 - randLength; i++)
				fourRandom = "0" + fourRandom;
		}
		return fourRandom;
	}
	
	
	@PostMapping(value = "/register")
	@ResponseBody
	public Map<String, Object> create(HttpSession session, @Valid RegisterForm registerForm) {
		//通过session获取验证码与验证账号
		String sess_regcode = (String) session.getAttribute("regcode");
		String sess_regaccount = (String) session.getAttribute("regaccount");
	
		//验证码
//		if (!StringUtils.equals(registerForm.getVcode(), sess_regcode))
//			throw new ServiceException("vcode", "vcode.error");
		//账号
//		if (!StringUtils.equals(registerForm.getAccount(), sess_regaccount))
//			throw new ServiceException("account", "account.error.verify");
		//是否同意
		if (!StringUtils.equals(registerForm.getAgree(), "yes"))
			throw new ServiceException("agree", "must.agree.to.the.terms");
		
		User user = new User();
		user.setAccount(registerForm.getAccount());
		user.setPassword(registerForm.getNpassword());
		//初始化个人数据
		user.init();
		userService.register(session, user);
		return Result.toUrl("/login");
	}
	
}