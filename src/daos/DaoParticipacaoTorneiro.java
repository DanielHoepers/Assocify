/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package daos;

import classes.ParticipacaoTorneiro;
import classes.Torneio;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

/**
 *
 * @author danie
 */
public class DaoParticipacaoTorneiro extends Dao{
            public boolean Inserir(ParticipacaoTorneiro x) {
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

    public boolean Editar(ParticipacaoTorneiro x) {
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

    public boolean Remover(ParticipacaoTorneiro x) {
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
    
    public ParticipacaoTorneiro selecionar(int codigo){
        Query consulta = em.createQuery("select x from ParticipacaoTorneiro x where x.id = :y");
        consulta.setParameter("y", codigo);
        return (ParticipacaoTorneiro) consulta.getSingleResult();
}
    
    public List<ParticipacaoTorneiro> buscarPorTorneio(Torneio torneio) {
    try {
        return em.createQuery("SELECT p FROM ParticipacaoTorneio p WHERE p.torneioId = :id", ParticipacaoTorneiro.class)
                 .setParameter("id", torneio.getId())
                 .getResultList();
    } catch (Exception e) {
        return new ArrayList<>();
    }
}

    
    public List<ParticipacaoTorneiro> listar() {
        return em.createQuery("FROM ParticipacaoTorneiro", ParticipacaoTorneiro.class).getResultList();
}
}
