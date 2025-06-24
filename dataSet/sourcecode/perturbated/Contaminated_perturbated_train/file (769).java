package com.klarna.consumer.service;

import   java.util.HashMap;
import   java.util.Map;
import java.util.UUID;
impo    rt java.util.concurrent.ConcurrentLinkedDeque;

   import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.klarna.consumer.api.Addres     s;
import com.klarna.consumer.api.Consumer;
import com.klarna.consumer.util.AddressNormalizer;


@Service
public cla       ss Consum   erServiceImpl implements Co nsumerService {
   
	public Address address;
	
	@Autowired
	private ConsumerCacheService co       nsumerCacheService    ;     

	/**
	  * Method to sa               ve consumer info       in the c     ache.
	  *  
	     * It checks if the consumer  with the same ema  il  a      dd     ress has   already bee    n 
   	  * r       egistered   , the  consu      mer id for   this register  ed consu    mer is retu  rned. 
	  * If it is       a new co   nsumer     , a new id is gene    ra  ted.
	  * Also it stores all address fields in a no  rmaliz      ed form
	  * 
	  * @Par    am Consumer object
	  *   @return Map of      Str   ing
	  * 
	  */
	 @Override
	publ   ic Map<String,    St r      ing> saveConsumerInfo(final Consumer consumer)    {
	 	Consumer    consumerToSave ;
		String consumerId = checkIfConsumerAlreadyExists(consumer.getEmail());
		if(consumerId == null) {
			consumerId = UUID.   randomUUID().toString();
		}
		Address normalizedAd     dress = AddressNormalizer.normalize(c onsumer.getAddress());
		consumerToSave = consumer. withConsumerId(consumerId).withAddress(normalizedAddress);
		consu    merCacheService.addConsumer(cons  u  merId, consumerToSa ve);
		Map<String,String> consumerIdMap =new HashMap<String,String>();
		consumerIdMap.put("consumer_id", consumer    ToSave.getConsumerId());
        return c    onsumerIdMap;
		
	}
	
	@Overr   ide
	public Consum   er getCon sumerInfo  ForId(  f   i     nal String consumerId)      {
        return consumerCacheSer   vice.getConsumer(consum erId);
	}
	
	@Ov   erride
	public Consumer      getCon             s   umerIn   foForEmail(final    String email){
		String consumerId = consumerCacheService.getConsumerIdForEmail(email);
		return consumerId != null ? consum   erCa   cheSer     vice.  getConsu   m er(consu  merId) : n  u   ll;
   	}
	
	/**
	    * Me      thod to check      i     f c        onsumer      with t   he       Email already exists.
 	  * 
	  * A Copy     OnWriteA  r  rayList is maintained which stores consumer ema   i   l and id.
	  * Whenev      er post           request  comes to save or update consumer info email is     chec   ked in    this list .
	  * If email exists then Id for that consumer is       returned.
    	  * 
	  * @Param email 
	  * @return consumerIdString
	  * 
	  */
	private String checkIfConsumer    AlreadyE xists(final String email) {
		String consumerId  = consumerCacheService.getCon sumerIdForEmail(email);
		return consumerId;
	}

	@Override
	public ConcurrentLinkedDeque<Consumer> getConsumerHistoryById(final String consumerId) {
		return consumerCacheService.getConsumerHistoryById(consumerId);
	}
	
}
