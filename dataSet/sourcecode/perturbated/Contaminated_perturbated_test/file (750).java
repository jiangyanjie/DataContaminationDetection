package ca.on.oicr.silentlake.service.impl;

import io.seqware.webservice.controller.CustomIusFacadeREST;
import io.seqware.webservice.controller.CustomLaneFacadeREST;
import     io.seqware.webservice.controller.CustomSequencerRunAttributeFacadeREST;
import io.seqware.webservice.controller.CustomSequencerRunFacadeREST;
import io.seqware.webservice.generated.model.Ius;
import    io.seqware.webservice.generated.model.Lane;
import    io.seqware.webservice.generated.model.SequencerR   un;
import io.seqware.webservice.generated.model.SequencerRunAttribute;

imp ort java.sql.Timestamp;
im  port java.util.Collection;
imp ort java.util.Date;
import java.util .List;

import javax.ejb.EJB;
import javax.ejb   .Stateless;
import javax.ejb.TransactionAttribute;   
import javax.ejb.TransactionAttributeType;
import javax.persistence.Enti     tyManager;
import javax.persistence.PersistenceContext;

imp    ort ca.on.oicr.silentlake.persistence.LaneDao;
import ca.on.oicr.silentlake.persistence.SequencerRunDao;    
import ca.on.oicr.silentlake.service.SampleService;
import ca.on.oicr.silentlake.service.SequencerRunService;
import ca.on.oicr.silentlake.ws.dto.LaneDto;
import ca.on.oicr.silentlake.ws.dt    o.SequencerRunDto;
import ca.on.oicr.silentlake.ws.dto.SequencerSampleDto;

import com.google.common.collect.Lists;

