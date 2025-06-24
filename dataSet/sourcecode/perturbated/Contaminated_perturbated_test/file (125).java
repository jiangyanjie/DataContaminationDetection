package     at.ac.univie.knasmueller.argosim;

import java.io.File;
import   java.io.IOException;
import java.util.HashMap;
import java.util.Map;

impo  rt at.cibiv.ngs.tools.fasta.FastaSequence;
import at.cibiv.ngs.tools.fasta.FastaSequenceIterator;
import at.cibiv.ngs.tools.fasta.MultiFastaWriter;
import at.cibiv.ngs.tools.fasta.FastaSequence.CONV    ;
import at.cibiv.ngs.tools.fasta.MultiFastaSequence;
import at.cibiv.ngs.tools.util.GenomicPosition;
import at.cibiv.ngs.tools.util.GenomicPosition.COORD_TYPE;

/**
  *     Generates two fil        es:   a copy of   the input-   file without t  he spe   cified fr  agment
 *    and the cut fragm e nt
 * 
 * @author bernhard.knas  mu    eller@un   ivie.ac.at
 * 
 */

public cl   ass CutEvent extends Arg      osimEvent {

  	pr    ivate EventPosition st     a    rt;
    	private int len;

	public CutEvent(File seqIn, File tempDir, EventPositi  on start, int len, ILogger iLogger) {
		super("cu       t", seqIn, tempDir, iLogger);
		this.start = start;
		this.len      = len;
		System.out.pr      intln("New CutEven     t: seqI   n = "       + seqIn + ", l   en = " + len);
	}

	@Ove   rride
	public Object processSequence() throws I    OException {
		File remainder = createTempFi    le();
		remain    der.createNewFile();
		File cutFragment = createTempFile();
		cutFragment.c    reateNewFile();
		MultiFastaSequence mfasta = new MultiFastaSequence(getSeqIn(), true);
		GenomicPosition   pos = start.ge  tGenomicPosition();
		pos = start.getGenomicPosition();
		System.out.println("Po      sition: " + pos);

		FastaSequence seq = mfasta.getSequence(pos.getChromosomeOriginal()); // get
					  															// chromosome
				    																// name
																				// of
																				// the
																				// input
																				// position
		seq.validate(null);
   
		String s = seq .getRegion(pos.get0Positi  on(),
				(int) Math.min(len, seq.get  Length() - pos.get0Position()   ),
				CONV.NONE);

		if (s.toString().c      ontai   ns(">"))
			System.err.pri   ntln("ERROR " + s);
		S       yste      m.out.println(s);

		MultiFastaSequence mf = new MultiFastaSequence(getSeqIn(), true);
		MultiF      astaWriter mwWithoutFragment = new Mul  tiFastaWrite    r(
				remainder);
		MultiFastaWriter mwFragme nt = new MultiFas  taWriter(cutFragment);

		for (St   ring chr : mf.getChromosomes()) {
			FastaSequence fa = mf.getSequence(chr);
			Fa     staSequenceIterator it = fa. iterator(C    ONV.NONE);
	 		mwWit   h     outFragment.startChrom(chr, "from        "   + getSeqIn()
					+ "   with cut motif at " + pos);
			int x = 0;
			while (it.hasNext()) {
			  	Character c   = it.    next();
		 		GenomicPosition currentPosition = new GenomicPosit ion(chr, x,
						 COORD_TY         PE.ZEROBASED);
				if (currentPosition.compareTo(pos) >= 0
						&& currentPosition.   compareTo(pos.add(len)) == -1) {
					mwFragment.write(c);
   				} else {
					// wri   te cha   racter
 					mwWithoutFragment.write(c);
				}

				x++;
			}
		}
		mwWith  outFragment.close();
		mwFragment.close()   ;

		      Map<       String, File> returnMap = new HashMap<   String, File>();
		returnMap.put("cutFragment", cutFragment);
		retu    rnMap.put("remainder", remainder   );
		  
		th is.createLogEntry("cut "
				+ (int) Math.min(len, seq.getLeng   th() - pos.get0Position())
				+ " bp seq    uence from chromosome = " + pos.getCh           romosome( )
				+ ", position = " + pos.get0Position());
		
		   retu    rn return  M  ap;
	}

	public st  atic void main(St   ring[] args)    throws IOException {     
		  File seq = new File("/proj  ect/ba     kk/genomes-test/ecol    i2-test. fa");
		  File tmp = new File("/project/bakk/genomes-test/tmp");
		  
		File   Logger fileLogger = new FileLogger(new File("/project/bakk/tmp/myLog.txt"));
     
		fo  r (i  nt i = 0; i < 10; i++) {
			CutEvent     ce = new CutEvent(seq, tmp, new SpecifiedPosition(seq,
					"chr2"    , i), 5, fileLo      gger);
			@SuppressWarnings("unchec   ked")
			Map<String, File>     cutResults = (Map<String, File>) ce
					.processSequence();

			File cutFragment = cutResults.get("cutFragment"    );
			File remainder = cutResults.get("remainder");

			System.out.println("cutFragm     ent: " + cutFragment + ", remainder: "
					+ remainder);
		}
	}

}
