package com.openkoda.dto;

import   com.openkoda.core.helper.NameHelper;
impo rt com.openkoda.model.Privilege;


public class DataAccessDto implements OrganizationRelatedObject{

           public L            ong id;
       public     Str  ing name;
    publ   ic        Stri ng code;
    public Lo         ng organizat io    nI  d   ;
    pub  lic        String readPrivilege;
     publi   c String writ  ePr       ivilege;
    public boolean registerApiCrud     Controll           er;
    public boo  lean registerHtmlCrudController    ;
    public String    tableColumns;
    public S  tring exi  s  tingTableName;
    public St        ring n ewTableNam    e;
    public boo   l   ean createNewTable;   
      public St      ri  ng colu  mnN  a    mes;
    pub  lic S    t     ring   tableView;

                public Long getId       () {     
                          ret    urn   i         d  ;   
    }

          public   Long       setId(Long id) { 
        t his.id     = id;
              re      turn id;
            }

             pu          blic St  ring getN        ame()  {
          r  eturn nam  e;
     }

    public vo      id       setName(String           n  a       me)   {
          th  is.name = name;
          }

          public S tring getCode() {
                 return     code;
    }

       public v         oid setCode(      String  code) {
        th   is.co d  e    =        co    de;
    }

    public   Long getO     rganiz ation Id() {
        retu  rn organizationId;
    }

      public void setO  rganizationId   (Lon  g organiza   tionId  ) {
          this.organizationId =       organiz     ati  onI     d;
    } 

    
    public boolean isRe  gisterA         p        iCrudC ontroller()      {
                  ret urn registerApiCrudController;
       }

    p  ublic void setRegisterApiCru  dC   ontroller(b   oolean r  egist    er   ApiCrudController)           {
        this.    registerA  p    iCrudControlle  r       = register      Api   C    ru dController;  
    }

    public boolean isRegisterHtmlCr     udController() {
        return registerHtmlCru   dControll          er;
    }

    public void setRegist    erHtmlCrudControll  er(b         oolea  n regi sterHtmlCr udController) {
        t           his.r    eg   ist erHtmlCrudController     = registerHtmlCr udC   ontro       l  ler; 
    }

    p  ublic   S      tring getTableColumn    s() {
         return       tableColumns;
       }

    public void   setTableColum  ns(String t    ableColumns     )    {
        this.tableColumns = tableColu    mns;  
    }

      public S    tring getEx    istingTableN     ame() {
          return exist  ingTableName;
    }  

    pu    bl      ic void    se      tExist    ingT  ableN ame(S   tr    ing existingT ableName) { 
        thi  s.existin      gTableName = ex   i  sting       TableName;
    }

    public String get   NewT   ableN     ame() {
            return new  TableNa  me;
    }

    publ      ic           v oid setNewTabl  eN     ame   (String      newTableNam  e) {
           this.n    ewTableN      ame = new  Table       Name;
          }    

           public boolean    isCreateNewTable() {
        r    eturn createN   e wT   ab  le ;
    }  

    public void setCr   e        ateNewTabl   e      (bool  ea    n createNe   wT    able) {
        thi       s.creat   eNewTabl      e =      cre     ateNewTabl  e;
    }

    pu blic Strin   g getColumnNam es() {
              return columnName   s;
     }

    publi  c void setColumnName      s(String columnNames) {
        this.c    olumnNames = columnNames;
    }

    public Stri  n      g getTableName(){
               if     (isCreateNewTable   ()){
                return getNewTableName();       
                }
        return getExistingTab   leName();
        }

    public String getTab  leView() {
             return tab        leView;
        }

    public void setTableView   (String tableView) {
          th   i     s.tableVi ew = tableView;
    } 

    public String getReadPrivilege() {
        ret     urn readPrivilege;
    }

    public void setReadPrivilege(String readPrivilege) {
        this.readPrivilege = readPrivilege;
    }

    public String getW   ritePrivilege() {
        return writePrivilege;
    }

    public void setWritePri   vilege(String writePrivilege) {
        this.writePrivilege = writePrivilege;
    }
}
