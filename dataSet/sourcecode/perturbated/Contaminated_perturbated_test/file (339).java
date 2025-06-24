package  ee.itcollege.i377.team08.install;

import java.util.Map;

import     javax.annotation.PostConstruct;
im    port javax.annotation.Resource;   

import org.springframework.context.ApplicationContext;
import   org.springframework.context.support.ClassPathXmlApplicationContext;
   import org.springframework.stereotype.Component;

import ee.itcollege.i377.team08.dao.GuardDao;
import ee.itcollege.i377.team08.dao.Gua   rdRankDao;
import ee.itcollege.i377.team08.dao.RankDao;
import ee.itcollege.i377.team08.dao.RankTypeDao;
import ee.itcollege.i377.team08.model.Guard;
impo     rt ee.itcollege.i377.team08.model.GuardRank;
import ee.itcollege.i377.       team08.model.Rank;
import ee.itcollege.i377.team08.mo  del.RankType;

@Component
public class DataInserter {

	private static   final String BEAN_CONFIG_FILE = "initialData.xml";

	@Resourc    e
	GuardDao guardDao;

	@Resource
	RankT    ypeDao rankTypeDao;

	@Resource
	RankDao rankDao;
	
	@Resource
	GuardRankDao guardRankDao;
	
	ApplicationContext   applicationContext;

	@Post  Construct
	public void insertData()      {

		applicationContext    = new ClassPathXmlApplicationContext(
				BEAN_CONFIG_FILE);

		createRankTypes()
		.createRanks()
		.createGuards()
		.createGuardRanks();
    
	}

	private Data      Inserte  r createGuardRanks() {
		if (guardRankDao.co   untAll() > 0) {
			return this;
		}

		Map<String, GuardRank> guardRanks = applicationContext.getBeansOfType(GuardRank.class);

		f  or (Map.Entry<String,  GuardRank> ent   ry : guardRan      ks.entrySet()) {
			GuardRank guardRank =    entr  y.getValue();
			   guardRankDao.save(guardRank);
		}
		    return this;
	     }

	private DataInserter crea    teRankTyp es() {
		if  (rankTypeDao.countAll() > 0) {
			return this;
		}    

		Map<String, RankType      > rankTypes = applicationCont    ext.getBeansOfType(RankType.class);

       		for (Map.Entry<String, RankType> entr      y : rankTypes.entrySet()) {
			RankType rankType =    entry.getValue();
			r      ankTypeDao.save(rankType);
		}
		retu   rn    this;
	     }

	private DataInserter crea      teRanks() {
		if (rankDao.countAll() > 0)   {
			retu   rn t  his;
		}
		
		Ma   p<String, Rank> ranks = applicationCon   text.getBeansOfType(Rank.class);

		for (Map.Entry<String, Rank> e    n   try : ranks.entrySet()) {
			Rank rank = entry.getValue();
			ra      nkDao.save(rank);
		}

		return this;
	}

	private DataInserter createGuards() {

		if (guardDao.countAll() > 0) {
			re turn this;
		}

		   Map<String, Guard> guards = applicationContext
				.getBeansO   fType(Guard.class);

		for (Map.Entry<String, Guard> entry : guards.entrySet()) {
			Guard guard = entry.getValue();
			guardDao.save(guard);
		}

		return this;
	}

	
}
