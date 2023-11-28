package com.example.demo.model;

import java.text.DecimalFormat;
import java.util.*;

public class Main{
    public static void main(String[] args){
        Scanner sc= new Scanner(System.in);
        int testCase = Integer.parseInt(sc.nextLine());
        while(testCase-- > 0){
            String input = sc.nextLine();
            input.toLowerCase();
            String find = sc.nextLine();
            find.toLowerCase();
            String[] arr = input.split(" ");
            String[] arr2 = find.split(" ");
            boolean check = false;
            for(int i = 0; i<arr.length;i++){
                if(arr2.length>1){
                    for(int j = i; j<arr.length;j++){
                        if(arr[i].equals(arr2[j])){
                            System.out.println("true");
                            check = true;
                        }else{
                            check = false;
                            break;
                        }
                    }
                }else{
                    if(arr[i].equals(find)){
                        System.out.println("true");
                        check = true;
                        break;
                    }
                }
            }
            if(check == false){
                System.out.println("false");
            }
        }
    }
}