    /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package jllm;

import controller.Controller;
import io.github.jonelo.jAdapterForNativeTTS.engines.exceptions.SpeechEngineCreationException;
import model.ExportJSON;
import model.ExportXML;
import model.FakeLLM;
import model.ILLM;
import model.IRepository;
import model.Model;
import model.RandomCSVLLM;
import view.ApplicationView;
import view.SimpleConsole;
import view.TTS;

/**
 *
 * @author Nestor Capellan
 */
public class JLLM {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args){
       IRepository repository;//utilizamos la referencia de la interfaz como tipo estático
       ApplicationView view;//utilizamos la clase abstracta como tipo estático
       ILLM tipo;//utilizamos la referencia de la interfaz como tipo estático
        
        if(args.length == 3){
            repository = getRepositoryForOption(args[0]);
            tipo=getModelForOption(args[1]);
            view = getViewForoption(args[2]);
           
            
        }else{
            // Opciones por defecto:
            repository = new ExportJSON();
            tipo=new RandomCSVLLM();
            view = new SimpleConsole();
          
        }
        
        Model model = new Model(tipo,repository);
        Controller c = new Controller(model, view);
        
        c.initApplication();  
    }
    //son métodos de clase que solo se acceden desde la misma
    private static IRepository getRepositoryForOption(String argumento) {
        switch (argumento) {
            case "xml":
                return new ExportXML();
            case "json":
                return new ExportJSON();
                //la opción por defecto es exportar en Json
            default:
                return new ExportJSON();

        }
    }
    private static ILLM getModelForOption(String argumento){
        switch(argumento){
            case "fake":
                return new FakeLLM();
            case "csv":
                return new RandomCSVLLM();
                //la opcion por defecto el la interfaz de consola simple
            default:
                return new RandomCSVLLM();
        
        }
    
    }

    private static ApplicationView getViewForoption(String argumento){
        switch (argumento) {
            case "consola":
                return new SimpleConsole();
                //la opcion por defecto el la interfaz de consola simple
            case "TTS":
                return new TTS();
            default:
                return new SimpleConsole();
        }
    }
    
}


