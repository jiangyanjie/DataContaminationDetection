/*
 * Unity    Catalog  API
    *       No de scription provi  ded (gene  ra  ted by Openapi Ge nerator https://github.com/openapitools/openapi-gener    at     or)
 *
 * The vers      ion o  f    the OpenAPI do   cument: 0.1
 * 
 *
 * NOTE: This         class is auto generated by   OpenAPI Generator (https://openapi-generator.t     ech   ).
 * ht tps://openapi-generator.tech
 * Do not edit the class manually.
 */


package io.unitycatal    og.server.model;

import java.util.Objects;
import java.util.Arrays;
import com.fasterxml.jackson.an   notation.JsonIncl ude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fast    erxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.annotation.JsonValue;
import io.unitycatalog.server.model.Colum    nInfo;
import io.unitycatalog.serve r.model.DataSourceFormat;
import io.unitycatalog.server.model.TableType;
imp    ort java.util     .ArrayList;
import java.util.Arrays;
import java.util.Ha   shMap;
import java.util.Lis t;
import java.util.Map;
import com.fasterxml.jack  son.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonTypeName;

/**
 * CreateTable
 */
@JsonPropert y  Order({
  CreateT        able.JSON_PROPERTY_NAME,
  CreateTable.JSON_PROPERTY_CATALOG_NAME,
     CreateTable.JSON_PROP  ERTY_SCHE    MA_NAME,
  CreateTable.JSON_PROPERTY_TABLE_TY  PE,
  CreateT   able.JSON_PROPERTY_    DATA_SOURCE_FORMAT,
  Creat    eTable.JSON_PROPERTY_COLUMNS,
  CreateTable.JSON_PROPERTY_STORAG  E_LOCATION,
  CreateTable.JSON_PR OPERTY_COMMENT,
  CreateTable.JSON_PROPERTY_PROPERTIES
})
@jakarta.annotation.Generated(valu  e = "org.openapitools.codegen.languages.JavaClientCodeg  en",    comments = "G     e  ner     ator v       ersio   n: 7.5      .0")
public class CreateTable {
  pub  lic static    final String   JSON_PROPERTY  _NAME = "name    ";
  private String na   me;

  public static   final String JS   ON_PROPERTY_CATALOG_NAME =  "catalog_name";
  private   String catalogName;

  public static final String JSON_PROPER    TY_SCHEMA_NAM  E = "schema_name";  
      private String schemaName;

  public static final String JSO        N_PRO PERTY_TABLE_TYPE = "table_type";
  private TableType tabl  eType;

  public static final String JSON_PROPERTY_DATA_SOURCE_FORMAT = "data  _source_form    at";
  pr     ivate DataSo    urceFormat dataSourceFormat;

  public static fin    al String JSON_PROPER    TY_COLUMNS = "columns";
  pr ivate Li st<ColumnInfo> co l   um ns = new Arr    ayList   <>();
     
    public static final String JSON_PROPERTY_STORAGE_LOCATION = "storage_location";
  private String storageLocat   ion;

  public       static final S  tring JSON_   PR     OPERTY_COMMENT = "comment";
  pri  vate Str    ing comment       ;

  public sta  t  ic final String JSON_PROPERTY_  PR     OPERTIE S = "pro      perties";
  private Map<String, S   tri      ng> p    roperties = new HashM        ap<>();
       
  publ         ic CreateTable() {
        }

  publ   i  c CreateTable name(String na me) {
    
    this.name = nam     e      ;
    return this;  
  }
  
   /**
   *      Name of table, relative to parent schem   a.
       * @return  na  me
  **/
  @jakarta   .annotation.Nonnull
  @JsonP roperty(JSON_PROP    ERTY_N     AME)
     @JsonInc      lude(value = Json I   nclude.I   nc     lude .AL WAYS)

     public St   rin                   g   getName() {
    return name;
  }


  @JsonProperty(JSON_PROPERTY                _NAME)
  @JsonInclude(value    = JsonI       nclude.Include.ALWAYS)
  public void setName(String name) {
        this.name = name;
  }


  p  ublic CreateTable catalogName(String catalogName) {
    
    this.catalog        Name = catal     og        Name;
     re  turn this;
  }

   /**
   * Name    of pare          nt catalog.
   * @return catalogName
  **/
       @ jakarta.    annotation.Nonnull
     @JsonProp erty(JSON_PROPERTY_CATALOG     _NAM   E)
  @JsonInclude(value = Js    o       nInclude.Include.ALWAYS)

  pub      lic String getCata  l   ogName() {
      return catalogName;
       }


