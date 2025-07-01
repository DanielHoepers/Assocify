package telas;

import classes.Associado;
import classes.PagamentoMensalidade;
import enuns.StatusPagamento;
import service.AssociadoService;
import service.PagamentoMensalidadeService;

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class CadastroPagamentoMensalidade extends JPanel {

    private JComboBox<Associado> cbAssociado;
    private JTextField tfValor;
    private JTextField tfJuros;
    private JComboBox<StatusPagamento> cbStatus;
    private JButton btnCadastrar, btnCancelar;

    private PagamentoMensalidadeService pagamentoService;
    private AssociadoService associadoService;

    public CadastroPagamentoMensalidade(PagamentoMensalidadeService pagamentoService, AssociadoService associadoService) {
        this.pagamentoService = pagamentoService;
        this.associadoService = associadoService;

        initComponents();
        carregarAssociados();
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

        cbAssociado = new JComboBox<>();
        cbAssociado.setPreferredSize(new Dimension(240, 25));
        tfValor = new JTextField(20);
        tfJuros = new JTextField(20);
        cbStatus = new JComboBox<>(StatusPagamento.values());

        gbc.gridx = 0; gbc.gridy = 0;
        painelForm.add(new JLabel("Associado:"), gbc);
        gbc.gridx = 1;
        painelForm.add(cbAssociado, gbc);

        gbc.gridx = 0; gbc.gridy++;
        painelForm.add(new JLabel("Valor:"), gbc);
        gbc.gridx = 1;
        painelForm.add(tfValor, gbc);

        gbc.gridx = 0; gbc.gridy++;
        painelForm.add(new JLabel("Juros:"), gbc);
        gbc.gridx = 1;
        painelForm.add(tfJuros, gbc);

        gbc.gridx = 0; gbc.gridy++;
        painelForm.add(new JLabel("Status:"), gbc);
        gbc.gridx = 1;
        painelForm.add(cbStatus, gbc);

        btnCadastrar = new JButton("Cadastrar");
        btnCancelar = new JButton("Cancelar");

        btnCadastrar.setBackground(new Color(33, 150, 243));
        btnCadastrar.setForeground(Color.WHITE);
        btnCancelar.setBackground(Color.LIGHT_GRAY);
        btnCancelar.setForeground(Color.BLACK);

        JPanel botoes = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        botoes.setBackground(Color.decode("#F4F6F8"));
        botoes.add(btnCadastrar);
        botoes.add(btnCancelar);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        painelForm.add(botoes, gbc);

        JPanel alinhamento = new JPanel(new BorderLayout());
        alinhamento.setBackground(Color.decode("#F4F6F8"));
        alinhamento.add(painelForm, BorderLayout.WEST);
        add(alinhamento, BorderLayout.NORTH);


        add(alinhamento, BorderLayout.NORTH);

        btnCadastrar.addActionListener(e -> cadastrarPagamento());
        btnCancelar.addActionListener(e -> fecharAba());
    }

    private void carregarAssociados() {
        try {
            List<Associado> associados = associadoService.listarAssociados();
            DefaultComboBoxModel<Associado> model = new DefaultComboBoxModel<>();
            for (Associado a : associados) {
                model.addElement(a);
            }
            cbAssociado.setModel(model);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar associados: " + ex.getMessage());
        }
    }

    private void cadastrarPagamento() {
        try {
            Associado associado = (Associado) cbAssociado.getSelectedItem();
            if (associado == null) {
                JOptionPane.showMessageDialog(this, "Selecione um associado válido.");
                return;
            }

            BigDecimal valor = new BigDecimal(tfValor.getText().trim());
            BigDecimal juros = new BigDecimal(tfJuros.getText().trim());
            StatusPagamento status = (StatusPagamento) cbStatus.getSelectedItem();

            PagamentoMensalidade pagamento = pagamentoService.criarObjeto(associado, valor, juros, status);
            pagamento.setData(LocalDate.now());

            boolean sucesso = pagamentoService.salvarObjeto(pagamento);

            if (sucesso) {
                JOptionPane.showMessageDialog(this, "Pagamento cadastrado com sucesso!");
                fecharAba();
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao cadastrar pagamento.");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage());
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
