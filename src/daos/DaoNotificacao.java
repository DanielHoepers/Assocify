/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package daos;

import classes.Associado;
import classes.Feedback;
import classes.Notificacao;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

/**
 *
 * @author danie
 */
public class DaoNotificacao extends Dao{
    public boolean Inserir(Notificacao x) {
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

    public boolean Editar(Notificacao x) {
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

    public boolean Remover(Notificacao x) {
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
    
    public Notificacao selecionar(int codigo){
        Query consulta = em.createQuery("select x from Notificacao x where x.id = :y");
        consulta.setParameter("y", codigo);
        return (Notificacao) consulta.getSingleResult();
}
    
    public List<Notificacao> listar() {
        return em.createQuery("FROM Notificacao", Notificacao.class).getResultList();
}
    
    public List<Notificacao> buscarPorAssociado(Associado associado) {
    try {
        return em.createQuery("SELECT n FROM Notificacao n WHERE n.associado.id = :id", Notificacao.class)
                 .setParameter("id", associado.getId())
                 .getResultList();
    } catch (Exception e) {
        return new ArrayList<>();
    }
}

}