    @JsonProperty(JSON_PROPERTY_CATALOG_NAME)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
         public void    setCatalogNa    me(String catalogNa     me) {  
         this.catalogName = catalogName;
      }


  pub lic Cre           at      eTable schemaName(String schemaN  ame)    {
    
    thi    s.schemaNa me = schemaName;
    retu    rn this;
      }

     /** 
   * Name of parent schema  relative to i    ts parent   catalog.
   * @  return schemaName
  **/
  @jak   arta.annotation.N onnull
  @J    sonProperty(JSON_  PROPERTY_SCHEMA_NAME)
       @JsonInclude   (value = J     sonInclude.Include.AL  WAYS)

      p  ublic String getSchemaName(  ) {
    r   etu rn schemaName;
     }
     

  @JsonProperty(JSON   _PROPERTY_    SCHEMA_NAME)
  @Json   Include(value      = JsonI   nclude.Include.ALWAYS)
  public   void setSchemaName(String schemaName) {
      this.sche maName = schema      Name;
  }


  publ    ic C     reat eTab  le t ableType(Tab           leType tableTy  pe) {
    
    this.tableT  ype = tableT  ype;
    return this;
  }

      /**
   *    Get table   Type
          *         @ return tableType
  *   */
  @jakarta.annotation     .Nonnull
  @JsonProperty(            JSON_PROPERTY_TABLE_TYPE)
  @JsonInclude(   value = JsonIn  clude.Include.ALWAYS)

      p   ublic TableType     getTableType() {
             retur    n tableType;
  }

     
  @JsonProperty(JSON_PROPERTY_TABLE_TYPE)
  @J   sonInclude(val   ue   = JsonInclude.In  clude.ALW AYS)
  public void set  TableType(TableTy        pe tableTy       pe) {
    this.tableType = tableType;
  }


  publ  ic CreateTa    ble dataSourceFormat(DataSourceFormat d   ataSour  ceFormat) {
       
    t       his.dataSourceFormat = d     ataSourceFormat;
    return       this;
  }

   /**
     * Get     dataSourc  e        For         mat
   *     @return   dataSour    ceFormat
    **/
  @jakarta.annotation.Nonnull
  @JsonProperty(JSON_       PROPERT Y_DATA_SOURCE_FORMA T)
  @Json      Include(value = Js onInclude.Inc lud e.ALWAYS)   

  public         Data     S  ourceFormat getDataS    ourceForm         at() {
    return dat  aSourceFormat    ;
  }


  @JsonP    roperty(JSON_PROPERT   Y_DATA     _SO   URCE_FORM AT)
  @JsonInc  lude    (val ue = JsonInclude.Include.ALWAYS)
  public void setDataSourceFormat(DataSour   ceF  ormat dataS   ource   Format) {
    this.dataSourceFormat = dat aSourceFormat; 
  }


  public CreateTable    columns(List<Co  lumnInfo>     c     ol    umns) {
        
     this.columns = columns;
    return this;
  }

  public CreateTable addColumnsIte  m(   Col umn  Info   columnsItem) {       
    if (this.       columns == nu    ll) {
      this.columns = n ew   ArrayList<>()  ;
    }
    this.co    lumns.add(columnsItem);
      return this;
  }

   /   **
             * The array of __ColumnInfo  __ definitions o f the table&#3    9;  s columns.
   * @retur   n columns
  **/
  @jakarta.annotati   on.N  onnull
    @    JsonP           roperty(JSON_PROPERTY_COLUMNS)
  @JsonIncl         ude(va     lue             = Json  I nclude.Include.ALWAYS)

  public List<Co      lu mnInfo>       getCol umns() { 
        r    eturn column   s;
  }

  
    @Jso nProperty(JSON_PRO   PERTY_COLUMNS)
  @JsonInclude(valu e = JsonInclude.  Include.ALWAYS)
  public void se      t  Colum  ns(List<C       olumnInfo     > columns) {
    this.columns = columns;
  }


   pub    lic C    reateT able storageLocation(String storageLocation) {
    
    this.storageLocation     = storageLocation;
        return this;
  }  

           /**  
   * Storage root URL fo   r ta  ble (f   or **MANAG   ED**, **EXTERNAL** tables)  
          * @re turn storageLocation
  **/  
    @jakarta. ann    ot       ation.Nullable
  @    JsonProperty(JSON_PROPERTY_STORAGE_LOCATION)
  @JsonIn    clude(value = Json       In  clude.Include.USE_DE        FAULTS)

