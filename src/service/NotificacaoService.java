package service;

import classes.Associado;
import classes.Notificacao;
import daos.DaoNotificacao;

import java.util.List;

public class NotificacaoService {

    private DaoNotificacao daoNotificacao;

    public NotificacaoService(DaoNotificacao daoNotificacao) {
        this.daoNotificacao = daoNotificacao;
    }

    public Notificacao criarNotificacao(Associado associado, String mensagem, boolean lida, boolean importante) throws Exception {
        if (mensagem == null || mensagem.trim().length() < 5) {
            throw new Exception("Mensagem inválida");
        }
        return new Notificacao(associado, mensagem, lida, importante);
    }

    public boolean salvarNotificacao(Notificacao notificacao) {
        return daoNotificacao.Inserir(notificacao);
    }

    public boolean editarObjeto(Notificacao notificacao) {
        return daoNotificacao.Editar(notificacao);
    }

    public boolean removerObjeto(Notificacao notificacao) {
        return daoNotificacao.Remover(notificacao);
    }

    public void enviarBoasVindas(Associado associado) {
        try {
            Notificacao notificacao = criarNotificacao(
                associado,
                "Bem-vindo(a), " + associado.getNomeCompleto() + "! Sua associação foi concluída com sucesso.",
                false,
                true
            );
            salvarNotificacao(notificacao);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Notificacao> buscarPorAssociado(Associado associado) {
        return daoNotificacao.buscarPorAssociado(associado);
    }
}
