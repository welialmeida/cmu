package pt.ulisboa.tecnico.cmu.hoponcmu;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class SubmitQuizActivity extends AppCompatActivity {
    boolean mBound = false;
    ServerService mService;
    public final static String EXTRA_MESSAGE = "pt.ulisboa.tecnico.cmov.hoponcmu.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_quiz);
    }

    public void listVenues(View v){
        ArrayList<String> listV= new ArrayList<String>();
        Intent intent = new Intent(SubmitQuizActivity.this, ServerService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
        if (mBound) {

            listV = mService.getListVenue();
            Toast.makeText(this, "list: " + listV, Toast.LENGTH_SHORT).show();
        }
        Intent intent2 = new Intent(SubmitQuizActivity.this, TourVenuesActivity.class);
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

        Intent intent = new Intent(SubmitQuizActivity.this, HomeActivity.class);
        startActivity(intent);
    }

    public void resultQuiz(View v){
        Intent intent = new Intent(SubmitQuizActivity.this, ResultQuizActivity.class);
        intent.putExtra(EXTRA_MESSAGE, "Aqui tem que estar os resultados do questionario que o servidor tem que fornecer");
        startActivity(intent);
    }
    public void backToQuiz(View v){

        Intent intent = new Intent(SubmitQuizActivity.this, QuizActivity.class);
        startActivity(intent);
    }
}
