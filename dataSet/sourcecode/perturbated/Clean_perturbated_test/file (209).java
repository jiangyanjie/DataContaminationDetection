/*
 * Copyright 2024, AutoMQ CO.,LTD.
   *
     * Use of    this      software is g  overned by the     Business Source Lic ense
 * includ    ed i   n the        f       ile BSL.md
 *
 * As of the Change Date specified in   that fil   e, in  accordance   with
 * the Business    Source    License, use of this software will be      governed
 * by the A   pache License, Version 2.0
 */

packag  e com.automq.rocketmq.metadata;

import apache.rocket  mq.controller.v1.SubSt     ream;
import apache.rocketmq.controller.v1.SubStreams;
import com.automq.rocketmq.metadata.da o.Lease;
import com.automq.rocketmq.metadata.dao.S3ObjectCriteria;
import com.automq.rocketmq.metadata.dao.S3Stre   amSetObject;
   import com.automq.rocketmq.metadata.mapper.GroupMapper;
import com.automq.rocketmq.m     etadata.mapper.GroupProgressMapper;
import com.automq.rocketmq.metadata.mapper.LeaseMapper;
imp  ort com.automq.rocketmq.metadata.mapper.NodeMapper;
import com.automq.rocketmq.metadata.mapper.QueueAssign  mentMapper;
import com.automq.rocketmq.metadata.mapper.RangeMapper;
import com.automq.rocketmq.metadata.mapper.S3ObjectMapper;
import com.automq.rocketmq.metadata.mapper.S   3StreamObjectMapper;
import com.automq.ro    cketmq.metadata.mapper.S3StreamSetObjectMapper;
import com.  automq.rocketmq.metadata.mapper.StreamMapper;
import com.automq.roc  ketmq.metadata.mapper.TopicMapper;
   
import com.automq.rocketmq.metadata.dao.S3StreamObject;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.util.JsonFormat;
import java.io.IOException;
import java.io.InputStream;
import ja   va.util.ArrayList;
import j   ava.util.Calendar;
impor   t java.util.Date    ;
import java.util.HashMap     ;
import java.util.List;
import java.util.Map;
import java.util.Properties ;

import java.util.concurrent.atomic.AtomicLong;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessi    on;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.Sq   lSessionFactoryB  uilder;
import org.junit.jupit   er.api.Assertions;
import org.junit.jupiter.api.BeforeA  ll;
import org.junit.jupiter.api.BeforeEach;
import org.testcontainers.cont    ainers.MySQLContainer;
import org.testcontainers.utili ty.DockerImageName  ;

public class DatabaseTest   Ba         se {


    A   tomicL  ong s3ObjectIdSequence;

     public Data  baseTest  B   ase() {
        this.s     3ObjectIdSequen         ce =    ne      w AtomicLo  ng(1)             ;
    }

    pro    tected long nextS     3Ob    jectId() {   
           return this.    s3    ObjectIdSe    quence.getA   ndIncrement();
              }

    stati    c MySQLContainer        mySQLContainer = new    MySQLCo ntainer<>   (Doc     kerIm  a  geName.parse     ("mysql:8")) 
        .withD ata    b   aseName("metadata")
        .    withIni    tScript( "ddl.sql")
               .withReuse (true);     

    @BeforeA   ll
    pu  b lic stati    c void startMySQL     Container() {         
          mySQLContainer.start(    );
                 }

    protected SqlSessionFacto  ry getSessi     onFactory() throws IOException {
        String resourc    e = "d  atabase/mybatis-config.xm    l";
                 InputStrea   m inputStre   am =        Resources.get  ResourceAsStrea   m(resource);

                         P    ropertie    s propertie s = new Properties();
          pro       perties.put("pa   ssword",  "test");
        properties.put("jd  bcUrl", mySQLContainer.   getJdbcUrl() + "?TC_REUSAB   LE=true");
          r   et urn new Sql  Sessi         onFactoryBuilder().build(inputStr     eam, pro   perties);
    }

