package br.edu.unibratec.projetoeltondois.repository;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import br.edu.unibratec.projetoeltondois.entity.Evento;

public class EventoDB {

	private DBHelper helper;

	public EventoDB(Context contexto) {
		helper = new DBHelper(contexto);
	}

	public long inserir(Evento evento) {
		SQLiteDatabase db = helper.getWritableDatabase();

		ContentValues values = valoresPorEvento(evento);
		long id = db.insert("evento", null, values);
		evento.setId(id);
		evento.setFavorito(true);

		db.close();

		return id;
	}

	private ContentValues valoresPorEvento(Evento evento) {
		ContentValues values = new ContentValues();
		values.put("nome", evento.getNome());
		values.put("logomarca", evento.getLogomarca());
		values.put("endereco", evento.getEndereco());
		values.put("data", evento.getData());
		values.put("detalhes", evento.getDetalhes());
		return values;
	}

	public int excluir(Evento evento) {
		SQLiteDatabase db = helper.getWritableDatabase();
		int rows = db.delete("evento", "_id = " + evento.getId(), null);
		evento.setFavorito(false);

		db.close();

		return rows;
	}

	public boolean favorito(Evento evento) {
		SQLiteDatabase db = helper.getReadableDatabase();
		Cursor cursor = db.rawQuery("select * from evento where nome = ?",
				new String[] { evento.getNome() });

		boolean resultado = false;
		if (cursor != null && cursor.getCount() > 0) {
			cursor.moveToNext();
			evento.setId(cursor.getLong(cursor.getColumnIndex("_id")));
			resultado = true;
		}

		cursor.close();
		db.close();
		return resultado;
	}

	public List<Evento> todosOsEventos() {
		List<Evento> eventos = new ArrayList<Evento>();

		SQLiteDatabase db = helper.getReadableDatabase();

		Cursor cursor = db.rawQuery("select * from evento", null);

		while (cursor.moveToNext()) {
			Evento evento = preencherEvento(cursor);
			eventos.add(evento);
		}
		cursor.close();
		db.close();
		return eventos;
	}

	private Evento preencherEvento(Cursor cursor) {
		long id = cursor.getLong(0);
		String nome = cursor.getString(1);
		String logomarca = cursor.getString(2);
		String endereco = cursor.getString(3);
		String data = cursor.getString(4);
		String detalhes = cursor.getString(5);

		Evento evento = new Evento();
		evento.setId(id);
		evento.setNome(nome);
		evento.setLogomarca(logomarca);
		evento.setEndereco(endereco);
		evento.setFavorito(true);
		evento.setData(data);
		evento.setDetalhes(detalhes);
		return evento;
	}
}
