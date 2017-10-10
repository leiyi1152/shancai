package com.icloud.common.dto;

/**
 * 添加记录返回信息 通用dto
 * 
 * @author luoqw 2016-9-7上午10:13:30
 */
public class BaseAddDto extends BaseDto  {

	 
	private Integer id;

	public BaseAddDto(String resultType, String resultCode, Integer id) {
		super();
		this.resultType = resultType;
		this.resultCode = resultCode;
		this.id = id;
	}

	public BaseAddDto() {

	}

	public BaseAddDto(String resultType, String resultCode) {
		super();
		this.resultType = resultType;
		this.resultCode = resultCode;
	}

	public BaseAddDto(String resultType, String resultCode, String errorMsg) {
		super();
		this.resultType = resultType;
		this.resultCode = resultCode;
		this.errorMsg = errorMsg;
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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

}
