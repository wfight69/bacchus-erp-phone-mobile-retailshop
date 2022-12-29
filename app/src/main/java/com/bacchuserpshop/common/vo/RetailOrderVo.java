/*****************************************************************************
* 파일명 : EntprsOrdrVo.java
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


public class RetailOrderVo {
	
	private String wholesalerUuid;
	private String employeeUuid;
	private String retailOrderTelephone;		// 주문전화번호
	private String retailOrderChannel;			// 주문채널(SMS, KAKAO, CODE, VOICE)
	private String orderDescription;			// 주문내용
	private String fcmToken;

	public String getWholesalerUuid() {
		return wholesalerUuid;
	}

	public void setWholesalerUuid(String wholesalerUuid) {
		this.wholesalerUuid = wholesalerUuid;
	}

	public String getEmployeeUuid() {
		return employeeUuid;
	}

	public void setEmployeeUuid(String employeeUuid) {
		this.employeeUuid = employeeUuid;
	}

	public String getRetailOrderTelephone() {
		return retailOrderTelephone;
	}

	public void setRetailOrderTelephone(String retailOrderTelephone) {
		this.retailOrderTelephone = retailOrderTelephone;
	}

	public String getRetailOrderChannel() {
		return retailOrderChannel;
	}

	public void setRetailOrderChannel(String retailOrderChannel) {
		this.retailOrderChannel = retailOrderChannel;
	}

	public String getOrderDescription() {
		return orderDescription;
	}

	public void setOrderDescription(String orderDescription) {
		this.orderDescription = orderDescription;
	}

	public String getFcmToken() {
		return fcmToken;
	}

	public void setFcmToken(String fcmToken) {
		this.fcmToken = fcmToken;
	}

}