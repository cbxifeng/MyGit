package com.zijunlin.Zxing.Demo;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.data.code.util.CodeData;

public class History extends Activity {

	private SQLiteDatabase database;

	private CodeData sqldata;
	private Cursor cursor;
	private ListView list;
	private ArrayList<byte[]> bitmaps;
	private ArrayList<String> contents;
	private ArrayList<String> times;
	private ArrayList<String> logs;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		this.requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.history);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.title);
		CheckData();
		
		super.onCreate(savedInstanceState);
	}

	class OnitemOnclick implements OnItemClickListener {

		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// TODO Auto-generated method stub
			cursor.moveToPosition(position);
			byte[] in = cursor.getBlob(cursor.getColumnIndex("pic"));
			String content = cursor.getString(cursor.getColumnIndex("content"));
			String time = cursor.getString(cursor.getColumnIndex("time"));
			String log = cursor.getString(cursor.getColumnIndex("flage"));
			String address = cursor.getString(cursor.getColumnIndex("address"));
			
			Intent intent=new Intent();
			intent.putExtra("in", in);
			intent.putExtra("content", content);
			intent.putExtra("time", time);
			intent.putExtra("log", log);
			intent.putExtra("adress", address);
			
			intent.setClass(History.this, Contents.class);
            startActivity(intent);
		}

	}

	void CheckData() {
		list = (ListView) findViewById(R.id.list);

		sqldata = new CodeData(this);

		database = sqldata.getWritableDatabase();

		cursor = database.rawQuery("select * from dhdata", null);

		bitmaps = new ArrayList<byte[]>();
		contents = new ArrayList<String>();
		times = new ArrayList<String>();
		logs = new ArrayList<String>();
		while (cursor.moveToNext()) {

			byte[] in = cursor.getBlob(cursor.getColumnIndex("pic"));

			if (in.length != 0) {
				Log.i("图片长度", "ggg" + cursor.getCount());
				// Bitmap bmpout = BitmapFactory.decodeByteArray(in, 0,
				// in.length);
				bitmaps.add(in);
			}

			String content = cursor.getString(cursor.getColumnIndex("content"));
			contents.add(content);
			Log.i("content", "ggg" + content);
			String time = cursor.getString(cursor.getColumnIndex("time"));
			times.add(time);
			String log = cursor.getString(cursor.getColumnIndex("flage"));
			logs.add(log);
		}
		Message message = new Message();
		message.what = 1;
		handler.sendMessage(message);
	}

	private class HisAdapter extends BaseAdapter {

		private LayoutInflater inflater = null;

		public HisAdapter(Context context) {

			inflater = LayoutInflater.from(context);

			// TODO Auto-generated constructor stub
		}

		public int getCount() {
			// TODO Auto-generated method stub
			return times.size();
		}

		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			ViewHolder holder;
			if (convertView == null) {
				holder = new ViewHolder();
				convertView = inflater.inflate(R.layout.hislist, null);
				holder.imageView = (ImageView) convertView
						.findViewById(R.id.imageView1);
				holder.time = (TextView) convertView.findViewById(R.id.time);
				holder.content = (TextView) convertView
						.findViewById(R.id.content);
				holder.log = (TextView) convertView.findViewById(R.id.log);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			Bitmap bmpout = BitmapFactory.decodeByteArray(
					bitmaps.get(position), 0, bitmaps.get(position).length);

			holder.imageView.setImageDrawable(new BitmapDrawable(bmpout));

			holder.time.setText(times.get(position));

			holder.content.setText(contents.get(position));

			holder.log.setText(logs.get(position));

			return convertView;
		}

		class ViewHolder {
			ImageView imageView;
			TextView time;
			TextView content;
			TextView log;
		}

	}

	Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			if (msg.what == 1) {

				list.setAdapter(new HisAdapter(History.this));
				list.setOnItemClickListener(new OnitemOnclick());
			}
			super.handleMessage(msg);
		}

	};
}
