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

public class StartQuizActivity extends AppCompatActivity {

    ServerService mService;
    boolean mBound = false;
    private String idOfMessage;

    String name;
    String code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_quiz);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        this.name=extras.getString("EXTRA_USERNAME");
        this.code=extras.getString("EXTRA_PASSWORD");
    }

    public void startLogout(View v){
        int num=0;
        Intent intent2 = new Intent(StartQuizActivity.this, ServerService.class);
        Bundle extras = new Bundle();

        extras.putString("EXTRA_USERNAME",this.name);
        extras.putString("EXTRA_PASSWORD",this.code);
        intent2.putExtras(extras);
        bindService(intent2, mConnection, Context.BIND_AUTO_CREATE);
        if (mBound) {

            num = mService.doLogout();
            Toast.makeText(this, "number: " + num, Toast.LENGTH_SHORT).show();
        }
        if(num==1){
            Intent intent3 = new Intent(StartQuizActivity.this, HomeActivity.class);
            startActivity(intent3);

        }
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

    public void startQuiz(View v){
        Intent intent = new Intent(StartQuizActivity.this,QuizActivity.class);

        startActivity(intent);
    }
}
