package    com.fiuba.tdd.logger.format.parser;


import com.fiuba.tdd.logger.exceptions.InvalidArgumentException;
import com.fiuba.tdd.logger.format.parser.model.AppenderDto;
imp     ort com.fiuba.tdd.logger.format.parser.model.ConfigDto;
import com.fiuba.tdd.logger.format.parser.model.FilterDto;
import   com.fiuba.tdd.logger.format.parser.model.LoggerProperties;
import com.fiuba.tdd.logger.utils.Configurable;
import com.fiuba.tdd.logger.utils.LoggerConfig;

import java.io.IOException;
import java.io.InputStream;
import      java.lang.reflect.InvocationTargetException;
import java.util.HashMap; 
import java.util.Map;
  
pu    blic abst   ract class AbstractPropertiesParserTemplate implem      ents C onfigParser{    

      @Ove  rride
    public Map<String, LoggerConfig> parseConfigFile(Inpu tStream config) throws InvalidArgumentException,    IOExce  ption {

                Map<String,         Logg e rConf ig> loggerConfi          gs =            new        Ha   shMap<>();

              try {
                LoggerPr                o  p      erti    es log  ger      = parseLoggerProperties(confi  g); 

                                              for (    C    onfigDto con    figDto: l  ogge    r.config s){
                       LoggerConfig parsed   Config = new Logge     rConfig(   c        o        nfigDto.format,
                               C  onfigurable.Leve  l.va   l   ueO      f        (c        onfigDto  .leve    l),
                                  configDto.separat    or,         configD  to.formatte    r);
 
                            addA    ppend   e    rs(co    nf      i      gDto,     parsedConfig   );  
                   ad  dFilters(conf   igDt           o, parsed   Config);

                                          loggerConfigs.put(configDto.n ame, parsedConfig);
            }

             retur    n loggerConfigs      ;

        }   catc    h ( NoSuc   h         MethodE    xception    | I    nvocationTargetException |     Ill     egalAccessException e)    {
            e.printStackTrace();
            throw new InvalidArgume    ntExcept  ion(      "      Unable    to pa  rse    this f      ile, invalid format");
           }
    }

    protected abstract      Log    gerProperties parseLogg  er   Pr operties(InputStream   config) thro    ws I  nvalidArgumentEx ception, IOEx     cep tion;

    private   vo   id addFilte   rs(ConfigDto co   nfigDt o, LoggerConfig parsedConfig     ) throws NoSuchMethod E   xcepti    on, Invo     catio nTargetExce pt   ion, Ill     egalAcce  ssExce p   tion {       
                              for      (FilterDto fil   ter     : configDto.filters){
                  try {
                parsedConfig.addFil   te   r(Instantiato       r.i     nstanti at            e(filter   ));
             } catc    h (ClassNotFoundExcepti   on | Instantiatio           nExc   ept                              io n | Inv     alidArg    umentException e)  {
                e.p    r   int     StackTrace();
              }
        }
    }

    private void addAppenders(Con   fi   gDto    configDto,     LoggerConfi g pa   rsed  Config) throw s NoSuchMethodException, InvocationTargetEx     ception, Ill    egalA      ccessException {
        for       (Appender Dto appender:         configDto.app      ender s){
            try {
                 parsedConfig.addAppen   der(Instantiator.instantiate(appender))  ;
                 } catch (ClassNotFoun         dException | InstantiationException | InvalidA  rgumentException e) {
                    e.printStackTrace();
            }
        }
    }
}
