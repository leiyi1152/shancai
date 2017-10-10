package com.icloud.common.dto;

public class BaseDto {

	protected String resultType;
	protected String resultCode;
	protected String errorMsg;

	public BaseDto() {

	}

	public BaseDto(String resultType, String resultCode) {
		super();
		this.resultType = resultType;
		this.resultCode = resultCode;
	}

	public BaseDto(String resultType, String resultCode, String errorMsg) {
		super();
		this.resultType = resultType;
		this.resultCode = resultCode;
		this.errorMsg = errorMsg;
	}

	
	public boolean isSuccess(){
		return ("success".endsWith(resultType) && "10000".endsWith(resultCode) );
	}
	
	
	public String getResultType() {
		return resultType;
	}

	public void setResultType(String resultType) {
		this.resultType = resultType;
	}

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

}
