package com.planetexpress.bender.youremote;

/**
 * Created by bender on 28/11/14.
 */
public class Video {

    private String _name;
    private String _id;

    public Video(String name, String id){
        _name = name;
        _id = id;
    }

    public String get_name() {
        return _name;
    }

    public void set_name(String _name) {
        this._name = _name;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    @Override
    public String toString() {
        return "Video{" +
                "_name='" + _name + '\'' +
                ", _id='" + _id + '\'' +
                '}';
    }
}
