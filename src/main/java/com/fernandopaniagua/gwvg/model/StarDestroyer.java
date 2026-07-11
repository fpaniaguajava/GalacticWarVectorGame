package com.fernandopaniagua.gwvg.model;

import com.fernandopaniagua.gwvg.ui.JFMain;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Representa un Imperial Star Destroyer (Destructor Estelar).
 */
public class StarDestroyer extends EmpireShip {

    public StarDestroyer(String pathname) throws FileNotFoundException, IOException {
        super(pathname);
    }

    @Override
    public void reset() {
        super.reset();
        // Los destructores estelares son naves capitales gigantes.
        // Se mueven y escalan de forma mucho más lenta.
        this.xSpeed = (Math.random() - 0.5) * 0.15;
        this.ySpeed = (Math.random() - 0.5) / 200;
        this.scaleAbs(0.02); // Empiezan siendo un punto muy pequeño en el horizonte
    }
}
