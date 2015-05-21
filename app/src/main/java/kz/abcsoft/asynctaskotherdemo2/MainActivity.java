package kz.abcsoft.asynctaskotherdemo2;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;


public class MainActivity extends ActionBarActivity {

    ImageView logoImage ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        logoImage = (ImageView) findViewById(R.id.ivFromSite);

        String urlImage = "http://developer.alexanderklimov.ru/android/images/android_cat.jpg";
        URL myUrl ;

        try{
            myUrl = new URL(urlImage) ;
            new MyAsyncTask(logoImage).execute(myUrl) ;
        } catch(MalformedURLException e){
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    private class MyAsyncTask extends AsyncTask<URL, Void, Bitmap> {

        ImageView ivLogo;

        public MyAsyncTask(ImageView iv) {
            ivLogo = iv;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Bitmap doInBackground(URL... urls) {
            Bitmap networkBitmap = null;

            URL networkUrl = urls[0];

            try{
                networkBitmap = BitmapFactory.decodeStream(networkUrl.openConnection().getInputStream()) ;
            } catch (IOException e){
                e.printStackTrace();
            }


            return networkBitmap ;
        }

//        @Override
//        protected void onProgressUpdate(Integer... values) {
//            super.onProgressUpdate(values);
//
//            this.progress.setProgress(values[0]);
//        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);

            ivLogo.setImageBitmap(bitmap);
        }
    }
}
