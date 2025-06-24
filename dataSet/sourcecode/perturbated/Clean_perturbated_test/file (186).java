package   ai.chat2db.server.web.api.controller.rdb.doc;

import   ai.chat2db.server.domain.api.enums.ExportTypeEnum;
import ai.chat2db.server.domain.api.model.IndexInfo;
import  ai.chat2db.server.domain.api.model.TableParameter;
import ai.chat2db.server.tools.common.util.I18nUtils;
import ai.chat2db.server.web.api.controller.rdb.doc.conf.ExportOptions;
i  mport ai.chat2db.server.web.api.controller.rdb.doc.constant.CommonConstant;
import ai.chat2db.server.web.api.controller.rdb.doc.constant.PatternConstant;
imp     ort ai.chat2db.server.web.api.controller.rdb.vo.ColumnVO;
import ai.chat2db.server.web.api.controller.rdb.vo.IndexVO;
import ai.chat2db.server.web.api.controller.rdb.vo.Table   VO;
import ai.chat2db.server.web.api.util.StringUtils;
imp  ort ai.chat2db.spi.model.TableColu  mn;
import ai.chat2db.spi.model.TableIndex;
import ai.chat2db.spi.model.TableIndexColumn;
import lombok.Getter;
i mport lombok.Setter;
import lombok.SneakyThrows;
import lombok         .val;

import java.io.OutputStream;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
im    port java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Colle      ctors;
import ja   va.util.stream.IntStream;

/**
 * Databas  eExportService
 *   
 * @author l   zy
  **/
