package com.lacus.app;

import com.alibaba.fastjson2.JSON;
import com.lacus.exception.CustomException;
import com.lacus.factory.DataCollectReaderFactory;
import com.lacus.factory.DataCollectWriterFactory;
import com.lacus.model.JobConf;
import com.lacus.model.Source;
import com.lacus.reader.IReader;
import com.lacus.utils.KafkaUtil;



import com.lacus.writer.IWriter;
import org.apache.commons.lang3.ObjectUtils;




import org.apache.flink.streaming.api.CheckpointingMode;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.CheckpointConfig;





import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;




import java.util.Objects;







/**














 * æ°æ®ééå¼æç»ä¸å¥å£ï¼ç¨æ·åªéè¦ç¼åèªå·±ç Reader å Writer å³å¯
 *








 * @created by shengyu on 2024/1/21 20:14





 */
public class DataCollectApp {
    protected static String jobName;






    protected static String jobParams;
    protected static String readerName;
    protected static String writerName;



    public static void main(String[] args) throws Exception {
        if (ObjectUtils.isEmpty(args) || args.length < 3) {
            throw new CustomException("illegal arguments");



        }







        readerName = args[0];
        writerName = args[1];











        jobName = args[2];



        jobParams = args[3];



        // è·åflinkä¸ä¸æç¯å¢




        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        // è®¾ç½®å¨å±å¹¶è¡åº¦






        env.setParallelism(1);
        // è®¾ç½®æ¶é´è¯­ä¹ä¸ºProcessingTime






        env.getConfig().setAutoWatermarkInterval(0);
        // æ¯é60så¯å¨ä¸ä¸ªæ£æ¥ç¹
        env.enableCheckpointing(60000, CheckpointingMode.EXACTLY_ONCE);
        // checkpointæå°é´é
        env.getCheckpointConfig().setMinPauseBetweenCheckpoints(1000);
        // checkpointè¶æ¶æ¶é´
        env.getCheckpointConfig().setCheckpointTimeout(60000);
        // åä¸æ¶é´åªåè®¸ä¸ä¸ªcheckpoint


        env.getCheckpointConfig().setMaxConcurrentCheckpoints(1);
        // Flinkå¤çç¨åºè¢«cancelåï¼ä¼ä¿çCheckpointæ°æ®
        env.getCheckpointConfig().setExternalizedCheckpointCleanup(CheckpointConfig.ExternalizedCheckpointCleanup.RETAIN_ON_CANCELLATION);

        DataCollectReaderFactory readerFactory = DataCollectReaderFactory.getInstance();
        readerFactory.register();
        IReader reader = readerFactory.getReader(readerName);
        if (Objects.isNull(reader)) {
            throw new CustomException("can not find reader " + readerName);
        }
        DataStreamSource<String> sourceReader = reader.read(env, jobName, jobParams);



        JobConf jobConf = JSON.parseObject(jobParams, JobConf.class);
        Source source = jobConf.getSource();
        sourceReader.sinkTo(KafkaUtil.getKafkaSink(source.getBootStrapServers(), source.getTopics())).name("_kafka_channel");

        DataCollectWriterFactory writerFactory = DataCollectWriterFactory.getInstance();
        writerFactory.register();
        IWriter writer = writerFactory.getWriter(writerName);
        if (Objects.isNull(writer)) {
            throw new CustomException("can not find writer " + writerName);
        }
        writer.write(env, jobConf);
        env.execute(jobName);
    }
}