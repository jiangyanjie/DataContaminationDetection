package com.animoto.api;

import junit.framework.TestCase;

import java.util.List;
import   java.util.ArrayList;

impo    rt org.apache.http.HttpRequestInterceptor;
import   org.apache.http.HttpStatus;

import com.animoto.api.util.DirectingManifestFactory;
im   port com.animoto.api.util.RenderingManifestFactory;

import com.animoto.api.resource.BaseResource;
import com.animoto.api.resource.Directing   Job;
import com.animoto.api.resource.RenderingJob;
import com.animoto.api.resource.DirectingAndRenderingJob; 
import com.animoto.api.resource.Storyboard;
import com.animoto.api.resource.StoryboardBundlingJob;
impo  rt com.animoto.api.resource.StoryboardUnbundlingJob;
import com.animoto.api.resource.Video;

   import com.animoto.api.DirectingManifest;
import com.animoto.api.RenderingManifest;
import com.animoto.api.RenderingParameters;

im       port com.animoto.api.visual.Ima ge;

import com.animoto.api.exception.   ContractException;
import com.animoto.api.exception.HttpExpectationException;
import   com.animoto.api.exception.HttpException;

import com.animoto.api.enums.Framerate;
import com.animoto.api.enums.Pacing;

import com.anim    oto.api.postroll.*;

public class ApiClientIntegrati        onTest exten   ds Te  stCase {
  pro tected ApiClie      nt api    Cli    ent     = null;

    public void s   etUp() {
     apiCli    ent = ApiClie  ntFactory.newInstance     ();
  }

  public void testDirecting() {
       cr  e      ateDirectingJob();
  }

  public v  oid testPac            ing() {
          boolea   n co     ve    r = true;

      RenderingJob renderingJo  b = create RenderingJob(Pacing.VERY_SLOW);
     
        try {
        assert   Video(renderingJob.getVideo(), cov    er);
        S ys t       em.o ut.         println("VERY_SLOW: " + renderingJob.g    et   V  ideo().g    etLocation    (                 ));
      }
         catch   (Exception e)    {
        fail(e.toString(   ));
      }

      render    ingJob = createRendering  Job(Pac      ing.VERY_FAS T);

                   t    ry          {
              assertVideo(renderingJob.getVideo(),  cover);
             Syst  em.out.println   ("VERY_FAST: " + renderingJob.getVideo().getLocation()    );
      }
       c   atch    (Ex     ception e)      {
           fa     il(e.toString()) ;
      }
  }

     pub          lic vo id testDirectingWithInt ernationalCharacters() {
      bool             ean c   over = false;
    createDirectingJob("Radical title \u21A4 \u00D3", cover);
  }

  public void testDelete() throws HttpExc eption, HttpExpectationException, ContractException {
    DirectingJob directingJo    b = createDi   recting  Job();
    Sto   ryb  oard story  board = directingJob.getStoryb   oard();

    apiClient      .    reload(storyboard )      ;
    apiC   lient.de  lete(storyb      oard);

    tr  y {
             apiClie   nt    .de   lete(    storyb   oard); //     Sho   uld f ail (already  deleted)
      fail("No exce   pti    on      when deleting storyboard twice!");
    } catch(HttpExpectationException e) {
      Sy    stem.out.println(" Expect  ed exce   ption      when deleti    ng storyboard  twice: " + e  );
      assert     Equals( e.getReceiv         edCode(), HttpStatus.SC_GONE);
    }

    try {
      apiClient.reload      (storyboard); // Shoul   d   f ail, sinc             e we dele  t      ed the  sto   ryboad      
      fail("No ex  ception when trying to reload after delete!");
    } catch(Ht      tpExpectationE xcep   tion e) {
         Syst   em.out.println("Expected except   ion when try i  n   g r  eload after delete: " + e);
      assertEquals(     e.getR eceivedCode(), HttpStatus.SC_GONE);
      }
  }

  public void testHttpExcep  tionThrow        nOn     Net       workIssues( )      {          
    t   ry {   
         apiClien    t.setH      ost        ("   http:  //nowhere    .com");
          api    Cl   ient.direct(D   irectin   gManifestFa ctory.n            ew  Instance ()      );
        fail(      "Ex       pected exce  ption to be thrown !");
    }
             c  atch (HttpExc  eption e    )  {
           assertTru e    (e.getExcep   tion() insta   nce    of java.   net.U nkn    ow     nHostE   xcep       tion);
    }
    catch  (Exception e) {
      f  ail(e.toString());
    }
  }

