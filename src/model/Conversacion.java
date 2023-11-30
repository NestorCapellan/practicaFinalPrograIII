/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

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
  private ArrayList<Mensaje> mensajesLLM;
  private ArrayList<Mensaje> mensajesUsuario;
  private ArrayList<Mensaje> chatGeneral;
  private  long fechaInicio;
  private  long fechaFinal;
  
  private static final long serialVersionUID=-191344385794009611L;
 

  // al inicio de una conversacion se proporcionan el LLM utilizado y el tiempo de comienzo
  //como nos lo piden en segundos se utiliza el metodo getEpochSecond que mide
  // ese momento concreto en segundos teniendo en cuenta que el inicio es el 01/01/1970
  // ademas vamos a ir alamacenando los mensajes del usuario en un array y los del LLM en
  //otro, para más tarde introducirlos ordenados en el array ChatGeneral
  
    public Conversacion(String nombreLLM,Instant inicio){
        this.nombreLLM = nombreLLM;
        this.fechaInicio=inicio.getEpochSecond();
        mensajesLLM=new ArrayList<>();
        mensajesUsuario=new ArrayList<>();
        chatGeneral=new ArrayList<>();
    }
    //necesitamos un constructor sin atributos para importar en XML
    public Conversacion() {
    }
    
  
    public final String bienvenida(){
        String textoBienvenida=String.format("¿En qué te puedo ayudar ?, %s. %s",System.getProperty("user.name"),this.nombreLLM);
        Mensaje mensajeBienvenida=new Mensaje(Instant.now());
        mensajeBienvenida.setContenido(textoBienvenida);
        mensajeBienvenida.setEmisor("Agent");
        mensajesLLM.add(mensajeBienvenida);
        return textoBienvenida;
    }
    
    public final String despedida(){
        String textoDespedida=String.format("Ha sido un placer hablar contigo, %s",System.getProperty("user.name"));
        Mensaje mensajeDespedida=new Mensaje(Instant.now());
        mensajeDespedida.setContenido(textoDespedida);
        mensajeDespedida.setEmisor("Agent");
        mensajesLLM.add(mensajeDespedida);
        // como siempre es el ultimo mensaje que se va almacena en el chat lo que hacemos es crear aqui 
        //el numero de posiciones null totales del chat que necesitaremos para el metodo set del ArrayList
        //tambien se podria utilizar el método de ArrayList add(int i,Object ob) que añade el objeto en la posicion i
         for(int i=0;i<mensajesLLM.size()+mensajesUsuario.size();i++){
         chatGeneral.add(null);
        }
        return textoDespedida;
    }
    
   
    
    public void setMensajeLLM(String mensaje){
     //También se podria establecer por constructor
     //Mensaje("Agent",Instant.now(),mensaje);
     Mensaje nuevomensaje=new Mensaje(Instant.now());
     nuevomensaje.setContenido(mensaje);
     nuevomensaje.setEmisor("Agent");
    mensajesLLM.add(nuevomensaje);
    }
    public void setMensajeUsuario(String mensaje){
     Mensaje nuevomensaje=new Mensaje(Instant.now());
     nuevomensaje.setContenido(mensaje);
     nuevomensaje.setEmisor("Yo");
    mensajesUsuario.add(nuevomensaje);
    }
    
    public void ordenarConversacion(){
      /*for(Mensaje mensaje:mensajesLLM){
          System.out.println(mensaje.getLineMessage());     
      }
        System.out.println();
         for(Mensaje mensaje:mensajesUsuario){
          System.out.println(mensaje.getLineMessage());     
      }
        //como tiene mensaje de bienvenida y despedida mensajesLLM tiene siempre mas elementos
        */
    //ordenamos los mensajes. también lo podriamos haber hecho añadiendo los dos arrays y con
    //Comparator<Mensaje> comparatordate = Comparator.comparing(Mensaje::getFecha);
    //Collections.sort(chatGeneral, comparatordate);
    int cont1=0,cont2=0;
        
    //para utilizar set necesitamos que haya instaciadas como null
    for(int i=0;i<mensajesLLM.size()+mensajesUsuario.size();i++){
        if(i%2==0){
         chatGeneral.set(i,mensajesLLM.get(cont1));
         cont1++; 
        }else{
        chatGeneral.set(i,mensajesUsuario.get(cont2));
        cont2++;
        }
       
    }
        
    //chatGeneral.add(mensajesLLM.get(mensajesLLM.size()-1).getLineMessage());
    }
    public String getFechaInicioFormato() {
         LocalDateTime dateTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(this.fechaInicio), ZoneId.systemDefault());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("[dd/MM/yy: HH:mm:ss]");
        String tiempo = dateTime.format(formatter);
        return tiempo;
    }

    public void setFechaFinal(Instant fechaFinal){
        this.fechaFinal=fechaFinal.getEpochSecond();
    }
    public long duracion(){
        return this.fechaFinal-this.fechaInicio;
    }
    public ArrayList<Mensaje> getConversacionOrdenada(){
        //no haria falta volver a llamar a ordenarConversacion
        //porque en la vista getResumeLine va primero
        return chatGeneral;
}
    
    public String getResumeLine(int num){
         // llama primero a chat ordenado porque la cabecera necesita
         // saber el numero de mensajes totales
         // pasa el numero de la conversacion, fecha de inicio,
         // los primeros 20 caracteres de la conversacion(por diseño siempre es la misma frase de bienvenida)
         //y la duracion de la misma
         //ordenarConversacion();
        return String.format("%3d. [%s] |Mensajes:%3d | %1.20s |Duracion: %3d seg",num 
                ,this.getFechaInicioFormato(),chatGeneral.size(),mensajesLLM.get(0).getContenido(),this.duracion());
    }
    
    
    
    //establecemos que se comparan dos conversaciones por su tiempo de inicio
    //esto es un cambio
    
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

    public ArrayList<Mensaje> getChatGeneral() {
        return chatGeneral;
    }

    public long getFechaInicio() {
        return fechaInicio;
    }

    public long getFechaFnal() {
        return fechaFinal;
    }

    public void setChatGeneral(ArrayList<Mensaje> chatGeneral) {
        this.chatGeneral = chatGeneral;
    }

    public void setFechaInicio(long fechainicio) {
        this.fechaInicio = fechainicio;
    }

    public void setFechaFinal(long fechafinal) {
        this.fechaFinal = fechafinal;
    }
       
    

    
}

   