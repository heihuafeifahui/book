package com.book.web;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.book.server.UserService;
import com.book.util.AESenc;
import com.book.util.Result;
import com.book.util.ServiceException;
import com.book.valid.LoginForm;
import com.google.code.kaptcha.servlet.KaptchaExtend;


@Controller
public class LoginController extends KaptchaExtend {
	private final Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private UserService userauth;


	@GetMapping(value = "/login")
	public String login(HttpServletRequest request, Model model, String r) {
		log.info("getRemoteHost====================="+request.getRemoteHost());
		log.info("getServerName====================="+request.getServerName());
		String domain = request.getServerName().replaceAll(".*\\.(?=.*\\.)", "");
		log.info("domain====================="+domain);
		if (StringUtils.contains(domain, "."))
			log.info("url=====================accounts."+domain);
		if (StringUtils.isNotBlank(r)) {
			log.info("r=================" + r);
			try {
				log.info("url=================" + AESenc.decrypt(r));
				model.addAttribute("r", r);
				// model.addAttribute("url", AESenc.decrypt(r));
			} catch (Exception e) {
				log.info("decrypt url failed...");
				e.printStackTrace();
			}
		}
		return "login";
	}

	@PostMapping(value = "/login")
	@ResponseBody
	public Map<String, Object> auth(HttpSession session, @Valid LoginForm loginForm, String r,
			HttpServletRequest request) {
		Integer num = (Integer) session.getAttribute("login_num");
		if (null != num && num > 0)
			num = num + 1;
		else
			num = 1;
		session.setAttribute("login_num", num);

		if (num > 3) {
			String vcode = request.getParameter("vcode");
			if (!StringUtils.equals(vcode, getGeneratedKey(request)))
				throw new ServiceException("vcode", "vcode.error");
		}

		userauth.login(session, loginForm.getAccount(), loginForm.getPassword(), loginForm.getVcode());

		if (StringUtils.isNotBlank(r)) {
			try {
				log.info("url=================" + AESenc.decrypt(r));
				return Result.toUrl(AESenc.decrypt(r));
			} catch (Exception e) {
				log.info("decrypt url failed...");
				e.printStackTrace();
			}
		}

		return Result.toUrl("/");
	}

}
