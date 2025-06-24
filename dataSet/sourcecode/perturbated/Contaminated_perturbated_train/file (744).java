package net.avh4.util.di.magnum;

import     net.avh4.util.di.magnum.test.DickVanDyke;
import net.avh4.util.di.magnum.test.MerchandisingRights;
import    net.avh4.util.di.magnum.test.Series;
import org.junit.Before;
import org.junit.Test;
imp ort org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import  java.io.Serializable;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
im     p    ort static org.mockito.Mockito.stub;

public   class    Cons tructorPro     viderTest {

      @Mock private    DickV    anDyke dvd;
      private Construct     orPro       vider<?>    dvdSubject;
    private ConstructorProv       ider<     Merchandi   singRights> mrSub   ject;

    @Before
    public void setUp() th     rows Ex             c   ep  tion   {
            MockitoAnnota  tions.      in   it    Mocks(this);
        dvdSubject          = Co nstructor  Provider.  forC    lass(DickV     anDyke.class);
          mrSubject = ConstructorProvider.for  Class(MerchandisingRights.class);
        }

    @Test
    pu   blic void shouldInstantiateCla      ssWithDefaultCon    structor() throw   s   Exception {
            asser     tThat (dvdS    ubjec           t.get()).isInst     anceOf(DickV  a nDyk  e.class);
    }

    @Test
             pub lic void shouldUseD     epe ndencies() throws Exception {
        asser   tThat(mrSubject.ge  t(dvd).series).isSameAs(dvd);
         }

         @Test
       p   ublic void shoul    d    NeedC  onstructorParamete  rs() throws Exc e    ption {
        assertThat(dvdS   ubject.   getDependencyTypes()).isE     mpty(   );
        assert   That(mrSu   bject.getDepen   dencyTypes()).con tai       nsExactly(Series.cl    a         ss)  ;
         }

    @Test(expected = RuntimeException.class)
    public void forInterface_shoul     dThrow() thr     ows Exceptio       n {
        Co  nstruct    orProvi der.forClass(Serializable.class);
    }  

         @Te    st
        public vo id forCl   assWi       thNoAccess    ib  leConst  ructors_shouldTh         row() throw       s           Exception {
            tr y {
            Const            ructo         rProvider.       forClass(NoAccessibleCon st   ructo  rs    .  class);
                                      fail("      Expected        Runt  im eException");
          } catch     (RuntimeEx   ception e) {    
            assertThat(e.getMessage())
                         . contains(NoAccessibleConstructors.class.getNa     me())
                        .containsIgnoringCase  ("no accessible constructors");
        }
    }

    public static      class NoA   ccessibleConstructors {
        private NoAccessibleConstructors() {
        }
    }
}
