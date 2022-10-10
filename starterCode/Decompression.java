
import java.util.Scanner;

public class Decompression {

    manageCompressedFile compressedFile = new manageCompressedFile();
    CompressedContent data=compressedFile.readCompressedFile(input());
    public String bitString=data.bitString;
    public char[]symbols=data.symbols;
    public String[]codeForSymbols=data.codeForSymbols;
    private Node root;
//to accept the full path for compressed file.
    public String input() {
        System.out.println("-------------------------------------------");
        System.out.println("Enter Fill Name and Full path to be Decompressed");
        System.out.println("Eg. C:\\Users\\abebe\\Desktop\\compressedFile.cmp ");

        System.out.println("-------------------------------------------");
        
        Scanner sc=new Scanner(System.in);
        String InputFile = sc.nextLine();
        return InputFile;
    }

    public boolean isEmpty(){
        if(this.root == null )
            return true;
        else
            return false;
    }
 //crate huffmantree using the codeForSymbols
    public Node huffmantree()
     {
         int z=0;
         if(isEmpty()){
         root=new Node('\0',null,null);//if root is empty crate a new node with 0 value
         }
         Node temp=this.root;// crate a temp node
         for (int j=0;j<codeForSymbols.length;j++) //to get each string from codeForSymbols ex{"010","1011",...} j=0 is "010"
         {
             while (z < codeForSymbols[j].length()) //for j=0 which is 010 z counts the character at 0
             {
                 if (codeForSymbols[j].charAt(z) == '0') //if the character at z==0 we go to the left
                 {
                     if(temp.leftNode==null) {
                         temp.leftNode = new Node('\0', null, null);
                     }  temp=temp.leftNode;
                     z++;


                 }
                 else if (codeForSymbols[j].charAt(z) == '1') //if the character at z==1 we go right
                 {
                     if(temp.rightNode==null) {
                         temp.rightNode = new Node('\0', null, null);
                     }
                     temp=temp.rightNode;
                     z++;
                 }


             }
             temp.character=(symbols[j]);//if there is no more node we put the character at the end
             z=0;// make z=0 for the next iteration
             temp=root;// got back the root
         }

        return root;
     }


//travers  the tree to crate the decompressed word using the bitString
    public void decompression() {


        StringBuilder stringBuilder=new StringBuilder();//to put the sequence of characters that are decompressed
        Node temp=huffmantree();
        int i=0;
        while(i<bitString.length())
        {
            while (!temp.isleaf()&&!isEmpty())
            {
                if (bitString.charAt(i) == '0')//go left of the tree
                {
                    temp= temp.leftNode;//make it the root
                }
                else if (bitString.charAt(i) == '1')//go right of the tree until its leaf
                {
                    temp = temp.rightNode;
                }
                i++;
            }
            stringBuilder.append(temp.character);// if its leaf append the character to the string
            temp=root;//go back to the root
        }


        System.out.println("---------------------------------------");
        System.out.println( "Decompressed output");
        System.out.println(stringBuilder.toString());
        System.out.println("-----------------------------------------");
        fileManagement wr=new fileManagement();
        String filname="./decompressedText.txt";
        //write the decompressed fill to the new file
        wr.writeToAFile(filname, stringBuilder.toString());



    }




}
