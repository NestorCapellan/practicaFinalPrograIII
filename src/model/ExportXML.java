/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.jsr310.*;

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
public class ExportXML implements IRepository {

    /**
     *
     * @param archivo
     * @return 
     */
    @Override
    public ArrayList<Conversacion> importar(File archivo) {
        ArrayList<Conversacion> importaciones;
        if(archivo.isFile() && archivo.exists()){
            try {
            XmlMapper xmlMapper = new XmlMapper();
            String xml = new String(Files.readAllBytes(archivo.toPath()), StandardCharsets.UTF_8);
            // Utiliza TypeFactory para obtener el tipo de lista correcto
            importaciones=xmlMapper.readValue(xml, xmlMapper.getTypeFactory().constructCollectionType(List.class, Conversacion.class));
        }catch (IOException ex) {
           System.err.println("ERROR AL IMPORTAR EN XML"+ex.getMessage());
           return null;
        }
        return importaciones;
        }else{
            return null;
        }
    }

    @Override
    public boolean exportar(ArrayList<Conversacion> lista, File archivo)  {
         try {
            XmlMapper xmlMapper = new XmlMapper();
            String xml = xmlMapper.writeValueAsString(lista);
            Files.write(archivo.toPath(), xml.getBytes(StandardCharsets.UTF_8));
            return true;
        } catch (JsonProcessingException ex) {
             System.err.println("ERROR AL EXPORTAR EN XML"+ex.getMessage());
             return false;
        } catch (IOException ex) {
             System.err.println("ERROR AL EXPORTAR EN XML"+ex.getMessage());
             return false;
        }             
  }
    @Override
    public String getIdentifier() {
        return "Ha elegido exportar o importar en formato XML";
    }

    
    
}
