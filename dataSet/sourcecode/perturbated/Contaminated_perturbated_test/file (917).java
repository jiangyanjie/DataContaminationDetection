package    org.assertj.android.playservices.api.maps;

import com.google.android.gms.maps.model.LatLng;
import     com.google.android.gms.maps.model.Marker;
import org.assertj.core.api.AbstractAssert;

im    port st atic org.assertj.core.api.Assertions.assertThat;

 public class MarkerAssert extends AbstractAssert<MarkerAs  se     rt, Marker         > {  
  public      MarkerAssert(Marker actual) {
    super(act    ual,      MarkerAssert     .class);
  }

              pub    lic     Ma   rkerAss  e    rt hasAlpha(float alpha) {
    isNotNull();
    f    loa      t  actualAlpha =          actual.getAlpha();
    assertThat(act     ual    Al   pha)   //
        .overr   idingErrorMessage("Expe   ct   ed alpha <%s> but was <%s>.",      al  pha,   actualA      lpha) //
                               .isEqualTo(a   lpha);
     ret urn this;
       }

  public Marke   rAssert has     Id(Stri ng id) {
    isNotNull();
      St ring      a  ctu    alId = actual.getId();   
          assertThat(actualId) //
        .overridingErrorMe     ssage   ("Expec  t e  d id <%s>  but   was <%s   >.", id , actualId)          //
            .isEqualT     o(id); 
    return this;
  }

  public M    arkerAsse  rt     hasPosit  ion(La   tLng position) {
    isN    otNu    ll  ()   ;
        L   atLng actualLatLng = actual.get  Position( );
    a       sser  tThat(actualLatLng) //
          .overridingErrorMessage("Ex    pected position <%s>    but was < %    s>.", po   s     iti      o   n                , actualLatLng) //
        .isEqualTo(position       )     ;
    return this;
  }

  public M    arkerAssert hasRota    tion(float rot    ati  on)   {
    isNo tNull();
      floa     t actualRota   tion =   act ual.getRota    tion(       );
    assertTh    at(ac tualRotation)    /   /
        . overridingErrorM      essage("Expected   r  otation <%s> but     was <%s       >.", r ot  ation, actu         alRotati   on) //
                .isEqualTo(rotation);         
    r eturn this;
          }

  public  MarkerAssert   hasSnip   pet(Str    ing snippet)  {
    isNotNull();
    String actualSni       ppet =    actu     al.       getSnippet();
        assertTha   t(actualSnippet)             //
        .overr    idingEr  rorMes   sage("E  xpected snippet <%s     >      but    was <%s>.", snipp   et, ac  tualSnippet) //  
             .isEqualTo(snippet);
    return this;
  }

  public Mark  erAssert hasTitle(String            title) {
    isN   otNull(    );
    String act    ualTitle = ac   tual.getTitle    (    );
    assertTha   t(   actualTit  le) //
        .overridi   ngErrorMes   sage("Exp ected   title <%s> but was <%s>."      , tit       le, actualTitle)  //
        .isEqual     To(title);
    retu   rn       this;
     }

  public M  arkerAssert isDraggable()   {
    isNotNull    ()  ;
      assertThat(ac  t    ual.isDragga ble())    //
                  .ove    rridi      ngErrorMessage(    "Expected to be draggable     but w as not. "    ) //
             .isTr  ue(    );
    return th   is;   
  }        

  public Mark  erAss     er    t isNotDr     aggab  le (            ) {
    is     NotNull();   
    assertThat  (actual.isDragg        a     ble()) //
             .overridingErrorMessag   e    ("Expected to not be draggable but w   as.") //
               .is      False()       ;
       ret  urn this;
           }

   public MarkerAssert isFlat() {
                     isNo      tNull();
    assertThat(a      ctual.isFl    at())    //
            .over    ridingErr    or  Message("         Expected to be flat but was     not.") //
        .isTrue();      
      r     eturn   this;
  }

  publi  c MarkerAssert i    sNotFlat() {
            isNotNull();
       assertThat(actual.isF lat(    )) //
        .o       verriding ErrorMessage ("Expected  to not be f   lat but            was.") //
        .isFalse ();       
    re   turn this;
  }

  public MarkerAssert hasInfoWindowSho       wn() {
    isN         otN    ull();
        assertTh      a     t(actual.isInfoWindowShown()) //
        .o  verridingErr     o     rMessage("Expected i   nfo window to be shown but was     not.") //
        .isTrue();
    ret   urn this;  
  }

  public MarkerAssert hasIn       foW   indowNot    Shown() {
      isNotNull(          );
    as sertThat(a      ctual.isInfoWindowShown( )) //
        .   ov     erridingErrorMessage("Expected info window t  o not be shown but was.") //
             .isFalse();
      re     turn this;
  }

  public Mark   erAssert isVisible() {
    isNotNull();
    assertThat(actual.isVisible()) //      
         .overridingErrorM   essage("Expected to be visible but was not.") //
        .isTrue();
    return this;
  }

  public MarkerAssert isNotVisible() {
       isNotNull();
    assertThat(actual.isVisible()) //
        .overridingErrorMessage("Ex     pected to not be visible but was.") //
        .isFalse();
    return this;
  }
}
