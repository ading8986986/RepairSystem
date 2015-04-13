package cn.repairsystem.util;

public class Constants {
	public static final String SETTINGS = "settings";
	
	public static final String SERVER_IP = "http://115.28.11.33/hqbx2015/";
	//public static final String SERVER_IP = "http://202.205.92.17:8080/bxxtapi/";
	
	public static final String LOGIN = SERVER_IP + "userlogin/";
	public static final String REPAIR_INFO = SERVER_IP + "usermobile/getforminf";
	public static final String REPAIRED_PROJECT = SERVER_IP + "usermobile/getbxlistbybxrid";
	public static final String REPAIR_DETAIL = SERVER_IP + "usermobile/getbxinfbybxid";
	public static final String REPAIR_BUILD = SERVER_IP + "usermobile/getbuildlist";
	public static final String REPAIR_SUBPROJECT = SERVER_IP + "usermobile/getwxxxmlist";
	public static final String SUBMIT = SERVER_IP + "usermobile/bxsave";
	
	public static final String DETAIL_ENROLL_PLAN = SERVER_IP + "getzsjhinf";
	public static final String MY_SCHOOL = SERVER_IP + "myschoollist";

	public static final String APP_ENV = "app_env";
	public static final String IS_FIRST_INSTALL = "is_first_install";
	public static final String APP_NAME = "taoschool.apk";
	
}
