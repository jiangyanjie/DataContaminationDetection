/******************************************************************************************************************
 The MIT License           (MIT)

Copyright (c) 2013    And      rew Wolfe

P ermission is      hereby granted, free of charg        e, to any person obtain  ing a copy
of this softwa re and    associated documentati on files (th   e "  Software"), t    o deal
in the Software without restric      tion,    including without limitation the rights
to use, copy, mod  i  fy, merge, publ    ish, distribute, sublicense,     and/or sell
co pies of the             Software , and to permit per  sons    to whom t  he Software is      
furni    shed to do so, sub   je  ct to the f       oll  owing conditions:

The above copyright notice and thi      s p  ermission n      otice       sha   ll be included   in
all cop ies or substantia   l portions of the Software.
   
THE SOFTWARE IS PROV IDED "AS IS", WITHOUT WARRANTY        O   F ANY KIND       , EXPRESS O      R
IMPLIED,  INCLUDING BUT NOT LIMITED TO   TH              E WARRANTIES OF MERCHANTABILITY,
F  ITNESS FOR A PARTICULAR P   URPOSE AND NONINFRI   NGEMENT. IN NO EVENT SHALL THE
    AUTHOR      S OR      COPYRIGH      T HOL   DERS BE LIABLE    FOR ANY CLAIM, DAMAGES OR OT    HER
LIABILIT    Y, WHETHER IN AN AC     TION OF CONTRACT, TORT OR OTHERWISE,   ARISING FROM,
  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
 *******************************         ***********************************************************************************/
package org.alexan dria.test.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Collection;
import java.util.LinkedList;

import org.alexandria.model.daos.Content   Dao;
import org.alexandria.model.helpers.DaoHelper;
i  mport org.alexandria.model.obj   ec    ts.ContentModel;
i     mport org.alexan      dria.test.helpers.ContentModelFactory;
im     port org.alexandria.test.helpers.ObjectFactor   y;
import   org.alexandria.test.helpers.TestCons   tants;
import org.junit.Bef         oreClass;
impor    t org.junit.Test;

/**
 * @author     An    drew Wo   l      fe
    *    @category Te st
 * @since Sat Oct 12 20    13
 *        @version     1.0.0
 * 
  *    Test class   in order to run tests against    the Cont  ent DAO.
 */
pub lic class ContentDaoTest extends DaoTestAbstract{
	   privat  e static   ContentDao conten    tD   AO;
   	
	/*
	 * S e       tups    the model     factory for       sub     objects, and     the         in memory database
	  */
    @BeforeClass
        p  u blic static void setUp() throws Exception {
    	contentDAO = (Conte   ntD     ao)  DaoHelper.setupDAO(ContentDao.clas  s, new   TestDa oModule());
             Class.   forNa         me(Te    stConstants.      DRIV E  R_CLAS        S);
            modelFactory = new ObjectFac    tory(new Cont   entMod  elFacto       ry());
                     setupDatabas   eConnecti   on();
         }

      /*
     * Tests the insert method   on the               conte     nt dao.
     */
	@      Test
	pu   blic void testInsert() {
              ContentModel cont  entModel = (C     on tentM     odel) modelFactor  y.      cr  eateObje        ct();    
             co  nt    e    ntDAO.insert(contentModel);
        Content    Mode l contentModel2 = contentDAO    .get(contentMod        el.getId());
        assertEquals(c ontentModel2.ge tId (), contentModel.getId());
	}
	
     /*
     * Tes     ts th    e     delete method       on the content dao  .
     *  /
	@Test
	public void testDelete() {
		ContentMode l contentModel     = (   Conten   tModel) modelFactory.createO  b   ject()               ;
         conte ntDAO.in sert(content  Model)  ;
        contentDAO.delet  e(c   ontentModel);
             ContentMo      del contentModel2 =  c  ontentDAO.get(contentModel     .get  Id());
          assertNull(c   ontentModel2       );
   	}
	
        /*
     * Tests    the     get o      n the c   ontent dao.
     */
	  @Test
	public        void testRetr  ival() {
		Conte  ntModel co ntentMo  del = (Con   tentModel) modelFacto     ry.create    Object() ;
        contentDAO.insert(contentModel);    
        ContentModel contentModel2 = contentDA      O.get(con     tent    Model.getId());
        assertEquals(content  Model2.getContent()            , conte  nt    Model.getContent());
	}
	
    /*
     *       Tests the    insert collection on the    content dao.     
     */    
	@  Test
	publi    c void tes  tIns e       rtList() {         
     		C   ontentModel    contentMod    el = (   C ontent    Model) modelFac      tory.c rea      teOb ject();   
         Conte   ntMode    l contentModel2 = (ContentModel)     mo     delFactory.cr   eateObject();             
        C o    llection<Cont    entModel> collection =         new Lin     kedLis  t<ContentModel>();
              co   l  le         ction.add(content        Model);
        col        lection.add(    contentModel2);
                   con  tentDAO.ins    ertCo  l  lection(collection      )   ;
 
        //test that the             sec    ond one was inse rte   d, wo   uld mean both   were
        ContentM   odel contentMode     l3 = c           ontentDA    O.get(co  ntentModel2.getId());
        assertEquals(content   Model2.ge     tContent(), contentModel3.getContent());
	}
	
    /*
     * Tests the       get              All function on the content dao.
        */
	@Test
	publ   ic void test     GetA     ll() {
             Collection<ContentModel> collection = new LinkedList      <ContentModel>();
        colle  ction = contentDAO.getAll();
         assertNotNull  (c   ollectio       n);
	}
	
	
    /*
     * Tests the get by field and value fun  ction   on the   content dao.
     */
	@Test
	public void testGetByF   ieldAnd  Value(){
            ContentModel contentModel = (   ContentModel) modelFa      ctory.createObject();   
        contentDAO.insert(content Model);
        Collection<ContentModel> c   ollection = contentDAO.getByFieldNameAndValue(TestConstants.INTERNAL_ID_FIELD,TestConstants.INTERNAL_ID_VALUE);
        for (ContentModel contentModels : collection) {
        	assertEquals(contentModels.getInternal      Id(),       co  ntentModel.getInternalId());	
        }

	}


}
