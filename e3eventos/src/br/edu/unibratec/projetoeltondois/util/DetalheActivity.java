package br.edu.unibratec.projetoeltondois.util;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import br.edu.unibratec.projetoeltondois.entity.Evento;
import br.edu.unibratec.projetoeltondois.fragments.DetalheFragment;

public class DetalheActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Evento restaurante = (Evento) getIntent()
				.getSerializableExtra("evento");

		DetalheFragment d = DetalheFragment.novaInstancia(restaurante);

		getSupportFragmentManager().beginTransaction()
				.replace(android.R.id.content, d).commit();
	}

}
