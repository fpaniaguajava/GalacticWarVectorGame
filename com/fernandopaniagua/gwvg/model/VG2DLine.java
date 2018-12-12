/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fernandopaniagua.gwvg.model;

import java.awt.Color;

/**
 *
 * @author Paniagua
 */
public class VG2DLine {
    public VG2DPoint p1;
    public VG2DPoint p2;
    public Color color;

    public VG2DLine(double x1, double y1, double x2, double y2, Color color){
        this.p1 = new VG2DPoint(x1, y1);
        this.p2 = new VG2DPoint(x2, y2);
        this.color = color;
    }
}
