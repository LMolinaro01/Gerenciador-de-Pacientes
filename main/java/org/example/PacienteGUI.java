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
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.util.List;


public class PacienteGUI extends JFrame {
    private JButton cadastrarButton, editarButton, excluirButton;
    private JTable pacientesTable;

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

        pacientesTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(pacientesTable);

        cadastrarButton = new JButton("Adicionar");
        editarButton = new JButton("Editar");
        excluirButton = new JButton("Excluir");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(cadastrarButton);
        buttonPanel.add(editarButton);
        buttonPanel.add(excluirButton);

        cadastrarButton.addActionListener(e -> cadastrarPaciente());
        editarButton.addActionListener(e -> editarPaciente());
        excluirButton.addActionListener(e -> deletarPaciente());

        getContentPane().add(scrollPane, BorderLayout.CENTER);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        atualizarTabelaPacientes();

    }
    private void atualizarTabelaPacientes() {
        List<Pacientes> Pacientes = PacienteDAO.listarPacientes();
        DefaultTableModel tableModel = new DefaultTableModel(new Object[]{"ID", "Nome", "Idade", "Celular", "Gênero", "Tratamento", "Frequencia (Semanal)", "Faixa Etária"}, 0);
        for (Pacientes Paciente : Pacientes) {
            tableModel.addRow(new Object[]{Paciente.getId(), Paciente.getNome(), Paciente.getCelular(),Paciente.getGenero(),Paciente.getTratamento(),Paciente.getFrequencia(), Paciente.getFaixaEtaria()});
        }
        pacientesTable.setModel(tableModel);
    }

    private void cadastrarPaciente(){
        JTextField nomeField = new JTextField();
        JTextField idadeField = new JTextField();
        JTextField celularField = new JTextField();
        JTextField tratamentoField = new JTextField();
        JTextField generoField = new JTextField();
        JTextField frequenciaField = new JTextField();

        JPanel panel = new JPanel(new GridLayout(3, 2));
        panel.add(new JLabel("Nome:"));
        panel.add(nomeField);

        panel.add(new JLabel("Idade:"));
        panel.add(idadeField);

        panel.add(new JLabel("Celular:"));
        panel.add(celularField);

        panel.add(new JLabel("Tratamento:"));
        panel.add(tratamentoField);

        panel.add(new JLabel("Gênero:"));
        panel.add(generoField);

        panel.add(new JLabel("Frequência:"));
        panel.add(frequenciaField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Adicionar Contato", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            String nome = nomeField.getText();
            String celular = celularField.getText();
            String idadeStr  = idadeField.getText();
            String tratamento = tratamentoField.getText();
            String genero = generoField.getText();
            String frequencia = frequenciaField.getText();

            if (!nome.isEmpty() && !celular.isEmpty() && !idadeStr .isEmpty() && !tratamento.isEmpty() && !genero.isEmpty() && !frequencia.isEmpty() ) {
                int idade = Integer.parseInt(idadeStr);
                Pacientes novoPaciente = new Pacientes();
                novoPaciente.setNome(nome);
                novoPaciente.setCelular(celular);
                novoPaciente.setIdade(idade);
                novoPaciente.setTratamento(tratamento);
                novoPaciente.setGenero(genero);
                novoPaciente.setFrequencia(frequencia);

                PacienteDAO.adicionarPaciente(novoPaciente);
                atualizarTabelaPacientes();
            } else {
                JOptionPane.showMessageDialog(this, "Por favor, preencha todos os campos necessários.");
            }
        }

    }

    public void editarPaciente(){
        int linhaSelecionada = pacientesTable.getSelectedRow();
        if (linhaSelecionada != -1){ // quando nenhuma linha está selecionada, retorna -1
            int id = (int) pacientesTable.getValueAt(linhaSelecionada, 0);
            String nome = JOptionPane.showInputDialog("Digite o novo nome do contato:");
            String celular = JOptionPane.showInputDialog("Digite o novo número do celular:");
            String idadeStr = JOptionPane.showInputDialog("Digite a nova idade:");
            String tratamento = JOptionPane.showInputDialog("Digite o novo nome do tratamento:");
            String genero = JOptionPane.showInputDialog("Digite o genero:");
            String frequencia = JOptionPane.showInputDialog("Digite a nova frequência semanal do paciente:");

            if (!nome.isEmpty() && !celular.isEmpty() && !idadeStr .isEmpty() && !tratamento.isEmpty() && !genero.isEmpty() && !frequencia.isEmpty() ) {
                int idade = Integer.parseInt(idadeStr);
                Pacientes novoPaciente = new Pacientes();
                novoPaciente.setNome(nome);
                novoPaciente.setCelular(celular);
                novoPaciente.setIdade(idade);
                novoPaciente.setTratamento(tratamento);
                novoPaciente.setGenero(genero);
                novoPaciente.setFrequencia(frequencia);

                PacienteDAO.adicionarPaciente(novoPaciente);
                atualizarTabelaPacientes();
            } else {
                JOptionPane.showMessageDialog(this, "Por favor, Selecione um Paciente.");
            }
        }
    }

    public void deletarPaciente() {
        int selectedRow = pacientesTable.getSelectedRow();
        if (selectedRow != -1) {
            int id = (int) pacientesTable.getValueAt(selectedRow, 0);

            // cria uma mensagem de confirmação
            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "Você realmente deseja excluir este paciente?",
                    "ATENÇÃO!",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE
            );

            // verifica se o usuário confirmou
            if (confirm == JOptionPane.YES_OPTION) {
                PacienteDAO.excluirPaciente(id);
                atualizarTabelaPacientes();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um paciente para excluir.");
        }
    }

    public static void main(String[] args) {
        PacienteDAO.criarTabela();
        SwingUtilities.invokeLater(() -> {
            new PacienteGUI().setVisible(true);
        });
    }

}

