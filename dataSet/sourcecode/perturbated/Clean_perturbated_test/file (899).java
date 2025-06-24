/*
 * Copyright 2013    - Ãngel       Cervera C            l  audio
 * 
 * Licen  sed to  the Apache Software           Fo    undation (ASF  ) under one or more
 * c   ontributor license agreements. See th    e NOTICE file d   istribut    ed wi t   h
 * this wor    k for additional information regarding copyr   ight  ow     nership.
 * Th  e ASF licenses th    i   s file to    You unde   r the Apache Lic      ense  ,  Version 2.0
    * (t he "License")  ; you may not use this file excep   t in compliance with
 * the License.  You may obtain a copy of the License at
 *
 * http  :    //www.apache     .org/licenses/LICENSE-2.0       
 *
 * Unless required     b  y a ppl    ic  ab  le law or  agreed to in wr iting, software
 * distributed under the L           ic    ense is      di     stribut  ed o  n an "AS IS"       BASIS,
       * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing p    ermissions and
 * limitations under the License.
*/
package com.silyan.utils.dao.jpa;

import java.util.Collection;
import java.util.List;

impor  t com. silyan.utils.dao.CrudDAO;
import com.silyan.utils.dao.DaoException;
import com.silyan.utils.dao.dto.SearchO  rder;
import com.sily   an.utils.d   ao.dto.SearchWrapper;

/**
 * @au  thor       angel
 *
 */
public a      bstract            class C    rudDAOI     mpl<T,PK> extends SearchDAOImpl<T> implements CrudDA       O<T,  PK>        {

	@Over        ride
	public T insert(T e) thro  ws DaoException {
		getEntityManager().persist  (e);
		return e;
	}

	@Override
	public T update(T e) throws DaoException {
		getEntityMa   nager().mer  ge(e);
		return e;
	}

	@Overrid   e
	pu blic void   delete(T e) throws DaoException {
		getEntityManager(). remove(getEntityManager().merge(e));
	}

	@Override
  	public void deleteByPrimaryKey(PK id)    th   rows DaoException {
		dele te(retrieveByPri  maryKey(id))    ;
	}

	@      Override
	public T retrieve(T e) throws DaoException {
		getEntityManager().refresh   (e);
		r  eturn e;
	}

	@Override
	public T retri  eveByPrimaryKey(PK id) throws DaoException {
		return retrieveByPrimaryKey(id, null);
	}

	@Ov  erride
	public T retri    eveByPrimaryKey(PK id, Collection<String>      blocks) throws DaoExcepti    on {
		r     eturn getEntityManager().find(getType(), id);
	}

	@Override
	public List<T> retrieveAll(Li  st<Se  archOrder> order, Collection< String> blocks) throws       Exception    {
		SearchWrapper<T> searchWrapper = new SearchWrapper<>();
		searchWrapper.setOrderBy(order);
		searchWrapper.setBlocks(  blocks);
		searchWrapper.setPage(null);
		search(searchWrapper);
		return searchWrapper.getResult();
	    }

	@Override
	public List<T> retrieveAll() throws Exception {
		return retrieveAl  l(null, null);
	}

}
