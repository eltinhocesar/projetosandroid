package br.edu.unibratec.projetoeltondois.repository;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import br.edu.unibratec.projetoeltondois.entity.Evento;

public class EventoParser {

	public static List<Evento> retrieveEvento() throws Exception {

		URL url = new URL(
				"https://raw.githubusercontent.com/eltinhocesar/projects/master/eventos/eventos.json");
		HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
		conexao.setRequestMethod("GET");
		conexao.setReadTimeout(10000);
		conexao.setConnectTimeout(15000);
		conexao.setDoInput(true);

		conexao.connect();
		if (conexao.getResponseCode() == 200) { // HTTP_OK
			return parseEventoJson(conexao.getInputStream());
		}
		return null;
	}

	public static List<Evento> parseEventoJson(InputStream is)
			throws JSONException, IOException {
		List<Evento> restaurantes = new ArrayList<Evento>();

		JSONObject json = new JSONObject(bytesToString(is));

		JSONArray jsonEventos = json.getJSONArray("eventos");
		for (int i = 0; i < jsonEventos.length(); i++) {
			JSONObject jsonRestaurante = jsonEventos.getJSONObject(i);
			Evento restaurante = new Evento();
			restaurante.setNome(jsonRestaurante.getString("nome"));
			restaurante.setLogomarca(jsonRestaurante.getString("logomarca"));
			restaurante.setEndereco(jsonRestaurante.getString("endereco"));
			restaurante.setData(jsonRestaurante.getString("data"));
			restaurante.setDetalhes(jsonRestaurante.getString("detalhes"));
			restaurantes.add(restaurante);
		}
		return restaurantes;
	}

	private static String bytesToString(InputStream is) throws IOException {
		byte[] bufferzinho = new byte[1024];
		ByteArrayOutputStream bufferzao = new ByteArrayOutputStream();
		int bytesLidos;
		while ((bytesLidos = is.read(bufferzinho)) != -1) {
			bufferzao.write(bufferzinho, 0, bytesLidos);
		}
		return new String(bufferzao.toByteArray());
	}
}
