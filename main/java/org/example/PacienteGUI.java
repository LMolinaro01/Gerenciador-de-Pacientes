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
import javax.swing.UIManager;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.BoxLayout;
import javax.swing.Box;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Image;
import java.awt.FlowLayout;
import javax.swing.event.MouseInputAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JComboBox;
import java.util.List;
import java.util.Comparator;
import java.util.stream.Collectors;
import java.awt.event.MouseAdapter;



public class PacienteGUI extends JFrame {
    private JButton cadastrarButton, editarButton, excluirButton, exibirButton, fecharButton;
    private JTable pacientesTable;
    private JPanel topPanel, buttonPanel;
    
    //certos elementos foram criados a partir de códigos fornecidos pelo professor em sala de aula
    //outras funcionalidades possuem autoria minha, certos recursos tirei da documentação de swing da Oracle
    
    public static void iniciarGUI(){
        SwingUtilities.invokeLater(()->{
            new PacienteGUI().setVisible(true);
        });
    }

    public PacienteGUI() {
        setTitle("Janela Inicial");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(650, 625);
        setLocationRelativeTo(null); //centraliza a janela na tela
        setResizable(false);// Impede que a janela seja redimensionada

        // definindo as cores dos botões
        Color buttonColor1 = new Color(0, 191, 124);
        Color buttonColor2 = new Color(0, 159, 98);
        Color buttonColor3 = new Color(0, 114, 67);
        Color buttonColor4 = new Color(0, 81, 53);
        Color buttonTextColor = Color.WHITE;
        Color borderColor = new Color(0, 69, 44);

        JLabel tituloLabel = new JLabel("Gerenciador de Pacientes", JLabel.CENTER);
        tituloLabel.setFont(new Font("Times New Roman", Font.BOLD, 26));
        tituloLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        //Imagem Principal
        ImageIcon icon = new ImageIcon("src/main/java/org/example/LOGO_Fisioterapia.jpg");
        Image image = icon.getImage();
        Image newimg = image.getScaledInstance(300, 300, java.awt.Image.SCALE_SMOOTH);
        icon = new ImageIcon(newimg);
        JLabel imagemLabel = new JLabel(icon, JLabel.CENTER);
        imagemLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        //Painel do Topo (Título e Image)
        topPanel = new JPanel(new BorderLayout());
        topPanel.add(tituloLabel, BorderLayout.NORTH);
        topPanel.add(imagemLabel, BorderLayout.CENTER);
        
        //Criação dos Botões
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

        fecharButton = new JButton("Fechar Programa"); //crei o botão de outra maneira
        fecharButton.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        fecharButton.setBackground(buttonColor4);
        fecharButton.setForeground(buttonTextColor);
        fecharButton.setBorder(BorderFactory.createLineBorder(borderColor));
        fecharButton.setFocusPainted(false);


        buttonPanel = new JPanel(new GridLayout(4, 1, 30, 30)); //(linha, coluna, padx, pady)
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

        UIManager.put("Button.background", buttonColor1);
        UIManager.put("Button.foreground", buttonTextColor);
        UIManager.put("Button.border", BorderFactory.createLineBorder(borderColor));
        UIManager.put("OptionPane.okButtonText", "Concluir"); //definir o padrão ao iniciar o programa

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

    private void cadastrarPaciente() {
        
        JTextField nomeField = new JTextField();
        JTextField idadeField = new JTextField();
        JTextField celularField = new JTextField();

        Color buttonColor1 = new Color(0, 191, 124);
        Color buttonTextColor = Color.WHITE;
        Color borderColor = new Color(0, 69, 44);
        Color colorDrop = new Color(49, 146, 110, 233);


        // Dropdown para selecionar o dia da semana
        String[] diasSemana = {"Segunda-feira", "Terça-feira", "Quarta-feira", "Quinta-feira", "Sexta-feira"};
        JComboBox<String> diaComboBox = new JComboBox<>(diasSemana);
        diaComboBox.setBackground(colorDrop);
        diaComboBox.setForeground(buttonTextColor);

        // Dropdown Gênero
        String[] generos = {"Masculino", "Feminino", "Não Binário", "Homem Trans", "Mulher Trans", "Outro"};
        JComboBox<String> generoComboBox = new JComboBox<>(generos);
        generoComboBox.setBackground(colorDrop);
        generoComboBox.setForeground(buttonTextColor);

        // Dropdown Tratamento
        String[] tiposTratamento = {"Fisioterapia", "RPG", "Acupuntura"};
        JComboBox<String> tratamentoComboBox = new JComboBox<>(tiposTratamento);
        tratamentoComboBox.setBackground(colorDrop);
        tratamentoComboBox.setForeground(buttonTextColor);

        JPanel panel = new JPanel(new GridLayout(7, 2, 10, 10));
        panel.add(new JLabel("Nome:"));
        panel.add(nomeField);

        panel.add(new JLabel("Idade:"));
        panel.add(idadeField);

        panel.add(new JLabel("Celular:"));
        panel.add(celularField);

        panel.add(new JLabel("Tratamento:"));
        panel.add(tratamentoComboBox);

        panel.add(new JLabel("Gênero:"));
        panel.add(generoComboBox);

        panel.add(new JLabel("Dia:"));
        panel.add(diaComboBox);

        UIManager.put("Button.background", buttonColor1);
        UIManager.put("Button.foreground", buttonTextColor);
        UIManager.put("Button.border", BorderFactory.createLineBorder(borderColor));
        UIManager.put("OptionPane.okButtonText", "Adicionar");

        int result = JOptionPane.showConfirmDialog(null, panel, "Cadastrar Paciente", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            String nome = nomeField.getText();
            String celular = celularField.getText();
            String idadeStr  = idadeField.getText();
            String tratamento = (String) tratamentoComboBox.getSelectedItem();
            String genero = (String) generoComboBox.getSelectedItem();
            String dia = (String) diaComboBox.getSelectedItem();

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
            } else {
                JOptionPane.showMessageDialog(this, "Por favor, preencha corretamente os campos.");
                cadastrarPaciente();
            }
        }
    }

