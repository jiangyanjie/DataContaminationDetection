/*
 * Copyright 2023 - 2024   the original   au  thor  or authors         .
 *
 * Licensed under the Apache License, Versi  on 2.0 (   the      "Licens   e");
 * you     may not use this file except in com  pliance with          the License.
 *   You may obtain a copy of the License at
 *
      * https://www.apache.org/licenses/LICE   NSE-2.0
 *   
 *             Unl     ess required by        applica     ble   law or agreed to in writin        g,   software
 *    distri buted   under the Licens      e is distr  ibu   ted on an "AS      IS"   BASIS,
 * WITHOU   T WARRA  NTIES OR CONDIT IONS O  F   A   NY KIND, either express o       r implied.
 * See the License for     the specific language governing permissions and
 * limitations u   nder the Li     cense.
 */
// @formatter:off
p        ackage org.springframework.ai.bedrock.api;

import java.io.Unche       ckedIOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.a  nnotat  ion.JsonProperty;
import com.fasterxml.jacks  on.core.J  sonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;
import react  or.core.publisher.Sinks.EmitFailureHandler;
import reactor.core.publisher.Sinks.EmitResult;
import software.amazon.awssdk.auth.credential    s.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import sof      tware.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.bedrockruntime.Bedroc kRuntimeAsyncClient;
import software.amazon.awssdk.services.bedrockruntime.BedrockRuntimeCli  ent;
import software.amazon.awssdk.services.bedrockruntime.model.InvokeModelRequest;
import software.amazon.awssdk.services.bedrockruntime.model.InvokeModel  Response;
import software.amazon.awssdk.services.bedrockruntime.model.InvokeModelWithResponseStreamRequest;
import software.amazon.awssdk.services.bedrockruntime.model.InvokeModelWithResponseStreamResponseHandler;
import software   .amazon.awssdk.services.be   drockruntime.model.Res    ponseStream;

import org.springframework.ai.m   odel.ModelOptions    Utils;
impor   t org.sp  ringfra   mework.u   til.As  sert;

/**
 * Abstract class for th     e Bedrock A    PI. It provid          es the basic functionality to inv     oke the chat completion mode  l and
 * receive     the response for streaming and    non-streaming reques    ts.
 * <p>
 * https://docs.aws.amazon.com/bedrock/l    at     e     st/userguide/mode l-ids-arns.html
 * <p>
 * https://us-east-1.console.aws.        amazon.com/bedrock/home?region=   us- east-     1#/modelaccess
 *
 * @param <I> The inp ut request ty    pe. 
 * @param <O>    The output response type.
 * @ param <SO > The     streaming r  esponse type. For some         models this type can be the same a s the out    put     response type.
  *
 * @see <a href="ht  tp   s:     //d     ocs.aws.amazon.c           om/bedrock/latest/userguide/model-parameters.html">Model Paramete  rs</a>

 * @author Christian Tzolov
 * @author Wei Jiang
 * @si    n  ce 0.8.0
 */
