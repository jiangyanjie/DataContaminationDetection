package analysis;

import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.Locale;
import java.util.StringTokenizer;

import org.apache.commons.lang3.StringUtils;

public class ContentAnalysis {
	public static String[] negativeWords = {  "disappoint", "abandoned", "abuse", "abused", "accused", "addicted", "afraid", "aggravated", "aggressive", "agony", "anger", "angered", "angry", "anguish", "annoyed", "annoying", "anxiety", "anxious", "argumentative", "arrogant", "artificial", "ashamed", "assaulted", "at a loss", "at risk", "atrocious", "attacked", "avoided", "awful", "awkward", "bad", "backfire", "badgered", "baffled", "banned", "barren", "beat", "beaten down", "belittled", "berated", "betrayed", "bitch", "bitched at", "bitching", "bitchy", "bitter", "bitterness", "bizzare", "blacklisted", "blackmailed", "blamed", "blasphemy", "bleak", "blown away", "blur", "bogus", "bored", "boring", "bossed-around", "bothered", "bothersome", "bounded", "boxed-in", "bozo", "bruised", "brushed-off", "bugged", "bullied", "bully", "bummed", "bummed out", "bummer", "burdened", "burdensome", "burned", "burned-out", "caged in", "can worms", "careless", "chaotic", "chased", "cheated", "cheated on", "chicken", "churlish", "claustrophobic", "clingy", "clueless", "clumsy", "coaxed", "coerced", "cold", "cold-hearted", "combative", "complain", "complained", "compulsive", "conceited", "concerned", "condescended to", "confined", "conflicted", "confronted", "conned", "consumed", "contemplative", "contempt", "contentious", "contrite", "contrition", "convicted", "cornered", "corralled", "corrupt", "cowardly", "crabby", "cramped", "cranky", "crap", "crappy", "crass", "crazy", "creeped out", "creepy", "criticized", "cruddy", "crummy", "crushed", "cut-down", "cut-off", "cynical", "damn", "damned", "dangerous", "dark", "dazed", "dead", "deceived", "deep", "defame", "defamed", "defeated", "defective", "defenseless", "defensive", "defiant", "deficient", "deflated", "degraded", "dehumanized", "dejected", "delicate", "deluded", "delusion", "demanding", "demeaned", "demented", "demoralized", "depleted", "depraved", "depressed", "depression", "deprived", "deserted", "deserving of pain/punishment", "desolate", "despair", "despairing", "desperate", "desperation", "despicable", "despised", "destroy", "destroyed", "destructive", "detest", "detestable", "detested", "devalued", "devastate", "devastated", "devastation", "deviant", "devious", "devoid", "diagnosed", "dictated to", "directionless", "dirty", "disagreeable", "disappointed", "disappointing", "disappointment", "disappoints", "disapproved of", "disbelieved", "discardable", "discarded", "disconnected", "discontent", "discouraged", "discriminated", "disdain", "disdainful", "disempowered", "disenchanted", "disgraced", "disgruntled", "disgust", "disgusted", "disheartened", "dishonest", "dishonor", "dishonorable", "dishonour", "disillusioned", "dislike", "disliked", "dismal", "dismay", "dismayed", "disorganized", "disoriented", "disowned", "displeased", "disposable", "disregarded", "disrespected", "dissatisfied", "distant", "distracted", "distraught", "distress", "distressed", "disturbance", "disturbances", "disturbed", "disturbing", "dizzy", "domineering", "don't like", "doomed", "double-crossed", "doubted", "doubtful", "down and out", "down in the dumps", "downhearted", "downtrodden", "drained", "dramatic", "dread", "dreadful", "dreary", "dropped", "drunk", "dry", "dumb", "dumped", "dumped on", "duped", "edgy", "egocentric", "egotistic", "egotistical", "elusive", "emancipated", "emasculated", "embarass", "embarassed", "embarassment", "embarrassed", "embitter", "emotionally bankrupt", "emotionless", "encumbered", "endangered", "enrage", "enraged", "enslaved", "entangled", "evaded", "evasive", "evicted", "exasperate", "exasperation", "excessive", "excoriate", "exhausted", "explode", "exploited", "explosion", "exposed", "fuck", "failful", "fake", "feckless", "fed up", "flawed", "forgetful", "forgettable", "forgotten", "fragile", "fraud", "freak out", "freaked out", "frightened", "frigid", "frustrate", "frustrated", "frustrating", "fuck", "fucked", "fucker", "fucking", "fucks", "furious", "gaddamit", "gloomy", "glum", "goddam", "goddamn", "gothic", "grey", "grim", "gross", "grossed-out", "grotesque", "grouchy", "grounded", "grumpy", "guilt-tripped", "guilty", "hackles", "harass", "harassed", "harassment", "hard-hearted", "harmed", "hassled", "hate", "hateful", "hatred", "haughty", "haunted", "heart broken", "heartbreak", "heartbroken", "heartless", "heavy-hearted", "heinous", "hell pay", "helpless", "henpecked", "hesitant", "hideous", "hindered", "hopeless", "horrible", "horrified", "horror", "hostile", "hostilities", "hostility", "hot-tempered", "humiliate", "humiliated", "humiliation", "hung over", "hung up", "hurried", "hysterical", "idiot", "idiotic", "ignominy", "ignorant", "ignored", "ill will", "ill-tempered", "imbalanced", "imposed-upon", "impotent", "imprisoned", "impulsive", "in the dumps", "incapable", "incommunicative", "incompetent", "indecisive", "indignant", "indignation", "indoctrinated", "inebriated", "ineffective", "inefficient", "inferior", "infuriate", "infuriated", "inhibited", "inhumane", "injure", "injured", "injusticed", "insane", "insecure", "insidious", "insignificant", "insincere", "insufficient", "insulted", "intense", "interrogated", "interrupted", "intimidated", "intoxicated", "invalidated", "invisible", "irate", "irrational", "irritable", "irritate", "irritated", "irritating", "isolated", "jackass", "jaded", "jealous", "jerked around", "joyless", "judged", "kept quiet", "lame", "last rites", "laughable", "laughed at", "lazy", "leaned on", "lectured to", "left out", "let down", "lied about", "lied to", "livid", "loathe", "lonely", "lonesome", "longing", "lost", "lousy", "loveless", "mad", "madden", "made fun of", "man handled", "manipulated", "masochistic", "megalomania", "mend fences", "messed up", "messed with", "messy", "miffed", "miserable", "misery", "misled", "mistaken", "mistreated", "mistrusted", "misunderstood", "mixed-up", "mocked", "molested", "moody", "moron", "morose", "mortification", "mortified", "nagged", "needy", "nervous", "neurotic", "nightmare", "nightmarish", "noisome", "nonconforming", "noxious", "numb", "nuts", "nutty", "objectified", "obligated", "obloquy", "obnoxious", "obsessed", "obsessive", "obstructed", "odium", "offended", "offense", "on display", "oppressed", "opprobrium", "outrage", "over-controlled", "over-protected", "overbearing", "overwhelmed", "pain", "pained", "panic", "paranoid", "passive", "pathetic", "patronizing", "pessimistic", "petrified", "petrify", "petrifying", "phony", "picked on", "pissed", "pissed off", "plagiarize", "played with", "pooped", "powerless", "pre-judged", "preached to", "predjudiced", "preoccupied", "pressured", "prosecuted", "provoked", "psychopathic", "psychotic", "pulled apart", "pulled back", "punished", "pushed away", "put down", "puzzled", "quarrelsome", "queer", "quixotic", "rage", "raped", "rattled", "ravage", "reprehensible", "resented", "resentful", "retarded", "revengeful", "ridiculed", "ridiculous", "robbed", "rotten", "rude", "sad", "sad news", "saddle", "sadistic", "sarcastic", "scared", "scarred", "screwed", "screwed over", "screwed up", "seedy", "self-centered", "self-destructive", "self-hatred", "selfish", "senile", "sepulchral", "shame", "shamefaced", "shameful", "sheepish", "shouted at", "singled-out", "slander", "sloppy", "smothered", "snapped at", "sonofabitch", "sophistry", "sordid", "sorrow", "spiteful", "stereotyped", "stressed", "stretched", "struggle", "stupefied", "stupid", "submissive", "suffer", "suffering", "suffocate", "suffocated", "suicidal", "supercilious", "superficial", "suppressed", "swollen headed", "tantrum", "terrible", "torment", "tormented", "travesty", "trouble", "umbrage", "unctuous", "unsavory", "unsavoury", "vain", "vainglorious", "vapid", "vendetta", "villainous", "violence", "vitriol", "vituperative", "vulgar", "wishy washy", "worthless", "wrath", "wreak",  "confused", "critical", "down", "hurt", "kept away", "kept out", "limited", "pushed",  "stuck", "suspicious", "shortsightedness", "wreaking", "wrongful", "wtf", "yucky" };
	public static String[] stopWords = {"a", "about", "above", "across", "after", "afterwards", "again", "against", "all", "almost", "alone", "along", "already", "also", "although", "always", "am", "among", "amongst", "amoungst", "amount", "an", "and", "another", "any", "anyhow", "anyone", "anything", "anyway", "anywhere", "are", "around", "as", "at", "back", "be", "became", "because", "become", "becomes", "becoming", "been", "before", "beforehand", "behind", "being", "below", "beneath", "beside", "besides", "between", "beyond", "bill", "both", "bottom", "but", "by", "call", "can", "cannot", "cant", "co", "con", "could", "couldnt", "cry", "de", "describe", "detail", "do", "done", "down", "due", "during", "each", "eg", "eight", "either", "eleven", "else", "elsewhere", "empty", "enough", "etc", "even", "ever", "every", "everyone", "everything", "everywhere", "except", "few", "fifteen", "fify", "fill", "find", "fire", "first", "five", "for", "former", "formerly", "forty", "found", "four", "from", "front", "full", "further", "get", "give", "go", "had", "has", "hasnt", "have", "he", "hence", "her", "here", "hereafter", "hereby", "herein", "hereupon", "hers", "herself", "him", "himself", "his", "how", "however", "hundred", "i", "ie", "if", "in", "in front of", "inc", "indeed", "inside", "instead of", "interest", "into", "into", "is", "it", "its", "itself", "keep", "last", "latter", "latterly", "least", "less", "ltd", "made", "many", "may", "me", "meanwhile", "might", "mill", "mine", "more", "moreover", "most", "mostly", "move", "much", "must", "my", "myself", "name", "namely", "near", "neither", "never", "nevertheless", "next", "nine", "no", "nobody", "none", "noone", "nor", "not", "nothing", "now", "nowhere", "of", "off", "often", "on", "on top of", "once", "one", "only", "onto", "or", "other", "others", "otherwise", "our", "ours", "ourselves", "out", "out of", "outside", "over", "own", "part", "past", "per", "perhaps", "please", "put", "rather", "re", "same", "see", "seem", "seemed", "seeming", "seems", "serious", "several", "she", "should", "show", "side", "since", "sincere", "six", "sixty", "so", "some", "somehow", "someone", "something", "sometime", "sometimes", "somewhere", "still", "such", "system", "take", "ten", "than", "that", "the", "their", "them", "themselves", "then", "thence", "there", "thereafter", "thereby", "therefore", "therein", "thereupon", "these", "they", "thickv", "thin", "third", "this", "those", "though", "three", "through", "throughout", "thru", "thus", "to", "together", "too", "toward", "towards", "twelve", "twenty", "two", "un", "under", "underneath", "until", "up", "upon", "us", "very", "via", "was", "we", "well", "were", "what", "whatever", "when", "whence", "whenever", "where", "whereafter", "whereas", "whereby", "wherein", "whereupon", "wherever", "whether", "which", "while", "whither", "who", "whoever", "whole", "whom", "whose", "why", "will", "with", "within", "without", "would", "yet", "you", "your", "yours", "yourself", "yourselves"};
	public static String[] positiveWords = {"congratulations", "+1", "a shining example", "a step ahead", "absolutely", "absorbing", "abundance", "ace", "active", "admirable", "adore", "adulation", "agree", "agreed", "alert", "alive", "amazement", "amazing", "appealing", "approval", "aroma", "astonish", "astonished", "astonishing", "astounding", "attraction", "award", "awe inspiring", "awesome", "bargain", "beaming", "beats", "beautiful", "best", "better", "bits", "blissful", "boost", "bounce", "breakthrough", "bright", "brightens up", "brilliant", "brimming", "care", "celebrate", "celebration", "charming", "chic", "choice", "clean", "cloud nine", "colorful", "comedy", "comfy", "compliment", "confidence", "congrats", "congratulate", "congratulated", "congratulating", "connoisseur", "consensus", "cool", "courteous", "coy", "creamy", "crisp", "cuddly", "dazzling", "debonair", "delicate", "delicious", "delightful", "deluxe", "dependable", "desire", "diamond", "discerning", "distinctive", "divine", "does the trick", "dreamy", "drool", "dynamic", "easy", "ecstasy", "ecstatic", "effervescent", "efficient", "electrified", "electrifying", "endless", "energy", "enhance", "enjoy", "enormous", "enraptured", "ensure", "enticing", "essence", "essential", "euphoria", "exactly", "excellent", "exceptional", "excited", "exciting", "exclusive", "exhilaration", "exotic", "expert", "exquisite", "extol", "extra", "eye-catching", "fab", "fabled", "fabulous", "fair", "famous", "fantastic", "fascinating", "fashionable", "fast", "favorite", "felicitate", "felicitation", "festivity", "fetching", "finesse", "finest", "fizz", "flair", "flattering", "flourishing", "foolproof", "fragrance", "freshness", "friendly", "fun", "funny", "galore", "generous", "genius", "gentle", "giggle", "glad", "glamorous", "gleeful", "glitter", "glorious", "glowing", "go-ahead", "golden", "good", "good news", "goodness", "gorgeous", "graceful", "grand", "grateful", "gratitude", "great", "guaranteed", "gush", "ha ha", "haha", "hahaha", "happiness", "happy", "healthy", "heart warming", "heartwarming", "heaven sent", "heavenly", "high spirits", "humor", "humour", "ideal", "immaculate", "impressive", "incredible", "inspire", "instant", "interesting", "invigorating", "invincible", "inviting", "irresistible", "jewel", "joke", "joy", "joyous", "jubilation", "juicy", "k.o.", "keenest", "kind", "kissable", "leads", "legend", "leisure", "light", "like", "liked", "lingering", "logical", "lol", "love", "loved", "lovely", "lucky", "luscious", "luxurious", "magic", "magnifies it", "making an impression", "marvel", "marvellous", "marvelous", "matchless", "maxi", "memorable", "mighty", "mindblowing", "miracle", "nice", "nicely", "nutritious", "o.k.", "omg", "on the right foot", "ooh", "oooh", "ooooh", "opulent", "outlasts", "outrageous", "outstanding", "palate", "palatial", "pamper", "paradise", "passionate", "peak", "pearl", "perfect", "pick-me-up", "pleases", "pleasure", "plenty", "plum", "plump", "plus", "popular", "positive", "power", "pre eminent", "precious", "preeminent", "prefer", "prestige", "priceless", "pride", "prime", "prize", "protection", "proud", "pure", "quality", "quenching", "radiant", "rapture", "rather", "rave", "ravishing", "real", "really cool", "reap", "recommendation", "refined", "refreshing", "relax", "reliable", "remarkable", "remarkably", "renowned", "reputation", "rest", "rewarding", "rhapsodic", "rich", "right", "rosy", "royal", "safety", "satisfaction", "save", "scores", "seductive", "sensational", "sensitive", "serene", "service", "sexy", "shapely", "sheer", "shy", "simple", "sizzling", "skilful", "slapstick", "slick", "smashing", "smiles", "smooth", "soft", "solar", "sound", "sounds good", "sparkling", "special", "spectacular", "speed", "spice", "spicy", "splendid", "spotless", "spruce", "standing ovation", "star", "strong", "stunning", "stupendous", "stylish", "subtle", "success", "succulent", "super", "superb", "superlative", "supersonic", "supreme", "sure", "sweet", "swell", "symphony", "tan", "tangy", "tasty", "tempting", "terrific", "thank god", "thank goodness", "thankful", "the no 1", "thoroughbred", "thrill", "thrilling", "thriving", "timeless", "tingle", "tiny", "totally", "traditional", "transformation", "treasure", "treat", "trendy", "true", "trust", "ultimate", "unbeatable", "unblemished", "undeniably", "undoubtedly", "unquestionably", "unrivaled", "unsurpassed", "v.i.p.", "valuable", "valued", "vanish", "varied", "versatile", "victor", "vigorous", "vintage", "vital", "vivacious", "walking on air", "warm", "wee", "well", "whiz", "whole", "whopper", "winner", "wise", "wonderful", "worthy", "wow", "wow!", "young", "youthful", "yule", "zap", "zeal", "zest", "zip"};

