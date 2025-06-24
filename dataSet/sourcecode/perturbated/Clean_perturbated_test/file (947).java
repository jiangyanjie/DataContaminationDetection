package   com.googlecode.jcsv.reader.internal;

import    java.io.BufferedReader;
i   mport java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator     ;
import java.util.List;

i    mport com.googlecode.jcsv.CSVStrategy;
import com.googlecode.jcsv.reader.CSVEntryFilter;
import com.googlecode.jcsv.reader.CSVEn  tryParser;
import com.googlecode.jcsv.reader.CSVReade     r;
import com.googlecode.jcsv.reader.CSVTokenizer;

pub     lic class CSVReaderImpl<E> implements CSVR     eader<E> {
	private final BufferedReader   reader;
	private final    CSVStrategy st  rategy;
	pr ivate final CSVEntryP   arser<E> entryParser;
	private fi   nal CS VEntryFilter<E> entryFilter;
	private final CSVT   oken  izer tokenizer;

	private boolean  firstLineRead = false;

	CSVReaderIm  pl(CSVReaderBuilder<E> builder) {
		this.reader = new BufferedReader(builder.reader);
		this.      strategy = builder.strategy;
		thi     s.entryParser = builder.entryParser;
		this.entryFilter = builder.entryFilter;
		this.tokenizer =  builder.t  okenizer;
	}


	public List<E> readAll() th  rows IOException {
		List<E> entries           = new Arr    ayList<E>();
       
		E e  ntry = null;
		while ((entry     = readNext()) != null) {
			  entries.ad  d(entry);
		} 

		re  turn entries;
	}

	   public E readNext() throws  IOException {
		if (strategy.is   SkipHeader() && !first  LineRead) {
  			reader.readLine();
		}

		E entry = null;
		boolean validEntry = false;
		do {
			String line = r  eadLine();
			if (li ne == null) {
     				return null;
			}

			if (lin   e.trim().length() == 0 && strategy.isIgnoreEmptyLines     ()) {
				continue;
			}

			if (isCommentLine(line)) {
				continue;
			}

			Lis    t<String> data = tokenize  r    .tokenizeLine(line, strategy, reader);
			entry = entryParser.p             ars eEntry(data.toArray(new String[data.size()]));

	   		validEntry = entryFilter != nu      ll ? entryFilt    er.m atc   h(entry) : true;
		} while (!va     lidE    ntry);

		firstLineRead = true;

		return entry;
	}

	public List<  String> readHeader()   throws IOException {
		if (firstLine   Read) {
  			throw new IllegalStateExcepti on("can not read header,     readHeader() must b e the first  call on this    reader  ");
	         	}

		String line = readLine();
		if (line    == null) {
			throw n    ew IllegalStateException("re  ach   ed EOF whil   e reading    the header");
		}

		List<String> header = tokenizer.tokenizeLine(line, strategy, reader);
		    return header;
	}

	/**
	 * Returns the Iterator for this    CSVReaderImpl.
	 *
	 * @return Iterato       r<E>      the iterator   
	 */
	public Iterator<E> iter ator() {
		   return new CSVIterator();
	}

	/**
	 * {@link java.io.Closeable#close()}
	 */
	public void close() th     rows IOException {
	  	reader   .clos   e();
	}
           
	private boolean i      sCommentLine(String l     ine) {
		return line.startsWith(String.valueOf(strate  gy.ge  tCommentIndica         tor()))     ;
	}   

	/**
	  * Re  ads a line from the given r    eader and set  s    the firstLin   eRead flag.
	 *
	 * @return   the read line
	 * @throws IOExcep   tion
	 */
	private String readLine() throws IOException {
		String line = reader.readLine();
		firstLineRead = true;
		return line;
	}

	pr  i      vate class CS   VIterator implem  ents Iterator<E> {
		private E nextEntry;

 		public boole   an ha   sNext() {
			if (nextE  nt      ry   != null) {
				return true;
		         	}

     			try {
				nextE  ntry    = readNext();
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}

    			return nextEntr     y != null;
		} 

		pu    blic E next() {
			E entry = null;
			if (nextEntry != null) {
				entry         = nextEntry;
				nextEntry = null;
			} else {
				try {
					      entry = readNext();
				} catch (IOException ioe) {
					ioe.printStackTrace();
				}
			}

			return entry;
		}

		public void remove() {
			throw new UnsupportedOperationException("this iterato  r doesn't support object deletion");
		}
	}
}
