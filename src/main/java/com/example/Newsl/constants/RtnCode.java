package com.example.Newsl.constants;

public enum RtnCode {
	SUCCESS("200","News added successfully."),
	SUCCESS1("201","News altered successfully."),
	SUCCESS2("202","News deleted successfully."),
	Empty("203","Title, content, and category cannot be empty."),
	Empty1("204","no news"),
	Fail("205","Failed to alter news."),
	InvalidCategory("206","It's InvalidCategory.");
	
	
	private String code;
	private String message;

	private RtnCode(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	

}
