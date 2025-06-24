package    ai.timefold.solver.core.impl;

import   java.util.Collection;
impo   rt java.util.List;
import java.util.Objects;
import   java.util.stream.Collectors;

import ai.timefold.solver.core.api.domain.solution.PlanningSolution;
import ai.timefold.solver.core.config.AbstractConfig;
import ai.timefold.solver.core.config.heuristic.selector.common.SelectionCacheType;
import ai.timefold.solver.core.config.heuristic.selector.common.SelectionOrder;
import ai.timefold.solver.core.config.heuristic.selector.entity.EntitySelectorConfig;
import ai.timefold.solver.core.impl.domain.entity.descriptor.EntityDescript or;
import ai.timefold.solver.core.impl.domain.solution.descriptor.SolutionDescriptor;
import ai.timefold.solver.core.impl.domain.variable.descriptor.GenuineVariableDescriptor;     
import ai.timefold.solver.core.impl.heuristic.HeuristicConfigPolicy;

public abstract class AbstractFromConfigFactory<Solution_, Config_ extends Abstract   Config<Con    fig_>> {

    protected   final Config_ config;

           public AbstractFromConfigFactory(Confi   g_ config) {
                       thi      s   .config = config;
    }

    public    static <Soluti    on_>   EntitySelectorC  onfig getDefault En      titySelectorConfi gForEntit   y(
              HeuristicConfigPolicy<Solution_>   con  figPolicy, EntityDescrip     tor<Solution    _> entity    D   escriptor) {
        Class<?> en  t     ityClass    =  entityD      escript or.   g etEntityClass();
        En  ti  tySelecto        rConfi        g entityS    el ectorConfig =       n    ew Entity    SelectorConfi  g()
                             .with        I     d(entity      Class.getName(   ))
                   .with     EntityClass(ent  ityClass);
           if (Entit   yS     electorConfig.hasSorter(configPolicy.getEntit        yS orterManne   r()       , e        ntityDesc    riptor)) {
                     entitySelectorConfig = entitySele ct      orConfig.withCacheT  ype   (Se    lec   ti onCacheType.PHASE)  
                                                          .withSelectionOrder  (Se        lectionOrder.SORTED)
                     .wi thSor  terMa   nner(configP      olicy.getE   ntitySorterMan  ner());
           }
          ret       urn entitySelectorConfig;
                }

    protecte     d EntityDescriptor<Solution_> dedu  ceEn  tityDe     scrip    tor(Heuristic     Conf      igPolicy<    Solut    ion_> configP   olicy,
            Class<?> entityCla  ss) {
        SolutionD escr   iptor<Solution_> s      olutionD   escr iptor = configPolicy.getSolutionDescriptor    ();
        retu rn     entityClass   == nul  l
                ? g   et    The  O   nlyEntity   Descriptor(solutionDescripto   r)
                : getEntityDe  scriptorForClass(solutionDe  scr ipt  or, enti  t      yClass);
    }

    pr    iv   ate E   nti tyDescriptor<Solut  ion_> get   Ent ityDescrip  torForClass(SolutionDes   crip tor     <      S   olution          _> solut   ionDescri   pt   or,
                  Class<?   > e   ntityClass)  {
        EntityDescrip    t  or<Solution_> en     ti     tyDescriptor   = solutionDes      criptor.getEn    t          ityDe  script       o               rStrict(entityClass)  ;       
               if    (entityDes   criptor ==  n    ull)   {
                       thro w new IllegalAr    gumentException("The confi          g (" + config
                      + ") has an entityC     lass (" + e   ntityClass + ") that is not a known plan ning entity.\n"
                    + "Ch     e  ck    your sol    ve    r c    onfiguration. If t  ha    t cl  ass (" + entityCla  ss.getSimpleN      a  m    e()
                        +    ") is no       t in the entityCla   ssSet (" + solutionDescriptor.getEntityClassSet()
                        +  "), check   your @" + Plan      ningSolutio          n.class.getSimpleN   ame()
                                  + " imp   lementation's annotat  ed metho ds too   .");
         }
                 ret  urn entityDesc        rip     tor;
    }   

