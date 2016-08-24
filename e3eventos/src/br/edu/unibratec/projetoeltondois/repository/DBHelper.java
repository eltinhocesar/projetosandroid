package br.edu.unibratec.projetoeltondois.repository;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

	private static final String NOME_BANCO = "eventosDB";
	private static final int VERSAO_BANCO = 1;

	public DBHelper(Context context) {
		super(context, NOME_BANCO, null, VERSAO_BANCO);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE evento (_id INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ "nome TEXT NOT NULL, logomarca TEXT, endereco TEXT NOT NULL, data TEXT NOT NULL, detalhes TEXT)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Utilizar so na proxima versao :)
	}

}
