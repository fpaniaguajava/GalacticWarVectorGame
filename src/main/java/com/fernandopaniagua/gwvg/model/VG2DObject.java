/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fernandopaniagua.gwvg.model;

import java.awt.Color;
import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 *
 * @author Paniagua
 */
public class VG2DObject {

    protected ArrayList<VG2DLine> lines;
    protected double xt, yt; //Translacción de los ejes
    protected double s = 1;//Escalado

    public VG2DObject(String pathname) throws FileNotFoundException, IOException {
        this.lines = new ArrayList();
        InputStream in = this.getClass().getClassLoader().getResourceAsStream(pathname);
        InputStreamReader isr = new InputStreamReader(in);
        BufferedReader br = new BufferedReader(isr);
        StringTokenizer st;
        Color color = null;
        String linea = br.readLine();
        while (linea != null) {
            if (linea.contains("color")) {
                st = new StringTokenizer(linea, ",");
                st.nextElement();
                color = new Color(
                        Integer.parseInt(st.nextToken()),
                        Integer.parseInt(st.nextToken()),
                        Integer.parseInt(st.nextToken()));
            } else {
                st = new StringTokenizer(linea, ",");
                this.lines.add(new VG2DLine(
                        Integer.parseInt(st.nextToken()),
                        Integer.parseInt(st.nextToken()),
                        Integer.parseInt(st.nextToken()),
                        Integer.parseInt(st.nextToken()),
                        color));
            }
            linea = br.readLine();
        }
        br.close();
        isr.close();
    }
    
    /**
     * Escala el modelo completo, de manera incremental.
     *
     * @param s Factor de escalado
     */
    public void scale(double s) {
        this.s = this.s + s;
    }

    /**
     * Escala de manera absoluta el modelo completo.
     *
     * @param s Factor de escalado
     */
    public void scaleAbs(double s) {
        this.s = s;
    }

    public void translateInc(double xt, double yt) {
        this.xt = this.xt + xt;
        this.yt = this.yt + yt;
    }

    public void translateAbs(double xt, double yt) {
        this.xt = xt;
        this.yt = yt;
    }


    public void draw(Graphics2D g2d) {
        double x1, y1, x2, y2;
        for (VG2DLine line : this.lines) {
            g2d.setColor(line.color);
            x1 = line.p1.x * s + xt;
            y1 = line.p1.y * s + yt;
            x2 = line.p2.x * s + xt;
            y2 = line.p2.y * s + yt;
            
            g2d.drawLine((int) x1, (int) y1, (int) x2, (int) y2);
        }
    }
}
