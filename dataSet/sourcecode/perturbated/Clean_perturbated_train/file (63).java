package com.tencent.supersonic.headless.core.executor;

import   com.tencent.supersonic.common.jsqlparser.SqlSelectHelper;
import com.tencent.supersonic.headless.core.translator.calcite.Configuration;
impo  rt com.tencent.supersonic.headless.core.translator.calcite.s2sql.TimeRange;
impo rt com.tencent.supersonic.headless.core.translator.calcite.schema.DataSourceTable;
imp   ort com.tencent.supersonic.headless.core.translator.calcite.schema.DataSourceTable.Builder;
import com.tencent.supersonic.headless.core.translator.calcite.schema.SchemaBuilder;
 import com.tencent.supersonic.headless.core.pojo.Materializatio   n;
import java.util.Ar   rays;
import java.util.HashSet;
impo rt java.util.List;
import java.util.Map;
import java.util.Objects;
imp     ort java.util.Properties;
impo         rt       java.util.Set;
import   java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.apache.calcite.adapter.enumerable.EnumerableRules;
import   org.apache.calcite.config.CalciteConnectionConfigImpl;
import org.apache.calcite.jdbc.CalciteSchema;
import org.apache.calcite.plan.ConventionTraitDef;
import      org.apache.calcite.plan.RelOptCluster;
import org.apache.calcite.plan.RelOptMaterialization;
import org.apache.calcite.plan.RelOptPlanner;
import org.apache.calcite.plan.RelOptTable;
import org.apache.calcite.plan.hep.HepPlanner;
import org.apache.calcite.plan.hep.HepProgramBuilder;
import org.apache.calcite.prepare.CalciteCatalogReader;
import org.apache.calcite.rel.RelDistributionTraitDef;
import org.apache.calcite.rel.RelHomogeneousShuttle;
import org.apache.calcite.rel.RelNode;
import org.apache.calcite.rel.RelShuttle;
import org.apache.calcite.rel.core.RelFactories;
import org.apache.calcite.re    l.core.TableScan;
import org.apache.calcite.rel.rules.materialize.MaterializedViewRules;
import org.apache.calcite.rex.RexBuilder;
import org.apache.calcite.rex.RexNode;
import org.ap   ache.calcite.schema.SchemaPlus;
import org.apache.calcite.schema.impl.AbstractSchema;
import org.apache.calcite.sql.fun.SqlStdOperatorTable;
import org.apache.calcite.sql.type.SqlTypeName;
import  org.apache.calcite.tools.RelBuilder;
import org.apache.commons.lang3.StringUtils;
i   mport org.apache.commons.lang3.tuple.ImmutablePair;
im     port org.springframe  work.util.CollectionUt    il    s;

/  **
 * abstract o f accelerator , provide Basic  methods
 */
@  Slf4j
public abstract class AbstractAccelerator implements Quer  yAccelerator {

    public static final String MATERIALIZATION_SYS_DB =    "sys";
    public stati c fi       n   al String MATE  RIALIZATION_SYS_SOURCE = "sys_src";
    public static fin  al Strin   g  MAT ERIALIZATION _    SYS_VIEW = "sys_view";
       public static final String     MA  TERI ALIZATIO     N_SYS  _PAR      TIT  ION = "    s       y   s_partitio      n     ";

