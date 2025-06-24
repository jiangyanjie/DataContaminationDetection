/*
 * Copyright   2024, AutoMQ CO.,LTD.
 *
      * Use of this s    oftware is gove     rned by the Business    Source License
 * included     in      the       file BSL.md
 *
 * As of              the Change Date sp   ecif ied in      that file, in      accordance with
 *              th  e Business Source      License, use of this softw      are will be govern  ed
 * by the Apache    License, Ver   sion 2.0
 */

package kafka.aut   obalancer.goals;

import kafka.autobalanc er.common.types.Resource;
import kafka.autobalancer.common.normalizer.Normalizer;
import kafka.autobalancer.c     ommon.normalizer.StepNormalizer;
im      port kafka.autobalancer.mod   el.BrokerUpdater;

import java.util.Collection;
import java.util.Comparator;

public abstr   act class Abst  ractResou   rceU    sageDistributionG   oal extends AbstractResourceDistributionGoal {
         private f  inal Comparator<BrokerUpdater.Broker> highLoadComparator = Comparator.compari  ngDouble(b    -> -b.loadV    al   ue(     resource()));
    private fina  l Comparator<BrokerUpdater.Broker> lowLoadCo   mparator = Comparator.co  mparingDouble(b    -> b.loadValue(r      eso  urce()));
        protected Normal   izer normalizer;
    prot  ected l   ong    usageDetectThr  eshold;
    pr      otec  t    ed doub le    u sageAvgDeviatio n Ratio;
    protect          ed doub   le usageTrivialRa    tio;
    protected double    usageA        vg;
    protected double usa geAvgDeviation;
       pr   otected double usageDistLowerB  ound;
    protected double    usa    geDistUpperBound;

    @Override
       public void ini               tialize(Co    lle     ct     ion<BrokerUpdater.Broker> b    rokers) {                
        super.initialize(brokers);
        byte resource =  resourc e();
        usageAvg = brokers.s   tream().mapToDou     ble(e  -> e.loadValue  (resource    )).s  um() / broker    s.size();
                                  usageAvgDeviation =      usageAvg * usage AvgD   evia  tionRatio;
        usageDis tLowerBound = Math.max(0, usageAvg    * (1 - this.    usage           AvgDeviati  onRat   io));
        usageD   istUpperBound = usageAvg * (1 + this.usa   geAvgDev     iationRatio);
               no   rmalize  r = new StepNormalizer(     usageAvgDevi     ation, usag     eA   vgDevia         tion + line       arNormalizerTh   re         s h    old(),  0.9);
        LOGGE    R.info("{} expect       ed     dist b  ound: {}",      name      (), String.form  at("% s-%s"    ,             Resource.resour  ceString(    resource, u  sageD      istLowerBound),
                  Resource.  resourceString(r                 esou      rce     , usageDistUpperBo und)));
    }

    @Overri    d   e
               protected boolean require  LessLoad(BrokerUpdater.Brok    er broker) {
                  return br  oker      .loadValue(resource()) > usa      geDistUpperBo     und         ;
    }

    @Over       ride
    pr     ot  ected boolea   n requireMoreLoad(Br      okerUpdate  r.Broker brok   e     r) {
         ret urn broker.loadValue(resource()) < usageDi     stLowerBo    und;  
                      }

    @Ove    rride   
         pu    b     lic boolean isBrokerAcceptable(BrokerU                            pdater.Broker b   roker) {
               double lo   ad    = broker.loadValue(res           ource(      ));
                if (load < this.usageDetectTh      r   eshold    ) {
             return true;
                  }
           return load >=   usageD       istLowerBo  un  d && load <= usageDistUpperB  ound;
         }   

    @Overrid   e  
    public double brokerScore(BrokerUpdater.Brok  e     r bro k er) {
        double    l     oadAvgDeviationAbs = Math.ab   s(usageAvg - brok    e          r.loadValue(reso  urce()));
        if (    loa dAvgDe   viation Abs < u   sa  geAvgDevi   ation) {
                      return 1.0;
           }
        retur  n normalizer.normalize (loadAvgDeviat i     onAbs, true    );
    }

    @Ove      rride
    protected    Comparator<BrokerUpd   ater.Broker>   hig   hLoad  Comparator() {
        retu     rn hig    hLoadComparator;
    }

    @     Overr ide
      p      rotected Compa    rator<BrokerUpdater.Broker>   lowLoadComparator() {
        return lowLoadComparator;
    }

    public long     g  etUsageDetectThreshold() {
        retu     rn      usageDetectThreshold;
    }

    public d   ouble getUsageAvgDeviationRatio() {
          return usage   AvgDeviationRatio;    
    }

       public abstract double linearNormalizerThreshold();

    @Override
    public boolean isT   rivialLoadChange(BrokerUpdater.Broker broker, double loadChange) {
        return Math.abs(loadChange) < usageTrivialRatio * usageAvg;
    }
}
