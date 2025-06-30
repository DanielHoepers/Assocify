package service;

import classes.Financeiro;
import classes.PagamentoMensalidade;
import daos.DaoFinanceiro;
import enuns.TipoFinanceiro;
import enuns.StatusPagamento;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class FinanceiroService {

    private DaoFinanceiro daoFinanceiro;

    public FinanceiroService(DaoFinanceiro daoFinanceiro) {
        this.daoFinanceiro = daoFinanceiro;
    }

    public Financeiro criarObjeto(BigDecimal valor, TipoFinanceiro tipo, String descricao) throws Exception {
        if (valor == null || valor.compareTo(BigDecimal.ZERO) <= 0) throw new Exception("Valor inválido");
        if (tipo == null) throw new Exception("Tipo financeiro obrigatório");
        if (descricao == null || descricao.trim().isEmpty()) throw new Exception("Descrição obrigatória");
        return new Financeiro(valor, tipo, descricao);
    }

    public boolean salvarObjeto(Financeiro financeiro) {
        return daoFinanceiro.Inserir(financeiro);
    }

    public boolean editarObjeto(Financeiro financeiro) {
        return daoFinanceiro.Editar(financeiro);
    }

    public boolean removerObjeto(Financeiro financeiro) {
        return daoFinanceiro.Remover(financeiro);
    }

    public BigDecimal calcularJuros(PagamentoMensalidade pagamento) {
        long mesesAtraso = ChronoUnit.MONTHS.between(
            pagamento.getData().withDayOfMonth(1),
            LocalDate.now().withDayOfMonth(1)
        );
        BigDecimal juros = new BigDecimal("0.05").multiply(new BigDecimal(mesesAtraso));
        BigDecimal total = pagamento.getValor().add(pagamento.getValor().multiply(juros));
        return total.setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    public void registrarPagamento(PagamentoMensalidade pagamento) {
        pagamento.setStatus(StatusPagamento.Pago);
        pagamento.setData(LocalDate.now());
        daoFinanceiro.Inserir(new Financeiro(pagamento.getValor(), TipoFinanceiro.Entrada, "Mensalidade paga"));
    }

    public void gerarRecibo(PagamentoMensalidade pagamento) {
        /*System.out.println("Recibo gerado para pagamento no valor de R$" + pagamento.getValor());*/
    }
}
