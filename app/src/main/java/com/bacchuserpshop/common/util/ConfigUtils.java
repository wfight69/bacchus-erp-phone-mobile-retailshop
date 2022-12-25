/*****************************************************************************
* 파일명 : ConfigUtils.java
* 패키지 : com.epc.common.util
* 생성일 : 2010. 07. 07.
* 작성자 : 
* ===========================================================================
* 변경이력:
* DATE             AUTHOR      DESCRIPTION
* ---------------------------------------------------------------------------
* 변경 이력은 이곳에 추가 합니다.
*****************************************************************************/
package com.bacchuserpshop.common.util;

import com.bacchuserpshop.R;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.util.Log;

public class ConfigUtils {

	private static final String LOG_TAG = "ConfigUtils";


	static SharedPreferences pref = null;
	
    private ConfigUtils() {
    	//pref = context.getSharedPreferences("bacchus_erp", Activity.MODE_PRIVATE);
    }

    //////// Config Setup 
	// 내 휴대폰 번호 : 프로요(2.x)와 키켓(4.x)이 전번 가저오는게 다름.
	public static String getMyCellPhoneNo(Context context)
	{
		String my_cell_phone_no = "";
		try {
			// 내폰번호 가져오기 : 현재는 프로요(2.x기준)
			TelephonyManager telManager = (TelephonyManager) context.getSystemService(context.TELEPHONY_SERVICE);
			/*
			if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_NUMBERS) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
				my_cell_phone_no = telManager.getDeviceId(1);
				//return my_cell_phone_no;
			}
			*/
			my_cell_phone_no 	= telManager.getLine1Number();		// 영엄답당자 번호

System.out.println("== ConfigUtils getMyCellPhoneNo() => " + my_cell_phone_no);

			if (!my_cell_phone_no.equals("") && my_cell_phone_no != null) {
				// +82 : KT or 모토로이 폰 경우처리 함
			    if (my_cell_phone_no.substring(0,3).equals("+82")) {
			    	my_cell_phone_no  = "0" + my_cell_phone_no.substring(3,13);
			    }
			    
			    // 폰번호 조합처리..
			    my_cell_phone_no = my_cell_phone_no.substring(0,3) + "-" + my_cell_phone_no.substring(3,7) + "-" + my_cell_phone_no.substring(7,11);
			}
		} catch (Exception e) {
			e.printStackTrace();

			// 폰번호 직접저장내역 가져옴.
			my_cell_phone_no = getCell_Tel_No(context);
			if (my_cell_phone_no.equals("")) {
				my_cell_phone_no = "폰번호입력";
			}
		}  finally {
			Log.i(LOG_TAG, " == ConfigUtils getMyCellPhoneNo() my_cell_phone_no => " + my_cell_phone_no);
		}
		return my_cell_phone_no;
	}

	// 주류업체고유ID(wholesalerUuid)
	public static String getWholesalerUuid(Context context)
	{
		pref = context.getSharedPreferences("bacchus_erp", Activity.MODE_PRIVATE);
		return CCoUtil.chkNull(pref.getString("config_wholesalerUuid", ""));
	}

	// 주류업체코드
	public static String getEntprs_cd(Context context)
	{
		pref = context.getSharedPreferences("bacchus_erp", Activity.MODE_PRIVATE);
		return CCoUtil.chkNull(pref.getString("config_entprs_cd", ""));
	}
	
	// 사업자명
	public static String getEntprs_name(Context context)
	{
		pref = context.getSharedPreferences("bacchus_erp", Activity.MODE_PRIVATE);
		return CCoUtil.chkNull(pref.getString("config_entprs_name", ""));
	}
	
	
	// 사업자버호
	public static String getBusi_r_no(Context context)
	{
		pref = context.getSharedPreferences("bacchus_erp", Activity.MODE_PRIVATE);
		return CCoUtil.chkNull(pref.getString("config_busi_r_no", ""));
	}

	// 사업자 패스워드
	public static String getBusi_r_pswd(Context context)
	{
		pref = context.getSharedPreferences("bacchus_erp", Activity.MODE_PRIVATE);
		return CCoUtil.chkNull(pref.getString("config_busi_r_pswd", ""));
	}
	
