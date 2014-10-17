package com.planetexpress.bender.ipm_mdb;

import java.net.URL;

/**
 * Created by bender on 17/10/14.
 */
public class Movie {

    private Integer _id;
    private String _title;
    private Integer _year;
    private URL _urlimage;
    private String _synopsis;
    private String _category;

    public Movie(Integer id, String title, Integer year){
        _id = id;
        _title = title;
        _year = year;
    }

    public Movie(Integer id, String title, Integer year, URL urlimage, String synopsis, String category){
        _id = id;
        _title = title;
        _year = year;
        _urlimage = urlimage;
        _synopsis = synopsis;

        _category = category;
    }

    public Integer get_id() {
        return _id;
    }

    protected void set_id(Integer _id) {
        this._id = _id;
    }

    public String get_title() {
        return _title;
    }

    protected void set_title(String _title) {
        this._title = _title;
    }

    public Integer get_year() {
        return _year;
    }

    protected void set_year(Integer _year) {
        this._year = _year;
    }

    public URL get_urlimage() {
        return _urlimage;
    }

    protected void set_urlimage(URL _urlimage) {
        this._urlimage = _urlimage;
    }

    public String get_synopsis() {
        return _synopsis;
    }

    protected void set_synopsis(String _synopsis) {
        this._synopsis = _synopsis;
    }

    public String get_category() {
        return _category;
    }

    protected void set_category(String _category) {
        this._category = _category;
    }

}
