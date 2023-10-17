package com.example.Newsl.constants;

public enum Msg {
	
	
	SUCCESS("200","add successfully."),
	SUCCESS1("201","News altered successfully."),
	SUCCESS2("202","News deleted successfully."),
	Empty("203","Title, content, and category cannot be empty."),
	Empty1("204","input is empty"),
	Empty2("205","category or parentcategory is empty"),
	Fail("206","Failed to alter news."),
	Fail1("207","ParentCategoryBame and input cannot be the same."),
	InvalidCategory("207","It's InvalidCategory."),
	Status("301","未發布"),
	Status1("302","已發布"),
	Remind("401","pls input the valid date.");
	
	
	
	private String code;     // コード
	private String message;  // メッセージ


	private Msg(String code, String message) {
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
