package com.planetexpress.bender.ipm_mdb.Model;

import android.content.Intent;

import com.planetexpress.bender.ipm_mdb.GlobalNames;

import java.util.Calendar;

/**
 * Created by bender on 20/10/14.
 */
public class Comment {

    private Integer _id;
    private String _content;
    private String _commentDate;
    private String _username;
    private String _email;


    public Comment(String content){
        _content = content;

        Calendar c = Calendar.getInstance();
        StringBuilder s = new StringBuilder(100);
        s.append(c.get(Calendar.DAY_OF_MONTH)).append("/").
                append(c.get(Calendar.MONTH)).append("/").
                append(c.get(Calendar.YEAR));
        _commentDate = s.toString();
        _username = null; //Se consigue haciendo una peticion de la sesion
        _email = null; //= email;
    }

    public Comment(Integer id, String content, String commentDate, String username, String email){
        _id = id;
        _content = content;
        _commentDate = commentDate;
        _username = username;
        _email = email;
    }

    public String get_content() {
        return _content;
    }

    public String get_commentDate() {
        return _commentDate;
    }

    public String get_username() {
        return _username;
    }

    public Integer get_id() {
        return _id;
    }
}
