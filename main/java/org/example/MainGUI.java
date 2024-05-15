package org.example;

//tirar o *
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainGUI extends JFrame {
    private JPanel Main;
    private JTextField campoNome;
    private JTextField campoIdade;
    private JTextField campoCelular;
    private JTextField campoFisioResp;
    private JTextField campoFreq;
    private JTextField CampoGen;
    private JButton cadastrarButton;
    private JTable table1;
    private JButton editarButton;
    private JButton deletarButton;
    private JButton buscarButton;
    private JTextField pacienteBusca;


    public MainGUI(){
        setTitle("Janela");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(700,400);
        setLocationRelativeTo(null);
        setVisible(true);
        cadastrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //PacienteDAO.adicionarPaciente(Pacientes paciente);
            }
        });
    }
}
