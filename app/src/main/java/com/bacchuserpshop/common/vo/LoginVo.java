/*****************************************************************************
* 파일명 : LoginVo.java
* 패키지 : com.davada.common.vo
* 생성일 : 2011. 10. 19.
* 작성자 : 
* ===========================================================================
* 변경이력:
* DATE             AUTHOR      DESCRIPTION
* ---------------------------------------------------------------------------
* 변경 이력은 이곳에 추가 합니다.
*****************************************************************************/
package com.bacchuserpshop.common.vo;

public class LoginVo {
	
	// common
	private String entprs_cd;			// 서비스업체코드		
	private String busi_r_no;			// 사업자번호
	private String busi_r_name;			// 사업자명
	private String entprs_name;			// 상호명		
	private String comp_no;				// 법인번호	
	private String regn_cd;				// 지역코드
	private String regn_name;			// 지역명
	private String compn_tel_no;		// 회사전화번호
	private String compn_fax_no;		// 회사팩스번호
	private String compn_open_date;		// 회사개업일
	private String compn_email	;		// 회사이메일
	private String reprs_cell_no;		// 대표휴대폰번호	
	private String reprs_email	;		// 대표이메일
	
	private String user_id;				// 사용자id
	private String user_no;				// 사번(사업자번호)
	private String user_name;			// 성명
	private String cell_tel_no;			// 휴대전화번호	
	private String chrg_name;			// 코스-담당자명-차후 검색에 사용함
	private String home_tel_no;			// 자택전화
	private String psn_cell_no;			// 개인대표휴대전화
	private String psn_email;			// 개인대표이메일
	
	private String ordr_busi_r_no;		// 거래처코드
	private String ordr_busi_r_name;	// 거래처명
	private String hd_offc_mobile_no;	// 대표핸드폰번호
	private String hd_offc_tel_no1;		// 대표 전화번호1
	private String hd_offc_tel_no;		// 대표전화
	private String call_chng_div;		// 착신상태

	private String message_filter;
	private String except_filter;
	private String filter_use_yn;
	private String mobile_sms_send_yn;
	private String mobile_yn;			// Mobile주문 이용 여부(Y/N)
	private String mobile_use_div;		// 모바일사용 서비스구분(A:전체,S:문자-WEB,V:음성)

	private String site_div;			// 언어구분(한글-영문)
	private String skin_type;			// SKIN TYPE
	private String admin_yn;			// OBS 관리자구분
	private String system_admin_yn;		// 시스템 관리자구분
	
	//
	private String job_posi_name;		// 직위(회장,사장,상무,이사등)
	private String job_duty_name;		// 직책(영업담당,총무담당등)
	
	// 2013.07.05일 추가(영업사원 로그인 및 목록처리)
	private String login_div;				//  GM: Gereral Market(소매점), SM: Sales Man(영업사원) 
	
	// 2013.08.16일 추가(영업사원 접속시 엄체건수/및 메시지 필터
	private String equp_item_model_cnt;	// 자산품목모델 건수
	private String common_code_cnt;		// 공통코드 겉수
	private String salesman_entprs_cnt;	// 영업사원별 업체건수
	private String as_svc_amt;			// AS서비스금액
	
	// 2014.10.14추가(영업사원 or AS수리업체 로그인)
	private String dept_cd;				// 현소속부서코드
	private String entprs_bing_div;		// 업체소속구분(1:ㅈ류업체, 2:수리AS업체, 3:제조업체, 4:기타
	private String as_entprs_cnt;		// 수리업체 담당자 AS할 주류업체 건수
	private String as_entprs_list;		// AS요청수신되어진 주류업체 목록(명), 주류업체사업자번호:명 을 token개념 처리	

	// AS접수확인 및  AS승인완료에 사용되어짐(업체설정에 등록되어짐)
	private String 	sms_as_appr_cell_no; 			// 승인완료후 처리할 추가승인자 폰번호
	