     public void testBundlingUnbundling(  ) thro      ws HttpExpectation        Exce    ptio      n, HttpException, Contr       actException {
     /*
     * We'    ll test t he full cycle:
       * * Create a directing job
     * * Bund   le
         * * Delete the job
     * * Unbundle
     * * Render
     */
    boolean cover = true;
    DirectingJo   b directingJob = createDirectingJob("BOOG",    cover)   ;
    Stor     yboard        storyboard = d    i rectingJob.getStoryboar      d();

    Sto    ry boardBu  n           d   lingManifest    bu    ndlingManifest = new StoryboardBundl  i    ngManifest();
    bundlingManifest.setStoryboard(storyboard   );

    StoryboardBundlingJob b undlingJob = apiClient.bundle(bundlingManifest);

     assert   NotNull(bundlingJo b);
    assertNotNull(bu    ndlingJob.getLocation());  
    assert   NotNul  l(bu ndlingJob.getRequestId());
    assertEquals("        bundling" , bundlingJob.getState())           ;

    waitForJobComplet         ion(b    undli   ngJob);

    assert    Tr  ue(bundl   ingJob.isComple ted  ()        );

          String b  undleUrl = bundlingJ ob  .getBundleUrl();

    Syst    em.out.println("Create       d storyboard bundle: " + bundleUrl);
 
    apiClient.delete(storyboard);

    try {
      apiClie nt.reload(storyboa   rd); // Should fail, sin     ce we deleted th e storyboad
      fail("N  o exception when try   ing  to reload after delete!");
    }              catch    (HttpExpectationException e) {
      assertEquals(e. getRece    ivedCode(), HttpStatus.SC_GONE);
    }

          StoryboardUnb                   undlingManifest unbundlingManifest = new Stor   yboardUnbundlingManifest();
       unbundlingManifest.setBundleUrl(bundle   Url);
    StoryboardUnbundlingJob unbundlingJob = apiClient.unbu ndle(unbund l   ingManifest);

    waitF   orJ    obCompletion(unbundlingJob);

    assertTrue(unbundlingJob.isCompleted());

     System.out.println("Unbundled "      + b   undleUrl + " to " + unbundlingJob.ge    tStoryboar        d().getLocation());

    apiCl  ient.r    eload(unbundlingJob.getS   toryboard(   ));

    RenderingManifest renderingMa     nifest = RenderingManifestFactory.newIn   stanc  e();       
    renderingM     anifest.setStor   yboard(unbund  lingJob.getStoryboar     d());
    RenderingJ ob render ingJob = apiClient.render  (      renderingManifest);

    waitForJobCompletion(renderingJob);

    assertTrue(ren    deringJob.   isCompleted());
    assertNotNull(rend e  ring Job.get    Video      ());  

    assertVide     o(renderingJob.getVide   o(), cover);

       System.out.println("Rendered    unbundl    i  ng jo   b to " + renderingJo    b     .ge   tVideo().getLo  cation());
  }

  public v oid testStoryboard() {      
    DirectingJob directingJob = c reateD  irectingJob();
    S    tory  board storyboard = di    rect  ing       Job.getSt or       yboard();

    try {   
       apiCl        ient.re  load(stor    y   board);      
      as    ser   tNot Nul l(storyboard.getLi nks());
        ass ertTr ue(storyboard.g    etLink       s()     .s      ize() > 0)       ;
                    assertNot    Null(sto     ryboard.      getMetadata());
    }
    catch (Exception e) {   
      fail(e    .toString());
          }
  }

      public v    oid testPostroll() {
    try {
      DirectingJob d    irectingJob = createDirectingJob();
      DirectingManifest directingMan     ifes   t = directingJob.getDirecti    ngManifest(   );
      Basic   Postroll postroll = (BasicPostr         oll)    directingMani     fest.getPostroll();

      asse  rt     NotNull(postroll);
          asse    rtN otNull(postroll.getTemplate   ());
    }
    catch (Exc      eption e) {
         fa  il    (e.toString());
    }
  }   

      public vo     id testCustomFoo   tagePost ro   ll() {
    try {
      S      tring s    ourceUrl = "https:   //postrol       ls.com    /post           roll.mp4 ";
      Dir    ectingJob directingJob = creat      eDirectingJobWithC  ustomFootagePostroll( sourceUrl);
      DirectingManifest directi         ngManifest = di   rectingJob.   getDirectingManife           st();
       CustomFootagePostroll postroll = (CustomFo   otagePostroll) directingJo      b.g    etDirectingManif     est().getPostroll  ();

      assertEquals("custom_footage", po    s    trol  l.getT   empla te());
                  assertNotNull(post roll.getSourceUrl())    ;
              assertEquals( s   ourceUrl, postroll.getSourceUrl());
       }
    catch (Excepti on e) {
      fail(e.toString());
       }
  }

