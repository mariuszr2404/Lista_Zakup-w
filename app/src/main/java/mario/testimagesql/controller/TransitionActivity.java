package mario.testimagesql.controller;

import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import mario.testimagesql.R;

/**
 * Created by mariu on 10.01.2017.
 */

public class TransitionActivity extends AppCompatActivity implements SensorEventListener{

    private TextView productLists;
    private ImageView goToActivity;
    private SensorManager mSensorManager;
    private Sensor mSensor;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.strat_adding);

        name = this.getIntent().getExtras().getString("name");
        productLists = (TextView) findViewById(R.id.tv_name_of_list);
        productLists.setText(name);

        goToActivity = (ImageView) findViewById(R.id.iv_go_to_list);
        goToActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TransitionActivity.this, AddProductActivity.class);
                intent.putExtra("name",name);
                startActivity(intent);
            }
        });

        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
    }

    // Nadpisanie metod onSensorChandged oraz  onAccuracyChanged z interfejsu SensorEventListener
    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.values[0] == 0){
            Intent intent = new Intent(TransitionActivity.this, AddProductActivity.class);
            intent.putExtra("name",name);
            startActivity(intent);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    protected void onResume(){
        super.onResume();
        // Rejestrujemy listenera dla czujnika zbliżeniowego
        mSensorManager.registerListener(this,mSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    protected void onPause(){
        super.onPause();
        // aplikacja nie dostepna jesli urzadzenie nie posiada danego sensora
        mSensorManager.unregisterListener(this);
    }
}

