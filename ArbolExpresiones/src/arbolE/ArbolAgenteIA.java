/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package arbolE;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;
import java.util.StringTokenizer;
import javax.swing.JOptionPane;

/**
 *
 * @author jeane
 */
public class ArbolAgenteIA {
        //Atributos
    Stack<Nodo> arbolNodo;
    Stack<String> caracter;    
        //Identificar entre OPERADOR Y OPERANDO
    
    final String espacios = "\t";
    final String aritmeticos = "+-*()^=?+/";
    final String variables = "abcdefghijklmnopqrstuvwxyz";
    private Nodo raiz;
    String[] temporales ={"T1","T2","T3","T4","T5"};
    HashMap<String,String> tablaSimbolos;
    HashMap<String,String> erroresSemanticos;
    HashMap<String,String> producciones;
    HashMap<String,String> reglasSemanticas;
    int paso;
    String reglaSemantica;
    String r;    
    
    ArrayList<String> reglasEjecutadas;
    private ArrayList<String[]> tripletas;
    
    public String emu86; //15 de Julio
    
    public ArbolAgenteIA(){
        emu86 = "; IBARRA HERNANDEZ JEANELY FERNANDA \n"+
                ".MODEL SMALL \n" + 
                ".STACK \n" + 
                ".DATA \n";
        
        tablaSimbolos = new HashMap<>();
        erroresSemanticos = new HashMap<>();
        arbolNodo = new Stack<Nodo>();
        caracter = new Stack<String>();
        producciones = new HashMap<>();
        reglasSemanticas = new HashMap<>();          
        paso = 0;
        reglaSemantica="";
        r = "";
        reglasEjecutadas = new ArrayList<String>(); 
        tripletas = new ArrayList<String[]>();
    }//constructor
    
    private boolean esNumero(String texto){
        try{
            Double.parseDouble(texto);
            return true;
        }catch(NumberFormatException e){
            return false;
        }
    }
    
    public String getReglasEjecutadas(){
        String reglasE = "";
        for(int i=0;i<reglasEjecutadas.size();i++){
            System.out.println("Reglas Ejecutadas" +
                reglasEjecutadas.get(i));
            reglasE += reglasEjecutadas.get(i) + "\n";            
        }//for
        return reglasE;
    }//getReglasEjecutadas
    
    public void AgregaVaLex(String lexema, String valor){
         tablaSimbolos.put(lexema, valor);        
    }//AgregaVaLex
    
    public String RegresaVaLex(String lexema){
        return this.tablaSimbolos.get(lexema);
    }//RegresaVaLex
    
    private int obtenerPrioridad(String operador){
        switch (operador){
            case "^":
                return 3;
            case "*": case "/":
                return 2;
            case "+": case "-":
                return 1;
            case "=":
                return 0;
            default:
                return -1;// Para paréntesis u otros caracteres
        }
    }//obtenerPrioridad
    
    private void guardar() {
        if (arbolNodo.size() < 2 || caracter.empty()) {
            return;
        }

        paso++;
        r = "r" + paso;

        Nodo derecho = arbolNodo.pop();
        Nodo izquierdo = arbolNodo.pop();
        String operador = caracter.pop();

        Nodo nuevo = new Nodo(izquierdo, operador, derecho);

        double resultado = 0;

        switch (operador) {
            case "+":
                resultado = izquierdo.getValor() + derecho.getValor();
                break;

            case "-":
                resultado = izquierdo.getValor() - derecho.getValor();
                break;

            case "*":
                resultado = izquierdo.getValor() * derecho.getValor();
                break;

            case "/":
                if (derecho.getValor() != 0) {
                    resultado = izquierdo.getValor() / derecho.getValor();
                } else {
                    JOptionPane.showMessageDialog(null,
                            "Error: División entre cero.");
                    resultado = 0;
                }
                break;

            case "^":
                resultado = Math.pow(izquierdo.getValor(),
                                     derecho.getValor());
                break;

            case "=":
                resultado = derecho.getValor();
                break;
        }

        nuevo.setValor(resultado);

        arbolNodo.push(nuevo);
        tripletas.add(new String[]{
            operador,
            izquierdo.getDato(),
            derecho.getDato()
        });

        String reglaE = "E.nodo = new Nodo(" + operador + ",E1.nodo,T.Nodo)";
        reglasEjecutadas.add("P" + paso + ": " + reglaE);
    }
    
