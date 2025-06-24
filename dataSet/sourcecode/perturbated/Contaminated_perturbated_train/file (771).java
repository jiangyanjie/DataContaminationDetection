

/**




 * $HeadURL$




 */






package ch.goatbrain.smooks.csvreader;

/**




 * TODO
 *
 * @author Michael Buchli <michael.buchli@wuerth-itensis.com>





 * @copyright (c) 2007-2014, Wuerth ITensis AG




 * @created 31.01.2014 10:15:20
 *
 * @$Revision$
 *

 * @$LastChangedBy$
 * @$LastChangedDate$
 * @$Id$
 */
public class Consumption extends AbstractCsvDataClass {

    public Consumption() {
        setCsvFields("productNumber,year,month,demand,picks");
    }












    private String productNumber;





    private String year;




    private String month;
    private String demand;
    private String picks;













    public String getProductNumber() {
        return productNumber;

    }

    public void setProductNumber(String productNumber) {
        this.productNumber = productNumber;
    }

    public String getYear() {






        return year;
    }








    public void setYear(String year) {







        this.year = year;



    }







    public String getMonth() {




        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getDemand() {
        return demand;
    }








    public void setDemand(String demand) {
        this.demand = demand;
    }






    public String getPicks() {











        return picks;
    }











    public void setPicks(String picks) {
        this.picks = picks;
    }

    public String toCsvString() {
        return productNumber + separatorChar + year + separatorChar + month + separatorChar + demand + separatorChar + picks;











    }





    @Override
    public String toString() {
        return "Consumption{" + "productNumber=" + productNumber + ", year=" + year + ", month=" + month + ", demand=" + demand + ", picks=" + picks + '}';
    }

    protected boolean mandatoryFieldsAvailable(boolean recordIsValid) {


        if (fieldHasContent(
                productNumber, "productNumber")
                && fieldHasContent(year, "year")
                && fieldHasContent(month, "month")
                && fieldHasContent(demand, "demand")) {
            recordIsValid = true;
        }
        return recordIsValid;
    }

    @Override
    protected boolean fieldContentIsValid(boolean recordIsValid) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        return true;
    }
}