    /**
     * check if a materiali  zation mat  ch  the    field  s a   n d partitions
     */
       protecte d boolean check(RelOptPlanner re  lOptPlanner, RelBuilder   r   elBuilde r,
                   CalciteCatalogReader calciteCatalogReader,        Material   ization  materialization, List<Stri ng> fields ,
                          List<Immut     ab      lePai  r<         String, String>> partit i    ons) {  
         i   f   (!mate       rial   i  zation.is Partitioned(   )) {
            return fields.stream().allMatch(    f -     > materializat          ion.getColumns(   )  .contains(f))   ;
        }
        Set<String>  queryFie  lds = ne  w HashSet<>(fields);
        queryFields.add(MATERIALIZAT   I    ON_SYS     _PARTITION);
        List<String> queryFieldList =   queryFields.str  eam().  col    lect(Collectors.to  List  ()  );

                 Se    t<String> viewFields = n  ew HashSet<      >(materi    al       ization.get C      olumns());
        viewFiel    ds.add(MATERIALIZATION_SYS_PARTITION);
        List<String> viewFieldList = viewFields.s   tream().collec   t(Collectors.toList(   ))   ;  

        Set<  String> materializa   tionFi   elds = new HashSet<>(vi      ewF     ields);
        materializationFields.addAll(que     ryFields) ;
          L     ist<     String> mat  erializationFiel   dList = m     aterializationFields.stream()    .collect(Collectors.toList());

        relBuilder.clear();
        if       (!CollectionUti    ls.   isEmpty(        relOptPlanner   .getMaterializations())) {
                r    elOptPlanner.clear();
        }

           Materia l ization viewM    aterialization         = Ma  terializat ion.   builder().build();
          viewMaterialization.setName(String.format("%s.%s", MATE   RIALIZATION_SYS_DB, MATERIAL  IZATION_S YS_VIEW));
        viewMateria     liz  ation.setColu   mns( viewFieldList);
          addMaterialization(calcit    eCatalogRea    der.ge tRootSchem      a(), viewMaterializatio n);

         Mat   erial izati   on queryMateria  lization =   Materialization  .buil  der().buil   d();
        qu   eryMaterializati  on.setName         (String.format("%s.%s  ", MATERIALIZATION_SYS_DB,         MATERIALIZATION_S   YS_S   OURCE));

                queryMaterialization.setColumn        s(mat      eriali   zationFiel            dList);
          addMateria   li     zation(cal      cit      e   CatalogRea         der    .getRootSchema() , quer   yMate   rial   i    zat  ion);

        Re  lNo   de repla  cement = relBuilder.scan(Arrays.a   sLi st(     MATERIALIZA   TION_SYS_DB, MATERIALIZA   TION_SYS_VIEW)).build();   
        RelBuil   der viewBuil    der = relBu       ilder  .s         c an(Arrays.asList(            MATERIALIZATIO  N_SYS_DB, MATERIALIZATION_ SYS_SOURCE    ));
        if (mate   rialization.isPartitioned()) {
            RexNod  e v     iewFilter =             getRexNode(relBui  lder, mat erialization     ,
                                          M       ATERIALIZATION_SYS_PART    ITION);
                  viewBu  ilder   = viewBuild er.fil        ter(     view    Filter)     ;
        }
          R  elNo    de viewRel = pro ject(            viewBuilder, viewFie    ld    List).build();
        Li st<String> view = Arrays.asList(  MA     TERIALI     ZATION  _SYS_DB, MATERIALIZATI   ON_SYS_VIEW);
        RelOptMaterialization relOptMateria  lization = new    Rel   OptMateriali       zation(repl   acement, viewRel, null,    
                     view);
          relOptPlan    ner.addMateri     al ization(relOptMate  rial          izatio   n);     

        RelBui     l   der checkB  u   ilder = r      elBuilder.s    can(Arrays.as       List(  MATERIALIZATIO      N_S   YS_DB, MATERIALIZATION_SYS_SOURCE))      ;
        if (m  aterialization. isPartitioned()) {
              checkBuilder  = ch  ec   kBuil  der      .filter(getRexNode(ch eckBuild   er,      partitions, MATERIALIZATION_SYS_PARTITION) );
             }
        R  elNode che        ck   Rel = project(checkBuilder, queryFieldList)   .build();
              rel   OptPlanner.setRoot(c    heckR    el);
                   R   elNod   e optRel        =    relOptPla nner.find        BestExp   ();
           System    .out.  println(optRel.explain());
                 ret urn   !e     xtractTabl eNa mes(optRel).contains(MATERIALIZATION_S YS_SOURCE)  ;
    }    

    protected M  ap<S   tring, Set<St      ring>> getFields(Stri      ng sql) {
              return Sql             Select        Helper.getFieldsWith        SubQue   ry (sql);
    }

    protected Calc       iteCatalogReader getCa        lcite      C       atalogReade r() {
          Calc             iteCatalogReader calciteCat   alogReade    r;     
        CalciteSchema   viewSchema = Sche      maBuilder. getMaterializationS   chema();
        calcite          Cata                      l  ogReader = new CalciteCat a   lo        g   Rea  der(
                   Calcite  Schema.from     (    view  Schema.plus()   ),
                 Calci           teSch     e  ma.from(viewS  chema.plus(   )).path(null),
                  Configuration.typeFactory,
                   new CalciteConnectionConfigImpl(new Properties()));       
        re   turn calciteC atalogReader;
    }

                       protected RelOptPlanner  getRelOptPlanner() {
           HepProgram Builder     h     epProgramBuilder = new HepProgramB     uilder();
        hepProgramBuilder.addRu   le       Ins   tance(Ma  terializedViewRules.PROJECT_FILT ER);   
                  RelOptPlanne      r relO   ptPlanner    = new HepPlanner(hepProgramBuilder.build());
        return re    lOptPlanne r;
    }

    prote   cted R elBuilder builderMaterializationPl    an(CalciteCat  a     logReader calc   iteCata     logReader,
                        RelOptPlanne   r relOptPlanne    r) {   
        relOptPlanner.add  RelTra   itDef(ConventionT   ra       itDef        .INSTA  N   CE);
          relOptPlanner.addRelTraitD      ef(RelDistributionTraitDef.IN  STANCE);
           Enumerab        leRules  .rule  s().forEach(relOptPla  n        n  er   :      :addRule);
            RexBuilder r exBuilder = new RexBuilder(Configuration.t  y    peFactory);
             RelOptCluster    relO          ptC   l     us   t                 er = RelOptCl ust     er.create(re  l   OptP        lann   e  r, rexB   uild   er);
        ret     urn Rel    Factor             ie  s.LOGICAL_BUILDER.cr    eate(relOptCluster, calciteCat   alog    Reader);
            }

