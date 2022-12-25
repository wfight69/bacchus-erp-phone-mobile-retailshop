package com.bacchuserpshop.formact.login;

import com.bacchuserpshop.R;
import com.bacchuserpshop.common.util.DialogUtils;

import com.bacchuserpshop.common.util.ConfigUtils;


import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

public class PopConfigActivity extends Activity {

	private static final String LOG_TAG = "PopConfigActivity";
	
	private SharedPreferences pref = null;
	
	private EditText   config_edtxt_conn_server   		= null;			// 접속서버 설정
	private EditText   config_edtxt_conn_port   		= null;			// 접속포트설정
	private CheckBox   config_chkbox_nfc_voice_use_yn = null;			// NFC음성 설정
	
	private String 	   config_conn_server				= null;		// 접속서버
	private String 	   config_conn_port				= null;		// GPS거리
	private String 	   config_as_call_time 				= null;		// AS콜리프레쉬 타임
	private String 	   config_nfc_voice_use_yn			= "N";		// NFC음성 사용여부(Y/N)
	
	private Button config_btnClose;
	private Button config_btnSave;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// 윈도우 설정
		WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();    
		lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
		lpWindow.dimAmount = 0.8f;
		getWindow().setAttributes(lpWindow);
		
		setContentView(R.layout.pop_configsetup);
		
		setLayout();
		
		// 환경설정 가져오기
		configGetandSave(1);
	}
	
	/*
	 * Layout
	 */
	private void setLayout(){
		config_edtxt_conn_server 		= (EditText)findViewById(R.id.config_edtxt_conn_server);
		config_edtxt_conn_port 		= (EditText)findViewById(R.id.config_edtxt_conn_port);
		config_chkbox_nfc_voice_use_yn	= (CheckBox)findViewById(R.id.config_chkbox_nfc_voice_use_yn);
        
		config_btnClose = (Button) findViewById(R.id.config_btnClose);
		config_btnSave = (Button) findViewById(R.id.config_btnSave);
		
 		config_btnSave.setOnClickListener(btnSave_listener);
		config_btnClose.setOnClickListener(btnClose_listener);
	}

	// 화면폼 체크..
	public boolean isFormValidate() {

		String  formMsg = "";

		Log.i(LOG_TAG, "== 환경저장  isFormValidate() start..");

		config_conn_server	= config_edtxt_conn_server.getText().toString();
		config_conn_port	= config_edtxt_conn_port.getText().toString();

		Log.i(LOG_TAG, "== config_conn_port	=> " + config_conn_port);
		Log.i(LOG_TAG, "== config_as_call_time 	=> " + config_as_call_time);

		if (!formMsg.equals("")) {
			DialogUtils.messageDialog(PopConfigActivity.this,  formMsg);
			return false;
		}

		return true;
	}
	
	@Override  
	protected void onApplyThemeResource(Resources.Theme theme, int resid, boolean first)  
	{  
	    super.onApplyThemeResource(theme, resid, first);  
	          
	    // no background panel is shown  
	    theme.applyStyle(android.R.style.Theme_Panel, true);  
	}  
	

    // 환경설정 저장
	private View.OnClickListener btnSave_listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

			// 화면폼 체크..
			if(!isFormValidate()) {
				return;
			}

	    	AlertDialog.Builder builder = new AlertDialog.Builder(PopConfigActivity.this);
			builder.setMessage("환경설정을 저장하시겠습니까?")
			       .setCancelable(false)
			       .setPositiveButton("예", new DialogInterface.OnClickListener() {
			           public void onClick(DialogInterface dialog, int id) {

			           	configGetandSave(2);
			           	finish();

			           }
			       })
			       .setNegativeButton("아니오", new DialogInterface.OnClickListener() {
			           public void onClick(DialogInterface dialog, int id) {
					         
			             dialog.cancel();

			           }
			       });
			AlertDialog alert = builder.create();
			alert.show();

        }
    }; 
    
    // 화면 종료
	private View.OnClickListener btnClose_listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
        	 finish();
        }
    }; 
    

    // 환경설정내용을 가져오기및 저장한다(cfDiv 1:가져오기, 2:저장하기).
	private void configGetandSave(int cfDiv)
	{
		Log.i(LOG_TAG, "== configGetandSave() start.. cfDiv => " + cfDiv);
		
		pref = getSharedPreferences("bacchus_erp", Activity.MODE_PRIVATE);
		
        switch(cfDiv) {
		case (1) : 
			// 환경설정 가져오기.
			//config_conn_server 		= CCoUtil.chkNull(pref.getString("config_conn_server", ""));
			config_conn_server  = ConfigUtils.getConnServer(PopConfigActivity.this);
			config_conn_port = ConfigUtils.getConnPort(PopConfigActivity.this);

			config_edtxt_conn_server.setText(config_conn_server);
			config_edtxt_conn_port.setText(config_conn_port);

			// NFC음성 사용여부(Y/N)			
			config_nfc_voice_use_yn = ConfigUtils.getNfcVoiceUseYn(PopConfigActivity.this);
			if (config_nfc_voice_use_yn.equals("Y")) {
				config_chkbox_nfc_voice_use_yn.setChecked(true);
			} 
		
			break;
		case (2) : 
       		SharedPreferences.Editor editor = pref.edit();
		
			config_conn_server	= config_edtxt_conn_server.getText().toString();
			config_conn_port	= config_edtxt_conn_port.getText().toString();

			editor.putString("config_conn_server", 	config_conn_server);
			editor.putString("config_conn_port", 	config_conn_port);

			// NFC음성 사용여부(Y/N) 
			if (config_chkbox_nfc_voice_use_yn.isChecked()) {
				config_nfc_voice_use_yn = "Y";			
			} else {
				config_nfc_voice_use_yn = "N";
			}
			editor.putString("config_nfc_voice_use_yn", 	config_nfc_voice_use_yn);

			// 혼경설정 저장
      		editor.commit();
		}
	}	
	
}


