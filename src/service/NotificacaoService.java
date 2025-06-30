package service;

import classes.Associado;
import classes.Notificacao;
import daos.DaoNotificacao;

import java.util.List;
import javax.mail.MessagingException;

public class NotificacaoService {

    private DaoNotificacao daoNotificacao;
    private EmailService emailService;

    public NotificacaoService(DaoNotificacao daoNotificacao, EmailService emailService) {
        this.daoNotificacao = daoNotificacao;
        this.emailService = emailService;
    }

    public Notificacao criarNotificacao(Associado associado, String mensagem, boolean lida, boolean importante) throws Exception {
        if (mensagem == null || mensagem.trim().length() < 5) {
            throw new Exception("Mensagem inválida");
        }
        return new Notificacao(associado, mensagem, lida, importante);
    }

    public boolean salvarNotificacao(Notificacao notificacao) {
        boolean sucesso = daoNotificacao.Inserir(notificacao);

        if (sucesso) {
            try {
                enviarEmailNotificacao(notificacao);
            } catch (MessagingException e) {
                System.out.println("Erro ao enviar e-mail: " + e.getMessage());
            }
        }

        return sucesso;
    }

    public void enviarBoasVindas(Associado associado) throws Exception {
        Notificacao notificacao = criarNotificacao(
            associado,
            "Bem-vindo(a), " + associado.getNomeCompleto() + "! Sua associação foi concluída com sucesso.",
            false,
            true
        );
        salvarNotificacao(notificacao);
    }

    private void enviarEmailNotificacao(Notificacao notificacao) throws MessagingException {
        String para = notificacao.getAssociado().getEmail();
        String assunto = notificacao.getMensagem(); 
        String texto = "Olá " + notificacao.getAssociado().getNomeCompleto() + ",\n\n"
                + "Você recebeu uma nova notificação:\n\n"
                + notificacao.getMensagem() + "\n\n"
                + "Atenciosamente,\nEquipe Assocify";

        emailService.enviarEmail(para, assunto, texto); 
    }

    public boolean editarObjeto(Notificacao notificacao) {
        return daoNotificacao.Editar(notificacao);
    }

    public boolean removerObjeto(Notificacao notificacao) {
        return daoNotificacao.Remover(notificacao);
    }

    public List<Notificacao> buscarPorAssociado(Associado associado) {
        return daoNotificacao.buscarPorAssociado(associado);
    }
}
