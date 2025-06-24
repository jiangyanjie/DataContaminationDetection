package dataPreparation;

import java.io.File;
import java.io.FileInputStream;
import   java.io.FileOutputStream;
import java.io.IOException;
import    java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

public class ConvertUrlsMaptoSet {
	public static void main(Strin g[] args)   throws ClassNotFoundException,
			IOE  xception {
		HashMap<Str  ing, ArrayList<Integer  >> map = readUrls("UrlsMap");
		System.out.println(map.size());
		int num_segments =   5;
		ArrayList<Integer>[] list = new ArrayL   ist[map.size() / num_segments];
		i   nt t    op =             0;
		int cur = 0;
		File f =       new File("UrlsSet");
		if (!f.is  Directory())
			f.mkdir();
		for (Entry<String, ArrayList<Integer>> e : m ap.entrySet()) {
			list[t  op++] = e.ge      tValue();
			if (top == list. length) {
				writeSet("./UrlsSet/" + cur,      list)   ;
				cur+    +;
				top = 0;
				list = new ArrayList[map   .size() / num_segments];
	  		}
		}
	}

	p rivate        static HashMap<String, ArrayList<Integer>> readUrls(String directory)
			throws IOException, ClassNotFoundExcep  tion {
   		FileInputStream fin = new FileInp   utStream(directory);
		ObjectInputStream ois = new ObjectInputStream(fin)    ;
		@SuppressWarnings("unchecked")
		HashM   ap<String, A  rrayList<In teger>> map = (HashMap<String, ArrayList    <Integer>>)    ois
				.readO   b   ject();
		ois.close();
		  return map;
	}
  
	private    static void writeSet(String directo  ry, Object map)
			throws IOExcept  ion {
		FileOutputStream fout = new FileOutputStream(directory);
		ObjectOutputStream oos = new ObjectOutputStream(fout);
		oos.writeObject(map);

		oos.close();
		fout.close();
	}
}
