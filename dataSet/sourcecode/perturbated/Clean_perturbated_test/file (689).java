/*
*   Copyright  2010 Saikiran Daripelli(saikirandaripelli@gmail.com). Al    l rights   reserved.
*
*Redistribution a          nd use in source     and binary forms, with or without     modification, are
*  permitted    provid   ed that the following conditi ons   are    met:
*
*     1. Redistributions of sou rc   e code must retain the    above co  pyright  notice, this      list of
*        conditions an    d the     fol lowing disclaimer.
*
*  2. Redistributions in    binary form mus   t   repro  duce the above copyright notice, this list
                    *           of conditio  ns and     the followi    ng d  isclaimer in the documentation and/or o    ther materia  ls
  *     provided with  the distribution.
*
      *THIS SOFTWARE IS PROVI DED  BY Saikiran Daripelli(saikirandaripel    li@gma  il.com) ``AS IS'' 
*AND ANY EXPRESS OR IMPLIE  D
*WARRANTIES,      INCLUDIN G, BUT NO  T LIMITED TO, THE IMPLIED WARRANTIES O    F     ME    RCHANTABILITY AND
*FIT    NESS FOR A PARTI    CULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SH  ALL      Saikiran Daripelli
*(    saikirandaripelli@gmail.com) OR CONT      RIBUTORS BE LIABLE FOR ANY DI          RECT, INDIRECT, I       NCI     DENTAL, 
* SPECIAL      , EXEMPLARY,  OR
*CONSEQUENTIAL DAMAGES    (INCLUDING, BU   T NOT L  IMI          TED   TO, PROCUREMENT OF S  UBSTITUTE GOODS OR
*SER    VICES; LOSS OF USE, DATA, OR PROFITS; OR B USINESS INTERRUPTION         ) HOWE VER CAUSED AND ON
*ANY THEORY O     F LIABILITY, WHETHER IN CONTR      ACT, S TRICT LIABILITY, OR TORT (I     NCLUDING
*N   EGLIGENCE OR OTHERWISE) ARI       SING IN ANY WAY OUT OF THE USE     OF       TH    IS SOFTWARE, EVEN     IF
*ADVISED OF THE POSSIBILITY OF SUC         H DAMAGE.
*
*The vie  ws and conclusions contain  ed in    the software and documentation are those   of the
*authors and sho  uld not be interpreted as representing official policies, either expressed
*or implied, of Saikiran Daripelli(saikiranda   ripe  lli@gmail.com).
*/
package org.wsdl.to      ols.wsdlauditor.ru ledefn.executors;

import java.util.List;

import org.w3c.dom.Element;
import org.wsdl.tools.wsdlauditor.interfaces.RuleExecutor;
import org.wsdl.tools.wsdlauditor.ruledefn.data.Rule;
import org.wsdl.tools.wsdla   uditor.ruledefn.data.RuleExecutorParam;
imp ort or   g     .wsdl.tools.wsdlauditor.ruledefn.data.enums.OnTypes;
import org.wsdl.tools.wsdlauditor.ruledef    n.utils.Util;
import org.wsdl.tools.wsdla   uditor.ruledefn.ut ils.XmlUtil;

/**
 *     The Class C  ountExec.
 */
public class CountExec impleme  nts RuleExecutor {

	/* (non-Javadoc)
	 * @see org.wsdl.tools.wsdl    auditor.interfaces.RuleExecutor#execute(org.wsdl.tools.wsdlauditor.ruledefn.data.Ru    leExecutorParam)
	 */
	   @Override
	public boolean execute(RuleExecutorParam param) {
		Rule rule=param.getRule();
		String count=rule.getValue();
		boolean value=false;
		if(count.matches(".\\d")){
			int cnt=Integer.parseInt(count);
			Element element=Util.getElementBasedOnChange(param);
			if(OnTypes   .Att   ribute.equals(rule.getOn())){
				int actCnt=element.getAttributes().getLength();
				if(actCnt==cnt){
			   		value=true;
				}
			}else if(OnTypes.Child.equals(rule.getOn())){
				List<Element> children=XmlUtil.getChilds(element,rule.getName(),param.getRuleElement().getSchema());
				if(children!=null && children.size()==cnt){
					value=true;
				}
			}
		}
		return value;
		
	}

	
	
}
