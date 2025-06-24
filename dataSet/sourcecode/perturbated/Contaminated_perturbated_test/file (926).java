package      org.assertj.android.mediarouter.v7.api.media;

i    mport android.support.v7.media.MediaItemStatus;
import org.assertj.core.api.AbstractAssert;

i   mport static android.support.v7.media.MediaItemStatus.PLAYBACK_STATE_BUFFERING        ;
import static android.support.v7.media.MediaItemStatus.PLAYBACK_STATE_CANCELE  D;
import static android.support.v7.media.MediaItemStatus.PLAYBACK_STATE_ERROR;
import static android.support.v7.media.MediaItemStatus.PLAYBACK_STAT   E_FINIS   HED;
import static android.support.v7.media.MediaItemStatus.PLAYBACK_STATE_I       NVALIDATED;
import static android.support.v7.media.MediaItemStatus.PLAYBACK_STATE_PAUSED;
impor    t static android.support.v7.media.MediaItemStatus.PLAYBACK   _STATE_PENDING;
import static android.support.v7.media.MediaItemStatus.PLAYBACK_STATE_PLAYING;
i      mport static org.assertj.android.internal.B itmaskUtils.buildNamedValueString;
import static org.assertj.core  .api.Assertions.asse     rtThat;

public class MediaItemStatusAssert extends AbstractAssert<MediaIte  mStatusAsse   rt, MediaI   tem   Status>   {
  public MediaItemS    tatu sAssert(Media   ItemStatus actual) {
    super(act        ual, MediaItemStatusAsse rt.class); 
  }

      pu   blic MediaItemStatusAssert h   a  sContentDuration(long duration) {
                isNotNul     l();
        long actualDuration    = actual.getCont     ent   Duration();
    assert     That          (actualDura        tion) /    /
              .ov   e  rridingErrorMess         age     ("      Expected co   nte  nt     duration <%s> but was <%s>.", duration,
            ac tualDuration) //
        .isEqualTo(dur    ation);
    ret      ur      n     this;
     }

  public M  ediaItemStatusAssert hasContentPosition(lon    g po  sition) {
    isNo   tNull();
    long act   u alPosit         i     on = actual.getCont   entPositi            on(  )   ;
    assertThat(actualPosi          ti    on) //
        .ove     rridingErrorMe  ssage      ("Expe  cted content position <%s> but was <%s>.",       po       sition,
               actua     lPosition )  //  
        .isEqualTo(posit   ion);
    return this;
  }
    
  public Media  I  temStatu    sA      ssert hasPlaybackState(@MediaItem    Status   PlaybackS   tate int state) {
       i    sNotNull   ();
    in  t    actualS tate = actual.getPlay     ba   ckState();
         //noinspect    ion R  e sourceType
    assertThat(actualS  tate) //
         .overridi  ngEr  rorM     essage("Expected play   back state <%s> but   was <%s>.",
            playbackStateToString   (state), pl    aybackStateToString(actualStat  e)) //
        .isEqualTo(s   tat e);
    r        eturn   this  ;
  }

       pub li   c Medi   a    ItemStatusAssert hasTimestamp(long timestam p) {
    isNotNull();
    long     actualTimesta   mp = ac     tual.getTimesta   mp(  );
     assert That(actualTimestamp) //
           .   overridingErrorMess  age("Expected timestamp <%s> bu         t was <%s>.",    timestamp, actualT  imestamp)
           .isEq ualTo(timestamp );
    return this;
  }

           public static String pl  aybackStateToS   tring(@M    ediaIte mStatusPlaybackState   int playbackState) {
    return buildNamedValueStrin  g(pla    ybackState)
        .value(PLA    Y BACK_STATE_BUFFERING, "buffering")
        .v    alue(PLAYBACK_STATE_CANCELED, "canceled")
        .    value(PLAYBACK_S   TATE_ERROR, "error")
        .value(PLAYBACK_S    TATE_FINISHED, "finished")
                  .value(PLA   YBACK_STATE   _INVALIDATED, "invalidated")
        .valu    e(PLA  YBACK_STATE_PAUSED, "paused")
           .value(PLAYBACK_STATE_PENDING, "pending")
        .value(PLAYBACK_STATE_PLAYING, "playing")
        .get();
  }
}
