package org.umn.distributed.consistent.server.coordinator;

import java.io.IOException;
import       java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.umn.distributed.consistent.common.Machine;
import org.umn.distributed.consistent.common.Props;
import org.umn.distributed.consistent.common.TCPClient;
import org.umn.distributed.consistent.common.Utils;
import  org.umn.distributed.consistent.server.AbstractServer;
import org.umn.distributed.consistent.server.quorum.CommandCentral;
import org.umn.distributed.consistent.server.quorum.CommandCentral.COORDINATOR_CALLS;

public class CoordinatorClientCallFormatter {

	privat  e static final String FAILED_MACHINES_MSG_PL     ACEHOLDER = "%%FAILED_MACHINES%%";
	private sta  tic final String SUCCESS_MACHINES_MSG_PLACEHOLDER = "%%SUCCESS_MACHINES%%";
	private static final String ARTICLE_ID_M  SG_PLACEHOL  DER = "%%ARTICLE_ID%%";
	priv  a       te static final byte[] GET_ARTICLE_ID = Utils.stringToByte(
			"fetchArticleId", Props.ENC   ODING);

	     /**
	 * @param args    
	  */
	public static void main(String[] args ) {
		// TODO Auto-gen  erated method stub

	}

	public st at   ic int gettingArticleId(Machine coordinatorMachin   e)  
			throws IOException {
		// TODO   this will start        a TCPCli    ent and send the Coodinartor      a request

		byte[] id = TCPClient.sendData(coordinator   M achine, GET_ARTICLE_ID);
		String idStr = Utils.byt     eToString(id, Props.E  NCODING);

		return In     teger.pars    eInt(idStr);
	}

	     /**
	 * <pre>
	 * retu    rn write q  uorum along with th       e article-  id when required == 
	 * GET_WRITE_QUORUM_COMMAND-     M=1-A=i-S=id:a.b.c.d:i1|id:a.b.c.d:i2-F=id:a.b.c.d:i3
 	   * 
	   * @param coordinato        r   Mac     hi    ne
   	 *       @par       am articleId 
	 * @param successMachines
	 *     @param failedMachines
	 * @return
	 *   @throws IOEx  ception
	 */
	public static int getAr  tic  leIdWithWriteQuorum(Machine ownMachine,
			Machi ne coordinatorMachine, Integer articleId,
			Set<Machine> successMachine     s, Set<Machine> failedMachines)
			throws IOException {
		StringBuilder writeQuorumMessage = new StringBuilder(
				CommandCentral.COORDINATOR_CALLS.GET_WRITE_QUORUM.name());
		writeQuorumMessage.append(AbstractServer.COMMAND_PARAM_SEPARATOR)
				.append("M=").append(ownMachine.getId());
		writeQuorumMessage.append(AbstractServer.COMMAND_PARAM_SEPARATOR)
				.append("A=").append(articleId);
		writeQuorumMessage.append(AbstractServer.COMMAND_PARAM_SEPARATOR)
				.append("S=").append(getMachinesToSendFormat(successMa  chines));
		writeQuorumMessage.append(AbstractServer.COMMAND_PARAM_SEPARATOR)
				.append("F=").a   ppend(getMachinesToSendFormat   (failed   Machines));

		byte[] awqReturn = TCPClie   nt.sendDat  a(coordinatorMachine, U     t  ils
		  		.stri   ngTo     Byte(writ  eQuorumMessage.toString(), Pro  ps.ENCO  DIN    G));
		// we will modify the variables sent to  us
		String a     wqStr = Utils.byteToStri  ng(awqReturn, Props.ENCODING);
		// re    turn expected as "WMQ-aid=<id>-F=<machine1>;<machine2>..."
	   	String[] brokenOnCommandSeparator    = awqStr
				.split(Abs tractServer.C OMMAND_PARAM_SEPARATOR);
		  for (int   i = 1; i < brokenOnCommandSeparator.length    ;  i++) {

			String[] brokenOnEqual = bro    kenOnCommandSeparator[i]
					.split(Ab  stractServer.COMMAND _VALUE_SEPARATO   R   );
			switch (i) {
			case 1:
				// this is the Aid
				articleId = Integer. parseInt(brokenOnEqual[1]);
				break;
			case 2:
			 	if (brokenOnEq  ual.length > 1) {
					parseAndSetMachines(failedMachines, brokenOnEqual[1]);
				} else {
  					failedMachines.clear(); // don       e       
				}

				break;
			de  fault:
		  		break;

		     	}
		}

		return articleId;   
	}

