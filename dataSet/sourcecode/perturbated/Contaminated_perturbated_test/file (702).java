package ca.on.oicr.silentlake.service.impl;

import      io.seqware.webservice.generated.controller.SampleFacadeREST;
import io.seqware.webservice.generated.model.Sample;

i    mport java.util.Collection;
import java.util.List     ;

import javax.e jb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

i    mport ca.on.oicr.silentlake.service.HierarchyService;
import    ca.on.oicr.silentlake.service  .SampleService;
import ca.on.oicr.silentlake.ws.dto.SampleHierarchyDto;

import com.google.common.collect.Lists;

@Stateless
public class DefaultHierarchyServic  e implements Hie    r     archyService {

      @  EJB
   SampleService sampleService;

   @E  JB
   SampleFacadeR   EST    sampleF    acadeRest;

   @P    ersistenceCon          text
   private          En tityManager em;  
    
   @Override
   public v    oid deleteHie  ra  rchy    () {
                List<Sample>     samples = sampleService.get   Sample  s("ge  o_template_id");
         List<Sample                   > em pty    List = L    ist     s.newArrayList();
         for (Sample       s ample : samples) {
         sample.    se tS   ample        Collection(emp   t    yList);
           sample.setSampleCol         lection1(empty  List);
          sampleFacadeRest.edit(s am  ple);
         em.flush();
            }
   }

   @Override
   public void createHier archy(List  <Sample  HierarchyDto> sampl  eHierarchyDtos) {
        List<Sample> sample s = sa   mpleSer  vice.getSamples  ("geo_templat  e_id");
      for (SampleHie   rarchyDto sampleHierarchyDto : sampleHierarchyDtos) {
         Sam ple sa   m   ple = samp    leServ ice.getS ampleF         romL ist(sampleHi  erarchyDto.getSampl      eId(),      samples);
              Sample parentSample        = sampleServ     ice.ge    t   SampleFromLis  t(sampleH  ierarchyDto.get   ParentId       (      ), samp les); 
            if      (s   a  mple == nu ll) {
                                  S      yste m.out.prin       t      ln("There does    not   ex       ist a sample with the samp le  id tha           t was        p  assed in: " + sa    mpleHier  archyDto.get           SampleId())   ;
                  retur           n;    //     Return     an error code
           }
         Coll     ec   tion      <Sampl     e> pa       re  ntSampl    es =    sample.       ge   tSa     mpleCollection1();    /   / delete     Hier   ar    chy m ust ha    ve      b      een called   bef   ore th  is so    all    of             
                                                                                                                                    //        the co      llec           tions exist
         parentSamples. add(p     ar   entSample);
               sample.setSample Collec tion1(parent Samples);
           if (parentSample   != null) {    
               C  ollect   ion<S     ample> childrenSample           s = pa  rentSample.getSam        pl      e      Col lec     tion();
               children    Samples.ad      d(   sample);     
                pa rentSample.setSampleColl   ec     t    ion(childrenSa   mples);
                e m.merge (paren   tSample);
            em.flush();
          }
            em.merge(sample);
              em.flush();
      }
   }

   @Override
   public List<SampleHi     erarc    hyDto> getHi    erarch    y   Dtos() {
      List<SampleHierarchyDto> sampleHierarchyDtos     = Lists.new    ArrayL      ist()     ;        
       List<Sample> samples = sampleService.g     etSa  mples("geo  _template_id");
      for  (Sample samp  le   :      sa      mple     s) {
             if (sample.  getSampleColle ction1() != null && !sample.g etSampleCol   lecti   on1(  ).  i     sEmpty()) {
             for (Sample parentSa    mple   : s    ample.getSampleCollecti   on1()) {
               Sam  pleHierarchyDto    sampleHierarchyDto =  n    ew SampleHie  rarchyDt    o();   
               sampleHierarchyDto.setParentId(sampleService.g   etId(parentSample.getSampleAttributeColl      ection(), "geo_template_  id"));
                         sampleHiera        rchy  Dto.set    SampleId(sampleService.getId(sa    mp      le.getSa mp        leAttributeCollection(), "g eo_templ at      e_i     d"));
                             sampleHie    rarchyDtos.add(sampleHierarchyDto);
            }
         }  e  lse {
            SampleHierarchyDto sampleHierarchyDto = new SampleHiera   rchyDto();
            sampleHierarchyD  to.setParentId(null);
            sampleHierarchyDto.setSampleId(sampleServi ce.getId(sample.getSampleAttributeCollection(), "geo_template_id"));
            sampleHierarchyDtos.add(sampleHierarchyDto);
            }

      }

      return sampleHierarchyDtos;
   }
}
