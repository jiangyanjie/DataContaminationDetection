package com.sabujak.angelshot.conf;

public class Define {
	
	public static String encoding = "UTF-8";
	
	public static String colmaEncryptkey = "eaqfk1ifpzm8z904sc7xf1s3l9z909tj";	//	콜마너 encrypt키
	public static String colmaDecryptkey = "zx8m770efpbz4lkfa7mf9scz909zzil1";	//	콜마너 decrypt키


	
	public static String colmaCapitalAreaUrl = "http://open.callmaner.com:8080/smartphone/tmap/mnservice.jsp";	//	수도권 URL
	public static String colmaExtraAreaUrl = "http://open.callmaner.com:8082/smartphone/tmap/mnservice.jsp";	//	지방 URL
	
	
	public static String colmaChargeUrl = "http://open.callmaner.com:8080/smartphone/tmap/charge.do";	
	public static String colmaOrderUrl = "http://open.callmaner.com:8080/smartphone/tmap/order.do";
	public static String colmaBatchUrl = "http://open.callmaner.com:8080/smartphone/tmap/batch.do";
	
	public static String nddsUrl_dev = "http://211.200.12.109:9070/esc-channel/poi/search/findpois";	//	자동완성 개발서버
	public static String nddsUrl_use = "http://211.200.12.110:9070/esc-channel/poi/search/findpois";	//	자동완성 준상용서버
	public static String ndds_dev_code = "D001";	//  test page
//	public static String ndds_dev_code = "D002";	//	fitnesse test
//	public static String ndds_dev_code = "D003";	//	모니터링용
//	public static String ndds_dev_code = "D004";	//	기타 test
	public static String ndds_ap_code = "ESC_AP_TMAPDRWJ";	// 어플리케이션 코드
	public static String ndds_content_type = "application/json";
	public static String ndds_method = "POST";
	
	public static String ndds_svc_requester = "tmap";
	public static String ndds_user_id = "ESC";
	public static int ndds_req_cnt = 5;
	public static String ndds_searchType = "A";	// A :정확도순, R :거리순
	
	
	public static String autocomplete_url = "211.200.12.179";
	public static int autocomplete_port = 7700;
	public static int autocomplete_timeout = 0;

	public static String url_test = "http://open.callmaner.com:8080/smartphone/tmap/order.do?t=";
	public static String url_real = "http://open.callmaner.com:8080/smartphone/tmap/order.do?p=";
	
	public static String colmaCapitalArea = "http://open.callmaner.com:8080";
	public static String colmaExtraArea = "http://open.callmaner.com:8082";
		
	public static String colmaCharge = "/smartphone/tmap/charge.do";	
	public static String colmaOrder = "/smartphone/tmap/order.do";
	public static String colmaBatch = "/smartphone/tmap/batch.do";
	
}