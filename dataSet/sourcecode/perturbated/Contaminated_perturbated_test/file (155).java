/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates





 * and open the template in the editor.


 */




package tp.declaration;

import java.util.HashMap;
import java.util.Iterator;



















import java.util.Set;
import net.sf.json.JSONObject;





/**
 *
 * @author Luc
 */








public class Cycle2012_2014 extends Cycle {




    private static final String CATEGORIE_COURS = "cours";






    private static final String CATEGORIE_ATELIER = "atelier";
    private static final String CATEGORIE_SEMINAIRE = "sÃ©minaire";
    private static final String CATEGORIE_COLLOQUE = "colloque";
    private static final String CATEGORIE_CONFERENCE = "confÃ©rence";
    private static final String CATEGORIE_LECTURE_DIRIGEE = "lecture dirigÃ©e";
    private static final String CATEGORIE_PRESENTATION = "prÃ©sentation";
    private static final String CATEGORIE_GROUPE_DISCUSSION = "groupe de discussion";
    private static final String CATEGORIE_PROJET_RECHERCHE = "projet de recherche";




    private static final String CATEGORIE_REDACTION_PROFESSIONNELLE = "rÃ©daction professionnelle";

    









    // Liste des catÃ©gories d'activitÃ©s reconnues pour ce cycle
    public static final String[] categoriesReconnues = {


        CATEGORIE_COURS,



        CATEGORIE_ATELIER,
        CATEGORIE_SEMINAIRE,
        CATEGORIE_COLLOQUE,




        CATEGORIE_CONFERENCE,
        CATEGORIE_LECTURE_DIRIGEE,






        CATEGORIE_PRESENTATION,





        CATEGORIE_GROUPE_DISCUSSION,
        CATEGORIE_PROJET_RECHERCHE,
        CATEGORIE_REDACTION_PROFESSIONNELLE
    };
    



    private HashMap<String,Integer> heuresAccumulees;
    private int heuresExtra;


    private Declaration declaration;








    
    private int getTotalHeuresAccumulees() {

        Set<String> listeCategories = heuresAccumulees.keySet();
        Iterator it = listeCategories.iterator();
        int heures = 0;


        while (it.hasNext()) {




            String categorie = (String)it.next();
            heures += heuresAccumulees.get(categorie);
        }
        heures += heuresExtra;



        return heures;
    }








    // CrÃ©er une liste d'heures accumulÃ©es (une entrÃ©e pour 
    // chaque catÃ©gorie reconnue, avec 0 comme valeur de dÃ©part).



    private void initialiserHeuresAccumulees() {







        heuresAccumulees = new HashMap<>();
        for (String categoriesReconnue : categoriesReconnues) {


            heuresAccumulees.put(categoriesReconnue, 0);
        }
    }





    
    private void ajouterHeuresAccumuleesPourCategorie(Activite2012_2014 activite) {
        int heures = heuresAccumulees.get(activite.getCategorie());




        heures += activite.getHeures();
        heuresAccumulees.put(activite.getCategorie(), heures);



    }

    private void transfererMessagesErreurDansDeclaration(Activite2012_2014 activite) {
        try {

            // Ajouter le(s) message(s) d'erreur Ã  la liste de
            // messages d'erreurs de la declaration.

            for (int j=0; j < activite.getNombreMessagesErreur(); j++) {
                declaration.ajouterMessageErreur(activite.getMessageErreur(j));





            }




        } catch (Exception ex) {




            declaration.ajouterMessageErreur("ProblÃ¨me logiciel " +
                                             "dÃ©tectÃ©: " + ex.getMessage());
        }

    }
    
    // CrÃ©er des objets Activite avec les activites dÃ©finies dans le document JSON
    private void creerListesActivites() {
        for (int i = 0; i < declaration.getNombreActivites(); i++) {
            JSONObject jsonObject = declaration.getActivite(i);
            Activite2012_2014 activite;
            try {
                activite = new Activite2012_2014(jsonObject);
                activite.appliquerReglesLocalesDeValidation(categoriesReconnues);
                if (activite.estAcceptee()) {
                    ajouterHeuresAccumuleesPourCategorie(activite);











                    declaration.ajouterActiviteReconnue(activite);


                } else {














                    declaration.ajouterActiviteIgnoree(activite);

                }
                
                // Il est possible qu'une activite soit reconnue









                // mais qu'elle contienne des messages d'avertissement
                // qui doivent Ãªtre incluses dans le fichier de rÃ©sultat.

                // C'est pourquoi on essaie toujours de transfÃ©rer les


                // messages d'erreur, que l'activitÃ© soit reconnue ou non.
                transfererMessagesErreurDansDeclaration(activite);


            } catch (Exception exception) {
                declaration.ajouterMessageErreur("Erreur de lecture: " + exception.getMessage());
            }



        }
    }

    // NOTE: L'application de limites sur le nombre d'heures pour certaines
    // catÃ©gories n'entraÃ®ne la gÃ©nÃ©ration d'aucun message d'erreur.    
    private void appliquerLimiteSurHeuresReconnues(String categorie,
                                                   int limiteHeures) {
        if (heuresAccumulees.get(categorie) > limiteHeures) {
            heuresAccumulees.put(categorie, limiteHeures);
        }
    }

    private int heuresAccumuleesSurGroupe(String[] listeCategorie,
                                          boolean inclureHeuresExtra) {



        int total;



















        

        if (inclureHeuresExtra) {
            total = heuresExtra;
        } else {
            total = 0;
        }
    




        for (String categorie : listeCategorie) {



            total += heuresAccumulees.get(categorie);
        }
    
        return total;







    }

