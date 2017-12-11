package kmitl.projectfinal.se.kmitlbackyard.activity;

import android.support.test.rule.ActivityTestRule;
import android.view.View;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;

import kmitl.projectfinal.se.kmitlbackyard.R;

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

    @After
    public void tearDown() throws Exception {
        mLoginActivity = null;
    }

}