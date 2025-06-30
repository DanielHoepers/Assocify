package service;

import classes.Evento;
import daos.DaoAssociado;
import daos.DaoEvento;

public class EventoService {

    private DaoEvento dao;

    public EventoService(DaoEvento dao) {
        this.dao = dao;
    }

    public Evento criarObjeto(String nome, String descricao, String local, int limiteParticipantes) {
        return new Evento(nome, descricao, local, limiteParticipantes);
    }

    public boolean salvarObjeto(Evento evento) {
        return dao.Inserir(evento);
    
    }

    public boolean editarObjeto(Evento evento) {
        return dao.Editar(evento);
    }

    public boolean removerObjeto(Evento evento) {
        return dao.Remover(evento);
    }
}
