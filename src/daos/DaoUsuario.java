package daos;

import classes.Associado;
import classes.Usuario;
import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

public class DaoUsuario extends Dao {

    public boolean Inserir(Usuario usuario) {
        try {
            em.getTransaction().begin();
            em.persist(usuario);
            em.getTransaction().commit();
            return true;
        } catch (PersistenceException e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            System.out.println("Erro ao inserir usuário: " + e.getMessage());
            return false;
        }
    }

    public boolean Editar(Usuario usuario) {
        try {
            em.getTransaction().begin();
            em.merge(usuario);
            em.getTransaction().commit();
            return true;
        } catch (PersistenceException e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            System.out.println("Erro ao editar usuário: " + e.getMessage());
            return false;
        }
    }

    public boolean Remover(Usuario usuario) {
        try {
            em.getTransaction().begin();
            em.remove(em.contains(usuario) ? usuario : em.merge(usuario));
            em.getTransaction().commit();
            return true;
        } catch (PersistenceException e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            System.out.println("Erro ao remover usuário: " + e.getMessage());
            return false;
        }
    }

    public Usuario selecionar(int id) {
        try {
            Query consulta = em.createQuery("SELECT u FROM Usuario u WHERE u.id = :id");
            consulta.setParameter("id", id);
            return (Usuario) consulta.getSingleResult();
        } catch (Exception e) {
            System.out.println("Usuário não encontrado pelo ID: " + id);
            return null;
        }
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
