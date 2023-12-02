/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;


import java.io.FileNotFoundException;
import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;



/**
 *
 * @author Nestor Capellan
 */
 public class RandomCSVLLM implements ILLM {

    private final Path ruta=Paths.get(System.getProperty("user.home"),"Desktop","jLLM","randomCSV.txt");
    private final String delimitador = ",";
    private ArrayList<Frase> frases=new ArrayList<>();
    private final String[] tipo={"refran","saludo","despedida","afirmacion","negacion","sorpresa","pregunta","respuesta"};
    private final ArrayList<Frase> respuestas=new ArrayList<>();
    private final ArrayList<Frase> saludos=new ArrayList<>();
    private final ArrayList<Frase> despedidas=new ArrayList<>();
    private final ArrayList<Frase> refranes=new ArrayList<>();
    private final ArrayList<Frase> afirmaciones =new ArrayList<>();
    private final ArrayList<Frase> negaciones=new ArrayList<>();
    private final ArrayList<Frase> preguntas=new ArrayList<>();
    private final ArrayList<Frase> sorpresas=new ArrayList<>();
    
  
    private ArrayList<Frase> importar(){
        //importamos las frases del csv con toda la teoria de importar de ficheros planos
        ArrayList<Frase> frasesFicha=new ArrayList<>();
        try {
            List<String> lineas = Files.readAllLines(ruta);
            for (String linea : lineas) {
                Frase s = Frase.getSplit(linea, delimitador);
                //descartamos las frases que no contengan tres apartados
                if (s != null) {
                    frasesFicha.add(s);
                }
            }
        }catch(FileNotFoundException e){
         System.err.println("ERROR AL ACCEDER AL RANDOMCSV: "+e.getMessage());
         return null;
        }catch (IOException ex){
        System.err.println("ERROR AL ACCEDER AL RANDOMCSV: "+ex.getMessage());
        return null;
        }
        
        return frasesFicha;
    }
    
        
     
    @Override
    public String speak(String intro) {
        try {
            frases=importar();
        } catch (Exception ex) {
            System.err.println("ERROR AL IMPORTAR FRASES DEL RANDOMCSV"+ex.getMessage());
        }
        
        if (!frases.isEmpty() && frases!=null) {
            ordenarFrasesPorTipos();
            //si el mensaje escrito por el usuario presenta un ? entonces
            // se contesta con una respuesta al azar
            if(intro.endsWith("?") && intro.length()<=12){
                Random rand=new Random();
                Random rund=new Random();
                int opcion=rand.nextInt(1);
                switch(opcion) {
                    case 0: return afirmaciones.get(rund.nextInt(afirmaciones.size())).getContenido();
                    case 1: return negaciones.get(rund.nextInt(negaciones.size())).getContenido();
                }
            }
            
            else if (intro.contains("?")) {
                Random rand=new Random();
                return respuestas.get(rand.nextInt(respuestas.size())).getContenido();
            }
            else if(intro.contains("adios") || intro.contains("adiós")){
                Random rand=new Random();
                return despedidas.get(rand.nextInt(despedidas.size())).getContenido();
            }
            else if(intro.contains("hola")|| intro.contains("Hola")){
                Random rand=new Random();
                return saludos.get(rand.nextInt(saludos.size())).getContenido();
            }
            else if(intro.startsWith("sabes") ||intro.startsWith("Sabes")){
                Random rand=new Random();
                return sorpresas.get(rand.nextInt(sorpresas.size())).getContenido();
            }
            else{
                Random rand=new Random();
                Random rund=new Random();
                int opcion=rand.nextInt(1);
                switch(opcion) {
                    case 0: return refranes.get(rund.nextInt(refranes.size())).getContenido();
                    case 1: return preguntas.get(rund.nextInt(preguntas.size())).getContenido();
                }
            }

        } else {
                        return ">>>No sé que contestar,me he quedado literalmente sin palabras";
        }
        return null;
    }

    @Override
    public String getIdentifier() {
        return "Estás hablando con LLM RandomCSVLLM";
    }
   
    
    
    private void ordenarFrasesPorTipos(){
    for(int i=0;i<frases.size();i++){
        if(frases.get(i).getTipoFrase().equalsIgnoreCase(tipo[0])){
              refranes.add(frases.get(i));
            }
        else if(frases.get(i).getTipoFrase().equalsIgnoreCase(tipo[1])){
              saludos.add(frases.get(i));
            }
        else if(frases.get(i).getTipoFrase().equalsIgnoreCase(tipo[2])){
              despedidas.add(frases.get(i));
            }
        else if(frases.get(i).getTipoFrase().equalsIgnoreCase(tipo[3])){
              afirmaciones.add(frases.get(i));
            }
        else if(frases.get(i).getTipoFrase().equalsIgnoreCase(tipo[4])){
              negaciones.add(frases.get(i));
            }
        else if(frases.get(i).getTipoFrase().equalsIgnoreCase(tipo[5])){
              sorpresas.add(frases.get(i));
            }
        else if(frases.get(i).getTipoFrase().equalsIgnoreCase(tipo[6])){
              preguntas.add(frases.get(i));
            }
        else if(frases.get(i).getTipoFrase().equalsIgnoreCase(tipo[7])){
              respuestas.add(frases.get(i));
            }
         }
    }
    
    
    
    
    
    
    
    
}
