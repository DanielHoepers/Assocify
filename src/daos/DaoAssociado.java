/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package daos;

import classes.Associado;
import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

/**
 *
 * @author danie
 */
public class DaoAssociado extends Dao{
    
    public boolean Inserir(Associado x) {
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

    public boolean Editar(Associado x) {
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

    public boolean Remover(Associado x) {
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
    
    public Associado selecionar(int codigo){
        Query consulta = em.createQuery("select x from Associado x where x.id = :y");
        consulta.setParameter("y", codigo);
        return (Associado) consulta.getSingleResult();
}
    
    public List<Associado> listar() {
    return em.createQuery("FROM Associado", Associado.class).getResultList();
}
    
     public Associado buscarPorCpf(String cpf) {
    try {
        return em.createQuery("SELECT a FROM Associado a WHERE a.cpf = :cpf", Associado.class)
                 .setParameter("cpf", cpf)
                 .getSingleResult();
    } catch (NoResultException e) {
        return null;
    }
}
     public Associado buscarPorEmail(String email) {
    try {
        return em.createQuery("SELECT a FROM Associado a WHERE a.email = :email", Associado.class)
                 .setParameter("email", email)
                 .getSingleResult();
    } catch (NoResultException e) {
        return null;
    }
}


}