	// 2015.04.06일 추가(자산조사및 자산서비스에 사진추가건수 제어)
	private String  equp_item_cd_max_seq;			// 자산(장비)품목코드 최대값 저장
	private String  equp_rsch_pto_max;				// 자산조사 사진추가 최대건수
	private String  equp_as_pto_max;				// 자산as 사진추가 최대 건수 	
	private String  equp_rsch_pto_yn;				// 자산조사 사진추가 여부(y/n)
	private String  equp_as_pto_yn;					// 자산as 사진추가 여부(y/n)	
	
	// 로그인 클라이언트 세션id 설정
	private String session_id;			

	// 메시지처리방식(카톡,sms)
	private String  app_ktalk_inner_send_div;		// 모바일앱 카톡 내부사용자 전송여부 (1:SMS,2:KTalk,3:미전송),
	private String  app_ktalk_out1_send_div;		// 모바일앱 카톡 외부사용자1 전송여부(매입처) ,
	private String  app_ktalk_out2_send_div;		// 모바일앱 카톡 외부사용자2 전송여부(매출처) ,
    private String  app_ktalk_out3_send_div;		// 모바일앱 카톡 외부사용자3 전송여부(납품처) ,
    private String  pc_ktalk_send_div;				// pc처리 카톡 전송여부 (1:SMS,2:KTalk,3:미전송)
    
	// 메뉴권한처리 여부(차후 염두에둔 처리)
	private String  menu1_grant_yn;					// 자산조사관리
	private String  menu2_grant_yn;					// AS수신관리
	private String  menu3_grant_yn;					// AS승인관리
	private String  menu4_grant_yn;					// 자산관리(지원:1,수리:2,회수:3,분실:4,폐기:5,점검:6,판매:7 등)
	private String  menu5_grant_yn;					// 약정관리
	private String  menu6_grant_yn;					// 자산현황(관리자)
	private String  menu7_grant_yn;					
	private String  menu8_grant_yn;		
	private String  menu9_grant_yn;
	
    // AS수신시 AS처리후 승인요청없이 바로 승인완료 처리여부
    private String  appr_as1_fin_yn;				// AS출고 AS수신처리후 승인요청(N)/승인완료(Y)  처리여부 체크
    private String  appr_as2_fin_yn;				// AS수리 AS수신처리후 승인요청(N)/승인완료(Y)  처리여부 체크
    private String  appr_as3_fin_yn;				// AS회수 AS수신처리후 승인요청(N)/승인완료(Y)  처리여부 체크
    private String  appr_as9_fin_yn;				// AS기타 AS수신처리후 승인요청(N)/승인완료(Y)  처리여부 체크      
    
    // 사진찍기 카메라 타입 선택
    private String take_pic_camera_type;			// 사진찍기 카메라 타입(A:다바다앱카메라, P:시스템폰카메라)
    
    // 플레이 스토어에 업로드 되어진  냉동냉장AS관리 sw버전
    private String playstore_frozen_ver;

	// 담당자별 위치전송 여부(Y/N)
	private String gps_send_yn;
	
