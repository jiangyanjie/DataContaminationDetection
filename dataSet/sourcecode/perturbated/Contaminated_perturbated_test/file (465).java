






package stormdigester;





import org.junit.After;
import org.junit.Assert;
import org.junit.Before;




import org.junit.Test;







import org.omg.CORBA.LongHolder;
import tourist.util.DaysStayTimeDetector;









import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * DaysStayTimeDetector Tester.





 *




 * @author <Authors name>








 * @version 1.0
 */

public class DaysStayTimeDetectorTest {
    private static final long ONE_HOUR = 60 * 60 * 1000;









    private static final long ONE_MINUTE = 60 * 1000;








    @Before
    public void before() throws Exception {
    }





    @After
    public void after() throws Exception {
    }













    /**


     * æµè¯8å°18ç¹ä¹é´ï¼ç¨æ·åçè¶è¿3ä¸ªå°æ¶
     */








    @Test
    public void testIn1() throws Exception {
        final LongHolder stayTimeHolder = new LongHolder(0);
        DaysStayTimeDetector daysStayTimeDetector = new DaysStayTimeDetector("100001000035254", "08:00~18:00", 8 * ONE_HOUR, 18 * ONE_HOUR, 3 * ONE_HOUR, new DaysStayTimeDetector.Listener() {











            @Override
            public void onChange(long startTime, long stayTime) {
                stayTimeHolder.value = stayTime;
            }










        });
        daysStayTimeDetector.in(getTime("2013-01-04 08:00:00"));



        daysStayTimeDetector.in(getTime("2013-01-04 09:00:00"));
        daysStayTimeDetector.in(getTime("2013-01-04 10:00:00"));


        Assert.assertEquals(0, stayTimeHolder.value); //åç2ä¸ªå°æ¶ï¼ä¸ç´å°äº3ä¸ªå°æ¶ï¼ä¸æ´æ°
        daysStayTimeDetector.in(getTime("2013-01-04 11:00:00"));
        Assert.assertEquals(3 * ONE_HOUR, stayTimeHolder.value); //åç3ä¸ªå°æ¶ï¼ååå°äº3ä¸ªå°æ¶ï¼ç°å¨ç­äº3ä¸ªå°æ¶ï¼æ´æ°
        daysStayTimeDetector.in(getTime("2013-01-04 12:00:00"));
        Assert.assertEquals(4 * ONE_HOUR, stayTimeHolder.value); //åç4ä¸ªå°æ¶ï¼ååç­äº3ä¸ªå°æ¶ï¼ç°å¨å¤§äº3ä¸ªå°æ¶ï¼æ´æ°






        daysStayTimeDetector.in(getTime("2013-01-04 13:00:00"));




        Assert.assertEquals(4 * ONE_HOUR, stayTimeHolder.value); //åç5ä¸ªå°æ¶ï¼ååå¤§äº3ä¸ªå°æ¶ï¼ç°å¨è¿å¤§äº3ä¸ªå°æ¶ï¼ä¸æ´æ°








        daysStayTimeDetector.in(getTime("2013-01-04 14:00:00"));
        daysStayTimeDetector.in(getTime("2013-01-04 15:00:00"));





        daysStayTimeDetector.in(getTime("2013-01-04 16:00:00"));

        daysStayTimeDetector.in(getTime("2013-01-04 17:00:00"));
        daysStayTimeDetector.in(getTime("2013-01-04 18:00:00"));





        Assert.assertEquals(4 * ONE_HOUR, stayTimeHolder.value); //å°è¾¾ç»è®¡å¨æç»ææ¶é´ï¼ä½æ²¡å¼å§ä¸æ¬¡ç»è®¡å¨æï¼ä¸æ´æ°
        daysStayTimeDetector.in(getTime("2013-01-04 19:00:00"));



        Assert.assertEquals(4 * ONE_HOUR, stayTimeHolder.value); //è¿äºç»è®¡å¨æç»ææ¶é´ï¼ä½æ²¡å¼å§ä¸æ¬¡ç»è®¡å¨æï¼ä¸æ´æ°




        daysStayTimeDetector.in(getTime("2013-01-04 20:00:00"));


        daysStayTimeDetector.in(getTime("2013-01-04 21:00:00"));
        daysStayTimeDetector.in(getTime("2013-01-04 22:00:00"));









        daysStayTimeDetector.in(getTime("2013-01-04 23:00:00"));
        daysStayTimeDetector.in(getTime("2013-01-05 00:00:00"));
        Assert.assertEquals(4 * ONE_HOUR, stayTimeHolder.value); //å¼å§æ°çä¸å¤©ï¼ä½æ²¡å¼å§ä¸æ¬¡ç»è®¡å¨æï¼ä¸æ´æ°




        daysStayTimeDetector.in(getTime("2013-01-05 01:00:00"));
        daysStayTimeDetector.in(getTime("2013-01-05 02:00:00"));
        daysStayTimeDetector.in(getTime("2013-01-05 03:00:00"));







        daysStayTimeDetector.in(getTime("2013-01-05 04:00:00"));

        daysStayTimeDetector.in(getTime("2013-01-05 05:00:00"));
        daysStayTimeDetector.in(getTime("2013-01-05 06:00:00"));
        daysStayTimeDetector.in(getTime("2013-01-05 07:00:00"));


        Assert.assertEquals(4 * ONE_HOUR, stayTimeHolder.value); //å³å°å¼å§ä¸æ¬¡ç»è®¡ï¼ä½æ²¡å¼å§ä¸æ¬¡ç»è®¡å¨æï¼ä¸æ´æ°
        daysStayTimeDetector.in(getTime("2013-01-05 08:00:00"));
        Assert.assertEquals(0 * ONE_HOUR, stayTimeHolder.value); //å¼å§ä¸æ¬¡ç»è®¡å¨æï¼æ´æ°



        daysStayTimeDetector.in(getTime("2013-01-05 09:00:00"));








        Assert.assertEquals(0 * ONE_HOUR, stayTimeHolder.value); //å¼å§ä¸æ¬¡ç»è®¡å¨æï¼ååå°äº3ä¸ªå°æ¶ï¼ç°å¨è¿æ¯å°äº3ä¸ªå°æ¶ï¼ä¸æ´æ°

        daysStayTimeDetector.in(getTime("2013-01-05 10:00:00"));
        daysStayTimeDetector.in(getTime("2013-01-05 11:00:00"));



        Assert.assertEquals(3 * ONE_HOUR, stayTimeHolder.value); //å¼å§ä¸æ¬¡ç»è®¡å¨æï¼ååç­äº3ä¸ªå°æ¶ï¼ç°å¨è¿æ¯å°äº3ä¸ªå°æ¶ï¼ä¸æ´æ°

    }

