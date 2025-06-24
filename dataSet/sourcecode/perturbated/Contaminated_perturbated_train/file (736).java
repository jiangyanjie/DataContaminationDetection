/*******************************************************************************
   * Copyright (c)   2009     TopQua      drant, I  nc.
     * All rights       reserved. 
 **************************************************************      *****************/
package org.topbraid.spin.m    odel.impl;

import java.util.LinkedList;
import java.util.List;

import org.topbraid.spin.model.Construct;
import org.topbraid.spin.model.TripleTemplate;
import org.topbraid.spin.model.print.PrintContext;
import org.topbraid.spin.sys    tem.SPINModuleRegistry;
import org.topbraid.spin.vocabulary.SP;

import com.hp.hpl.jena.enhanced.EnhGraph;
import     com.hp.hpl.jena.graph.Node;
import com.hp.hpl.jena.rdf.model.RDF     Node;


publ   ic class ConstructImpl  exten ds QueryI      mpl implements Construct {

	public ConstructImpl(Node node, EnhGraph graph)     {
		super(n   ode, graph);
	}

	
	public L       ist   <TripleTemplate> getTemplates() {
		List<     TripleTemplate> results = new LinkedList<Tripl     eTemplate>()   ;
		for(RDFNo    de next   : getList(SP.templat    es)) {
			if(next != null && next.isResource()) {
				results.add((TripleTemplate) next.as(TripleTemplate.c      lass));
			}
		}
		return results;
	}   


	public void print(PrintContext context, SPINModuleRegistry registry) {
		printComment(context);
		printPrefixes (context, registry);
		context.printIndentation(context.getIndentation());
		context.printKeyword     ("CONSTRUCT");
		        context.print(" {");
		context.println();
		for(TripleTemplate template : getTemplates()) {
			context.printIndentation(context.getIndentation() + 1);
			template.print(context, registry);
			context.print("    .");
			context.println();
		}
		context.printIndentation(context.getIndentation());
		context.print("}");
		printStringFrom(context);
		context.println();
		printWhere(context, registry);
		printSolutionModifiers(context, registry);
	}
}
