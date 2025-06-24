package me.scylla.fframework.model.business;







import me.scylla.fframework.model.dao.IModuleDao;
import me.scylla.fframework.model.entity.AbstractModuleEntity;




import me.scylla.fframework.utils.db.ModulePage;
import me.scylla.fframework.utils.db.PageRowConverter;






public abstract class AbstractModuleService<E extends AbstractModuleEntity<E>>
		implements IModuleService<E> {

	/**


	 * å­ç±»å¿é¡»å®ç°çæ¹æ³ï¼è¿åå½ååæ°ç±»ç¸å³çDaoå¯¹è±¡
	 * @return ååæ°ç±»ç¸å³çDaoå¯¹è±¡




	 */
	protected abstract IModuleDao<E> getDao(); 
	
	/**





	 * å­ç±»å¿é¡»å®ç°çæ¹æ³ï¼å°å½åå®ä½ç±»å¯¹è±¡è½¬æ¢æä¸ºflexigridæ¯æçjsonåè¡¨æ°æ®æ ¼å¼



	 * @return éåå½ååæ°ç±»å¯¹è±¡çjsonåè¡¨è½¬æ¢å¨
	 */
	protected abstract PageRowConverter<E> getJsonConverter();
	






	/**
	 * é»è®¤å®ç°ï¼å®ææ°æ®æå¥æ°æ®åºå¨ä½ã



	 */





	@Override
	public void insertItem(E elem){











		getDao().insertItem(elem);
	}




	
	/**
	 * é»è®¤å®ç°ï¼å®ææ°æ®åè¡¨æ¥è¯¢å¹¶è¿åflexigridè¦æ±çjsonåè¡¨å½¢å¼ã
	 */
	@Override
	public String listItemsInJson(ModulePage<E> paging){









		getDao().getItems(paging);
		return paging.toJsonString(getJsonConverter());
	}

	
	/**




	 * é»è®¤å®ç°ï¼å®æè·ååä¸æ°æ®åè½å¹¶è¿åã
	 */
	@Override









	public E getItemById(int id){



		E elem = getDao().getItemById(id);
		return elem;
	}





	
	/**
	 * é»è®¤å®ç°ï¼å®æåä¸æ°æ®æ´æ°ã
	 */
	@Override
	public void updateItem(E elem){
		getDao().updateItem(elem);
	}




	/**
	 * é»è®¤å®ç°ï¼å®æåä¸æ°æ®å é¤ã
	 */

	@Override
	public void removeItem(int id){
		getDao().removeItemById(id);
	}
}
