package pt.ulisboa.tecnico.cmu.hoponcmu;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import pt.ulisboa.tecnico.cmu.hoponcmu.util.StatusTracker;
import pt.ulisboa.tecnico.cmu.hoponcmu.util.Utils;

public class QuizActivity extends AppCompatActivity {
    private String mActivityName;
    private TextView mStatusView;
    private TextView mStatusAllView;
    private StatusTracker mStatusTracker = StatusTracker.getInstance();
    boolean mBound = false;
    ServerService mService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
    }

    @Override
    protected void onStart() {
        super.onStart();

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
        if (mBound) {
            unbindService(mConnection);
            mBound = false;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mStatusTracker.setStatus(mActivityName, "on_destroy");
        mStatusTracker.clear();
    }

    public void listVenues(View v){
        ArrayList<String> listV= new ArrayList<String>();
        Intent intent = new Intent(QuizActivity.this, ServerService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
        if (mBound) {

            listV = mService.getListVenue();
            Toast.makeText(this, "list: " + listV, Toast.LENGTH_SHORT).show();
        }
        Intent intent2 = new Intent(QuizActivity.this, TourVenuesActivity.class);
        intent2.putStringArrayListExtra("listV",listV);
        startActivity(intent2);

    }
    private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            ServerService.LocalBinder binder = (ServerService.LocalBinder) service;
            mService = binder.getService();
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mBound = false;
        }
    };

    public void backToHome(View v){

        Intent intent = new Intent(QuizActivity.this, HomeActivity.class);
        startActivity(intent);
    }
    public void submitQuiz(View v){
        Intent intent = new Intent(QuizActivity.this, SubmitQuizActivity.class);
        startActivity(intent);
    }
}

