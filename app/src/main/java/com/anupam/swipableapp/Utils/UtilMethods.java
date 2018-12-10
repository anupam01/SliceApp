package com.anupam.swipableapp.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.app.ActivityCompat;
import android.widget.ImageView;

import com.anupam.swipableapp.Activities.SlicedActivity;

import java.io.FileOutputStream;
import java.util.Random;

/**
 * Created by Anupam on 10-12-2018.
 */

public class UtilMethods {

    private static final int REQUEST_CODE_READ_PERMISSION = 22;

    private static final String ALLOWED_CHARACTERS ="0123456789qwertyuiopasdfghjklzxcvbnm";

    public static void splitImage(Context context, ImageView image, int numberOfSlices) {

        int rows, columns;
        int image_Height, image_Width;

        BitmapDrawable mydrawable = (BitmapDrawable) image.getDrawable();
        Bitmap bitmap = mydrawable.getBitmap();
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth(), bitmap.getHeight(), true);

        rows = 1;
        columns = numberOfSlices;
        image_Height = bitmap.getHeight()/rows;
        image_Width = bitmap.getWidth()/columns;

        int yCoordinate = 0;
        Intent intent = new Intent(context, SlicedActivity.class);

        int xCoordinate = 0;
        for(int y=0; y<columns; y++){
            try {
                Bitmap bmp = Bitmap.createBitmap(scaledBitmap, xCoordinate, yCoordinate, image_Width, image_Height);
                String filename = "splitImage"+y+".png";
                FileOutputStream stream = context.openFileOutput(filename, Context.MODE_PRIVATE);
                bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);

                stream.close();
                bmp.recycle();

                intent.putExtra("image"+y, filename);
            } catch (Exception e) {
                e.printStackTrace();
            }
            xCoordinate += image_Width;
        }
        yCoordinate+= image_Height;
        intent.putExtra("slice_number",columns);
        context.startActivity(intent);
    }

    public static String randomString() {
        final Random random=new Random();
        final StringBuilder sb=new StringBuilder(10);
        for(int i=0;i<10;++i)
            sb.append(ALLOWED_CHARACTERS.charAt(random.nextInt(ALLOWED_CHARACTERS.length())));
        return sb.toString();
    }

    public static boolean hasGalleryPermission(Context context) {
        return ActivityCompat.checkSelfPermission(context,
                android.Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED;
    }

    public static void askForGalleryPermission(Context context) {
        ActivityCompat.requestPermissions((Activity) context,
                new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},
                REQUEST_CODE_READ_PERMISSION);
    }
}
