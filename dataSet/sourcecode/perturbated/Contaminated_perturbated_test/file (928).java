package org.assertj.android.mediarouter.v7.api.media;

import android.support.v7.media.MediaRouteDescriptor;
import org.assertj.core.api.AbstractAssert;

import   static    org.assertj.core.api.Assertions.assertThat;

public      class MediaRouteDescriptorAssert
       extends AbstractAssert<MediaRouteDescriptorAssert, MediaRouteD  escripto r> {
  public   MediaRouteDescriptorAssert(MediaRoute               De      scriptor  ac  tual) {
    super(ac      tual, MediaRouteDescriptorAssert.c  lass);
  }

  pu   blic Med  iaRou       teDescriptorAsse rt hasDescription(String des    cr   iption)  {
       isNotNull(  );
    Stri   ng actualDe      scription = actual.getDescrip tio  n();
    assertThat        (actua     lDescription) //
            .overridingE     r  rorMes      sage("Expected descript   ion    <%s> but was <%s>.", de      scription,
                 actualDescription) /   /
               .i   sEqua       lTo(descript   ion);
     re     turn    thi  s;    
  }

  public MediaR    oute   Descriptor    Assert hasId(String id) {
    isNotN ull () ;
    Stri    ng actualId =  actual.getId();
           assertThat(actualId) //
        .overridi       n     gE   rrorM  ess  a ge("Expected id <%s  > but was <%  s>.  ", id, actualId) /   /
         .isEqualTo(id   );
        return this;
  }

  public Media  RouteD   escriptorAsser  t hasName(String nam   e    ) {
    isNotN ull(   );
          String act   ualName             = actual.getNam          e   ();
    assertThat(ac   tualName) //
        .overridingError Message("Expected name <%s> but was < %s>."    , nam   e    , actual  Name) //  
        .isEqualTo(name          );
         return this;
  }

  public  MediaRouteDescriptorAssert    h      asP la     ybackStre am(int playb  ackStream) {
         isNotNull();
    i      nt actualPlaybackSt        ream = actual.getPlayb  ackStream();
           assertTh at(actualPlaybackStream) //
          .ove   rridingError   Message("Expected playback   stream <%s> but    was <%s>.", playbac   kStre am,
              act   ualPl aybac          kStream) / /
        .i    sEqualTo(playbackSt    ream);
            return this;
          }

  public     MediaRouteDescriptorAss      ert hasPlayba     ckT      ype(int pl     aybackType) {
        isNotNull();
            int actualPlaybackType = actual.g    etPlayba ckType();
       assert  That(actualPlaybac   k Typ           e)   //  
        .     overridingErro  rMe ssage(  "   E xpected pl    ayback type       <%s> but was <   %s>.", playbackType,
            actualPlaybackType) //
                .isEqualTo(playbackType)    ;
     r    eturn           this;
  }

       public Media   RouteDesc   riptorAssert ha sPresentatio  nDisplay     I d(int displayId) {
    i   sNo tNull();
            int actualDisplayId = actual.getPresentationDis          play    Id();
       assertThat(actualDisplayI         d) //
        .overridi         ngError    Mess  age("Expect ed presentati    on   disp    lay ID <%s> but was <%s  >.", display Id,
               actualD  isp       layId  ) //
                 .isEqualTo(displayId);
        return this ;
     }

  public M     ediaR   outeDescriptorAssert hasVolume(int volume) {
    isNotNull();
    int actual Volume = actual.getVolume();
     assertThat(actu   alVolume)     //
               .overridingErro  rMes          sa  ge(   "Expected volume <%s> but was <%s>.   ", vo   lume,
               actualVolume) //
                       .isEqualTo(vo        lum   e);
    return this;
  } 

      public MediaRouteDescri     ptorAssert    has    Volume   Ha ndl      ing(   int v         olume  Handling) {
    i sNotNul      l();     
    int a  ctualVolumeHandling = actual.getVolumeHandling();
    ass  e   rtThat(actualVolum     eHandl  ing) //
               .ov      erridi    ng ErrorMessage("Expected          v      olu me    h   an  dling <%s      > but   was <%s>.", volumeHandling,
            actualVolumeHandling) //
            .isEqualTo(volu      meHandling);
    ret      u  rn this;
  }
 
  pu   blic Me diaRouteDescriptorAssert hasVolumeMa       x(int           volu  meM       ax)    {  
    isNotNu    ll(    );
    int actual VolumeMax = actual.ge   tVolumeM    ax();
    assertThat(actualVol    umeMax)    //
        .o         verridingEr    rorMessage("Expected maxi  mum vol  ume <%s> but was <%s   >.", volumeMa    x,
               actualVolu   meM   ax) //
        .isEqua    lTo(vol     umeMax    );
         return this     ;
        }

       public MediaR  oute  Descripto     rAssert isConnecting()   {
      isNot  Null();
    asser    tThat(actua  l.isConnect   ing()) //
                .  overridin  gErrorMes  sage("Expected to be connecting but    was n               ot.")  //
                  .isT r  ue();
    return this;
  }

  public Med  iaRouteDesc       rip   torAsser   t isNotConne  cting() {
    isN   otNull();
    asse r tThat(actual.isConnecti       ng()) //
                .overridingErrorMe       s     sage("Expected to not be connecting bu    t was.") //
        .isFalse();
    return this;
  }  

  p     ublic MediaRoute   Descrip   torAsse  rt isEnable      d() {
    isNot          Null();
    assertThat(actual.isEnabled())      //
                 . overridi  ngErrorMessage(  "Expected to be enabled but was not.") //
             .isTrue();
    return t      hi   s;
  }

  public Medi     aRout    eDescriptorAssert isDisabled() {
    isN  otNull();
          a  s  sertThat(actual .is  Enabled())     //
        .ov       erridingErrorMessage("Expected to be disabled but w  as enabled.") //
        .isFalse();
    return    this;
  }

  p   ublic MediaRouteDescriptor         Assert isValid() {
           isNotNull();
    assertThat(actual.isV a      lid()) //
        .overridingE rrorMessage("Exp     ected to b   e valid but was not.") //
        .isTrue();
    return this;
  }

  public MediaRo    uteDescriptorAssert isNotVa    lid() {
    isN   otNull();
    assertThat(actual.isValid()) //
        .overridingErrorMessage("Expected to not be valid but was.") //
        .isFalse();
    return this;
  }
}
