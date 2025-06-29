/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package daos;

import classes.Passaro;
import java.util.List;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

/**
 *
 * @author danie
 */
public class DaoPassaro extends Dao{
            public boolean Inserir(Passaro x) {
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

    public boolean Editar(Passaro x) {
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

    public boolean Remover(Passaro x) {
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
    
    public Passaro selecionar(int codigo){
        Query consulta = em.createQuery("select x from Passaro x where x.id = :y");
        consulta.setParameter("y", codigo);
        return (Passaro) consulta.getSingleResult();
}
    
    public List<Passaro> listar() {
        return em.createQuery("FROM Passaro", Passaro.class).getResultList();
}
}
