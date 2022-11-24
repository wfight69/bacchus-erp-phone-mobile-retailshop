/*****************************************************************************
* 파일명 : CommonCodeVo.java
* 패키지 : com.davada.common.vo
* 생성일 : 2011. 10. 25.
* 작성자 : 강덕준
* ===========================================================================
* 변경이력:
* DATE             AUTHOR      DESCRIPTION
* ---------------------------------------------------------------------------
* 변경 이력은 이곳에 추가 합니다.
*****************************************************************************/
package com.bacchuserpshop.common.vo;


public class CommonCodeVo {

	private String comm_cd_seq;	
	private String entprs_cd;	 	// 업체코드('000000 or master'은 공통코드 목록을 의미함. ex 01:직무, 02:직책등)
	private String comm_cd;	 		// 공통코드
	private String comm_name;	 	// 공통코드명
	private String use_yn; 			// 사용여부
	private String sort_no; 		// 소트번호
	private String etc1;	 		// 기타1
	private String etc2;	 
	private String etc3; 
	private String etc4; 
	private String remarks;			// 비고
	private String up_comm_cd;	 	// 상위공통드	
	private String regit;	 		// 등록자
	private String reg_date; 		// 등록일자
	private String modir;	 		// 수정자
	private String modi_date;		// 수정일자

	
	public String getComm_cd_seq() {
		return comm_cd_seq;
	}

	public void setComm_cd_seq(String comm_cd_seq) {
		this.comm_cd_seq = comm_cd_seq;
	}

	public String getEntprs_cd() {
		return entprs_cd == null ? "" : entprs_cd;
	}

	public void setEntprs_cd(String entprs_cd) {
		this.entprs_cd = entprs_cd;
	}

	public String getComm_cd() {
		return comm_cd == null ? "" : comm_cd;
	}

	public void setComm_cd(String comm_cd) {
		this.comm_cd = comm_cd;
	}

	public String getComm_name() {
		return comm_name == null ? "" : comm_name;
	}

	public void setComm_name(String comm_name) {
		this.comm_name = comm_name;
	}

	public String getUse_yn() {
		return use_yn == null ? "" : use_yn;
	}

	public void setUse_yn(String use_yn) {
		this.use_yn = use_yn;
	}

	public String getSort_no() {
		return sort_no == null ? "" : sort_no;
	}

	public void setSort_no(String sort_no) {
		this.sort_no = sort_no;
	}

	public String getEtc1() {
		return etc1 == null ? "" : etc1;
	}

	public void setEtc1(String etc1) {
		this.etc1 = etc1;
	}

	public String getEtc2() {
		return etc2 == null ? "" : etc2;
	}

	public void setEtc2(String etc2) {
		this.etc2 = etc2;
	}

	public String getEtc3() {
		return etc3 == null ? "" : etc3;
	}

	public void setEtc3(String etc3) {
		this.etc3 = etc3;
	}

	public String getEtc4() {
		return etc4 == null ? "" : etc4;
	}

	public void setEtc4(String etc4) {
		this.etc4 = etc4;
	}

	public String getRemarks() {
		return remarks == null ? "" : remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getUp_comm_cd() {
		return up_comm_cd == null ? "" : up_comm_cd;
	}

	public void setUp_comm_cd(String up_comm_cd) {
		this.up_comm_cd = up_comm_cd;
	}

	public String getRegit() {
		return regit == null ? "" : regit;
	}

	public void setRegit(String regit) {
		this.regit = regit;
	}

	public String getReg_date() {
		return reg_date == null ? "" : reg_date;
	}

	public void setReg_date(String reg_date) {
		this.reg_date = reg_date;
	}

	public String getModir() {
		return modir == null ? "" : modir;
	}

	public void setModir(String modir) {
		this.modir = modir;
	}

	public String getModi_date() {
		return modi_date == null ? "" : modi_date;
	}

	public void setModi_date(String modi_date) {
		this.modi_date = modi_date;
	}

	
}