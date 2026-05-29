package vista;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class SistemaVista extends JFrame {
    private JButton btnRegistroEmpresas;
    private JButton btnOfertasTrabajo;
    private JButton btnCandidatos;
    private JButton btnSalir;

    public SistemaVista() {
        setTitle("Sistema de Reclutamiento TI");
        setSize(400, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); 

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 1, 15, 15));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        JLabel lblTitulo = new JLabel("MENÚ PRINCIPAL", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(lblTitulo);

        btnRegistroEmpresas = new JButton("1. Registro de Empresas");
        btnOfertasTrabajo = new JButton("2. Ofertas de Trabajo");
        btnCandidatos = new JButton("3. Candidatos y Postulación");
        btnSalir = new JButton("0. Salir");

        panel.add(btnRegistroEmpresas);
        panel.add(btnOfertasTrabajo);
        panel.add(btnCandidatos);
        panel.add(btnSalir);

        add(panel);
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Información", JOptionPane.INFORMATION_MESSAGE);
    }

    public void addRegistroEmpresasListener(ActionListener listener) { btnRegistroEmpresas.addActionListener(listener); }
    public void addOfertasTrabajoListener(ActionListener listener) { btnOfertasTrabajo.addActionListener(listener); }
    public void addCandidatosListener(ActionListener listener) { btnCandidatos.addActionListener(listener); }
    public void addSalirListener(ActionListener listener) { btnSalir.addActionListener(listener); }
}