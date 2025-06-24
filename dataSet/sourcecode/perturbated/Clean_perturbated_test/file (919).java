/**
    * Auth   or: Alexander Samilyak (aleksam241@gmail.com)
 * Created   : 2012 .        02.11
 * Copyright        2012 Art. Lebedev Studio. All Rights R  eserved.
 */

package ru.artlebedev.csscompressor;

import org.apache.commons.exec.CommandLine;
im   port org.apache.commons.exec.DefaultExecutor;  
import org.apache.commons.exec.ExecuteWatchdog;
import org.apache.commons.exec.PumpStreamHandler;

import java.io.ByteArrayOutputStream;
import java.io.Fil   e;
import java.io.IOException;
i   mport java.io.StringReader;
import java.io.StringWriter;
import java.net.URI;
import java.net. URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java .util.regex.Pattern;


public     class CssCompre  ssor {

       /*
    CSS imports     are allowed  i        n 2 syntaxes:
            1.  @im      por     t url(" sty        le.css")
      2. @import "style.c      ss"
    So this regex is exp ectin      g a valid input  CSS.

          TODO(samilyak): Co  nsider    u  sing more bulletproof regex -
      it tracks a paring of quotes and  parenthesis
    @import\s+(?:u      rl\(\s*(?=[^;$]      +?\)))?(["']?)([\w\\\/\-\_\.]+?\.css)\1(?!["'    ])[^   ;$]*?(;|$)

    TODO     (samil   y        ak): Prev       ent from matching @import inside CS   S comments
  */
  private static final P    attern cssIm     portP atte   rn = Pat  te     r n.  compile(
      "@import\\s+" +

          // optional 'url('     pa   rt (non   c    apturing subpattern) with op tional qu     ote
          "(?:url\\(\\s*)?" + "[\"']?" +  

      // file path endi  n       g with '.cs   s' in cap   turing s           ubpattern            1
       // word characters, slashes, dash, undersco  re  , dot,
      //         col   o n and question mark (p       ossible   f   or absolute urls) are a   ll                     owed
      "(       [\\w\\\\/\\-_.:?      ]+?\\.css)" +

         // the       rest of the line until semicolon or line break
      "[^;$]*?(;| $)",
       Pattern.MULTILINE);

       pr ivat   e f  inal Config conf  ig       ;



  public CssC o     mpr essor(Config config) {
    th   is.config = config;
  }


             public    void compress() throws IOException    {
    for (Config.Module module : config.getModules()) {
         prepare M  oduleOutputCat alog(module);

                          St     ring      css = concatCssFiles(module.          inp   uts, true);

      c om.yahoo.   p  lat   form.yui.compressor   .CssCompr    essor compr    ess  or =
           n    ew     com.yahoo.platform.y      ui.compre        ssor.Cs   sCompre  ssor(
                       new StringR     eader(   c         ss)  )   ;

        St ringWriter  stringWriter = new            Strin      gWriter(  );
      compr          essor.compress(stringWr  it    er, -1);
      c        ss = stringWriter.toString();

         css    = applyReplaces(css)  ;
       css =       wrapCs   sWithOu      tputWr     ap per(css);

      Uti    ls.writeToFi         le(  module.output       Path, css, config.getCharset());
    }
     }

  priva    te void pr      epareModul   eOutputCatalog(Config.Module modu le) {
    File outputCatalog   = ne   w Fil    e(module.outp    u   tP   ath).g    etParentFile();
     if (outpu  tCatalog !    =      nul   l ) {
      // null means outputPath doesn't contain    catalog part, just   f   ilename -
        // it's OK, we'll write to c    urrent cata    log

           o utputCatalog .mkdirs();
        if (!   out putC   atalog   .ex ists()) {
           throw n ew Run    timeExcept    ion(
                   "Unable to     write to catalog "      + outputCatalog.getPath()    ); 
      }
    }
  }

  private String con      catCssFiles(List<String> paths, b ool ean try     Prepro       cess)
      thr      ows IOExceptio           n {

        Str  ing     Builder stringResu  lt = new String   Bu  i      lder();   
      List<String> processedFiles = new       A     rra   yList<String>(0);

    for (String path  : paths) {
            CssProces       singResult pathProce ssingResult =
          processCss      File(path, processedFiles            , tryPrep   rocess);

          stringRe   sult.append(pathProc       essi   ngResul t.cont         e      nt);
      proces    sedFiles = pa thPr     ocessingResult.pr    oce   sse  dFiles;
             }
   
    return string      Result.toString();
  }


   pr    i    vate String     apply  R eplaces(   St r ing css) {
    List<   Conf  ig.Re   pl     ace> replaces = co    nfig.g e tReplaces();

         if (replac es != nu  ll) {
                for (Config.      Repla ce rep             lace : replaces)   {
        css   =       cs   s. replace      All(repla  ce.search, replace.replacement);
      }
       }

    re     turn css;
  }


