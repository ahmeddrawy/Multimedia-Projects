package apadtivehuffman;

import javax.management.StandardEmitterMBean;
import java.util.Vector;

public class Tree {
    Node root = null;
    Node NTE = null ;
    Node oldNTE = null ;
    int indx=  100;
    Tree(){
        root = new Node(null , null ,null, 0,indx);
        NTE = root;
    }
    void split(char c){
        oldNTE =NTE;
        Node l = new Node(null, null,NTE, 0,indx-2,Character.MIN_VALUE,NTE.getRepresentation()+'0');
        Node r = new Node(null, null,NTE, 1,indx-1,c,NTE.getRepresentation()+'1');
        NTE.setRight(r);
        NTE.setLeft(l);
        NTE.setCnt(NTE.getCnt()+ 1);    /// check
        NTE = l;
        indx-=2;
    }

    void swap(Node a , Node b){ /// todo
        Node tmp = new Node(a);
        a.setCnt(b.getCnt());
        a.setLeft(b.getLeft());
        a.setRight(b.getRight());
        a.setSymbol(b.getSymbol());

        /// we swap left and right , cnt and symbol 'A'for example
        b.setCnt(tmp.getCnt());
        b.setLeft(tmp.getLeft());
        b.setRight(tmp.getRight());
        b.setSymbol(tmp.getSymbol());
    }
    boolean inMytree(Node a,Node start){
        if(start== null)return false;
        return a.getNodeNumber()== start.getNodeNumber() || inMytree(a,start.right) ||inMytree(a,start.left);
    }
    Node getNodeToBeSwapped(Node a , Node start) {/// todo recheck
        if(start ==null)
            return null;
        /// check larger number and larger weight
        int w= start.getCnt(); /// weight
        int n= start.getNodeNumber();
        if(w >= a.getCnt() && n>a.getNodeNumber() &&!inMytree(a,start) ){
            return start;
        }
        Node r = getNodeToBeSwapped(a,start.right);
        Node l = getNodeToBeSwapped(a,start.left);
        if( r != null){
            return r;
        }
        else
            return l;
//        else  ///redunant to check l if null return it
//            return null;
    }
    void HandleSwap(Node a){
        Node b = getNodeToBeSwapped(a,root);
        if(b !=null){
            swap(a,b);
            updateRepresentation(root, null,"");
            a.setCnt(a.getCnt()+1);
            HandleNode(a);
        }else { /// increment
            a.setCnt(a.getCnt() + 1); /// increment counter
            HandleNode(a);
        }
    }
    void updateRepresentation(Node start,Node P , String s ){
        if(start ==null)
            return ;
        if(start!=root) {
            start.setRepresentation(s);
            start.setParent(P);
//            start.right.setRepresentation(start.getParent().getRepresentation() + c);
        }
        updateRepresentation(start.right,start,start.getRepresentation()+'1');
        updateRepresentation(start.left, start,start.getRepresentation()+'0');
    }
    void HandleNode(Node in){
        if(in == root) {
            return;
        }
        Node p = in.getParent();
        HandleSwap(p);
        HandleNode(p);
    }
    boolean symbolExist(char symbol, Node start){ /// todo
        if(start == null)
            return false;
        return (start.getSymbol() ==symbol) || symbolExist(symbol ,start.right)|| symbolExist(symbol,start.left);
    }

    Node getSymbolNode(char symbol, Node start){/// todo
        if(start == null)
            return null;
        if(start.getSymbol() == symbol){
            return start;
        }
        Node l = getSymbolNode(symbol,start.left);
        Node r = getSymbolNode(symbol,start.right);
        if(r!=null)
            return r;
        else return l;
//        return getSymbolNode(symbol,start.left)
    }
    void inputSymbol(char symbol){
        if(symbolExist(symbol, root)){
            HandleSwap(getSymbolNode(symbol,root));
        }
        else {
            split(symbol);
            HandleNode(getSymbolNode(symbol,root).getParent());
        }
    }
    public void ProcessString(String s ){
        for (int i = 0 ; i < s.length(); ++i) {
            if (symbolExist(s.charAt(i) , root)){
                Node a=  getSymbolNode(s.charAt(i),root);
                System.out.println(a.getRepresentation());
            }
            else  {

                System.out.println(NTE.getRepresentation());
                System.out.println( Integer.toBinaryString(  (int)  s.charAt(i) -'a')); /// todo

            }
            inputSymbol(s.charAt(i));
        }
    }


}