  public Strin  g getSt    orageLocation()   {
    return storageLocation;
  }
 

              @JsonProperty(JSON_PROPERTY_STORAGE_LOCATION)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void set     Storag eLocation(St      r   ing storag    e    Location) {
    this.storageLocati  on = storageLocation;
  }


  public          CreateTable c        omment(String comme   nt ) {
    
    this.comment = comment;     
     return this;
  }

        /**
        * User-provided     f    ree-form text descri    pt ion.
   * @retur   n comment
  **/
  @jakarta.annotation  .Nullabl e
  @Js    onPrope    r ty(JSON_PROPERTY_COMMENT)    
  @JsonInclude(value = JsonInclude     .Include.USE_DEFAULTS)

  public Str      ing     g   etComm  ent() {
    retur        n     comment;
  }


  @JsonProperty(JSO N_PROP  ERTY_COMMENT)
        @JsonInclude(va     lue = JsonInclude.Include.USE_DEFA        UL     TS)
  public void   setComment(Stri ng comment)   {
    this.commen  t = comm   ent;
  }   

  
  p  ublic CreateT       able pr     op    erties(Map     <String, S     tring> pro  per  ti  es    )       {
    
    this.properties         =    pr oper    ties;
    ret   urn th  is;
  }

  publ  ic CreateTable putPr opertiesItem(String ke   y, String propertie   sItem) {
    if          (this.properties    == null) {   
         this.properti   es    = new Hash        Map<   >();
             }
    this.properti    es.put(key, propertiesItem);
    return this;
       }

   /**
   *      A   map of key-  value properties attached to the securable.
   * @return pro   perties
         **/     
     @jak      arta.annot     at ion.Nullable
  @JsonProperty(JSON_PROPERTY_PROPERTIES)
  @JsonInclude(value =   J  sonInclude.     Inclu     de.  USE_DEFAUL   TS)

            public Map<Stri    ng, String>  getProperties()        {
        return pr  operties;
  }


  @J  sonProperty(JSON_PROPERTY_PROPERT  IES)
  @JsonInc    l ude(value = JsonInclude.Incl  ude.U   SE_  DEFAULTS)
  public voi  d setProperties( Map<String, String> properties) {
    this.properties = properties;
  }

      @Override
  public boolean equals(Object o) {
    if    (this ==  o) {
      return true;
    }
       if (o ==  nu      l     l ||     g     etClass() != o.    getClass()   )     {
      return false;
    }
    Cre    ateTable createTabl    e = (          CreateTable  ) o;
    ret  urn Obj  ects.equals(this.name   , createTable.name) &&
        Objec     ts.equ als(this.catalogName, createTable.catalogName) &  &
        O   bjects      .equal s(this.s   chemaName, createTable.schemaName) &&
        Objects.equals(this.       tableType     , createTable.tableType) &&
         Obje c    t s.eq    ual s(t    his.dataSourceFormat , creat  e Table.dataSourceFormat)      &&
             Objects.eq          uals(this.columns,     createTable.columns) &&
          Objects.equals(this.storag   e  Locat ion, createTable.storageLocation) &&
        Ob  jects.e   q  u    als(t          his.comment, cre ateTable.comment)  &&
        Objects.equals(this.proper  ties, createTable.properties);
  }

  @Override
  public int hashCode() {
    return Objects.has   h(name, catalogNa   me, schemaName, tableTyp  e, dataSo  urceFormat, columns, storageLocation, comment, proper  ties);
        }

  @Override
  pub     lic  String     toString() {
    StringBuilder sb   =    n ew StringBuilder();
    sb.append("clas    s CreateTable {\n");
      sb.append("    name: ").ap  pend(toIndentedString(name)).append("     \n");
    sb.append("    catalogName: ").append(toIndentedString(catalogName))    .append("\n");
        sb.append("    schemaName: ").append(toIndentedString(schemaName)).append   ("\n"   );
    sb.   append("    tableType: ").a    ppend(toIndentedString(tableType)).append("\n  ");
    sb.append("    dataSourceFormat: ").append(t  oInden    tedString(dataSourceFormat)).append("\n")   ;
    sb.append("    columns: ").append(toIndentedString(columns)).append("\n");
    sb.append("      storageLocation: ").append(toIndented String(storageLocation)).append("    \n");
    sb.append("    comment: ")        .append(toIndentedStrin        g(    comment)).append("\n");
    sb.append("    properties: ").append(toIndent      edString(properties)).append("\n");
    sb.append("}");
    retur     n sb.toString     ();     
  }

  /**
   * Conv     ert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private St    ring toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }

}

