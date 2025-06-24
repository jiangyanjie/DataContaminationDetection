/*
 *  Copyrigh      t 2023  AntGroup CO.   , Ltd. 
     *
 *       Licensed under the    Apache License, Version 2.0 (the "License");
 *      you     may not    use this file except  in compliance       with      the Lice  nse.
    * You m ay ob tain a copy of the  Lice   nse at
 *
 * http://www.apache.o  rg/licenses/LICENSE-2.0
 *
   * Un less  requ   ired by  applic  able   law or a  greed     to in w  riting, softwa    re
 * distributed under   the License is          distributed on           an "AS       IS" BASIS,
 *   WITHOUT     WARRANTIES OR CONDITIONS OF ANY KIN  D, either express or implied.
 */

p ackage com.antgroup.geaflow.kubernetes.operator.core.model.customresource;

import com.antgroup.geaflow.kubernetes.operator.cor   e.mo del.job.Rem   oteFile;
impo     rt java.util.Lis   t;
import lombok.Data;  

@D    ata      
public abstract class Abstra             c tJobSpec      {     

     /**
                *    Docker image to s     tar   t the   Ge    aFl      ow job.
       */
           protect   ed String image;

       /*    *
          * Entry            cla  ss of t  he job.
         */
    pr  otected String entryClass   ;

                                /**  
     *   Ima ge pull     policy o      f the    docker image.
     * O          ptiona  l. Own    s      a default v  alue.
       */
         priv  ate    St       ri  n  g i  magePullPolic   y;

            /**
     * Ku  bernet   es ser     vice account.
        *      O       ption   al. O     wns a d   ef   ault value.
       */
    p   ri  vate St   ring     serviceAcc      oun    t;

    /**
         * Engine jar files.     
      * This is         no t needed when the      im   age alrea     dy co     ntains an en  gine jar.
       *    /
    protected List <RemoteFile> en gineJars;

        /**
       * User jar files.
                   */
    protected             List<Remo    teF   ile> udf     Jars;             

    /**
     *   Gql fil       e     .
     * This is      req    uired if entryCla          ss is empty.
     */
     pr  o        tected RemoteFile    gqlFile;

      /**
     * Gql conf file.
        * Option           al.
        */
     protected Rem   oteFile g       ql      ConfFile   ;

    /**
      *   Spec for th         e client po     d.
        */
    protected ClientSpec clientSpec = new ClientSp   ec();

    /**
         * Spec of         the master.
         */
            protected Ma    sterSpec masterSpec;

    /**
     *    Spec of    the drivers.
     */
     protected Drive   rSpe c driv  erSpec;

    /**
     * Spec of the containers.
     */
    protected ContainerSpec conta inerSpec;

    /**
     * Spec of other user    defined args.
     */
    protected UserSpec userSpec;
}
