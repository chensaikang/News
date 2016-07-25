package com.very_news;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
	private static String DBNAME = "news.db";
	private static int VERSION = 4;

	public DBHelper(Context context) {
		super(context, DBNAME, null, VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String sql = "CREATE TABLE news (_id INTEGER PRIMARY KEY AUTOINCREMENT,title VARCHAR(20),imageUrl VARCHAR(200))";
		db.execSQL(sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		if (newVersion > oldVersion) {
			String dropSql = "drop table if exists news";
			db.execSQL(dropSql);

			String sql = "create table news (_id integer primary key autoincrement,title VARCHAR(20),imageUrl VARCHAR(50))";
			db.execSQL(sql);
		}
	}

}
