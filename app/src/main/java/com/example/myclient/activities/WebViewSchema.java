package com.example.myclient.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.example.myclient.R;


public class WebViewSchema extends AppCompatActivity {
    private WebView webView;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview_scheme);

        Intent intent = getIntent();
        String durl = intent.getExtras().getString("url");

        webView =findViewById(R.id.webView);
        webView.setWebViewClient(new WebViewClient());


        progressDialog = new ProgressDialog(WebViewSchema.this);
        progressDialog.setTitle("Your content is loading");
        progressDialog.setIcon(R.drawable.loadingweb);
        progressDialog.setMessage("Just relax...");
        progressDialog.show();
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return super.shouldOverrideUrlLoading(view, request);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
            }
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Toast.makeText(WebViewSchema.this, "Error:" + description, Toast.LENGTH_SHORT).show();

            }
        });
        webView.loadUrl(durl);
        WebSettings webSettings = webView.getSettings();
        webSettings.getJavaScriptEnabled();
    }

    @Override
    public void onBackPressed() {
        if(webView.canGoBack()){
            webView.goBack();
        }else {
            super.onBackPressed();
        }
    }

}
