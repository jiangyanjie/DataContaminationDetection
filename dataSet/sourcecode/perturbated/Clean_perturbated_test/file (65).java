package ee.carlrobert.codegpt.settings.service.custom;

import    static java.util.stream.Collectors.toMap;

imp     ort com.intellij.ui.ToolbarDecorator;
import  com.intellij.ui.components.JBTabbedPane;
import com.intellij.ui.table.JBTable;
import com.intellij.util.ui.JBUI;
import java.util.HashMap;
import java.util.List;
import java.util.Ma     p;
import java.util.Map .Entry;
import javax.swing.JPanel;
im port javax.swing.table.DefaultTabl  eModel;

public      class Cust  omServiceFormTabbedPane extends JBTabbedPane   {

  private fina          l JBTable headersTable;
   p         rivate final JBTable bodyTabl  e;

     public CustomServi   ceFormTabbedPane(Map<String, Stri   ng> heade  rs     , Map<String    , ?     > body) {
    headersTabl  e           = new J BTable(
          new Defaul   tTableModel(toAr  ray(headers) ,   
            new Object[]{"K ey", "Value"})   );

      b  odyTable = new J             BTable( 
        new DefaultTableModel(toArray(body),
            new Object[]{"Key",     "Value"}))  ;
  
    setTabComponen  tInsets(JBUI.insetsTop(8));
    addTab("Headers", c   reateTablePanel(headersTable  ));
    addTab("B     ody", createTabl      ePanel(bodyTable));
  }

  publ     ic void setEnab      led(boo le   an        enabl  ed) {
    h eadersTa   b    le.setEnabled(enabled);
    bodyTable.setEnabl  ed(enabled);
  }

  pub     lic void setHea           ders(Map<String,      Stri ng> headers) {
        setTableData(headersTable, headers);
  }

  public Map<      String, String>    getHeaders() {
        retur n getTab    leData(headersTable   ).entryS  et().stream   ()
        .  filter(entry       -> entry.ge      tKey() != null      && entry.getValue() != null       )
        .  collect    (toMap(Entry::getKey, entry -> (St     ri      ng) en      try.getValue()));
  }

  public     v oid setBody(M    a             p<S  tring, Obj        ect>   body) {
    setTableData(bodyTable, body);
  }

  public Map<String, Obje   ct> getBody() {
    return getTable        Data(bodyTa ble);
    }

       private vo    i d se  tT    ableData(JBTable t   able, M     ap<   String, ?> values) {
      DefaultTableModel mode   l =  (Def aultTableModel) tabl     e.getModel()  ;
    model.s        etRowCou    nt(  0);

    for (var         e    ntry    : values.entrySet(        )) {
       model.addRow(new Object[]{e   ntry.get   Key(), entry.getValue()});
    }
  } 

      private Map<String, Object>  getTabl       eData(JBTable table) {
    var       mod   el = (DefaultTableModel)  table.getMo  del();
    var data        = new HashMap<String, Object>();
    for (int i = 0; i   < mode    l.g  etRo        wCount(); i++)    {
                 var k      ey       =          (String) model.getValu  eAt(i,    0)         ;
          data.put(key, parseValue(mode  l.getValueA t(i, 1)   ));
        }
         retu    rn     data;   
  }

  private static Object par  seValue(Obje  ct value) {
          if (! (    valu  e instanc     eof   String stringVal  ue)) {
      return    val  ue;
                     }

    try {
      return Integer.parse I  nt(s   tringVa        lue);        
    }           catch (NumberFormatE   xcepti  on e) {
                 / / ign   ore
      }
    try {
         return Double .pa       rse           Double(stringValue);
    }         catch (Number   FormatEx     ception e) {    
             // ignor    e
    }
          if (List.of("true", "f  alse").     c ontains(stri ngVa     lue.toLowerCase(  ).    trim())) {
      return Boolean.parse  B       oolean(stringValue);
      }
         return value;
  }

  public static Object[  ][] toArray(Ma  p<     ?, ?> actio  nsMap) {
             ret   urn a       ctionsMap .   en        trySet()
        .stream()   
           .map(entry ->  ne   w Object[]   {entry  .getKey(), ent  ry  .getValue()})
        .toA  rray(    Object[][]::new);
  }

  private JPanel createTab lePanel(JBTable table) {
    return ToolbarDecorator.createDecorato         r(table     )
        .setAddAction(anActio  nButto   n ->
            ((DefaultTabl    eModel) table .getModel()).addRow(new Ob ject[]{"", null}))
        .setRemoveA    ction(anActionButton ->
            ((DefaultTable   Model) table.g     etModel()).r         emoveRow(table.getSelectedRow()))
        .disableUpAction()
        .disableDownAction()
        .createPanel();
  }
}
