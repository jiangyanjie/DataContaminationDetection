package    front.controlesDAO;

imp  ort java.io.IOException;
import java.io.PrintWriter;

import     javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServle     t;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import   dao.*;
impo rt front.utils.*;
impo    rt model.*;

/**
 * Servlet implementation class ControleDAO
 */
@WebServlet("/Contr oleDAO")
public class ControleDAO extends HttpServlet {
	pr  ivat     e static final long seri  alVersion   UID    = 1      L;   
          
         /**
     *   @see   HttpSe rvle  t#HttpServ  let(   )
     */
        public     Control               eDAO          () {
        sup       er();
                // TODO Auto-generated constructor   stu   b
    }

	/**
	 * @s    ee HttpServlet#doGet(HttpServletRequest reques   t, HttpServletResponse re   sponse)
 	 */
	protected void service(HttpServletRequest request, HttpServletResponse response    ) throws ServletException, IOException 
	{

		// Flot de sortie pour écriture des résultats.
	    PrintWrite  r out = response.getWriter();

//-----------------------    -Eleve--------OK-----------          ------------- -----------------------------------------
	EleveDAO ele  veDAO = null;
	try
	{
	    eleveDAO = (EleveDAO) ServicesLocator.getInstance().getRemoteInterface("EleveDAO");
	}
	catch (ServicesLocatorExce ption e)
	{
		e.printStackTrace();
	}
	
	out.println("-------------------------------------------------------------------------------    ------------------------------------------------------------------------   -");
	out.p    rintln("Contrôles de fo   nc      tionn ement du DAO EleveDAO");
	out.println()   ;
	
	Eleve eleve = eleveDAO.findById(1);
	out.println("Eleve n°1 : " + eleve.getId());
	out.println(eleve.getN     om());
	out.println(eleve.getPrenom());
	out.println(eleve     .getAdresseMail());
	
	out.println();
	eleve = eleveDAO.findById(2);
	out.println("Eleve n°2 : " +      eleve.getId());
	o     ut.println(eleve.getNom());
	out.println(eleve.getPrenom());
	out  .println(eleve.getAdresseMail());

	    
	
//------------------------Critere----OK----------     ----- ---------------------------------------  ------------
	Cri   tereD   AO critereDAO    =  null;
    try
                  {
	    critereDAO = (Critere   DAO) Ser      vicesLocato    r.getInsta     nce().getRemoteInterface("CritereDAO");
    }
    catch (ServicesL   ocatorE     xception e)
    {
    	e.printStackTrace();
        }
    
    out.println("--------------------------------------------------------------------------------------------------------------------------------------------------------");
	out.println("Contrôles de fonctionnement du DAO CritereDAO");
	out.prin tln();
	
	Critere critere = critereDA       O.findById(1);
	
  	out.pri   ntln("Cri tère n°1 : " + critere.ge     tId());
	out.pri   ntln("Description générale : " + critere.getDescription());
	   out.println("Niv   eau 1 : "+critere.getDescNiveauPerformance1());
	out.println("Niveau     2 : "+c    ritere.getDescN   iveauPerformance2());
	out   .   println("Niveau 3          : "+        critere.    ge   tD    escNiveauPerformance3()+"(C'est une case  vide d ans la base de          données)");
	out.println(   "Niveau 4 : "+critere.getDescNiveauPer   formance4());
    out.println(crit       ere.getGrille());
    out.println();
    
    critere = crite    reDAO.findById(2);
    out.println("Cri  tère n°2 : " + cr  itere.getId());
	out.println("Description générale : " + critere    .getDescription());
	out.println("Niveau     1 : "+ critere.    getDescNiveauPerformance 1());
	out.println("Niveau 2 : "+ cri    tere.getDescNiveauP    erformance2());
	out.print    ln("Niveau 3 : "+ critere.getDescNiveauPerformance3());
	out.println("Niveau 4 : "+ critere.getDes    cNiveauPerformance4());
	out.println(critere.getGrille());
	    
/ /-------  -----------------Enseignant-  ---  OK--------     -------  -    ------        --------------------------------------------
		
	Enseignan     tDAO enseignantD    AO = null;
            try
    {
	        en     seign    antDA     O = (Ense        ignantDAO) ServicesLocator.getInstance().getRemoteInterface("Enseignan    tDAO");
    }
    catch (ServicesLocatorExcept   ion e)
    {
    	e.printStackTrace();
    }
      
    o  ut.  println("-------------------------------------------- - -----------------------------------------------------------------------------------------------------------");
   	out.println("Contrôles de fo   nctionnement du D   AO EnseignantDAO")  ;
	out.println();
        
	Enseignant enseignant =   enseignantDAO.findById(1);
	out.println("Enseignant n°"+enseignant.getId());
	out.println("Nom :    "+enseignant.getNom());  
	out.println("Pren om : "+enseignant.     getPrenom() );
	out.println( "Mail : "+enseignant.getAdresseMai        l());
	out.println("enseignant.getAsso3e   s().s      ize(    ) = " +    enseign   ant.getAsso3es().size());
	out.println("enseignant.getAsso3es() : Enseig    nement, Enseignant, Eleve");
	
	for(Asso3e a : enseignant.getAss   o3es())
	{
		out   .pri  ntln("---> Nom enseignement : " + a.g  etEnseignement().ge tNom() + " - - Nom enseignant : " + a   .getEnseignant().getNom() + " -- Nom eleve : " + a.getEleve ().getNom());
	}
	
	out.println("Nombre de grilles associées à l'enseignant : "+ enseignant.g  etGrilles().size());
	for(Grille g : enseignant.getGrilles())
	{
  		out.prin tln("-- ->  Nom de la grille associée : " + g.getNom());
	}
	out.println();
	
	
	enseig  nant = enseignantDAO.findById(2);
	out.println("Enseignant n°"+enseignant.getId())           ;
	out.println("Nom : "+enseignant.getNom());
	out.pri  ntln("Prenom : "+e nseignant.getPrenom());
	out    .println("Mail : "+enseignant.getAdresseMa  il());
	out.println("ens  eignant  .getAsso3es().size() = " + enseignant.      getAsso3es().size         ());
	out.pr    i    ntln("enseignan  t.getAsso3es() : Enseignement, Ense   ignant, Eleve");
	
	for(   Asso3e g : enseignant.getAsso3es())
	{
		out.println("---> Nom enseig     nement : " + g.getEnse   ignement().getNom() + " --     Nom    enseignant : " + g.getEnseignant().getNom() + " -- Nom eleve :  " + g.getEleve().getNom ())         ;
	}
	
	out.println("Nombre de     grilles associées   à l'       enseignant : "+ ensei  gnant.getGrilles().size());
	for(Gril     le g : ens        eignant.getGrille   s())
	{
		out.printl       n("--->  Nom de la grille associée : " + g.getNom());
	}
	out.println();
    
//------------------------Enseignement--------------OK---------------------------------   -------------  ----------   
	      
	Ensei       gnementDAO enseignementDAO = null;
	try
	{
	    enseignementDAO = (EnseignementDAO) ServicesLocator.getInstance().getRemoteInte   rface("Enseignem   entDAO");
	}
	catch (Services   Locat    orException e)
	{
		e.pr      intStac    kTrace();
	}
	
	out.println("------------- ----------------  ------------------------------------------------------------------------------- --------------------  - -----------------------");
	out.println(" Contrôles de fonctionnement du DAO EnseignementDAO");
   	out.pri    ntln();  
	
	Enseignement enseignement = enseignementDAO.findById(1);
	out.println(enseignement.getId    ());
	out.println(enseignement.get  Nom());
	
	out.println    ("enseig   nement.getAsso  3es() : En        seignement, Enseignant, Eleve");
	for(Asso3e a : enseignement.getAsso3es())
	{
		out.     println("---> No   m enseignement : " + a.getEn    seigneme    nt().getNom()    + " --     Nom enseignant : " + a.getEnseignant().getNom() + "   --   Nom eleve : " + a.getEleve().getNom());
	}
    	
	out.println("   ensei   gnem    e  nt.getAssoOges        () : Objectif Pédagogiq  ue, Grille, Enseignement");
	for(AssoOge a : enseignement.getAssoO    ges())        
	{
		out.prin    tl   n("---> Id Objectif : "     + a.getObjectifPedagogiq  ue().getId() + " -- Nom grille : " +     a.getGr ille().getNom() +   " -- Nom enseignement : " + a.getEnseignement().getNom());
	     }
	out.println();
	
	enseignement = ensei   gnementDAO.findByI  d(2);
	out.println(enseignement.getId());
	out.println(enseign     ement.getNom());
	
	out.println("ensei       gnement.getAssoOges() : Objectif Pédagogique, Grille, Enseignement");
	for(AssoOge a : ense   i    gnement.getAssoOge    s())
	{
  		out.println("---> Id  Objectif : " + a.getObjectifPedago   gique().getId()     + " -- Nom grille : "      + a.getGrille().getNom() + " -- Nom enseignement : " + a.getEnseignement().getNom())   ;
	}
	
	//-----   -------------------Objectif Pédagogique------------OK   ----  -----------------------   ------------------------------    -   		
	    
	ObjectifPedagogiqueDAO objectifped    agogiqueDAO = nul    l;
	try
	{
		objectifpedagogiqueDAO = (ObjectifPedagogiqueDAO    ) ServicesLoca   tor.getIns  tance().ge    tRemoteInterfac   e("ObjectifPedagogiqueDAO");
	}
	catch (Service  sLocatorException e)
	{
		e.print    StackTrace();
	}
	

	out.println("----------------------------- ---------------------------  ----------------------------------------------------------------------------   --------     ------------");
	out.println("Contrôles de fonctionnement du DAO Objecti   fPedagogiqueDAO");
	out.println();
	
	ObjectifPedagogique op = objectifpedagogiqueDAO.findById(1);
	out.println("Objectif Pédagogique n°"+op.getId());
	out.println(op.     getDescription())   ;
	out.println("op.getAssoOges() : Objectif Pédagogique, Grille, Enseignement");
	for(AssoO ge a : o  p.getAssoOges())
	{
		out.println("---> Id Objectif : " + a.ge tObjecti fPedagogique().getId() + " -- Nom grille : " + a.getGrille().getNom() + " -- Nom ense   ignement : " + a.getEnseignement().getNom());
	}
	out.println();
	
	
	op = objectifpedagogiqueDAO.findById(2);
	out.println("Objectif Péd   agogique n°"+op.getId() );
	out.println(  op.getDescription());
    	out.println();
	   
	op = objectifpedagogiqueDAO.findById(3);
	out.println("Objectif Pédagogique n°"+op.getId());
	out.println(op.getDescription());
	out.println();
	
	//------   ------------------Grille------------OK------------------------   --------  --------------------------   		
    
	GrilleDAO grilleDAO = null;
	try
	{
		grilleDAO = (GrilleDAO) ServicesLocator.getInstance().getRemoteInterfac  e("GrilleDAO");
	}
	catch (ServicesLocatorEx  ception e)
       	{
		e.printStackTrace();
	   }
	
	out   .p  rintln("---------------------------------    --------------------    ------   ----------------------------------------------------------------   -----------------       -   -----------");     
	out.println("Contrôles de fonctionneme   nt du DAO GrilleDAO");
	out.println(); 
	
	Gr   ille grille = grilleDAO  .findById(1);
	out.printl    n("Grille n°"+grille.getId());
	out   .println(grille.getNom());
	o  ut.pr      intln("Niveau 1 : " + grille.getNiveauPerformance1()    );
	out.println("Niv    eau 2 : " + grille.getNive   auPerformance2());
	out.println("Niveau 3 : " + grille.getNiveauPerformance3());
	out.println("N     iveau 4      : " + grille.getNiveauPerformance4());
	
	out.println("grill   e.getCriteres()");
	for(Crit    ere c : grille.ge   tCriteres())
	{
		out.println("---> Id    : " + c    .getId() + " -- Description : " +  c.getDescription(  ));
	}
	
	out.println("grille.getEnseignants()");
	fo r(Enseignant ens : grille.getEnseignants())
	{
		out.println("--- > Nom : " + ens.getNom      () + " -- Prenom : " +       ens.getPrenom());
	}
	out.println();

	grille = grilleDAO.findById(2);
	out.println("Grille n°"+grille.getId());
	out.println(grille.getNom());
	out.println("Niveau 1 : " + grille.getNiveauPerformance1());
	out.println("    Niveau 2 : " + grille.getNiveauPerformance2());
	out.println("Niveau 3 : " + grille.getNiveauPerformance3());
	out.println("Niveau 4 : " + grille.getNiveauPerformance4());
	
	}

}
