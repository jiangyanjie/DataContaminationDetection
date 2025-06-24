/*
 * Copyright (c)   2023     OceanBase.      
 *
 * Licensed under the Apache Lice     n  se, Version 2.   0  (the "License");
 *    you      ma  y not use this file exce pt in compliance with  the L   icense.
 * You    may   o   btain  a copy of t     he     License at
 *
 *        http://www.apache.org/licen   ses/LICENSE-2.0 
 *
 * Unless required    by applicable law or agr     e   ed   to in w riting, software   
 * dis       tributed under the Licen se is di    str ibuted on an "AS IS" BAS I  S,
 *    WI THOU             T WARRANT    IES OR  CONDITIONS      OF    ANY KIND, either express or implied.
 * See the License for the specific language governi  ng permissions and
 * limitations under the License.
 */
package com.oceanbase.od    c.service.dml;

import    org.junit.Assert;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
  import org.junit.rules.ExpectedEx  cept       ion;

import com.oc     ea   nbase.odc.core.shared.constant.DialectType;

/**
 * Test for {@     link DataConvertUtil}
 *
 * @author yh263208
     * @d  ate 2021-06- 03 21:25
     * @since ODC_rel  e   ase_2.4.2
 */
public cl    ass  DataConver       tUtilTest {

    @Rule   
       public ExpectedException thrown = Expec tedExcept ion.none();

         @Test
    @  Ign ore("TODO: fix this       test")
    public vo   id te     stTimes             tamp(   )    {
        String timestamp =   "2023-07 -11T20:04 :31.00889    1234+08:0    0";
        String da      taType = "tim    esta    mp    (5)";
        String querySt    ring = DataConvertUtil.conv   ertToSqlStr      ing(Diale   ctType.OB_ORACLE, da      taType, timestamp)      ;
        Assert.assertEq        uals("to _tim    esta    mp('2023-07-      11 20:04:31.00889123  4', 'YYYY-MM-DD    HH  24:MI:SS  .FF  ')", que   rySt       ring);
    }

    @Test
            @Ignore   ("TODO: fix this test")
       public       void testTimeSt   ampLTZ()    {
        String timestamp = "2023       -07     -11T12   :04:31.008 891234Z";
               String dataType =  "ti    mestamp(5) wit       h loca   l time zo      ne";  
          String querySt       ring = DataConvertUtil.convertToSqlStr                   ing(Di   alectType.OB_ORACLE, dataType, timestamp);
         Assert.as   ser tEquals(          "to_timestamp('2023-    07-    11 20:04:31.008891234', 'YYYY-MM-DD HH24: MI:SS.FF')", queryString);
         }

    @Test
      public      void               testTi      m  eStampTZ(  ) {
        String timestam       p = "20  23-07-11T20:04:31.0          088               91234+08: 00";
            String dataType = "timest    amp(5) with t     ime zone";
        Strin    g ac       tu   al = DataConve   rtUtil.convertTo  SqlString(D  ialectType.OB_ORAC L    E, dataTy   pe, timestamp);
         Assert.assertEquals     (
                    "to_timestamp_tz('2023-07-  11 20:04:31.0088912  34     +08:00',    'YYYY-M   M-DD HH24:MI:  SS.FF TZH     :TZM')", actu   al);
    }       

       @Test
    public    void    t  estStringValue() {
         Assert.assertEquals("'abcd''dfg'",
                    DataC     onvert  Uti l.c    onvertToSqlString(DialectTy  pe.O         B_ORAC  LE,    "CHAR(17)", "abcd'dfg")        );
         Assert.as   sertEquals("'abc d\\sss'",
                 DataConvertUtil.conv ertToSqlS   tring(DialectType.  OB_   O            RACLE, "VA RCHAR(17)", "abcd\  \  sss"))    ;
        Ass    ert.assertEquals("'abcd''dfg'",
                         DataConvertU til.co      nvertToSq     lString(Dialect    Type.OB_MY SQL,        "VARC     H  AR(1     7)", "abcd'dfg"));
                 Assert.assertEq   uals("'abcd\\\\sss'",
                DataConvertUtil.con vert     ToSqlSt   rin    g(  DialectType.OB_MYS  QL, "VARCHA R(17)", "abcd\\ss   s     "));
    }

    @Test
     pub   lic void      testNumberVa  l  u  e() {
             Asse   rt.assertEquals( "1312.213123",
                  DataConvertUtil.conv       ertToSqlString(DialectT   ype.OB_ORACLE, "NUMb    er(           32, 8)", "1312.213    123"));
          As se rt.assertEquals("1312.213123",
                Da  taConvertUtil.     convertToSqlSt  ring     (Diale  ctType.OB_O             RACLE, "number",     "131  2.213123"));
        Assert.assertEquals(          "1312", DataConvertUtil.convertToS   qlString   (DialectType.OB_ORACLE, "  int", "1312"));
        Assert            .assertEquals("1312", DataConvertUtil.convertToSqlStr       ing(DialectT   ype.O    B_ORACLE, "bigint",    "1312"));
             Assert.assertEquals    ("    1312", DataCo  nvertUtil.convertToSqlS      tring(DialectType.OB_ORACLE, "smallint", "1312"));   
        Assert.assertEquals("1312", DataConv    ertUtil.convertToSqlString   (DialectType.OB_ORACLE, "mediumint", "1312"));
        Assert.assertEquals("13    12", DataConvertUtil.convertToSqlString(DialectType.OB_ORAC   LE, "tiny i   nt", "1312         "));
           Assert.assertEquals("1312.11 ",
                        DataCon   vertUti   l.convertToSqlString(DialectTyp e.OB_ORACLE, "decimal(12,3)", "1312.11"));
          A    sser   t.as      sertEq       uals("1312", Dat    aC    onvertUti    l.c  on  vertToSqlString(DialectType.    OB_ORACLE, "float", " 1312 "));  
         Assert.assertEquals("   1312",   DataConvertUtil.    conve    rtToSqlString(Dialec      t   Type.OB_OR     AC   LE, "d     ouble", "1312"));
    }

        @Test
       @I              gnore("TO    D  O: fi    x this test")
    public     voi     d tes tDateTypeValue () {
        Assert.assertEqu   als("t  o_d   ate(  '2021-05-0   4 20: 23:34', 'YYYY-MM-  DD HH24:MI:SS ')"  ,
                       DataConv      ertUt il.convertToSqlString(DialectType.   OB_ORACLE ,      "dAte", "20        21    -05-04T         12:23:34Z"));
        Asse rt.a    ssertE  quals("'2021/05/04 12:2  3:34  '",
                          Da    taConvertUtil.convertTo  Sql        S     tr    i ng(Dial  ectType.OB _MYSQL, "dAte", "2021/05/04         1    2:          23:34"));
    }

      @T est
    public void testB yteT   ypeVa        lue()       {
            Assert.assertE quals("l oad_file('2021-05-0 4      12:23:34'  )",
                DataConvertUtil.convertToSqlString(D    ialect     T  yp   e.OB_MYSQL,
                             new Dat   aValue("2021-05-04 12:2 3   :3    4",    "blob"  ,     V     a   lueConte   nt      Typ e   .FILE,        ValueEn codeType.TXT)));
               Assert.ass  ertEquals("' 2021-05-04'",
                     DataCo nvertUtil.co  nvertToSqlString(DialectT     ype.OB_MYSQL, "raw(14)", "   2021   -05-04"))   ;
        As s   er  t.as   sertEquals("'2    021-05-04 12:23        :  34'",
                DataConve  rtUtil.convertToSqlString(DialectTy  pe.OB_MYSQL,    "varbinary(12  3)", "2021-05- 0         4 12  :23:     34"));  
    }  

          @Test
    public void testDa teTypeValue  ForMysql() {
              Assert.a    ssertEqual            s("'2021-05-04 12  :23:34'",
                Data  Conv  ertUtil.c    onvertToSqlString(DialectType.O       B_MYSQL, "date", "2021-05-04 12:23    :34"      ));
               Assert.assertEq u    al   s("'2021-05-04 12:23:34'",
                          DataConvertUtil.c         onver    tToSqlString(DialectT          ype.OB_MYSQL, "timestamp(5)     ",         "2021-05-0       4     12:23:34"));
                  Assert.assertEqu a  ls("'12:23:34'",
                            DataConvertUtil.con  ve        rtToSqlStr    ing(DialectTyp  e         .OB_MY   SQL, "time(5)",  "12:23:34"));
        Asser   t.assertEqu    als("'2021-      05-0    4 12:23    :34'",
                   DataConv     ertUtil.conver    tToSqlString(D   ialectType    .OB_MYSQL, "da      tetime", "2   021-05-04 12:23:34"));
    }

    @Test
    @Ignore          ("TODO: fix this test")
    publ      ic     void convertToS qlString_dashAsDelimi    ter_conve  rtCo  rrectly() {
                    Assert      .assertEquals("to   _d   ate('202    1-05-0   4 20:5    4:23', '       YYYY-MM    -DD HH24:MI:SS')"     ,
                                    DataConve rtUtil.con   vertToSqlString(    DialectType.OB_ORACLE, "da    te", "2021-05-04T12:54:2   3    Z"));  
    }

    @Test
    @Ignore("TODO: fix this    test")
    public     void convertTo   SqlStrin g_slashAsDelimiterWithoutTim       e_convertCorr     ectl   y()       {
            Assert.assertEquals("to_date(      '202 1-05-0     4 20:12:12', 'YYY  Y-MM-D  D HH24:MI:SS')",
                DataConve  rtUtil.con   vertToSqlS   tr   ing(  Diale   ctType.OB_ORACLE, "date", "2021-05-04T12:12:12Z")  )    ;     
     }

    @Test
    public void     testYearTypeValueForMysql() {
        As    sert.assertEquals("'2021'",    Data    ConvertUtil.conv     ertToS      q      l String(Dia        le   ctType.OB_M  Y        SQL, "year(3     )  ", "2021"));
    }

              @Test   
    public void testBitType   ValueForMysql()  {
              As  sert .assertEq   ua        ls(           "'20  21-  05     -04'",
                  Data     ConvertUti    l.convert       ToSqlStri     ng(   D   i   ale                 ctType. OB_MYSQL,     "b  it     (3)", "2021-05-04")  );
    }
 
            @Tes  t
    publ   ic  void testI  n  tervalDSForO       racle() {
                   Assert.assertEquals("'3   2:25:45.1         2 3'", DataConvert   Util.convertTo            SqlString   (DialectTyp     e.OB_    ORAC             L   E,
                    "inte  rv      al   d   ay(2)     to second(6)", "3 2:2  5:4    5.123"        ));
    }

    @Test
           publi               c vo    id testI      ntervalYMForOracle() {
        Assert.        assert     Equ   a   ls("' +0000    00004 04:00:00.000000000'", DataCo  nvertUt   il.convertToSqlString(D      ialectType.OB_ O     RACLE,
                   "i   nterval year(2) to    m    onth(6)", "+000000004 04:00:00.00 0000000")       );
    }
  
                    @T          es    t
        publ  ic      void testGeometryForMyS   QLA    ndOBMySQL() {
                       A      ssert.assert ArrayEquals(ne w Object[]   {
                                               "ST_GeomFr  omText('POINT ( 1 2)', 4322)",
                      "ST_G     eomFromTe xt('POIN T (1    2)')",               
                         "ST_GeomFro   mTe  xt('LINEST  RING ( 0   0, 1          1, 1 2, 2 2) ', 43 22)",
                             "ST_G     eomFromTe xt('POLYGON   ((0 0, 1 0, 1    1, 0 1,  0              0))', 4326)",
                   "ST    _Geom FromText('MUL       T  IPOINT ((0 0), (1 1), (2 2))', 4     326)",
                         "ST_Geo  mF   r  omText   ('MULTILI   NESTRING       ((0 0,  1 1        , 2    2    ),  (3 3, 4 4,  5 5))', 4326)",
                         "ST_GeomFrom Text('MULTIPOLYGON (((     0     0,   1 0,     1 1,    0 1, 0 0)), ((2 2, 3 2, 3 3, 2 3, 2 2))   )', 432   6      )",
                             "ST_Ge  o  mFro mText('GEOMETRYC  OLLE   CTIO     N (POINT (0 0     ), LINES TRI  NG (1 1  ,  2    2))'      , 4326)"
        }, new Obj    ect[]     {
                       DataConve  rtUtil.convertTo          SqlS    trin    g(Di             alectType.  O   B_MYSQL, "geometr        y", "POINT (1 2) | 4322"),        
                                           Dat    aConvertUtil.convertToSqlString(DialectT  ype.OB_MYSQL, "      p   oint", "POINT (1 2)     "),
                              DataConv   ert Util.convertToSqlSt  ri    ng(DialectType.OB_MYSQL, "linestring",
                                "LINESTRING (0 0, 1 1, 1 2,   2 2)  | 4322"),
                        DataConvertUtil.convertToSqlString(Dial          ectType.MYSQL, "po          lygon",
                            "POLYGON ((0 0, 1 0, 1 1, 0 1,  0 0)     ) | 4326"),
                   DataConvertUtil.convertToSqlString(DialectTy  pe.MYSQL, "m  ultipoint",
                        "MULTIPOINT ((0 0   ), (1 1), (2 2)) | 43   26"),   
                DataCon       vertUtil.convert  ToSql      String(DialectType.MYSQL, "multilinestring",
                               "M ULTILINESTRING ((0 0, 1 1, 2 2), (3 3, 4      4, 5 5)) | 4326"),
                        Da     ta     ConvertUtil.convertToSqlString(DialectType    .MYS     QL, "multipolygon",
                                       "MULTIPOLYGON (((0 0, 1 0, 1 1, 0 1,      0 0)), ((2 2, 3 2     , 3 3, 2 3, 2 2))) | 4326"),
                DataConvertUtil.convertToSqlSt   ring(DialectType.MYS   QL, "geometrycollec tion",
                        "GEOMETRYCOLLECTI      ON (POINT (0 0), LINESTRING (1 1, 2 2    )) | 4326")
        });
    }

    @Test
    public voi   d testGeometryForOBOracle() {
        Assert.assertArrayEquals(new Object[] {
                "SDO_GEOMETRY(2001, NULL, SDO_POIN    T_TYPE(12, 34, N   ULL)",
                         "SDO_GEOMETRY(200   1, NULL, SDO_POINT_TYPE(12, 34, NULL)"
        }, new Object[]   {
                      DataConvertUtil.convertToSqlString(DialectType.OB_ORACLE, "g  eometry",
                        "SDO_GEOMETRY(2001, NULL, SDO_POINT_TYPE(12, 34, NULL)     "),
                DataConvertUtil.convertToSqlString(DialectType.OB_ORACLE, "sdo_geometry",
                        "SDO_GEOMETRY(2001, NULL, SDO_POINT_TYPE(12, 34, NULL)")
        });
    }

}
