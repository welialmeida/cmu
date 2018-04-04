package pt.ulisboa.tecnico.cmu.hoponcmu;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import pt.ulisboa.tecnico.cmu.hoponcmu.R;
import pt.ulisboa.tecnico.cmu.hoponcmu.util.StatusTracker;

public class LoginActivity extends AppCompatActivity {
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
        setContentView(R.layout.activity_login);
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

    //isto eh o logout, tem que ser implementado no servidor de forma a apagar da lista dos clientes activos
    public void backToHome(View v){
        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
        startActivity(intent);
    }
    public void startOK(View v){
        int num=0;
        Intent intent = new Intent(LoginActivity.this, ServerService.class);

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

            num = mService.resultLogin();
            Toast.makeText(this, "number: " + num, Toast.LENGTH_SHORT).show();
        }

        //se foi bem autenticado entao comeca o quiz
        if (num==3){
            Intent intent2 = new Intent(LoginActivity.this, StartQuizActivity.class);
            intent2.putExtras(extras);
            Toast.makeText(this, "Passou no if 3" , Toast.LENGTH_SHORT).show();
            startActivity(intent2);
        }

        if (num==4){

            Toast.makeText(this, "Passou no if 4" , Toast.LENGTH_SHORT).show();

            TextView textView = (TextView) findViewById(R.id.txv_error);
            textView.setTextSize(40);

            textView.setText("Error login, name or code doesn'exist");
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
