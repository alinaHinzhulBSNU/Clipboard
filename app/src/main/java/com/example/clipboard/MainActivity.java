package com.example.clipboard;

import androidx.appcompat.app.AppCompatActivity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    EditText editText;
    TextView textView;
    ClipboardManager clipboard;
    List<String> buff = new ArrayList();
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Elements for buffer testing
        editText = findViewById(R.id.input);
        textView = findViewById(R.id.buffer);
        clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);

        // Button
        button = findViewById(R.id.button);

        // Work with buffer
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipData.Item item = clipboard.getPrimaryClip().getItemAt(0);
                String pasteData = item.getText().toString();
                textView.setText(pasteData);
            }
        });

        editText.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                String str = editText.getText().toString();

                if(!str.isEmpty()){
                    buff.add(str);
                    ClipData clip = ClipData.newPlainText("simple text", buff.toString());
                    clipboard.setPrimaryClip(clip);
                }

                return false;
            }
        });

        // Go to another activity for testing "Drag and Drop" and clipboard
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, DragAndDrop.class);
                startActivity(intent);
            }
        });
    }
}