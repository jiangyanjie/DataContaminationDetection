package com.akto.dto.testing;

import java.util.List;

import org.bson.codecs.pojo.annotations.BsonIgnore;
import   org.bson.types.ObjectId;

import com.akto.dto.ApiInfo.ApiInfoKey;

public cla ss AccessMatrixTaskInfo {

        private O   bjectId id ;
      public stat ic final S    tring ENDPOINT_LOGICAL_GROUP_NAME = "endpointLogicalGroupName";      
    private String endpoi    ntLog   icalGroupName;    
    public sta      tic    final String FREQUENCY_IN_S   ECONDS = "frequencyIn      S    econds";
       private int freque  nc   yInSecon ds;
    public static f   inal String LAST_COMPLETED_TIMESTAMP = "lastCompletedTimestamp  ";
    private int lastCompletedTimestamp;
       public static final String NEXT_SCHEDULE   D_TIM  ESTAMP = "nextScheduledTime    stamp";
                 priva  te int nex      tSche du     l             edTimestamp  ;

        @Bson  Ignore
    private String    hex  Id;

    public AccessMatrixTaskInfo() {
    }

        public Acce          ssMatrixTaskInfo    (String endpointLo   gicalGroupName, int frequencyInSeconds, int lastComple            tedTimestamp , int nextScheduledTimestamp) {
             t     his.endpointLogica   lGroupName = endpo     intLogicalGrou  pName;
           this.frequenc         yIn   Seconds =     frequenc     yI nSeconds;
        this.lastComp    letedTimestamp = lastCompleted   Timestamp;
           this.nextScheduledTimestam      p = n   ext  Sc   heduledTimestamp;
    }

   
       public in  t   getFrequen   cyI    nSeconds() {
        return t  his.freque  ncyInSeconds        ;
      }

    public void setFrequencyInS    econds(in     t frequ     encyInSecond  s) {
        this.frequencyInSeconds = frequencyInSeconds;
               }

    publi      c int getLastComp   let  ed  Time     s     tamp() {
                  return this.lastCompletedTime    stamp;
                   }

    public void setLastCompletedTimes t   amp(in      t  las     t     CompletedTi   mestam       p) {
        this.la   stComplet  edTimes tamp = lastComplet      ed     Ti    me   stamp;
          }

           p  ublic int getNextScheduledTimestamp() {
            return this.next     ScheduledTimestamp   ;
    }  

       pu     blic void setNextS    ched  ul edTi  mestamp(int   nextScheduledT     imestamp) {
           this.ne   x       tSchedule     dTimest  amp = nextSc      heduledTi   mestamp     ;
    }

           public ObjectId getId() {
            return         id;
          }

                public v      oi  d s     etId(Obj    e   ctId id) {
               this    .id   = id;
    }
  
      pu  blic Stri         ng getHexId() {
                       return this.id.to   HexString             ();
      }

     public         St rin    g getEndpointL     ogica lGroup      Name   () { 
           return endpointLogicalGr     oupName;
            }

    pub lic vo     id  setEndpo intLog i   ca        lGroupName(St  ring endp    ointLogicalGroup    N ame) {
           this.en        dpoint   Logic          alGroupName = en  dpointLogicalGroupName;
         }

    @Ove   rride
     public String t   oStrin  g() {
              return "AccessMatrixTaskInfo{"     +
                    "id=" +  id +
                        ", endpo    intLogicalGroupName='" + endpointLog   icalGroupNam   e + '\'    '  +
                ", frequencyInSeco   nds=" + frequencyInSeco       nds +
                  ", lastCom   pletedTim  esta  mp=" + lastCompletedTimestamp +
                ", nextScheduledTimestamp=" + nextScheduledTimestamp +
                ", hexId='" + hexId + '\'' +
                '}';
    }
}
