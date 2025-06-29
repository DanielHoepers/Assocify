package service;

import classes.Associado;
import classes.Notificacao;
import classes.PagamentoMensalidade;
import daos.DaoAssociado;
import daos.DaoPagamentoMensalidade;
import enuns.Categoria;
import enuns.StatusPagamento;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class AssociadoService {

    private DaoAssociado daoAssociado;
    private DaoPagamentoMensalidade daoPagamento;
    private NotificacaoService notificacaoService;

  
    public AssociadoService(DaoAssociado daoAssociado, DaoPagamentoMensalidade daoPagamento, NotificacaoService notificacaoService) {
        this.daoAssociado = daoAssociado;
        this.daoPagamento = daoPagamento;
        this.notificacaoService = notificacaoService;
    }

    
    public Associado criarObjeto(String nomeCompleto, String cpf, String email, String telefone, String endereco, Categoria categoria) throws Exception {
        if (nomeCompleto == null || nomeCompleto.isEmpty()) throw new Exception("Nome obrigatório");
        if (cpf == null || cpf.length() != 11) throw new Exception("CPF inválido");
        if (email == null || !email.contains("@")) throw new Exception("Email inválido");
        if (telefone == null || telefone.isEmpty()) throw new Exception("Telefone obrigatório");
        if (endereco == null || endereco.isEmpty()) throw new Exception("Endereço obrigatório");
        if (categoria == null) throw new Exception("Categoria obrigatória");

        return new Associado(nomeCompleto, cpf, email, telefone, endereco, categoria);
    }

    
    public void salvarObjeto(Associado associado) {
        daoAssociado.Inserir(associado);

        try {
            Notificacao notificacao = notificacaoService.criarNotificacao(
                associado,
                "Bem-vindo(a), " + associado.getNomeCompleto() + "! Sua associação foi concluída com sucesso.",
                false,
                true
            );
            notificacaoService.salvarNotificacao(notificacao);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean editarObjeto(Associado associado) {
        return daoAssociado.Editar(associado);
    }

    public boolean removerObjeto(Associado associado) {
        return daoAssociado.Remover(associado);
    }

    
    public void verificarInadimplencia(Associado associado, List<PagamentoMensalidade> pagamentos) {
    long atrasadas = pagamentos.stream()
    .filter(p -> p.getStatus() == StatusPagamento.Pendente)
    .filter(p -> p.getData().isBefore(LocalDate.now().minusMonths(3)))
    .count();

       if (atrasadas >= 3) {
    associado.setContaDesativada(true);
}
       
       
}


public BigDecimal calcularSaldoAssociado(Associado associado) {
    List<PagamentoMensalidade> pagamentos = daoPagamento.buscarPorAssociado(associado);

    return pagamentos.stream()
        .filter(p -> p.getStatus() == StatusPagamento.Pendente)
        .map(PagamentoMensalidade::getValor)
        .reduce(BigDecimal.ZERO, BigDecimal::add);
}

 public Associado buscarPorId(int id) {
        return daoAssociado.selecionar(id);
    }
   
}
