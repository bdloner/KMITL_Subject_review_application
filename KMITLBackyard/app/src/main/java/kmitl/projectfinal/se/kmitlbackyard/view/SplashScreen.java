package kmitl.projectfinal.se.kmitlbackyard.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

import kmitl.projectfinal.se.kmitlbackyard.R;
import kmitl.projectfinal.se.kmitlbackyard.activity.LoginActivity;

public class SplashScreen extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash_screen);

        Thread screenThead = new Thread() {
            @Override
            public void run() {
                try {
                    sleep(2000);
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        screenThead.start();
    }
}
