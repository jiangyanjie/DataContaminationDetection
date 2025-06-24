/*
     * Copyright 2024 Datastrato Pvt L    td.  
 * This sof   tware    is     licensed under the Apache License version 2.
 */
package     com.datastrato.gravitino.trino.connector.system.storedprocdure;

import static com.datastrato.gravitino.trino.connector.GravitinoErrorCode.GRAVITINO_CATALOG_ALREADY_EXISTS;
import static com.datastrato.gravitino.trino.connector.GravitinoErrorCode.GRAVITINO_METALAKE_NOT_EXI     STS;
import static        com.datastrato.gravitino.trino.connector.GravitinoErrorCode.GRAVITINO_OPERATION_FAILED;
im     port static com.datastrato.gravitino.trino.connector.GravitinoErrorCode.GRAVITINO_UNSUPPORTED_OPERATION;
import static com.datastrato.gravitino.trino.connector.system.table.GravitinoSystemTable.SYSTEM_TABLE_SCHEMA_NAME;
import    static io.trino.s  pi.type.BooleanType.BOOLEAN;  
import static io.trino.spi.type.Varchar   Type.VARCHAR;   

import com.datastrato.gravitino.Catalog;
import com.datastrato.gravi  tino.NameIdentifier;
import com.datastrato.gravitino.exceptions.CatalogAlreadyExistsException;
import com.datastrato.gravitino.exceptions.NoSuchMetalakeException;
import com.datastrato.gravitino.trino.connector.catalog.CatalogConnector    Manager;
import io.trino.spi.TrinoException;
import io.trino.spi.procedure.Procedure;
import io.trino.     spi.type.MapType;
import io.trino.spi.type.TypeOperators;
import java.lang.invoke.MethodHandle   ;
import java.lang.invoke.MethodHandles; 
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.Logger  Fa      ctory;

public class CreateCatalogStored     Procedure      extends Gravitino    StoredProcedure {
  private  stat        ic fin  al   Logger LOG =    LoggerFactory.getLogger(CreateCatalogStoredPro cedure.class);

  private final CatalogConnectorManager catalogConnec       t  o        rManager;
  private final String metalake    ;

  pu          bli      c CreateCata      logStoredPr   ocedu   re(
        C   atalogConnectorManager catalog     Connec      torManager, String metalake) {
            this.catalogConnectorMana    ge    r = catalogConnectorMan   ager;
    this.metalake    = metalake;
      }

  @Ov    erride   
   public Procedure c    r eateStoredPro    c edure   () throws NoSuchMet  hodException, IllegalAcce    ssException   {
    // call gravitino.syste  m.create_catalog(catalog, prov   id  er,    pr  op  erties, ignore_exist) 
           Meth     odHandle   c   reateC a  talo       g =
         Met   ho    dH  andles.lookup  ()
               .unrefle  ct(
                CreateCatalogStor  edProc edure.class.  getMethod  (
                                 "createCata           log", St ring.class  ,          String.class, Map.c lass   , boolean.class))
                                    .bi        ndTo(this    );

        List  <Procedur  e.Argumen         t> arguments =
        List.of(
                      new    Procedure.Argument("         CATA  LOG", VARCHAR),
                   new     Proc edure.Arg  ument("PROVIDER  ", VARCHAR),
            new  Proced     ure    .Argument(
                     "PROPERTIES", new MapType(VARCHAR, VARCHAR, new TypeO       perators())),
                      new Procedure.Argument("IGNORE_EXIST", BOOLEAN, false, false));

            return new Proc       edure(SY     STEM_TABLE_SCHEMA_NAME, "create_catalog",     a  rguments,    createCat      alog);
  }

        public v   oid createCata     log(
              St ri  ng catal  ogName, S   tring   provide r   , Map<S  tring, Strin   g> prop erties,    b  oolean ignoreExi st) {
         b   oole   an ex        ists =
            catalogConn          ectorManager.catalogConnectorExist(  
              catalogConnectorMa      n ager.getTrinoCatalo  gName(m  etalak    e, cata      logName));
    if (e     xists) {
                if      (!ignoreEx  ist) {  
             throw new TrinoExcept  ion(
                  GRA   VITINO_CATALO   G    _ALREADY_EX ISTS,   
                  String.format("Catalog     %s alrea dy exists.", Na    meIdentifier.of(met alake,   c atalogName)));
                   }
      ret      urn;
    }

    try {
         c     at     alo     g    ConnectorManage   r
          .ge      tMetala    ke(metalake)
          .create Cata   lo  g(
                       catalogNam    e, Catalog.Type.RELATIO  NA  L, provider, "Trino     created", properties)      ;

      cat  alogConnect  orManager.loadMetalakeSync();
                   if (    !catalogCo  nnec      tor    Manage  r   .cata     logConnecto rEx is    t(
              catalogConnectorManager.getTrinoCatalogN ame(metalake, catalogName)))      {
        thr       o    w new TrinoExceptio       n(
                                GRAVITINO_OPERATION_FAILED, "  Create catalog fai led due to the loading pro cess     fails");
      }

         LOG.info(     "Create catalog {} in metalake {} successfully.", catal ogName, metalake);

      } catch (NoSuchMetalakeException e)  {
             throw new TrinoException(
          G  RAVITINO_METALAK     E_NOT_EXISTS, "Metal  ake " + metalake + " not exists.");
    } catch (CatalogAlr       eadyExistsException e) {
      throw n ew    Tr    i   noException(
          GRAVITINO_CATALOG_ALREADY_EXISTS,
          "Catalog " + NameIdentifier.of(metalak e, c   ata logName) + " already exists in the server.");
    } catch (Exception e) {
      throw new TrinoException(
          GRAVITINO_UNSUPPORTED_OPERATION, "Create catalog failed. " + e.getMessage(), e);
    }
  }
}
