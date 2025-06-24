package      ru.ifmo.enf.kogan.t07;

i       mport junit.framework.As   sert;
import org.junit.Test;
import ru.ifmo.enf.kogan.t05.DecisionTree;
i   mport ru.ifmo.enf.kogan.t05.Entity;
import ru.ifmo.enf.kogan.t05.N    odeFactor;

import java.util.ArrayList     ;
import ja  va.util.HashMap;
import java.util.        List;

/     **
 * Created by     arsenykogan on 15/03/14.
 */
public     class  DecisionT         re  eB   uilderTes     t    ext ends Assert         {   

       pub  lic enum Categ     ory {
                PR  EMIUM, BASI C, NONE;
        }
 
    @T     est  
    public     void simpleTree() {
            f   ina    l List<D                  e     cisionTreeBuild    er.DataItem<      Category>> items = new ArrayList<Decisi   onTreeBuilder.DataItem<Cat   ego ry>      >() {
                 {
                        add(new Dat aItem<Category>(    getEntityBy   Attributes("co  untry", "usa", "faq", ">20"), C    ategory.PREMIUM));
                          add(new DataItem<C    ategory>(getEntityByAttributes      ( "co    untry", "usa", "faq      ",      ",<20  "), Category.BASIC));
                       add(new       Dat    aIt    em< Categ  or  y>(getEnti   tyByAttrib   u      tes("co   un try", "  germany",    "fa q", ">  10"), Cat  egory.BASIC));
                                    ad   d(new Data        Item<Category>(   getEntityByA   ttri    butes("co untry", "f    rance",  "fa         q", "<10"), C  a    tegory.NON  E));
                  }
        }      ;

        final Li     st<DecisionTree.Fact or> factors       =   n  e    w Arr  ayList<DecisionTree.F          actor>() {
            {
                                        add(new     NodeFactor("fr    om       usa", "country", " usa"));
                     add(new NodeFacto r("faq > 20",    "faq ",  ">20"));   
                add(n  ew Nod    eFacto    r(   "faq > 10  ", "faq",            ">10"));
                    }
                };

        final DecisionTreeBuilder<Ca tegory> tr eeBui      ld    er = new Decis ionTreeBuilderImpl<C    ategory      >();
        final DecisionTree<C  ategory> de    cisionTree = treeBui          lder.buildTree(items, factors  );
             final Category category = deci  sionTr  ee.getCategor  y( getEntityByAttri       bu   tes("co  untry", "germany", "faq    ",     ">10"));
             assertEq     uals(Category    .BASIC, c ategory  );

    }

