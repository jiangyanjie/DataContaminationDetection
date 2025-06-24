/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.UserTransaction;
import model.Consults;
import model.Patients;
import service.exceptions.NonexistentEntityException;
import service.exceptions.RollbackFailureException;

/**
 *
 * @author alexandra
 */
public class ConsultsJpaController implements Serializable {

	public ConsultsJpaController(UserTransaction utx, EntityManagerFactory emf) {
		this.utx = utx;
		this.emf = emf;
	}
	private UserTransaction utx = null;
	private EntityManagerFactory emf = null;

	public EntityManager getEntityManager() {
		return emf.createEntityManager();
	}

	public void create(Consults consults) throws RollbackFailureException, Exception {
		EntityManager em = null;
		try {
			utx.begin();
			em = getEntityManager();
			Patients idPatient = consults.getIdPatient();
			if (idPatient != null) {
				idPatient = em.getReference(idPatient.getClass(), idPatient.getId());
				consults.setIdPatient(idPatient);
			}
			em.persist(consults);
			if (idPatient != null) {
				idPatient.getConsultsCollection().add(consults);
				idPatient = em.merge(idPatient);
			}
			utx.commit();
		} catch (Exception ex) {
			try {
				utx.rollback();
			} catch (Exception re) {
				throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
			}
			throw ex;
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	public void edit(Consults consults) throws NonexistentEntityException, RollbackFailureException, Exception {
		EntityManager em = null;
		try {
			utx.begin();
			em = getEntityManager();
			Consults persistentConsults = em.find(Consults.class, consults.getId());
			Patients idPatientOld = persistentConsults.getIdPatient();
			Patients idPatientNew = consults.getIdPatient();
			if (idPatientNew != null) {
				idPatientNew = em.getReference(idPatientNew.getClass(), idPatientNew.getId());
				consults.setIdPatient(idPatientNew);
			}
			consults = em.merge(consults);
			if (idPatientOld != null && !idPatientOld.equals(idPatientNew)) {
				idPatientOld.getConsultsCollection().remove(consults);
				idPatientOld = em.merge(idPatientOld);
			}
			if (idPatientNew != null && !idPatientNew.equals(idPatientOld)) {
				idPatientNew.getConsultsCollection().add(consults);
				idPatientNew = em.merge(idPatientNew);
			}
			utx.commit();
		} catch (Exception ex) {
			try {
				utx.rollback();
			} catch (Exception re) {
				throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
			}
			String msg = ex.getLocalizedMessage();
			if (msg == null || msg.length() == 0) {
				Integer id = consults.getId();
				if (findConsults(id) == null) {
					throw new NonexistentEntityException("The consults with id " + id + " no longer exists.");
				}
			}
			throw ex;
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	public void destroy(Integer id) throws NonexistentEntityException, RollbackFailureException, Exception {
		EntityManager em = null;
		try {
			utx.begin();
			em = getEntityManager();
			Consults consults;
			try {
				consults = em.getReference(Consults.class, id);
				consults.getId();
			} catch (EntityNotFoundException enfe) {
				throw new NonexistentEntityException("The consults with id " + id + " no longer exists.", enfe);
			}
			Patients idPatient = consults.getIdPatient();
			if (idPatient != null) {
				idPatient.getConsultsCollection().remove(consults);
				idPatient = em.merge(idPatient);
			}
			em.remove(consults);
			utx.commit();
		} catch (Exception ex) {
			try {
				utx.rollback();
			} catch (Exception re) {
				throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
			}
			throw ex;
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	public List<Consults> findConsultsEntities() {
		return findConsultsEntities(true, -1, -1);
	}

	public List<Consults> findConsultsEntities(int maxResults, int firstResult) {
		return findConsultsEntities(false, maxResults, firstResult);
	}

	private List<Consults> findConsultsEntities(boolean all, int maxResults, int firstResult) {
		EntityManager em = getEntityManager();
		try {
			CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
			cq.select(cq.from(Consults.class));
			Query q = em.createQuery(cq);
			if (!all) {
				q.setMaxResults(maxResults);
				q.setFirstResult(firstResult);
			}
			return q.getResultList();
		} finally {
			em.close();
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

	public int getConsultsCount() {
		EntityManager em = getEntityManager();
		try {
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
