/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import controller.Controller;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Paths; 
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


/**
 *
 * @author Nestor Capellan
 */
public class Model {
    private Controller c;
    private ILLM LLM;
    private IRepository repository;
    File ficheroEstadoSerializado;
    File archivoexportar;// el archivo donde se exportan las conversaciones
    File archivoimportar;// el archivo donde se importan las conversaciones
    private ArrayList<Conversacion> conversaciones;
    
    
    //establecemos en el constructor del modelo las rutas de importacion y exportacion

    public Model(ILLM LLM, IRepository repository) {
        this.LLM = LLM;
        this.repository = repository;
        this.ficheroEstadoSerializado=Paths.get(System.getProperty("user.home"),"Desktop","jLLM","jLLM","jLLM.bin").toFile();
        conversaciones=new ArrayList<>();
        if(this.repository instanceof ExportJSON){
        this.archivoexportar=Paths.get(System.getProperty("user.home"),"Desktop","jLLM","jLLM","output.json").toFile();
        this.archivoimportar=Paths.get(System.getProperty("user.home"),"Desktop","jLLM","jLLM","input.json").toFile();
        }else{
        this.archivoexportar=Paths.get(System.getProperty("user.home"),"Desktop","jLLM","jLLM","output.xml").toFile();
        this.archivoimportar=Paths.get(System.getProperty("user.home"),"Desktop","jLLM","jLLM","input.xml").toFile();  
        }
    }
    
    // asociamos el controlador para comunicar errores a la vista
    public void setController(Controller c){
    this.c=c;
    }
    
  //--------------Serializacion----------------------
  //-------------------------------------------------
    public int loadInitialState() throws Exception{
        // haremos distinción entre las excepciones
    if(ficheroEstadoSerializado.exists() && ficheroEstadoSerializado.isFile()){
            ObjectInputStream ois=null;
        try{
           FileInputStream fir= new FileInputStream(ficheroEstadoSerializado);
           BufferedInputStream oif=new BufferedInputStream(fir);
           ois=new ObjectInputStream(oif);
           this.conversaciones= (ArrayList<Conversacion>) ois.readObject();
        }catch(IOException  ex){
            c.errores("ERROR AL IMPORTAR ARCHIVO BINARIO",ex.getMessage());
        }finally{
            if(ois!=null){
                try{
                     ois.close();
                }catch(IOException ex){
                    c.errores("ERROR AL CERRAR OBJECTOUTPUTSTREAM AL IMPORTAR", ex.getMessage());
                }
            }
        }
        return this.conversaciones.size();// devuelve el numero de conversaciones que se han tenido
    }else{
         c.errores("No existe el archivo de deserialización");
         return -1;
    } 
    }
    
