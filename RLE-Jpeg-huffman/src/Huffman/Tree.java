package Huffman;

import javafx.util.Pair;

import java.io.*;
import java.net.Inet4Address;
import java.util.*;

public class Tree {
    Node root = null;
    public Tree( Node _root ){
        root = _root;
    }
    Tree(){

    }
    private void toLeaves(Node start , String state){
        if(start.left==null || start.right == null )
            return;
        start.left.setEncoding(state +'0');
        toLeaves(start.left, state+'0');
        start.right.setEncoding(state +'1');
        toLeaves(start.right, state+'1');
    }
    private void runHuffman(Node n1 , Node n2){
        n1.setEncoding("0"); ///small 0 ,big 1
        n2.setEncoding("1");
        toLeaves(n1,n1.getEncoding());
        toLeaves(n2,n2.getEncoding());
        root = new Node(n1, n2 , 1);
    }
    public  void build(int arr[] , int n ,String path  ){
        Vector<Pair< Pair<Integer , Integer> , String >> v  =getCategories(arr , n );
        HashMap<Pair<Integer , Integer > , Integer> frq = new HashMap<>();
        for(int i = 0 ; i < v.size() ; ++i){
            if(frq.containsKey(v.elementAt(i).getKey() )){
                frq.put(v.elementAt(i).getKey() , frq.get(v.elementAt(i).getKey()) +1);
            }
            else
                frq.put(v.elementAt(i).getKey() ,  1);

        }
        frq.put(new Pair<>(0,0) ,1 );
        PriorityQueue<Node> pq = new PriorityQueue<Node>();
        HashMap<Pair<Integer , Integer> , Node> map = new HashMap<>();
        Iterator it = frq.entrySet().iterator();
        while(it.hasNext()) {
            Map.Entry mp = (Map.Entry)it.next();
            Node nn = new Node((Pair<Integer, Integer>) mp.getKey(),1.0*(Integer)mp.getValue()/(n+1));
            pq.add(nn);
            map.put((Pair<Integer, Integer>) mp.getKey() , nn);
//            vec.add(nn);
        }
        while(pq.size()>2){
            Node n1 = pq.peek();
            pq.remove();
            Node n2 = pq.peek();
            pq.remove();
            Node n3  = new Node(n1, n2 , n1.getProbabiliy() + n2.getProbabiliy());///small left
          //  n3.setCurr(n1.getCurr() + n2.getCurr());
            pq.add(n3);
        }
        Node n1 = pq.peek();
        pq.remove();
        Node n2 = pq.peek();
        pq.remove();

        runHuffman(n1 , n2);
        /// todo print huffman table
        Set<Pair <Pair<Integer , Integer> , String> > mset = new HashSet<>();
        for (Pair< Pair<Integer , Integer> , String > itt: v) {
          //  System.out.println( itt.getKey().getKey() + " " +itt.getKey().getValue()+" "+map.get(itt.getKey()).getEncoding());
            mset.add( new Pair <Pair<Integer , Integer> , String>   (itt.getKey() , map.get(itt.getKey()).getEncoding()));

        }
        mset.add(new Pair <Pair<Integer , Integer> , String>   ( new Pair<>(0,0), map.get(new Pair<>(0,0)).getEncoding()));
        for(Pair <Pair<Integer , Integer> , String>  itt : mset){
            System.out.println(itt.getKey());
            System.out.println(itt.getValue());
            System.out.println("----");
        }
            String ret = "";
        for (Pair< Pair<Integer , Integer> , String > itt: v) { ///encoded string
            ret+=map.get(itt.getKey()).getEncoding() + itt.getValue();
//            System.out.println(map.get(itt.getKey()).getEncoding() + " "+ itt.getValue() );
        }
        System.out.println(ret);
        writetofile(path ,ret ,mset);

    }
    /// taking an array of integers and convert it to vectors of <<cnt of zeros , number> , binary representation>
    private Vector<Pair< Pair<Integer , Integer> , String >> getCategories(int arr[] , int n ){
        Vector<Pair< Pair<Integer , Integer> , String>> ret= new Vector<Pair< Pair<Integer , Integer> , String>>();

        int cnt=0 , lst = 0  ;
        for (int i = 0 ; i<n ; ++i, cnt++){
            if(arr[i] !=0){
                int catg = 0 ;
//                char c;
                boolean negative =arr[i]<0;
                catg = getCategory(Math.abs(arr[i]));

                String indx= getIndex(Math.abs(arr[i]),catg , negative);
                ret.add( new Pair <Pair<Integer, Integer> ,String>(   new Pair<Integer, Integer>(cnt,catg)   , indx));
                cnt = -1 ;
                lst = i ;
            }

        }




        return ret;
    }
    /// get the category of the number  floor(log2(n)) +1
    private int getCategory(int  n){
        return (int)Math.floor(Math.log(n)/Math.log(2)) +1;
    }
    private String getIndex(int n , int category , boolean negative){
        String ret=  "";
        if(category>1) {
            int indx = n - (1 << (category - 1));      /// n - 2^category-1
            if (negative)
                indx = (1 << (category - 1) - 1) - indx;
            ret = Integer.toBinaryString(indx);
            for (int i = ret.length(); i < category - 1; ++i) {
                ret = '0' + ret;
            }
        }
        if(negative)
            return '0' + ret;
        return '1' + ret;

    }
    private void writetofile(String path , String encoded ,Set<Pair <Pair<Integer , Integer> , String> > mset ){
        /// todo write code book here
//        PrintStream p = null;
        DataOutputStream p = null;
        System.out.println("1");
        try {
            p = new DataOutputStream(new FileOutputStream(path) );
            p.writeInt(mset.size());
            for(Pair <Pair<Integer , Integer> , String> itt : mset ){
                p.writeInt(itt.getKey().getKey());
                p.writeInt(itt.getKey().getValue());
                p.writeInt(itt.getValue().length());
                p.writeBytes(itt.getValue());
            }
            p.writeInt(encoded.length());
            p.writeBytes(encoded);


        } catch (FileNotFoundException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("finished");

    }

    public Vector<Pair< Pair<Integer , Integer> , String >>  readFromFile(String path ){
        String encoded = "";
        Vector<Pair <Pair<Integer , Integer> , String> > mset = new Vector<>();

        /// todo read vheight, vwidth , number of vectors,
        if(!new File(path).canRead()){
            System.out.println("cant read");
        }
        try {
            DataInputStream f = new DataInputStream(new FileInputStream(path));
            try {
                int booksz = f.readInt();
                for (int i = 0 ; i < booksz ; ++i){
                    int zeroCnt = 0 , category = 0 ;
                    String representation = "";
                    zeroCnt= f.readInt();
                    category = f.readInt();
                    int sz = f.readInt();
                    byte[] arr =new byte[sz];
                    f.read(arr);
                    representation=   new String(arr);
                    mset.add(new Pair<>(new Pair<>(zeroCnt , category ) , representation) );
                }
                for(Pair <Pair<Integer , Integer> , String> itt : mset){
                    System.out.println(itt.getKey().getKey());
                    System.out.println(itt.getKey().getValue());
                    System.out.println(itt.getValue());
                }
                int sz = f.readInt();
                byte[] arr = new byte[sz];
                f.read(arr);
                encoded= new String(arr);
                System.out.println(encoded);

            } catch (IOException e) {
                e.printStackTrace();
            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        /// read code book
        return mset;

    }

    public void Decode(String FilePath, String seq){
        Vector<Pair< Pair<Integer , Integer> , String >> Huffman  =readFromFile(FilePath);
        String bits ="";
        String out = "";
        String AdditionalBits ="";
        int Zeroes = 0;
        int value =0;
        int bitsSize = 0;
        for(int i =0 ; i <seq.length(); i++){
            bits += seq.charAt(i);
            for(Pair< Pair<Integer , Integer> , String > it : Huffman) {
                if (it.getValue().equals(bits)) {
                    Zeroes = it.getKey().getKey();
                    bitsSize = it.getKey().getValue();
                    for (int j = 0; j < Zeroes; j++) {
                        out += "0";
                    }
                    bits = "";
                    AdditionalBits = seq.substring(i+1, i+bitsSize+1);
                    /*if (AdditionalBits.charAt(0) == '0')
                        out += "-";*/
                    value = getNumber(AdditionalBits);
                    out += Integer.toString(value);
                    i += bitsSize;
                }
            }
        }
        System.out.println(out);
    }

    private int getNumber(String AdditionalBits){
        double n = 0;
        int category = AdditionalBits.length();
        char SignBit = AdditionalBits.charAt(0) ;
        if(category == 1){
            if(SignBit == '1')
                return 1;
            else
                return -1;
        }
        else {
            if (SignBit == '1') {
                int decimal = Integer.parseInt(AdditionalBits.substring(1), 2);
                n = (Math.pow(2.0,Double.valueOf(category) -1.0)) + decimal;
            } else {
                int decimal = Integer.parseInt(AdditionalBits.substring(1), 2);
                n = (Math.pow(2.0,Double.valueOf(category)) -1.0) - decimal;
                n = -n;
            }
            return (int)n;
        }
    }


}
