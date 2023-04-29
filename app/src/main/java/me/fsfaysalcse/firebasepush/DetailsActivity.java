package me.fsfaysalcse.firebasepush;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Intent intent = getIntent();
        String websiteLink = intent.getStringExtra("website_link");

        if(websiteLink != null || !websiteLink.equals("")) {
            TextView textView = findViewById(R.id.textView);
            textView.setText(websiteLink);
        }
    }
}