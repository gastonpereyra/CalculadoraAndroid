package com.gaston.cursoandroid.calculadoraandroid;

import mathjs.niltonvasques.com.mathjs.MathJS;

public class Screen {
    private String expresion;
    private String visor;
    private boolean decimal;

    private final String padNum="0123456789.";
    private final String padOp="+-/x*";
    private final String padOt="C=";

    public Screen() {
        this.decimal= false;
        this.expresion= "";
        this.visor= "";
    }

    public String getVisor() {
        return this.visor;
    }
    public String getExpresion() {
        return this.expresion;
    }

    public void onChangeVisor(String nuevoCaracter){
        if (this.padNum.contains(nuevoCaracter)) {
            if (this.visor.length() == 1 && this.padOp.contains(this.visor))
                this.visor = "";
            if (this.padNum.contains(nuevoCaracter)) {
                if ( !nuevoCaracter.equals(".")) {
                    if (!(this.visor.length() == 0 && nuevoCaracter.equals("0"))) {
                        this.visor += nuevoCaracter;
                        this.expresion += nuevoCaracter;
                    }
                } else if (!this.decimal && nuevoCaracter.equals(".")) {
                    if (this.visor.length() == 0) {
                        this.visor += "0";
                        this.expresion += "0";
                    }
                    this.visor += nuevoCaracter;
                    this.expresion += nuevoCaracter;
                    this.decimal = true;
                }
            }
        } else if (this.padOp.contains(nuevoCaracter)) {
            if ( (this.expresion.length()<2) &&
                    (this.visor.length()<2) &&
                    nuevoCaracter.equals("-") ) {
                this.visor = nuevoCaracter;
                this.expresion = nuevoCaracter;
            } else if ( this.expresion.length()>1 &&
                    this.visor.length()<2 &&
                    this.padOp.contains(this.visor)) {
                this.visor = this.validarOperador(nuevoCaracter);
                this.expresion = this.expresion.substring( 0, this.expresion.length()-1 ) +
                        this.validarOperador(nuevoCaracter);
            } else if ( this.expresion.length()>0 &&
                    this.padNum.contains(this.expresion.substring(this.expresion.length()-1))) {
                this.visor = nuevoCaracter;
                this.expresion += nuevoCaracter;
                this.decimal = false;
            }
        }
    }

    private String validarOperador(String operador) {
        if (operador.equals("x")) {
            return "*";
        } else if (this.padOp.contains(operador)) {
            return operador;
        }
        return "";
    }

    public void onClickClear() {
        this.visor= "";
        this.expresion= "";
    }

    public void onClickEqual() {
        MathJS math = new MathJS();
        if (this.expresion.length()<1 ||
                (this.padOp.contains(this.expresion.substring(this.expresion.length()-1)))) {
            this.expresion += "0";
        }
        String result = math.eval(this.expresion);
        this.expresion += "=";
        this.visor = result;
        math.destroy();
    }

}
