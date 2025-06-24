/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package business.client;

import business.filter.attempt4.AppleColourPredicate;
import business.filter.attempt4.ApplePredicate;
import business.objects.Apple;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Narendran
 */
public class AppleClient {
    
    
    public static void main(String[] args) {
        List<Apple> inventory  = new ArrayList();
        Apple apple1 = new Apple(100L, "green");
        inventory.add(apple1);
        Apple apple2 = new Apple(120L, "green");
        inventory.add(apple2);
        Apple apple3 = new Apple(140L, "green");
        inventory.add(apple3);
        Apple apple4 = new Apple(100L, "red");
        inventory.add(apple4);
        Apple apple5 = new Apple(120L, "red");
        inventory.add(apple5);
        Apple apple6 = new Apple(140L, "red");
        inventory.add(apple6);
        Apple apple7 = new Apple(160L, "red");
        inventory.add(apple7);
        Apple apple8 = new Apple(170L, "red");
        inventory.add(apple8);
        Apple apple9 = new Apple(180L, "green");
        inventory.add(apple9);
        Apple apple10 = new Apple(180L, "red");
        inventory.add(apple10);
        
        //attempt One
        List<Apple> filteredApples = null;
        filteredApples = business.filter.attempt1.AppleFilter.filterGreenApples(inventory);
        
        for(Apple apple:filteredApples)
        {
            System.out.println("Attempt 1");
            System.out.println(apple);
        }
        
        //attempt Two
        filteredApples = business.filter.attempt2.AppleFilter.filterApplesByColour(inventory, "green");
        
        for(Apple apple:filteredApples)
        {
            System.out.println("Attempt 2");
            System.out.println(apple);
        }
        
         //attempt Three
        filteredApples = business.filter.attempt3.AppleFilter.filter(inventory, "green",150L);
        
        for(Apple apple:filteredApples)
        {
            System.out.println("Attempt 3");
            System.out.println(apple);
        }
        
        //attempt Four
        ApplePredicate testGreenColour = new AppleColourPredicate(); //Behaviour Parameterized
        
        filteredApples = business.filter.attempt4.AppleFilter.filter(inventory, testGreenColour); // function as parameter
        
        for(Apple apple:filteredApples)
        {
            System.out.println("Attempt 4");
            System.out.println(apple);
        }
        
        
        //attempt Five : Anonymous Function usage
        
        filteredApples = business.filter.attempt4.AppleFilter.filter(inventory, new ApplePredicate() {

            @Override
            public boolean test(Apple apple) {
                return "green".equalsIgnoreCase(apple.getColour());
            }
        }); // function as parameter
        
        for(Apple apple:filteredApples)
        {
            System.out.println("Attempt 5");
            System.out.println(apple);
        }
        
        //attempt Six : Anonymous Function usage
       
        filteredApples = business.filter.attempt5.GenericFilter.filter(inventory, 
                (Object obj)->{
                    Apple apple = (Apple) obj;
                    return "green".equalsIgnoreCase(apple.getColour());}); // function as parameter
        
        for(Apple apple:filteredApples)
        {
            System.out.println("Attempt 6");
            System.out.println(apple);
        }
    }
            
}
