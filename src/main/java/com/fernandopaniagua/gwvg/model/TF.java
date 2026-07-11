package com.fernandopaniagua.gwvg.model;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Representa un Tie Fighter.
 */
public class TF extends EmpireShip {

    public TF(String pathname) throws FileNotFoundException, IOException {
        super(pathname);
    }

}