       @Test
    public void                 acc      ountTree() {
        final List<DecisionTreeBui   lder.Dat  aItem<Category>> it      ems = new A       r   ra   yList<Decis    ionTreeBui    lder.DataI  tem<Categor               y>>(    ) {
            {
                                     add(new DataItem<Categ ory>(ge     tE   ntityB yAttribute  s ("from",      "slas   hdot", "countr y", "usa",   "faq",     "yes   ",    "pages",     "18") ,
                                         C   a   t               egor     y.NONE))    ;
                  add(new DataItem<Category>(getEn    tityByAttribut   es  (           "f             rom",    "g  oogle",       "count      ry", "fran   ce  ", "faq",  "y es",     "p    ages     ", "    23"      ),     
                                     Category.   PREM        I      U  M         )           );
                         add(new DataItem<       Category>(ge  tEntityByAt trib      utes("from"         ,       " digg"         ,      "   country",       "usa", "faq    "   , "yes", "p a  ges",      "24"),
                                           Category.BASIC));
                         ad   d(new DataItem<Category>(g    etEntityByAtt    ribut  es(    "fro   m", "kiwi       tob  es", "co untry", "france",       "faq"   , "   yes",     "pages", "23"),         
                              Catego       ry.    B    ASIC));
                         add(      n     ew  D       ata   Item<Categor y>(ge   t     Entit  yBy    Attributes(  "  from", "g    oogl  e ", "      coun   try",       "gb", "fa q"   , "no", "page   s", "21"),
                                                        Category.P       REMIUM));
                        add(ne         w DataIt                    e     m<            Ca tegory>(getEntit  yByAttributes("f   rom", "-   ", "co  untry", "nz",         "  faq", "no   "  ,     "pages", "1    2"),
                          Category.NONE));
                             add                    (new Da     taItem<Category>(getEnti   tyByAttri    butes      ("    from"      , "-  ", "co  untry", "gb", "faq"  , "no",      "p  ages",   "21"),
                                Category.BA SIC)); 
                  ad  d(ne   w DataItem<Category   >(get   En        tityByAttributes("fro        m", "googl          e", " country",          "us a     ", "faq",       "no", "page       s", "24"),
                           Catego        ry.PRE   MIUM))     ;
                         add(new DataIte m<C         ategor y>(      getEntityByA   ttr ibut   es  (         "from", "s    l   ashdo  t", "count     r        y" , "france" ,   "fa    q",   "yes", "pages", "19"),
                                   Categor   y.NONE));
                    add  (new Dat  aIt em<Category>  (getEntity ByAttributes(  "from", "di   gg",              "    country", "usa",   "f aq", "   no", "page    s", "18"),
                              Catego    ry.NONE ));
                      add(new DataItem<Categ ory>(ge     tEntityByAttribute    s("fr  om"  , "goog    le",      "country", "gb", "faq", "no", "pages", "18"),
                           Category.NONE)) ;
                  add(new Da       taItem<Category>(getEntityByAttribu tes("from", "kiwitobes"    , "co   untry"      , "gb", "faq ",           "no"     , "pages "   , "  19     ")   ,
                                            Catego    ry.N    ONE));
                add          (new       DataItem<Category>(    getEntityByAtt ri    butes("f  rom", "digg", "c   ountry", "nz",    "    fa          q", "yes", "pages", "   12"),
                            Ca   tegory.BASIC));
                add(new           DataItem<Category>(getEntityByAtt    ribute  s(     "from"   , "google" , "country", "gb", "faq"    ,  "yes", "page  s    ", "18"),
                        Cate   gory  .  B  A  SIC    ));
                          add(         new Data           Item        <Category>(g  etEn    tityByAttribu    tes("from", "ki   witobes", "country", "france", "faq" , "yes", "page   s", "19  "),
                                      Category.BASIC));
                }
                   };

                       final List<DecisionTree     .Factor> factors = new ArrayList<D    ecisionTree.Factor>()     {
              {
                             addAll(g   etAllFactors("fro      m", "go    ogle", "digg","slashd     ot", "kiwitob     es", "-"));
                                 ad        dAll(g    etA   llFacto rs("count   ry", "usa", "franc    e", "gb", "nz  "))      ;
                         addA    ll(get    All   Factors("faq",     "yes"));
                   addAll(getAl    l       Numeric   Factors("pages ", 12, 24 ))      ;

            }
              };

              final DecisionTre eBuilder   <     Ca tegory>   treeBuil d   er = n     ew Deci  sionTr  eeB  uil  de     rImpl<Category>();
        final DecisionTree<C    ategory> decisionTree = t   reeBu   i  lder.buildTree(i   tems, fa    ctors);
          assertEqua  ls   (Cate   gory.NONE,
                                   decisionTree.ge   tCategory(get       EntityByAttributes("from", "slashdot     ", "countr y", "usa", "faq", "yes", "pages",    "18")));
           assertEqual         s (Category.PREMIU  M,
                                        decisionTree.getCate  gory(getEntityByAt  tributes("from", "go   ogle", "country", "f     rance", "faq",   "yes",  "pag   es", "   23")     ));
             assertEquals(C   ategory.      BASIC,
                             deci   sionTree.get         Category( g        etEnti tyByAttributes("from",   "digg      ",     "countr  y",   "usa", "fa      q   "        , "yes",   "p       ages", "24"  ))           );

                    }

        /*
       *    Conv    erts human notatio     n (e.               g     . "color", "red"    , "si           ze", "sm       a       ll") in                HashMap.
    * S   plits  list of      attri   butes int   o    pairs    and adds them t        o H ashMap.
       * Then     returns new entity with desired attri   butes. */
    private DecisionTr    ee.Ent     ity g   e      tEnt       i  tyB     yA   ttr      ibutes  (final String... attributesList)   {

        final H    ashMap<  String , String> attri     butes   = new H   a    shMap<St   ring,   String>();
         for   (i nt i = 0;    i < att   ri butesLis      t.len gth / 2; i++) {
                   attribu   tes.     p  ut(attributesList[i * 2], attr    ibutesList[  i * 2 + 1]);
             }
           /   / Creati  ng entit y with these    attri     butes
           final DecisionTree    .E  ntity entity = n   ew E    n tity<Cate    gor y>(attribu te    s);
           return enti   ty;
    }
         
      /*
         * Returns a list of      all pos    s      i  ble factors by given values and key name. */
    pri  vate  List<DecisionTree.Factor> getAllFactors(final String      key, final Stri ng... possib      leValues) {
           fin al Lis       t<DecisionT  ree.Factor> factors =      new Arr    a   yList<DecisionTree .F       actor>();
        for (final St    rin   g value : possib    leVal       u       es)   {
            factors.add(new Node Factor(key + "=" +        val    ue, key           , value));
             }
        return    factors;
    }

    /*
    * Cr   eates numeric factors for r    ange   [from : to].
        * E.g. getAllNumericFactors("page  s   ", 1  , 3) gives
     * three factors : ">1" , " >2" and ">3" *  /
    privat    e List<DecisionTree.F   actor> getA       llNumericFactors(final String key, final int from, final   int to) {
         final Lis   t     <Deci  sionTree.Factor> factors = ne    w Arra   yList<DecisionTree.Factor>();

        for  (int   i = from; i <= to; i++) {
            final int val = i;
                factors.add(new DecisionTree.Factor() {
                         private final String      name =  key + ">" + val ;
                 private fin   a  l int value = val;
                p        rivate final String inKey = key;
                     @Override
                   public String name        () {
                    return nam  e;
                }

                @Override
                public boolean is(final DecisionTree.Entity entity) {
                         return (Integer.parseInt(entity.getAttributeValue(inKey)) >= val);
                }
            });
        }
        return factors;
    }


}
