package com.cubic.testrail;

public enum TestStatus {
	PASS("passed","1"),
	FAIL("failed","5"),
	BLOCKED("blocked","2"),
	UNTESTED("untested","3"),
	RETEST("retest","4"),
	NOT_APPLICABLE("custom_status1","6"),
	CONDITIONALLY_PASSED("conditonally_passed","7"),
	NOT_COMPLETED("not_completed","8"),
	FIELD_TEST_REQUIRED("custom_status4","9"),
	CARRIED_FORWARD("carried_forward","10");
	
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
