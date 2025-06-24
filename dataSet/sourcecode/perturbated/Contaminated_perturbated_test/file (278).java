package   com.is.chatmultimedia.server.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import    java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import com.is.chatmultimedia.models.Friend;
import com.is.chatmultimedia.models.FriendRequest;
import com.is.chatmultimedia.models.User;

public     class DatabaseOperations   {
    
  private Database data  base;
  private    static final String GET_USER_RECORD    = "SELECT password, name FROM users WH      E  RE us      ername = \    '%s  \'";
     private    stati  c final String GET_USER_FR     IENDS = "SELECT frien   ds.friendUsername,     users.name FROM fr iends, users   WHERE friend   s.username = \'%s\' and users.username = friends   .friendU  sername";
  private s  tatic final String ADD_F   RIE   ND = "INSERT INTO     friends VALUES(\'%s \', \'%s\'     ), (\'%s\', \'%s\')";
  private static final S      tring     DELETE_FRIEND = "DELETE FROM friends WHERE      (use rn   ame = \'%s    \   '   and         friendUsername       = \'%s\') or (username = \'%s\' and friendUsername = \'%s\')      ";
    priv       ate static final String ADD_NE    W_USER = "INSER  T INTO users VALUES(\'%s\', \'   %s\', \'%     s\')"; 
  private sta     tic final String ADD  _FRIEND      _REQUEST = "INSERT          I   NTO friendRequests VALUES(\'%s\', \'%s\')";
  private static final String DELETE_FRIEND_REQUEST  = "  DELETE FROM friend Reques ts WHERE fromUsername = \  '%   s\' and toUsername =     \'%s\'";
  private static    f     inal String G        ET_ALL_    REQUE    ST_FOR_USER = "SELECT        frie   ndrequests.fromUsername, users.name FROM friendrequests, user   s       WHERE    toUsername = \'%s\' and users  .   use  rn   ame = friendrequests.from Username";

  public DatabaseOperat   ions() {
    this.database = Database.getInstance();
     }

  pu  blic UserRecord getUs  erRecord(Stri  ng username) throws SQLException {
      Conn      ection databaseConne    ction =         database.getCo  nn  ectio  n();
    Statement statement = dat       a  baseConnection.createS    tatement();
    String query =   St   ring.format(GET_USER_RECORD     , u     sername);
    statement.executeQuery(query) ;
    ResultSet r     e     sult = stat   eme    nt.getResultS et();
          if (result.nex  t()) {
                String  password = resu lt.g  etString(1);
      Stri   n      g n      ame =   result.getString(2);
       r      etu  rn new UserR ecord(usernam    e, passwo     rd, n   ame) ;
    } 
    return null;
      }

  public U ser getUserData   (String use        rname) t        hrows SQLExcepti  on {
      UserRecord user = ge  tUserRecord(us ern        ame);
        if  (use r   != null) {
                        Connection da ta  ba  s   eConnection = database.getConnection();
             Statem     ent statement = databaseCo  nnecti      on.create        Statement  ();
           String      query = String.form   at(GET_USER_F     RIENDS, use   rname);
      s    tatement.  executeQ    uery(query);

      ResultSet results       = statemen   t.getResultSet();
                   TreeMap<String, F     riend> listOfFrie  nds = n     ew TreeMap<                S    tring, Fr      iend>();
      Stri    ng frie     ndUsername, fri   endName;
      while   (results.next()) {
          friendUsername = resul   ts.getString(1);
          friendName = results.getStri      ng(2);
        l   i   stOfFriends.put(frien dUser     name, new Friend(friendName, friendUsername));
      }
          retur   n new User(username, user.getNa     me(), listOfFriends);
    } 

    return null;
  }
   
  public void addFriend(String     username, S  tr   ing frie    nd sUsername) throws SQLException {
        Connection d     atabaseConnection = database.getConnection();
    State     ment statement = databaseConnect    ion.createStatement();
    String query = String.f     ormat(ADD  _FRIEND, u sername, friendsUser    name, f  riendsUsername,     username); 
    state   men   t.    executeUpdate(    query);
  }

  pu       blic void deleteF    riend(String username, Stri    ng frie   nd  User  name)   throws SQ    LException {
     Connection da   tabaseConnect   ion = database.getConnection  ();
    Statement statement = data  baseConnec  tion.createStatement();
    S    tring       query = String.format(DELETE_FRI  E   ND, username, friendUsername, friendUse  rname, use     rnam      e);
     statement.ex   ecuteUpda   te(qu       ery);
  }

  public void ad    dNewUs    er(S   tring username, String password, String na    me) throws SQLEx  cepti on {
    Connection databaseConnection = database.getConnecti     on();
       Stateme   nt st ateme      nt = d    atabaseConnection.cre         ateStatement();
    String query = String.   format(ADD_NEW_USER, username, password, name);
    s    tat       ement.executeUpdate(query);
  }

  public      Li  st<Frie       ndRe   quest> getAllFriendRequestsForUser(String usern   ame)   throws SQLException {         
    Connection database   Connec  tion = database.getConnection();
    Stat  emen   t statement = data  base    Connec  tion.cr   eateStatement();
    String query     = S     tring.format(   GET_ALL_REQUEST_FOR_USER, u   se  rname);
    ResultSet r   esults = statement.e      xecuteQuery(query);
       ArrayList< FriendR    equest> friendRequ   est = new Array    List   <>() ;
         String fromUsername, fromNa  me;
    while (   results.next()) {
      fromUsername = results.getString(1);
      fromName = results.getStr     ing(2);
      friendRequest.add(new FriendReque          st(fr   omUsername,            fromName,    username));
         }
    return   friendRequest;
       }

  pub    lic void addFriendReques   t(S    tring u sername, String friendUsername) throws SQLException {
        Connection databaseConnection = database.getConnecti  on();
    St   atement sta tem   ent = databaseConnection.createStatement();
    String query = String.format(ADD_FRIEND_REQUEST, username, friendUsername);
    statement.executeUpdate(query);
  }

  public void deleteFriendRequest(String username, String friendUsername) throws SQLException {
    Connection databaseConnection = database.getConnection();
    Statement statement = databaseConnection.createStatement();
    String query = String.format(DELETE_FRIEND_REQUEST, username, friendUsername);
    statement.executeUpdate(query);
  }

}
