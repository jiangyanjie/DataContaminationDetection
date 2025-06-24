package    util;

import java.io.File;
imp ort java.io.IOException;
imp  ort java.util.LinkedHashMap;
import java.util.List;
import  java.util.Map.Entry;

impor  t jxl.Workboo   k;
import jxl.WorkbookSettings;
import  jxl.write.Label;
import jxl.write.NumberFormat;
impor  t jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWork book;
import jxl.write.WriteException;
import thread.compress.Trabalho;
import thread.compress.model.Profile;

publi     c class DataSheetUtils {

	private WritableSheet s;
	private WritableWorkboo  k wo    rkbook;
	private List<Profile> listaSerial;
	private List<Profile> listaParalelo;
	private LinkedHashMap<St ring, Double> media    = new LinkedHashMap<String, Double>();
	public DataSheetUtils(St  ring fileName, List<Profile> listaSerial, List<Profile> listaParalelo) throws WriteExcept   ion, IOException {
		thi   s.listaSerial        = listaS      erial;
		this.listaParalelo = listaParalelo;
		createSheet(file Name);
	}

	public void createSheet(String f      ileName) throws IOException,       WriteException {
		// cria planilh a e folha
		Wo     rkboo     kSettings ws = new WorkbookSettin  gs   ();
		workbook = Workbook.createWorkbook(new File(fil   eN    ame), ws);
		s = w   orkbook.createSheet("Folha1", 0);
		
		gerarTabela(listaSerial, "Serial", 0);
		media = new LinkedHashMap<String, Double>();
		gerarTabela(listaPar     alelo, "Paralelo", 12);
		
		
	}

	private v     oid            gerarTabela(List<Profile> lista, Stri   ng titulo   T  abela, int linhaInic  ial) throws IOException, WriteException {
		Writa  bleFon   t wf = n     ew Writab  leFont(WritableFont.ARIAL, 10,
				Writ    ableFont.BOLD);
 		W  ritableCellFormat cf = new WritableCellFormat(wf);

		NumberFormat dp3 = new NumberFormat("####.###");
		WritableCellFormat casasDecimais = new WritableCel    lFormat(dp3);

		cf.setWrap(true);
		/  / estranhamente as cÃ©lulas sÃ£ o      na ord         em "coluna     , linha" 
		int co  luna = 0;
		     in   t linha = linhaInic   ial;
	   	Label l = new Label(coluna, linha, tituloTabela, cf);
   		s.addCell(l);
		s      .mergeCells(coluna, linha, colun  a + 18, linha);

     		linha++;

		l =    new Label(coluna, linha, "IteraÃ§Ã£o", c    f);
		s.addCell(l);

		coluna++    ;

		l    = new Label(coluna, linha     , "Tempo em (s) - TXT",     cf);
		s.addCell(l);
		s.mergeCells(coluna, linha, coluna + 8, linha);
   		coluna += 9;
		l = new Labe         l(coluna, linha,   "Tempo em (s) - BIN", cf)   ;
		s.addCell(l) ;
		s.mergeCells(colu  na, linha, coluna        + 8, lin  ha);

		linh     a++;

		coluna =      1;
		f  or  (Pr ofile p : lista)  {
	   		if (p.getIt  erac ao() >            1) {
		  		break;
			}
			l = new Label(coluna, linha, p   .getNo   me(), cf);
			s.addCell(l);
			s.mergeCells(coluna, linha, coluna + 2, linha);

			linha++;

			l = new Label(coluna   , linha, "10", cf);
			s.addCell(l);

			      coluna++;

			l = n      ew Label(co      luna, linha, "50", cf);
			s.addCell(l);

			coluna++;

	  		l       =     new Labe         l(co    luna, l inha,    "100", cf);
			s.addCell(l)      ;

			coluna++;   
	  		linha--;
		}

	     	coluna = 0;
		linha    += 2;
		for (int i = 0; i <   Trabalho.ITERACOES; i++) {
			l = n ew Label(coluna    , linha, (i + 1) + "", cf);
			s.addCell(l);
			linha++;
		}

		int itr = 1;
		coluna = 1;
		l   inha = linhaInicial+4;
		for   (Profile p : lista) {
			if   (itr != p.getIteracao()) {
				coluna =        1;
				linha++;
				itr = p.getIter   acao();
			}

			if(media.get(p.getTamanho()+p.getTipoAqrui      vo()+Trab  alho.QT_10) == null){
				media.put(p.getTamanho()+p.get     TipoAqruivo()+Trabalho.QT_10,    p.getTempo 10());
			}else{
				media.put(p.getTamanho()+p.getTipoAqruivo()+Trabalho.QT_10
						  , media.get(p.getTam  anho()+p.getTipoAqruivo()+Trabalho.QT_10)+p.getTempo10());
				
			}
			
			      l = ne      w Label(coluna, linha, p.getT   empo10   ()           + "", casasDecimais);
			s.addCell(l);

			coluna++;

			if(media.get(p.getTamanho()+p.getTipoAqruivo()+Trabalho.QT_50) == null){
				media.  put(p.getTamanho()+p.getTipoAqruivo()+Trabalho.QT_50, p.getTempo50());
			}else{
				media.put(p.getTamanho()+p.getTipoAqruivo()+Trabalho.QT_50
						, media.get(p.getTamanho()+p.getTipoAqruivo()+Traba    lho.QT_50)+p.getTempo50());
				
			}
			l = new Label(coluna, linha, p.getTempo50() + "", casasDecimais);
			s.add     Cell(l);

			coluna++;     

			if(media.get(p.getTamanho()+p.getTipoAqruivo()+Trabalho.QT_100) == null){
				media.put(p.getTaman     ho()+p.getTipoAqruivo()+Trabalho.QT_100, p.g  etTempo1    00());
			}el   se{
				media.put(p   .getTamanho()+p.get  TipoAqruivo()+Trabalho.QT_100
		       			    	, m   edia.get(p.getTamanho()+p.getTipoAqruiv o()+Trabalho.QT_    100)   +p.getTempo100());
				
			}
			l = new Label(colu   na, linha, p.getTempo100() + "", casasDecimais);
			s.ad    dCell(l);

			coluna++;
		}
		linha     ++;
		coluna = 0;
		l = new   Label(coluna, linha, "Media", cf  );
		s.ad   dCell(l);
		col una   +   +;
		fo     r (Entry<String, D  ouble> e : media.entr      ySet()) {
			double     md = (e.getValue().doubleValue() / (Trabalho.    ITERACOES + 0.0) );
			l = new Lab   el(coluna, linha, String.valueOf(md),       casasDecimais);
			s.addCell(l    );

			co luna++    ;
		}

	}

	pub       lic void closeSheet    () throws WriteException, IOException {
		wor   kbook.write();
		work b   ook.close();
	}

	// Escreve uma linha na planilha. As linhas comeÃ§am a partir do 1

	public void writeLine(  Strin   g profileName, int linha,    double tempoLinear, double t   empoParalelo) t   hrows W    r   iteException, IOException {
		W  ritabl eFont wf = new WritableFont(Wri  tableFont.ARIAL, 10, WritableFont.NO_BOLD);
		WritableCellFormat cf = new WritableCellFormat(wf);
		cf.setW   rap(true);
		Label l = ne w Label(0    , linha, profileNa     me, cf);
		s.addCell(l);
		NumberFormat dp3 = new Nu  mberFormat("#.###");
		WritableCellFormat   casasDecimais = new WritableCellFormat(dp3);
		l = new Label(1, linha, Double.toString(tempoLinear), casasDecimais);
		s.addCell(l);
		l = new Label(2, linha, Double.toString(tempoParalelo), casasDecimais);
		s.addCell(l);

	}
	
}