	public static int findNumCharacters(String plainContent) {
		return plainContent.length();
	}

	public static int findNumSentences(String content) {
		int result = 0;
		BreakIterator iterator = BreakIterator.getSentenceInstance(Locale.US);
		iterator.setText(content);
		for (int end = iterator.next(); end != BreakIterator.DONE; end = iterator.next()) {
			result++;
		}
		
		return result;
	}

	public static ArrayList<String> findSentences(String content){
		ArrayList<String> sentences = new ArrayList<String>();

		BreakIterator iterator = BreakIterator.getSentenceInstance(Locale.US);
		iterator.setText(content);
		int start = iterator.first();
		for (int end = iterator.next(); end != BreakIterator.DONE; start = end, end = iterator
				.next()) {
			sentences.add(content.substring(start,end));
		}

		return sentences;
	}
	
	public static int findNumPlusOnes(String content) {
		if (content != null && !content.equals(""))
			if (content.contains("+1 ") || content.contains("+1.")
					|| content.contains("+1ing") || content.contains("+1-ed"))
				return 1;

		return 0;
	}

	public static int findNumThanks(String content) {
		if (content != null && !content.equals(""))
			if (content.contains("Thanks") || content.contains("thanks")
					|| content.contains("Thank you")
					|| content.contains("thank you")
					|| content.contains("thx"))
				return 1;

		return 0;
	}

