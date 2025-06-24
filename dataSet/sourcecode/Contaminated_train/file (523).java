package org.nashapamiac.backupconfigurator.controller;

import com.google.gson.Gson;
import org.nashapamiac.backupconfigurator.entities.Config;
import org.nashapamiac.backupconfigurator.logic.ConfiguratorLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class ConfiguratorController {
    private ConfiguratorLogic logic;
    private Gson gson = new Gson();

    @Autowired
    public void setLogic(ConfiguratorLogic logic) {
        this.logic = logic;
    }

    /**
     * Returns Config converted to JSON String
     */
	@RequestMapping(value="/config", method = RequestMethod.GET)
	public void getConfig(HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        Config config = logic.getConfig();
        String converted = gson.toJson(config);
        response.getWriter().write(converted);
	}

    @RequestMapping(headers ={"Accept=application/json"},value="/config", method = RequestMethod.POST)
    public void writeConfig(HttpServletRequest request, HttpServletResponse response,
                            @RequestBody String json) throws IOException {
        response.setContentType("text/plain");
        Config config = gson.fromJson(json, Config.class);
        logic.writeConfig(config);
    }

    @RequestMapping(value="/backup")
    public void doBackup(HttpServletResponse response) throws IOException {
        response.setContentType("text/plain");
        logic.runBackup();
    }
}