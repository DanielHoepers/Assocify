package service;

import classes.Associado;
import classes.PagamentoMensalidade;
import daos.DaoPagamentoMensalidade;
import enuns.StatusPagamento;

import java.math.BigDecimal;

public class PagamentoMensalidadeService {

    private DaoPagamentoMensalidade dao;

    public PagamentoMensalidadeService(DaoPagamentoMensalidade dao) {
        this.dao = dao;
    }

    public PagamentoMensalidade criarObjeto(Associado associado, BigDecimal valor, BigDecimal juros, StatusPagamento status) throws Exception {
        if (valor == null || valor.compareTo(BigDecimal.ZERO) <= 0) throw new Exception("Valor inválido");
        if (status == null) throw new Exception("Status obrigatório");
        return new PagamentoMensalidade(associado, valor, juros, status);
    }

    public boolean salvarObjeto(PagamentoMensalidade pagamento) {
        return dao.Inserir(pagamento);
    }

    public boolean editarObjeto(PagamentoMensalidade pagamento) {
        return dao.Editar(pagamento);
    }

    public boolean removerObjeto(PagamentoMensalidade pagamento) {
        return dao.Remover(pagamento);
    }
}
