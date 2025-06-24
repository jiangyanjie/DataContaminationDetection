package front.controlesDAO;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.*;
import front.utils.*;
import model.*;

/**
 * Servlet implementation class ControleDAO
 */
@WebServlet("/ControleDAO")
public class ControleDAO extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ControleDAO() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{

		// Flot de sortie pour écriture des résultats.
	    PrintWriter out = response.getWriter();

//------------------------Eleve--------OK-----------------------------------------------------------------
	EleveDAO eleveDAO = null;
	try
	{
	    eleveDAO = (EleveDAO) ServicesLocator.getInstance().getRemoteInterface("EleveDAO");
	}
	catch (ServicesLocatorException e)
	{
		e.printStackTrace();
	}
	
	out.println("--------------------------------------------------------------------------------------------------------------------------------------------------------");
	out.println("Contrôles de fonctionnement du DAO EleveDAO");
	out.println();
	
	Eleve eleve = eleveDAO.findById(1);
	out.println("Eleve n°1 : " + eleve.getId());
	out.println(eleve.getNom());
	out.println(eleve.getPrenom());
	out.println(eleve.getAdresseMail());
	
	out.println();
	eleve = eleveDAO.findById(2);
	out.println("Eleve n°2 : " + eleve.getId());
	out.println(eleve.getNom());
	out.println(eleve.getPrenom());
	out.println(eleve.getAdresseMail());

	    
	
//------------------------Critere----OK------------------------------------------------------------------
	CritereDAO critereDAO = null;
    try
    {
	    critereDAO = (CritereDAO) ServicesLocator.getInstance().getRemoteInterface("CritereDAO");
    }
    catch (ServicesLocatorException e)
    {
    	e.printStackTrace();
    }
    
    out.println("--------------------------------------------------------------------------------------------------------------------------------------------------------");
	out.println("Contrôles de fonctionnement du DAO CritereDAO");
	out.println();
	
	Critere critere = critereDAO.findById(1);
	
	out.println("Critère n°1 : " + critere.getId());
	out.println("Description générale : " + critere.getDescription());
	out.println("Niveau 1 : "+critere.getDescNiveauPerformance1());
	out.println("Niveau 2 : "+critere.getDescNiveauPerformance2());
	out.println("Niveau 3 : "+critere.getDescNiveauPerformance3()+"(C'est une case vide dans la base de données)");
	out.println("Niveau 4 : "+critere.getDescNiveauPerformance4());
    out.println(critere.getGrille());
    out.println();
    
    critere = critereDAO.findById(2);
    out.println("Critère n°2 : " + critere.getId());
	out.println("Description générale : " + critere.getDescription());
	out.println("Niveau 1 : "+ critere.getDescNiveauPerformance1());
	out.println("Niveau 2 : "+ critere.getDescNiveauPerformance2());
	out.println("Niveau 3 : "+ critere.getDescNiveauPerformance3());
	out.println("Niveau 4 : "+ critere.getDescNiveauPerformance4());
	out.println(critere.getGrille());
	    
//------------------------Enseignant----OK------------------------------------------------------------------
		
	EnseignantDAO enseignantDAO = null;
    try
    {
	    enseignantDAO = (EnseignantDAO) ServicesLocator.getInstance().getRemoteInterface("EnseignantDAO");
    }
    catch (ServicesLocatorException e)
    {
    	e.printStackTrace();
    }
    
    out.println("--------------------------------------------------------------------------------------------------------------------------------------------------------");
	out.println("Contrôles de fonctionnement du DAO EnseignantDAO");
	out.println();
    
	Enseignant enseignant = enseignantDAO.findById(1);
	out.println("Enseignant n°"+enseignant.getId());
	out.println("Nom : "+enseignant.getNom());
	out.println("Prenom : "+enseignant.getPrenom());
	out.println("Mail : "+enseignant.getAdresseMail());
	out.println("enseignant.getAsso3es().size() = " + enseignant.getAsso3es().size());
	out.println("enseignant.getAsso3es() : Enseignement, Enseignant, Eleve");
	
	for(Asso3e a : enseignant.getAsso3es())
	{
		out.println("---> Nom enseignement : " + a.getEnseignement().getNom() + " -- Nom enseignant : " + a.getEnseignant().getNom() + " -- Nom eleve : " + a.getEleve().getNom());
	}
	
	out.println("Nombre de grilles associées à l'enseignant : "+ enseignant.getGrilles().size());
	for(Grille g : enseignant.getGrilles())
	{
		out.println("--->  Nom de la grille associée : " + g.getNom());
	}
	out.println();
	
	
	enseignant = enseignantDAO.findById(2);
	out.println("Enseignant n°"+enseignant.getId());
	out.println("Nom : "+enseignant.getNom());
	out.println("Prenom : "+enseignant.getPrenom());
	out.println("Mail : "+enseignant.getAdresseMail());
	out.println("enseignant.getAsso3es().size() = " + enseignant.getAsso3es().size());
	out.println("enseignant.getAsso3es() : Enseignement, Enseignant, Eleve");
	
	for(Asso3e g : enseignant.getAsso3es())
	{
		out.println("---> Nom enseignement : " + g.getEnseignement().getNom() + " -- Nom enseignant : " + g.getEnseignant().getNom() + " -- Nom eleve : " + g.getEleve().getNom());
	}
	
