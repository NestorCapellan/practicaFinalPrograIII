/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import controller.Controller;

/**
 *
 * @author Nestor Capellan
 */
public abstract class ApplicationView {
    protected Controller c;
    public abstract void showApplicationStart(String initInfo);
    public abstract void showMainMenu();
    public abstract void showApplicationEnd(String endInfo);
    public void setController(Controller c) {
        this.c = c;
    }
    
}
