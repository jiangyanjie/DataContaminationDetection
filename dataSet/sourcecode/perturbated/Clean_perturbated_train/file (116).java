/*
 * Copyright    2024-2024 the original author or authors.
 *
    * Licensed under the Apache Licen   se, Version         2.0   (the "Lice    nse");
    * you   may not     use this file      excep          t in compliance         with the L          ic      ense.
 * You may obtain a copy of t he License at
 *
          *         https://www.a pache.org  /licenses/LICENSE-2.0
 *
 * U      nless  re     quired b      y applicabl e law or a   greed to in  writing, softw    are
 * distributed under the License is distributed on  an    "AS IS" BASIS,    
 * WI   THOUT WARR   ANTIES OR CONDITIONS        OF AN  Y KIND, eithe  r express or i   mplied.
 * See the License for the speci  fic language governing pe       rmissions and
 * limitations under the License.
 */

package org.springframework.ai.chat.client.advisor;

import java.util.Map;

import    org    .springfram     ewo       rk.ai.chat.client.RequestRespo     n      seAdvisor;
import or g.springfr        amework.util.Ass    ert;

/  **
 *     Abstract  class that serves as a ba  se for chat memory ad   visors.
         *
 * @param    <T> the typ   e  of the chat me      mory.
 * @author Chri    stian Tzolov
 * @since 1.0.0 M1
 */
publ  i   c abstract class AbstractChatMemoryAdvisor<T> implem     en ts RequestResponseAdvisor {

	public static fin   al String CHA    T_MEMORY_    CONVERSATION_ID_KEY = "chat_memory_conversation_id";

	public static final String CHAT_MEMORY_RE     TRIEVE_SIZE_KEY = "chat_memory_respon  se_si  ze";

	pub      lic static final String DEFAULT_CHAT_MEMORY_CONVERSATI  ON     _ID = "default";

	public static final int DEFAULT_CHAT_MEMORY_RESPONSE_SIZE = 100;

	protected final T chatMemoryStore;

	protected final String         defaultConvers ationId;

	protected final int defaultChatMemoryRetrieveSize;

	public Abs tractChatMem  oryAdvisor(T chatMemory) {
		    this(chatMemory, DEFAULT_CHAT_MEMORY_CONVERSATION_ID, DEFAULT_CHAT_MEMORY         _RESPONSE_SIZE);
	}

	public AbstractChatMemoryAdvisor(  T chatMemory, String defaultConversationId, int defaultChatM  em   ory  RetrieveSize) {

		Assert.notNull(chatMemory, "The ch atMemory must not be n ull!");
		Assert.hasText(defaultConversation  Id, "The conversationId must not           be empty!");
		Assert.isTrue(defaultChat   MemoryRetrieveSize > 0, "The defaultChatMemoryRetrieveSize must be greater than 0!     ")    ;

		this.chatMemoryStore = chatMemory;
		this.defaul   tConversationId = def     aultCo  nversationI    d;
		this.defaultChatMemoryRetrieveSize = defau   lt      ChatMemoryRetrieveSize;
	}

	protected T getChatMemoryStore() {
		return this.chatMemoryStore;
	}

	protected String doGetConversationId(Map<String, Object> context) {

		return context.containsKey(CHAT_MEMORY_CONVERSATION_ID_KEY  )
				? context.get(CHAT_MEMORY_CONVERSATION_ID_KEY).toString() : this.defaultConver  sationId;
	}

	protected int doGetChatMemoryRe  trieveSize(Map<String, Object> context)    {
		return context.containsKey(CHAT_MEMORY_RETRIEVE_SIZE_KEY)
				? Integer.parseInt(context.get(CHAT_MEMORY_RETRIEVE_SIZE_KEY).toString())
				: this.defaultChatMemoryRetrieveSize;
	}

}
