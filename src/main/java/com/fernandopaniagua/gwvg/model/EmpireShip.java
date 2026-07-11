package com.fernandopaniagua.gwvg.model;

import com.fernandopaniagua.gwvg.ui.JFMain;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Clase base para todas las naves del Imperio.
 */
public class EmpireShip extends VG2DObject {

    protected final static int MAX_STEPS = 1000;
    protected double xSpeed = 0;
    protected double ySpeed = 0;
    protected int maxSteps = 0;//Numero de pasos antes de cambiar de dirección
    protected int steps = 0;//Numero de pasos que ha dado ya

    protected boolean isExploding = false;
    protected int explosionSteps = 0;

    public EmpireShip(String pathname) throws FileNotFoundException, IOException {
        super(pathname);
        reset();
    }
    
    public void reset() {
        this.isExploding = false;
        this.explosionSteps = 0;
        this.xSpeed = Math.random() - 0.5;
        this.ySpeed = (Math.random() - 0.5)/100;
        this.maxSteps = (int) (Math.random() * MAX_STEPS);
        this.steps = 0;
        this.scaleAbs(0.1);
        this.translateAbs(Math.random() * JFMain.SCREEN_WIDTH, Math.random() * JFMain.SCREEN_HEIGHT);
        for (VG2DLine line : this.lines) {
            line.ox = 0;
            line.oy = 0;
            line.angle = 0;
            line.vx = 0;
            line.vy = 0;
            line.vAngle = 0;
        }
    }

    public boolean isExploding() {
        return this.isExploding;
    }

    public void startExplosion() {
        this.isExploding = true;
        this.explosionSteps = 0;

        double localCenterX = (localMinX + localMaxX) / 2.0;
        double localCenterY = (localMinY + localMaxY) / 2.0;

        for (VG2DLine line : this.lines) {
            double lineCenterX = (line.p1.x + line.p2.x) / 2.0;
            double lineCenterY = (line.p1.y + line.p2.y) / 2.0;

            double dx = lineCenterX - localCenterX;
            double dy = lineCenterY - localCenterY;
            double dist = Math.hypot(dx, dy);

            if (dist < 0.001) {
                double angle = Math.random() * 2 * Math.PI;
                dx = Math.cos(angle);
                dy = Math.sin(angle);
                dist = 1.0;
            }

            // Normalizamos la dirección y multiplicamos por una velocidad aleatoria para que vuelen hacia fuera
            double speed = 1.5 + Math.random() * 3.5;
            line.vx = (dx / dist) * speed;
            line.vy = (dy / dist) * speed;

            // Rotación aleatoria en radianes
            line.vAngle = (Math.random() - 0.5) * 0.2;
        }
    }

    public void updateExplosion() {
        this.explosionSteps++;
        for (VG2DLine line : this.lines) {
            line.ox += line.vx;
            line.oy += line.vy;
            line.angle += line.vAngle;
        }
        // Desplazamiento inercial suave del objeto mientras explota
        this.translateInc(xSpeed * 0.2, ySpeed * 0.2);

        // Terminar la explosión y regenerar el Tie Fighter después de 150 frames (~1.5s)
        if (this.explosionSteps > 150) {
            reset();
        }
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
            reset();
        }
    }

}
