



/*


 * To change this template, choose Tools | Templates






 * and open the template in the editor.
 */







package model.applications;

import java.sql.ResultSet;
import java.sql.SQLException;










import java.util.ArrayList;
import java.util.Map;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletResponse;



import javax.servlet.http.HttpSession;


import model.Employee;
import view.XMLGenerator;










/**
 * Aplikacja sÅuÅ¼Äca do definiowania nowych przesylek.
 * 
 * @author Åukasz Fornalczyk
 */
public class DefiniowaniePrzesylek implements model.ApplicationInterface
{



    private WycenaPrzesylek wycenaPrzesylek;
    private ResultSet resultSet;
    private ArrayList<String> formErrorsList;
    private String charactersUsedToGenerateShipmentID;




    private String priorytet;
    


    /**
     * 
     */
    private String imie_nazwisko_nadawcy;

    private String adres_nadawcy;
    private String kod_pocztowy_nadawcy;



    private String nazwa_kraju_nadawcy;
    private String imie_nazwisko_odbiorcy;





    private String adres_odbiorcy;
    private String kod_pocztowy_odbiorcy;
    private String nazwa_kraju_odbiorcy;
    private String list_priorytet;
    private String masa_listu;
    private String paczka_priorytet;
    private String paczka_gabaryt;
    private String masa_paczki;
    private String kwota_przekazu;
    












    /**



     * Tablica zawierajaca dane, ktore sa wyswietlane w podsumowaniu po



     * zdefiniowaniu  i wyslaniu przesylki
     */
    private String[] shipmentSummary = {"ImiÄ i nazwisko nadawcy", "Adres nadawcy",
        "Kod pocztowy nadawcy", "Nazwa kraju nadawcy",



        "ImiÄ i nazwisko odbiorcy", "Adres odbiorcy",







        "Kod pocztowy odbiorcy", "Nazwa kraju odbiorcy"};





    
    /**



     * Obliczona cena przesylki
     */















    private Double shipmentPrice;
    




    /**
     * Flaga decydujaca czy wygenerowac gloowny formularz aplikacji


     */
    private Boolean udane;





    /**



     * Konstruktor bezargumentowy.
     */

    public DefiniowaniePrzesylek()



    {
        this.wycenaPrzesylek = new WycenaPrzesylek();








        this.resultSet = null;
        this.formErrorsList = new ArrayList<String>();
        this.charactersUsedToGenerateShipmentID = "1234567890";
        this.priorytet = null;




        
        this.imie_nazwisko_nadawcy = null;








        this.adres_nadawcy = null;
        this.kod_pocztowy_nadawcy = null;
        this.nazwa_kraju_nadawcy = null;






        






        this.imie_nazwisko_odbiorcy = null;
        this.adres_odbiorcy = null;
        this.kod_pocztowy_odbiorcy = null;
        this.nazwa_kraju_odbiorcy = null;
        
        this.list_priorytet = null;
        this.masa_listu = null;
        

        this.paczka_priorytet = null;
        this.paczka_gabaryt = null;


        this.masa_paczki = null;
        this.kwota_przekazu = null;


        
        this.shipmentPrice = null;
    }




















    /**
     * Metoda pochodzÄca z interfejsu ApplicationInterface.




     *
     * @param employee Pracownik ktÃ³ry chce to wykonaÄ.
     * @return Zwraca tytuÅ aplikacji wyÅwietlany na pasku aplikacji
     */
    @Override


    public String getTitle(Employee employee)
    {


        return "Definiowanie przesyÅek";
    }

    /**

     * @param employee Pracownik ktÃ³ry chce wykonaÄ Å¼ÄdanÄ akcjÄ.











     * @param httpServletResponse UmoÅ¼liwia wygenerowanie odpowiedzi przez
     * servlet.
     * @param xmlGenerator Referencja do XMLGenerator dziÄki, ktÃ³remu generowany





     * jest formularz.












     * @param parameterMap Parametry wprowadzone przez uÅ¼ytkownika w formularz.


     * @param httpSession Sesja uÅ¼ytkownika.
     */




    @Override

