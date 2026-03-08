package com.example.ex06_076;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
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
    ImageView v;
    MediaPlayer m;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);


        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (view, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            view.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            b = (Button) findViewById(R.id.button);
            v = (ImageView) findViewById(R.id.imageView);
            m = MediaPlayer.create(MainActivity.this, R.raw.music);
            b.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View view) {
                    MainActivity.this.v.post(new Runnable() {
                        @Override public void run() {
                            AnimationDrawable animation = new AnimationDrawable();
                            animation.addFrame(getResources().getDrawable(R.drawable.i1), 500);
                            animation.addFrame(getResources().getDrawable(R.drawable.i2), 500);
                            animation.addFrame(getResources().getDrawable(R.drawable.i3), 500);
                            animation.setOneShot(false);
                            MainActivity.this.v.setBackgroundDrawable(animation);

    animation.start(); } });
                }
            });
            new Thread(new Runnable() {
                @Override
                public void run() {
                    MainActivity.this.v.post(new Runnable() {
                        @Override
                        public void run() {
                            m.start();
                        }
                    });
                }
            }).start();
            return insets;
        });
    }
}