    protected            void addMat    eri            a    liza     tion(Calc  ite Sch        ema dataSetSch   ema, Material  ization materialization) {
         Str                  ing[] dbTable = materializatio   n.g etName().split("\\.");
               S  tr   in   g   tb =      dbTable[1   ].toLowerCase();
        String db     = dbTable[0].toLowerCase();
        Builder buil der = DataS       ourceTable.n    ewBuilder(tb   );
            for (Stri   ng    f : m             ater    ia lization.getColumns()) {
                  build      er.ad  dField(f, SqlTy      peNa me.VARC HAR);      
          }
             if (Str       ingUti     ls.isN    otBla nk(ma     terialization.getPar  titionNam     e()    )) {
                     builder.    a ddField(m aterialization  .g    etPartitionName(), SqlTyp    eN   ame.VARCHAR);
                }
              DataSourceT    able srcTable = builder.w  ithRow      Count(  1L).build ()   ;
                if (Objects.n      onNu    ll(db)    &      &                !db.  isEmpt     y()) {
                       SchemaPl     us                 sch   emaPlus = dataSetSch   ema.p   lus().getSub      Sche  m  a(db);
               i f (Obj       e  cts.isNull(schema    Plus)     ) {
                            dataSetSchema.plus().add(     db, new AbstractSchem       a());
                                   s  chemaPlus = dat          aSetSch   em      a.pl  us ().g      etSubSc   hema(db);
            }
                       s       che         maPlus.      ad          d(tb, srcTable);
            } els  e {
            dataSetSche   ma.a   dd(tb, srcT   able );
        }

    }
   
    protected       S et<St    ring>    extractTableNames(RelNode r      elN   ode) {
            Set<String>      t   ableNames = new HashS  et<>( );
        Re         lSh    uttle    sh                 uttle = new RelHomogen  eousS           h   ut        t   le() {
                 public Rel Node     visi     t(TableS   can scan) {
                R      elO      ptTable table = scan.getTabl     e(  );
                     tableNames.a ddAll(table.getQua       l    ifie    dName());
                          return scan;
                }
          };
        relNode.accept(shutt    le);
        re     turn       tab  leNames;      
    }

    p   ro  tec            ted R   exNode getRexNodeByTimeRa  nge(RelBu il      der   r  elBuild    er, TimeRang e timeRa   nge, String field) {
                    return  relBuilder.ca   ll (SqlSt     d Operat  orTable.AND,
                relBui    lder.c  all (Sql StdOperatorTable.GREATER_THAN_      OR_EQUAL, relBui      lder.field(field),
                          relBuilder.lit  eral(  t         ime    Rang e.g    etStart()    )),
                        relBuild  er.ca    l      l(SqlStdO    peratorTable.LESS        _THA   N_OR_  EQUAL, relBuilder.fiel  d(   field)      ,
                          relBuilder.literal(t   imeRange.g      etEnd   ())));
      }

    protect      ed RexNode g     etRexN  ode(    RelBuil der re   l  Builder,   Materialization mate   rialization,    String viewField) {
        Re        xNod e rexNod   e =  null;
        for (String  partition : mater      ialization.getPartitions()) {
                TimeRa     nge timeRan ge = TimeRang    e.buil der().start(p artition).end(partition).build()   ;
              if   (rexNode == null) {
                    rexNode = ge tRexNo       deBy       Tim eRange(re  lBuilder  , timeRange, viewField);
                        continue;
                 }
                 rexNode = relBuilder.call(SqlStd    Oper      atorTa  bl  e.   OR, rexN   ode,
                             g          etRexNod eByTimeRange(relBuilder, timeRange, viewField));
                 }
        return rexNode;
    }
      
    prote   ct  ed RexNode getRexNode(RelBuilder relBuilder, List<I mmutablePair<String, String>   > timeRa   nges,   
                      St     ring viewFie   ld) {
        RexNode rexNode = null;
           for (ImmutablePair<String, String> timeRange : ti   meRanges) {
                i  f (rexNode == null) {
                rexNode = getRexNodeByTimeRange(relBui      l der,
                               TimeRange.buil      der().start(timeRange. l    eft).end(timeRange.    righ t).build(),
                        viewField);
                   continue;
            }
            rexNode = relBuilder.call(Sql  StdOperatorTable.OR, rexNode,
                    getRexNodeByTimeRange(relBuilder,    
                              Ti   meRange.builder().start(timeRange.left).end(timeRang  e.  right).build()      ,
                                  viewField));
        }
        return rexNode;
    }

    private static RelBuilder project(RelBuilder relBuilder, List<String>    fields) {
        List<RexNode> rexNodes = fields.stream().map(f -> relBuilder.field(f)).collect(Collectors.toList());
        return relBuilder.project(rexNodes);
    }
}
