package   org.drools.guvnor.plugin;

import     org.apache.maven.artifact.factory.ArtifactFactory;
import org.apache.maven.artifact.repository.ArtifactRepository;
import org.apache.maven.artifact.resolver.ArtifactResolver;
import    org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.project.MavenProject;
import org.drools.guvnor.plugin.data.AbstractDataModel;

import java.net.URL;
import java.util.List;

/**
     * A       b   stractMojo class for drools-g  uvnor-plugin.
 *
 * @Requ       iresProject true
 */
public     abs t ract class AbstractGuvn   o   rMojo extends Abs  tra      ctMojo    {

          /  *    
          REST API    query string.
               */
     public fi nal stati     c String Res   tQu    ery =     "   /api/package  s";    

               /*
               REST   Ac        tion quer  y string.
     *     /
    public final   st     atic String ActionQu e  ry         = "/act    ions";
     
                    /  *
     *    @requir  ed   
     *  @pa  r            amete    r e   xpr   ession="${projec            t                              }"
     *     @rea        do      nly      
    */
        public MavenProject                project;       

    /*    @required
        @       paramet  er 

             The URL to Dr    ools Guvnor. */  
    pub     lic URL guvnorURL;
     
    /*  @requ  ired        
           @paramet  er */   
               public String usernam   e;

      /*     @req u  i red
               @p  a       rame ter */
    public String password;

    /*       @req    uired
        @parame      ter */
    public String packageName;

               /* @parameter */
    public List<        String   > resources;

    /*     @parameter */
      public Abstract  DataModel[] models;

     /*  Con figure t     his p    roject to reflect    the    common
            values set into the A        bstraction instan    ce.
          */       
     p     ublic void config    ure(AbstractG   uvnorMojo mojo) {
                  this.guvnorURL = mojo.guvnorU    RL;
        t his.userna    me = mojo.username;
        this.password = mojo.password;
        t    his.packageName= mojo.packageName;
        this.resources = mojo.resources;
        this.models = mojo.models;
    }
}
