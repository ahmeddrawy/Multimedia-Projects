package Huffman;

import javafx.util.Pair;

import java.util.*;
///-2,0,0,2,0,0,3,2,0,1,0,0,-2,0,-1,0,0,1,0,0,-1
///21 -2 0 0 2 0 0 3 2 0 1 0 0 -2 0 -1 0 0 1 0 0 -1
public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n ;
        n = sc.nextInt();
        int []arr= new int[n];
        for (int i = 0 ; i < n ; ++i){
            arr[i] = sc.nextInt();
        }
        ///for(int i = 0 ; i < n ; ++i) System.out.println(arr[i]);
        Tree t = new Tree() ;
        t.build(arr , n );

    }
}
