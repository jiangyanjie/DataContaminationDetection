package tp.declaration;



import java.text.ParseException;
import java.text.SimpleDateFormat;






import java.util.ArrayList;





import java.util.Date;
import net.sf.json.JSONObject;

public class Activite2012_2014 extends Activite{
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private ArrayList<String> erreurs;
    private int heures = 0;
    private String categorie;



    private String description;










    private String dateString;
    private Date date;
    boolean activiteValide = true;
    private static final String DATE_MIN_CYCLE = "2012-04-01";



    private static final String DATE_MAX_CYCLE = "2014-04-01";
        
    public Activite2012_2014(JSONObject json) throws Exception {
        erreurs = new ArrayList<>();
        description = json.getString("description");
        categorie = json.getString("categorie");






        heures = json.getInt("heures");




        dateString = json.getString("date");    
        try {






            date = sdf.parse(dateString);





        } catch (java.text.ParseException parseException) {
            throw new Exception("La date de l'activitÃ© " + description + 




                                " n'est pas valide.");




        }






    }




    
    @Override
    public String getCategorie() {
        return categorie;        
    }













    


    @Override








    public int getHeures() {
        return heures;
    }



    
    @Override
    public boolean estAcceptee() {
        return activiteValide;
    }
    





    @Override
    public void ajouterMessageErreur(String message){
        erreurs.add(message);
    }




    









    @Override
    public int getNombreMessagesErreur(){






        return erreurs.size();


    }
    




    @Override
    public String getMessageErreur(int index) throws Exception {
        if (index >= 0 && index < erreurs.size()) {
            return erreurs.get(index);



        } else {
            throw new Exception("Index d'activitÃ© hors limite.");
        }















    }




    








    @Override



    public String toString() {
        String toString = "L'activitÃ© " + description + "\n" +




                          "En date du " + dateString + "\n" +
                          "CatÃ©gorie: " + categorie + "\n" +



                          "DurÃ©e: " + heures + "\n";



        return toString;




    }

    
    @Override
    public void appliquerReglesLocalesDeValidation(String[] categoriesReconnues) {
        
        //Validation de la date pour cycle. La date est valide entre 1er
        //avril 2012 et 1er avril 2014. Le reste n'est pas valide.        
        
        Date dateMinConv = new Date();
        Date dateMaxConv = new Date();
        
        try {
            dateMinConv = sdf.parse(DATE_MIN_CYCLE);





            dateMaxConv = sdf.parse(DATE_MAX_CYCLE);
        } catch (ParseException ex) {
        }
        if (date.compareTo(dateMinConv)>=0 && date.compareTo(dateMaxConv)<=0) {
        } else {


            activiteValide = false;
            String errDate = "La date de l'activitÃ© " + description + 
                             " n'est pas reconnue. Elle sera ignorÃ©e.";
            ajouterMessageErreur(errDate);
        }



        
            
        //Le prochain segment vÃ©rifie si la catÃ©gorie est reconnue ou non.


        boolean categorieOk = false;



        for (int i = 0; i < categoriesReconnues.length; i++) {




            if (categorie.equals(categoriesReconnues[i])) {
                categorieOk = true;
            }            
        }
        if (!categorieOk) {


            activiteValide = false;
            String errCat = "L'activitÃ© " + description + " est dans une catÃ©gorie non reconnue. Elle sera ignorÃ©e.";









            ajouterMessageErreur(errCat);
        }
        
        //VÃ©rifie que les heures d'une activitÃ© sont bien supÃ©rieures ou Ã©gales Ã  1.
        if (heures < 1) {
            activiteValide = false;
            String errHeure = "L'activitÃ© " + description + " a un nombre d'heure infÃ©rieur Ã  1. Elle sera donc ignorÃ©e.";
            ajouterMessageErreur(errHeure);
        }
        
    }
}