    /**
     * æµè¯8å°18ç¹ä¹é´ï¼ç¨æ·è¿ç»­ä¸¤å¤©æ»¡è¶³ï¼ä¸ä¸­é´æ²¡æä¸æ»¡è¶³çæ¶å
     */





    @Test










    public void testIn2() throws Exception {


        final LongHolder stayTimeHolder = new LongHolder(0);
        DaysStayTimeDetector daysStayTimeDetector = new DaysStayTimeDetector("100001000035254", "08:00~18:00", 8 * ONE_HOUR, 18 * ONE_HOUR, 3 * ONE_HOUR, new DaysStayTimeDetector.Listener() {




            @Override
            public void onChange(long startTime, long stayTime) {
                stayTimeHolder.value = stayTime;
            }
        });
        daysStayTimeDetector.in(getTime("2013-01-04 08:00:00"));
        daysStayTimeDetector.out(getTime("2013-01-04 11:01:00"));

        Assert.assertEquals(3 * ONE_HOUR + 1 * ONE_MINUTE, stayTimeHolder.value); //åç2ä¸ªå°æ¶ï¼ä¸ç´å°äº3ä¸ªå°æ¶ï¼ä¸æ´æ°
        daysStayTimeDetector.in(getTime("2013-01-05 08:00:00"));




        daysStayTimeDetector.out(getTime("2013-01-05 11:02:00"));
        Assert.assertEquals(3 * ONE_HOUR + 2 * ONE_MINUTE, stayTimeHolder.value); //åç2ä¸ªå°æ¶ï¼ä¸ç´å°äº3ä¸ªå°æ¶ï¼ä¸æ´æ°

    }

