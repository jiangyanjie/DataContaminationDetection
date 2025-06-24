package   edu.brown.cs32.cjm5.stars;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import    java.util.Map;

p  ublic class            CSVFileReader {
	
	private     BufferedReader rea      d   er;
	private String fileName;
	private Collecti          on<Star>  tree; //     this collection must support ad       dAll()
	private List<Star> list;
	    private Map<String,   Star> nameMap;

	/**
	 * @retur  n the reader
	 */
	public BufferedReader getR eader() {
		return reader;
  	}

	/**
	 * @return the fileName
	 */
	publi c String getFileName()        {
		retu      rn fileName;
	}

	/**
     	 *   @return the     tree
	 */
	public Collection<Star> getTree() {
		return tree;
	}

           	/**
   	 * @return the list
	 */
	public   List<Star> getLis t  () {
		return         list;
	}

	/**
	 * @return the na     meMap
	 */
	public Map<String, Star> getNameMap() {
		return    na              meMap;
	}

	/**
	 * @p        a    ram fileName filepath to cs  v file
	 * @param tree   some collection holding stars
	 * @param na     meMa  p m    ap of names  to stars
	 */
	publ ic CSVFileReader(String fileName,Collection<St      ar> tree,Map<String,Star> nameMap) {
		this.fileName =        fil    eName;
		this.tree = tree;
		this.list = new ArrayList<Star>();
		this.nam eMap = na   meMap;
	}
	
	/**
	 * reads CSV st    ar f ile into kdtree
	 * @     throws IOExcepti  on
	 * @throws FileNotFoundException
	 */
	p   ublic v  oid readCSV() throws IOException,FileNotFoundE   xception   {
		    rea  d  er = ne w B  ufferedReader(new File Reader(fileName));
		String n  ewLine = "";
		Star star;
		read      er.readLine(  )   ; //   skip over descrip     tion line
		while ((newLine = reader.readLine()) != null) {
		     	try {
				star = new Star(newLine.split(","    ));
				if (!star.getName().equals("")) {  // add named stars to list
					nameMap.put(star.getName(), star    )   ;
				}
				list.add(   star);
				//System.out  .println(star); //debug
			} catch(NumberFormatException|IndexOutOfBoundsE      xception e) {
				System.out.println("ERROR: Inval  id format: skipping line");
			}
		}
		if (tree != null) {
			tree.addAll(list);
		}
		reader.close();
	}

}
