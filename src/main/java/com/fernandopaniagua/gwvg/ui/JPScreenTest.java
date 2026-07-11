/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fernandopaniagua.gwvg.ui;

import com.fernandopaniagua.gwvg.model.PunteroLaser;
import com.fernandopaniagua.gwvg.model.Star;
import com.fernandopaniagua.gwvg.model.TF;
import com.fernandopaniagua.gwvg.model.EmpireShip;
import com.fernandopaniagua.gwvg.model.StarDestroyer;
import com.fernandopaniagua.gwvg.model.VG2DLine;
import com.fernandopaniagua.gwvg.model.VG2DObject;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author Paniagua
 */
public class JPScreenTest extends javax.swing.JPanel {

    private final static int NUM_TF = 10;
    private final static int NUM_STAR = 1000;
    Graphics2D g2d;
    ArrayList<VG2DLine> alLineas = new ArrayList();
    ArrayList<VG2DObject> alObjetos = new ArrayList();
    ArrayList<Star> alEstrellas = new ArrayList();
    PunteroLaser punteroLaser;
    VG2DObject deathStar;
    private int cursorXPos = 0, cursorYPos = 0;//Posicion del cursor
    private int xmove, ymove;//Diferenciales a mover las estrellas
    
    // Estado del arma láser
    private boolean laserActive = false;
    private int laserDuration = 0;
    private int laserTargetX = 0;
    private int laserTargetY = 0;

