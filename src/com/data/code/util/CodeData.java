package com.data.code.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CodeData extends SQLiteOpenHelper {

	public CodeData(Context context) {
		super(context, System.DATABASE_NAME, null, System.DATABASE_VERSION);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub  
		try {

			StringBuffer sql = new StringBuffer();
			sql.append("CREATE TABLE " + System.TABLE_NAME_1 + " ( "  
					+ System.TABLE_FIELD_ID
					+ " INTEGER PRIMARY KEY autoincrement, "
					+ System.TABLE_FIELD_CONTENT + " text,"       
					+ System.TABLE_FIELD_PLACEL + " text,"
					+ System.TABLE_FIELD_PLACED + " text,"
					+ System.TABLE_FIELD_ADDRESS + " text,"
					+ System.TABLE_FIELD_PIC + " BLOB,"
					+ System.TABLE_FIELD_TIME + " text,"
					+ System.TABLE_FIELD_CODEFLAGE + " text,"
					+ System.TABLE_FIELD_NAME + " text,"
					+ System.TABLE_FIELD_PHONE + " text,"
					+ System.TABLE_FIELD_FLAGE + " text);");
			db.execSQL(sql.toString());
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL("DROP TABLE IF EXISTS " + "dhdata");
		onCreate(db);
	

	}

}
