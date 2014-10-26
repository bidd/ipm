package com.planetexpress.bender.ipm_mdb;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.planetexpress.bender.ipm_mdb.Model.Comment;
import com.planetexpress.bender.ipm_mdb.Model.Model;
import com.planetexpress.bender.ipm_mdb.Model.Movie;

import java.util.ArrayList;


public class comments extends Activity {

    String id;
    String accountName;
    String authToken;
    Model model;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);

        final Intent intent= getIntent();
        id = intent.getStringExtra("id");
        accountName=intent.getStringExtra("accountName");
        authToken=intent.getStringExtra("authToken");

        model = new Model(accountName,authToken);

        listView = (ListView) findViewById(R.id.commentsView);

        GetComments getComments = new GetComments();
        getComments.execute(id);


        Button sendButton = (Button) findViewById(R.id.sendButton);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView commentContent = (TextView) findViewById(R.id.commentText);
                String content = commentContent.getText().toString();

                AddComment addComment = new AddComment();
                addComment.execute(id.toString(), content);
            }
        });

        Button backButtonC = (Button) findViewById(R.id.backButtonC);
        backButtonC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        AdapterView.OnItemLongClickListener itemLongClickListener = new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                LinearLayout linearLayoutParent = (LinearLayout) view;
                RelativeLayout relativeLayout = (RelativeLayout) linearLayoutParent.getChildAt(0);
                TextView id_tv = (TextView) relativeLayout.getChildAt(3);
                String commentID = id_tv.getText().toString();

                onDeleteComment(commentID);
                return true;
            }
        };

        ListView commentsListView = (ListView) findViewById(R.id.commentsView);
        commentsListView.setOnItemLongClickListener(itemLongClickListener);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.comments, menu);
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

    public class GetComments extends AsyncTask<String, Void, ArrayList<Comment>> {
        @Override
        protected ArrayList<Comment> doInBackground(String... strings) {
            if (strings[0].isEmpty()){
                return null;
            }
            return model.getComments(strings[0]);
        }
        @Override
        protected void onPostExecute(ArrayList<Comment> comments){
            if (comments==null){
               finish();
            }else {
                AdapterComments<Comment> adapter = new AdapterComments<Comment>(comments.this, R.layout.row_comment, comments);
                listView.setAdapter(adapter);
            }
        }
    }

    public class AddComment extends AsyncTask<String, Void, Void>{
        @Override
        protected Void doInBackground(String... params) {
            String id = params[0];
            String content = params[1];

            model.addComment(id, content);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            //hide keyboard
            GetComments getComments = new GetComments();
            getComments.execute(id);
        }
    }

    public class DeleteComment extends AsyncTask<String, Void, Boolean>{
        @Override
        protected Boolean doInBackground(String... params) {
            String idMovie = params[0];
            String idComment = params[1];

            Boolean deleted = model.deleteComment(idMovie, idComment);

            return deleted;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            GetComments getComments = new GetComments();
            getComments.execute(id);
            if (!aBoolean)
                commentNotDeleted();
        }
    }

    public void onDeleteComment(final String commentID){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        // set dialog message
        alertDialogBuilder
                .setMessage("Delete movie?")
                .setCancelable(false)
                .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int ids) {
                        // if this button is clicked, close
                        // current activity
                        DeleteComment deleteComment = new DeleteComment();
                        deleteComment.execute(id, commentID);



                        dialog.cancel();
                    }
                })
                .setNegativeButton("No",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // if this button is clicked, just close
                        // the dialog box and do nothing
                        dialog.cancel();
                    }
                });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }


    public void commentNotDeleted(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        // set dialog message
        alertDialogBuilder
                .setMessage("You can't delete this comment")
                .setCancelable(false)
                .setPositiveButton("Ok",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int ids) {
                        // if this button is clicked, close
                        // current activity
                        dialog.cancel();
                    }
                });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }
}
