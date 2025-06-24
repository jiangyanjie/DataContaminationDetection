package com.sezgk.tractor.webservice;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sezgk.tractor.census.CensusTract;
import com.sezgk.tractor.census.CongressionalDistrict;
import com.sezgk.tractor.census.StateData;
import com.sezgk.tractor.census.TractGroupingService;
import com.sezgk.tractor.census.parser.ParsingService;
import com.sezgk.tractor.census.parser.StateService;

/**
 * Implementation of the data servlet. This servlet is responsible for responding to requests hitting the /data/*
 * pattern, where the wildcard is substituted by a valid state code. The response will consist of a serialized JSON
 * object representing the newly drawn congressional districts for the state with the given state code.
 * 
 * An example of a valid URL pattern for this servlet is <code>data/md/8</code> where md is the state code of Maryland
 * and 8 is the number of congressional districts we want to create.
 * 
 * @author Ennis Golaszewski
 * 
 */
public class DataServlet extends HttpServlet
{
    private static final long serialVersionUID = 1313131313L;

    private class DataRequest
    {
        public String stateCode;
        public int nDistricts;
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        DataRequest r = parseRequestURI(req.getRequestURI());
        // TODO error check.
        StateData sData = StateService.getData(r.stateCode);

        List<CensusTract> tracts = ParsingService.parseTracts(sData.getStateFIPS());

        if (sData.getStateAbbr().equals("MD"))
        {
            tracts = ParsingService.parsePrecincts(sData.getStateFIPS(), tracts);
        }

        List<CongressionalDistrict> districts = TractGroupingService.createDistricts(tracts, r.nDistricts,
                sData.getSeedCoordinate());

        Gson gson = new GsonBuilder().create();
        String json = gson.toJson(districts);

        resp.setContentType("text/json;charset=UTF-8");
        resp.getWriter().write(json);
    }

    /**
     * Parses the request URI in order to determine the state code and the number of districts to create.
     * 
     * @return the request data wrapped in a <code>DataRequest</code>.
     */
    private DataRequest parseRequestURI(String uri)
    {
        String[] elements = uri.split("/");
        DataRequest r = new DataRequest();
        r.stateCode = elements[elements.length - 2];
        r.nDistricts = Integer.parseInt(elements[elements.length - 1]);
        return r;
    }
}
