package telas;

import daos.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import service.*;

public class TelaPrincipal extends JFrame {

    private JTabbedPane tabPane;
    
    

    private DaoAssociado daoAssociado = new DaoAssociado();
    private DaoPagamentoMensalidade daoPagamento = new DaoPagamentoMensalidade();
    private DaoNotificacao daoNotificacao = new DaoNotificacao();
    private DaoUsuario daoUsuario = new DaoUsuario();

    private EmailService emailService = new EmailService();
    private NotificacaoService notificacaoService = new NotificacaoService(daoNotificacao, emailService);
    private AutenticacaoService autenticacaoService = new AutenticacaoService(daoUsuario);
    private AssociadoService associadoService = new AssociadoService(daoAssociado, daoPagamento, notificacaoService, autenticacaoService);
    private PagamentoMensalidadeService pagamentoService = new PagamentoMensalidadeService(daoPagamento);

    public TelaPrincipal() {
        setTitle("Assocify");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        // Lateral mais escura
        JPanel painelLateral = new JPanel();
        painelLateral.setBackground(new Color(211, 211, 211)); // cinza escuro
        painelLateral.setLayout(new BoxLayout(painelLateral, BoxLayout.Y_AXIS));
        painelLateral.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        painelLateral.setPreferredSize(new Dimension(200, getHeight()));

        JButton btnAssociado = new JButton("Cadastrar Associado");
        JButton btnPagamento = new JButton("Cadastrar Pagamento");

        configurarBotao(btnAssociado, new Color(33, 150, 243), Color.WHITE);
        configurarBotao(btnPagamento, new Color(33, 150, 243), Color.WHITE);

        painelLateral.add(Box.createRigidArea(new Dimension(0, 5)));
        painelLateral.add(btnAssociado);
        painelLateral.add(Box.createRigidArea(new Dimension(0, 10)));
        painelLateral.add(btnPagamento);
        painelLateral.add(Box.createVerticalGlue());

        tabPane = new JTabbedPane();
        tabPane.setBackground(new Color(211, 211, 211));
        tabPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);

        btnAssociado.addActionListener((ActionEvent e) -> {
            adicionarComAbaFechavel("Cadastro de Associado", new CadastroAssociado(associadoService));
        });

        btnPagamento.addActionListener((ActionEvent e) -> {
            adicionarComAbaFechavel("Cadastro de Pagamento", new CadastroPagamentoMensalidade(pagamentoService, associadoService));
        });

        // Separador vertical entre painel lateral e tabPane
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, painelLateral, tabPane);
        splitPane.setDividerSize(2); 
        splitPane.setEnabled(false); 
        splitPane.setDividerLocation(200); 
        splitPane.setBorder(null);

        add(splitPane, BorderLayout.CENTER);
    }

    private void configurarBotao(JButton botao, Color bg, Color fg) {
        botao.setFocusPainted(false);
        botao.setBackground(bg);
        botao.setForeground(fg);
        botao.setFont(new Font("Segoe UI", Font.BOLD, 12));
        botao.setAlignmentX(Component.CENTER_ALIGNMENT);
        botao.setMaximumSize(new Dimension(160, 30));
        botao.setPreferredSize(new Dimension(160, 30));
    }

    private void adicionarComAbaFechavel(String titulo, JPanel tela) {
        tabPane.addTab(titulo, tela);
        int index = tabPane.indexOfComponent(tela);

        JPanel abaComBotaoFechar = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        abaComBotaoFechar.setOpaque(false);

        JLabel tituloLabel = new JLabel(" " + titulo + " ");
        JButton fecharBtn = new JButton("x");
        fecharBtn.setMargin(new Insets(0, 4, 0, 4));
        fecharBtn.setFont(new Font("Arial", Font.BOLD, 10));
        fecharBtn.setFocusPainted(false);
        fecharBtn.setBorder(null);
        fecharBtn.setContentAreaFilled(false);
        fecharBtn.setForeground(Color.BLACK);

        fecharBtn.addActionListener(e -> {
            tabPane.remove(tela);
        });

        abaComBotaoFechar.add(tituloLabel);
        abaComBotaoFechar.add(fecharBtn);

        tabPane.setTabComponentAt(index, abaComBotaoFechar);
        tabPane.setSelectedComponent(tela);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TelaPrincipal tela = new TelaPrincipal();
            tela.setVisible(true);
        });
    }
}
