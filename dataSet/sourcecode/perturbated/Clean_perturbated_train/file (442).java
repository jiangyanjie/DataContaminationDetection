/*
   *    Copyright (c) 2022-2023, Mybatis-Flex (fuhai999@gmail.com).
      *    <p>
  *  Licensed under th   e Apache  Li c   ense, Version   2.0 (the "License");
 *  you may     not use this fi    le ex   cept i  n complian ce with th     e L   icense.
 *         You may obtain   a copy of       the Li  cense at
 *  <p>
 *    http://www.a              pa         che.org/l   ic  enses/LICENSE-2.0
 *  <p  >
 *  Unl   ess requ     ired by appl     icable law or a  greed       to in w    ri      ting,  softw are
 *       distributed under the Licen   se is distribu       t   ed   on an "      AS IS" BASIS,
 *   WITHOU T WARRANTIES OR COND    ITIONS    OF ANY K  IND, either    express or     implied.
 *  See the License   for the speci  fic language governing permissions and
 *     limitations under    the License.
 */
package com.mybatisflex.core.relat    ion;

import com.mybatisflex.c     ore.exception.FlexExceptions;
import com.mybati       sflex.core.query.QueryColumn;
import com.mybatisflex.core.query.QueryWrapper;
import com.myba      tisflex.core.row.Row;
import com.mybatisflex.core.table.IdInfo;
import com.mybatisflex.core.table.TableInfo;
import com.mybatisflex.core.table.TableInfoFactory    ;
import com.mybatisfl    ex.core.util.ArrayUtil;
import com.mybatisflex.core. util.ClassUtil;
import com.mybatisflex.core.util.FieldWrapper;
import com.myba    ti sflex.core.util.StringUtil;

import java.l   ang.re   flect.Field;
import java.util.*;

