/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fernandopaniagua.gwvg.model;

import com.fernandopaniagua.gwvg.ui.JFMain;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 *
 * @author Paniagua
 */
public class TF extends VG2DObject {

    final static int MAX_STEPS = 1000;
    double xSpeed = 0;
    double ySpeed = 0;
    int maxSteps = 0;//Numero de pasos antes de cambiar de dirección
    int steps = 0;//Numero de pasos que ha dado ya

    public TF(String pathname) throws FileNotFoundException, IOException {
        super(pathname);
        this.xSpeed = Math.random() - 0.5;
        this.ySpeed = (Math.random() - 0.5)/100;
        this.maxSteps = (int) (Math.random() * MAX_STEPS);
        this.scaleAbs(0.1);
        this.translateAbs(Math.random()*JFMain.SCREEN_WIDTH, Math.random()*JFMain.SCREEN_HEIGHT);
    }
    
    public void move() {
        this.translateInc(xSpeed, ySpeed);
        
        this.steps++;
        if (this.steps > maxSteps) {
            this.xSpeed = Math.random() - 0.5;
            this.ySpeed = Math.random() - 0.5;
            this.maxSteps = (int) (Math.random() * MAX_STEPS);
            this.steps=0;
        }
        if ((this.xt>JFMain.SCREEN_WIDTH) || (this.yt>JFMain.SCREEN_HEIGHT)){
            this.scaleAbs(0.1);
            this.translateAbs(Math.random()*JFMain.SCREEN_WIDTH, Math.random()*JFMain.SCREEN_HEIGHT);
        }
    }

}
