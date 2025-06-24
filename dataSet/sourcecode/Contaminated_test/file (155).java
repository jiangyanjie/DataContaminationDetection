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
    private static final String CATEGORIE_SEMINAIRE = "séminaire";
    private static final String CATEGORIE_COLLOQUE = "colloque";
    private static final String CATEGORIE_CONFERENCE = "conférence";
    private static final String CATEGORIE_LECTURE_DIRIGEE = "lecture dirigée";
    private static final String CATEGORIE_PRESENTATION = "présentation";
    private static final String CATEGORIE_GROUPE_DISCUSSION = "groupe de discussion";
    private static final String CATEGORIE_PROJET_RECHERCHE = "projet de recherche";
    private static final String CATEGORIE_REDACTION_PROFESSIONNELLE = "rédaction professionnelle";
    
    // Liste des catégories d'activités reconnues pour ce cycle
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

    // Créer une liste d'heures accumulées (une entrée pour 
    // chaque catégorie reconnue, avec 0 comme valeur de départ).
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
            // Ajouter le(s) message(s) d'erreur à la liste de
            // messages d'erreurs de la declaration.
            for (int j=0; j < activite.getNombreMessagesErreur(); j++) {
                declaration.ajouterMessageErreur(activite.getMessageErreur(j));
            }
        } catch (Exception ex) {
            declaration.ajouterMessageErreur("Problème logiciel " +
                                             "détecté: " + ex.getMessage());
        }
    }
    
    // Créer des objets Activite avec les activites définies dans le document JSON
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
                // qui doivent être incluses dans le fichier de résultat.
                // C'est pourquoi on essaie toujours de transférer les
                // messages d'erreur, que l'activité soit reconnue ou non.
                transfererMessagesErreurDansDeclaration(activite);
            } catch (Exception exception) {
                declaration.ajouterMessageErreur("Erreur de lecture: " + exception.getMessage());
            }
        }
    }

    // NOTE: L'application de limites sur le nombre d'heures pour certaines
    // catégories n'entraîne la génération d'aucun message d'erreur.    
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
    // formation en surplus qui ont été complétées lors du cycle précédent et
    // qui peuvent être utilisées dans le cycle courant. Ce nombre ne peut pas
    // être négatif et ne doit pas être supérieur à 7. S'il est supérieur à 7,
    // un message doit être produit dans le fichier de sortie et uniquement 7
    // heures seront considérées lors des calculs.
    private void validerHeuresTransferees() {
        if (declaration.getHeuresTransfereesDuCyclePrecedent() < 0) {
            declaration.setHeuresTransfereesDuCyclePrecedent(0);
            declaration.ajouterMessageErreur("Nombre d'heures transférées du" +
                   " cycle précédent est négatif. La valeur 0 sera utilisée.");
        } else if (declaration.getHeuresTransfereesDuCyclePrecedent() > 7) {
            declaration.setHeuresTransfereesDuCyclePrecedent(7);
            declaration.ajouterMessageErreur("Nombre d'heures transférées du" +
             " cycle précédent est supérieur à 7. La valeur 7 sera utilisée.");
        }
    }
    
    // Un minimum de 17 heures doivent être déclarées dans les catégories
    // suivantes : cours, atelier, séminaire, colloque, conférence, lecture
    // dirigée. Autrement dit, la somme des heures des activités appartenants
    // à l'ensemble de ces catégories doit être supérieure ou égale à 17 heures.
    // En dessous du 17 heures, un message doit être produit dans le fichier
    // de sortie. Les heures transférées du cycle précédent sont comptabilisées
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
                "ateliers, séminaires, colloques, conférences, lectures " +
                "dirigées et heures transférées du cycle précédent est " + 
                "inférieur à 17 heures.");
        }
    }
    
    // Un minimum de 40 heures de formation doivent être déclarées dans le cycle.
    // Il n'y a pas de maximum. En dessous du 40 heures, un message doit être
    // produit dans le fichier de sortie.
    private void validerNombreTotalHeuresAccumulees() {
        int total = getTotalHeuresAccumulees();
        if (total < 40) {
            declaration.setComplet(false);
            declaration.ajouterMessageErreur("Il manque " + (40 - total) +
                             "heures de formation pour compléter le cycle.");
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
     * Effectue les validations relatives au cycle 2012-2014. Les activités
     * sont créés sous forme d'objet Activite2012_2014 et rangées dans les
     * listes "reconnues" et "ignorées" de la déclaration. Si la
     * déclaration dans son ensemble est acceptée, le champ "complet"
     * de la déclaration est initialisé à true. Si l'une ou l'autre
     * des règles d'affaire du cycle n'est pas respectée, le champ
     * "complet" sera initialisé à false.
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
