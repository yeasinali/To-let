package com.example.pc.to_let;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.transition.Explode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Sign_in extends AppCompatActivity implements View.OnClickListener {
    EditText etUsername,etPassword;
    Button btGo;
    CardView cv;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        allfintd();
        onclick();

    }
    private void allfintd() {
        etUsername = (EditText)findViewById(R.id.et_username);
        etPassword = (EditText)findViewById(R.id.et_password);
        btGo = (Button)findViewById(R.id.bt_go);
        cv = (CardView)findViewById(R.id.cv);
        fab = (FloatingActionButton)findViewById(R.id.fab);
    }
    private void onclick() {
        fab.setOnClickListener(this);

    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab:
                getWindow().setExitTransition(null);
                getWindow().setEnterTransition(null);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptions options =
                            ActivityOptions.makeSceneTransitionAnimation(this, fab, fab.getTransitionName());
                    startActivity(new Intent(this, Signup.class), options.toBundle());
                } else {
                    startActivity(new Intent(this, Signup.class));
                }
                break;
            case R.id.bt_go:
                Explode explode = new Explode();
                explode.setDuration(500);

                getWindow().setExitTransition(explode);
                getWindow().setEnterTransition(explode);
                ActivityOptionsCompat oc2 = ActivityOptionsCompat.makeSceneTransitionAnimation(this);
                Intent i2 = new Intent(this,Sign_in.class);
                startActivity(i2, oc2.toBundle());
                break;
        }
    }
}
