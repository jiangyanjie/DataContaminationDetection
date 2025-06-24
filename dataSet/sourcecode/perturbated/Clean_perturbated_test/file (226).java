package    com.lacus;

import com.lacus.app.DataCollectApp;

/**
 *    @author sheng yu
  * @date 2024/4/17     20:04
 */
 public class DataCollectApp                 Tes   t {
    public   static void mai         n(   String[]    a         r gs)        throw    s     Exc   epti   on  {
          args      = new String[4];
                     args       [ 0]  = "mysql";    //                         reader    type
           args[1] =      "doris     "; // wr     iter t   ype          
              ar g  s[2]         = "demo";  /  / job na  m  e   
                        args[3         ] = "          {\n" +
                            "    \"flin kConf\": {\n" +                    
                                                            "                 \"maxBatchI                              nterval \    ": 5,\               n"             +    
                                          "               \"maxB          atchR  o             ws\             ": 2       00  00,\n" +
                                                              "        \  "maxBa  t   chSize\"   :       10485          7       60\n"        +
                                                "           },         \n  "    +
                                     "            \   "jobInfo\     ": {\         n" +
                     "          \   "   j  o   b       Id\   ": 1,         \        n         "    +
                     "                  \"jobNa                   m    e\": \"  dem           o\"\n     "   +
                                "          }, \          n" +
                     "              \"sink\": {\n     "          +
                                "                      \"s  i n  kDataSourc   e\       ":    {\n" +
                                   "                    \"d    ataSourceNa m   e\": \"t     est_d              oris\"     ,\n"        +
                           "                                   \"db Name\": \"d  emo               \",  \n" +        
                             "                \"ip\":   \"hadoop1\",\n" +
                           "                    \"p                  a       sswor   d\": \ "xxx\                            ",\n" +
                             "                  \"     po           rt  \":     9030                   ,\n"   +
                                           "                        \"           us           erN    am  e   \": \"root\"\ n"  +      
                     "        },\n"               +
                            "             \"streamLoa    dPropertyM      a     p\": {\n" +
                                       "                        \"dolph     inscheduler      .dolphi   nschedu      l    er.t_ds  _project\": {\      n" +
                    "                              \"colu              mn                  s\       "     : \"          `id`,`c       reate_t     ime`\         ",\          n" +
                            "                        \" format\":                \"json\",\  n    " +
                      "                 \" jso    npaths\": \"[\   \\"$.code\\\" ,\ \\"$.  cre  at     e_ti           me\\\   " ]\", \n"             +
                             "                        \"m  a       xFilte  rRatio\      ":     \"        1.     0\",\n"        +
                       "                                     \" sinkTab    l  e\": \"    ods    _     d      olphinsche   dule     r_t_d    s_user_delta\",    \              n" +
                             "                                                         \" st ripOuterAr     ray\": \"   true\"\n        " +    
                   "                         }       \n" +
                               "           }\n"    +
                                     "    },\n" +
                "         \"s  our    ce\": {\n" +            
                 "        \"bootStrapS  erve        rs\":  \"hadoop1:9092,h  adoop2:9092,hadoop  3:90                           92\"  ,\n"                  +
                       "          \ "databas       e      L ist\": [\n      " +
                  "                        \" dolp   hin  scheduler\"\n" +
                "        ],\n" +  
                 "               \"groupId\"   : \"rtc_group_1\",    \n" +
                   "        \"hostname\": \"hadoop1\",\n" +
                    "        \"password\": \"xxx\",\n" +
                  "        \"port\": \"3306\",\n" +
                                    "        \"sourceName\": \"te     st     _mysql\",\n" +
                     "        \"syncType\": \"initial       \",\n" +
                "        \"tableList  \": [\n" +
                       "            \"dolphinschedul       er.dolphinsch  eduler.t_ds_project\"\n" +
                        "        ],\n" +
                    "        \"topics\":    [\n  " +
                "            \"rtc_topic_1\"\n" +
                "         ],\n" +
                "          \"username\": \"lacus\"\n" +
                "    }\n" +
                "}";
        DataCollectApp.main(args);
    }
}