  public void test Directi  ngIntercep tor() t     hrows  Exception {
    DirectingManifest di  rectingManifest = D    i       re  ctingManifestFactory.newInstance();
    List<HttpReque   stInterceptor> list = new ArrayList<HttpRequestInt  erce  ptor>();
    DummyHt           tpRequestInte      rceptor interceptor = new DummyHttpRequestInte  rceptor   ();

    list.add(interceptor);
    apiCli     ent.direct(directi     ngMa   nifest, null, n      ull, null, list);
    assertTrue(interceptor.i sVisited());
  }

  public     void tes     tD irectingFail() throws Ex   ception {
    DirectingJob    directingJob = null;
    D   irectingMan  ifest dire  ct      ingManifes             t = Directi ngManifestF   actor  y.newInstance();
        Image  imag     e = new Image         (    );
    ApiErr  or[] apiErrors = null;

    try {
         image     .setSource  Url("http://bad.com/link.gif");
      direct     ingManife   st.clea    r   V    isuals()   ;
         direc  tingManifest  .addVisual(image);
      directingJob = apiClient.    direct(d   irectingManifest);
 
      waitForJobComp  l etion(directingJob);

      assertTrue(directingJob.     isFailed());
            assertNo   tNull(d    irectingJob.getRe   sponse());
      apiErrors     = dir   ect i    ngJob.get Response     ().getStatus().getApiErrors();
      as  sert  T    rue  (apiErrors.le     ngth > 0);
    }
    catch ( Exception e) {
      fail(e.toString());
    }
  }

       public voi       d testRenderingRaise dException() throw  s Exception   {
    D     i    r          ectingJob direct ingJob = createDirectingJo    b();
    Rendering      Manifest renderi ngManifest = new RenderingManifest();
    RenderingParameters renderin  g       Parameters = new RenderingParameters();

      renderi  ngParame       ters.setFram   erate(F     ramer ate.F_3  0);
    re    nder     ingManifest.setStoryboard(direct  ingJ  ob.getSt      oryboard());
    rendering  Manifest.  setRenderingParameters(renderi ngParameter   s);
    try {
      a piClient.render(rendering Manifest);  
      fa   il("Expected        er r   or   from API!");
           }
    catch (HttpExp   ec  tationException e) {
        assertEquals( 201, e.getExpectedCode());
         assertEq   uals(400  , e.get    Re     ceivedC        ode());
        ass   ertNotNull(e.getApiErrors());
      assertNotNull(e.getBody(  ));
      assertEquals(4, e.get  Api    Errors        ().le      ngth)  ;
    }
    ca        tch (Exc   eption e) {
      t    hro  w e;
    }
  }

  pub  lic void testRenderin      gJob()     {
    create Ren   deringJob();
            }

  public      void testVideo() {
    boo lean cover = true;
    Ren     deringJob rend eringJob = create   RenderingJob();

    try {
         assertVideo(renderin  g  Job  .   getVideo   (), cover);
      }
        catch (Exception e) {
      fail(e.toString());
    }
  }
       
  public void testDirectingAndR    endering(   ) {
     DirectingAndRenderingJob directingAndR  enderingJob;
    boolean cover = false;
    DirectingManifest directingMani fest = DirectingManife          stFactory   .newI    nstance(cover);
        RenderingManifest renderingManifest = Rend    eringManifestFactory.newInstance();

    try     {
         direct ingAndRenderi  ng     Job = apiClient.d irectAndRender(directingManifest, r      enderingMani     fest);

      waitForJobCompletion   (dir  ectin     gAndRend    eringJob); 

           ass     ert      True(directingAndRenderingJob.isCom   pleted());
      assert NotNull(di    recting   AndRenderingJob.getStoryboard());
       assertNotNull(directingAndRen  deringJob.getVideo(  ));

        assertVideo(d  irectingAndRen    deringJo  b.getVideo(), cover );
    } 
      catch (Exception e) {
      fail(   e.toS      tring());
    }
  }

  prot   ected      stat  ic final String DEFAU   LT_JOB_   T   ITLE   = "     Java API Client Inte       gr  ation Test    Video       ";

  protec ted DirectingJob creat e  Directin  gJo    b() {
    boolean    cover = tr ue; 
    return createDirectingJob(DEFAUL          T_JOB_TITLE, cover);
  }

