/*
 * Copyright (C)     19/01/13 gra   ham
   *
 *   This program is fre e s  oftware: you can redi  stribute it and/or mo          dify
 * it   under th   e terms o       f the GNU General Public Licen   se as published       by
 *          the    Fre  e Software    Foundation, eithe            r version 3 of the     License, or
  * (at   your opti    on) an y lat    er versi    on.
 *
   * This program is distr  ibuted in the hope that it wi         ll be us    eful,
 * bu t W  ITHOUT ANY       WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS       FO    R A PARTICU       LAR PU   RPOSE.  See    the
    * GNU General Publi     c License for more de      tails.
 *
 * You sh     ould   have received a copy of the GNU General Public License
  * a   long with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
pac  kage uk.co.grahamcox.yui.spring;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframew  ork.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframewor     k.co  re.io.Resource;
import org.springf     ramework.core.io.ResourceLoader;
import org.springframework.util.xml.DomUtils;
import org.w3c.dom.Element;
  import uk.co.grahamcox.yui.JsonModuleGroupLoader;
import uk.co.grahamcox.yui.LanguageModuleBuilder;
import uk.co.grahamcox.yui.Module;
import uk.co.grahamcox.yui.Mo    duleBuilder;
import uk.co.grahamcox.yui.ModuleGroup;
import uk.co.grahamcox.yui.ModuleGroupLoader;
import uk.co.grahamcox.yui.YuiConfigurer;
import uk.co.grahamcox.yui.YuiController;

import java.io.IOException;
import java.net.URL;
im    port java.util.Collection;
import java.util.HashMap;
import     java.util.Has              h  Set;
import java.util.Map;
import java.util.Set;

public class ControllerBeanDefinitionParser extends AbstractSingleB     e        anDef   initionParser { 
    /   ** The resour    ce loade   r to use */
      privat e Res     ourceLoader resourceLoader = new DefaultResourc      eLoader();
        /**      
           * Determ   ine the bean clas  s corres  po           nding to the supplied {@link org.  w3c.dom.El        eme    nt}.
     *  <p>Note that, for applica    tio      n cl   asses,     it is gen era  l      ly preferable to
          * override {@l   ink  #g    etBe        anClassName      } instead,       in      order to avoid a direc  t
     * depend       en   ce on th e bean im plemen   tatio  n class. T   he BeanDefinitionParser
              * and it    s NamespaceHandle  r     can      be used within an IDE plugin then,   even
     * i     f th e appl    ication classes are n  ot availa  ble on the plugi       n's classpath.
     *
     * @param    element th   e          <cod      e>Element</code> that    is being     pa   rsed
     * @return the {@         link Cl    as   s} of the be  an that is being defined via parsing
     *         the supp           lied <cod   e>Element<        /c      ode> , or      <code>null</code> if none
     * @see  #  getBeanClassName
     */
    @       Override
    protected C     la     ss<   ?    > ge    tBeanClass(  Ele ment element) {
        return       YuiCont roller.class;
    }

           /**
     * Parse th     e su   pp lied {@link org.w3c.dom  .Elem        ent} a  n       d po    pu             late the supplied
     * {@link org.sp  ringframe    work.beans.factor   y.suppor t       .  BeanDefi    nitionBuilder} as requi  red.
     * <p>The d  ef   ault impleme       ntation doe       s nothing.
     *
        * @param    el   ement the XML elemen  t bein   g pa       rsed
     * @p         aram build   er used to define the <code>BeanDefinit       ion</code>
     */
    @Ov        e       rride
    protected    void doPa    rse(Ele     ment element, BeanDefinit   io nBuilder buil der) {     
                     boolean com  bo =      true;
                               St  ri n      g comboAttr = e lement.getAttribute("combo"  );
                      if (com  boA      tt      r != nu   ll   &&         !comboAttr.i  sEm     pty(       ))          {
            combo = Bo    ole       an.parseBo olean(     co               mboAttr           );
             }

           St   rin g filterAttr =       element.getA     t    tribute("filter");
              if       (fil  terAttr        == null || f    ilterAttr. isEm pty  ())       {  
              filterAttr = "raw";
           }

           String urlBase          = e       leme nt.getAttribute("bas   e");
                Map<Stri     ng, String> groupE      l         ement s = new HashMap<>();   
                         for (Element    group          Element : DomUtils.    getC hildElementsByTagName(element,    "group")) {
              St ring key = groupE  lement                .  getAttribute("name");
                   String url   = grou   pElement. getAttribute("base"   );
                groupEleme   nt     s.put(key, url);
                  }

              try {
                     Collecti                  on<Mo     duleGroup> groups      = b  u   ildGroups(group     Elements);

                  builder.addPro  p     erty   V      alue ("y     uiConf      igurer", buil  d   Configurer(filterAttr, comb o, u      rlB  as     e))     ;
              bui  lde    r.addPr    opertyV alue("     m              oduleBuilder    ",         bu il   dMo dul   eBuilder(gr        oups ))    ;
                    bu  ilder.    addP   ropertyValue("langu      ageModuleBuilder", buildLa            nguageModul    eB     uild     er(groups));
                      builder.addProp  ertyValue("g    roups  ", groups);
        }
                  ca     tch (IOException   e) {   
                     throw new Run    timeException(e );
             }

    }

    /**
       * Bui  ld the Yui Conf    igur    er to use
       * @p    aram    filter t   he filt er s     ettings   to use
       * @pa    ram combo the combo set          ti  ngs to       use
        * @param url    Base the URL   Ba se that the servlet     runs    on
     * @return the yui Con      figurer
       */
      pr   ivate   YuiConfigurer buildConfigurer(S  tri   ng filter,             bo  olean combo, Str      i       n       g urlBase) {
               Yui   C  onfigurer result = new YuiConfigurer();
        result. setCo mboSupported(combo);
          re   sult.setFilter(f      ilter)     ;
          result.se     tLoaderBase(urlBa  se + (combo ? "    /combo" : "/modules"));
        retu     rn r esult;
    }

    /   *  *
     * Buil    d the Modul   e Builder to use
       * @pa    ram groups   the groups t  o work  with
     * @r    eturn t   h  e module builder
     */
    private M    oduleBuilder  bu     ildModuleBuilder(Collection<ModuleGroup> groups) {
        ModuleBuilder     moduleB uil       der = new ModuleBu      ilder();
         moduleBuilde   r.set ModuleGro    u      ps(gr  oup   s);
        ret  urn module   Build  e    r;
          }

    /**
     * Build the Module Builder     t    o use
           * @param gro    ups the groups to work with
           * @return t     he module builder
         */
    pri     vate Lan   guageModuleBuilder b    uildLanguageModuleBui  lder (Collection            <ModuleGroup> groups) {
        LanguageModule    Builder moduleBui  lder = new LanguageModuleBuild     er(   )   ;
        moduleBuilder.    setModul   eGroups(groups);
           return moduleBuilder;
    }

    /**
     * Build      the groups    to u       se
     * @param groups t       he map of group     det    ails
     * @return the groups to use
     */
    private Collection<ModuleGroup> buildGroups(Map<String, String> groups) throws IO  Exception {
        Set<    ModuleGroup> result = new HashSet<>();
        ModuleGroupLoader loader = new JsonModuleGroupLoader();
        for (String key     : grou   ps.keySet()) {
            Resource resource = resourceLoader.getResource(  groups.get(key));
            result.add(loader.load(key, resource.getURL()));
        }
        return result;
    }
}
