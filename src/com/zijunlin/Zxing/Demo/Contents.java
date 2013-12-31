package com.zijunlin.Zxing.Demo;

import com.data.code.util.CodeData;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

public class Contents extends Activity {
	private SQLiteDatabase database;

	private CodeData sqldata;
	private Cursor cursor;

	private byte[] in;
	private ImageView imageView;
	private TextView contents;
	private TextView times;
	private TextView logs;
	private TextView adresss;

	private String time;
	private String log;
	private String adress;
	private   DisplayMetrics dm;
	private String content;
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		// TODO Auto-generated method stub
		setContentView(R.layout.contens);

		Intent intent = this.getIntent();
		in = intent.getByteArrayExtra("in");
		content = intent.getStringExtra("content");
		time = intent.getStringExtra("time");
		log = intent.getStringExtra("log");
		adress = intent.getStringExtra("adress");

		InutView();
		Init();
		super.onCreate(savedInstanceState);
	}

	void Init() {
		
		Bitmap bmpout = BitmapFactory.decodeByteArray(
				in, 0,
				in.length);

		imageView.setImageDrawable(new BitmapDrawable(bmpout));
		contents.setText(content);
		times.setText(time);
		logs.setText(log);
		adresss.setText(adress);
	}

	void InutView() {
		dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		contents=(TextView) this.findViewById(R.id.concontent);
		times=(TextView) this.findViewById(R.id.contime);
		logs=(TextView) this.findViewById(R.id.conlog);
		adresss=(TextView) this.findViewById(R.id.conadress);
		imageView = (ImageView) this.findViewById(R.id.conimage);
		LinearLayout.LayoutParams layoutParams=(LinearLayout.LayoutParams) imageView.getLayoutParams();
		layoutParams.height=dm.heightPixels/3;
		imageView.setLayoutParams(layoutParams);
	}
}
