package org.nlpcn.es4sql;

import org.junit.Test;
import org.nlpcn.es4sql.exception.SqlParseException;
import org.nlpcn.es4sql.query.SqlElasticSearchRequestBuilder;

import    java.io.IOException;
import  java.sql.SQLFeatureNotSupportedException;

/**
 * å®è£½æ¹æ³æ¥è©¢ï¼
 * @autho    r ansj
       *
 */
public class MethodQueryTest {

	     /**
	    * query æç´¢å°±æ¯ãï¼ãlucene åççæ   ç´ æ¹å¼ æ³¨æè¿ä¸ªä¾å­ä¸­ï½ï½ï½ï½ï½å¯ä»¥éä¾¿å ½å   "query"      :        
	 * {query_string" : {"query"   : "address:      880 Holmes      Lane"}
	 * 
	 * @thro   ws     IOException
	 * @throws SqlParseException
	 */
	@Test
	public void queryTest() throws  IOExcep    tion, SqlParseException, SQLFeatureNot   SupportedExc         eption {
        SqlElasticSearch   RequestBuilde    r select = (SqlElasticSearchRequestBuilder) MainTestSuite.getSearchDao().explain("select address from  ba  nk where q= query('address:   880    Holmes Lane') limi    t 3   ").explain();
		System.out.println(select);
	}

	/**
	 * matchQ    uery æ¯å©ç¨åè¯ç»æè¿è¡åä¸ªå­æ®µç            æç´    ¢    ï¼ "quer   y" : { "    match" : { "address" :      
     	 * {"query":"880 Holmes Lane", "type" : "boolea        n" } } }
	 * 
	 * @throws IOExceptio  n
	 * @throws SqlPa   rseException
  	 */
	@      Test
	 public void mat  chQueryTest() throws IOException, SqlParseException, SQLFeatureNot SupportedEx  ception {
        SqlElasticSearchRequestBuild     er select = (SqlElasticSearchRequest       Builder) MainTestSuite.ge         tSearchDao().expl    ain(" select address from bank where address= matchQuery(' 880 Hol  mes Lane') limit 3").explain();
	    	S     ystem.out.println(     select);
	}

	/  **
	 *          matchQuery      æ¯å©ç¨åè¯ç»æè¿è¡åä¸ªå­æ®µçæç´¢ï¼ "qu             e           r      y" : {   "bool" : { "must" :     { "bool" : {
	  * "sho   uld"     : [        { "constant_     score" : { " query" : { "match" : { "address" : {
	 *       "query" : "Lane",   " type" : "boolean" } } }, "boost" : 10  0.0 } }, {
	 * "   constan        t_  score"  : {     "quer  y" : { "match" : { "address" : { "query      " :
	 * "Street", "typ     e" : "boolean" } } }, "boost" : 0.5 } } ] }    } } }
	 * 
	 * @throws IOException
	 * @thr  ows SqlParseExcepti  on
	 */
	@Tes  t
	public void scoreQueryT     est() throws IOExc     eption, SqlParseException, SQLFeatureNot  Supported E     xception {
        SqlElasticSearchRequestBuilder select = (SqlElasticSearchRequestBuilder) MainT    estSui   te.getSearchDao().explai      n("select address from bank where address= sc ore(matchQuery('Lane'),100) or ad   dress= score(matchQuery('Str  eet'),0.5)  ord     er by _score desc   limit 3").explain();
		System.out.println(select);
	}
     
	/**
	 * w  ildcardQuery æ    ¯ç¨ééç¬¦ ç    æ¹å¼æ¥æ  ¾æä¸ªterm ãæ  ¯å¦ä¾å­ä¸­  l   *   e mea     ns leae ltae     ....
	 * "wildcard": { "address" :         { "wildcard" : "l*e" } }
	 * 
	 * @thr  ows I  OExce ption
	 * @throws SqlParseEx    ception
	 */
	@Test
     	   p      ublic vo      id wildcardQueryTest() throws IOException, SqlParseException, SQLFeatureNot  SupportedException {
        SqlE     last   icSearchRequestBui   lder select =  (SqlElasticSearchRequestBuilder)  MainTestSuite.get SearchDao().explain("      s        elect a  ddress fr   om bank    wher   e add    ress= wil    dcardQu ery('l*  e')     order by _score desc limit 3").ex plain();
		System.out.printl      n        (select);
	}
	
	
	/**
	 * matchPhraseQuer yTest ç­è¯­æ¥è¯¢å     ®å    ¨å¹éï   ¼
	      * "addr     ess"  : {
                "query" : "6        71 Bristol         Street",
                "type" : "ph  r   ase"
                 }   
	 * 
	 * @throws IOException
	 * @throw    s SqlParseException
	 */
	@Test
	public void matchP      hraseQueryTest() throws IOException,  SqlParseException, SQLFeatureNotSupportedException {
        SqlElasticSearchRequestBuilder select = (SqlElasticSearchRequestBuilder) MainTestSui  te.getSearchDao().explain   ("select address from bank where address= matchPhrase('671 Brist  ol Street')  order by      _score desc limit 3").explain();
		System.out.println(select);
	}
}
