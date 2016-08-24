package br.edu.unibratec.projetoeltondois.util;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import br.edu.unibratec.projetoeltondois.R;
import br.edu.unibratec.projetoeltondois.entity.Evento;

import com.squareup.picasso.Picasso;

public class EventoAdapter extends ArrayAdapter<Evento> {

	public EventoAdapter(Context context, List<Evento> eventos) {
		super(context, 0, eventos);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		if (convertView == null) {
			convertView = LayoutInflater.from(getContext()).inflate(
					R.layout.linha_evento, null);
		}
		Evento evento = getItem(position);

		ImageView imgFoto = (ImageView) convertView.findViewById(R.id.imgFoto);
		TextView txtNome = (TextView) convertView.findViewById(R.id.txtNome);
		TextView txtData = (TextView) convertView.findViewById(R.id.txtData);

		Picasso.with(getContext()).load(evento.getLogomarca()).noFade()
				.into(imgFoto);
		txtNome.setText(evento.getNome());
		txtData.setText(evento.getData());
		return convertView;

	}
}
