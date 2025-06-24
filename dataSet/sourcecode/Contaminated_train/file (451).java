package arrays;

import java.util.Arrays;

/**
 * @author Dilip
 *
 *         github Profile: https://github.com/satya444
 *
 */
public class ActivitySelection {

	class Activity implements Comparable<Activity> {
		int startTime;
		int EndTime;

		Activity(int startTime, int endTime) {
			this.startTime = startTime;
			this.EndTime = endTime;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Comparable#compareTo(java.lang.Object)
		 */
		@Override
		public int compareTo(Activity in) {

			return Integer.compare(this.startTime, in.startTime);
		}
	}

	public void getActivities(Activity[] allActivities) {
		Arrays.sort(allActivities);
		int[] activityCount = new int[allActivities.length];
		int[] backTrack = new int[allActivities.length];
		for (int i = 0; i < allActivities.length; i++) {
			backTrack[i] = -1;
		}
		for (int i = 0; i < allActivities.length; i++) {
			activityCount[i] = 1;
		}
		int j = 1;
		int i = 0;

		while (j < allActivities.length) {
			for (i = 0; i < j; i++) {
				if (allActivities[i].EndTime < allActivities[j].startTime) {
					if (1 + activityCount[i] > activityCount[j]) {
						activityCount[j] = 1 + activityCount[i];
						backTrack[j] = i;
					}
				}
			}
			j++;
		}
		int maxActivities = 0;
		int endIndex = -1;
		for (int k = 0; k < allActivities.length; k++) {
			if (activityCount[k] > maxActivities) {
				maxActivities = activityCount[k];
				endIndex = k;
			}
		}

		printBackTrackArray(allActivities, backTrack, endIndex);
	}

	/**
	 * @param backTrack
	 * @param endIndex
	 */
	public void printBackTrackArray(Activity[] allActivities, int[] backTrack,
			int endIndex) {
		while (endIndex != -1) {
			System.out.println(allActivities[endIndex].startTime + " "
					+ allActivities[endIndex].EndTime);
			endIndex = backTrack[endIndex];
		}
	}

	public static void main(String args[]) {
		ActivitySelection as = new ActivitySelection();
		Activity a[] = { as.new Activity(5, 24), as.new Activity(39, 60),
				as.new Activity(15, 28), as.new Activity(27, 40),
				as.new Activity(50, 90) };
		as.getActivities(a);
	}
}
