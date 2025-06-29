package service;

import classes.Feedback;
import classes.Associado;
import classes.Evento;
import classes.Torneio;
import daos.DaoFeedback;
import enuns.StatusFeedback;

public class FeedbackService {

    private DaoFeedback daoFeedback;

    public FeedbackService(DaoFeedback daoFeedback) {
        this.daoFeedback = daoFeedback;
    }

    public Feedback criarObjeto(Associado associado, Evento evento, Torneio torneio, String comentario) throws Exception {
        if (comentario == null || comentario.trim().length() < 5) {
            throw new Exception("Comentário muito curto");
        }
        Feedback feedback = new Feedback(associado, evento, torneio, comentario);
        feedback.setStatus(StatusFeedback.Enviado);
        return feedback;
    }

    public boolean salvarObjeto(Feedback feedback) {
        feedback.setStatus(StatusFeedback.Enviado);
        return daoFeedback.Inserir(feedback);
    }

    public boolean editarObjeto(Feedback feedback) {
        return daoFeedback.Editar(feedback);
    }

    public boolean removerObjeto(Feedback feedback) {
        return daoFeedback.Remover(feedback);
    }
}
