/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import static com.coti.tools.Esdia.readInt;
import io.github.jonelo.jAdapterForNativeTTS.engines.SpeechEngine;
import io.github.jonelo.jAdapterForNativeTTS.engines.SpeechEngineNative;
import io.github.jonelo.jAdapterForNativeTTS.engines.Voice;
import io.github.jonelo.jAdapterForNativeTTS.engines.VoicePreferences;
import io.github.jonelo.jAdapterForNativeTTS.engines.exceptions.SpeechEngineCreationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author Nestor Capellan
 */
public class TTS extends SimpleConsole {
    private SpeechEngine speechEngine;

    //creamos la voz a partir del  método setVoice que configura la voz
    public TTS(){
        try{
        this.speechEngine = setVoice();
        }catch(SpeechEngineCreationException ex ){
            System.err.println("Error al cargar voz "+ex.getMessage());
        }
    }
    
   
  
    public static SpeechEngine setVoice() throws SpeechEngineCreationException{
        SpeechEngine speechEngine = null;
        speechEngine = SpeechEngineNative.getInstance();
        
        List<Voice> voices = speechEngine.getAvailableVoices();

    
        // Solo tenemos dos opciones y son voces femeninas americanas y españolas
        VoicePreferences voicePreferences = new VoicePreferences();
        voicePreferences.setLanguage("es"); //  ISO-639-1
        voicePreferences.setCountry("ES"); // ISO 3166-1 Alpha-2 code
        voicePreferences.setGender(VoicePreferences.Gender.FEMALE);
        Voice voice = speechEngine.findVoiceByPreferences(voicePreferences);

        // en caso de que se tome otra opcion no existente
        if (voice == null) {
            System.out.printf("Warning: Voice has not been found by the voice preferences %s%n", voicePreferences);
            voice = voices.get(0); // garantiza que tenga al menos una voz
            System.out.printf("Using \"%s\" instead.%n", voice);
        }

        speechEngine.setVoice(voice.getName());
        return speechEngine;
        
    }
    

    /**
     *
     */
    @Override
    public void showMainMenu() {
        ArrayList<String> opciones=new ArrayList<>();
        int opc;
        String menu="-".repeat(19)+"MENU PRINCIPAL JLLM"+"-".repeat(19);
        opciones.add("pulse 1 para: Nueva conversacion");
        opciones.add("pulse 2 para: Gestión de conversaciones");
        opciones.add("pulse 3 para: Exportación o importación");
        opciones.add("pulse 4 para: terminar con jLLM");
      
        
        do{
           
            System.out.println();
            System.out.println(menu);
            for(String opcion:opciones){
                System.out.println(opcion);
                 try {
                    this.speechEngine.say(opcion);
                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException ex) {
                            System.err.println("error en la espera de voz");
                        }
                } catch (IOException ex) {
                    System.err.println("ERROR SALIDA VOZ");
                }
            }
            opc=readInt("introduce la opción que desee-->");
            switch(opc){
                case 1:
                    super.nuevaconversacion();
                    break;
                case 2: 
                    menuCRUD();
                    break;

                case 3:
                    exportacion();
                    break;

                case 4:
                    super.getOut();
                    break;
                default:
                    System.out.println("Todavía no tenemos suficiente financiación como para tener más  opciones. Opción no disponible ");
                    break;
            }
          
        }while(opc!=4);
    }
    // las funciones están estipuladas private porque solo tiene sentido
    // que se acceden desde la vista y desde ningún otro sitio

 private void menuCRUD(){
String encabezado="-".repeat(17)+"GESTIÓN CONVERSACIONES"+"-".repeat(17);
System.out.println(String.format("-".repeat(57)));
ArrayList<String> opcionesCRUD=new ArrayList<>();
opcionesCRUD.add("1-Listar conversaciones");
opcionesCRUD.add("2-Eliminar alguna conversación");
opcionesCRUD.add("3-Volver al menu principal");

int opcion;
do{
    System.out.println(encabezado);
    System.out.println("MENU ");
    for(String opcionCRUD:opcionesCRUD){
        System.out.println(opcionCRUD);
        try {
                this.speechEngine.say(opcionCRUD);
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException ex) {
                        System.err.println("error en la espera de voz");
                    }
        } catch (IOException ex) {
            System.err.println("ERROR SALIDA VOZ");
        }
    }
    opcion=readInt("introduzca la opción que desee-->");
    switch(opcion){
          case 1:
                    super.listarConver();
                    break;
                case 2: 
                    super.eliminarConver();
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
   
 private void exportacion(){
    String encabezado="-".repeat(19)+"E/S CONVERSACIONES"+"-".repeat(19);System.out.println(String.format("-".repeat(57)));
    int opc;
    ArrayList<String> opcionesexportacion=new ArrayList<>();
    opcionesexportacion.add("1-Exportar");
    opcionesexportacion.add("2-Importar");
    opcionesexportacion.add("3-Volver al menu principal");
    do{
        System.out.println(encabezado);
        System.out.println(String.format("-".repeat(57)));
        System.out.println("MENU ");
        for(String opcionexportacion:opcionesexportacion){
        System.out.println(opcionexportacion);
        try {
                this.speechEngine.say(opcionexportacion);
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException ex) {
                        System.err.println("error en la espera de voz");
                    }
        } catch (IOException ex) {
            System.err.println("ERROR SALIDA VOZ");
        }
        }
        opc=readInt("introduzca la opción que desee-->");
        switch(opc){
                case 1:
                    super.exportarConversaciones();
                    break;

                case 2: 
                    super.importarConversaciones();
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
}