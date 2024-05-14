package org.example;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JPanel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.GridLayout;

import java.util.List;


public class PacienteGUI extends JFrame {
    private JTable PacientesTable;
    private JButton adicionarButton, editarButton, excluirButton;

    public static void iniciarGUI(){
        SwingUtilities.invokeLater(()->{
            new PacienteGUI().setVisible(true);
        });
    }

    public PacienteGUI(){
        setTitle("Gerenciador de Usuários");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700,500);
        setLocationRelativeTo(null);

        PacientesTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(PacientesTable);

        adicionarButton = new JButton("Adicionar");
        editarButton = new JButton("Editar");
        excluirButton = new JButton("Excluir");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(adicionarButton);
        buttonPanel.add(editarButton);
        buttonPanel.add(excluirButton);

        //adicionarButton.addActionListener(e -> PacienteDAO.adicionarPaciente(Paciente));
        //editarButton.addActionListener(e -> PacienteDAO.atualizarPaciente(Paciente Paciente));
        //excluirButton.addActionListener(e -> PacienteDAO.excluirPaciente(Paciente Paciente));

        getContentPane().add(scrollPane, BorderLayout.CENTER);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        atualizarTabelaContatos();

    }
    private void atualizarTabelaContatos() {
        List<Pacientes> Pacientes = PacienteDAO.listarPacientes();
        DefaultTableModel tableModel = new DefaultTableModel(new Object[]{"ID", "Nome", "Email"}, 0);
        for (Pacientes Paciente : Pacientes) {
            tableModel.addRow(new Object[]{Paciente.getId(), Paciente.getNome()});
        }
        PacientesTable.setModel(tableModel);
    }

}

