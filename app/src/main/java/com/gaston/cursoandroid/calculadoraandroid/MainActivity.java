package com.gaston.cursoandroid.calculadoraandroid;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
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
    // Controlador
    private Screen pantalla;
    // Bind los elementos Visuales
    @BindView(R.id.Screen)
    EditText screen;
    @BindView(R.id.main)
    LinearLayout main;
    @BindView(R.id.screenFullExp)
    TextView screenFull;
    // Crear la vista
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Para Ligar los elementos
        ButterKnife.bind(this);
        // Configurar la Pantalla
        configScreen();
        // Para el cambio de orientación
        if(savedInstanceState!=null) {
            screen.setText(savedInstanceState.getString("Screen"));
            screenFull.setText(savedInstanceState.getString("screenFullExp"));
            pantalla.resetearScreen(screen.getText().toString(), screenFull.getText().toString());
        }
    }
    // Resguardar los datos en el cambio de Orientación
    @Override
    public void onSaveInstanceState (Bundle outState)
    {
        outState.putString("Screen", screen.getText().toString());
        outState.putString("screenFullExp", screenFull.getText().toString());

        super.onSaveInstanceState(outState);
    }
    // Para configurar la Pantalla
    private void configScreen() {
        // Crear el controlador de la Pantalla
        pantalla = new Screen();
        // Deshabilitar Aparición Teclado Virtual
        /*screen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager input = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                input.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
        });*/
        // Deshabilitar Teclado Fisico
        screen.setKeyListener(null);
        // Habilitar el boton para borrar
        screen.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (screen.getRight() - screen.getCompoundDrawables()[2].getBounds().width())) {
                        pantalla.borrar();
                        pantalla.actualizarScreen(screen, screenFull);
                    }
                    return true;
                }
                return false;
            }
        });
    }
    // Agregar funcionalidad de los Botones
    @OnClick({R.id.Pad_07, R.id.Pad_08, R.id.Pad_09, R.id.Pad_04, R.id.Pad_05, R.id.Pad_06,
            R.id.Pad_01, R.id.Pad_02, R.id.Pad_03, R.id.Pad_dot, R.id.Pad_00, R.id.Pad_div,
            R.id.Pad_mult, R.id.Pad_neg, R.id.Pad_sum, R.id.Pad_clear, R.id.Pad_equal})
    public void onClickPad(View view) {
        String valor = ((Button) view).getText().toString();
        int mensaje= 0;
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
            case R.id.Pad_dot:
            case R.id.Pad_sum:
            case R.id.Pad_neg:
            case R.id.Pad_mult:
            case R.id.Pad_div:
                mensaje= pantalla.ingresarValor(valor);
                if ( mensaje != 0)
                    mostrarMensaje(mensaje);
                break;
            case R.id.Pad_clear:
                pantalla.limpiarPantalla();
                break;
            case R.id.Pad_equal:
                pantalla.calcular();
                break;
        }
        pantalla.actualizarScreen(screen, screenFull);
    }
    public void mostrarMensaje(int mensaje) {
        int stringMensaje;
        switch (mensaje) {
            case 1:
                stringMensaje= R.string.error_1;
                break;
            case 2:
                stringMensaje= R.string.error_2;
                break;
            case 3:
                stringMensaje= R.string.error_3;
                break;
            case 4:
                stringMensaje= R.string.error_4;
                break;
            default:
                stringMensaje= R.string.error_0;
        }
        Snackbar.make(main, stringMensaje, Snackbar.LENGTH_SHORT).show();

    }

}
