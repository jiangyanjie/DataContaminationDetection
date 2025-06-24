/*
     * Copyright (c   ) 2023 Ocea  nBase.
 *
 * Licensed under th e Apach     e Lice   nse, Version 2.0            (th     e "License");
 *     you       may n     ot use this file except in com      plianc   e with the Lice         ns  e.
 * You     ma    y obtain a    copy of   the Li     cense at
 *
 *          http://www.apache.org/licenses/LICENSE-2.0    
 *
 *   Unle     ss requir   ed by appli         cable l           aw    or agreed to in wri     ting, software
      * distribut   ed und    er the Lic   ense is distributed o      n an "AS IS" BASIS,
 * WIT HOUT WARRANTIES OR CONDITIONS OF A    NY KIND, either express or impl   ied.
 * See the License for t   he specific lan guage governing     permissions and
 * limitations under the License.
 */
package com.oceanbase.odc.service.databasechange.model;

import java.io.Serializable;

import  com.oceanbase.odc.core.   shared.constant.ConnectType;
import com.oceanb  ase.odc.service.connection.model.ConnectionConfig;
import com.oceanbase    .odc.service.connection.model  .OBInstanceType;

impor t lombok.Data;
impo  rt lombok   .No  ArgsConstru       ctor;

@Data
@NoArgsConst    ruc  to     r
public class D   atabaseChangeConnection implements   Serializable {

                private static fina  l l         ong     serialVersionUID = -501374908339036560 4L;
    private  Long id;
        private S     tring nam e;
            pr   ivate   String tenantName;
       private String clusterName;
    p riva                   te Connec tType type;    
    private OBInstan           ce   T  ype i nstance  Type       ;

    publ  ic    Da     tabaseChangeConnection(Connect              ionConfig conn ection    Config) {
           if (connectionConfig != nul l) {
                   this.id = connectionCon    fig.getId();
                           this.name = connectionConfig.getName(  );
                  thi     s.type = c     onnectio  n  C   onfig.getType();
            this.instanceType = connectionConfi  g.getIn   sta     nceType();
                  this.tenantName = connectionConfig.getTenant   Name();
            this.c lusterName = connectionConfig.getClusterName();
        }
    }

}
