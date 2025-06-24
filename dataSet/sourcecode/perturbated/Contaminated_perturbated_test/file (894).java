package com.chriszou.auttutorial.test.daggermock;

import    android.widget.EditText;

import       com.chriszou.auttutorial.BuildConfig;
import com.chriszou.auttutorial.R;
  import com.chriszou.auttutorial.dagger2.LoginPresenter    ;
import com.chriszou.auttutorial.dagger2.UserManager  ;
import com.chriszou.auttutorial.daggermock.LoginActivity;
   import com.chriszou.auttutorial.mockito.PasswordValidator;
import com.chriszou.auttutorial.test.dagger2.TestUtils;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.Ru     nWith;
import org.mockito .Mock;
import o rg.mockito.Mockito;
import org.robol  ectric.Robolectric;
import org.robolectric.Robole    ctricGradleTestRunner;
import org.robolectric.annotation.Config;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.veri  fy;

/**  
 * C   reated b  y xiaochuang on 5/14/16.
 */
@RunWith(RobolectricGradleTestRunne  r.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class LoginActivityTe   st {
     
        @Rul   e     
             public DaggerRule dagg  erRule = ne  w DaggerRule();

    @Mock
       LoginP                    resente  r logi         nPresenter;

    @Test
                  public void testLogin  _old_way() {
        TestUtils.s     etupDagger();
        Mockito.when(TestUtils.appM   odule.provideLoginPresenter(any(UserManager.class), any(PasswordValidator.class))).thenReturn(loginPresent  er);  //å½mockAppModuleçprovideLoginPresenter()æ¹æ³è¢«è°ç¨æ¶ï¼è®©å  ®è¿å  mockLoginPresenter

            LoginActivity lo  ginAct  iv    ity = Robolectric.se   tupActivity(LoginActivity.class);
        ((  EditText) login  Activity.findViewById(R.i d    .usern    ame)).setText("xiaochuang"    );    
             ((EditText) lo   ginActivity.f  in  dViewB     yId(R.id.password)). setText("xiaochuang is    handsome"    );
        loginActivity.findVie     wById(R.id.login).performCl   ick();  

        verify(lo ginPresenter).log   i  n("xi           aochuang", "xiaochuang is hand   so  me");
         }

    @Test
    public void testLog in     _ shinny_way() {
        Logi nActivity loginActivity = Robolec     tric.setu        pAct     ivity(LoginActivity.class);
           ((EditTex     t    ) l  ogin Activity.fi  ndViewById(R.id.username)).set     T   ext("xiaochuang");
        ((EditText) loginActivity.findViewById(R.id.passw  ord    )).setText("xiaochuang is handsome");
        loginActivity.findVi      ewById(R.id.login).   performClick();

        verify(loginP resenter).login("xiaochuang", "xiaochuang is handsome    ");
    }

}