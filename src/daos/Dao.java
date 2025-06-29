package daos;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Dao {
    EntityManagerFactory emf =  Persistence.createEntityManagerFactory("associfybd");
    EntityManager em = emf.createEntityManager();
}