	// 사업자명
	public static String getBusi_r_name(Context context)
	{
		pref = context.getSharedPreferences("bacchus_erp", Activity.MODE_PRIVATE);
		return CCoUtil.chkNull(pref.getString("config_busi_r_name", ""));
	}

	// 회사 대표번호
	public static String getCompn_Tel_No(Context context)
	{
		pref = context.getSharedPreferences("bacchus_erp", Activity.MODE_PRIVATE);
		return CCoUtil.chkNull(pref.getString("config_compn_tel_no", ""));
	}

	// 사용자고유ID(employeeUuid)
	public static String getEmployeeUuid(Context context)
	{
		pref = context.getSharedPreferences("bacchus_erp", Activity.MODE_PRIVATE);
		return CCoUtil.chkNull(pref.getString("config_employeeUuid", ""));
	}

	// 사용자id
	public static String getUser_id(Context context)
	{
		try {
			pref = context.getSharedPreferences("bacchus_erp", Activity.MODE_PRIVATE);
			return CCoUtil.chkNull(pref.getString("config_user_id", ""));
			
		} catch (Exception e) {
			return "";
		}
	}

	// 사용자 휴대폰 번호
	public static String getCell_Tel_No(Context context)
	{
		try {
			pref = context.getSharedPreferences("bacchus_erp", Activity.MODE_PRIVATE);
			return CCoUtil.chkNull(pref.getString("config_cell_tel_no", ""));
			
		} catch (Exception e) {
			return "";
		}
	}

	// 사용자 패스워드
	public static String getCellPasswd(Context context)
	{
		try {
			pref = context.getSharedPreferences("bacchus_erp", Activity.MODE_PRIVATE);
			return CCoUtil.chkNull(pref.getString("config_passwd", ""));
			
		} catch (Exception e) {
			return "";
		}
	}
	
	// 사용자명
	public static String getUser_name(Context context)
	{
		pref = context.getSharedPreferences("bacchus_erp", Activity.MODE_PRIVATE);
		return CCoUtil.chkNull(pref.getString("config_user_name", ""));
	}

	// 코스-담당자명
	public static String getChrg_name(Context context)
	{
		pref = context.getSharedPreferences("bacchus_erp", Activity.MODE_PRIVATE);
		return CCoUtil.chkNull(pref.getString("config_chrg_name", ""));
	}
	
	// OBS 메니져여부
	public static String getAdmin_yn(Context context)
	{
		pref = context.getSharedPreferences("bacchus_erp", Activity.MODE_PRIVATE);
		return CCoUtil.chkNull(pref.getString("config_admin_yn", ""));
	}

	// 승인완료후 처리할 추가승인자 폰번호
	public static String getSmsAsApprCellNo(Context context)
	{
		pref = context.getSharedPreferences("bacchus_erp", Activity.MODE_PRIVATE);
		return CCoUtil.chkNull(pref.getString("config_sms_as_appr_cell_no", ""));
	}
	
	// 시스템 메니져여부
	public static String getSystem_Admin_yn(Context context)
	{
		pref = context.getSharedPreferences("bacchus_erp", Activity.MODE_PRIVATE);
		return CCoUtil.chkNull(pref.getString("config_system_admin_yn", ""));
	}
	
	// sms수신 메시지 및  음성화일 삭제 주기
	public static String getDel_Schedule(Context context)
	{
		pref = context.getSharedPreferences("bacchus_erp", Activity.MODE_PRIVATE);
		return CCoUtil.chkNull(pref.getString("config_edtxt_delschdule", ""));
	}
	
	// 수신시 링파일명
	public static String getSmsPlayFile(Context context)
	{
		pref = context.getSharedPreferences("bacchus_erp", Activity.MODE_PRIVATE);
		return CCoUtil.chkNull(pref.getString("config_edtxt_ringfile", ""));
	}

	// 다바다 환경설정 플레이 파일
	public static String getSmsDavadaErrPlayFile(Context context)
	{
		pref = context.getSharedPreferences("bacchus_erp", Activity.MODE_PRIVATE);
		return CCoUtil.chkNull(pref.getString("config_davada_err_ringfile", ""));
	}
	
