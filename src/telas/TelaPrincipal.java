package telas;

import daos.DaoAssociado;
import daos.DaoNotificacao;
import daos.DaoPagamentoMensalidade;
import daos.DaoUsuario;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import service.AssociadoService;
import service.AutenticacaoService;
import service.EmailService;
import service.NotificacaoService;
import service.PagamentoMensalidadeService;

public class TelaPrincipal extends JFrame {

    public TelaPrincipal() {
        initComponents();
    }

    private void initComponents() {
        setTitle("Assocify");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);

        JPanel topoPanel = new JPanel();
        topoPanel.setLayout(new BorderLayout());
        topoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JLabel tituloLabel = new JLabel("Assocify");
        tituloLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        topoPanel.add(tituloLabel, BorderLayout.WEST);

        JSeparator separator = new JSeparator();

        JPanel lateralPanel = new JPanel();
        lateralPanel.setLayout(new BoxLayout(lateralPanel, BoxLayout.Y_AXIS));
        lateralPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        lateralPanel.setPreferredSize(new Dimension(180, 0));

        JButton btnCadastroAssociado = new JButton("Cadastrar Associado");
        btnCadastroAssociado.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnCadastroAssociado.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        btnCadastroAssociado.addActionListener(this::abrirCadastroAssociado);

        JButton btnCadastroPagamento = new JButton("Cadastrar Pagamento");
        btnCadastroPagamento.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnCadastroPagamento.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        btnCadastroPagamento.addActionListener(this::abrirCadastroPagamento);

        lateralPanel.add(btnCadastroAssociado);
        lateralPanel.add(Box.createRigidArea(new Dimension(0, 10))); 
        lateralPanel.add(btnCadastroPagamento);
        lateralPanel.add(Box.createVerticalGlue());

        JPanel conteudoPanel = new JPanel();
        conteudoPanel.setBackground(Color.WHITE);

        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());
        contentPane.add(topoPanel, BorderLayout.NORTH);
        contentPane.add(separator, BorderLayout.AFTER_LAST_LINE);
        contentPane.add(lateralPanel, BorderLayout.WEST);
        contentPane.add(conteudoPanel, BorderLayout.CENTER);
    }

    private void abrirCadastroAssociado(ActionEvent e) {
        DaoAssociado daoAssociado = new DaoAssociado(); 
        DaoNotificacao daoNotificacao = new DaoNotificacao();
        EmailService emailService = new EmailService();
        DaoPagamentoMensalidade daoPagamento = new DaoPagamentoMensalidade();
        NotificacaoService notificacaoService = new NotificacaoService(daoNotificacao, emailService);
        DaoUsuario daoUsuario = new DaoUsuario();
        AutenticacaoService autenticacaoService = new AutenticacaoService(daoUsuario);
        
        AssociadoService service = new AssociadoService(daoAssociado, daoPagamento, notificacaoService, autenticacaoService);
        CadastroAssociado cadastro = new CadastroAssociado(service);
        cadastro.setVisible(true);  
    }

    private void abrirCadastroPagamento(ActionEvent e) {
        DaoPagamentoMensalidade daoPagamentoMensalidade = new DaoPagamentoMensalidade();
        PagamentoMensalidadeService pagamentoService = new PagamentoMensalidadeService(daoPagamentoMensalidade);
        CadastroPagamentoMensalidade cadastroPagamento = new CadastroPagamentoMensalidade(pagamentoService, null);
        cadastroPagamento.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TelaPrincipal principal = new TelaPrincipal();
            principal.setVisible(true);
        });
    }
}
