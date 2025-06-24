/*
   *    Licensed to          the Apache Software Foundati    on (ASF) u  nder one or more
 *       contributor l       icense     agreements. See the NOTICE file distributed  with
 * this work   for additio      nal   informat      ion      regarding  copyri ght own  ers    hi   p.
 * The ASF   lice    nses     this file to Yo       u under the Ap    ache L   icense, Version 2.0
 * (the "Li cen s  e"   ); you m ay not use this fil       e   ex    cept in compliance with
 * the License.        You may obtain a  cop   y of the License   at
 *
 *    http://ww w.apache.   org/licenses/LICEN    SE-2.0
 *
 * Unless requ    ired by applicable law           or agreed to in writing,     soft  ware
 * distributed un        der the Lic    ense is d   istr   ibu  t  ed on   an  "AS IS" BASIS,
 * WIT  HOUT WARRANTIES OR CONDITIONS    OF A   NY KI  ND, either express or impl     ied.
 * See    the License for the specific   languag  e governing permissions and
 * limitations under the License.
 */
package org.apache.kafka.coordinator.group.consumer;

import org.apache.kafka.common.Uuid;
import org.apache.kafka.common.errors.FencedMemberE poc    hException;
import org.apache.kafka.common.message.ConsumerGr oupHeartbeatRequestData;

import java.util.Col  lections;
import java.u      til.HashMap;
import java.util.HashSet;
import ja     va.util.List;
import java.u  til.M   ap;
   import java.util.Objects;
import java.util.Set;
import java.util.functio           n.BiFun  ct     ion;

/**
 * The Curren  tAssignme         ntBuilde   r class encapsu      lates the r   econciliat ion engi ne         of the
 * consu mer  group protocol.   Given  the current s     tate of    a  member an d a   desired or      target
 *    assign ment sta   te, the state machi    n     e tak e s the neces sary ste         ps to conv     erg   e the m.
 */
public class    Cu      rrentAssig  nmentBuilder {
          /**
     * The c   onsumer         group member   which is reconciled.
     *      /
      private          fi     nal ConsumerG   r      ou       pMem      ber membe     r; 

    /**
     *         The  tar  ge     t assignment e  po        ch.
        */      
    private int t argetAssign  mentEpoch;    

    /**
           * The target    assignment.
     */
    private     Assig  n       ment targetAs signme nt     ;

      /**
      *         A function which return   s the cur   rent epoch    o   f a topic-pa           rtiti on or -1 i    f the
     *     topic-partitio    n    is not a     ssigned.   The current epoch is the epoch of t  he  cur  r    ent own    er.
     */
    private B   i  F     unct      ion<       Uu  id, Integer, I    nteger>         currentParti      ti     onEpoch;

         /**
         *     The partition     s own       ed by      t h   e consumer. Thi     s is dire     ctly   provided b     y the m  emb   er in the
     *        ConsumerGroupHea    rtbeat re    quest.
     */     
    priv   ate List<ConsumerG      rou pHeartbeatRequestD ata.TopicPartitions> ownedTopicPart  itions;

        /**
     * Const   ructs the  CurrentAss    ignmentBu     ilder b    ased on the cu   rrent state  of the
     * provided  consumer group member.
     *          
     * @param mem              be  r    The         c     onsu   mer group mem   ber th     at must     be reco     nciled . 
     * /
         public CurrentAss  ignment     Builder(ConsumerGroupMem   b  er mem  ber) {
        this   .member =    Obj     ects.requ           ireNonNul   l(member);    
    }

                /**
     * Sets the tar  g   et assignment epoch and the target   as    signmen      t    that the
     * co nsum   er group      member must be           reconciled to.
           *
      * @para m targe  tAssig  nmentEpoch The tar  get assig     nment      epoc h.
                  * @param targetAssignment      The target assi              g   nm     ent.
       * @re          turn This object.       
     */
       pu     b    l ic CurrentAs       s     ignment    Bui   ld        er withTargetAssignment(
        int          t      argetAssignme ntEpoch,
              Ass     ig  nme nt     t   argetAs signment     
    ) {
            this.targetAssi gnmentEpoch        = t  ar     getAssignmen tEpoc h;
        this .tar     getAssignment = Objects.requireNonNul l(ta  rgetAssignment);
         return t      h           is;
    }

