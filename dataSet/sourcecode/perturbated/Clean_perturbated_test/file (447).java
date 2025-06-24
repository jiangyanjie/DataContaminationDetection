package    analysis;

imp   ort java.text.BreakIterator;
imp   ort java.util.ArrayList;
    import java.util.L    ocale;
import java.util.StringTokenizer;

      import org.apache.commons.lang3.StringUtils;

public cl    ass ContentAnalysis {
	public stat ic     String[] negativeWo   rds = {  "disappoint", "abando   ned", "ab   use", "ab use  d", "accused", "addicted", "afraid", "aggr avated", "aggressive", "agony", "anger", "angered", "angry", "anguish", "annoye      d", "annoying", "anxiety", "anxious",       "argumentative",   "arrogant", "artificial", "ashamed", "assaulted", "at a loss ", "at risk", "atrocious", "attacked", "avoided", "awfu  l", "awkward", "bad", "ba    ckfire", "badgered", "baffled", "banned   ", "barren", "beat",        "beaten down", "belittl     ed", "berated", "betrayed",           "bitch", "bi      tched at", "bitching", "bitchy", "bitter", "bitterness",    "bizzare", "blacklisted", "blackmailed", "   b      lamed",         "blasphemy", "bleak", "blown away",       "blur", "bogus" , "bored", "boring", "b     ossed-around", "bothered", "bothersome", "boun     ded",    "boxed-in", "bo      z o", "        bruised", "brushed-off   ", "bugged", "bullied", "   bully", "bummed", "bummed out", "bummer", "burden  ed", "burdensome  ",     "burned", "burned-out", "    caged   in", "can wo rms",       "    careless", "chaotic", "chased",    "cheated"       , "cheated on"   , "chicken  ", "churlish", "claustrophobic", "clingy", "clueless", "clumsy", "coaxed", "c  oerced", "cold", "cold-hearte  d", "combative", "complain", "comp  lained", "comp ulsive"  , "conceited", "concerned", "condescended to", "confined", "conflicted", "confronted", "conned", "consumed", "c   ontem plative", "contempt", "contentious", "co  ntri     te", "contriti    on", "convict ed", "cornered", "corralled", "corrupt", "c  owardly", "crabby", "cramped", "cra     n ky   ", "crap", "crappy", "crass", "crazy"    , "creepe  d out", "creepy", "critici    zed", "cruddy", "crummy",     "c  rushed   ", "cut-down", "cut-off", "cynic  al", "damn"    , "d   amned", "dangerous", "dark", "dazed", "dead",      "deceive    d", "deep", "defame", "defamed", "defeated", "defective", "defenseless", "defe     nsive", "defiant", "de fici  ent", "deflated", "degraded", "dehumanized", "dejected", "delicate", "deluded", "delusion", "demand  ing", "demeaned", "de mented", "demorali    zed", " d    epleted     ", "depraved", "depressed",      "depression",          "deprived", "deserted   ", "deserving of pain   /punishment", "desol   ate", "despair", "despairing", "desperate", "desperation", "despica    ble", "despise   d",     "destroy", "destroyed", "destructive", "    detest", "     detestable", "de tested", "devalued", "devastate", "devastated", "devastatio   n", "dev  iant", "devious", "devoid", "diag nosed", "dictated to", "directionless", "dirty", "   disagreeable", "disappoin   ted", "disappointing"     , "disappointment", "dis   appoints", "disapproved     of", "dis    bel      ieved"     , "discardable", "discar         ded", "di  sconnected", "discontent", "discouraged", "discriminated", "disdain", "disd   ainful", "disempowered", "disenchanted"    , "disgr   aced", "disgruntled", "disgu    st", "disg   usted", "d  ishearte  ned", "dishonest", "dishonor", "dishonorable", "d ishonour", " dis   illusione  d", "disli  ke", "disl   iked", "dismal", "dismay    ", "dismayed", "disorganized", "disoriented", "diso  wned", "dis ple ased", "disp  osable", "disregarded", "disrespected", "dissatisfied", "distant", "distracted", "distraught", "      distress", "distressed", "disturbance", "disturbances", "disturbed",      "dist urbing", "dizzy", "domine  ering", "d   on't like", "doomed", "double-crosse   d", "     doubted", "doub    tful", "down and out"    , "down in  the dumps", "downhearted", "do   wntrodden",  "drain         ed", "dramatic", "dr  ead", "d      readful", "dreary", "dropped  ", "drunk", "dry", "dumb", "dumped", "d     umped on", "duped", "edgy", "egocentric", "egotistic", "egotistical", "elusiv    e", "emancipated", "e masc       ulated", "embarass", "embarassed", "embarassm   ent", "embar  rassed", "embitter", "emotionally bank   ru         pt", "emotionless", "encum   bered", "endangered", "enrage", "enraged", "enslaved", "entangled", "ev    aded", "evasive", "evict    ed", "exasperate", "exasperation", "excessive", "e  xcori   ate",  "exhaus     ted", "exp   lode", "exploited", "explosion", "exposed", "fuck", "fai lful", "fake", "feckless", "fed up", "flawed", "forgetful", "   forgettable",      "forgotten",    "fragile", "fr   aud", "fr    eak out", "freaked out", "frightened"           , "frigid", "frustrate", "frustrated", "frustrating", "fuck", "fucked  ", "fucker", "fucking", "fucks", "furious", "gaddamit", "gl    oomy", "gl     um", "go   ddam", "g oddamn", "gothic", "grey", "grim", "g   ross",    "grossed-out"  , "grotesque", "grouchy", "gr  ounded", "grumpy",   "guilt-tripped", "guilty   ", "hack   les", "harass", "harassed", "harassment", "hard-hearted", "harmed",    "hassled", "hate", "hateful",       "hat red", "haught y", "hau  nted", "hear   t broken", "heartbreak",    "heartbroken", "heartless",     "heavy-hearted", "heinous",   "hell pay", "helpless", "henpecked", "hesitant   ", "h     ideous", "hinde red", "hopeless", "horrible", "horrified",      "horror", "hostile", "hosti    lities ", "hostili   ty", "hot-tempered", "humiliate", "humiliated", "humiliation", "   hung over", "hung up", "hurried", "hysterical", "idiot", "idiotic", "ignominy", "ignorant", "ignored"    , "ill will", "il   l-tempered", "imbalanced", "imposed-upon", "impotent", "imprisoned", "impulsive", "in the dumps", "incapable", "inco mmunica  tive", "incompe   tent", "indecisive", "indignant", "indignation", "indoctri     nated", "   inebriated", "ine ffective", "inefficient", "inferior  ", "infuriate", "infuriated", "inhibited",   "in humane", "injure", "injured", "injust  iced", "insane",   "insecure", "insidious", "insignificant", "in    sincere", "insu      fficient"   , "insulted",   "intense", "interro  g  ated", "inte  rrupted", "intimida    t   ed", "intoxicat    ed",      "invalidat    ed", "inv     isible", "irate", "irrational",    "irr      itabl  e", "irritate", "irrit  at  ed",    "irritating", "isolat ed", "jackass", "jaded", "j     ealous", "jerked around", "joyless", "judg ed", "kept quiet", "lame", "l   ast rites", "laughable", "laughed at", "lazy      ", "leaned on", "lectur     ed to", "left out", "let     down ", "lied abo ut", "lied to", "livid",   "loathe", "lonely", "lonesome   ", "longing", "lost", "lousy"    , "l  oveless", "ma  d", "madden", "made fun of", "man h     andled", "manipulated", "ma   sochistic", "megalomania", "mend   fences", "    messed up", "  messed with", "messy", "miffed", "miserab  le", "misery", "misled", "mista           ken", "mistreated", "    mistrusted", "misunderst  ood", "mixed-up", "mocked", "m   ole    sted", "moody", "moron", "morose", "mortification", "     mortified", "nagged", "needy", "nervous", "neurotic", "nightmare", "nightmarish", "noisome", " nonconforming", "noxious", "numb",     "nuts", "nutty", "objectified", "obli gated", "obloquy", "obnoxious", "obsessed", "obsessive", "ob structed", "odium  ", "  o ffen     ded", "offense", "on display  ", "oppressed", "opprobrium", "outrage", "o    ver-  controlled", "over-protected", "overbeari     ng", "overwhelmed", "pa in", "pa ined"       , "pani  c", "paranoid", "passive", "pathetic", "p      atronizing", "pessimistic", "petrified", "petrify", " petrify   ing", "phony", "picked on", "pissed", "pissed off", "       plagiariz   e", "played with", "pooped",    "powerless", "pre-judged", "preached to", "predjudiced", "preoccupied", "pressured", "pr os       ecuted", "provoked", "psychopathic", "psychotic", "   pulled apart", "      pulled back", "punished", "pushed away", "put down", "puzzled", "quar        relsome", "quee    r", "quixotic", "rage", "raped", "rat    tled",  "ravage", "reprehensibl e", "resented", "rese ntful", "retarded", "revengeful", "ridiculed", " ridiculous", "robbe d", "rot  ten", "rude", "sad", "sad news", "saddle", "sadisti    c",      "sarcastic"  , "scared", "scarred  ",   "screwed", "screwed over", "screwed up", "seedy", "self-centered", "self-destr  uctive", "self-hatred", "selfish", " senile", "se    pul     chral", "shame", "shamef   aced", "shameful", "shee    pish", "shouted at"    , "singled-o ut", "slander", "sloppy", "smothered", "snappe     d at      ", "sonofabitch",  "sophistry", "so   rdid", "sorrow", "spiteful", "stereo  typ   e      d", "stressed", "stretched", "struggle", "stupefied", "stupid     ", "submissive", "suffer", "suf  fering " , "suffocat  e", "suffo        cated", "suicidal", "supercilious", "superficial     ", "su      ppressed", "swo llen heade   d", "ta       ntrum", "terrible", "torment", "tormented", "travesty", "troubl   e", "umbrag          e"   , "unctuous", "unsa  vory", "unsavoury", "vai      n", "vainglorious",   "vap  id"    , "vendetta", "villainous", "violence",     "vitriol", "vituperative", "vulgar", "w  ishy washy", "worthl    ess", "wrath", "wreak ",  "  confused", "critical",   "do  wn", "hurt",      "kept away", "kept     out", "l imited", " pushed",  "stuck", "suspicious", "shorts   ightedness", "wreaking", "w    rongful", "     wtf", "yu    cky"  };
	public static String[] stopWords =    {"a", "about", "above", "   across",      "after", "afterwards", "aga     i     n", "aga    inst",   "all", "almost", "al      one", "alo    ng", "alrea         dy", "also", "alth    ough", "al   wa   ys", "am", "among", "amon   gst"         , "amo       ungst", "a  mount", "an", "and", "another", "any", "any  how", "anyone", "anythi  ng", "anyway", " anywhere", "are", "around", "as",     "at", "back", "be", "became", "because", "becom  e", "becomes", "becoming"   , "been", "before", "b    eforehand", "behind", "being", "below", "   b      eneath", "beside   ", " besides", "   between", "beyond", "bill",    "both",    "bottom", "but", "by ", "call", "       can", "canno   t", "cant", "co", "con", "could", "c    ouldnt",     "cry", "de", "describe", "detail", "do", "done", "down",   "due", "during", "each" ,    "e g", "eight", "either", "eleve  n", "else",               " elsewhere", "empty", "enough", "etc", "even", "ever"   , "every", "everyo   ne", "everything", "everywhere", "except"              , "few", "f if   teen", "fify", "fill", "fi    nd", "fire", "first", "  five", "for", "former", "formerly", "forty",    "found", "four"  , "fr   om", "front", "ful  l", "further", "get", "        give", "go", "had",  "has", "    hasnt", "have", "he", "   hence", "her", "her e          ", "hereafter", "hereby", "here    i     n", "hereupon", "her    s", "herself", "him", "himself", "his", "h    ow", "howev     e    r", "hundred", "i", "ie", "if", "in", "in front   of" , "inc", "i n     deed", "inside", "instead of", "interest", "into",    "into", "is", "it", "its", "itse  lf", "keep",  "last", "latter", "latterly", "lea  st",    "less", "ltd", "made", "ma        ny", "may", "me", "meanwhile", "might", "mill", "mine", "more", "moreover" ,      "most", "mostly", "move", "much", "must", "my", "myself", "name", "namely", "near", "neither", "never", "nevertheless", "ne    xt", "nine",       "   no", "nobody", "none",   "noone", "no         r", "not", "not hing" ,          "now", "nowhere", "of", "off", "often", "on      ", "on top of", "once", "one", "only", "onto", "or", "other", "o   thers", "otherwise", "our   ", "ours", "ourselves", "out", "out of", "outside", "over", "o  wn", "part", "past", "per", "perhaps", "please", "put"            , "rather", "re", "same", "see", "s    eem"      , "seemed", "seeming",   "seems", "serious",  "      several", "she", "should"    ,   "show", "side",    "since        ", "sin     cere", "six",    "sixty", "so", "some", "s   omehow", "someon  e", "s  omething  ", "    sometime", "somet  imes", "some  whe  re", "still", "such"  , "system", "take", "ten", "than", "that", "the", "their", "them       ", "the        mselves", "then",    "the nce", "there", "thereafter", "thereby", "therefore", "therein",   "there upon"    , "t     hese", "they", "th  ick  v", "thin", "third",  "this", "those   ", "though ", "three", "         through", "through   out      ", "thru", "th   us",      "to",  "together", "too", "toward", "towards",      "twelve", "twenty", "two", "un    ", "under", "underneath", "until", "up", "u  pon", "us", "very", "via", "was", "we", "well",        "were", "what", "whatever"    , "when", "whence", "whenever", "where", "whereafter", "whereas", "w hereby", "wherein", "whereupon", "where  v  er", "whether", "which", "while", "whith  er", "who", "whoever", "whole", "who   m", "  whose",      "w  hy", "will", "with", "within", "wit   hout", "would", "yet", "you", "your", "yours", "yourself", "yours   elves"};
	public static St  ring[] p     osi  tiveWords = {"congra  tulations", "+1", "a shining ex    ample", "a      step ahead", "absolutely", "absorbing", "abundance", "ace", "active", "admirable", "adore",       "adulation", "agree", "agre    ed", "al   ert", "alive",  "amaz ement", "amazing", "appealing      ", "approval", "aroma",     "aston  i   sh", "astonished", "asto    nishing", "astounding", "attraction", "awa rd", "awe inspiring", "awesome", "bargain", "beaming",    "beats", "beaut     iful"     ,       "best", "  better", "bits", "bl   issful", "boost", "bounce", "breakthrough", "bright", "br      ighten        s    up", "brilliant", "brimmi   ng", "care", "celebra   te", "celebration", "charming", "chic", "choice",    "clean", "clo    ud nine", "colorful", "comedy"  , "comf   y",  "com  pli     ment", "confidence", "congrats", "congratulate", "cong     ratulated", "congr  atulating", "    connoiss   eur", "consensus", "cool", "cou       rteous",    "coy", "crea my", "crisp", "cuddly", "dazzling", "debonair", "   delicate      ", "delicious", "delightful", "deluxe", "  dependable", "desire    ", "diamond"       , "discerning", "dist   i   n cti      ve", "divi     ne", "does the trick", "dreamy", "drool", "dyna       mic", "e       asy", "ecstasy", "ec static", "efferves ce     nt", "efficient",   "electrified", "electrifying", "endless", "energy", "enhance", "enjoy", "enormous", "en     rap     tured", "ensure", "en  ticing", "es    sence", "essentia   l", "euphoria",    "exactly",    "    excellent", "except ional", "excited", "exciting",  "exclusive", "exhilaration", "exotic", "expert", "exquisite", "extol", "extra", "eye-catching",      "fab", "fabled", "fabulous", "fair", "famous", "fantastic", "fascinati     ng",  "     fashio  nab le", "fast", "fav       orite", "felicitate", "felicitation", "f   estivity", "fetching",    "finesse",    "        finest", "fizz", "flair", "flattering", "flourishing", "foolproo f", "fragrance",    "freshn  ess", "friendly", "fun", "f  unny", "galore", "generous", "genius", "gentl   e", "giggle",   "glad", "g  lamoro    us", "gleeful", "glitter", "glorious", "glowing"    , "go-ahead", "golden", "good", "good news", "go      odness"      , "gorgeous", "gracefu   l"    , "g        ra  nd", "grateful", "gratitude", "great" , "guaranteed", "gush", "ha ha", "    haha", "hahaha", "ha    ppine    ss", "happy", "hea lthy", "heart warming", "heartwarming", "heaven sent", "heavenly", "high spirits"     , "hu  mor", "humour", "ideal", "im  maculate", "impressive", "incredible", "inspire", "instant", "interesting", "invigorating", "invinci       ble",     "inviting", "irresistible", "jew   el", "joke", "joy", "joyous", "jubilation", "juicy", "k.o  .",    "keenest", "kind", "kissable", "leads", "legend", "leisure", "l       i   ght    ", "like", "liked", "lingering   ", "logical", "lol",     "love ", "loved", "lovely",   "lucky", "luscious", " luxurious", "magic", "magnifies it", "making an im   pressio n", "marvel", "marvellous", "marvelous", "matchless",  "maxi"    ,      "memora  ble", "mighty", "mindblowing", "miracle   ", "nice", "nicely", "nutritiou    s", "o.k.", "omg", "on the right fo  ot", "ooh", "oooh", " ooooh", "opulent", "outlasts", "outrageous", "outs    tanding", "   pala        te", "palatial",    "pamper", "paradise", "passionate", "peak"   , "pearl", "perfect", "pick-m   e-up", "plea     ses", "ple         asure", "plenty  ", "plum", "plum    p", "plus", "popul    ar", "posi tive", "power", "pre e minent", "pre      cious", "preeminent", "prefer", "prestige", "priceles s", "pride", "prime", "pri   ze", "protect   ion", "proud", "pure", "quality", "quench     ing", "rad     iant", "rapt          ure", "rather", "rave", "rav     ishing", "real", "really cool", "reap", "recommendatio    n", "refined", "refreshing", "rela     x", "reliable", "remarkable", "remarkably", "renowned", "r   eputation", "rest", "rew    arding", "rhapsodic", "rich", "ri   ght", "rosy", "royal  ", "safe  ty", "satisfaction", "save", "scores", "seductive", "sens   ational", "sensitive", "serene", "service", "sex  y", "shapely", "sheer",             "s   hy", "simple", "sizzling", "skilful", "slapstick", "slick", "smas   hing", "smiles", "smooth", "soft", "solar", "sound", "sounds good", "sparkl     ing", "special", "spectacular"   , "speed", "s  pice", "spicy", "s    plend  id", "spotless", "spruc  e", "standing ovation", "star", "strong", "stunning", "stupendo  us", "stylish", "subtle ", "success", "succulent", "super", "su    perb", "superlative", "supersoni    c", "supreme", "sure", "sweet"  ,  "swell", "symphony", "tan", "tangy", "tas  ty", "tempting", "ter rific", "tha   nk god", "thank goodness", "thankf ul", "the no 1", "tho    roughbred", "thrill", "thrilling", "thriving", "timeless", "tingle",    "tin   y", "tota  lly", "traditional"    , "transform       ation" , " treasure", "treat", "trendy", "true", "trust", "ultimate", "unbeatabl   e", "unblemished", "unden     iably", "undoubtedly", "un     questionably", "unrivaled", "unsurpassed", "v.i.p.",    "valuable", "  valu      ed", "vanish", "varied", "versatile", "vic   tor", "vigorous", "vintage", "vital", "vivacious", "walking on air", "warm", "wee", "   well", "whiz", "w hole", "whopper", "winner", "wise",     "wonderful", "worthy", "wow", "wow!", "youn  g", "you   thful",  "yule", "zap", "zeal", "zest", "zip"};

	  p   ublic static int findNumChara       cters(String plainContent   ) {
		return plainC     ontent.length();
	}

	public static int findNumSentences(Stri ng co  ntent) {
		int result = 0;  
		BreakIterator iterator = BreakIterator.   get   SentenceInstance(Locale.US);
		iterator.setText(content);
	 	for (int end = iterator.next(); end != BreakIterator.DONE; end = iterator.n     ext()) {
			res     ult++;
		}
		
		retu  rn result ;
	}

	public static ArrayList<String> findS  ent  ences(String content){
		ArrayList<String> sentences = new ArrayList<String>();

		BreakItera   tor iterator = BreakIterator.getSe     ntenceIns    tance(Locale.US);
		iterator.setText(content);
		int start = iterator.first();
		for (int end = iterator.next(); end != Br   eakIterator.DONE; start = end, end = iterator
				.next()) {
			sentences.add(cont   ent.substring(start,end));
		}
      
		      return sentenc    es;
	}
	
	public static in t findNumPlusOnes(String content) {   
		if (content != null    && !content.equals(""))
			if      (conte       nt.contains("+1 ") ||     content.contains("+1.")
					|| content.contains("+1ing") || content.contains("+1-ed"))
				r       eturn 1;

		retur   n 0;
	}

	public st     atic int findNumThan ks(String content) {
		if (content != null     && !content.equals(""))
			if (content.con  tains("Thanks") || content.contains("t   hanks")
					|| content.contains("Thank you")
					|| content.contains("thank you")
					|| cont       ent.contains("thx"))
				return 1;

		return 0;
	}

	public static int findNum NegativeWords(String content) {
		int numNegativ eWords = 0;
	  	if (content != null && !content.equa         ls("")) {
			ArrayList<String> sentences = findSentences(co    nte    nt);
			for (String sentence :      se    nte n  ces) {
				sentence = sentence.toLowerCase ();
				for (in t i = 0;    i < negativeWords.length; i++)
					if (sentence.matche  s(".*\\b" +      negativeWords[i].to  LowerCase() + "\\b.*")
							&& !(sentence   .conta ins("doesn't")
									||       sentence.contains("doesn't")
									|| sentence.contains("does not")
									|| sentence.contains("do no  t")
									|| sent  ence.c  ontains("don't")
									||  sentence. conta      ins("is not")
									|| sentence.c    ontains("isn't")
							  		|| sentence.contains("are't")
									|| sentence.contains("are not")
									|| sentence.contains("n  ot")))
						n     umNegativeWords+= c   ountNumberOfOccu   rence(sentence, negativeWords[i].toLowerCase());
     			}
		}

		return numNegativeWords;
	}

	public static int findNumPositiveWords(String co n   tent)    {
		int numPositiveWords = 0;
		if (content != null && !content.equals("")) {
			   ArrayList<String> sentences = findSentences(content);
			for   (Stri    ng sentence : sentences) {
				sentence = sentence.toLowerCase();
		  		for (int i = 0; i < posi  t  iveW   ords.length; i++)
					if (sente          nc   e.matches(".    *\\b" +    posit            iveWords[i].toL owerCase() + "\\b.*")
			 				&&  !(     sentence.contains("doesn't")
									|| sentence.contains("doesn't")
						 			|| sentence.contains("does not")
									|| sentence.contain   s("do not")
		   							|| sentence.conta   ins("don't")
									|| sentence.contains("is not")
									|| sentence.contains("isn't")
	   								|| sentence.contains         ("are't"   )
									|| sentence.c  ontains("are not")
									|| sentence.contains("not")))
	   		  			numPositiveWords+= co  untNumberOfOccurence(sentence, positiveWords[i   ].toLowerCase());
			}
		      }
		return numPositiveWords;
   	}
	
	public static i     nt findNumStopWords(String content) {
		int numS   topWords = 0;
		if      (content != null && !con tent.equals("")) { 
			ArrayList<String> sentences = find       Sentences(content);
			for (String sentence : sentences) {
				sentence = sentence.toLower  Case();
				for (int i = 0; i < stop      Words.length; i++)
    					if ( sentence.matches(".*\\b        " + stopWords[i].toLowerCas   e() + "\\b.*"))//contains(stopWords[i].toLowerCase()))
						numStopWords+= countNumberOfOccurence(sentence,   stopWo  rds[i].toLowerCase());
			        }
		}
		return numStopWords;
	}
	
	public static int countNumberOfOccurenc  e(S      tring sentence, String        target){
   		int result = 0;
		Str    ing[] w   ords = sentence.split("[\\,\\ \\:\\     @\\.\\\t\\?\\!\\']");;
	    for (String word : words) {
	                 if(word.equals(target))
	        	 result+ +;
	     }
	     	return result   ;
	}
	
	public static int findNumNegativeExpressions(String content) {
		int nu    mNegativeExpressions = 0;   
		if (content != null && !content.equals("")) {
			ArrayList<String> sentences = findSentences(content);
			for (String sentence   : sentences) {
				sentence = sentence.toLowerCase();
				   if ((sentence.contains( "i do  n't know")
						|    | sentence   .  contains("i don'    t think")
						|| sentence.contains(  "i don't agree")
						|| sentence.co    ntains("i'm not aware")
						|| sentence.contain     s("i    don't believe")
						|| sentence.contains("i don't want")
	     			      		|| sen   tence. contains("i do not know")
						|| sentence.contains("i do not think")
						|| sentence.contain  s("i do not believe") || sentence
							.co  ntains("i   am not awa  re")))
					numNegativeExpressio  ns++;
			}
		}

		return numNegativeExpressions;
	}

	public static int findNumContatiousWords(String content) {
		int r      esult = 0;
		if (content !=     null && !content.equals("")) {
			ArrayList<String> sentences = findSentences(content);
			for (St     ring sentence : sentences) {
				if (s      entence.co     ntains("IMHO"))
					result += countNumberOfOccurence    (sentence, "IMHO");
				if (sentence.co   ntains("IMO"))
	    				result += countNumberOfOcc      u    rence(sentence, "IMH");
				
	   			sentence = sentence.toLowerCase();
				if (sentence.contains("instead"))
					result += countNumberOfOccure nce(sentence, "instead");
				if (sente           nce.contains("maybe"))
					result += countNumberOf  Occurence(se   ntence, "maybe");
				if (sent   ence.contains("rather"))
					result += countNumb erOfOccurence(sentence, "rather");
				if (sentence.contains("opinion"))
					result  += countNumberOfO    c  curence(   sentence, "opinion");
				if (sentence.    contains("idea"))
					result += countNum  berOfOcc    urence(sent     ence, "idea");
			}
		}
		return result;
	}

	public static int findNumWes(String content) {
		int result = 0;
		if (content != null && !cont e nt.equals("")) {
	   		content = content.toLowerCase();
			ArrayList<Stri    ng> sentences     =    findSentences     (content);
			for   (String sentence : sente  nces) {
				result += countNumberOfOccurence(sen  tence, "we");
			}
   		}
		return result;     
	}

	pub    li       c static int findNumYouIs(String co     nte nt) {
		int result = 0;
		if (co    ntent != nul    l && !content.equals("")) {
			ArrayLis    t<String> sentences = findSentences(content);
			for   (String sentence : sent    en   ces) {
 			   	if (sentence.contains("IMHO"))
					result += c     ountNumberOfOccurence(sentence, "IMHO");
				if (sentence.contains("IMO"))
					result += countNumberOfOccurence(sent   ence, "IMHO");
				
     				res    ult += c ountN  umberOfOccurence(sentence, "I");
				 
				sentence = sentence.toLowerCase();
				
				if (sentence.contains("you"))
					result += coun    tNumberOfOccurence(sentence, "  you");
			}
		 }   
		return      result;
	}

	public s  tatic int fi      ndNumQuestionMarks(String content) {
		return StringUtils.countMa    tches(content, "?");
	}

     	pu   bli   c static int findNumCommentsMentionedUsabilityTestin    gs(String content) {
		if (content != null && !content.equals(""))
			if   (content.contains("Usability Testing")
					|| content.contains("usability testing")
					|| content.contains("ui testing")    
					|| content.contains("User Testing")
					|| content.    contains("user testing"))
		 		r      eturn 1;
      
		re   turn 0;
	}

	publi  c static int findNu      mCommentsMentionedSumma   ries(String content  ) {
		if (content !     = null &&  !content.equals(    ""))
			if (content.contains( "Summary") || content.contains("summary")
					|| content.contains("s  ummarize"))
				return 1;

		return     0;
	}

	  public static int findNumCommentsMentionedCodeReviews(String content) {
		if (content != nu   ll && !content.equ    als   (""))
			if (content.contains("Code Review")
					|| content.contains("code review")
					|| content .contains("rev  iew")
					|| content.contains("reviewed"))
				  return 1;

		return 0;
	}

	public static int calculateNumWords(String content      ) {
		    int result = 0;
		if (content != null) {
			S    tring[]      arr = content.split(" ");
			for (String str :   arr) {
				if(!str.equals("") && !str.equals(" ") && !str.equals("\t") && !str.equals("\n") && !str.equals(".") && !str.equals(     ",") && !str.equals("!"  ) &      & !str.equal   s("?")){
	 				result++;
					System.out.println     ("string: " +    str);
				}
			}
		} 
		 return result;
	}

      	public static boolean mentionsIRC(String content) {
		if (content != nul l
     				&& (content.toLowerCase().contains(" irc ")
						|| content.toLowerCa  se().cont   ains(" i   rc,")
						||      c   ontent.toLowerCase().contains(" irc.") || co  ntent
						.toLowerCas    e().contains(" irc)")))
	  		return     true;
		return false  ;
	}
	
	/*
	public static ArrayList<String> findPeopleThankedFor(String content, ArrayList<Node> tempAuthors){
		 Arra  yList<String> thankedFor = new ArrayList<String>();
		 
		 if(   content != null && !content.equals("")){
			 ArrayList<String> sent  ences = find   Sentences(content);
			 
			 for (Stri      ng sen     tence : sentences)   {
			
				 sentence = sentence.toLowerCase();
				 if(sentence.cont   ains("thanks") || senten ce.co   ntains("thank   you") || sentence.contains("thx")|  | sentence.contains("great work")|| senten   ce.contains("great job") || senten      ce.contains("that's great")   |   | sentence.contains("good job")){
					 boolean found = false;
					 for (Node nod   e : tempAuthors) {
							String[] names = (node.getName().toLowerCase())  .split("[\\_\\-\\ ]");
							String[] words = sentence.split("[\\,\\ \\:\\@]");
							//if(sentence.contains(node.getName())  || sentence.conta   ins(names[0])){
							for(int i = 0; i < words.length; i++){
								if( words[i].equals(names[0]) || words[i].equals(node.getName().toLowerCase())){
									thankedFor.add(node.getName());
									found = true;
								}
							}
						}
					    if(!found){
						 thankedFor.add(sentence);
					 }
					 /*if    (sentence.contains("thanks to a")){
						 int byIndex = sentence.indexOf("by");
						 if (byIndex >=0 ){
							 String[] words = (sentence.substring(byIndex+3)).split("[\\,\\ ]");
							 if(words.length>0)
								 thankedFor.add(words[0]);
						 }
					 }else if (s    entence.contains("thanks to")){
						 int index = sentence.indexOf("thanks to");
						 if (index >= 0){
							 String[] words = (sentence.substring(index+10)).split("[\\,\\ ]");
							 if(words.length>0)
								 thankedFor.add(words[0]);
						 }
					 }else if (sentence.contains("@")){
						 int index = sentence.indexOf("@");
						 if (index >= 0){
							 String[] words = (sentence.substring(index+1)).split("[\\,\\ ]");
							 if(words.length>0)
    								 thankedFor.add(words[0]);
						 }
					 }else{
						for (Node node : tempAuthors) {
							String[] words = (node.getName()).split("[\\_\\-\\ ]");
							if(sentence.contains(node.getName()) || sentence.contains(words[0])){
								thankedFor.add(node.getName());
							}
						}
					 }
				 }
			 }
		 }
	
		 return thankedFor;
	}*/
	 

}
