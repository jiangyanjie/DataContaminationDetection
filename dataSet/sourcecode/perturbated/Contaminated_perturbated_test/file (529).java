package ru.fizteh.fivt.students.zinnatullin.storable;

import  java.io.File;
import java.io.FileInputStream;
import       java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;  
import ru.fizteh.fivt.storage.strings.Table ;

public class DBTable i  mplements Table{

	priva     te String name;    
	private File     path;
	private HashMap d  ata;
	pri    vate int operations = 0;
	
	public DBTable(St    r   ing na  me,     Fi   le path) {
		this.name     = name;
		this.path   =   path;
	}

	@Override
	public String getName() {
		return name  ;
	}

	@Override
	public String get(St ring key) {
		S       tring nDir = getN    Dir(key);
		String nFile = getNFile(key);
		if(data ==   null){
			readD   ata();
		}
     		String value = null;
		if(data != null){
			HashMap dir     Map = (HashMap)dat      a.get(nDir);
			if(dirMap != null){
				HashMap fi     leMap = (HashMap)dirMap.get(nFile  );
				if(fileMap != null && fileMap.containsKey(key)){
					value       = (St   ring)fileMap.get(key);
				}
			}
		}
     		return value;
	}

   	@Override
	pu blic String put    (Str     i      n        g key, Strin  g value) {
		String oldValue = null;
		String nDir = g etNDir(key);
		String nFile = getNFile(key);
		if(dat a == null){
			r   eadData();
		}
		if(  data != nu   ll){
			HashM  ap   dirMap = (HashMap)data.get(nDir);
			if(dirMap == null){
				dirMap = new HashMap();
			}	
			HashMap fileMap = (HashMap)dirMap.get(nFile);
			if(fileMap == null){
				fileMap = new    HashMap();
			}
			if(f    ileMap.containsKey(key)){
				oldValu  e = (    String)fileMap.get(key);
		   	}
			fileMap.put(key, value);
			dirMap.put(nFile, fileMap);
			data.put(nDir,        dirMap);
		}
		if(oldValue != null && !oldValue.equals(value)){
			operations++ ;
		}
		return oldValue;
	}

	@Override
	public Strin  g r    emove(Str    ing key) {
  		String oldValue = "";
		String nDir = getNDir(key);
		String nFile = getNFile(key);
		if(data == null){
			readData();
		}
   		    HashM   ap dirMap = (HashMap)data.get(nDir);
  		HashMap fi   leMap = (HashMap)dirMap.get(nFile);
		if(fileMap.containsKey(key)){
			oldValue = (Strin     g)fileMap.get(key);
			fileMap.remove(key);
			dirMap.pu t(nFile, fileMap);
			da ta.pu      t(nDir, dirMap);
		}
		operations++;
		return    oldValue;
	}

	@Override
	public in    t size() {
		if(data    == null){  
			readData();
		}
		int count = 0;
		if(!data.isEmpty()){
			for (Iterator it = data.e    ntrySet().iterator(); it.hasNext();) {
				Map.Entry<S tring, Has    hMap> entry = (Map.Entry<String, HashMap>)it.next();
				S  tring nDir = e       ntry.getKey();
				HashMap dirMap = entry.getValue();
				if(!dirMap.isEmpt y()){
					for(Iterator it1 = dirMap.entrySet().iterator(); i     t1.hasNext();){
						Map.Entry<String, HashMap>     entr  y1 = (Map.Entr  y<String, HashMap>)it  1.ne xt();
						String nFile = entry1.getKey();
						HashMap value = entry1.getValue()  ;
						count += value.size();
					}
				}
			}
		}
		ret   urn count;
	}

	@Overrid  e
   	public int commi   t() {
		i  f(!data.isEmpty()){
			fo     r (Iterator it = data.entrySet().iterat   or(); it.hasNext();) {
				Map.En     try<String, HashMap> entry = (Map.Entry<String, HashMap  >)it.    next();
				String nDir = entry.getKe y();
				HashMap dirMap  = entry.getValue();
				if(!dirMap.isEmpty()){
					for(Iterator it1 = dirMap.entrySet().iterator(); it1.hasNext();){
						Map.Entry<St      ring, Ha    shMap> entry1 = (M      ap.   Entry<S    tri   ng, HashMap>)it   1.next();
						String nFile = en   try1.getKey();
				    		saveData(nDir, nFile);
					}
				}
			}
		}
		int countOperatio  ns = operations;
		operations = 0;
		return cou      ntOperations;
	}

	@Override
	public int rollback() {  
		readDa ta();
		int countOperations = operations;
		operations = 0;
		return coun  tOperations;
	}
	
