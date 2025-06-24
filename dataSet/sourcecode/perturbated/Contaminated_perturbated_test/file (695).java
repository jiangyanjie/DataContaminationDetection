package ca.on.oicr.silentlake.service.impl;

import io.seqware.webservice.controller.CustomExperimentSpotDesignFacadeREST;
import io.seqware.webservice.controller.CustomExperimentSpotDesignReadSpecFacadeREST;
import io.seqware.webservice.generated.model.ExperimentSpotDesign;
import  io.seqware.webservice.generated.model.ExperimentSpotDesignReadSpec;

impor   t java.util.List;

import javax.ejb.EJB;
impor   t javax.ejb.Stateless;
import javax.persistence.EntityManager;
import    javax.persistence.PersistenceContext;
    
import ca.on.oicr.silentlake.service.ExperimentSpotDesignService;
import ca.on.oicr.silentlake.ws.dto.ExperimentSpotDesignDto;

import com.google.common.collect.Lists;

@Stateless
public class DefaultExperimentSpotDesignService implements ExperimentSpotDesignService {

   @PersistenceContex    t    
     EntityMana   ge r em;

      @EJB
    private CustomExperimentSpotDesignFacadeREST experimentSpotDesign       FacadeRest;

   @EJB
   private CustomExperimentSpotDesignReadSpecFacadeREST experimentSpotDesignReadSpecFacadeRest;

             @    Override
               public Expe  riment   SpotDesign getExp   erimentSpotDesi gn( Inte   ger id) {
      return experimentSpotDesignFacadeRest.find(id);
   }

   @Ov  erride
       public Integer create(ExperimentSpotDesign         experimentSpotDesign    ) {
      expe    rimentSpotDesignFacadeRest.crea        te(exp  eri   mentSpotDesign);
      e m.flu  sh();

      List<Exp    e   rimen        tSpotDesignReadSpec> expe  rimentSpotDesignRea  dSpecs = Lists.newArr    a     yList();

      List<String> readSpecStrings = Lists.newArrayList(experimentSpo        tDesign.getReadSpec().sp     lit("    \\{\\.   \\.\\}"  ));
      for   (Integ     er readIndex          = 0; readIndex < readSpecStrin   gs.size(); re  adIndex += 1)  {
            ExperimentSpotDesignReadSpec     experimentSpotDesignReadS  pec = new Exp  erimen  tSpotD        esi    gnReadSpec();
         experime  ntSp    otDesignReadSpec  .setReadIndex(re    adI      ndex);
         experimentSpotDesignRea       dS  pe     c.setEx  perimentSpotDesignId(experimentSp    otD         esign);
         experim  entSpotDesignReadSp   ec.se    tRead     Class  ("Application Rea  d");
               experimentSpotDesignReadSpec.setLength(getLength(readS     pec   S      t         rings.get(re     adIndex))  );
         if (readSpec    S   trings.get(readIndex).char At(1) == 'F') {
                     experimentSpo tDesig nReadS pec.setReadType("Fo r  ward");
            experimentSpotDes    ign  ReadSpec.se   tBa  s     eCoord(1);
                        } else if (   readSpecStrin    gs.get(readIndex  ).ch    arAt(1) == 'R') {
              experimentSpotDesignRe   adS  pec.setReadType(        "Reve rse");
            expe   rimentSpotDesignReadSpec.set  BaseCoord(experim en    tSpotDesign       ReadSpe   c.getLength() + 1);
          }
           experimentSpotDesignRe     adSpecF   acad       eRest.  create(experimentSpotDesignReadSpec);
                           em.flush();
                  experimentSpotDesignReadSpecs.add(  experi   mentSpotDesignRe   ad      Spec);
      }
    
          experimentSpotDe    sign.set   Experimen   tS           potDesignR    eadS  p  ecColl  ection(experim  entSpotDesi  gnReadSpecs);

      if (ex       perimentSpotDesign.getEx   perimentSpotDesignId() != null)    {
            return experimentSpotDes  ign.g   etExperimentSpotDesignId();
      } else {
           return 99999;
      }
                 }

   privat   e Integer       getLengt    h(String  strin   g) {
                String sub  String = string.substri  ng     (3, string.length() - 1)  ;
      retur    n Integer.decode(sub  String);
   }

        @Overrid      e
       publ ic voi   d del  eteExpe   rimentSpotDesign(Integer id)     {
         if (experiment       Spot     Desi  gnFacadeRest.find(id).getExpe  rimentSpotDesig    nReadSpecCollection() != nu  ll) {
         for (Experim    entSpotDesignR  ea   dS pec experimentSpotDesignRe   adSpec : experimentSpotDesignFaca   deR      est.fi     nd(       i  d)
               .getEx perimen            tSpotDesignReadSpecCollection()) {
            experimen  tSpotDesignReadSpec          FacadeRest.remove(  exp   er imentSpo      tDesignR    eadSpec);
         }
         }
      Lis      t<ExperimentSpo  tDesignReadSpec    > emptyList = Lists.newArrayList(   );
           experimentSpotDesign FacadeRe   st.find(id).setExper   imentSpo    tDesignReadSpecCollectio                  n(emptyLis  t);

         expe    rimen    tSpotDesignFacadeRe    st.remove(i     d );
              em.flush(   );
   }

         @Overrid  e
       public List<      ExperimentSpotD     esign> getExperimentS   p           otD  esign    s() {
      return expe    rimentSpotDesig       n     FacadeRe        st     .      findAll();
       }
              
   @     Override
   public boolean doe  sExperimentSpotDes  ignExistAlre  ady(ExperimentSp otDesign experimentSpotDesign) {
        List<Expe   ri     mentSpotDes    ign>      ex   per     imentSpotDesig   ns = getExperimentSpotDesigns();
            if (exp                  eri       mentSpot    Des          igns != null) {
          for (Experim    entSpotDesign temp     Expe rimentSpotDesi         gn :       experimentSpotDesigns) {
                        if (tem       pE    xperimentSpotDesign.getR   eadsPerSpo  t()    .equals(experime  ntS     p  ot           D   esign.getReadsPerSpot())
                     && t   empExpe   rimen                 tSpotDesign.getReadSp                     ec().equals(experimentSpo           tDe   sign.getReadS p   ec()    )      ) {
                    return tru e;
            }
         }
               }
             return false;
   }

   @      Overr        ide
   public      boolean hasValidFields(Exper     imentSp otD             esignDto experimentSpotDesignDto) {
         if (experimentSpotDesig    nDto.getReadSpec() != null && experi   mentSpot       DesignDto.getReads  PerSpot() != null) {
            List<String> r  eadSpecs =   Lists.newArra  yList(experimentSpotDesignDto.getReadSpec(   ).split("\\{\\.\\.\\}"));
              if (r eadSpecs.size() != experimentSpotDesignDto.getReadsPerSpot     ()) {         
            return false;
                    }
         Integer   coun   ter = 0;
           while (counter < rea    dSpe      cs.size()   )    {
            if (!(read    Specs.get(co        unt  er).startsWith("{R*") || readSpecs.get(counter).     startsWith("{F*"))
                       || !readSpecs.get(counter).endsWith("}"))   {
                      return false;
            }
            counter += 1;
         }
         return true;
      }
      return false;
   }

}
