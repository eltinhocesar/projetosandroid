package br.edu.unibratec.projetoeltondois.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import br.edu.unibratec.projetoeltondois.R;
import br.edu.unibratec.projetoeltondois.entity.Evento;
import br.edu.unibratec.projetoeltondois.repository.EventoDB;

public class DetalheFragment extends Fragment {
	Evento evento;
	EventoDB db;

	TextView txtNome;
	TextView txtEndereco;
	// WebView webViewConteudo;
	Button btnFavorito;

	public static DetalheFragment novaInstancia(Evento evento) {
		Bundle args = new Bundle();
		args.putSerializable("evento", evento);

		DetalheFragment f = new DetalheFragment();
		f.setArguments(args);
		return f;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
		db = new EventoDB(getActivity());
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View layout = inflater.inflate(R.layout.fragment_detalhe_evento, null);
		// ImageView imgFoto =
		// (ImageView)convertView.findViewById(R.id.imgFoto);
		txtNome = (TextView) layout.findViewById(R.id.txtNome);
		txtEndereco = (TextView) layout.findViewById(R.id.txtEndereco);

		evento = (Evento) getArguments().getSerializable("evento");

		evento.setFavorito(db.favorito(evento));
		txtNome.setText(evento.getNome());
		txtEndereco.setText(evento.getEndereco());
		// webViewConteudo.loadDataWithBaseURL(null, restaurante.nome,
		// restaurante.endereco, "UTF-8", null);
		return layout;
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.detalhe, menu);

		MenuItem item = menu.findItem(R.id.action_favoritos);
		if (evento.isFavorito()) {
			item.setIcon(android.R.drawable.ic_menu_delete);
		} else {
			item.setIcon(android.R.drawable.ic_menu_save);
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		if (item.getItemId() == R.id.action_favoritos) {

			if (evento.isFavorito()) {
				db.excluir(evento);
				Toast.makeText(getActivity(), "Removido dos favoritos",
						Toast.LENGTH_SHORT).show();
			} else {
				db.inserir(evento);
				Toast.makeText(getActivity(), "Adicionado aos favoritos",
						Toast.LENGTH_SHORT).show();
			}

			if (getActivity() instanceof EventoNosFavoritos) {
				((EventoNosFavoritos) getActivity())
						.eventoAdicionadoAoFavorito(evento);
			}

			((ActionBarActivity) getActivity()).supportInvalidateOptionsMenu();
		}
		return super.onOptionsItemSelected(item);
	}

	public interface EventoNosFavoritos {
		void eventoAdicionadoAoFavorito(Evento restaurante);
	}

}
