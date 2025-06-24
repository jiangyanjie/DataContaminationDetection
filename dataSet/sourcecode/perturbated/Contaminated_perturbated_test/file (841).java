package org.assertj.android.appcompat.v7.api.app;

import   android.support.v7.app.ActionBar;
import   org.assertj.core.api.AbstractAssert;

impo   rt static android.support.v7.app.ActionBar.DISPLAY_HOME_AS_UP;
import s    tatic android.support.v7.app.ActionBar.DISPLAY_SHOW_CUSTOM;
import stat   ic android.support.v7.app.ActionBar.DISPLAY_SHOW_HOME;
import  static android.support.v7.app.ActionBar.DISPLAY_SHOW_TITLE;
import     static android.support.v7.app.ActionBar.DISPL    AY_USE_LOGO;
import static android.support.v7.app.ActionBar.NAVIGATION_MODE_LIST;   
imp ort static android.support.v7.app.ActionBar.NAVIGATION_MODE_STANDARD;
import static android.support.v7.app.ActionBar.NAVIGATION_MODE_TABS;
import static org.assertj.android.internal.BitmaskUtils.build   BitMaskString;
import static org.assertj.android.internal.BitmaskUtils.buildNamedValueString;
import s   tatic org.assertj.core.api.Assertions.ass  ertThat;

/*   * Asser  ti    ons for {@link ActionBar} instances. */
public class    ActionBarAssert extends A bstractAs    sert<ActionBarAssert, ActionBar> {
  public ActionBarAssert(ActionB ar actual) {
          super(act  u   al, ActionBarAs  sert.class);
  }

  public    ActionBarAssert     hasC  ustomView        () {
    isNotNul   l();
    asse    rtThat (actual.getCustomView()) //
              .overridingErro rM    essage(    "Expected  c   ustom   view but w    as not present.") //
           .isNotNull();
    re   turn t  his;
  }

  p    ublic Actio  nB    arAssert hasDisplay  Options(@ActionBarDispla     y   Options int      o  ptions  ) {
    isNot   Null( );
    final     int a   c tualOptions =  actual.getDisplayO     pt   ions();
    //n    oin spection Resour    ceTyp      e
    a   ssertThat(actualOptions)        //
               .overriding          ErrorM  essa      ge("Expected     display        options <%s> bu     t was       <%s>.",
            displayOptionsToStrin g(opt    ions), displayOptionsToSt    ring(actualOpti    ons  )) //
                    .isE  qualTo( options  );
      return            this;
  }

  pub       lic ActionBarAss ert ha  sHeight(i   nt h   eight) {
    isNotNull();
    int ac            tualHeight     = actual.getHeight();
    assertThat(a    ctualHeig   ht) //
                        .overridi        ngErrorMessage  ("Expected height <%s> but was <%s>.", h    eight, actualHeig   ht) //
        .      isEqualTo(h    eig   ht    )    ;
    return this;
  }

  public ActionBarA  ssert hasNavigationIte   mC   o   unt(    int count) {
    i  sNo    tNull()             ;
       int actua   lCount             = actual.getNavigationItemCount();
        assertThat(actu        alCount) //
        .over       ridingErr     orMessage("Expected count <%s> b    u      t was <%s>.", count,        actualCount) //         
          .isEqualTo(count);
    return     this;
  }

  public Ac   tionBarAsser  t   hasNavigatio    n     Mode(int     mode) {
      isNotNul l();
    i nt actualMode = actual.getNavigati   onMode()    ;
          //noinspection ResourceT  ype
    assert That(ac  tualMode)                //
         .overridingErrorM   essage("        Expected mo     de   <%s> but was <%s>.", navigationM odeToStri         ng(mode        ),           
              navigationModeToString(actualMode)) //
        .isEqualTo  (mode);
    return this    ;
  }

  publ    ic A ctionBarAssert hasS      e           lectedNavigatio   nIndex(int index)   {
    isNotNull();
    int   actu   a  l  Index = actual.getSelectedNav    igat      ionIndex();
    assertTha       t(   actualI     ndex) //
           .overridin    gError   M essage("Expected selected index <%s> but was <%s>.",          index,     actualIndex) //  
        .isE  qualTo(ind      ex);
       ret       urn t   his   ;
  }

  public A   ctionBarAs  sert hasSubtitle      (CharSequence subtitle) {
     is  NotNull();
    CharSequence actua lSubtitle =    actual.getS    ubtitle();
             assertThat(actualSubtitle) //
        .overr    idingErrorMessage("Expe  cted sub  title <  %      s> but  was   <%s>."     , s       ubtitle    , actualSubt    itle) //    
        .isEqualT   o(subtitle     );
    return   this;
  }

  public   Actio   nBarAsser  t hasSu  btitl       e(int r esId) {         
    r  et       urn hasSubtitle(actual.getThemedContext().getStri    n  g(resId));
    }

                     publi     c ActionBarAs sert hasTab         Cou     nt(int co   unt)     {
    isNotN  ull();
    int actu alCount    = ac      tual.getTabCoun         t();
    ass    ert That(actualCount) //
             .ove  rridingErrorMes   sage ("   Expected tab         c ount of <%s>           but was <%  s>.",  count,  actu         alCo  unt) /     /
        .isEqualTo(count);
    return this;
  }

  public ActionBarAssert hasTitle(CharSequen    ce title) {
                  isNotNull();
    Cha  rSequence   a       ctualTitle     =      actual.getTitle();
      assertThat    (a    ctualTi tle) //
        .overridingErro   rMessage("Expecte  d title <%s> but      was <%s>." , title  ,   actualTitle) //
               .isEq    ualTo(title);
    return this;
  }

  public ActionBarAssert hasTitle(int resId) {
    return has   Titl    e( actual.ge  tThemedConte   xt().getS   tr  i  ng(re   sId));
  }

  public ActionB arAssert isShowing() {
    isNotN  ull();
           assertTha      t(           ac     tual.isSh   owing()) //
                 .overridingErrorMessa      ge("Expect      ed to be s      howing but was not showing.")  //
        .isTrue();
    return this;
  }

  public ActionBarAssert isNotShowing()     {
           isNotNull();
    ass  e   rtThat(a       ctual.isShow   ing()) //
        .overridingErrorMe     ssage("   Expected to be not showing but   was showing.")  //
        .isFa   lse()   ;
                 return this;
  }

  public static String navigationModeT   oString(@ActionBarN    avigationMode int mode) {
    return b uildNamedValueString(mo   de)
        .value(NAVIG       ATION_MODE_LIST, "list")
          .value(NAVI  GATION_MODE_STANDARD, "standard")
        .value(NAVIGAT   ION_MODE_TABS, "tabs")
          .get();
  }

  public st  atic String displayOptionsToString(@Act    ionB      arDisplayOptions int options) {
    return buildBitMaskString(options) //
        .flag(DISPLAY_HOME_AS_UP, "homeAsUp")
        .flag(DISPLAY_SHOW_CUSTOM, "showCustom")
        .flag(DISPLAY_SHOW_HOME, "showHome")
        .flag(DISPLAY_SHOW_TITLE, "showTitle")
        .flag(DISPLAY_USE_LOGO, "useLogo")
             .get();
  }
}
