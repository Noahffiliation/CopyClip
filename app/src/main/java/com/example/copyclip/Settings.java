package com.example.copyclip;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Settings extends AppCompatActivity implements View.OnClickListener {
    private Shared shared;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        this.shared = new Shared(getApplicationContext());

        findViewById(R.id.back).setOnClickListener(this);
        findViewById(R.id.saveButton).setOnClickListener(this);

        EditText maxBufferSizeField = findViewById(R.id.maxBufferSize);
        EditText maxPinnedSizeField =  findViewById(R.id.maxPinnedSize);

        // A "hack" to convert the getMax functions from ints to strings for setText
        maxBufferSizeField.setText(shared.getMaxBuffer() + "");
        maxPinnedSizeField.setText(shared.getMaxPinned() + "");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                onBackPressed();
                break;
            case R.id.saveButton:
                saveFields();
                onBackPressed();
                break;
        }
    }

    private void saveFields(){
        EditText maxBuffer = findViewById(R.id.maxBufferSize);
        EditText maxPinned = findViewById(R.id.maxPinnedSize);

        if (tryParseInt(maxBuffer.getText().toString())) {
            shared.setMaxBuffer(Integer.parseInt(maxBuffer.getText().toString()));
        } else {
            maxBuffer.setError("Must be a number");
        }

        if (tryParseInt(maxPinned.getText().toString())) {
            shared.setMaxPinned(Integer.parseInt(maxPinned.getText().toString()));
        } else {
            maxPinned.setError("Must be a number");
        }

        Toast.makeText(this, shared.getMaxBuffer() + " : " +shared.getMaxPinned(), Toast.LENGTH_SHORT).show();
    }

    boolean tryParseInt(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
