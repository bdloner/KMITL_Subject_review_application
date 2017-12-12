package kmitl.projectfinal.se.kmitlbackyard;

import android.support.test.rule.ActivityTestRule;
import android.view.View;
import android.widget.Button;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;

import kmitl.projectfinal.se.kmitlbackyard.activity.LoginActivity;

public class LoginActivityTest {

    @Rule
    public ActivityTestRule<LoginActivity> mActivityTestRule = new ActivityTestRule<>(LoginActivity.class);

    private LoginActivity mLoginActivity = null;

    @Before
    public void setUp() throws Exception {
        mLoginActivity = mActivityTestRule.getActivity();
    }

    @Test
    public void testLaunch() {
        ArrayList<View> views = new ArrayList<>();

        views.add(mLoginActivity.findViewById(R.id.login_email));
        views.add(mLoginActivity.findViewById(R.id.login_password));
        views.add(mLoginActivity.findViewById(R.id.btn_login));
        views.add(mLoginActivity.findViewById(R.id.btn_register));

        for (View view: views) {
            Assert.assertNotNull(view);
        }
    }

    @Test
    public void testLogin() {
        Button btn_login = mLoginActivity.findViewById(R.id.btn_login);
        btn_login.performClick();
    }

    @After
    public void tearDown() throws Exception {
        mLoginActivity = null;
    }

}