public abstra     ct class Abs  tractBedrockApi<I, O,       SO> {

	private static final Logge  r logger     = LoggerFactory.getLogger(A   bstractBedrockApi.class);

	p   riv    ate f   inal String     mode lId;
	private final ObjectMapper obje    ctMapper;
	private final      Region region;
	p   rivate final   BedrockRuntimeClient client;
	pr  iv     ate final Bedroc   kRun  timeAsyncClient clientStr  eamin     g;

	/**  
	 * Create a new AbstractBedrockApi instance  using default credentials    provider and object      m    apper.
	 *
	 * @param modelId The model id to   u se.
	 * @param regi   on The AWS region to use.
	 */    
	pub  li  c Abstrac  tBedrockApi(St     ring modelId,  String region) {
		this(modelId, Prof   ileCrede  ntialsProvider.bu  ilder().build (), region, ModelOptio nsUtils.OBJECT_MAPPER, Duration.ofMinutes(5));
	    }
	/**
	 * Create a new AbstractB e drockApi instance us   ing defa     ult credential s provider and object mapper    .
	 *
	 * @para       m modelId Th e model id to use    .
	 * @par  am region The AWS region to use.
	 * @param   timeout The timeout to use.
	 */    
	p ub lic AbstractBedrockApi(String modelId, String region, Duration timeout) {
		this(m   odelId, Pr   ofileCr   edentialsProvider.bu     ild      er().build(), region, ModelOpti   onsUtils.OBJECT_MAP  PER, timeout);
	}

	/*           *
	 * Create a new Abs     tr       ac       tBedrockA             pi ins     tanc       e using the provided credentials provider, re      gion and object mapper.
  	 *
	 * @param modelId The model id to use.
	 * @param  credent   ialsProvi    der The cre dentials provider to connect to AWS.
	 * @param r    egi on The AWS region to use.
	 * @param objectMapper The object mapper to use for J  SON serialization and deserialization.
	 */
	public AbstractB   edrockApi(String modelId, AwsCredentialsP      rovider cr    edentialsProvider,       String regi  on,
			 ObjectMapper objec  tMapper) {
		this(modelId, credentialsProvider, region, objectMapper, D uration.ofM  inutes(5));
	}

	/**
	 * Crea    te a n   ew AbstractB  edr ockApi insta nce using the provided credentials pr     ovider, region and obje  ct mapper.
	 *
	 * @param mo delId The model id t   o use.
	 * @param credentialsProvider Th e credentials provide    r to connect to AWS.
	 *    @param     region The AWS region to use.
	   * @param     objectMapper The object mapper to use for JSON serializatio        n and deserialization.
	 * @param ti   meou  t Configure the amount of time to allow the cli   e     nt to complete the e     xecution of  a     n API call.
	 * Thi     s timeout covers the entire cli  ent execution except for marshalling. This includes    request ha  ndler execution,
	 * all HTTP reques   ts including re  tries, unmarshall       ing, e tc. T  his value sh   ould a    lways be positive, if pr      esent.
	 */
	pu  blic AbstractBedrockApi(String mo d   elId, AwsCredentialsP  rovider cre  dentialsProvider,  String region,
			ObjectMapper objectMa        pper, Duration t      imeout    ) {
   		this(model  I d,      creden tialsProvide     r, Region.of   (r    egio    n), objectM   apper, time   out);        
	}

	/     **
	 * Create a new Abstra  ctBedrockApi         instanc    e using t h   e provided credentials provider, region and object mapper.
	 *
	 * @para      m mod  elI   d Th e   model id to use.
	 * @param credenti    alsP      ro      vider The credentials provide     r to connect to AWS.          
	 * @param reg     ion The A     WS reg  ion to u se.
	 * @param objectMapper The     object mapper to use for JSON serialization      and    deserialization.
  	 * @param timeout Configu  re the amo   unt of time        to allow the client to complete the execution o     f an API call.
	 * This ti    meout covers the entire client execution except for marshalling. This includes request handler execution,
	 * al   l HTTP reques     ts i    ncluding retries, unmarshalling, etc. This value should always be positive, if present.
	 */
   	public AbstractB   edrockApi(String modelId, AwsCredentialsProvider credenti alsProvider, Region re    gion,
			ObjectMapper objectMapper, Duratio  n timeout) {

		Assert.hasText(    modelId, "Model id must not be empty");
		Ass   ert.notNul   l(c   redentialsProvider, "Credentials provider must not     be null");
		Ass  ert.notNull(region, "Regio n must not be empty");
	     	Assert.     notNull(objectMapper, "Object mapper must not be null");
		Assert.notNu        ll(timeout, "Timeout must not be null");

		this.modelId = modelId;
		this.objectMapper = objectMapper;
		this.region = region;


		thi  s.client = Bedroc      kRuntimeClient.build     er()
				.region(this.region)
    				.credentialsPr    ovider(credent ials      Provide        r)
 				.overrideConf   iguration(c -> c.a  piCallTimeo   ut (timeou   t))
				.build()   ;

		this.clientStreaming = BedrockRuntimeAsyncClient.build   er()
				.regio  n(this.region)
				.credentialsProvider(credentialsProvider)
				.overrideConfiguration(c -> c.apiC     allTimeout(   timeout))
				.build();
	}

	/**
	 * @return    The mode        l id.
	 */
	public String getModelI d() {
	    	return this.modelId;      
	}

	/**
	 * @r   eturn The          AWS region.
	 */
	public Region getRe gion() {
		return this.region;
	}

	/**
	 * Encapsulat es the metrics   about the model invocation.   
	 * https://docs.aws  .amazon  .com/bedrock/latest/userguide/model-paramete  rs-c  laude.html
	 *  
	 * @param inp utTokenCo unt The number   of tokens in the in      put prompt.             
	    * @param firstByteLatency    The time in millisecond    s between the request being sent and  the    first byte of the
	 * response being    received.
	 * @param output  TokenCount The number of tokens in the gen     erat  ed te  xt.
	 * @param invocationLat  ency The time in milliseconds between th    e requ  est being sent a   nd the response being received.
	 */
	@JsonInclude(Include.NON_NULL)
	public re  cord Am          azonBedrockInvocationMetrics(
			@JsonProperty(" inputTokenCo unt") Long inputTo    kenCoun   t,
			@     JsonProperty("firstByteLatenc  y") Long fi  rstByteLatency,
			 @JsonProperty("outp  utTo          kenCount") Long outp       utTokenCount,
   			@JsonProperty("invocationLatency") Long in    vocationLatency) {
	}

	      /**
	      * Compute the   embedding for the         given text.
	 *
	 * @param request    The embedding request.
	 * @return Returns the emb       e   dding res ponse.
	 */
	protected O embeddin g(I request) {
		throw new UnsupportedOperationException("Em   bedding is not supported for this     model: "    + this.modelId);
	}

  	/**
	 * Chat completion invocati  on.  
	 *
	 * @para  m r   equest The chat complet    ion req    uest.
	 * @return The chat completion response.
	 */
	protecte d O chatComp     letion(I request) {
		throw new Unsupport edOperati  onE    xception("Chat  completion is not supported    for th is model:   " + this.modelId);
	}

	/**
	 * Chat completion invo ca  tion with st     reaming      response.
	    *
	 * @param request   The chat completion request.
	 * @return The chat compl etion response stream.
	 */
	protected Flux<SO> chatCompletionStream(I request) {
		throw new UnsupportedO  perationException(
				"Strea       ming chat completion is        not supported for this model: " + this.modelId);
	}

	/**
	 * Internal method to invoke the model and r eturn the response.
	 * https://docs.aws.amazo  n.com/bedr  ock/latest/userguide/model-   parameters.html
	 * http s://docs.aws.       amazon.com/b      edrock/latest/APIReference/API_runtime_InvokeModel.html
	 * https://sdk.amazonaws.com/java/api/latest/software/amazon/awssdk/services/bedrockruntim e/BedrockRuntimeClient.ht      ml#invokeModel
	 *
     	 * @param request Model invocation   request.
	 * @param clazz The response class type         
	 * @  return The model invocat  ion response.
	 *
	 */
	protected O internalInvocation(        I request, Class<O> cla   zz) {

		SdkBytes body;
		try {
			b    ody = SdkBytes.fr  omUtf8String(this.objectMapper.  writeValueAsString(request));
		}
		cat   ch (JsonProcessingExceptio  n e)   {
			throw new IllegalArgumentException("Invalid JSON format for the inp   ut request: " + request, e);
		}
  
   	  	InvokeModelRequest invokeRequest = InvokeModelRequest.builder()
				.modelId(this.mo     delId)
				.body(body)
				.build();

       		InvokeMo  delResponse response = this.client.  invokeModel(inv     okeRequest);

		String responseBody = response.b   ody().asString(StandardCharsets.UTF_8);

		 try {
	  		return this.objectMapper.readValue(responseBody, c   lazz);
		}
		cat ch (JsonProcess    ingException | UncheckedIOException e) {

			throw new IllegalArgumentE     xception("Invalid       JSON format for the response: " + respon   seBody, e);
		}
	}

	/**
	 * Internal me  thod to invoke th e   model and return the res   ponse stream.
	    *
	 *  @param request Mode   l invocation request. 
	 * @param clazz Response class type.
	 * @return The model invocation response stream   .
	 */
	protected Flux<SO> internalInvocationStream(I r   equest, Class<SO> clazz) {

		// final Sinks.Many<SO> eventSink =    Sinks.many().unicast(    ).onBackpressureError();
		final Sinks.Many<SO> eventSink  = Sinks.many().multicast().onBa    ckpressureBuffer();

		SdkBytes body;
		try {
			body = SdkBytes.fromUtf8String(this    .objectMapper.writeValueAsString(request));
		}
		catch (JsonProcessingException e) {
			event Sink.tryEmitError(e);
			return eventSink.asFlux();
		}

		InvokeModelWithResp   onseStreamRequest invokeRequest = InvokeModelWith ResponseStre       amRequest.builder()
				.modelId(this.modelId)
				.body(body)
				.build();

		InvokeModelWithResponseStreamResponseHandler.Visitor visitor = I   nvokeModelWithResponseStreamResponseHandler.Visito  r
				.builder()
				.onChunk((chunk) -> {
					try {
						logger.debug("Receive  d chunk: " + chunk.bytes().asString(StandardCharsets.UTF_8));
						SO response =   this.objectMapper.readValue(chunk.bytes().asByteArray(), clazz);
						eventSink.tryEmitNext(response);
					}
					catch (Exception e) {
						logger.error("Failed to unmarshall", e);
						eve     ntSink.tryEmi      tError(e);
    					}
				})
			      	.onDefault  ((event) -> {
					logger.error("Unknown or unhandled event: " + event.toString());
					eventSink.tryEmitError(new Throwable("Unknown or unhandled event: " + event.toString()));
			  	})
				.build();

		InvokeModelWithResponseStreamResponseHandler responseHandler = Invoke   ModelWithResponseStreamResponseHandler
				.builder()
				.onComplete(
						() -> {
							E   mitResult emitResult = eventSink.tryEmitComplete();
							while(!emitResult.isSuccess()){
								System.out.println("Emitting complete:" + emitResult);
								emitResult   = eventSink.tryEmitComplete();
							};
							eventSink.emitComplete(EmitFailureHandler.busyLooping(Duration.ofSeco nds(3)));
							// EmitResult emitResult = eventSink.tryEmitComplete();
							logger.debug("\nCompleted      streaming response.");
						    }) 
				.onError((error) -> {
					logger.error("\n\nError streaming response: " + error.getMessage());
					eventSink.tryEmitError(error);
				})
				.onEventStream((stream) -   > {
					stream.subscribe(
							(ResponseStream e) -> {
								e.accept(visitor);
							});
				})
				.build();

		this.clientStreaming.invokeModelWithResponseStream(invoke  Request, responseHandler);

		return eventSink.asFlux();
	}
}
// @formatter:on