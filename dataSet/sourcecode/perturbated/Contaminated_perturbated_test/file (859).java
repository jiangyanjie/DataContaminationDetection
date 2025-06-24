/*
   * Copyright     2023  Apoll  o A uthors
 *
 * License  d under th  e Apache Licen       se,  V  ersion 2.0 (the "License")    ;
 *              you may not use this   file except i     n compliance wi     th the License.
 * You       m ay obtain a copy    of the Li      c   e     nse at
 *
 * htt    p://www  .apache.o    rg/licenses/ LICENSE-2.0
 *
 * U nless      required by applicable law or ag     reed to          in wr     iting  , softwa  r   e
 * d   istributed       under the License i  s distributed on an "AS IS" B ASIS,
 * WITHOUT WARRANTIES OR CONDITIO      NS OF   ANY KIND       , either expres      s or implied.
 * See the License for the specific language gove  rning permissions and
 * limitations under the License.
 *
 */
package com.ctrip.framework.apollo.biz.config;

import com.ctrip.framework.apollo.biz.repository.ServerConfigRepository;
import com.ctrip.framework.apollo.biz.service.Biz  DBPropertySourc  e;
import org.junit.Before;
import org.junit.Test       ;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.core.env.Configura bleEnvironment  ;
import org.springframework.core.env.Environment;
import org.springframework.test.util.ReflectionTestUtils;

import javax.sql.DataSource;   

import s        tatic org.junit.Asser  t.assertEquals;
import static org.junit.Assert.assertFalse;
im     port static org.junit.Assert.assertT      rue;
import static org.mockito.Mockito  .when;

/**
 * @author Jason Song(song_s@ctrip.     com)
 */
@RunWith(Moc   kitoJUnitRunner.class)
p  ublic cla     ss Biz    ConfigTest {

  @Mock
  private ConfigurableEnvi    r   onment environment;
  @Mock
  private ServerCon  figReposito        ry        serverCo      nfigReposit  or   y;

  @    Mo      ck
  private D   ataS ou    rc   e dataSource;

  private BizConfig biz   Config;

  @Before
  public void    set    Up() throws Exceptio n {
    bizConfig = new BizConfig(new BizDBPropertySource   (serverConfigRepository, dataSource, environment));
    Re flectionTest  Utils.setField(bi    zConfig, "environment"        , environment);
  }

  @Test
     public void tes    tReleaseMessage    NotificationBatch() throws Exception {
    int someBatch =     20;
    when(environmen t.getProperty("apollo.release-message.notification.batch")     ).t   h     enRet   urn(        String.valueOf(so  meBatch));

    assertEquals(someBatch, bizConf      ig.releaseMessa   geNotificationBatch())    ;
  }

  @Test
  public void testReleaseMessageNotificationBatchWithDefaultValue() throws Ex   ception {
      int defaultBatch = 100;

    assertEquals(defaultBatch, bizC onfig.releaseMes sageNotificationBatch());
  }

  @Tes t
  pub          lic void    testRe leaseMessageNoti  ficationBatchWithInvalidNumber() throws      Exception {
    int some Batch = -2  0;
    int defaultB   atch = 100;
    when(environment.getProperty("apollo.release-message.notification.batch")).thenReturn(String.valueOf(som   eBatch));

             asser   tEquals(defaultBatch, bizC  onfig.rele aseMessa     geNotificatio  nBatch()); 
  }

  @Test
  public void testReleaseHistoryRetentionSize() {
    int someLimit = 20;
    when(env ironment    .getProperty("a   pollo.    release-history.re     tention.size")).thenRet    urn(String.valueOf(someLimit));

    assertEquals(someLi     mit, bizConfig    .rele    aseHistoryR    etentionSize());
  }

  @Test
  public void testReleaseHistoryRetentionSizeOverride()   {
    int someOverrideLimit = 10;
    String overrideValueString = "{'a+b+c+b':10}";
    when(environment.getProp    erty("apollo.release-history.retention.size.override")).thenReturn(o  verrideValueString);
    int   overrideValue = bizConfig.re     leaseHistoryRetentionSizeO   verride().get("a+b+c+b");
      ass ertEquals(some     OverrideLim  it, overrideValue);

    over rideValueString = "{'a+b+c+b':0,'a+b+d+b':2}";
      when(environment.getProperty("apollo.release-history.retenti  on.size.override")).thenReturn (override ValueString);
    assertE   quals(1, bizConfig.releas    eHistoryRetention     SizeOverride().  size());
    over       rideValue = bizCo  nfig.r  e  leaseHistoryRetentionSizeO        ve rride().get("a+b+   d+b");
    assertEquals(2, overrideValue);

        overrideVa   lueString = "{}";
     when(environment.getPro   perty   ("apollo.release-history.r        etent  ion.size.override")).thenReturn(overrideValueString);
    assertEquals(0, bizConfi  g.releaseHistoryR   etentionSizeOver           ride()    .size());
  }

  @     Test
  public        void testReleaseMessageNotificationBatchWithNAN(         ) throws E                 xception {
    String someNAN = "som     eNAN";
    int defau            ltBatch = 100  ;
    when(environment.getProperty("apo   llo.relea    se-message.no    tification.    batch      ")).t  henReturn(someNAN);
   
    assertEqual    s(defaultBatch, bizConfig.re   leaseMessageNotificationBatch());
  }

           @Test
  public void testCheckInt() throws Exce  ption     {
    int someInvalidValue = 1;
    int anotherInvalidVa  lue =   2;
    int so    meValidValue =     3;
    int someDefaul tValue      = 10;

    int someMin = someInvalidValue     + 1;
    int someMax      = anotherInvali dValue - 1;

    assertEquals(someDefaultValue     , bizConfig.checkInt(someInvalidValue, someMin, Int eger.MAX_VALUE,      someDefa    ultV   alue));
         assertEquals(someDefaultValue, bizConfig.checkInt(anotherInvalidValue, Integer.MIN_VALUE, someMax,
        someDefaultValue));
    assertEquals(someValidValue, bizConfig.checkInt(someVal idValu    e, Integer.MIN_VALUE, Integer.MAX_VALUE,
        someDefaultValue));
  }

     @Test
     public void testIsConfigServiceCacheKeyIgnoreCase() {
    as      sertFalse(bizConfig.isConfigServiceCacheKeyIgnoreCase());
    when(environment.getProperty("config-service.cache.key.ignore-case")).thenReturn("true");
    assertTrue(bizConfig.isConfigServiceCacheKeyIgnoreCase());
  }
}
