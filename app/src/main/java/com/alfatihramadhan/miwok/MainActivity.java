package com.alfatihramadhan.miwok;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.alfatihramadhan.miwok.Category.ColorsActivity;
import com.alfatihramadhan.miwok.Category.FamilyMembersActivity;
import com.alfatihramadhan.miwok.Category.NumbersActivity;
import com.alfatihramadhan.miwok.Category.PhrasesActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView number = (TextView)findViewById(R.id.number_activity);
        number.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent intent = new Intent(MainActivity.this,NumbersActivity.class);
                startActivity(intent);
            }
        });

        TextView family = (TextView)findViewById(R.id.family_members_activity);
        family.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,FamilyMembersActivity.class);
                startActivity(intent);
            }
        });

        TextView color = (TextView)findViewById(R.id.colors_activity);
        color.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,ColorsActivity.class);
                startActivity(intent);
            }
        });

        TextView phrases = (TextView)findViewById(R.id.phrases_activity);
        phrases.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,PhrasesActivity.class);
                startActivity(intent);
            }
        });

    }
}