	public static int findNumNegativeWords(String content) {
		int numNegativeWords = 0;
		if (content != null && !content.equals("")) {
			ArrayList<String> sentences = findSentences(content);
			for (String sentence : sentences) {
				sentence = sentence.toLowerCase();
				for (int i = 0; i < negativeWords.length; i++)
					if (sentence.matches(".*\\b" + negativeWords[i].toLowerCase() + "\\b.*")
							&& !(sentence.contains("doesn't")
									|| sentence.contains("doesn't")
									|| sentence.contains("does not")
									|| sentence.contains("do not")
									|| sentence.contains("don't")
									|| sentence.contains("is not")
									|| sentence.contains("isn't")
									|| sentence.contains("are't")
									|| sentence.contains("are not")
									|| sentence.contains("not")))
						numNegativeWords+= countNumberOfOccurence(sentence, negativeWords[i].toLowerCase());
			}
		}

		return numNegativeWords;
	}

	public static int findNumPositiveWords(String content) {
		int numPositiveWords = 0;
		if (content != null && !content.equals("")) {
			ArrayList<String> sentences = findSentences(content);
			for (String sentence : sentences) {
				sentence = sentence.toLowerCase();
				for (int i = 0; i < positiveWords.length; i++)
					if (sentence.matches(".*\\b" + positiveWords[i].toLowerCase() + "\\b.*")
							&& !(sentence.contains("doesn't")
									|| sentence.contains("doesn't")
									|| sentence.contains("does not")
									|| sentence.contains("do not")
									|| sentence.contains("don't")
									|| sentence.contains("is not")
									|| sentence.contains("isn't")
									|| sentence.contains("are't")
									|| sentence.contains("are not")
									|| sentence.contains("not")))
						numPositiveWords+= countNumberOfOccurence(sentence, positiveWords[i].toLowerCase());
			}
		}
		return numPositiveWords;
	}
	
