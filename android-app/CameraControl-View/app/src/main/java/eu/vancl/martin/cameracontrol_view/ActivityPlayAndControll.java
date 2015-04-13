package eu.vancl.martin.cameracontrol_view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.MediaController;
import android.widget.ToggleButton;
import android.widget.VideoView;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;


public class ActivityPlayAndControll extends ActionBarActivity implements  OnClickListener, OnTouchListener {

    // http://www.wowza.com/html/mobile.html
    String videoAddress[] = {
            "rtsp://wowzaec2demo.streamlock.net/vod/mp4:BigBuckBunny_115k.mov",
            "rtsp://wowzaec2demo.streamlock.net/vod/mp4:BigBuckBunny_115k.mov",
            "rtsp://wowzaec2demo.streamlock.net/vod/mp4:BigBuckBunny_115k.mov",
            "rtsp://wowzaec2demo.streamlock.net/vod/mp4:BigBuckBunny_115k.mov"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_activity_play_and_controll);

        // necha zapnuty display http://stackoverflow.com/a/3723649/1974494
        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "My Tag");
        wl.acquire();
        //wl.release();

        final Button btnOpen = (Button) findViewById(R.id.btnOpen);
        btnOpen.setBackgroundColor(Color.RED);
        btnOpen.setText("CLOSE");
        btnOpen.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                List<NameValuePair> pairs = new ArrayList<NameValuePair>(2);
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: // Start
                        btnOpen.setBackgroundColor(Color.GREEN);
                        Log.i("ActivityPlayAndControll", "DOOR OPEN");
                        btnOpen.setText("OPEN");

                        pairs.add(new BasicNameValuePair("door", "open"));
                        makeHttpPost("http://192.168.1.11:8000/", 200, pairs);

                        break;
                    case MotionEvent.ACTION_UP: // Stop
                        btnOpen.setBackgroundColor(Color.RED);
                        Log.i("ActivityPlayAndControll", "DOOR CLOSE");
                        btnOpen.setText("CLOSE");

                        pairs.add(new BasicNameValuePair("door", "close"));
                        makeHttpPost("http://192.168.1.11:8000/", 200, pairs);

                        break;
                }
                return false;
            }
        });

        final ToggleButton tglBtnLight = (ToggleButton) findViewById(R.id.tglBtnLight);
        tglBtnLight.setBackgroundColor(Color.RED);
        tglBtnLight.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                List<NameValuePair> pairs = new ArrayList<NameValuePair>(2);
                if (isChecked) {
                    tglBtnLight.setBackgroundColor(Color.GREEN);
                    Log.i("ActivityPlayAndControll", "LIGHT ON");

                    pairs.add(new BasicNameValuePair("light", "on"));
                    makeHttpPost("http://192.168.1.11:8000/", 200, pairs);
                } else {
                    tglBtnLight.setBackgroundColor(Color.RED);
                    Log.i("ActivityPlayAndControll", "LIGHT OFF");

                    pairs.add(new BasicNameValuePair("light", "off"));
                    makeHttpPost("http://192.168.1.11:8000/", 200, pairs);
                }
            }
        });

        Uri videoUri[] = new Uri[4];
        for (int i=0; i<4; i++) {
            videoUri[i] = Uri.parse(videoAddress[i]);
        }

        VideoView videoView1  = (VideoView) findViewById(R.id.videoView1);
        VideoView videoView2  = (VideoView) findViewById(R.id.videoView2);
        VideoView videoView3  = (VideoView) findViewById(R.id.videoView3);
        VideoView videoView4  = (VideoView) findViewById(R.id.videoView4);

        videoView1.setVideoURI(videoUri[0]);
        videoView2.setVideoURI(videoUri[1]);
        videoView3.setVideoURI(videoUri[2]);
        videoView4.setVideoURI(videoUri[3]);

        MediaController videoControll = new MediaController(this);

        videoControll.setAnchorView(videoView1);
        videoControll.setAnchorView(videoView2);
        videoControll.setAnchorView(videoView3);
        videoControll.setAnchorView(videoView4);

        // ovladaci prvky videa (play/pause/next,...)
        //videoView1.setMediaController(videoControll);
        //videoView2.setMediaController(videoControll);
        //videoView3.setMediaController(videoControll);
        //videoView4.setMediaController(videoControll);

        videoView1.start();
        videoView2.start();
        videoView3.start();
        videoView4.start();

        /*
        RelativeLayout relativeLayout1 = (RelativeLayout) findViewById(R.id.relativeLayout1);
        RelativeLayout relativeLayout2 = (RelativeLayout) findViewById(R.id.relativeLayout2);
        RelativeLayout relativeLayout3 = (RelativeLayout) findViewById(R.id.relativeLayout3);
        RelativeLayout relativeLayout4 = (RelativeLayout) findViewById(R.id.relativeLayout4);
        int width = 100;
        int height = 100;
        LayoutParams rlParams = new LayoutParams(width, height);
        //rlParams.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
        //rlParams.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
        relativeLayout1.setLayoutParams(rlParams);
        relativeLayout2.setLayoutParams(rlParams);
        relativeLayout3.setLayoutParams(rlParams);
        relativeLayout4.setLayoutParams(rlParams);
        videoView1.invalidate(); // redraw using new params
        videoView2.invalidate();
        videoView3.invalidate();
        videoView4.invalidate();
        */

        videoView1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.i("ActivityPlayAndControll", "videoView1 touch");
                return false;
            }
        });
        videoView1.setOnTouchListener(this);
        videoView2.setOnTouchListener(this);
        videoView3.setOnTouchListener(this);
        videoView4.setOnTouchListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_play_and_controll, menu);
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

    public boolean makeHttpPost(String url, int requstedResponseStatusCode, List<NameValuePair> pairs) {
        if (android.os.Build.VERSION.SDK_INT > 9) { // FIXME: je to prasarna, prepsat na AsyncTask!
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        // http://www.wikihow.com/Execute-HTTP-POST-Requests-in-Android
        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url);

        //List<NameValuePair> pairs = new ArrayList<NameValuePair>();
        //pairs.add(new BasicNameValuePair("key1", "value1"));
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(pairs));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        try {
            HttpResponse httpResponse = httpClient.execute(httpPost);
            if ( httpResponse.getStatusLine().getStatusCode() == requstedResponseStatusCode ) {
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    public void onUserFingerAction(View v) {
        Intent k = new Intent(this, FullscreenVideo.class);

        String log = "Uzivatel tapnul na UI prvek id=" + v.getId();
        Log.i("ActivityPlayAndControll", log);

        int cameraId = -1;
        switch (v.getId()) {
            case R.id.videoView1:
                cameraId = 1;
                break;
            case R.id.videoView2:
                cameraId = 2;
                break;
            case R.id.videoView3:
                cameraId = 3;
                break;
            case R.id.videoView4:
                cameraId = 4;
                break;
            default:
                cameraId = -1;
                break;
        }
        k.putExtra("camera", cameraId);

        k.putExtra("url", videoAddress[cameraId-1]);
        startActivity(k);
    }

    @Override
    public void onClick(View v) {
        onUserFingerAction(v);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        onUserFingerAction(v);
        return false;
    }
}
