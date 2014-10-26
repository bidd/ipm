package com.planetexpress.bender.ipm_mdb;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.planetexpress.bender.ipm_mdb.Model.Comment;
import com.planetexpress.bender.ipm_mdb.Model.Movie;

import java.util.List;

/**
 * Created by bender on 22/10/14.
 */
public class AdapterComments<C> extends ArrayAdapter<Comment> {

    Context context;

    public AdapterComments(Context context, int resourceId,
                         List<Comment> items) {
        super(context, resourceId, items);
        this.context = context;
    }

    /*private view holder class*/
    private class ViewHolder {
        TextView txtUsername;
        TextView txtDate;
        TextView txtContent;
        TextView txtId;

    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        Comment comment = getItem(position);

        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.row_comment, null);

            holder = new ViewHolder();

            holder.txtUsername = (TextView) convertView.findViewById(R.id.usernameText);
            holder.txtDate = (TextView)  convertView.findViewById(R.id.dateText);
            holder.txtContent = (TextView) convertView.findViewById(R.id.commentContent);
            holder.txtId = (TextView) convertView.findViewById(R.id.idText);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.txtUsername.setText(comment.get_username());
        holder.txtDate.setText(comment.get_commentDate());
        holder.txtContent.setText(comment.get_content());
        holder.txtId.setText(comment.get_id().toString());

        return convertView;
    }
}