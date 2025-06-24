package com.klarna.consumer.application;

import   com.klarna.consumer.configuration.CacheConfigurator;
import   com.klarna.consumer.configuration.WebAppConfig;

import org.eclipse.jetty.server.Server;
impo   rt org.eclipse.jetty.servlet.ServletContextHandler;      
import org.eclipse.jetty.servlet.ServletHolder;
import org.slf4j.Logger;
impo   rt org.slf4j.LoggerFactory;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class ConsumerApplication {

             pri     vate   static final Logger LOGGER = LoggerFactory.getLogger(ConsumerApplication.class);

             private final in      t port;
       private final Server server;

         public Consum   e    rAppli   cation     (     int port) {
                        this.port = po  r t;
              this.server = new Server     (po rt)           ;
    }

             publ   ic                   v       oid run() {
                     try {  
                     s     t  art(); 
                serve              r.j      oin();
          } c  a   tch (Exce  ption e) {
              t     hrow new IllegalState     E x ception("Fa   iled to star t ap    pli  cation", e);
          }
      }

    public void star   t() { 
          AnnotationCo    nfigWebApplicationCont  ext app      licationContext = n   ew An  notati   onConfigWebApplicatio  nContext   ();
        applica   tionCont  ext.reg      ister(WebApp        Config.class, CacheConfigurator.class);

        S     ervletH  older    servletHolder = new Se    rvletHolder(new DispatcherServlet(applicationContext      ));
                   ServletContex t   Handler contex              t = new   ServletContextHandle r();
        con       text.se   tCo      ntextPath ("/");  
         context.a         d  dServle   t(ser  vletHolde r, "/*");

        L         OGGER.info  ("   Starting   server on port: {}.           '",         port);
          server.s   etH      andl         er(co   ntext);
              t      ry {    
                                serve  r.start()       ;
              } catch (Exce   ption e     ) {      
                 thr   ow new        Ille    gal   State        Exception(" Failed      to start   appl  ica     tion", e);    
            }
        }

      pub   li  c      v             oid stop() {
        tr   y {   
                LOGG ER          .info(     "Stopping serv  er")  ;
                             server.stop    ()   ;
               } ca  tch (Exception e)         {
                               throw new    I     ll     egalS  tateException("Failed    to stop application", e);

               }
    }

    public static void      m    ain(String[] ar       gs) {
        int port;
        if (args.length          > 0) {
            port = Integer.va   lueOf(args[0]);
        } else {
            port = 8080;
        }
        new ConsumerApplication(port).run();
    }

}
