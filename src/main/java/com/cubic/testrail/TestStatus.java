package com.cubic.testrail;

public enum TestStatus {
	PASS("passed","1"),
	FAIL("failed","5"),
	BLOCKED("blocked","2"),
	UNTESTED("untested","3"),
	RETEST("retest","4");
	
	String status;
	String code;
	
	TestStatus(String status,String code){
		this.status=status;
		this.code=code;
	}

	public String getStatus() {
		return status;
	}

	public String getCode() {
		return code;
	}

}
