package com.book.server;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.book.dao.UserDao;
import com.book.domain.User;
import com.book.util.AESCoder;
import com.book.util.PasswordUtil;
import com.book.util.ServiceException;

@Service
@Transactional
public class UserService {
	private final Logger log=LoggerFactory.getLogger(getClass());
	@Autowired
	private UserDao userDao;
	
	//查询用户id
	public User findById(String id) {
		return userDao.findById(id);
	}
	//查询自己的id，与用户id
	public User findById(String mid, String id) {
		try {
			return userDao.findById(mid, id);
		} catch (Exception e) {
			throw e;
		}
	}

	///查询账号
	public User findByAccount(String account) {
		try {
			User user=userDao.findByAccount(account);
		return user;
		}catch (ServiceException e) {
			throw e;
		}
	
}	
	//账号是否注册
	public boolean accountIsExist(String account) {
		try {
			User user=userDao.findByAccount(account);
	
			if(null != user && StringUtils.isNotBlank(user.getId())) {
				return true;
				}
			return false;
		}catch(Exception e) {
			return false;
		}
	}
	//注册账号
	public void register(HttpSession session,User user) {
	try {	
		User existUser=userDao.findByAccount(user.getAccount());
		if(null !=existUser) {
			throw new ServiceException("register","account.exist");
		}
		userDao.save(user);
		//将sessionid转换为大写
		String sessionid=session.getId().toLowerCase();
		//通过session保存userid
		session.setAttribute("user", user.getId());
		
		System.out.println("suser:updata_login_sessionuser:"+
			user.getAccount()+sessionid);
	}catch (Exception e) {
		throw e;
	}	
}
	public User login(HttpSession session, String account, String password, String vcode) {
		String verifycode = (String) session.getAttribute("VerifyCode");
		Integer login_count = (Integer) session.getAttribute("login_count");
		if (null != login_count && login_count > 6) {
			if (StringUtils.isNotBlank(verifycode) && StringUtils.equals(verifycode, vcode)) {
			} else {
				// throw new ServiceException("verifycode_error", "user");
			}
		}

		User user = userDao.findByAccount(account);
		if (null != user) {
			if (!PasswordUtil.authenticatePassword(user.getPassword(), password)) {
				throw new ServiceException("password", "user.login.failed");
			}
			String sessionid = session.getId().toLowerCase();
			session.setAttribute("userid", user.getId());
			try {
				byte[] sesskey = AESCoder.initkey();
				session.setAttribute("sesskey", sesskey);
				byte[] sesscipher = AESCoder.encrypt(password.getBytes(), sesskey);
				session.setAttribute("sesscipher", sesscipher);
			} catch (Exception e) {
				e.printStackTrace();
				throw new ServiceException("user.security.encrypt.failed");
			}
			System.out.println("suser：update_login_sessionuser:" + user.getAccount() + sessionid);
			// user.setSessionid(sessionid);
			return user;
		} else {
			throw new ServiceException("user.login.failed");
		}
	}
	

}
