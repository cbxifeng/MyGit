package com.data.code.util;

import com.zijunlin.Zxing.Demo.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
  
public class UrlWebView extends Activity {
WebView webView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.webview);
		webView=(WebView)findViewById(R.id.webview);
		Intent intent=this.getIntent();
		String url=intent.getStringExtra("url");
		webView.loadUrl(url);
		
		webView.getSettings().setBuiltInZoomControls(true);// 会出现放大缩小的按钮
		webView.getSettings().setSupportZoom(true);

		webView.getSettings().setJavaScriptEnabled(true);
		webView.getSettings().setPluginsEnabled(true);
		//webView.addJavascriptInterface(new InJavaScriptLocalObj(), "local_obj");
		
	}

}