    public String loadFinalState() throws Exception{
     // haremos distinción entre las excepciones
        ObjectOutputStream oos=null;
        try{
            FileOutputStream foor= new FileOutputStream(ficheroEstadoSerializado);
            BufferedOutputStream oof=new BufferedOutputStream(foor);
            oos=new ObjectOutputStream(oof);
            oos.writeObject(this.conversaciones);
         }catch(IOException ex){
             c.errores("ERROR AL IEXPORTAR ARCHIVO BINARIO",ex.getMessage());
             return "err1";
         }finally{
             if(oos!=null){
                try{
                    oos.close();
                }catch(IOException ex){
                    c.errores("ERROR AL CERRAR OBJECTOUTPUTSTREAM AL EXPORTAR", ex.getMessage());
                    return "err1";
                }
             }
         }
        return "err0";
    }
    
 
    //------------nueva conversacion----------------
    //----------------------------------------------
public int nuevaconversacion(){
    //añade la conversacion en último lugar
    conversaciones.add(new Conversacion(this.LLM.getIdentifier(),Instant.now()));
    
      //esto devuelve el numero de conversaciones actuales(la que se acaba de crear también)y el la vista se resta 1
    return this.conversaciones.size();
}

public String mensajeBienvenida(int num) {
   try{
   return conversaciones.get(num).bienvenida();
   }catch(ArrayIndexOutOfBoundsException ex){
       c.errores("ACCEDIENDO A CONVERSACIÓN TODAVIA NO CREADA",ex.getMessage());
       return null;
   }
}

public void guardarMensajeUsuario(int num,String mensaje){
  try{
    conversaciones.get(num).setMensajeUsuario(mensaje);
   }catch(ArrayIndexOutOfBoundsException ex){
       c.errores("ACCEDIENDO A CONVERSACIÓN TODAVIA NO CREADA",ex.getMessage());
   }
}

public String contestacion(int num,String mensaje){
    //el LLM nos devuelve un mensaje siguiendo una lógica y almacenamos su respuesta
   String respuesta=this.LLM.speak(mensaje);
   if(respuesta!=null){
   conversaciones.get(num).setMensajeLLM(respuesta);
   //estamos devolviendo el mensaje sin la hora ni el nombre del emisor, no tendría mucho sentido poner la hora en un mensaje en directo,
   //solo se enseña la hora al hacer el registro
   // ademas el return devuelve el mensaje con el formato del dialogo
   }
   return Mensaje.getMessageCSV(respuesta);
}

public String mensajeDespedida(int num){
   return conversaciones.get(num).despedida();
}

public void setEndConversacion(int num){
    //establece cuando ha terminado la conversacion para calcular la duración
    conversaciones.get(num).setFechaFinal(Instant.now());
}

    
//----------------menu CRUD--------------------
//----------------------------------------------
public ArrayList<Conversacion> getConversaciones(){
    //para mantener la logica de nuestro negocio y evitar que la
    //vista modifique el array original, devolveremos una copia de las conversaciones
    ArrayList<Conversacion> copia=new ArrayList<>();
    for(Conversacion conversacion:this.conversaciones){
        copia.add(conversacion);
    }
    //Importante devolvemos la conversaciones ordenadas segun la fecha de inicio
    // aunque segun la arquitectura empleada en teoria ya tendria que estar en orden
    Comparator<Conversacion> comparatordate = Comparator.comparing(Conversacion::getFechaInicio);
    Collections.sort(copia, comparatordate);
return copia;
}
public boolean eliminarConversacion(int num) {
    // utilizamos el metodo remove que toma como argumento un objeto y devuelve boolean
    // elimina la conversacion deseada
    try{
        conversaciones.remove(conversaciones.get(num));
        return true;  
    }catch(ArrayIndexOutOfBoundsException ex){
       c.errores("ACCEDIENDO A CONVERSACIÓN TODAVIA NO CREADA",ex.getMessage());
       return false;
   }
    
} 




//----------------------------------
//-------exportacion----------------
//----------------------------------

public String exportacionBienvenida(){
    //nos recuerda con el LLM que estamos trabajando
    return this.repository.getIdentifier();
}
public boolean exportarConversaciones(){
    //nos redirigimos al metodo del LLM asociado
    return this.repository.exportar(this.conversaciones,archivoexportar);
}

public boolean importarConversaciones(){
    // implementamos las conversaciones dependiendo si ya estan o no segun su fecha de inicio con HashCode y equals
    ArrayList<Conversacion> conversacionesImportadas=this.repository.importar(archivoexportar);
    int cont=0;
    if(conversacionesImportadas!=null){
            for(Conversacion conversacionImportada:conversacionesImportadas){
                if(!conversaciones.contains(conversacionImportada)){
                    this.conversaciones.add(cont,conversacionImportada);
                }
                cont++;
            }
            return true;
    }
    else{
        return false;
        }
}
public String getRutaArchivoExportado(){
    return this.archivoexportar.getAbsolutePath();
}
public String getRutaArchivoImportado(){
    return this.archivoimportar.getAbsolutePath();
}



}





