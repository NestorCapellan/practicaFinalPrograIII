/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

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

    @Override
    public ArrayList<Conversacion> importar(File archivo) throws Exception {
        if(archivo.isFile() && archivo.exists()){
            try {
            XmlMapper xmlMapper = new XmlMapper();
            String xml = new String(Files.readAllBytes(archivo.toPath()), StandardCharsets.UTF_8);
            // Utiliza TypeFactory para obtener el tipo de lista correcto
            return xmlMapper.readValue(xml, xmlMapper.getTypeFactory().constructCollectionType(List.class, Conversacion.class));
        }catch (IOException ex) {
          throw new Exception("Se ha producido un problema al importar en XML: "+ex.getMessage(),ex);
        }
        }else{
            return null;
        }
    }

    @Override
    public boolean exportar(ArrayList<Conversacion> lista, File archivo) throws Exception {
         try {
            XmlMapper xmlMapper = new XmlMapper();
            String xml = xmlMapper.writeValueAsString(lista);
            Files.write(archivo.toPath(), xml.getBytes(StandardCharsets.UTF_8));
            return true;
        } catch (JsonProcessingException ex) {
          throw new Exception("Se ha producido un problema al exportar en XML: "+ex.getMessage(),ex);
        } catch (IOException ex) {
            throw new Exception("Se ha producido un problema al exportar en XML: "+ex.getMessage(),ex);
        }
  }
    @Override
    public String getIdentifier() {
        return "Ha elegido exportar o importar en formato XML";
    }

    
    
}
