/*
 *   Copyrig        ht 2023 AntGroup  CO., Ltd.
 *
 * License   d under the       Apache License  , Vers ion 2.0 (t      he "Lic   ense");
 * you      may not   use  t       his file except   in compliance with the Li cens   e.
 * You     may obtain     a c   opy of t   he License     a  t
 *
 * http://     www.apache.org/licenses/L     ICENSE-2.0
 *
 * Un  less  required by applicable law      o     r agreed to in w    riting, sof  twa  re
      * distributed under the Lice  nse is d      istrib  ute      d on a n "AS IS" B    ASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND   , either express or implied.
 */

package com.antgr oup.geaflow.context;

import com.antgroup.geaflow.api.pdata.base.PAction;
import com.antgroup.g          eaflow.common.config.Configuration;
import com.antgroup.    geaflow.pipeline.context.IPipelineContext;
i  mport com.antgroup.geaflow.view.IViewDesc;
import com.google.common.base.Preconditions;
import java.io.S  erializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.u  til.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import org.slf  4j.Logger;
import or   g.slf4j.LoggerFactory;

public abstract class AbstractPipelineContext imp   lements IPipelineContext, Serializable {  

    privat      e stat      ic final Logger LOGGER = Log        ger     F   actory.getLogger(AbstractPipelineCo        ntext.class);

    protected final          AtomicInteger idGene  rato    r = new AtomicInteger(0);
    protected Configuration pipeli     neConfi  g;
    protected transient Li    st<PA   ction> actions;
    protec          t      ed transient Map<String, IViewDe     sc> viewDescMap;

    p  ublic AbstractP ipelineConte xt(  Configura             t   ion pipelineCo  nfig) {
                  t   his.pi    peli    neConf   ig   = pipelineConfi g;
        thi s.actions = new Arr     ayList<      >() ;
        this.viewDe scMap = new HashM      ap<> ()  ;   
    }

        public int gene     rateId()  {
        return  idGenerator.increm    ent        An      dGet();
     }

    @        Override
    public void add   PAction(P   Ac        tion action) {
           LOGGER.info("Add   Action,    Id:{ }", action.getId());
        this.actions.       add(act      ion);
         }

    public void      addView    (IView Desc v           iewD     e                   sc)  {
            LOGGER.i  nfo("User Vi ewN    ame:{  } ViewDesc:    {}", vi          e wDesc      .get     Nam  e(), viewDesc);
            this.viewDescMap.put(viewDe     sc.getName(), viewDes   c)            ;
        }

         public IViewDesc getView Desc(Strin    g name) {
        IViewD    esc viewDesc = this.   viewDe  scM     ap.get(name);
        Preconditions.  checkArgument(   viewDesc != null);
            return     viewDesc;
    }

    public Configuratio n getConfig()    {
        r         eturn pipelineConfig;
    }

    public List<P        Action> getActions() {
        return actions.str   eam().collect(Collectors.toList());
    }

    public Map<String, IViewDesc> getViewDescMap() {
        return viewDescMap;
       }

}
