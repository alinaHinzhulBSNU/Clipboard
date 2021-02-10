package com.example.clipboard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class DragAndDrop extends AppCompatActivity implements View.OnTouchListener, View.OnDragListener {
    TextView newTextView;
    private ClipboardManager clipboardManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drag_and_drop);

        //DRAG AND DROP
        // Почати переміщення елементу
        findViewById(R.id.image).setOnTouchListener(this);

        // Обробка перетягування елементу
        findViewById(R.id.top_left).setOnDragListener(this);
        findViewById(R.id.top_right).setOnDragListener(this);
        findViewById(R.id.bottom_left).setOnDragListener(this);
        findViewById(R.id.bottom_right).setOnDragListener(this);

        // Для обробки перетягування за межі таблиці
        findViewById(R.id.main).setOnDragListener(this);

        //CLIPBOARD
        newTextView = findViewById(R.id.new_buffer);
        clipboardManager = (ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);

        newTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipData.Item item = clipboardManager.getPrimaryClip().getItemAt(0);
                String pasteData = item.getText().toString();
                newTextView.setText(pasteData);
            }
        });
    }

    // Перетягування елементу
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            // Які дані передати разом з об'єктом, що переміщується
            ClipData data = ClipData.newPlainText("", "");

            // Показувати зазначений ImageView при переміщенні
            View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);

            // Почати переміщення
            v.startDrag(data, shadowBuilder, v, 0);

            // Показувати лише зображення, визначене в shadowBuilder
            v.setVisibility(View.INVISIBLE);

            return true;
        } else {
            return false;
        }
    }

    // Помістити елемент у відповідний LinearLayout
    @Override
    public boolean onDrag(View v, DragEvent event) {
        if (event.getAction() == DragEvent.ACTION_DROP) {
            // Що переміщують
            ImageView imageView = (ImageView) event.getLocalState();

            // Звідки переміщують
            LinearLayout oldOwner = (LinearLayout) imageView.getParent();

            // Куди переміщують
            LinearLayout newOwner = (LinearLayout) v;

            // Не перетягувати за межі таблиці
            if(R.id.main != newOwner.getId()){
                oldOwner.removeView(imageView);
                newOwner.addView(imageView);
            }

            imageView.setVisibility(View.VISIBLE);
        }

        return true;
    }
}