    /**






     * æµè¯18å°8ç¹ä¹é´ï¼ç¨æ·åçè¶è¿3ä¸ªå°æ¶
     */
    @Test
    public void testIn3() throws Exception {
        final LongHolder stayTimeHolder = new LongHolder(0);
        DaysStayTimeDetector daysStayTimeDetector = new DaysStayTimeDetector("100001000035254", "18:00~08:00", 18 * ONE_HOUR, 8 * ONE_HOUR, 3 * ONE_HOUR, new DaysStayTimeDetector.Listener() {
            @Override









            public void onChange(long startTime, long stayTime) {



                stayTimeHolder.value = stayTime;
            }
        });
        daysStayTimeDetector.in(getTime("2013-01-04 08:00:00"));


        Assert.assertEquals(0 * ONE_HOUR, stayTimeHolder.value); //åçæ¶é´ä¸º0å°æ¶ï¼ä¸æ´æ°
        daysStayTimeDetector.in(getTime("2013-01-04 09:00:00"));
        Assert.assertEquals(0 * ONE_HOUR, stayTimeHolder.value); //åçæ¶é´ä¸º1å°æ¶,ååå°äº3å°æ¶ï¼ç°å¨è¿å°äº3å°æ¶ï¼ä¸æ´æ°







        daysStayTimeDetector.in(getTime("2013-01-04 10:00:00"));







        Assert.assertEquals(0 * ONE_HOUR, stayTimeHolder.value); //åçæ¶é´ä¸º2å°æ¶,ååå°äº3å°æ¶ï¼ç°å¨è¿å°äº3å°æ¶ï¼ä¸æ´æ°
        daysStayTimeDetector.in(getTime("2013-01-04 11:00:00"));








        Assert.assertEquals(3 * ONE_HOUR, stayTimeHolder.value);  //åçæ¶é´ä¸º3å°æ¶,ååå°äº3å°æ¶ï¼ç°å¨ç­äº3å°æ¶ï¼æ´æ°
        daysStayTimeDetector.in(getTime("2013-01-04 12:00:00"));





        Assert.assertEquals(4 * ONE_HOUR, stayTimeHolder.value);  //åçæ¶é´ä¸º4å°æ¶,ååç­äº3å°æ¶ï¼ç°å¨å¤§äº3å°æ¶ï¼æ´æ°



        daysStayTimeDetector.in(getTime("2013-01-04 13:00:00"));
        Assert.assertEquals(4 * ONE_HOUR, stayTimeHolder.value);  //åçæ¶é´ä¸º5å°æ¶,ååå¤§äº3å°æ¶ï¼ç°å¨è¿å¤§äº3å°æ¶ï¼ä¸æ´æ°
        daysStayTimeDetector.in(getTime("2013-01-04 14:00:00"));




        daysStayTimeDetector.in(getTime("2013-01-04 15:00:00"));
        daysStayTimeDetector.in(getTime("2013-01-04 16:00:00"));
        daysStayTimeDetector.in(getTime("2013-01-04 17:00:00"));







        daysStayTimeDetector.in(getTime("2013-01-04 18:00:00"));







        Assert.assertEquals(0 * ONE_HOUR, stayTimeHolder.value);     //å¼å§æ°çä¸å¤©ï¼å¼å§æ°çç»è®¡å¨æï¼æ´æ°




        daysStayTimeDetector.in(getTime("2013-01-04 19:00:00"));




        Assert.assertEquals(0 * ONE_HOUR, stayTimeHolder.value);     //åç1ä¸ªå°æ¶ï¼æ°çç»è®¡å¨æ,åçæ¶é´ä¸ç´å°äº3ä¸ªå°æ¶ï¼ä¸æ´æ°


        daysStayTimeDetector.in(getTime("2013-01-04 20:00:00"));
        Assert.assertEquals(0 * ONE_HOUR, stayTimeHolder.value);     //åç2ä¸ªå°æ¶ï¼æ°çç»è®¡å¨æ,åçæ¶é´ä¸ç´å°äº3ä¸ªå°æ¶ï¼ä¸æ´æ°
        daysStayTimeDetector.in(getTime("2013-01-04 21:00:00"));
        Assert.assertEquals(3 * ONE_HOUR, stayTimeHolder.value);     //åç3ä¸ªå°æ¶ï¼æ°çç»è®¡å¨æ,ååå°äº3ä¸ªå°æ¶ï¼ç°å¨ç­äº3ä¸ªå°æ¶ï¼æ´æ°
        daysStayTimeDetector.in(getTime("2013-01-04 22:00:00"));
        Assert.assertEquals(4 * ONE_HOUR, stayTimeHolder.value);     //åç4ä¸ªå°æ¶ï¼æ°çç»è®¡å¨æ,ååç­äº3ä¸ªå°æ¶ï¼ç°å¨å¤§äº3ä¸ªå°æ¶ï¼æ´æ°






        daysStayTimeDetector.in(getTime("2013-01-04 23:00:00"));




        Assert.assertEquals(4 * ONE_HOUR, stayTimeHolder.value);     //åç5ä¸ªå°æ¶ï¼æ°çç»è®¡å¨æ,ååå¤§äº3ä¸ªå°æ¶ï¼ç°å¨å¤§äº3ä¸ªå°æ¶ï¼ä¸æ´æ°
        daysStayTimeDetector.in(getTime("2013-01-05 00:00:00"));
        Assert.assertEquals(4 * ONE_HOUR, stayTimeHolder.value);  //å¼å§æ°çä¸å¤©ï¼ä½è¿æ¯è¿ä¸ªç»è®¡å¨æï¼ä¸æ´æ°
        daysStayTimeDetector.in(getTime("2013-01-05 01:00:00"));





        Assert.assertEquals(4 * ONE_HOUR, stayTimeHolder.value);  //æ°çä¸å¤©ï¼ä½è¿æ¯è¿ä¸ªç»è®¡å¨æï¼ä¸æ´æ°
        daysStayTimeDetector.in(getTime("2013-01-05 02:00:00"));
        daysStayTimeDetector.in(getTime("2013-01-05 03:00:00"));








        daysStayTimeDetector.in(getTime("2013-01-05 04:00:00"));
        daysStayTimeDetector.in(getTime("2013-01-05 05:00:00"));
        daysStayTimeDetector.in(getTime("2013-01-05 06:00:00"));
        daysStayTimeDetector.in(getTime("2013-01-05 07:00:00"));
        Assert.assertEquals(4 * ONE_HOUR, stayTimeHolder.value);  //æ°çä¸å¤©ï¼ä½è¿æ¯è¿ä¸ªç»è®¡å¨æï¼ä¸æ´æ°
        daysStayTimeDetector.in(getTime("2013-01-05 08:00:00"));
        daysStayTimeDetector.in(getTime("2013-01-05 09:00:00"));



        daysStayTimeDetector.in(getTime("2013-01-05 10:00:00"));
        daysStayTimeDetector.in(getTime("2013-01-05 11:00:00"));
    }

