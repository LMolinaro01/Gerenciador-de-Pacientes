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
import javax.swing.ImageIcon;
import javax.swing.BorderFactory;
import java.awt.Font;
import java.awt.Image;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Image;



public class PacienteGUI extends JFrame {
    private JButton cadastrarButton, editarButton, excluirButton, exibirButton;
    private JTable pacientesTable;

    public static void iniciarGUI(){
        SwingUtilities.invokeLater(()->{
            new PacienteGUI().setVisible(true);
        });
    }

    public PacienteGUI() {
        setTitle("Gerenciador de Pacientes");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(650, 625);
        setLocationRelativeTo(null);
        setResizable(false);

        Color buttonColor1 = new Color(0, 191, 124);
        Color buttonColor2 = new Color(0, 159, 98);
        Color buttonColor3 = new Color(0, 114, 67);
        Color buttonColor4 = new Color(0, 81, 53);
        Color buttonTextColor = Color.WHITE;
        Color borderColor = new Color(0, 69, 44);

        JLabel tituloLabel = new JLabel("Gerenciador de Pacientes", JLabel.CENTER);
        tituloLabel.setFont(new Font("Times New Roman", Font.BOLD, 26));
        tituloLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        ImageIcon icon = new ImageIcon("src/main/java/org/example/LOGO_Fisioterapia.jpg");
        Image image = icon.getImage();
        Image newimg = image.getScaledInstance(300, 300, java.awt.Image.SCALE_SMOOTH);
        icon = new ImageIcon(newimg);
        JLabel imagemLabel = new JLabel(icon, JLabel.CENTER);
        imagemLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));


        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(tituloLabel, BorderLayout.NORTH);
        topPanel.add(imagemLabel, BorderLayout.CENTER);

        cadastrarButton = new JButton("Adicionar");
        cadastrarButton.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        cadastrarButton.setBackground(buttonColor1);
        cadastrarButton.setForeground(buttonTextColor);
        cadastrarButton.setBorder(BorderFactory.createLineBorder(borderColor));
        cadastrarButton.setFocusPainted(false); //remove o quadrado branco do adicionar

        editarButton = new JButton("Editar");
        editarButton.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        editarButton.setBackground(buttonColor2);
        editarButton.setForeground(buttonTextColor);
        editarButton.setBorder(BorderFactory.createLineBorder(borderColor));
        editarButton.setFocusPainted(false);

        exibirButton = new JButton("Exibir Pacientes");
        exibirButton.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        exibirButton.setBackground(buttonColor3);
        exibirButton.setForeground(buttonTextColor);
        exibirButton.setBorder(BorderFactory.createLineBorder(borderColor));
        exibirButton.setFocusPainted(false);

        JButton fecharButton = new JButton("Fechar Programa"); //crei o botão de outra maneira
        fecharButton.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        fecharButton.setBackground(buttonColor4);
        fecharButton.setForeground(buttonTextColor);
        fecharButton.setBorder(BorderFactory.createLineBorder(borderColor));
        fecharButton.setFocusPainted(false);


        JPanel buttonPanel = new JPanel(new GridLayout(4, 1, 30, 30)); //(linha, coluna, padx, pady)
        buttonPanel.add(cadastrarButton);
        buttonPanel.add(editarButton);
        buttonPanel.add(exibirButton);
        buttonPanel.add(fecharButton);

        cadastrarButton.addActionListener(e -> cadastrarPaciente());
        editarButton.addActionListener(e -> telaEditar());
        exibirButton.addActionListener(e -> exibirPacientes());
        fecharButton.addActionListener(e -> System.exit(0)); // fecha o programa

        getContentPane().add(topPanel, BorderLayout.NORTH);
        getContentPane().add(buttonPanel, BorderLayout.CENTER);

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

    private void telaEditar() {
        JFrame editarFrame = new JFrame("Editar Paciente");
        editarFrame.setSize(600, 500);
        editarFrame.setLocationRelativeTo(null);
        editarFrame.setResizable(false);

        Color buttonColor1 = new Color(0, 191, 124);
        Color buttonColor2 = new Color(0, 159, 98);
        Color buttonColor3 = new Color(0, 114, 67);
        Color buttonColor4 = new Color(0, 81, 53);
        Color buttonTextColor = Color.WHITE;
        Color borderColor = new Color(0, 69, 44);


        pacientesTable = new JTable(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // impede a edição de células clicando duas vezes
            }
        };
        JScrollPane scrollPane = new JScrollPane(pacientesTable);
        scrollPane.setPreferredSize(new Dimension(600, 300));
        pacientesTable.getTableHeader().setReorderingAllowed(false); //não deixa arrastar as colunas

        JPanel buttonPanel = new JPanel(new GridLayout(3, 1, 30, 20)); // Painel para conter os botões

        JButton editarPacienteButton = new JButton("Editar Paciente"); //criei o botão de outra forma (mais simples)
        editarPacienteButton.setBackground(buttonColor1);
        editarPacienteButton.setForeground(buttonTextColor);
        editarPacienteButton.setBorder(BorderFactory.createLineBorder(borderColor));
        editarPacienteButton.setFocusPainted(false);

        JButton deletarButton = new JButton("Deletar");
        deletarButton.setBackground(buttonColor2);
        deletarButton.setForeground(buttonTextColor);
        deletarButton.setBorder(BorderFactory.createLineBorder(borderColor));
        deletarButton.setFocusPainted(false);

        JButton voltarButton = new JButton("Voltar");
        voltarButton.setBackground(buttonColor3);
        voltarButton.setForeground(buttonTextColor);
        voltarButton.setBorder(BorderFactory.createLineBorder(borderColor));
        voltarButton.setFocusPainted(false);

        buttonPanel.add(editarPacienteButton);
        buttonPanel.add(deletarButton);
        buttonPanel.add(voltarButton);

        editarPacienteButton.addActionListener(e -> editarPaciente());
        deletarButton.addActionListener(e -> deletarPaciente());
        voltarButton.addActionListener(e -> editarFrame.dispose());

        editarFrame.getContentPane().add(scrollPane, BorderLayout.NORTH);
        editarFrame.getContentPane().add(buttonPanel, BorderLayout.CENTER);


        atualizarTabelaPacientes();
        editarFrame.setVisible(true);

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

    public void exibirPacientes(){
        JFrame exibirFrame = new JFrame("Editar Paciente");
        exibirFrame.setSize(600, 480);
        exibirFrame.setLocationRelativeTo(null);
        exibirFrame.setResizable(false);;

        Color buttonColor1 = new Color(0, 191, 124);
        Color buttonTextColor = Color.WHITE;
        Color borderColor = new Color(0, 69, 44);

        pacientesTable = new JTable(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // impede a edição de células clicando duas vezes
            }
        };
        JScrollPane scrollPane = new JScrollPane(pacientesTable);
        scrollPane.setPreferredSize(new Dimension(600, 405));
        pacientesTable.getTableHeader().setReorderingAllowed(false);

        exibirFrame.getContentPane().add(scrollPane, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 1, 30, 20)); // Painel para conter os botões

        JButton voltarButton = new JButton("Voltar");
        voltarButton.addActionListener(e -> exibirFrame.dispose());
        voltarButton.setBackground(buttonColor1);
        voltarButton.setForeground(buttonTextColor);
        voltarButton.setBorder(BorderFactory.createLineBorder(borderColor));
        voltarButton.setFocusPainted(false);

        buttonPanel.add(voltarButton);
        exibirFrame.getContentPane().add(buttonPanel, BorderLayout.CENTER);

        atualizarTabelaPacientes();
        exibirFrame.setVisible(true);
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
