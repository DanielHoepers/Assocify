package telas;

import daos.DaoAssociado;
import daos.DaoUsuario;
import service.AutenticacaoService;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.formdev.flatlaf.FlatLightLaf;



public class TelaLogin extends JFrame {

    private JLabel loginLabel;
    private JTextField loginField;
    private JLabel senhaLabel;
    private JPasswordField senhaField;
    private JButton entrarButton;
    private JButton cancelarButton;

    private AutenticacaoService autenticacaoService;

    public TelaLogin(AutenticacaoService service) {
        this.autenticacaoService = service;
        initComponents();
    }

    private void initComponents() {
        setTitle("Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(350, 200);
        setLocationRelativeTo(null);
        setLayout(null);

        loginLabel = new JLabel("Login:");
        loginLabel.setBounds(30, 30, 80, 25);
        add(loginLabel);

        loginField = new JTextField(20);
        loginField.setBounds(100, 30, 200, 25);
        add(loginField);

        senhaLabel = new JLabel("Senha:");
        senhaLabel.setBounds(30, 70, 80, 25);
        add(senhaLabel);

        senhaField = new JPasswordField(20);
        senhaField.setBounds(100, 70, 200, 25);
        add(senhaField);

        entrarButton = new JButton("Entrar");
        entrarButton.setBounds(70, 120, 90, 30);
        add(entrarButton);

        cancelarButton = new JButton("Cancelar");
        cancelarButton.setBounds(180, 120, 90, 30);
        add(cancelarButton);

        entrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fazerLogin();
            }
        });

        cancelarButton.addActionListener(e -> System.exit(0));
    }

    private void fazerLogin() {
        String login = loginField.getText();
        String senha = new String(senhaField.getPassword());

        boolean autenticado = autenticacaoService.autenticar(login, senha);

        if (autenticado) {
            new TelaPrincipal().setVisible(true);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Login ou senha inválidos!", "Erro", JOptionPane.ERROR_MESSAGE);
            senhaField.setText("");
        }
    }

  public static void main(String[] args) {
    try {
        UIManager.setLookAndFeel(new com.formdev.flatlaf.FlatLightLaf());
    } catch (Exception ex) {
        ex.printStackTrace();
    }

    DaoUsuario daoUsuario = new DaoUsuario();
    AutenticacaoService dummy = new AutenticacaoService(daoUsuario);
    
    java.awt.EventQueue.invokeLater(() -> new TelaLogin(dummy).setVisible(true));
}

}
