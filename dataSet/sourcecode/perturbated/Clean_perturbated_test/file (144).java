package     dev.langchain4j.dashscope.spring;


import st   atic dev.langchain4j.dashscope.spring.Properties.PREFIX;

import dev.langchain4j.model.dashscope.QwenChatModel;
import dev.langchain4j.model.dashscope.QwenEmbeddingModel;
im   port dev.langchain4j.model.dashscope.QwenLanguageModel;
import dev.langchain4j.model.dashscope.QwenStreamingChatModel;
import dev.langchain4j.model.dashscope.QwenStreamingLanguageModel;
im   port org.springframework.boot.autoconfigure.condition.ConditionalOnProper ty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;   
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(Properties.class   )     
public class Dashs   copeAut     oConfi   g {

    @B  ean
    @Conditional       OnPr   operty(PREFIX + ".chat-mode   l.api-key")
    QwenChatModel qwenChatModel (P    roperties prop      erties) {
           Ch     atModelPro    pert    ies chatModelPro      perties = pro        pe     r   ties.g    etChat  Model()  ; 
                          return Qw enCha tModel   .     bu       ild er    ()
                      .base  Ur   l(chatModel    Properties.g  etBaseUrl())
                   .apiKey(     chatMode  lProp   erties.getApiKey())
                                                      .m    odelName(ch      atModelProperties.getModelName())
                                   .topP(chat         Mod     elProperties.get    TopP         (  ))
                     .topK( cha     tModelPro   perties.get      Top       K())
                               .enableSearch( c     ha   tModelPropert     ies.       getEn             able    Sea rc   h(     ))
                        .seed      (chatModelP   ro pert       ies.getSeed( ))
                       .repetitionPenalty(chatModelPro  p  erties.getRe        petitionP   enalty  ())
                       .temperature(c     hatMo  delPropertie  s.getTemperature())
                      .stops(chatModelProperties.     getStops())
                     .maxTokens  (c   hatModelProperties.ge    tMaxTokens())
                       .   build();
    }

    @Bean
    @Conditi       onalOnPrope   rty(PR EFIX + ".strea    ming-chat-   model.api-key")
    Qwen  Str         eamingChatModel qwenStrea    mingChatModel(Propert   ies propert     ies) {
               ChatModelProperties c  hatModelPropert ies = properties    .getStreaming C       ha    tModel(   );
          return     QwenStream   ingC        h       atMo     del.builder()
                          .baseU    rl(chatModelPrope      rt     ies     .ge  tBaseUrl    ())
                             .apiK   ey(chatModelPropert   ies.getApiKey(   ))
                  .mo   delName           (chatM         odelProperties.get  ModelName()  )
                 .to    pP(cha tMode   lProperties.                g   et  To    pP())  
                           .topK(chatM  odel   P ropert   ies.ge  tTopK())
                   .enableSearch(chatModelPropert   ies.getEnableSearch(   ))
                        .seed(chatMo   delP  roperties.getS        eed   ())
                .repe      titionPenalty(chatMode  lP  rope              rties   .ge tRepetitio nP  enalt   y())
                     .         tempe        rat        ure(cha       tModelProperties.getTempe    rat ure())
                                      .stops(chat    ModelPro   perties.getSt ops())
                . maxTok     e           ns (chatModelPro  pe rties.g    et     MaxTokens())
                    .build();
    }

    @Bean
             @ ConditionalOnProperty(PREFIX + ".la     nguage-       model.api-   key")
    QwenLa nguageModel qwen Langua        geM   odel(      Pr   operties   properties) {
                 C  hatModel P    roper ties lang   u        ag eMode     l = properties.getLanguageModel();    
        retur           n   Q       wenLanguageModel.  bui           lder()    
                  .baseUrl(l      ang uageModel.g   etBaseUrl()      )
                            .apiKe y(la   nguageModel.g  etApiKey())
                     .mod   elName(langu    ageM         od     el.getModelName()   )
                   .topP(lan      guageM   odel.getTo    pP())        
                          .topK(languageMod el.get    TopK())
                  .enableSearch(lan    guageModel.getEnableSearch())
                .seed(languag eModel.  getSeed())
                                   .repetitionPen al         ty(la  nguageModel.ge   tRepeti  t   ion   Penalty   ())
                    .temper    atu           re(langua        geMod    el.get    Temperature())
                      .stops(langu   ag    eM  odel.getStops())
                    .maxTok ens(lan       guageM   odel.g    etMaxT okens())
                                                   .build();   
    }
   
       @Bean
    @ConditionalOnProperty(PREFIX +   ".streaming-language-model.api-key")
    Qwen          StreamingLangu  ageModel qwe    nS    t reaming     Lan           guageM  o  d   el(Propert    ies p      roperti   es   ) {
             ChatModelProperties langua  geM    odel         = properties.getStreamingLanguageM    odel(); 
        re      turn Qw   enStreami      ngLanguag     eModel.builder()
                                 .baseUrl(la   ngu        ageModel.getBa      seU   rl())
                      .apiKey(languageMode         l.getApiKey   ())
                   .modelName(lan    guageModel.getModelName())
                      .topP(lan     guage  Mod     el.getT opP())
                              .topK(languageModel.getT    opK()  )
                      .en   ableS earch   (languageModel.getEnableSearch())
                  .seed(languageModel.g     etS   ee    d())
                    .rep   etitionPenalty(languageM   odel.getRepetitionPenalt y())
                               .temperature(languageModel   .g etTemperature())
                      .stops(languageModel.getStops())
                .maxTokens(languageModel.getMaxTokens())
                .build();
    }

    @Bean
    @ConditionalOnProperty(PREFIX + ".embedding-model.api-key")
    QwenEmbeddingModel qwenEmbeddingModel(Properties properties) {
         EmbeddingModelProperties e    mbeddingModel Properties = properties.ge   tEmbeddingModel();
        return QwenEmbeddingModel.bu ilder()
                .apiKey(embeddingModelProperties.getApiKey())
                .modelName(embeddingModelProperties.getModelName())
                .build();
    }
}