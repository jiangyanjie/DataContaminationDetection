/*
 * Apartment.java
 *
 * Created on April 30, 2007, 11:14 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package fhe;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author n8
 */
public class Apartment implements Comparable<Apartment> {
	List<Person> residents = new ArrayList<Person>();

	Gender gender;

	Address address;

	private int preAssignedGroupNum = -1;

	/** Creates a new instance of Apartment */
	public Apartment(List<Person> people) {
		if (people == null)
			throw new IllegalArgumentException("people is null");
		if (people.size() == 0)
			throw new IllegalArgumentException("apartment must have at least 1 resident");
		address = people.get(0).address;
		gender = people.get(0).gender; // assuming all residents have same
										// gender? Not a good idea
		for (Person per : people) {
			residents.add(per);
			extractPreAssignedGroupNum(per);
			doubleCheckAddress(per);
		}

	}

	private void doubleCheckAddress(final Person per) throws IllegalArgumentException {
		if (!per.address.equals(address))
			throw new IllegalArgumentException("Residents must live in same address. " + per.fullName + " lives in '"
					+ per.address + "', which doesn't equal '" + address + "'");
	}

	private void extractPreAssignedGroupNum(final Person p) throws IllegalStateException {
		if (p.getPreAssignedGroupNum() >= 0) {
			if (preAssignedGroupNum < 0) {
				preAssignedGroupNum = p.getPreAssignedGroupNum();
			} else if (p.getPreAssignedGroupNum() != preAssignedGroupNum)
				throw new IllegalStateException(
						"can't have two people in the same apartment pre-assigned to different groups");
			else {
				// two people have been assigned to the same group, do nothing
			}
		}
	}

	public int size() {
		return residents.size();
	}

	public void print() {
		System.out.println("\n\n-----------------------------------------");
		System.out.println("   " + address);
		System.out.println("-----------------------------------------");
		for (Person per : residents) {
			System.out.println(per.fullName);
		}
	}

	public void printAsLine() {
		StringBuilder sb = new StringBuilder();
		sb.append(address);
		sb.append(" - ");
		for (Person per : residents) {
			sb.append(per.fullName + "; ");
		}
		System.out.println(sb.toString());
	}

	public static void printApts(Collection<Apartment> apts) {
		for (Apartment apt : apts) {
			System.out.println("\n\n-----------------------------------------");
			System.out.println("   " + apt.address);
			System.out.println("-----------------------------------------");
			List<Person> persons = apt.residents;
			for (Person per : persons) {
				System.out.println(per.fullName);
			}
		}
	}

	public int compareTo(Apartment o) {
		Apartment other = (Apartment) o;
		return address.compareTo(other.getAddress());
	}

	public int getPreAssignedGroupNum() {
		return preAssignedGroupNum;
	}

	public Address getAddress() {
		return address;
	}

	public List<Person> getResidents() {
		return residents;
	}
}
