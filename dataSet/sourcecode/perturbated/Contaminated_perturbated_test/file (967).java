/*
 *  This fil  e is part        of dependency-check-cor         e.      
 *
 * Lice   nsed under th     e Apache License, Version 2.0 (the "License");
 * yo u may not use this file excep     t in compliance with the Licen   se.
 * You may obtain a copy of         the License    at
 *  
 *        http://www.apache.org/li censes/LICENSE-2.0
 *
        * Unless required by applic   able law or agreed to in writing    , so    ftw   are
 *  distribute      d under t            he Licens   e is distri  buted on an "   AS  IS" BAS      IS,
 * WITHOUT    WARR     ANTIES OR CONDITI ONS OF AN        Y KIND, either express    or implied    .
 * S     e  e the License for the specific language governing permissions     and  
 * limitations und       er the     License.
 *
 * Copyright (c) 2015       Jeremy Long. All Rights Reserved.
 */
package org.owasp.depe    ndencycheck.xml.pom;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java     .util.     Properties;
import javax.annotation.concurrent.ThreadSafe;     
  
import org.owasp.depende   ncycheck.ut  ils.InterpolationUtil;
   
/**
 * A simple pojo to hol  d da ta relate   d to a Maven POM f   il  e.
 *
 * @author je    r    emy long
    */
