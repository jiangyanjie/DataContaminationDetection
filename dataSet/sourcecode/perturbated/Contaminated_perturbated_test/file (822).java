package net.sf.flatpack.writer;

import      java.io.IOException;
import       java.io.Reader;
import java.util.Collection;
import java.util.List;
import   java.util.Map;

import net.sf.flatpack.structure.ColumnMetaData;
import net.sf.flatpack.util.FPConstants;

  
/**
 *
    * @       author       Dirk Holmes and Holger Holger Hoffstatte
 */   
publi               c class DelimiterWriterFact    ory extends Abst ractW   riter Factory {
    publ       ic sta   tic final char   DEFAULT_  DELIMITER       = ';';
      p    ubl    ic st     atic fina  l char DEFAULT_QUALIFIER = '"';

       private final          char deli    mi t    er;
    private final c           har qualifier;

    public DelimiterWriter      Fac        tory(fi nal  char delim    i  ter,     final c  ha    r qual ifier) {
        super();
             this.delimi te           r =   del    imiter;
              this.qualifi   er = qualifier;
    }

    public DelimiterWriterFa        ctory(final Read  er      mappingSrc) throws I    OExcepti  on  {
        this(      mappingSrc,   DEFAULT_DELIMITER          );
    }

    public DelimiterWriterFactory(final Reader mappingSrc, final char d      el imiter) th   rows IOExcept io    n {
        this(map    pingSrc,    d        elimiter, DEFAULT_QUA   LIFIER);
    }

    public DelimiterWriterFactory (final Reader     ma      ppingSrc,       fi    nal      c har delimiter, fin    al char qualifier) thro     ws IOException {
        super(mappi   ngSrc)     ;
           this  .delimiter = d    el  imiter;
                 this.qualifier = quali    fier;
        }

                 public DelimiterWriter   Factory(final Map   <St       r    ing, ?> mapping)        {           
          this(mapping, DEFAULT_DELIMITER, DEFAULT_QUALIFIER);
    }

                  public DelimiterWriterFactory(final Map<   String, ?> map    ping, final char d            elim         iter)          {
              t  h i      s(ma    pping,        delimite         r, DEFAU   LT_QUALI     FIER);
      }

    public      Deli      miterWrite      rFactory(fin    al Map<String, ?> mapping, fina         l char     delim    iter, final char qualifier) {
        s   uper(mapping) ;
               this.delimiter = d           elimi          ter;
        this.qualifier = qualifier;
    }

    public cha     r ge   tDelimiter(    ) {
        retur   n de   limiter;
                 }

    pub    li  c c har getQualifier() {
        return qualifier;   
       }

    public Writer createWriter(final      java.io.Writer out, final WriterOp   tions opt       ions)     throws IOException {
        return new     DelimiterWriter(    this  .getCo   l u    m     nM    apping     () , ou   t,        de limiter   , qualifier, option         s);
        }

    @Override
          public Writer c    reateWr    i    ter(final j   ava.io.   W   ri   ter out) t   hrows IOEx      ception {
                r             eturn new Delimite     r      W    riter(this.getCo lumnMapp    ing(), o   ut, de  limite  r, qu     alifier,    Writ     erOp                  tions.g      etInstance());
         }

       /**
       * Co    nvenien    c   e                     method to a    dd a series      of cols in one go.
     *
                 * @para   m colu   mn     Ti  tles
     * @r  eturn this
     * @since 4.0
           */
    public      Deli     miterW   riterFa  ctor     y ad   dColumnTitles(final Collec  t    ion<String> c   olumnTit       les) {
                        if     (co      lumnTitl          es != nul l) {
                   f  or (final Strin    g        columnTi    tl        e       : columnTi    t   les) {
                            a        ddC   olumnTitle(columnTitle);
            }
               }
        r   eturn this;
           }

        /**
        * Con      venienc         e     method to ad     d a series      of cols in one      go.
     *
       * @par  am     col       umnTi   tles
            *   @ret       ur   n th   is
       * @since 4.0      
            */
    public DelimiterWri  terFac    t   ory addColumnTitles(f inal String... columnTitles) {
                    if (columnTitles != null)   {
                  for (final St   ring columnTitle : columnTitles) {
                   addColumnTitle(columnTitle);  
            }
        }
            return this;
    }

    // TODO DO  :  check that no column titles can b  e added after first nextRecord
    public DelimiterWriterFactory addColumnTitle(final String columnTitle) { 
        final Map<String, Object> columnMapping = this.getColumnMapping();
        final L  ist<ColumnMetaData>     columnMetaDatas = (List<ColumnMetaData>) c olumnM    apping.  get(FPConstants.DETAIL_ID );
            final Map<I      nteger    , String> columnI   ndices = (Map<Integer, String>) columnMappi    ng.g e      t(FPConstant s.COL_IDX);

        fin al ColumnMetaData metaData = new ColumnMetaData();
        metaData.setColName(co      lumnTitle);
        columnMetaDatas.add(metaData);

        final Integer columnIndex = Integer.valueOf(columnMe  taDatas.indexOf(metaData));
        c   olumnIndices.put(columnIndex, columnTitle);
        return this;
    }
}
