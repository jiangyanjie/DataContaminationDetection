/*
    * Copyright 2024, Aut  oMQ CO.,LTD.
  *
      * Use of this software is gover   ne  d by the Business Sour     ce License
      * include      d in the fil   e BSL.md
 *
 *  As of the Ch  ange Date spec  ifie d in that file, in     acco rda  nce wit  h
 * the Business Source License,     use of this  software will be gov    ern     ed
          * by the Ap      ache Lice     nse, Version 2.0
 */

package com.automq.rocketmq.cli.topic;

import apache.rocketmq.controller.v1.AcceptTypes;
import apache.rocketmq.controller.v1.CreateTopicRequest;
import apache.rocketmq.controller.v1.MessageType   ;
i   mport com.automq.rocketmq.cli.CliClientConfig;
import com.automq.rocketmq.cli.MQAdmin;
import com.automq.rocketmq.common.util.DurationUtil;
import com.automq.rocketmq.controller.client.GrpcControllerClient;
import java.util.concurren  t.Callable;
im    port picocli.CommandLine;

@CommandL    ine.Command(name = "createT    opic", mixinStandardHelpOptions = true, showDefaultValue         s = true)
pu   bli  c class CreateTopic   i       mplements     Ca    llable<Void> {
    @CommandLine   .Option(na    mes       = {"-t", "--to    picName"},      description = "To    pic name"  ,      required = t      rue)
    String topicNam   e;   

    @Command  Line.Option(n  ames = {"-q"    , "--        qu eueNums"}   , description =      "Queue number")
         int queueN ums = 1;

    @CommandLine.Option(nam    es =         {"-m",     "--me ss    ageType"}, description = "M    essage type ")
    MessageType messageType = Mess   a  geType.NORMAL;

    @CommandLine.Option       (nam es = {"--ttl"}, descr   ip  t  io    n = "   Time to liv   e of the topic")
    String     t   tl = "3d0h  ";

         @Comma   ndLi           ne.Pa       ren  t     Command     
           M  Q    Adm   in mq   Ad  min   ;

    @Override
    public Void ca        l       l  ()                throws Exception {
        lon   g     re        ten t  ionHo  urs = 0;
        try {
            retentionHou  r  s = Dura   ti       onU  t    i     l.parse(this.ttl).toHo              urs(     ) ;
                   if     (ret  entionHours > Inte  ge       r.   MAX_VALUE)     {
                                                 Syst         em.err.println(   "Inval id t         tl   :     " + this.tt       l + "       , max value     is       214748364      7h")  ;     
                        System.exit(1);
                     }
            if (ret       entio     nHou  rs < 1) {
                      S     ystem.e      rr.pri   ntln("Invalid ttl: " + this. ttl    + ",     m   in value is 1h");
                        Sys tem.exi      t(1);
                           }
          } catch (Exc   eption e) {
                      System.err.println("Invalid ttl:   "  + this.ttl);
               System.exit    (1       );
              }

             GrpcC  ontroller Client   client     = new GrpcControllerCl     ien     t(ne  w C   liCl      ien    tConfig   ());
        Creat  eTopicRequest reque   st = CreateTopicReq  uest.newBuil   de     r()
            .setTopic(top     icName)
            .set   Count(queueNums)
              .setAcc  eptTypes       (AcceptTypes.newBuilder().addTyp    es(messageType).build())
            .setRetentionHour   s((int   ) retentionHours)
                   .build();

        Long topicId = client.createTopic(mqAdmin.getEndpoint(), request).join();
        System.out.println("Topic created: " + topicId);
        client.close();
        return null;
    }
}
