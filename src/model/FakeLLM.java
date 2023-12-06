/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Nestor Capellan
 */
public class FakeLLM implements ILLM{
        ArrayList<String> saludos=new ArrayList<>();
        ArrayList<String> afirmaciones=new ArrayList<>();
        ArrayList<String> negaciones=new ArrayList<>();
        ArrayList<String> datos=new ArrayList<>();
        ArrayList<String> despedidas=new ArrayList<>();
        String[] saludostipos={"Hola, encantado","Buenos días","Buenas tardes","Qué día más espléndido"};
        String[] afirmacionestipos={"si","pues si","vaya que sí","no tenía ni idea","debe ser que sí"};
        String[] negacionestipos={"no","pues no","ni idea","de ninguna manera","debe ser que no","jamas"};
        String[] datostipos={"Sé hablar más de 300 idiomas","puedo hacer cualquier tipo de cálculos","mañana saldra el Sol","es de día o es de noche","seguramente saques buena nota"};
        String[] despedidastipos={"un placer","hasta luego","seguro que volvemos a hablar"};
    @Override
    public String speak(String intro) {
        getfrases();
        if(intro.endsWith("?") && intro.length()<=12){
                Random rand=new Random();
                Random rund=new Random();
                int opcion=rand.nextInt(1);
                switch(opcion) {
                    case 0: return afirmaciones.get(rund.nextInt(afirmaciones.size()));
                    case 1: return negaciones.get(rund.nextInt(negaciones.size()));
                }
            }
            
            else if (intro.contains("?")) {
                Random rand=new Random();
                return negaciones.get(rand.nextInt(negaciones.size()));
            }
            else if(intro.contains("adios") || intro.contains("adiós")){
                Random rand=new Random();
                return despedidas.get(rand.nextInt(despedidas.size()));
            }
            else if(intro.contains("hola")|| intro.contains("Hola")){
                Random rand=new Random();
                return saludos.get(rand.nextInt(saludos.size()));
            }
            else if(intro.startsWith("sabes") ||intro.startsWith("Sabes")){
                Random rand=new Random();
                return datos.get(rand.nextInt(datos.size()));
            }
            else{
                Random rand=new Random();
                Random rund=new Random();
                int opcion=rand.nextInt(1);
                switch(opcion) {
                    case 0: return datos.get(rund.nextInt(datos.size()));
                    case 1: return "no sé que contestar";
                }
            }
            return null;
                   
    }

    @Override
    public String getIdentifier() {
        return "Estás hablando con LLM FakeLLM";
    }
    private void getfrases(){
        for(int i=0;i<saludostipos.length;i++){
             saludos.add(saludostipos[i]);
        }
        for(int i=0;i<afirmacionestipos.length;i++){
             afirmaciones.add(afirmacionestipos[i]);
        }
        for(int i=0;i<negacionestipos.length;i++){
             negaciones.add(negacionestipos[i]);
        }
        for(int i=0;i<datostipos.length;i++){
             datos.add(datostipos[i]);
        }
        for(int i=0;i<despedidastipos.length;i++){
            despedidas.add(despedidastipos[i]);
        }
    }
    
}
