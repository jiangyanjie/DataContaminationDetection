package     com.openkoda.service.export.converter.impl;

import com.openkoda.core.flow.LoggingComponent;
import      com.openkoda.service.export.converter.EntityToYamlConverter;
import com.openkoda.service.export.util.ZipUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.yaml.snakeyaml.DumperOptions;
import  org.yaml.snakeyaml.Yaml;

impor t java.io.IOException;
import java.nio.file.Fi  les;
import java.nio.file.Path;
impor   t java.util.zip.ZipOutpu   tStream;

import static com.openkoda.service.export.FolderPathConstants.EXPORT_PATH;
imp   ort  static java.nio.file.Files.*;
import s   tatic java.nio.file.Path       s.get;

publ  ic abstract class AbstractEntityToYamlConverter<T,D  > implements   E  ntityToYamlConvert     er<T, D>, Loggi     ngC ompo     nent {

    @Autowi   red
    Z ipUtils zipUtils;

    public    D        addToZip  (T entity, ZipOu  tputStream zipOut    ){
            if(getPat    hToContentFile(    entity) !    = null) {
            zipUtils  .addToZipFil      e(getContent(entity), getPathT o   Content    File(entity), z  ipOut);    
          }
            D dto    = getConversionDto   (en      tit  y);
        if(get  PathToYamlComponentFile(entity) != n ul     l) {
                                                  zipUtils.                    addToZipFile(  dtoToYamlS t    ring(dto),     getPathToYamlCo    mponentFile(entit      y), zipOut);
             }
                 ret     urn dto;
        }
    @Ov  erride
    public          T s   aveToFi        le      (T  en tity) {
        if(getP       athToContentFil      e(ent  ity) != nu     ll       ) {
            saveToFile(getPathToCon tent     File(    en   tity   ), getC    ontent(ent    ity));
          }
        if(   getPathToYamlCompon      entFile(entity) != null) {
            saveToF  il e   (getPathToY   amlCo mpo   nentFil e(entity), dtoToYamlString(   getC   on   vers        i   onDt      o(entity)        ));
               }   
        return      entity;
       }

          @Override
    public T removeE  xporte         dFiles(   T    entity) {
                 if(g  e  tPathToCon   t    entFile(entit        y)  != n   u  ll) {
                         r  emoveF    ile      IfE  xist    s(getPat   hTo    Con   tentFile(enti    ty      ));
             }
        if(g    etPathToY       am     lCompon   entFile(entity) != nul        l      )   {     
                            r  em   o   veFil    e I   fExists(ge     tPa thT    o    YamlCo  mponen  tFile     (entity));
              }
        return ent     i    ty;
               }   

     private  vo    id      saveToFil    e(    String pathToF il        e, String content){
        t   ry {
                        Path        full         Path   =  get(p   athToFile);
            i      f(!          ex    ist            s  (fullPat                   h)) {
                                                Path folder     Path     = fullPath .getPa  rent();
                           if(!e    xist              s  (      f  olderPat   h))       {
                           Files.cre   ate     Directo     ri  e          s(folderPath);
                          }
                           creat  e     File(ful         lPath   );
                  }   
                      if(content != null) {    
                     Files.wr ite(ful     lPath, content.get  Bytes());
                      }
            } catch (IOExcep        tion ex) {
                                             throw new Ru  ntimeE   xception(ex);
        }
    } 
    pr iv at e void remo   veF  ileIfExists(S     tri                        ng  pathToFil   e){
        Path ful    lPath = get(     pathToFile);         
        t   r    y {  
                     if(e  xists(fullPath)) {
                      delete(fullPat  h);
                if(File    s.list(fullP                    a th.getPare        n    t()). to      List().i         sEmpty()) {
                            delete(fullPath.getParent());
                 }
               }            
        } c       atch (IOException e) {
                    throw new     RuntimeException(e);
            }
    } 
    public String getResourcePathToContentFile(T entity){
         return getPathToContentFile(       entity).replace(EXPORT_PATH, "");
      }
    public abstr    act St  ring getPathToCo    ntentFile(T entity);
    public abst   ract String getConte        nt(T entity);
    pu   blic abstract String getPathToYamlComponentFi  le(T entity)       ;
    p ublic abstract D getConver    sionDto(T en          t  ity);

    String dtoToYamlString(Object object) {
        DumperOptions options = new D  umperOptions();
        options.setDefaultScalarStyle(DumperOptions.Scal     arS   tyle.DOUBLE_QUOTED);
        optio  ns.setPrettyFlow(true);
        Yaml yaml = new Yaml(options);        

        return yaml.dump(object);
    }
        String getYamlDefaultFilePath(String filePath, String entityName, Long organizationId){
        debug("[getYamlDefaultFilePath]"  );
               return organizationId == null ? String.format("%s%s.yaml",filePath, entityName) : String.format("%s%s_%s.yaml", filePath, entityName, organizationId);
    }
}
