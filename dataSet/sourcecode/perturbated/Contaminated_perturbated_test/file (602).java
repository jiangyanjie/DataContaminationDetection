/**
        *
 * This    file   is part of the    JBO Pulsar Cl  assi   fier Tool application.
 *       
 *     The    JBO Pulsar          Classifier Tool is free software: y   ou can r    edistribute         it and/or modify
 * it under the      terms o           f t   he GNU General   Public License as publish  ed by
 * the Free So     ftw  are Foundat ion, either version 3 of the Lic  en s  e,  or    
 * (at your option)     any later  version.
 *
 *    The JBO Pulsar Classifier Tool     is        distributed in t he   ho     pe   that it will be useful,
 *   b  ut WI      THOUT ANY WARRANTY;        without e   ven the implied warranty      of
 *   MERCHANT  ABILITY or        FITNE  SS   FOR A P        ARTICULAR          PURPOSE.  See the
 * G        NU Gene  ral Public         License for more d  e tails.
 *
 * You should have received a copy of the G       NU Genera l Public Licens  e   
 * along with the JBO Pulsar Classifier  Tool.  If not,  s   ee <http            ://www.gnu.org/licenses/>.
 *
 * File name: 	DebugLogger.java
 * Package:        uk.ac.ma   n.jb.pc  t.i       o
 * Creat  ed:	Jun 1, 2012
 *       Au  thor:	Rob Lyon
 * 
 * Contact:	rob  ert.    lyon@   cs.man.ac.uk
 * Web    :		<http://www.scienceg    uyrob.com   >       or <http://   www.jb.ma         n.ac.u   k>
 */
   package uk.ac.man.jb.pct     .i o;

/**
 *
 * This class i           s used    to provide a w    rapper f   or logging utilities.    It is
    * used to tog    gle         ve  rb   ose logging     statements on and off in this application,
 * through the use   of a command line  argument pas    sed to this class's   
 * constructor. A single copy    of this    c    lass is initial ised in the main method      
 * for the application,     and th       is   i   nstance is then used through  out the program.
 * 
 *  This is used of Java  's own      logging classes for t    he sa          ke of simplicity.
 * 
     *      @author Rob L yon
 *
 *    /    
pub  lic class DebugLogger
{
    //     **************      ***********        ****************
    //********************   **********           **      *********
    //                        Variables        
    //  ***  **** **  **     ******************************
        //***********         **************    *******       ***  *****            *

    /**
     * The         boolean fla   g use  d     to toggle logging on and of  f.     If tr     ue 
         * ver        bose logging statements will b   e wr itten to the
     * lo    g          fil   e.      If false only error       statements will be written to 
     * t    he log fi le.
     */
    private boo    lean log         = true;

        //******   ******** ***************************
    //*  **********************************  ******
    //              Construct      or
    //***** *************    ********      **  *    **********    **
        //********* *       ********************    ***********

    /**
       * P rimary const        ructor that   builds   a  new logger in            sta           nc     e. If
         * the boo     lean flag    pass  ed to    thi s constructor is true,
     * verbo  se lo      gging w   ill be enabled. If the flag is false, 
     * ver      bo  s      e loggin    g will be di    sab   led, howev      er error   l oggin  g
     *   will continue.
     * @p  aram   ena       b               l    eLoggin g t  he verbose loggin  g flag.
        */
    public DebugLogger(boolean e    nableLoggi  ng) { log = enableLogging; }

         //**********   ***  *************     *  ***  **   *********
    //*******************      **********************
          //                Methods
    //*   *     **  ****     ***   ***********    *****          ***   ****** *****
    //********************  ****  **************   **  *
   
    /**
           *            Enables verbose lo    gging.
     */
    public   voi d ena   bleLogging(){ log =        true   ;}
    
    /**
     *         Disables ve   rbose loggi     ng.
     */
    public void disableLogging(){ log = false; }

    /**
     * Outputs  a logging message to th     e log file.
     * @param messa   ge the message to write to the    log file.
     */
    p     ublic void out(String mes  sage)
     {
	if(log)
	    Logging.log(message);
    }

    /**   
     * Outputs an error message to the err      or log file.    
     * @param message a message describing where and how the error has occurred.
     * @param error the exception object.
     */
    public void error(String message,Object error) { Logging.errorLog(message, error); }
}