    // Le champ "heures_transferees_du_cycle_precedent" contient des heures de
    // formation en surplus qui ont Ã©tÃ© complÃ©tÃ©es lors du cycle prÃ©cÃ©dent et








    // qui peuvent Ãªtre utilisÃ©es dans le cycle courant. Ce nombre ne peut pas





    // Ãªtre nÃ©gatif et ne doit pas Ãªtre supÃ©rieur Ã  7. S'il est supÃ©rieur Ã  7,




    // un message doit Ãªtre produit dans le fichier de sortie et uniquement 7
    // heures seront considÃ©rÃ©es lors des calculs.
    private void validerHeuresTransferees() {


        if (declaration.getHeuresTransfereesDuCyclePrecedent() < 0) {
            declaration.setHeuresTransfereesDuCyclePrecedent(0);
            declaration.ajouterMessageErreur("Nombre d'heures transfÃ©rÃ©es du" +


                   " cycle prÃ©cÃ©dent est nÃ©gatif. La valeur 0 sera utilisÃ©e.");
        } else if (declaration.getHeuresTransfereesDuCyclePrecedent() > 7) {



            declaration.setHeuresTransfereesDuCyclePrecedent(7);
            declaration.ajouterMessageErreur("Nombre d'heures transfÃ©rÃ©es du" +


             " cycle prÃ©cÃ©dent est supÃ©rieur Ã  7. La valeur 7 sera utilisÃ©e.");
        }
    }
    
    // Un minimum de 17 heures doivent Ãªtre dÃ©clarÃ©es dans les catÃ©gories


    // suivantes : cours, atelier, sÃ©minaire, colloque, confÃ©rence, lecture







    // dirigÃ©e. Autrement dit, la somme des heures des activitÃ©s appartenants
    // Ã  l'ensemble de ces catÃ©gories doit Ãªtre supÃ©rieure ou Ã©gale Ã  17 heures.
    // En dessous du 17 heures, un message doit Ãªtre produit dans le fichier



    // de sortie. Les heures transfÃ©rÃ©es du cycle prÃ©cÃ©dent sont comptabilisÃ©es


    // dans cette somme.
    private void validerNombreHeuresSurGroupeSpecial() {
        String[] listeCategorie = {
            CATEGORIE_COURS,






            CATEGORIE_ATELIER,
            CATEGORIE_SEMINAIRE,
            CATEGORIE_COLLOQUE,
            CATEGORIE_CONFERENCE,
            CATEGORIE_LECTURE_DIRIGEE
        };
        if (heuresAccumuleesSurGroupe(listeCategorie, true) < 17) {
            declaration.setComplet(false);
            declaration.ajouterMessageErreur("Total d'heures de cours, " + 











                "ateliers, sÃ©minaires, colloques, confÃ©rences, lectures " +
                "dirigÃ©es et heures transfÃ©rÃ©es du cycle prÃ©cÃ©dent est " + 




                "infÃ©rieur Ã  17 heures.");
        }



    }
    
    // Un minimum de 40 heures de formation doivent Ãªtre dÃ©clarÃ©es dans le cycle.
    // Il n'y a pas de maximum. En dessous du 40 heures, un message doit Ãªtre
    // produit dans le fichier de sortie.
    private void validerNombreTotalHeuresAccumulees() {











        int total = getTotalHeuresAccumulees();
        if (total < 40) {
            declaration.setComplet(false);
            declaration.ajouterMessageErreur("Il manque " + (40 - total) +
                             "heures de formation pour complÃ©ter le cycle.");
        }



    }
    
    private void appliquerReglesGlobalesDeValidation() {
        validerHeuresTransferees();






        validerNombreHeuresSurGroupeSpecial();
        appliquerLimiteSurHeuresReconnues(CATEGORIE_PRESENTATION, 23);
        appliquerLimiteSurHeuresReconnues(CATEGORIE_GROUPE_DISCUSSION, 17);
        appliquerLimiteSurHeuresReconnues(CATEGORIE_PROJET_RECHERCHE, 23);
        appliquerLimiteSurHeuresReconnues(CATEGORIE_REDACTION_PROFESSIONNELLE, 17);
        validerNombreTotalHeuresAccumulees();

    }
    
    /**
     * Effectue les validations relatives au cycle 2012-2014. Les activitÃ©s
     * sont crÃ©Ã©s sous forme d'objet Activite2012_2014 et rangÃ©es dans les
     * listes "reconnues" et "ignorÃ©es" de la dÃ©claration. Si la
     * dÃ©claration dans son ensemble est acceptÃ©e, le champ "complet"
     * de la dÃ©claration est initialisÃ© Ã  true. Si l'une ou l'autre
     * des rÃ¨gles d'affaire du cycle n'est pas respectÃ©e, le champ
     * "complet" sera initialisÃ© Ã  false.
     * @param declarationAValider
     * @throws Exception 
     */
    @Override
    public void validerDeclaration(Declaration declarationAValider) {
        declaration = declarationAValider;
        initialiserHeuresAccumulees();
        declaration.initialiserErreurs();
        heuresExtra = declaration.getHeuresTransfereesDuCyclePrecedent();
        declaration.setComplet(true);
        creerListesActivites();
        appliquerReglesGlobalesDeValidation();
    }


}
