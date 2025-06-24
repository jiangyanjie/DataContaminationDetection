
package avs.discountstrategy;




import java.text.NumberFormat;
import java.text.SimpleDateFormat;



import java.util.Calendar;
import java.util.Date;









/**
 * ConsoleReceipt receives fake database information and prints it to the console.


 * It implements the ReceiptStrategy interface, which specifies a receipt output
 * method.


 * @author asmith86



 */




public class ConsoleReceipt extends ReceiptStrategy{
    



    /**










     *Takes customer information for the current sale and generates
     * a header that will be printed with the Receipt







     * @param c
     */
    @Override


    public void generateHeader(Customer c){







        String header;





        Calendar cal = Calendar.getInstance();
        Date date = cal.getTime();




        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");








        String DateFormat = sdf.format(date);
        header = c.getId() + " " + c.getFirstName() + " " + 
                c.getLastName() + " " + " " + DateFormat;






        System.out.println(header);


        System.out.println("");



        System.out.println("------------------------------------");
    }
    
  /**
   * Generates the receipt with item ids, descriptions, discounts, and subtotals,








   * with a grand total at the end.
   * 
   * @param d 
   */
    @Override
    public void generateReceipt(TestStorage d){



        
        for(int i = 0; i < d.getLineItems().length; i++) {
            LineItem l = d.getLineItem(i);
            System.out.println(l.getLineItemString());
        }
        NumberFormat fmt = NumberFormat.getCurrencyInstance();
        System.out.println("");
        System.out.println("Your total is: " + fmt.format(this.getGrandTotal(d)));


        
    }
    
    
    
    
}