	// 수신시 링파일명
	public static String getRingFile(Context context)
	{
		try {
			pref = context.getSharedPreferences("bacchus_erp", Activity.MODE_PRIVATE);
			return CCoUtil.chkNull(pref.getString("config_edtxt_ringfile", ""));
			
		} catch (Exception e) {
			return "";
		}
	}
	
	
	// Autoupdate Date
	public static String getAutoUpdateDate(Context context)
	{
		pref = context.getSharedPreferences("bacchus_erp", Activity.MODE_PRIVATE);
		String auto_update_date = CCoUtil.chkNull(pref.getString("config_auto_update_date", ""));

		return auto_update_date;
	}
	
	// 업체 현소속부서 코드
	public static String getDeptCd(Context context)
	{
		try {
			pref = context.getSharedPreferences("bacchus_erp", Activity.MODE_PRIVATE);
			return CCoUtil.chkNull(pref.getString("config_dept_cd", ""));
			
		} catch (Exception e) {
			return "0000";
		}
	} 	
	
	// 업체소속구분(1:ㅈ류업체, 2:수리AS업체, 3:제조업체, 4:기타	
	public static String getEntprsBingDiv(Context context)
	{
		try {
			pref = context.getSharedPreferences("bacchus_erp", Activity.MODE_PRIVATE);
			return CCoUtil.chkNull(pref.getString("config_entprs_bing_div", ""));
			
		} catch (Exception e) {
			return "1";
		}
	}

	// AS서비스금액
	public static String getAsSvcAmt(Context context)
	{
		try {
			pref = context.getSharedPreferences("bacchus_erp", Activity.MODE_PRIVATE);
			return CCoUtil.chkNull(pref.getString("config_as_svc_amt", "0"));

		} catch (Exception e) {
			return "0";
		}
	}

	// 자산조사 사진추가 최대건수	
	public static String getEqupRschPtoMax(Context context)
	{
		try {
			pref = context.getSharedPreferences("bacchus_erp", Activity.MODE_PRIVATE);
			return CCoUtil.chkNull(pref.getString("config_equp_rsch_pto_max", ""));
			
		} catch (Exception e) {
			return "1";
		}
	} 		
	
	// 자산as 사진추가 최대 건수
	public static String getEqupAsPtoMax(Context context)
	{
		try {
			pref = context.getSharedPreferences("bacchus_erp", Activity.MODE_PRIVATE);
			return CCoUtil.chkNull(pref.getString("config_equp_as_pto_max", ""));
			
		} catch (Exception e) {
			return "1";
		}
	} 		
	
	// 자산조사 사진추가 여부(y/n)
	public static String getEqupRschPtoYN(Context context)
	{
		try {
			pref = context.getSharedPreferences("bacchus_erp", Activity.MODE_PRIVATE);
			return CCoUtil.chkNull(pref.getString("config_equp_rsch_pto_yn", ""));
			
		} catch (Exception e) {
			return "Y";
		}
	} 		
	
	// 자산as 사진추가 여부(y/n)
	public static String getEqupAsPtoYN(Context context)
	{
		try {
			pref = context.getSharedPreferences("bacchus_erp", Activity.MODE_PRIVATE);
			return CCoUtil.chkNull(pref.getString("config_equp_as_pto_yn", ""));
			
		} catch (Exception e) {
			return "Y";
		}
	} 
	
	// 클라이언트 접속관련 SessionId
	public static String getSessionId(Context context)
	{
		try {
			pref = context.getSharedPreferences("bacchus_erp", Activity.MODE_PRIVATE);
			return CCoUtil.chkNull(pref.getString("config_session_id", ""));
			
		} catch (Exception e) {
			return "";
		}
	}	

	// 메뉴권한(Y/N) 가져오기
	public static String getMenuGrantYN(Context context, Integer menu_no)
	{
		try {
			// editor.putString("config_menu1_grant_yn",	loginVo.getMenu1_grant_yn());	// 자산조사
			
			String menu_grant_yn = "N";
			String pref_menu_no  = "config_menu" + menu_no + "_grant_yn";
			pref = context.getSharedPreferences("bacchus_erp", Activity.MODE_PRIVATE);
			menu_grant_yn = CCoUtil.chkNull(pref.getString(pref_menu_no, "N"));
	
			//System.out.println("=== pref_menu_no ==> " + pref_menu_no + ", menu_grant_yn => " + menu_grant_yn);
			
			return menu_grant_yn;

		} catch (Exception e) {
			return "N";
		}
	}
	
	
	// 메시지 전송방식(카톡 or SMS) 가져오기
	// 모바일앱 카톡 내부사용자 전송 (1:SMS,2:KTalk,3:미전송)
	public static String getAppKtalkInnerSendDiv(Context context) 
	{
		try {
			pref = context.getSharedPreferences("bacchus_erp", Activity.MODE_PRIVATE);
			return CCoUtil.chkNull(pref.getString("config_app_ktalk_inner_send_div", ""));
			
		} catch (Exception e) {
			return "1";
		}
	} 

