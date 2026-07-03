/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package arbolE;

/**
 *
 * @author Ibarra Hernandez Jeanely Fernanda
 * CLASE PARA ARMAR EL ÁRBOL
 * 
 * Parte 1. Análisis sintáctico
 * Parte 2. Análisis semántico
 * Parte 3. Código intermedio
 * Parte 4. Código objeto
 */
public class Nodo {
    //Atributos
    private String dato;
    private Nodo padre;
    private Nodo izq;
    private Nodo der;
    private String codigoIntermedio;
    private String lugar; // Temporales
    
    public Nodo (String dato){
        this.dato = dato;
    }//Constructor
    
    public Nodo(Nodo derecho, String dato, Nodo izquierdo) {
        this.dato = dato;
        this.izq = izquierdo;
        this.der = derecho;
        this.codigoIntermedio = "";
        this.lugar = "";
    }

    public String getDato() {
        return dato;
    }

    public Nodo getPadre() {
        return padre;
    }

    public Nodo getIzq() {
        return izq;
    }

    public Nodo getDer() {
        return der;
    }

    public String getCodigoIntermedio() {
        return codigoIntermedio;
    }

    public String getLugar() {
        return lugar;
    }

    public void setDato(String dato) {
        this.dato = dato;
    }

    public void setPadre(Nodo padre) {
        this.padre = padre;
    }

    public void setIzq(Nodo izq) {
        this.izq = izq;
    }

    public void setDer(Nodo der) {
        this.der = der;
    }

    public void setCodigoIntermedio(String codigoIntermedio) {
        this.codigoIntermedio = codigoIntermedio;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }  
    
}
