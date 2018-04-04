package pt.ulisboa.tecnico.cmu.hoponcmu;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import pt.ulisboa.tecnico.cmu.hoponcmu.util.StatusTracker;
import pt.ulisboa.tecnico.cmu.hoponcmu.util.Utils;


public class SignupActivity extends Activity {
    private String mActivityName;
    private TextView mStatusView;
    private TextView mStatusAllView;
    private StatusTracker mStatusTracker = StatusTracker.getInstance();
    public final static String EXTRA_MESSAGE = "pt.ulisboa.tecnico.cmov.hoponcmu.MESSAGE";

    ServerService mService;
    boolean mBound = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registry);
        mActivityName = "Registry Activity";

        mStatusTracker.setStatus(mActivityName, "on_create");


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
    public void backToHome(View v){
        Intent intent = new Intent(SignupActivity.this, HomeActivity.class);
        startActivity(intent);

    }
    public void startOK(View v){
        int num=0;
        Intent intent = new Intent(SignupActivity.this, ServerService.class);

        EditText editText = (EditText) findViewById(R.id.edt_name);
        String userName= editText.getText().toString();

        EditText editText2 = (EditText) findViewById(R.id.edt_code);
        String password= editText.getText().toString();

        Bundle extras = new Bundle();

        extras.putString("EXTRA_USERNAME",userName);
        extras.putString("EXTRA_PASSWORD",password);
        intent.putExtras(extras);

        //Isto eh para comunicar-se com o servidor e depois retomar o valor se foi ou nao bem autenticado
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
        if (mBound) {

            num = mService.resultOfRegistry();
            //Toast.makeText(this, "number: " + num, Toast.LENGTH_SHORT).show();
        }

        //se foi bem autenticado entao comeca o quiz
        if (num==1){
            Intent intent2 = new Intent(SignupActivity.this, StartQuizActivity.class);
            intent2.putExtras(extras);

            startActivity(intent2);
        }
        if (num==2){

            TextView textView = (TextView) findViewById(R.id.txv_error);
            textView.setTextSize(40);
            //Toast.makeText(this, "Passou no if 2" , Toast.LENGTH_SHORT).show();
            textView.setText("Error signup, name or code already used");
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
}
