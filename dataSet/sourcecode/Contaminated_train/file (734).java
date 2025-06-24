import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
public class ConstructHouse {

	/**
	 * This class holds the main method for input which is used to make a house
	 * object
	 * @param args
	 */
	public static void main(String[] args) {
		boolean hasYard = false;
		List<Double> coordsList = new ArrayList<Double>();
		Scanner input = new Scanner(System.in);
		System.out.print("Please enter the number of bedrooms your house will have: ");
		int numHouses = input.nextInt();
		System.out.print("Please enter the number of bathrooms your house will have: ");
		int numBathrooms = input.nextInt();
		System.out.print("Will this house have a yard? (y/n): ");
		String hasYardInput = input.next();
		if (hasYardInput == "y" || hasYardInput == "Y")
				hasYard = true;
		else hasYard = false;
		System.out.print("What material will the roof be made out of?: ");
		String roofMaterial = input.next();
		//loop 4 times to put required coordinates in
		for (int i = 0; i < 4; i++){
			System.out.print("Please enter in coord x position number " + (i + 1) + ": ");
			coordsList.add(input.nextDouble()); //add x here
			System.out.print("Please enter in coord y position number " + (i + 1) + ": ");
			coordsList.add(input.nextDouble()); // and add y here
			
		}
		double[] gpsCoords = new double[coordsList.size()];
		for (int i = 0; i < gpsCoords.length; i++){
			gpsCoords[i] = coordsList.get(i);
		}
		//creates the house with captured input
		House newHouse = new House(numHouses, numBathrooms, hasYard, roofMaterial, gpsCoords);
		// output statements
		System.out.println("The total area is: " +  newHouse.getArea() + "sqm");
		System.out.println("The total perimeter is: " + newHouse.getPerimeter());
		System.out.println("The total for this quote is : $" + newHouse.getCost());
	}

}
