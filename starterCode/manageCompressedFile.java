import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class manageCompressedFile {

    public int numberOfLines = 6;

    public List<String> stringToByte(String str){
        List<String> byteStrings = new ArrayList<String>();

        for(int i= str.length(); i > -1 ; i=i-8){
            if((i-8)>-1){
                byteStrings.add(0,str.substring(i-8,i).strip());
            }
            else{
                byteStrings.add(0,str.substring(0,i).strip());
            }
        }
        return byteStrings;
    }

    public char binaryStringToChar(String byteString){

            int charCode = Integer.parseInt(byteString,2)+14;
            char charValue = (char)charCode;
        return charValue;
    }

    public String binaryArrayToString(List<String> byteStrings){
        String outputString="";
        int temp=0;
        for(String aByte : byteStrings){
            char c = this.binaryStringToChar(aByte);
            outputString=outputString+c;
            temp++;
        }
        return outputString;
    }

    public String charToBinaryString(char charVal){
        int intCode = charVal-14;
        String binaryString= Integer.toBinaryString(intCode);
        return binaryString;
    }

    public String[] stringtoBinaryArray(char[] charArray){
        String[] binaryArray=new String[charArray.length];
        for(int i=0; i<charArray.length;i++){
            binaryArray[i] = this.charToBinaryString(charArray[i]);
        }
        return binaryArray;
    }

    public String fixBinaryStringLength(String str,int len){
        String fixedString=str;
        if(str.length() < len){
            int remainingZeros = len-str.length();
            fixedString="0".repeat(remainingZeros)+str;
        }
        return fixedString;
    }

    public String[] fixBitLength(String[] stringArray,int len){
        String[] longBinaryArray=new String[stringArray.length];
        for(int i=0; i<stringArray.length;i++){
            longBinaryArray[i]=this.fixBinaryStringLength(stringArray[i],len);
         }
         return longBinaryArray;
    }

    public String[] fixBitLength(String[] stringArray,int[] len){
        String[] longBinaryArray=new String[stringArray.length];
        for(int i=0; i<stringArray.length;i++){
            longBinaryArray[i]=this.fixBinaryStringLength(stringArray[i],len[i]);
         }
         return longBinaryArray;
    }

    public Boolean writeACompressedFile(String bitString,char[] symbols,String[] codeForSymbols,String outputFile){
        List<String> byteStrings = this.stringToByte(bitString);
        String outputString=this.binaryArrayToString(byteStrings);

        List<String> codeArray= Arrays.asList(codeForSymbols);

        String codeArrayLength = "";
        for(String str : codeArray){
            char tempChar = (char)str.length();
            codeArrayLength+=tempChar;
        }

        String codeString=this.binaryArrayToString(codeArray);
        char[] temp=codeString.toCharArray();
        int cout=0;
        for(char c:temp){
            cout++;
        }

        String SymbolString = String.valueOf(symbols);

        try{
            fileManagement fileWriter= new fileManagement();
            fileWriter.writeToAFile(outputFile,"!@#"+bitString.length()+"!@#"+ //line 1
                    codeArrayLength+"!@#"+ //line 2
                    SymbolString+"!@#"+ //line 3
                    codeString+"!@#"+ //line 4
                    outputString+"!@#"); //line 5

            return true;
        }
        catch(Exception e){
            System.out.println(e);
            return false;
        }

    }

    public CompressedContent readCompressedFile(String InputFile){
        fileManagement fileReader = new fileManagement();
        String fileData = fileReader.readOneFile(InputFile);
        String [] inputArrayArray=fileData.split("!@#");

        if(inputArrayArray.length == this.numberOfLines){
            char[] codesLength= inputArrayArray[2].toCharArray();
            char[] symbols= inputArrayArray[3].toCharArray();
            char[] charCodes = inputArrayArray[4].toCharArray();
            char[] charData = inputArrayArray[5].toCharArray();

            int[] codesArrayLength= new int[codesLength.length];
            for(int k=0; k< codesLength.length;k++){
                codesArrayLength[k]=codesLength[k];
            }

            String[] codes=this.stringtoBinaryArray(charCodes);
            String[] codeForSymbols = this.fixBitLength(codes,codesArrayLength);

            String[] bitStringArray=this.stringtoBinaryArray(charData);
            bitStringArray = this.fixBitLength(bitStringArray,8);
            String temp = "";
            for (String str : bitStringArray)temp +=str;
            int bigningIndex = temp.length() - Integer.valueOf(inputArrayArray[1]);
            String bitString = temp.substring(bigningIndex);

            CompressedContent compContent = new CompressedContent(bitString, symbols,codeForSymbols);
            return compContent;
        }
        else{
            System.out.println("file not properly written");
            return null;
        }
    }
}
