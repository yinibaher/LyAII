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
public class ArbolJeanely {
    //Atributos
    Stack<Nodo> arbolNodo;
    Stack<String> caracter;    
        //Identificar entre OPERADOR Y OPERANDO
    
    final String espacios = "\t";
    final String aritmeticos = "+-*()/^=";
    final String variables = "abcdefghijklmnopqrstuvwxyz";
    private Nodo raiz;
    
    HashMap<String,String> tablaSimbolos;
    HashMap<String,String> erroresSemanticos;
    HashMap<String,String> producciones; 
    ArrayList<String> reglasEjecutadas;
    int paso; 
    
    
    public ArbolJeanely(){
        reglasEjecutadas = new ArrayList(); 
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
        Nodo derecho = arbolNodo.pop();
        Nodo izquierdo = arbolNodo.pop();        
        String operador = caracter.pop();
        
        arbolNodo.push(new Nodo(izquierdo,operador,derecho));
        
        String reglaE = "E.nodo = new Nodo("+operador+",E1.nodo,T.Nodo)";
        reglasEjecutadas.add("P" + paso + ": "+ reglaE); 
    }//guardar
    
    public Nodo crear(String expresion){
        //1. Considerar la expresión como un conjunto de tokens y separarlos
        paso = 0;
        StringTokenizer tokenizer = new StringTokenizer(expresion,espacios+aritmeticos,true);
                   
        //2. Mientras existan tokens
        while(tokenizer.hasMoreTokens()){
            String token;            
            token = tokenizer.nextToken();
            System.out.println("Token: "+token); 
            
            //3. Omitir espacios en blanco
            if(espacios.contains(token)){
                System.out.println("Omitiendo espacios...");
                
            //4. Si no es un operador aritmético
            }else if (!aritmeticos.contains(token)){
                //5. Meter el toekn a la pila de nodos
                arbolNodo.push(new Nodo(token));
                paso++;
                String regla = "T.nodo = new Hoja(id<"+token+">, id.entrada_"+token+")";
                reglasEjecutadas.add("P"+paso+": "+regla);
                
            //5.Tratar tokens dentro de paréntesis    
            }else if(token.equals(")")){
                //6. Guardar mientras no llegue al paréntesis que abrió            
                while(!caracter.empty() && !caracter.peek().equals("(")){
                    guardar();
                }//while
                caracter.pop();
            }else{
                if (!token.equals("(") && !caracter.empty()){
                    String exa = caracter.peek();
                    while(!exa.equals("(") && !caracter.empty() && aritmeticos.indexOf(exa)>=aritmeticos.indexOf(token)){
                        guardar();
                        if (!caracter.empty()){
                            exa = caracter.peek();
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
            }
        }//while
        raiz= arbolNodo.peek();
        return raiz;
    }//Nodo Crear   
    
    
}//class
