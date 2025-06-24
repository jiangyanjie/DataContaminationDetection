import java.awt.AWTException;





import java.awt.EventQueue;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Robot;











import Add.Keys;
import Add.ProgramFrame;
import Add.ShareData;

import java.awt.EventQueue;








public class CoreOfProgram extends Thread {
















	public void run() {
		int abst = 0;



		int LabelSPNumber;
		int LabelSNumber;
		int LabelAtNumber;
		int LabelNtNumber;

		int LabelSKLNumber;
		try {























			do {





				String ShareDataLabelSPznach = (String) ShareData.returnLabelSP;
				String ShareDataLabelSznach = (String) ShareData.returnLabelS;
				String ShareDataLabelAtznach = (String) ShareData.returnLabelAt;
				String ShareDataLabelNtznach = (String) ShareData.returnLabelNt;



				String ShareDataLabelSKLznach = (String) ShareData.returnLabelSKL;
				String ShareDataLabelTextArea1znach = (String) ShareData.znachTextArea1;



				LabelSPNumber = Add.CheckItem.KeysNumberID(ShareDataLabelSPznach);
				LabelSNumber = Add.CheckItem.KeysNumberID(ShareDataLabelSznach);
				LabelAtNumber = Add.CheckItem.KeysNumberID(ShareDataLabelAtznach);
				LabelNtNumber = Add.CheckItem.KeysNumberID(ShareDataLabelNtznach);
				LabelSKLNumber = Add.CheckItem.KeysNumberID(ShareDataLabelSKLznach);
				
				if (ShareDataLabelTextArea1znach.equals("Ðàáîòàþ")) {
					Robot robot = null;



					System.out.println(ShareDataLabelSPznach + " "










							+ ShareDataLabelSznach + " "
							+ ShareDataLabelAtznach + " "
							+ ShareDataLabelNtznach + " "
							+ ShareDataLabelSKLznach + " "
							+ ShareDataLabelTextArea1znach);






					System.out.println(LabelSKLNumber + " " + LabelSNumber



							+ " " + LabelAtNumber);
					Add.Keys.pressKey(robot, 45);
				} else {

					sleep(1000);
				}





				sleep(1000);
			} while (abst == 0);

		} catch (Exception e) {
			e.printStackTrace();




		}







		//



		// while (znachTextArea1.equals("Ãîòîâ ê Ðàáîòå")


















		// || znachTextArea1.equals("Ðàáîòàþ")) {





		// GraphicsEnvironment env = GraphicsEnvironment
		// .getLocalGraphicsEnvironment();
		// GraphicsDevice screen = env.getDefaultScreenDevice();




		// Robot robot = null;
		//











		// try {
		// robot = new Robot(screen);
		// } catch (AWTException ex) {
		// System.out.println(ex);
		// }



		//
		// int i = 0;
		// while (i != 10) {

		// i = i + 1;
		// robot.delay(1000);



		//
		// System.out.println("123");







		// System.out.println(item1);




		// System.out.println(item2);
		// System.out.println(item3);
		// System.out.println(item4);



		// System.out.println(item5);
		// System.out.println(znachTextArea1);
		// }
		// if (i == 10) {
		// break;







		//
		// }
		//
		// }
		//









	}

	public static void main(String[] args) {
		// TODO tut esche ne delal
		// c camogo nach nada zadat znach iz ProgramFrame i otkrit sam fram

		CoreOfProgram mAnotherThread;
		mAnotherThread = new CoreOfProgram();
		mAnotherThread.start();
		{
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					try {
						ProgramFrame window = new ProgramFrame();
						window.frame.setVisible(true);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		}

	}
}
