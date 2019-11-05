package com.company;

import java.util.HashMap;
import java.util.Vector;

public class Main {
    static Vector<Integer> Tags = new Vector<Integer>();
    static Vector<String> Dictrionary = new Vector<String>();
    static HashMap<String,Integer> Dic = new HashMap<String, Integer>();
    static HashMap<Integer,String> DecompresseDic = new HashMap<Integer, String>();
    static int indx = 0 ;
    public static void main(String[] args) {
	// write your code here
        String in = "ABAABABBAABAABAAAABABBBBBBBB";
        Main.compress(in);
        String ret=  Main.Decomprese();
        System.out.println(in);
        System.out.println(ret);
        if(ret.compareTo(in) == 0){
            System.out.println("YES");
        }
        else System.out.println("NO");
    }
     static void compress(String in){
        String ret= ""+ in.charAt(0);
        indx= 65;
        for (int i = 65 ; i < 128 ; ++i) {
//            String ss= "";
//            if(Dic.containsKey(ss+in.charAt(i)) == false){
                String s =""+(char)i;
                Dic.put(s , i);
//            }
        }
        indx= 128;
        for (int i = 1 ; i < in.length() ; ++i){
//            ret+=in.charAt(i);
            if(!Dic.containsKey(ret + in.charAt(i))){
                Dic.put(ret+in.charAt(i) , indx++);
                Tags.add(Dic.get(ret));
                ret = "" + in.charAt(i);
            }else
            ret +=in.charAt(i);
        }
        if(ret.compareTo("" ) !=0){
            Tags.add(Dic.get(ret));

        }
        for (int i = 0 ; i < Tags.size(); ++i)
            System.out.print(Tags.elementAt(i) + " ");
         System.out.println("");

    }
    static String Decomprese(){
        for (int i = 65; i <128 ; ++i){
            String s = ""+ (char)i;
            DecompresseDic.put(i , s);
        }
        String res=""; String prev="";
        indx = 128;
        for (int i : Tags) {
            if(DecompresseDic.containsKey(i)){
                res = res + DecompresseDic.get(i);
                String toadd ="";
//                if(prev.compareTo("") !=0)
                 toadd = prev + DecompresseDic.get(i).charAt(0);
                prev = DecompresseDic.get(i);
                if(toadd.compareTo(DecompresseDic.get(i)) !=0)
                    DecompresseDic.put(indx++ , toadd);
            }
            else {
                String toadd = prev + prev.charAt(0);
                res= res + toadd;
                prev = toadd;
                DecompresseDic.put(indx++ , toadd);
            }
        }
//        System.out.println(res);
        return res;
    }
}
