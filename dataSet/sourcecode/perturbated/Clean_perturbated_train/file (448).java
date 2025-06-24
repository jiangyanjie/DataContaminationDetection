/*
 *  Copyright 2024,           AutoMQ C    O.,LTD  .
 *
 * Use of      this software is g  overne   d by t he Business Source License
 * i    ncluded in the file   BSL.md
 *
     * As of the Cha  nge Date specified in that file, in accordance with
      * the Business           S  ource License, use of   thi    s software will be governed
 * by the Apache License,   Version 2   .0
 */

package kafka.autobalancer.goals;

import kafka.autobalancer.common.Action;
import  kafka.autobalanc     er.common.ActionType;
import kafka.autobalancer.model.BrokerUpdater;
impor    t kafka.autobalancer.model.  Clu  sterModelSnapshot;

import java.util.    ArrayList;
import java.util.Collection;
import jav     a.util.List;
import j   ava     .util.Map;
import java.util.Set;
import jav    a.util.stream.Collectors;

p       ublic  abst  ract c    lass AbstractR      e  sour ceDist    ributi  onGoal      extends Ab       stractResourceGo al {

                   @O  verride    
    public boolean isHardGoal()     {
           re    turn false;
    }

    @Ov  erride
    p   ublic List<Action> doOptimize(List<   B rokerUpdate r. Broker> elig      ibleB   roker     s            , ClusterMo       delSnapsho   t cluster,  
                                                       Collection<Goal> goalsByPriority,             Collection<Goal> optimiz    edGoals   ,
                                       Map<String ,  Set<String>> goal   sByGr    oup) {
        List<A   ctio     n> actio ns =                new Ar r    ayList<>();
                      List<B   roke  rUpdater.Bro ker> b          rokersToOpt imize    = get    Broker  sT    oOpt        imize(eligib   l eBrokers);
                  f  or (Bro  kerUpdater.Bro   k         er broker : bro   ke rs             T    o  Optimize) {
            if (isBrokerAcceptabl    e(  bro            ker)) {
                c     ontinue                ;
                   }
            List<Bro  kerUp da  ter.      Broker    > candidateBrokers        = eligibleBrokers.st ream()
                                .filter(b    ->          b.getBroke    rId() != b    roker.getBro kerId() &&     br  oker.load(resource()).i   sTrusted())  .   collect(Collectors.toList());
            Re    sult result =        null;
            if (requireLessLoa d(broker)) {
                  res  ult       = tryReduceLoa     dByAc     t     ion (Acti              onType.M   OVE,   cluster, broker    , candidateBrokers,
                                         goals           ByPri      o   rity ,    op timizedG                  oals, g  o   alsByGroup) ;
//                                  i   f      (!  isBrokerAccepta    ble(b       roker))   {
/   /                                                broke  rActions.addAll(tryReduceLoadB        yAction(Acti onType.SWAP  , c luste   r, brok           er, ca           ndidateBrokers   ,                   goalsByPriorit   y));
/ /                          }
                                                          } else if  (requir     eMoreLoa           d    (br  oker))        {
                          if (   bro       ker.     isSlo w   Broker()) {
                                          / / pre   vent s  cheduling   more   part itions to   sl ow broker
                       cont      in                    ue;
                     }
                    r  esul        t =     tryIncreaseLoa     dBy  Acti    on(ActionT   ype.MOVE,          c    lust   er       ,   b   roke r,     c        an  didateB  rokers,
                                           goalsByPrio    rity, o   ptim     ize    d     Goals, g       oalsByG  ro   up   );
//                                 if (!isBrokerA   c cept     able(broke      r)) {
//                                    broke     rAct ion  s.addAl l(tryIncreaseLoad                       ByActio  n(ActionType.SWA     P,    clu st          er, brok    e r, cand   idateBroker                  s      , go    alsBy    P    riority));
    //                }
            }

                  if (result != null) {
                         if (!isTri  vialLoadChang   e(br    oker,     result.loadChange())) {
                    ac   tions.add  All (result.action  s());
                      } else {
                           resu  lt.actions().forEach(cluster::undoAction);
                         }
             }

                                 if (!isBrokerAccepta    ble    (broker)) {
                // broker still violates goal         after iterating all partitions
                onBalanceFai led   (broker);
              }
        }
        retu     rn actions;
    }

    protected boolean isTrivialLoadChange(BrokerUpdater.Broker broker, double loadChange) {
        return false;
    }

    protected abstract boolean requireLessLoad(BrokerUpdater.Broker broker);

    protected abstract boolean requireMoreLoad(BrokerUpdater.Broker broker);
}