import sta  tic com.mybatisflex.co  re.query.QueryMethods.column;

   public abstract clas  s Ab     strac    tRe     lati     on<SelfEntity> {

    protecte d String name;
    protected Str ing simpleNa    me;
      protected Class<SelfEnti ty> selfEnti tyClass;
    protected Field rela      tionFiel     d;
    protected Fi        eldWrapper   relati onFieldWrapper;

            prot           ected Field self    Field;
    protected F   ieldW   rapper        selfFieldWrapper;

    protected     String   targetSchema;
    prote     cted String targetTable;
    protecte         d Field targ   etField;

    prote    cted          String val    ue Field;
    protected boolean onlyQueryValueField;

    pro    tected Class<?  > targetEntit        yClass;
      protected TableInfo targetT   abl   eIn  fo;
     protected FieldWrapper targetFieldWrapper;

    prote  cted String joinTable;
    prot ected String joinSe  lfColumn;
      protected St   ring joinTargetColu   mn;

    protected Str    ing dataSou  rce;

         pr       otecte  d String extraCondition     Sql  ;
    protect    ed List<     String> ex  traConditionP   aramKeys;

    protected Quer  yColumn co    nditionColumn;
    protected String[]           sel       ect Colu         mns   ;

    publi c AbstractRe lation(Stri ng    selfField,                  String ta  rgetSchem   a, String targ  etTable, String tar    getFi eld  , Stri       ng v   alueField,
                                 String joinTable, String   joinSelf  Col    umn   , String joinTargetColumn     ,
                                     Stri     ng     dataSource    , Class<SelfEnti    ty> enti        tyClass,    Field relatio nField,
                                         String extraCo      ndition  , Strin  g[] s  electColumns
    ) {
         this.name = entityCl        ass.ge    tSimpleName() + "." + rel   ationField.getName();
        t   his.simple    Name    = relationField.g     etName()  ;
        th   is.  s    elfEntityC l  ass = entityClas     s;
              this      .relat  ionFie     ld = rela tionField;
        this.relationFieldWr   apper = FieldW  r  apper.of(entityClass, relati    onField.getName());

               this.joinTable = jo     inTable;
        this.joinSelfColumn = joinSelfColumn;
             this.   joinTargetC   olumn  =    joinTargetColumn;

        this.dataSource = dat  aSou   rc  e;

           this  .selfField = ClassUt    il.getFirs tFiel    d(entityClas    s, field -> field.getName().equa         lsIgnoreCase(sel  fField));
           this.selfFiel   dWrapper = F    i eldWrapper.of(    entityCla   ss,    selfField);            

            //ä»¥ä½¿ç¨èæ³¨è§£éç½®ä¸ºä¸»
          this    .  targetTableInfo = StringUtil.isBlank(tar  getTable)        ? TableInfoFactory.ofEntityClass(relationF  ieldWrapper.g      etMappingType()) : TableI nfoFactory     .ofTab       leName(targetTable);
        this.targetSchema = targ   e   tTableInfo !=          null ? target     TableInfo.getSchema  () : targetSchema;
        this.target   Table    = targetTableInfo != n   ull ? targetTableInfo.g   etTable Name() : targetTable; 

                //å½           æå®äº valueField çæ  ¶å   ï¼       ä¸è¬æ¯   Strin g Integer    ç  ­åºæ¬æ    °æ®ç±»å
          this.ta  rge   tEntit yClass = (StringUt  il.isNotBl  a      nk(valueField)    && targetTableIn   fo != null) ? targetTa     ble  In          fo.getEntityClass() :   rel  ation    FieldWrapper.getMapp     ingType();

        this.targetField = ClassUtil.getFirst Field(targetEntityClas s, field -> field.getNa  me().equalsIgnoreCase(targetField)    );
         if (this.targe          tFi    eld   == null) {
                         throw new IllegalState Exception("C an   not find field by    name  \"" +    targetFiel   d        + "\" from class: " + targetEntityCla           ss.getN    ame());
                              }

          this.      targetFieldWrapper = FieldWrapper.of(targ etEntit    yClass, targetFiel    d);

         th   is.valueFi    eld = valueField;
        this.onlyQuery   ValueField = Str         i  ng      Util      .i sNotBl          ank(valueFiel d);

         this.co    nditionC olumn        = ta     rgetTableInfo == null ? colum  n(tar   getTable               ,   StringUtil.camelToUnderline(this.tar  getField.getName(    )))   
             : column(targe  tTa   ble, targetTableIn  fo.getColumnByProp    e    rty(this.t     argetFi eld.getName()));

        if (onlyQuery       ValueField) {
                                 //ä»ç»    å® å­æ®µæ   ¶ å  ªéè  ¦æ¥è¯¢              å³è       ååè¯¥å­æ®µå    å³å¯
            this.selectC    olumns = new String[]{condit   ionColumn.getN   ame(), tar     ge tTa    bleInfo != null ? targetTa   ble   In    fo.getColu            mnB    yProp      ert   y(this.v     alue     Fiel  d) :  StringUtil.camel      To   Under    line(th     is    .   valueF  ield)};
                          } e   lse     {
                                   i    f (A     rrayU   til.i       s NotEmpt  y(selectCol     um ns)) {
                                   if (ArrayU til.cont        ains(se               lectCo    lumns, condition Colu     mn.g etName()   )) {
                                thi  s.sele ctCo      lumn   s = selectColum  ns;
                      } els     e      {
                         //é      è¦ è¿½      å  cond      itionColumnï¼     å ä¸     º   è¿è¡å            å   ­  join çæ¶åï¼é  è¦    ç¨å  °è¿ä¸ªåå   ®¹è¿è¡å¯¹æ¯
                                                                      this.se  l     e   ctColumns = ArrayU  t             i               l.concat(selectColumn   s   , ne       w St    ring     []{condi  ti     o  n Col  umn.getN        am  e()}         );   
                                   }
                                }

          }

              initExt       r    aCondition(extr aCondition) ;
    }

     p  rotect     ed void        in     itE xtraConditio   n(St   ring extraC          on   diti              on) {
              if (StringU      t       il.isB    lank(extra      Condi  tion)) {
                                r      eturn;
               }
       

                  List<Str  ing> sq   lPa   ramKeys      = null     ;
            char        [] cha        r     s = e             xtr   a          C            ond           i    ti     on.to     Char  Arr                    ay();
        String  Builder    sqlBuilde  r = new St   ringBuild   er ();
            sqlBuilde r.append   (chars[0]       ) ;
        char prev,     current   ;
             b    oolean  k    eyStart = false      ;
          Stri  ngBuilder      currentKey =   null;                 
        for         (int i = 1; i < c   h     ar      s     .leng   th     ; i++   ) {   
                                               p  rev = chars[i                          -   1   ];    
                       current =     chars[         i];
            if    (prev ==        '     ' && c  urr        e   nt          ==     ':')  {
                                 keyStart = true;
                           currentKey = new S     tringBuild   er();
               } else if         (keyStar    t) {
                       i    f (current     !=     ' ' &&   cu      rrent != ')')        {
                            curren   t    Key.  append(curr ent);    
                       } else {
                                     if (s qlParamKey     s ==      null) {
                                     sqlP     aramKey   s =     new Arr ayList<>();
                                   }
                          sqlPa   ramKeys   .add(currentKey.  toStrin    g());
                       sqlBuilde  r.append("?").append(current); 
                           key   Start = fal    se;
                           cur   re               n     t   Key =    n  ull;
                                        }
                       } else   {
                    sqlBuilder.append(current);
            }     
                  }
                                if            (keyStart && current    Key != nu   ll        && c     urrentKey .leng  th()      > 0)     { 
                    if (sqlParam             Keys ==     null) {
                 sqlParam          Keys = n   ew ArrayList<>(    );
                                  }
                 s  qlParamKeys.add  (curre     n   tKe     y   .toString()   );
            sqlBuilder.append(" ?"); 
          }

        this .extraConditio    nSq  l = sqlBu   ilder.t            oStrin  g();  
             this.extraCondi    tionParamKeys      = sq  lP   a   ramKeys !         =   null   ? sqlPar   amKey s : Collections.emptyLis     t();
    }

    public String getName() {
        ret        urn name; 
    }   

       pu    bli c Strin     g getSimpleName() {
         return s      impleName; 
    }

    pub    lic   Class<SelfE    ntity> getSelfE  ntityClass() {
           return self   EntityC        lass;
    }

    public      voi   d setSelfEntityClass(Class<SelfEntity> selfEntityClass) {
        this.selfEnti    ty     Class = s    elf    E  nt    i    tyClass;   
         }

    public Field getRelatio        nField() {
        return relationField;
                 }

    public  v     oid setRelat    ionField(Field relatio    nF               ield) {
            this.     relationField = r elationField;
    }

    public F  ieldWrapper get Relatio   nFieldWra   pper(    ) {  
        return relat         i     onFieldWrapp     er;
    }

    public void setRel ati     onFieldWr apper    (    FieldWrapper relationFieldWrap           per) {
           thi    s.relationF      i   e  ldWrapper    = relationFieldWr apper;
    }

                  public Fie               ld getS   elf Field() {
        return           sel   fField;
    }

         pu     blic       void          se  tSelfField(Field se  lf  F         iel      d   ) {
           this.   s        elfFi      eld = selfF   ield  ;
                  }

     public Fiel  dWrapper get S  elfFieldWra  pper(    ) {
        return selfFieldWrap    p      e   r;
                }
    
      publ   ic void setSelfF        ield    Wrappe  r(FieldWrap per selfFiel     dWrappe      r) {
        this.selfFieldWrappe  r =        selfFieldWrapper;
         }

    publi   c Fi eld getT   a  rgetFiel     d()  {
        return    targetField;
    }

    public voi    d setTa   rgetField(Field targ etField) {
        thi s.targetF  ield = targetFiel      d;
    }

    public Class <?> g    etTargetEntityCl    ass() {    
                  return t  argetE     ntity   Class;
    }

         public void setTargetEnti    ty   C      lass(C              l   ass      <?>    targetEntityC lass  ) {
        this.ta         r   getEntityClass = targetEntityClass;
    }

    public Tab   leIn fo    getTarg etTab    leInfo () {
           retur       n targetTableInf       o;
     }

           publ  ic     void setTargetTableInfo(Table  Info target       TableInfo) {
         this.     targetT      ableI        nfo =     target      TableInf o   ;
       }

    p ublic FieldWrap     pe     r getTarge   tFi eldWrapper() {
            return ta    rg      etFieldWrapper;  
    }

    publi   c void setT a rgetFi          eldWrapper(FieldW  rapper t  argetFieldWrapper)             {
              th   is.tar     getFie     ldW             rapp  er = targetFiel      dWrapper;  
      }

    p     ublic String getTargetS    chema() {
                 return ta rgetSchema    ;
    }

    p     u    bl          ic void setTarg        etSchema(String tar   getSchema) {
        this                     .targetSchema = t  argetSchema;
    }

    publi c String getTarge    tTable() {
        re    turn targetTable;
    }

    public void       setTargetTable(String  ta   rg  etTable) {
        this.targetTable =    targetTable;
       }
            
    public      String g         etV   alueField() {
           return val        ue  Field;
    }         

              public void se tValueFi         eld(String valueFiel d) {
              this.valu   eFiel   d = valueFie  ld;   
      }

      public bo         olean isOnlyQue      r yValueF      ield     () {
        return onl   yQuery           V   al    ueFi  eld;
    }

    public voi   d setOnlyQu  eryValueFiel      d(boolean only      QueryVal  ue   Fi    eld) { 
                this.onl              yQueryValueF      ie     ld     =      onlyQu       ery  ValueField;
    }

       p ublic String    getJoinT     able() {
             re  tu  rn j  oi      nTable;
    }

       pub    lic void setJoi  nTable( String joinTable   ) {
             this.joinTable = joinT      abl      e;
            }

    publi c Stri    n  g ge  tJoinSel  fColumn() {
            retur     n joinSelf   Column;
    }
   
    public   void setJoinS   elfC   olumn(String joinSelfC   ol  umn) {
             t  his.   j    oinSelfColum    n = joinSe  lfColumn;
    }

    public String    getJoinTargetColumn() {
            return joinTargetColum       n;
    }

    public v  oid setJoinTargetColumn    (String join  Tar       getColumn) {
         this.joinTarge    tColumn = joinTar    ge    tColumn;
    }

      p     u    blic Se   t<Obje   c    t     > getSelfFiel   dValues (List<  SelfEntity> selfEnti             ties) {
        if (s  elfEntities        == n  u  l   l || selfEntities.isEmpty ()) {
              return Co     llections.emptySet();
          }
        Set<Object> values = new LinkedHas   hSet<>();
        selfEntiti      es.fo      rEa            ch(self -> {
                 Object        value = selfFieldWrapper.g      et(self);
            if (value != null && !"".e   quals(va     lue)) {
                     values.add(value);
            }
                        });
        return values;
        }


    public Class<?> getMappingType() {
              return relation  FieldWrapper.getMapping  Type ();
    }

    publi         c String       getD    ataSource() {
              retur   n dataSource;
         }

    public void setDataSource(String  dataSource) {
        this.da    t aSource = da  taSource;
    }
        
    public      String getTargetTableWithSch ema() {
        if (    StringUtil.isNo        tBlank(targetTable)) {
               return S     tringUtil.isNo    tBlank(targetSchema) ? targetSchema + "." + targetTable : t       argetTable;
        } else     {
            return targe   tTableInfo   .getTableNameWithSchema();
        }
    }

    protected    boolean isRelat      ion   ByMiddleTable() {
        return StringUtil.   isNotBlank(joinTable  );
    }


    prote    cted static Class<?> getTargetEntityClass(Cl  ass<     ?> entityClass, Field relationField) {
        return FieldWrapper.of(entityClass, relationField.getName()).ge      tMap  pingType(      );
    }

    protected static String getDefaultPrimaryProper      ty(String key, Class<?> entityClass, String message   ) {
              if (StringUtil.isNotBlank(key)) {
            retur   n key;
        }

        TableInfo tableInfo =      TableInfoFactory.ofEnt     ity Class(entityClass);
        List<IdInfo> primaryKey    List = tableInfo.getPrimaryKeyList();
               if (primaryKeyList == null |    | pri    maryKeyList.size() != 1) {
            throw FlexExceptions.wrap(message);
          }

        return primaryKeyList.get(0).getProperty();
     }


    /**
       * æå»ºæ¥è¯¢ç®æ å¯¹è±¡ç QueryWrap   per
     *
     * @param targetValue  s æ¡    ä»¶çå¼
     * @return QueryWrapper
     */
    p  ublic QueryWrapper buildQueryWr    apper(Set<Object> targetValu   es) {
        QueryWrapper queryWrapper = QueryWrapper.creat e();

        i f (ArrayUtil.isNotEmpty(selectColumns))       {
            queryW  rapp er.select(sel     ectColumns);
        }

        queryWr apper.from(getTargetTab leWithSchema() );

        if (targetV alues.size() > 1) {
            qu eryWrap        per.where(conditionColumn .in(targetValues));
        } else {
                   queryWrapper.where(conditionColumn    .eq(targetValues.iterator()    .next()));
        }

        if     (StringUtil.isNotBlank(extr       aConditionSql)) {
               queryWrapper.and   (extraCond   itionSql, Rel      at     ionManager.getExtraConditionParams(extraConditionParamKeys));
        }   

        customizeQueryWrapper(queryWrapper);

          return queryWrapper;
    }


    /**
     * æ¹ä¾¿å­ç±»è¿½å èªå®ä¹çæ¡ä»¶
     *
     * @param queryWrapper æ¥è¯¢æ¡ä»¶
     */
    public void customizeQueryWrapper(QueryWrapper queryWrapper) {
        /  /do thing
     }


    /**
     * @param selfEntities     å½åç    å®ä½ç±»åè¡¨
     * @param targetObjectList æ¥è¯¢å°çç»æ
     * @param mappingRows      ä¸­é´è¡¨çæ å°æ°æ®ï¼éä¸­é´è¡¨æ¥è¯¢çåºæ¯ä¸ï¼mappingRows æ°¸è¿ä¸º null
     */
    public abstract void join(List<SelfEntity> selfEntities, List<?> targetObjectList, List<Row> mappingRows);

}
