package data2;

import static data2.Tests.*;

/**
 * Testing class
 * @see Tests
 * 
 * Performance for this program should be as follows:
 *      Bags: best/average/worst case (assuming balancing works) = O(log n)
 *      Interfaces: best/average/worst case = O(n)
 *      Everything else: should be O(n) time as well
 */


public class Data2 {

    public static void main(String[] args) throws Exception {

        System.out.println("Random Int Checker - " + randInt(0, 100) + " " + randInt(0, 100) + " " + randInt(0, 100));

        int adNauseum = 10; //repeats all tests this many times
        for (int i = 0; i < adNauseum; i++) {

            Tests inty = new Tests(new RandomInteger());
            Tests stringy = new Tests(new RandomString());
            Tests booly = new Tests(new RandomBoolean());

            int randomInt = randInt(0, 1);
            inty.isEmptyHuhCardCheck(randomInt);
            stringy.isEmptyHuhCardCheck(randomInt);
            booly.isEmptyHuhCardCheck(randomInt);

            inty.cardAddCheck();
            stringy.cardAddCheck();
            booly.cardAddCheck();

            int randInt = randInt(0, 20);
            inty.cardAddSomeCheck(randInt);
            stringy.cardAddSomeCheck(randInt);
            booly.cardAddSomeCheck(randInt);

            inty.cardRemoveCheck();
            stringy.cardRemoveCheck();
            booly.cardRemoveCheck();

            inty.cardEmptyCheck();
            stringy.cardEmptyCheck();
            booly.cardEmptyCheck();

            inty.addMemberCheck();
            stringy.addMemberCheck();
            booly.addMemberCheck();

            inty.addMemberInterCheck();
            stringy.addMemberInterCheck();
            booly.addMemberInterCheck();

            inty.addRemoveEqualCheck();
            stringy.addRemoveEqualCheck();
            booly.addRemoveEqualCheck();

            int rInt = randInt(0, 20);
            inty.addRemoveSomeEqualCheck(rInt);
            stringy.addRemoveSomeEqualCheck(rInt);
            booly.addRemoveSomeEqualCheck(rInt);

            inty.unionSubsetCheck();
            stringy.unionSubsetCheck();
            booly.unionSubsetCheck();

            inty.unionCardCheck();
            stringy.unionCardCheck();
            booly.unionCardCheck();

            inty.unionMemberCheck();
            stringy.unionMemberCheck();
            booly.unionMemberCheck();

            inty.memberRemoveAllCheck();
            stringy.memberRemoveAllCheck();
            booly.memberRemoveAllCheck();

            inty.diffMemberCheck();
            stringy.diffMemberCheck();
            booly.diffMemberCheck();

            inty.diffEqualCheck();
            stringy.diffEqualCheck();
            booly.diffEqualCheck();

            inty.equalIntercheck();
            stringy.equalIntercheck();
            booly.equalIntercheck();

            inty.isRedHuhBlackenCheck();
            stringy.isRedHuhBlackenCheck();
            booly.isRedHuhBlackenCheck();

            // ideally I want a method that explicity checks whether the trees
            // produced are balanced, but the one I have right now doesn't do that
//            inty.balanceCheck();
//            stringy.balanceCheck();
//            booly.balanceCheck();

//          // these are for visualizing sequences - thanks again to bryce :)
//            inty.countItCardCheck();
//            stringy.countItCardCheck();
//
//            inty.toStringItCheck();
//            stringy.toStringItCheck();
        }

        System.out.println("");
        System.out.println("The following tests each passed " + adNauseum * Tests.repeats + " times");
        System.out.println(" over Integers, Strings, and Booleans! :)");
        System.out.println("");
        System.out.println("isEmptyHuhCardCheck()");
        System.out.println("cardAddCheck()");
        System.out.println("cardAddSomeCheck()");
        System.out.println("cardRemoveCheck()");
        System.out.println("cardEmptyCheck()");
        System.out.println("addMemberCheck()");
        System.out.println("addRemoveEqualCheck()");
        System.out.println("addRemoveSomeEqualCheck()");
        System.out.println("addMemberInterCheck()");
        System.out.println("unionSubsetCheck()");
        System.out.println("unionCardCheck()");
        System.out.println("unionMemberCheck()");
        System.out.println("memberRemoveAllCheck()");
        System.out.println("diffMemberCheck()");
        System.out.println("diffEqualCheck()");
        System.out.println("equalIntercheck()");
        System.out.println("isRedHuhBlackenCheck()");
        System.out.println("");
    }
}
