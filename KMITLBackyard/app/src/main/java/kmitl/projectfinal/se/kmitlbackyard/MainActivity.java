package kmitl.projectfinal.se.kmitlbackyard;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.valdesekamdem.library.mdtoast.MDToast;

public class MainActivity extends AppCompatActivity {
    private FirebaseUser firebaseAuth;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();

            switch (item.getItemId()) {
                case R.id.navigation_home:
                    HomeFragment fragmentHome = new HomeFragment();
                    transaction.replace(R.id.content, fragmentHome, "HomeFragment").commit();
                    return true;
                case R.id.navigation_news:
                    NewsFragment fragmentNews = new NewsFragment();
                    transaction.replace(R.id.content, fragmentNews, "NewsFragment").commit();
                    return true;
                case R.id.navigation_me:
                    MeFragment fragmentMe = new MeFragment();
                    transaction.replace(R.id.content, fragmentMe, "MeFragment").commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.content, new HomeFragment()).commit();

        firebaseAuth = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseAuth == null){
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            MDToast mdToast = MDToast.makeText(getApplicationContext(), "กรุณาล็อกอินก่อน", MDToast.LENGTH_SHORT, MDToast.TYPE_ERROR);
            mdToast.show();
            startActivity(intent);
            finish();
        }

    }
}
