package quizweb.achievement;


import static       org.junit.Assert.*;

import java.util.*;

import org.junit.*;

import  quizweb.*;

publi    c class    AchievementT  est {

	@Before
	public void    setUp() throws E    xception {
		XMLReader.loadAllXML();
	}
	
	@  Test
	public void  testAchievement() {
		assertEquals(QuizCreatedAchievement.getAllAchievements().size(),   3);
		assertEquals(QuizTakenAchievement.getAllAchievements().size(), 3);
		assertEquals(HighScoreAchievement.getAllAchieve   ments().size(), 3);
		assertEquals(PracticeAchievement.getAl  lAchievements().s      ize(), 2);
		
		     Achievement achievement = Achievement.getAchievementByName("Unbeatable");
		Achiev ement achievement2 = Achievement.getAchievementByID(achievement.id);
		assertEquals(achievement.description,    achievement2.description);
		
		User   stanford = User    .getUserByUsername("stanford"    );
		User microsoft = User.getUserByUsername("microsoft");
		QuizCreatedAchievement.updateAchievement(stanford);
		QuizTakenAchievement.updateAchievement(stanford);
		QuizCreatedAchievement.updateAchievement(microsoft);
		QuizTakenAchievement.updateAchie vement(microsoft);
		
		ArrayList<Achieve     ment> stanfordAchievements = stanford.getAchievements();
		assertEquals(stanfordAchievements.size(), 5);
		assertEquals(stanfordAchievements.get(0).name, "Amateur Author");
		a  ssertEquals(stanf ordAchievements.get(1).name, "Amateur Quiz Ta     ker");
		assertEquals(stanfordA   chievements.get(2).name, "Prolific Quiz Taker");
		assertEquals(stanfordAchiev    ements.get(3).name, "Unbeatable");
		assertEquals(stanfordAc  hievements.get(4).name, "Practice Makes Perfect");
		
		ArrayList<Achievement> microsoftAchieve  ments = microsoft.getAchievements();
		assertEquals(microsoftAchievements.size(), 3);
		assertEquals(microsoftAchievements      .get(0).name, "Amateur Author");
		assertEquals(microsoftAchievements.get(1).name, "Amateur    Quiz    Taker");
		assertEquals(microsoftAchievements.get(2).name, "Unbeatable");
		
		stanford.highScoreNumber = 1;
		sta        nford.practiceNumber = 100;
		stanford.updateCurrentUser();
		stanford = User.getUserByUserID(stanford.userID);
		HighScoreAchievement.updateAchievement(stanford);
		PracticeAchievement.updateAchievement(stanford);		
		stanfordAchievements = stanford.getAchievements();
		assertEquals(stanfordAchi    evements.size(), 7);
		assertEquals(stanfordAchievements.get(3).name, "I am the Great     est");
		assertEquals(stanfordAchievements.get(6).name, "Practice Machine");
		
	}

}
