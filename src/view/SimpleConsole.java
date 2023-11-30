/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import static com.coti.tools.Esdia.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Conversacion;
import model.Mensaje;
/**
 *
 * @author Nestor Capellan
 */
public class SimpleConsole extends ApplicationView{
    
    
    //mensaje de bienvenida con el número de conversaciones cargadas en el registro de otras ejecuciones
    @Override
    public void showApplicationStart(String initInfo) {
        String encabezado="-".repeat(15)+"EMPIEZA A UTILIZAR JLLM HOY"+"-".repeat(15);
        System.out.println(initInfo);
        System.out.println(encabezado);
        System.out.println(String.format("-".repeat(57)));
                
    }
    //---------------------------------------
    //------------Menu Principal-------------
    //---------------------------------------
    @Override
    public void showMainMenu() {
        int opcion;
        String menu="-".repeat(19)+"MENU PRINCIPAL JLLM"+"-".repeat(19);
        do{
           
            System.out.println();
            System.out.println(menu);
             System.out.println(String.format("-".repeat(57)));
            System.out.println("pulse 1 para: Nueva conversacion");
            System.out.println("pulse 2 para: Gestión de conversaciones");
            System.out.println("pulse 3 para: Exportación o importación");
            System.out.println("pulse 4 para: terminar con jLLM");
            opcion=readInt("introduce la opción que desee-->");
            switch(opcion){
                case 1:
                    nuevaconversacion();
                    break;
                case 2: 
                    menuCRUD();
                    break;
                case 3:
                
                    try {
                        exportacion();
                    } catch (Exception ex) {
                        Logger.getLogger(SimpleConsole.class.getName()).log(Level.SEVERE, null, ex);
                    }
                
                    break;

                case 4:
                    getOut();
                    break;
                default:
                    System.err.println("Todavía no tenemos suficiente financiación como para tener más  opciones. Opción no disponible ");
                    break;
            }
          
        }while(opcion!=4);
    }
    // las funciones están estipuladas private porque solo tiene sentido
    // que se acceden desde la vista y desde ningún otro sitio
 private void getOut(){ 
        boolean salir = siOno("¿Esta seguro de que desa salir?");
        if (salir){
            System.out.println("Hasta la próxima....");
        }
    } 
 
//---------------------------------------
//------------nueva conversacion-------------
//---------------------------------------
private void nuevaconversacion(){
    // creamos una nueva conversacion y nos devuelve el número 
    // el cual le corresponde esa conversacion en el array de conversaciones
    // para asignar correctamente los mensajes a la conversacion correspondiente
    System.out.println(String.format("-".repeat(57)));
    int aux=c.nuevaconversacion();
    // aux nos devuelve el tamaño del array de conversaciones
    int num=aux-1;
    String salir="/salir";
    String mensaje;
    System.out.println(">>>"+c.mensajeBienvenida(num));
    do{
        mensaje=readString_ne(">>>");
        if(!mensaje.equalsIgnoreCase(salir)){
          //mientras no sea la palabra clave para salir, guardamos cada mensaje en cada conversacion
            c.guardarMensajeUsuario(num,mensaje);
            System.out.println(c.contestacion(num,mensaje));
        }
        else{
            //tambien guardamos la palabra clave y añadimos un final prefijado
            c.guardarMensajeUsuario(num,mensaje);
            c.setEndConversacion(num);
        }
    }while(!mensaje.equalsIgnoreCase(salir));
    System.out.println(">>>"+c.mensajeDespedida(num));
    System.out.println(String.format("-".repeat(57)));
}
    
//----------------------------------------------
//------------Gestión Conversaciones-------------
//-----------------------------------------------
private void menuCRUD(){
String encabezado="-".repeat(17)+"GESTIÓN CONVERSACIONES"+"-".repeat(17);
System.out.println(String.format("-".repeat(57)));
int opcion;
do{
    System.out.println(encabezado);
   
    System.out.println("MENU ");
    System.out.println("1-Listar conversaciones");
    System.out.println("2-Eliminar alguna conversación");
    System.out.println("3-Volver al menu principal");
    opcion=readInt("introduzca la opción que desee-->");
    switch(opcion){
          case 1:
                    listarConver();
                    break;
                case 2: 
                    eliminarConver();
                    break;
                case 3:
                    System.out.println("volviendo a menu principal...");
                    break;
                default:
                    System.err.println("Todavía no tenemos suficiente financiación como para tener más  opciones. Opción no disponible ");
                    break;
    }
    
}while(opcion!=3);

}


private void listarConver(){
   System.out.println();
   ArrayList<Conversacion> conversaciones=c.getConversaciones();
   for(Conversacion conversacion:conversaciones){
   conversacion.ordenarConversacion();
   }
   boolean ver;
   boolean salida;
   do{
        System.out.println(String.format("-".repeat(16)+"LISTADO DE CONVERSACIONES"+"-".repeat(16)));
        System.out.println();
        if(conversaciones.isEmpty()){
            System.out.println("Actualmente no hay ninguna conversacion registrada");
            salida=true;
        }
        else{
                for (int i=0;i<conversaciones.size();i++){
                    // cabeceras resumen de las conversaciones, listadas en pantalla a partir de la numero uno
                    System.out.println(conversaciones.get(i).getResumeLine(i+1));
                }
                System.out.println();
                ver=siOno("¿Desea ver alguna conversacion?");
                if(ver){
                    System.out.println();
                    int opcion=readInt("indica el numero de la conversacion que desea ver",1,conversaciones.size());
                    System.out.println();
                    // las listas por consola estan listadas empezando por 1
                    System.out.println(String.format("| Conversacion del %s |",conversaciones.get(opcion-1).getFechaInicioFormato()));
                    System.out.println(String.format("-".repeat(57)));
                        for(Mensaje mensaje:conversaciones.get(opcion-1).getConversacionOrdenada()){
                             System.out.println(mensaje.getLineMessage());
                        }  
                    salida=false;
                    System.out.println(String.format("-".repeat(57)));
                }
                else{
                    salida=true;
                    System.out.println();
                    System.out.println("volvemos al menu de gestión de conversaciones...");
                    System.out.println();
                }
        }
    
    }while(!salida);   

    
 }   
    
