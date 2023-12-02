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


/**
 *
 * @author Nestor Capellan
 */
public class Mensaje implements Serializable{
    private String emisor;
    private  long fecha;
    private String contenido;
    
    private static final long serialVersionUID=-87008705013848912L;
 

    public Mensaje(Instant fecha) {
        //establecemos el momento de ejecucion del constructor mensaje
        this.fecha = fecha.getEpochSecond();
    }
    //contructor sin atributos para importar de XML
    public Mensaje() {
    }
    
    
    // constructor alternativo:
    //public Mensaje(String emisor,Instant fecha,String contenido){
    //  this.fecha = fecha.getEpochSecond();
    //  this.emisor=emisor;
    //  this.contenido=contenido;
    //  }
   
   
    public String getLineMessage(){
        // Formatear la fecha y hora y la establecemos segun el formato [dd/MM/yy: HH:mm:ss]
        //devolvemos la cabecera de los mensajes en los registros
        LocalDateTime dateTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(this.fecha), ZoneId.systemDefault());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("[dd/MM/yy: HH:mm:ss]");
        String tiempoFormateado = dateTime.format(formatter);
        return String.format("%s [%s]: %s",this.emisor,tiempoFormateado,this.contenido);
    }
    
    public static String getMessageCSV(String mensaje){
    // metodo estatico que se aplica a la representacion en la conversacion
    //se aplica tanto a los mesajes del usuario como a los de la maquina
    return ">>> "+mensaje;
    }
    //seters y getters

    public String getEmisor() {
        return emisor;
    }

    public double getFecha() {
        return fecha;
    }

    public String getContenido() {
        return contenido;
    }

    public void setEmisor(String emisor) {
        this.emisor = emisor;
    }

    public void setFecha(Instant fecha) {
        this.fecha = fecha.getEpochSecond();
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }
   
    
    
}
