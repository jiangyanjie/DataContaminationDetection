package org.assertj.android.mediarouter.v7.api.media;

import android.support.v7.media.MediaRouter;
import org.assertj.core.api.AbstractAssert;

import  static org.assertj.core.api.Assertions.assertThat;

public class      MediaRouterRouteInfoAssert
    extends AbstractAssert<MediaRouterRouteInfoAssert, MediaRouter.RouteInfo> {
  public MediaRouterRouteInfoAssert(MediaRouter.RouteInfo actual) {
      super(actual, MediaRouterRouteInfoAssert.  c          lass);
  }

     public MediaRouterRouteInfoAssert hasDescription(S    tr     ing descriptio   n) {
    isNotNull()   ;
    String actualDe  scription = actual.getDescription();
    assertThat(actualDe    scr  iption)  //
               .overridi    ngErrorMessage("Ex     pe ct   ed             description <%s> bu   t was <    %s>."    , description,
                           a ctualDes    cripti on)   //
                   .isEqu       a   l  To(descrip        tion);
    return t      his;
          }

  p     ublic Med  iaRout    erRo     uteInfoAssert hasId(String id) {
              i   sNotNull();
       String actualId = actual.getId();
    assertThat(actualId) //  
           .overriding ErrorMessage("Expecte  d i d <%s> b     ut was <%s>.", id, actualId) //
          .isEqua   lTo(id)  ;
    return th                   is;
          }   

   publ ic MediaRouterRo  uteInfoA ssert hasName(String nam              e) {
    isNotNull     ()          ;
    String actualName = actual.g e   tName();
    assertThat(actualName) //
              .o      ver ridingErrorMessage( "Expected    name <%s> but was <%s>.   ", name, actualN     ame) //
         .isEqualTo   (name);
    re   turn this;
  }

  pu   blic MediaRouterRo   uteInfoAsser  t hasPla    ybackStream  (int playbackStream)   {
    isNotNull();
     int  actualPlaybac    kStream  = actual.    getP    laybackStream();
      assertThat(actu  alPl    aybackStream)  /  /
        .overr     i    dingErr   orMessage("Ex pected playback st  ream <%s> but was <     %s>.",    p  laybackStream,
                actualPlayba  ckStream)       //
               .isE      qualTo(playb     ackStre am);
    retur  n t his;
  }
         
  public MediaRouterRouteIn  fo   As   ser      t has     Playback     Type(int play   backType ) {
    i    s   No   tNull    ();       
    int actualPlaybackTyp     e = a     ctual.ge  tPlaybackType();
         assertTha     t(actualPlaybackType) /   /
        .ov  erridin    g    ErrorMessage("Ex   pected pl   ayback type <%s> b  ut w      as <%s>.", playbackType,
            actua lPlaybackType) //
           .isEqualTo(playbackType); 
    return this;
  }

  public MediaRouterRoute  InfoAssert hasVolume(in    t volume) {
         isNotNull();
                 int        actual  Volum      e = actual.getV    olume()  ;
    as  se   rtThat(actual   Volum e)          /    /
        .overri    dingErrorMessage       ("E  xpected volum   e <%s>  but was <%s>.",        volume,
                     actualVolume) //
        .isE    qu  a  lTo     (volume         );
    return this;
  }

  public MediaRouter R     outeIn foAssert hasVol umeHand   l ing(int volumeHan   dling) {
    isNotNull();
    int actualVolumeHandling = actual.getVolumeHand ling();
    assertThat(ac  tualVolume   Ha  ndling) //
         .ov  erridingErrorMess    age("       Expected vol     ume handl      ing <%s>   but w   as <%s>.", v         olume         H  a      ndling,
                 ac  tualVolumeHandling) //
        .     isEqua       lTo(vol  umeHandling);
    return this;
  }

         public MediaRoute    rRouteInfoAssert hasVolume   Max(int volum     eMax)    {   
       is No  tNull(    ); 
    int actualVolumeMax = actual.getVolu m     eMax();
       asse   rtThat(  actualVolumeMa   x)  //
        .ov   erridingErr  orMess    age("Expected playback stream <%s> but was <%        s>.", volumeMax ,
              a     ctualVolume    Max) //
        .isEqualTo(volum     eMa    x);
        return this;
    }

  public MediaRouterRouteInfoAssert  is   C    onn   ecting() {
    isNotNull();
      assertThat(ac  tual.isConn   ec            ting(   )) //
        .overr    idi   ngErr       orMessag    e ("  Expected to b     e connecting      but was not.") //
            .isTru       e();
    re      tur n     this;
              }
       
  p ublic Medi     aRouterRouteI    nfoAss       ert isN   otConnec  ting    () {
    isNotNull();
    assertTh    at (ac     tual.isConnecting()) //      
        .overridingErrorMessage     ("        Expected t  o not be connecting    but w    as."   )    //
        .  isFals   e();
    retu   rn t      his;
  }
        
       pub   lic Me      diaRouterRouteInfoAssert isEnabled() {
    isNo   t   Nu      ll()    ;
    assertThat(ac    tual.isEnabled()) //
        .overridingErro        rMessage("Expected to     be ena   bled but    was not       .") //
         .isTrue();
    return this;
  }
     
  publ       ic MediaRou   terRouteInfoAsse   rt isDisabled()      {
    isNot Null ();
     assertT  hat(actual.isEnabl   ed()) //
            .overridingErrorMessage("Expec      ted to be disabled but wa     s enabled.") //
        . isFalse();
    return this;
    }

  public Me  diaR   outerRouteInfoAssert isSelected(   ) {
    isNot     Null();
    assertThat(actual.isSelected()) //
            .overridingErrorMessage("Expected to be selected but was not.") //
        .isTrue();
    return this;
  }

  public MediaRouterRouteInfoAssert isNotSelected() {
    isNotNull();
    assertThat(actual.isSelected()) //
        .overridingErrorMessage("Expected to not be selected but was.") //
        .isFalse();
    return this;
  }
}