	out.println("Nombre de grilles associées à l'enseignant : "+ enseignant.getGrilles().size());
	for(Grille g : enseignant.getGrilles())
	{
		out.println("--->  Nom de la grille associée : " + g.getNom());
	}
	out.println();
    
//------------------------Enseignement--------------OK--------------------------------------------------------   
	    
	EnseignementDAO enseignementDAO = null;
	try
	{
	    enseignementDAO = (EnseignementDAO) ServicesLocator.getInstance().getRemoteInterface("EnseignementDAO");
	}
	catch (ServicesLocatorException e)
	{
		e.printStackTrace();
	}
	
	out.println("--------------------------------------------------------------------------------------------------------------------------------------------------------");
	out.println("Contrôles de fonctionnement du DAO EnseignementDAO");
	out.println();
	
	Enseignement enseignement = enseignementDAO.findById(1);
	out.println(enseignement.getId());
	out.println(enseignement.getNom());
	
	out.println("enseignement.getAsso3es() : Enseignement, Enseignant, Eleve");
	for(Asso3e a : enseignement.getAsso3es())
	{
		out.println("---> Nom enseignement : " + a.getEnseignement().getNom() + " -- Nom enseignant : " + a.getEnseignant().getNom() + " -- Nom eleve : " + a.getEleve().getNom());
	}
	
	out.println("enseignement.getAssoOges() : Objectif Pédagogique, Grille, Enseignement");
	for(AssoOge a : enseignement.getAssoOges())
	{
		out.println("---> Id Objectif : " + a.getObjectifPedagogique().getId() + " -- Nom grille : " + a.getGrille().getNom() + " -- Nom enseignement : " + a.getEnseignement().getNom());
	}
	out.println();
	
	enseignement = enseignementDAO.findById(2);
	out.println(enseignement.getId());
	out.println(enseignement.getNom());
	
	out.println("enseignement.getAssoOges() : Objectif Pédagogique, Grille, Enseignement");
	for(AssoOge a : enseignement.getAssoOges())
	{
		out.println("---> Id Objectif : " + a.getObjectifPedagogique().getId() + " -- Nom grille : " + a.getGrille().getNom() + " -- Nom enseignement : " + a.getEnseignement().getNom());
	}
	
	//------------------------Objectif Pédagogique------------OK----------------------------------------------------------   		
	    
	ObjectifPedagogiqueDAO objectifpedagogiqueDAO = null;
	try
	{
		objectifpedagogiqueDAO = (ObjectifPedagogiqueDAO) ServicesLocator.getInstance().getRemoteInterface("ObjectifPedagogiqueDAO");
	}
	catch (ServicesLocatorException e)
	{
		e.printStackTrace();
	}
	

	out.println("--------------------------------------------------------------------------------------------------------------------------------------------------------");
	out.println("Contrôles de fonctionnement du DAO ObjectifPedagogiqueDAO");
	out.println();
	
	ObjectifPedagogique op = objectifpedagogiqueDAO.findById(1);
	out.println("Objectif Pédagogique n°"+op.getId());
	out.println(op.getDescription());
	out.println("op.getAssoOges() : Objectif Pédagogique, Grille, Enseignement");
	for(AssoOge a : op.getAssoOges())
	{
		out.println("---> Id Objectif : " + a.getObjectifPedagogique().getId() + " -- Nom grille : " + a.getGrille().getNom() + " -- Nom enseignement : " + a.getEnseignement().getNom());
	}
	out.println();
	
	
	op = objectifpedagogiqueDAO.findById(2);
	out.println("Objectif Pédagogique n°"+op.getId());
	out.println(op.getDescription());
	out.println();
	
	op = objectifpedagogiqueDAO.findById(3);
	out.println("Objectif Pédagogique n°"+op.getId());
	out.println(op.getDescription());
	out.println();
	
	//------------------------Grille------------OK----------------------------------------------------------   		
    
	GrilleDAO grilleDAO = null;
	try
	{
		grilleDAO = (GrilleDAO) ServicesLocator.getInstance().getRemoteInterface("GrilleDAO");
	}
	catch (ServicesLocatorException e)
	{
		e.printStackTrace();
	}
	
	out.println("--------------------------------------------------------------------------------------------------------------------------------------------------------");
	out.println("Contrôles de fonctionnement du DAO GrilleDAO");
	out.println();
	
	Grille grille = grilleDAO.findById(1);
	out.println("Grille n°"+grille.getId());
	out.println(grille.getNom());
	out.println("Niveau 1 : " + grille.getNiveauPerformance1());
	out.println("Niveau 2 : " + grille.getNiveauPerformance2());
	out.println("Niveau 3 : " + grille.getNiveauPerformance3());
	out.println("Niveau 4 : " + grille.getNiveauPerformance4());
	
	out.println("grille.getCriteres()");
	for(Critere c : grille.getCriteres())
	{
		out.println("---> Id : " + c.getId() + " -- Description : " +  c.getDescription());
	}
	
	out.println("grille.getEnseignants()");
	for(Enseignant ens : grille.getEnseignants())
	{
		out.println("---> Nom : " + ens.getNom() + " -- Prenom : " +  ens.getPrenom());
	}
	out.println();

	grille = grilleDAO.findById(2);
	out.println("Grille n°"+grille.getId());
	out.println(grille.getNom());
	out.println("Niveau 1 : " + grille.getNiveauPerformance1());
	out.println("Niveau 2 : " + grille.getNiveauPerformance2());
	out.println("Niveau 3 : " + grille.getNiveauPerformance3());
	out.println("Niveau 4 : " + grille.getNiveauPerformance4());
	
	}

}
