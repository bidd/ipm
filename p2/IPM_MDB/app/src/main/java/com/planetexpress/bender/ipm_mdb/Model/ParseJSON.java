package com.planetexpress.bender.ipm_mdb.Model;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by bender on 21/10/14.
 */
public class ParseJSON {

    protected ArrayList<Movie> parseJSONArrayMovies(JSONObject json) throws Exception {
        ArrayList<Movie> movies = new ArrayList<Movie>();
        String result = json.getString("result");
        if (result.contentEquals("success")) {
            JSONArray data = json.getJSONArray("data");
            for (int i = 0; i < data.length(); i++) {
                JSONObject object = data.getJSONObject(i);

                movies.add(i, new Movie(
                        object.getInt("id"),
                        object.getString("title"),
                        object.getInt("year"),
                        object.getString("url_image")));


                Log.d("title", object.getString("title"));
            }
        }
        return movies;
    }

    protected Movie parseJSONMovie(JSONObject json) throws Exception {
        Movie movie;

        JSONObject object = json.getJSONObject("data");
        movie = new Movie(
                object.getInt("id"),
                object.getString("title"),
                object.getInt("year"),
                object.getString("url_image"),
                object.getString("synopsis"),
                object.getString("category"));

        return movie;
    }

    protected Boolean parseJSONFav(JSONObject json) throws Exception {
        return json.getString("data").contentEquals("true");
    }

    protected ArrayList<Comment> parseJSONArrayComments(JSONObject json) throws Exception {
        ArrayList<Comment> comments = new ArrayList<Comment>();

        JSONArray data = json.getJSONArray("data");

        for (int i = 0; i < data.length(); i++) {
            JSONObject object = data.getJSONObject(i);

            comments.add(i, new Comment(
                    object.getInt("id"),
                    object.getString("content"),
                    object.getString("comment_date"),
                    object.getString("username"),
                    object.getString("email")));
        }

        return comments;
    }

    protected Boolean parseJSONDeleteComment(JSONObject json) throws Exception{
        String result = json.getString("result");
        return result.contentEquals("success");
    }
}
