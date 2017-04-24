package mario.testimagesql.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import mario.testimagesql.R;


public class MainActivity extends AppCompatActivity {

    ImageView add_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_activity);
        add_list = (ImageView) findViewById(R.id.iv_add_list);

        add_list.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent listactivity = new Intent(MainActivity.this, CreateListActivity.class);
                startActivity(listactivity);
            }
        });
    }
}
