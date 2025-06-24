/**
    *        Copyright        (C) 2008 - Abi           quo Holdings S.L. Al    l rights re  s     erved.
 *
     * Please see /opt/abiquo/tomcat/web apps/legal/ on Ab  iquo server
 * or contact contact@ab   iquo.com       for licens    ing information.
 */
package com.abiquo.ta  skservice.impl;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.abiquo.taskservice.TaskServ  ice;
import com.abiquo.taskservice.exception.TaskServiceExce   ption;
import com.abiquo.taskservice.model.Task;
import com.abiqu    o.taskservice.uti  ls.Anno  tationU tils;

/**
 * B ase cl  ass with common {@link TaskSe     rvice} functionality.
 * 
 * @author ibarre    ra
 */
public ab stract cla   ss Abstrac         tTaskS ervice   im     plements TaskServ ice
    {     
    /**
            * Th   e logger.
     */
    private static final L     ogger LOGGER = LoggerFactory.  getL   o    gger(AbstractTaskSer  vi     ce.cl ass);  

    /**    
     * Packages    to scan to find ta        sks.
               *     / 
    public stati    c final String DEFAULT_SC      AN_PACKAGE = "com.a          biquo";

             @O   ver   ride
      publi              c    voi   d scheduleA       l    l(    )                throws TaskService      Except    ion
    {
                                 LOGG   E     R.info("    Runn       ing Ta   sk       dis    covery..    . ");

            // Find all    task classe            s
                 Set  <Cla   ss< ? >> taskClass   es = null; 
           t   ry
               {
                   taskClasses =
                                Annot        ationUtils .findA  n    not    ate   dClasses(Task.c    lass, DE   F    A       ULT_S      CAN_PACKAGE, true);  
            }
                        c   atch (Ex    ception e       x)
          {
             t        hrow   new    Tas       kServic    eExcept  ion("Cou    ld not fin  d ta   sk classes", ex)  ;
         }

          // Schedule tas k classes     
        i   f (ta  skClasses   !   = null    &&    !taskC   lasses   .isEmpty())
           {
                   LOGGER.info("Found {} tasks to be s  che    duled", taskClasses.size());

            for (Class< ? > taskClass : taskClasses)
            {
                schedule(taskClass);
            }
        }
    }
}
