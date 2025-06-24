package dao;

import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import model.Critere;

/**
 * Session Bean implementation class EntrepriseDAO
 * @author Hugo COLLET
 */

@Stateless
@LocalBean

public class CritereDAO

{
	//-----------------------------------------------------------------------------
	/**
	* R�f�rence vers le gestionnaire de persistance.
	*/
	@PersistenceContext
	EntityManager entityManager;
	//-----------------------------------------------------------------------------
	/**
	 * Default constructor.
	 */
	public CritereDAO()
	{
		// TODO Auto-generated constructor stub
	}
	//-----------------------------------------------------------------------------

	/**
	 * Rend persistante l'instance (bean entity) de l'entreprise.
	 * @param Critere bean entity repr�sentant l'instance.
	 * @return l'instance du critere une fois persist�e dans la base de donn�es.
	 */

	public Critere persist(Critere critere)

	{

		entityManager.persist(critere);

		return critere;

	}

	//----------------------------------------------------------------------------
	
	
	public Critere findById(Integer id)

	{

		return entityManager.find(Critere.class, id);

	}

	//----------------------------------------------------------------------------

  @SuppressWarnings({ "rawtypes", "unchecked" })

  public List<Critere> findAll()

	{

		Query query = entityManager.createQuery("select critere from Critere critere order by critere.id");

		List l = query.getResultList(); 

		

		return (List<Critere>)l;

	}

	//-----------------------------------------------------------------------------

	public Critere update(Critere critere)

	{

		entityManager.merge(critere);

		return findById(critere.getId());

	}

	//-----------------------------------------------------------------------------

	public void remove(Critere critere)

	{

		if(!entityManager.contains(critere))         // Si l'entit� n'est pas dans un �tat "g�r�" (managed),

		{                                               // il est impossible de la supprimer directement, erreur "Entity must be managed to call remove"

			critere = entityManager.merge(critere);	// Il faut la "rattacher" au contexte de persistance par l'appel		

		}                                               // de la m�thode merge de l'EntityManager.

		

		// L'entit� �tait d�j� attach�e ou a �t� rattach�e, on peut donc la supprimer...

		entityManager.remove(critere);

	}

	//-----------------------------------------------------------------------------

}