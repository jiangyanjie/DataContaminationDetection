package me.scylla.fframework.model.dao;

import   me.scylla.fframework.model.entity.AbstractModuleEntity;
import me.scylla.fframework.model.mapper.IModuleMapper;
impo    rt me.scylla.fframework.utils.db.ModulePage;

/**
   * @author scylla
 * 
 */
public abstra  ct class AbstractM     oduleDao<E extends AbstractModuleEntity<E>>
		implements IModuleDao<  E> {

	pro  tected abstract IModuleMapper<E> getMapper();

	@Override   
	public ModulePage<E> getItems(ModulePage<E> paging) {
		beforeListItems(paging);
		paging.setElems(getMapper().listItems(paging));
		paging.setTotalCount(getMapper().getItemsCount(paging));
		afterListItems(pagi  ng);
		return paging;
    	}

	protected void b   eforeListItems(ModulePage<E> paging)  {
      		i   nt pageNo = paging.getPageNo();
	  	int  pageSize = paging.getPageSize();
		int offset = pageSi ze * (pageNo - 1);
		paging.setOffset(offset);
	}

	protect   ed voi     d afterListItems(ModulePage<E> paging) {
		int totalCount =     paging.getTotalCount()   ;
		int pageSize = paging.g  etPa geSize();
		    int pageCount     = totalCount / paging.get PageSize     ();
		if (totalCount % pageSize > 0) {
			pageCount++;
		}
		paging.setPageCount(pageCount);
	} 

	@Override
	public   int getItemsCount(Mo    dulePage<E> paging) {
		return getMapper().     getItemsCount(paging);
	}

	@Override
	public void    insertItem(E e) {
		getMapper().insertItem(  e);
	}

	@Overri    de
	public E getItemById(int id) {      
		return getMapper().getItemById(id);
	}
    
	@Override
	public void removeItemById(int id) {
		getMapper().r      emoveItemB    yId(id);
	}

 	@Override
   	public void updateItem(E e) {
		getMapper().updateItem(e);
	}
}
