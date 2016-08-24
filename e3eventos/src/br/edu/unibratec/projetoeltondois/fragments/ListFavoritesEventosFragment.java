package br.edu.unibratec.projetoeltondois.fragments;

import java.util.List;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;
import br.edu.unibratec.projetoeltondois.entity.Evento;
import br.edu.unibratec.projetoeltondois.repository.EventoDB;
import br.edu.unibratec.projetoeltondois.util.ClicouNoEvento;
import br.edu.unibratec.projetoeltondois.util.EventoAdapter;

public class ListFavoritesEventosFragment extends ListFragment {

	List<Evento> eventos;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		setRetainInstance(true);
	}

	@Override
	public void onResume() {
		super.onResume();
		refreshList();
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);

		if (getActivity() instanceof ClicouNoEvento) {
			((ClicouNoEvento) getActivity())
					.eventoFoiClicado(eventos.get(position));
		}
	}

	public void refreshList() {
		EventoDB db = new EventoDB(getActivity());
		eventos = db.todosOsEventos();

		EventoAdapter adapter = new EventoAdapter(getActivity(),
				eventos);
		setListAdapter(adapter);
	}
}
