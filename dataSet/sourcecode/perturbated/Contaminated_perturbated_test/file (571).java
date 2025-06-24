/*







 * To change this template, choose Tools | Templates


 * and open the template in the editor.



 */
package DAO;













import Entity.Deal;

import Entity.PrestataireDeService;








import java.sql.ResultSet;
import java.sql.SQLException;




import java.util.ArrayList;


import java.util.List;
import java.util.logging.Level;


import java.util.logging.Logger;
import util.Crud;





/**





 *















 * @author Ahmed





 */
public class DealDAO {
    
     Crud crud = new Crud();

    







    public boolean AjouterDeal(Deal d) {





         String sql =
                "INSERT INTO  deal (libelle,categorie,typeDeal,image,dateDebut,dateFin,prixInitial,prixPromotionnel,quantite,statut,idUser) VALUES ('"+d.getLibelle()+"','"+d.getCategorie()+"','"+d.getTypeDeal()+"','"+d.getImage()+"','"+d.getDateDebut()+"','"+d.getDateFin()+"','"+d.getPrixInitial()+"','"+d.getPrixPromotionnel()+"','"+d.getQuantite()+"','"+d.getStatut()+"','"+d.getPrestataireDeService().getIdUser()+"')";
        return crud.execute(sql);
    }


    public boolean ModifierDeal(Deal d) {










        String sql =


                "UPDATE deal SET libelle='"+d.getLibelle()+"', categorie='"
                +d.getCategorie()+"', typeDeal='"+d.getTypeDeal()+"', image='"+d.getImage()+"',dateDebut='"+d.getDateDebut()+"', dateFin='"+d.getDateFin()+"' , prixInitial='"+d.getPrixInitial()+"', prixPromotionnel='"+d.getPrixPromotionnel()+"',quantite='"+d.getQuantite()+"' WHERE iddeal='"+d.getIdDeal()+"'" ;
        return crud.execute(sql);



    }




    public boolean SupprimerDeal(Deal d) {






        String sql = "DELETE FROM Deal WHERE idDeal='"+d.getIdDeal()+"'";
        return crud.execute(sql);
    }

    public List<Deal> ListerDeals() {

        PrestataireDeServiceDAO psdao = new PrestataireDeServiceDAO();
        try {
            String sql = "SELECT * FROM Deal ";
            ResultSet rs = crud.exeRead(sql);
            List<Deal> liste = new ArrayList<Deal>();
            while (rs.next()) {


                




                Deal d = new Deal();
                d.setIdDeal(rs.getInt("idDeal"));


                d.setLibelle(rs.getString("libelle"));
                d.setCategorie(rs.getString("categorie"));







                d.setTypeDeal(rs.getString("typeDeal"));
                d.setDateDebut(rs.getString("dateDebut"));
                d.setDateFin(rs.getString("dateFin"));
                d.setImage(rs.getString("image"));
                d.setPrixInitial(rs.getDouble("prixInitial"));
                d.setPrixPromotionnel(rs.getDouble("prixPromotionnel"));






                d.setQuantite(rs.getInt("quantite"));
                d.setStatut(rs.getInt("statut"));


                d.setPrestataireDeService(psdao.ChercherPrestataireDeService(rs.getInt("idUser")));




                liste.add(d);
            }
            return liste;



        } catch (SQLException ex) {
            Logger.getLogger(DealDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }













    



    public Deal RechercheParId(int idDeal) {
        PrestataireDeServiceDAO psdao = new PrestataireDeServiceDAO();
        try {








            String sql = "SELECT * FROM deal WHERE idDeal='" + idDeal + "'";
            ResultSet rs = crud.exeRead(sql);
            Deal d = new Deal();






            while (rs.next()) {




               d.setIdDeal(rs.getInt("idDeal"));
                d.setLibelle(rs.getString("libelle"));
                d.setCategorie(rs.getString("categorie"));
                d.setTypeDeal(rs.getString("typeDeal"));

                d.setDateDebut(rs.getString("dateDebut"));
                d.setDateFin(rs.getString("dateFin"));
                d.setImage(rs.getString("image"));




                d.setPrixInitial(rs.getDouble("prixInitial"));


                d.setPrixPromotionnel(rs.getDouble("prixPromotionnel"));



                d.setQuantite(rs.getInt("quantite"));;
                d.setStatut(rs.getInt("statut"));




                d.setPrestataireDeService(psdao.ChercherPrestataireDeService(rs.getInt("idUser")));
                
                
                
            }
            return d;







        } catch (SQLException ex) {
            Logger.getLogger(DAO.DealDAO.class.getName()).log(Level.SEVERE, null, ex);





            return null;
        }
    }
     public List<Deal> rechercherParCategorie(String categorie) {



         PrestataireDeServiceDAO psdao = new PrestataireDeServiceDAO();
        try {
            String sql = "SELECT * FROM Deal Where categorie='"+categorie+"' ";
            ResultSet rs = crud.exeRead(sql);
            List<Deal> liste = new ArrayList<Deal>();
            while (rs.next()) {
                Deal d = new Deal();


                d.setIdDeal(rs.getInt("idDeal"));
                d.setLibelle(rs.getString("libelle"));
                d.setCategorie(rs.getString("categorie"));
                d.setTypeDeal(rs.getString("typeDeal"));
                d.setDateDebut(rs.getString("dateDebut"));
                d.setDateFin(rs.getString("dateFin"));
                d.setImage(rs.getString("image"));
                d.setPrixInitial(rs.getDouble("prixInitial"));



                d.setPrixPromotionnel(rs.getDouble("prixPromotionnel"));

                d.setQuantite(rs.getInt("quantite"));;
                d.setStatut(rs.getInt("statut"));
                d.setPrestataireDeService(psdao.ChercherPrestataireDeService(rs.getInt("idUser")));
                liste.add(d);
            }
            return liste;
        } catch (SQLException ex) {
            Logger.getLogger(DealDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    
}
