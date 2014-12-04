package com.planetexpress.bender.youremote;

import android.content.ClipData;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import java.util.List;

/**
 * Created by bender on 28/11/14.
 */
public class AdapterVideos extends ArrayAdapter<Video> {

    public AdapterVideos(Context context, List<Video> video) {
        super(context, 0, video);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Video video = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_video, parent, false);
        }
        // Lookup view for data population
        TextView tvTitle = (TextView) convertView.findViewById(R.id.video_name);
        // Populate the data into the template view using the data object

        tvTitle.setText(video.get_name());
        // Return the completed view to render on screen
        return convertView;
    }
}
