import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity2 extends AppCompatActivity {

    EditText e1,e2;
    Button b,b2,b3,b4,b5;
    SQLiteDatabase db;
    Cursor c;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main2);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            b = findViewById(R.id.button2);
            b2 = findViewById(R.id.button4);
            b3= findViewById(R.id.button5);
            b4 = findViewById(R.id.button6);
            b5 = findViewById(R.id.button7);
            // Make sure button2 exists in XML
            e1 = findViewById(R.id.editTextText3);
            e2 = findViewById(R.id.editTextText4);

            db=openOrCreateDatabase("Student", MODE_PRIVATE, null);
            c=db.rawQuery("SELECT * FROM Student",null);
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    c.moveToFirst();
                    e1.setText(c.getString(0));
                    e2.setText(c.getString(1));

                }
            });
            b2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(c.isLast()){
                        Toast.makeText(MainActivity2.this,"This is Last",Toast.LENGTH_SHORT).show();

                    }
                    else {
                        c.moveToLast();
                        e1.setText(c.getString(0));
                        e2.setText(c.getString(1));
                    }

                }
            });
            b3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    c.moveToNext();
                    e1.setText(c.getString(0));
                    e2.setText(c.getString(1));

                }
            });
            b4.setOnClickListener(new View.OnClickListener() {
                @Override

                public void onClick(View v) {
                    if(c.isFirst()){
                        Toast.makeText(MainActivity2.this,"This is First",Toast.LENGTH_SHORT).show();

                    }
                    c.moveToPrevious();
                    e1.setText(c.getString(0));
                    e2.setText(c.getString(1));

                }
            });
            b5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(MainActivity2.this, MainActivity.class);
                    startActivity(i);

                }
            });
            return insets;
        });
    }
}
