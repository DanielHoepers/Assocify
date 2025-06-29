package main;

import classes.Associado;
import classes.Carteirinha;
import classes.Evento;
import classes.Feedback;
import classes.Financeiro;
import classes.Notificacao;
import classes.PagamentoMensalidade;
import classes.ParticipacaoTorneiro;
import classes.Passaro;
import classes.Torneio;
import classes.Usuario;

import daos.DaoAssociado;
import daos.DaoCarteirinha;
import daos.DaoEvento;
import daos.DaoFeedback;
import daos.DaoFinanceiro;
import daos.DaoNotificacao;
import daos.DaoPagamentoMensalidade;
import daos.DaoParticipacaoTorneiro;
import daos.DaoPassaro;
import daos.DaoTorneio;
import daos.DaoUsuario;

import enuns.Categoria;
import enuns.StatusPagamento;
import enuns.TipoFinanceiro;
import enuns.TipoTorneio;
import enuns.TipoUsuario;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        Associado associado = new Associado("Daniel", "12345678900", "daniel@email.com", "4999999999", "Rua Teste", Categoria.Ativo);
        DaoAssociado daoAssociado = new DaoAssociado();
        daoAssociado.Inserir(associado);

        Carteirinha carteirinha = new Carteirinha(associado, "SOC123", "Pai e Mãe", LocalDate.now().plusYears(1), "APVC", "12.345.678/0001-00");
        DaoCarteirinha daoCarteirinha = new DaoCarteirinha();
        daoCarteirinha.Inserir(carteirinha);

        Evento evento = new Evento("Festival", "Grande evento", "Salão", 100);
        DaoEvento daoEvento = new DaoEvento();
        daoEvento.Inserir(evento);

        Torneio torneio = new Torneio("Torneio de Canto", LocalDate.now(), "Ginásio", TipoTorneio.Canto, false);
        DaoTorneio daoTorneio = new DaoTorneio();
        daoTorneio.Inserir(torneio);

        Feedback feedback = new Feedback(associado, evento, torneio, "Muito bom");
        DaoFeedback daoFeedback = new DaoFeedback();
        daoFeedback.Inserir(feedback);

        Financeiro financeiro = new Financeiro(new BigDecimal("150.00"), TipoFinanceiro.Entrada, "Mensalidade recebida");
        DaoFinanceiro daoFinanceiro = new DaoFinanceiro();
        daoFinanceiro.Inserir(financeiro);

        Notificacao notificacao = new Notificacao(associado, "Você está inadimplente!", false, true);
        DaoNotificacao daoNotificacao = new DaoNotificacao();
        daoNotificacao.Inserir(notificacao);

        PagamentoMensalidade pagamento = new PagamentoMensalidade(associado, new BigDecimal("50.00"), new BigDecimal("5.00"), StatusPagamento.Pago);
        DaoPagamentoMensalidade daoPagamento = new DaoPagamentoMensalidade();
        daoPagamento.Inserir(pagamento);

        Passaro passaro1 = new Passaro(associado, "Canarinho", "Canário", "Belga", LocalDate.of(2021, 5, 1));
        DaoPassaro daoPassaro = new DaoPassaro();
        daoPassaro.Inserir(passaro1);

        List<Passaro> passaros = new ArrayList<>();
        passaros.add(passaro1);

        ParticipacaoTorneiro participacao = new ParticipacaoTorneiro(torneio, associado, passaros, true, 1);
        DaoParticipacaoTorneiro daoParticipacao = new DaoParticipacaoTorneiro();
        daoParticipacao.Inserir(participacao);

        Usuario usuario = new Usuario("admin", "123456", TipoUsuario.Administrador, true);
        DaoUsuario daoUsuario = new DaoUsuario();
        daoUsuario.Inserir(usuario);

        Associado a = daoAssociado.selecionar(associado.getId());
        System.out.println("Associado inserido: " + a.getNomeCompleto());
    }
}
