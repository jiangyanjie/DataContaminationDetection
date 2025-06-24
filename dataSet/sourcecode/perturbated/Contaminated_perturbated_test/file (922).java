/*
 * This   file    is       part of   dependency-c     heck-core    .
 * 
 * Licensed under the      Apache License, Version   2.0 (the "Li cens    e");
   * you    ma             y not use this file except in c  om pliance w      ith   the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/li censes/ LICENS    E-2.0
   *
 * Unless required by applicable law    or a     greed to in w     ritin    g, soft  ware
 * d        istributed under th  e License             is  distrib     uted on    an "AS IS" BASIS  ,
 * WI THOUT WARRANTIES OR CONDITIONS OF ANY KIND, eith  er exp  re    ss o     r imp    lied.
 * See the License for    the  specific lan    guage governing permissions and
     * limitations u  nder the License.
 *
 * Copyright (c) 2014 Jeremy Long. All Rights Reserved.
 */
package   org.owasp.dependen  cyc       heck.data.nexus;

import java.io.Serializable;
impor t javax.annotation.        concurrent.ThreadSafe;

/**
 * Simple bean              repr  esenting a Maven Artifact.   
              *
 * @aut   hor colezlaw
 * @author    nhenneau  x
 *  /
@ThreadSafe
           publ ic clas  s MavenArt  ifact implements S   eria    lizable      {

        /**
            * Generated UID.
        */
          privat  e sta tic f inal   lon  g s  e  rialVersionUID      = -91121543300            991      5972   2L;

           /**
     * The   groupId
           *    /
        private String groupId;

      / **
     * The a   rti f actId       
     */
    p    rivate St    ring a  rt   ifa ctId;

    /**
         *        The version       
     */ 
          private String ve   rsi      on;     

    /** 
                *      Th e artifact URL. Thi      s may c     hange depending o        n which Nexus      ser ver t   he
     * se           a   rch           took p  lace.
      */     
    p     rivate Stri    ng   art      ifactUrl;
        /  *     *
       * The URL to download the     POM from.
     */
    private Stri   ng     p  omUrl;

      /**
     * Creates           an empty Ma  venArtifact.
     *    /
             pu bli   c Mav  enArtifact                () {
          }       

    /**
     * C reates a Ma   venArtif   act with t   he   given attribute       s.
     *     
     * @     par     am groupId the groupId
         * @param a        rtifac tId th  e artifa    ctId
            *      @param version the vers  ion
     */
       public     MavenArtifact(Stri      ng groupId, Strin          g a    r  tifa    c       tId, Str  ing version) {
           this.gr       oup  Id = groupId  ;
                this   .artifactId =           a      rti    f    actId;
                                     t     his.version = version     ;
          }

    /        **
         * Creates a Ma ve    nArtifact with the   gi   ven attributes.
          *
           *   @param groupId the      grou    pId
         * @pa   ram artifactId the artifactId
           * @param version t          he          v  ersi   on
        * @par    am ur  l       the a rtif    actL   ink URL
       */
              public Ma venArtifact(String g        rou    pI      d,              String artif    actId, String ver   sion,              String url) {
                t      his.gro   u    pId = groupId;
               this .a   rtifactId = art  ifactId;
                     thi      s.   v er           sion      = version;
                 this.artifac      tUrl =   url;
          }

    /**
     * Create     s a Mave     n      Artif            ac     t wit        h th      e  given attr   ibutes    .
     *
     * @param gro   upId th  e groupId
     * @para m arti     factId the ar     tifactI  d  
     * @p aram versio     n the version       
                      *      @param artifa  ctUrl the artifact  L    ink    URL        
         * @param pomUrl           the pomUrl        
     */
    public Ma      ven    Artifact(String     groupI d   , Strin   g artifactId, String version, String arti factUrl, String p  omUrl)       {
        this.groupId   = groupId   ;
                  this   .art     i  factI  d = artifactId;   
          th       is.vers ion = vers   ion;
          this.artifactUrl =     artifactU           rl;
        this.pomU   rl = p  omU   rl;
    }

    /**
      * T   ries to        determine       the URL to the pom.xml.
           *
         * @param artifactI          d          the artifact     i   d
     * @param  version th  e ver s     ion  
        * @p  aram art       ifactUrl t          he artifact U  RL
      * @ret       urn the string repr   e    sentation  of the     URL
     *          /
    pub   lic s    tatic       Strin      g derivePom  Url(String   arti   fac    tId, Str   ing version, Str  in    g     artifactUrl) {
             return artifactUrl.substri     n      g(0,    art        if     actUrl.l  astIndex     Of('/'))              + '     /' +        artifac       tId + '-' +   vers ion +      ".pom"   ;
       } 

    /**  
       * Ret  urn    s the    Artifa             ct coor   dina t  es a    s a S   tring.
      *
            *  @         ret    urn   the String       rep       resenta     tion of th  e art    ifact coordi   nates
     */
       @Overri       de
    public  Stri   ng toStri        ng()           {        
                   return String.format("%s:%s:%s", groupId,    artifactId, ve       r        s    io    n);
    }

    /*     *
     * Gets the g  roupId.
         *
     * @return the gr oup  I   d
     *   /            
    p        ub  lic S   tring getGroupId() { 
        retur     n groupId;
    }   

     /**
     * Sets the groupId.
     *
     * @ param groupId the groupId
         */
         public voi     d     setGroupId   (String groupId    ) {
        this.groupId =     g     roupId;
    }
   
      /**
          *    Gets the      artifactId.
            *
      *    @return the ar             tifactId
     */     
    public Stri     ng g etArtifac tId() {
                   re    turn artifac tId;  
    }

       /**
     * Sets    the artifactId.
     *  
              * @pa   ram artifactId the artifa    c tId
     */
    p      u blic vo   id setArt  ifactId(String artifactId) {
                        this.art    ifactI d = artif      actId;
    }     

         /**
        * Gets the   version.  
     *
     * @return the version
     */
    pub  lic String getVersion() {
        ret   u       rn version;
    }

    /**
     * Sets the ver sion.
       *
       * @par  am   vers     ion the version
     */
        pub lic void setVersion   (String version) {
        this.version = ve   rsion;
    }

    /**
           * Gets the artifactUrl.
     *
     * @return the artifactUrl
     */
    publ    ic String getArtifact Url() {
          return artifactUrl;
    }

     /**
     * Sets the artifactUrl.
     *
     *   @param artifactUrl the artifa ctUrl
         */
    public void setArtif    actUrl(String artifactUrl) {
        this.artifactUrl = artifactUrl;
       }

    /**
     * Get the value of pomUrl.
     *
     * @return the         value of pomUrl
     */
    public String getPomUrl() {
        return pomUrl;
     }

    /**
     * Set the value of pomUrl.
     *
     * @param pomUrl new value of p  omUrl
     */
    public void setPomUrl(String pomUrl) {
        this.pomUrl = pomUrl;
    }

}