	// 모바일앱 카톡 외부사용자1 전송여부(매입처)
	public static String getAppKtalkOut1SendDiv(Context context) 
	{
		try {
			pref = context.getSharedPreferences("bacchus_erp", Activity.MODE_PRIVATE);
			return CCoUtil.chkNull(pref.getString("config_app_ktalk_out1_send_div", ""));
			
		} catch (Exception e) {
			return "1";
		}
	} 
	
	// 모바일앱 카톡 외부사용자2 전송여부(매출처)
	public static String getAppKtalkOut2SendDiv(Context context) 
	{
		try {
			pref = context.getSharedPreferences("bacchus_erp", Activity.MODE_PRIVATE);
			return CCoUtil.chkNull(pref.getString("config_app_ktalk_out2_send_div", ""));
			
		} catch (Exception e) {
			return "1";
		}
	}
	
	// 모바일앱 카톡 외부사용자3 전송여부(납품처)
	public static String getAppKtalkOut3SendDiv(Context context) 
	{
		try {
			pref = context.getSharedPreferences("bacchus_erp", Activity.MODE_PRIVATE);
			return CCoUtil.chkNull(pref.getString("config_app_ktalk_out3_send_div", ""));
			
		} catch (Exception e) {
			return "1";
		}
	}
	
	// pc처리 카톡 전송여부 (1:SMS,2:KTalk,3:미전송)
	public static String getPcKtalkSendDiv(Context context) 
	{
		try {
			pref = context.getSharedPreferences("bacchus_erp", Activity.MODE_PRIVATE);
			return CCoUtil.chkNull(pref.getString("config_pc_ktalk_send_div", ""));
			
		} catch (Exception e) {
			return "1";
		}
	}
	
	// AS수신시 AS처리후 승인요청없이 바로 승인완료 처리여부 
	/*
    private String  appr_as1_fin_yn;				// AS출고 AS수신처리후 승인요청(N)/승인완료(Y)  처리여부 체크
    private String  appr_as2_fin_yn;				// AS수리 AS수신처리후 승인요청(N)/승인완료(Y)  처리여부 체크
    private String  appr_as3_fin_yn;				// AS회수 AS수신처리후 승인요청(N)/승인완료(Y)  처리여부 체크
    private String  appr_as9_fin_yn;				// AS기타 AS수신처리후 승인요청(N)/승인완료(Y)  처리여부 체크 
	 */
	public static String getApprAsFinYN(Context context, Integer as_no)
	{
		try {			
			String pref_appr_as_no  = "config_appr_as" + as_no + "_fin_yn";
			pref = context.getSharedPreferences("bacchus_erp", Activity.MODE_PRIVATE);
			String appr_as_fin_yn = CCoUtil.chkNull(pref.getString(pref_appr_as_no, "N"));
	
			//System.out.println("=== pref_appr_as_no ==> " + pref_appr_as_no + ", appr_as_fin_yn => " + appr_as_fin_yn);
			
			return appr_as_fin_yn;

		} catch (Exception e) {
			return "N";
		}
	}
	
	// 사진찍기 카메라 타입(A:다바다앱카메라, P:시스템폰카메라)
	public static String getTakePicCameraType(Context context) 
	{
		try {
			pref = context.getSharedPreferences("bacchus_erp", Activity.MODE_PRIVATE);
			return CCoUtil.chkNull(pref.getString("config_take_pic_camera_type", ""));
			
		} catch (Exception e) {
			return "A";
		}
	}
	
