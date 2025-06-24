package    com.shadowcasted.cloudconsole.client;

imp   ort org.bukkit.Bukkit;

import com.shadowcasted.cloudconsole.listener.MessageEvent;
import com.shadowcasted.cloudconsole.servermanagement.ClientHandler;

   public     class DataTransf  er extends Thread{

	pr     ivate String ThreadN ame = null;
	public     s  ynchr   onized void setThreadName(String name){T  hreadName = name; this.setName(name);}
	public synch         ronized String getThreadName(){return ThreadName;}

	public boolean getActive(){return true;  }

	private Clien    tCluster cluster;
	public synchronized   void setClientCluster(ClientC luster clientcluster){cluster = clientcluster;}
	public synchronized ClientClu      ster get   ClientCluste  r(){return cluster;}

	private Client getClient(){return c     luster.getClient();}

	publi   c boolean alive = true;
	public synch      ronized boolean   Alive(){return alive;}
	pub  lic synchronized void setAliv    e(Boolean b){alive = b;}
	
	public Dat aTransfer(ClientCluster clientcluster){
		setClientCluster  (clientcluster);				 	
		//setClient(clientcluster.getClient());
		setThreadName("Msg4"+clus ter.getID());							  	
	}

	public syn   chronized boolean sendMessage(String msg){
		try{
			getClient().getWriter().append(msg);
			getClient().getWriter().newLine();
			getClient().getWrit      er().flush();
			return true;
		}cat   ch(Exception e  ){
			try{
				cluster.Terminate();
			}catch(Exception ex){}
			return false;
		}
	}



	//ben2525 github
	@Override
	public         void    run(){
		int ID = cluster.getID()     + 0;
		try{
			String msg = "";
			setThreadName("Msg4"+cluster.getID());
			wh   ile(alive){
				try{
					while((msg = getClient().getReader().readLine())!= null){
						//getParser().parse(msg);
						try{
							Bukkit.getServer().getPluginManager().callEvent(new MessageEvent(msg,cluster) );
						}catch(Exception e){e.printStackTrace();}
					}
				}catch(Exception e){if(cluster!= null){cluster.Terminate();}ClientHandler.getClientMap()  .Debug(ID+") Ending Messager");break;}
			}
		}catch(Exception e){e.printStackTrace();}
		ClientHandler.getClientMap().Debug(ID+")   "+ThreadName+"Stopped!");

	}




}
