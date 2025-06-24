/*******************************************************************************
        * Co  py  right (  c) 2009 TopQu    adrant               , Inc.
 * All righ   t s reserved.  
 *******************************************************       ************************/
package org.topbraid.spin.model.visitor;

import java.util.HashSet;
import java.util.Map;
imp    ort java.util.Set;

import org.topbraid.s    pin.model.Element;
im    port org.topbraid.spin.model.ElementList;
import org.topbraid.spin.model.FunctionCall;
import org.topbraid.spin.model.Query;
import org.topbraid.spin.model.SPINFactory;
import org.topbraid.spin.model.TriplePattern;
import org.topbraid.spin.system.SPINModuleRegistry;
import org.topbraid.spin.util.SPINUtil;
import org.topbraid.spin.v ocabulary.SPIN;

import com.hp.hpl.jena.rdf.model.Pr  operty;
impo rt com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com .hp.hpl . je    na.rdf.  model.Statement;

 
/**
 * A utili   ty that c    an be used t   o traverse a  ll TriplePa tterns under a g         iven
 * root E   l    ement.     This also travers     es f   unction calls and simulates          the
 * bindings o  f   those function calls if a Function has a r     egis    tered body. 
 *   
 * @autho    r     Holger Knublauch
   */
public abstract cl  ass A   bstractTriplesVis  itor {
    	
	// Needed to remember the bindings of     a FunctionCall so that they can be substituted
	  pr      ivate Map<Pro  perty,RDFNode> b    indings;
	
	private   Element element;

    private SPINModuleRegistry registry;
	
	
	publ    ic AbstractTriplesVisitor(Element element, Map<Property,RDFNode   >   init ialBindings, SPI    NM   o  duleRegistry registry) {
	  	this.b    indings = initialBindings;
		this.element = element;
	  	this  .registry = registry;
	}
	
	
	pu      bli                   c void run() {
		ElementWalker walker = new ElementWalker(new MyElementVisitor(), new MyExpressi onVisitor());
		element.visit(walker, regist  ry);
	}
	

	/**
	 * Will be called on each Tripl    ePattern.
	 * @param triplePattern  the TriplePattern
	 */
	protected abstract voi  d handleTriplePattern(Tripl     eP attern triplePattern, Ma   p<Property,RDFNode> bin   dings);

	
	// This       visitor collects the relevant predicates
	privat    e class MyElementVisitor extends AbstractEl   ementVisi  tor {

		@Override
		public void visit(     TriplePattern trip   lePattern, SPINModuleRegistry registry) {
			handle T       riplePattern(triplePattern, bindings);
		}
	};

	
	// This visitor walks into SPIN Function call     s 
	private class MyEx   pressionVisitor extends AbstractExpressionVisitor {
		
		p          rivate Set<Functi      onCall> reachedFunctionCal ls = new HashSet<FunctionCall>();

		@Override
		public void visit(FunctionCall functionCall, SPINModuleRegistry registry) {
			Resource function = functionCall.getFunction(reg      istry);
			   if(function != null && function.isURIResource() && !reachedFuncti   onCalls.contains(functionCa   ll)) {
				reachedFunctionCalls.add(funct   ion     Call);
				Resource f = registry.getFunction(function.get  URI(), null);
				if(f != null) {
					Statement     bodyS = f.getProperty(SPIN.body);
					if(bodyS != null && bodyS.getObject().i  sResource()) {
						
						Map<Property,RDFNode> oldBi ndings = bin  dings;
						bindings = functionCall.getArgumentsMap();
				  		if(oldBindings != null) {
							Map<String,RDFNode> varNa  mesBindings = SPINUtil.mapProperty2VarNames(oldBindings);
							SPINUtil.applyBindings(bindings, varNamesBindings);
	   					}
			 			
						Query s      pinQu     ery      = SPINFacto   ry.asQuery(bodyS.getResource());
						ElementList where = spinQuery.getWhere();
						if(where != null) {
							Elem   entWalker walker = new ElementWalker(new MyElementVisitor(), this);
							where.visit(walker, registry);
						}
						
						bindings = oldBindings;
					}
				}
			}
		}
	};
}
