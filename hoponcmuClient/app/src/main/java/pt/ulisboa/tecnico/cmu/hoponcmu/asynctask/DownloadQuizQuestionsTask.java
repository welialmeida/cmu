package pt.ulisboa.tecnico.cmu.hoponcmu.asynctask;

import android.os.AsyncTask;
import android.util.Log;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import pt.ulisboa.tecnico.cmu.command.Command;
import pt.ulisboa.tecnico.cmu.command.HelloCommand;
import pt.ulisboa.tecnico.cmu.hoponcmu.SampleMainActivity;
import pt.ulisboa.tecnico.cmu.response.HelloResponse;
import pt.ulisboa.tecnico.cmu.response.Response;

public class DownloadQuizQuestionsTask extends AsyncTask<String, Void, String> {

    private SampleMainActivity sampleMainActivity;

    public DownloadQuizQuestionsTask(SampleMainActivity sampleMainActivity) {
        this.sampleMainActivity = sampleMainActivity;
    }

    @Override
    protected String doInBackground(String[] params) {
        Socket server = null;
        String reply = null;
        Command hc = new HelloCommand(params[0]);
        try {
            server = new Socket("192.168.1.199", 9090);

            ObjectOutputStream oos = new ObjectOutputStream(server.getOutputStream());
            oos.writeObject(hc);

            ObjectInputStream ois = new ObjectInputStream(server.getInputStream());
            Response hr = (HelloResponse) ois.readObject();
            //reply = hr.getMessage();

            oos.close();
            ois.close();
            Log.d("DummyClient", "Hi there!!");
        }
        catch (Exception e) {
            Log.d("DummyClient", "DownloadQuizQuestionsTask failed..." + e.getMessage());
            e.printStackTrace();
        } finally {
            if (server != null) {
                try { server.close(); }
                catch (Exception e) { }
            }
        }
        return reply;
    }

    @Override
    protected void onPostExecute(String o) {
        if (o != null) {
            sampleMainActivity.updateInterface(o);
        }
    }
}