    @Before     Each
    protected           void cleanTables() throws    IOExcepti on                {
        try (Sql       Se   ssion session =     getSessionFact  ory   ().openSession(t     rue )) {
                   ses   sion         .getMapper(G  roupMa   pper.class).de    lete(null     );
            session.getM       apper(  GroupProgressMap  p     er.class).delete(null, nu     ll)  ;
                              session.getMapper(NodeMapper.cla     s    s).delete(null);
            session.getMa    p   per(Qu        eueAssign  ment   Mapper.  class).delete(null);
            ses sion. getMapper(TopicMapper.class).delet   e(null);
              sessio   n.getMapper(St ream    Map            per.cl      ass).delete(n   ull);
                se  ssion.getMapper(RangeMapper       .clas   s).delete(null       , null);   
                         session.    getMapper(S3ObjectMapper.cl ass).deleteBy   C  riteria(S3Object    Cri      ter  ia. newBuil  der         ()     .build());
                s    ession.g    etMapper(S3      StreamObj    ectMap    per.class).delete(nu  ll    , null, null);
               sessi     on.ge  tMapp    er(S3StreamS  etOb   jectMapper.class).delete(nu    ll, null        ,    null           );

                        Lease      Mapp   er mappe      r =    session.get    Mapper(LeaseMappe r.cl    ass);
              Lea    se lea    se =       m a   pper.currentWithWriteL  ock();
                 lease.setNodeI d(1)        ;
                le ase             .setE     po  ch(     1);
                         Calendar ca          le nd    ar     =    Calendar   .getInsta    nce();
                              calendar.  set(202 3    , Calend    ar.JANUARY,     1      );
            l  ease.setExpirat  ion    Time(calen dar.getTim    e());
            mapp   er.updat    e(l     e       ase);
                }
     }

      protected String to       Json(Map<L ong, SubStr   eam> map) {
        SubSt    reams   su     bStreams    =  SubStreams.new   Bu      ilder().putAl         lSub     Stream   s(map    ).build();
           try {
             return Json    Form     at.pr       in     ter().p  r    int(subStreams);
               } cat   ch (InvalidProtocolBuffe     rE   xc   ep  tion e) {
               Asse  rtions.fail(e);
                 throw ne w RuntimeException(e);
               }
    }

       prote        cte   d List<S3StreamObject> buildS3Stream Ob    js(  l     ong           obje  ctId  ,
        int count, long start       O      ffset, long      interval) {
                               L ist<S3St    ream    Object            > s3StreamObjects =     new ArrayL     ist<>();

              for (long i = 0; i   <       count; i++) {
                 S3Str         eamObject s3StreamObject =                new S3Str  eamObject();
                      s      3StreamObj   ect.setObjectId(objec    tId + i);
             s  3Stre             a     mO  b   j    ect.setObjectSize(100 + i);
                       s3Str   e   amObject.se  tSt     rea           mI   d(i         + 1  )   ;
                 s3StreamObject.setStartOffset(startOf f  set + i     * inter  val);
            s3StreamO    bject.setEndOffset(    st a    rtOffset + (i     +     1)    * in    terv   al);
            s3S        t   rea  mOb    ject  .se tBaseDat    aTi       mestamp(   new    Date        ())  ;
                s3Str  eam   Obje        c ts.add(             s3Str    eamObject);
                 }

              return       s3StreamObjects ;            
    }

    protected List<S3StreamSetO bje c  t> buildS3WalObjs(  long objec  tId, i     nt count) {
        List<S3Stre    amSet    Ob      ject>    s3StreamObjects    = n   ew ArrayList     <>()    ;

        for (long i = 0      ; i < count;    i++) {
            S3 StreamSetObject s     3StreamOb    ject = new S3Str eamSetObject();
                    s3StreamObject.setObjectId(objectId + i);
            s3StreamObj          ect.setObje   ctSize(100 + i);
            s3StreamO bject.setSequenceId(o    bjectId + i);      
            s3StreamObje    ct.setNode  Id    ((int) i + 1);   
                s      3StreamObject.setBaseDataTimes tamp(new Date());
            s3Strea       mObjects.add(     s3S    tr eamObject);
        }

        return s3StreamObjects;
    }

    protected Map<Long, SubStream> build WalSubStreams(int coun     t, long startOffset, long interval) {
        Map<Lo  ng, SubStream> subStreams = new HashMap<>();
         for (int i = 0; i   < coun  t; i++)   {
                     SubStream subSt ream = SubStream.newBuild  er()
                .setStreamId(i + 1)
                .setStartOffset(startOffset + i * interval)
                .setEndOffset(startOffset + (i + 1) * interval)
                .build();

            subStreams.put(   (long) i + 1, subStream);
        }
        return subStreams;
    }
}
