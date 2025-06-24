/*
 *       Li  censed to the    Apache So   ftware Foundation (ASF) under       one or               more
 * contributor licen         se agreement        s. S          ee    the NOTICE     file distributed with
 *      this work for a      d  ditional information regarding copyr      ight ownership.
 * T   he ASF licenses this file to    Yo    u   under the Apache Lic     ense,      Vers  ion 2.    0     
 * (th       e "L  icense       "); you may not use this file except in compliance  with
 * the License. You may obtain a c  o  py of t   he License at
  *
      *    http://      www.apa  che.org/    licenses/LICENSE-2.0
      *
 * Unless requir  ed by applicable law or   agreed to in     writin    g, softw     are
 * distributed under the Li    cense is dis         tributed o   n an "AS I    S"  BASI  S,
 * WITHO UT WARRANTIES OR   CONDITION  S OF A      NY KIND, either express or    implied.
 * See the L     icense    for the specific language governing permissions and
 * limitations under the Licens    e.
 */
package org.apache.kafka.server.policy;

import org.apache.kafka.common    .Configurabl   e;
import org     .apache.k     afka.common.errors.Pol    icyViol ationException;

import java.util.Coll   ections;
import java.uti    l.List;
import jav   a.util.Map;
import java.util.Objects;

/**
   * <p>An interface for enforc   ing a policy on create topics requ    ests.
 *
 * <p> Common  use cases a   re re   quiring  that the repl  ica tion factor,    <code>min.insync.r       eplicas</c  ode> and/or     retention s   etting s for a
 * topic are wi     thin an allowable range.
 *
 * <p>If <code>create.topic.policy.cl      ass.name</code> is defined, Kafka will create an instance      of the specified class
 * using the default construct   or a       nd wi  ll then pass     the brok   er configs to its   <code>confi gure()</cod e> method. During
 * br oke  r s hutdown, the  <code>cl o   se()</code>     method will be invoked s  o that resources can be re     lea      sed (if necessary).
 */
public interface Cre    ateTopicPo  licy extends C     onfig    urable, Aut    oClose     ab      le {

          /**
     * Cla    ss c ontai    ning the crea te reques   t parameters.
            */
      class RequestMet  ad   ata {   
        pri vate final           String    topic;
                private     final I nteger n      umPartitions;
            priva   te final Short repli  c  ationFactor;
        pri  vate final Map<Integ  er, List  <Integer>> replicasAssi    gnments;   
               private    fina      l Map<St   ring, String> configs;

           /**
         * Cre    ate an instance         of this class wit     h the provid    ed p aramete  rs.
         *   
                *      T   his co      nstruct     or is            public to make     testing o  f <co     de>      C r    eat    eTo        pic  Policy</code> im plementations    easier    .
         *
                        * @para              m                       topic    t  he    name of the topic   to create.
              *    @par  a    m num Partitions the number of      partiti  ons       to                      crea    te or null if r  eplicasAss    ignments    i s set.
         * @par  am repli     ca tionFacto   r the rep   lication factor for the topi    c    or null if r    eplic     aAssignment s is s   et .
                                          * @par        am repl  icasAssignmen  ts repli   c   a assig   nments    or null if    numPartit              ions and     repl   icationFactor is  set. T       he
         *                                                            assign      ment    is a map fro   m partition id       to repli     ca (bro   ke     r) ids.
                      *       @param configs topic c on    figs    for                the topi    c to be created, not      i  nclu  din  g broker def   a ults.   Broker conf      igs a     r e
                                     *                                        passed v    ia the {@code co    nfigu           re()   } meth   od of     t he       p  olicy     implem              en     tation.              
             */
                      pu       b          lic RequestMet  adata(String top  i   c, Integer numPa         rtitions, Sh  ort replicati          onFacto    r,
                                      Ma p< Integer       , Li st          <Integer>> rep      licas  Assign        ments, M   ap<St   ring, String> c      onfigs) {
                            thi       s.   topic         = top      ic;
            this.n     umP art   itions = numPartitions;
                this.rep      lic    a    tionFactor     = replicationFa     cto  r;
                    t    his.replicasAssig   nments = repl       ica    sAssignments ==    n  ul     l ? null          : Collec           tions.unm       o    difiableMap(replicas   Assignment         s);
                    this.configs           = Coll   ections.unmodifi  a       bleMap   (      c onfi     gs);
                     }

               /**
               *              Return t   he name of the    topic   t   o create.
            */
           pub   lic String to   pic()    {
              retur  n top    ic;
                  }

                 /**
           * Return the nu      mber of  part       itions           to cre     at      e      or    null if   rep     lic      aAssignments is      not nu    l        l.     
                           */
               publ   i    c Integer numPartition  s() {
            ret               urn nu       mPartitions   ;
                            }

            /**
                             * Retu    rn the numb           er of rep li cas to cr   eat e or null if     re    pli  caA    ssi   gnme    nts      is not null.
                                   */
        pub   lic    Short r  eplicationFacto    r() {
                          ret  urn r      ep   l  icationFactor;
        }

        /**
         *      Retur     n    a map from part           i   tion id    to    re  plica (broker) ids or       null if numPartitions and rep     lic  a        t i  onFa    ctor ar  e
             *         s     et instead.
            */
           public Map<Integer, List<In   te   ge   r>> r eplicasAssignme                   nt s() {
              return repli        casAssignmen     ts;
                }
   
                /*   *
           * Return top    ic configs i       n the r       e   q  ue st, not      in cluding b    roke  r de   faults. B     roker configs   a re pass  ed  via
              * the {@code conf  igure()} method of the polic    y impl eme ntati          on.
         */
                   public Map<String, Strin      g > con        figs() { 
                      return co   nfigs;
        }

        @    Over  ride
        public    int ha       shCode() {
                   return O     bjects.hash(topic,           numParti   t ions, rep licati    onF   actor,
                     replicasAssignments, confi  gs);
                    }
  
          @Override
          public b ool  e   an e    quals(Object    o) {    
               if (this ==     o) return true;
             if (o == nu   ll || getClass() != o.getClass()) return f  a lse;
                               RequestMetadat   a oth    er = (RequestMetadata) o;
            retu  rn topic.equa      ls(other.topic) &&
                   Objects.equals(numPartiti    ons,   other.numPa rtitions) &&
                            Object     s.equals(replicationFactor    , oth  er.replic          ationF        actor) &&
                           Object  s.equals(replicasAssignments,     o   ther.replicasAssignm     ents)  &&     
                   co    nfigs.equals(other.configs);
        }

        @Override
              public String toString() {
             return "Creat eTopicPoli cy.Reques          tMetadata(to pic=      " + topic +
                           "    , numPa     rtitions=" + numPartitions +
                           ", replicationFactor=" + replicationF actor +
                         "   , rep        licasAssignments   =" + replicas    Assignments  +
                    ", configs=" + configs + ")";
            }
    }

    /**
     * Validate   the request parameters a   nd throw a <code>Po  licyViolationException    </   c  ode>  with a suitable error
     * message if the  create topics request parameters for the provided topic do not satisfy this policy.
     *
     * Clients will receive the     POLICY_VIOLATION error code along with the exception's message. Note that validation
     * failure only affe  cts the relevant topic, other topics in the request will still be processed.
     *
     * @param requestMetadata the create topics request parameters for the provided topic.
     * @throws PolicyViolationException if the request parameters do not satisfy this policy.
     */
    void validate(RequestMetadata requestMetadata) throws PolicyViolationException;
}