    /**
     * Creates new form JPScreenTest
     */
    public JPScreenTest() {
        initComponents();
        //Creación de las estrellas
        for (int i = 0; i < NUM_STAR; i++) {
            alEstrellas.add(new Star());
        }
        //Creación de los objetos 2D
        try {
            punteroLaser = new PunteroLaser("vector/punterolaser.dat");
            punteroLaser.scale(0.5);
            alObjetos.add(punteroLaser);
            deathStar = new VG2DObject("vector/estrella.dat");
            deathStar.scale(-0.7);
            deathStar.translateInc(JFMain.SCREEN_WIDTH/2, JFMain.SCREEN_HEIGHT/3);
            alObjetos.add(deathStar);
            for (int i = 0; i < NUM_TF; i++) {
                alObjetos.add(new TF("vector/tiefighter.dat"));
            }
            for (int i = 0; i < 3; i++) {
                alObjetos.add(new StarDestroyer("vector/stardestroyer.dat"));
            }
            
            
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                //Mueve el puntero laser
                punteroLaser.translateAbs(e.getX()-30, e.getY()-20);
                
                //Mueve las estrellas
                if ((cursorXPos==0) && (cursorYPos==0)) {
                    cursorXPos = e.getX();
                    cursorYPos = e.getY();
                }                 
                xmove = cursorXPos - e.getX();
                ymove = cursorYPos - e.getY();
                cursorXPos = e.getX();
                cursorYPos = e.getY();
                for (Star star : alEstrellas) {
                    star.moveStar(xmove, ymove);
                }
                deathStar.translateInc(xmove, ymove);
            }
        });

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    laserActive = true;
                    laserDuration = 10;
                    laserTargetX = e.getX();
                    laserTargetY = e.getY();

                    // Buscar la nave del imperio más cercana (escala s más grande) que contenga el punto del click
                    EmpireShip hitShip = null;
                    for (VG2DObject objeto2D : alObjetos) {
                        if (objeto2D instanceof EmpireShip) {
                            EmpireShip ship = (EmpireShip) objeto2D;
                            if (!ship.isExploding() && ship.containsPoint(laserTargetX, laserTargetY)) {
                                if (hitShip == null || ship.getScale() > hitShip.getScale()) {
                                    hitShip = ship;
                                }
                            }
                        }
                    }
                    if (hitShip != null) {
                        hitShip.startExplosion();
                    }
                }
            }
        });

        alLineas.add(new VG2DLine(135 * JFMain.SCREEN_WIDTH / 320, 240 * JFMain.SCREEN_HEIGHT / 240, 150 * JFMain.SCREEN_WIDTH / 320, 225 * JFMain.SCREEN_HEIGHT / 240, Color.red));
        alLineas.add(new VG2DLine(150 * JFMain.SCREEN_WIDTH / 320, 225 * JFMain.SCREEN_HEIGHT / 240, 150 * JFMain.SCREEN_WIDTH / 320, 230 * JFMain.SCREEN_HEIGHT / 240, Color.red));
        alLineas.add(new VG2DLine(150 * JFMain.SCREEN_WIDTH / 320, 225 * JFMain.SCREEN_HEIGHT / 240, 165 * JFMain.SCREEN_WIDTH / 320, 225 * JFMain.SCREEN_HEIGHT / 240, Color.red));
        alLineas.add(new VG2DLine(165 * JFMain.SCREEN_WIDTH / 320, 225 * JFMain.SCREEN_HEIGHT / 240, 165 * JFMain.SCREEN_WIDTH / 320, 230 * JFMain.SCREEN_HEIGHT / 240, Color.red));
        alLineas.add(new VG2DLine(165 * JFMain.SCREEN_WIDTH / 320, 225 * JFMain.SCREEN_HEIGHT / 240, 180 * JFMain.SCREEN_WIDTH / 320, 240 * JFMain.SCREEN_HEIGHT / 240, Color.red));
        alLineas.add(new VG2DLine(135 * JFMain.SCREEN_WIDTH / 320, 240 * JFMain.SCREEN_HEIGHT / 240, 150 * JFMain.SCREEN_WIDTH / 320, 230 * JFMain.SCREEN_HEIGHT / 240, Color.blue));
        alLineas.add(new VG2DLine(165 * JFMain.SCREEN_WIDTH / 320, 230 * JFMain.SCREEN_HEIGHT / 240, 180 * JFMain.SCREEN_WIDTH / 320, 240 * JFMain.SCREEN_HEIGHT / 240, Color.blue));
        alLineas.add(new VG2DLine(150 * JFMain.SCREEN_WIDTH / 320, 230 * JFMain.SCREEN_HEIGHT / 240, 165 * JFMain.SCREEN_WIDTH / 320, 230 * JFMain.SCREEN_HEIGHT / 240, Color.blue));

        alLineas.add(new VG2DLine(102 * JFMain.SCREEN_WIDTH / 320, 2 * JFMain.SCREEN_HEIGHT / 240, 218 * JFMain.SCREEN_WIDTH / 320, 2 * JFMain.SCREEN_HEIGHT / 240, Color.green));
        alLineas.add(new VG2DLine(218 * JFMain.SCREEN_WIDTH / 320, 2 * JFMain.SCREEN_HEIGHT / 240, 218 * JFMain.SCREEN_WIDTH / 320, 22 * JFMain.SCREEN_HEIGHT / 240, Color.green));
        alLineas.add(new VG2DLine(218 * JFMain.SCREEN_WIDTH / 320, 22 * JFMain.SCREEN_HEIGHT / 240, 160 * JFMain.SCREEN_WIDTH / 320, 2 * JFMain.SCREEN_HEIGHT / 240, Color.green));
        alLineas.add(new VG2DLine(160 * JFMain.SCREEN_WIDTH / 320, 2 * JFMain.SCREEN_HEIGHT / 240, 102 * JFMain.SCREEN_WIDTH / 320, 22 * JFMain.SCREEN_HEIGHT / 240, Color.green));
        alLineas.add(new VG2DLine(102 * JFMain.SCREEN_WIDTH / 320, 22 * JFMain.SCREEN_HEIGHT / 240, 102 * JFMain.SCREEN_WIDTH / 320, 2 * JFMain.SCREEN_HEIGHT / 240, Color.green));

        alLineas.add(new VG2DLine(120 * JFMain.SCREEN_WIDTH / 320, 2 * JFMain.SCREEN_HEIGHT / 240, 120 * JFMain.SCREEN_WIDTH / 320, 6 * JFMain.SCREEN_HEIGHT / 240, Color.green));
        alLineas.add(new VG2DLine(140 * JFMain.SCREEN_WIDTH / 320, 2 * JFMain.SCREEN_HEIGHT / 240, 140 * JFMain.SCREEN_WIDTH / 320, 6 * JFMain.SCREEN_HEIGHT / 240, Color.green));
        alLineas.add(new VG2DLine(178 * JFMain.SCREEN_WIDTH / 320, 2 * JFMain.SCREEN_HEIGHT / 240, 178 * JFMain.SCREEN_WIDTH / 320, 6 * JFMain.SCREEN_HEIGHT / 240, Color.green));
        alLineas.add(new VG2DLine(198 * JFMain.SCREEN_WIDTH / 320, 2 * JFMain.SCREEN_HEIGHT / 240, 198 * JFMain.SCREEN_WIDTH / 320, 6 * JFMain.SCREEN_HEIGHT / 240, Color.green));

    }

    private void drawAVGLines(Graphics2D g2d) {
        for (VG2DLine linea : alLineas) {
            g2d.setColor(linea.color);
            g2d.drawLine((int) linea.p1.x, (int) linea.p1.y, (int) linea.p2.x, (int) linea.p2.y);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g2d = (Graphics2D) g;
        //FONDO OSCURO
        g2d.setColor(Color.black);
        g2d.fillRect(0, 0, JFMain.SCREEN_WIDTH, JFMain.SCREEN_HEIGHT);
        //DIBUJA EL PANEL
        this.drawAVGLines(g2d);
        //ESCALA LOS TF Y DESTRUCTORES ESTELARES
        for (VG2DObject objeto2D : alObjetos) {
            if (objeto2D instanceof EmpireShip) {
                EmpireShip ship = (EmpireShip) objeto2D;
                if (ship.isExploding()) {
                    ship.updateExplosion();
                } else {
                    double scaleFactor = (ship instanceof StarDestroyer) ? 0.00005 : 0.0001;
                    objeto2D.scale(scaleFactor);
                    ship.move();
                }
            }
        }
        //DIBUJA LAS ESTRELLAS
        for (Star star : alEstrellas) {
            star.draw(g2d);
        }

        //DIBUJA LOS OBJETOS
        for (VG2DObject objeto2D : alObjetos) {
            objeto2D.draw(g2d);
        }
        //ESCALA LA ESTRELLA DE LA MUERTE
        deathStar.scale(0.0001);
        
        // DIBUJA EL LÁSER SI ESTÁ ACTIVO
        if (laserActive) {
            g2d.setColor(Color.red);
            g2d.setStroke(new java.awt.BasicStroke(3f));
            g2d.drawLine(0, JFMain.SCREEN_HEIGHT, laserTargetX, laserTargetY);
            g2d.drawLine(JFMain.SCREEN_WIDTH, JFMain.SCREEN_HEIGHT, laserTargetX, laserTargetY);
            
            // Núcleo blanco para efecto de brillo vector
            g2d.setColor(Color.white);
            g2d.setStroke(new java.awt.BasicStroke(1f));
            g2d.drawLine(0, JFMain.SCREEN_HEIGHT, laserTargetX, laserTargetY);
            g2d.drawLine(JFMain.SCREEN_WIDTH, JFMain.SCREEN_HEIGHT, laserTargetX, laserTargetY);
            
            // Restaurar trazo
            g2d.setStroke(new java.awt.BasicStroke(1f));
            
            laserDuration--;
            if (laserDuration <= 0) {
                laserActive = false;
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setBackground(new java.awt.Color(153, 255, 153));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
