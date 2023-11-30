/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package model;

import java.io.File;
import java.util.ArrayList;

/**
 *
 * @author Nestor Capellan
 */
public interface IRepository {
     // todos los métodos de una interfaz son públicos y abstractos
    //por tanto es redundante declararlos como public y abstract
    public abstract ArrayList<Conversacion> importar (File archivo) throws Exception;
    public abstract boolean  exportar(ArrayList<Conversacion> lista,File archivo) throws Exception;
    public abstract String getIdentifier();
}
