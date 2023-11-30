/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package model;

/**
 *
 * @author Nestor Capellan
 */
public interface ILLM {
     // todos los métodos de una interfaz son públicos y abstractos
    //por tanto es redundante declararlos como public y abstract
    public abstract String speak(String intro);
    public abstract String getIdentifier();
}
