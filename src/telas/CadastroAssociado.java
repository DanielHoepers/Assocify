package telas;

import classes.Associado;
import enuns.Categoria;
import service.AssociadoService;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CadastroAssociado extends JFrame {

    private JLabel nomeLabel;
    private JTextField nomeField;
    private JLabel cpfLabel;
    private JTextField cpfField;
    private JLabel emailLabel;
    private JTextField emailField;
    private JLabel telefoneLabel;
    private JTextField telefoneField;
    private JLabel enderecoLabel;
    private JTextField enderecoField;
    private JLabel categoriaLabel;
    private JComboBox<Categoria> categoriaCombo;
    private JButton cadastrarButton;
    private JButton cancelarButton;

    private AssociadoService associadoService;

    public CadastroAssociado(AssociadoService service) {
        this.associadoService = service;
        initComponents();
    }

    private void initComponents() {
        nomeLabel = new JLabel("Nome:");
        nomeField = new JTextField(20);

        cpfLabel = new JLabel("CPF:");
        cpfField = new JTextField(20);

        emailLabel = new JLabel("Email:");
        emailField = new JTextField(20);

        telefoneLabel = new JLabel("Telefone:");
        telefoneField = new JTextField(20);

        enderecoLabel = new JLabel("Endereço:");
        enderecoField = new JTextField(20);

        categoriaLabel = new JLabel("Categoria:");
        categoriaCombo = new JComboBox<>();
        for (Categoria c : Categoria.values()) {
            categoriaCombo.addItem(c);
        }

        cadastrarButton = new JButton("Cadastrar");
        cancelarButton = new JButton("Cancelar");

        setTitle("Cadastro de Associado");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 350);
        setLocationRelativeTo(null);
        setLayout(null);

        // Posicionamento manual (x,y,width,height)
        nomeLabel.setBounds(20, 20, 100, 25);
        nomeField.setBounds(130, 20, 220, 25);

        cpfLabel.setBounds(20, 55, 100, 25);
        cpfField.setBounds(130, 55, 220, 25);

        emailLabel.setBounds(20, 90, 100, 25);
        emailField.setBounds(130, 90, 220, 25);

        telefoneLabel.setBounds(20, 125, 100, 25);
        telefoneField.setBounds(130, 125, 220, 25);

        enderecoLabel.setBounds(20, 160, 100, 25);
        enderecoField.setBounds(130, 160, 220, 25);

        categoriaLabel.setBounds(20, 195, 100, 25);
        categoriaCombo.setBounds(130, 195, 220, 25);

        cadastrarButton.setBounds(80, 250, 100, 30);
        cancelarButton.setBounds(200, 250, 100, 30);

        add(nomeLabel);
        add(nomeField);

        add(cpfLabel);
        add(cpfField);

        add(emailLabel);
        add(emailField);

        add(telefoneLabel);
        add(telefoneField);

        add(enderecoLabel);
        add(enderecoField);

        add(categoriaLabel);
        add(categoriaCombo);

        add(cadastrarButton);
        add(cancelarButton);

        cadastrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                salvarAssociado();
            }
        });

        cancelarButton.addActionListener(e -> dispose());
    }

   private void salvarAssociado() {
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
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Erro ao cadastrar associado. Verifique se o CPF já está em uso.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    } catch (Exception ex) {
        JOptionPane.showMessageDialog(this, "Erro ao cadastrar: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
    }
}


    public static void main(String[] args) {
        // Só para teste, cria um service dummy (substitua pela real injeção de dependência)
        AssociadoService dummyService = new AssociadoService(null, null, null, null);
        java.awt.EventQueue.invokeLater(() -> new CadastroAssociado(dummyService).setVisible(true));
    }
}
