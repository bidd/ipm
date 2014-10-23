package com.planetexpress.bender.ipm_mdb;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.planetexpress.bender.ipm_mdb.Model.Model;
import com.planetexpress.bender.ipm_mdb.Model.Movie;

import java.util.ArrayList;


public class MainWindow extends Activity {

    private ListView listView;
    public Model model;
    public Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_window);

        intent=getIntent();

        listView = (ListView) findViewById(R.id.moviesView);


        Intent intent = getIntent();
        final String accountName = intent.getStringExtra(GlobalNames.ARG_ACCOUNT_NAME);
        final String authToken = intent.getStringExtra(GlobalNames.ARG_AUTH_TOKEN);

        model = new Model(accountName, authToken);
        ListMovies listMovies = new ListMovies();
        listMovies.execute();

        SearchView searchView = (SearchView) findViewById(R.id.searchView);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                SearchMovie searchMovie = new SearchMovie();
                searchMovie.execute(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                LinearLayout linearLayoutParent = (LinearLayout) view;
                TextView title = (TextView) linearLayoutParent.getChildAt(2);
                TextView id_tv = (TextView) linearLayoutParent.getChildAt(3);
                String id = id_tv.getText().toString();

                Intent myIntent = new Intent(MainWindow.this,MovieDetail.class);
                myIntent.putExtra("id",id);
                myIntent.putExtra("accountName",accountName);
                myIntent.putExtra("authToken",authToken);
                startActivity(myIntent);
            }
        };

        listView.setOnItemClickListener(itemClickListener);


    }

    public class ListMovies extends AsyncTask<Void, Void, ArrayList<Movie>>{
        @Override
        protected ArrayList<Movie> doInBackground(Void... params) {
            return model.listMovies();
        }
        @Override
        protected void onPostExecute(ArrayList<Movie> movies){

            AdapterMovies<Movie> adapter = new AdapterMovies<Movie>(MainWindow.this,R.layout.row_listview,movies);
            listView.setAdapter(adapter);      //  <-------      nullpointer
        }
    }

    public class SearchMovie extends AsyncTask<String, Void, ArrayList<Movie>>{

        @Override
        protected ArrayList<Movie> doInBackground(String...title) {
            return model.searchMovie(title[0]);
        }
        @Override
        protected void onPostExecute(ArrayList<Movie> movies){
            ListView listView = (ListView) findViewById(R.id.moviesView);
            AdapterMovies<Movie> adapter = new AdapterMovies<Movie>(MainWindow.this,R.layout.row_listview,movies);
            listView.setAdapter(adapter);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_window, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
