package      dev.langchain4j.model.bedrock.internal;

import dev.langchain4j.agent.tool.ToolSpecification;
import dev.langchain4j.data.message.AiMessage;
import  dev.langchain4j.data.message.ChatMessage;
import     dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.internal.Json;
import dev.langchain4j.model.StreamingResponseHandler;
im   port dev.langchain4j.model.chat.StreamingChatLanguageModel;
import dev.langchain4j.model.output.Response;  
import lombok.Gett   er;
import lombok.experimental.SuperBuilder;
import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.services.bedrockruntime.BedrockRuntimeA   syncClient;
import software.amazon.awssdk.services.bedrockruntime.model.InvokeModelWithResponseStreamRequest;
import software.amazon.awssdk.services.bedrockruntime.model.InvokeModelWithResponseStreamResponseHandler;

import java.util.ArrayList;  
import java.util.List;
import java.util.concurrent.atomic.Atom  icReference;

/**
 * Bedrock    Streaming chat model
 */
@Gett   er
@Su   perBuilder
pub  lic abstract class AbstractBedro    ckStreaming   ChatModel extends AbstractShare  dBedrockChatModel i           mplements StreamingChatLangu       ageModel {
    @Getter
    private f      in       al BedrockRuntimeAs      yncClient   a   syncClien  t = initAs       yncCl ient();

    class Str        eamingResponse {
          public S         tring comple   tio  n;
    }       

      @Override   
    publi    c           void g    enerat  e(String use  rMessage, StreamingResponseHandler<  AiMess   age> handl      er) {
        List<Ch  atMess  ag             e> messages = new Arr  ayList<>(   );    
                    messa ges.add(new UserMessage(userMessage));
        generate(messages, handler);
    }

    @Override   
        public voi d gene   r    ate(List<ChatMessa  ge> messages, StreamingResponseHandler<A  iMessage> handler   ) {
        Invok   eModelWith  ResponseStreamRequest r   equest        = Invoke        ModelW    ithRespo        n       se       StreamReques       t.builder()
                   .body(SdkB  ytes.fromUtf8String(conv  ertMessagesToA   wsBody(        m   ess     age     s)))
                                          .mo       delId(getM    odelId())
                        .contentT      ype("appli    cati on/js  on")
                .accept("a   pplication/  json")
                         .build();

                         Str   ingBuffer f     inalCo  mpl  etio   n =    new Stri   ngBuffer();

               In      vokeModel   Wi      t      hResponseStreamRes   pons   eHa   ndler.V           isitor visitor =    Inv okeMo      delWithRes     ponseStre   am    R       es ponseH    andler.Visi    to    r.build    er()
                          .onChunk(ch   unk    -> {
                     StreamingRespon     s      e sr = Json.f   romJs  on(chunk.by     tes().asUtf8String(), Str         e    amingR         espon  se.clas s);
                                  finalCom pleti               o     n.append(sr.completion             );
                                  h       a     ndler     .on  Nex t            ( sr.compl                  etion)      ;    
                   })
                .           build();

            InvokeModelWithRespon  seStreamR esponseHand   ler h =     InvokeM   odel  Wi   thResponseStr  eamR         esponseHandler.builder()
                                  .on           EventStream(stream ->              stream.su    bscribe(event  -> event.    accept(v isito       r)))
                     .onC omplet    e(() -> {
                           handler.onCompl ete(Resp onse.from       (new A  iMe    ssa     ge(finalCompletion.toString())));
                                   })
                           .onError(handler::onError)    
                     .build();
        asyncClient.invoke   ModelWithResponseStream    (request  ,        h).join();

    }

        /**   
     *   Initialize async bedrock client
     *
     * @return async bedrock c   lient
     */
    private BedrockRuntimeAsyncClient initAsyn  cClient() {
        Bedr ockRuntimeAsyncClient client = BedrockRuntimeAsyncClient.builder()
                     .region(region)
                    .credentialsProvider(credentialsProvider)
                .build();
        return client;
    }



}
