package data.bedien;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import entity.bedien.BestelStatus;
import entity.bedien.Gerecht;
import entity.bedien.Type;
/**
 * Bevat de SQL queries voor het vullen van de Gerechten zodat deze toegevoegd kunnen
 * worden aan de bestelling.
 * 
 * @author Alex
 * @see 						DAOManager
 * @see							entity.bedien.Bestelling
 * @see							#getBesteldeGerechten()
 */
public class DAOBestelling {
	private DAOManager daoMgr;
	
	public DAOBestelling() {
		this.daoMgr = new DAOManager();
	}
	
	/**
	 * Vraagt gerechten op uit de database die besteld zijn
	 * 
	 * @return					Bestelde gerechten
	 * @see						entity.bedien.Gerecht
	 * @see						DAOManager#startConnection()
	 * @see						DAOManager#execSelectQuery(String)
	 * @see						DAOManager#closeConnection()
	 */
	public Gerecht[] getBesteldeGerechten(){
		ArrayList<Gerecht> gerechten = new ArrayList<Gerecht>();
		String q = "SELECT Product.productNr," +
				"Product.productNaam," +
				"bestelling.tafelNr," +
				"bestelling.bestelTijdstip," +
				"bestelling.status," +
				"bestellingregelgerecht.aantal," +
				"bestelling.type " +
				"FROM product,bestelling,bestellingregelgerecht " +
				"WHERE (bestelling.status='GEPLAATST' OR bestelling.status='GEREED') " +
				"AND (bestellingBestellingNr=bestellingNr AND productProductNr=productNr)";
		try{
			daoMgr.startConnection();
			ResultSet rs = daoMgr.execSelectQuery(q);
			while(rs.next()){
				Gerecht g = new Gerecht();
				g.setID(rs.getInt("productNr"));
				g.setNaam(rs.getString("productNaam"));
				g.setTafel(rs.getInt("tafelNr"));
				g.setBestelTijd(rs.getTimestamp("bestelTijdstip"));
				g.setStatus(BestelStatus.valueOf(rs.getString("status")));
				g.setAantal(rs.getInt("aantal"));
				g.setType(Type.valueOf(rs.getString("type")));
				gerechten.add(g);
			}
			daoMgr.closeConnection();
		}catch(SQLException | NullPointerException e){
			e.printStackTrace();
			System.out.println("Geen database gevonden, hardcoded backuplijst gebruikt");
			return getGerechten();
		}
		Gerecht[] ger = new Gerecht[gerechten.size()];
		gerechten.toArray(ger);
		return ger;
	}
	
	/**
	 * Hardcoded toewijzen van gerechten voor geval ontbreken database
	 * 
	 * @return					Array van gerechten
	 */
	public Gerecht[] getGerechten(){
		Gerecht[] gerechten = new Gerecht[7];
		gerechten[0] = new Gerecht();
		gerechten[0].setNaam("Drank 1");
		gerechten[0].setAantal(2);
		gerechten[0].setBestelTijd(new Date(100));
		gerechten[0].setStatus(BestelStatus.GEPLAATST);
		gerechten[0].setTafel(11);
		gerechten[0].setType(Type.DRANK);

		gerechten[1] = new Gerecht();
		gerechten[1].setNaam("Drank 2");
		gerechten[1].setAantal(4);
		gerechten[1].setBestelTijd(new Date(4504575));
		gerechten[1].setStatus(BestelStatus.GEPLAATST);
		gerechten[1].setTafel(10);
		gerechten[1].setType(Type.DRANK);

		gerechten[2] = new Gerecht();
		gerechten[2].setNaam("Drank 3");
		gerechten[2].setAantal(1);
		gerechten[2].setBestelTijd(new Date(45464562));
		gerechten[2].setStatus(BestelStatus.GEPLAATST);
		gerechten[2].setTafel(24);
		gerechten[2].setType(Type.DRANK);

		gerechten[3] = new Gerecht();
		gerechten[3].setNaam("Drank 4");
		gerechten[3].setAantal(2);
		gerechten[3].setBestelTijd(new Date(1234));
		gerechten[3].setStatus(BestelStatus.GEPLAATST);
		gerechten[3].setTafel(1);
		gerechten[3].setType(Type.DRANK);
		
		gerechten[4] = new Gerecht();
		gerechten[4].setNaam("Gerecht 1");
		gerechten[4].setAantal(2);
		gerechten[4].setBestelTijd(new Date(12354));
		gerechten[4].setStatus(BestelStatus.GEREED);
		gerechten[4].setTafel(1);
		gerechten[4].setType(Type.GERECHT);
		
		gerechten[5] = new Gerecht();
		gerechten[5].setNaam("Gerecht 2");
		gerechten[5].setAantal(2);
		gerechten[5].setBestelTijd(new Date(452456757));
		gerechten[5].setStatus(BestelStatus.GEREED);
		gerechten[5].setTafel(1);
		gerechten[5].setType(Type.GERECHT);
		
		gerechten[6] = new Gerecht();
		gerechten[6].setNaam("Gerecht 3");
		gerechten[6].setAantal(2);
		gerechten[6].setBestelTijd(new Date(45457));
		gerechten[6].setStatus(BestelStatus.GEREED);
		gerechten[6].setTafel(1);
		gerechten[6].setType(Type.GERECHT);
		
		return gerechten;
	}
}
