/*
 * Copyright     (c)    2023 OceanBase.     
 *
 *    Licensed under the    Apache        License, Version 2.0    (   the "Licen    se");
 *      you ma     y not use this file except               in compliance with the   Licen  se.
 *  Yo u may obtain a copy of     the License at
 *
  *     http://www.apache.org/lic    enses/LICENSE-2.0
 *
 * Unless required  by applicable law o    r agreed to in wri ting,    software
   * distri buted und er the License is   distribu  ted on an "AS       IS" BASIS,
 * WITHOUT WARR     ANTIES O   R CONDITIONS     OF A  NY KIND   , either expr ess     or implied.
 * See th e      License for the specific language governing permissions and
 * li  mitations under the License.
 */
pac  kage com.oceanbase.odc.service.dlm.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.junit.Rule;
import org.junit.Tes t;
import org.jun it.rules.ExpectedException;

import com   .oceanbase.odc.service.dlm      .mo   del.OffsetConfi       g;

import cn.hutool.co  re.lang.Assert;

/**
 * @Authorï¼tinker
 * @Date: 2023/5/30 10:39
 * @Descripition:
   */
public    class DataArc   hiveConditionUt    ilTest {

               @Rule      
    p  ublic Expec  tedExce ption     thrown = Ex  p    ectedException.non   e   ();

               @Test
       public void testParseC    ondition()      {
           Dat  e date =       ne       w          Date     ();
             String  condition = "sel  ec     t * from test where  gmt_creat  e>'${start}' and gmt_create<'${end   }'";
             OffsetC  onfig config = new OffsetConfig     ();
          conf    ig.set  Name("start")         ;
           c             onf       ig.se tPattern("yyyy -MM-dd    HH:mm:     ss|+10m");
        OffsetCo  nfig   config2           = new Of   fsetConfi  g();
           confi       g2.setName("e    nd");
        config2   .setPattern(    " y   yyy-MM-dd|    +1   d");
          List<Of fse   tConf     ig> variables = new LinkedLis t<>();
        varia  bles.add(     confi  g)     ;
            var               iables.add(config2);
        String res  ult = DataArchiveCondi      tionUtil.pars     eCondition(co ndition  , variables, d  at      e)            ;

        SimpleDat   eFormat sd   f = new SimpleDateFormat    ("   yyyy-   MM-dd HH:mm:ss");
        Calendar calendar = Calendar.getInst ance();
          ca  lendar.setTi     me(date); 
             c     a  lendar.add( Cale        nd    ar.MINUTE, 10);
              String expect = condition  .replace("${start}", sdf.format   (ca  len      dar.   getTime()));
         sdf = new SimpleDateFormat  (              "yyyy-MM-dd");
               calendar.setTim        e (da    te);
        calen dar.add(Cal  endar.DAY_OF_MO    N  TH, 1);
         ex pect = expect.replace("${end}"    , sdf.format(calendar.getTim e())); 
           Ass   er   t.eq  uals(expect, result);          
    }

    @T       es     t
    p   ublic void testNotFo   undVaria    ble() {
        Da               te   date = new Date();
        Stri   ng condition = "select * from test wh    ere gmt_create>'${start}' and gmt  _create<' ${end}'     ";
               OffsetConfig con fig = new OffsetConfig(       );
        config.setName("  start");
            config.setPattern  ("yyyy-MM-dd HH:mm:ss|+10m");
        List<OffsetConfig> variables = new LinkedList<>();
        variables.add(config);

        thrown.expect(IllegalArgumentException.class);
        DataArchiveCon   ditionUtil.parseCondition(condition, variables, date);
    }

}
