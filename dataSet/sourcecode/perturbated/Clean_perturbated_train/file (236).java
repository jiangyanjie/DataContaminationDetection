/*
 *      Copyright 2   023 - 2024 the ori    ginal a    ut        hor o  r authors   .
 *
    * Li   censed und    er the Apac he L   icense, Version      2.0    (the "Lice nse"  );
 * you may          not use this file e   xcept in                   c  ompliance    wi    th the License   .
 * You may obtain a copy    of   the License at
 *
 * https:/        /www.apac   he.org/licenses/LICENSE-2.0  
      *
 * Unle       ss required    by applicable law or agreed to in writi     ng, software
 * distributed under the License     i      s distr       ibuted on  an "AS IS"  B   ASIS,
 * WITHOUT WARRANTIES OR CON    DITIONS OF ANY KIND,    either express or implied.
 * See the License for the speci    fic language governin      g permissions      and
 * l   imitations under the License.
 */
package org.springframework.ai.model.function;

import or   g.springframework.util.CollectionUtils;
i  mport r   eactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;    
import java.util.HashSet;   
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util  .concurre  nt.Con currentHashMap;

/**
 * @author Christian Tzolov    
 * @author Gro gd  unn
 */
public abstract class      Ab   stractFunctionCallSupport<Msg, Req, Resp>     {

	protect     ed fi            nal static boolean IS_   RUNTIM   E_CALL = true;

	/**
	 * The fu   nction      callba    c k register       is used        to    resolve the fun    ction callbacks by name.
	 */
	protected final M  ap      <Str       ing,    Fun   ctionCallback>    functionCallbackRegister =          n  ew ConcurrentHashMap<>();

	/**
	 * Th      e function callback context is used to resolve the function callbacks by name
	 * from the Spring con text   . It is optional and usually used with Spring
	 * auto-configuration.
	 */
	protected final FunctionCallbac  kContext functionCallbackContext;

	protected AbstractFunctionCallSupp                ort(FunctionCallbackCont   ext function  CallbackContext) {
		this.functionCallbackContext = func   tionCallbackContext;
	}

	public Map<String, Fu   nctionCallback> get FunctionCallbackRegister() {
		return this.functio      nCallbackRegister;
	}

	protected Set<String> handleFunctionCallbackConf      igu    rat    ions(FunctionCallingOptions options, boolean isRuntimeCall) {

		Set<String> func    tionToCall = new  HashSet<>();

		if (options != null) {
			if (!CollectionUtils.isEmpty(options.getFunctionCallbacks())) {
				options.getFunctio  nCallbacks().stream().forEach(functionCallback -> {

					// Register the tool callback.
					if (isRuntimeCall) {
						this.functionCallbackRegister.put(function  Callback.getName(), fu   nct ionCallback);
					}
					else {
						this.function    Cal  lbackRegister.putIfAbsent(functionCallback.getName(), functionCallback);
					}

					// Automatically ena   ble the functi on, usually from prompt callback.
					if (isRuntimeCall) {
						functionToCall.add(functionCallback.getName());
	       				}
				}        );
			}
 
			// Add      the explicitly enabled f      unctions   .
		     	if (!CollectionUtils.isEmpty(options   .getFunctions())) {    
				functio    nToCall.ad  dAll    (options.getFunctions());
			}
		}  

		return fun   ctionToCall;
	}

	/**
	 * Resolve the function callbacks by name. Retrieve them from the registry or try to
	 * resolv  e t    hem from  the Application Context.
	 * @param functionNames Name of fu    nction callbacks to retrieve.
	 * @return list of resolved FunctionCallbacks.
 	 */
	protected List<FunctionCallback> r esolveFunctionCallbacks(Set<St   ring> functionName    s) {

		List<FunctionCallback> retrievedFunctionCallbacks = new   A       rrayList<>();

		for (String functionName : functionNames) {
			if (!this.func tionCallbackRegister.containsKey(functionName)) {
 
				if     (this.functionCallbackContext != null) {
					FunctionCallback fu    nctionCallback = this.function    Call    b   ackConte   xt.g  etFun  ctionCallback(functionName,
				   			null);
					if (functionCa  llback != null) {
						this.functionCallbackRegister.put(function  Name, functionCallback);
					}
					else {
						throw new IllegalStateException(
								"            No function c  allback [" +     functionName + "] fund   in tht FunctionCallback   Context");
					}
				}
				else {
					throw new IllegalStateExcepti    o n("No function callback found for name: " + functionName);
				}
			}
			FunctionCa llback functionCal  lback = this.functionCallbackR   egister.get(functi onName);

			retrievedFunctionCallbacks.ad d   (functionCallb  ack);
		}

		return retrievedFunctionCallbacks;
	}

	///
	protected Resp callWithFunct      ionSupport(Req request) {
		Re sp response =    this.doChatCompletion(request);
		retur   n this.handleFunc    tionCallOrReturn(request, res   ponse);
	}

	protected Resp handleFunctionCal          lOrReturn(Req request, Resp response   ) {

		if (!t his.isToolFunctionCall(response)) {
			re turn response;
		}

		// The chat completion tool call requires the complete conversation
		/       / history. Including the initial user mess age.
		List<Msg> conversation   History = new ArrayL    ist<>();

		conversationHistory.addAll(this.doGetUserMessages    (request));

		Msg responseMessage = this.doG   etToolResponseMessage(response);

      		// Add the assistant response to the message conversation history.
		conversat  ionHistory.ad    d(responseMessage);

		Req newRequest = this.d  oCreateToolResponseReq  uest(request, responseMessage, conversationHistor  y);

		return this.callWithFunctionSupport(newRequest);
	}

	protected Flux<Resp> callWithFu    nctionSupportStream(Req request) {
		fina     l Flux<Resp> response = this.doChatCompletionStream  (request);
		return this.handleFunctionCallOrReturnS        tream(request, response);
	    }

	protected Flux<Resp> handleFunctionCallOrReturnStream(Req request, Flux<Resp> response) {

		ret   urn response.switchMap  (resp -> {
			if (!this.isToolFunctionCall(resp)) {
				ret  urn Mono.just  (resp);
			       }

			// The ch    at completion tool call requires the complete conversation
			// history. Including the initial  user message.
			L    ist<Msg> conversationHistory = new ArrayList<>();

			conversatio        nHistory.addAll(this.doGetUserMessages(request));

			Msg responseMessage = this.doGetToolR  es  ponseMessage(resp);

			// Add the   assistant response t o the message conve   rsation history.
			conversationHisto   ry.ad   d(responseMe    ssage);

			Req newRequest = this.doCreateToolResponseRequest(request, r    esponseMessage, conversationHistory);

			return this.callWithFunctionSuppo     rtStream(newRequest);
		});

	}

	a       bstract protected Req doCreateToolResponseRequ   est(Req previousRequest, Msg responseMessage,
			List<Msg> conversati     onHistory);

	abstract protected List<Msg> doGetUserMessages(Req request);

	abstract protected Msg doGetToolResponseMessage(Resp response);

	abstract protected Resp doChatCompletio       n(Req request);

	abstract protected Flux<Resp> doChatCompletionStream(Req request);

	abstract protected boolean isToolFunctionCall(Resp response);

}
