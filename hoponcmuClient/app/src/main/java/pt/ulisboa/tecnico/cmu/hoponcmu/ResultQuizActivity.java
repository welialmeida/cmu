package pt.ulisboa.tecnico.cmu.hoponcmu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ResultQuizActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_quiz);
        Intent intent = getIntent();
        String message = intent.getStringExtra(SubmitQuizActivity.EXTRA_MESSAGE);
        TextView textView = (TextView) findViewById(R.id.txv_results);
        textView.setTextSize(40);
        textView.setText(message);
    }
    public void backToHome(View v){

        Intent intent = new Intent(ResultQuizActivity.this, HomeActivity.class);
        startActivity(intent);
    }
    public void backToSubmitQuiz(View v){
        Intent intent = new Intent(ResultQuizActivity.this, SubmitQuizActivity.class);
        startActivity(intent);
    }
}
