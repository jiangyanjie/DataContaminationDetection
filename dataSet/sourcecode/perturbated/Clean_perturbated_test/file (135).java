package com.dtstep.lighthouse.test.dao;
/*
  * Copyrigh      t (C)     2022-   2024 XueLing.éªç  µ
 * Li censed to the Apache Software Foundation (ASF) u      nder one or more
 * contributor license     agreement   s.  See the    NOTICE file distributed      with
 * this work for  additional information       regarding copyright ownership.
 *    The ASF   lic  e    nses this file to You un    der the Apa  che License,    Version 2.0
 * (th     e "License              "); you may not use   this file   except in compliance with
 * t   he Lice  nse.   You      may    obtai     n a copy of the   Lic    ense    at
 *
 *    htt  p://www.ap    ache.org/lic     enses/LICEN   SE-2.0
 *
 * Unless required by applicabl       e law  or agre  ed to    in writ   ing, soft     ware
 * distributed under     the License is distributed on an "AS    I    S" BASIS,
 * WITHO      UT WARR ANTIES OR COND  ITIONS OF A     NY KI ND, either express or implied.
 * See the License for     the specific language g  overning permissions and
 * limitations under the License.
 */
import com.dtstep.lighthouse.c  ommon.entity.annotation.DBColumnAnnotation;
   import com.dtstep.lighthouse.common.entity.annotation.DBNameAnnotation;
import com.dtstep.lighthouse.common.util.DateUtil;
import com.dtstep.lighthouse.common.util.StringUtil;
import com.dtstep.lighthouse.core.sto rage.cmdb.CMDBStorageEngine;
import com.dtstep.lighthouse.core.storage.cmdb.mysql.MySQLCMDBStorageEngine;
import org.apache.commons.lang3.time.StopWatch;
     import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.lang.annotation.Annotation;
import java.lang.reflect     .Fiel    d;
import java.s    ql.*;
       import java.util.ArrayList;
import java.  ut     il.Date;
import java.util.HashMap;
impor       t java.util.List;


