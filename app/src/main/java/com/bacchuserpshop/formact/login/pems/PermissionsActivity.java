package com.bacchuserpshop.formact.login.pems;
import com.bacchuserpshop.formact.main.WebViewMangActivity;
import com.bacchuserpshop.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class PermissionsActivity extends AppCompatActivity {

    private static final String LOG_TAG = "PermissionsActivity";

    public static final int PERMISSIONS_GRANTED = 0;
    public static final int PERMISSIONS_DENIED = 1;

    private static final int PERMISSION_REQUEST_CODE = 0;
    private static final String EXTRA_PERMISSIONS = "com.stylingandroid.permissions.EXTRA_PERMISSIONS";
    private static final String PACKAGE_URL_SCHEME = "package:";

    private PermissionsChecker checker;
    private boolean requiresCheck;

    public static void startActivityForResult(Activity activity, int requestCode, String... permissions) {
        Intent intent = new Intent(activity, PermissionsActivity.class);
        intent.putExtra(EXTRA_PERMISSIONS, permissions);
        ActivityCompat.startActivityForResult(activity, intent, requestCode, null);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getIntent() == null || !getIntent().hasExtra(EXTRA_PERMISSIONS)) {
            throw new RuntimeException("This Activity needs to be launched using the static startActivityForResult() method.");
        }
        setContentView(R.layout.smart_davada_spalish); //xml 파일명 변경 필요

        checker = new PermissionsChecker(this);
        requiresCheck = true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (requiresCheck) {
            String[] permissions = getPermissions();

            if (checker.lacksPermissions(permissions)) {
                requestPermissions(permissions);
            } else {
                allPermissionsGranted();
            }
        } else {
            requiresCheck = true;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private String[] getPermissions() {
        return getIntent().getStringArrayExtra(EXTRA_PERMISSIONS);
    }

    private void requestPermissions(String... permissions) {
        // SDK API-33 버전에서는 지원하지 않음.. Android-12버전(SDK 32)사용
        //ActivityCompat.requestPermissions(this, permissions, PERMISSION_REQUEST_CODE);
        //
        Log.i(LOG_TAG, "== onResume() requestPermissions launch..");
        // SDK API-33 버전사용함
        requestPermissionsLauncher.launch(permissions);
    }

    private void allPermissionsGranted() {
        Log.i(LOG_TAG, "== onResume() allPermissionsGranted..");

        setResult(PERMISSIONS_GRANTED);
        finish();

        // 로그인 이동
        goToLoginScreen();
    }

    // SDK API-33 버전사용함
    private ActivityResultLauncher<String[]> requestPermissionsLauncher = registerForActivityResult(
            new ActivityResultContracts.RequestMultiplePermissions(),
            grantResults ->
            {
                Log.i(LOG_TAG, "== onResume() registerForActivityResult grantResults : " + grantResults.toString());
//                if (hasPermissions(grantResults)) {
//                    requiresCheck = true;
//                    allPermissionsGranted();
//                } else {
//                    requiresCheck = false;
//                    showMissingPermissionDialog();
//                }
            }
    );

//    private boolean hasPermissions(String[] permissions) {
//        if (permissions != null) {
//            for (String permission : permissions) {
//                if (ActivityCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
//                    Log.d("PERMISSIONS", "Permission is not granted: " + permission);
//                    return false;
//                }
//                Log.d("PERMISSIONS", "Permission already granted: " + permission);
//            }
//            return true;
//        }
//        return false;
//    }

    // SDK API-33 버전에서는 지원하지 않음.. Android-12버전(SDK 32)사용
//    @SuppressLint("MissingSuperCall")
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        if (requestCode == PERMISSION_REQUEST_CODE && hasAllPermissionsGranted(grantResults)) {
//            requiresCheck = true;
//            allPermissionsGranted();
//        } else {
//            requiresCheck = false;
//            showMissingPermissionDialog();
//        }
//    }

    private boolean hasAllPermissionsGranted(@NonNull int[] grantResults) {
        for (int grantResult : grantResults) {
            if (grantResult == PackageManager.PERMISSION_DENIED) {
                return false;
            }
        }
        return true;
    }

    //모두 거부 하였을 때 나타나는 팝업 창 설정입니다.
    private void showMissingPermissionDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(PermissionsActivity.this);
        dialogBuilder.setTitle(R.string.help); //팝업 창 타이틀
        //팝업 안내 메시지 부분으로 string.xml에서 설정한 메시지를 노출합니다.
        dialogBuilder.setMessage(R.string.string_help_text);
        //팝업 창 버튼 2개를 노출합니다.
        //확인버튼을 누르면 앱 종료?인데 저는 팝업 창만 사라졌네요
        dialogBuilder.setNegativeButton(R.string.quit, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                setResult(PERMISSIONS_DENIED);
                finish();
            }
        });
        //설정 버튼을 누르면 설정 화면으로 이동합니다.
        dialogBuilder.setPositiveButton(R.string.settings, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startAppSettings();
            }
        });
        dialogBuilder.show();
    }

    private void startAppSettings() {
        Intent intent = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse(PACKAGE_URL_SCHEME + getPackageName()));
        startActivity(intent);
    }

    // 로그인 이동
    protected void goToLoginScreen() {

        try {
            // 로그인 이동
            //Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            //startActivity(intent);

            // 다바다ERP 웹뷰 호출
            Intent intent = new Intent(getApplicationContext(), WebViewMangActivity.class);
            startActivity(intent);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
