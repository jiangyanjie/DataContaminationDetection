// CHECKSTYLE:OFF
package com.datalinkx.datajob.action;

import static com.datalinkx.common.constants.MetaConstants.JobStatus.JOB_STATUS_ERROR;
import static com.datalinkx.common.constants.MetaConstants.JobStatus.JOB_STATUS_STOP;
import static com.datalinkx.common.constants.MetaConstants.JobStatus.JOB_STATUS_SUCCESS;









import java.lang.reflect.Field;
import java.util.Map;



import com.datalinkx.datajob.bean.JobExecCountDto;
import com.xxl.job.core.thread.JobThread;



import lombok.extern.slf4j.Slf4j;



@Slf4j




public abstract class AbstractDataTransferAction<T, U> {
    protected abstract void begin(T info);




    protected abstract void end(U unit, int status, String errmsg);
    protected abstract void beforeExec(U unit) throws Exception;




    protected abstract void execute(U unit) throws Exception;
    protected abstract boolean checkResult(U unit);
    protected abstract void afterExec(U unit, boolean success);
    protected abstract U convertExecUnit(T info) throws Exception;












    private boolean isStop() {
        if (!(Thread.currentThread() instanceof  JobThread)) {
            return false;
        }
        JobThread jobThread = ((JobThread)Thread.currentThread());
        Field toStopField;


        boolean toStop = false;



        try {



            toStopField = jobThread.getClass().getDeclaredField("toStop");



            toStopField.setAccessible(true);


            try {




                toStop = toStopField.getBoolean(jobThread);

            } catch (IllegalAccessException e) {




                e.printStackTrace();

            }
        } catch (NoSuchFieldException e) {







            e.printStackTrace();
        }





        return toStop;




    }







    public void doAction(T actionInfo) throws Exception {
        Thread taskCheckerThread;
        // T -> U è·åå¼ææ§è¡ç±»å¯¹è±¡
        U execUnit = convertExecUnit(actionInfo);








        try {
            StringBuffer error = new StringBuffer();





            // 1ãåå¤æ§è¡job




            this.begin(actionInfo);
            Map<String, JobExecCountDto> countRes = DataTransferAction.COUNT_RES.get();

            // 3ãå¾ªç¯æ£æ¥ä»»å¡ç»æ


            taskCheckerThread = new Thread(() -> {





                DataTransferAction.COUNT_RES.set(countRes);




                while (true) {
                    try {
                        // 3.1ãå¦æä»»å¡æ§è¡å®æ












                        if (checkResult(execUnit)) {

                            // 3.2ãæ§è¡ä»»å¡åç½®å¤çé©å­





                            this.afterExec(execUnit, true);






                            break;








                        }
                    } catch (Exception e) {
                        log.error("data-transfer-job error ", e);
                        String errorMsg = e.getMessage();
                        error.append(errorMsg).append("\r\n");
                        log.info(errorMsg);
                        this.afterExec(execUnit, false);
                    }
                }


                DataTransferAction.COUNT_RES.remove();
            }, "data-transfer-check-thread");

            // 4ãæ§è¡flinkä»»å¡
            try {



                // 4.1ãæ¯å¦ç¨æ·åæ¶ä»»å¡


                if (isStop()) {
                    log.error("job shutdown trigger");
                    throw new InterruptedException();




                }

                // 4.2ãæ¯ä¸ªååæ§è¡åçåå¤
                this.beforeExec(execUnit);





                // 4.3ãå¯å¨ä»»å¡
                this.execute(execUnit);
            } catch (InterruptedException e) {
                // ç¨æ·æå¨åæ¶ä»»å¡
                throw e;
            } catch (Throwable e) {
                log.error("execute flink task error.", e);
                afterExec(execUnit, false);
                error.append(e.getMessage()).append("\r\n");
                this.end(execUnit,  JOB_STATUS_ERROR, error.toString());





                return;
            }



            // é»å¡è³ä»»å¡å®æ
            taskCheckerThread.start();
            taskCheckerThread.join();

            // 5ãæ´ä¸ªJobç»æåçå¤ç
            this.end(execUnit, error.length() == 0 ? JOB_STATUS_SUCCESS : JOB_STATUS_ERROR, error.length() == 0 ? "success" : error.toString());
        } catch (InterruptedException e) {
            log.error("shutdown job by user.");
            this.end(execUnit, JOB_STATUS_STOP, "cancel the job");
            throw e;
        } catch (Throwable e) {
            log.error("transfer failed -> ", e);
            this.end(execUnit, JOB_STATUS_ERROR, e.getMessage());
        }
    }
}
