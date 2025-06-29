/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package daos;

import classes.Associado;
import classes.Notificacao;
import classes.PagamentoMensalidade;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

/**
 *
 * @author danie
 */
public class DaoPagamentoMensalidade extends Dao{
        public boolean Inserir(PagamentoMensalidade x) {
        try {
            em.getTransaction().begin();
            em.persist(x);
            em.getTransaction().commit();
            return true;
        } catch (PersistenceException y) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            return true;
        }

    }

    public boolean Editar(PagamentoMensalidade x) {
        try {
            em.getTransaction().begin();
            em.merge(x);
            em.getTransaction().commit();
            return true;
        } catch (PersistenceException y) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            return true;
        }

    }

    public boolean Remover(PagamentoMensalidade x) {
        try {
            em.getTransaction().begin();
            em.remove(x);
            em.getTransaction().commit();
            return true;
        } catch (PersistenceException y) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            return true;
        }

    }
    
    public PagamentoMensalidade selecionar(int codigo){
        Query consulta = em.createQuery("select x from PagamentoMensalidade x where x.id = :y");
        consulta.setParameter("y", codigo);
        return (PagamentoMensalidade) consulta.getSingleResult();
}
    
    
    public List<PagamentoMensalidade> buscarPorAssociado(Associado associado) {
    try {
        return em.createQuery("SELECT p FROM PagamentoMensalidade p WHERE p.associado_Id = :id", PagamentoMensalidade.class)
                 .setParameter("id", associado.getId())
                 .getResultList();
    } catch (Exception e) {
        return new ArrayList<>();
    }
}
    
    public List<PagamentoMensalidade> listar() {
        return em.createQuery("FROM PagamentoMensalidade", PagamentoMensalidade.class).getResultList();
}

}