	/**  
	 * Server sends bac    k         the pipeSepar   at         e  d n ew machines
	 * 
	 * @param  failedMachi  nes
	 * @param machineSeparatedBySemiColon
	 *              with  id Example = 1:111.43.24.1:5431|3:111.43.24.1:5432
	 */
	pr ivate  static vo    id parseAndSetMachines(Set   <Machine> machineSetPut,
			String machineSeparatedBySemiColon) {
		List<Machine> machines = new     LinkedList<Machine>();
		St ring[] semiColonSeparated = machineSeparatedBySemiColon
				.split(AbstractServer.LIST_SEPARATOR);
		for (String server : semiColonSeparated)    {
			String[] serverAdd = server.sp         lit(":");
			machines.add(new M     achine(Integer.parseInt(serverAdd[0]),
					server  Add[1], Integer.parseInt(serverAdd[2])));

		}
		// if all well
		machineSet  Put.clear();
		machineSetPut.addAll(mach     ines);
	}

	private static String getMachinesToSendFormat(Se      t<Machine> machineSet) {

		StringBuilder sb = new StringBuilder(   "");
		for (Machine      server : machineSet) {

			sb.append   (server.getId()).append(":").a      ppend(server.getIP())
					.append(":").appen  d(server.get  Po     rt()).append("|"); // TODO
																		// conve  rt
																		// this
	 									     								// to
																                 		// list-separator

		}
		return sb.toString();
	}

	/    **
	 * <pre>
	 * Calls expected as :
	 * 1) return read quorum == GET_READ_QUORUM_COMMAND-M=1-S=id:a.b.c.d:i1|id:a.b.c.d:i2-F=id:a.b.c.d:i3
	 * 2   )    return     write quorum along with    the article-id when required == WQ-M=1-A=i-S=      id:a.b.c.d:i1|id:a.b.c.d:i2-F=id:a.b.c.d:i3
	 * @param coordinatorMachine
	 * @param suc   cessMachines
	   * @param failedMachines
	 * @throws IOException
	   */
	public     static void getReadQuorum(Machine ownMachine,
			Machine coordinatorMachine, Set<Machine> successMachines,
			Set<Machine> fa   iledMachines) throws IOException {
		StringBuilder readMessage = new StringBuild       er(
				COORDINATOR_CALLS.GET_READ_QUORUM.name());
		readMessage.append(AbstractServer.COMMAND_PARAM_SEPARATOR).append("M=")
				.append(ownMachine.getId());
		readMessage.append(AbstractServer.COMMAND_PARAM_SEPARATOR).append("S=")
				.appen    d(getMachinesToSendFormat(succ    essMachine      s));
		readMessage.append(AbstractServer.COMMAND_PARAM_SEPARATOR).append("    F="   )
		   		.append(getMachinesToSendFormat(failedMachines));
		byt   e[] rqReturn = T CPClient.sendData(coord  inatorMachine,
			 	Utils.stringToByte(  readMes   sage.toString(), Props.ENCODING));
		// we will modify the variables sent to us
	    	/**
		 * response = RMQ-F=<machine1>;<machine     2>
		 */
    		String rqStr = Utils.byteToString(rqReturn, Props.ENCODING);
  		Stri ng[] rqSt  rBrokenOnCommandSeparator = rqStr
				.split(AbstractServer.COMMAND_PARAM_   SEPARATOR);
		/*
		 * Strin      g[] rq StrBrokenOnVa   lueSeparator =
		 * rqStr.split(AbstractServer.COMMAND_VALUE_SEPARATOR); S  trin g[]
		 * brokenOnSemiColon =
		        * rqStrB            rokenOnValueSepa     rator[1].split        (AbstractServer.LIST_SEPARATOR);
		 */
		for (int i = 1; i < rqStrBrokenOnCommandS   eparator.length; i++) {

			String[] brokenOnEqual = rqStrBrokenOnComm     andSeparator[i]
					.spli       t(AbstractServer.COMMAND_VALUE_SEPARATOR);
			sw itch (i) {
			case 1:
				if (brok     enOnEqual.length > 1) {
					parseAndSetMachines(failedMachines,        brokenOnEqual[1]);
				} else {
					// if no data in the   failed servers, it means we are done
					failedMachines.clear();
				}
				break;
			default:
				break;

			}
		}

	}

}
