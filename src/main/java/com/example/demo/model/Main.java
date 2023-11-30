package com.example.demo.model;

import java.util.HashMap;
import java.util.Map;

class Warehouse {
    private Map<String, Integer> products;

    public Warehouse() {
        this.products = new HashMap<>();
    }

    public void addProduct(String product, int stock) {
        products.put(product, stock);
    }

    public int stock(String product) {
        return products.getOrDefault(product, 0);
    }

    public boolean take(String product) {
        if (products.containsKey(product)) {
            int currentStock = products.get(product);
            if (currentStock > 0) {
                products.put(product, currentStock - 1);
                return true;
            }
        }
        return false;
    }

    public void printStock() {
        System.out.println("stock:");
        for (Map.Entry<String, Integer> entry : products.entrySet()) {
            if(entry.getKey().equals("coffee")){
                System.out.println(entry.getKey() + ":  " + entry.getValue());
            }else {
                System.out.println(entry.getKey() + ": " + entry.getValue());
            }

        }
    }


}


public class Main {
    public static void main(String[] args) {
        Warehouse warehouse = new Warehouse();
        warehouse.addProduct("coffee", 1);
        warehouse.addProduct("sugar", 0);

        // In ra thông tin stock trước khi lấy
        warehouse.printStock();

        // Lấy coffee 2 lần và sugar 1 lần
        System.out.println("taking coffee " + warehouse.take("coffee"));
        System.out.println("taking coffee " + warehouse.take("coffee"));
        System.out.println("taking sugar " + warehouse.take("sugar"));

        // In ra thông tin stock sau khi lấy
        warehouse.printStock();
    }
}