package com.anupam.swipableapp.Activities;

import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.anupam.swipableapp.R;

import static com.anupam.swipableapp.Utils.UtilMethods.askForGalleryPermission;
import static com.anupam.swipableapp.Utils.UtilMethods.hasGalleryPermission;
import static com.anupam.swipableapp.Utils.UtilMethods.splitImage;

public class MainActivity extends AppCompatActivity {

    private AppCompatButton button1, button2, button3;
    private ImageView imageView;
    private AppCompatButton chooseButton, doneButton, cancelButton;
    private LinearLayout chooseLayout;
    private RelativeLayout actionLayout;

    private int sliceNumber = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initWidgets();
        clickEvents();
    }

    private void initWidgets(){

        chooseLayout = (LinearLayout) findViewById(R.id.choose_layout);
        actionLayout = (RelativeLayout) findViewById(R.id.action_layout);

        imageView = (ImageView) findViewById(R.id.imageView);
        chooseButton = (AppCompatButton) findViewById(R.id.choose_button);
        doneButton = (AppCompatButton) findViewById(R.id.done_button);
        cancelButton = (AppCompatButton) findViewById(R.id.cancel_button);

        button1 = (AppCompatButton) findViewById(R.id.button1);
        button2 = (AppCompatButton) findViewById(R.id.button2);
        button3 = (AppCompatButton) findViewById(R.id.button3);
    }

    private void clickEvents(){

        chooseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sliceNumber = 1;
                button1.setBackground(getResources().getDrawable(R.drawable.button_background_selected));
                button2.setBackground(getResources().getDrawable(R.drawable.button_background_unselected));
                button3.setBackground(getResources().getDrawable(R.drawable.button_background_unselected));

                button1.setTextColor(getResources().getColor(R.color.white));
                button2.setTextColor(getResources().getColor(R.color.colorAccent));
                button3.setTextColor(getResources().getColor(R.color.colorAccent));
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sliceNumber = 2;
                button1.setBackground(getResources().getDrawable(R.drawable.button_background_unselected));
                button2.setBackground(getResources().getDrawable(R.drawable.button_background_selected));
                button3.setBackground(getResources().getDrawable(R.drawable.button_background_unselected));

                button1.setTextColor(getResources().getColor(R.color.colorAccent));
                button2.setTextColor(getResources().getColor(R.color.white));
                button3.setTextColor(getResources().getColor(R.color.colorAccent));
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sliceNumber = 3;
                button1.setBackground(getResources().getDrawable(R.drawable.button_background_unselected));
                button2.setBackground(getResources().getDrawable(R.drawable.button_background_unselected));
                button3.setBackground(getResources().getDrawable(R.drawable.button_background_selected));

                button1.setTextColor(getResources().getColor(R.color.colorAccent));
                button2.setTextColor(getResources().getColor(R.color.colorAccent));
                button3.setTextColor(getResources().getColor(R.color.white));
            }
        });

        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                splitImage(MainActivity.this, imageView, sliceNumber);
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sliceNumber = 1;
                chooseLayout.setVisibility(View.VISIBLE);
                actionLayout.setVisibility(View.GONE);
            }
        });
    }

    private void chooseImage() {

        if (!hasGalleryPermission(MainActivity.this)) {
            askForGalleryPermission(MainActivity.this);
            return;
        }

        Intent intent =new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(intent,100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100 && resultCode == RESULT_OK) {
                Uri uri = data.getData();
                imageView.setImageURI(uri);

                chooseLayout.setVisibility(View.GONE);
                actionLayout.setVisibility(View.VISIBLE);
            }
    }
}
