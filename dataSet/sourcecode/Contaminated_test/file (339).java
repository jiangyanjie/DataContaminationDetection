package ee.itcollege.i377.team08.install;

import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import ee.itcollege.i377.team08.dao.GuardDao;
import ee.itcollege.i377.team08.dao.GuardRankDao;
import ee.itcollege.i377.team08.dao.RankDao;
import ee.itcollege.i377.team08.dao.RankTypeDao;
import ee.itcollege.i377.team08.model.Guard;
import ee.itcollege.i377.team08.model.GuardRank;
import ee.itcollege.i377.team08.model.Rank;
import ee.itcollege.i377.team08.model.RankType;

@Component
public class DataInserter {

	private static final String BEAN_CONFIG_FILE = "initialData.xml";

	@Resource
	GuardDao guardDao;

	@Resource
	RankTypeDao rankTypeDao;

	@Resource
	RankDao rankDao;
	
	@Resource
	GuardRankDao guardRankDao;
	
	ApplicationContext applicationContext;

	@PostConstruct
	public void insertData() {

		applicationContext = new ClassPathXmlApplicationContext(
				BEAN_CONFIG_FILE);

		createRankTypes()
		.createRanks()
		.createGuards()
		.createGuardRanks();

	}

	private DataInserter createGuardRanks() {
		if (guardRankDao.countAll() > 0) {
			return this;
		}

		Map<String, GuardRank> guardRanks = applicationContext.getBeansOfType(GuardRank.class);

		for (Map.Entry<String, GuardRank> entry : guardRanks.entrySet()) {
			GuardRank guardRank = entry.getValue();
			guardRankDao.save(guardRank);
		}
		return this;
	}

	private DataInserter createRankTypes() {
		if (rankTypeDao.countAll() > 0) {
			return this;
		}

		Map<String, RankType> rankTypes = applicationContext.getBeansOfType(RankType.class);

		for (Map.Entry<String, RankType> entry : rankTypes.entrySet()) {
			RankType rankType = entry.getValue();
			rankTypeDao.save(rankType);
		}
		return this;
	}

	private DataInserter createRanks() {
		if (rankDao.countAll() > 0) {
			return this;
		}
		
		Map<String, Rank> ranks = applicationContext.getBeansOfType(Rank.class);

		for (Map.Entry<String, Rank> entry : ranks.entrySet()) {
			Rank rank = entry.getValue();
			rankDao.save(rank);
		}

		return this;
	}

	private DataInserter createGuards() {

		if (guardDao.countAll() > 0) {
			return this;
		}

		Map<String, Guard> guards = applicationContext
				.getBeansOfType(Guard.class);

		for (Map.Entry<String, Guard> entry : guards.entrySet()) {
			Guard guard = entry.getValue();
			guardDao.save(guard);
		}

		return this;
	}

	
}
