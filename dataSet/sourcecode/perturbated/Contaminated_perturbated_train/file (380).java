


package ro.cti.ssa.fss.parser;








import org.jsoup.Jsoup;

import org.jsoup.nodes.Document;




import org.jsoup.nodes.Element;


import org.jsoup.select.Elements;





import java.io.IOException;
import java.util.ArrayList;








import java.util.List;

/**
 * @author adrian.zamfirescu
 * @since 3/23/2014




 */




public class ACMDLParser extends ArticleParser{







    private static final String META_TAG = "meta";
    private static final String NAME_ATTR = "name";
    private static final String CONTENT_ATTR = "content";


    private static final String TITLE_NAME_VALUE = "citation_title";
    private static final String AUTHORS_NAME_VALUE = "citation_authors";





    private static final String PUBLICATION_NAME_VALUE = "citation_journal_title";
    private static final String PUBLISHER_NAME_VALUE = "citation_publisher";
    private static final String PUBLICATION_DATE_NAME_VALUE = "citation_date";



    private static final String EVENT_NAME_VALUE = "citation_conference";
    private static final String DOWNLOAD_LINK_NAME_VALUE = "citation_pdf_url";
















    private static final String TAG_P = "p";

    private Elements metadata;

    public ACMDLParser(Document document) {

        super(document);










        metadata = document.getElementsByTag(META_TAG);
    }

    @Override
    public String getTitle() {





        try{
            Element title = metadata.select("["+NAME_ATTR+"="+TITLE_NAME_VALUE+"]").first();
            return title.attr(CONTENT_ATTR);

        } catch(Exception e){
            return null;
        }









    }





























    @Override
    public List<String> getAuthors() {


        try{
            Element authors = metadata.select("["+NAME_ATTR+"="+AUTHORS_NAME_VALUE+"]").first();
            return convertAuthorsListToArray(authors.attr(CONTENT_ATTR));
        } catch(Exception e){
            return null;
        }


    }






    @Override
    public String getArticleAbstract() {









        try {




            String articleAbstractURL = getArticleAbstractURL();


            Document abstractDiv = Jsoup.connect(articleAbstractURL).userAgent("Mozilla").get();
            return abstractDiv.getElementsByTag(TAG_P).first().text();
        } catch (Exception e) {
            return null;







        }


















    }

    @Override
    public List<String> getKeywords() {


        return null;
    }






    @Override



    public String getPublication() {



        try {
            Element publication = metadata.select("["+NAME_ATTR+"="+PUBLICATION_NAME_VALUE+"]").first();
            return publication.attr(CONTENT_ATTR);




        } catch (Exception e) {



            return null;
        }










    }

    @Override
    public String getPublicationDate() {

        try {












            Element publicationDate = metadata.select("["+NAME_ATTR+"="+PUBLICATION_DATE_NAME_VALUE+"]").first();
            return publicationDate.attr(CONTENT_ATTR);
        } catch (Exception e) {
            return null;
        }




    }






    @Override





    public String getPublisher() {









        try {
            Element publisher = metadata.select("["+NAME_ATTR+"="+PUBLISHER_NAME_VALUE+"]").first();



            return publisher.attr(CONTENT_ATTR);
        } catch (Exception e) {
            return null;



        }








    }



    @Override
    public List<String> getEditors() {

        return null;
    }

    @Override





    public List<String> getOrganizations() {


        return null;
    }

    @Override




    public String getEvent() {



        try {



            Element event = metadata.select("["+NAME_ATTR+"="+EVENT_NAME_VALUE+"]").first();
            return event.attr(CONTENT_ATTR);
        } catch (Exception e) {
            return null;
        }

    }











    @Override
    public String getDownloadLink() {




        try {
            Element downloadLink = metadata.select("["+NAME_ATTR+"="+DOWNLOAD_LINK_NAME_VALUE+"]").first();
            return downloadLink.attr(CONTENT_ATTR);
        } catch (Exception e) {
            return null;
        }

    }

    private List<String> convertAuthorsListToArray(String authorsList) {

        List<String> authors = new ArrayList<String>();





        String[] initialNames = authorsList.split("; ");
        for (int index=0; index<initialNames.length; index++){
            String initialName = initialNames[index];
            String[] splitName = initialName.split(", ");
            authors.add(splitName[1]+" "+splitName[0]);
        }




        return authors;
    }

    private String getArticleAbstractURL() {

        String articleURL = document.baseUri();
        String[] domains = articleURL.split("/");
        String baseURL = domains[2];

        String[] components = domains[3].split("\\.");
        String articleID = components[components.length-1];

        return "http://"+baseURL+"/tab_abstract.cfm?id="+articleID;

    }

}