	public static int findNumStopWords(String content) {
		int numStopWords = 0;
		if (content != null && !content.equals("")) {
			ArrayList<String> sentences = findSentences(content);
			for (String sentence : sentences) {
				sentence = sentence.toLowerCase();
				for (int i = 0; i < stopWords.length; i++)
					if (sentence.matches(".*\\b" + stopWords[i].toLowerCase() + "\\b.*"))//contains(stopWords[i].toLowerCase()))
						numStopWords+= countNumberOfOccurence(sentence, stopWords[i].toLowerCase());
			}
		}
		return numStopWords;
	}
	
	public static int countNumberOfOccurence(String sentence, String target){
		int result = 0;
		String[] words = sentence.split("[\\,\\ \\:\\@\\.\\\t\\?\\!\\']");;
	    for (String word : words) {
	         if(word.equals(target))
	        	 result++;
	     }
		return result;
	}
	
	public static int findNumNegativeExpressions(String content) {
		int numNegativeExpressions = 0;
		if (content != null && !content.equals("")) {
			ArrayList<String> sentences = findSentences(content);
			for (String sentence : sentences) {
				sentence = sentence.toLowerCase();
				if ((sentence.contains("i don't know")
						|| sentence.contains("i don't think")
						|| sentence.contains("i don't agree")
						|| sentence.contains("i'm not aware")
						|| sentence.contains("i don't believe")
						|| sentence.contains("i don't want")
						|| sentence.contains("i do not know")
						|| sentence.contains("i do not think")
						|| sentence.contains("i do not believe") || sentence
							.contains("i am not aware")))
					numNegativeExpressions++;
			}
		}

		return numNegativeExpressions;
	}

