package com.example.ex03_076;

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

    Button b;
    ImageView iv;
    Canvas c;
    Paint p,q,p1,p2;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            b=(Button)findViewById(R.id.button);
            iv=(ImageView)findViewById(R.id.imageView);
            Bitmap bg=Bitmap.createBitmap(720,1280,Bitmap.Config.ARGB_8888);
            c=new Canvas(bg);
            p=new Paint();
            q=new Paint();
            p1=new Paint();
            p2=new Paint();

            p.setColor(Color.GRAY);
            q.setColor(Color.BLACK);
            p1.setColor(Color.DKGRAY);
            p2.setColor(Color.WHITE);




            iv.setBackgroundDrawable(new BitmapDrawable(bg));
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    c.drawCircle(200,1150,90,q);
                    c.drawCircle(500,1150,90,q);
                    c.drawCircle(200,300,90,q);
                    c.drawCircle(500,300,90,q);
                    c.drawCircle(350,900,280,p);
                    c.drawCircle(430,450,20,p2);
                    c.drawCircle(600,600,70,p2);
                    c.drawCircle(350,500,200,p1);
                    c.drawCircle(280,450,20,p2);
                    c.drawCircle(430,450,20,p2);










                }
            });

            return insets;
        });
    }
}