package service;

import classes.Associado;
import classes.ParticipacaoTorneiro;
import classes.Passaro;
import classes.Torneio;
import daos.DaoTorneio;
import daos.DaoParticipacaoTorneiro;
import enuns.TipoTorneio;

import java.time.LocalDate;
import java.util.ArrayList;

public class TorneioService {

    private DaoTorneio daoTorneio;
    private DaoParticipacaoTorneiro daoParticipacao;

    public TorneioService(DaoTorneio daoTorneio, DaoParticipacaoTorneiro daoParticipacao) {
        this.daoTorneio = daoTorneio;
        this.daoParticipacao = daoParticipacao;
    }

    public Torneio criarObjeto(String nome, LocalDate data, String local, TipoTorneio tipo, boolean finalizado) throws Exception {
        if (nome == null || nome.isEmpty()) throw new Exception("Nome obrigatório");
        if (data == null || data.isBefore(LocalDate.now())) throw new Exception("Data inválida");
        if (local == null || local.isEmpty()) throw new Exception("Local obrigatório");
        if (tipo == null) throw new Exception("Tipo obrigatório");
        return new Torneio(nome, data, local, tipo, finalizado);
    }

    public boolean salvarObjeto(Torneio torneio) {
        return daoTorneio.Inserir(torneio);
    }

    public boolean editarObjeto(Torneio torneio) {
        return daoTorneio.Editar(torneio);
    }

    public boolean removerObjeto(Torneio torneio) {
        return daoTorneio.Remover(torneio);
    }

    public void validarInscricao(Associado associado) {
        if (associado.isInadimplente()) {
            throw new RuntimeException("Associado inadimplente não pode se inscrever.");
        }
        if (associado.isContaDesativada()) {
            throw new RuntimeException("Conta do associado está desativada.");
        }
    }

    public boolean verificarLimiteInscritos(Torneio torneio, int limiteMaximo) {
        int totalInscritos = daoParticipacao.buscarPorTorneio(torneio).size();
        return totalInscritos < limiteMaximo;
    }
    
    public ParticipacaoTorneiro inscreverAssociado(Torneio torneio, Associado associado) throws Exception {
    validarInscricao(associado);
    ParticipacaoTorneiro participacao = new ParticipacaoTorneiro(torneio, associado, new ArrayList<Passaro>(), false, 0);
    daoParticipacao.Inserir(participacao);
    return participacao;
}

}
