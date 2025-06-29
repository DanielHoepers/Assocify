package Testes;

import classes.*;
import daos.*;
import enuns.*;
import org.junit.Test;
import service.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class DanielTestes {

    @Test
    public void testAssociadoRecemCadastradoEstaAtivo() throws Exception {
        // Arrange
        DaoAssociado daoAssociado = mock(DaoAssociado.class);
        DaoPagamentoMensalidade daoPagamento = mock(DaoPagamentoMensalidade.class);
        NotificacaoService notificacaoService = mock(NotificacaoService.class);

        AssociadoService service = new AssociadoService(daoAssociado, daoPagamento, notificacaoService);
        Associado associado = service.criarObjeto("daniel hoepers", "12345678900", "danielrch.hoepers@gmail.com", "47999998888", "rufolfo mass, 11", Categoria.Ativo);

        // Act
        service.salvarObjeto(associado);

        // Assert
        assertFalse(associado.isContaDesativada());
    }

    @Test
    public void testVerificarInadimplencia() throws Exception {
        // Arrange
        AssociadoService associadoService = new AssociadoService(null, null, null);
        PagamentoMensalidadeService pagamentoService = new PagamentoMensalidadeService(null);

        Associado associado = associadoService.criarObjeto("daniel hoepers", "12345678900", "danielrch.hoepers@gmail.com", "47999998888", "rufolfo mass, 11", Categoria.Ativo);

        PagamentoMensalidade p1 = pagamentoService.criarObjeto(associado, new BigDecimal("150.00"), BigDecimal.ZERO, StatusPagamento.Pendente);
        p1.setData(LocalDate.now().minusMonths(4).minusDays(1));
        PagamentoMensalidade p2 = pagamentoService.criarObjeto(associado, new BigDecimal("150.00"), BigDecimal.ZERO, StatusPagamento.Pendente);
        p2.setData(LocalDate.now().minusMonths(5).minusDays(1));
        PagamentoMensalidade p3 = pagamentoService.criarObjeto(associado, new BigDecimal("150.00"), BigDecimal.ZERO, StatusPagamento.Pendente);
        p3.setData(LocalDate.now().minusMonths(6).minusDays(1));

        List<PagamentoMensalidade> pagamentos = List.of(p1, p2, p3);

        // Act
        associadoService.verificarInadimplencia(associado, pagamentos);

        // Assert
        assertTrue(associado.isContaDesativada());
    }

    @Test
    public void testCalcularSaldoAssociado() throws Exception {
        // Arrange
        DaoAssociado daoAssociado = mock(DaoAssociado.class);
        DaoPagamentoMensalidade daoPagamento = mock(DaoPagamentoMensalidade.class);
        NotificacaoService notificacaoMock = mock(NotificacaoService.class);

        AssociadoService service = new AssociadoService(daoAssociado, daoPagamento, notificacaoMock);
        PagamentoMensalidadeService pagamentoService = new PagamentoMensalidadeService(daoPagamento);

        Associado associado = service.criarObjeto("daniel hoepers", "12345678900", "danielrch.hoepers@gmail.com", "47999998888", "rufolfo mass, 11", Categoria.Honorário);

        PagamentoMensalidade pagamento = pagamentoService.criarObjeto(associado, new BigDecimal("120.00"), BigDecimal.ZERO, StatusPagamento.Pendente);

        when(daoPagamento.buscarPorAssociado(associado)).thenReturn(List.of(pagamento));

        // Act
        BigDecimal saldo = service.calcularSaldoAssociado(associado);

        // Assert
        assertEquals(new BigDecimal("120.00"), saldo);
    }

    @Test
    public void testCalcularJuros() throws Exception {
        // Arrange
        DaoPagamentoMensalidade daoPagamento = mock(DaoPagamentoMensalidade.class);
        PagamentoMensalidadeService pagamentoService = new PagamentoMensalidadeService(daoPagamento);

        DaoFinanceiro daoFinanceiro = mock(DaoFinanceiro.class);
        FinanceiroService financeiroService = new FinanceiroService(daoFinanceiro);

        PagamentoMensalidade pagamento = pagamentoService.criarObjeto(null, new BigDecimal("200.00"), BigDecimal.ZERO, StatusPagamento.Pendente);
        pagamento.setData(LocalDate.now().minusMonths(2));

        // Act
        BigDecimal total = financeiroService.calcularJuros(pagamento);

        // Assert
        assertEquals(new BigDecimal("220.00"), total);
    }

    @Test
    public void testValidarInscricaoInadimplente() throws Exception {
        // Arrange
        DaoAssociado daoAssociado = mock(DaoAssociado.class);
        DaoPagamentoMensalidade daoPagamento = mock(DaoPagamentoMensalidade.class);
        NotificacaoService notificacaoService = mock(NotificacaoService.class);
        DaoTorneio daoTorneio = mock(DaoTorneio.class);
        DaoParticipacaoTorneiro daoParticipacaoTorneio = mock(DaoParticipacaoTorneiro.class);

        AssociadoService associadoService = new AssociadoService(daoAssociado, daoPagamento, notificacaoService);
        Associado associado = associadoService.criarObjeto("joão pereira", "98765432100", "joao.pereira@email.com", "47988887777", "avenida central, 50", Categoria.Ativo);
        associado.setInadimplente(true);

        TorneioService service = new TorneioService(daoTorneio, daoParticipacaoTorneio);

        // Act
        boolean exceptionThrown = false;
        try {
            service.validarInscricao(associado);
        } catch (RuntimeException e) {
            exceptionThrown = true;
        }

        // Assert
        assertTrue(exceptionThrown);
    }

    @Test
    public void testNotificacaoBoasVindas() throws Exception {
        // Arrange
        DaoNotificacao daoMock = mock(DaoNotificacao.class);
        NotificacaoService notificacaoService = new NotificacaoService(daoMock);

        DaoAssociado daoA = mock(DaoAssociado.class);
        DaoPagamentoMensalidade daoP = mock(DaoPagamentoMensalidade.class);
        AssociadoService associadoService = new AssociadoService(daoA, daoP, notificacaoService);

        Associado associado = associadoService.criarObjeto("daniel hoepers", "12345678900", "danielrch.hoepers@gmail.com", "47999998888", "rufolfo mass, 11", Categoria.Ativo);
        associadoService.salvarObjeto(associado);

        Notificacao notificacao = new Notificacao(associado, "seja muito bem-vindo à associação!", false, false);
        when(daoMock.buscarPorAssociado(associado)).thenReturn(List.of(notificacao));

        // Act
        List<Notificacao> notificacoes = notificacaoService.buscarPorAssociado(associado);

        // Assert
        assertTrue(notificacoes.stream().anyMatch(n -> n.getMensagem().toLowerCase().contains("bem-vindo")));
    }

    @Test
    public void testInsercaoFeedback() throws Exception {
        // Arrange
        DaoFeedback daoMock = mock(DaoFeedback.class);
        FeedbackService service = new FeedbackService(daoMock);

        Associado associado = new Associado("lucas martins", "99887766554", "lucas.martins@email.com", "47977777777", "rua das acácias, 77", Categoria.Ativo);
        Evento evento = new Evento("festa junina", "festa típica", "salão social", 100);
        Torneio torneio = new Torneio("canto dos pássaros", LocalDate.now(), "clube central", TipoTorneio.Canto, false);

        // Act
        Feedback feedback = service.criarObjeto(associado, evento, torneio, "excelente organização!");
        service.salvarObjeto(feedback);

        // Assert
        assertEquals(StatusFeedback.Enviado, feedback.getStatus());
    }

    @Test
    public void testAutenticacaoValida() {
        // Arrange
        DaoUsuario daoUsuario = mock(DaoUsuario.class);
        Usuario usuarioMock = new Usuario("admin", "123456", TipoUsuario.Administrador, true);
        when(daoUsuario.buscarPorLogin("admin")).thenReturn(usuarioMock);

        AutenticacaoService service = new AutenticacaoService(daoUsuario);

        // Act
        boolean autenticado = service.autenticar("admin", "123456");

        // Assert
        assertTrue(autenticado);
    }

    @Test
    public void testAutenticacaoInvalida() {
        // Arrange
        DaoUsuario daoUsuario = mock(DaoUsuario.class);
        Usuario usuarioMock = new Usuario("usuario@email.com", "senhacorreta", TipoUsuario.Associado, true);
        when(daoUsuario.buscarPorLogin("usuario@email.com")).thenReturn(usuarioMock);

        AutenticacaoService service = new AutenticacaoService(daoUsuario);

        // Act
        boolean autenticado = service.autenticar("usuario@email.com", "senhaerrada");

        // Assert
        assertFalse(autenticado);
    }

    @Test
    public void testRegistrarPagamento() throws Exception {
        // Arrange
        PagamentoMensalidadeService pagamentoService = new PagamentoMensalidadeService(null);
        DaoFinanceiro daoFinanceiro = mock(DaoFinanceiro.class);
        FinanceiroService financeiroService = new FinanceiroService(daoFinanceiro);

        Associado associado = new Associado("marcos silva", "00011122233", "marcos.silva@email.com", "47999990000", "rua das palmeiras, 10", Categoria.Ativo);
        PagamentoMensalidade pagamento = pagamentoService.criarObjeto(associado, new BigDecimal("100.00"), BigDecimal.ZERO, StatusPagamento.Pendente);

        // Act
        financeiroService.registrarPagamento(pagamento);

        // Assert
        assertEquals(StatusPagamento.Pago, pagamento.getStatus());
    }

    @Test
    public void testGerarRecibo() throws Exception {
        // Arrange
        DaoFinanceiro daoFinanceiro = mock(DaoFinanceiro.class);
        FinanceiroService service = new FinanceiroService(daoFinanceiro);
        PagamentoMensalidade pagamento = new PagamentoMensalidade(null, new BigDecimal("50.00"), BigDecimal.ZERO, StatusPagamento.Pago);

        // Act
        service.gerarRecibo(pagamento);

        // Assert
        assertTrue(true);
    }

    @Test
    public void testNotificacaoImportanteNaoLida() throws Exception {
        // Arrange
        DaoNotificacao daoMock = mock(DaoNotificacao.class);
        NotificacaoService notificacaoService = new NotificacaoService(daoMock);

        DaoAssociado daoA = mock(DaoAssociado.class);
        DaoPagamentoMensalidade daoP = mock(DaoPagamentoMensalidade.class);

        AssociadoService associadoService = new AssociadoService(daoA, daoP, notificacaoService);
        Associado associado = associadoService.criarObjeto("paula mendes", "77777777777", "paula.mendes@email.com", "47999998877", "rua das rosas, 45", Categoria.Ativo);

        Notificacao notificacao = notificacaoService.criarNotificacao(associado, "mensagem urgente", false, true);
        notificacaoService.salvarNotificacao(notificacao);

        // Assert
        assertTrue(notificacao.isImportante() && !notificacao.isLida());
    }

    @Test
    public void testGeracaoCarteirinha() throws Exception {
        // Arrange
        DaoCarteirinha daoCarteirinha = mock(DaoCarteirinha.class);
        CarteirinhaService carteirinhaService = new CarteirinhaService(daoCarteirinha);

        Associado associado = new Associado("daniel hoepers", "12345678901", "danielrch.hoepers@gmail.com", "47999998888", "rufolfo mass, 11", Categoria.Ativo);
        String codigoSocio = "soc2025";
        String filiacao = "família hoepers";
        LocalDate validade = LocalDate.now().plusYears(1);
        String nomeAssociacao = "assocify";
        String cnpjAssociacao = "00.000.000/0001-00";

        Carteirinha carteirinha = carteirinhaService.criarObjeto(
            associado, codigoSocio, filiacao, validade, nomeAssociacao, cnpjAssociacao
        );

        // Act
        carteirinhaService.salvarObjeto(carteirinha);

        // Assert
        assertEquals("soc2025", carteirinha.getCodigoSocio());
        assertEquals("assocify", carteirinha.getNomeAssociacao());
        assertEquals(associado.getId(), carteirinha.getAssociado().getId());
    }

    @Test
    public void testInscricaoValidaEmTorneio() throws Exception {
        // Arrange
        DaoAssociado daoAssociado = mock(DaoAssociado.class);
        DaoPagamentoMensalidade daoPagamento = mock(DaoPagamentoMensalidade.class);
        NotificacaoService notificacaoService = mock(NotificacaoService.class);
        DaoTorneio daoTorneio = mock(DaoTorneio.class);
        DaoParticipacaoTorneiro daoParticipacao = mock(DaoParticipacaoTorneiro.class);

        AssociadoService associadoService = new AssociadoService(daoAssociado, daoPagamento, notificacaoService);
        TorneioService torneioService = new TorneioService(daoTorneio, daoParticipacao);

        Associado associado = associadoService.criarObjeto("daniel hoepers", "12312312300", "danielrch.hoepers@x.com", "47999998877", "rufolfo mass, 11", Categoria.Ativo);
        associado.setInadimplente(false);

        Torneio torneio = new Torneio("torneio regional", LocalDate.now().plusDays(10), "ginásio central", TipoTorneio.Canto, false);

        // Act
        ParticipacaoTorneiro participacao = torneioService.inscreverAssociado(torneio, associado);

        // Assert
        assertEquals(associado, participacao.getAssociado());
        assertEquals(torneio, participacao.getTorneio());
    }
}
