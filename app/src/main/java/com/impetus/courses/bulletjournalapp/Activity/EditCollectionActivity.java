package com.impetus.courses.bulletjournalapp.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.impetus.courses.bulletjournalapp.R;

public class EditCollectionActivity extends AppCompatActivity {
    EditText updateTitle,updateContent;
    Button updateCollection;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_collection);
        updateTitle=findViewById(R.id.editText5);
        updateContent=findViewById(R.id.editText6);
        updateCollection=findViewById(R.id.button3);

        updateCollection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
}
