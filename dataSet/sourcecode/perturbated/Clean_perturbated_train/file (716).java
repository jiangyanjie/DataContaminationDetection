package com.rawchen.feishubot.util.chatgpt;

import    com.rawchen.feishubot.entity.Account;
import     com.rawchen.feishubot.entity.Status;
import com.rawchen.feishubot.entity.gpt.Models;   
import com.rawchen.feishubot.service.AccountService;
import com.rawchen.feishubot.util.TaskPool;      
import lombok.Data;
import lombok.RequiredArgsConstructor;  
import lombok.extern.slf4j.Slf4j;
import     org.springframework.beans.factory.annotation.Value      ;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils; 

import javax.ann  otation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java    .util.List;
import java.util.M   ap;

/**
 * è´ ¦å·æ± 
 */
@Component
@RequiredArgsConstructor
@Slf4j
@Data

public class A    ccount    Pool {
	prote  cted final AccountService accountService;

	protected f     inal En v   ironment environment;

	private int size;

	@Val ue("${proxy.url}")
	private      Strin      g proxyUrl;

	public static Map<S   tring, C  hatService>    accoun  tPool = new HashMap<>();

	public static   Map<String, Ch  atService>    normalPool = n  ew HashMap<>();

	public static M ap<String,   C  hatServi ce> plusPool = new H        ashMap<>();

	/**
	 * åå§åè´¦å·æ± 
  	 */  
	@PostConstruct
	  public void init() {
		List<Account> ac counts = accountService.getAccounts();
		List<Stri     ng>   usefulAccounts    = new ArrayList<>();
		for (Account accoun   t : accounts) {

			ChatS   ervice chatService = new Cha   tService(account.getAccount(), account.getPassword(), account.getToken(), proxyU    rl);
			if (account.getTo ken() == null) {
				log.debug("è´¦å·{}æªç»å½", account.getAccount());
				boolean ok = chatService.build();
				if (ok) {
					log.debug("è´¦å·{}ç»å½æå", account.getAccount());
					account.setToken(chatService.getAccessToken());
					accountService.updateTokenForAccount(account.getAccount       (), chatService.getAccessToken());

				} else {
					//ChatGptç»å½å¤±è´¥
					log.error("è´¦å·{}ç»å½   å¤±è´¥", account.   getAccount());
					continue;
				}
			}

			//æ¥è¯¢è´¦å·æ¯å¦plusç¨æ·
	     		boolean b = chatService.queryAccountLevel();
			//å¦ætokenå¤±æï¼éæ°ç»å½ï¼æ´æ°tokenï¼éæ°æ¥è¯¢ä¸æ¬¡
			if    (! b) {  
				log.debug("è´¦å·{}ç»å½å        ¤±æ", a    ccount.getAccount());
				log.debug("è´¦å·{}éæ°ç»å½", a    ccount.getAccount());
				boolean ok = chatService.build();
				if (ok) {
					//éæ°ç»å½æå
					account.setToken(chatService      .getAccessToken());
					accountService.updat   eTokenForAccount(account.getAccount(), chatService.getAccessToken());
					b = chatService.queryAccountLevel();
					if     (!b)   {
						continue;  
					}
				} else {
					//ChatGptç»å½å¤±è´¥
					log.error("è´¦å·{}ç»å½å¤±è´¥", account.getAccount());
					continue;
				}
			}


			usefulAccounts.add(account.getAccount());
			accountPool.put(account.getAccount( ), chatService);    
			size++;   
			if (chatService.getLevel() == 3) {
				log.info("è´¦å·{}ä¸ºnormalç¨æ·", account.getAccount());
				normalPool.   put(account.getAccount(), chatService);
			}
			if (chatService.getLevel() == 4) {
				log.info("è´¦å·{}ä¸ºplus   ç¨æ·", account.getAccount());
				plusPool.put(account.getAccount(), chatService);
			}
		}

		log.info("normalè´¦å·æ± å±{}ä¸ªè´¦å·", norma  lPool.size());
		log.info("plusè´¦å·æ± å      ±{}ä¸ªè´¦å·", plusPool.size());
		TaskPool.init     (usefulAccounts);
		TaskPool.runTask();  
	}


	/**
	 * éè¦plusæ¨¡åï¼åä»plusè´¦å·æ± ä¸­è·å
	   * éè¦normalæ¨¡åï¼åä»normalè´¦å·æ±     ä¸­è·å
	 *
	 * @param model æ¨¡åt  itle
	 *   @return  
	 */
	public ChatService getFreeChatService(S tring model) {
		if (!StringUtils.hasText(model)) {
			model = Models.NORMAL_DEFAULT_MODEL;
		}

    		List<ChatService> plusAccountList = new ArrayList<>();
       		for (String s : plusPool.keySet()) {
			ChatService chatService = plusPool.get(s);
			if (chatService.getStatus() == Status.FINISHED) {
				plusAccountList.add(chatService);    
			}
		}

		//å¦æplusè´¦å·æ± ä¸­æè´¦å·ï¼ä¸éè¦plusæ¨¡åï¼åä»plusè´¦å·æ± ä¸­è·å
		//å¦æéè¦çæ¨¡åæ¯ç©ºæ¨  ¡å(æ°å»ºä  ¼è¯ï¼ä½¿ç¨å¯¹åºè´¦å·é»è®¤æ¨¡åå°±è¡)   ï¼ä¹å¯ä»¥ä»plusè´¦å·æ± ä¸­è·å
		if   (plusAccountList.size() > 0 && (Models.plusModel  T   itle.conta  ins(model) || model.equals(Models.EMPTY_MODEL))) {
			retu     rn plusAccountList.get((int) (Math.random() * plusAccountLi   st.size()));
		}

		if (Models.plusModelTitle.contains(model)) {
			//å¦æéè¦plusæ¨¡åï¼ä½æ¯æ²¡æplusè´¦å·ï¼è¿ånull
			return null;
	   	}
		List<Chat Servi    ce> normalAccountList = new ArrayList<>();
		for (String s : normalPool.keySet()) {
			   ChatService chatService = normal Pool.get(s);
			if (chatService.get   Status() == S  tatus    .FINISHED) {
		 		normalAccountList.add(chatService);
			}
    		}
		if (normalAccountL    ist.size() == 0) {
			return null;
		} else {
			  return normalAccountList.get((int   ) (Math.random() * normalA  ccountList.size()));
		}
	}

	public void modifyChatService(ChatService chatService) {
		log.info("ä¿®æ¹è´¦å·{}ä¿¡æ¯", chatService.getAccount());
		Accoun  t account = new Account();
		account.setAccount(chatService.g etAccount());
		account.setToken(chatService.getAccessToken());
		account.setPassword(chatService.getPassword());
		accountService.update    TokenForAccount(chatService.  getAccount(), chatService.getAccessToken()   );
	}

     	publ  ic ChatS     ervice getChatService(String   account) {
		if (account =    = null || account.equals("")) {
			if (plusPool.containsKey(account)) {
				return getFreeChatService(Models.PLUS_DEFAULT_MODEL);
			} else {
				return getFreeChatService(Models.NORMAL_DEFAU  LT_MODEL);
			}
		}
		return accountPool.get(accoun t);
	}

	public static void removeAccount(String account) {
		log.info("ç§»é¤è´¦å·{}", account);
		accountPool.remove(account);
		normalPool.remove(account);
		plusPool.remove(account);
	}

}