 private void eliminarConver(){
   
    boolean salir;
    boolean salida;
    do{
         ArrayList<Conversacion> conversaciones=c.getConversaciones();
        System.out.println(String.format("-".repeat(16)+"ELIMINAR DE CONVERSACIONES"+"-".repeat(15)));
        System.out.println();
        if(conversaciones.isEmpty()){
            System.out.println("Actualmente no hay ninguna conversacion registrada");
            salida=true;
        }
        else{
            for(int i=0;i<conversaciones.size();i++){
                System.out.println(conversaciones.get(i).getResumeLine(i+1));
            }
            System.out.println();
            int opcion=readInt("indica el numero de la conversacion que desea eliminar",1,conversaciones.size());
            if(c.eliminarConversacion(opcion-1)){
                  System.out.println("se ha eliminado con éxito la conversación");
            }else{
                  System.err.println("no se ha podido eliminar la conversación");
            }  
            salir=siOno("desea eliminar alguna conversacion mas?");
            if(salir){
                salida=false;
            }
            else{
                salida=true;
            }
            System.out.println(String.format("-".repeat(40)));
        }
    }while(!salida);   

 
 }
private void exportacion() throws Exception{
    String encabezado="-".repeat(19)+"E/S CONVERSACIONES"+"-".repeat(19);System.out.println(String.format("-".repeat(57)));
    int opc;
    do{
        System.out.println(encabezado);
        System.out.println(String.format("-".repeat(57)));
        System.out.println("MENU ");
        System.out.println("1-Exportar");
        System.out.println("2-Importar");
        System.out.println("3-Volver al menu principal");
        opc=readInt("introduzca la opción que desee-->");
        switch(opc){
                case 1:
                    exportarConversaciones();
                    break;
                case 2: 
                    importarConversaciones();
                    break;
                case 3:
                    System.out.println("volviendo a menu principal...");
                    break;
                default:
                    System.err.println("Todavía no tenemos suficiente financiación como para tener más  opciones. Opción no disponible ");
                    break;
        }

    }while(opc!=3);
}
private void exportarConversaciones() throws Exception{
    System.out.println();
    System.out.println(c.exportacionBienvenida());
    if(!c.getConversaciones().isEmpty()){
        System.out.println(String.format("Se van a exportar %d conversaciones...",c.getConversaciones().size()));
        System.out.println();
        if(c.exportarConversaciones()){
            System.out.println(String.format("Se han exportado todas correctamente en %s",c.getRutaArchivoExportado()));
              
        }
        else{
            System.err.println("No se han conseguido exportar bien");
        }
    }
    else{
            System.out.println("no hay conversaciones en el registro para exportar");
    }
    
}
private void importarConversaciones() throws Exception{
    System.out.println(c.exportacionBienvenida());
    System.out.println("Importando conversaciones.... ");
    if(c.importarConversaciones()){
        System.out.println(String.format("Se han importado todas conversaciones procendentes de %s",c.getRutaArchivoImportado()));
         System.out.println(String.format("Conversaciones actuales: %d",c.getConversaciones().size()));
    }
    else{
       System.err.println("No se han conseguido importar bien");
    }

}    

    @Override
public void showApplicationEnd(String endInfo) {
        String despedida="-".repeat(15)+"GRACIAS POR CHARLAR CONMIGO"+"-".repeat(15);
        System.out.println(endInfo);
        System.out.println(despedida);
        
    }
    
}
