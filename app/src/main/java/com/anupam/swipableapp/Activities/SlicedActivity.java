package com.anupam.swipableapp.Activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.GridView;
import android.widget.Toast;

import com.anupam.swipableapp.R;
import com.anupam.swipableapp.Adapters.SplitAdapter;
import java.io.FileInputStream;
import java.util.ArrayList;

import static com.anupam.swipableapp.Utils.UtilMethods.randomString;

public class SlicedActivity extends AppCompatActivity {

    private AppCompatButton saveButton;

    private ArrayList<Bitmap> slicedImage = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sliced);

        initWidgets();
        clickEvents();

        int sliceNumber = getIntent().getIntExtra("slice_number",1);

        for (int i = 0; i < sliceNumber; i++){
            Bitmap bmp = null;
            String filename = getIntent().getStringExtra("image"+""+i);
            try {
                FileInputStream is = this.openFileInput(filename);
                bmp = BitmapFactory.decodeStream(is);
                is.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            slicedImage.add(bmp);
        }

        GridView image_grid = (GridView) findViewById(R.id.gridView);

        image_grid.setAdapter(new SplitAdapter(this, slicedImage));

        image_grid.setNumColumns(sliceNumber);
    }

    private void initWidgets(){

        saveButton = (AppCompatButton) findViewById(R.id.save_button);
    }

    private void clickEvents(){

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int j = 0; j < slicedImage.size(); j++){
                    MediaStore.Images.Media.insertImage(getContentResolver(), slicedImage.get(j), randomString(), "Slice App Images");
                }

                Toast.makeText(SlicedActivity.this, getResources().getString(R.string.success), Toast.LENGTH_LONG).show();

                Intent intent = new Intent(SlicedActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }
}
