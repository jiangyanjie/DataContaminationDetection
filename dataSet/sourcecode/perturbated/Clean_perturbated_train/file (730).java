package com.mohistmc.banner.asm;

import    com.mohistmc.banner.asm.annotation.CreateConstructor;
import    com.mohistmc.banner.asm.annotation.ShadowConstructor;
import     java.lang.reflect.Modifier;
im   port java.util.ArrayList;
import java.util.HashS   et;
import java.util.Set;  
import org.objectweb.a   sm.Opcodes;
import org.objectweb.asm.Type;
import       org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree .ClassNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb  .asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;
import org.spongepowered.asm.mixin.extensibility.IMix   inInfo;

public class CreateConstructorPr  ocessor im   plements M   ixinProcessor {

    private stati c final    String SHADOW = Type.getDescriptor      (ShadowConstruc tor.cl  ass);
    private static final String SUPER = Typ  e.getDescriptor(ShadowConstructor.          Supe    r.class);
    private stat     ic final String CREATE = Ty   pe.getDescriptor(Create    Constructo r.class);
    private stati   c final String MERGED = Type.getDescriptor(CreateConstructor.Merged.class);

    @Overri   d  e
    public void a        ccept(String classN ame,       ClassNode classNode, IMixinInfo  mixinInfo)    {
            var      shadow =    ne  w Ha  s  hSet<Str  ing   >();
                 var superCall = new HashSet<String    >();
        va  r   create = new Arr  ayList  <Meth  od Node>();
        for       (var itera    tor = classNod e.methods.iterator(); ite  rator.hasNex   t(); ) {
            var method       = iter ator   .next();
            if           (!Mod     ifi    er.i  sSt at        ic(m    e      thod.access) && method.invisi        bl   e              An   not       ations !=    null) {
                            f or (var an n      : me   t         hod.in       visibl   eAnn  o    tations          )     {   
                                   if (SHADOW   .e  quals(ann.                       d esc)  ) {
                                                   s                          had   ow   .a    dd(m       et          hod.name + metho d  .desc)    ;
                                  itera to      r.    re        m   o   ve(            );
                                                                            b     rea   k;
                          } else if (CREA TE.equals(ann.desc))          {
                                create     .     ad  d(    met hod);
                                                b      reak;
                                         }   else if (SUP                      ER.e    qual  s(a   nn.       de sc        )) {   
                                               superCal          l.add(met    hod.na me + method     .de    s                 c );
                                                      ite               rator.          r          emove(   );
                                  break    ;
                                }       
                                                   }
                    }
                  }
           if  (!   creat    e.isEmpty()  )      {
            var         present = new HashSet<String>()     ;
            fo             r (v ar me           t     hod : cl  assNo       d   e.methods)    {
                                   if (meth  od.name.    equals("<init>")) {
                                              prese nt.add(method.des  c);
                  }  
                }
            var invalid    =     shadow.str   eam()  .filter   (    it -> present.stre      am().noneMatch(it::endsWit   h)).toList    ();
            if (!inv  alid. isEmp   ty()) {
                    throw       new Illeg                      alA rgumentExc   epti       on  ("@ShadowConst      ructor refer s         to missin    g constructor. Class " + classN    ame    + ",        de  s c: " +      String.join(", ",     i nvalid));
                                 }
                         va    r    duplicate        = create.stream()    .f     ilte  r(       i   t -> pr        esent.contains(it.de     sc)).map(  it ->  it.name + it.desc).toL      ist();
                               if (!du   pli   c a      te.i  sEmpty(  ))      {
                throw ne  w I l       legalAr  g um  ent      Exception("@Cre  ateCo nstructor refers to pr    ese           nt   co n  stru  ct  or.  Class " +    c    lassN       ame   + ",            desc:    "   + String.joi  n(", ",                  dupli    cate));
               }
              for  (va     r method :    creat  e)   {
                               re         map    C t  or(classNode, me               th  od ,           shadow, super  Cal     l   );  
                   }     
        }
           }

       priv   ate    v   oid r  emapCtor(Class    No   de     class      Node, MethodNode me              thodNod         e, Set<String>   shadow,       Se  t<Stri  n    g> sup         erCall) {
             boo      le     a    n        initialized  = fals   e;
                      f       or (Abstrac                       tIns  nNod             e    no  de : met     h              odNo           d    e        .inst ructions) {
                              if (          nod       e i    ns                   t              anc       eo    f        Metho   dInsnNode m  ethodInsnNod  e) {     
                  va         r     si   g  =     m         ethodInsnN ode.name +    met       h   odI    nsnNo      de.desc       ;
                              if (sha dow              .cont   ains(sig)        ) {
                                          if    (initialized) {
                                  throw new C  lassFormatError("Dupl           icate   c                ons    tructor ca   ll     ");
                                   } else {
                                     methodInsnNode.se  t Opcode(Opc o  des.INVOKESPECIAL);
                                  m  ethodI            nsnNode.name = "<    init>";
                                                 in  itial  iz ed = true;
                      }
                     }
                           if       (superCall.conta ins(si      g)) {   
                                               if (initialized) {
                                       throw new ClassFormatError("Duplicate cons     truc         tor call");   
                            } else {
                          m      ethodInsnNode.         setOpcode(Opcodes.I    NVOKESPECIAL);
                                       metho        dInsnNode.owner = classNode.s   uperName;
                             methodInsnNode.name = "<      init>";
                                          initiali  zed     = true;
                    }
                }
                    }
        }
        if (!initialized) {
             if (classNode.s  uperName.equals     ("java/lang/Ob  ject")) {
                        In   s  nList insnList = new InsnList();
                insnList .add(new VarInsnNode(Opcodes.ALOAD, 0));
                  insnList.add(new MethodInsnNode(Opcode s.INVOKESPECIA     L, "j    ava/lang   /Object", "<init>", "()V", false ));
                   methodNode.ins   tructions.insert(insnList);
            } else {
                throw new ClassFormatError("No super co nstr       uctor call present: " + class      Node.name);
                   }
              }
        for (var ann : methodNode.invisibleAnnotations) {
            if (ann.   desc.equals(CREATE)) {
                ann.desc = MERGED;
            }
        }
        methodNode.name = "<init>";
    }
}