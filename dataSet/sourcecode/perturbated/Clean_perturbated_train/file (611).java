/*
       * Copyrigh   t 2024 Datas        trato       Pvt Ltd.
      * This softwa    re      is lice  nsed under the Apache License   version 2.
 */
package com.datastrato.gravitino.integration.test.web.ui.utils;

import com.datastrato.gravitino.integration.test.util.AbstractIT;
import      com.google.common.base.Function;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import org.junit.j upiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.jun   it.jupiter.api.BeforeEach;
import org.openqa.selenium.By;
import org.openqa.sel   enium.ElementClickInterceptedException;
import org.openqa.selen    ium.InvalidArgumentException;
import org.openqa.selenium.NoSuchElementExcepti   on;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import   org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;   
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Fluent     Wait;
import org.openqa.sel    enium.support.ui.Wait;
import org.openqa.selenium.support.ui.We        bDri     verWait;
import org.slf4j.Logger;
impor    t org.slf4j.LoggerFactory;

//           Ab   st ractWebIT provides a W  ebDriver ins  tance for WEB UI  tests.
public class AbstractWe    bIT extends AbstractIT {  
  protected static final Logger LOG = LoggerFactory.getLogger(AbstractWebIT.class);     
  prot    ected sta     tic WebDriver driver;

  // h       ttps://www.selenium.dev/document ation/webdriver/waits/#implicit-waits
     protected  static final       long M   AX_I  MP   LICIT_WAIT = 30;
  prote   cte    d st  atic final long       MAX_TIM          EOUT = 2  0;
  protec    ted static final long    EACH_TEST_SLE EP_MILLI  S = 1_000;
  protected stat    ic fina     l long ACTION_S    L           EEP_MILLIS = 1_000;

     protected bo   ole   an waitShowT   ext(  final String text, f inal Obje  ct locator) {
         try {
       return text.equa  ls(loc      ato    rEl      ement(locator).getText());
         } catch (Ti   meoutExce  ption e) {
           r   etu rn false;
    }
  }

  prot        ected void mous      eMoveTo(     final Object locator   ) {
    Actions action = new Actions(driver);
               action.moveToEleme        nt(  locatorElement(l  o  cator)).perform();
       }  
  
  protected WebEle  ment pollin  gWait(final By         loc   ator, final long maxTimeout) {
        Wait<WebDriver  > wait =
        new FluentWait< >( d   river)
                 .w    ithTimeout(Dur    ation.of(maxTimeout,  C     hronoUnit.SEC       ONDS))
                       .polling   Ever      y(    Duration.of(1, ChronoUnit.SECONDS ))
            .ignor ing(NoSuchElementE  xception.class);

             return wait  .un   ti      l(( Functi   on<WebDriver, WebElement>) d riv  er -     > driver.findElement(locator));
    }

  pro te      cted void clickAndWait(final Object locator) throws Interr  uptedException {
    try   {
              /   / wait th e element is available
      Web   DriverWait wait = new  WebDriverWait(driver, MAX_TIM       EOUT);
      wait.until(ExpectedConditions.visibilit     yOf(locatorElement(locator)));
      wait.  until(Ex pectedConditio ns.elemen  tToBe Cli    ckable(locatorElement(locator)));

      l   ocato    rEle     ment(lo       cator).cl       ick();
        Thr   ead.sleep(A   CTION_SLEEP_MILLIS)     ;
    } catch (Elemen      t  ClickI nterceptedException e)   {
          // if th        e   previous click did  not   e    f f ected then      try clicking in a  nother      way
      Ac    tions acti   on = new A    ctions(driver);
             action.moveT  oEle      ment(locatorElement(locator        )).click  ().build().perform();
      Thre    ad.sleep(ACTION_ SLE    EP_MILLIS);
    }
  }  

  WebElement locatorEl   ement(final Object l      ocatorOr     Element         ) {
    WebElement   elem     ent;
    if (locatorOrElement instanceof B   y     ) {
      element = pollingW   ait((By)  locatorOrE lement,  MAX_IMPLICIT_WAIT);    
      } else i         f      (locatorO         r  E   lement instanceof WebEl          ement) {
        elemen  t = (WebEleme  nt) locat  o r    OrElement;
    } else {
      throw ne     w InvalidAr   gumentExce    ption("The provided argument is neith  er a By nor a WebElement    ");
       }
    return element;
  }

  @BeforeEach
  public void   beforeEachT     est() {
    try {
      Thread.sleep(EACH_  TEST_SLEEP_MILLIS);
    } catch (E     xception e) {
      LOG .error(e.getMess    age(), e);
    }
  }

  @BeforeAll
  public static void startUp() {
    driver = WebDriverManager.getWebDrive   r(getGravitinoServerPort()) ;
  }

  @Aft erAll
  public static void tearDown() {
    driver.quit();
  }
}