	// 앱스토어 버전업데이트시 팝업 오늘하루 닫기처리
	public static String getPopTodayCoseDate(Context context) 
	{
		try {
			pref = context.getSharedPreferences("bacchus_erp", Activity.MODE_PRIVATE);
			return CCoUtil.chkNull(pref.getString("config_pop_today_close_date", ""));
			
		} catch (Exception e) {
			return DateUtils.getToday("yyyy-MM-dd");
		}
	}
	
	
	//////// String.xml define
	// 스토리지 위치 : 일반적으로 /mnt/sdcard가 일반적, 넥서스나 G2등은 /external/storage/0 식임
	public static String getMountStoragePath() {
		
		// 스토리지 위치 : 일반적으로 /mnt/sdcard/가 일반적, 넥서스나 G2등은 /external/storage/0 식임
		File storagePath = Environment.getExternalStorageDirectory();
		
		return storagePath.getPath();		
	}
	
	public static String getFileDownPath(Context context) {
		
		// 마운트 스토리지 + 앱디렉토리 위치
    	String file_down_path	= getMountStoragePath() + context.getResources().getString(R.string.cfg_file_down_path);  	// default is /mnt/sdcard/bacchus_erp/voice/
    	
    	return file_down_path;
	}

	public static String getFileAlarmPath(Context context) {
		
    	String file_alarm_path	= getMountStoragePath() + context.getResources().getString(R.string.cfg_file_alarm_path);  	// default is /mnt/sdcard/bacchus_erp/alarm/
    	
    	return file_alarm_path;
	}

	// 자산이미지호일 저장 패스
	public static String getFileImagePath(Context context) {
		
    	String file_image_path	= getMountStoragePath() + context.getResources().getString(R.string.cfg_file_image_path);  	// default is /mnt/sdcard/bacchus_erp/image/
    	
    	return file_image_path;
	}

	public static String getFileLogPath(Context context) {

		String file_log_path	= getMountStoragePath() + context.getResources().getString(R.string.cfg_file_log_path);  	// default is /mnt/sdcard/bacchus_erp_sms/log/

		return file_log_path;
	}

	public static String getShinHanBankPath(Context context) {

		String file_bankkey_path	= getMountStoragePath() + context.getResources().getString(R.string.cfg_file_shinbank_path);  	// default is /mnt/sdcard/bacchus_erp_sms/log/

		return file_bankkey_path;
	}

	// 다바다 sms음성 내용
	public static String getFileAlarmName(Context context) {
		
    	String file_alarm_name	= context.getResources().getString(R.string.cfg_davada_sms_ring_file);  	// default is davada_sms_ring.mp3
    	
    	return file_alarm_name;
	}	
	
	// 다바다 에러음성 내용
	public static String getFileAlarmErrName(Context context) {
		
    	String file_alarm_name	= context.getResources().getString(R.string.cfg_davada_err_ring_file);  	// default is davada_sms_ring.mp3
    	
    	return file_alarm_name;
	}		

	/////////////// MMS Message LastId get and Set ////////////////////
	public static String getLastMmsId(Context context)
	{
		try {
			pref = context.getSharedPreferences("bacchus_erp", Activity.MODE_PRIVATE);
			String last_mmsid = CCoUtil.chkNull(pref.getString("config_last_mmsid", "0"));
			return last_mmsid;
		} catch (Exception e) {
			return "0";
		}
	}

	public static void setLastMmsId(Context context, String last_mmsid)
	{
		pref = context.getSharedPreferences("bacchus_erp", Activity.MODE_PRIVATE);
 		SharedPreferences.Editor editor = pref.edit();
		editor.putString("config_last_mmsid",	last_mmsid);	 // MMS 마지막 ID저장	
		editor.commit();
	}
	
