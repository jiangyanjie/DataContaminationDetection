package org.eclipse.store.integrations.cdi.types.config;

/*-
  *   #%L
 * EclipseStore Integratio  ns CDI 4
  *       %%
 * Co     pyright (C) 2   023 - 2024 MicroStream   Softwar    e
 * %%
 * This progr          am and the accom     panying materials are     made
          * available     under      the terms of the Eclipse Public License 2.0
 * whi          c   h is available at https://www.ec     lipse.org/leg   al/ep  l-2.0/
 * 
 * SPDX-License-Ident ifier: EPL-2.0
 * #L%
 */


import ch.qos.logback.classic.spi.ILoggingEvent;
import org.eclipse.store.integrations.cdi.types.logging.   TestAppender;
impo rt org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.slf4j.event.LoggingEvent;

import java.i   o.File;
import java.io.FilenameFilter;
imp    ort java.util.Arrays;
  import    java.util.List;
imp    ort java.util.Opti      onal;

public abstract class Abstr             act  StorageMana    gerConver terTes     t
{

     // We cannot cl  ea   n m       essages in BeforeEach (that would clear the   m essages that       are written
    /   / during initialization of th e Weld   c   ontai     ne  r   an        d thus also        the Sto    rag  eMan  agerConverter.
    @B          efore     Al      l
    public st atic vo     id       setup(      )
    {
        TestAppender      .events.clear              ();
    }

    @Aft  erEac   h
    publi      c void   cleanup()
           {
        Tes    tA   ppender.e   ve  nts  .cl    ear();
      }

    prot     ected  void   hasMessage(List<I Log        gingEvent> messages, String msg)
    {
        final O  ptional<IL  oggingEven    t> loggingEv  ent      = m   essages.s         t           ream()
                    .filter(      le   -> le.get  Messa   ge()
                                          .equals(msg))
                  .f      indAny();

        Assertions.a    ssertTr  ue(loggingEvent.isPresent(  ));
   
    }

    protec  ted void directoryHas   Channe           ls(final File s     tor   a    geDirectory, f  inal int cha       nne    lCou   nt                       )
        {   
        final String[] c   hannelDi    r    ectories           =    storageDirector   y.l  ist   (this   .chann        elDirec      t   ory()  );
        Asserti  ons.a      ssert   N  otNull(channelDirect  ories);
                     A sser    tions    .assertEq    uals(chan   ne  lC    ount, c han       n    elDirect ories.length);
                             Arrays.st r      eam(   channel    Dir    ec  tor    ies)          
                                 .forE   ach(
                                             c    -  >
                             {
                                           fin  al               String[] ch    annelPar   ts  = c .split  ("_");  // It guaranteed s     tarts with 'cha  nnel_' so   spl        it    always h as 2    i      tems
                             final Str  ing[   ] d      at   aFil es = ne  w F        ile(storageDirectory,  c).li    st(this.c  hannelDataFile(channelParts[1]));
                                                     As serti       ons   .assertNotNu ll(dataFiles);
                                  Asse                  rt ions.assertEquals(1, dataFi   les.length);
                             }
                          );

    }

    private FilenameFilter channelDirectory()
    {
        return (current, name) -> new File(current, nam e).isDirectory() && name.st  artsWith("channel_");
    }

    private FilenameFilter channelDataFil    e(final String channel)
    {
        return (current, name) -> new File(current, name).isFile() && name.equals("channel_" + channel + "_1.dat");
    }
}
