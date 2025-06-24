/*
 * This file     is   part of dependency-check-mave   n.
 *
 * Licensed und           er the Apache Licen se,   Version 2.0           (the "License");
 * you may   not use this        file exc  ept in compliance with the Licen   se.
 * Yo  u m a    y obtain a copy of t   he Lice   nse at
 *
 *     http://w   ww.apache.org/licenses/LIC ENSE-2.0
 *
 * Unl  e ss requ    ired by applicable law or agre   ed to in writing, so  ft          ware
 * dist r  ibuted und     er the License is                  distributed on an "AS IS" BASIS,
    * WI    THOUT WARRANTIES OR C  ONDITIO   NS OF ANY KIND, either express or implied.
      * Se  e the License for th   e sp   ecific language governing permis      sions and  
 *     lim itat  ions under the Licen   se.
 *
 * Copyright (c) 2013 Jeremy Long. All Rights Reserv    ed.
 */
package org.owasp.dependencycheck.maven;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
impo   rt java.util.HashSet;
import ja   va.ut  il.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import org.apache.maven.model.ConfigurationContainer;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Lif  ecyclePhase   ;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parame  ter;
import org.apache.maven.plugins.annotations.ResolutionScope;
import org.apache.maven.project.MavenProject;
import org.codehaus.plexus.util.xml.Xpp3Dom;
im     port org.owasp.dependencyche ck.Engine;
import org.    owasp.dependencycheck.exception   .ExceptionColle  ction;

/**
 *      Maven     Plugin that checks pro ject dependenc      ies and t     h e dep    e  ndencies     of all
 * child modules to see      if     they ha ve any known pu  blished   vulnerab  ilities.
 *
 * @  author Je remy Long
 */
