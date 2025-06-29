/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package daos;

import classes.Evento;
import classes.Feedback;
import java.util.List;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

/**
 *
 * @author danie
 */
public class DaoFeedback extends Dao{
     public boolean Inserir(Feedback x) {
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

    public boolean Editar(Feedback x) {
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

    public boolean Remover(Feedback x) {
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
    
    public Feedback selecionar(int codigo){
        Query consulta = em.createQuery("select x from Feedback x where x.id = :y");
        consulta.setParameter("y", codigo);
        return (Feedback) consulta.getSingleResult();
}
    
      public List<Feedback> listar() {
        return em.createQuery("FROM Feedback", Feedback.class).getResultList();
}
}