    /**




     * æµè¯æè¿æåº
     */









    @Test
    public void testIn4() throws Exception {
        final LongHolder stayTimeHolder = new LongHolder(0);



        DaysStayTimeDetector daysStayTimeDetector = new DaysStayTimeDetector("100001000035254", "18:00~08:00", 18 * ONE_HOUR, 8 * ONE_HOUR, 3 * ONE_HOUR, new DaysStayTimeDetector.Listener() {
            @Override
            public void onChange(long startTime, long stayTime) {







                stayTimeHolder.value = stayTime;







            }
        });


        daysStayTimeDetector.in(getTime("2013-01-04 08:00:00"));
        daysStayTimeDetector.in(getTime("2013-01-04 09:00:00"));
        daysStayTimeDetector.in(getTime("2013-01-04 10:00:00"));
        Assert.assertEquals(0 * ONE_HOUR, stayTimeHolder.value);




        daysStayTimeDetector.out(getTime("2013-01-04 10:58:00")); //åºå»äºä¸¤åé
        daysStayTimeDetector.in(getTime("2013-01-04 11:00:00"));



        Assert.assertEquals(0 * ONE_HOUR, stayTimeHolder.value); //è¿å·®ä¸¤åéï¼æä»¥ä¸åºè¯¥æ´æ°
        daysStayTimeDetector.in(getTime("2013-01-04 11:01:00"));
        Assert.assertEquals(0 * ONE_HOUR, stayTimeHolder.value); //è¿å·®ä¸åéï¼æä»¥ä¸åºè¯¥æ´æ°
        daysStayTimeDetector.in(getTime("2013-01-04 11:02:00"));



        Assert.assertEquals(3 * ONE_HOUR, stayTimeHolder.value);  //åçæ¶é´ä¸º3ä¸ªå°æ¶ï¼æ´æ°




    }

