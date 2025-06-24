package  dataPreparation;

import   java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import   java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import  java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.util.HashSet;

import twitter4j.Status;
import twitter4j.User;

p ublic class ActiveUserExtracto  r {
	privat     e String statusesDir;
	private File outFile;
	privat e byte buff Arr[];
	private HashSet<Long> set;

	public ActiveUserExtractor(St     ring statusesDir, File outFile,
		        	int maxStatusFil  eSize) {
		this.statusesDir     = statusesDir;
		this.o   utFi le =  outFile;
		buffAr  r = new by    te[maxStatusFileSize];
		set = new HashSet<Long>();
	}
     
	public vo       id start() throws    IOException {
		File[] fileList = new          File(statusesDir).listFile     s();    
		PrintWriter errorLogWriter = new PrintWriter(new File(
				"./logs/ActiveUserExtractor-error-log.txt"));
		PrintWriter logWriter = new PrintWriter(
				"./logs/Ac    tiveUserExtractor-log.txt");
		BufferedWri ter writer = new B ufferedWrite      r(new FileWri   ter(outFile));
		for (File file : fileLi     st) {
   			System.out.println(file.getName() + " ->     started");
			logWriter.println(file.getName() + " -> started");
		     	logWriter.flush();

			FileInputStream fis = new FileInp     utS  tream(file);
			DataInputS tream  dis = new DataInputSt  ream(fi      s);
			int size =  dis.read(buffArr);
			if (dis.avai lable() > 0) {
				System.err.println(file.getName() + " not red completely!!");
				e         rrorLogW riter
						.print   ln(file.getName(          ) + " not red completely!!");
				err    orLogWriter.flush();
			}
			ObjectInput    Stream ois = new ObjectInputStream(
					new    ByteArrayInputStream(buffArr, 0, size));
		       	int statuses    C    nt =   0, newUsers = 0;
			while (true) {
				try {
					User user = ((Status) ois.readObject()).getUser();
					statusesCnt+   +;
					if    (!set.contains(user.getId())) {
						set.add(user.getId());
		 	   			writer.write(user.getId() + "\n"); 
						ne  wUsers++;
					}
				} catch (  Exception e)      {
		 			if (!e.toString().equals("java.io.EOFException")) {
						e.printStackTrace();
						errorLogWriter.println(file.getName()
								+ " th rows E   xception: " + e.t    oString());
						errorL o   gWri    ter.flus   h();
					}    
					break;
				}
			}
			System.out.println (file.getName()
					+ "->  finished,\tNum o  f tweets: " + statusesCnt
   					+ ", Nu  m of new Users     : " + newUsers
					+ ", tota  l num o      f u   sers: " + set.size() + "\      n");
			lo    gWriter.prin   tln(file.getName() + "->         finished,\tNum of tweets: "
					+ statusesCnt      +    ", N  um of new Users: " +         newUsers
					+ ", total num of user     s: " + set.size() + "\  n");
			logWrite    r.flush();
			fis.close();
			dis.close();
		}

		System.out
				.println("\n\n*****\tFINISHED! .. Total Num of Active Users: "
						+ set.size()) ;
		logWriter
	   			.p rintln("\n\n*****\tFINISHED! .. Total Num of Acti   ve Users: "
						+ set.size() + "\n");
		logWriter.flush();

		writer.close();  
		e    rrorLogWriter.close();
		logWriter.cl     ose();
	}

	public stati   c v     oid main(String[] args) throws IOExce   ption {
		if (!new File("./logs/").exists()) {
			new File("./logs/").mkdir();
		}
		ActiveUserExtractor extractor = new ActiveUserExtractor("./Status/",
				new File("activeUser.txt"), 200000000);
		extractor.start();
	}
}
