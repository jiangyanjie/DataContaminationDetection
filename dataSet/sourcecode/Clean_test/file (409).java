/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.mum.model;

import edu.mum.model.exceptions.NonexistentEntityException;
import edu.mum.model.exceptions.RollbackFailureException;
import java.io.Serializable;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.Query;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;

/**
 *
 * @author Admin
 */
@Stateless
@LocalBean
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class ApplicantDaoBean implements Serializable {

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("ComproAdmissionPU");
    private EntityManager em = emf.createEntityManager();

    public void create(Applicant applicant) throws RollbackFailureException, Exception 
    {
       
        em.getTransaction().begin();
        em.persist(applicant);
        em.getTransaction().commit();
    
            
        
    }

    public Applicant findApplicantByEmailAddr(String emailAddr) {

           // em.getTransaction().begin();
       
        try {
            System.out.println("query applicant with emailAddr "+emailAddr);
            Query emailQuery = em.createNamedQuery("Applicant.findByEmailAddr");
            emailQuery.setParameter("emailAddr", emailAddr);
            Applicant foundApplicant = (Applicant) emailQuery.getSingleResult();
            //em.getTransaction().commit();
            return foundApplicant;
        } catch (NoResultException e) {
            System.out.println("query applicant with emailAddr no result exception return null");
            return null;
        }
    }

public void edit(Applicant applicant) throws NonexistentEntityException, RollbackFailureException, Exception {
    
    Long id = applicant.getId();
        em.getTransaction().begin();
        
        if (findApplicant(id) == null) {
             throw new NonexistentEntityException("The applicant with id " + id + " no longer exists.");
         }
        applicant = em.merge(applicant);
        em.getTransaction().commit();
    }
public Applicant findApplicant(Long id) {

        em.getTransaction().begin();
        Applicant returnAppl = em.find(Applicant.class, id);
        em.getTransaction().commit();


        return returnAppl;

    }

    public void destroy(Long id) throws NonexistentEntityException, RollbackFailureException, Exception {
       
            Applicant applicant;
            em.getTransaction().begin();

        try {
            applicant = em.getReference(Applicant.class, id);
            applicant.getId();
        } catch (EntityNotFoundException enfe) {
            throw new NonexistentEntityException("The applicant with id " + id + " no longer exists.", enfe);
        }
        em.remove(applicant);
        em.getTransaction().commit();

    }
}
/*
    public List<Applicant> findApplicantEntities() {
        return findApplicantEntities(true, -1, -1);
    }

    public List<Applicant> findApplicantEntities(int maxResults, int firstResult) {
        return findApplicantEntities(false, maxResults, firstResult);
    }

    private List<Applicant> findApplicantEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq



.select(cq.from(Applicant.class  

    ));
            Query q = em.createQuery(cq);
    
    if (!all

    
        

    
        ) {
                q.setMaxResults(maxResults);
        q.setFirstResult(firstResult);
    }
    

    return q.getResultList ();
}
finally {
            em.close();
        }
    }
*/

/*
    public int getApplicantCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root



<Applicant> rt = cq.from(Applicant.class  

    );
            cq.select (em.getCriteriaBuilder
    
    ().count(rt));
            Query q = em.createQuery(cq);
    
    return ((Long

    

    ) q.getSingleResult ()

).intValue();
        } finally {
            em.close();
        }
    }
   
}
 */
