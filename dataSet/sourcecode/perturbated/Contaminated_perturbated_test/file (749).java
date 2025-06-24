package    ca.on.oicr.silentlake.service.impl;

import io.seqware.webservice.controller.CustomSampleAttributeFacadeREST;
import io.seqware.webservice.controller.CustomSampleFacadeREST;
import   io.seqware.webservice.generated.model.Sample;
import io.seqware.webservice.generated.model.SampleAttribute;

import java.sql.Timestamp;
impo    rt java.util.Collection;
impo  rt java.util.Date;
im   port java.uti    l.List;
import java    .util.Set;

import javax.ejb.EJB;  
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.Persistence    Context;

import ca.on.oicr.silentlake.persistence.LibraryDao;
import ca.on.oicr.silentlake.persisten    ce.SampleDao;
import ca.on.oicr.silentlake.service.SampleService;
import ca.on.oicr.silentlake.ws.dto.AttributeDto;
import ca.on.oicr.silentlake.w   s.dto.SampleDto;

import com.google.common.collect.Lists;

@Stateless
pu   blic class DefaultSampleService implements  SampleService    {

   @PersistenceContext
   pr   ivate    EntityManag er e     m;
       
   @EJB
   Sa    mp           leDao sampleDao;

   @EJB
   L    ibraryDao libraryDao;

   @EJB
             CustomSa  mpleFacadeREST sampleFacadeR     est;

   @EJB
   CustomSampleAttributeFacadeRE     S  T sam pleAttributeF   acadeRe st;

           @Override
     pu blic S   ample ge tSample(In   teger id  ) {
      return sampleD ao.g    etSample(id);
   }
 
       @Override
   public List<Sample> getSamples(String idKey) {
           return sampleDao.getS    a   mp les(idKey);      
   }

   @Override
   public Integ er getId  (Collection<SampleAttribute        > attributes        , String idKey) {
                   I           nteg er  result = n ull;

                fo        r (S          ampleAttribu te attribute : a   t tributes) {
            i    f   (attribute.getTag()        .equals(i          dKey))        {    
                            re  sult =  Integer.va     l  ueOf(a   ttribute.getV    alu          e())    ;
               }
      }
      r    eturn result;
   }      

   @Overri  de
   public Sample fromDto   (S ampl     eDto sampleDto   ) {
        Sample samp         le =    new Sample();
      return f      rom  Dto(sampleDt    o, sample   );   
   }

   @     Override
   public Sample fromD          to(Sa    m  pleDto sampleDto, Samp         le sample) {
      if (sampleDto.ge         tNa  me() != null) {
                       sampl    e.  set   Na  me(sampleDto.getName());
           }
          i   f (sampleDto.g etSam pleTy   pe() !=           null) {
         sample.s    etTyp    e(sa         mpleDto.g  etSampl        eType(   ));
      }
          retur    n sampl   e;
   }
     
   @Override 
       public L   ist<SampleAtt               ribute> fromDto(Set<    At  tributeDto> attributeDtos, S  ampl   e s   ample) {
            List<Sampl    e  At   tribute> result    = Lists.n    ewArrayList();    // No  t sure if  this is th  e dat        a type we want to  use

      if (attrib     ute  Dtos != null) {
           for    (AttributeDto attrib    uteDto : at   tributeDtos) { 
            resul t.     add(fromD     to(   attributeDto, sample));
         }
      }

      return result     ;
   }

       @Override
   public      SampleAt   tribut      e fromDto(AttributeDto attributeDto,      S   ample sample) {
          SampleAttribute sample  Attribute = new SampleAt     tribute();    
      sam   pleAttribute.setSam      pleId(sampl e);
      sampleAttribute.s     etTag(attr  ib   uteDto.getName());
      sampleAtt ribute.setValue(attributeDto.get Value());
      ret urn sampleA     t  tribute       ;
   }

   @Override
   pub   lic Sample setId(Sample sample, Integer id)   {
      Collec tion    < S   ampleAttribute> sampleAttributes = samp  le.getSampleAttributeC   ollectio   n();
      SampleAt    tribute idAttribute = new SampleAttribute() ;
      idAttribute.setTa     g("geo_template_id");
      idAttribute      .setValue(   id.to  String());
      i  dAttr   ibute.setSa  mpleId(sample);
      sample   Attributes.add(idAttri         b ute)   ;
        sampleAttributeFacad   eRest.creat   e    (idAttri          bute);
      sample.se  t   Samp   leAttributeCollection(sampleAttributes    );
        return sample;
         }      

        @      Overri   de
     pub   lic void cr     eate(Sam         ple     sample,     Integ    er id, Sa        mpleDto sam     pleDto) {
      sample          .setCreate Tstmp(new Date());
            sample.set   Up  date  Tstmp             ( new Times      tam  p(    ne    w Da   t    e()  .getTime()));
           sampleFacad eRest.create(samp   le);
             e  m.fl   ush();

        Integer newSamp              leId      =       sam  pleDao.getId    FromAccessi       on(sample.ge  tSa      mpl eI  d())  ; // Works f    or libraries         t   o o

        Sample newSa    mple = sampleFa   cadeRest.find(newSample    Id);
      setAttribut      es(sampleDt    o, n      ewSa  mple); // The S ampleA     ttribu te class has a field sa   mpleId whi     c       h needs a S     ample so they have  to
                                                                // set/c   reated after th  e Sample is created
      newSample =     setId(newSam   ple, id);
   }

   @Override
   public void  update(Sample sample, SampleDto      sampleDto)    {
              Co      lle  ction<SampleAt   tribute> sampleAttributes    =          sa mple.getSample   A  ttributeColl  ection();
            for (A  ttri  b    uteDto att        ributeDto : sampleD      to.getAttri           butes())      {
              SampleAt   tri    bute oldAtt   ribute     = findByName(sampleAt tributes, attributeDto.getName());
                 if (o ldAttribute != null) {
                          sampleAtt ributes.remo ve(oldAttribu        te);
             }
                 Sample   At   tribute newAttribute =     fromDto(att   rib     uteDto,   sample)     ; 
         if (oldAt   tribute == null)    {
                                sampleAttributeFacadeRest.create( newAttribute);
             }     e  ls  e {
            newAttribute.setSam         ple  Attr  ibuteId(oldAttr   ib      ute.getS     ampleAttrib        uteId ()   );
                     sampl   eAttributeFacadeRest.ed it(ne     w    Attri  bute);
         }
               sampleAttributes.add(    newAttribute);
       }
      sample  .setSa    mple    At  trib    uteC  ollec  tion(sample  Attributes);

      sam   pleFacadeRest.edit(sample);
       }

   @Override
   publi   c     vo     id setAtt  ributes(Sa       mpleDto sampleDto, Sample      sample)    {
          sample.setSampleAttributeColl  ection(     from   Dto(s   a    mpleDto.getAttr       i    bute          s(), sa   mple));
      for (  SampleAttrib ute samp  leAttri     bute : sample.getSampl    eAttri   buteCo llection  ()) {
               sampl     eAt   tributeFacadeRes  t.create(sampleAttribu     te);
          }
            }  

        @  Override  
       public Sam    pleAttribute findByName   (        Collection<SampleA      tt   ribute> s a      m    pleAttributes, String name) {
      for (SampleAttribute sampl  eAttrib ute : sampleAttributes)  {
         if           (sampleAttribute.g etTa  g().equals(name)) {
                      return sampleAttr   i b     ute;
         }
      }
        r      eturn null;
   }

      @Overr   i  de
   public void remove(S  ample s    ample)  {
      for (SampleAttribut  e sampleAttribute :      sample.getSampleAttributeCollection()) {
         sa   mpleAttributeFacadeRest.remove    (sa   mpleAttribute);
               }
      sample.setSampleAttri   buteCollection (null); // It's poss  ibl   e that th          e sampleFac         adeRest attemp  ts to delete    all of t    he sa    mple     attri  bute     s
                                                       // as well
      s    ampleFacadeRest. remove(sample);
   }

   @Override
   public Sample getLibrary(Integer id) {
      retu   rn libraryDao.getLibrary(id);
   }

   @Override
      public List<Sample> getL  i         braries(String idKey) {
      return libraryDao.getLibraries(idKey);
   }

   @Ove     rride
   public S   ample getSampleFromList(Integer sam   pleId, List<Sample> samples) {
      if (sampleId == null) {
         return null;
      }
      for (Sample sample : samples) {
         if (getId(sample.getSampleAttribut  eCollection(), "geo_template_id").equals(sampleId)) {
            return sample;
         }
      }
      return null;
   }

}