    /**
                * Sets a    B iF unction wh          ich a   llows to r     etrieve    the c  urrent epoch o   f a
            * par    tition. This is       used by the        state ma chine   to determine i f a
     * par titi on is free or still         used    b y  ano        ther member.
     *
     * @pa  ram currentPartitionE  poch A B  iFunction which gets     t   he ep   och of a
     *                                          t op  ic i     d   / parti      tions                id pair.
         * @ret  u  rn This ob  jec    t.
     */
    public Curren   tAssignm  entBuilder withCur    r     entP artitionEpoch(
                    BiFunction<Uuid, Integer   ,    Integer> currentPartitionEpoch 
       ) {
        this.c   urrentPar     titionEpoch = Objects.requireNonNull(c      urrentParti                  t        ion   Epoch);
               retur       n this;
    }

       /**
     * Se     ts the par     titions    currently owned by the member. T    his    comes   direc tly
         * from the la      st ConsumerGrou   pH   eartbeat request. Th          i    s    is used        to     d eterm  ine
     * if  th   e member         has re    v                  oked the nec    ess    ary      partit    ions.
         *
       * @param owne   dTop   icPartitions A list of topic-   partitions.
        * @r  eturn Th         i  s    obj     ect.    
       */
           public Cur      rentAssignm        en   tBuil  der withOwnedTopi     cP   artitions(
        List<Con   sum             e                 rGroupHeartbeatR eques          tDa   ta        . TopicPartitions> ownedTopicP     ar      t   itions
    )    {
        this.owne   dTo  picPartition    s = ownedTopic     Par   titions;
        r    et       urn this;
                        }

    /**
             * Buil ds the next s   ta  t  e  for         the member or    keep the c  u     rrent one if      it
     * is  n    ot     poss  ible to move     forw      ard wit     h         t             he curr ent state.
     *
        *      @retur  n A ne    w ConsumerGroupMember        o    r t      he    current o   ne.
     */
    pub  lic ConsumerGroupMem      ber bu   ild() {
                    switch (member      .   state()) {
                      c      ase S       TABLE:
                                             /   /    When the  member is i   n the      S TABLE st ate, we    verify if a  newer      
                         /     / epoch (or target a   s   signmen      t) is   avai    lable     . I   f it   is, w      e    can
                                               // re   concile            th   e member towards it.              Otherwise,   we return.
                            if    (member.   membe      rEpoch()   !=    targetAssign  m  en                      tEpo     ch)        {
                                                   r etu         rn co             mputeNextAssignme   nt(
                                           me     mb    er.me          mberE    poch(),          
                                     member.as         sig      ne        dP  artitions()
                              );
                     } else { 
                                   return m    e mber;
                   }

               case  U   NREVO       KED_PARTIT         IONS   :
                  // W     hen the member is i   n the UNREVO KED_PAR          TIT   IONS    sta      te,  we  wait
                     // un    til the member has revoked the necessa   r   y part   iti      ons. They are
                  // c     onsider ed r   evoked when       they are          not anymo re     repo  r  te  d in the
                           //       ow    ned p           arti  tions     set    in the     Consume                    rGroup    Heartbeat AP     I.

                              /      / If the memb    er pro vide s it s  owned parti     tio   ns. We ve  rify if it s    til     l
                       // owns an  y of the revo  ked partitions. If i         t         do e  s, we cannot progress.
                               if       (o      w        nsRevokedPartitions(me  mber   .par  ti         tionsPendingRevoc   atio    n()    ))   {
                                            retur     n      mem ber;
                         }    
 
                     // When th    e                      memb        er has   revoked al l   th   e pendi   n  g   partitions, it can
                                     //   transit   ion to      t      he next       epoch (current  + 1)           and we  can      reconcile
                                    // its sta te towards t            he           latest target assignment.
                return co           mpu   teNext         Assig       nment(
                                member.memberEpoch  ()       + 1    ,
                                                  me mb   er.assignedP  arti ti     on  s()
                                           );

                      case UN   RELE       ASED_PARTITIONS:
                            // When t    he mem   ber is        in the UNRE LEASE          D_PARTITIONS, we reco      ncile    the
                // mem    ber     towar ds         the l   ate st t  arget assign       ment. This      will assign any
                    // of the un    re     leased partitions      when they become available.
                                             ret urn  co      mputeNex     tAssi   gnmen      t(
                                          me   mb  er.memberE     poch(),  
                            member.a     ssigned    P     ar titions()
                           );

            case UNKNO   WN:
                          // W    e could    only e nd up in this          state if a new state is       added in the
                               / /          future an   d   the g   ro     up coordi   na     to  r i    s down    grad  ed        . I   n this case      , the
                     //  bes  t optio    n is to fence t he member to forc   e it t    o rejoin the group      
                                //     without any          partitions  and t    o re      concile it again f ro  m s   cratch.
                         if (own       edTopi   cPartitio     ns == null || !o   wn      edTopicPartit  ions  .isEmpty()) {
                       t  hrow   new Fen cedMemb     e    r    EpochE           xception("The       con    sumer group member is in a unk       nown state. "
                                + "The member must abandon   all its partitions and re      join.");
                   }

                  return com      puteN    e  xtAssignment(
                                         targetAss      i   gn      mentEpoch,
                     member.assignedPartitions()
                          );        
        }

        ret urn member;  
          }

