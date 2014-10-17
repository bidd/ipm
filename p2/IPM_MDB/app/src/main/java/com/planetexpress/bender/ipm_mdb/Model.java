package com.planetexpress.bender.ipm_mdb;

/**
 * Created by bender on 16/10/14.
 */

import android.app.Activity;
import android.content.Intent;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;


public class Model extends Activity{

    public static String TAG; //= IPMAccountManagerDemoTest.class.getSimpleName();

    Intent intent = getIntent();
    final String accountName = intent.getStringExtra(GlobalNames.ARG_ACCOUNT_NAME);
    final String authToken = intent.getStringExtra(GlobalNames.ARG_AUTH_TOKEN);


    //pending:
    //      get movies by page
    private class listMovies extends AsyncTask<Void, Void, JSONObject>{
        @Override
        protected JSONObject doInBackground(Void... voids){
            String url;
            if (accountName.endsWith(GlobalNames.ACCOUNT_NAME_DEVELOPMENT_SERVER_SUFFIX)) {
                url = "http://10.0.2.2:5000/session";
                Log.d(TAG, "authToken= " + authToken);
            } else {
                url = "http://ipm-movie-database.herokuapp.com/movies";
            }

            AndroidHttpClient client = AndroidHttpClient.newInstance(GlobalNames.HTTP_USER_AGENT);
            HttpGet request = new HttpGet(url);
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
    }

    private class searchMovie extends AsyncTask<String, Void, JSONObject>{
        @Override
        protected JSONObject doInBackground(String... title){
            StringBuilder url = new StringBuilder(100);
            if (accountName.endsWith(GlobalNames.ACCOUNT_NAME_DEVELOPMENT_SERVER_SUFFIX)) {
                url = url.append("http://10.0.2.2:5000/session");
                Log.d(TAG, "authToken= " + authToken);
            } else {
                url = url.append("http://ipm-movie-database.herokuapp.com/movies?q=").append(title);
            }

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
    }

    private class detailMovie extends AsyncTask<Integer, Void, JSONObject>{
        @Override
        protected JSONObject doInBackground(Integer... id){
            StringBuilder url = new StringBuilder(100);
            if (accountName.endsWith(GlobalNames.ACCOUNT_NAME_DEVELOPMENT_SERVER_SUFFIX)) {
                url = url.append("http://10.0.2.2:5000/session");
                Log.d(TAG, "authToken= " + authToken);
            } else {
                url = url.append("http://ipm-movie-database.herokuapp.com/movies/").append(id.toString());
            }

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
    }

    private class isFav extends AsyncTask<Integer, Void, JSONObject>{
        @Override
        protected JSONObject doInBackground(Integer... id){
            StringBuilder url = new StringBuilder(100);
            if (accountName.endsWith(GlobalNames.ACCOUNT_NAME_DEVELOPMENT_SERVER_SUFFIX)) {
                url = url.append("http://10.0.2.2:5000/session");
                Log.d(TAG, "authToken= " + authToken);
            } else {
                url = url.append("http://ipm-movie-database.herokuapp.com/movies/")
                        .append(id.toString())
                        .append("/fav");
            }

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
    }

    private class favMovie extends AsyncTask<Integer, Void, JSONObject>{
        @Override
        protected JSONObject doInBackground(Integer... id){
            StringBuilder url = new StringBuilder(100);
            if (accountName.endsWith(GlobalNames.ACCOUNT_NAME_DEVELOPMENT_SERVER_SUFFIX)) {
                url = url.append("http://10.0.2.2:5000/session");
                Log.d(TAG, "authToken= " + authToken);
            } else {
                url = url.append("http://ipm-movie-database.herokuapp.com/movies/")
                        .append(id.toString())
                        .append("/fav");
            }

            AndroidHttpClient client = AndroidHttpClient.newInstance(GlobalNames.HTTP_USER_AGENT);
            HttpPost request = new HttpPost(url.toString());
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
    }

    private class unfavMovie extends AsyncTask<Integer, Void, JSONObject>{
        @Override
        protected JSONObject doInBackground(Integer... id){
            StringBuilder url = new StringBuilder(100);
            if (accountName.endsWith(GlobalNames.ACCOUNT_NAME_DEVELOPMENT_SERVER_SUFFIX)) {
                url = url.append("http://10.0.2.2:5000/session");
                Log.d(TAG, "authToken= " + authToken);
            } else {
                url = url.append("http://ipm-movie-database.herokuapp.com/movies/")
                        .append(id.toString())
                        .append("/fav");
            }

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
    }

    //pending:
    //      get comments by page
    private class comments extends AsyncTask<Integer, Void, JSONObject>{
        @Override
        protected JSONObject doInBackground(Integer... id){
            StringBuilder url = new StringBuilder(100);
            if (accountName.endsWith(GlobalNames.ACCOUNT_NAME_DEVELOPMENT_SERVER_SUFFIX)) {
                url = url.append("http://10.0.2.2:5000/session");
                Log.d(TAG, "authToken= " + authToken);
            } else {
                url = url.append("http://ipm-movie-database.herokuapp.com/movies/")
                        .append(id.toString())
                        .append("/comments");
            }

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
    }

    private class addComment extends AsyncTask<Object, Void, JSONObject>{
        @Override
        protected JSONObject doInBackground(Object... params){
            int id = (Integer) params[0];
            String comment = (String) params[1];

            StringBuilder url = new StringBuilder(100);
            if (accountName.endsWith(GlobalNames.ACCOUNT_NAME_DEVELOPMENT_SERVER_SUFFIX)) {
                url = url.append("http://10.0.2.2:5000/session");
                Log.d(TAG, "authToken= " + authToken);
            } else {
                url = url.append("http://ipm-movie-database.herokuapp.com/movies/")
                        .append(Integer.toString(id))
                        .append("/comments");
            }

            AndroidHttpClient client = AndroidHttpClient.newInstance(GlobalNames.HTTP_USER_AGENT);
            HttpPost request = new HttpPost(url.toString());
            HttpResponse response = null;
            JSONObject responseObject = null;

            request.addHeader("Data", comment);
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
    }

    private class deleteComment extends AsyncTask<Object, Void, JSONObject>{
        @Override
        protected JSONObject doInBackground(Object... params){
            int idMovie = (Integer) params[0];
            int idComment = (Integer) params[1];

            StringBuilder url = new StringBuilder(100);
            if (accountName.endsWith(GlobalNames.ACCOUNT_NAME_DEVELOPMENT_SERVER_SUFFIX)) {
                url = url.append("http://10.0.2.2:5000/session");
                Log.d(TAG, "authToken= " + authToken);
            } else {
                url = url.append("http://ipm-movie-database.herokuapp.com/movies/")
                        .append(Integer.toString(idMovie))
                        .append("/comments")
                        .append(Integer.toString(idComment));
            }

            AndroidHttpClient client = AndroidHttpClient.newInstance(GlobalNames.HTTP_USER_AGENT);
            HttpPost request = new HttpPost(url.toString());
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
    }




}
