/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package arbolE;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;
import java.util.StringTokenizer;

/**
 *
 * @author Ibarra Hernandez Jeanely Fernanda
 */
public class Arbol {
    //Atributos
    Stack<Nodo> arbolNodo;
    Stack<String> caracter;    
        //Identificar entre OPERADOR Y OPERANDO
    
    final String espacios = "\t";
    final String aritmeticos = "+-*()/^=";
    final String variables = "abcdefghijklmnopqrstuvwxyz";
    private Nodo raiz;
    
    //30 de Junio
    String[] temporales ={"T1","T2","T3","T4","T5"};
    
    HashMap<String,String> tablaSimbolos;
    HashMap<String,String> erroresSemanticos;
    HashMap<String,String> producciones;
    int paso;
    
    ArrayList<String> reglasEjecutadas; //1 de Julio
    
    public Arbol(){
        reglasEjecutadas = new ArrayList(); //1 de Julio
        
        tablaSimbolos = new HashMap();
        erroresSemanticos = new HashMap();
        producciones = new HashMap();
        
        arbolNodo = new Stack<Nodo>();
        caracter = new Stack<String>();
        paso = 0;
    }//constructor
    
    public String getReglasEjecutadas(){
        String reglasE = "";
        for(int i=0;i<reglasEjecutadas.size();i++){
            System.out.println("Reglas Ejecutadas" +
                reglasEjecutadas.get(i));
            reglasE += reglasEjecutadas.get(i) + "\n";            
        }//for
        return reglasE;
    }//1 de Julio
    
    public void AgregaVaLex(String lexema, String valor){
    }
    
    public String RegresaVaLex(String lexema){
        return this.tablaSimbolos.get(lexema);
    }
    
    public void guardar(){
        paso++;
        Nodo izquierdo = (Nodo) arbolNodo.pop();
        Nodo derecho = (Nodo) arbolNodo.pop();
        String operador = caracter.peek();
            //  Peek sirve para mirar el elemento que está en la cima o 
            //  al principio sin sacarlo de la estructura.
        
        arbolNodo.push(new Nodo(derecho,caracter.pop(),izquierdo));
        if (operador.equals("+")){
            String reglaE = "E.nodo = new Nodo(+,E1.nodo,T.Nodo)";
            reglasEjecutadas.add("P" + paso + ": "+ reglaE); 
        }
        
        if (operador.equals("*")){
            String reglaE = "E.nodo = new Nodo(*,E1.nodo,T.Nodo)";
            reglasEjecutadas.add("P" + paso + ": "+ reglaE);
        }
        
        if (operador.equals("/")){
            String reglaE = "E.nodo = new Nodo(/,E1.nodo,T.Nodo)";
            reglasEjecutadas.add("P" + paso + ": "+ reglaE);
        }
        
        if (operador.equals("-")){
            String reglaE = "E.nodo = new Nodo(-,E1.nodo,T.Nodo)";
            reglasEjecutadas.add("P" + paso + ": "+ reglaE);
        }
    }//guardar
    
    public Nodo crear(String expresion){
        //1. Considerae la expresión como un conjunto de tokens
            // La clase StringTokenizer en Java (perteneciente al paquete java.util) 4
            // permite dividir una cadena de texto en partes más pequeñas llamadas 
            // tokens basándose en un conjunto de delimitadores.
        StringTokenizer tokenizer;
        String token;
        paso = 0;             
        
        //2. Separación de tokens de la expresión
        tokenizer = new StringTokenizer(expresion,espacios+aritmeticos,true);
        
        //3. Mientras existan tokens
        while(tokenizer.hasMoreTokens()){
            //4. Omitir espacios en blanco
            token = tokenizer.nextToken();
            System.out.println("Token: "+token);          
            if(espacios.indexOf(token)>=0){
                System.out.println("Omitiendo espacios...");
            
            }else if (aritmeticos.indexOf(token)<0){//5. No es un operador aritmético
                //6. Extraer de la pila los términos que estaban
                arbolNodo.push(new Nodo(token));
                paso++;
                String regla = "T.nodo = new Hoja(id<"+token+">, id.entrada_"+token+")";
                reglasEjecutadas.add("P"+paso+": "+regla);
                
            }else if(token.equals(")")){
                //7. Tratar tokens que no son paréntesis             
                while(!caracter.empty() && !caracter.peek().equals("(")){
                    guardar();
                }//while
                caracter.pop();
            }else{
                if (!token.equals("(") && !caracter.empty()){
                    String exa = (String) caracter.peek();
                    while(!exa.equals("(") && !caracter.empty() && aritmeticos.indexOf(exa)>=aritmeticos.indexOf(token)){
                        guardar();
                        if (!caracter.empty()){
                            exa = (String) caracter.peek();
                        }//if
                    }//while
                }//if
                caracter.push(token);
            }//else              
        }//while
        while(!caracter.empty()){
            if(caracter.peek().equals("(")){
                caracter.pop();
            }else{
                guardar();
                raiz=(Nodo) arbolNodo.peek();
            }
        }//while
        return raiz;
    }//Nodo Crear
    
    
    
}//class
