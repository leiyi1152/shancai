package com.icloud.model;

public class BaseModel {
	
	private Integer pageNo=1;
	private Integer pageSize=10;
	private Integer startRow=0;
	private Integer pages=1;
	private Integer totalNum;
	public Integer getPageNo() {
		return pageNo;
	}
	public void setPageNo(Integer pageNo) {
		this.startRow = (pageNo - 1) * pageSize;
		this.pageNo = pageNo;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.startRow = (pageNo - 1) * pageSize;
		this.pageSize = pageSize;
	}
	public Integer getPages() {
		return pages;
	}
	public void setPages(Integer pages) {
		this.pages = pages;
	}
	public Integer getTotalNum() {
		return totalNum;
	}
	public void setTotalNum(Integer totalNum) {
		this.totalNum = totalNum;
	    
		this.pages = totalNum%pageSize==0?totalNum/pageSize:totalNum/pageSize+1;
		 
	}
	public void setPages(int totalNum,int pageSize){
		this.pages = totalNum%pageSize==0?totalNum/pageSize:totalNum/pageSize+1;
	}
	public Integer getStartRow() {
		return startRow;
	}
	public void setStartRow(Integer startRow) {
		this.startRow = startRow;
	}

}
