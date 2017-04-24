package mario.testimagesql.controller;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

import mario.testimagesql.R;

/**
 * Created by mariu on 09.01.2017.
 */

public class CreateListActivity extends AppCompatActivity {

    private ImageView iv_close;
    private EditText et_new_list;
    private TextView tv_name_of_list;
    private TextView create_view;
    private String nameOfList;
    private Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_list);


        iv_close = (ImageView) findViewById(R.id.iv_view_close);
        tv_name_of_list = (TextView) findViewById(R.id.tv_nazwa_listy);
        et_new_list = (EditText) findViewById(R.id.et_nazwa_listy);

        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent productlistIntent = new Intent(CreateListActivity.this, MainActivity.class);
                startActivity(productlistIntent);
            }
        });


        create_view = (TextView) findViewById(R.id.tv_createList);
        create_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(chceckLength()) {
                    Intent createListIntent = new Intent(CreateListActivity.this, TransitionActivity.class);
                    // Zostaje przekazana nazwa listy
                    createListIntent.putExtra("name", nameOfList);
                    startActivity(createListIntent);
                }
                else {
                    Toast.makeText(CreateListActivity.this, "Nazwa listy nie może być pusta", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        timer = new Timer();
        final long DELAY = 400; // in ms

        et_new_list.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }
            @Override
            public void onTextChanged(final CharSequence s, int start, int before,
                                      int count) {
                if(timer != null)
                    timer.cancel();
            }

            @Override
            public void afterTextChanged(final Editable s) {
                if (s.length() >= 3) {

                    timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            nameOfList = et_new_list.getText().toString();
                        }
                    }, DELAY);
                }
            }
        });
    }

    // W przypadku wpisywania nazwy listy, textView staje się widoczny.
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        tv_name_of_list.setVisibility(View.VISIBLE);
        return super.onKeyUp(keyCode, event);
    }

    // Sprawdza czy dlugość nazwy listy jest większa od zera
    public boolean chceckLength() {
        String name = et_new_list.getText().toString();
        if(name.length()>0){
            return true;
        }
        return false;
    }

}