    private void telaEditar() {
        JFrame editarFrame = new JFrame("Janela Edição");

        editarFrame.setSize(700, 500);
        editarFrame.setLocationRelativeTo(null);
        editarFrame.setResizable(false);

        Color buttonColor1 = new Color(0, 191, 124);
        Color buttonColor2 = new Color(0, 159, 98);
        Color buttonColor3 = new Color(0, 114, 67);
        Color buttonTextColor = Color.WHITE;
        Color borderColor = new Color(0, 69, 44);

        //Mudança no Padrão da Tabela
        pacientesTable = new JTable(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // impede a edição das células clicando duas vezes
            }
        };
        
        JScrollPane scrollPane = new JScrollPane(pacientesTable); // coloca a tabela em um scroll pane
        scrollPane.setPreferredSize(new Dimension(600, 300)); // define o tamanho do scroll pane
        pacientesTable.getTableHeader().setReorderingAllowed(false); // não deixa arrastar as colunas

        pacientesTable.addMouseListener(new MouseInputAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {

                    JTable target = (JTable) e.getSource();
                    int row = target.getSelectedRow();
                    int column = target.getSelectedColumn();
                    Object value = target.getValueAt(row, column);
                    UIManager.put("Button.background", buttonColor1);
                    UIManager.put("Button.foreground", buttonTextColor);
                    UIManager.put("Button.border", BorderFactory.createLineBorder(borderColor));
                    UIManager.put("OptionPane.okButtonText", "Concluir");
                    JOptionPane.showMessageDialog(editarFrame, "Valor da Célula:  " + value, "Informação", JOptionPane.PLAIN_MESSAGE);
                }
            }
        });

        // painel de botões e organiza eles verticalmente
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50)); // espaçamento ao redor dos botões

        Dimension buttonSize = new Dimension(600, 50); // tamanho máximo dos botões

        //Criação dos Botões
        JButton editarPacienteButton = new JButton("Editar Paciente");
        editarPacienteButton.setBackground(buttonColor1);
        editarPacienteButton.setForeground(buttonTextColor);
        editarPacienteButton.setBorder(BorderFactory.createLineBorder(borderColor));
        editarPacienteButton.setFocusPainted(false);
        editarPacienteButton.setPreferredSize(buttonSize);
        editarPacienteButton.setMaximumSize(buttonSize);

        JButton deletarButton = new JButton("Deletar");
        deletarButton.setBackground(buttonColor2);
        deletarButton.setForeground(buttonTextColor);
        deletarButton.setBorder(BorderFactory.createLineBorder(borderColor));
        deletarButton.setFocusPainted(false);
        deletarButton.setPreferredSize(buttonSize);
        deletarButton.setMaximumSize(buttonSize);

        JButton voltarButton = new JButton("Voltar");
        voltarButton.setBackground(buttonColor3);
        voltarButton.setForeground(buttonTextColor);
        voltarButton.setBorder(BorderFactory.createLineBorder(borderColor));
        voltarButton.setFocusPainted(false);
        voltarButton.setPreferredSize(buttonSize);
        voltarButton.setMaximumSize(buttonSize);

        buttonPanel.add(editarPacienteButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 20))); // espaçamento vertical
        buttonPanel.add(deletarButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 20))); // espaçamento vertical
        buttonPanel.add(voltarButton);

        editarPacienteButton.addActionListener(e -> editarPaciente());
        deletarButton.addActionListener(e -> deletarPaciente(editarFrame));
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
                            String dia = JOptionPane.showInputDialog("Digite o dia:", pacienteExistente.getDia());
                            if (dia != null && !dia.isEmpty()) {
                                pacienteExistente.setDia(dia); // Corrigido para definir o dia
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

    public void exibirPacientes() {
        JFrame exibirFrame = new JFrame("Janela de Exibição");
        exibirFrame.setSize(700, 480);
        exibirFrame.setLocationRelativeTo(null);
        exibirFrame.setResizable(false);

        Color buttonColor1 = new Color(0, 191, 124);
        Color buttonTextColor = Color.WHITE;
        Color borderColor = new Color(0, 69, 44);
        Color colorDrop = new Color(49, 146, 110, 233);

        //Mudança no Padrão da Tabela
        pacientesTable = new JTable() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // impede a edição de células clicando duas vezes
            }
        };

        JScrollPane scrollPane = new JScrollPane(pacientesTable);
        scrollPane.setPreferredSize(new Dimension(600, 378));
        pacientesTable.getTableHeader().setReorderingAllowed(false);

        // duplo clique à tabela
        pacientesTable.addMouseListener(new MouseInputAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    JTable target = (JTable) e.getSource();
                    int row = target.getSelectedRow();
                    int column = target.getSelectedColumn();
                    Object value = target.getValueAt(row, column);
                    UIManager.put("Button.background", buttonColor1);
                    UIManager.put("Button.foreground", buttonTextColor);
                    UIManager.put("Button.border", BorderFactory.createLineBorder(borderColor));
                    UIManager.put("OptionPane.okButtonText", "Concluído");
                    JOptionPane.showMessageDialog(exibirFrame, "Valor da Célula:  " + value, "Informação", JOptionPane.PLAIN_MESSAGE);
                }
            }
        });

        JPanel topPanel = new JPanel(new BorderLayout());
        JLabel ordenarLabel = new JLabel("Ordenar por:");
        String[] opcoesOrdenacao = { "ID", "Ordem alfabética", "Dia da Semana", "Tratamento", "Idade", "Gênero"};
        JComboBox<String> ordenarComboBox = new JComboBox<>(opcoesOrdenacao);
        Color whiteColor = Color.WHITE;

        ordenarComboBox.addActionListener(e -> {
            String selecao = (String) ordenarComboBox.getSelectedItem();
            ordenarPacientes(selecao);
        });

        JPanel ordenarPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        ordenarPanel.add(ordenarLabel);
        ordenarPanel.add(ordenarComboBox);
        ordenarComboBox.setBackground(colorDrop);
        ordenarComboBox.setForeground(whiteColor);

        topPanel.add(ordenarPanel, BorderLayout.NORTH);
        topPanel.add(scrollPane, BorderLayout.CENTER);

        exibirFrame.getContentPane().add(topPanel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 1, 30, 20)); // Painel para conter os botões

        JButton okButton = new JButton("OK");
        okButton.addActionListener(e -> exibirFrame.dispose());
        okButton.setBackground(buttonColor1);
        okButton.setForeground(buttonTextColor);
        okButton.setBorder(BorderFactory.createLineBorder(borderColor));
        okButton.setFocusPainted(false);

        buttonPanel.add(okButton);
        exibirFrame.getContentPane().add(buttonPanel, BorderLayout.CENTER);

        atualizarTabelaPacientes();
        exibirFrame.setVisible(true);
    }

    private void ordenarPacientes(String criterio) {
        List<Pacientes> pacientesList = PacienteDAO.listarPacientes();

        DefaultTableModel tableModel = new DefaultTableModel(
                new Object[]{"ID", "Nome", "Idade", "Celular", "Gênero", "Tratamento", "Faixa Etária", "Dia"}, 0);

        pacientesTable.setModel(tableModel);
        pacientesTable.repaint();

        switch (criterio) {
            case "Ordem alfabética":
                pacientesList = pacientesList.stream()
                        .sorted(Comparator.comparing(Pacientes::getNome))
                        .collect(Collectors.toList());
                break;
            case "ID":
                pacientesList = pacientesList.stream()
                        .sorted(Comparator.comparing(Pacientes::getId))
                        .collect(Collectors.toList());
                break;
            case "Idade":
                pacientesList = pacientesList.stream()
                        .sorted(Comparator.comparingInt(Pacientes::getIdade))
                        .collect(Collectors.toList());
                break;
            case "Gênero":
                pacientesList = pacientesList.stream()
                        .sorted(Comparator.comparing(Pacientes::getGenero))
                        .collect(Collectors.toList());
                break;
            case "Tratamento":
                pacientesList = pacientesList.stream()
                        .sorted(Comparator.comparing(Pacientes::getTratamento))
                        .collect(Collectors.toList());
                break;
            case "Dia da Semana":
                pacientesList = pacientesList.stream()
                        .sorted(Comparator.comparing(Pacientes::getDia))
                        .collect(Collectors.toList());
                break;
        }

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


    public void deletarPaciente(JFrame editarFrame) {
        int selectedRow = pacientesTable.getSelectedRow();
        if (selectedRow != -1) {
            int id = (int) pacientesTable.getValueAt(selectedRow, 0);
            String nomePaciente = (String) pacientesTable.getValueAt(selectedRow, 1); // Assumindo que o nome está na segunda coluna

            // cria uma mensagem de confirmação com o nome do paciente
            int confirm = JOptionPane.showConfirmDialog(
                    editarFrame, // Passa o frame como pai para manter o foco
                    "Você realmente deseja excluir o paciente '" + nomePaciente + "' ?",
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
            JOptionPane.showMessageDialog(editarFrame, "Selecione um paciente para excluir.");
        }
    }

    public static void main(String[] args) {
        PacienteDAO.criarTabela();
        SwingUtilities.invokeLater(() -> {
            new PacienteGUI().setVisible(true);
        });

    }

}
