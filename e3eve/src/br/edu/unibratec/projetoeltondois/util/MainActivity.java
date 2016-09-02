package br.edu.unibratec.projetoeltondois.util;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.SimpleOnPageChangeListener;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.Tab;
import android.support.v7.app.ActionBar.TabListener;
import android.support.v7.app.ActionBarActivity;
import br.edu.unibratec.projetoeltondois.R;
import br.edu.unibratec.projetoeltondois.entity.Evento;
import br.edu.unibratec.projetoeltondois.fragments.DetalheFragment;
import br.edu.unibratec.projetoeltondois.fragments.DetalheFragment.EventoNosFavoritos;
import br.edu.unibratec.projetoeltondois.fragments.ListEventoFragment;
import br.edu.unibratec.projetoeltondois.fragments.ListFavoritesEventosFragment;

public class MainActivity extends ActionBarActivity implements TabListener,
		ClicouNoEvento, EventoNosFavoritos {

	ListEventoFragment fragment1;
	ListFavoritesEventosFragment fragment2;
	ViewPager pager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		fragment1 = new ListEventoFragment();
		fragment2 = new ListFavoritesEventosFragment();

		final ActionBar actionBar = getSupportActionBar();

		pager = (ViewPager) findViewById(R.id.viewPager);
		FragmentManager fm = getSupportFragmentManager();
		pager.setAdapter(new MeuAdapter(fm));
		pager.setOnPageChangeListener(new SimpleOnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
				super.onPageSelected(position);
				actionBar.setSelectedNavigationItem(position);
			}
		});

		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		Tab aba1 = actionBar.newTab();
		aba1.setText("Eventos Disponiveis");
		aba1.setTabListener(this);

		Tab aba2 = actionBar.newTab();
		aba2.setText("Favoritos");
		aba2.setTabListener(this);

		Tab aba3 = actionBar.newTab();
		aba3.setText("Top Eventos");
		aba3.setTabListener(this);

		actionBar.addTab(aba1);
		actionBar.addTab(aba2);
		actionBar.addTab(aba3);
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction arg1) {
		// pager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(Tab arg0, FragmentTransaction arg1) {
	}

	@Override
	public void onTabSelected(Tab arg0, FragmentTransaction arg1) {
		// responsavel por muda a pagina, ao clicar nas abas
		pager.setCurrentItem(arg0.getPosition());
	}

	@Override
	public void eventoFoiClicado(Evento evento) {
		if (isTablet()) {
			DetalheFragment d = DetalheFragment.novaInstancia(evento);

			getSupportFragmentManager().beginTransaction()
					.replace(R.id.detail, d).commit();
		} else {
			Intent it = new Intent(this, DetalheActivity.class);
			it.putExtra("evento", evento);
			startActivity(it);
		}
	}

	private boolean isTablet() {
		return findViewById(R.id.detail) != null;
	}

	@Override
	public void eventoAdicionadoAoFavorito(Evento restaurante) {
		fragment2.refreshList();
	}

	class MeuAdapter extends FragmentPagerAdapter {

		public MeuAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			if (position == 0) {
				return fragment1;
			}
			return fragment2;
		}

		@Override
		public int getCount() {
			return 2;
		}
	}

}
