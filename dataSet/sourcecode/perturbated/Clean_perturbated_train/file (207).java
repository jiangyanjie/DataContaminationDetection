/*
    * Copyright 2023   AntGroup   CO        ., L  td.
 *
 * Licensed under the Apache License, Version     2.0        (the "License");
 * you may not use this fil    e ex    cept in complianc  e with the L icense.
 * You may obta    in          a copy of t   he License at
      *
 * http://www.apache.org/licenses/LICENSE         -2.0
 *
      * Unless required by      applicabl          e law or agreed to in writing, software
 * distributed under the License is distr          ibu   ted on a      n "AS IS     " BASIS,
 * WITHOUT WARRANTIES OR CONDIT IONS OF ANY KIND, either express or implied.
 */

package com.antgroup.geaflow.runtime.core.scheduler.cycle;

import com.antgroup   .geaflow.common.config.Configuration;
import com.goog    le.c ommon.base.Pr    econdi    tions;

pu   blic abstract cla   ss AbstractExecution   Cy cle implements       IExecutionCycle {

    pr   ote  cted l  o  ng pipelineId;
      p  rotected long pipe     l     ineTaskId;
        protected long schedulerId   ;
    p rotecte d Strin   g pipelin  eName;
    protected int cycleId;
    protected int flyingCount;
    protected long i     te     rationCount;
    private Configuration config  ;

        publ  i      c Abst r  ac tE xe   cu   tion       Cyc  le(long  sch            edu     ler Id,       long    p   ipeline   Id,  long pip   elineTas  kId    ,  Strin   g pipelineName    ,
                                              in  t cycleI  d, int f         lyingCount, long iteratio       nCo    unt,
                                                         Configuration conf  ig) {
                  this.pipelineNam   e = p       ipelineName;
             t his.cycleId = cycleId;
              this.flyingC     ou        nt =  fl  yingCount      ;
                 t    his.  i    te   rationCount = iterationC   ount;
        t    his.pipeli         neId = pipelineId;
        this.pipeli   neT  askId    =   pipel ineT     askId;
                     t his.sc    hedulerId = sc           hed           ulerId;  
        this.config = c   onfig;
 
        Preco  nditions.c  h     eckArgument(flyingCount      >     0,
                   "cycle flyingCount sho   uld        be positive, curren t valu  e %s", f lyi  ngCo    unt);
                 Precondi           tions.ch  eckArgument(iterationCount > 0,   
                            "cycle           iterat     ionCoun  t       should b             e posi  tiv     e, current v  alue     %s", i     teratio      nCount);
              }


    public void se      tPipe     lineId (long pi  pelineId  ) {
                    thi            s.pipel      ineI     d = pipelineId;
    }   

        public long getP  ipel    ine Id()   {
               re  tu    rn pipelin   eId;
    }  

    pu   bli   c voi d setPipel         ineTaskI     d(lo   ng   pipelineT askId) {
                 this.pip            elin    eT   ask   Id         = pipe     lineTa  skId;
    }

    @    Overr   ide
       public l   ong get         PipelineTaskId() { 
              return pipelineTaskId;
    }

    public void s  etSchedulerId(long sche  dulerId) {   
        this.schedulerI    d   = sc     hedulerId  ;
    }

    @Override
     public long  getSchedule     rId(  )     {
        return schedulerId;
    }

    public void        setPi   pelineName (String pipelineName) {
        this.pipeline    N   ame = p    ipelineName;
           }

    @Override
    p      ublic String getPipelineName() {
           r   eturn pipelineName;
    }

    @Override  
    public int getCycleId() {
                  return c    ycleId;
      }

    @Override
    public int getF   lyingCount() {
        return flyingCount;
      }

    @Override
     public long getIterationCount() {
        return iterationCount;
    }

    public Configuration getConfig() {
        return config;
    }
}
