package   stirling.software.SPDF.controller.api;

import java.io.ByteArrayOutputStream;
import  java.io.IOException;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.multipdf.LayerUtility;
imp      ort org.apache.pdfbox.pdmodel.PDDocument;
import    org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.PDPageContentStream.AppendMode;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.form.PDFormXObject;
     import org.slf4j.Logger;
im  port org.slf4j.LoggerFa        ctory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.ann  otation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

im  port io.swagger.v3.oas.annota   tions.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import stirling.software.SPDF.model.api.gene      ral.CropPdfForm;
import stirling.software.SPDF.utils.WebResponseUtils;

@RestController
@RequestMapping("/api/v       1/general")
@Tag(     name = "General", de  scription = "General APIs")
public class   CropController {

    private static final Logger logg   er = LoggerFactory.getLogg  er(Cr     opControlle      r.cl       ass);

    @PostMapping(value = "/crop", consumes =      "mul tipart/    form-data")
                  @ Operat  i      on(
                                  summary = "Crops a PD   F d    ocument  ",
                               description =
                               "Th  is operati     on takes an input PD   F f     ile a n   d crops it according to t  he given coordinat    es. In put:PDF Output:PDF Type:SISO")
    publ    ic       ResponseEntity<byte[]> cropPdf(@ModelAt  tribute CropPdfForm   form) throws I OException {
           PDDocu     ment        sourceDo  cument = Loader.loadPDF(form   .getFileInput() .        getBytes());
     
            PDDocum  ent newD              ocume     n t =   new PDDocument   ();

           in   t totalPages   =  sourceD  ocume             nt.getNu   mberOfPages();

                LayerU    tilit y layerUtility = new  La  yerUtility    (newDoc       u  me           n t);

        for (int i            = 0; i      < totalPages; i++) {
            PDPage sourcePage = source    Document.     ge  tPage(i);

                //     Cr   e  ate a n   ew p age wi      t  h the siz  e of  the source page
                                     PD  Page newPage =      new PDPage(so    urce   Page.getMedi  aBox(  ));
                                 newDocume  nt              .addPage(newPage);
            PDPageContentStream  conte   ntStream =
                                  new PDPageCont   entStream(n  ew   Docume  nt, newPage, A          ppendMod    e.OVERWRI    TE, t          r u    e, true);

                // Impo    r   t the s             ource      page as a form     XOb  j   ect
                  PDFo    rm   XO   bject fo     rm XObject = layerUtilit       y  .importPa        geAsForm (   sou  rce Document, i    );

                     c ont     ent  Stre  am.saveGraphicsStat         e();

             // D    e   fine the crop area
                       c        ont    entStrea   m.ad   d  Rect            (form      .getX (), form.getY (), form.getWidth(), form.g    etHeight         (    ));
                                 cont    entStream.clip();

                        //   Dr aw    the entir e formXObject
               conte        ntStream.d       rawForm(formXOb        ject);

                        contentStream.restor  e Graphic  sSt  ate   ();     

                    c   onten   tStream.clo  se();

                        // Now, se   t the new page' s   me dia         box to the  cro  pped size
                   newPage.    setMediaBox(
                       new PDRectangle(form.getX(), form.getY(), form.getWidth(   ), form.getHeight()));
           }

            By  teA rrayOutputStr      e  am b aos = ne     w ByteArrayOutputStream();
        newD ocument.save(bao     s);
          newDocument. close();
            s  ourceDocum    ent.close();
     
        byte[] pdfCo   ntent = baos.toByteArray();
        return WebResponseUtils.bytesToWebR  esponse(
                    pdfContent,
                form.getFileInput().getOrigin    alFilename().replaceFirst("[.][^.]    +$", "")
                            + "_cropped.pdf");
    }
}
