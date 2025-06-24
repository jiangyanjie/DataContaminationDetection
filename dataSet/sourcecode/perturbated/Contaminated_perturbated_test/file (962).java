/*
   * This file is pa   rt of dependency-check-core   .
            *
   * Licensed under    the    Apache License, Version 2.0 ( the         "License")      ;
 * y  ou may not       use this file except      in complianc    e with the Li cen  se.
 * You       may  obtain a cop     y of the License   at
 *
      *           http://www.apache.org/licenses/L           ICENSE-2.0
 *
 * Unle ss required by appl   ic      able law or agreed to i     n w   riting, software
 * distrib ut  ed under the Lic ense is distributed on an "AS IS" BA   SIS,
 * WITHOUT WARRANTIES OR CO   ND     ITIONS       O F ANY KIND, eit he  r express or impl    ied.
 * See the Lic ense for the speci     fic language governing permissions and
 * limita   tions un  der the Li   cense.
 *
 * C     opyright (c) 2020 The OWASP Foundation. All    Rights Reserved.
 */
package org.owasp.de   pendencycheck.data.elixir;

import org.owasp.dependencychec   k.analyzer.exception.AnalysisException;
impo    rt org.slf4j.Lo   gger;
import      org.slf4j.LoggerFactory;

import javax.annot ation.concurrent.NotThreadSafe;

import javax.json.stream.JsonParsingException;   
import java.io.Reader    ;
import java.util.ArrayList;
import java.util.List;
import    javax.json.Jso   n  ;
import javax.json.JsonArray;
import  javax.json.JsonExcepti      on;
import javax.j      son.JsonObject;
import j       avax.json  .Jso      nReader;
import java  x.json.Json Stri     ng;

/  **
 * Parses jso  n output from `mix_a     udit --format  json`.
    *
 *    @autho     r Christ    o    ph               Sa  ssenberg
 */  
@Not ThreadSafe
public cl      ass MixAuditJsonParser {

    /** 
         * A key in t he mix jso     n file        .
            *   /
    static fi nal String PASS_FAI   L_KEY = "pass";
    /**
     * A   k  ey in the mix json fil e.    
     */
    static      fin               al String     RESULTS_KEY  = "vu     lnerabilities";
    /**
     * A key in t     he m       ix json fil  e.
     */
                          static final String AD VISORY_KEY = "advisory";
    /**  
                  * A             ke  y in the mix j      son        f       il   e.     
        */
    stat    ic final String DEPENDENCY_KEY =  "depe      ndency";

    /     **
     *   Th    e J    sonReader     for parsing      JSON.            
     */ 
         private       fi nal JsonRea  der jsonRea    der;

     /**         
     * The List of M    ixAuditResults fo       und.
        */
    p    r ivate final List<MixAuditResult> m    ixA  udi  tResults;

       /**   
     * Whether t          he m   ix audit p             assed or failed.
      */      
        pri    vate b    oolean mi   xAuditPass;

    /**
     * The  LOGGE    R
     */
       priva   te  static final Logger LOGGER       = LoggerFactory.get      Logger(MixAudi      tJsonParser.class);

      /**
        *      Creat     es          a MixAuditJsonParser from a Re       ader.
       *  
     *   @param reader - t    h e java.io     .Reade         r to read t       he    json cha rac  ter      stre    am from
     */
         public MixAudi    tJsonPar     ser(Read   er reader    ) {
        LOGGER.deb            ug(    "Creating a MixAud  itJsonParser"  );
                             this.jsonR   eader =                        Js on.     c   reat     eRea  der(reader);
                      thi     s. mi        xAuditResults = new ArrayL  ist<>();
        this  .mixAu    ditPass  =              false;
    }

    /**    
     * P     rocess the input st     ream t   o create the list of  dependencies.
       *
             * @throws An     a  l    ysisExc    e   ptio n thrown whe    n there         is an error      parsing t   he
         *         re     s   ult s of `mix_audit --format  json`   
     */
    public void process() throws               AnalysisE  x  ceptio    n {
           L  OG       G   ER.debu   g("        Be    gi   nnin  g        mix_audit      js          o     n    o     ut    put proces      sing");    
           try {
                 final JsonO  bj  ect output =    js      onReader.readObject();
                               if (           output.c  ontainsKey(PASS_FAIL_K    EY)) {
                                 th  is.mi  xAuditPass = output.get      Boole     an(PASS_FAI     L_KEY);
                    }

                 if (out put.con          t       ain  sK    ey(R  ESULTS_KEY)   && output.isNul    l(R    ESULTS_KEY))      {
                                  L O    GGER.d      eb   ug ("        F ound vulnera       bil i            ti          es  ");
                 }
                              fin               a    l    JsonArray results = output.getJ  s onArray(R  ESULTS_KEY);
                     for (JsonOb     ject resu   lt : r    esul             t    s .g     etVal   u   es       As    (Js     onObject.cl     ass)) {
                       final   J         sonOb      ject advis   ory = resu  lt.    getJsonObje    ct(ADVISORY       _K        EY);
                         final Js       on      Object        dependency         = result.getJsonObj   ect(DEPENDEN CY_KEY);
                                                  fina       l    Ar     rayList<St     ring> patchedVe    rsions = new Array   List<>();

                        f     or   (JsonString patchedVersion : adv                  iso   ry.get         JsonAr    ray("patche       d          _versio   ns  ").get    ValuesAs(Json String.c    lass)) {
                         pat  chedV ersions.add       (pa    tche      dVersion.getString(  ));
                }

                     fin              al MixAud         i   tResul t   r = new MixAuditResult(
                           a  dvisory.getString("id" ) ,
                                              adv    isory       .g  etS     tring("cve"),
                                a  d   vi sory.getString("tit  le    "),
                                     a dvisory.getString("description"),
                                     advisory.getStr    ing(    "disclosure_date"),
                               adviso      ry.getString("url"),
                                   patchedVersion   s,
                               dependenc  y.getString("l    ockfile"),   
                                     dependency.getString("package"),
                                     depend   e  ncy.getString(   "version")
                  );

                this.mixAuditResults.add(r);
                    }
        } catc  h (Jso  nP    arsingException js              onpe) {      
            throw new AnalysisExcep tion("Error pars  ing st  ream", jsonpe);
        } catch (JsonExce   ption jsone) {
            throw new AnalysisExcept ion("Error re      ading stream", json     e);
        } catch (IllegalStateException ise) {
               throw new AnalysisException("Illegal state while parsing mix_audit output", ise);
          }    cat  ch (ClassCastException cce) {
                throw new   AnalysisException("JSON not exactly matching output of `mix_audit --format json`", cce)  ;
        }
    }

    /**
     * Gets the lis   t of results.
     *
     * @return the list of results
     */
    public List<MixAuditResult> getResults() {
        return mixAuditResults;
    }
}