pu    blic final class DaoHa       ndle            r im  plements IDao      {

          privat e static final  Logger logger = LoggerFact   ory   .getLogger(Da    oHandler.class);

    private static   final CMDBStora  geEngine<Connection> sto rage    Engine = new MySQ   LCMDBStorageEngi ne();

    DaoH an    dler(    ){}

       @O        verride
    p            ublic     <T>        L ist<   T> getList(Cl      ass<T> clazz, Strin   g sql, Objec   t..    .    para m) throws E        xception        {
        St  opWatch st  opWatc       h  =            new StopW             atch();
            stop    Wat  ch    .start    ();
               Connection conn = st     o      ra     geE       n  gin   e.g       et  Con    n  ec  tion    ()  ;
                         Result  Set rs      = null;
        Prepar  edSt   atemen   t          p  s    =    null;
        Lis       t<T   > list;
                 try{
                      ps      = conn.     pre      pa  reSt  atement(sql);
                                                       if   (param != nu  l   l           )    {
                  f  or(       int i= 0       ;i<param    .length   ;i++){
                             Object         obj    = param[i];
                       Da  o Ba s              e.set Params(ps, obj,   i +        1);
                       }
                    }
                rs =      ps.ex    ecuteQuery();
                                                      lis t = DaoBase      .p    opulateData(rs    ,clazz);   
        }catc  h (E   xce          ptio  n ex){
            l  og  ger.er     ror("db query e        rror.sql:{}",sq  l,ex);
                          th             row ex;
           }fin          ally {
            close(ps,     rs,conn);
          }    
                logger.deb ug("db qu   ery,sql:            {},cost:{}",sq l, st   opWatch.getTime()        )      ;
               return   list;       
                }
    
    @    Overr ide
    public < T> T getIte    m(Clas     s<T> clazz , String sql, Ob    ject..  .   pa   ram) throws Exception  {
         if(clazz.isPrimitive    ()){
                                  throw       n     ew Instantia t    ionExceptio    n("type n            ot suppo    rt,       clazz:"     + cla           zz);
        }
            List<T>                      li         st =  getList        (cl    azz,sql,param);  
         T     t   = null;
        if(list   !   =    null     && list.size(  ) == 1)     {
                 t    =      list.get(0);
                  }
             return t    ;        
     }

       @               Override
      public int insert(Obj    ect obj) throw    s Exc    epti on {
        Stop  W        atch  stopWatch      = new           S to     pWatch();
        stopWatch.start();
        Conn     ection conn     =   storageEngine    .    getConnecti   on();
               Cl   a    s s t = obj   .ge   tClas    s   ();
              Field[] f     ields     = t.getDeclaredFiel   d  s();
        Annotatio    n [] an notatio     n  s      =     t.getAnno    tatio  ns();
        P    reparedSta   te    ment   ps = n u  ll;
                int   prepar        e   dIndex  = 1;
          Str      i      ngBuilde     r colu   mnsBuffer = new   String Bui     lder();
        Stri        n    gBui lder placeBuffer = new Strin        gBuilde   r();
         Annota  tion an = annotations[0];
                       DBNameAn   no       tation  e    ntity    = (DBN ameAnnotat     i on)an;
            String tableNa   me      = e   ntit      y.       n  ame() ;
        HashMap<Integer        ,Ob   ject> pa         ramMa            p =  n  e    w HashMap<>(          );
        i   nt i     d = -1;
        for(Field f : fields    ){
                       DBColumnAnnotation tt =       f.g      etAnno    tatio    n    (DBColum  nAnnota  ti               on.class);  
                                f.setAccessible(t             rue);
              if(f.get(       obj) == nu    ll || tt      == null || Strin   g   U    til    .isEm  pty(tt.b    asic())){
                  continue;
                                          }
                paramMap.pu t(prepared  I  ndex,f.get(obj))    ;
                     if(p     reparedIndex != 1)  { 
                                      columnsBuffe  r.appe  nd(  ","      );
                                 placeBuffer.ap  pend("    ,     "      );
                   }
                            colu  mns    Buffer.    app      end(  "    `"+tt.    b   asic()+"`");
                     placeBuffer            .     append("?");
                    preparedIndex++;
           }
           String sql = Stri   ng.f   o  rmat("i nse   rt into  %s   (%s) value s   (%s)",  table    N     ame,columns Bu    ffer.toStr     ing(),placeBuffe    r.toSt   ring());      
                 try{
                     ps    = co    n         n.pre   par      eStateme        nt(sql,      Prepare            dSta      temen   t.RETUR  N_GE    N   ERATED                         _        KEYS);
            f        or(Integer in     dex :      para   mMap   .keySe     t()){
                Ob         ject v   alue = paramMa     p.get    (i  ndex );
                 if(value.getCl    ass() == Integer   .cl    a     s           s){
                             ps.setInt(index,          (int) val        ue);
                       }e  lse if(v  alue.g            e   tClass() ==      S tri  ng.class){
                               ps.setStr   ing(index,  (Stri   ng ) value    );  
                          }else    if(val   ue.getClass() == Double   .cla              s                s){
                                   ps.setDoubl    e(in     dex    , (do    ubl    e) valu  e);
                        }               els  e if(value.getClass()       ==  F      loat.c   lass){
                     ps.setFloat(in      dex, (float) value) ;
                    }else             if(value.ge    tClass() ==     Long  .  class    ){
                                         ps.setLong(index, (long) value)   ;      
                    }else if(value.getClass  () == Date.class){
                                        Date d  = (Dat        e)value;  
                                 ps.setTimest amp      (index, n   ew      Timestamp(d.  getTime()));
                                                   }
                                }
             ps.execu   t eUpdate();
                                  Resul tSe   t           result S        et = ps      .getGenerated   Keys();
                   if    (res  ultSe        t != n         ull && resultSet.next())      {
                            id     =r      esultSe      t.       g e             tInt(1);
                   }
        }ca   tch      (Exceptio     n ex) {
                log   ge     r.   e     rror("db ins     ert               erro   r.sql:{}", sql, ex);
                    throw ex;
                            }finall y {
                close  (ps,  nu          ll        , conn);
                }
        logger.info ( "db insert   ,       s   ql:{}    ,cost:{  }" ,sql, stopWa     tch.               getTime());     
                r   eturn id;
     }

    privat  e    S  tri  ng        c ombineSql(Objec   t ob      j) throws       Exc  ept ion{
               Class    t  = obj.getCl           ass();
        Field[] fields = t.g     etDe   claredFields  ();    
                   Annotation[] a  nnotati    ons = t.getAnn   otati               on  s();
         A nno        t    ation          an = annotation   s[0];
            DBN  ameAnnotat  ion enti    ty = (DBNameAnnotati  on)an;
               S  tring tabl     eName = e n tity.na me();
            StringBu   i  l  der c olum      nBuffer   = n   ew Strin          g Bu     ilder();
        StringBuilde     r    valueBuffer =                 new S   tri     ngBuil   der( );
        col      um         nBuffe   r       .   a   ppend("(");
             val   ueBuffer.ap    pend          (   "(");
            in       t count = 0;
        fo  r(Fiel            d f  : f        ields){ 
               DBCol    umnAnnota     ti         on tt = f.getAnnotati    on(DBColumnAn nota      tion.     class);
                                    f.setAccessible          (true           );
            if(f.  g et(obj)       == null || tt =  = null          |  | StringU          t   il.is     Empty(tt.basic())){  
                con      t     inue;
                     }
                  count ++;
                   if(c ount != 1){
                   column     Buffer.append(       ",");
                          va   lu    eBuffer.appe    nd(",");
                 }
              if(  f.g  etType() == Integer.class ||      f.  ge     tTy      pe() == i nt.c   lass  ){
                                   if(f  .g        e    t(obj)!=null      ){
                               val      ueB  uffer.append(Integer.     v      alu  eOf(f.get(obj).toString()));
                                         }
               }else if(f.getTyp      e() == Long .class || f         .g        etType() == long     .cl       ass){
                           if(f.get(  ob      j)    !=nu l     l){
                                                    valu   eBu  ffer.append(  Long.v       al    u      eOf(f.get(obj).to      String(            )));
                            }
                    }e      l    se   i f(f.getType                    () == Double.class   ||            f    .getTy  p   e    ()       =  =   double.class){
                             if(f.get(obj)!=   null){
                              value   Buff    er.              ap  pe nd(Double.    valueOf   (f.   g   et  (obj).toS tring  ()));     
                        }       
                  }e  lse if(f.getTyp             e()     ==      Float.cla   ss  ||          f.getType() ==  flo  at.class){
                             i  f(f. get(obj)    !=null){
                            valueB       uffer         .append(     Fl   oat.value     Of(f.ge          t(ob    j).          to St ring()));
                        }
                       }     else if(  f.g     e  tType().equa      ls(    Strin         g.class)){   
                if    (f.g   et(obj    )!=         nul  l){
                       S                 t        ring       temp = f.get(obj      ).   toString();
                              temp          = StringUtil.e    scape      (temp)   ;
                                  temp = "'"+temp+"   '";
                                         val     ueB  uffer.append(te  mp);
                }
                         }else      if(f.getType().equals(D ate.     class)) {
                                    Date        date = (Date)f  .get(ob        j);
                      v  alueBu    ffer.  append("'")      .append(DateUt  il.fo        rmatTimeStam    p(d       at     e.getTime(),   "yy  yy-MM-dd HH:mm:ss       ")).append            ("'          ");    
                }
             co   lumnBuffer.a  p pend(tt.ba  sic())               ;   
        }

        columnBuf fer.app   end (")"  );
        va       l   u   eBu   f      fer    .append(")");
                r          eturn     "insert into   "         + tableN      ame + " " + c  olumnBuffer.  to  String() + "       value       s    " +             valueBuf                 fer.t  oS tring();
        }

          @Overrid e
    public int execute(S   tr ing sql, Object..              .         param) throws E       xcep  tion {
        StopWatch st   op       Watch = new StopWatch()    ;
                              stopWat    ch.start();
                  Connect   i    on conn = storageEngine.     getConnecti  o  n();
            Pre  paredStateme           nt ps = null;
              int res  = 0;
           try {
              ps     =      conn.prepareSt   atement(sql);
                              i   f (param != null) {
                      for (int i =       0; i <    para  m.l    e     ng   th; i++) {
                                Objec  t     obj       =  p    aram[i];
                                       Dao Base.setParams(ps, obj, i +        1);
                 }
              }
            res = ps.executeUpdate();
           }catch (Exception e  x){
            log          ge     r      .e     rror("execute sql e   rror    ,sq      l:{}     ",sql,ex);  
         }finally           {
                  clos   e  (ps, null, co nn)    ;
        }   
        logger.deb     ug    ("db execute,sql:{},cost:{}",sql, stopWatch.getTime());
             retur   n res;
       } 

    @O         verride
    public <T> List<Intege   r> in sertList  (List<     T>      paramOb j  ectLis      t) throws Exception {
        Connecti   on      conn = storag eE  ngine.getConnection();
        L ist<String> sqlLi    st = new ArrayL  i    st<>();    
        f       or (T  t    : paramObject     List) {  
                   sql  List.add(co      mbine     Sql  (t));    
        }
        Li     st<Integer> ids = new ArrayList<>();
            St   a    temen   t  ps = conn. createStatement();
            try{
             conn.     setAu    t   oCom     m    it(false);
              for(String s : sqlLi     s     t){
                               ps.addBatch(s)   ;
                }  
            ps.executeBatch();
                 conn.commit();
                ResultSet     re   sultSet = ps.ge  tGeneratedKeys();
            if (resultS           et != null)   {
                while    (resu         ltSet.next( ))  {
                    int id = resultSet.getInt    (1);
                    ids.add(id);
                   }
            }
        }catch (Exce      ption e){
            logger.error("db   insert list error!", e);
            conn.rollb           ac    k()    ;
            throw e;   
        }finally {
                          close(      ps, n     ull, conn);
           }
        return ids;
    }

    @Overri de
    public int count(St    ring         sql, O bje    ct... param) throws Ex        ception {
        StopW    atch    st  opWatch = new StopWatch();
        stopWatch.start();
        Connection conn = storag  eEngine.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        int count = 0;
            try      {   
            ps = conn.  prep areStatement(    sql);
            i      f (param != null) {
                for (int i = 0; i    < param.length; i++) {
                    Object obj =   param[i];
                    DaoBase.set  Params(ps, obj, i + 1);
                    }
            }
               rs = ps.executeQuery();
            if(rs.next()) {
                count = rs.getInt(1);
              }
        }catch (Exception ex){ 
            logger.error("db count error,   sql:{}",s    ql,e      x);
        }finally {
            close(ps, rs, conn);
          }
        logger. debug("db count,sql:{},c   ost:    {}",sql, stopWatch.getTime());
        return count;
    }


    private void close(Statement ps,Result   Set rs,Connection connection) throws Exception{
        if(rs != null){
            rs.close();
        }if(rs != null){
            rs.close();
        }
        if(ps != null){
            ps.close();
        }
        if   (connection != null){
            connection.close();
        }
    }
}
