package pt.ulisboa.tecnico.cmu.hoponcmu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import pt.ulisboa.tecnico.cmu.hoponcmu.util.StatusTracker;
import pt.ulisboa.tecnico.cmu.hoponcmu.util.Utils;

public class HomeActivity extends Activity {

    private String mActivityName;
    private TextView mStatusView;
    private TextView mStatusAllView;
    private StatusTracker mStatusTracker = StatusTracker.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mActivityName = "Home Activity";
        mStatusTracker.setStatus(mActivityName, "on_create");
    }


    @Override
    protected void onStart() {
        super.onStart();
        startService(new Intent(this, ServerService.class));
        mStatusTracker.setStatus(mActivityName, "on_start");
        Utils.printStatus(mStatusView, mStatusAllView);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        mStatusTracker.setStatus(mActivityName, "on_restart");
        Utils.printStatus(mStatusView, mStatusAllView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mStatusTracker.setStatus(mActivityName,"on_resume");
        Utils.printStatus(mStatusView, mStatusAllView);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mStatusTracker.setStatus(mActivityName, "on_pause");
        Utils.printStatus(mStatusView, mStatusAllView);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mStatusTracker.setStatus(mActivityName,"on_stop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mStatusTracker.setStatus(mActivityName, "on_destroy");
        mStatusTracker.clear();
    }


    public void startSignup(View v) {
        //Log.d("HomeActivity","signup passou aqui");
        Intent intent = new Intent(HomeActivity.this, SignupActivity.class);
        startActivity(intent);
    }

    public void startLogin(View v) {
        Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
        startActivity(intent);
    }
}
