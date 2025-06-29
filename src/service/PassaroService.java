package service;

import classes.Associado;
import classes.Passaro;
import daos.DaoPassaro;

import java.time.LocalDate;

public class PassaroService {

    private DaoPassaro dao;

    public PassaroService(DaoPassaro dao) {
        this.dao = dao;
    }

    public Passaro criarObjeto(Associado associado, String nome, String especie, String raca, LocalDate dataNascimento) {
        return new Passaro(associado, nome, especie, raca, dataNascimento);
    }

    public boolean salvarObjeto(Passaro passaro) {
        return dao.Inserir(passaro);
    }

    public boolean editarObjeto(Passaro passaro) {
        return dao.Editar(passaro);
    }

    public boolean removerObjeto(Passaro passaro) {
        return dao.Remover(passaro);
    }
}
