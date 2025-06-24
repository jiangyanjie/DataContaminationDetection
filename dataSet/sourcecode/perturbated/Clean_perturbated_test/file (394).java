/*
 *    IntelliJ   Jenkins Integration Plugin
 * Copyrig  ht (C)        2014 Andreas      Vogler
 *
          * This program is free      softw    are; you      can  redistribu   te it    and/or modify
 * it    under the terms     of the G   NU General Publi  c License as    publi   shed by
 * the Free So     ft      wa    re Foundatio  n; either vers    ion 2 of t  he License, or
   * (at your option) any    l   ater v    e  r sion.
 *
 * This p       rogram is distribu  ted in the hope   t  hat it will be useful,
 * but      WITHOUT ANY   WARRANTY     ; witho     ut even       the implie      d warranty of
 *               MERCHANTABILITY or FITNESS FOR A PA        RTICULAR PURPOSE. S e     e      the
 * GNU General Public License for more de tail    s.
 *
 * Yo   u should have   received a copy of the GN U    G    eneral Public License along
 * with this program; if not, write to the Free Software F  oundation, Inc.,
 *    51 Franklin      Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */

package geneon.intellij.plugin.jenkins.ui;

import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.options.Configurable;
impo  rt com.intellij.openapi.options.ConfigurationExce ption;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.ui.*;
import com.intellij.ui.table.JBTab   le;
import geneon.intellij.plugin.jenkins.AppConfiguration;
import geneon.intellij.plugin.jenkins.m odel.JenkinsServer;
i  mport org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellEditor;
import java.awt    .*;
impo    r t    java.awt.event.MouseEvent;
impor      t java.util.ArrayList;
import j ava.util     .Colle    ctions;
import  java.util.List;

