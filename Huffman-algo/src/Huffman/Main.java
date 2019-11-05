package Huffman;

import javafx.util.Pair;

import java.util.*;

public class Main {

    public static void main(String[] args) {
	// write your code here
        String s;

        Scanner sc = new Scanner(System.in);
        s = sc.nextLine();
        HashMap<Character , Integer> frq= new HashMap<Character, Integer>();
        for(int i  = 0 ; i < s.length(); ++i){
//            frq[s.charAt(i)]++;
            if(frq.containsKey(s.charAt(i))) {
                int old = frq.get(s.charAt(i));
                frq.put(s.charAt(i), old + 1);
            }
            else {
                frq.put(s.charAt(i) ,1);
            }
        }
        PriorityQueue<Node> pq = new PriorityQueue<Node>();
        PriorityQueue<Node> pq2 = new PriorityQueue<Node>();
        Iterator it = frq.entrySet().iterator();
//        Vector<Node > tst = new Vector<Node>();
        while(it.hasNext()) {
            Map.Entry mp = (Map.Entry)it.next();
            Node n= new Node(1.0*(Integer)mp.getValue()/(Integer)s.length());
            n.setCurr(mp.getKey().toString());
            pq2.add(n);
            pq.add(n);
        }
//        tst.sort();

        while(pq.size()>2){
            Node n1 = pq.peek();
            pq.remove();
            Node n2 = pq.peek();
            pq.remove();
            Node n3  = new Node(n1, n2 , n1.getProbabiliy() + n2.getProbabiliy());///small left
            n3.setCurr(n1.getCurr() + n2.getCurr());
            pq.add(n3);
        }
        Node n1 = pq.peek();
        pq.remove();
        Node n2 = pq.peek();
        pq.remove();
        n1.setEncoding("0"); ///small 0 ,big 1
        n2.setEncoding("1");
       n1.toLeaves(n1,n1.getEncoding());
       n2.toLeaves(n2,n2.getEncoding());
       while(!pq2.isEmpty()){
           Node tmp = pq2.peek();
           pq2.remove();
           System.out.println(tmp.encoding + " " + tmp.getCurr());
       }
    }
}
