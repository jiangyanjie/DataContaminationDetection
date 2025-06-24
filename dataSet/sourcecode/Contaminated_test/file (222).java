package dao;

import java.util.List;


import javax.persistence.EntityManager; 
import javax.persistence.PersistenceContext;
import javax.persistence.NamedQuery;
import javax.persistence.Query;


import java.util.Set;
import java.util.HashSet;

import org.springframework.stereotype.Component;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.*;

import persistence.CarteGrise;
import persistence.Proprietaire;
import persistence.Vehicule;
//import persistence.Appartenance;

@NamedQuery(
        name="findCartesGriseFromProp",
        query="SELECT ap.carteGrise FROM Appartenance ap INNER JOIN ap.carteGrise  WHERE ap.proprietaire.id = :idProp"
        )

@Component
public class DAOCarteGrise implements JpaRepository <String, CarteGrise>{
	
	@PersistenceContext
	private EntityManager em; 
	 
	public Vehicule lireVehicule(String numSerie) {
		return em.find(Vehicule.class, numSerie);
	} 
	
	public CarteGrise lireCarteGrise(String immatriculation) {
		return em.find(CarteGrise.class, immatriculation);
	}
	
	public Proprietaire lireProprietaire(Long idProprio) {
		return em.find(Proprietaire.class, idProprio);
	}
	
	public void enregistrerCarteGrise(CarteGrise cg) {
		em.persist(cg);
	}

	public Page<String> findAll(Pageable arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public long count() {
		// TODO Auto-generated method stub
		return 0;
	}

	public void delete(CarteGrise arg0) {
		// TODO Auto-generated method stub
		
	}

	public void delete(String arg0) {
		// TODO Auto-generated method stub
		
	}

	public void delete(Iterable<? extends String> arg0) {
		// TODO Auto-generated method stub
		
	}

	public void deleteAll() {
		// TODO Auto-generated method stub
		
	}

	public boolean exists(CarteGrise arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	public String findOne(CarteGrise arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public String save(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public void deleteInBatch(Iterable<String> arg0) {
		// TODO Auto-generated method stub
		
	}

	public List<String> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<String> findAll(Sort arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public void flush() {
		// TODO Auto-generated method stub
		
	}

	public List<String> save(Iterable<? extends String> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public String saveAndFlush(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Set<CarteGrise> findCartesGrises(Proprietaire p){
        Query q1 = em.createNamedQuery("findCartesGriseFromProp");
        q1.setParameter("idProp",p.getId());
        return new HashSet<CarteGrise>(q1.getResultList());
	}
}