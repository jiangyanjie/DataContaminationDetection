package com.edisonyang.pesgoal.data;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.edisonyang.pesgoal.data.bo.Body;
import com.edisonyang.pesgoal.data.bo.Player;
import com.edisonyang.pesgoal.data.bo.PlayerStyle;
import com.edisonyang.pesgoal.data.bo.Speed;
import com.edisonyang.pesgoal.data.bo.Team;
import com.edisonyang.pesgoal.data.bo.Technique;
import com.edisonyang.pesgoal.data.bo.WillPower;
import com.edisonyang.pesgoal.util.CommonUtil;

public class CSVHandler implements IDataHandler {

	private String path;
	private Logger logger = Logger.getLogger(this.getClass());

	public CSVHandler(String path) {
		super();
		this.path = path;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	/**
	 * 通过文件读取球员信息
	 */
	public List<Player> readPlayer() {

		FileReader fileReader = null;
		BufferedReader playerReader = null;
		List<Player> players = new ArrayList<Player>();

		try {
			
			fileReader = new FileReader(path + "player.csv");
			playerReader = new BufferedReader(fileReader);
			String first = playerReader.readLine();
			if (CommonUtil.verifyNull(first))
				return null;

			String record = playerReader.readLine();

			while (!CommonUtil.verifyNull(record)) {

				Player player = new Player();
				String[] prop = record.split("\\,");
				int id=Integer.parseInt(prop[0]);
				if(id==0){
					record = playerReader.readLine();
					continue;
				}
				player.setPlayerId(id);
				
				String name=prop[1];
				if(name==null||"".equals(name)||name.indexOf("unname")!=-1){
					record = playerReader.readLine();
					continue;
				}
				player.setName(name);
				player.setShirtName(prop[2]);
				player.setSpacing(prop[4]);
				player.setCommentary(prop[5]);
				player.setAge(Byte.valueOf(prop[6]));
				
				String nationality=prop[7];
				if(nationality==null||"".equals(nationality)||nationality.indexOf("Free Nationality")!=-1){
					record = playerReader.readLine();
					continue;
				}
				player.setNationality(prop[7]);
				player.setFoot(prop[8]);
				player.setWeight(Integer.parseInt(prop[9]));
				player.setHeight(Integer.parseInt(prop[10]));
				player.setForm(prop[11]);
				player.setGrowthType(Integer.parseInt(prop[15]));
				player.setMarketPrice(Integer.parseInt(prop[16]));

				StringBuilder positionFit = new StringBuilder();
				if (prop[17] != null && "1".equals(prop[17])) {
					positionFit.append("GK,");
				}
				if (prop[18] != null && "1".equals(prop[18])) {
					positionFit.append("SW,");
				}
				if (prop[19] != null && "1".equals(prop[19])) {
					positionFit.append("CB,");
				}
				if (prop[20] != null && "1".equals(prop[20])) {
					positionFit.append("LB,");
				}
				if (prop[21] != null && "1".equals(prop[21])) {
					positionFit.append("RB,");
				}
				if (prop[22] != null && "1".equals(prop[22])) {
					positionFit.append("DMF,");
				}
				if (prop[23] != null && "1".equals(prop[23])) {
					positionFit.append("CMF,");
				}
				if (prop[24] != null && "1".equals(prop[24])) {
					positionFit.append("LMF,");
				}
				if (prop[25] != null && "1".equals(prop[25])) {
					positionFit.append("RMF,");
				}
				if (prop[26] != null && "1".equals(prop[26])) {
					positionFit.append("AMF,");
				}
				if (prop[27] != null && "1".equals(prop[27])) {
					positionFit.append("LWF,");
				}
				if (prop[28] != null && "1".equals(prop[28])) {
					positionFit.append("RWF,");
				}
				if (prop[29] != null && "1".equals(prop[29])) {
					positionFit.append("SS,");
				}
				if (prop[30] != null && "1".equals(prop[30])) {
					positionFit.append("CF,");
				}

				player.setPositionFit(positionFit.toString().substring(0,
						positionFit.length() - 1));
				player.setPosition(prop[31]);

				StringBuilder cardS = new StringBuilder();
				if (prop[55] != null && "1".equals(prop[55])) {
					cardS.append("S01,");
				}
				if (prop[56] != null && "1".equals(prop[56])) {
					cardS.append("S02,");
				}
				if (prop[57] != null && "1".equals(prop[57])) {
					cardS.append("S03,");
				}
				if (prop[58] != null && "1".equals(prop[58])) {
					cardS.append("S04,");
				}
				if (prop[59] != null && "1".equals(prop[59])) {
					cardS.append("S05,");
				}
				if (prop[60] != null && "1".equals(prop[60])) {
					cardS.append("S06,");
				}
				if (prop[61] != null && "1".equals(prop[61])) {
					cardS.append("S07,");
				}
				if (prop[62] != null && "1".equals(prop[62])) {
					cardS.append("S08,");
				}
				if (prop[63] != null && "1".equals(prop[63])) {
					cardS.append("S09,");
				}
				if (prop[64] != null && "1".equals(prop[64])) {
					cardS.append("S10,");
				}
				if (prop[65] != null && "1".equals(prop[65])) {
					cardS.append("S11,");
				}
				if (prop[66] != null && "1".equals(prop[66])) {
					cardS.append("S12,");
				}
				if (prop[67] != null && "1".equals(prop[67])) {
					cardS.append("S13,");
				}
				if (prop[68] != null && "1".equals(prop[68])) {
					cardS.append("S14,");
				}
				if (prop[69] != null && "1".equals(prop[69])) {
					cardS.append("S15,");
				}
				if (prop[70] != null && "1".equals(prop[70])) {
					cardS.append("S16,");
				}
				if (prop[71] != null && "1".equals(prop[71])) {
					cardS.append("S17,");
				}
				if (prop[72] != null && "1".equals(prop[72])) {
					cardS.append("S18,");
				}
				if (prop[73] != null && "1".equals(prop[73])) {
					cardS.append("S19,");
				}
				if (prop[74] != null && "1".equals(prop[74])) {
					cardS.append("S20,");
				}
				if (prop[75] != null && "1".equals(prop[75])) {
					cardS.append("S21,");
				}
				if (prop[76] != null && "1".equals(prop[76])) {
					cardS.append("S22,");
				}
				if (prop[77] != null && "1".equals(prop[77])) {
					cardS.append("S23,");
				}
				if (prop[78] != null && "1".equals(prop[78])) {
					cardS.append("S24,");
				}
				if (prop[79] != null && "1".equals(prop[79])) {
					cardS.append("S25,");
				}
				if (prop[80] != null && "1".equals(prop[80])) {
					cardS.append("S26,");
				}

				String s = cardS.toString();
				if (!"".equals(s)) {
					player.setCard_S(s.substring(0, s.length() - 1));
				} else {
					player.setCard_S(s);
				}

				StringBuilder cardP = new StringBuilder();
				if (prop[81] != null && "1".equals(prop[81])) {
					cardP.append("P01,");
				}
				if (prop[82] != null && "1".equals(prop[82])) {
					cardP.append("P02,");
				}
				if (prop[83] != null && "1".equals(prop[83])) {
					cardP.append("P03,");
				}
				if (prop[84] != null && "1".equals(prop[84])) {
					cardP.append("P04,");
				}
				if (prop[85] != null && "1".equals(prop[85])) {
					cardP.append("P05,");
				}
				if (prop[86] != null && "1".equals(prop[86])) {
					cardP.append("P06,");
				}
				if (prop[87] != null && "1".equals(prop[87])) {
					cardP.append("P07,");
				}
				if (prop[88] != null && "1".equals(prop[88])) {
					cardP.append("P08,");
				}
				if (prop[89] != null && "1".equals(prop[89])) {
					cardP.append("P09,");
				}
				if (prop[90] != null && "1".equals(prop[90])) {
					cardP.append("P10,");
				}
				if (prop[91] != null && "1".equals(prop[91])) {
					cardP.append("P11,");
				}
				if (prop[92] != null && "1".equals(prop[92])) {
					cardP.append("P12,");
				}
				if (prop[93] != null && "1".equals(prop[93])) {
					cardP.append("P13,");
				}
				if (prop[94] != null && "1".equals(prop[94])) {
					cardP.append("P14,");
				}
				if (prop[95] != null && "1".equals(prop[95])) {
					cardP.append("P15,");
				}
				if (prop[96] != null && "1".equals(prop[96])) {
					cardP.append("P16,");
				}
				if (prop[97] != null && "1".equals(prop[97])) {
					cardP.append("P17,");
				}
				if (prop[98] != null && "1".equals(prop[98])) {
					cardP.append("P18,");
				}

				String p = cardP.toString();
				if (!"".equals(p)) {
					player.setCard_P(p.substring(0, p.length() - 1));
				} else {
					player.setCard_P(p);
				}

				Technique tech = new Technique();
				tech.setWeakFootAcc(Byte.valueOf(prop[12]));
				tech.setWeakFootFrequency(Byte.valueOf(prop[13]));
				tech.setAttack(Byte.valueOf(prop[32]));
				tech.setDefence(Byte.valueOf(prop[33]));
				tech.setHeaderAcc(Byte.valueOf(prop[34]));
				tech.setDribbleAcc(Byte.valueOf(prop[35]));
				tech.setShortPassAcc(Byte.valueOf(prop[36]));
				tech.setShortPassSpeed(Byte.valueOf(prop[37]));
				tech.setLongPassAcc(Byte.valueOf(prop[38]));
				tech.setLongPassSpeed(Byte.valueOf(prop[39]));
				tech.setShotAcc(Byte.valueOf(prop[40]));
				tech.setPlaceKick(Byte.valueOf(prop[41]));
				tech.setSwerve(Byte.valueOf(prop[42]));
				tech.setBallCtrl(Byte.valueOf(prop[43]));
				tech.setGoalKeepingSkills(Byte.valueOf(prop[44]));

				player.setTech(tech);

				Speed speed = new Speed();
				speed.setResponse(Byte.valueOf(prop[45]));
				speed.setExplosivePower(Byte.valueOf(prop[46]));
				speed.setDribbleSpeed(Byte.valueOf(prop[47]));
				speed.setTopSpeed(Byte.valueOf(prop[48]));

				player.setSpeed(speed);

				Body body = new Body();
				body.setBodyBalance(Byte.valueOf(prop[49]));
				body.setStamina(Byte.valueOf(prop[50]));
				body.setKickingPower(Byte.valueOf(prop[51]));
				body.setJump(Byte.valueOf(prop[52]));
				body.setInjury(prop[14]);
				player.setBody(body);

				WillPower wp = new WillPower();
				wp.setTenacity(Byte.valueOf(prop[53]));
				wp.setTeamwork(Byte.valueOf(prop[54]));
				wp.setAttackAwareness(Byte.valueOf(prop[99]));
				wp.setDefenceAwareness(Byte.valueOf(prop[100]));

				player.setWillPower(wp);

				PlayerStyle style = new PlayerStyle();
				style.setSkinColor(Byte.valueOf(prop[101]));
				style.setSkinTexture(Byte.valueOf(prop[102]));
				style.setFaceMode(prop[103]);
				style.setLinkedFace(Byte.valueOf(prop[104]));
				style.setFaceSlot(Integer.parseInt(prop[105]));
				style.setLinkedHair(Byte.valueOf(prop[106]));
				style.setHairSlot(Integer.parseInt(prop[107]));
				style.setBoots(Byte.valueOf(prop[108]));
				style.setUntuckedShirt(Byte.valueOf(prop[109]));
				style.setTightKit(Byte.valueOf(prop[110]));
				style.setGloves(Byte.valueOf(prop[111]));
				style.setDribbleStyle(Byte.valueOf(prop[112]));
				style.setFreeKickStyle(Byte.valueOf(prop[113]));
				style.setPenaltyKickStyle(Byte.valueOf(prop[114]));
				style.setDropKickStyle(Byte.valueOf(prop[115]));
				style.setGoalCelebrationStyle1(Byte.valueOf(prop[116]));
				style.setGoalCelebrationStyle2(Byte.valueOf(prop[117]));

				player.setPlayerStyle(style);

				player.setClubTeam(prop[118]);
				player.setNumber(Integer.parseInt(prop[119]));
				if (prop.length == 121) {
					player.setNational(prop[120]);
				}

				players.add(player);
				record = playerReader.readLine();
			}
			
			logger.info("一共：" + players.size()+" 球员信息。");

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				playerReader.close();
				fileReader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}

		return players;
	}

	public List<Team> readTeam() {
		
		FileReader fileReader = null;
		BufferedReader teamReader = null;
		List<Team> teams = new ArrayList<Team>();

		try {
			
			fileReader = new FileReader(path + "team.csv");
			teamReader = new BufferedReader(fileReader);
			String first = teamReader.readLine();
			if (CommonUtil.verifyNull(first))
				return null;

			String record = teamReader.readLine();
			int teamId=1;
			while (!CommonUtil.verifyNull(record)) {
				Team team = new Team();
				String[] prop = record.split("\\,");
				team.setTeamId(teamId++);
				team.setTeamName(prop[0]);
				team.setShortName(prop[1]);
				team.setStadium(Integer.parseInt(prop[2]));
				team.setChantsSlot(Byte.valueOf(prop[3]));
				team.setFlagSlot(Integer.parseInt(prop[4]));
				
				Map<Byte, String> member=new HashMap<Byte, String>();
				for(int i=5;i<69;i++){
					String name=prop[i++];
					Integer number=Integer.parseInt(prop[i]);
					if(name==null||"".equals(name)||number==255){
						continue;
					}
					if(!member.containsKey(number.byteValue())){
						member.put(number.byteValue(), name);
					}else{
						logger.error("数据处理错误，出现相同号码： "+number);
					}
				}
				team.setMember(member);
				team.setLongFK(Byte.valueOf(prop[69]));
				team.setShortFK(Byte.valueOf(prop[70]));
				team.setFK2(Byte.valueOf(prop[71]));
				team.setCornerL(Byte.valueOf(prop[72]));
				team.setCornerR(Byte.valueOf(prop[73]));
				team.setPenalty(Byte.valueOf(prop[74]));
				team.setCaptain(Byte.valueOf(prop[75]));
				team.setTeamNameEN(prop[301]);
				teams.add(team);
				record = teamReader.readLine();
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				teamReader.close();
				fileReader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		
		logger.info("一共："+teams.size()+" 只有效队伍");
		
		return teams;
	}

}
