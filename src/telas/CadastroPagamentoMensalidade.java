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

public class CadastroPagamentoMensalidade extends JFrame {

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
        setTitle("Cadastro de Pagamento Mensalidade");
        setSize(450, 280);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 10, 5, 10);

        JLabel lblAssociado = new JLabel("Associado:");
        cbAssociado = new JComboBox<>();

        JLabel lblValor = new JLabel("Valor:");
        tfValor = new JTextField();

        JLabel lblJuros = new JLabel("Juros:");
        tfJuros = new JTextField();

        JLabel lblStatus = new JLabel("Status:");
        cbStatus = new JComboBox<>(StatusPagamento.values());

        btnCadastrar = new JButton("Cadastrar");
        btnCancelar = new JButton("Cancelar");

        gbc.gridx = 0; gbc.gridy = 0;
        add(lblAssociado, gbc);
        gbc.gridx = 1;
        add(cbAssociado, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        add(lblValor, gbc);
        gbc.gridx = 1;
        add(tfValor, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        add(lblJuros, gbc);
        gbc.gridx = 1;
        add(tfJuros, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        add(lblStatus, gbc);
        gbc.gridx = 1;
        add(cbStatus, gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        add(btnCadastrar, gbc);
        gbc.gridx = 1;
        add(btnCancelar, gbc);

        btnCadastrar.addActionListener(e -> cadastrarPagamento());
        btnCancelar.addActionListener(e -> dispose());
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
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao cadastrar pagamento.");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage());
        }
    }
}
