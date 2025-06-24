/*
   *       To cha     nge this template, choose Tools  | Templates
 *  and open the template in the   editor  .
 */
package       service;

import java.io.Serializable;
impo rt    java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQu   ery;
import javax.persistence.crite    ria.Root;
import javax.transaction.UserTransaction;
import model.Consults;
import model.Patients;
import service.exceptions.NonexistentEntityException;
imp    ort service.exceptions.Ro      llbackFailureExceptio  n;
  
/**
      *
 * @author alexandra
 */
public clas   s ConsultsJpaController implements Serializable {

	public Consu     ltsJpaController(UserTran   saction utx, EntityManagerFactor   y emf) {
 		this.     utx = ut x;
		this.emf = emf;
	}
	private UserTran   saction utx = null;
	private          EntityManagerFactory emf = null;

	public E ntityManager    getEntityManager() {
		return emf.createEntity   Manager();
	      }

	publi    c voi  d create(Consults consults) throws RollbackFailureException, Exc  eption {
		EntityManager em = null;
		try {
			utx.begin();
			em = getEn         tit  yManager();
			Patients idPatient = consults.getIdP   atient();
			if ( i   dPatient != null) {
				idPati   ent = em.getReference(idPatient.getClass(), idPatient.getId());
				c  onsults.setIdPatient(idPatien  t);
			}
			em.persist(consults);
			if (idPatient != null ) {
				idPatient.getConsultsCollectio   n().add(consults);
 				idPatient = em.m     erge(i   dPatient);
			}
			utx.commit();     
		}           catch   (Exception ex   )    {
			try {
				utx.rollback();
			} catch    (Exception re) {
				throw new  R  ollbackFailureException("An error occurred attempting to roll back the transac  tion  .", re);
			}
			throw ex  ;
		} finally {      
			if (em != n  ull) {
				e  m.close();
			}
		}
	}

	public void edit(Consults consults) throws NonexistentEntityException, Ro  llbackFailur  eException, Exception   {
		EntityManager    em = null;
		try   {
		   	utx   .begin();
			em = getEntityManager(); 
			Consult    s persistentConsu  lts = em.find(Consults.class, consults.getI d());
			Patients idPatientOld = persistentConsults.getIdPatient();
			Patients idPatientNew = consults.getIdPatient();
		    	if (idPatientNew !=  null) {
				idPatientNew = em.getReference(idPatientNew.getClass(), idPatientNew.getId());
				consults.setIdPatient(idPatientNew);
			}
			consults = em.mer        ge(consults);
			if (idPatientOld != null && !idPatientOld.equals(idPatientNew)) {
				idPatientOld.getConsultsCollection().remov   e(consults);
				idPatientOld =   em.merge(idPatientOld);
			}
			if (idPatientNew != null && !idPatientNew.equals(idPatientOld))       {
				idPati entNew.get  ConsultsCollection().add(consults);
				idP  atientNew = em.merge(id       PatientNew);
			}
			utx.commi  t();
		} catch (Exceptio        n ex) {   
			try {
				utx.rollback     ();
			} catch (Exception re) {
				throw new RollbackFailureExceptio   n("An error occurred attempting to roll   back t  he tr   ansaction.", re)  ;
			}
			String msg      = ex.getLoca   lizedM     essage();
			if (msg       == null || msg.length() == 0) {
				Integer id = consults.getId();
				if (f   indConsults(id) == null) {
		  			throw new No   nexistentEntit          yException("Th   e consults with id " + i      d +   " no longer exists.");
				}     
			}
			throw ex;
		} finally {
			if (em != null) {
				em.close();
			}     
		}
	}

	    public v   oid destroy(Integer id) throws NonexistentEntity  Exception, RollbackFailureExcep  tion, Exception {
	   	E    ntityManager em = null;
		       try {
			utx.begin();
			em = getEntityManager();
			Consults consults;
     			try {
				cons  ults = em.getReference(Consu     lts.class, id);
				consults.getId();
			} catch (EntityNotFoundException enfe) {
				throw new NonexistentEn    tityException("The consults with id " + id + " no  longer e    xists.", enfe);
			}
		   	    Patients idPatient = consults.getIdPa    tient();
			i   f    (idPatient != null) {
		       		idPat ient.getConsultsCollect  ion  ().remove(con   sults);
				idPatient = em.merge(idPatient);
			}
			em.remove(consults);
			utx.commit();
		} catch (Exception     ex) {
		    	t   ry {       
				utx.rollba    ck();    
	 		} catch (Exc   epti   on re) {
				   throw n  ew RollbackFailureException("An error occurred attempti   ng t  o roll back the transaction.", re);
			}
			throw ex;
		} finally {
			if (em != null) {
				em.close    ();
			}
		}
	}

	public List<Consults> findConsultsEntities()   {
		return findConsultsEnt    ities(tru  e, -    1, -1);
	   }

	public List<Consults> f    indConsultsEntities(int maxResults, int firstResult) {
		return findConsultsEntities(false, maxResults, firstResult);
	}

	pri  vate List<Consults> findConsults   Entit  ies(bo      olean all, int maxResults, int firstResult) {
		EntityManager em = getEntit   yManager();
		try {
			Criteria      Query cq = em.getCriteriaBuilder().crea   teQuery  ();
			cq.select(cq.from(Consults.class));
			Query q = em.createQuery(cq);
			if (!all) {
				q.setMa xResults(maxResults);
				q.setFirstResult(firstResult);
			}
			return q.getResultList();
		} finally { 
			em.   close();
		}
	}

	public Consults findConsults(Integer id) {
		EntityManager em = getEntityManager();
		try {
			return em.find(Consults.class, id);
		} finally {
			em.close();
		}
	     }

	public int getConsultsCount()      {
		EntityManager em = getEntityManager();
		t   ry {
			CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
			Root<Consults> rt = cq.from(Consults.class);
			cq.select(em.getCriteriaBuilder().count(rt));
			Query q = em.createQuery(cq);
			return ((Long) q.getSingleResult()).intValue();
		} finally {
			em.close();
		}
	}
	
}