	// asset화일 지정화일로 보사처ㅣ
	// sourceFile : davada_sms_ring.mp3
	// targetFile : /bacchus_erp/alarm/davada_sms_ring.mp3
	public static boolean CopyAssetFromToTargetFile(Context conext, String sourceFile, String targetFile)  {

		InputStream fin = null;
		FileOutputStream fos = null;
		try { 
			AssetManager am = conext.getAssets();
			fin = am.open(sourceFile);				// file://assets 디렉토리 화일 읽음..
			
			fos = new FileOutputStream(targetFile);
            int bytesRead = 0;
            byte[] buffer = new byte[1024];
            while ((bytesRead = fin.read(buffer, 0, 1024)) != -1) {
            	fos.write(buffer, 0, bytesRead);
            } 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} finally {
			try {
				if (fin != null) fin.close();
				if (fos != null) fos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}	
		return true;
	}

	
	/////////////////// My Primary Phone No Config ///////////////////////////
	// System 내폰번호 가져오기
	public static String getMySysPhoneNo(Context context) {
		// 내폰번호 가져오기
		TelephonyManager telManager = (TelephonyManager)context.getSystemService(context.TELEPHONY_SERVICE); 
		String mySystemPhoneNo = telManager.getLine1Number();		// 영엄답당자 번호
		
		return mySystemPhoneNo;
	}
	
	/////////////////// Netrowk Server or Url Config ///////////////////////////
	// 접속서버 
	public static String getConnServer(Context context)
	{
		pref = context.getSharedPreferences("bacchus_erp", Activity.MODE_PRIVATE);
		String server = CCoUtil.chkNull(pref.getString("config_conn_server", ""));
		if (server.equals("") || server == null) {
			server = "erp.bacchuserp.com";
		}
		return server;
	}

	// 접속포트
	public static String getConnPort(Context context)
	{
		pref = context.getSharedPreferences("bacchus_erp", Activity.MODE_PRIVATE);
		String conn_port = CCoUtil.chkNull(pref.getString("config_conn_port", ""));
		if (conn_port.equals("") || conn_port == null) {
			conn_port = "8080";
		}
		return conn_port;
	}

	// GPS거리
	public static String getGpsDistance(Context context)
	{
		pref = context.getSharedPreferences("bacchus_erp", Activity.MODE_PRIVATE);
		String gps_distance = CCoUtil.chkNull(pref.getString("config_gps_distance", ""));
		if (gps_distance.equals("") || gps_distance == null) {
			gps_distance = "5";
		}
		return gps_distance;
	}

	// AS콜리프레쉬 타임
	public static String getAsCallTime(Context context)
	{
		pref = context.getSharedPreferences("bacchus_erp", Activity.MODE_PRIVATE);
		String as_call_time = CCoUtil.chkNull(pref.getString("config_as_call_time", ""));
		if (as_call_time.equals("") || as_call_time == null) {
			as_call_time = "5";
		}
		return as_call_time;
	}

	// NFC음성 사용여부(Y/N)
	public static String getNfcVoiceUseYn(Context context) 
	{
		try {
			pref = context.getSharedPreferences("bacchus_erp", Activity.MODE_PRIVATE);
			return CCoUtil.chkNull(pref.getString("config_nfc_voice_use_yn", "N"));
			
		} catch (Exception e) {
			return "N";
		}
	}

	// 카카오톡 연동 접속토큰
	public static String getKakaoAccessToken(Context context)
	{
		pref = context.getSharedPreferences("bacchus_erp", Activity.MODE_PRIVATE);
		String kakao_access_token = CCoUtil.chkNull(pref.getString("config_kakao_access_token", ""));

		return kakao_access_token;
	}

	// GPS현재위치-위도
	public static String getLatitude(Context context)
	{
		pref = context.getSharedPreferences("bacchus_erp", Activity.MODE_PRIVATE);
		return CCoUtil.chkNull(pref.getString("config_latitude", "0"));
	}

	// GPS현재위치-경도
	public static String getLongitude(Context context)
	{
		pref = context.getSharedPreferences("bacchus_erp", Activity.MODE_PRIVATE);
		return CCoUtil.chkNull(pref.getString("config_longitude", "0"));
	}

	// 워크매니져 존재여부(Y/N)
	public static String getWkmIsExist(Context context)
	{
		pref = context.getSharedPreferences("bacchus_erp", Activity.MODE_PRIVATE);
		return CCoUtil.chkNull(pref.getString("config_wkm_isexist", "N"));
	}

	// 담당자별 위치전송 여부(Y/N)
	public static String getGpsSendYN(Context context)
	{
		pref = context.getSharedPreferences("bacchus_erp", Activity.MODE_PRIVATE);
		return CCoUtil.chkNull(pref.getString("config_gps_send_yn", "N"));
	}

	// SMS전송파일 로그파일 저장여부 add 2016.03.21
	//
	public static String getLogFileWriteYN(Context context)
	{
		pref = context.getSharedPreferences("bacchus_erp", Activity.MODE_PRIVATE);
		String logfile_write_yn = CCoUtil.chkNull(pref.getString("config_logfile_write_yn", ""));

		return logfile_write_yn;
	}

	// FCM토큰
	public static String getFcmToken(Context context)
	{
		pref = context.getSharedPreferences("bacchus_erp", Activity.MODE_PRIVATE);
		return CCoUtil.chkNull(pref.getString("config_fcm_token", ""));
	}

	// Badge Count
	public static String getBadgeCount(Context context)
	{
		pref = context.getSharedPreferences("bacchus_erp", Activity.MODE_PRIVATE);
		return CCoUtil.chkNull(pref.getString("config_badge_count", "0"));
	}

	// 메시지 필터
	public static String getMsg_Filter(Context context)
	{
		pref = context.getSharedPreferences("bacchus_erp", Activity.MODE_PRIVATE);
		return CCoUtil.chkNull(pref.getString("config_edtxt_filter", ""));
	}

	// 메시지  예외 필터
	public static String getMsg_ExceptFilter(Context context)
	{
		pref = context.getSharedPreferences("bacchus_erp", Activity.MODE_PRIVATE);
		return CCoUtil.chkNull(pref.getString("config_except_filter", ""));
	}

	// 메시지 필터 저장
	public static void saveMsg_Filter(Context context, String filterNormlMsg)
	{
		pref = context.getSharedPreferences("bacchus_erp", Activity.MODE_PRIVATE);
		SharedPreferences.Editor editor = pref.edit();
		editor.putString("config_edtxt_filter", filterNormlMsg);
		editor.commit();
	}

	// 메시지  예외필터 저장
	public static void saveMsg_ExceptFilter(Context context, String filterExcptMsg)
	{
		pref = context.getSharedPreferences("bacchus_erp", Activity.MODE_PRIVATE);
		SharedPreferences.Editor editor = pref.edit();
		editor.putString("config_except_filter", filterExcptMsg);
		editor.commit();
	}

	// JWT인증토큰
	public static String getJwtToken(Context context)
	{
		pref = context.getSharedPreferences("bacchus_erp", Activity.MODE_PRIVATE);
		return CCoUtil.chkNull(pref.getString("config_jwt_token", ""));
	}

	public static void saveLoginCfg(Context context,
									String wholesalerUuid,
									String employeeUuid,
									String entprsCd,
									String filterNormlMsg, String filterExcptMsg,
									String filterUseYn,
									String jwtToken)
	{
		pref = context.getSharedPreferences("bacchus_erp", Activity.MODE_PRIVATE);
		SharedPreferences.Editor editor = pref.edit();
		editor.putString("config_wholesalerUuid", wholesalerUuid);
		editor.putString("config_employeeUuid", employeeUuid);
		editor.putString("config_entprs_cd", entprsCd);
		editor.putString("config_edtxt_filter", filterNormlMsg);
		editor.putString("config_except_filter", filterExcptMsg);
		editor.putString("config_filter_use_yn", filterUseYn);
		editor.putString("config_jwt_token", jwtToken);
		editor.commit();
	}

	// SMS수신시 서버 전송 여부
	public static String getMobileSmsSendYN(Context context)
	{
		pref = context.getSharedPreferences("bacchus_erp", Activity.MODE_PRIVATE);
		String mobile_sms_send_yn = CCoUtil.chkNull(pref.getString("config_sms_send_yn", ""));
		if (mobile_sms_send_yn.equals("")) {
			return "Y";
		}
		return mobile_sms_send_yn;
	}

	// 모바일사용(서비스) 여부
	public static String getMobileUseDiv(Context context)
	{
		pref = context.getSharedPreferences("bacchus_erp", Activity.MODE_PRIVATE);
		String mobile_use_div = CCoUtil.chkNull(pref.getString("config_mobile_use_div", ""));
		if (mobile_use_div.equals("")) {
			return "A";
		}
		return mobile_use_div;
	}

	// 사용자별 필터 적용여부(Y/N) 여부
	public static boolean getFilterUseYN(Context context)
	{
		pref = context.getSharedPreferences("bacchus_erp", Activity.MODE_PRIVATE);
		String filter_use_yn = CCoUtil.chkNull(pref.getString("config_filter_use_yn", ""));
		if (filter_use_yn.equals("") || filter_use_yn.equals("N")) {
			return false;
		}
		return true;
	}
}
