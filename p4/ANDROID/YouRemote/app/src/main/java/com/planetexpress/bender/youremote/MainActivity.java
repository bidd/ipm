package com.planetexpress.bender.youremote;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static com.planetexpress.bender.youremote.R.id.play_button;


/*TO DO:
    Almacenar id de videos en una lista
    Cargar esa lista en el listView
    Dialogo de añadir video (al pulsar el boton)
    Añadir imagenes a los botones. De izquierda a derecha: play, pause, stop, add video
KNOWN BUGS:
    Funciona en emulador pero no en telefono. La maquina virtual abre el puerto pero el operativo no.
    La IP de la maquina se debe escribir en tiempo de compilacion.
*/



public class MainActivity extends Activity {

    ListView listView;
    Model model = new Model();
    private AdapterVideos adapter;
    private List<Video> videoList = new ArrayList<Video>();
    private String m_Text = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adapter = new AdapterVideos(this, videoList);

        listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapter);

        //Leer lista de videos (de donde? quien sabe)

        AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String ids = videoList.get(position).get_id();

                new VideoById().execute(ids);
            }
        };

        AdapterView.OnItemLongClickListener itemLongClickListener = new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                onDeleteVideo(position);
                //Ventana de dialogo que pregunte si se quiere borrar el video de la lista

                return false;
            }
        };

        listView.setOnItemClickListener(itemClickListener);
        listView.setOnItemLongClickListener(itemLongClickListener);

        ImageButton play_button= (ImageButton) findViewById(R.id.play_button);
        play_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new PlayVideo().execute();
            }
        });

        ImageButton pause_button= (ImageButton) findViewById(R.id.pause_button);
        pause_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new PauseVideo().execute();
            }
        });

        ImageButton stop_button= (ImageButton) findViewById(R.id.stop_button);
        stop_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new StopVideo().execute();
            }
        });

        ImageButton add_button= (ImageButton) findViewById(R.id.add_button);
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onAddVideo();
            }
        });
    }

    public class PlayVideo extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            model.Play();
            return null;
        }
    }

    public class PauseVideo extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            model.Pause();
            return null;
        }
    }

    public class StopVideo extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            model.Stop();
            return null;
        }
    }

    public class VideoById extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            model.VideoById(params[0]);
            return null;
        }
    }

    public class TitleById extends AsyncTask<String, Void, List<String>> {
        @Override
        protected List<String> doInBackground(String... params) {
            String title = model.getVideoTitle(params[0]);
            List<String> strings= new ArrayList<String>();
            strings.add(title);
            strings.add(params[0]);
            return strings;
        }

        @Override
        protected void onPostExecute(List<String> params) {
            Log.d("PRUEBA", params.get(0));
            Video video = new Video(params.get(0), params.get(1));
            videoList.add(video);
            adapter.notifyDataSetChanged();
        }
    }

    public void onAddVideo(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Title");

        // Set up the input
        final EditText input = new EditText(this);
        // Specify the type of input expected
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                new TitleById().execute(input.getText().toString());

            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    public void onDeleteVideo(final Integer position){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        // set dialog message
        alertDialogBuilder
                .setMessage("Delete video?")
                .setCancelable(false)
                .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // if this button is clicked, close
                        // current activity
                        videoList.remove(videoList.get(position));          //Esto es culpa de android, no funciona videoList.remove(position)
                        adapter.notifyDataSetChanged();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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
