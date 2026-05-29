package com.mycompany.parcialprogra2;

import javax.swing.SwingUtilities;

import controlador.SistemaControlador;
import vista.SistemaVista;

public class App { // <-- Cambiado de Main a App
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SistemaVista vista = new SistemaVista();
            SistemaControlador controlador = new SistemaControlador(vista);
            controlador.iniciar();
        });
    }
}