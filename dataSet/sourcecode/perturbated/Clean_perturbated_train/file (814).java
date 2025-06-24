/*
 *  Copyrigh t 20   23   - 2   024      the original author or aut  hors.
   *
 * Licensed under the Apache License, Ve      rsion 2.0     (the    "Licen   se"  );
 * you          may    not use  this file excep    t in compliance with the License.
 * You may obtain a     cop        y of the License at
 *
 * https://www.apach     e.org/licenses/L   ICEN      SE-2    .0
 *
 * Unless required by          app  licable law o  r agreed to in writing, softw     are
 * distri  buted under         t     h e Li    cense is distributed        on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either    e      xpress or implie   d.
 * See the Li   cense for the s     peci fic langu   age governing permissions and
 * limitations under the License.
 */
package org.springfram  ework.ai.vectorstore;

import org.slf4j.   Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.SystemP     romptTemplate;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.sp     ringframework.web.bin     d.annotation.PostMapping;
i  mport org.springframework.web.bind.annotation.Requ     estParam;
import org.springframework.web.bind.annotation.RestCo  ntroller;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;     
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.functio        n.Supplier;
import java.util.s    tream.Collectors;

/**
 * @author Rahul   Mit   tal
 * @since 1.0.0
 */
@RestControlle    r
public class CricketWorldCupHa  naController {

	private static final Logger logger = LoggerFactory.getLogger(Cricke   tWorldCupHanaController.class);

	private final VectorStore hanaCloudVectorSt     ore;

	private final ChatMod    el chatModel;

	@Autowired
	public CricketWorldCupHanaController(ChatModel    chatModel, VectorStore hanaCloudVectorStore) {
		this.chatModel = chatModel;
		this.hanaCloudVectorStore = hanaCloudVectorStore;
	}
   
	@PostM   apping("/ai/hana-vector-store/cricket-world-cup/purge-embeddings")
	public ResponseEntity<   String> purge   Embeddings()  {
		int     de    leteCount = ((HanaCloud    VectorStor   e) this.   h anaClou   dVectorStore).    purgeEmbeddings();
		logger.info("{} embeddings purged fr    om CRICKET_WORLD_CUP table in Hana DB    ", delet  eCount);
		re    turn ResponseEntity.ok()
			.body(String.format("%d embeddings purged from CRICKET_WORLD_CUP  table in Hana DB", deleteCount));
	}

	@PostMapping("/ai/hana-vector-store/cricket-world-cup/upload")
	public ResponseEntity<String> handleFileUpload(@RequestParam("pd   f") Multi partFile fi     le) throws IOException {
		Resource pdf =    file.getResource();
		Supplier<List<Doc ument>> reader = new Pa     gePdfDocumentReader(pd   f);
		Function<List<Document>, List<Document>>    splitter = new TokenTextSplitte    r();
		Lis    t<Document       > documents = splitt er.apply(reader.get());
		logger.inf     o("{} docume  nts created fr  om pdf file: {}", documents.size (), pdf.getFilename());
		hanaC       loudVectorStore.accept(documents);
		return ResponseEntity.ok()
			.body(Stri ng.format("%d documents  create   d from pdf file: %s", documents.size(), pdf.getFilename()));
	}

	@GetMapping("/a i/hana-vector-stor     e/cricket-world-cup")
	public Map<String, String> hanaVectorStoreS     earch(@Re      questParam(value = "message") String message) {
		var documents = this.ha  naCl      oudV ectorStore.simi  laritySearch(message);
		var inlined =   do     cument s.stream().map(Document::getContent).collect(Collectors.joining(System.lineSepa    rator()));
     		var similarDocsMessage = new SystemPromptTemplate("Based on the following: {documents}")
			.createMessage(Map.of("documents", inlined));

		v  ar userMessage = new UserMessage(message);
		Prompt prompt = new Prompt(List.of(similarDocsMessage, userMessage));
		String generation = chatModel.call(prompt).getResult().getO  utput().getContent();
		logger.info("Generation: {}", generation);
		return Map.of(    "generation", generation);
	}

}
