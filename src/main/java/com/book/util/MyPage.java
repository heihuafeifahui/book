package com.book.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyPage<T> implements Serializable {
	private static final long serialVersionUID = -7538513433895399481L;
	//每页数量
	public static final int PAGESIZE=20;
	private int pagesize=PAGESIZE;	
	//对象集合
	private List<T> items;
	//对象总数
	private int totalcount;
	//起始查询记录
	private int startindex=0;
	//总页数
	private int pagecount;
	//当前页
	private int page;
	//相近的五个页码
	private List<Integer> pages=new ArrayList<Integer>();
	//上一页码
	private int pre;
	//下一页码
	private int next;
	//数据是否超过一页
	private boolean paging;
	
	public boolean isPaging() {
		if(this.pages.size()>1)
			paging=true;
		else
			paging=false;
		return paging;
	}
	
	public MyPage() {
		this.totalcount=0;
		this.pagesize=1;
		this.page=1;
	}
	
	public MyPage(int totalcount,int pagesize,int page) {
		//对象集合获取数组第一条元素
		this.items=new ArrayList<T>(0);
		
		this.totalcount=totalcount;
		//判断总条数是否小于0
		if(this.totalcount < 0)
			this.totalcount=0;
		
		
		this.pagesize=pagesize;
		//判断每页数量是否小于1
		if(this.pagesize<1 )
			this.pagesize=1;
		
		this.page=page;
		//判断当前页是否小于1
		if(this.page<1)
			this.page=1;
		//当前页第一条等于(当前页-1)*每页条数
		this.startindex=(page-1)*pagesize;
		//总页数等于 总条数除以每页数量(向上取整)
		this.pagecount = (int) Math.ceil(Arith.div(this.totalcount, this.pagesize));
		
		this.next=this.page+1;
		//下一页不能超过总页数
		if(this.next>this.pagecount)
			this.next=this.pagecount;
		
		this.pre=this.page-1;
		//上一页不能小于1
		if(this.pre<1)
			this.pre=1;
		
//		判断相近的五个页面
		int first=1;
		int end=this.page+2;
		//当前页减2大于零，否则5页中最小页为1
		if(this.page-2>0) {
			first=this.page-2;
		}
		//如果相近5页的最后一页大于总页数的最后一页，则等于总页数最后一页
		if(end>this.pagecount) {
			end=this.pagecount;
		}
		//当前页为第一页时，显示当前页与后四页
		if((end-first)<4 && (first + 4) <=this.pagecount) {
			end=first+4;
		}
		//当前页最后一页，显示前四页与当前页
		if((end-first)<4 && (end-4) >=1) {
			first=end-4;
		}
		
		int fornum=end-first+1;
		this.pages=new ArrayList<Integer>();
		for(int i=0; i<fornum;i++) {
			this.pages.add(first);
			first++;
		}
	}
	
	public List<List<T>> split(int num){
		Map<Integer,List<T>> map=new HashMap<Integer,List<T>>();
		if(num<1) {
			num=1;
		}
		
		int column=(int) Math.ceil(Arith.div(totalcount, num));
		if(column <= num) {
			num=column;
		}
		
		if(null!=items && items.size()>0) {
			for (int i = 1; i<= num ;i++) {
				List<T> sublist=new ArrayList<T>();
				map.put(i, sublist);
			}
			int i=1;
			for(T object : items) {
				List<T> temp=map.get(i);
				temp.add(object);
				if(i>num) {
					i=1;
				}else {
					i++;
				}
			}
		}
		List<List<T>> list=new ArrayList<List<T>>(map.values());	
		return list;
	}
	public int getPagesize() {
		return pagesize;
	}
	public void setPagesize(int pagesize) {
		this.pagesize = pagesize;
	}
	public List<T> getItems() {
		return items;
	}
	public void setItems(List<T> items) {
		this.items = items;
	}
	public int getTotalcount() {
		return totalcount;
	}
	public void setTotalcount(int totalcount) {
		this.totalcount = totalcount;
	}
	public int getStartindex() {
		return startindex;
	}
	public void setStartindex(int startindex) {
		this.startindex = startindex;
	}
	public int getPagecount() {
		return pagecount;
	}
	public void setPagecount(int pagecount) {
		this.pagecount = pagecount;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public List<Integer> getPages() {
		return pages;
	}
	public void setPages(List<Integer> pages) {
		this.pages = pages;
	}
	public int getPre() {
		return pre;
	}
	public void setPre(int pre) {
		this.pre = pre;
	}
	public int getNext() {
		return next;
	}
	public void setNext(int next) {
		this.next = next;
	}
	public void setPaging(boolean paging) {
		this.paging = paging;
	}
	
	
}

