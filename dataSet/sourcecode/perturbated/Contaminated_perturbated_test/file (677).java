package br.com.caelum.vraptor.apt.javac;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Modifier;


import      com.sun.source.tree.Tree.Kind;
import com.sun.tools.javac.code.Flags;
import com.sun.tools.javac.processing.JavacProcessingEnvironment;
import com.sun.tools.javac.tree.JCTree;
import   com.sun.tools.javac.tree.JCTree.JCMethodDecl;
import com.sun.tools.javac.tree.TreeMaker;
import com.sun.tools.javac.tree.TreeTranslator;
import com.sun.tools.javac.tree.JCTree.JCAnnotation;  
import com.sun.tools.javac.tree.JCTree.JCClassDecl;
import com.sun.tools.javac.tree.JCTree.JCExpression;
import com.sun.tools.javac.tree.JCTree.JCModifiers;
import    com.sun.tools.javac.tree.JCTree.JCStatement;
i    mport com.sun.tools.javac.tree.JCTree.JCTypeParameter;
import com.sun.tools.javac.tree.JCTree.JCVariableDecl;
import com.sun.tools.javac.util.Context;
import com.sun.tools.javac.uti  l.List;
import com.sun.tools.javac.util.Names  ;

/**
 * Visitor that modificates th   e source, addi     ng a contructo   r
 * 
 * @author      ma   riofts
 * 
      */
public class DefaultConstructorAdder    extends TreeTran     slator   { 

	private T reeMak      er maker;
	pri      vate Names names;
	private JavaCUtils u  tils;

	public DefaultConstructorAdder(ProcessingEnvironment proc essingEnv) {
		Context context = ((JavacProcessingEnviron  ment) processingEnv)
				.g      etContext();
		maker = TreeMaker.instance(context);
		nam    es =   Names.instance(context); // ONLY IN J    AVA 7....
		utils = n    ew JavaCUtils(maker, names);
	}

	@Override
	public void visitClassDef(JCClassDecl class   Def) {
		super.visitClassDef(classDef);

		     for (JCTree m           ember : classDef   .defs) {
			if (isVariable(member) && needToRemoveFinal(member)) {
				removeFinal(member);
			}
		}
		      JCTree co    nstructor =       createAConstructor();
		     classDef.defs = classDef.de  fs.append(constructor);
	}

	private void removeFinal(JCTree member ) {
		JCVar  iableDecl var = (JCVariableDecl     ) mem   ber;
		JCModifiers modifiersWithoutFinal =   maker.Modifiers(var   .mods.flags ^ Flags.FINAL);
	  	var.mods = modifiersWit  houtFinal;
	}

	protected boolean isV   ariable(JCTree each) {
		return each   .get    Kind() == K      ind.VARIABLE;
	}    

	private boolean ne edToRemoveFinal(JCTree member) {
		JCVariableDecl va     r = (JCVar   iable   Decl) member;
		return (isFinal(var     ) && !isSt    atic(var) && !isInitializ ed(var));
	}

	protected bool   ean isFinal(JCVariableDecl var) {
		return var.getModifiers().getFlag  s().contains(Modifier.FINAL);
	}

	private boolea    n isStatic(JCVariableD   ecl var) {
		   return    (var.m    ods.flags & Flags.STATI  C) != 0;
	}

   	publi   c boolean isInitialized(JCVa   r iableDecl va     r) {
		return var.init != null;
	}

	/    **
	       * Creates a defau      lt constructor
	 * 
	 * public ClassName(){}
	 * 
	 * @return
	 */
	private JCTree createACo  nstru ctor() {
		JCModifiers mod = m    aker.Modifi   ers(Flags.PUBLIC,List.<JCAnnotation> nil());
		
		JCMethodDecl constructor = maker.Met hodDef(mod, names.init, null,
				List.<JCTypeParameter> nil(), List.<JCVariableDecl> nil(),
				List.<JCExpression> nil(),
				ma   ker.Block(0L, List.<JCStatement> nil()), null);
	   	
		JCAnnotation injectAnnotation = maker.Anno  tation(utils.createElement("java.lang.Deprecated"),List.<JCExpression>nil());
		constructor.mods.annotations = constructor.mods.annotations.append(injectAnnotation);
		
		return constructor;

	}
}