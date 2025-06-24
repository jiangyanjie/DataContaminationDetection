package ag.twittersimulation.user;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.TreeMap;

public abstract class AbstractUserLoader {
	public abstract TreeMap<String, User> LoadUsers() throws IOException;
	
	protected static String RemoveSpaces(String stringWithSpaces) {
		char[] charArrayWithSpaces = stringWithSpaces.toCharArray();
		StringBuilder stringWithoutSpaces = new StringBuilder();
		
		for (char c: charArrayWithSpaces) {
			if (c != ' ') {
				stringWithoutSpaces.append(c);
			}
		}
		return stringWithoutSpaces.toString();
	}
	
	protected static String GetFollowerName(String userFileLine) {
		int followerNameEnds = userFileLine.indexOf(" follows ");
		return userFileLine.substring(0, followerNameEnds);
	}
	
	protected static ArrayList<String> GetFolloweeNames(String userFileLine) {
		int followerNameEnds = userFileLine.indexOf(" follows ");
		int followeeNamesStart = followerNameEnds + 9;
		String strFolloweeNames = RemoveSpaces(userFileLine.substring(followeeNamesStart));
		ArrayList<String> followeeNames = new ArrayList<String>(Arrays.asList(strFolloweeNames.split(",")));
		return followeeNames;
	}

	protected static TreeMap<String, User> AddUsers(TreeMap<String, User> users, String followerName, ArrayList<String> followeeNames) {
		TreeMap<String, User> tempUsers = users;
		if (tempUsers.get(followerName) == null) {
			User followerUser = new User(followerName, followeeNames);
			tempUsers.put(followerName, followerUser);
		} else {
			User existingUser = tempUsers.get(followerName);
			existingUser.AddFollower(followeeNames);
			tempUsers.put(followerName, existingUser);
		}
		
		for (String followee: followeeNames) {
			if (tempUsers.get(followee) == null) {
				User followeeUser = new User(followee);
				tempUsers.put(followee, followeeUser);
			}	 
		}		
		return tempUsers;
	}
	
}
