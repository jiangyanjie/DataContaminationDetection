package testprojetandroid;

import java.sql.Connection;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import modele.*;
import myconnections.DBConnection;

public class Controler_antho {

    DBConnection dbc = new DBConnection();
    Connection con = dbc.getConnection();
    protected Scanner sc = new Scanner(System.in);
    protected int choix = 0;

    public void Menu() {
        do {
            System.out.println("===================================");
            System.out.println("                Menu");
            System.out.println("===================================");
            System.out.println("1. NoteDB");
            System.out.println("2. CategorieDB");
            System.out.println("3. Quitter");
            System.out.println("Choix :");
            choix = Integer.parseInt(sc.nextLine());
            switch (choix) {
                case 1:
                    MenuNote();
                    break;
                case 2:
                    MenuCategorie();
                    break;
                case 3:
                    System.out.println("Fin du programme.");
                    break;
                default:
                    System.out.println("Choix incorrecte");
            }
        } while (choix != 3);
    }

    /*
     =======================================================
     TEST UNITAIRE POUR LA CLASSE : NoteDB
     =======================================================
     */
    public void MenuNote() {
        do {
            System.out.println("===================================");
            System.out.println("            Menu NoteDB");
            System.out.println("===================================");
            System.out.println("1. Inserer une note");
            System.out.println("2. lire une note");
            System.out.println("3. Modifier une note");
            System.out.println("4. Supprimer une note");
            System.out.println("5. Retour a l'accueil");
            System.out.println("Choix :");
            choix = Integer.parseInt(sc.nextLine());
            switch (choix) {
                case 1:
                    createNote();
                    break;
                case 2:
                    lireNote();
                    break;
                case 3:
                    updateNote();
                    break;
                case 4:
                    deleteNote();
                    break;
                case 5:
                    System.out.println("Retour a l'accueil");
                    break;
                default:
                    System.out.println("Choix incorrecte");
            }
        } while (choix != 5);
    }

