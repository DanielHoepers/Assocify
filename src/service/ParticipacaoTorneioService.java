package service;

import classes.Associado;
import classes.Passaro;
import classes.ParticipacaoTorneiro;
import classes.Torneio;
import daos.DaoParticipacaoTorneiro;

import java.util.List;

public class ParticipacaoTorneioService {

    private DaoParticipacaoTorneiro dao;

    public ParticipacaoTorneioService(DaoParticipacaoTorneiro dao) {
        this.dao = dao;
    }

    public ParticipacaoTorneiro criarObjeto(Torneio torneio, Associado associado, List<Passaro> passaros, boolean certificadoEmitido, int colocacao) {
        return new ParticipacaoTorneiro(torneio, associado, passaros, certificadoEmitido, colocacao);
    }

    public boolean salvarObjeto(ParticipacaoTorneiro participacao) {
        return dao.Inserir(participacao);
    }

    public boolean editarObjeto(ParticipacaoTorneiro participacao) {
        return dao.Editar(participacao);
    }

    public boolean removerObjeto(ParticipacaoTorneiro participacao) {
        return dao.Remover(participacao);
    }
}