    public void printApplication(Employee employee, HttpServletResponse httpServletResponse, XMLGenerator xmlGenerator, Map<String, String[]> parameterMap, HttpSession httpSession)
    {

        udane = false;
        holdEnteredDataInForm(parameterMap);
        String[] rows = { this.imie_nazwisko_nadawcy, this.adres_nadawcy,











            this.kod_pocztowy_nadawcy, this.nazwa_kraju_nadawcy, 
            this.imie_nazwisko_odbiorcy, this.adres_odbiorcy, 
            this.kod_pocztowy_odbiorcy, this.nazwa_kraju_odbiorcy};

        if (parameterMap.get("wyslij_list") != null)
        {
            validateMainForm(parameterMap);
            validateLetterMass(parameterMap);



            if (formErrorsList.isEmpty() == true)
            {


                String generatedShipmentID = generateShipmentID();
                resultSet = model.ConnectionSingleton.executeQuery("select count(*) "









                        + "from przesylki where idPrzesylki = " + generatedShipmentID);





                while (checkGeneratedShipmentID() == true)
                {
                    generatedShipmentID = generateShipmentID();
                    resultSet = model.ConnectionSingleton.executeQuery("select count(*) "
                            + "from przesylki where idPrzesylki = " + generatedShipmentID);
                }



                changeShipmentPriority(parameterMap, "list_priorytet");












                model.ConnectionSingleton.executeUpdate("insert into przesylki values (" + generatedShipmentID + ", "
                        + "'" + parameterMap.get("imie_nazwisko_nadawcy")[0] + "', "
                        + parameterMap.get("nazwa_kraju_nadawcy")[0] + ", "
                        + "'" + parameterMap.get("kod_pocztowy_nadawcy")[0] + "', "




                        + "'" + parameterMap.get("adres_nadawcy")[0] + "', "
                        + "'" + parameterMap.get("imie_nazwisko_odbiorcy")[0] + "', "
                        + parameterMap.get("nazwa_kraju_odbiorcy")[0] + ", "
                        + "'" + parameterMap.get("kod_pocztowy_odbiorcy")[0] + "', "

                        + "'" + parameterMap.get("adres_odbiorcy")[0] + "', "



                        + "NOW())");
                model.ConnectionSingleton.executeUpdate("insert into listy values(" + generatedShipmentID + ", "



















                        + priorytet + ", " + parameterMap.get("masa_listu")[0] + ")");




                model.ConnectionSingleton.executeUpdate("insert into historiaPrzesylek "

                + "values (null, " + generatedShipmentID
                + " , 1, 1, NOW(), '');");
                













                generateShipmentSummary(parameterMap, xmlGenerator, shipmentSummary, rows, generatedShipmentID, "list");

            }
            else
            {








                printFormErrors(xmlGenerator);
            }





        }

        else if (parameterMap.get("wyslij_paczke") != null)
        {


            validateMainForm(parameterMap);
            validatePackageMass(parameterMap);




            if (formErrorsList.isEmpty() == true)
            {
                String generatedShipmentID = generateShipmentID();
                resultSet = model.ConnectionSingleton.executeQuery("select count(*) "





                        + "from przesylki where idPrzesylki = " + generatedShipmentID);





                while (checkGeneratedShipmentID() == true)
                {



                    generatedShipmentID = generateShipmentID();
                    resultSet = model.ConnectionSingleton.executeQuery("select count(*) "



                            + "from przesylki where idPrzesylki = " + generatedShipmentID);


                }





                changeShipmentPriority(parameterMap, "paczka_priorytet");
























                model.ConnectionSingleton.executeUpdate("insert into przesylki values (" + generatedShipmentID + ", "
                        + "'" + parameterMap.get("imie_nazwisko_nadawcy")[0] + "', "


                        + parameterMap.get("nazwa_kraju_nadawcy")[0] + ", "
                        + "'" + parameterMap.get("kod_pocztowy_nadawcy")[0] + "', "
                        + "'" + parameterMap.get("adres_nadawcy")[0] + "', "
                        + "'" + parameterMap.get("imie_nazwisko_odbiorcy")[0] + "', "
                        + parameterMap.get("nazwa_kraju_odbiorcy")[0] + ", "
                        + "'" + parameterMap.get("kod_pocztowy_odbiorcy")[0] + "', "
                        + "'" + parameterMap.get("adres_odbiorcy")[0] + "', "
                        + "NOW())");
                model.ConnectionSingleton.executeUpdate("insert into paczki values(" + generatedShipmentID + ", "

                        + priorytet + ", " + parameterMap.get("masa_paczki")[0] + ", "


                        + parameterMap.get("paczka_gabaryt")[0] + ")");
                
                model.ConnectionSingleton.executeUpdate("insert into historiaPrzesylek "
                + "values (null, " + generatedShipmentID
                + " , 1, 1, NOW(), '');");










                generateShipmentSummary(parameterMap, xmlGenerator, shipmentSummary, rows, generatedShipmentID, "paczka");
            }
            else



            {
                printFormErrors(xmlGenerator);








            }


        }
        else if (parameterMap.get("wyslij_przekaz") != null)







        {
            validateMainForm(parameterMap);
            validateOrderAmount(parameterMap);
            if (formErrorsList.isEmpty() == true)



            {
                String generatedShipmentID = generateShipmentID();



                resultSet = model.ConnectionSingleton.executeQuery("select count(*) "


                        + "from przesylki where idPrzesylki = " + generatedShipmentID);





















                while (checkGeneratedShipmentID() == true)
                {


                    generatedShipmentID = generateShipmentID();
                    resultSet = model.ConnectionSingleton.executeQuery("select count(*) "


                            + "from przesylki where idPrzesylki = " + generatedShipmentID);
                }



                model.ConnectionSingleton.executeUpdate("insert into przesylki values (" + generatedShipmentID + ", "
                        + "'" + parameterMap.get("imie_nazwisko_nadawcy")[0] + "', "
                        + parameterMap.get("nazwa_kraju_nadawcy")[0] + ", "
                        + "'" + parameterMap.get("kod_pocztowy_nadawcy")[0] + "', "





                        + "'" + parameterMap.get("adres_nadawcy")[0] + "', "
                        + "'" + parameterMap.get("imie_nazwisko_odbiorcy")[0] + "', "





                        + parameterMap.get("nazwa_kraju_odbiorcy")[0] + ", "
                        + "'" + parameterMap.get("kod_pocztowy_odbiorcy")[0] + "', "
                        + "'" + parameterMap.get("adres_odbiorcy")[0] + "', "
                        + "NOW())");




                model.ConnectionSingleton.executeUpdate("insert into przekazy values(" + generatedShipmentID + ", "
                        + parameterMap.get("kwota_przekazu")[0] + ")");









                


                model.ConnectionSingleton.executeUpdate("insert into historiaPrzesylek "
                + "values (null, " + generatedShipmentID




                + " , 1, 1, NOW(), '');");






                generateShipmentSummary(parameterMap, xmlGenerator, shipmentSummary, rows, generatedShipmentID, "przekaz");
            }


            else
            {
                printFormErrors(xmlGenerator);
            }
        }







        if (!udane)
        {


            generateForm(xmlGenerator);
        }




    }

