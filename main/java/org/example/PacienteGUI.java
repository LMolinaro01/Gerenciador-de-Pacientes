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
        setTitle("Gerenciador de Pacientes");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(825,500);
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
        List<Pacientes> pacientesList = PacienteDAO.listarPacientes();
        DefaultTableModel tableModel = new DefaultTableModel(new Object[]{"ID", "Nome", "Idade", "Celular", "Gênero", "Tratamento", "Faixa Etária", "Dia"}, 0);

        for (Pacientes paciente : pacientesList) {
            tableModel.addRow(new Object[]{
                    paciente.getId(),
                    paciente.getNome(),
                    paciente.getIdade(),
                    paciente.getCelular(),
                    paciente.getGenero(),
                    paciente.getTratamento(),
                    paciente.getFaixaEtaria(),
                    paciente.getDia()

            });
        }

        pacientesTable.setModel(tableModel);
        pacientesTable.repaint();

    }


    private void cadastrarPaciente(){
        JTextField nomeField = new JTextField();
        JTextField idadeField = new JTextField();
        JTextField celularField = new JTextField();
        JTextField tratamentoField = new JTextField();
        JTextField generoField = new JTextField();
        JTextField diaField = new JTextField();

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

        panel.add(new JLabel("Dia:"));
        panel.add(diaField);


        int result = JOptionPane.showConfirmDialog(null, panel, "Adicionar Contato", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            String nome = nomeField.getText();
            String celular = celularField.getText();
            String idadeStr  = idadeField.getText();
            String tratamento = tratamentoField.getText();
            String genero = generoField.getText();
            String dia = diaField.getText();


            if (!nome.isEmpty() && !celular.isEmpty() && !idadeStr.isEmpty() && !tratamento.isEmpty() && !genero.isEmpty() && !dia.isEmpty() && !nome.matches(".*[;,'\\+\\.].*") && !tratamento.matches(".*[;,'\\+\\.].*") && !genero.matches(".*[;,'\\+\\.].*") && !dia.matches(".*[;,'\\+\\.].*")) {
                int idade = Integer.parseInt(idadeStr);
                Pacientes novoPaciente = new Pacientes();
                novoPaciente.setNome(nome);
                novoPaciente.setCelular(celular);
                novoPaciente.setIdade(idade);
                novoPaciente.setTratamento(tratamento);
                novoPaciente.setGenero(genero);
                novoPaciente.setDia(dia);

                PacienteDAO.adicionarPaciente(novoPaciente);
                atualizarTabelaPacientes();
            }
            else{
                JOptionPane.showMessageDialog(this, "Por favor, preencha corretamente os campos.");
                cadastrarPaciente();
            }
        }

    }

    public void editarPaciente() {
        int linhaSelecionada = pacientesTable.getSelectedRow();
        if (linhaSelecionada != -1) {
            int id = (int) pacientesTable.getValueAt(linhaSelecionada, 0);
            Pacientes pacienteExistente = PacienteDAO.buscarPacientePorId(id);
            if (pacienteExistente != null) {
                String[] opcoes = {"Nome", "Idade", "Celular", "Tratamento", "Gênero", "Dia"};
                String escolha = (String) JOptionPane.showInputDialog(null, "Escolha o campo a ser editado:", "Editar Paciente",
                        JOptionPane.QUESTION_MESSAGE, null, opcoes, opcoes[0]);

                if (escolha != null) {
                    switch (escolha) {
                        case "Nome":
                            String nome = JOptionPane.showInputDialog("Digite o novo nome do contato:", pacienteExistente.getNome());
                            if (nome != null && !nome.isEmpty()) {
                                pacienteExistente.setNome(nome);
                                PacienteDAO.atualizarPaciente(pacienteExistente);
                                atualizarTabelaPacientes();
                            } else {
                                JOptionPane.showMessageDialog(this, "Nome inválido.");
                            }
                            break;
                        case "Idade":
                            String idadeStr = JOptionPane.showInputDialog("Digite a nova idade:", pacienteExistente.getIdade());
                            if (idadeStr != null && !idadeStr.isEmpty()) {
                                int idade = Integer.parseInt(idadeStr);
                                pacienteExistente.setIdade(idade);
                                PacienteDAO.atualizarPaciente(pacienteExistente);
                                atualizarTabelaPacientes();
                            } else {
                                JOptionPane.showMessageDialog(this, "Idade inválida.");
                            }
                            break;
                        case "Celular":
                            String celular = JOptionPane.showInputDialog("Digite o novo número do celular:", pacienteExistente.getCelular());
                            if (celular != null && !celular.isEmpty()) {
                                pacienteExistente.setCelular(celular);
                                PacienteDAO.atualizarPaciente(pacienteExistente);
                                atualizarTabelaPacientes();
                            } else {
                                JOptionPane.showMessageDialog(this, "Número de celular inválido.");
                            }
                            break;
                        case "Tratamento":
                            String tratamento = JOptionPane.showInputDialog("Digite o novo nome do tratamento:", pacienteExistente.getTratamento());
                            if (tratamento != null) {
                                pacienteExistente.setTratamento(tratamento);
                                PacienteDAO.atualizarPaciente(pacienteExistente);
                                atualizarTabelaPacientes();
                            } else {
                                JOptionPane.showMessageDialog(this, "Tratamento inválido.");
                            }
                            break;
                        case "Gênero":
                            String genero = JOptionPane.showInputDialog("Digite o gênero:", pacienteExistente.getGenero());
                            if (genero != null && !genero.isEmpty()) {
                                pacienteExistente.setGenero(genero);
                                PacienteDAO.atualizarPaciente(pacienteExistente);
                                atualizarTabelaPacientes();
                            } else {
                                JOptionPane.showMessageDialog(this, "Gênero inválido.");
                            }
                            break;
                        case "Dia":
                            String dia = JOptionPane.showInputDialog("Digite o gênero:", pacienteExistente.getDia());
                            if (dia != null && !dia.isEmpty()) {
                                pacienteExistente.setGenero(dia);
                                PacienteDAO.atualizarPaciente(pacienteExistente);
                                atualizarTabelaPacientes();
                            } else {
                                JOptionPane.showMessageDialog(this, "Dia inválido.");
                            }
                            break;

                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Paciente não encontrado.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um paciente para editar.");
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

