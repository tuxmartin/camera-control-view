package eu.vancl.martin.cameracontrol_view;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;


public class FullscreenVideo extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_fullscreen_video);

        // necha zapnuty display http://stackoverflow.com/a/3723649/1974494
        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "My Tag");
        wl.acquire();
        //wl.release();

        Intent k = getIntent();
        String url = k.getStringExtra("url");
        Log.i("FullscreenVideo", "URL = " + url);

        int camera = k.getIntExtra("camera", -1);
        String title = "Camera  " + camera;
        TextView txtTitle = (TextView) findViewById(R.id.textView);
        txtTitle.setText(title);
        Log.i("FullscreenVideo", "cameraID = " + camera);
        //Toast.makeText(getApplicationContext(), title, Toast.LENGTH_LONG);

        VideoView videoView  = (VideoView) findViewById(R.id.videoViewFA);



        Uri videoUri = Uri.parse(url);
        videoView.setVideoURI(videoUri);

        MediaController videoControll = new MediaController(this);

        videoControll.setAnchorView(videoView);
        videoView.start();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_fullscreen_video, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
