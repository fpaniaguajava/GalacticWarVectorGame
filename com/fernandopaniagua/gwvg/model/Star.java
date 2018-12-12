/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fernandopaniagua.gwvg.model;

import com.fernandopaniagua.gwvg.ui.JFMain;
import java.awt.Color;
import java.awt.Graphics2D;

/**
 *
 * @author Paniagua
 */
public class Star {

    public double x;
    public double y;

    public Star() {
        this.x = (Math.random() * JFMain.SCREEN_WIDTH *3 ) - JFMain.SCREEN_WIDTH;
        this.y = (Math.random() * JFMain.SCREEN_HEIGHT * 3) - JFMain.SCREEN_HEIGHT;
    }

    public void draw(Graphics2D g2d) {
        g2d.setColor(Color.white);
        this.drawLine(x, y, x + 1, y + 1, g2d);
        this.drawLine(x, y, x, y + 1, g2d);
        this.drawLine(x, y, x + 1, y, g2d);
        
    }
    public void moveStar(int incx, int incy){
        this.x += incx;
        this.y += incy;        
    }
    private void drawLine(double x1, double y1, double x2, double y2, Graphics2D g2d){
        g2d.drawLine((int)x1, (int)y1, (int)x2, (int)y2);
    }

}
