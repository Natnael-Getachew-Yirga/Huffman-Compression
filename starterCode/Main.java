import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

      inputFile();

    }

    //method for input of the text file to be compressed and return character array of input
    public static void inputFile()
    {

        System.out.println("-------------------------------------------");
        System.out.println("Enter 1 to Compress File");
        System.out.println("Enter 2 to Decompress File");
        System.out.println("-------------------------------------------");


        Scanner sc = new Scanner(System.in);
        int input1= Integer.parseInt(sc.nextLine());

        if(input1==1) {
            System.out.println("-------------------------------------------");
            System.out.println("Enter Fill Name and Full path to be Compressed");
            System.out.println("Eg. C:\\Users\\abebe\\Desktop\\myfile.txt. ");

            System.out.println("-------------------------------------------");
            
            String input = sc.nextLine();
            fileManagement fileReader = new fileManagement();
            String fileData = (fileReader.readOneFile(input)).trim();
            char[] character = fileData.toCharArray();//converting the input text string to character array
            Huffman h=new Huffman();
            h.writetoCompressedFile(character);
        }
        else if (input1==2)
        {
           Decompression d=new Decompression();
            d.decompression();
        }
        else
            {
                System.out.println("Wrong Input");
            }

    }

}