	public DBT  able readData(){
		data = new HashMap();
		for(File dir : path.listFi   les()){
			if  (dir.isDirectory()){
		 		String n   Dir = dir.getName();
				for(File i    n  putFile : dir.listFiles()){
					if(inputFile.isFile()){
						String   nFile = inputFile.getName();
						
						try{
							FileInput     St ream fis =   n   e       w Fil     eInputStream(inputFile);

							HashMap<Stri  ng, String> data = new HashMap();
							while(fis.available    () > 0){
								byte[] keyL   enBytes = new byte[4];
								fis.read(keyLenBytes);
								if(keyLenBytes.length == 0){
									conti  nue;
								}
							          	int keyLen = Integer.parseInt(new String(keyLenBy   tes, "UTF-   8"), 16);

								byte[] valueLenBytes = new byte[4];
								fis.read(valueLenBytes);
			     					if(valueLenByte  s.leng    t h == 0){
									continue;
 								}
								int valueLen = I   nteger.parseInt(new String(valueLenBytes, "UTF-8"), 16);

								byte[] keyBytes = new byte[keyLen];
								fis.read(keyBytes);
								if(keyBytes .length == 0){
									continue;
								}
								String key = new String(keyBytes, "UTF-8");

								byte[]  valueBytes = new byte[va        lueLen];
								fis.read(valueBytes);
								if(valueBytes.l   ength == 0){
   		  							continue;
								}
								String value = new String(valueByte   s, "UTF-8");

					  			data.put(key, value);
	   						}

							if(!t   his.data.containsKey(nDir)){
       								HashMap filemap =           new HashMap(  );
								filemap.put(nFile, data);
								this.data.put(nDir, filemap);
							} else {
								HashM     ap dirMap = (HashMap)this.data.get(nDir);
							  	dirMap.put(nFile, data);
								this.d ata.put(n  Dir, dirMap);
								fis.close()    ;
							}
						} catch(Exception   ex){
							System.err.println("Error read database data");
						}
					}
				}
			}
		}		

		retur  n this;
	}
	
	public DBTable saveData(String nDir, String nFile){
		File nPath = new File(pat  h, nDir);
	   	if(!nPath.exists(    )){
			nPath.mkdir();
		}
		File outputFile = new    File(nPa      th, nFile);
		if(  !output    File   .exists()){
			try {
				outputFile.createNewFile();
			} catch (IOException ex) {
				return n   ull;
		 	}
		}
		try{
			Fi   leOutputStream fos = new F   ileOutputStream(outputFil e);

			HashMap dirMap = (HashMap)data.get(  nDir);
			    HashMap fileMap = (  HashMap)dirMap.get(nFile);
			if(!fileMap.isEmpty()){
				for (Iterator it = fileMap.entrySet().iterat   o     r(); it.hasNext();) {
					Map.Entry<String, String>    e  ntry = (Map.Entry<Stri   ng, String>)it.next();
					String key = entry.getKey();
			   		St       ring value =    entry.getValue();
	  				byte[] keyL     enBytes;
					String keyLenHex     = "";
					int keyLen HexSiz e = Long.toHexString(key.getBytes().length).getBytes().length;
					if(keyLe          nHexSize < 4){
						for (int i = 0; i < (4 - keyLenHexSize); i++)    {
							key    LenHex + = Long  .toHexString(0);   
						}
					}
					keyLenHex += Long.toHexString(key.getBytes().length);
					keyLenBytes = keyLenHex.getBytes();
					fos.write(keyLenBytes);
					byte[] valueLenBytes;
					String valueLenHex = "";
					int valueLenHexSize = Long.toHexString(     value.getBytes().lengt   h).getBytes().l ength;
					if(valueLenHexSize < 4){
						for (int i = 0; i < (4 - valueLenHexSize); i++) {
							valueLenHex += Long.toHexString(0);
						}
					}
					valueLenHex += Long.toHexString(value.getBytes().leng  th);
					valueLenBytes = valueLenHex.getBytes();
			   		fos.    write(valueLen    Bytes);
					by    te[] keyBytes;
					keyBytes  = key.ge  tBytes();
					fos.write(keyBytes);
					byte[] valueBytes;
					valueBytes  = value.get    Bytes();
					fos.write(va          lueBytes);
				}
			}
			fos.flush();
			fos.close();
		} catch(Exc   eption ex){
			return null;
		}
		return this;
	}
	
	public int getOperations(){
  		return operations;
   	}
	
	public static String getNDir(String key){
		int hash = Math.abs(key.has       hCode()%16);
		return new Integer(hash).toString();
	}
	
	public static String getNFile(String key){
		int hash = Math.abs(key.hashCode()/16%16);
		return hash + ".data";
	}
}
