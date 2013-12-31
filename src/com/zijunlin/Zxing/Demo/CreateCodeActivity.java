package com.zijunlin.Zxing.Demo;

import java.io.ByteArrayOutputStream;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.StructuredName;
import android.provider.ContactsContract.RawContacts;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.BMapManager;
import com.data.code.util.CodeApplcation;
import com.data.code.util.CodeData;
import com.data.code.util.System;
import com.data.code.util.Time;
import com.data.code.util.UrlWebView;
import com.zijunlin.Zxing.Demo.view.ViewfinderView;

@TargetApi(Build.VERSION_CODES.ECLAIR)
public class CreateCodeActivity extends Activity {

	private EditText phone;// ����

	private EditText name;// ����

	private TextView codetext;// ����

	private SQLiteDatabase database;

	private CodeData sqldata;

	private static final int TO_SCAN = 99;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		CodeApplcation applcation = (CodeApplcation) this.getApplication();

		if (applcation.bMapManager == null) {

			applcation.bMapManager = new BMapManager(this);

		}
		applcation.bMapManager.init(applcation.key,
				new CodeApplcation.MyGeneralListener());
		setContentView(R.layout.codemain);

		Intent intents = new Intent(CreateCodeActivity.this,
				CaptureActivity.class);
		startActivityForResult(intents, TO_SCAN);

		super.onCreate(savedInstanceState);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		codetext = (TextView) this.findViewById(R.id.codetext);
		sqldata = new CodeData(this);
		database = sqldata.getWritableDatabase();

