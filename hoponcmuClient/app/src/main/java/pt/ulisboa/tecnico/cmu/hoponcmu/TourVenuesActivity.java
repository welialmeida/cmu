package pt.ulisboa.tecnico.cmu.hoponcmu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class TourVenuesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tour_venues);
        Intent intent = getIntent();

        ArrayList<String> venues = intent.getStringArrayListExtra("listV");
        ListView listOfVenues = (ListView) findViewById(R.id.lstv_venues);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,venues);
        listOfVenues.setAdapter(adapter);
    }

    public void backToHome(View v){

        Intent intent = new Intent(TourVenuesActivity.this, HomeActivity.class);
        startActivity(intent);
    }
    public void backToQuiz(View v){

        Intent intent = new Intent(TourVenuesActivity.this, QuizActivity.class);
        startActivity(intent);
    }
}