	public static int findNumContatiousWords(String content) {
		int result = 0;
		if (content != null && !content.equals("")) {
			ArrayList<String> sentences = findSentences(content);
			for (String sentence : sentences) {
				if (sentence.contains("IMHO"))
					result += countNumberOfOccurence(sentence, "IMHO");
				if (sentence.contains("IMO"))
					result += countNumberOfOccurence(sentence, "IMH");
				
				sentence = sentence.toLowerCase();
				if (sentence.contains("instead"))
					result += countNumberOfOccurence(sentence, "instead");
				if (sentence.contains("maybe"))
					result += countNumberOfOccurence(sentence, "maybe");
				if (sentence.contains("rather"))
					result += countNumberOfOccurence(sentence, "rather");
				if (sentence.contains("opinion"))
					result += countNumberOfOccurence(sentence, "opinion");
				if (sentence.contains("idea"))
					result += countNumberOfOccurence(sentence, "idea");
			}
		}
		return result;
	}

	public static int findNumWes(String content) {
		int result = 0;
		if (content != null && !content.equals("")) {
			content = content.toLowerCase();
			ArrayList<String> sentences = findSentences(content);
			for (String sentence : sentences) {
				result += countNumberOfOccurence(sentence, "we");
			}
		}
		return result;
	}

