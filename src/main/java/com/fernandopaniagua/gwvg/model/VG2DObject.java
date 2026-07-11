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

    // Caja delimitadora local del objeto (coordenadas del modelo original)
    protected double localMinX = 0;
    protected double localMaxX = 0;
    protected double localMinY = 0;
    protected double localMaxY = 0;

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

        // Calcular la caja delimitadora local
        calculateBoundingBox();
    }

    private void calculateBoundingBox() {
        if (!this.lines.isEmpty()) {
            localMinX = this.lines.get(0).p1.x;
            localMaxX = this.lines.get(0).p1.x;
            localMinY = this.lines.get(0).p1.y;
            localMaxY = this.lines.get(0).p1.y;
            for (VG2DLine line : this.lines) {
                localMinX = Math.min(localMinX, Math.min(line.p1.x, line.p2.x));
                localMaxX = Math.max(localMaxX, Math.max(line.p1.x, line.p2.x));
                localMinY = Math.min(localMinY, Math.min(line.p1.y, line.p2.y));
                localMaxY = Math.max(localMaxY, Math.max(line.p1.y, line.p2.y));
            }
        }
    }

    public boolean containsPoint(double px, double py) {
        double actualMinX = localMinX * s + xt;
        double actualMaxX = localMaxX * s + xt;
        double actualMinY = localMinY * s + yt;
        double actualMaxY = localMaxY * s + yt;

        double padding = 10.0; // margen de ayuda para facilitar el apuntado
        return px >= (actualMinX - padding) && px <= (actualMaxX + padding) &&
               py >= (actualMinY - padding) && py <= (actualMaxY + padding);
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

    public double getScale() {
        return this.s;
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

            double px1 = line.p1.x;
            double py1 = line.p1.y;
            double px2 = line.p2.x;
            double py2 = line.p2.y;

            // Si hay rotación de la línea (durante la explosión), la rotamos sobre su propio centro
            if (line.angle != 0) {
                double cx = (px1 + px2) / 2.0;
                double cy = (py1 + py2) / 2.0;

                double dx1 = px1 - cx;
                double dy1 = py1 - cy;
                double dx2 = px2 - cx;
                double dy2 = py2 - cy;

                double cos = Math.cos(line.angle);
                double sin = Math.sin(line.angle);

                px1 = cx + (dx1 * cos - dy1 * sin);
                py1 = cy + (dx1 * sin + dy1 * cos);
                px2 = cx + (dx2 * cos - dy2 * sin);
                py2 = cy + (dx2 * sin + dy2 * cos);
            }

            // Aplicar escala, offset de la línea y translación del objeto
            x1 = (px1 + line.ox) * s + xt;
            y1 = (py1 + line.oy) * s + yt;
            x2 = (px2 + line.ox) * s + xt;
            y2 = (py2 + line.oy) * s + yt;
            
            g2d.drawLine((int) x1, (int) y1, (int) x2, (int) y2);
        }
    }
}
