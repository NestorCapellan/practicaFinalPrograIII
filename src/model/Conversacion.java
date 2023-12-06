/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;


/**
 *
 * @author Nestor Capellan
 */
public class Conversacion implements Serializable{
    
  private String nombreLLM;
  private ArrayList<Mensaje> chatGeneral=new ArrayList<>();;
  private  long fechaInicio;
  private  long fechaFinal;
  
  private static final long serialVersionUID=-191344385794009611L;
 

  // al inicio de una conversacion se proporcionan el LLM utilizado y el tiempo de comienzo
  //como nos lo piden en segundos se utiliza el metodo getEpochSecond que mide
  // ese momento concreto en segundos teniendo en cuenta que el inicio es el 01/01/1970
  // ademas vamos a ir alamacenando los mensajes del usuario en un array y los del LLM en
  //otro, para más tarde introducirlos ordenados en el array ChatGeneral
  
    public Conversacion(String nombreLLM,long inicio){
        this.nombreLLM = nombreLLM;
        this.fechaInicio=inicio;
        
    }
    //necesitamos un constructor sin atributos para importar en XML
    public Conversacion() {
       
    }
    
  //----------------------------------
  //---------Nueva conversacion----------
  //------------------------------------
    public final String bienvenida(){
        String textoBienvenida=String.format("¿En qué te puedo ayudar ?, %s. %s",System.getProperty("user.name"),this.nombreLLM);
        Mensaje mensajeBienvenida=new Mensaje(Instant.now().getEpochSecond());
        mensajeBienvenida.setContenido(textoBienvenida);
        mensajeBienvenida.setEmisor("Agent");
        chatGeneral.add(mensajeBienvenida);
        return textoBienvenida;
    }
    
    public final String despedida(){
        String textoDespedida=String.format("Ha sido un placer hablar contigo, %s",System.getProperty("user.name"));
        Mensaje mensajeDespedida=new Mensaje(Instant.now().getEpochSecond());
        mensajeDespedida.setContenido(textoDespedida);
        mensajeDespedida.setEmisor("Agent");
        chatGeneral.add(mensajeDespedida);
        return textoDespedida;
    }
    
   //observemos que segun nuestra arquitectura de la vista, los mensajes se van a ir añadiendo al chatGeneral
    // ya ordenados en el tiempo
    public void setMensajesLLM(String mensaje){
     //También se podria establecer por constructor
     //Mensaje("Agent",Instant.now().getEpochSecond(),mensaje);
     Mensaje nuevomensaje=new Mensaje(Instant.now().getEpochSecond());
     nuevomensaje.setContenido(mensaje);
     nuevomensaje.setEmisor("Agent");
    chatGeneral.add(nuevomensaje);
    }
    public void setMensajesUsuario(String mensaje){
     Mensaje nuevomensaje=new Mensaje(Instant.now().getEpochSecond());
     nuevomensaje.setContenido(mensaje);
     nuevomensaje.setEmisor("Yo");
    chatGeneral.add(nuevomensaje);
    }
    
    public void setFechaFinal(long fechaFinal){
        this.fechaFinal=fechaFinal;
    }
    //-----------------------------------------------------
    //------------------------menu CRUD--------------------
    //-----------------------------------------------------
    
    public long duracion(){
        return this.fechaFinal-this.fechaInicio;
    }
    
   
    public String getResumeLine(int num){
         
         // saber el numero de mensajes totales y esto pertenece a la logica de la aplicación
         // pasa el numero de la conversacion, fecha de inicio,
         // los primeros 20 caracteres de la conversacion(por diseño siempre es la misma frase de bienvenida)
         //y la duracion de la misma
        try{
        return String.format("%3d. [%s] |Mensajes:%3d | %1.20s |Duracion: %3d seg",num 
                ,this.getFechaInicioFormato(),chatGeneral.size(),chatGeneral.get(0).getContenido(),this.duracion());
        }catch(ArrayIndexOutOfBoundsException ex){
            System.err.println("No se han conseguido ordenar la conversacion "+ ex.getMessage());
        }
       return null;
    }
    
    
    //establecemos que se comparan dos conversaciones por su tiempo de inicio al importar frases para que no 
    //aparezcan repetidas
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + (int) (this.fechaInicio ^ (this.fechaInicio >>> 32));
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Conversacion other = (Conversacion) obj;
        return this.fechaInicio == other.fechaInicio;
    }

   //----------------------------------------------
    //-----------setters y getters------------------
    //---------------------------------------------
    
    // es importante poner todos los getters de los atributos pues al importar y exportar
    // en xml reconocen los atributos por lo getters
    // en caso que un metodo tambien empieze con get pero no es un getter de atributo
    // lo estipulamos como @JsonIgnore para que xml no lo tenga en cuenta 
    
    public ArrayList<Mensaje> getChatGeneral() {
        return chatGeneral;
    }

    public long getFechaInicio() {
        return fechaInicio;
    }

    public long getFechaFinal() {
        return fechaFinal;
    }

    
    
    

    public void setFechaInicio(long fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getNombreLLM() {
        return nombreLLM;
    }
        

    @JsonIgnore
    public String getFechaInicioFormato() {
        LocalDateTime dateTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(this.fechaInicio), ZoneId.systemDefault());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("[dd/MM/yy: HH:mm:ss]");
        String tiempo = dateTime.format(formatter);
        return tiempo;
    }
    
       
    

    
}

   