package com.planetexpress.bender.ipm_mdb;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.planetexpress.bender.ipm_mdb.Model.Movie;

import java.util.List;

public class AdapterMovies<M> extends ArrayAdapter<Movie> {

    Context context;

    public AdapterMovies(Context context, int resourceId,
                                 List<Movie> items) {
        super(context, resourceId, items);
        this.context = context;
    }

    /*private view holder class*/
    private class ViewHolder {
        ImageView imageView;
        TextView txtAno;
        TextView txtTitle;
        TextView txtId;

    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        //Posicion da pelicula
        Movie movie = getItem(position);

        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.row_listview, null);

            holder = new ViewHolder();

            holder.txtTitle = (TextView) convertView.findViewById(R.id.texto);
            holder.txtAno = (TextView)  convertView.findViewById(R.id.anho);
            holder.imageView = (ImageView) convertView.findViewById(R.id.imageMovie);
            holder.txtId = (TextView) convertView.findViewById(R.id.id);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.txtTitle.setText(movie.get_title());
        holder.txtAno.setText(movie.get_year().toString());
        holder.txtId.setText(movie.get_id().toString());
        holder.imageView.setImageResource(R.drawable.ic_launcher);

        return convertView;
    }
}