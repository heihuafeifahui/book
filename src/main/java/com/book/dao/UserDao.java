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
			//构造离线查询
			DetachedCriteria dc=DetachedCriteria.forClass(User.class);
			Session session=getSession();
			//判断mid是否为空
			if(!StringUtils.isNotBlank(mid)) {
				mid= "";
			}
			//通过session创建过滤器过滤好友
			Filter filter=session.enableFilter("user_myfriends");
			//设置过滤器参数用户自我id关联
			filter.setParameter("uid", mid);
			//添加查询条件数据库查询"id"是否与id相等
			dc.add(Property.forName("id").eq(id));
			//将criteria.list()赋值给users
			List<User> users=findAllByCriteria(session, dc);
			
			try {
			//获得第一条元素
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
			//disunction组合一组逻辑或【or】条件
			Disjunction dis=Restrictions.disjunction();
			//判断账号是否相等
			dis.add(Property.forName("account").eq(account));
			//增加查询条件
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
