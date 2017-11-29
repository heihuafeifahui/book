package com.book.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.book.util.MyPage;

public class BaseDao<T> {
	protected final Logger log = LoggerFactory.getLogger( getClass() );

	@PersistenceContext
	private EntityManager entityManager;

	public Session getSession() {
		return entityManager.unwrap(Session.class);
	}

	public MyPage<T> findPageByCriteria(final DetachedCriteria detachedCriteria) {
		return findPageByCriteria(detachedCriteria, MyPage.PAGESIZE, 0);
	}

	public MyPage<T> findPageByCriteria(final DetachedCriteria detachedCriteria, int page) {
		return findPageByCriteria(detachedCriteria, MyPage.PAGESIZE, page);
	}

	public MyPage<T> findPageByCriteria(final DetachedCriteria detachedCriteria, final int pageSize,
			int page) {
//		绑定一个Session并返回criteria实例
		Criteria criteria = detachedCriteria.getExecutableCriteria(getSession());
//		根据条件查询记录数
//		rowCount 返回查询结果的总条数
//		uniqueResult 数据库根据查询条件返回唯一结果
		Object object = criteria.setProjection(Projections.rowCount()).uniqueResult();
//		设置总条数为0
		long totalCount = 0;
		try {
			//将查询的记录数量赋值给总条数
			totalCount = (Long) object;
		} catch (Exception e) {
		}
		
		MyPage<T> ps = new MyPage<T>((int) totalCount, pageSize, page);
		//分组或统计为空
		criteria.setProjection(null);
		//对查询后的重复的结果进行排查
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		//从起始页开始查询,取每页 (相当于MySQL中的limit 0, 4;)
		List<T> items = criteria.setFirstResult(ps.getStartindex()).setMaxResults(pageSize).list();
		//MyPage<T> ps = new MyPage<T>(items, (int) totalCount, pageSize, startIndex);
		ps.setItems(items);
		return ps;
	}

	public MyPage<T> findPageByCriteria(Session session, final DetachedCriteria detachedCriteria,
			final int pageSize, int page) {
		//final int startIndex = (page - 1) * pageSize;
		Criteria criteria = detachedCriteria.getExecutableCriteria(session);
		Object object = criteria.setProjection(Projections.rowCount()).uniqueResult();
		long totalCount = 0;
		try {
			totalCount = (Long) object;
		} catch (Exception e) {
		}
		MyPage<T> ps = new MyPage<T>((int) totalCount, pageSize, page);
		criteria.setProjection(null);
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		List<T> items = criteria.setFirstResult(ps.getStartindex()).setMaxResults(pageSize).list();
		//MyPage<T> ps = new MyPage<T>(items, (int) totalCount, pageSize, startIndex);
		ps.setItems(items);
		return ps;
	}

	//离线查询转在线
	public List<T> findAllByCriteria(final DetachedCriteria detachedCriteria) {
		Criteria criteria = detachedCriteria.getExecutableCriteria(getSession());
		return criteria.list();
	}

	public List<T> findAllByCriteria(Session session, final DetachedCriteria detachedCriteria) {
		Criteria criteria = detachedCriteria.getExecutableCriteria(session);
		return criteria.list();
	}

	public int getCountByCriteria(final DetachedCriteria detachedCriteria) {
		Criteria criteria = detachedCriteria.getExecutableCriteria(getSession());
		Object object = criteria.setProjection(Projections.rowCount()).uniqueResult();
		long totalCount = 0;
		try {
			totalCount = (Long) object;
		} catch (Exception e) {
		}
		return (int) totalCount;
	}

}