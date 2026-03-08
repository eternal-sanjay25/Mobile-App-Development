package com.example.teddy;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    ImageView iv;
    Button b;
    Canvas c;
    Paint c1,c2,c3,c4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            iv=(ImageView)findViewById(R.id.imageView);
            b=(Button)findViewById(R.id.button);
            Bitmap bg=Bitmap.createBitmap(720,1280, Bitmap.Config.ARGB_8888);
            c=new Canvas(bg);
            c1=new Paint();
            c2=new Paint();
            c3=new Paint();
            c4=new Paint();
            c1.setColor(Color.rgb(85,0,0));
            c2.setColor(Color.argb(75,255,255,255));
            c3.setColor(Color.rgb(255,0,0));
            c4.setColor(Color.rgb(70,0,0));
            c3.setTextSize(75);
            iv.setBackgroundDrawable(new BitmapDrawable(bg));
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    c.drawText("\"Teddy\"",235,250,c3);
                    c.drawCircle(360,640,150,c1);
                    c.drawCircle(360,590,100,c2);
                    c.drawCircle(300,330,25,c1);
                    c.drawCircle(420,330,25,c1);
                    c.drawCircle(417,335,15,c2);
                    c.drawCircle(303,335,15,c2);
                    c.drawCircle(360,430,100,c1);
                    c.drawCircle(360,440,50,c3);
                    c.drawCircle(360,415,50,c1);
                    c.drawCircle( 325,390,15,c3);
                    c.drawCircle( 395,390,15,c3);
                    c.drawCircle( 325,403,15,c1);
                    c.drawCircle( 395,403,15,c1);
                    c.drawCircle( 250,525,40,c1);
                    c.drawCircle( 470,525,40,c1);
                    c.drawCircle( 470,725,60,c4);
                    c.drawCircle( 250,725,60,c4);

                }
            });
            return insets;
        });
    }
}