	public static int findNumYouIs(String content) {
		int result = 0;
		if (content != null && !content.equals("")) {
			ArrayList<String> sentences = findSentences(content);
			for (String sentence : sentences) {
				if (sentence.contains("IMHO"))
					result += countNumberOfOccurence(sentence, "IMHO");
				if (sentence.contains("IMO"))
					result += countNumberOfOccurence(sentence, "IMHO");
				
				result += countNumberOfOccurence(sentence, "I");
				
				sentence = sentence.toLowerCase();
				
				if (sentence.contains("you"))
					result += countNumberOfOccurence(sentence, "you");
			}
		}
		return result;
	}

	public static int findNumQuestionMarks(String content) {
		return StringUtils.countMatches(content, "?");
	}

	public static int findNumCommentsMentionedUsabilityTestings(String content) {
		if (content != null && !content.equals(""))
			if (content.contains("Usability Testing")
					|| content.contains("usability testing")
					|| content.contains("ui testing")
					|| content.contains("User Testing")
					|| content.contains("user testing"))
				return 1;

		return 0;
	}

	public static int findNumCommentsMentionedSummaries(String content) {
		if (content != null && !content.equals(""))
			if (content.contains("Summary") || content.contains("summary")
					|| content.contains("summarize"))
				return 1;

		return 0;
	}

