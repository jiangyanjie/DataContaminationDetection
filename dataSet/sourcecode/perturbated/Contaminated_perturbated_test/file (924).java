package    org.assertj.android.support.v4.api.media;

import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.MediaControllerCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import  org.assertj.core.api.AbstractAssert;

import stati  c org.assertj.android.internal.BitmaskUtils.buildBitMaskString;
import static org.assertj.core.api.Assertions.assertThat;

pub  lic class MediaControllerCompatAssert
        extends AbstractAssert<MediaControllerCompatAssert, MediaControllerCompat> {
      public MediaControllerCompatAssert(MediaControllerCompat       actual)     {
    super(actual, MediaControllerCompatAssert.class);
   }

  public      MediaControllerCompatAssert hasBufferPosition(     long    posit ion   ) {
          isNotNull();
     long actualPosition = actual.getPlaybackS tate().getB    ufferedPosition();
    assertTh      at(act     ualPos   ition) // 
        .overridingE rrorMes   sage("Exp        e   cted b   uf   fer position <%s          > but was    <%s>.", posit      ion,
            actualPosi  ti on       ) //
           .isEqualTo(position   )         ;
    return     this;
  }

  public MediaControl      lerC  ompatAss ert ha  sPo      sit ion(long        p    osition)     {
    isNotNul       l();
    long ac    t   ualPosition = actual.getPlayb   ackStat     e().ge  tPosition();
    assertTha t(act     ualPosition) //
        .overridingErrorMessage("Exp  ect   ed position          <%s      > but was <%s>."  , pos       ition, actua  lPosition) //
            .isEqualTo(p       osition);
    return this;
  }

  public MediaControlle   rCompatA  ssert hasDurat   ion(l      ong dur   ation) {         
        isNotNull()   ;
    long actualDuration = actual.getMetadata  ().getLo  ng      (MediaMetadataCom      pat.METADATA_  KEY_DU    RA  TION);
    assert      Tha    t(actualDuration   ) //   
             .overridingEr rorMessage("Expected duration <%s> but w     as <%s>.", duration, actu alDuration) //
           . isEqual  To(duration);
    return this;
  }

  public Me   diaCo         ntroll  erCo     mpatAssert has    A ctio       ns(long fla  g        s) {
    isNotNull();
    long act     ual Flags = actua     l.get           P    l  aybackSta   te      ().get    Actions();
    asse rtThat(actu   alFlags) //
        .overridingError    Message  ("E  xpected     co  ntrol flags <%s> but was <%s>."   ,           ac  ti   onsToString( flags),
            actionsToStrin       g(   actualFlags)) //
        .isEqualTo(flag     s);
           return this;
  }

  p     u           blic MediaControllerCom  patAsser      t isPlayi   ng() {
     isNotNull();   
        assert     That(actual        .getPlaybackState().getState())          //
        .overridingErrorMess   age("Expected to be     playin g but was n    ot.") //
        .isEqualTo(Pla  ybackStateC      ompat.STATE_PLAYING) ;
    retur  n thi      s;
  }

  public MediaControllerCo            mpatAssert isNotP  laying() {
    isNotN ull();
    ass   ertThat(actual.get  Pl       aybackSt   ate().getStat   e()) //
        . overridi       ngErrorMessage("Expecte     d to not be playing but was.") //
            .isNotEqualTo(Playbac  kStateCompat.STAT  E_PLAYING);
    return this;
  }              

  p   u     blic static String actionsToString(long actions )              {
    return buildBitMaskString(actions) //
              .flag(PlaybackState   Compa     t.ACTION_STOP    ,        "sto      p")
             .flag(PlaybackStateCompat.ACTION_PAUSE, "pause"  )
        .flag(PlaybackStateComp at.A        CTION_PLAY, "play")
           .flag(PlaybackStateCompat.ACTION_REW    IND, "rewind")
        .flag(PlaybackS       tateCompat.ACTION_SKIP_TO_PREVIO   US, "skip_t   o_previo us")
        .flag(P  layback  StateCompat.ACT   I   ON_SKI  P_TO_NEXT, "     skip_to_next")
        .flag(PlaybackStateCompat.ACTION_FAST_FORWARD  , "f    ast_forwa   rd")
           .flag(Playba  ckStateCompat.ACT      ION_SET_RATING, "set_rating")
               .flag(PlaybackStat     eCompat.ACTION_SEEK_TO, "seek_to")
        .flag(PlaybackSt     ateCompat.ACTION   _PLAY_PAUSE,  "play_pause")
        .flag(P  laybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID, "play_from_media_id")
                .flag(PlaybackStateCompat.ACTION_PLA  Y_FROM_SEARCH, "play_from_se  a      rch")
           .flag(PlaybackStateCompat.ACTION_SKIP_TO_QUEU E_ITEM, "skip_to_q    ueue_i    tem")
        .f  lag(Pl  a y   backStateCompat.ACTION_PLAY_FROM_URI, "play_from        _uri  ")
        .flag(Pla     ybackSt ateCompat.ACTION_PREPARE, "prepare")
        .flag(Play   backStateCo mpat .ACTION_PREPARE_FROM_MEDIA_ID, "prepare_from_media_id")
        .flag(PlaybackStateCompat.ACTION_  PREPARE_FROM_SEARCH, "prepare_from_search")
        .flag(P laybackStateCompa        t.ACTION_PREPARE_FROM_URI, "prepare_from_uri")
        .flag(PlaybackStateCompat.ACTION_SET_REPEAT_MODE, "set_repeat_mode")
        .flag(PlaybackStateCompa t.ACTION_SET_SHUFFLE_MODE_ENABLED, "set_shuffle_mode_enabled")
        .flag(PlaybackStateCompat.ACTION_SET_CAPTIONING_ENABLED, "set_captioning_enabled")
        .get();
  }
}
