package com.example.classmanagementapp;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.ClientCertRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.classmanagementapp.Utils.TimeUtil;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

public class BrowserActivity extends AppCompatActivity {
    ImageButton imgBtn_goHome, imgBtn_addTab, imgBtn_showMore;
    RelativeLayout btn_showTabList;
    EditText edit_userInput;
    WebView webView;
    SharedPreferences preferences;
    String startPage = "https://j29-plw.osaka-sandai.ac.jp/cas/login?service=https%3A%2F%2Fed24lb.osaka-sandai.ac.jp%2Fwebclass%2Flogin.php%3Fauth_mode%3DCAS";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browser);

        preferences = this.getApplicationContext().getSharedPreferences("Setting", MODE_PRIVATE);

        imgBtn_goHome = findViewById(R.id.imgBtn_goHome);
        imgBtn_addTab = findViewById(R.id.imgBtn_addTab);
        imgBtn_showMore = findViewById(R.id.imgBtn_showMore);
        btn_showTabList = findViewById(R.id.btn_showTabList);
        edit_userInput = findViewById(R.id.edit_userInput);
        webView = findViewById(R.id.webView);

        Dexter.withContext(this).withPermission(Manifest.permission.INTERNET)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        appSetUP();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                        Toast.makeText(BrowserActivity.this, "インターネットの使用を許可してください", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                }).check();
    }

    private void appSetUP() {
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setBuiltInZoomControls(true);

        edit_userInput.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if(view != null && keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                    webView.loadUrl(edit_userInput.getText().toString());
                    InputMethodManager im = (InputMethodManager) BrowserActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
                    im.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);
                    Log.d("MyLog", "searching");
                    return true;
                } else {
                    return false;
                }
            }
        });

        webView.setWebViewClient(myClient);
        webView.loadUrl(startPage);

        imgBtn_goHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(webView != null) {
                    webView.loadUrl("https://google.com");
                }
            }
        });
    }


    private final WebViewClient myClient = new WebViewClient() {
        boolean failed = false;
        @Override
        public void onPageFinished(WebView view, String url) {
            Log.d("MyLog", "pageFinished");
            Log.d("MyLog", url);
            failed = TimeUtil.runOnDoubleTap() ? true : false;
            String username = preferences.getString("webClassUsername", "");
            String password = preferences.getString("webClassPassword", "");
            if(url.equals(startPage) && !username.equals("") && !password.equals("") && !failed) {
                Log.d("MyLog", preferences.getString("webClassUsername", ""));
                Log.d("MyLog", preferences.getString("webClassPassword", ""));
                webView.evaluateJavascript(String.format("document.getElementById(\"user\").value = '%s';",
                        preferences.getString("webClassUsername", "")), null);
                webView.evaluateJavascript(String.format("document.getElementById(\"password\").value = '%s';",
                        preferences.getString("webClassPassword", "")), null);
                webView.evaluateJavascript("document.querySelector(\"input.button_login\").click();", null);
            }
            super.onPageFinished(view, url);
        }
    };

    @Override
    public void onBackPressed() {
        if(webView != null && webView.canGoBack()) {
            webView.goBack();
        } else if(webView != null && !webView.canGoBack() && TimeUtil.runOnDoubleTap()) {
            super.onBackPressed();
        } else {
            Toast.makeText(this, "戻るには戻るボタンを２回タップしてください。", Toast.LENGTH_SHORT).show();
        }
    }
}