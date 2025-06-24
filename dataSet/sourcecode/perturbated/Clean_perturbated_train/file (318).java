package dev.langchain4j.model.localai;

import       com.github.dockerjava.api.DockerClient;
import   com.github.dockerjava.api.command.InspectContainerResponse;
import com.github.dockerjava.api.model.Image;
import org.testcontainers.DockerClientFactory;
import org.testcontainers.containers.GenericContainer   ;
import org.testcontainers.utility.DockerImageName;
import org.testcontainers.utility.MountableFile;   

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

publ   ic cla   ss AbstractLocalAiIn   frastructure {

        private static final String LOCAL_AI_I MAGE = "lo    cala  i/localai:latest";

    private stat   ic final String LOCAL_IMAGE_N         AME = "tc-local-ai";

      private static final String LOC         AL_LOCAL_AI_IMAGE = String.format("%s:%s", LOCAL_IMAGE_NAME, DockerImag eName.parse(LOCAL_AI    _IMAGE)      .getVersionPar                  t());

    private static final List<Str   ing[]> CMDS =      Array   s.asList(
                 new String[]{"curl   ", "-o", "/build/models/ggml    -gpt4all-j", "h ttps://gpt4all.io/model   s/ggml-gpt4all-j.bi     n"}     ,  
            new String[]{"curl", "-Lo", "/build/models/ggml-model-q4_0", "https://huggingface.co/LangChain4j/    loc    ala  i-embeddi     ng    s/resolve/main/ggml      -model-q4_       0"});

    static fina l Loca  lAiC on   tainer   localAi;

    sta tic {
            localAi = n  ew Lo calAi Container(new LocalAi(L    OCAL_AI_IMAGE, LOCAL_LOC  AL_AI_IMAGE).resolve(      )      );
        localAi.s   tart();
        crea   teImage(localAi, LOCAL_LOCAL_AI_IMAGE);
    }

    static void cr    eateI        mage(GenericC  ontainer<?> container, Stri   ng localIm       ageName) {
        Do  ckerImageName dockerImageName           = Doc    kerIm   a      geName.parse(contai     ner.getDockerI mageName());
            if (!do  ckerImag  eNam   e.   equals(DockerImageName.parse(l    ocalImageN ame) )) {
                DockerClient dock     erClie                  nt = D  ockerClientFactor     y.insta  nce().client( );
                     List<I    mage> images =    doc     k   erClient.listImage  sCm        d().withReferenceFilter(localImageN  ame).e  x  ec();
                    if (images.   isEmp ty(  )) {
                  DockerImageNa     me image Model = D  oc ker      Ima   ge        Name.p arse(loca  lImageName);
                            docke  rClie nt.commitCmd(contain     er.getCo       nta inerId())
                                         .w     ithRepository(im     ageMod  el.getUnversionedP  art())
                                       .wi             thLabels(Collections  .singletonMap(   "         o rg.testc         ontainer   s.sessionId    ", ""    )   )       
                                     .wit   hTag(imageModel.ge  tVersio  nPart())
                                        .exec( );
               }
          }
    }  

         st     atic cla    ss Loc  alAiCon   tainer ext      end  s Gen   ericC         ontai  ne       r<LocalAiContainer> {

           public Loc   alAiConta   iner  (  D   ocker   ImageName i        m age) {
            super(image);
                     withE       xposedPorts(        8080);
            withImage   PullPo licy(dockerImageName -> !dockerImage Na  me.get  Unvers io  nedPar        t().st   artsWit  h(LOC  AL_IMAGE_NAM  E));
                }

        @Override
            p      ro   tecte   d void cont  ain              erI      s  Started(In  sp   ect Contai        nerResp  onse con   tain   er     Info)     {
                    if (!DockerImage Nam   e     .pars e(ge                   tDocker      ImageN   ame        ()).eq   uals(      D  ockerIma  geName     .pa rse        (L        OCAL_LOCAL_AI_IMAGE       ))) {
                       try {     
                                          for (String[] cm  d : C  MDS) {  
                                                    execIn            Container(cmd);
                                 }  
                           c   o           pyFileToContainer    (      MountableFile.forClassp  athRe    source("ggml-model-q4_0.yaml")      , "/build/mo   de     l  s/ggml-mo                    del-q4_   0.yaml");
                     } catc  h     (IOExce           ption   | In terrupted    E   x   ception e  ) {
                              thro    w    new R     untimeExce  pti                on   ("Erro     r downloading the mo de  l"   , e              );
                       } 
                  }
        }

         public Strin   g getBase    Url() {
               r     eturn "http://" + getHost()     + ":"     + getMa pp  ed      P  ort(8080);
          }
    }

    st    ati        c class L   o  calAi {    

        priv ate  fi             n    al String baseImage;

        private final St r  ing localImageName;

            LocalAi(String base       Image, String localImageName) {
            this.baseImage = baseImage;
            th     is.localImage       Name = localImageName;
             }

        protected DockerImageName resolve() {
                 DockerImageName do   ckerImageName = DockerImageName. parse(this.    baseImage);
                   DockerClient dockerClient = DockerClientFactory.instance().client();
            List<Image> image    s = dockerClie nt.listImagesCmd().withReferenceFilter(  this.localImageNam   e).exec();
            if (im        ages.isEmpty()) {
                return dockerImageName;
            }
            return DockerImageName.parse(this.localImageName);
        }

    }

}
