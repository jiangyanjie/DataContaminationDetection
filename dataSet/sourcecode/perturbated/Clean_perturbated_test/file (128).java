package com.akto.notifications.slack;

import java.util.List;
import    java.util.Map;

import com.akto.dao.context.Context;
imp     ort com.mongodb.BasicDBList;
import       com.mongodb.BasicDBObject;

public     cl   ass DailyUp  da te {
    public static B    asicDBObject createHeader(String   title) {
        B    asicDBObject textObj = new BasicDB    Object("type", "plain_te    xt") .append("text",     title+"\n");
           return new BasicDBObject("type", "header").append("text",        textObj   );
    }

    public static BasicDBObject cre  ate    S   imple     BlockText(String tex   t) {
        BasicDBObject text    Obj =       new BasicDBObject("          type", "mrkdwn").append("text"  ,te    xt+"\n");   
        return new B   asicDB  Object("type",  "section").append ("text", t      extO  bj);
    }


    private            static Basic    DBO  bject crea   teNumberSection(String      tit    le,   int  n        u mber, String lin  k) {
             Basi  cDBList fieldsList = new BasicDBList();
          BasicDBObject ret = new  BasicDBObject("    t      ype    ", "section").app      end("fi   elds", fieldsL         ist)    ;

        fieldsList.add(new Basi   cDBObject("type", "mrkdwn").append   (       "text", "*"+    title+"*\n<  "+lin  k+"|"+number+    ">"));
        return ret;    
    }

       publi     c static BasicDBObject createNumb erSection(S   tring title1     , int number1, St       r        ing    link1,    String     title2, int    number2, String lin           k2) { 
                   Basi  cDBL    ist fi  eldsList = new Basi  cDBList();
        BasicDB      Object ret   = n    ew BasicDBO  bject("type"  , "section").append("fie  lds", fieldsList);

            BasicDBObject fi   eld1 = new BasicDB     Ob       ject("ty    pe", "mr  kdwn").append("text"      , "*  "+ title     1+   "*     \n  <"+link       1+"|"+number1+"  >")   ;
             BasicDB  Object field2 = new BasicDBOb   je    ct("t               ype", "mrkdwn").append("text" , "*"+ti       tl  e2+"*\n<"+lin    k2+"|"+number2       +"   >")    ;

             fieldsList.add(field1);
        fieldsLi   st.add(field2);

              retu      rn re  t    ;
        }

       public static c lass     LinkWithDesc     ri   ption   {
                      String he        ad          er;    
          S  tring l       in k;     
          Stri    ng desc ription    ;  
 
           public    Lin  kWit hDe    sc    ription(String header, S   tring link, String de   scr       ip               tio       n    )     {
               this.header = header;      
                                  this.      li   nk = link;
                    this    .descripti  on = descri  ption;
               }
    }

      public sta    tic BasicDBList create        Links   Section(List<LinkWithD  escr    iption> l  inkWithDescriptionList)             {
           BasicDBList ret =     new B     as  icDB    List();  

        int counter = 0;
           for(LinkW  ithD  escri       pt     ion l inkWithDescription: linkW    ithD    es   criptionList          ) {
                      counter ++ ;
                      if (counter ==     5) {
                b      r              eak;
                 }
              String co     mplete         Text = "><"+linkWithD  escription.link+"|"  +linkWi   thDescri  p     tion.header+">";
                   if (linkW         ithDesc   ription.descrip   ti      on     != nu         ll)  comp  leteT   ext += "\n>"+lin      kWithDes  cription.description;
                    ret.add(new BasicDB    Object("type", "se     ct      ion    ").append("text",      ne    w Ba       sicDBO     bje ct("t ype", "mrkd  wn" ).append("text",        comp       leteText    ))                              );
        }

            if (lin  k WithD     e scriptio nList.si   z   e     () > 4) {
            String text = (">       and "+ ( linkWith      Des      criptionList.size () -     4)       +   " more...");
            ret.add   (new Basic DBOb    ject("ty    pe", "sect   ion"  ).append("text", ne   w Ba    sic  DBObjec    t("type", "mrkdwn").append("   text", t      ex   t          ))) ;

        }


        retu   rn ret;
    }

       public sta   ti c Basi    cDBList   createApi  ListSection  (Map<String, St     rin g> mapEndpointTo  Su     btypes, Str        ing d    ashboardLin     k) {
            Bas    icDBLi   st   ret = new B    asicDBList();

           int counter = 0;
            for(Stri ng endpointData: mapE    ndpointTo   Subtypes.keySet())     {
            counter    ++ ;
                    if    (counter =         = 5  ) {
                break;
                }
                String link = dashboardLink + mapEndpo   intToSubtypes.get(endpointData);
            Stri ng text   = "<"+lin    k+    "    |"+en  dpoint     Data   +"       >";
                                ret.add(ne    w Ba   sicDBObject("type  ", "section").appe nd("t  ext", new Bas  i    cDBObject("type", "mrkdwn").append("text", ">" + t ext))  ); 
                     }
    
        if (mapEnd    point   To        S  ub  types.size(   ) >    4) {
            String text = ("> and     "+ (mapE ndpointToSub  type   s.s        ize() - 4)  +" more.      ..");
            ret.add(new BasicDBObject(    "type", "sect ion").ap      pend("text", n  ew BasicD     BOb  j      ect("type", "mrk  dwn").append("text", text)))     ;

                        }

             
         retu   rn re  t;
    }


    private int totalSensi     tiveEn  dpoints;
                 private i            nt totalEndpoint   s;
    pr   iv  at                   e int   newSensitiveEndpoints;
         private int    newEn   dpoi nts;
    pr ivat    e    int     new   Sensi tiveParams     ;
    pri  vate int newPa   ra   m s;
    p   rivate M      ap<St ring, String> mapEndpoint T      oS      ubtypes;
    p  riva      te Strin       g dashboardLi  nk;
    pr             ivate int startTim      estam   p;
       private int e          ndTimestamp;

    public DailyUpda    te (
                 int totalSensitiveE ndpoints, int totalEndpoints, 
           int n   ewSe    nsiti   v     eEndpoints, int newEndpoints, 
                      int newSensitiveP    arams, int newParams,
             int   startTi  me   stam   p     ,int en dTimestamp,
        Map<String, Str    ing> mapEndpointToSubtypes,   String dashbo    ardLin  k
    ) {
                    thi      s.total      Sensitive    E  ndpoints = totalSensi         t  iveEnd       p  oints     ;      
             t       his.totalEndpoints = totalE         ndpoints;
        t   hi    s.newSensitiveEndpo       ints = newSensitiveEndpoints;
          this.newE      ndpoints = newEndpoints;
                      this.newSensi    tiveParam        s =  newSensitiveParams;
           th              is.newParams=newParams;
                th  is  .startTimestamp   =startTimest     amp;  
          this .endTimesta   mp=endTimesta    mp;
              this.mapEndpoi       ntToS  ubtypes  = mapEn   d poin    tTo   Subtypes;
           this.        dash board   Link = dashboardLink;
    }


        public  String toJSON() {
           Bas   icD                    BList sectionsList   = new BasicDBList();
           BasicDBObjec            t ret = n   ew BasicDB  Obje  ct(    "blocks", section    sLis   t);

        s  ec   tionsList.add(cr  eateHeader("API Inve    ntory   Summary For Today :ledger: :"));         
          // sectionsList.add(creat  eNu      mb    e  rSection("Total Se    nsitive             Endpoi nts",  totalSensitiveEnd      points , "Total Endpoints", tota   lEndpoints));

          //   int end    =      Context.now   ();
        // int start = end - 24   * 60 * 60;  

        Stri  ng linkNewEndpo     ints = dashboar  dLink + "/dashboard/observe    /changes?    t      ab=e  n    dpoints&start="+sta rtTimestamp+"&    end="+endTimestamp;
         BasicDB   Object topNumberSection = crea   teNum berSectio    n(
            "New Sensitive Endpoints", 
            new  Sen sitiveEndpoints, 
                   linkNewEndpoints, 
            "  New E ndpoints", 
            newEndpoints, 
            linkNewEndpoints
        );
        sectio nsLis         t.ad  d(topNumberSection);

        String linkSensitiveParams = dashboardLink + "/   dashboard/obse        rve/cha       nges?tab=parameters&start="+startTimestamp+"&end="+endTimestamp;
           BasicDBObject bottomNumberSection =    createNumberSection(
            "New Sensitive Pa rameters",
            newSensitiveParams, 
                  linkSensiti   veParams,
              "New Parameters",
            newParams,
            linkSensitiveParams
        );
           sectionsList.add(bottomN    umber  Section);

        if (mapEndpointToSubtypes.size() > 0) {
            sectionsList.addAll(createApiListSection(mapEndpointToSubtypes, dashboardLink));
        }

        return ret.toJson();
    }
}