    /**




     * Tworzy formularz odpowiedzialny za definiowanie przesyÅek.
     *
     * @param xmlGenerator Referencja do XMLGenerator dziÄki, ktÃ³remu generowany


     * jest formularz.


     */
    private void generateForm(XMLGenerator xmlGenerator)



    {
        xmlGenerator.printStartTag("script",


                "type", "text/javascript",
                "src", "../js/pages/formularz.js");







        xmlGenerator.printEndTag();





        xmlGenerator.printStartTag("form", "action", "", "method", "POST", "id", "form1");



        xmlGenerator.printStartTag("div", "id", /*"tab tabActive"*/ "mainForm");

        xmlGenerator.printStartTag("label", "id", "nadawca_imie_nazwisko");
        xmlGenerator.println("ImiÄ i nazwisko nadawcy (np. Jan Kowalski):");



        xmlGenerator.printEndTag();
        xmlGenerator.printEmptyElement("input", "name", "imie_nazwisko_nadawcy",






                "id", "imie_nazwisko_nadawcy",
                "type", "text",
                "value", imie_nazwisko_nadawcy);


        xmlGenerator.printEmptyElement("br");






        xmlGenerator.printStartTag("label", "id", "nadawca_adres");










        xmlGenerator.println("Adres nadawcy - ulica oraz miasto (np. Lewa 5a Szczecin):");
        xmlGenerator.printEndTag();



        xmlGenerator.printEmptyElement("input", "name", "adres_nadawcy",
                "id", "adres_nadawcy",
                "type", "text",





                "value", adres_nadawcy);
        xmlGenerator.printEmptyElement("br");

        xmlGenerator.printStartTag("label", "id", "nadawca_kod_pocztowy");
        xmlGenerator.println("Kod pocztowy nadawcy:");
        xmlGenerator.printEndTag();
        xmlGenerator.printEmptyElement("input", "name", "kod_pocztowy_nadawcy",
                "id", "kod_pocztowy_nadawcy",
                "type", "text",


                "value", kod_pocztowy_nadawcy);
        xmlGenerator.printEmptyElement("br");








        xmlGenerator.printStartTag("label", "id", "nadawca_nazwa_kraju");
        xmlGenerator.println("Nazwa kraju nadawcy:");






        xmlGenerator.printEndTag();
        resultSet = model.ConnectionSingleton.executeQuery("select idKraju, kraj from kraje");






        printResultSetContent(xmlGenerator, "nazwa_kraju_nadawcy");




        xmlGenerator.printEmptyElement("br");
        xmlGenerator.printEmptyElement("br");











        xmlGenerator.printStartTag("label", "id", "odbiorca_imie_nazwisko");
        xmlGenerator.println("ImiÄ i nazwisko odbiorcy (np. Jan Kowalski):");
        xmlGenerator.printEndTag();
        xmlGenerator.printEmptyElement("input", "name", "imie_nazwisko_odbiorcy",
                "id", "imie_nazwisko_odbiorcy",
                "type", "text",




                "value", imie_nazwisko_odbiorcy);





        xmlGenerator.printEmptyElement("br");



        xmlGenerator.printStartTag("label", "id", "odbiorca_adres");
        xmlGenerator.println("Adres odbiorcy - ulica oraz miasto (np. Lewa 5a Szczecin):");
        xmlGenerator.printEndTag();




        xmlGenerator.printEmptyElement("input", "name", "adres_odbiorcy",
                "id", "adres_odbiorcy",
                "type", "text",

                "value", adres_odbiorcy);


        xmlGenerator.printEmptyElement("br");








        xmlGenerator.printStartTag("label", "id", "odbiorca_kod_pocztowy");
        xmlGenerator.println("Kod pocztowy odbiorcy:");









        xmlGenerator.printEndTag();





        xmlGenerator.printEmptyElement("input", "name", "kod_pocztowy_odbiorcy",



                "id", "kod_pocztowy_odbiorcy",
                "type", "text",
                "value", kod_pocztowy_odbiorcy);
        xmlGenerator.printEmptyElement("br");



        xmlGenerator.printStartTag("label", "id", "odbiorca_nazwa_kraju");







        xmlGenerator.println("Nazwa kraju odbiorcy:");
        xmlGenerator.printEndTag();









        resultSet = model.ConnectionSingleton.executeQuery("select idKraju, kraj from kraje");
        printResultSetContent(xmlGenerator, "nazwa_kraju_odbiorcy");
        xmlGenerator.printEmptyElement("br");













        xmlGenerator.printStartTag("label", "id", "przesylki_typ");
        xmlGenerator.println("Typ przesyÅki:");










        xmlGenerator.printEndTag();










        resultSet = model.ConnectionSingleton.executeQuery("select idTypu, typ from typyPrzesylek");
        printResultSetContent(xmlGenerator, "typ_przesylki");
        xmlGenerator.printEmptyElement("br");
        xmlGenerator.printEmptyElement("br");

        xmlGenerator.printEmptyElement("input", "name", "dalej >>", "type", "submit", "value", "Dalej >>", "class", "tabEvaluator");
        xmlGenerator.printEndTag();
        xmlGenerator.printEmptyElement("br");





        xmlGenerator.printStartTag("div", "class", "tab tabList");
        xmlGenerator.printStartTag("label", "id", "priorytet_list");
        xmlGenerator.println("List priorytetowy:");
        xmlGenerator.printEndTag();


        xmlGenerator.printStartTag("select", "name", "list_priorytet");


        xmlGenerator.printStartTag("option", "value", "Tak");
        xmlGenerator.println("Tak");

        xmlGenerator.printEndTag();
        xmlGenerator.printStartTag("option", "value", "Nie");




        xmlGenerator.println("Nie");



        xmlGenerator.printEndTag();
        xmlGenerator.printEndTag();
        xmlGenerator.printEmptyElement("br");

        xmlGenerator.printStartTag("label", "id", "list_masa");
        xmlGenerator.println("Masa listu:");
        xmlGenerator.printEndTag();




        xmlGenerator.printEmptyElement("input", "name", "masa_listu",
                "id", "masa_listu",







                "type", "text",
                "value", masa_listu);








        xmlGenerator.printEmptyElement("br");
        xmlGenerator.printEmptyElement("input", "type", "submit", "name", "wyslij_list", "value", "WyÅlij list");
        xmlGenerator.printEmptyElement("br");
        xmlGenerator.printEndTag(); // div class="tab tabList"










        xmlGenerator.printStartTag("div", "class", "tab tabPaczka");


        xmlGenerator.printStartTag("label", "id", "priorytet_paczka");
        xmlGenerator.println("Paczka priorytetowa:");
        xmlGenerator.printEndTag();
        xmlGenerator.printStartTag("select", "name", "paczka_priorytet");
        xmlGenerator.printStartTag("option", "value", "Tak");


        xmlGenerator.println("Tak");



        xmlGenerator.printEndTag();
        xmlGenerator.printStartTag("option", "value", "Nie");
        xmlGenerator.println("Nie");
        xmlGenerator.printEndTag();
        xmlGenerator.printEndTag();
        xmlGenerator.printEmptyElement("br");













        xmlGenerator.printStartTag("label", "id", "gabaryt_paczka");





        xmlGenerator.println("Gabaryt paczki:");
        xmlGenerator.printEndTag();



        resultSet = model.ConnectionSingleton.executeQuery("select idGabarytu, opisGabarytu from gabaryty");
        printResultSetContent(xmlGenerator, "paczka_gabaryt");
        xmlGenerator.printEmptyElement("br");





        xmlGenerator.printStartTag("label", "id", "masa_paczka");




        xmlGenerator.println("Masa paczki:");




        xmlGenerator.printEndTag();




        xmlGenerator.printEmptyElement("input", "name", "masa_paczki",






                "id", "masa_paczki",


                "type", "text",
                "value", masa_paczki);
        xmlGenerator.printEmptyElement("br");
        xmlGenerator.printEmptyElement("input", "name", "wyslij_paczke", "type", "submit", "value", "WyÅlij paczkÄ");





        xmlGenerator.printEmptyElement("br");

        xmlGenerator.printEndTag();     //div class="tab tabPaczka"

        xmlGenerator.printStartTag("div", "class", "tab tabPrzekaz");
        xmlGenerator.printStartTag("label", "id", "przekaz_kwota");
        xmlGenerator.println("Kwota przekazu:");
        xmlGenerator.printEndTag();
        xmlGenerator.printEmptyElement("input", "name", "kwota_przekazu",
                "id", "kwota_przekazu",

                "type", "text",



                "value", kwota_przekazu);
        xmlGenerator.printEmptyElement("br");
        xmlGenerator.printEmptyElement("input", "name", "wyslij_przekaz", "type", "submit", "value", "WyÅlij przekaz");




        xmlGenerator.printEmptyElement("br");
        xmlGenerator.printEndTag();     //div class="tab tabPrzekaz"





        xmlGenerator.printEndTag();     // form







    }




