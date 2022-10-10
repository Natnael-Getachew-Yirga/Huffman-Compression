
import java.util.*;


public class Huffman {

    //Hash table for finding the frequency of the characters in the text
    //using hash i can find the previously stored character and add the frequency which is the value(int)
    public Hashtable<Character, Integer> frequencyCount(char[] character) {

        Hashtable<Character, Integer> frequency = new Hashtable<>();
        for (int i = 0; i < character.length; i++) {
            if (frequency.containsKey(character[i]))//if the hash table already contains the character increment the frequency
                frequency.put(character[i], frequency.get(character[i]) + 1);
            else
                frequency.put(character[i], 1);//else put the character as key and count frequency one
        }

        return frequency;//returns the character and its frequency crating a frequency table

    }

    //huffman tree implementation
    public Node HuffmanTree(Hashtable<Character, Integer> fr) {
        //using priority queue to make the huffman tree
        PriorityQueue<Node> pq = new PriorityQueue<>();
        //crate non connected individual node using the character and frequency from the above method

        for (char c : fr.keySet()) {
            //adding the nodes in to to the priority queue
            pq.add(new Node(fr.get(c), c));

        }
        //the priority queue has nodes arranged based on frequency from the smallest to highest

        while (pq.size() > 1) {
            //retrieve and remove the head of the queue and put it in the left node and the right node
            Node leftNode = pq.poll();
            Node rightNode = pq.poll();
            //crate a root node that and make its left and right node connected to the retrieved nodes
            Node root = new Node(leftNode.frequency + rightNode.frequency, '\0', leftNode, rightNode);//add frequency of the two nodes put it root frequency
            pq.add(root);//put the root node back in the the priority q and loop again until there is one left
        }

        return pq.poll();//return the root of the huffman tree
    }

    //hashtable to hold the character and their code
    public Hashtable<Character, String> characterCode(Node temp)
    {
        Hashtable<Character,String> codeTable=new Hashtable<>();
        Traverse(temp,"",codeTable);
        return codeTable;
    }

    //traversing trough the tree and writing code for each character
    public void Traverse(Node temp, String s, Hashtable<Character, String> codeTable) {

        if(!temp.isleaf()){
            Traverse(temp.leftNode,s+'0',codeTable);//recursively traverse to the left and append 0
            Traverse(temp.rightNode,s+'1',codeTable);//recursively traverse to the left and append 1
        }
        else
        {
            codeTable.put(temp.character,s);//put char and codeword in hashtable
        }

    }

    // using that code table to generate a string of ones and zeros.
    public String getBitString(char[] cha,Hashtable<Character,String> codeTable){
        //using stringBuilder to crate a changeable characters sequence
        StringBuilder stringBuilder=new StringBuilder();
        for (char ch:cha)
        {
            stringBuilder.append(codeTable.get(ch));
        }


        return stringBuilder.toString();
    }

    //write to compressed file
    public void writetoCompressedFile(char[] text)
{
     Hashtable<Character, Integer> f=frequencyCount(text);
     Node p=HuffmanTree(f);
     Hashtable<Character,String> code=characterCode(p);

    String bitString=getBitString(text,characterCode(p));//writing the string bit
    //gating symbols from hash table
    char[] symbols = new char[code.size()];
    int i = 0;
    for (Character c : code.keySet()) {
        symbols[i++] = c;
    }
    //gating code symbols
    String[]codeForSymbols= code.values().toArray(new String[0]);

    String outputFile="./CompressedFile.cmp";
    manageCompressedFile compressedFile=new manageCompressedFile();
    compressedFile.writeACompressedFile(bitString,symbols,codeForSymbols,outputFile);

    //display
     display(text,codeForSymbols,symbols);
    System.out.println("Compressed File location : "+outputFile);

}


    //to display the text and code symbols to console
    public void display(char[] cha,String[] codeForSymbols,char[] symbols)
    {


       final Hashtable<Character, Integer> f=frequencyCount(cha);
        final Node p=HuffmanTree(f);
        Hashtable<Character,String>  characterCode=characterCode(p);

        System.out.println("-------------------------------------------");
        System.out.println("Input text:");
        for (int i=0;i<cha.length;i++)
            System.out.print(cha[i]);//print input
        System.out.println(" ");
        System.out.println("--------------------------------------------");
        System.out.println("Bit String generated :- "+getBitString(cha,characterCode));//bit string



        for (int i=0;i<codeForSymbols.length;i++) {
            System.out.print(symbols[i]+"===> ");
            System.out.print(codeForSymbols[i]);
            System.out.println(" ");
        }






}



}
