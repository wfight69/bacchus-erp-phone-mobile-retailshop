/*****************************************************************************
* 파일명 : FileVo.java
* 패키지 : com.davada.common.file
* 생성일 : 2011. 11. 29.
* 작성자 : 강덕준
* ===========================================================================
* 변경이력:
* DATE             AUTHOR      DESCRIPTION
* ---------------------------------------------------------------------------
* 변경 이력은 이곳에 추가 합니다.
*****************************************************************************/
package com.bacchuserpshop.common.vo;


public class FileVo {
 	 
	private String file_no;			// 파일 순번
	private String file_seq;		// 파일 시퀀스
	private String title;			// 제목(다운로드 링크 포함)
	private String file_name;		// 파일명칭
	private String file_path;		// 파일 경로
	private String file_ext;		// 파일확장자
	private String file_ext_img;	// 파일확장자 이미지 url
	private String file_url;		// 파일 링크
	private String file_size;		// 파일 사이즈
	private String file_size_byte;	// 원본파일 Byte Size
	private String file_width;		// 이미지 가로사이즈
    private String file_height;		// 이미지 세로사이즈
	private String file_main_yn;	// 메인파일여부(메인:Y)
	private byte[] file_image;		// 파일 이미지			// blob : byte[], clob : String
	private String file_type;		// 파일구분	
	private String entprs_cd;		// 서비스업체코드	
	private String pgm_name;		// 등록한 프로그램명
	private String regit;			// 등록자
	private String reg_date;		// 등록일자
	private String modir;			// 수정자
	private String modi_date;		// 수정일자
	private String width;			// 이미지 가로사이즈
    private String height;			// 이미지 세로사이즈
	
	private String[] file_seq_array;	//파일 순번 배열
	
	private boolean success_flag;	// 업로드 성공여부
	

	public String getFile_no() {
		return file_no == null ? "" : file_no;
	}

	public void setFile_no(String file_no) {
		this.file_no = file_no;
	}

	public String getFile_seq() {
		return file_seq == null ? "" : file_seq;
	}

	public void setFile_seq(String file_seq) {
		this.file_seq = file_seq;
	}

	public String getTitle() {
		return title == null ? "" : title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getFile_name() {
		return file_name == null ? "" : file_name;
	}

	public void setFile_name(String file_name) {
		this.file_name = file_name;
	}

	public String getFile_path() {
		return file_path == null ? "" : file_path;
	}

	public void setFile_path(String file_path) {
		this.file_path = file_path;
	}

	public String getFile_ext() {
		return file_ext == null ? "" : file_ext;
	}

	public void setFile_ext(String file_ext) {
		this.file_ext = file_ext;
	}

	public String getFile_ext_img() {
		return file_ext_img;
	}

	public void setFile_ext_img(String file_ext_img) {
		this.file_ext_img = file_ext_img;
	}

	public String getFile_url() {
		return file_url == null ? "" : file_url;
	}

	public void setFile_url(String file_url) {
		this.file_url = file_url;
	}

	public String getFile_size() {
		return file_size == null ? "" : file_size;
	}

	public void setFile_size(String file_size) {
		this.file_size = file_size;
	}

	public String getFile_size_byte() {
		return file_size_byte == null ? "" : file_size_byte;
	}

	public void setFile_size_byte(String file_size_byte) {
		this.file_size_byte = file_size_byte;
	}

	public String getFile_width() {
		return file_width == null ? "" : file_width;
	}

	public void setFile_width(String file_width) {
		this.file_width = file_width;
	}

	public String getFile_height() {
		return file_height == null ? "" : file_height;
	}

	public void setFile_height(String file_height) {
		this.file_height = file_height;
	}

	public String getFile_main_yn() {
		return file_main_yn == null ? "" : file_main_yn;
	}

	public void setFile_main_yn(String file_main_yn) {
		this.file_main_yn = file_main_yn;
	}

	public byte[] getFile_image() {
		return file_image;
	}

	public void setFile_image(byte[] file_image) {
		this.file_image = file_image;
	}

	public String getFile_type() {
		return file_type == null ? "" : file_type;
	}

	public void setFile_type(String file_type) {
		this.file_type = file_type;
	}

	public String getEntprs_cd() {
		return entprs_cd == null ? "" : entprs_cd;
	}

	public void setEntprs_cd(String entprs_cd) {
		this.entprs_cd = entprs_cd;
	}

	public String getPgm_name() {
		return pgm_name == null ? "" : pgm_name;
	}

	public void setPgm_name(String pgm_name) {
		this.pgm_name = pgm_name;
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

	public String[] getFile_seq_array() {
		return file_seq_array;
	}

	public void setFile_seq_array(String[] file_seq_array) {
		this.file_seq_array = file_seq_array;
	}

	public boolean isSuccess_flag() {
		return success_flag;
	}

	public void setSuccess_flag(boolean success_flag) {
		this.success_flag = success_flag;
	}

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	
}