		super.onResume();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case TO_SCAN:
			if (resultCode == RESULT_OK) {

				String time = Time.TimeFor();

				Cursor cursor = database.rawQuery("select _id from dhdata",
						null);

				Log.i("����", "" + cursor.getCount());

				ContentValues values = new ContentValues();

				final String scanStr = data.getStringExtra("RESULT");

				ByteArrayOutputStream os = new ByteArrayOutputStream();

				if (ViewfinderView.resultBitmap != null) {
					Log.i("��ͼ", "ss" + ViewfinderView.resultBitmap);
					ViewfinderView.resultBitmap.compress(
							Bitmap.CompressFormat.PNG, 0, os);

					values.put("pic", os.toByteArray());

				}

				values.put("time", time);
				Log.i("scanStr", "" + scanStr);
				if (scanStr.startsWith("MECARD")) {
					// ��Ƭ
					LayoutInflater inflater = LayoutInflater
							.from(CreateCodeActivity.this);

					View view = inflater.inflate(R.layout.card, null);

					name = (EditText) view.findViewById(R.id.resulteditname);

					phone = (EditText) view.findViewById(R.id.resulteditmobile);

					if (true) {
						String[] temp = scanStr.split(";");
						int begin = temp[0].indexOf(":");
						int end = scanStr.indexOf(";");
						String[] names = temp[0].split(":");
						String nametruescan = names[2].toString().trim();
						// System.out.println(t);
						String[] tempphone = temp[3].split(":");
						// System.out.println(tempphone[1]);
						String phonetruescan = tempphone[1];
						name.setText(nametruescan);
						phone.setText(phonetruescan);

						values.put(System.TABLE_FIELD_NAME, nametruescan);

						values.put(System.TABLE_FIELD_PHONE, nametruescan);

						values.put(System.TABLE_FIELD_FLAGE, "��Ƭ");

						// codeEdit.setVisibility(8);
						AlertDialog dialog = new AlertDialog.Builder(
								CreateCodeActivity.this)
								.setTitle("��Ƭ����")
								.setView(view)
								.setPositiveButton("ȡ��",
										new DialogInterface.OnClickListener() {

											public void onClick(
													DialogInterface dialog,
													int which) {
												// TODO Auto-generated method
												// stub
												Intent intent = new Intent();

												startActivity(intent);
												CreateCodeActivity.this
														.finish();
											}

										})
								.setNeutralButton("����", new OnClickListener() {

									public void onClick(DialogInterface dialog,
											int which) {
										// TODO Auto-generated method stub
										String number = phone.getText()
												.toString();
										Intent intent = new Intent();
										intent.setAction("android.intent.action.CALL");
										intent.setData(Uri.parse("tel:"
												+ number));
										startActivity(intent);// �����ڲ����Զ�ΪIntent������android.intent.category.DEFAULT
										CreateCodeActivity.this.finish();
									}
								})
								.setNegativeButton("����",
										new DialogInterface.OnClickListener() {

											public void onClick(
													DialogInterface dialog,
													int which) {
												// TODO Auto-generated method
												// stub

												CardAdd();
												CreateCodeActivity.this
														.finish();
												// IscloseDialog.keepDialog(dialog);
												// startActivity(intent);
											}
										}).create();
						dialog.setCancelable(false);// ���η��ؼ�
						dialog.show();

					}

				} else if (scanStr.startsWith("http")) {
					// one.setVisibility(8);
					// ��ַ
					LayoutInflater inflater = LayoutInflater
							.from(CreateCodeActivity.this);

					View view = inflater.inflate(R.layout.view, null);

					TextView textView = (TextView) view.findViewById(R.id.view);
					textView.setText(scanStr);

					values.put(System.TABLE_FIELD_CONTENT, scanStr);

					values.put(System.TABLE_FIELD_FLAGE, "��ַ");

					AlertDialog dialog = new AlertDialog.Builder(
							CreateCodeActivity.this)
							.setTitle("���ʶ�ά��")
							.setView(view)
							.setPositiveButton("ȡ��",
									new DialogInterface.OnClickListener() {

										public void onClick(
												DialogInterface dialog,
												int which) {
											// TODO Auto-generated method stub
											Toast.makeText(
													CreateCodeActivity.this,
													"����ȡ������",
													Toast.LENGTH_SHORT).show();
											CreateCodeActivity.this.finish();
										}

									})
							.setNegativeButton("ȷ��",
									new DialogInterface.OnClickListener() {

										public void onClick(
												DialogInterface dialog,
												int which) {
											// TODO Auto-generated method stub

											Intent intent = new Intent();
											intent.setClass(
													CreateCodeActivity.this,
													UrlWebView.class);
											intent.putExtra("url", scanStr);
											startActivity(intent);
											CreateCodeActivity.this.finish();
										}
									}).create();
					dialog.setCancelable(false);
					dialog.show();
				} else {
					// �ı�
					codetext.setText(scanStr.toString());

					values.put(System.TABLE_FIELD_CONTENT, scanStr);

					values.put(System.TABLE_FIELD_FLAGE, "�ı�");
				}

				values.put("placel", CodeApplcation.longitude);
				values.put("placed", CodeApplcation.latitude);
				values.put("address", CodeApplcation.address);
				long in = database.insert(System.TABLE_NAME_1, null, values);

				Log.i("����ֵ", "" + in);

			} else if (resultCode == RESULT_CANCELED) {
				Toast.makeText(CreateCodeActivity.this, "ɨ��ȡ��",
						Toast.LENGTH_SHORT).show();
				CreateCodeActivity.this.finish();
			} else {
				Toast.makeText(CreateCodeActivity.this, "ɨ���쳣",
						Toast.LENGTH_SHORT).show();
				CreateCodeActivity.this.finish();
			}
			break;

		default:
			break;
		}
	}

	// ��ϵ��
	void CardAdd() {

		ContentResolver resolver = getContentResolver();

		ContentValues values = new ContentValues();
		values.put(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, "dfds");
		Uri rawContactUri = resolver.insert(RawContacts.CONTENT_URI, values);

		Long rawContactId = ContentUris.parseId(rawContactUri);

		values.clear();
		values.put(ContactsContract.Data.RAW_CONTACT_ID, rawContactId);
		values.put(ContactsContract.Data.MIMETYPE,
				StructuredName.CONTENT_ITEM_TYPE);
		values.put(StructuredName.DISPLAY_NAME, name.getText().toString());
		resolver.insert(ContactsContract.Data.CONTENT_URI, values);

		Uri phoneUri = null;
		phoneUri = Uri.withAppendedPath(rawContactUri,
				ContactsContract.Contacts.Data.CONTENT_DIRECTORY);
		values.clear();
		values.put(ContactsContract.CommonDataKinds.Phone.IS_SUPER_PRIMARY, 1);
		values.put(ContactsContract.Data.MIMETYPE,
				ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE);
		values.put(ContactsContract.CommonDataKinds.Phone.NUMBER, phone
				.getText().toString());
		resolver.insert(phoneUri, values);

		Toast.makeText(CreateCodeActivity.this, "�����ϵ�˳ɹ�", Toast.LENGTH_SHORT)
				.show();
	}

}
