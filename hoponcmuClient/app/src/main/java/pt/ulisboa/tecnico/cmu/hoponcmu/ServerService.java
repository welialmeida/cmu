package pt.ulisboa.tecnico.cmu.hoponcmu;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class ServerService extends Service {

    public static TreeMap clients = new TreeMap();
    public static TreeMap activeClients = new TreeMap();
    private final IBinder mBinder = new LocalBinder();


    String name;
    String code;
    ArrayList<String> listV = new ArrayList<String>();


    public class LocalBinder extends Binder {
        ServerService getService() {
            // Return this instance of LocalService so clients can call public methods
            return ServerService.this;
        }
    }

    public ServerService() {

    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        Bundle extras = intent.getExtras();

        this.name=extras.getString("EXTRA_USERNAME");
        this.code=extras.getString("EXTRA_PASSWORD");

        return mBinder;
    }

    @Override
    public void onCreate() {
        Toast.makeText(this, "Service was Created", Toast.LENGTH_SHORT).show();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "Service Started", Toast.LENGTH_SHORT).show();
        return START_STICKY; // read more on: http://developer.android.com/reference/android/app/Service.html
    }

    @Override
    public void onDestroy() {
        Toast.makeText(this, "Service Destroyed", Toast.LENGTH_SHORT).show();
    }

    /*   Result retunerd
         0 -> nothing
         1 -> signup successful
         2 -> error signup, name or code already used
         3 -> successful login
         4 -> error login, user not exists
    */
    public int resultOfRegistry(){

        if (!clients.containsKey(name)&&!clients.containsValue(code)){
            //depois acrescentar a verificacao de codigos validos
            clients.put(name,code);
            activeClients.put(name,code);
            Toast.makeText(this, "Entrou aqui SIGN UP", Toast.LENGTH_SHORT).show();
            return 1;
        }
        else {
            //Toast.makeText(this, "Entrou aqui ERRO SIGN UP", Toast.LENGTH_SHORT).show();
            return 2;
        }
    }


    public int resultLogin(){

        if (clients.containsKey(name)&&clients.containsValue(code)&&(clients.get(name).equals(code))){
            activeClients.put(name,code);
            Toast.makeText(this, "Entrou aqui LOGIN", Toast.LENGTH_SHORT).show();
            return 3;
        }
        else {
            Toast.makeText(this, "Entrou aqui LOGIN", Toast.LENGTH_SHORT).show();
            return 4;
        }
    }


    public int doLogout(){
        if (activeClients.containsKey(name)&&activeClients.containsValue(code)&&activeClients.get(name).equals(code)){
            activeClients.remove(name);
            return 1;
        }
        return 2;
    }
    public ArrayList<String> getListVenue(){
        listV.add("Ponte 25 de Abril");
        listV.add("Torre Belém");
        listV.add("Vasco da Gama");
        listV.add("Museu da Electricidade");

        return listV;
    }
}
