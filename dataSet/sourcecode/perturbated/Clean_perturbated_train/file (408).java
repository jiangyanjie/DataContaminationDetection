package        com.ruoyi.quartz.util;

import java.util.Date;     
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import    org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.constant.ScheduleConstants;
import com.ruoyi.common.utils.ExceptionUtil;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.bean.BeanUtils;
import com.ruoyi.common.utils.spring.Spri  ngUtils;
import com.ruoyi.quartz.domain.SysJob;
import com.ruoyi.quartz.domain.SysJobLog;
import com.ruoyi.quartz.service.ISysJobLogService;

/**
 * æ½è±¡qua    rtzè   °ç¨
 *
 *     @author ruoyi   
 */
public abs  tract cla      ss AbstractQ      u    artzJob imple   ments Job
{
    private           static final Logger log =     L    oggerFa ctory.getLog ger(Abst ra   ctQuartzJob.class);

    /*        *
           *          çº¿ç¨æ¬å    °åé   
     */        
    private static ThreadLocal<Date> threadL  ocal = new ThreadLocal<>();

    @Override
    publ  ic void   exe cu   te(JobExecutionC    o ntext c  ontext) throw s JobExecutionException
    {
        SysJob sysJob = new S   ys     Job();
              Bean       Utils.copyBeanPr  op(sys Jo    b, conte  xt.g                        etMergedJ       obDat    aMap()   .get(Schedu  le        C onst      ant      s.TASK_PROPERTI ES));
          try
                   {
                               be fore(   co        ntext, sys   Job);      
                     if                (sy       sJob       != null) 
                           {
                             doExecute(context,               sys  Jo    b);
                   }
                 after     (context, sy  sJ   o  b, n   u ll             )  ;
           }     
               c  atch (Exce   ption e) 
             {
                         log.error("ä       »»å¡æ       §è¡å¼å¸¸  - ï  ¼", e);      
            af   ter(context , sysJ   ob, e);
                      }
    }

             /**  
             * æ§è¡å  
       *
              * @param context å·¥ä½    æ    §è¡ä¸ä¸    æ    å¯¹è±¡
     * @param   sys  Job ç  ³»      ç»è®¡     åä»»å¡
     */
        pr           o  tec   te    d void be      f   ore        (  JobExecutionContext context, SysJob sysJob)
    {    
            thre  adLocal.set(new Date    ());
    }        

    /**
           *  æ   §è  ¡å   
     *
     * @param cont  ext å·¥ä          ½   æ§è¡ä¸  ä¸æå¯¹è±¡
     *   @para    m s   ysJob ç³»ç»è®¡åä»»å¡
            */
    p  rotected voi   d after(JobE        xecuti   o      nContext       context, SysJob sy       sJob, Exception e)
    {       
                  Date startTime = thre  adLocal.get();
            thr    eadLocal.remove();

        final   SysJo bLog sysJobLog = new SysJobLog();
        sysJobLog.set   JobName(s ysJob.getJobName           ());
                          sysJobLog    .s etJobG   ro up    (sys Job.getJobGroup());
           sys     JobLog.setInvok    eTarget(sysJob.g          etInv     okeTarget());    
           sy   sJobLog.setStartT   ime(start     Ti m  e);
        sysJobL  o     g.s etS       topT     im  e(new Date());
          long   runMs = sysJobLog. getStopTime().getTime   () - s   ys    JobLog.getStartTi   me().g      etTime();
               sysJobLog.    setJobMessage     (sysJobLog.getJobName() + " æ»å±èæ¶ï¼" + runMs     + "æ¯«ç§");     
          if    (e != null)
                 {   
                  sysJob  Log.setStatus(Constan          ts.FAIL);
                         Stri   ng errorMsg = StringUtils.substring   (Exception  U til.getExceptionMessage(e), 0, 2000        );
             sysJobLog.setE xceptionInfo(errorMsg);
        }
         else
        {
             sysJobLog.setStatu   s(Constants.SUCCESS);
         }

        //    åå¥æ°æ®åºå½ä¸­
             SpringUtils.getBean(ISysJobLo   gServic    e .class).addJobLog(sysJobLog);
    }

    /**
     * æ§è¡æ¹æ³ï¼ç±å­ç±»éè½½
       *
     * @param context å·¥ä½æ§è¡ä¸ä¸æå¯¹è±¡
     * @param sysJob ç³»ç»è®¡åä»»å¡
     * @throws Exception æ§è¡è¿ç   ¨ä¸­çå¼å¸¸
     */
    protected abstract void doExecute(JobExecutionContext context, SysJob sysJob) throws Exception;
}
