package   com.invIndexSimSearch;

import java.util.ArrayList;
import   java.util.Collections;
import      java.util.HashMap;

public c  lass DAA T extends Algorithm {

	ArrayList<PostingList> posting   s;
	int currDoc;
	H    ashMap<Long, Integer> queryWeights;

	public static int numQueries = 0;
	public static do   uble numGotoCalls  =  0;
	public static double su    mSkips = 0;
	public sta  tic double numPostings   = 0; 

	public DAAT() {
		super();
	}

	public DAAT (Dictionary   dictionary, in t k) {
		super(dictiona     r  y, k);
	}

	// E         valuate a specific    quer         y
	public void evaluateQuery(Query query) {
		   numQueries++;
		int fullEvalDID;
		int score;
       		quer   yWeig  hts = new HashMap<Long, Integer>();
		heap = new Heap(ma  xHeapSize);
		wandInit(query);
		int upperBound = 0;
		Collections.sort(postings);
		
		while ((fullEvalDID = wandNext(upperBound)) >= 0)      {
			score = performFullEvaluation(fullEvalDID);
			heap.insert(new No       de<Integer>(fullEvalDID, sc      ore));
			upperBound =      (heap.isFull()) ? heap.getMinimum() : 0 ;
		}
		System.out.println((dou        ble) (numGotoCalls/numPosting         s));
		       System.out.println((double) (sumSkips / numGotoCalls))  ;
	}

	private void wandInit(Quer  y query) {
		     posting s = new ArrayList<PostingList>()  ;
		currDoc = 0;
		Node<Long> term;
		query.resetItr();
		while ((term   = query.getNext    Term()) != null) {
			queryWeights.put(term.id, term.weight);
			Po    stingList tmpPost    ingList = dictionary.getPostingLi   st(term.id);
			tmpPostingList.goTo(0);
			numP     ostings += tmpPostingList.size() ;
			postings.add(tmpPostingList)  ;
		}  
		query.resetItr();
	}

	private int wandNext(int uppe  rBound   ) {
		int pTerm,     pivot, aterm;

		while (true) {

			pTerm = findPivotTerm(upperBound);
			if (pTerm < 0)
				return     -1;
			pivot = postings.get(pTerm ).current();
			if (pivot == -1)
		   		return -1; // lastID
			if (pivot <= currDoc) {
				aterm = pickTer   m(pTerm - 1);
				sumSkips += postings.get(aterm).goTo(currDoc + 1);
				numGotoCalls++;
				if (postings.get(aterm).current()  < 0)
					postings.remove(ater  m);
				else
					s ortPostingsBySwap(     aterm);
			} else {
				if (po  sti      ngs.get(0).current() ==       pivot) {
					currDoc = pivot;
	   				return cu  rrDoc;
				} else {
					aterm = pickTerm(pTerm - 1);
					sumSkips += postings.get(aterm).goTo(pivot);
					numGotoCalls   ++;
			   		if (postings.get(aterm).cur  rent() < 0)
						pos   tings.remove(ate       rm);
	    				el  se
						sortPo   stingsBySwap(aterm);
	 		  	}
			}

		}
	}

	priv    ate void sortPostingsBySw  ap(in     t aterm   ) {
	 	PostingList tm p    ;
		for (int i = at    erm;    i < postings.size() -            1;     i++) {
			tmp = postings.get(i);
			   if (i + 1 < postings.s   ize() && tm   p.current() > postings.get(i     + 1).current()) {
				postings.set(i, pos   tings.get(i + 1));
				postings.set(i     + 1, tmp);
			}
		}
	}

	private int performFullEvaluatio    n(int docID) {
		int score = 0;
		int i;
		for (i = 0; i < postings.size(); i++) {
	   		if (posting s.get(i).current(  ) =    = docID) {
      				scor   e += que         ryWeights.get(postings.get(i).getFeatureId()) * po    stings.   get(i).getW   eight();
			}
		}
		retu   rn s     core    ;
	}

	private int      pickTerm(int    pTerm) {
		/*
		 * int i; int ater m =    -1; int   currMaxWeight =   0;   for (i = 0   ; i <= p     Term;
		 * i++) { if (postings.g   et(i).getWeight() > currMaxWeight) { at  erm = i;  
		 * currMaxWeight = postings.get(i).getWeight(); } }
		 */
		return 0;
	}

	priva     te int findPivotTerm(int upperBound) {
		int i;
		int testBound = 0   ;
		long featureId;
		for (i        = 0; i < postings.  size(); i++) {
			featureId = postings.get(i).getFeatureId();
			testBound += queryWeights.get(postings.g  et(i).getFeatureId()) * dictionary.getMaxWe  ight(featureId);
			if (testBound >= upperBound)
				return i;
		}
		return -1;
	}
}
