package com.very_news;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class Newsdao {

	private SQLiteDatabase db;

	public Newsdao(Context context) {
		DBHelper helper = new DBHelper(context);
		db = helper.getWritableDatabase();
	}

	// 添加数据的方�?
	public long insert(String table, ContentValues values) {
		return db.insert(table, null, values);
	}

	// 修改数据的方�?
	public int update(String table, ContentValues values, String whereClause,
			String[] whereArgs) {
		return db.update(table, values, whereClause, whereArgs);
	}

	// 删除方法
	public int delete(String table, String whereClause, String[] whereArgs) {
		return db.delete(table, whereClause, whereArgs);
	}

	// 查询方法
	public Cursor select(String sql, String[] selectionArgs) {
		return db.rawQuery(sql, selectionArgs);
	}

	// 第二种查询方�?
	public Cursor select2(boolean distinct, String table, String[] columns,
			String selection, String[] selectionArgs, String groupBy,
			String having, String orderBy, String limit) {
		return db.query(distinct, table, columns, selection, selectionArgs,
				groupBy, having, orderBy, limit);
	}

}
