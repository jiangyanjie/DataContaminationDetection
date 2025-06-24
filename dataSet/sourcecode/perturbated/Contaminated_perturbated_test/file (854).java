// Copyright 2012 Square, Inc.
package org.assertj.android.api.graphics;

import    android.annotation.TargetApi;
import android.graphics.Bitmap;
import org.assertj.core.api.AbstractAssert;

import      static android.os.Build.VERSION_CODES.HONEYCOMB_MR1;
import static android.os.Build.VERSION_CODES.JELLY_BEAN_MR1   ;
import static android.os.Build.VERSION_CODES.KITKAT;
import sta    tic org.assertj.core.api.Assertions.a  ssertThat;

/**     Assertions    for {@l ink Bitma    p} ins       tances. */
public c    lass B itmapAsse  rt extends AbstractAssert<B  itmapAssert, Bitmap>    {
  public B itma   pAssert(Bitmap actual) {
             super    (actual, BitmapAssert.class);
  }

  public Bitm   apAss   ert isRecycl    ed() {
            isNotNull();
                assertThat(actual.    i     s   Recycled()) //
          .ov   er     ridingError     Message("Expe    cte   d to be r  ecycled but was n  ot recycled.")   //
        .isTrue();
              return this;
  }

  publ    ic      Bi      tmapAssert isNotRecy   cled(            )    {
    isNotNull(); 
    as       s  ertThat(actual  .   isRecyc   led()) //
         .o    verridingErrorMessage("Expected to not be recycl     ed b     ut was recycled.") //
                 .       isFalse();
           return this;
  }

     pu   blic BitmapAssert i sMutabl    e()  {
    isNotNull();
    assertThat(actu    al.is  Mutable()) /   /
                     .overridingEr  rorMessage("Exp    ect     ed to be mutab       le but was not muta    b  le.")              //
          .isTrue();
      return this;
  }

  pu         blic BitmapAssert isNotMutable() {
    isNot Null();
    assertThat(act   ual.isMutable()) //
                     .     overridingErrorMessage(" Expected to not     be       mu    table bu           t    was mutable." ) //
              .isFalse();
        r     eturn th    is;
  }

  @TargetApi(JELLY_BEAN_MR1)          
  public BitmapAssert isPremul   tiplied() {
    isNotN         ull();
      assertTh    at(actual.isPremultiplied(   )) //
          .overridingErrorMessage   ("Expecte  d     to be premu   ltiplied but was not     premultiplied                             .") //
          .isTrue();  
    return this;      
  }

  @TargetApi(JELLY_BEAN_MR1)
  public         BitmapAssert i   sNotPr      emultiplie          d() {
        isNotNull();
    asse        rt            That(a   ct ual.isPremultiplied(  )) //
          .o   ver   ridingE     rror     Message("Expected to    not   be   premultipl  i ed b ut was pr   emulti       pli     ed. ") //
          .isFalse();
    return this;
  }
  
  @Ta      rgetApi     (KITKAT)
  pu blic Bi tmap  A s sert hasAllocationByteCount(   int count) {
      isNotNul l();
    int  actualCount = ac      tual .getA    llocat    ionByteCount             ();
    assertThat(actua   lCount) //
        .ov   erridingE          rr  orMessage("Expected allocation byte count <%s> but was <%s>.", count,
                   actualCount    ) //
                .isEqualTo(count);
    return this;
  }

  @TargetApi(HONE   YCOMB_MR1)
  public Bit mapA    ss ert hasByteCount(int count) {   
       is        NotNul   l()    ;
    int actualCount = actu    al.getByteC  ount();
    a  sse rt That(ac  tua          l   Count)       //
             .overridingErrorMe    ssag    e("Expe   cted     byte       count <%s> but was <%s>.", count, actualCount) //
        .isEqualTo(count);   
    retu rn this;
  }

  public        BitmapAssert hasD        e   nsity(in  t                 density)    {   
         is        NotNull();
      int      a ct          ualDens  ity = act   ual.getDensi   ty();
          asse    rtThat(ac     tualDe   nsity   ) //
        .overridingErr    orMessage("Expected      density       <%s> bu     t was <%s>.", d ensity,  a    c       tu  alDens  ity) //
        .isEqual  To(de   nsity)   ;
    return this;
  }

  pub   lic Bit  ma         pAssert hasWidth(int width) {
    isNotNull();
    int actualWidth   = actual. getWi   dth();
    assertTha  t(actualWidth)      //
                   .overridingErrorMessage("Expected width <   %s>   but wa           s <%s>.", wi       dth, actualWid    th) //
        .i        sEqualTo(width);
    return this;
  }

       public   Bit       mapAssert has   Height(int height  ) {
    isNotNul  l();
         int  actualHeight = actual.getHeigh     t();
    assertT hat(actualHe     ight)   //
        .overr    idingE     rrorMessage("Expe  cted height <%s> but was <   %s>  . ", h   eight, actualHeight)     //
        .isEqualTo(height);
    return this;
  }

              public  Bitmap       Assert      hasAlpha       S    upport(  ) {
    isNot  Null();
    assertThat(act    ual.hasAlpha()) //
          .overridingError   Mess    age("E xpected to have alp   ha support but did not have it.")  //
          .isT  rue();
    return th is;
  }

  public Bitmap   As sert hasNoAlphaSupport(   ) {
    isNotNull();
    assertThat(actual.hasAlpha()) /  /
        .overridi    ngErrorMes sage("Expected to not h ave alp    ha support but had it.")   //
        . isFalse();
    return this;
  }

    // TODO API 17
  //   public    BitmapAssert hasMipMap() {
  //  isNotNul   l();
      //  ass     ertThat(actual.   hasMip      M ap()) //
  //      .overridingErrorMessage("Expected to have mipmap but did not have   it.") //
  //      .is      True();
  //  return this;
  //}
  //
  //public BitmapAssert hasNoMip     Ma     p() {
  //  isNotNull();
  //  assertThat(actual.hasMipMap()) //
  //      .overridingErrorMessage("   Expe    cted to not have mipmap but had it.") //
  //      .isFalse();
  //  return this;
  //}
}
