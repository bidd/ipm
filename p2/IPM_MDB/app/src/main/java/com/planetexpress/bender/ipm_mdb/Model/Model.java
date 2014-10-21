package com.planetexpress.bender.ipm_mdb.Model;

/**
 * Created by bender on 16/10/14.
 */

import android.app.Activity;
import android.content.Entity;
import android.content.Intent;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.util.Log;

import com.planetexpress.bender.ipm_mdb.GlobalNames;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;


public class Model{

    //private Model _model = null;
    private static String _url;
    private ParseJSON parseJSON = new ParseJSON();

    //private String accountName;
    private String authToken;

    public Model(String accountName, String authToken) {
        if (accountName.endsWith(GlobalNames.ACCOUNT_NAME_DEVELOPMENT_SERVER_SUFFIX)) {
            _url = "http://10.0.2.2:5000/session";
        } else {
            _url = "http://ipm-movie-database.herokuapp.com/";
        }
        this.authToken = authToken;
    }
    /*
    public Model getModel() {
        if (_model == null) {
            _model = new Model();
        }
        return _model;
    }
*/
    private JSONObject executeGetQuery(String url){
        AndroidHttpClient client = AndroidHttpClient.newInstance(GlobalNames.HTTP_USER_AGENT);
        HttpGet request = new HttpGet(url.toString());
        HttpResponse response = null;
        JSONObject responseObject = null;

        request.addHeader("Cookie", authToken);
        try {
            response = client.execute(request);
            responseObject = new JSONObject(EntityUtils.toString(response.getEntity()));
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return responseObject;
    }

    private JSONObject executeDeleteQuery(String url){
        AndroidHttpClient client = AndroidHttpClient.newInstance(GlobalNames.HTTP_USER_AGENT);
        HttpDelete request = new HttpDelete(url.toString());
        HttpResponse response = null;
        JSONObject responseObject = null;

        request.addHeader("Cookie", authToken);

        try {
            response = client.execute(request);
            responseObject = new JSONObject(EntityUtils.toString(response.getEntity()));
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return responseObject;
    }

    private JSONObject executePostQuery(String url, String data){
        AndroidHttpClient client = AndroidHttpClient.newInstance(GlobalNames.HTTP_USER_AGENT);
        HttpPost request = new HttpPost(url.toString());
        HttpResponse response = null;
        JSONObject responseObject = null;

        if (data != null){
            request.addHeader("Data", data);
        }
        request.addHeader("Cookie", authToken);

        try {
            response = client.execute(request);
            responseObject = new JSONObject(EntityUtils.toString(response.getEntity()));
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return responseObject;
    }

    protected ArrayList<Movie> parseJSONArrayMovies(JSONObject json) throws Exception{
        ArrayList<Movie> movies = new ArrayList<Movie>();

        JSONArray data = json.getJSONArray("data");
        for (int i = 0; i < data.length(); i++){
            JSONObject object = data.getJSONObject(i);

            movies.add(i, new Movie(
                    object.getInt("id"),
                    object.getString("title"),
                    object.getInt("year"),
                    object.getString("url_image")));

            Log.d("title", object.getString("title"));
        }
        return movies;
    }

    public ArrayList<Movie> listMovies() {
        StringBuilder url = new StringBuilder(40);
        url.append(_url).append("movies");

        JSONObject json =  executeGetQuery(url.toString());
        ArrayList<Movie> movies = null;
        try {
            movies = parseJSONArrayMovies(json);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return movies;
    }

    public ArrayList<Movie> searchMovie(String word){
        StringBuilder url = new StringBuilder(100);
        url.append(_url).append("movies?q=").append(word);
        ArrayList<Movie> movies = null;
        JSONObject json = null;

        json = executeGetQuery(url.toString());
        try {
            movies = parseJSON.parseJSONArrayMovies(json);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return movies;
    }

    public Movie detailMovie(String word){
        StringBuilder url = new StringBuilder(100);
        url.append(_url).append("movies?q=").append(word);

        executeGetQuery(url.toString());

        return null;
    }

    public Boolean isFav(Integer id){
        StringBuilder url = new StringBuilder(100);
        url.append(_url).
                append("movies/").
                append(id.toString()).
                append("/fav");

        executeGetQuery(url.toString());

        return null;
    }

    public Void favMovie(Integer id){
        StringBuilder url = new StringBuilder(100);
        url.append(_url).
                append("movies/").
                append(id.toString()).
                append("/fav");

        executePostQuery(url.toString(), null);

        return null;
    }

    public Void unfavMovie(Integer id){
        StringBuilder url = new StringBuilder(100);
        url.append(_url).
                append("movies/").
                append(id.toString()).
                append("/fav");

        executeDeleteQuery(url.toString());

        return null;
    }

    public ArrayList<Comment> getComments(Integer id){
        StringBuilder url = new StringBuilder(100);
        url.append(_url).
                append("movies/").
                append(id.toString()).
                append("/comments");

        executeGetQuery(url.toString());

        return null;
    }

    public Void addComment(Integer id, String comment){
        StringBuilder url = new StringBuilder(100);
        url.append(_url).
                append("movies/").
                append(id.toString()).
                append("/comments");

        executePostQuery(url.toString(), comment);

        return null;
    }

    public Void deleteComment(Integer idMovie, Integer idComment){
        StringBuilder url = new StringBuilder(100);
        url.append(_url).
                append("movies/").
                append(idMovie.toString()).
                append("/comments")
                .append(idComment);


        executeDeleteQuery(url.toString());

        return null;
    }

}