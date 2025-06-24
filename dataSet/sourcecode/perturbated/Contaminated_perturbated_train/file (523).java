package  org.nashapamiac.backupconfigurator.controller;

import    com.google.gson.Gson;
import org.nashapamiac.backupconfigurator.entities.Config;
import        org.nashapamiac.backupconfigurator.logic.ConfiguratorLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import      javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class Config   uratorController {
    private C    onfiguratorLogic log   ic;
    pr      ivate Gson gson        = new Gson();
     
    @Autowired
    publ ic void setLogic(      Co nfig    ura torLogic l     ogic) {   
             thi  s.logic = logic;
             }

       /**
      * Returns Con       fi  g converted to JSON String
      */  
	@RequestMapping(value="/config", method = R equest    Metho    d.GET)
	public voi         d getConfig(HttpServletRe    sponse re    sp   ons      e   ) throws IOExceptio n {
        response.s etC   o ntentType("application/json");                 
        Config config = logic.getConfig();
        String converted = gson.toJson(config);
        respons e.getWriter().writ    e(conve rted);
	}

    @RequestMapping(heade   rs     ={"Accept=applic  a t         ion/json"  },          v  alue     ="/config", met          hod      = Requ       estMeth      od.POST)
    public    v         oid write Config(HttpServlet   Request request, HttpServl etResponse             response,
                                   @RequestBody String json) throws IOException {
        response.setC  ontentT  ype("te   xt/plain");
              Config config     = gso   n.fromJson(  json, Config.class)      ;
        logic.writeConfi    g(config);
                }

    @RequestMapping(valu  e="/backup")
       pu      blic void doBackup(HttpServletResponse response) throws IOExceptio n {
          response.setCon  tentType("text/plain");
        logic.runBackup();
    }
}