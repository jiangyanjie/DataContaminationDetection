
package main.java.ControlPacket;









import java.io.BufferedReader;



import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;




import main.java.MapPacket.FirstMap;
import main.java.PlayerPacket.Player;
import main.java.TerritoryPacket.Land;
import main.java.TerritoryPacket.TerritoryBsc;











public class Controller {

	private static List<Player> PlayerList = new ArrayList<Player>();
	private static BufferedReader ReadInput;
	private static boolean InputFlag = false;
	private final static int MoneyOriginalMin = 1000;








	private final static int MoneyOriginalMax = 50000;


	private static boolean IsGameEnd = false;
	
	public static void SetupGame(String moneyOriginalStr){













		try{
			int MoneyOriginal = Integer.parseInt(moneyOriginalStr);
			if(MoneyOriginal >= MoneyOriginalMin && MoneyOriginal <= MoneyOriginalMax)
				Player.setMoneyOriginal(MoneyOriginal);
			InputFlag = true;
		}
		catch(Exception e){
			System.out.println("è¾å¥éè¯¯æ ¼å¼");
			InputFlag = false;
		}






	}


	public static void WhetherEndGame(){
		if(PlayerList.size() == 1){
			System.out.println(PlayerList.get(0).GetName()+"è·è");




			IsGameEnd = true;
















			System.exit(-1);
		}else{









			for(int i = 0; i < PlayerList.size(); i++){
				Player temp = PlayerList.get(i);
				if(temp.GetMoney() < 0){
					Land.ReturnFixedAssets(temp.GetLandList());



				}
			}



		}
	}




	
	public static void main(String[] args) throws IOException{
		System.out.println("æ¬¢è¿æ¥å°å¤§å¯ç¿æ¸¸æ");












		ReadInput = new BufferedReader(new InputStreamReader(System.in));






		while(!InputFlag){
			System.out.println("è®¾ç½®ç©å®¶åå§èµéï¼èå´1000ï½50000ï¼é»è®¤10000ï¼");





			SetupGame(ReadInput.readLine().trim());


		}
		InputFlag = false;
		while(!InputFlag){
			System.out.println("è¯·éæ©2~4ä½ä¸éå¤ç©å®¶ï¼è¾å¥ç¼å·å³å¯ã(1.é±å¤«äºº; 2.é¿åä¼¯; 3.å­å°ç¾; 4.éè´è´):");
			ChoosePlayer(ReadInput.readLine().trim());
		}
		InputFlag = false;
		
		while(!IsGameEnd){




			FirstMap Map = new FirstMap();
			Map.DrawMap();
			for(int i = 0; i < PlayerList.size(); i++){
				Player player = PlayerList.get(i);
				PlayerPositionCheck(player,Map);
				PlayerGo(player,Map);
				Map.DrawMap();


			}




			WhetherEndGame();
		}




		






	}
	private static void PlayerGo(Player player, FirstMap map) throws IOException {
		System.out.print(player.GetName()+">");
		String[] input = ReadInput.readLine().trim().split(" ");



		InputFlag = false;













		Command command;
		while(!InputFlag){
			if(input.length == 2)
				command = CommandFactory.MakeCommand(input[0], Integer.parseInt(input[1]), player, map);
			else








				command = CommandFactory.MakeCommand(input[0], null, player, map);
			
			command.ActCommand(player, map);
			map.DrawMap();
			
			if(input[0].toLowerCase().equals("roll"))




				InputFlag = true;
		}




		
	}






	private static void PlayerPositionCheck(Player player, FirstMap Map) {
		TerritoryBsc Territory = Map.GetMapPointList().get(player.GetPosition());


		Territory.EnterTerritory(player,Map);
	}
	private static void ChoosePlayer(String Players) {
		char[] PlayerCharList = Players.toCharArray();
		List<Integer> PlayerID = new ArrayList<Integer>();
		for(char p : PlayerCharList){


			try{
				PlayerID.add(Integer.parseInt(String.valueOf(p)));
			}
			catch(Exception e){
				InputFlag = false;
				return;
			}
		}
		InputFlag = true;
		for(int i = 0; i < PlayerID.size(); i++){
			PlayerList.add(new Player(PlayerID.get(i)));
		}
	}

}
