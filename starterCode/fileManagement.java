
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Scanner;

public class fileManagement {
    String readOneFile(String fileName){
        String data="";
        try {
            File theFile = new File(fileName);
            if(theFile.exists() && theFile.isFile()){
                Scanner reader = new Scanner(theFile);            
                while(reader.hasNextLine()){
                    data = data +"\n"+reader.nextLine();
                }
                reader.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }
    public void writeToAFile(String fileName, String data){
        this.writeToAFile(fileName,data,false);
    }
    public void writeToAFile(String fileName, String data,boolean append){
        try {
            FileWriter writerToFile = new FileWriter(fileName,append);
            PrintWriter fileOut = new PrintWriter(writerToFile);
            fileOut.println(data);             
            writerToFile.close();
            fileOut.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
