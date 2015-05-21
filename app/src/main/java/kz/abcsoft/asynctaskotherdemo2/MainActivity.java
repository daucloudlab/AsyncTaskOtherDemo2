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

    ProgressBar progress;
    ImageView[] targetImage = new ImageView[3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        targetImage[0] = (ImageView) findViewById(R.id.target0);
        targetImage[1] = (ImageView) findViewById(R.id.target1);
        targetImage[2] = (ImageView) findViewById(R.id.target2);

        progress = (ProgressBar) findViewById(R.id.progress);

        // Загружаем картинки из интернета
        String urlImage0 = "http://developer.alexanderklimov.ru/android/images/pinkhellokitty.jpg";
        String urlImage1 = "http://developer.alexanderklimov.ru/android/images/keyboard-cat.jpg";
        String urlImage2 = "http://developer.alexanderklimov.ru/android/images/cat-tips.jpg";

        URL myURL0, myURL1, myURL2;

        try{
            myURL0 = new URL(urlImage0);
            myURL1 = new URL(urlImage1);
            myURL2 = new URL(urlImage2);

            new MyAsyncTask(3, targetImage, progress).execute(myURL0, myURL1,myURL2);

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

    private class MyAsyncTask extends AsyncTask<URL, Integer, Void> {

        ImageView[] aIV;
        Bitmap[] aBM;
        ProgressBar progressBar;

        public MyAsyncTask(int numberOfImage, ImageView[] iv, ProgressBar pb) {
            aBM = new Bitmap[numberOfImage];

            aIV = new ImageView[numberOfImage];
            for (int i = 0; i < numberOfImage; i++) {
                aIV[i] = iv[i];
            }

            progressBar = pb;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(URL... urls) {
            if (urls.length > 0) {
                for (int i = 0; i < urls.length; i++) {
                    URL networkUrl = urls[i];

                    try {
                        aBM[i] = BitmapFactory.decodeStream(networkUrl
                                .openConnection().getInputStream());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    publishProgress(i);

                    // делаем искусственую задержку (необязательно)
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            if (values.length > 0) {
                for (int i = 0; i < values.length; i++) {
                    aIV[values[i]].setImageBitmap(aBM[values[i]]);
                    progressBar.setProgress(values[i] * 50);
                }
            }
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            Toast.makeText(getBaseContext(), "Загрузка завершена",
                    Toast.LENGTH_LONG).show();
        }
    }
}
