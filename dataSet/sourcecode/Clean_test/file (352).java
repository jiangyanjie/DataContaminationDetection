package org.jcssc.meetdirector.controller;

import java.util.List;

import org.jcssc.meetdirector.bean.Club;
import org.jcssc.meetdirector.bean.Skater;
import org.jcssc.meetdirector.service.ClubService;
import org.jcssc.meetdirector.service.RinkService;
import org.jcssc.meetdirector.service.SkaterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ApiController
{
    private static final Logger logger = LoggerFactory.getLogger( ApiController.class );

    @Autowired
    private SkaterService skaterService;

    @Autowired
    private ClubService clubService;

    @Autowired
    private RinkService rinkService;

    @RequestMapping(value = "/api/skaters", method = RequestMethod.GET)
    public @ResponseBody List<Skater> getSkaters()
    {
        if( logger.isDebugEnabled() )
        {
            logger.debug( "getSkaters" );
        }

        return skaterService.findAll();
    }

    @RequestMapping(value = "/api/skater/{id}", method = RequestMethod.GET)
    public @ResponseBody Skater getSkater( @PathVariable("id") long id )
    {
        if( logger.isDebugEnabled() )
        {
            logger.debug( "getSkater: id={}", id );
        }

        return skaterService.findById( id );
    }

    @RequestMapping(value = "/api/skater", method = RequestMethod.POST)
    public @ResponseBody Skater updateSkater( @RequestBody Skater skater )
    {
        if( logger.isDebugEnabled() )
        {
            logger.debug( "updateSkater: skater={}", skater );
        }

        return skaterService.update( skater );
    }

    @RequestMapping(value = "/api/skater", method = RequestMethod.PUT)
    public @ResponseBody Skater addSkater( @RequestBody Skater skater )
    {
        if( logger.isDebugEnabled() )
        {
            logger.debug( "addSkater: skater={}", skater );
        }

        return skaterService.add( skater );
    }

    @RequestMapping(value = "/api/clubs", method = RequestMethod.GET)
    public @ResponseBody List<Club> getClubs()
    {
        if( logger.isDebugEnabled() )
        {
            logger.debug( "getClubs" );
        }

        return clubService.findAll();
    }

    @RequestMapping(value = "/api/club/{id}", method = RequestMethod.GET)
    public @ResponseBody Club getClub( @PathVariable("id") long id )
    {
        if( logger.isDebugEnabled() )
        {
            logger.debug( "getClub: id={}", id );
        }

        return clubService.findById( id );
    }

    @RequestMapping(value = "/api/club", method = RequestMethod.POST)
    public @ResponseBody Club updateClub( @RequestBody Club club )
    {
        if( logger.isDebugEnabled() )
        {
            logger.debug( "updateClub: club={}", club );
        }

        return clubService.update( club );
    }

    @RequestMapping(value = "/api/club", method = RequestMethod.PUT)
    public @ResponseBody Club addClub( @RequestBody Club club )
    {
        if( logger.isDebugEnabled() )
        {
            logger.debug( "addClub: club={}", club );
        }

        return clubService.add( club );
    }

}