	public static int findNumCommentsMentionedCodeReviews(String content) {
		if (content != null && !content.equals(""))
			if (content.contains("Code Review")
					|| content.contains("code review")
					|| content.contains("review")
					|| content.contains("reviewed"))
				return 1;

		return 0;
	}

	public static int calculateNumWords(String content) {
		int result = 0;
		if (content != null) {
			String[] arr = content.split(" ");
			for (String str : arr) {
				if(!str.equals("") && !str.equals(" ") && !str.equals("\t") && !str.equals("\n") && !str.equals(".") && !str.equals(",") && !str.equals("!") && !str.equals("?")){
					result++;
					System.out.println("string: " + str);
				}
			}
		} 
		return result;
	}

	public static boolean mentionsIRC(String content) {
		if (content != null
				&& (content.toLowerCase().contains(" irc ")
						|| content.toLowerCase().contains(" irc,")
						|| content.toLowerCase().contains(" irc.") || content
						.toLowerCase().contains(" irc)")))
			return true;
		return false;
	}
	
	/*
	public static ArrayList<String> findPeopleThankedFor(String content, ArrayList<Node> tempAuthors){
		 ArrayList<String> thankedFor = new ArrayList<String>();
		 
		 if(content != null && !content.equals("")){
			 ArrayList<String> sentences = findSentences(content);
			 
			 for (String sentence : sentences) {
			
				 sentence = sentence.toLowerCase();
				 if(sentence.contains("thanks") || sentence.contains("thank you") || sentence.contains("thx")|| sentence.contains("great work")|| sentence.contains("great job") || sentence.contains("that's great") || sentence.contains("good job")){
					 boolean found = false;
					 for (Node node : tempAuthors) {
							String[] names = (node.getName().toLowerCase()).split("[\\_\\-\\ ]");
							String[] words = sentence.split("[\\,\\ \\:\\@]");
							//if(sentence.contains(node.getName()) || sentence.contains(names[0])){
							for(int i = 0; i < words.length; i++){
								if(words[i].equals(names[0]) || words[i].equals(node.getName().toLowerCase())){
									thankedFor.add(node.getName());
									found = true;
								}
							}
						}
					 if(!found){
						 thankedFor.add(sentence);
					 }
					 /*if (sentence.contains("thanks to a")){
						 int byIndex = sentence.indexOf("by");
						 if (byIndex >=0 ){
							 String[] words = (sentence.substring(byIndex+3)).split("[\\,\\ ]");
							 if(words.length>0)
								 thankedFor.add(words[0]);
						 }
					 }else if (sentence.contains("thanks to")){
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
