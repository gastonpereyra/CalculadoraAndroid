package com.gaston.cursoandroid.calculadoraandroid;

public class Tecla {
    //Atributos
    public static final String NUM_DOT= ".";
    public static final String[] NUM_PAD= {"0","1","2","3","4","5","6","7","8","9", NUM_DOT};
    private static final String OPE_MUL= "*";
    public static final String[] OPE_PAD= {"+","-","/","x", OPE_MUL};
    public static final String PAD_RES= "=";
    // Metodos
    // Si es un Numero
    public static final boolean esNUM(String pad) {
        for(String num : NUM_PAD){
            if (num.equals(pad))
                return true;
        }
        return false;
    }
    // Si es una Operacion
    public static final boolean esOPE(String pad) {
        for(String ope : OPE_PAD){
            if (ope.equals(pad))
                return true;
        }
        return false;
    }
    // Si es un punto decimal
    public static final boolean esDOT(String pad) {
        return NUM_DOT.equals(pad);
    }
    // Si ya hay resultado
    public static final boolean hayRES(String pad) { return PAD_RES.equals(pad); }
    // Poner los Operadores Correctos
    public static final String validarOPE(String pad) {
        if (esOPE(pad)) {
            if (pad.equals("x"))
                return OPE_MUL;
            else
                return pad;
        } else
            return "";
    }
    // Poner los Numeros Correctos
    public static final String validarNUM(String pad) {
        return esNUM(pad) ? pad : "";
    }
}
