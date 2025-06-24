package solution ;

import java.math.BigInteger;

import util.MathTool;

public class Decode {
	/**
     * @param p 素数
     * @param q 素数
     * @param publicKey_e 暗号化の際に暗号対象を累乗する指数
     * @return 秘密鍵のprivateKey_dを求める
     * http://www.maitou.gr.jp/rsa/rsa11.php
     */
    public BigInteger culcPrivateKey(BigInteger p, BigInteger q, BigInteger publicKey_e){

        BigInteger phi = MathTool.phiFunctionByPrimeNumber(p,q) ; // 結果は(p-1)*(q-1)の解
        int effectiveNum = culcEffectiveMinimumNthTerm(phi, publicKey_e) ; 
        BigInteger mulEAndD = mulEAndD(phi, effectiveNum) ;
        BigInteger result = mulEAndD.divide(new BigInteger(publicKey_e+"")) ; // dとeの積からeを割る事でdを求める(d*e / e = d ) 
    	return result ;
    }
   
    /**
     * 
     * @param phi
     * @param publicKey_e
     * @return
     * 秘密鍵privateKey_dが自然数となる時の「サルでもできるRSA暗号」中に使われてるnを求める。
     * http://www.maitou.gr.jp/rsa/rsa11.php
     * 
     */
    private int culcEffectiveMinimumNthTerm(BigInteger phi, BigInteger publicKey_e){
    	int n = 1; 
    	while(true){
    		BigInteger mulEAndD = mulEAndD(phi, n) ;
    		BigInteger residue = mulEAndD.mod(publicKey_e) ; //residueはe*d mod eの解, eとdの積がeで割ったときに0ならば自然数dは自然数と言える
    		if(isZero(residue)){ 
    			break; 
    		}
    		n++ ;
    	}
    	return n ;
    }
    
    /**
     * 公開鍵eと秘密鍵dの積を公式を用いて求める
     * 積は公式によって n × (p - 1) × (q - 1) + 1 で求まる。 
     * → http://www.maitou.gr.jp/rsa/rsa11.php
     * @param phi (p-1) × (q-1) 
     * @param index
     * @return
     */
    private BigInteger mulEAndD(BigInteger phi, int index){
    	BigInteger mulPhiAndIndex = phi.multiply(new BigInteger(index+"")) ;
    	BigInteger result = mulPhiAndIndex.add(BigInteger.ONE) ;
    	return result ;
    }
    
   
    
    private boolean isZero(BigInteger num){
    	return num.compareTo(BigInteger.ZERO)==0 ;
    }
}