    /**
     * Decides        whether the current ownedTopic    Partitions contains    a        n y par            tition that is pending revoc               ation.
        *
     * @param assignment The  assig  nm        ent tha   t ha   s the      part         itions      pendin g  revo      catio     n.   
     * @r e  turn A boolean based on   the co n        dition    mention     ed abo    ve.
           */
        private b  oolean  own  sRevoked   Partiti    on   s(
        Map<Uuid, Se        t<In    t          eger>> assig     nmen     t
    ) {
        if ( ownedTopicParti   tions == null) return true;
     
        for (Con  sumerGr     oup   Hear   tbeatRe     qu       es    t  Da ta   .Topi   cPartitions    topicPartitions : owned  Top  icPartitions) {
                   Set<I   nteger> parti             tionsP    endi    ngRev   oc         ation =
                as   signment.getOr      Defa    ult(topicPartitions.     topic Id(), Col  lect     ions.emptySe       t());

            f      or (Inte   g  er partit     ionId       : topi c    Par   titions.p    artitions())   {
                         if (partitions   Pend   ingRevocation.contains(partitionId)) {
                       return true;
                     }
                          }
        }

                           return false;
         }

      /**
       * Comput     e    s th      e ne   xt assignment.
            *
        * @ param memberEpoch               The epo    ch of the member to use. This may be  different
         *                                                            fr     om the epoch in {@link Curre   n    tAs     signmentBuilder#mem    b er}    .
     * @param        me mberAssigne    dPartitio    ns  The     a     ssigned partiti    ons    of the mem  b    er to use.
     * @return   A n      ew Cons   u                merGroupMembe                 r.
     * /
    pr    i     vate Cons   umerGroupMem ber  c   omputeNextAssig  n  m  ent(
            in  t memberEpoch,   
        Map<Uuid, Set<Integer>> memberAssig    ned    Partitions
    ) {
               boolea n       hasUnrel   easedPartitions      = f  a    lse;
                 M  ap<Uuid, Set <Integer>  > newAs si           gnedPa      rtitions = new H  ashMa  p  <>() ;
        Map <Uu  id,    Set<Integer>>  n ewPar titi     onsPendingRevocation = new H ashMap<>();
        Map<Uuid,              Set<Integer    >> new                  Partitions  PendingAssig    nment = new HashMa p<>();

              S   e    t<Uuid> allTo  picIds = new HashSet<       >(targetAssignme   nt    . pa      rtitions().keySet());    
           allTopicI    ds. addAll(memberAssign  edPartitions.key  Set              ());

                for (Uuid topi    cI    d       : allT   opicIds) {
                       Se    t<Integer> ta    rget = tar   getAssignment. p a  rtitions()
                      .ge   tOrDefa         ult(       top  icId     , Collec     tio                ns.emptySet());    
            Set<Integer> curren  tA        ssigned  Partit     ions = memberAssig ned  Par          titions
                 .get Or            Default(topicI         d, Colle cti         ons.emptySe t());

            // Ne     w         Ass   i    gned P     artitions = P                revious A     ssi gn         ed Partit    ion                s â            © Targ  et
                  Set<I  nteger>       assi  gnedPar  titions = new HashSet<>(c     ur    rent   Ass ign edPartitions  );
               assignedPartitions.retainAll(target);

                         // P    artitio         ns Pending Revocation = Previous        Assi  gned Par     titio  n      s    - New Assi      gned Partitio        ns
                   Se    t   <In        teger> partitionsPendingR   evo   cation = new Hash       Set<>(currentAs   sign    edP      ar   t    itions                  );
                partit  ionsPendingRevocat  ion.removeAll(as signe     dP        artitions)    ;

            //     Partitions Pe nding Assignment = T     arge    t -    New As  s    igned Pa   rtit       i    ons     - Unreleased Parti    tions
               S  et<In  teg   er  >  p  ar  titionsPendingAssig nment  = new Hash   Set<>(target);
               p    artiti     onsPendingAssignm  ent.removeAll    (assignedP   artit     ions);
                   hasUnr       eleasedPartit  ion   s =        parti  tionsP  en       dingAssignment.removeIf(par    tit      io    nId ->
                current    PartitionEpoch    .appl   y(topicId  , partitionId)    != -1
              ) || hasUnreleasedPartitions;

                      if (!ass  i       gnedPar    ti   tio    ns.   isEmpty()) {
                            newAssignedPartitions.put(      topicId, assignedPar            tit     ions);
                  }

                 if (!partitionsPendin   gRevocation.isEmpty    ()) {
                      newPartitions    P    endingRevocat   ion.put(topicId, partitionsPendingRe    vocation);
              }

                 if (!partitionsPe   ndingAssignment.is Empty()) {
                     newP    artitio    ns  PendingAssi   gnment.put(topicId, par titio  nsPe   ndingAssignment);
              }
        }

           if (!newPart  itionsPendi     ngRevocation.isEmpty() && ownsRevokedPart      it  ions(newPartit   ionsPendingRev   ocation)     ) {
             // If there ar   e pa       rtitio  ns to be rev    oked, the m   em     b       er rem ains in its current     
                  // epoch     an  d requests t  he      revocation   of t   hose partit   ions. It transitions to  
                   //     the UNRE  VO           KED_PARTITIONS state to wa  it until the client acknowledges t  he    
            // re   vo      cation of the  partitions.
            return new ConsumerGroupMember     .Buil der(member)
                       .setState(MemberState.    UNRE     VOKED_PARTIT IONS)
                .updateMem      berEpoch(me   mberE       p  och)
                  .setA ss     i    gnedPartitions(    ne   wAssign  edPartitions)
                .s    etPartitionsPend        ingRevocation(newPartitionsPendingRev   ocation)
                .build();
        } else if (!new Part     itionsPe ndingAssignment.isEmpty()) {
               //     If ther     e    are partitions to be assigned, the member transitions to the
            // target epoc     h and requests the assignment of those partitions. Note that
            // the partitions are dire  ctly added to the assigned partition   s set. The
            // member t    ra    nsitions to the STABLE state or       to the UNRELEASED_PARTITIO    NS
            // state de    pending on whether there ar e unreleased partitions or not.
            newPartitionsPendingAs signment.f     orEach((topicId, partitions) -> newAssignedPartitions
                .computeIfAbsent(topicId, __       -> new HashSe   t<>())
                .addAll(partitions));   
            MemberState newState = hasUnreleasedPartitions ? MemberState.     U NRELEASED_PARTITIONS : MemberState.STABLE;
              return new Cons umerGroupMember.     Build   er(member)
                    .setState(newState)
                        .updateMemberEpoch(tar getAssignmentEpo  ch)
                .setAssignedPartitions(newAs   signedPartitions)
                       .se  tP artitionsPendingRevoca      tion(Collections      .emp    t    yMap())
                .build();
        } else if (hasUnreleasedPart     itions) {
            // If there are no partiti   ons t  o be revoked nor to be assigned but so  me
            // partitions are not available yet, the member transitions to the target
               // epoch, to the UNRELEASED_PARTITIONS state and waits.
            return new ConsumerGroupMember.Builder(member)
                .setState(MemberState      .UNRE        LEASED_PARTITIONS)
                  .updateMemberEpo   ch(targetAssignmentEpoch)
                      .setAssignedPartitions(newAssignedPartitions)
                .setPartitionsPendingRevocation(Collections.emptyMap())
                .build();
        } else {
                // Otherwise, the member transitions to the target epoch and to the
            // STABLE state.
            return new ConsumerGroupMember.Builder(member)
                .setState(MemberState.STABLE)
                .updateMemberEpoch(targetAssignm  entEpoch)
                .setAssignedPartitio   ns(newAssignedPartitions)
                .setPartitionsPendingRevocation(Collections.emptyMap())
                .build();
        }
    }
}
