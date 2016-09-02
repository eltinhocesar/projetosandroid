package br.edu.unibratec.projetoeltondois.fragments;

import java.util.List;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.AsyncTask.Status;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import br.edu.unibratec.projetoeltondois.R;
import br.edu.unibratec.projetoeltondois.entity.Evento;
import br.edu.unibratec.projetoeltondois.repository.EventoParser;
import br.edu.unibratec.projetoeltondois.util.ClicouNoEvento;
import br.edu.unibratec.projetoeltondois.util.EventoAdapter;

public class ListEventoFragment extends ListFragment {
	List<Evento> eventos;
	ReadEventoAsyncTask task;
	ProgressBar progress;
	TextView txtMensagem;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		setRetainInstance(true);
		if (eventos != null) {
			txtMensagem.setVisibility(View.GONE);
			progress.setVisibility(View.GONE);
			refreshList();

		} else {
			if (task != null && task.getStatus() == Status.RUNNING) {
				mostrarProgress();
			} else {
				iniciarDownload();
			}
		}
	}

	private void iniciarDownload() {

		ConnectivityManager cm = (ConnectivityManager) getActivity()
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		if (cm.getActiveNetworkInfo() != null
				&& cm.getActiveNetworkInfo().isConnected()) {

			task = new ReadEventoAsyncTask();
			task.execute();

		} else {
			progress.setVisibility(View.GONE);
			txtMensagem.setVisibility(View.VISIBLE);
			txtMensagem.setText("Sem conexao com a Internet");
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View layout = inflater.inflate(R.layout.fragment_lista_eventos,
				container, false);

		progress = (ProgressBar) layout.findViewById(R.id.progressBar1);
		txtMensagem = (TextView) layout.findViewById(R.id.textView1);

		return layout;
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);

		if (getActivity() instanceof ClicouNoEvento) {
			((ClicouNoEvento) getActivity())
					.eventoFoiClicado(eventos.get(position));
		}
	}

	private void refreshList() {
		// ArrayAdapter<Post> adapter = new ArrayAdapter<Post>(
		// getActivity(),
		// android.R.layout.simple_list_item_1,
		// posts);
		EventoAdapter adapter = new EventoAdapter(getActivity(),
				eventos);
		setListAdapter(adapter);
	}

	private void mostrarProgress() {
		progress.setVisibility(View.VISIBLE);
		txtMensagem.setVisibility(View.VISIBLE);
		txtMensagem.setText("Carregando...");
	}

	class ReadEventoAsyncTask extends
			AsyncTask<Void, Void, List<Evento>> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			mostrarProgress();
		}

		@Override
		protected List<Evento> doInBackground(Void... params) {
			try {
				// Thread.sleep(4000);
				return EventoParser.retrieveEvento();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(List<Evento> result) {
			super.onPostExecute(result);
			if (result != null) {
				eventos = result;
				refreshList();
				txtMensagem.setVisibility(View.GONE);
			} else {
				txtMensagem.setText("Deu erro!");
			}
			progress.setVisibility(View.GONE);
		}
	}
}
