/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.util.ArrayList;
import model.Conversacion;
import model.Model;
import view.ApplicationView;
import view.SimpleConsole;

/**
 *
 * @author Nestor Capellan
 */
public class Controller {
    Model model;
    ApplicationView view;

    public Controller(Model model, ApplicationView view) {
        this.model = model;
        this.view = view;
        this.model.setController(this);
        view.setController(this);
    }
   
    public void initApplication(){
        //el grueso del programa está en esta función
        // aqui se carga la informacion registrada en usos 
        //anteriores (persistencia), se ejecuta el programa
        // y se guardan los cambios realizados.
        // vamos a distinguir las excepciones y errores
    if(model.loadInitialState()>0){
            view.showApplicationStart(String.format("Se han generado %d conversaciones pasadas", model.loadInitialState()));
    }
    else if(model.loadInitialState()==0){
            view.showApplicationStart("No se han cargado conversaciones antiguas porque nunca hemos hablado o se han eliminado todas");
    }
    else{
        view.showApplicationStart("ERROR AL CARGAR SERIALIZACION");
    }
   
    view.showMainMenu();
    
    switch(model.loadFinalState()){
        case "err0":
            view.showApplicationEnd("se han guardado todas las conversaciones");break;
        case "err1":
            view.showApplicationEnd("no se han podido guardar las conversaciones actuales");break;
        default:
            view.showApplicationEnd("no se han cargado conversaciones antiguas por un error desconocido");break;
    }
}

  //---------exccepciones-------------
  //--------------------------------------
    
    //Al acceder a métodos específicos de esa vista, tenemos que hacer un casting
   public void errores(String mensajeError,String error){
    SimpleConsole simpleConsole=(SimpleConsole)this.view;
   simpleConsole.erroresSerializable(mensajeError, error);
   }  
    
   public void errores(String mensajeError){
   SimpleConsole simpleConsole=(SimpleConsole)this.view;
   simpleConsole.erroresSerializableFichero(mensajeError);
   }
    
    
    
  //---------nuevaconversaion-------------
  //--------------------------------------
public int nuevaconversacion(){
    return model.nuevaconversacion();
}
public String mensajeBienvenida(int num){
return model.mensajeBienvenida(num);
}

public String contestacion(int num,String mensaje)throws ArrayIndexOutOfBoundsException {
return model.contestacion(num,mensaje);
}
public void setFinalConversacion(int num)throws ArrayIndexOutOfBoundsException{
model.setFinalConversacion(num);
}

public String mensajeDespedida(int num){
return model.mensajeDespedida(num);
}
public void guardarMensajeUsuario(int num,String mensaje) throws ArrayIndexOutOfBoundsException{
model.guardarMensajeUsuario(num,mensaje);
}


//-------------menu CRUD-------------------
//-----------------------------------------
public ArrayList<Conversacion> getConversaciones(){
   return model.getConversaciones();
}

public boolean eliminarConversacion(int num){
return model.eliminarConversacion(num);
}


//-------exportacion e importacion---------
//-----------------------------------------
public String exportacionBienvenida(){
return model.exportacionBienvenida();
}
public boolean exportarConversaciones(){
return model.exportarConversaciones();
}

public boolean importarConversaciones(){
return model.importarConversaciones();
}
public String getRutaArchivoExportado(){
 return model.getRutaArchivoExportado();
}
public String getRutaArchivoImportado(){
return model.getRutaArchivoImportado();
}
}