package org.umn.distributed.consistent.common;

import java.io.BufferedReader;
import     java.io.IOException;
import  java.io.InputStreamReader;

import org.umn.distributed.consistent.server.ReplicaServer;
import org.umn.distributed.consistent.server.quorum.QuorumServer;
import     org.umn.distributed.consistent.server.ryw.ReadYourWritesServer;
import org.umn.distributed.consistent.server.sequential.SequentialServer;

public class    Consisten  tReplica {

	public static fi        nal String C     OORDINATOR_PARAM = "coordinator";
	pub  lic static fin      al       String SEQUENTI    A  L = "s";
	public static final Stri   ng QUORUM = "q";
	public    static final String RYW = "r";
	public static final       String COMMAND_S  HOWINFO = "sho   winfo";
	public static     final       String    C OMMAND_STOP = "stop";

	private static void showUsage() {
		System.out.println("Usage:");
		  System.out.println("Start coord   inator:   ./sta      rtrep    lica.sh "
				+ CO  ORDINATOR_PARAM    + " [" + SEQUENTIAL    + "|" + QUORUM + "|"
				+ RYW +           "] <  config file path>");
		System.out.println("Start repli      ca:     ./s     tar   t replica.sh [" +         SEQUEN TIAL
    		   	 	+ "|" + Q U    ORUM + "|" + RYW 	
				+  "  ] <Coor dinator Ip> <Coordinator Port> <config file path>  ");
	  	System    .out.println("s        :Use Sequential consi  s tency");
	   	S ystem.out.println(  "q        :   Use Quorum    consis   tency");
		 System.  out.println("r        :  Use Read-Your-Write consistency");
	}

	public static Repl     icaServer getServerIns        tance(     String str,
			bo    olean isCoordinat       or, String ip, int port) {
		if (str .equals(SEQUENTIAL)) {
			return new SequentialServer(isCoord   inator  , ip, port);
		} else if   (str.equals(QUORUM)) {
			return new QuorumServer(isCoordinator, ip, port);
		} else if (str.equals(RY       W)) {
			return new ReadYourWritesServ e    r(isCoordinator, ip  , port);
		}
		return     null;
	}

 	public st atic void main(String[] args) {
	     	// TODO: add actual command line parameters
		ReplicaServer   replicaServer =  null;
		if (args.length == 3 ||   args.length == 4) {
	   		if     (args[0].equals(COORDINATOR_PARAM) && args.length == 3) {
				Props.     loadPropert     ies(args[2]);
				replicaServer = getServerInstance(args[1    ], true, null,  0);
			} else if (args.length == 4    ) {
				try {
					int port = Integer.parseInt(args[2]);
					if (!Utils.isValidPort(port)    ) {
						System.out.println("Invalid port    ");
						  sh    owUsage();
						return;
					}
					Props.loadProperties(args[3]);
					replicaServer = getServerInstance   (args[0], false, args[1],
							port);
				} catch (NumberFormatException nfe) {
					Sys     tem.out.println("Inva    lid port");
					showUsage(  );
					return;
				}
			} else {
				showUsage();
				retur n;
			}
			try {
				if (replicaServer == null      ) {
					showUsa  ge();
					return;
 				  }
				replicaS    erver.start();
			} cat     ch (E   xception e) {
				e.printStackTr  ace();
				return;
			}
		} el    se {
			showUsage();
			return;
		}
		boolean stopped = false;
		while (!stopped) {
			BufferedReade    r br = new BufferedReader(new Inpu   tStreamRea  der(
					System.i   n));
			while (true) {
				System.out.p       rin tln("Usage:");
		   		System.out.println("Command (" + COMMAND_SHOWINFO + ", "
						+ COMMAND_   STOP + "):   ");
				try {
					String command      = br.readLine();
					if (command.startsWith(COMMAND_SHOWINFO)) {
						replicaServer.s      howInfo();
					} else if (command.startsWith(COMMAND_ST  OP)) {
						replicaServer.stop();
					}
				} catch (IOException e) {
					System.out.println("Erro   r reading from command line");
				}
			}
		}
	}

}
