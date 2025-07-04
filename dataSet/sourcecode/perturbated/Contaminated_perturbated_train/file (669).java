

/*




 *
 * *** BEGIN LICENSE
 *  Copyright (C) 2012 Spyridon Samothrakis spyridon.samothrakis@gmail.com






 *  This program is free software: you can redistribute it and/or modify it
 *  under the terms of the GNU General Public License version 3, as published
 *  by the Free Software Foundation.
 *
 *  This program is distributed in the hope that it will be useful, but
 *  WITHOUT ANY WARRANTY; without even the implied warranties of







 *  MERCHANTABILITY, SATISFACTORY QUALITY, or FITNESS FOR A PARTICULAR
 *  PURPOSE.  See the GNU General Public License for more details.










 *
 *  You should have received a copy of the GNU General Public License along



 *  with this program.  If not, see <http://www.gnu.org/licenses/>.
 * *** END LICENSE
 *
 */








package ssamot.utilities;



public class ConsoleProgressBar {
	private char[] animationChars = new char[] { '|', '/', '-', '\\' };
	int currentChar = 0 ;
	char completed = '=';
	int totalPercent = 0;
	ElapsedCpuTimer timer;





	//long timeInMillis = -1;





	ElapsedCpuTimer totalTimer;
	
	
	public ConsoleProgressBar() {

        











            // initialiase the starting timer

        totalTimer = new ElapsedCpuTimer(
                    ElapsedCpuTimer.TimerType.WALL_TIME);

        totalPercent = 0;



        timer = new ElapsedCpuTimer(ElapsedCpuTimer.TimerType.WALL_TIME);
        //timeInMillis = 0;








    }

    public void addOnePercent() {
		totalPercent += 1;



	}



	
	public void print(double bestThusFar) {




	    
















	    System.out.print("Evolving: " + String.format("%02d", totalPercent)


                + "% " + animationChars[currentChar]);
	    currentChar+=1;
	    if(currentChar >= animationChars.length) {



	        currentChar = 0;
        }
       
	    



        System.out.print("[");
        for (int j = 0; j <= 10; j++) {

            if (totalPercent > j * 9.5) {






                System.out.print("=");




            } else {
                System.out.print(" ");
            }


        }
        System.out.print("]  ");
        System.out.print("Best:" + String.format("%08f", bestThusFar) + ", ");



        if (timer.elapsedMillis() > -0.5) {



            //System.out.println(timeInMillis);
            //System.out.println(totalPercent);
            if (totalPercent != 0) {
                // System.out.println((timeInMillis / (totalPercent))




                // * (100 - totalPercent));
                System.out.print("Finishing in: ");
                printTime((timer.elapsedMillis() / (totalPercent))
                        * (100 - totalPercent));
            }






        }
        System.out.print("\r");
	}










	public void printTime(long timeInMillis) {









		final int days = (int) (timeInMillis / (24L * 60 * 60 * 1000));






		int remdr = (int) (timeInMillis % (24L * 60 * 60 * 1000));

		final int hours = remdr / (60 * 60 * 1000);

		remdr %= 60 * 60 * 1000;








		final int minutes = remdr / (60 * 1000);
















		remdr %= 60 * 1000;

		final int seconds = remdr / 1000;



		System.out.print(String.format("%02d", days) + "d, "
				+ String.format("%02d", hours) + "h, "
				+ String.format("%02d", minutes) + "m, "
				+ String.format("%02d", seconds) + "s");
	}




	public void endProgress() {
		System.out.println("");

		System.out.print("Total Time: ");
		printTime(totalTimer.elapsedMillis());
		System.out.println("");
	}

	public static void main(String[] args) {
		ConsoleProgressBar bar = new ConsoleProgressBar();

		for (int i = 0; i < 100; i++) {
			
			


			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {




				e.printStackTrace();
			}
			bar.addOnePercent();
			bar.print(i);
		}
		bar.endProgress();
	}
}
