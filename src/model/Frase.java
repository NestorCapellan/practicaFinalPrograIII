/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;




/**
 *
 * @author Nestor Capellan
 */
public class Frase {
   private String tipoFrase;
   private int extension;
   private String contenido;

    public Frase(String tipoFrase, int extension, String contenido) {
        this.tipoFrase = tipoFrase;
        this.extension = extension;
        this.contenido = contenido;
    }
   
   
    
    
    
 public static Frase getSplit(String linea,String delimitador){
     
    String[] chucks=linea.split(delimitador);
    if(chucks.length!=3){
    return null;
    }
    try{
        String tipoFrase=chucks[0];
        int extension=Integer.parseInt(chucks[1]);
        String contenido=chucks[2];
        Frase f=new Frase(tipoFrase,extension,contenido);
        return f;      
    }catch(NumberFormatException e){
        return null;
    
    }
    }

    public String getTipoFrase() {
        return tipoFrase;
    }

    public int getExtension() {
        return extension;
    }

    public String getContenido() {
        return contenido;
    }

    public void setTipoFrase(String tipoFrase) {
        this.tipoFrase = tipoFrase;
    }

    public void setExtension(int extension) {
        this.extension = extension;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }
 
 
    
 
    
}