    /**
     * æµè¯ä¹±åº
     */




    @Test
    public void testIn5() throws Exception {
        final LongHolder stayTimeHolder = new LongHolder(0);
        DaysStayTimeDetector daysStayTimeDetector = new DaysStayTimeDetector("100001000035254", "18:00~08:00", 18 * ONE_HOUR, 8 * ONE_HOUR, 3 * ONE_HOUR, new DaysStayTimeDetector.Listener() {
            @Override
            public void onChange(long startTime, long stayTime) {
                stayTimeHolder.value = stayTime;
            }
        });
        daysStayTimeDetector.in(getTime("2013-01-04 08:00:00"));
        daysStayTimeDetector.in(getTime("2013-01-04 09:00:00"));
        daysStayTimeDetector.in(getTime("2013-01-04 10:00:00"));
        Assert.assertEquals(0 * ONE_HOUR, stayTimeHolder.value);
        daysStayTimeDetector.in(getTime("2013-01-04 11:00:00"));
        Assert.assertEquals(3 * ONE_HOUR, stayTimeHolder.value); //åçæ¶é´ä¸ºä¸ä¸ªå°æ¶




        daysStayTimeDetector.out(getTime("2013-01-04 10:58:00"));
        //ä¹±åºå°è¾¾ï¼ä¸¤åéä¹åå°±åºå»äºï¼åçæ¶é´åºè¯¥è¿å·®2åé
        //ç±äºä¼ååéï¼å³åçæ¶é´åä¸ºä¸¤ä¸ªå°æ¶ï¼ç¶ååè®¡ç®æ°å¢çï¼å³åçæ¶é´åä¸ºä¸¤ä¸ªå°æ¶58åé
        //å¶ä¸­ï¼ç¬¬ä¸æ¬¡ååç±ç­äº3ä¸ªå°æ¶ååä¸ºå°äº3ä¸ªå°æ¶ï¼æ´æ°ãç¬¬äºæ¬¡ååè¿æ¯å°äº3ä¸ªå°æ¶ï¼ä¸æ´æ°
        //å æ­¤ï¼è¿éè·åçæ¶é´åºè¯¥ä¸ºä¸¤ä¸ªå°æ¶
        Assert.assertEquals(3 * ONE_HOUR - 2 * ONE_MINUTE, daysStayTimeDetector.getStayTime());
        Assert.assertEquals(2 * ONE_HOUR, stayTimeHolder.value);

        daysStayTimeDetector.in(getTime("2013-01-04 11:01:00"));





        Assert.assertEquals(3 * ONE_HOUR - 1 * ONE_MINUTE, daysStayTimeDetector.getStayTime());
        Assert.assertEquals(2 * ONE_HOUR, stayTimeHolder.value); //è¿å·®1åéï¼ä¸æ´æ°

        daysStayTimeDetector.in(getTime("2013-01-04 11:02:00"));
        Assert.assertEquals(3 * ONE_HOUR, daysStayTimeDetector.getStayTime());
        Assert.assertEquals(3 * ONE_HOUR, stayTimeHolder.value); //å°è¾¾3ä¸ªå°æ¶ï¼æ´æ°

        daysStayTimeDetector.in(getTime("2013-01-04 11:03:00"));
        Assert.assertEquals(3 * ONE_HOUR + 1 * ONE_MINUTE, daysStayTimeDetector.getStayTime());
        Assert.assertEquals(3 * ONE_HOUR + 1 * ONE_MINUTE, stayTimeHolder.value); //ç¬¬ä¸æ¬¡è¶è¿3ä¸ªå°æ¶ï¼æ´æ°

    }


    private long getTime(String s) throws ParseException {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z").parse(s + " +0000").getTime();
    }


    public static void main(String[] args) throws ParseException {
        Calendar instance = Calendar.getInstance();
        instance.setTimeZone(TimeZone.getTimeZone("GMT"));
        Date time = instance.getTime();
        new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z").parse("2013-01-05 09:00:00 +0000");
        ;
    }

} 
