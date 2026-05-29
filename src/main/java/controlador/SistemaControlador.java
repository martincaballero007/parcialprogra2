package controlador;

import vista.SistemaVista;

public class SistemaControlador {
    private SistemaVista vista;
    
    public SistemaControlador(SistemaVista vista) {
        this.vista = vista;
        inicializarEventos();
    }
    
    // Asignamos las acciones que ocurrirán al presionar cada botón de la vista
    private void inicializarEventos() {
        
        vista.addRegistroEmpresasListener(e -> {
            // Aquí iría la lógica o llamada a una nueva ventana de Registro
            vista.mostrarMensaje(">> Abriendo módulo de Registro de Empresas...");
        });

        vista.addOfertasTrabajoListener(e -> {
            // Aquí iría la lógica o llamada a una nueva ventana de Ofertas
            vista.mostrarMensaje(">> Abriendo módulo de Ofertas de Trabajo...");
        });

        vista.addCandidatosListener(e -> {
            // Aquí iría la lógica o llamada a una nueva ventana de Candidatos
            vista.mostrarMensaje(">> Abriendo módulo de Candidatos y Postulación...");
        });

        vista.addSalirListener(e -> {
            int confirmacion = javax.swing.JOptionPane.showConfirmDialog(
                    vista, 
                    "¿Estás seguro que deseas salir?", 
                    "Confirmar Salida", 
                    javax.swing.JOptionPane.YES_NO_OPTION
            );
            if (confirmacion == javax.swing.JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });
    }
    
    public void iniciar() {
        // Hacemos visible la ventana principal
        vista.setVisible(true);
    }
}