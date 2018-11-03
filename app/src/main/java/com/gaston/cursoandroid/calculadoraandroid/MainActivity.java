package com.gaston.cursoandroid.calculadoraandroid;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    Screen pantalla;
    @BindView(R.id.Screen)
    EditText Screen;
    @BindView(R.id.main)
    LinearLayout main;
    @BindView(R.id.screenFullExp)
    TextView screenFull;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        configScreen();
        if(savedInstanceState!=null) {
            Screen.setText(savedInstanceState.getString("Screen"));
            screenFull.setText(savedInstanceState.getString("screenFullExp"));
            pantalla.resetearScreen(Screen.getText().toString(), screenFull.getText().toString());
            //pantalla.setExpresion((String) screenFull.getText().toString());
            //pantalla.setVisor((String) Screen.getText().toString());
        }
    }

    @Override
    public void onSaveInstanceState (Bundle outState)
    {
        outState.putString("Screen", Screen.getText().toString());
        outState.putString("screenFullExp", screenFull.getText().toString());

        super.onSaveInstanceState(outState);
    }

    private void configScreen() {
        pantalla = new Screen();
        Screen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager input = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                input.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
        });
        Screen.setKeyListener(null);
    }

    @OnClick({R.id.Pad_07, R.id.Pad_08, R.id.Pad_09, R.id.Pad_04, R.id.Pad_05, R.id.Pad_06,
            R.id.Pad_01, R.id.Pad_02, R.id.Pad_03, R.id.Pad_dot, R.id.Pad_00, R.id.Pad_div,
            R.id.Pad_mult, R.id.Pad_neg, R.id.Pad_sum})
    public void onClickPad(View view) {
        String valor = ((Button) view).getText().toString();
        switch (view.getId()) {
            case R.id.Pad_00:
            case R.id.Pad_01:
            case R.id.Pad_02:
            case R.id.Pad_03:
            case R.id.Pad_04:
            case R.id.Pad_05:
            case R.id.Pad_06:
            case R.id.Pad_07:
            case R.id.Pad_08:
            case R.id.Pad_09:
            case R.id.Pad_sum:
            case R.id.Pad_neg:
            case R.id.Pad_mult:
            case R.id.Pad_div:
            case R.id.Pad_dot:
                pantalla.ingresarValor(valor);
                pantalla.actualizarScreen(Screen, screenFull);
                break;
        }
    }

    @OnClick({R.id.Pad_clear, R.id.Pad_equal})
    public void onClickPadOt(View view) {
        switch (view.getId()) {
            case R.id.Pad_clear:
                pantalla.limpiarPantalla();
                pantalla.actualizarScreen(Screen, screenFull);
                break;
            case R.id.Pad_equal:
                pantalla.calcular();
                pantalla.actualizarScreen(Screen, screenFull);
                break;
        }
    }
}
