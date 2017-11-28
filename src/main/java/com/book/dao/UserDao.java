package com.book.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Filter;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

import com.book.domain.User;

@Component
@Transactional
public class UserDao extends BaseDao<User>{
	public void save(User instance) {
		log.debug("saving User instance");
		try {
			getSession().save(instance);
			log.debug("save successful");
		}catch(RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}
	public void updata(User instance) {
		log.debug("updata User instance");
		try {
			getSession().update(instance);
			log.debug("update successful");
		}catch (RuntimeException re) {
			log.error("update filed", re);
			throw re;
		}
	}
	public void delete(User instance) {
		log.debug("deleting User instance");
		try {
			getSession().delete(instance);
			log.debug("delete successful");
		}catch(RuntimeException re) {
			log.error("delete failed",re);
			throw re;
		}
	}
	public User findById(String id) {
		log.debug("getting User instance with id :" + id);
		try {
			User instance=getSession().get(User.class, id);
			return instance;
		}catch(RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
	
	public User findById(String mid,String id) {
		log.debug("getting User instance with id: " + id);
		try {
			DetachedCriteria dc=DetachedCriteria.forClass(User.class);
			Session session=getSession();
			if(!StringUtils.isNotBlank(mid)) {
				mid= "";
			}
			Filter filter=session.enableFilter("user_myfriends");
			
			filter.setParameter("uid", mid);
			
			dc.add(Property.forName("id").eq(id));
			
			List<User> users=findAllByCriteria(session, dc);
			
			try {
				return users.get(0);
			}catch(Exception e) {
				return null;
			}
		}catch(RuntimeException re) {
			log.error("get failed",re);
			throw re;	
		}
	}
	
	
	public User findByAccount(String account) {
		try {
			DetachedCriteria dc=DetachedCriteria.forClass(User.class);
			Disjunction dis=Restrictions.disjunction();
			
			dis.add(Property.forName("account").eq(account));
			
			dc.add(dis);
			List<User> list=findAllByCriteria(dc);
			try {
				return list.get(0);
			}catch(Exception e) {
				return null;
			}
		}catch(RuntimeException re) {
			log.error("merge failed",re);
			throw re;
		}
	}
}