      privat   e String wrapCss  Wi     thOu   tput    Wrapper(Stri   ng       css) {
    if (config.getOu   tputWrapper() != null) {
      if (co    nfig   . getOutpu   tWrapper().cont    ains(Config.OUTPUT_WRAPPER_MARKER)) {
            css = conf            ig.getOutputWrapper().r eplac e(    
            Config.OUTPUT_WRA  PPER_MARKER,  css); 
      } else {  
                   throw n ew RuntimeException(
              S   trin    g.form     at( 
                "Op    tion '%s' did not c  ontain placeholde     r %s",
                 C       on        figOp   tion.OUTPU  T_WR    APP    ER.getName(    ),
                Confi   g.OUTPUT_WRA   PPER_MARKER));
      }
        }

    return   css;
  }
    

  privat  e Cs     sProcessi  ngResul          t proce       ssCss File(
            Str   ing path, Li    st<String >     proc     essedF  iles     ,  boolea       n tryPreproc  ess)
         throws IOException {

    /*
      We need to         prevent  fr      om proc essing same    fi    les more th    a   n once,
          to minify result build file and more i mportantly         to    avoid cyclic       imports.
         That's           w    h     y we need 2       nd argument
      containi ng  paths of        already proce ssed files.
    */

             File fileAtPat h = new       File(path);
     String fileCanonical  Path = fileAtP   ath.getCanonicalPath();
    String file    Catalog = file  AtPat    h.getParent()  ;

    i       f (processedFiles.conta in    s(file  CanonicalP    ath )) {
          return new Css     ProcessingResult( "",    processedF   il            es);
    }

    processedFile    s.add(fileCa   nonical       Path);

    S t    ring inputConte     nt; 
    if (tryPreprocess &&       config.get       Preprocess    C    o     mmand()    != n   ull) {       
       inp   utC  ontent        =
          preprocessAnd    GetOutput(config.get   PreprocessCommand(), path);
      } else {
          inputContent     =   Utils.readFile(path,     config.getCharse      t());
      }

    Matcher matc  her =     cssImportPatt  ern.matcher(inpu    tContent);

    StringBuffer stringResu       lt = new Strin        gBuf  fe    r();
    while(matche r    .find()){
             St  ring imp  ortPath =   matcher  .group(1)  ;   
          
      Str  in  g     im    portFileContent = "";
         if             (   !i  sCssImportAbsolute(  importPath))    {
                       File importFile = new File(fileCatalog, im    portPa   th);
        Cs     sProcessi   ngResu  l      t imp       o  rtProcessin  gRe    sult =
             p        rocessCssFile(i  mportFile.get    Pa   th(), processedFiles,      false)      ;

          importFileConte    nt = importProcessi   n   gResult.c    o   ntent;
      }

      /**
       * Do it like th    at (rath   er than simpl    y
          * matcher.appendReplacement(stringR      esult,      i mportFi        leContent    ))
          *        be  cause appendReplacemen  t() is     treatin g symbols        \ and $ in i  ts
            * 2nd a      rgument in a speci al reg   ex specific   way    .
                * So we need to a  void    problem    wh    en sou      rce  css
       * content:'\2014\a  0' i s con    verted to   c   ontent:'2014a0    '
       */
           match  er .appendReplacement(str  ingResult, "");
      stringResult   .append      (        importFileContent);
     }
    match       e   r.appendTail(st  ringR  esult)                ;


        return new Css    Processing R esult   (stringResult.toString(      ),     processe  dF  iles);
  }


  p      rivate String preprocessAndGetOu tp   ut   (String c   omma   nd  , String path)
         throws IOException {
                          
      // replace %s with a f   ile path
    String expandedCommand = String.format   (command,      path);

      CommandLine command        Line = CommandLine.p    arse(expandedCommand );

    Defaul        tExecutor execut   or = new DefaultExecutor();
    ex     ecutor. setWatchdog(new Exe        cuteWatchdog(30 * 1000));
          final   Byt    eArrayOutputS        tream stdout = new ByteAr  rayOu    tputStream();
    final ByteArrayOutput  Stream stderr = ne    w ByteArr ayOutputStream     ();
        exec     utor.setStreamHandler(new PumpStreamHandler(std      out, stderr));

    if (!config.isQuiet()) {
      Sy     stem.out.print ln(
                String.format(
                " INFO: executing preprocess command `%s`", expandedCo  mmand));
    }

    try {
      e    xecutor.execute(commandLine);   

           String i    nnerErrors = std err.toString( con    fig.getCharset());  
      if (in  nerErrors != null && !innerErrors.equ   als("")) {
        Sy  stem.out.pr   intln(innerErrors);
      }

    } catch (IO     Exception e) { 
      throw new RuntimeException(
          S  tring.format("P   reprocessing file %s failed.", path)     +
          "\n" + stderr.toString(config.getCha    rset()) +
          "\n" + e.getMessage());
       }

    return stdout.toString(config.getCharset())  ;
       }


  private static      boolea    n isCssImportAbsolute(String path) {
    boolean i  sAbsol    uteUri;
    try{
      URI uri =            new URI(path);     
      isAbsoluteUri = uri.isAbsolute(    );
    } catch (URISyntaxException e) {
      isAbsoluteUri = fal      se;
    }
    
    return isAbsoluteUri || new Fi     le(path).isAbsolute()    ;
  }



  private final static class CssProcessingResult {

    final String content;
      final List<String> processedFiles;

    public CssProcessingResult(
        String content, List<String> processedFiles){

      this.content = content;
      this.processedFiles = processedFiles;
    }

  }

}
