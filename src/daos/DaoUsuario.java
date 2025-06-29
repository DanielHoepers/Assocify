/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package daos;

import classes.Usuario;
import java.util.List;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

/**
 *
 * @author danie
 */
public class DaoUsuario extends Dao{
           public boolean Inserir(Usuario x) {
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

    public boolean Editar(Usuario x) {
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

    public boolean Remover(Usuario x) {
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
    
    public Usuario selecionar(int codigo){
        Query consulta = em.createQuery("select x from Usuario x where x.id = :y");
        consulta.setParameter("y", codigo);
        return (Usuario) consulta.getSingleResult();
}
    
    public Usuario buscarPorLogin(String login) {
    try {
        return em.createQuery("SELECT u FROM Usuario u WHERE u.login = :login", Usuario.class)
                 .setParameter("login", login)
                 .getSingleResult();
    } catch (Exception e) {
        return null;
    }
}
public List<Usuario> listar() {
        return em.createQuery("FROM Usuario", Usuario.class).getResultList();
}



}
