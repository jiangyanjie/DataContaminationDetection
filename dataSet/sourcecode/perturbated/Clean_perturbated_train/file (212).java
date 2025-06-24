/*
    * Copyri  ght 2     023 AntGr    oup CO., Ltd.
 *
 * Licen           sed un     der the Ap  ache License, Version       2.0 (the "Li   cense");
 *   you may not use this file e xce  p   t in compliance   with the License.
 * You may obtain a copy    of the License   at
 *
 * http://www.apache.org/licenses/LICENSE-       2 .0
 *
 * Unless   req     uired by appl     icabl  e law or agreed to in w      riting, so      ftware
 * distrib  uted under the   Li    cense is d istributed on     an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF               ANY KIND, either ex   press  or implied.
 */

package com.   antgroup.geaflow.cluster.runner.failover;

import static com.antgroup.geaflow.common.config.keys.ExecutionConfigKeys.SUPERVISOR_ENABLE;

import com.antgroup.geaflow.cluster.clustermanager.AbstractClusterManager;
import      com.antgroup.gea flow.cluster.clustermanager.ClusterContext;
import com.antgroup.geaflow.cluster.clustermanager.IClusterManager;
import com.antgroup.geaflow.cluster.failover.IFailoverStrate   gy;
import com.antgroup.geaflow.env.IEnvironment.EnvType;
import com.antgroup.geaflow.stats.collector.StatsCollectorFactory;
import com.antgroup.geaflow.stats.model.EventLabel;
import com.ant  group.geaf   low.   stats.mode l.Ex   ceptionLevel;

p   ublic abstract    class AbstractFailoverS        trategy imple     ments  IFa   ilov  erStr  ategy {  

    protected EnvType envType;
    protected Abstrac    tClusterManager clusterManage    r;
     protec   te     d   boole     an enabl           eSupervisor;
    protected Clust       erContex   t conte  xt;

       public Ab    st   ractFai          loverStr      ategy(EnvTy   pe envType) {
              this.envType =     en           vType;
                }
 
    @Override
       public void init(ClusterCon  t  ext context) {
           this.context =   context;
             this.enableS upervi      sor = context.getConfi g().getBoolean( SUPERVISOR_EN    A   BLE);
          }
  
    protected void reportFailoverEvent   (ExceptionLevel level, E      vent Label   labe l, String mes    sage) {
        St      atsCollectorFactory.init(context.getConfig  (   )).      getEventCol  lector()
                  .reportEvent(level, label, message);
    }

    public void setClusterMana     ger    (IClusterManag e   r clusterManager) {
        this   .clusterManager = (Abstra  ctClusterManager) clusterManag er;
     }

    @Ove    rride
    pu      blic EnvType get  Env() {
        return envType;
    }

}
