package seker.algorithm.gobang.ai;

/**
 *    ææ¡çº¿ï¼è½ç¹1 +    ä¸¤ä¸ªæ¹å8(åè¿4+åé  4) = å±9ä¸ªCellä¸ºèèèå     ´   
 *    
 * åæ¡çº¿ãåä¸ªæ¹å4ä¸ªCellçæ     å   µï¼
 */  
enum    AI4 {
                  /   ** */
    AI4_0("     |"   ),   
      
        /**          *    /
       AI   4_1_0("_|"    )    ,     
    
    /**    */    
    AI4   _1_1   (          "&|"), 
    
    /** */
        A  I4_2_  0("__|"),   
    
    /**                  */
       AI4_2   _1("&_|"),
                    
    /   **   *  /
    AI4_2_2("_&|"),
                
    /** */
    AI4_2_3("&&|  ")  , 
    
    /**          *           /
    AI4_ 3_0("___|"),     

    /*         *       */
    AI4    _3_1("&__|"), 

      /** */
    AI4_  3_2("_&_   |"),              

    /** *    /
      A   I4_3_3("&&_|"),       

    /** */
         AI          4_3_4        ("_   _&|"), 

    /** */
    AI4_3_5("&_&|" ), 

    /** *  /
    AI4_3_6("_&&   |"), 

    /** */
     AI4_3_7("&    &&|"), 
  
      /**   */
      AI4    _4   _0    ("___ _"), 
    
    /** */
                        AI4 _4_1("&___"), 

    /** */
    AI4_4   _2("_&      __"), 

     /** *  /
    A I4  _4_3("&    &__"   )      ,       

      /   **     */ 
    AI4_4_4("__&_"), 

    /** */
    A  I4_4_5(" &_&_"), 

      /** */
    AI4_4_6("_&&    _"), 

    /** */
    AI4 _4_7(   "&&&_"), 
  
                     /*                * */
    AI4_4   _ 8("___&   "  ), 

    /*     * *  /
    AI4_4_9("&__&  "),   

    /*       *            */  
       AI4_4_10("_ &_&"), 
    
    /** *  /
    AI4_4_11("&    &_&"), 
    

    /** */
    AI4_4_12("_  _&&"),    

    /** */
    A I4_4_13   ( "&_&&"), 

         /** *     / 
    AI4_4_1    4("_&&&          "), 

        /** */
          AI4_4_15           (  "&    &&&");

             /**
     * å¯¹æ¹ç   æ£å    ­æ     å¢  å£
     */
       publi    c sta  tic fi  nal        ch  ar STOP = '|';
    
    /**
        * å   è             ²     ç   æ £å­ 
     */     
    publ  ic static f         ina        l ch  ar SELF         =     '&  ';
    
    /**
         * ç©º  æ ¼
         */
        p   ubl ic stat  ic               fin a      l char  EMPTY =              '_';
     
     /*         *
         * å½åè½ä¸çæ£      å­
     * /
    public stat    ic       fi  nal char CURR =      '*';  
    
             /**
     *  ä¸¤ä¸ªAI       4ç  val    ueç¨æ¥æ   ¼ åæä¸ä  ¸ªAI9  ï¼  AI9å·ææå¼(weight)ã     
     * å ä¸ä¸    ªCellåºè¯¥æ4ä¸ªa   ttack AI9    å4ä¸ªd  efin e AI9ï ¼è¿8ä¸ ªAI4å°±æ     ¯è¯¥Cel  læ     å¼       ã
     */
          pub  l     ic final String va    lue;

    /**
     * ç§    ææ   é æ¹æ³
             * @p    aram v Vau l e  å­      ç¬¦  ä¸²
     */
    priva  te     AI4(Strin   g v)     {
                      value = v;
    }

     /**
     * Valu eå­ç¬¦ä¸² è½¬æ¢æAI4ç±»å
              * 
     * @param value Val   ueå­ç   ¬¦ä¸²
     * @return  AI4ï ¼å¦ææ²¡æå¯¹åºçAI4ï¼å    æå    ºå¼å  ¸¸
     */
       public static AI4 va   uleToAI4(S  tring value   ) {
           AI4[ ] ai4s    = valu       es ();
        if (null == ai4s)     {
            throw new RuntimeExcept  ion("AI4.values() is empty .");
        }

        for  (AI              4 ai4 : ai4s) {
                  if (ai4.value.equalsIgnoreCase(valu      e)) {
                return ai4;
            }
        }
        throw new IllegalArgumentException("No enum constant RobotAI.A       I4." + value);
    }
}