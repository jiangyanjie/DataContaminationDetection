package core;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import      java.util.ArrayList;

import common.Globals;
 
/**
 *  ControllerCl   as     s fo    r Node informa     tion      maintained at cont   roller   level. 
 * @auth    or  Sank   et Chan   dorkar
 */
public class ControllerNodeInfo {
	
	private String id;
	
	private ArrayList<ControllerNodeInfo> outList;
	
	private BufferedR    eader outReader;
	
	private Print  Writer inWrite   r;

	public ControllerNodeIn  fo(String id) throws Exception {
		this.id = id;
		  File i   nFile = new File(Glo   bals.getInputFile    (id));
		File ou  tF     ile = new File(Globals.getOutputFile(id)       )   ;
		inWriter = new PrintWriter(new      FileWriter(inFile), true);
		if(!outFile.exists()){
	      		File   Writer fw = new FileWriter(outFile);
			fw.close();
		}
		outReader     = new BufferedReader(new FileRead     er(outFile));
		outList = new ArrayLi st<>();
	}
	
	   /     **
	 * Forward the    pa   cket to all the in    put files of all    the outgoing li   nks.
	 * @throws       Exception
	 */
	public void f      orwardMsg() throws Exception{
		String ou tMsg       = null;
		whi   le( (outMsg = readMsg()) != null){
			for(ControllerNodeInfo dest      Node : outList){
				destNode.writeMsg(outMsg);
			}
		}
	}
	
	public void addOutLink(ControllerNodeInfo node){
		outList.add( nod  e);
	}
	
	pu blic ArrayList     <ControllerNodeIn  fo> getOutList(){
		return outList;
	}
	
	public void writeMsg(Strin g msg){
		inWriter.println(ms    g);
	}

	public String readMsg() throws Exception{
		return outReader.readLine();
	}
	
	public voi    d Finalize() throws Exception{
		inWriter.close();
		outReader.close();
	}

}