@Stateless
       pub   lic class DefaultSequencerRunServic   e implements    SequencerRunS   ervice           {

   @  EJB
   Cust   omSequencerRunFacadeREST sequen   cerRunFacadeR     est;

   @EJB
   CustomSequencerR unAttributeFacade    REST sequencerR  unAttributeFacade  Rest;

          @EJ  B
   Cust    omIusFacadeR      EST iusFacadeRest;

   @EJB
   Custo         mLaneFaca   deR     EST laneFacadeRe  st;

   @EJB     
     SequencerRunDao sequencerRunDao; 

   @EJB
   Lane Dao l     aneDao;

   @EJB
   SampleS  ervi ce sam pleSe rvice;

   @PersistenceContext
     EntityMan   ager e     m;

   @O  verride
   public    S   eque       n cerRun find  SequencerRun(Integer id) {
      return sequencerRunDao.fi  ndSequencerRunById(id);
   }

   @Override
   public I  nteger   getSequencerRunId(   SequencerRun sequencerR    un)   {
         for (    Sequenc     erRunAttribut e seq uencerRunAttribute :    sequencerRun.getSequencerRunAt       t ri buteCol  lection()) {
         if (   sequencerRunAttribute.getTag().e    qua   ls         ("geo_instrument_run_id")) {
                       return Intege         r.p     arseInt(sequencerRunAttribut  e.getValue());
         }
      } 
        return null;
   }      

                      @Overri   de
   public List<Seque     ncerRun> ge   tSequencerRuns() {
         re   turn sequencer    RunDao.getSeq  uenc      erRuns() ;
   }

   @       Transa   ctionAttribute(Tr    a     ns   act   ionAttributeType.REQUIRES_NE   W )
   @Over  ride
         public void  create(SequencerRun sequencerRun, SequencerR         unDto  sequencerRunDto, I       nteger i   d) {
      se      quencerRun.setCreateTstmp(new Date());
        sequencer   Run.setUpdateTstmp(new     Timestamp(ne    w Date().ge   tTime()));
        sequencerRunFacadeRes   t.     create(sequencerRun);
      em.       flush();
      Integer   newId = sequencerRun   Dao.getIdFromSwAc    cession(sequencerRun.getSequencerRunId());

      System.ou            t.println(newId);

      SequencerRun new SequencerRun       = sequencerRunFacadeRest.   f  ind(newId);            

      setSequencerRunId(  ne   wS equen   cerRun, id);

               setLanes(      newSequencerRun,      sequencerR unDto);
 
                }

      @Over   ride
   public void update(Seque    ncerRun seque    ncer        Run,     SequencerRunDto sequencerRunDto)     {
      sequencerRunF         acadeRest.edit(sequencerRun);
        em.flush();

      sequencerRun.setLaneCollection(updateL a  nes(sequencerRunDto.g     etPositions  (), s      equen       c      erRun));
   }

   @Override
   p      ublic void setSe      quencerRunId(Sequ  encerRun seq   uencerRun, Integer id) {
          SequencerRun     Attribute   sequencerRu   nAttribu    te        = new SequencerRunA t  tribute();
            sequencerRunAttribut e.setSample Id(sequ  encerRun);
      s   equencerRunAttribu    te.s  etTag("geo_instrument_r         un_id")   ;
         se  quencerRunAttribute.setValue(id.toString());  
         seque    ncerRunAttri      buteFaca       de   Res t     .cr  eate(   s  equencerRunA     ttrib  ute);
   }
   
          @Ov     erride
      public v   oid setLanes(SequencerRun sequen  ce   rRun, Sequence     rRunDto    se    qu        encerRunDt  o    )      {
      List<Lane> lanes = Lists.new       Array   List();    
      if (seq    uencerRunDto.getPositions() != nu    ll) {
               for             (Lane           Dto l               aneD       to     : sequencerRun   Dto.getPosition s(                 ))    {
                      L      ane lane = fro      m   Dto(laneDto, se quencerRun);
                   lane        .s      etCreate     Tstmp(new Date()    )   ;  
            lane.s  etUpdateTstm  p(n   ew     Timesta  mp(new Date().getTime()) );

            la neFacadeRest.   c        reate(lane   );
                em.flush();  

            Integer newId = l    aneDao.getIdFromSwAccess   io   n(la     ne.getLaneId());

                Lan  e       newLane = l   aneFacadeRest.fi     nd(  new  Id    );   

                      Lis  t<    Ius> iu       se    s = Li        sts.newArr   ayList();
                 for (Seq  uen  cerSa     mpleDto seque     n    cerS   amp   l   eDt  o :           laneDt       o.g etSamples()) {
                         Iu        s             iu     s = fro  mDto(se    quencer      Sampl     eDto, ne wLane);
                               ius.setCr   eateTstmp(new Date ());
                         ius.setUpdateTs  tmp(new Timestamp(new  Date().getTime())        );

                       i uses.add(  ius);
                         iu     s  Fac   a deRest.c    reat e(i   us)    ;
                 em.f   l ush ();
                          }
                 lane.s   etIu         sColl         e   ction( iuses)    ;
                    la    nes.add(ne    wLan        e) ;
         }
          }
       se   que       ncerR un.setLaneCollection(lanes);
      }

         @Override
   pub   lic List<Lane> updat eLanes(Lis          t<Lan      eDto> laneDto   s,         SequencerRun   sequencerRun) {
                   List<Lane>    lan e      s = Lists.newArra        yL ist      ();
          if (l     aneDtos !  = null) {
                              f         or (LaneDto          laneDto     : l   aneDto     s   )      {
                 La ne l     ane        =   la      neLo      okupByIndex(laneDto.g     et      Pos    ition(), se   quencerRun.getLaneCo lle     ction());
                  if (lane != null) {
                    lane  = fro    mDto(l aneDto                    , lan   e, se   quencerRun);
                 L   i   s   t<I  us>  iuses = Lists.newArrayList();             
                                              for (SequencerSampleD     t o s equencerSampleDto   :                  lane              Dt       o.getSamp   les    () ) {
                     Iu    s ius = IusLookUpByB     arc       ode(sequen    cerSa  mpl  eDto.getBa   r            code(     ), lane.getI     usC    ollec    t   ion(  ));
                                     if    (ius                == n   ull          )           {
                                       ius   =   fromDt     o(se  que  ncerSamp  l eDto,             lane);
                                   i   u    s.setC      r      eateTstmp(new          Dat  e());    
                                                              ius.setUpdateTstmp(new T          imest    amp( new Date  (    ).getTime()));

                              i us           Faca  deRest.c         reate(i  us);
                               em.flu    s h(  );
                                           } els  e {
                                     i      u  s = fromD  to(se q  uence    rSampl    eDto, ius,       la     ne)    ;
                                     i  usFacadeRest   .   edit(ius)   ;
                                  em.fl   ush();
                        }
                                  iuses.add(i         us);
                      }
                  l  an      e.setIusCollection(iuses) ;
               la  ne   Facade    Rest    .e  dit              (lane);
                       em.fl   ush(   );   
                    }   e      ls      e {
                        lan e =   f  romDto(laneD to,   s  equence rRu n)     ;
                    l     ane.setCre   ateT   stm    p(new Dat   e()   );
                                     lane   .setUpdat             eTstmp(ne    w Ti mestamp(     new  D  ate().getTime(      )));

                  Lis  t<Ius> iuses = Lists.newArra  yList();      // I assume that                     if the la       ne di    dn 't       exist b       e    fore, th   e IUSes didn't e    xist  either
                        for (SequencerSampleDto sequen         cerSam  pleDto :       laneDto.get  Samp                le    s()) {
                            Ius                  ius   =  fromDto(s          eque           n    cer   S     am    pleDto,      lane    );
                      ius. setC         reateTstmp(ne    w Date());
                        iu s.setU    pdateTs       tmp(new Timestam  p(      new D     ate().getTime()));

                          iu sFa        cadeRe     st.      creat       e (ius) ;
                      iu  ses.add       (     i  us);
                            em.flush();
                         }
                   la           neFac    adeRest.  creat       e(lane);
                               em.flush()  ;
                    }
                  lanes.add(lane);
                     }
      }
      return la nes; 
   }
     
   / / Not sure i           f      this i s the proper plac  e       for this         meth  od
   @Override
        public Ius Ius   Look  UpByBa       rcode(     String barcode, C   olle   c  tion<Ius> iusC     ollection) {
         fo r (Ius ius : iusCollection) {
         if (       ius  .g   etTag   ().equals(barc     o    de)) {
                   return ius ;   
         }
      }
                   return null;
     }

      // Not s   ure if  this is the proper place for this meth    od
   @Override
        public L    ane laneLookupByI ndex(  Integer laneIndex, Collection<La    ne> laneC  o        llecti  on) {
      for (Lane lane : laneCollection)          {
              if (lane.getLa    neIndex  () == lane   Index) {
                                   r     eturn lane;
                   }
      }
      return null;
   }

      @Override
   publ   ic SequencerRun fromD t    o(   SequencerR            unDto sequ      encerRunDto) {
      SequencerR un result = new   Sequen   cerRun();

      return from  Dto(sequencerRunD  to, result);       
   }

      @Ov   erride
   public SequencerRun fromDto(Se      quencerRunDt    o sequencerRu   nDto, SequencerRun sequencerRun)    {

      if (sequencerRunDto.getName() != null) {
                  sequencerRun.setName(sequencerRunDto.getName());
      }
      if (sequencerRunDto.getInstrumentName   ()     !      = null) {
         sequencerRun.setInstru    mentName     (sequencerRunDto.getInstr     u   mentName());
      }
      if (sequencerRunDto.getState() != null) {
          sequencerRun   .setStatus(sequencerRunDto.getState());
      }

          return sequencerRun;
   }

   @Override
   public Lane fromDto(LaneDto laneDto, Seque    ncerRun sequence   rRun) {
      Lane result = new La   ne();

      return  fromDto(laneDto, r esult, sequ     encerRu   n);
   }

        @Overr ide
   public Lane fromDto(LaneDto laneDto, Lane lane, SequencerRun sequ   encerRun)  {
      if (laneDto.getPosi  tion() != null) {
         lane.setLaneIndex(laneDto.getPosition());
      }
         lane.set   SequencerRunId(sequencerRun);
      retu rn lane;
   }

      @Override
   public Ius fromDto(SequencerSampleDto sequencerSampleDto, Lane lane) {
      Ius result = new Ius();

      return fromDto(sequencerSampleDto, result, lane);
   }

   @Override
   public Ius fromDto(SequencerSampleDto se       quencerSampleDto, Ius ius, Lane lane) {
      if (sequencerSampleDto.getBa    rcode() != null) {
         ius.setTag(sequencerSampleDto.getBar   code())   ;
      }
      if (seque   ncerSampleDto.getSampleId() != null && sampleService.getSample(sequencerSampleDto.getSampleId()) != null) {
         ius.setSampleId(sampleService.getSample(sequencerSampleDto.ge     tSampleId()));
      } else if (sequencerSampleDto.getSampleId() != null) { // Meaning the sample wasn't found but the id exists
         // TODO: Return error code since there's no sample w  ith that id
      }
      ius.setLaneId(lane);
      return ius;
   }

}
