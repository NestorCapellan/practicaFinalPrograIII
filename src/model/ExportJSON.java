/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.io.File;
import java.io.IOException;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Nestor Capellan
 */
public class ExportJSON implements IRepository{
   
    //Se deben implementar todos los métodos de la interfaz pues
    //por definición todos los métodos de una interfaz
    //son abstractos y en los métodos abstractos no es
    //necesario escribir @Override 
    
    
    @Override
    public ArrayList<Conversacion> importar(File archivo) throws Exception {
         if(archivo.isFile() && archivo.exists()){
             try{
                Gson gson=new Gson();
                String json=new String(Files.readAllBytes(archivo.toPath()),StandardCharsets.UTF_8);
                Type tipoDeLista = new TypeToken<List<Conversacion>>() {}.getType();
                return gson.fromJson(json, tipoDeLista);
                }
                catch(IOException e){
                   throw new Exception("Se ha producido un problema al importar en JSON"+e.getMessage(),e);
                }
                }else{
                    return null;
                }
        }
         

     
    @Override
    public boolean exportar(ArrayList<Conversacion> lista,File archivo) throws Exception {
        try {
                Gson gson = new Gson();
                String json = gson.toJson(lista);
                Files.write(archivo.toPath(), json.getBytes(StandardCharsets.UTF_8));
                return true;
        }
        catch (IOException ex) {
            throw new Exception("Se ha producido un problema al exportar en JSON"+ex.getMessage(),ex);
           
        }
    
    }
    
    //para saber con repositorio estamos trabajando
    @Override
    public String getIdentifier(){
    return "Ha elegido exportar o importar en formato JSON";
    }

    
    
}
