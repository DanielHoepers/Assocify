package telas;

import classes.Associado;
import enuns.Categoria;
import service.AssociadoService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class CadastroAssociado extends JPanel {

    private JTextField nomeField;
    private JTextField cpfField;
    private JTextField emailField;
    private JTextField telefoneField;
    private JTextField enderecoField;
    private JComboBox<Categoria> categoriaCombo;
    private JButton cadastrarButton;
    private JButton cancelarButton;

    private AssociadoService associadoService;

    public CadastroAssociado(AssociadoService service) {
        this.associadoService = service;
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout());
        setBackground(Color.decode("#F4F6F8"));

        JPanel painelForm = new JPanel(new GridBagLayout());
        painelForm.setBackground(Color.decode("#F4F6F8"));
        painelForm.setBorder(BorderFactory.createEmptyBorder(30, 0, 0, 100));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 10, 8, 10);
        gbc.anchor = GridBagConstraints.WEST;

        nomeField = new JTextField(20);
        cpfField = new JTextField(20);
        emailField = new JTextField(20);
        telefoneField = new JTextField(20);
        enderecoField = new JTextField(20);
        categoriaCombo = new JComboBox<>(Categoria.values());

        gbc.gridx = 0; gbc.gridy = 0;
        painelForm.add(new JLabel("Nome:"), gbc);
        gbc.gridx = 1;
        painelForm.add(nomeField, gbc);

        gbc.gridx = 0; gbc.gridy++;
        painelForm.add(new JLabel("CPF:"), gbc);
        gbc.gridx = 1;
        painelForm.add(cpfField, gbc);

        gbc.gridx = 0; gbc.gridy++;
        painelForm.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        painelForm.add(emailField, gbc);

        gbc.gridx = 0; gbc.gridy++;
        painelForm.add(new JLabel("Telefone:"), gbc);
        gbc.gridx = 1;
        painelForm.add(telefoneField, gbc);

        gbc.gridx = 0; gbc.gridy++;
        painelForm.add(new JLabel("Endereço:"), gbc);
        gbc.gridx = 1;
        painelForm.add(enderecoField, gbc);

        gbc.gridx = 0; gbc.gridy++;
        painelForm.add(new JLabel("Categoria:"), gbc);
        gbc.gridx = 1;
        painelForm.add(categoriaCombo, gbc);

        cadastrarButton = new JButton("Cadastrar");
        cancelarButton = new JButton("Cancelar");

        cadastrarButton.setBackground(new Color(33, 150, 243));
        cadastrarButton.setForeground(Color.WHITE);
        cancelarButton.setBackground(Color.LIGHT_GRAY);
        cancelarButton.setForeground(Color.BLACK);

        JPanel botoes = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        botoes.setBackground(Color.decode("#F4F6F8"));
        botoes.add(cadastrarButton);
        botoes.add(cancelarButton);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        painelForm.add(botoes, gbc);

        JPanel alinhamento = new JPanel(new BorderLayout());
        alinhamento.setBackground(Color.decode("#F4F6F8"));
        alinhamento.add(painelForm, BorderLayout.WEST);
        add(alinhamento, BorderLayout.NORTH);


        cadastrarButton.addActionListener(this::salvarAssociado);
        cancelarButton.addActionListener(e -> fecharAba());
    }

    private void salvarAssociado(ActionEvent e) {
        try {
            String nome = nomeField.getText();
            String cpf = cpfField.getText();
            String email = emailField.getText();
            String telefone = telefoneField.getText();
            String endereco = enderecoField.getText();
            Categoria categoria = (Categoria) categoriaCombo.getSelectedItem();

            Associado associado = associadoService.criarObjeto(nome, cpf, email, telefone, endereco, categoria);
            boolean sucesso = associadoService.salvarObjeto(associado);

            if (sucesso) {
                JOptionPane.showMessageDialog(this, "Associado cadastrado com sucesso!");
                fecharAba();
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao cadastrar associado. Verifique CPF/email.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao cadastrar: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void fecharAba() {
        Container container = this.getParent();
        while (container != null && !(container instanceof JTabbedPane)) {
            container = container.getParent();
        }
        if (container != null) {
            JTabbedPane tabs = (JTabbedPane) container;
            int index = tabs.indexOfComponent(this);
            if (index != -1) {
                tabs.remove(index);
            }
        }
    }
}
