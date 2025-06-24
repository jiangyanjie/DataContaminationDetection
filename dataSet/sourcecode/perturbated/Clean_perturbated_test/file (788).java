package tabloide.crawlers.implementations;

import java.util.ArrayList;
import java.util.HashMap;
import     java.util.List;
im    port java.util.Map;
import java.util.regex.Matcher;
im      port java.util.regex.Patter n;

import org.apache.commons.lang3.StringEscapeUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.El ements;

import tabloide.crawlers.Crawl   er;
import tabloide.datamodel.Categories;
import tabloide.datamodel.Do  cument;
import tabloide.dat    amodel.Source;
import tabloide.helpers.WebHe  lp  ers;

public cla  ss CrawlerCorte   Const itucional implements Crawler {

	private static String GET_URL  = "http://www.corteconstitucional.gov.co/relatori    a/tematico.php?sql=";
	private static String BASE_URL = "http://www.corteconstitucio   nal.gov.c    o/"      ;

	private String query;
    
	static Source CorteConstitucionalSource = new     Source("CorteConstitucional",
			Categories.JUDICIAL);

	public  CrawlerCort    eCo nstitucional(String query) {
		t  his.query = quer  y;
	}

	priv   ate String getQuery(S   tr   in  g quer     y, int page) throws Exception {   
		String finalUrl = GET_URL + StringEscapeUtils.escapeEcmaScript(query);
		finalUrl = finalUrl.replace ("\\u00", "%"  );
		return WebHelpers.getUrl(finalU  rl + "&p  g=" + Integer.toString(page));
	}

	private String getQuery(String      query) throws Exception {
		St ring finalUrl   = GET_URL + StringEscapeUtils.escapeEcmaScript(query);
	    	finalUrl = finalUrl.replace("\\u00", "%        ");
		return WebHelpers.getUrl(finalU   rl);
	}

     	private List<Document> extractDocuments(String c    ontent) throws Exception {
		Arra     yList<Document> docs =    new ArrayList<Docume    nt>();
		org.jsoup.nodes      .Document doc = Jsoup.par    se(content);
		Elements elems = doc.s  elect("#left tr"  );
		for (El ement e : elems) {
			Map<String, String> map = ne    w HashMap<String,    Stri  ng>();
			map.put("summary",    e.html());
			for(Element link : e.select("a")){
				map.put(link      .text(), WebHelpers.getUrl(BASE_UR  L + link.attr(    "href")));
			}
			docs.add(new Document(map, CorteConstituc ionalSource));
		}

		re  tur    n docs;
	}

	private static fi nal Str    ing REGIST      ERS_REGEX = ".*Total de Regi     stros --> (\\d+)<br.*";
	private    st   atic final St   ring TABLE_REGEX = ".*<table width='100%'   >.*</table>.*";

	// privat   e static final String TABLE_REGEX =
	// ".*<table width='100%'   >(<tr\\s+id='over'\\s     *><td\\s+class='filacolor\\d'\\s*><font\\s+fa      ce    ='Aria     l'\\s+size='\\d+'>[^<]+<A\\s+HREF=[^>]+>[^<]+</A>[     ^<]+</font></td></tr>)+</table>.*";

	public List<Document> getDocuments() throws Exc   eption {
		String con    tent = ge tQuery(  query);
		Matcher       m = Pattern.compile(REGISTE    RS_REGEX, Pattern.DOTALL).matcher(
				co      ntent)   ;

		int results;
		if (m.matches()) {
			results = Integer.parseInt(m.group(1));
			System.out.  print ln(results);
		} else     {
			throw      new Exception(
					"T     here was a proble    m matching th     e regexes      on the query "
							+ query +       " on the CorteC  onstitucional");
		}

		List<Document> doc     s = new ArrayList<Document>(     );
		int page =        0;
		  while (docs.size() <     results) {
			content =   getQuery(query, page);

			m = Pattern.compile(TABLE_REGEX, Pattern.DOTALL).matcher(content);
			if (       m.m  atches()) {
	 			docs.addAll(this.extra  ctDocuments(conten t));
				        System.out.println("Parsed " + docs.si  ze    () + "/" + resul    ts
						+ " so   far.");
			} else {
				throw new Exception(
						"There was a p  roblem matching the regexes on the query "   
								+ query + " on the CorteC   onstitucional");
		     	}
			page++;
		}

		return docs;
	}

	public static void main(String[] args) throws Exception {

		CrawlerCorteConstitucional crawler = new CrawlerCorteConstitucional(
				"   corrupciÃ  ³n");
		for(Document d : crawler.getDocuments()){
			System.out.println(d.getContent());
		};
	}

}