    public void createNote() {
        String titreTmp = "";
        String contenuTmp = "";
        //Date date_noteTmp = null;
        int id_carnetTmp = 0;
        int id_categorieTmp = 0;

        System.out.println("titre :");
        titreTmp = sc.nextLine();
        System.out.println("contenu :");
        contenuTmp = sc.nextLine();

        System.out.println("date :");
        String date = sc.nextLine();
        //String date = "2000-11-01";
        java.sql.Date date_noteTmp = java.sql.Date.valueOf(date);

        System.out.println("id_carnet :");
        id_carnetTmp = Integer.parseInt(sc.nextLine());
        System.out.println("id_categorie :");
        id_categorieTmp = Integer.parseInt(sc.nextLine());
        NoteDB.setConnection(con);
        NoteDB note = null;
        note = new NoteDB(titreTmp, contenuTmp, date_noteTmp, id_carnetTmp, id_categorieTmp);
        try {
            note.create();
        } catch (Exception ex) {
            Logger.getLogger(Controler_antho.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (note.getId_note() != -1) {//client trouvé
            System.out.println("Object crée :");
            System.out.println(note.toString());
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(Controler_antho.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void lireNote() {
        int id_noteTmp = -1;
        System.out.println("ID :");
        id_noteTmp = Integer.parseInt(sc.nextLine());
        NoteDB.setConnection(con);
        NoteDB note = null;
        note = new NoteDB(id_noteTmp);
        try {
            note.read();
        } catch (Exception ex) {
            Logger.getLogger(Controler_antho.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (note.getId_note() != -1) {
            System.out.println("Object trouvé :");
            System.out.println(note.toString());
        }
    }

    public void updateNote() {
        int id_noteTmp = -1;
        System.out.println("ID :");
        id_noteTmp = Integer.parseInt(sc.nextLine());
        NoteDB.setConnection(con);
        NoteDB note = null;
        note = new NoteDB(id_noteTmp);
        try {
            note.read();
        } catch (Exception ex) {
            Logger.getLogger(Controler_antho.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (note.getId_note() != -1) {
            System.out.println("Object trouvé :");
            System.out.println(note.toString());
            String titreTmp = "";
            String contenuTmp = "";
            int id_categorieTmp = 0;
            System.out.println("Nouveau titre(" + note.getTitre() + ") :");
            titreTmp = sc.nextLine();
            System.out.println("Nouveau contenu(" + note.getContenu() + ") :");
            contenuTmp = sc.nextLine();

            System.out.println("Nouvelle date(" + note.getDate_note() + ") :");

            String date = sc.nextLine();
            //String date = "2000-11-01";
            java.sql.Date date_noteTmp = java.sql.Date.valueOf(date);

            System.out.println("Nouvelle catégorie(" + note.getId_categorie() + ") :");
            id_categorieTmp = Integer.parseInt(sc.nextLine());
            note.setTitre(titreTmp);
            note.setContenu(contenuTmp);
            note.setDate_note(date_noteTmp);
            note.setId_categorie(id_categorieTmp);
            try {
                note.update();
            } catch (Exception ex) {
                Logger.getLogger(Controler_antho.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(Controler_antho.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void deleteNote() {
        int id_noteTmp = -1;
        System.out.println("ID :");
        id_noteTmp = Integer.parseInt(sc.nextLine());
        NoteDB.setConnection(con);
        NoteDB note = null;
        note = new NoteDB(id_noteTmp);
        try {
            note.read();
        } catch (Exception ex) {
            Logger.getLogger(Controler_antho.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (note.getId_note() != -1) {//client trouvé
            String suppression = "";
            System.out.println("Object trouvé :");
            System.out.println(note.toString());
            System.out.println("Supprimer cet object ?");
            do {
                System.out.println("Choix(oui/non) :");
                suppression = sc.nextLine();
                if (suppression.equals("oui")) {
                    try {
                        note.delete();
                        System.out.println("Suppression réussis");
                    } catch (Exception ex) {
                        Logger.getLogger(Controler_antho.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if (suppression.equals("non")) {
                    System.out.println("Annulation de la suppression");
                }
            } while (!suppression.equals("oui") && !suppression.equals("non"));
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(Controler_antho.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /*
     =======================================================
     TEST UNITAIRE POUR LA CLASSE : CATEGORIEDB
     =======================================================
     */
    public void MenuCategorie() {
        do {
            System.out.println("===================================");
            System.out.println("            Menu CATEGORIEDB");
            System.out.println("===================================");
            System.out.println("1. Ajouter une catégorie");
            System.out.println("2. Lire une catégorie");
            System.out.println("3. Modifier une catégorie");
            System.out.println("4. supprimer une catégorie");
            System.out.println("5. Retour a l'accueil");
            System.out.println("Choix :");
            choix = Integer.parseInt(sc.nextLine());
            switch (choix) {
                case 1:
                    createCategorie();
                    break;
                case 2:
                    lireCategorie();
                    break;
                case 3:
                    updateCategorie();
                    break;
                case 4:
                    deleteCategorie();
                    break;
                case 5:
                    System.out.println("Retour a l'accueil");
                    break;

                default:
                    System.out.println("Choix incorrecte");
            }
        } while (choix != 5);
    }

    private void createCategorie() {
        CategorieDB.setConnection(con);
        CategorieDB cat = null;
        String labelTmp = "";
        String couleurTmp = "";
        System.out.println("Label de la categorie :");
        labelTmp = sc.nextLine();
        System.out.println("Couleur de la categorie :");
        couleurTmp = sc.nextLine();
        cat = new CategorieDB(labelTmp, couleurTmp);
        try {
            cat.create();
        } catch (Exception ex) {
            Logger.getLogger(Controler_antho.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (cat.getId_categorie() != -1) {
            System.out.println("Object trouvé :");
            System.out.println(cat.toString());
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(Controler_antho.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void lireCategorie() {
        int id_categorieTmp = -1;
        System.out.println("ID :");
        id_categorieTmp = Integer.parseInt(sc.nextLine());
        CategorieDB.setConnection(con);
        CategorieDB cat = null;
        cat = new CategorieDB(id_categorieTmp);
        try {
            cat.read();
        } catch (Exception ex) {
            Logger.getLogger(Controler_antho.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (cat.getId_categorie() != -1) {//client trouvé
            System.out.println(cat.toString());
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(Controler_antho.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void updateCategorie() {
        int id_categorieTmp = -1;
        System.out.println("ID :");
        id_categorieTmp = Integer.parseInt(sc.nextLine());
        CategorieDB.setConnection(con);
        CategorieDB cat = null;
        cat = new CategorieDB(id_categorieTmp);
        try {
            cat.read();
        } catch (Exception ex) {
            Logger.getLogger(Controler_antho.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (cat.getId_categorie() != -1) {//client trouvé
            String labelTmp = "";
            String couleurTmp = "";
            System.out.println("Nouveau label(" + cat.getLabel() + "): ");
            labelTmp = sc.nextLine();
            System.out.println("Nouvelle couleur(" + cat.getCouleur() + "): ");
            couleurTmp = sc.nextLine();
            cat.setLabel(labelTmp);
            cat.setCouleur(couleurTmp);
            try {
                cat.update();
            } catch (Exception ex) {
                Logger.getLogger(Controler_antho.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println(cat.toString());
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(Controler_antho.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void deleteCategorie() {
        int id_categorieTmp = -1;
        System.out.println("ID :");
        id_categorieTmp = Integer.parseInt(sc.nextLine());
        CategorieDB.setConnection(con);
        CategorieDB cat = null;
        cat = new CategorieDB(id_categorieTmp);
        try {
            cat.read();
        } catch (Exception ex) {
            Logger.getLogger(Controler_antho.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (cat.getId_categorie() != -1) {//client trouvé
            String suppression = "";
            System.out.println("Object trouvé :");
            System.out.println(cat.toString());
            System.out.println("Supprimer cet object ?");
            do {
                System.out.println("Choix(oui/non) :");
                suppression = sc.nextLine();
                if (suppression.equals("oui")) {
                    try {
                        cat.delete();
                        System.out.println("Suppression réussis");
                    } catch (Exception ex) {
                        Logger.getLogger(Controler.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if (suppression.equals("non")) {
                    System.out.println("Annulation de la suppression");
                }
            } while (!suppression.equals("oui") && !suppression.equals("non"));
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(Controler_antho.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
