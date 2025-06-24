package    front.controlesDAO;

import   java.io.IOException;
import java.io.PrintWriter;

i    mport javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
impo  rt javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

impo rt dao.*;
import front.utils.*   ;
import model.*;
  
/**
 * Servl      et implementation class Cont   roleDAO
 */
@WebServlet("/Controle  DAO")
p     ublic class ControleDAO ex   tends HttpServlet {
	private     sta     tic final  long ser    ialVersi  onUID = 1L;   
             
    /* *              
        * @see H        t  tpServlet#HttpServlet()
     */
    publ   ic      ControleDAO() {   
             super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see  HttpServlet#doG  et(HttpServletRequest request, HttpServletResponse response)
	 */
	protec    ted void service(HttpServletReq      uest     re  quest, HttpServletResponse r     esponse) throws ServletExcepti  on, IOException 
	{

		// Flot        de so rtie     pour écriture des ré     sultats.
	    PrintWriter out = response.getWriter();

//-----------------       -------Eleve--------OK-------------------------------   ----------------------------------
	Ele   veDAO eleveDAO = null;
	try
	{
	    eleveDAO = (EleveDAO) ServicesLocator.getInstance().getRemoteInterface("EleveDAO");
	}
	catch (ServicesLocat     orException e)
	{
		e.printStackTrace();
	}
	
	out.println("------------------------------------------------------------------------------------------------------------------------------ --------------------------");
	out  .println("Contrôles de fonctionnement du DAO EleveDAO");
	out.println();
	
	Eleve eleve    = eleveDAO.findById(1);
	out.  println("Eleve n°1 : " + eleve.getId());
	out.println(eleve.getNom()        );   
	out.println(eleve.getPrenom());
	out.println(eleve.getAdresseMail());
	
	out.println();
	eleve = eleveDAO.findById(2);
	out.println("Eleve  n°2 : " + eleve.getId());
	out.println(    eleve.getNom());
	out.println(eleve.getPrenom());
	out.println(el eve.getAdresseMail());

	    
	
//------------------------Cr       itere----OK---------   ----------  ----       ------------------------------------- ------
	C       ritereDAO criter  eDA O = null;  
    t   ry
    {
	    critere DA O = (CritereDAO) ServicesL  ocator.getInstance().getRemoteInte rface("Crite       reDAO");
    }
    catch (ServicesLocatorException e)
      {
    	e.printStackTrace();
    }
    
    out.println("---------------     ------------------------------------------------------   -------  ----------------------------  ---------------   -------------------------  --------");
	out.println("Contrôles de fon     ctionnement du DAO CritereDAO");
	out.println();
	
	    Criter e critere = critereDAO.findById(1);
	
	o   ut.println("Critère n°1 : " + critere.getId());
	 ou  t.println("Description  générale : " + critere.getDescription());
	out.println("Niveau 1 : " +critere.getDescNiveauPerfo           rmance1(  ));  
	out.println("Niveau 2 : "+c    ritere.getDescNiveauPerformance2());
	out.println("Niveau 3 : " +critere.g     etDescNiveauPe  rformance3()+"(C'est une case vide dans la base de données)");
	out.println("Niveau 4 : "+cri    tere.    getDescNiveauPerformance4());
    o        ut.println(critere.getGril  l   e());
    out.println();
      
    cr  itere = critereDAO.findById(2);
       out.print   ln("Critère n°2 :   " + critere.getId());
	    out.println("     Description gé nér  ale : " + critere.getDescription());
	out.println("Niveau 1 : "+ critere.getDescNivea uPerf     ormance1());
	out.println("Niveau 2 : "+ critere.get  DescNiveauPerformance2());
	    out.println("Niv   eau 3 : "+ critere.getDescNiveauPerformance3());
	out.println           ("Niveau 4 : "+ critere.getD  escNiveau Performance    4());
	      out.println(critere.getGrille() );
	    
//----------------  ---   -----Enseignant----OK--------------------------------------------------  -   ----------     -----
      		
	EnseignantDAO enseignantDA    O = null;
    t      ry
    {
	    enseignantDAO = (EnseignantDA        O)         ServicesL ocator.getInstance().g   etRemoteI   nterf   ac    e("   EnseignantDAO  ");
    }
    catch    (ServicesLocato   rExcept   ion e)
    {
    	e.print   StackTrace(    );
    }
    
    out.println("----------------------------------     ----------------------------        ------------------------------------------------------------------------------    ------------");
	out.println("Contrôles de f on    ctionnement du DAO E     nseignantDAO");
	out.println();
    
	Ense   ignant enseignan t = enseignantDAO.findById(1);
	out.println("Enseignant n°"+   enseignant.getId());
	out.print   ln("Nom : "+enseignant.getNom());
	out.println("Pr  enom : "+enseignant .getPrenom());
	out.println("Mail : "+enseignant.ge   tAdresseMail()) ;
	out.println("ens   eignant.getAsso3es().size() = " + enseign    ant.getAsso3e  s().size());
	out.print   ln("enseignant.getAsso3es() : Enseignement, En    sei        gnant, Eleve");
	
	for( Asso3e a :     enseignant.getAsso3es())
	{
		out.println("---> Nom enseignement :  " + a.getEnseignement().getNom() + " -- Nom enseignant : " + a.getEnseignant().getNom() + " -- Nom eleve : " + a.getEleve().getNom());
	}
	
	out.println("Nombre d      e gri  lles as     sociées à l'enseignant : "+ enseignant.getGrilles().size()); 
	for(Grille g : enseignant .getGril   les())
	{
		out.   println("--->      Nom de l  a grille associée : " + g.get  Nom());
	}
	out.println();
	
	
	enseignant = enseignantDAO.findById(2);
	out.printl   n("Ense    ignant n°"+enseignant.getId());
	   out.println("Nom : "+enseignant.getNo    m()    );
	o   ut.print      ln("Prenom : "+enseignant.getPrenom());
	out.   println("Mai   l : "+enseignant.getAdr     esseMail());
	out.println("enseignant.getAsso3es().size() = " + enseignant     .getAsso3es()   .size());
	out.    println("enseignant.getAsso3e   s() : En          seignement, Enseign   ant, Eleve");   
	
	for(Asso3e g : e    nseignant.getAsso3es(   ))
	{
		out.println("---  > Nom en        sei gnement : " + g .getEnseignement().getNom() + " -- Nom en seignant : " + g.getEnseignant().getNom() + " -- Nom   eleve : " + g.   getEleve().getNom());
	}
	
	out.println("Nombre de gr   illes associées à l'ense ignant :     "+ enseignant.getGr    illes().size());
	for(Grille g : enseignant.getG      rilles())
	{
		out.print  ln("--->  Nom de la grille as sociée : " + g.getNom());
	}
	out.println(); 
    
//------------------------Enseigne  ment-----------  ---OK----------  ---  -------------------------------------------   
	        
	EnseignementDAO e     nseignementDAO = null;
	try
	{
	    enseignementDAO = (EnseignementDAO) S     ervicesLocator.getInst a     nce().getRemoteInterface("EnseignementDAO");
	}
	catch (ServicesLocatorExce   ption e)
	{
		e.printStackTrace();
	}
	
	out.println(      "-------------------------  --------------------------------------------------------------------------------------------------   ----------------------------       -");
	out.print ln("Contrôles de fonctionnement d   u DAO Ens    eignementDAO");
	out.p  rintln();
	
	Enseignemen  t enseignement = enseignementDAO.findById(1);
	out.println(e   nseignement.getId());
	out.println(enseignement.getNom(  ));
	
	out.println(" enseignement.getA    ss      o3es()   : Enseignement, Enseignan t, Eleve");
	for(Asso3e a : enseignement.ge   tAsso3es())
	{
		out.println("---> No      m enseignement : " + a.getEnseignement().getNom() +   " - - No   m en seignant : " + a.getEnseignant().getNom() + " -- Nom  eleve      : " + a.getEleve().getNom());
	}
  	
	out.println("enseignement.getAssoOges() : Objectif Pédag ogique, Grille, Enseignement");
	for(AssoOge a : enseig     nement.getAssoOges())
	{
		ou     t.pr      in tln("---> Id Object  if : " + a.getObjectifPedagog  ique().getId() + " -- Nom grille : " + a.getGrille().getN    om()    + " -- Nom enseign       ement : " + a.getEnseignement().getNom())  ;
	}
	out.println();
	
	enseignement = enseignementDAO.findById(2);
	out.println(enseignement.getId());
	out.println(enseignement.g   etNo   m());
  	
	out.println("enseignement.getAssoOges() : Objectif       Pédagog   ique, Grille, Enseignement");
	for(AssoOge a : enseignement  .getAssoOges())  
	{
		out.println("---> Id Objectif : " + a.getObjectifPedagogique().getId() + " -- Nom grille : " + a.   getGrille().g etNom() + " -- Nom en   seignement : " + a.getEnseignemen t().getNom());   
	}
	
	//--------       ----------------Objectif Pédagogique------------OK--- -------------------------------------------------------   		
	      
	ObjectifPedagogiqueDAO obje   ctifpedagogiqu   eDAO = null;
	try
	{
		objectifpedagogiqu     eDAO = (ObjectifPedagogiqueDAO) ServicesLocator.getInst   ance().getRemoteInterface("ObjectifPedagog  iqueDAO");
	}
	catch (S   ervicesLocatorException e)
	{
		e.printStackTrace();
	}
	

	out.println("-------------------------------------      ---------------------------   ----------------------       ----------------------------------------------   --------------------");
  	out.println("Contrôles de fonctionnement du DAO ObjectifPedagogiqueDAO");
	out.println();
	
	ObjectifP  edagogique op =      ob   jectifpedagogiqueDAO.findById(1);
	out.println("Obj  ectif Pédagogique n°"      +op.getId());
	out.println(op.getDescript  ion());
	out.println("op.getAssoOges() : Objectif Pédagogique, Grille, Enseignement");
	for(AssoOge a : op.getAssoOges())
	{
		out.pri   ntln("---> Id Objectif : " + a.getObjectifPedagogique().getId() + " -- Nom grille : " + a.getGrille().getNom  () + " -- Nom enseignement : " + a.ge   tEnseignement().getNom());
	}
	out.println();
	
	
	op = objectifpedagogiqueDAO.findById(2);
	out.prin    tln("Objectif Pédagogique n°"+op.getId());
	o   ut.println(op.get Description());
	out.println();
	
	op    = objectifpeda  gogiqueDAO.findById(3);
	out.print  ln   ("Objectif Pédagog  ique n°"+op.getId());
	out.println(op.getDescription());
	out.      println();
	
	/      /------------------------Grille------------OK-----------------------------------  -----------------------   		
    
	Grill  eDA   O grilleDAO =  null;
	try
	{
		grilleDAO = (G     rilleDAO) ServicesLo   cator.getInstance().ge       tRemoteInterface("GrilleDAO");
	}
	catch (S ervi    cesLocatorException e)
	{
   		e.printStackTrace();
	}
	
	out.println("----------  --------------------    ----  ------------------------------- -------     -    ----------     ---------------------------------   ------------------------------------");
	out.println("Contrôles de fonc      tionnement du DAO GrilleDAO");
	out.println();
	
	Grille grille = g  rilleDAO.findById(1);
	out.println("Gri   lle n°"+grille.getId());
	out.println(g  rille.getNo  m());
   	out.println("Ni    veau 1 : " + grille.g etNiveauPerform  ance1());
	out.println("Niveau 2 : " + grill    e.getNiveauPerform  ance2());
  	out.println("Niveau 3 : " + grille.getNiveauPerformance3());
	out.println("Niveau    4 : " + grille.getNiveauPerformance4());
	
	out.println("g       rille.getCriteres  ()");
	for(Critere c : grille.getCriteres())
	{
		out.println(   "---> Id : " + c.getId() +  " -- Description : " +  c.getDe    scription());
	}
	
	out.println(    "grille.getEnseignants()");
	for (Enseignant ens : grille.getEnseignants())
	{
		out.printl     n("---> Nom : " + ens.getNom() + " -- Prenom : " +  ens.getPrenom());
	}
	out.pri ntln();

	grille = grilleDAO.findById(2);
	out.println("Grille n°"+grille.getId());
	out.println(grille.getNom());
	out.println( "Niveau 1 : " + grille.getNiveauPerformance1());
	out.println("Niveau 2 : " + grille.getNiveauPerformance2(     ));
	out.println("Niveau 3 : " + grille.getNiveauPerformance3());
	out.println("Niveau 4 : " + grille.getNiveauPerformance4());
	
	}

}