    /**









     * Metoda "podtrzumuje" dane w formularzu, gdy po jego wyslaniu wystapia bledy
     * zwiazane z walidacja wprowadzonych danych do formularza.
     * 



     * @param parameterMap Parametry wprowadzone przez uÅ¼ytkownika w formularz.
     */





    private void holdEnteredDataInForm(Map<String, String[]> parameterMap)
    {
        if (parameterMap.get("imie_nazwisko_nadawcy") != null)
        {
            imie_nazwisko_nadawcy = parameterMap.get("imie_nazwisko_nadawcy")[0];
        }
        else



        {








            imie_nazwisko_nadawcy = "";
        }







        if (parameterMap.get("adres_nadawcy") != null)
        {




            adres_nadawcy = parameterMap.get("adres_nadawcy")[0];



        }








        else



        {
            adres_nadawcy = "";









        }

        if (parameterMap.get("kod_pocztowy_nadawcy") != null)










        {




            kod_pocztowy_nadawcy = parameterMap.get("kod_pocztowy_nadawcy")[0];



        }


        else




        {







            kod_pocztowy_nadawcy = "";
        }





        


        if (parameterMap.get("nazwa_kraju_nadawcy") != null)




        {
            nazwa_kraju_nadawcy = parameterMap.get("nazwa_kraju_nadawcy")[0];
            resultSet = model.ConnectionSingleton.executeQuery("select kraj "
                        + "from kraje where idKraju = " + nazwa_kraju_nadawcy);
            try
            {
                resultSet.next();




















                nazwa_kraju_nadawcy = (String)resultSet.getObject(1);
            }











            catch (SQLException ex)
            {






                Logger.getLogger(DefiniowaniePrzesylek.class.getName()).log(Level.SEVERE, null, ex);
            }
        }








        if (parameterMap.get("imie_nazwisko_odbiorcy") != null)
        {






            imie_nazwisko_odbiorcy = parameterMap.get("imie_nazwisko_odbiorcy")[0];

        }



        else






        {



            imie_nazwisko_odbiorcy = "";

        }




        if (parameterMap.get("adres_odbiorcy") != null)
        {
            adres_odbiorcy = parameterMap.get("adres_odbiorcy")[0];
        }
        else
        {
            adres_odbiorcy = "";
        }







        if (parameterMap.get("kod_pocztowy_odbiorcy") != null)
        {

            kod_pocztowy_odbiorcy = parameterMap.get("kod_pocztowy_odbiorcy")[0];
        }
        else
        {

            kod_pocztowy_odbiorcy = "";



        }



        if (parameterMap.get("kod_pocztowy_odbiorcy") != null)

        {
            kod_pocztowy_odbiorcy = parameterMap.get("kod_pocztowy_odbiorcy")[0];
        }
        else
        {




            kod_pocztowy_odbiorcy = "";





        }
        
        if (parameterMap.get("nazwa_kraju_odbiorcy") != null)




        {
            nazwa_kraju_odbiorcy = parameterMap.get("nazwa_kraju_odbiorcy")[0];
            resultSet = model.ConnectionSingleton.executeQuery("select kraj "







                        + "from kraje where idKraju = " + nazwa_kraju_odbiorcy);


            try








            {
                resultSet.next();
                nazwa_kraju_odbiorcy = (String)resultSet.getObject(1);
            }
            catch (SQLException ex)


            {
                Logger.getLogger(DefiniowaniePrzesylek.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
		




        if (parameterMap.get("list_priorytet") != null)
        {
                list_priorytet = parameterMap.get("list_priorytet")[0];
        }

        if (parameterMap.get("masa_listu") != null)
        {
            masa_listu = parameterMap.get("masa_listu")[0];






        }




        else


        {


            masa_listu = "";
        }






		
        if (parameterMap.get("paczka_priorytet") != null)


        {
                paczka_priorytet = parameterMap.get("paczka_priorytet")[0];
        }















        if (parameterMap.get("paczka_gabaryt") != null)
        {





                paczka_gabaryt = parameterMap.get("paczka_gabaryt")[0];
                resultSet = model.ConnectionSingleton.executeQuery("select opisGabarytu "
                        + "from gabaryty where idGabarytu = " + paczka_gabaryt);
            try
            {
                resultSet.next();
                paczka_gabaryt = (String)resultSet.getObject(1);


            }
            catch (SQLException ex)
            {
                Logger.getLogger(DefiniowaniePrzesylek.class.getName()).log(Level.SEVERE, null, ex);













            }







        }




        if (parameterMap.get("masa_paczki") != null)
        {
            masa_paczki = parameterMap.get("masa_paczki")[0];



        }
        else





        {
            masa_paczki = "";

        }




        if (parameterMap.get("kwota_przekazu") != null)

        {




            kwota_przekazu = parameterMap.get("kwota_przekazu")[0];
        }
        else
        {
            kwota_przekazu = "";
        }
    }






    /**
     * Drukuje w liÅcie rozwijanej zawartoÅÄ ResultSet'u. Metoda drukuje
     * zawartoÅÄ ResultSet'u, ktÃ³ry zostaÅ wczeÅniej pobrany z bazy danych.
     *
     * @param xmlGenerator Referencja do XMLGenerator dziÄki, ktÃ³remu generowane
     * sÄ odpowiedzi dla klienta.


     * @param fieldName Nazwa listy rozwijanej
     */





    private void printResultSetContent(XMLGenerator xmlGenerator, String fieldName)


    {


        xmlGenerator.printStartTag("select", "name", fieldName);










        try
        {
            while (resultSet.next() == true)
            {


                xmlGenerator.printStartTag("option", "value", String.valueOf(resultSet.getObject(1)));
                xmlGenerator.println((String) resultSet.getObject(2));
                xmlGenerator.printEndTag(); // zakonczenie znacznika option
            }
        }
        catch (SQLException ex)
        {
            Logger.getLogger(DefiniowaniePrzesylek.class.getName()).log(Level.SEVERE, null, ex);
        }





















        xmlGenerator.printEndTag(); // zakonczenie znacznika select
        try


        {
            resultSet.close();

        }
        catch (SQLException ex)
        {
            Logger.getLogger(DefiniowaniePrzesylek.class.getName()).log(Level.SEVERE, null, ex);
        }
    }









    /**
     * Genereuje ID przesyÅki. Metoda generuje ID przesyÅki w oparciu o prywatne
     * pole klasy charactersUsedToGenerateShipmentID.
     *










     * @return Wygenerowane ID przesyÅki w postaci String'a.
     */
    private String generateShipmentID()
    {
        Random random = new Random();
        int length = this.charactersUsedToGenerateShipmentID.length();
        char text[] = new char[length];








        for (int i = 0; i < length; i++)
        {
            if (i == 0)
            {



                text[i] = this.charactersUsedToGenerateShipmentID.charAt(random.nextInt(length - 1));
            }








            else

            {
                text[i] = this.charactersUsedToGenerateShipmentID.charAt(random.nextInt(length));
            }

        }





        return new String(text);
    }

    /**
     * Sprawdza czy wygenerowane ID przesyÅki juÅ¼ istnieje w bazie danych.
     * Spradwzenie czy w bazie istnieje rekord o wygenerowanym ID polega na
     * wykonaniu select count(*) z tabeli przesylki gdzie idPrzesylki rÃ³wna siÄ
     * wygenerowanemu ID. NastÄpnie sprawdzana jest liczba wierszy. Gdy jest ona



     * > 0 zwracane jest true. W przeciwnym wypadku false.
     *




     * @return true, gdy w bazie istnieje rekord o wygenerowanym ID. false, gdy
     * w bazie nie istnieje rekord o wygenerowanym ID.



     */
    private boolean checkGeneratedShipmentID()
    {
        int rowCount = 0;
        try
        {
            resultSet.next();

            rowCount = resultSet.getInt(1);




        }



        catch (SQLException ex)
        {
            Logger.getLogger(DefiniowaniePrzesylek.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (rowCount > 0)



        {

            return true;
        }
        return false;
    }






    /**
     * Zmienia priorytet przesyÅki z Tak / Nie na 1 lub 0.



     *





     * @param parameterMap Parametry wprowadzone przez uÅ¼ytkownika w formularz.
     * @param formFieldName Nazwa pola w formularzu, ktÃ³rego wartoÅÄ zostaje
     * wyciÄgniÄta z parameterMap.
     */
    private void changeShipmentPriority(Map<String, String[]> parameterMap, String formFieldName)


    {
        if (parameterMap.get(formFieldName)[0].equals("Tak"))


        {
            priorytet = "1";
        }
        else
        {





            priorytet = "0";





        }
    }

    /**






     * Walidacja gÅÃ³wnego formularza. Metoda sprawdza poprawnoÅÄ wprowadzonych w
     * pola formularza danych dotyczÄcych miÄdzy innymi adresu nadawcy,
     * odbiorcy, kodu pocztowego itp. Walidacja kaÅ¼dego z pÃ³l odbywa siÄ za
     * pomocÄ wyraÅ¼enia regularnego. W przypadku bÅÄdnie wprowadzonych danych,
     * bÅÄd z danego pola zostaje zapisany do listy bÅÄdÃ³w.
     *
     * @param parameterMap Parametry przekazane przez uÅ¼ytkownika poprzez
     * formularz.
     */
    private void validateMainForm(Map<String, String[]> parameterMap)
    {
        String senderNameSurname = parameterMap.get("imie_nazwisko_nadawcy")[0];
        String senderAddress = parameterMap.get("adres_nadawcy")[0];
        String senderPostCode = parameterMap.get("kod_pocztowy_nadawcy")[0];


        String recipientNameSurname = parameterMap.get("imie_nazwisko_odbiorcy")[0];
        String recipientAddress = parameterMap.get("adres_odbiorcy")[0];
        String recipientPostCode = parameterMap.get("kod_pocztowy_odbiorcy")[0];

        if (!senderNameSurname.matches("([A-ZÅ»Å¹ÄÅÃÅÄÄÅ]{1}[a-zÅ¼ÅºÄÅÃ³ÅÄÄÅ]{2,30}[ ][A-ZÅ»Å¹ÄÅÃÅÄÄÅ]{1}[a-zÅ¼ÅºÄÅÃ³ÅÄÄÅ]{2,30}|[a-zÅ¼ÅºÄÅÃ³ÅÄÄÅ]{2,30}[ ][a-zÅ¼ÅºÄÅÃ³ÅÄÄÅ]{2,30})"))
        {
            if (!senderNameSurname.matches("([A-ZÅ»Å¹ÄÅÃÅÄÄÅ]{1}[a-zÅ¼ÅºÄÅÃ³ÅÄÄÅ]{2,30}[ ][A-ZÅ»Å¹ÄÅÃÅÄÄÅ]{1}[a-zÅ¼ÅºÄÅÃ³ÅÄÄÅ]{2,30}[-][A-ZÅ»Å¹ÄÅÃÅÄÄÅ]{1}[a-zÅ¼ÅºÄÅÃ³ÅÄÄÅ]{2,15}|[A-ZÅ»Å¹ÄÅÃÅÄÄÅ]{1}[a-zÅ¼ÅºÄÅÃ³ÅÄÄÅ]{2,29}[ ][A-ZÅ»Å¹ÄÅÃÅÄÄÅ]{1}[a-zÅ¼ÅºÄÅÃ³ÅÄÄÅ]{2,29}[ ][-][ ][A-ZÅ»Å¹ÄÅÃÅÄÄÅ]{1}[a-zÅ¼ÅºÄÅÃ³ÅÄÄÅ]{2,15})"))
            {
                if (!senderNameSurname.matches("([a-zÅ¼ÅºÄÅÃ³ÅÄÄÅ]{2,30}[ ][a-zÅ¼ÅºÄÅÃ³ÅÄÄÅ]{2,30}[-][a-zÅ¼ÅºÄÅÃ³ÅÄÄÅ]{2,18}|[a-zÅ¼ÅºÄÅÃ³ÅÄÄÅ]{2,30}[ ][a-zÅ¼ÅºÄÅÃ³ÅÄÄÅ]{2,30}[ ][-][ ][a-zÅ¼ÅºÄÅÃ³ÅÄÄÅ]{2,16})"))
                {

                    formErrorsList.add("ProszÄ podaÄ poprawne imiÄ i nazwisko nadawcy");
                }
            }
        }

        if (!senderAddress.matches("[1-9a-z]{0,1}[ A-ZÅ»Å¹ÄÅÃÅÄÄÅa-zÅ¼ÅºÄÅÃ³ÅÄÄÅ]{2,34}[ ]([0-9]{1,3}[/][0-9]{1,3}|[0-9]{1,3}[a-z]{0,1})[ ][A-ZÅ»Å¹ÄÅÃÅÄÄÅa-zÅ¼ÅºÄÅÃ³ÅÄÄÅ ]{2,34}"))
        {
            formErrorsList.add("ProszÄ podaÄ poprawny adres nadawcy");
        }

        if (!senderPostCode.matches("\\d{2}[-]{0,1}\\d{3}"))
        {
            formErrorsList.add("ProszÄ podaÄ poprawny kod pocztowy nadawcy");
        }

        if (!recipientNameSurname.matches("([A-ZÅ»Å¹ÄÅÃÅÄÄÅ]{1}[a-zÅ¼ÅºÄÅÃ³ÅÄÄÅ]{2,30}[ ][A-ZÅ»Å¹ÄÅÃÅÄÄÅ]{1}[a-zÅ¼ÅºÄÅÃ³ÅÄÄÅ]{2,30}|[a-zÅ¼ÅºÄÅÃ³ÅÄÄÅ]{2,30}[ ][a-zÅ¼ÅºÄÅÃ³ÅÄÄÅ]{2,30})"))
        {



            if (!recipientNameSurname.matches("([A-ZÅ»Å¹ÄÅÃÅÄÄÅ]{1}[a-zÅ¼ÅºÄÅÃ³ÅÄÄÅ]{2,30}[ ][A-ZÅ»Å¹ÄÅÃÅÄÄÅ]{1}[a-zÅ¼ÅºÄÅÃ³ÅÄÄÅ]{2,30}[-][A-ZÅ»Å¹ÄÅÃÅÄÄÅ]{1}[a-zÅ¼ÅºÄÅÃ³ÅÄÄÅ]{2,15}|[A-ZÅ»Å¹ÄÅÃÅÄÄÅ]{1}[a-zÅ¼ÅºÄÅÃ³ÅÄÄÅ]{2,29}[ ][A-ZÅ»Å¹ÄÅÃÅÄÄÅ]{1}[a-zÅ¼ÅºÄÅÃ³ÅÄÄÅ]{2,29}[ ][-][ ][A-ZÅ»Å¹ÄÅÃÅÄÄÅ]{1}[a-zÅ¼ÅºÄÅÃ³ÅÄÄÅ]{2,15})"))
            {
                if (!recipientNameSurname.matches("([a-zÅ¼ÅºÄÅÃ³ÅÄÄÅ]{2,30}[ ][a-zÅ¼ÅºÄÅÃ³ÅÄÄÅ]{2,30}[-][a-zÅ¼ÅºÄÅÃ³ÅÄÄÅ]{2,18}|[a-zÅ¼ÅºÄÅÃ³ÅÄÄÅ]{2,30}[ ][a-zÅ¼ÅºÄÅÃ³ÅÄÄÅ]{2,30}[ ][-][ ][a-zÅ¼ÅºÄÅÃ³ÅÄÄÅ]{2,16})"))


                {
                    formErrorsList.add("ProszÄ podaÄ poprawne imiÄ i nazwisko odbiorcy");
                }
            }


        }

        if (!recipientAddress.matches("[1-9a-z]{0,1}[ A-ZÅ»Å¹ÄÅÃÅÄÄÅa-zÅ¼ÅºÄÅÃ³ÅÄÄÅ]{2,34}[ ]([0-9]{1,3}[/][0-9]{1,3}|[0-9]{1,3}[a-z]{0,1})[ ][A-ZÅ»Å¹ÄÅÃÅÄÄÅa-zÅ¼ÅºÄÅÃ³ÅÄÄÅ ]{2,34}"))
        {
            formErrorsList.add("ProszÄ podaÄ poprawny adres odbiorcy");
        }

        if (!recipientPostCode.matches("\\d{2}[-]{0,1}\\d{3}"))
        {
            formErrorsList.add("ProszÄ podaÄ poprawny kod pocztowy odbiorcy");
        }
    }

    /**
     * Walidacja pola z masÄ listu. Metoda sprawdza poprawnoÅÄ wprowadzonej w
     * pole formularza masy listu. Walidacja odbywa siÄ za pomocÄ wyraÅ¼enia
     * regularnego. W przypadku bÅÄdnej masy, bÅÄd ten zostaje zapisany do listy
     * bÅÄdÃ³w.
     *




     * @param parameterMap Parametry przekazane przez uÅ¼ytkownika poprzez
     * formularz.
     */
    private void validateLetterMass(Map<String, String[]> parameterMap)








    {
        String letterMass = parameterMap.get("masa_listu")[0];


        if (!letterMass.matches("[0-9]+"))
        {
            formErrorsList.add("ProszÄ podaÄ poprawnÄ masÄ listu");
        }
    }







    /**
     * Walidacja pola z masÄ paczki. Metoda sprawdza poprawnoÅÄ wprowadzonej w
     * pole formularza masy paczki. Walidacja odbywa siÄ za pomocÄ wyraÅ¼enia
     * regularnego. W przypadku bÅÄdnej masy, bÅÄd ten zostaje zapisany do listy
     * bÅÄdÃ³w.



     *
     * @param parameterMap Parametry przekazane przez uÅ¼ytkownika poprzez
     * formularz.


     */
    private void validatePackageMass(Map<String, String[]> parameterMap)



    {





        String packageMass = parameterMap.get("masa_paczki")[0];
        if (!packageMass.matches("[0-9]+"))
        {
            formErrorsList.add("ProszÄ podaÄ poprawnÄ masÄ paczki");
        }
    }

    /**
     * Walidacja pola z kwotÄ przekazu. Metoda sprawdza poprawnoÅÄ wprowadzonej
     * w pole formularza kwoty przekazu. Walidacja odbywa siÄ za pomocÄ
     * wyraÅ¼enia regularnego. W przypadku bÅÄdnej kwoty, bÅÄd ten zostaje
     * zapisany do listy bÅÄdÃ³w.


     *
     * @param parameterMap Parametry przekazane przez uÅ¼ytkownika poprzez
     * formularz.





     */
    private void validateOrderAmount(Map<String, String[]> parameterMap)
    {
        String orderAmount = parameterMap.get("kwota_przekazu")[0];
        if (!orderAmount.matches("([0-9]{1,10}|[0-9]{1,10}[.]{1}[0-9]{2})"))
        {



            formErrorsList.add("ProszÄ podaÄ poprawnÄ kwotÄ przekazu");




        }
    }

    /**



     * WyÅwietla na ekranie bÅÄdy, ktÃ³re pojawiÅy sie w formularzu. Metoda
     * wyÅwietla na ekranie bÅÄdy, ktÃ³re zostaÅy spowodowane bÅÄdnym
     * wypeÅnieniem pÃ³l formularza. Po ich wyÅwietleniu lista zawierajaca bÅÄdy
     * zostaje wyczyszczona. BÅÄdy przechowywane sÄ w formErrorsList (lista
     * StringÃ³w).
     *
     * @param xmlGenerator Referencja do XMLGenerator dziÄki, ktÃ³remu generowane
     * sÄ odpowiedzi dla klienta.
     */


    private void printFormErrors(XMLGenerator xmlGenerator)
    {
        for (String i : this.formErrorsList)
        {
            xmlGenerator.printStartTag("font", "color", "red");
            xmlGenerator.printStartTag("b", "");
            xmlGenerator.println("* " + i + " <br>");
            xmlGenerator.printEndTag();
            xmlGenerator.printEndTag();

        }
        this.formErrorsList.clear();
    }

    /**
     * metoda generuje podsumowanie widoczne po zdefiniowaniu i wyslaniu przesylki.
     * 
     * @param parameterMap Parametry przekazane przez uÅ¼ytkownika poprzez
     * formularz.
     * @param xmlGenerator Referencja do XMLGenerator dziÄki, ktÃ³remu generowane
     * sÄ odpowiedzi dla klienta.
     * @param rowsDescription Tablica zawierajaca dane, ktore sa wyswietlane w podsumowaniu po
     * zdefiniowaniu  i wyslaniu przesylki np. Imie i nazwisko nadawcy:
     * @param rows Tablica zawierajaca dane wprowadzone w formularz
     * @param generatedShipmentID Wyegenerowany numer przesylki


     * @param packageType Okresla typ przesylki
     */
    private void generateShipmentSummary(Map<String, String[]> parameterMap, XMLGenerator xmlGenerator, String[] rowsDescription, String[] rows, String generatedShipmentID, String packageType)
    {
        ShipmentPrice(parameterMap);
        xmlGenerator.printStartTag("form", "action", "", "method", "POST");
        xmlGenerator.printStartTag("table", "");

        for (int i = 0; i < rows.length; i++)
        {
            xmlGenerator.printStartTag("tr", "");
            xmlGenerator.printStartTag("td", "");
            xmlGenerator.printStartTag("font", "color", "black", "size", "5");
            xmlGenerator.printStartTag("b", "");
            xmlGenerator.println(rowsDescription[i] + ": " + rows[i]);
            xmlGenerator.printEndTag();

            xmlGenerator.printEndTag();
            xmlGenerator.printEndTag();
            xmlGenerator.printEndTag();
        }

        xmlGenerator.printEndTag();
        xmlGenerator.printStartTag("font", "color", "black", "size", "5");
        xmlGenerator.printStartTag("b", "");
		
        if (packageType.equals("list"))
        {
                xmlGenerator.println("List priorytetowy: " + this.list_priorytet);
                xmlGenerator.println("<br>");
                xmlGenerator.println("Masa listu: " + this.masa_listu);
                xmlGenerator.println("<br>");
                xmlGenerator.println("Koszt przesylki: " + this.shipmentPrice);
                xmlGenerator.println("<br>");
        }
        else if (packageType.equals("paczka"))
        {
                xmlGenerator.println("Paczka priorytetowa: " + this.paczka_priorytet);
                xmlGenerator.println("<br>");
                xmlGenerator.println("Gabaryt paczki: " + this.paczka_gabaryt);
                xmlGenerator.println("<br>");
                xmlGenerator.println("Masa paczki: " + this.masa_paczki);
                xmlGenerator.println("<br>");
                xmlGenerator.println("Koszt przesylki: " + this.shipmentPrice);
                xmlGenerator.println("<br>");
        }
        else if (packageType.equals("przekaz"))
        {
                xmlGenerator.println("Kwota przekazu: " + this.kwota_przekazu);
                xmlGenerator.println("<br>");
                xmlGenerator.println("Koszt przesylki: " + this.shipmentPrice);
                xmlGenerator.println("<br>");
        }
        else {}
        
        xmlGenerator.println("Kod przesyÅki: " + generatedShipmentID);
        xmlGenerator.printEndTag();
        xmlGenerator.printEndTag();


        xmlGenerator.println("<br><br>");
        xmlGenerator.printEmptyElement("input", "type", "submit", "value", "Wstecz", "name", "Wstecz");
        xmlGenerator.printEndTag(); // form
        udane = true;
    }
    
    /**
     * Metoda za pomoca klasy WycenaPrzesylek oblicza koncowa cene przesylki.
     * Cena ta jest widoczna w podsumowaniu.
     * 
     * @param parameterMap Parametry przekazane przez uÅ¼ytkownika poprzez
     * formularz. 
     */
    private void ShipmentPrice(Map<String, String[]> parameterMap)
    {
        Integer idShipmentType = Integer.valueOf(parameterMap.get("typ_przesylki")[0]);
        Integer idPackageDimension = Integer.valueOf(parameterMap.get("paczka_gabaryt")[0]);
        shipmentPrice = wycenaPrzesylek.calculateShipmentPrice(idShipmentType, 
        parameterMap.get("nazwa_kraju_nadawcy")[0],
        parameterMap.get("nazwa_kraju_odbiorcy")[0],
        idPackageDimension);
    }
}