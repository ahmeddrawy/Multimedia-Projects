package Huffman;

import javafx.util.Pair;

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
    public  void build(int arr[] , int n ){
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
        
        for (Pair< Pair<Integer , Integer> , String > itt: v) { ///encoded string
            System.out.println(map.get(itt.getKey()).getEncoding() + " "+ itt.getValue() );
        }


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

}
