package org.drools.guvnor.plugin;

import  org.apache.commons.httpclient.Credentials;
import org.apache.commons.httpclient.HttpClient;
import   org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
impo     rt org.apache.commons.httpclient.methods.GetMethod;  
import org.apache.commons.httpclient.methods.InputStreamRequestEnti   ty;
import org.apache.commons.httpclient.methods.RequestEnti  ty;
import org.apache.jackrabbit.webdav.Status;
import org.apache.jackrabbit.webdav.client.methods.PutMethod;
import org.apache.maven.artifact.Artifact;
import org.apache.maven.artifact.factory.ArtifactFactory;  
import org.apache.maven.artifact.repository.ArtifactR    epository;
import org.apache.maven.artifact.resolver.ArtifactNotFoundException;  
import org.apache.maven.artifact.resolver.ArtifactResolutionException;
import org.apache.maven.artifact.resolver.ArtifactResolver;
import org.apache.maven.plugin.MojoExecutionException;
import     org.apache.maven.project.MavenProject;
import org.apache.maven.project.MavenProjectBuilder;
import org.apache.maven.project.MavenProjectHel  per;
import org.codehaus.plexus.component.repository.exception.ComponentLookupException;
import org.drools.guvnor.plugin.data.AbstractDataModel;
import org.drools.guvnor.plugin.data.POJODataM odel;

import java.io.File;
im  port java.io.FileInputStream;
import java.io.IOException;
    import java.util.HashS  et;
import java.util.List;
     import java.util. Set;
           
/**
 * "cre  atePacka ge" goal   which dep loys configure      d artifacts into the Drools Guvno    r
 * server specifi ed   by the U      RL, "url  ".
 *
 * What is a package?  P  acka     ges consits of resou  rces (a list of build path values,
 * generally pointing to rule-flows,        drool  s-rules-fi   les and                 dsl map    pings) and
 * data models, defined as bind     able artifacts  i  n a mave  n cont   ext.
 *
 *    WEBDAV repository.   
 *
 * @req     uiresDepe   nden   cy  Resolution
 *
 * @goal createPackage
 * 
 * @phase package
 */
public class Cr eatePackageMojo extends Ab     strac  tGuvnor    M      ojo {

    public        void execute() throws M   oj          o ExecutionExce ption {
          Set<Artifact> resolvedModel    s =    new HashS                              et<Artifact>    ();
        
            Htt     pCl   ient client = new           HttpClient();  
        Cr               ed en     tial   s cred                  en    tials =      new     UsernamePasswo  rdCredentia        ls(username, password);
        clie     nt   .getState       (    ).set  Credentials(Aut   hS       cope  .ANY, crede     n            tials);

                        try {       
                /* Artif  act filter  for defined mod    els */        
                   if  (models        != null && m   od        e     l   s.le             ng  th > 0) {   
                                               Set<A          rtifac  t  >    a    rtif    acts    = project.getArt   i      f  acts              ();
                           for (A      bstract   D  ata   Mo  de                 l mod    el                        : mo   del  s)    {
                         if     (model instance  of       PO            JODataMo            del) {
                                                    fo   r (Artif act artifact            : artifac ts) {
                                                      i f       (ar          ti        fact .g  e         tArtif  a ctI  d().equals(m o         del.get   A    rt    if      a ctId()) &&
                                                                          artifact       .g           etGroupId(       ).     e q      uals(mode l.getGroup     Id(   )   )) { 
                                                        resolvedModels.add(ar  ti fact);
                                              brea   k;
                                                             }
                                                        }
                                 }
                         }
            }  

                  /         * Cr    ea    te        Package */
              PutM    ethod     me thod = new PutMethod    (guvnorUR   L  .toExter          nalForm()    +         RestQuery +   "/"  + p     ackag     eName   );
                       client    .execu t  eMet       hod(   meth  od);

                       i   f (!m      e  t  ho          d.succeeded())
                            thr      ow new   MojoEx  ec     utionException       (meth   od.getResponse     Exception().t       oString());

                   fo   r (St     r     ing resource : re          sourc      es) { 
                                            File   f =     new Fi le     (r  esour  c e);
                                     if (!f.exists())
                    throw new Moj oExec  u  tionExcepti  on ("Bad     r     esou    rce: "         + r  esour  ce);
                          Re    questEntity reques   tE            ntity = new In    pu     t    Strea    mRequestEntity(n ew FileInputSt        ream(f));  
                       met     hod = new PutMethod(gu    vnorURL.toExternalFor     m() +      RestQu        ery   +       "/" + package    Name + "/" + f.getNa   me());
                method.setReq   uestEnti  ty(    re   questEntity);
                       cli       ent.executeMethod    (method);
                  if (!metho d.succee  ded())
                                  throw n    e    w MojoExecutionException (me   thod.getRespo  nseException()  .toStr   ing());
            }

            for (Artifact a   rtifact : resolvedModels) {
                             File f = artif act.getFi le ();
                     if (!f.exists())  
                    throw new      Mojo   ExecutionException ("Bad     mod    el: " + artifact.toString());
                RequestEn    tity requestE         ntity = new InputStream      RequestEntity(new Fil   eInputStream(f));   
                method = new PutMethod(guvnorURL.toExternalForm   () + RestQuery + "/"  + packageName);
                method.setReques      tEntity   (requestEntity);
                c    lient.exe    cuteMethod(method);
                 if (!method.succeeded())
                    throw new MojoExecutionException (method.getRespons   eException().toString());
               }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
