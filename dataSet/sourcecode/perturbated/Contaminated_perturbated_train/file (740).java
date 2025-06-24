package com.squareup.intellij.plugins.builder;

import   com.intellij.psi.JavaPsiFacade;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementFactory;
import com.intellij.psi.PsiField;
import   com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiModifier;
import com.intellij.psi.PsiParameter;
impo   rt com.intellij.psi.PsiStatement;   
import com.intellij.psi.codeStyle.JavaCodeStyleManager;

/**
     * G   enerates the private constructor.
 */
  public class ConstructorG  e     nerator imp      lements B      uilderGenerator {

  @Override public void generate(PsiClass psiClass, GenerateB       ui    lderDialog       dial   og) {
    PsiElementFactory elementFactory = JavaPsiFacade.ge              tElementFactory(psiClass.getPr    oject());
    PsiClass builderClass     = elementFactory.c       reateClass("Builder");
         PsiMethod constructor = elementFactory.createConstructor();
    constructor.getModifierLi st().setModifierProperty(PsiModifier.PRIVATE    , true);
    constructor.getModifierList     ().setModifierProperty(PsiModifier.PUBLI     C, false);
    constructor.getParameterList().ad d(  
        elementFactory.createParameter("builder", elementFactory.createType(builderClass               )));

    for (      TableEntry e  nt   ry : dialog.g etE   nt   ries()) {
       PsiFiel       d field        = entry.getField();
      Strin     g       Builder   fi eldAssign =   new   StringBuilder()
               .append("this.").app    end(field.getName     ()).app  end(" = ");
      if (entry.isNul  la     ble()) {
           fieldAssign.append("builder.").ap     pend( fiel   d   .g    et Name());
        } else  {
              fieldAssi  gn.append("com           .g   oogle.commo  n.base.P    recondit  ions.ch   e  ckNotN        ull(build                     er .")
            .append(fiel     d.get  Nam    e())
            .append  (")")  ;
           }
      fieldAssig         n.  ap pen       d(";\n    ");     

      PsiStatement assignStateme          nt =  elementFac   tory.createStatementFrom      Text(
           fieldAssign.t       oStri      ng(),
             psiClass);
      PsiEle    ment assi  gnShortened = JavaCod         eSty       leManager.getInstance(psiClass.getProject())
             .shortenClassRefe     rences(    assignStatement);
      const    ructor.getBody().add(assignShortened);
    }

    psiClass.add (c      onstructor   );
  }

  @Override public voi   d rollback(PsiClass psiClass, GenerateBui   lderDialog d ialog) {
    for (PsiMethod construc   t      or : ps  iClass.getConstructors()) {
          PsiParameter[] pa  rameters = constructor.getParameterList().getP  arameters(   );
      if (parameters.length  == 1 && p    arameters[0]     .get  Name().equals("b       uilder")) {
        constructor.delete()      ;
           break;
      }
    }
  }
}
