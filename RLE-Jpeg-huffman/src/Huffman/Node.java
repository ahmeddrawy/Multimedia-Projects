package Huffman;

import javafx.util.Pair;

public class Node implements Comparable<Node> {
    double Probabiliy = 0.0;
    Node  left=  null ;
    Node  right = null ;
    String encoding ="";
    String curr = "";
    Pair<Integer , Integer > nodeCategory = null ;
    public Node(double _probability){
        Probabiliy = _probability;
    }
    public  Node(Node _left, Node _right , double _probability){
        Probabiliy = _probability;
        left = _left;
        right = _right;
    }
    Node(Pair<Integer , Integer> p , double _prob){
        nodeCategory = p;
        this.Probabiliy = _prob;
    }
    public int compareTo(Node rhs){
        return Double.compare(Probabiliy, rhs.Probabiliy);
    }
    public double getProbabiliy(){
        return Probabiliy;
    }
    public void updateEncoding(char state){
        encoding = encoding+ state;
    }
    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public void setCurr(String curr) {
        this.curr = curr;
    }
    public String getCurr() {
        return curr;
    }

    public void toLeaves(Node start , String state){
        if(start.left==null || start.right == null )
            return;
        start.left.setEncoding(state +'0');
        toLeaves(start.left, state+'0');
        start.right.setEncoding(state +'1');
        toLeaves(start.right, state+'1');
    }
}