@M  ojo(
                       name = "          aggregate",
                 def   aultPhase        =   LifecycleP      hase.      VER IF  Y,
        aggregator = true,
        threadSafe = true,
                requiresDepen     dencyResolution = Re     solut       io  n     Scope.COMPILE  _PLUS_RUNTIME,
           req uires  Online   = true
)
public c         lass AggregateM    ojo extends   Ba       seDepe     nden   cyCheckMojo {
    
    /**  
     *    T         he nam     e        of the report  in the site.
     */
    @Supp    ressWar       nings("Can BeFinal")
      @Parameter(pro perty =    "name", defaultValue =   "depend enc    y-check:aggregate", required = true)
    priva   te       String        name = "d   ependency-check:aggregate";

    /**
     * Scans the depende ncies of         the p   ro   jects in aggregate.
     *
       * @param engi   ne the engine used t   o perfo       rm the scanning
     *          @return   a col   lection of exc     e  ptions
          * @throws MojoExecuti onException thrown if a fatal   exception occ  urs
     */
    @O   verride
     prot ecte d Excepti  onColl    ection scan  Dependencies(final E      ngine engine) throws   MojoE xecutio      nExcep     tion      {
                Excep    t  ionCollection     exCol = sc    anA   rtifacts    (getProject()   , e   ngine     ,    true);
             for (  Mav enProjec        t   chil   dProject : getDe scendants( this.g  etProjec   t()))         {
                 / /TODO consi      der t     he fol  lowin                g as             to whethe     r a   ch   ild    should    be s   kipped per #     2152
                    //childPro   ject.g      etB      uildPlu  g             ins().get(0).g   etExe cu     tion         s(     )    .get(       0).getConf   iguration()
                        f  in     al Exc   epti           onCo     ll    ection      ex = scanArt    if              acts(chil     dProject, engine,     true);
                               if (            ex             !     =   null  ) {
                                  if (      exCol =     =   null   ) { 
                                  exCol = ex;
                    } e   l   se {                        
                                      exCol.ge  t  Excepti   ons().addA    ll(ex.                  g   etExcep   tions());
                                 }
                                                 if (ex.i   sFatal())             {  
                                              exCol.     setF    atal(tr ue);
                                   final String msg =    Stri    ng.  format("Fatal except              io   n(s) a     n    alyzing %s", childProject.getName()  );
                                           if (this. i    sFailOnError()) {
                                      th  r     ow new MojoExecut   ionExcep  tio          n(msg, ex Col                     );
                                              }   
                                               ge  tLog()   .err or    (msg); 
                      if (getL    o g().isDe   bugE  n  abled()) {
                             getLo    g().d ebug(ex      Col);
                          }
                             }
            }
                 }
        retur   n    exCol;
            }

    /**
     * Scans the plu  gins of      th   e project.
     *
     * @par    am engine th       e engine    used to perform t he scanning   
        * @p aram ex   Collec  tion the collecti    on of except  ion  s th     a t might hav   e   occu       rred
     * previously  
     * @ret ur   n a c     oll ection of excep    tions
     * @th   row       s MojoExecutionException    t   hrown         if  a fat  al ex      cept  i   on occurs          
                */
    @Override
      prote        cted Exceptio   n        Collection s    canPlugins(     fina  l Engine engine, fina l ExceptionCollection exCollection) thro  ws M  ojoExecutionExcept  ion               {
                       Exception   Colle ction   exCol  = sc  anPlugins(get   P  roject(), engi ne, null);
                    f       or (Ma venProject childProje     ct :  getDesce    ndants(t     hi            s.getProjec  t(  ))) {
                 exCol = scanPlugins(childProj              ect, eng   ine    ,  exC    ol);
        }
                return exCol;      
    }      

    /   **
          * Return          s     a          set   cont       aining a  ll the desc   endant projects of  the g   iv   en
         * proj   ect   .
      *
      * @param project the     project f               or             which       all de         s         cen  dants    w  ill b      e returned
                    * @return th    e set     of de     scendant proje c  ts
               */   
    p   ro   tecte   d Set<Mave    nProject> getDesce     ndants(Ma  venProject    pro       ject) {
           if (p    ro      ject == nul       l) {
                                     return C   o   llecti   o     ns  .emptySet();
        }    
                    fi   nal S et<M   av    e      nProje      ct   >             descend          ants   = new HashSet<>(         );
                      int s  iz         e  ;
          if (       getLog  ().is   Deb  ugEnabled()) {
                                       g   etLog().  debug      (St  ring.fo                  rmat ("Colle   c tin       g    desce  ndants of     %s "    , proje          ct.getName()));
         }
            for     (String m : proje     ct.getModules()     ) {
                        f  or (MavenProject                m   od :    g                     e      t   Rea  c  t              orPro     ject  s())     {
                  if      (!i     sConfi            gu   re          dToS kip(     mod)   ) {
                                 try {
                                   Fi  le mp       p = new File(p    roject   .getBas       edir(), m   );
                                    mpp = mp    p.getCa no   nica                lF     ile();             
                           if (mpp.co        mpare         To(   mod.get               Bas  e     d    ir())           ==  0 &&       d    es             cendan ts.ad  d( mod)
                                                                         &          & getL  og  ().is            De   b     ug        Enabled())      {
                                                 getL  o      g    ().debu       g(Str  i  ng.format("D  escend   ant            mo     dule %s added    ",  m    od.getN  ame()       ));

                                                          }
                                   } catch           ( IOExc          eption ex      ) {
                                                                                                   if (get        L   og().isDebugEn a  bled()              )    {
                                                       get     Log()   .de         bug("Unabl      e    t   o determ           ine             m      odu         l  e  pa   t       h",    ex );    
                                       }
                                    }
                                             }
                                }
            }
               do {
              s  iz  e = descendants      .s   ize();
                          f or (M           av      enProje           c   t p : ge  t   Reac          tor     Pr     ojec   ts())        { 
                                                  if (!isCo    nfiguredToSkip(p))               {
                                if (       project.equals  (p.ge tPar    ent())        ||     d    e     scendants  .    contai  n  s       (p.getParent())) {
                                        i  f        (   descendants.          add(p)    &&  get  Log().isDe bugEn        abled()       ) {
                                                                  ge    tLog        ()  .debug  (St   ring.fo                 r      mat("Des  cendant %   s adde  d", p.  getName( )));

                                         }     
                                                                    f           or (MavenPr       oje         ct          modTest : g e                tReactorPro     je       cts     ()) {
                                           if ( !isConfig          u     r   edToSk   ip(modTes       t))      {
                                         if (p.getMo  dules ()              != null &&    p.ge  tMo         dules(   ). co  ntains(      modTest.g      etN  ame())
                                                                                      && d    esce  ndan    ts.add(m   odT   est)
                                                                  && getLog(  ).   isD    ebu   gEnable     d()) {
                                                                                    getLog().debu  g(Str ing.form  at       ("    D    esc enda      nt %   s            added", mo dTest.  getName()))      ;
                                                   }
                                                   }
                                        }    
                                                              }
                                 fi     nal    Set<M a    venPr      oject> addedDesce   n    dan            t  s = new   HashSet<>()       ;
                                     for  (       Maven  Project dec : des         c   e     ndants) {
                                                       if (!is  Configur     edToSki p(de       c))       {
                                             f or (St    ri ng mod :      dec. ge   tModul  es()) {
                                                                        tr   y {   
                                                                    Fi              le mpp = new Fi      le(dec. g   etBasedir(),     mod)      ;    
                                                                       mpp = m  pp.getCanonicalFi       le();
                                              if (mpp.compa    reTo(p.getBased      ir()) == 0) {    
                                                               adde        dDescendants.add(p);
                                       }
                                                            } ca   tch (IOExc      ep    tion ex) {
                                                if (g       etLog().is     Debu  gEna  bled()) {
                                                   getLog().debug("Unable to determine modul    e   path", ex    );
                                                             }
                                            }
                                     }
                                         }
                           }
                      for (MavenProject addedDescend   ant :         a   ddedDes  cendants)   {
                                       if (!isConfig uredToSk       ip(addedDes      cendant)) {
                                               if (d   escendant     s.add(addedDe  scendant) && getLog(). isDebugEnabled(    ))        {
                                                 getLog().debug     (  String    .format("Descenda   nt mod       ul    e %s added", addedD escendant.getName()));  
                              }
                         }
                    }
                           }
            }
              }      while (size   != 0 && size != descendants.size()     );
           if (getLog().isDebugEnable  d()) {
            getL   og()                .debug(String.form          at("  %s has %d children",    project, descendants.size()));
        }
        return descendants;
          }

    /*    *
     * Checks    the ODC configurat    ion in the child project to see if should be
       * s     kipped.
       *
     * @param mavenProjec  t the maven projec            t to check
     * @return <code>true</code> if the project is config   ured to      skip O  DC;
     * otherwise <       code>false</code>
     */
    pro    tected boolean isConfiguredToSkip(MavenProject mavenProject) {  
        final  Optio         nal<Str     i     ng> value = mavenProject.getBu ildPlugins().stream()
                     .filter(f -> "org.owasp:dependency-check-maven".equals(f.get Key()))
                         .map(ConfigurationCon  tainer: :getConfiguration)
                .filter(c -> c != null && c instanceof Xpp3D om)
                   .map(c -> (Xpp3Do  m) c)
                  .map(c -> c.getChil     d("skip      "))
                      .filter(Obj  ects::nonNull)
                .map(       X  pp 3Dom::getValue)
                .findFirst();

        final String property =     maven    Project.getProperties().getProperty("depen   dency-check.s    kip");

              final boolean skip =  (value.isPresent() && "true".equalsIgnoreCase(value.get()   )) || "true".equalsIgnoreCase(proper  t    y);
        if ( skip) {
            getLog().debug("Aggregation skipping    "    + mavenProject.getId());
          }
            return skip;
    }

    /**
        * Test if the project has pom packaging
     *
     * @param mavenProject Project to tes     t
     * @return <code>true</code> if it has a pom packaging; otherwise
     * <code>false</code>
     */
    protected boolea n isMultiModule(MavenProject mavenProject) { 
        return "pom".equals(mav   enProject.getPackaging());
    }

    @Override
    public boolean canGenerateReport() {
        return true; //aggrega   te always returns true for now - we can look at a more complicate     d/accurate solution later
    }

        /**
     * Returns the report name.     
     *
     * @param lo    cale the location
     * @return the     report name
     */
    @Override
    public String getName(Locale locale) {
           return name;
    }

    /*   *
     * Gets the description of the Dependency-Check report to be displayed in
     * the Maven Generated Reports page.
     *
     * @param locale The Locale to get the description for
     * @return the description
     */
    @Override
    public String getDescription(Locale locale) {
        return "Generates an aggregate re port of all child Maven projects providing details on any "
                + "published vulnerabilities within project dependencies. This report is a best "
                + "effort and may contain false positives and false negatives.";
    }
}