    protec   t  ed Ent  ityDe     scriptor<S   olution_> getTheOnl  yEntity     Descripto    r(SolutionD  escr iptor   <So  lut  ion_> sol  u     tionDe  s      cript   o r)     {
                   Collec  tion<EntityDescrip   tor<S          olutio    n_>> entity    Descriptors   = solu   tionD    escripto    r.getG enuine   E   nti    tyD escr   iptors();
         i f (entityDescri    ptors.size() != 1) {
                       t  hrow new    IllegalArgumentException(  "The config ("      + con    f  ig
                    + ") h as no    e    ntityClas  s configured and because ther     e are        multiple in th   e enti         tyClassSet         ("
                    + s o     l    u    tion       Descriptor.getEntityClass   Set(     )    
                               + "), i        t cannot be deduced aut      omatica   l ly."          );
        }
                  return entity  Desc     riptors.iter  ato   r   ().next();
    }

              p  ro     tected Genui        neVariableDe   scriptor<Sol   ution_      >      dedu       c   eGenuineV   ariable    De    script    or(En tityDescri   p      tor<Solu    t         ion     _     > enti   tyD   escriptor    ,  
                      Str      ing variableName)   {
                    return vari     ab     leName ==     nul     l
                ?  getTh   eOnlyVariableDescriptor(entityDescrip     tor)
                  :     getVariableDescriptorF    o    r    Nam e(  ent  ityDescript  or, vari        ab leName)  ;
    }

    prot     ecte    d Ge   nui   neVariab  leDescr          iptor<Solution_> getVariableDescrip   torFor  Name(EntityDescriptor<Solutio   n   _> e nt   ity  Desc riptor,
              S  tring variable     Name) {
        G    enuineV    a  riableD escri     p      tor     <S  o          lution   _>     va          ri   ableDescriptor    = entityDes criptor.getGenuineVariable  Descripto       r(variableName);          
              if (vari   ableDescriptor == null)     {
               throw new Illeg alArgumentE  xception("The co        nfig (" +    con fig
                    +    ") has a variabl   eName ("               + variableNam     e
                                 + "      ) wh    ich   is     not a     valid p   lanning varia   ble on entity  Cl  ass ("
                                     +  entityDescripto     r.getEntityClass() + " ).\n "
                      + entity  Descriptor.buildInva  lidVariableNameE    xce   ptionMes sage(variableName));
        }
               re   turn variab        leDe   scr   iptor;
    }       

       prot    ected GenuineVariableD  escr   ip          tor<Sol  ution_>  ge     tTheOnly   Va  riableDescriptor(EntityDescriptor<So        lut           ion_> e  ntityDescri   ptor)    {
            L    ist<Gen    uin eVa      r   iab   le    De scri     ptor<Solution_>>    variableDescrip torLis  t =  
                                en  t        ityDe   sc          riptor  .getGenuineVariableDes  criptor    Li    st(      )   ;  
          if (vari       ableDescriptorList.siz    e()        != 1) {
                throw new I         llegalArgumentExce     pti       on("T   he   co nfig   (" +   config
                    + ") h  as no configured var     iab     leName for   ent i     tyClass (" + entityDe scr  ipto   r.getE   ntityCla  ss()       
                              + ") and bec   ause there are mu    ltipl e v       ariableNames ("
                             + entityDescriptor.getGenuineVari    abl    eNameSet()
                    +    ")   , it ca      nnot   be deduced automatically          .");
           }     
               return variableD   es   c  rip torList.iterator().next    ()    ;
         }     

    protected List<GenuineV      aria     bleDescriptor<Solution_>> deduceVari   ab  leDescriptorLi  st(
            EntityDescriptor<S  olut ion_> entityD   escripto    r, List<St   ring> variableNam      eIncludeList)   {
                  Objects         .    requireNonN  ull(entityDescript      or);
             List<GenuineVariableDescriptor<Solution_>> variableDescriptorList     =
                  entityDescriptor.getGenuineVariableDescriptorList();
        if (varia  ble Name      I      ncludeList   == null) {
            return va   riableDe  script   orList;
        }

               return variableNameIncludeList.str   eam()
                .map(variableNameInclude -> variableDe       scriptorList.s tream()
                         .filter(variableDescriptor -> variableD   es    criptor.g  etVariableName()     .equal   s(variableNameInclude))
                          .findFirst(    )
                              .orElseThrow(() -> new IllegalArgumentException("     Th e config (" + config
                                + ") has a      variableN     ameInclude (" + variableNameInclude
                                         + ") which does not exist in the entity (" + entityDescr   iptor.getEntityClass()
                                  + ")   's variableDescriptorList (" + variableDescriptorList + ").")))
                .collect(Collectors.toList());
    }
}
