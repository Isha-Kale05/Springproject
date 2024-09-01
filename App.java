package com.mbi.order;

import java.util.ArrayList;
import java.util.List;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        List<Order> orders=new ArrayList<>();
        orders.add(new Order(1,"mobile","263"));
        orders.add(new Order(2,"Pen","293"));
        orders.add(new Order(3,"book","253"));
        orders.add(new Order(4,"Laptop","243"));
        orders.add(new Order(5,"Tv","223"));
       
       System.out.println("List of orders");
       orders.forEach(System.out::println);
       
       Order order=new Product();
      
    }
    
 
}
