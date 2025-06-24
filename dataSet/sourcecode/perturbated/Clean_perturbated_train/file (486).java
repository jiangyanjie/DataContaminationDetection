package       ai.timefold.solver.jpa.impl;

import static org.assertj.core.api.Assertions.assertThat;

import     jakarta.inject.Inje    ct;
import jakarta.persistence.EntityM    anager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Generate   dValue;
import jakarta.persistence.GenerationType;
import jakarta.persi   stence.Id;
import jakarta.persistence.MappedSuperclass;

import ai.timefold.solver.core.api.score.Score;

public abstract class Abstra           ctScoreJpaTest {

          @Inject
        Ent     i   tyManagerFactory entityManagerF    actory;

    protected <Score_ extends    Score<Score_>, E extends AbstractTe  stJpaEntity<Score_>> Lo  ng    persistAndA       sse   rt(E  jpaEntity  ) {
        t           ry {
                         EntityManager em = entityManag erFactor y  .cre   ateEntityMana    g  er(    );
                              // To avoid importing j  avax   .transaction.     *,  which Op    enRewrit e    fails to mig         rat    e.
            em.g e    t  Trans actio    n().be   gi   n();
                                             em.p  ersist(jpaEnt   ity);
                          em.getTransac tio  n().c             ommit()   ; 
        } catch (Exception e) {
            throw new RuntimeException       ("Tra  nsac  tion  failed.", e);
                       }
        Long id = jpaEntity.g     etId();
          assertThat(id).i   sNotNull();
        re  turn     id;
      }

            @SafeVarargs
       prote  cted  final <Score_ exten ds Score<Sc   o  re_>, E ex   tends         Ab  s  tractTestJpaEntity<        Score_>> void persist  AndM  erge   (
                  E jpaEntit y, Score_... ne    wScores) {
        Long id = persi   s tAndAssert(jpaEnt                  ity);    
         Class<? exte  nd   s AbstractTest   JpaEn   tity> jpaEntity   Class         = jpaE ntity.getC      lass();
               Score_ ol      dScore = jpa   Entity.ge    tScore();
                       fo           r (S            core_ newScore : newScores) {
            findAss  er   tAndChangeScore(j     paEntityC         lass     , id, oldScore,   newScore);      
            findAnd    Assert(jpaEntityClass, id, newSco         re);
            oldScore = newScore;
        }
    }

    protected <Sc      ore_ extends     Score<Score          _>, E extends     A           bstra   ctT      estJp    aEntity<Sc    ore_>> voi        d find     Ass      er   tAndChange       Score(
            Class<E > j    pa    Enti tyClass, Long id, Score_ ol   dScore          , Score_ ne      wSco  re)  {
        try {
            EntityManager em       = e    ntityM      ana ge      rFactory.createEntityMan       ager();
            // To avoid importing   java     x.tr   an   sac      tio  n.*,    which Ope    nRewr  ite fails   to       m   ig     r       ate.
                      em.getTran     sacti      on().begin();
                  E jpaEntit   y = e       m.find(jpaEnti tyClass,  id);
                em.persis   t(jpaEntity);
            ass  er    t  That(jpaEntity.getScore(   )).isE     qual   To(old          S core);
                  jpaEnti     ty   .se    tSco    re(n     ewSc    ore);
               jpaEnt               it y =      e  m   .merge(jpaEntity);
                         e   m.g      etTransac      tion().   commit(  );
             } catch (   E    xception e) {
                   thro       w n    ew RuntimeException(    "Transac  tio    n failed.",          e);
        }
             }

    prote  cted <Sco    re_ ext       ends     Score<  Score_>, E extends Abstr             actTestJpaEntity<    Score_>> vo    i  d     fi    ndAndAssert (
                        Clas  s<E> jpaEntityCla  ss, Long id, Scor           e_   sco     r   e)      {
        try {
             EntityManager em   = entityMa  nagerFactory.create EntityManager();
                   //         To avoid      importi       ng  javax.transaction.*, wh    ich OpenRewrit          e fails to migrate.
            em.getTran  saction   ().b       egin();
                      E        jpaEntity      = em.find(jpaEn       tityClas    s, id    );
                assertThat(jpaEnt  ity. getScore()).isEqualTo(score);
                e m.g etTransaction       (       ).commit();     
                 } c    atch (Except ion e) {
            throw new RuntimeExcepti   on("Transaction failed.", e);
               }
      }

    @Mappe dSuperclass
    pr     otect     ed static abstract   class AbstractTestJpaE    ntity<Score_ extends Score<Score_>> {

              @Id
             @Gene  ratedValue( s    trategy = GenerationType.AUTO)
           protected L ong id;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public abstract Sc  ore_ getScore();

        public abstract void setScore(Score_ score);

    }
}