	public String getEntprs_cd() {
		return entprs_cd;
	}
	public void setEntprs_cd(String entprs_cd) {
		this.entprs_cd = entprs_cd;
	}
	public String getBusi_r_no() {
		return busi_r_no;
	}
	public void setBusi_r_no(String busi_r_no) {
		this.busi_r_no = busi_r_no;
	}
	public String getBusi_r_name() {
		return busi_r_name;
	}
	public void setBusi_r_name(String busi_r_name) {
		this.busi_r_name = busi_r_name;
	}
	public String getEntprs_name() {
		return entprs_name;
	}
	public void setEntprs_name(String entprs_name) {
		this.entprs_name = entprs_name;
	}
	public String getComp_no() {
		return comp_no;
	}
	public void setComp_no(String comp_no) {
		this.comp_no = comp_no;
	}
	public String getRegn_cd() {
		return regn_cd;
	}
	public void setRegn_cd(String regn_cd) {
		this.regn_cd = regn_cd;
	}
	public String getRegn_name() {
		return regn_name;
	}
	public void setRegn_name(String regn_name) {
		this.regn_name = regn_name;
	}
	public String getCompn_tel_no() {
		return compn_tel_no;
	}
	public void setCompn_tel_no(String compn_tel_no) {
		this.compn_tel_no = compn_tel_no;
	}
	public String getCompn_fax_no() {
		return compn_fax_no;
	}
	public void setCompn_fax_no(String compn_fax_no) {
		this.compn_fax_no = compn_fax_no;
	}
	public String getCompn_open_date() {
		return compn_open_date;
	}
	public void setCompn_open_date(String compn_open_date) {
		this.compn_open_date = compn_open_date;
	}
	public String getCompn_email() {
		return compn_email;
	}
	public void setCompn_email(String compn_email) {
		this.compn_email = compn_email;
	}
	public String getReprs_cell_no() {
		return reprs_cell_no;
	}
	public void setReprs_cell_no(String reprs_cell_no) {
		this.reprs_cell_no = reprs_cell_no;
	}
	public String getReprs_email() {
		return reprs_email;
	}
	public void setReprs_email(String reprs_email) {
		this.reprs_email = reprs_email;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getUser_no() {
		return user_no;
	}
	public void setUser_no(String user_no) {
		this.user_no = user_no;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getCell_tel_no() {
		return cell_tel_no;
	}
	public void setCell_tel_no(String cell_tel_no) {
		this.cell_tel_no = cell_tel_no;
	}
	public String getChrg_name() {
		return chrg_name;
	}
	public void setChrg_name(String chrg_name) {
		this.chrg_name = chrg_name;
	}
	public String getHome_tel_no() {
		return home_tel_no;
	}
	public void setHome_tel_no(String home_tel_no) {
		this.home_tel_no = home_tel_no;
	}
	public String getPsn_cell_no() {
		return psn_cell_no;
	}
	public void setPsn_cell_no(String psn_cell_no) {
		this.psn_cell_no = psn_cell_no;
	}
	public String getPsn_email() {
		return psn_email;
	}
	public void setPsn_email(String psn_email) {
		this.psn_email = psn_email;
	}
	public String getOrdr_busi_r_no() {
		return ordr_busi_r_no;
	}
	public void setOrdr_busi_r_no(String ordr_busi_r_no) {
		this.ordr_busi_r_no = ordr_busi_r_no;
	}
	public String getOrdr_busi_r_name() {
		return ordr_busi_r_name;
	}
	public void setOrdr_busi_r_name(String ordr_busi_r_name) {
		this.ordr_busi_r_name = ordr_busi_r_name;
	}
	public String getHd_offc_mobile_no() {
		return hd_offc_mobile_no;
	}
	public void setHd_offc_mobile_no(String hd_offc_mobile_no) {
		this.hd_offc_mobile_no = hd_offc_mobile_no;
	}
	public String getHd_offc_tel_no1() {
		return hd_offc_tel_no1;
	}
	public void setHd_offc_tel_no1(String hd_offc_tel_no1) {
		this.hd_offc_tel_no1 = hd_offc_tel_no1;
	}
	public String getHd_offc_tel_no() {
		return hd_offc_tel_no;
	}
	public void setHd_offc_tel_no(String hd_offc_tel_no) {
		this.hd_offc_tel_no = hd_offc_tel_no;
	}
	public String getCall_chng_div() {
		return call_chng_div;
	}
	public void setCall_chng_div(String call_chng_div) {
		this.call_chng_div = call_chng_div;
	}

	public String getMessage_filter() {
		return message_filter;
	}

	public void setMessage_filter(String message_filter) {
		this.message_filter = message_filter;
	}

	public String getExcept_filter() {
		return except_filter;
	}

	public void setExcept_filter(String except_filter) {
		this.except_filter = except_filter;
	}

	public String getFilter_use_yn() {
		return filter_use_yn == null ? "N" : filter_use_yn;
	}

	public void setFilter_use_yn(String filter_use_yn) {
		this.filter_use_yn = filter_use_yn;
	}

	public String getMobile_sms_send_yn() {
		return mobile_sms_send_yn == null ? "N" : mobile_sms_send_yn;
	}

	public void setMobile_sms_send_yn(String mobile_sms_send_yn) {
		this.mobile_sms_send_yn = mobile_sms_send_yn;
	}

	public String getMobile_yn() {
		return mobile_yn;
	}
	public void setMobile_yn(String mobile_yn) {
		this.mobile_yn = mobile_yn;
	}
	public String getMobile_use_div() {
		return mobile_use_div;
	}
	public void setMobile_use_div(String mobile_use_div) {
		this.mobile_use_div = mobile_use_div;
	}
	public String getSite_div() {
		return site_div;
	}
	public void setSite_div(String site_div) {
		this.site_div = site_div;
	}
	public String getSkin_type() {
		return skin_type;
	}
	public void setSkin_type(String skin_type) {
		this.skin_type = skin_type;
	}
	public String getAdmin_yn() {
		return admin_yn;
	}
	public void setAdmin_yn(String admin_yn) {
		this.admin_yn = admin_yn;
	}
	public String getJob_posi_name() {
		return job_posi_name;
	}
	public void setJob_posi_name(String job_posi_name) {
		this.job_posi_name = job_posi_name;
	}
	public String getJob_duty_name() {
		return job_duty_name;
	}
	public void setJob_duty_name(String job_duty_name) {
		this.job_duty_name = job_duty_name;
	}
	public String getSystem_admin_yn() {
		return system_admin_yn;
	}
	public void setSystem_admin_yn(String system_admin_yn) {
		this.system_admin_yn = system_admin_yn;
	}
	public String getLogin_div() {
		return login_div;
	}
	public void setLogin_div(String login_div) {
		this.login_div = login_div;
	}
	public String getEqup_item_model_cnt() {
		return equp_item_model_cnt;
	}
	public void setEqup_item_model_cnt(String equp_item_model_cnt) {
		this.equp_item_model_cnt = equp_item_model_cnt;
	}
	public String getCommon_code_cnt() {
		return common_code_cnt;
	}
	public void setCommon_code_cnt(String common_code_cnt) {
		this.common_code_cnt = common_code_cnt;
	}
	public String getSalesman_entprs_cnt() {
		return salesman_entprs_cnt;
	}
	public void setSalesman_entprs_cnt(String salesman_entprs_cnt) {
		this.salesman_entprs_cnt = salesman_entprs_cnt;
	}
	public String getAs_svc_amt() {
		return as_svc_amt;
	}

	public void setAs_svc_amt(String as_svc_amt) {
		this.as_svc_amt = as_svc_amt;
	}

	public String getDept_cd() {
		return dept_cd;
	}
	public void setDept_cd(String dept_cd) {
		this.dept_cd = dept_cd;
	}
	public String getEntprs_bing_div() {
		return entprs_bing_div;
	}
	public void setEntprs_bing_div(String entprs_bing_div) {
		this.entprs_bing_div = entprs_bing_div;
	}
	public String getAs_entprs_cnt() {
		return as_entprs_cnt;
	}
	public void setAs_entprs_cnt(String as_entprs_cnt) {
		this.as_entprs_cnt = as_entprs_cnt;
	}
	public String getAs_entprs_list() {
		return as_entprs_list;
	}
	public void setAs_entprs_list(String as_entprs_list) {
		this.as_entprs_list = as_entprs_list;
	}
	public String getSms_as_appr_cell_no() {
		return sms_as_appr_cell_no;
	}
	public void setSms_as_appr_cell_no(String sms_as_appr_cell_no) {
		this.sms_as_appr_cell_no = sms_as_appr_cell_no;
	}
	public String getEqup_item_cd_max_seq() {
		return equp_item_cd_max_seq;
	}
	public void setEqup_item_cd_max_seq(String equp_item_cd_max_seq) {
		this.equp_item_cd_max_seq = equp_item_cd_max_seq;
	}
	public String getEqup_rsch_pto_max() {
		return equp_rsch_pto_max;
	}
	public void setEqup_rsch_pto_max(String equp_rsch_pto_max) {
		this.equp_rsch_pto_max = equp_rsch_pto_max;
	}
	public String getEqup_as_pto_max() {
		return equp_as_pto_max;
	}
	public void setEqup_as_pto_max(String equp_as_pto_max) {
		this.equp_as_pto_max = equp_as_pto_max;
	}
	public String getEqup_rsch_pto_yn() {
		return equp_rsch_pto_yn;
	}
	public void setEqup_rsch_pto_yn(String equp_rsch_pto_yn) {
		this.equp_rsch_pto_yn = equp_rsch_pto_yn;
	}
	public String getEqup_as_pto_yn() {
		return equp_as_pto_yn;
	}
	public void setEqup_as_pto_yn(String equp_as_pto_yn) {
		this.equp_as_pto_yn = equp_as_pto_yn;
	}
	public String getSession_id() {
		return session_id;
	}
	public void setSession_id(String session_id) {
		this.session_id = session_id;
	}
	/**
	 * @return the app_ktalk_inner_send_div
	 */
	public String getApp_ktalk_inner_send_div() {
		return app_ktalk_inner_send_div;
	}
	/**
	 * @param app_ktalk_inner_send_div the app_ktalk_inner_send_div to set
	 */
	public void setApp_ktalk_inner_send_div(String app_ktalk_inner_send_div) {
		this.app_ktalk_inner_send_div = app_ktalk_inner_send_div;
	}
	/**
	 * @return the app_ktalk_out1_send_div
	 */
	public String getApp_ktalk_out1_send_div() {
		return app_ktalk_out1_send_div;
	}
	/**
	 * @param app_ktalk_out1_send_div the app_ktalk_out1_send_div to set
	 */
	public void setApp_ktalk_out1_send_div(String app_ktalk_out1_send_div) {
		this.app_ktalk_out1_send_div = app_ktalk_out1_send_div;
	}
	/**
	 * @return the app_ktalk_out2_send_div
	 */
	public String getApp_ktalk_out2_send_div() {
		return app_ktalk_out2_send_div;
	}
	/**
	 * @param app_ktalk_out2_send_div the app_ktalk_out2_send_div to set
	 */
	public void setApp_ktalk_out2_send_div(String app_ktalk_out2_send_div) {
		this.app_ktalk_out2_send_div = app_ktalk_out2_send_div;
	}
	/**
	 * @return the app_ktalk_out3_send_div
	 */
	public String getApp_ktalk_out3_send_div() {
		return app_ktalk_out3_send_div;
	}
	/**
	 * @param app_ktalk_out3_send_div the app_ktalk_out3_send_div to set
	 */
	public void setApp_ktalk_out3_send_div(String app_ktalk_out3_send_div) {
		this.app_ktalk_out3_send_div = app_ktalk_out3_send_div;
	}
	/**
	 * @return the pc_ktalk_send_div
	 */
	public String getPc_ktalk_send_div() {
		return pc_ktalk_send_div;
	}
	/**
	 * @param pc_ktalk_send_div the pc_ktalk_send_div to set
	 */
	public void setPc_ktalk_send_div(String pc_ktalk_send_div) {
		this.pc_ktalk_send_div = pc_ktalk_send_div;
	}

	public String getMenu1_grant_yn() {
		return menu1_grant_yn;
	}
	public void setMenu1_grant_yn(String menu1_grant_yn) {
		this.menu1_grant_yn = menu1_grant_yn;
	}
	public String getMenu2_grant_yn() {
		return menu2_grant_yn;
	}
	public void setMenu2_grant_yn(String menu2_grant_yn) {
		this.menu2_grant_yn = menu2_grant_yn;
	}
	public String getMenu3_grant_yn() {
		return menu3_grant_yn;
	}
	public void setMenu3_grant_yn(String menu3_grant_yn) {
		this.menu3_grant_yn = menu3_grant_yn;
	}
	public String getMenu4_grant_yn() {
		return menu4_grant_yn;
	}
	public void setMenu4_grant_yn(String menu4_grant_yn) {
		this.menu4_grant_yn = menu4_grant_yn;
	}
	public String getMenu5_grant_yn() {
		return menu5_grant_yn;
	}
	public void setMenu5_grant_yn(String menu5_grant_yn) {
		this.menu5_grant_yn = menu5_grant_yn;
	}
	public String getMenu6_grant_yn() {
		return menu6_grant_yn;
	}
	public void setMenu6_grant_yn(String menu6_grant_yn) {
		this.menu6_grant_yn = menu6_grant_yn;
	}
	public String getMenu7_grant_yn() {
		return menu7_grant_yn;
	}
	public void setMenu7_grant_yn(String menu7_grant_yn) {
		this.menu7_grant_yn = menu7_grant_yn;
	}
	public String getMenu8_grant_yn() {
		return menu8_grant_yn;
	}
	public void setMenu8_grant_yn(String menu8_grant_yn) {
		this.menu8_grant_yn = menu8_grant_yn;
	}
	public String getMenu9_grant_yn() {
		return menu9_grant_yn;
	}
	public void setMenu9_grant_yn(String menu9_grant_yn) {
		this.menu9_grant_yn = menu9_grant_yn;
	}

	public String getAppr_as1_fin_yn() {
		return appr_as1_fin_yn == null ? "N" : appr_as1_fin_yn;
	}
	
	public void setAppr_as1_fin_yn(String appr_as1_fin_yn) {
		this.appr_as1_fin_yn = appr_as1_fin_yn;
	}

	public String getAppr_as2_fin_yn() {
		return appr_as2_fin_yn == null ? "N" : appr_as2_fin_yn;
	}

	public void setAppr_as2_fin_yn(String appr_as2_fin_yn) {
		this.appr_as2_fin_yn = appr_as2_fin_yn;
	}

	public String getAppr_as3_fin_yn() {
		return appr_as3_fin_yn == null ? "N" : appr_as3_fin_yn;
	}

	public void setAppr_as3_fin_yn(String appr_as3_fin_yn) {
		this.appr_as3_fin_yn = appr_as3_fin_yn;
	}

	public String getAppr_as9_fin_yn() {
		return appr_as9_fin_yn == null ? "N" : appr_as9_fin_yn;
	}

	public void setAppr_as9_fin_yn(String appr_as9_fin_yn) {
		this.appr_as9_fin_yn = appr_as9_fin_yn;
	}
	/**
	 * @return the take_pic_camera_type
	 */
	public String getTake_pic_camera_type() {
		return take_pic_camera_type == null ? "A" : take_pic_camera_type;
	}
	/**
	 * @param take_pic_camera_type the take_pic_camera_type to set
	 */
	public void setTake_pic_camera_type(String take_pic_camera_type) {
		this.take_pic_camera_type = take_pic_camera_type;
	}
	/**
	 * @return the playstore_frozen_ver
	 */
	public String getPlaystore_frozen_ver() {
		return playstore_frozen_ver == null ? "0" : playstore_frozen_ver;
	}
	/**
	 * @param playstore_frozen_ver the playstore_frozen_ver to set
	 */
	public void setPlaystore_frozen_ver(String playstore_frozen_ver) {
		this.playstore_frozen_ver = playstore_frozen_ver;
	}

	public String getGps_send_yn() {
		return gps_send_yn == null ? "N" : gps_send_yn;
	}

	public void setGps_send_yn(String gps_send_yn) {
		this.gps_send_yn = gps_send_yn;
	}

}