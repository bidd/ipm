package com.planetexpress.bender.youremote;

import android.net.http.AndroidHttpClient;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by bender on 28/11/14.
 */
public class Model {

    private final String ip = "172.16.63.147";

    private HttpResponse executeGetQuery(String url){
        AndroidHttpClient client = AndroidHttpClient.newInstance("YouRemote-HttpClient/UNAVAILABLE");
        HttpGet request = new HttpGet(url.toString());
        HttpResponse response = null;
        JSONObject json = null;
        try {
            response = client.execute(request);
        } catch (IOException e) {
            e.printStackTrace();
        }
        client.close();
        return response;
    }

    public HttpResponse Play() {
        return executeGetQuery("http://" + ip + ":8888/msg/play");             //Configurar para usar la ip del equipo en el que esta corriendo el servicio
    }

    public HttpResponse Pause() {
        return executeGetQuery("http://" + ip + ":8888/msg/pause");             //Configurar para usar la ip del equipo en el que esta corriendo el servicio
    }

    public HttpResponse Stop() {
        return executeGetQuery("http://" + ip + ":8888/msg/stop");             //Configurar para usar la ip del equipo en el que esta corriendo el servicio
    }

    public HttpResponse VideoById(String id) {
        return executeGetQuery("http://" + ip + ":8888/msg/i="+id);
    }

    public String getVideoTitle(String id) {

        String url = "http://www.youtube.com/oembed?url=http://www.youtube.com/watch?v=" + id + "&format=json";
        AndroidHttpClient client = AndroidHttpClient.newInstance("YouRemote-HttpClient/UNAVAILABLE");
        HttpGet request = new HttpGet(url.toString());
        HttpResponse response = null;
        JSONObject json = null;
        try {
            response = client.execute(request);
            json =  new JSONObject(EntityUtils.toString(response.getEntity()));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        client.close();

        String title = null;
        try {
            if (response == null){
                title = "No consigo el titulo";
            } else {
                title = json .getString("title");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return title;
    }
}
