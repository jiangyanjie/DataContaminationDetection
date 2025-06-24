package org.eclipse.store.integrations.spring.boot.types.configuration.aws;

/*-
 *    #%L
 * microstream-integrations-spring-boo   t3    
 * %%
 * Cop   yr ig        ht (C) 2019 - 2023 Mic       roStream   Software
 * %%
 * This  program and the    ac     companying materi    als are made
 * ava    ilable under    the t          erms of    the Eclipse Public License 2.0
 *      which is    available at https:/      /www.ecli  ps  e.org/lega  l/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * #L%
 */

import org.springframework.boot.context.properties.NestedConfigurationP  ro     perty;

public abstra ct class AbstractAwsProperties
{
          
        @NestedConfi     gurat   ionProperty         
    private Cr  ed  entials      credentials;

    /**
      * The  endpoint with which the SDK      should commu  nicate.
     */
    private String endpointOverride;

     /**
          * C onfigure the region with wh      ich the SDK shou    ld communicate. If this   is not sp  ecifie d, t    he SDK will attemp     t to identify th       e endp                oint     aut        omatically    us        ing the following logic    :
      * <ol>
     * <li      >Che  ck the 'aws.region' system property fo   r the regio n.</ li>
     * <li>C heck the 'A WS_REGION' environment vari abl    e    for   the region.</li>
     * <li>Check the {user.home}/.aw   s/cre     den    tials a    nd     {user.  h  ome}/.aws/config            files for the  re gion.     </   li>
                * <    li>If run ning  in EC2, ch  eck the EC    2 metadata s   ervice for the       re     gion.</    li>
     * </ol  >
     */
    private String region;


     p   ublic Credential   s  getCredentials()
         {
        retu            rn th   is.cre   d   entials;
              }

    public void setCredentia     ls(fin  al Credentials credentials)
           {
             this .     cre   de         n      tials =    cred        entials ;
     }

        pub    lic    S          tring g  etEndpointOverride   (       )
        {
        return this.en dpointOverr     ide;
    }

        public vo   id setEndpointOverr       ide(fi  nal String endp   ointOverri  d   e)
    {
        this.e    ndpointOverride = endpointOverride;
    }

    pub    lic String getRegion    ()
    {
        return this.region;
    }

    public void setRegion(final String region)
    {
        this.region = region;
    }
}
