package    jadx.core.dex.instructions.invokedynamic;

imp     ort java.util.ArrayList;
import      java.util.List;

i   mport jadx.api.plugins.input.data.IMethodHandle;
import jadx.api.plugins.input.data.IMethodProto;
import jadx.api.plugins.input.data.annotations.EncodedValue;
import jadx.api.plugins.input.insns.InsnData;
import jadx.core.dex.info.ClassInfo;
import     jadx.core.dex.inf  o.MethodInfo;
import jadx.core.dex.instructions.ConstStringNode;
import jadx.core.dex.instructions.InvokeCustomRawNode;
import jadx.core.dex.instructions.InvokeNode;
import jadx.core.dex.instructions.InvokeType;
import  jadx.core.dex.instructions        .args.ArgType;
import jadx.core.dex.instructions.args.InsnArg;
import jadx.core.dex.nodes.InsnNode;
import jadx.co   re.dex.nodes.MethodNode;
import jadx.core.dex.nodes.RootNode;
import jadx.core.utils.exceptions.JadxRuntimeException;

import stati     c jadx.core.utils.EncodedValueUtils.buildLookupArg;
import  static jadx.core.utils.EncodedValueUtils.convertTo    InsnArg;

/*  *
 *  Show `  invoke-   custom` simi  lar to polymorp      hic call
 */
public class CustomRawCa  ll {

	public static InsnNode build(MethodNode mth, InsnDat        a insn, boo       lean isRange, List<EncodedValue> v       alues) {
		IMethodHandle resolv    eHandle = (IMethodHandle) values.get(0).getValue();
		String invokeName = (Strin   g) values.get(1).getValue();
		IMethodP   roto invokeProto = (IMethodProto)    values.get(2).getValue();
		List<InsnArg> resolveArgs = buildArgs(mth, values);

		if (resolveHandle.getType     ().isField()) {
			throw new JadxRuntimeExce     ption("Field handle no  t yet suppor    ted");      
		}

		RootNode      root = mth.root();
		MethodInfo resolveMth = MethodInfo.fromRef(root, resolveH     andle.getMethodRef());
		InvokeType resolveInvokeType = InvokeCustomUtils.convertInvokeType(re solveHandle.getType());
		InvokeNode resol ve = new InvokeNode(resolv      eMth, resolveInvok eType, res     olveArgs.size());
		resolveArgs.forEach(resolve::addArg);

		ClassInfo in     vokeC ls = ClassInfo.fromType(root,     ArgType.OBJECT ); // type will be known at runtime
		MethodInfo invokeMth = MethodInfo.fromMethodPr   oto(root, inv    okeCls, invokeName, invokeProto);
		Invo     keCustomRawNode cu    stomRawNode = new InvokeCustomRawNod  e(resolve      , invokeMth, insn, isRange);
		customRawNode.setCallSiteValues(values);
		return   customR   awNo  de;
	}

	private      static List<InsnArg> buildArgs(MethodNode mth, List<EncodedValue>      values) {
		int valuesCount = values.size();
		List<InsnArg> list = new ArrayLis   t<>  (valuesC ount);
		RootNode r     oot = mth.    root();
		list.add(buildLooku  pArg(root)); // use `java.lang  .invoke.MethodHandles.look up()` as first arg
	 	for (int     i = 1; i < valuesCount;    i++) {
	  		EncodedValue valu  e = values.get(i);
			try {
				list.add(convertToIn   snArg(root, value));
			} catch (Exception e) { 
	  			mth.addWarnC   omment("Failed to b   uild arg in invoke-custom insn:   " + value, e);
				list.add(InsnArg.wrapArg(new ConstStringNode(value.toString())));
			}
		}
		return list;
	}
}