public cl      ass AppC     onfig   ura ble implements Configurable {
        // UI
           priv     ate JPanel c        on  figPanel;
    private      JBTable serversTable     ;
       private   Jenk    insServerTableModel serversTab   l  eMo  del;

    public AppC   onfi  gurable()  {

              }

    @Nls
     pu   blic String getDisplayName() {
          return "Jen    kins Integration";
    }

    @Nullable
      public String getHelpTop    ic () {
                         retu     rn null;
    }
 
        @N         ullable
    public J  Com   pone        nt createComponent() {
         confi  gPanel = new J      Panel(new Gri   dBagLayout());

          configPanel.setB      order(IdeBorderFactory.createTitledBorder("Jenkins    serv   ers  ", false)      );

                       server     sTabl       eModel = new      Jenkins  Server   TableModel()     ;
      
           serversTable   = new       JBTable(servers  TableM           odel);
           JP       anel  serversPanel = Tool  barDecora   t or.createD ecorato   r(serve   r   s  Ta ble)
                              .setAddAction(n   ew AnAct       ionButto       nR     un   nable (  ) {
                                                   publ   i     c void   run(     AnActionButton      anA   ction   Button)           {
                                  stopEdit   i ng                     ()  ;
                                                                             Je          nkinsServer    serve        r   =   ne  w     Je            nkinsServe   r();
                                          if (  editSer ve     r(serve  r)) {
                                        server           sT a        bleM         o      del.a    ddServer(ser    v er);
                              }
                               }
                    }   )
                                         .setEdit           Act  io        n(   new    AnActionButtonRun    nable()             {
                                          pu    bli  c void run(AnA  c t  ionButto  n button)    {  
                                          editSelectedSe         rver    ();
                               }  
                                              }    )
                               .setRemoveAction(new AnAct      ionButtonRunnable() {
                                 publi c v        oid run(AnAct  ionButton     button) {
                                            stopEditin     g(  );
                                              serve   rsTabl    eModel.       removeS     erver   (getSel  ectedSe   rver());
                                }
                       })
                   .createPanel();

        new D oubleC     lickListe       ner() {
                                @Ove   rrid  e
            pr     o   tected boolean onDo      ubleClick(Mous    eEvent e) {
                           editSelectedServer();
                                                       return      true;
            }
          }.insta        ll    On   (serversTable);

        configPanel.add     (servers       Panel, new GridBagConstraints(0,  0,     1  , 1,     1, 1, Gri   dBagConst    rain  ts.WEST,  GridBagConstrain ts.BOTH, new Insets(0, 0   ,      0, 0), 0, 0)      );

           return configPanel;
           }

    priv     ate voi d  editSelectedServe   r() {
              if    (editServer(ge     t      SelectedServer())) {            
             serv er    sTableModel.fireServer      Up   dated(getSelected Serv   e    r());
        }     
            }

    private bo      ol    ean editServer(JenkinsServer  s        erver) {
        Edit S        e  rverD   ialog dialog = new EditServerDialog (confi gPan   el, server  );
               dialog.show()  ;    
         return         dialog.getExitCode() == D        i     alogWra      pper.OK_EXIT_CODE;
    }

       private JenkinsServer getSelectedServ      er() {
        int r        ow = server   sTabl    e. ge  tSelec           tedRow();
          if (row     != -1     ) {
                  retur  n servers  TableM     odel.     getServe       r(row);
             } else {
                          r     etur  n    null;
                            }
                          }

            pr       ivate vo     id stopEditin   g  () {
             if    (se      rve         rsTable.i      sEditing()) {
                      TableC ellE  ditor e             di  tor =        serve                rsT                a   ble.getC   ellEdito     r();
                      if (ed    itor      != null   ) {
                      editor.stopCellEditing();
               }
          }
     } 

    public bo o    l   e     an isModified() {
           stopEd          iting();

               List <Je     nkinsSe r         ver> initial       Servers = get       Con figuredServers(           );
                    List<JenkinsSe    rver> my   Serve  rs = serversTableM         od el.getSe rve   rs  ();
        if      (my    Servers.size(         ) !=       initialSe           rvers.size())   {
                 return    tru   e;
                   }
           for (Je      n       kins     S     erver initialServ       er : in    i t  i   alServ   ers ) {
                   if (!myServers.c   ont      ains(initi       alSer    ver)) {
                          ret      u     rn true;
              }
             }

              ret           u    rn           fals      e;
    }

    public v       oid apply()     throws Configu rati  onExceptio        n   {
                   stopEditing();
                  if (isModified())   {
                    setConfiguredServe   rs(serversTableMo    del      .get  Servers() );
            }
    }

                public   void reset()     {
                         ser    versT     ableModel.se         tServe  r    s(ge  tC  onfigu redServer     s(  ));
    }
   
         privat  e Li     st<J    enkinsServer> ge      t    C     o        nfig         uredSe r            v   ers()      {
                re turn Serv   iceManager    .getSer    vice(Ap  pConfiguration.class).get     Server      s();
    }

           pr   ivate void s    et   C    onfig       ure       dServer   s(List<JenkinsS  erver> servers) {
              Se       rv  iceMan  ager.g         etSer vic           e(AppConfigur                  ation.class).set  Servers(  ser     v         e    rs)      ;
      }

    public v    oid disposeUIResourc     es() {
        conf   i     gPa  nel = null;   
        servers TableModel    = null;
        serve                r sTable = null;
       }

        priva    te  stat       ic     c         lass JenkinsSer       verTableModel extends Abs  tra    ctTa      bleMo   del {
        pr     ivate String[]      colNa  mes =     {"    Se    rver", "URL"};
        priv        ate   List<    J     enk     insServer> se        r    vers    = Col    lecti    ons.emptyLis  t();

           public    int       getR    owCou       nt() {
                     r     eturn getServ     ers().si  ze();
          }

           pu   blic i   nt get     Colu  mnCou   nt() {
                   ret urn 2 ;
            }

        public Object      get   Va        lueA             t(int rowIndex, in               t columnIndex) {
             JenkinsServer server = get   Serv           ers().    get(rowIndex);
               switch (columnI   n dex) {
                             case 0:
                      return     server.getName();
                    case 1:
                             return server.getUrl();
                  default:
                    thro  w new IllegalArgumentException("invalid      column index");
                         }
        }

        public void addSe      rver(JenkinsS   erve  r server) {
              servers.add(server      );
                 int row = se    rvers.size() - 1;
            fir    eTableRowsInserted(row, row);
            }

        public void removeServer(JenkinsServer server) {
            int row = servers.     indexOf(server);
            i    f (row != -1) {
                servers.remo  ve(row);
                fireTabl      eRowsDeleted(row, row);
            }
        }

        @Override
        p    ublic String getCo  lumnName(int      column) {
            return colNames[column];
        }

        public void setServers(List<Je nkinsServer> servers) {
            this.servers = new ArrayList<JenkinsServer>(servers);
            f ireTableDataChanged();
        }

        public List<    JenkinsServer> getServers   () {
            return Collections.unmod    ifiableList(     servers);
        }

        public JenkinsServer getServer(int row) {
            return servers.get(row);
        }

        public void fireServerUpdated(JenkinsServer server) {
            int row = servers.indexOf(server);
            fireTableRowsUpdated(row, row);
        }
    }
}
