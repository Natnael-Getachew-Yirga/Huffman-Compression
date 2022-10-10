
public class Node implements Comparable<Node>
//made the node implement comparable because We can’t create PriorityQueue of Objects that are non-comparable
{
    int frequency;
    char character;  
    Node leftNode,rightNode;
    public Node(int f, char c){
        this.frequency=f;
        this.character=c;
        this.leftNode=null;
        this.rightNode=null;
    }
    public Node(int f, char c, Node l, Node r)
    {
        this.frequency=f;
        this.character=c;
        this.leftNode=l;
        this.rightNode=r;
    }

    public Node(char c, Node l, Node r)
    {

        this.character=c;
        this.leftNode=l;
        this.rightNode=r;
    }
   // to compare frequency of node
    public int compareTo(Node node) {
        return frequency - node.frequency;
    }

    //toc check if the we are the leaf node
public boolean isleaf()
{
    if(this.leftNode==null && this.rightNode==null)
    return true;
    else return false;

}
}