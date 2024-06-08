package com.mokshesh.misc;

import javax.swing.plaf.synth.SynthOptionPaneUI;
import java.util.HashSet;
import java.util.Set;

public class SetComparison {
  public static void main(String[] args) {
    Set<Integer> set1 = new HashSet<>();
    set1.add(1);
    set1.add(2);
    set1.add(3);
    set1.add(4);
    set1.add(5);

    Set<Integer> set2 = new HashSet<>();
    set2.add(1);
    set2.add(2);
    set2.add(3);
    set2.add(4);
    set2.add(5);
//    set2.add(6);
//    set2.add(7);

    System.out.println(set1.equals(set2) ? "Equal" : "Not Equal");


    // Find the union of two sets
//    Set<Integer> union = new HashSet<>(set1);
//    union.addAll(set2);
//    System.out.println("Union of two sets: " + union);
//
//    // Find the intersection of two sets
//    Set<Integer> intersection = new HashSet<>(set1);
//    intersection.retainAll(set2);
//    System.out.println("Intersection of two sets: " + intersection);
//
//    // Find the difference of two sets
//    Set<Integer> difference = new HashSet<>(set1);
//    difference.removeAll(set2);
//    System.out.println("Difference of two sets: " + difference);
  }
}
