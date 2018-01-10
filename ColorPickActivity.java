package com.example.rbk.notatnik.git;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.rbk.notatnik.R;

public class ColorPickActivity extends AppCompatActivity {

    static Context context;
    static int selectedcolor;
    static String for_var = null;
    static View colorpreview;
    static ImageView image_palette;
    static ImageButton bt_back;
    static ImageButton bt_save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_pick);
        context = getApplicationContext();

        colorpreview = (View)findViewById(R.id.color_preview);
        image_palette = (ImageView)findViewById(R.id.image_palette);
        bt_back = (ImageButton)findViewById(R.id.colorpick_back_button);
        bt_save = (ImageButton)findViewById(R.id.colorpick_save_button);

        Intent intent = getIntent();
        for_var = intent.getExtras().get("for").toString();
        selectedcolor = Integer.parseInt(intent.getExtras().get("color").toString());
        colorpreview.setBackgroundColor(selectedcolor);



        bt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent result = new Intent();
                setResult(RESULT_CANCELED,result);
                finish();
            }
        });

        bt_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent result = new Intent();
                result.putExtra("for",for_var);
                result.putExtra("color",selectedcolor);
                setResult(RESULT_OK,result);
                finish();
            }
        });


        image_palette.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        image_palette.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_MOVE){

                    Bitmap bitmap = ((BitmapDrawable)image_palette.getDrawable()).getBitmap();

                    // real bitmap x and y size
                    float bitmapWidth = bitmap.getWidth();
                    float bitmapHeight = bitmap.getHeight();

                    // scaled image view for bitmap x and y size
                    float imageviewWidth = image_palette.getWidth();
                    float imageviewHeight = image_palette.getHeight();

                    // scale between real bitmap size and scaled on image view size
                    float Xscale = imageviewWidth/bitmapWidth;
                    float Yscale = imageviewHeight/bitmapHeight;

                    // touch coordinates
                    float X = Math.round(motionEvent.getX());
                    float Y = Math.round(motionEvent.getY());

                    // real bitmap coordinates calculated with scale factor
                    int x = Integer.valueOf(Math.round(X/Xscale));
                    int y = Integer.valueOf(Math.round(Y/Yscale));

                    // out of image borders check for x coordinate
                    if(x < 0){
                        x = 0;
                    }else if(x > bitmap.getWidth()-1){
                        x = bitmap.getWidth()-1;
                    }

                    // out of image borders check for y coordinate
                    if(y < 0){
                        y = 0;
                    }else if(y > bitmap.getHeight()-1){
                        y = bitmap.getHeight()-1;
                    }

                    selectedcolor =  bitmap.getPixel(x,y);

                    setPreviewColor(selectedcolor);

                    return true;
                }
                return false;
            }
        });

    }

    private static void setPreviewColor(int color)
    {

        colorpreview.setBackgroundColor(color);
        colorpreview.invalidate();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            //Toast.makeText(context, "landscape", Toast.LENGTH_SHORT).show();


        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            //Toast.makeText(context, "portrait", Toast.LENGTH_SHORT).show();


        }
    }
}
