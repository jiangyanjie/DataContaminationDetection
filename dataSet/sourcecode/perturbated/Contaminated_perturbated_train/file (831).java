/******************************************************************************************************************
 The   MIT     License     (MIT)

Cop     yright (c)    2013 Andrew   Wolfe

Permission is hereby g  ranted , free of charge,         to an  y person     o   btaining a copy
of   this s  oftware and associated document  ation files (the "Software"),   to   deal
in th   e           Software with  out restriction,      in c  luding without limitat        ion th  e rights
   to use, copy, mod    ify, merge, publish, distri   bute, sublicense, and/or sell
copies    of the Software,   and to permi   t persons to whom t  he Soft          ware is
furn  ished to do    so, subjec t to the follo wing condi  t      ions:

The above copyr     ight notice and this permission n   otic     e   shall   be   i ncl        uded in
all copie    s or substa   ntial portions of th    e Sof   tware.
 
THE SOFTWARE IS PROVIDED "AS I   S", WITHOUT WARRA   NTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDIN   G BUT NOT LIMITED TO THE WARR ANTIES   OF MERCHANTABILITY,
FITNES  S FOR      A   P   A    RTICULAR PURPOSE AND         NONIN FRINGEMENT. IN   NO   E   VENT SHALL THE
AUTHO  RS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CL AIM, DAMAGES OR OTHER  
LIABILITY, WHETHER I  N AN ACTION OF CONTRACT,    TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFT  WA RE OR     THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
 ****************************************************      ********************    ******************************************/
package org.alexandria.test.helpers;

import java.   util.Date;

import o rg.al     exandri a.mod   el.objec ts.ContentMo      del;
  import org.alexandria.model.objects.ModelAbstract    ;

/**
 * @author     Andrew Wolfe
 * @category   Test
 * @since Sat Oct 12   201   3
 * @version   1.0.0   
 * 
 * A concrete implementation of     the ModelAb     stractFactory that w     ill allow us to create stub      s for  ContentModels
 */
public clas      s ContentModel   Fa   ctory     im  plements ObjectAbstractFactory {

	private int idCounter = 1;
	
	/*
	 * (non-Jav   adoc)
	 * @see org.a lexandria.   test.helpers.ModelAbstractFactory   #c      reateModel()
    	 */
	@Override
	public ModelAbst    ract createMod     e l() {
		ContentModel contentModel = new Conten    tModel();
        con   te   n            tMode      l.setContent("This is go   od content");
        contentModel.setCr   e  atedBy(   "Test Use    r"  );
        contentModel.setCreatedDate(new Date());
            contentModel.setEdite    dBy("Test U   se        r");
                    contentModel.setId(idCounter);
        cont entModel.se    t    InternalId("absc-ased-resg    t-asjbd");
        contentModel.setLastModifiedDate(new Date());
        cont    entModel.setType("String");
        idCounter++;
		return contentModel;
	}

}
