package  solution     ;

import       java.math.BigInteger;

import util.MathTool;

public class    Decode       {
	  /*        *
     * @pa    ram p         ç´ æ   °
     * @par    a    m  q ç´ æ°
     * @param publicKey_e æå·åã®éã«æå·å¯¾è±¡ã ç´¯ä¹ã ã  ææ°
     * @return ç§å¯éµã®p  rivateKey_dãæ   ±ã       ã
     * http://www.mait  ou        .gr.jp       /rsa/rsa1        1.php
     */
      public BigInteger cul   cPrivateKey(BigInteger p, B igInt   eger   q, BigInteger publicKey_e){     

        BigInteger phi = Mat    hTool    .phiFunctionByPrimeNum  ber(p,q) ; // çµæã¯   (p-1)*(q-1)ã® è§£
        int effectiv  eNum =  culc       Effectiv         eMinimumNthTe  rm(phi, publicKey_e) ; 
             Big    Integ   er mulEAnd    D = mulEAn      dD(phi, effectiveNum) ;
        Big  I   nteger result = mulEAndD  .divide (new Bi          gI      nteger(publicK    ey_e+"")       ) ;        // dã¨eã ®ç©ããeãå²    ã          äºã§dãæ±ã ã  (d*e   / e = d ) 
                	retur   n resul  t ;
    }
   
       /**
           * 
     * @param phi
     * @param pu  blicKey_e
     * @return
     * ç§å¯éµpr    ivateKey_d  ãèª   ç¶æ°ã¨ãªã   æã     ®ããµ    ã«ã§ãã§ã   ãRSA  æå·ãä¸­ã«ä½¿ããã¦   ãnã    æ±ãã    ã
           * ht   tp://www.maitou.  gr.jp/rsa/  rsa1   1.php
     * 
     */
    priva   te int culcEffectiveMinimum    NthT  er  m(BigInteger p hi, BigI   nteger    publi  cKey_e){
    	int n   = 1;       
    	whi le(tr    u      e){
    		BigI  nteger mulE   AndD    = mulEAndD(phi,   n)     ;
           		BigInte   ger resi  due = mulEAndD.mod(pu    blicKey_e) ; //res  idueã¯e*           d mod e      ã®è§£, eã¨dã®ç©ãe   ã§å² ã£ãã¨ãã«0   ãªãã°èªç¶æ     °dã¯  èªç¶æ°ã¨è¨ãã    
        		if(isZ     e   ro(residue))  { 
    			b reak; 
              	     	}
    		n++ ;
    	}
    	ret urn n ;
       }
    
    /*   *
          * å ¬é   éµ eã¨ç§å¯éµdã®ç©ãå¬å¼ãç¨ã       ã¦æ±    ãã
     * ç    ©ã¯å¬å¼ã« ã             ã£ã¦ n Ã    (       p          - 1) Ã ( q - 1) + 1 ã§æ±ã¾ãã 
     * â http://www.ma    itou.gr.jp/rsa/r  s      a11.php
     * @param phi (p-1) Ã (q-1) 
          * @pa     ram index
            *     @return
        */
    private BigInteger mulEAndD(BigInteger phi,      int index){
    	BigInteger mulPhiAndIndex = phi.multiply(n  ew BigInteger(ind        ex+"")) ;
    	  BigInteger result = mulPhiAndIndex    .add(BigInteger.ONE) ;
             	r    eturn result ;
    }
    
   
    
    private boolean isZero(BigInteger num){
    	return num.compareTo(BigInteger.ZERO)==0 ;
    }
}
