package com.dayakar.classroom.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.dayakar.classroom.R;

public class WnnouActivity extends AppCompatActivity {
    private WebView mWebView;
    private ProgressBar progressBar;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wnnou);
        // initializing toolbar
        getSupportActionBar().setTitle("Winnou");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //setting web view and progressbar
        mWebView = findViewById(R.id.webwiew1);
        progressBar = findViewById(R.id.pb);
        progressBar.setMax(100);

        mWebView.setWebViewClient(new HelpClient());
        mWebView.setWebChromeClient(new WebChromeClient(){


            public void onProgressChanged(WebView view,int progress){

                progressBar.setProgress(progress);
                if(progress==100){
                    progressBar.setVisibility(View.GONE);
                }else {
                    progressBar.setVisibility(View.VISIBLE);
                }
                super.onProgressChanged(view,progress);
            }
        });
        mWebView.getSettings().setLoadWithOverviewMode(true);
        mWebView.getSettings().setUseWideViewPort(true);
        mWebView.getSettings().setBuiltInZoomControls(true);
        mWebView.getSettings().getJavaScriptEnabled();
        mWebView.loadUrl("https://mgit.winnou.net/");
        progressBar.setProgress(0);


    }


    private class HelpClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request){
            String url = request.getUrl().toString();
            view.loadUrl(url);
            return true;

        }}
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_BACK:
                    if (mWebView.canGoBack()) {
                        mWebView.goBack();
                    } else {
                        finish();
                    }
                    return true;
            }

        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case  android.R.id.home:{
                onBackPressed();
            }

        }

        return super.onOptionsItemSelected(item);
    }
}
