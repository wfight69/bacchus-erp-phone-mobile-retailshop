/*****************************************************************************
* 파일명 : DateUtils.java
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

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;


public class DialogUtils implements DialogUtilsInterface.DialogReturn {

	static DialogUtilsInterface myInterface;
	DialogUtilsInterface.DialogReturn dialogReturn;
    
	private String message = null;
	private Context contextx = null;

    private DialogUtils(Context ctx) {
    	this.contextx = ctx;
    }


	/**
	 * 날짜를 Long타입으로 리턴한다.
	 *
	 * @param int year
	 * @param int month
	 * @param int date
	 * @return long
	 */
	public static void getMillis(int year,int month,int day) {

		//return (new DateTime(year, month, day, 0, 0, 0, 0)).getMillis();
	}

	/**
	 * 날짜를 Long 타입으로 리턴한다.
	 *
	 * @param String dateStr
	 * @param int format
	 * @return String
	 */
	public static void messageDialog(Context context, String message) {
		AlertDialog.Builder ab = null;
		ab = new AlertDialog.Builder( context );
		ab.setMessage(message);
		ab.setPositiveButton(android.R.string.ok, null);
		ab.setTitle( "다바다" );
		ab.show(); 
	}

	public static void messageDialog(Context context, String title, String message) {
		AlertDialog.Builder ab = null;
		ab = new AlertDialog.Builder( context );
		ab.setMessage(message);
		ab.setPositiveButton(android.R.string.ok, null);
		ab.setTitle( title );
		ab.show(); 
	}
	
	public static void YesNoDialog(final Context context, String message) {
		String dlg_message = "Are you sure you want to exit?";
		if (!message.equals("'") || message != null) {
			dlg_message = message;
		}
		
		AlertDialog dialog = new AlertDialog.Builder(context).create();
        dialog.setTitle("Confirmation");
        dialog.setMessage(message);
        dialog.setCancelable(false);
        dialog.setButton(DialogInterface.BUTTON_POSITIVE, "Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int buttonId) {
                myInterface.getListener().onDialogCompleted(true);
            }
        });
        dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int buttonId) {
                myInterface.getListener().onDialogCompleted(false);

            }
        });
        dialog.setIcon(android.R.drawable.ic_dialog_alert);
        dialog.show();
	}
	
	/*
	public static void ConfirmNoDialog(final Context context, String message) {
		String dlg_message = "Are you sure you want to exit?";
		if (!message.equals("'") || message != null) {
			dlg_message = message;
		}
		
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setMessage(dlg_message)
		       .setCancelable(false)
		       .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		             Toast.makeText(context, "ID value is " + Integer.toString(id), Toast.LENGTH_SHORT).show();
		           }
		       })
		       .setNegativeButton("No", new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		             Toast.makeText(context, "ID value is " + Integer.toString(id), Toast.LENGTH_SHORT).show();
		             dialog.cancel();
		           }
		       });

		AlertDialog alert = builder.create();
		alert.show();
	}
	*/
		
	
	public static ProgressDialog DialogProgress(Context context) {
	       ProgressDialog dialog = ProgressDialog.show(context, "",
	                        "잠시만 기다려 주세요 ...", true);
	       
		    /*
		    ProgressDialog pgsDialog = new ProgressDialog(LoginActivity.this);
		    pgsDialog.setMessage("잠시만 기다려 주세요 ...");
		    pgsDialog.setIndeterminate(false); 
		    pgsDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		    pgsDialog.show();
		    */
	       
	       return dialog;
	      // 창을 내린다.
	      // dialog.dismiss();
	}


	@Override
	public void onDialogCompleted(boolean answer) {
		// TODO Auto-generated method stub
		
	}

}