    public Nodo crear(String expresion){
        //1. Considerar la expresión como un conjunto de tokens y separarlos
        StringTokenizer tokenizer = new StringTokenizer(expresion,espacios+aritmeticos,true);
        String token;
        paso = 0;
        reglaSemantica = "";
        r = "";
        
        //2. Mientras existan tokens
        while(tokenizer.hasMoreTokens()){                        
            token = tokenizer.nextToken();
    
            System.out.println("Token: "+token); 
            
            //3. Omitir espacios en blanco
            if (espacios.contains(token)) {
                System.out.println("Omitiendo espacios...");
                continue;
            }
            //4. Si no es un operador aritmético
            if (!aritmeticos.contains(token)){
                // Si es una variable y aún no tiene valor
                if (esNumero(token)) {
                    // Es una constante numérica
                    AgregaVaLex(token, token);

                } else if (!tablaSimbolos.containsKey(token)) {
                    // Es una variable nueva
                    String valor = JOptionPane.showInputDialog( null,"Ingrese el valor de " + token + ":");
                    while (!esNumero(valor)) {
                        valor = JOptionPane.showInputDialog(null,"Solo se aceptan valores numéricos.\nIngrese el valor de " + token + ":");
                    }
                    AgregaVaLex(token, valor);
                }
                Nodo hoja = new Nodo(token);
                hoja.setValor(Double.parseDouble(tablaSimbolos.get(token)));
                arbolNodo.push(hoja);
                emu86+=token+" dw "+ (int)hoja.getValor() + "\n";
                paso++;
                String regla = "T.nodo = new Hoja(id<"+token+">, id.entrada_"+token+")";
                reglasEjecutadas.add("P"+paso+": "+regla);
            }else if(token.equals("(")) 
                caracter.push(token);                
            //5.Tratar tokens dentro de paréntesis    
            else if(token.equals(")")){
                //6. Guardar mientras no llegue al paréntesis que abrió            
                while(!caracter.empty() && !caracter.peek().equals("(")) guardar();                
                if (!caracter.empty()) caracter.pop(); //quita el "("
            }else{ // es un operador
                while(!caracter.empty() && !caracter.peek().equals("(")){
                    if(obtenerPrioridad(caracter.peek()) >= obtenerPrioridad(token))
                        guardar();
                    else break;
                }//while                
                caracter.push(token); 
            }
        }//while tokenizer
        while(!caracter.empty()){
            if(caracter.peek().equals("(")){
                caracter.pop();
            }else{
                guardar();                
            }
        }//while
        raiz= arbolNodo.peek();
        return raiz;
    }//Nodo Crear   
    
    public void mostrarTablaSimbolos(){
        System.out.println("--------------------------------");
        System.out.println("Tabla de Simbolos");
        System.out.println("|Lexema\t|Valor\t|");
        System.out.println("--------------------------------");

        for(String lexema : tablaSimbolos.keySet()){
            System.out.println("|" + lexema + "\t|" + tablaSimbolos.get(lexema) + "\t|");
        }
        System.out.println("--------------------------------");
    }
    
    
    public Nodo convertirAGAD(Nodo raizAST) {
        HashMap<String, Nodo> tabla = new HashMap<>();
        return convertir(raizAST, tabla);
    }

    private Nodo convertir(Nodo n, HashMap<String, Nodo> tabla) {
        if (n == null) return null;

        if (n.getIzq() == null && n.getDer() == null) {
            String clave = "HOJA#" + n.getDato();
            Nodo existente = tabla.get(clave);
            if (existente != null) return existente; // reutiliza
            tabla.put(clave, n);
            return n;
        }

        // Procesar hijos primero (post-orden): así al llegar al padre
        // ya sabemos si los hijos son nodos compartidos o no.
        Nodo izqNuevo = convertir(n.getIzq(), tabla);
        Nodo derNuevo = convertir(n.getDer(), tabla);

        // Reasignar hijos (puede que ahora apunten a nodos ya existentes)
        n.setIzq(izqNuevo);
        n.setDer(derNuevo);

        String clave = n.getDato() + "#" 
                     + System.identityHashCode(izqNuevo) + "#" 
                     + System.identityHashCode(derNuevo);

        Nodo existente = tabla.get(clave);
        if (existente != null) return existente; 

        tabla.put(clave, n);
        return n;
    }
    
    public ArrayList<String[]> getTripletas() {
        return tripletas;
    }
}//clase