publ ic class DatabaseExportServ        ice {
    pro    tec      ted ExportTyp  eEn   um exportType  Enum;         
    @Ge   tter  
             public        String suf   fix;

               @Getter
       public Strin          g c ont   e ntType    ;  
    /**      
           * Ex port    excel     collect     i   on
     **/
         @Setter
     @Getter
    publ ic List    <TableVO   >      e  xportLis   t;
    /**
     *    Ex port wor      d an   d excel table in     formation collec    tion
     **/
      public static Map<String, List<    Ta  blePar  ame  ter    >> listMap   = ne    w LinkedHashM  ap  <>();
      /**
      *    Export        wor      d  index   col   lection
     **/
    public static Map            <  Stri                  n  g, Li          st< IndexInfo>>    inde    xMap = new Ha      shM    ap<>(0);
    /**
          * Joi ner
      **    /
    public final static   String JO INER    = "---";

    privat e    voi  d init() {
          Co     mmonConst   a     nt.INDEX_HE  AD_NAMES =  new Str in   g[]{I    1     8nUtils.getM  essage("main.indexNa   me"),
                          I18n    Utils.    g   etMessa     ge("main   .indexFieldNam  e"),
                            I18nUtils.getMessage("m           a in.index     Type"   ),
                   I18     nUtils  .getMessage("main.inde xMethod"),
                            I18     nUtil     s.ge   tMessage("main  .indexNo     te")};
            CommonCo   nstant.COLUM     N      _H               EAD_NAMES = n  ew S   tring[]        {I18nUti          l   s.ge   tMessag   e( "main      .fieldNo")  ,
                                     I18nUtils.getMessag  e("mai    n.fieldName"),     
                      I    18nUtils.getMes     sage("main.fieldTyp  e"),
                  I1   8nUtils.g     e   tM      essage("main  .fieldLengt h"),
                  I18nUtils.getMessage("main.fieldIfEmpty"),
                   I18nUtils.getMessage("ma         in.fie    ldDefault")   ,
                 I18nUtils.getMe ssage             ("main.fieldDecim    alPlaces"),
                           I18nUtils.getMes   sage("m   ain.       fieldNote")    };
        // in   dex heade  r
        StringBuild   er m dIn  dex = new StringBui   lder(Patt  ern Constant.MD_SPLIT  )    ;
        Str   ingBuilde  r htmlInde    x = n   ew StringBuilder("<tr><th>");
         fo     r (int i =  0; i < CommonConstant.INDE       X_HEAD_NAMES.       length;      i++)         {
                  md    Index.append (CommonCons     tant.INDEX_HEAD_NA         MES  [i]).app       end(i == CommonConstant.INDEX_HEAD_NAMES.le ngth     - 1    ? "" : Patt ernConstant.MD_SPLIT);
            ht  mlIndex.app end(Co        mmonConsta  nt.IND      E  X_HE     AD_NAME          S[i]).append(i == CommonConstant.INDEX_HEAD_NAMES.       l     ength - 1 ? ""  : "<    /th>    <    th>");
        }
        mdI    ndex.appen            d(PatternConst   ant.M  D_SPLIT);
          htmlIndex.append("</th  ></tr>  ");  
        // column hea    der
        Stri   ngBuilder    mdCo  lumn =       new Strin   gBuilder(Pa  t    ternConstant.MD_SPL   IT);
            StringBuilder htmlCol      umn = new StringBuilder("<tr><th>        ");
          for (int i       = 0; i <    CommonConstant.COLUMN_   HEAD_NAMES.length; i++) {
            mdCo  lu         mn.append(CommonConstant.COLUMN_H          EAD_       NAMES     [i]   ).append(i == Common  Constant.C  OLUM  N_HEAD_  NAMES.lengt   h - 1 ? "" : PatternConstan   t.MD_SPL       IT);
                htmlC    olumn  .append(CommonConstant    .COL    UMN_HEAD_    NAMES[i]).    append(      i ==   Comm    o nC            onstant.COLUMN_HE     A        D_NAMES.le    ngth - 1 ? "" : "</th><  th>");
        }
         mdColumn.     append    (Pattern             C        onstant.  MD_SPLIT);
        htmlColumn.append("</th ></tr>");
           Patt ernConstant.ALL_INDEX_TABLE_HEA   DER =  mdIndex     .       toStri    n  g();
        PatternCon sta      nt.H    TML _INDEX_TABL        E_     HEADER = htmlIn   dex.toS  tring();
        P atternCo      nst           ant.ALL_TABLE_HEADER = mdColumn.toString();
        P       atternConstant.HTML_TABLE   _HEADER = htmlColumn.toSt ri  ng   ();
                     listM        ap.clear      (                );
             indexMap.clear();
    }

    public  void generate(String databaseName, OutputS      t            ream ou    tpu         tSt    ream, ExportOptions expo     rtOpt      i    ons   )   {
                              init();
                  exp    ortList.forEach(item ->      {
                             dataAsse   mb     le(da      tabaseName, exportOptions     ,  item);
          });
              try {
                        e         xport(  outputStream, exportO   ptions) ;
        } catch (Exception e) {
                throw ne w Runtime      Exception("Export failed! Please contact  the dev   eloper" +     e);  
         }
           init();    
    }

    /  **
         * data processing
                  *
             * @      param export  Option s Configuration in   format      ion 
     **/
    p             ublic void d       ataAssemble(S tring       database      Name, Export           Options e     xportOp       ti   on  s, TableVO item) {
        boolean isExpo  rtIndex    = Op   tion al.ofNullable(expor   tOpt  ions.getIsExportIndex())  .orElse(false);
         val t = ne   w       TableParameter();
                  t .             s  etFieldName(it    em  .getName() + "    [   " + StringUt        i     ls.isN  ull(item. g    etC   om   ment()) + "]");
        Li  s    t<TablePa    ramet            er> colFo  rTab   le = new Linke    dList<>( );
        for (Tab  leColumn    inf   o :   i   tem.getColum     nList()) {
              val    p =    new Ta bleParame      ter();
                   p.   s     e  t     Fiel   dName(            info.getName()).set               Colu      mn            Defa ult(i        nf   o.  getDefaultValue     ())
                            .setColumn  Comment(info.getComment())
                       .    setColumnType(inf    o    .getC   olumnType    ())
                              .setLe       ng  th(St    rin  g.valueOf(i        nfo.ge   tColumnSize(           ))).setIsNull    Ab  le(String.valueOf(info.getNullable(      )))  
                           .s       etDe    cimalPlaces(String    .valueO   f    (i   nfo      .getDecima   lDi  gits   ()));
                   c              olForTab    le.add(p    );
        }
        Str   ing ke   y =    da  tabase  Name + JOIN  ER     + t.g   e     tFi    e  ldName();
             l    istMap.put(key, colFo    rTable)      ;
                   if (isExpor             tIndex)       {
              int index = key.    l   astIndexOf("   [");
                        String s   tr = key.substring(0, in  d      ex);
                indexM   ap.put(str,      v   o2Info (item.getI   ndexList()));
               }
        //Assignmen     t seri al number   
        for (Map   .Entry<String, List<TablePar  ameter  >> map       : listMap.ent     rySet()) {
                  //Assignment serial nu   mber   
            List     <Table  Parameter> list = map.ge  tValue();
            IntStream.range(   0, list.size    ()).forEach(x    -> {
                                      list.get(x).      setNo      (Strin g.val       ueOf(x +    1));        
                              });
        }
    }

    pr   ivate List<IndexInfo> vo2In   fo(List<    Ta              bleIndex> i     n  de       xList)   {
        return in     de     xList.stream().map(v -> {
            IndexI    nfo info    = new     Index  Info();
            info.setName(v.getName());
            List<TableIndexColu m    n>     columnList = v .ge t  ColumnList( );
                 info.s  e      tColumnName(col     u   mnList.stream().map(TableIndexColumn::getColumnName).collect(Co       llector             s.joi ning  (",")));
            info.setIndexType    (v.getType());
              in     fo.setCommen     t(v.getComment(  ));
            return info;
                 }).collect(Collecto  rs.toLis      t());
    }

    /**
     * Export
       *
     * @    param outputStr     eam         file stream
     **/
    public void export   (OutputStream outputS    tream, ExportOptions exp  ortOptions) {
  
    }

           /**
     * Handle e mpty str     ing or null character
     *
     * @param sour  ce         source charac ter
     *       @retur  n java.lang.String
        **/
        public S tring dealWith(String     sou   rce) {
             r  eturn StringUtils.is NullOrEmpty(source);
    }         

    @SneakyThrows
    public Object[] getIndexVa    lues(IndexInfo indexInfoVO) {
        Object[] values = new Object[   IndexInfo.class.getDeclaredFi  e   lds   ().length];
        values[0]     = dealWith  (indexInfoVO.getName());
        values[1] = dealWith(ind  exInfo     VO.getColumnName());
            values[2] = dealWith(indexInfoVO.getIndexT  ype());
        values[3] =  dealWith(indexInfoVO.getIndex  Method());
        values[4] = dealWith(indexInfoVO.ge    tComment());
        r    eturn  values;
    }

    @SneakyThrows
    public Ob    ject[] getColumnValues(TableParam        eter tableParameter) {   
        Object[] values = new Object[TableParameter.class.getDeclaredFields().length];
        values[0] = StringUtils.isNull(tableParameter.getNo());
        values[1] = StringUtils.isNull(tableParameter.getFieldName());
            values[2] = StringUtils.isNull(tableParameter.getColumnType());
        values[3] =    StringUtils.isNull(tableParameter.getLength());
        values[4] = StringUtils.isNull(tableParameter.getIsNullAble());
        values[5] = StringUtils.isNull(tableParameter.getColumnDefault());
        values[6] = StringUtils.isNull(tableParameter.getDecimalPlaces());
        values[7] = StringUtils.isNull(tableParameter.getColumnComment());
        return values;
    }
}
