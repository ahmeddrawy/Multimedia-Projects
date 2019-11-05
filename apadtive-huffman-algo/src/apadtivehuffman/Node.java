package apadtivehuffman;

public class Node {
    Node left = null;
    Node right = null;
    Node Parent = null;
    int cnt = 0 ;
    int nodeNumber = 0 ;
    char symbol ;
    String representation ="";
    Node(Node _left , Node _right , Node _parent , int _cnt , int _nodeNumber){
        left = _left;
        right = _right;
        Parent = _parent;
        cnt = _cnt;
        nodeNumber =_nodeNumber;
    }
    Node(Node _left , Node _right , Node _parent , int _cnt , int _nodeNumber , char symbol){
        left = _left;
        right = _right;
        Parent = _parent;
        cnt = _cnt;
        nodeNumber =_nodeNumber;
        this.symbol = symbol;
    }
    Node(Node _left , Node _right , Node _parent , int _cnt , int _nodeNumber , char symbol, String representation){
        left = _left;
        right = _right;
        Parent = _parent;
        cnt = _cnt;
        nodeNumber =_nodeNumber;
        this.symbol = symbol;
        this.representation= representation;
    }
    Node(Node a){
        this.symbol = a.getSymbol();
        this.representation= a.getRepresentation();
        this.cnt = a.getCnt();
        this.Parent = a.getParent();
        this.left = a.getLeft();
        this.right = a.getRight();
        this.nodeNumber = a.getNodeNumber();

    }
    public String getRepresentation() {
        return representation;
    }

    public void setRepresentation(String representation) {
        this.representation = representation;
    }

    public void setSymbol(char symbol) {
        this.symbol = symbol;
    }

    public char getSymbol() {
        return symbol;
    }
    public void setCnt(int cnt) {
        this.cnt = cnt;
    }
    public int getCnt() {
        return cnt;
    }
    public void setNodeNumber(int nodeNumber) {
        this.nodeNumber = nodeNumber;
    }

    public int getNodeNumber() {
        return nodeNumber;
    }

    public void setLeft(Node left) {
        this.left = left;
    }

    public void setRight(Node right) {
        this.right = right;
    }

    public void setParent(Node parent) {
        Parent = parent;
    }
    public Node getLeft() {
        return left;
    }

    public Node getParent() {
        return Parent;
    }

    public Node getRight() {
        return right;
    }
}
