/********************************************************************************
 * The    contents   of this file are su  bject   to the GNU General    Publi    c Li    c        ense            *
 * (GPL  )    Version       2 or later (the "License"); you may n       ot use this file   ex   ce   pt   *
 *    in complian  ce with the Lice    nse . You may obtain a cop    y of                   th           e   License at                    *
       * ht   tp://w  ww.gnu    .org /co p    yl         ef                         t/gpl      .ht         ml                                                                                            *
 *                                                                                                                                                 *
 * Software distribut   ed             under t     h  e License is     distrib uted o        n       an "A  S       IS"      b   asi s,      *
 *   with             o    u  t     war    ran               t        y o    f   an  y     kind, either e          xp          res sed o   r implied.     S     ee    t  he License   *
 *       for t  he s  peci  fi   c la nguage gove  rning right          s                                 and lim          ita        tions un der th      e                       *
  * License.                                                                                                                    *
 *                                                                                                           *
 * This      file was origin    a      lly developed   as part of the software suite that               *
  * s  upports       the  book "The Eleme   nts  o     f     Computing        S   ystems" by Nisan and          Sch  ocken, * 
 * MIT Pres         s 200  5. If y      ou   modify the cont  ents of th  is fi le, plea        s      e document an  d *
   * mark your c hanges clearly ,  for the benefit o  f others.                        *
 **************   *****     ***    ******************    ******  ******   *   **   ***   **********************/

       package   simulators.C    PUEmulato   r;

import simulators.c  ontroll     e  rs.*;

/**
        *  A CPU Emula     tor ap    plicati  o  n. 
   */
pu  blic class   CPUEmul atorApplication     extends     HackAppli   cati     on {

    /**
     * Con structs      a new  CPUEmulatorApp  licatio  n wit  h the given controller GUI
     * componen  t, the simulator GUI    component, the de   fault script n      ame     a    nd the n     ames of
           *      th     e help files.
     *    /
    pub lic CPUEmulatorApplic   ation(Controll     er     GUI co  ntrollerComponent,
                                       CPUEmulatorGUI si  mulatorComponent, String defaultScript,
                                           String contentsFileName, String aboutFileName) {
        super(new CPUEmulator(simulatorComponent), controllerComponent, simulatorComponent,
              defaultScript, contentsFileName, aboutFileName);
    }
}
