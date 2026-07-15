;Ibarra Hernandez Jeanely Fernanda
.MODEL SMALL
.STACK
.DATA
    A   DW  4 ;DW = DEFINE WORD = 16 BITS  
    B   DW  2 ;
    R   DW  0
.CODE 
    MOV AX,@DATA;INICIALIZACION
    MOV DS,AX
    
    XOR AX,AX ; INICIALIZAR A CERO
    XOR BX,BX ;        
    MOV AX,A  ; MOVER AX, el contenido de A
    MOV BX,B  ;
    ;ADD A,B  ; SUMAR A + B No se puede
    
    ADD AX,BX   ;AX = AX + BX
    MOV R,AX    

END