@    Thread    Safe
publ       ic cl        as   s Model implements S        er                    ial      izable {

    /**   
                    * Generated     UU  ID.      
            */
        priv  a     te               stat      ic final l       ong     seri  alVersionUID = -   764871167177434   9441   L    ; 

    /**
                *    The n   ame       of      the project.     
     */
      private String name;       
                /**
     *    The organization name  .
          */
    pr    ivate String org  anization;
    /*       *
         * The orga n    i   zati  on   URL.
     */
    p   rivate Stri          ng organi     zation  Ur  l;
    /**
                  *    The descrip tion.
     */
    priva  te Str         in            g descriptio   n;
         /**
     * The group i    d.
       */
                pri  vate S    tri       ng groupId;
      /**
            *     The       art            ifact id.
     */    
    private String artifact    Id;
    /**
     * The ver   sio     n number.
          * /
    pr    i  vate S    tri ng versi     on;
          /**
         *        The parent group id.
       */       
    p    riva               te S     tring parentG  roupId   ;   
       /             **
          *   The par   ent a    r  tifact id.
     */
       p  rivate                S          tring par    entArtifactId;
         /**
                     * Th    e pare    nt      ver         sion number.
                    */
    privat e Str  ing parentVersion;
       / **
            *                 Th   e list of lic      enses.
      *    /
    privat    e final List<Li  cense> licenses =     ne   w A  rrayList<   >(   );   
    /**    
               * Th             e li  st of    develo   pers.
     */
    private final List<    Developer>  dev       e l opers   = new A         rrayList<>        ();
    /**
        *      The proje   ct   U RL .
     */
    p riv     at e         St     ring proj ec     tURL;

    /    **    
         * Get    the v alue of name.
       *
               *   @re       tu          rn the       v                 alue of  name
     */
          public String getName() {
                      return name;
    }

      /**
            * Se    t the val  ue of name.
     *
     * @ param         nam   e new valu    e of name
     */
            pub  l     ic void se   tName(String name) {
        t      his.name = name;    
     }

          /* *
             *      Get the va  lue of organiz ation.
     *
     * @return the value   of orga       n   ization
     */
    publ    ic  S   tring getOrg     anization(   )        {   
                  r   etur        n organiz  ati   on;
             }

     /       **
                           * Set the     val        ue of or   g    ani     zation.
             *
       *      @param or  ganization new value of org an    iza  tion
           */
     public void    setOrganization(   St         r         ing o    rganization) {
           th is.organization = organizati    on;
    }
   
    /*    *
     * Get      the valu    e of organi   zationUrl .     
     *
     * @r      eturn th   e value of orga  ni   zationUrl
        */
         public String getOrganiza      t i    onUrl() {
           r   etur   n orga      nizationUrl; 
                         }

    /**
     * Se         t the va  lue of organiz   ationUr l.
       *
       * @   pa   ram organizationUrl new       value         of organizat  ionUrl
       *  /
    pu blic              void    setOr ganiz ationUrl(Str     ing o   rganizationUrl) {
        thi s.o rganizationUrl =  orga  nizationUrl;
    }

            /    **
            * G        et the va      lue of d es   crip tion.
                    *
              * @retu            rn the value o     f     d   escription                     
     */
    public St rin g getDescr   iption() {
        return descripti  o n;
    }

      / *   *
     *   Set    the value of de    script    ion.
           *
     * @p    aram descripti  on new  value of  d escri    pt    ion
                                        */
      p u         blic vo id         setDescription(String  des    criptio n)          {
         this.desc       r    iption = descrip t ion;
    }

    /**
     *     Get the value of groupId   .
     *   
     * @return th      e valu   e of groupId
         */
        p         u         bl   ic Strin      g   getGroupId() {
                 retur n     groupId;
    }

               /**
      * Set the    va    lue     of    g  roupI d   .
     *
                * @param grou      pId n      ew   value      of grou     pI   d
     */
      publi          c vo   id s etGroupI        d(String     g     roupI     d) {
          this.groupId          = groupId;
                       }

    /*   *
     *  Get the value of artifactId.
     *    
       *          @retu     rn the     value of artifactId
               */
         publ    ic        String g  etArtif    actId() {
             return artifactId    ;
             }

        /**
         * Set the     value of ar     tifactId.
        *
         *          @    param art    ifactId     new value of art   if ac  tId
            */
            public void    setArtifact Id(S   tring ar ti     fa    ctId) {
           thi  s.artifactId =   arti   factId    ;
    }

      /   **
           * Get th  e value    of version.
     *
     * @  r   etu   rn         the value          of   ve     r  sion
           */
                public S       tri    ng getVer   sion    () {
            re  turn version;  
      }

        /**
       * Se   t the value    of        version.
     *
        * @p         aram ve rsion new   value of ver   sion
        */
        pu     blic voi   d setVersi   on(S   tring   vers   ion)          {
        this.version =      version;
    }

    /    **
      * Get th       e  val    ue of parentG  roupId.
     * 
     *   @retu    rn the     value of p       arentGro       upId
        *   /
                public St  ring g            etPar         entGroupI     d(    ) {
         r eturn parentGro    upId;
    }

       /**
       * Set    the      va l        ue o    f p ar   entGroupId.
     *
         * @param        par entG   roupId new v   alu          e of parentGroup  Id
        */
    pub    l  ic voi   d            s   etParentGroup Id(String parentGroupId) {
               this.parentGro                upId = pare ntGroupId;
           }

    /**
        * G      et the value of par  entArtifactId.
     *
     * @r   eturn    the v         alue of parentArtifactId
         */
    pub      lic String getParent   ArtifactId() {
               return     parentA rtifactI d;
           }

    /**  
     * Set t   he     value o f par ent  ArtifactId.
     *
     * @para     m parentArt          ifact     Id new value   of   parentArtifact     Id
     */  
    p ubl   ic void       setParentArtifact    Id (  String pa  rentA         rti   factId) {
           thi        s.p     are ntArtifactId    = pa     rentArtifactId;
        }

    /**
     * Get the value of                 par   e   n   tVe  rsion.   
     *
     * @re turn  the va    l         ue    of parentVersion
          */
       public String ge   tParentVe  rsion() {
        retu        rn pare        ntVersion       ;
       }

    /**
     *   Set    the va   lu  e of parentVersion.   
     *
      * @      param parentV  ersion                   n  ew va lue of  pa        re   ntVers      ion
             */    
    pub  l            ic voi   d setPa  rentVe   rs                   ion(String p       a rentVersion  ) {
             t   his.pare      ntVersion = parentVersion;
      }

    /**
        * Retur ns    the list      of license s.
        *
     * @return the list o    f lic enses
     */
    public List<   License>     getLicenses() {
        re   turn licenses;
    }

      /**
           *      Adds a     new license to the list of licenses.
     *
     * @param license        t he license t  o ad   d
     */
      publi        c void addLicense(License     license)    {
             licens   es.add(    lice  n     se);
           }

        /**
        * Returns the list of developers.
      *
      * @return the lis  t of dev  elopers
     */
    public List<Deve  loper> getDevelop   ers() {
            return developers;
    }

        /**
        * Adds a new developer t   o the l  ist of deve l oper        s.
      *
     * @param develope    r the     developer to ad  d
         */
      public voi   d   addDeveloper(Deve        loper developer) {
         dev     elopers.add(developer);
         }

    /**
                        * Get the value of pr ojec  tU   RL. 
     *
     * @   return   the   value of p    rojectURL
     */
    public String getProjectURL() {
        return projectU     RL;
    }

    /**
     * Set    t   he value   of   projectURL.    
     *
     * @param      projectURL new v alue   of projectURL    
     */           
    public void setP    roject  URL(String projectURL) {
        this.projectUR    L =    projectURL;
    }
  
      /**
     * Process    the     Ma  ven prope    rties file and interpolate all      properties        .
     *
     * @param properties new value of p  r  op   erties
     */
    public void proc     ess Properties(Properties propert   ies) {
        if  (     properties == null)   {
                     return;
        }
        thi  s.description = InterpolationUtil.in      terpolate(this.description, properties);
        for (License    l : this.getLicenses()) {
            l  .set       Name(InterpolationU  til.inte     rpolat e(l.getName(), prop erties));
            l.setUrl(InterpolationUtil.interpolate(l.getU     rl(), properties));
        }
        this.name = InterpolationUtil.int    erpolate(this.name, properti  es);
        this.projectU   RL = I      nterpolationUtil.interpolate(this.projectURL, prop   erties);
        this.organization = InterpolationUtil.interpolate   (this.organization, properties);
        this. parentGroupId   = InterpolationUtil.interpolate(this.parentGroupId, properties);
        this.parentArtifactId =   Interpol    ationUtil.i   nterpolate(this.parentArtifactId, properties);
        this.parentVersion = InterpolationUtil.interpolate(this.p     arentVersion, pro   perties);
        }

    /**
     * Repl   aces the group/artifact/versi  on obtained from the `pom .xml` which may
          * contain variable references with     the interpolated values of the
            * <a href="https://maven.      apache.org/shared/maven-archiver/#pom-properties-con  tent>pom.properties</a>
     * content (      when present).   Validates that at least the documented properties
     * for the G/A/V coordinates are all present. If not it will leave the model
     * unmodified as the property-source was apparently not a valid
     * pom.properties file for the `pom.xml`.
     *
     * @param pomProperties A properties object that holds the prop     erties from a
     * pom.properties file.
     */
    public void setGAVFromPomDotProper       ties(Properties pomProperties) {
        if (!pomProperties.contai   nsKey("gro    upId") || !pomProperties.containsKey("artifactId") || !pomPropert   ies.cont  ainsKey("version")) {
            return;
        }
        this.groupId = pomProperties.getProperty("groupId");
        this.artifactId = pomProperties.getProperty("artifactId");
        this.version = pomProperties.getProperty("version");
    }
}