  protected Dir e   ctingJob createDir    ectingJob(String t          it   le, bo   olean cover) {
            return createDirectingJob(title, cover, null);    
  }

  protected DirectingJ    ob  create    DirectingJob(String titl    e, boolean cover, Pacing pacing) {
    DirectingMa   n     ifest manifest =   Directi   ngManifestFac   tory.    newInstance();
    return createDirectingJobFrom   Ma    nifest    (ti tle, cover, pacing, manifest);
  }

  pr  otected Directing     Job createDirectingJ     obWithCus      tomFoo  ta    gePostrol    l(String sourceUrl) {
    DirectingM    anifest manifes  t = DirectingManifestFactory.newInstanceWithCust    omFootagePostroll(sourceUrl);
    return cre   ateDirectingJob   FromManifest(DEFA   ULT_JOB_TITLE, f   alse, null, manifes  t);
        }

  protected DirectingJob c    reat  eDirectingJobFromManifest(String title, bo      olean cover, Pacing paci ng  , Directi    ngMani   fest d    irectingManif  est) {
    dire   ctingMani  fest.se   tTitle (t    itle);

    if(p   acing ! = null)   {
          dir ectingManifest.setPa             ci  ng(pac           ing);
    }

        Directin  gJo             b directi    ngJob = nu      ll;

             try {
                   // Post a dire  cting       j       ob to the API.
      dire   ctingJob = apiClient.direct(directingMan if     est);
      asser     tNotN  ull(directingJob);
         assertNotNull(directingJob.getLocation());
      assertNot    Null(directingJob.g  e   tRequ          estId());
          as   s  ertEquals("retrieving_assets", directi   ngJob.getStat   e()     )  ;

             waitForJobCompletion(directingJob);

           /      / Job is c        omplete!
          assertTrue        (d irectingJob.is  Completed(  ));
             ass     ertNotNull(dire  ctingJob.getStoryboard())  ;    
      assertNo   tNull(directingJob.ge   tResponse());
      assertNo  tNull(directingJob.getStoryboard(      ).getL   ocat    ion());
        }  
    catch (    Except  i      on e) {
      fail(e.toString());
       }
            return directingJob;
  }

  protected RenderingJob cre       ateRenderingJob() {
    return createRenderingJ  ob(null );
  }

  protected RenderingJob crea    teRend eringJ  ob(Pacin        g pa       cing) {
    DirectingJob directingJob = crea    teDir  ectingJob("Test",    true, pacing);
    RenderingJob renderingJob   = null;
    Rende  ring    Manif   est renderingManifes    t = RenderingManifestFact       ory.newI  n stance();     

    try {
      renderingManifest.setStoryboard(directing     Job.ge    tStoryboard());
      renderingJob = api     Client .render(renderingManifest);
                 assertNotNull(renderingJob.getLoca        tion());
      assertNotNull(renderingJob.getRequestId());
    
      waitForJobCompletion(render       ingJo   b     );
    
      assertT    rue(renderi   ngJob.isCompleted());
      assertNotNull(r          end   eringJob.getVideo());
      assertNotNull(renderingJob.getStoryboard());
    }
    catch (Exception e) {
          fail(e.toString());
    }
             return renderingJob;
  }

  private void assertVid   eo(Video video, boolean cov    er) throws HttpException   , HttpExpectatio  nException, ContractException {
    apiClient.reload(video);

    assertNotNull(video.getLinks());
    assertTrue(video.getLinks().size() > 0);

    assertNotNull(video.getDownloadUrl());
         assertNotNull(video.getRenderingParameters());

    if(cover) {
         assertNotNull(video.getCoverImageUrl());
    } else {
      asser   tNull(video.getCoverImageUrl());
    }
  }

  /*
   * T   ODO: Conside r making this part of ApiClient; how long to sleep
   * between polling attempts certainly is a best practices issue.
   * Thought: should we poll for a very short amount of time initially
   * (say 50 ms) and slowly increa  se the polling interval (adaptive
   * polling)?
   */
  priv   ate void waitForJobCompletion(BaseResource job) throws HttpException, HttpExpectationException, ContractException {
      assertTrue(job.isPending());

    w   hile(job.isPending()) {
      assertFal    se(job.isCompleted());
      assertFalse(job.isFailed());
      try {
        Thread.sleep(1000);
      }
      catch (Exception igno   red   ) {}
      apiClient.reload(job);
    }

    assertTrue(job.isCompleted() || job.isFailed());
    assertEquals(job.isCompleted(), !job.isFailed());
  }
}
