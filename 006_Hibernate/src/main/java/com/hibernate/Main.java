package com.hibernate;

import com.hibernate.api.CarStoreAPI;
import com.hibernate.entity.Car;
import com.hibernate.entity.Lot;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class Main {
    public static void main(String[] args) {
        CarStoreAPI store = new CarStoreAPI();

        //Creating new cars
        Car car1 = new Car();
        car1.setNew(false);
        car1.setProduced(new GregorianCalendar(2014, Calendar.FEBRUARY, 15).getTime());
        car1.setModel("Supra");
        car1.setBrand("Toyota");

        Car car2 = new Car();
        car2.setNew(true);
        car2.setProduced(new GregorianCalendar(2022, Calendar.SEPTEMBER, 11).getTime());
        car2.setModel("Camry");
        car2.setBrand("Toyota");

        Car car3 = new Car();
        car3.setNew(true);
        car3.setProduced(new GregorianCalendar(2021, Calendar.APRIL, 9).getTime());
        car3.setModel("Skoda");
        car3.setBrand("Octavia");

        //Creating new lots
        Lot lot1 = new Lot();
        lot1.setAvailable(1);
        lot1.setPrice(21000);
        lot1.setCar(car1);

        Lot lot2 = new Lot();
        lot2.setAvailable(7);
        lot2.setPrice(30000);
        lot2.setCar(car2);

        Lot lot3 = new Lot();
        lot3.setAvailable(5);
        lot3.setPrice(28000);
        lot3.setCar(car3);

        //adding lots to the API list and transferring them to the database
        System.out.println("\n1. Adding new lots");
        store.addLot(lot1);
        store.addLot(lot2);
        store.addLot(lot3);
        store.push();

        //selecting records with different parameters
        System.out.println("\n2. All 'Toyota' lots\n"
                + store.loadAllByBrand("Toyota"));
        System.out.println("\n3. All cars in the price range of 28000 - 30000\n"
                + store.loadByPriceBetween(28000, 30000));
        System.out.println("\n4. All 'Toyota' lots in the price range of 29000 - 31000\n"
                + store.loadByBrandAndPriceBetween("Toyota", 29000, 31000));
        System.out.println("\n5. All lots\n"
                + store.loadAllLots());

        //changing values in the last lots of the list and push them to the database
        System.out.println("\n6. Updating of the last two lots\n");
        lot1 = store.get(store.size() - 1);
        lot1.setAvailable(999999);

        lot2 = store.get(store.size() - 2);
        lot2.setPrice(888888L);
        lot2.getCar().setBrand("--Test--");
        lot2.getCar().setModel("-Test-");

        store.push();
        System.out.println(store);

        //deleting 3 last lots
        System.out.println("\n7. Deleting of the last three lots (without cars)\n");
        store.removeLot(store.get(store.size() - 1));
        store.removeLot(store.get(store.size() - 1));
        store.removeLot(store.get(store.size() - 1));

        store.push();

        //get all cars without associated lots
        System.out.println("\n8. Find all detached cars\n"
                + store.getCarsWithoutLots());
        //remove all detached cars from the database
        System.out.println("\n9. Remove detached cars from the database\n");
        store.clearDetachedCars();

        //final selection
        System.out.println("\n10. Final selection\n"
                + store.loadAllLots());
    }
}
