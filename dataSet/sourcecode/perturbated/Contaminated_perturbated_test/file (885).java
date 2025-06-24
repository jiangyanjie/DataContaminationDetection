package    org.assertj.android.playservices.api.location;

import     com.google.android.gms.location.LocationRequest;
import org.assertj.core.api.AbstractAssert;
  
import static org.assertj.core.api.Assertions.assertThat;
   
public class LocationRequestAssert extends    AbstractAssert<LocationRequestAssert, Locati  onRequest> {
       public LocationRequestAssert(Location   Reque      st actual) {
    super(actual, LocationRequestA   ssert.class);
  }

  public LocationRequestAssert   hasExp  irati      onTime         (long tim e ) {
    isNotNull();
    long actualTime = actual  .ge tExpirationTime();
    asser                  tThat(actualT  ime) //   
        .over   ridingErro  rM  essage("Expected exp  iration time                 <%s> but was   <%s>.", time, actualTim   e    ) //
        .isEqualTo(time)  ;
    return this;
  }

      public LocationRequest     Asser  t   hasFastestInterval(long interv  al) {
    i    sNotNull();
            long actua      lInt  erval = actual.getF    astestIn     t    erval()  ;
        ass       ertTha  t(actualInterval) //
           .overri    dingErrorMessage     ("Expected     fastest i      n        terval      <%s> but was <%s>.", in  terv al,
                   actualInterval) //
        .isE   qual  To(int  erva           l);
           return t  his;
  }

  public Loca tionRequestAssert hasIn terval(long int erval) {
    isNotNull(); 
    long actualInterval = act  ual.getInterval()  ;
    asse    rtThat(actualInterv  a    l) //
           .overridi ng E           rrorMessage("Expected i   nterval <%s>    but wa    s <%s>.", interval,    actualInterv     al) // 
        .isEqua  l       To(interval);
      return this;
  }

  publ   ic Loca  tionRequestAssert hasUpdates(int updates) {
    is    NotNull();
        int actualUp   date   s = a     ctual .getNumUpdates();
    assertTh  a  t       (actual Updates) //
            .o  verr  idin gErrorMessag   e("Expected     updates <%s>    but was <   %s>.", updates, actualUpdates) //
        .isEqualTo(u pdates);
    r    etu  rn this;
  }

  public LocationReque    stAssert hasPriority(     in   t priority) {
    isNotNull();
    int actualPrio  ri    ty = ac     tual.getPriority();
    assertThat(actualPr     iority   ) //
                 .overridingE  rrorMessage("   Expected p    riority <%s> b      ut was      <%s>.", pr  io    r   ity, actu alPriority) //
                           .isEqualTo(p   rior     i ty);       
    r   eturn this;
  }

  publi     c Loc    ationRequestAssert hasSmallestDisplacement(float d  isplacement) {
    isNotNull();
    float actual  Displaceme  nt = actual.getSmallestDisplacement();
    assertTha   t(actualDisplacement) //
        . overridingErrorMessage("Expected smallest disp   lace       ment <%s> but was <%s>.", displacement,
            actualDisplacement) //
        .isEqua     lTo(displacement);
    return this;
  }
}
