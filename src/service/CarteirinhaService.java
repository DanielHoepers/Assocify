package service;

import classes.Associado;
import classes.Carteirinha;
import daos.DaoCarteirinha;

import java.time.LocalDate;

public class CarteirinhaService {

    private DaoCarteirinha dao;

    public CarteirinhaService(DaoCarteirinha dao) {
        this.dao = dao;
    }

    public Carteirinha criarObjeto(Associado associado, String codigoSocio, String filiacao, LocalDate validade, String nomeAssociacao, String cnpjAssociacao) {
        return new Carteirinha(associado, codigoSocio, filiacao, validade, nomeAssociacao, cnpjAssociacao);
    }

    public boolean salvarObjeto(Carteirinha carteirinha) {
        return dao.Inserir(carteirinha);
    }

    public boolean editarObjeto(Carteirinha carteirinha) {
        return dao.Editar(carteirinha);
    }

    public boolean removerObjeto(Carteirinha carteirinha) {
        return dao.Remover(carteirinha);
    }
}
