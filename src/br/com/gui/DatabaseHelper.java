package br.com.gui;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

	private static final String DATABASENAME_NAME = "produtos.sqlite";
	private static final int DATABASE_VERSION = 1;

	public DatabaseHelper(Context context) {
		super(context, DATABASENAME_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		StringBuilder sb = new StringBuilder();
		sb.append("CREATE TABLE produtos ( ");
		sb.append("_id INTEGER PRIMARY KEY, ");
		sb.append("codigo TEXT, ");
		sb.append("nome TEXT, ");
		sb.append("preco REAL, ");
		sb.append("estoque REAL, ");
		sb.append("categoria INTEGER,");
		sb.append("FOREIGN KEY(categoria) REFERENCES categorias(_id))");
		db.execSQL(sb.toString());
		
		sb = new StringBuilder();
		sb.append("CREATE TABLE categorias ( ");
		sb.append("_id INTEGER PRIMARY KEY, ");
		sb.append("descricao TEXT, ");
		sb.append("cor TEXT)");
		db.execSQL(sb.toString());
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}

}
