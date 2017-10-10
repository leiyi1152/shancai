package com.icloud.common.dto.vo;

public class EventIamge {
	
	private String id;
	private String url;
	private boolean isIndex;
	
	public EventIamge(){
		
	}
     public EventIamge(String id,String url,boolean isIndex){
		this.id=id;
		this.url = url;
		this.isIndex=isIndex;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public boolean getIsIndex() {
		return isIndex;
	}
	public void setIsIndex(boolean isIndex) {
		this.isIndex = isIndex;
	}
	
	/*public static void main(String[] args) {
		//
		List<EventIamge> list = new ArrayList<EventIamge>() ;
		
		list.add(new EventIamge("1", "http://img02.sogoucdn.com/app/a/100520024/d97452e0a41f27d5eae93aa6230b503c",true));
		list.add(new EventIamge("2", "http://img03.sogoucdn.com/app/a/100520024/9c9b8991df66d491a85fa604faa3c834",false));
		list.add(new EventIamge("1", "http://img02.sogoucdn.com/app/a/100520024/dcc96bdf49c02e543911ca6583f7cbe2",false));
	   System.out.println(JSONObject.toJSONString(list));
	
	}*/
	

}
