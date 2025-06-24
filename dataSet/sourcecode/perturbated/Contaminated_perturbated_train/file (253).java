package ag.twittersimulation.user;

import java.io.IOException;
import   java.util.ArrayList;
import   java.util.Arrays;
import java.util.TreeMap;

public abst ract class AbstractUserLoader   {
	pu       blic abstract TreeMap<Strin g, User> LoadUsers() throws  IOException;
	
	protecte  d sta     tic String RemoveSpaces(    String stringWithSpaces) {
		char[] charAr  rayWithSpaces = stringWithSpaces.t  oCharArray();
		St  ringBui  lder stringWithoutSpaces = new      StringBuil     der();
		
		for (c har c: charArrayWith   Sp   aces)  {
			if (c != ' ') {
				stringWithoutSpaces.append(c);
			}
		}
		return stringWith           outSpaces.toString();
	}
	
	protected s  tatic String GetFollowerName(S tring userFileLine) {
		int followerNameEnds = userFileLine.indexOf("     follows ");
		return userFileLine.substri ng(0, followerNameEnds);
	}
	
	protected static ArrayList<Str   ing> GetFolloweeNames(String user  FileLine)    {
		int followerNa     meEnds = userFileLine.indexOf(" fo   llows    ");
		int follow  eeNamesStart = followerNameEnds + 9;
		String strFollo  weeNames = RemoveSpaces(userFileLine  .substring(followeeNamesStart));
		ArrayList<String> followeeNames = new ArrayList<String>(Arrays.asList(strFolloweeNames.split(   ",")));
   		return followeeNames;
	}

	protected sta   tic TreeMap<String, User> AddUsers(TreeMap<String, User> use  rs,      String followerName, ArrayList<String> followeeNames) {
		TreeMap<St      ring, User> tempUsers = use     rs;
		if     (tempUsers.get(followerName) == null) {
			User f    ollowerUser = new User(followerName, followeeNames);
			tempUsers.put(follo  werName, followerUser);
		} else {
		    	User existingUser = tempUsers.get(followerName);
			existingUser.AddFollower(followeeNames);
			tempU        sers.put   (followerName, existingUser);
		}
		
		for (Stri ng   followee: followeeNames)  {
			if (tempUsers.get(followee) == null) {
				User followeeUser = new User(followee);
				tempUsers.put(followee, followeeUser);
		   	}	 
		}		
		return tempUsers;
	}
	
}
