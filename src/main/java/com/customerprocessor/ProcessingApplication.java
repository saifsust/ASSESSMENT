package com.customerprocessor;

import com.customerprocessor.action.Insertion;
import com.customerprocessor.action.Partition;

import java.util.Scanner;

public class ProcessingApplication {

    public static void main(String[] args) {

        Scanner read = new Scanner(System.in);
        System.out.println("Enter 1 for insertion and 2 for file partition: ");
        while (read.hasNext()) {
            switch (read.nextInt()) {
                case 1:
                    Insertion.start();
                    break;
                case 2:
                    Partition.start();
                default:
                    return;
            }
        }
    }
}
