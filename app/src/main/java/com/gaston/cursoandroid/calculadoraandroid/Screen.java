package com.gaston.cursoandroid.calculadoraandroid;

import android.widget.EditText;
import android.widget.TextView;
import mathjs.niltonvasques.com.mathjs.MathJS;

public class Screen {
    // Variables - Estados
    private String expresion;
    private String visor;
    private boolean decimal;
    // Constantes
    private final String padNum="0123456789.";
    private final String padOp="+-/x*";
    private final String padOt="C=";
    // Constructor
    public Screen() {
        this.decimal= false;
        this.expresion= "";
        this.visor= "";
    }
    // Resetear los valores
    public void resetearScreen(String nuevoVisor, String nuevaExpresion) {
        this.visor= nuevoVisor;
        this.expresion= nuevaExpresion;
    }
    // Actualizar Pantalla
    public void actualizarScreen(TextView screenVisor, TextView screenExpresion) {
        screenVisor.setText(this.visor);
        screenExpresion.setText(this.expresion);
    }
    public void actualizarScreen(String screenVisor, String screenExpresion) {
        screenVisor = this.visor;
        screenExpresion = this.expresion;
    }
    // Agregar Numeros y Operaciones
    public void ingresarValor(String nuevoValor) {
        if (Tecla.esNUM(nuevoValor))
            this.ingresarNUM(nuevoValor);
        else if (Tecla.esOPE(nuevoValor))
            this.ingresarOPE(nuevoValor);
    }
    private void ingresarNUM(String nuevoNUM) {
        int longitud = this.expresion.length();
        String ultimo= longitud>0 ? this.expresion.substring(this.expresion.length()-1) : "";

        if (Tecla.esOPE(ultimo)) {
            this.visor = "";
        } else if (Tecla.hayRES(ultimo)) {
            this.visor= "";
            this.expresion= "";
        }
        if (!Tecla.esDOT(nuevoNUM)) {
            this.visor += nuevoNUM;
            this.expresion += nuevoNUM;
        } else if (!this.decimal) {
            if (this.visor.length() == 0) {
                this.visor += "0";
                this.expresion += "0";
            }
            this.visor += nuevoNUM;
            this.expresion += nuevoNUM;
            this.decimal= true;
        }
    }
    private void ingresarOPE(String nuevoOPE) {
        int longitud= this.expresion.length();
        String ultimo= longitud>0 ? this.expresion.substring(this.expresion.length()-1) : "";

        if ( longitud == 0 && nuevoOPE.equals("-") ) {
            this.visor = Tecla.validarOPE(nuevoOPE);
            this.expresion += Tecla.validarOPE(nuevoOPE);
        } else if ( longitud>0 && Tecla.esNUM(ultimo) ) {
            if (Tecla.esDOT(ultimo))
                this.expresion= this.expresion.substring(0,longitud-1);
            this.visor= Tecla.validarOPE(nuevoOPE);
            this.expresion += Tecla.validarOPE(nuevoOPE);
            this.decimal= false;
        } else if (longitud > 1 && Tecla.esOPE(ultimo)) {
            this.visor = Tecla.validarOPE(nuevoOPE);
            this.expresion = this.expresion.substring(0, longitud - 1) + Tecla.validarOPE(nuevoOPE);
        } else if (Tecla.hayRES(ultimo)) {
            this.expresion = this.visor + Tecla.validarOPE(nuevoOPE);
            this.visor = Tecla.validarOPE(nuevoOPE);
        }
    }
    public void borrar() {
        int longitud= this.expresion.length();
        String ultimo= longitud>0 ? this.expresion.substring(longitud-1) : "";

        if (Tecla.hayRES(ultimo) || longitud == 1) {
            this.visor= "";
            this.expresion = "";
        } else if (longitud >1) {
            this.expresion = this.expresion.substring( 0,
                    longitud > 2 ? this.expresion.length()-1 : 1);
            String nuevoUltimo= this.expresion.substring(this.expresion.length()-1);

            if (Tecla.esOPE(ultimo)) {
                String[] terminos= this.expresion.split("[-*+/]");
                this.visor= terminos[terminos.length-1];
            } else if (Tecla.esNUM(ultimo)) {
                if (this.visor.length()>1) {
                    this.visor = this.visor.substring(0,
                            this.visor.length()>2 ? this.visor.length()-1 : 1 ) ;
                } else {
                    this.visor = nuevoUltimo;
                }
            }
        }
    }

    public void limpiarPantalla() {
        this.visor= "";
        this.expresion= "";
    }

    public void calcular() {
        MathJS math = new MathJS();
        int longitud = this.expresion.length();
        String ultimoDigito = longitud > 0 ? this.expresion.substring(longitud-1) : "";
        if (Tecla.hayRES(ultimoDigito)) {
            this.expresion= this.expresion.substring(0, longitud-1);
        }
        if (longitud == 0 || Tecla.esOPE(ultimoDigito) ) {
            this.expresion += "0";
        } else if ( Tecla.esDOT(ultimoDigito) ) {
            this.expresion =  this.expresion.substring( 0, longitud-1);
        }
        String result = longitud>0 ? math.eval(this.expresion) : "0";
        this.expresion += "=";
        this.visor = result;
        math.destroy();
    }

}
