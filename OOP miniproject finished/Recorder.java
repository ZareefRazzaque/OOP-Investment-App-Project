
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;

//this class is for the recording of stock data to a csv file
public class Recorder {    
    String filename;
    BufferedWriter writer;
    File file;

    /////////////////////////////////////////////////////////////////////////////
    //constructors

    public Recorder(String name) throws IOException {
        this.filename = name; 
        this.writer = new BufferedWriter(new FileWriter((filename+".CSV"),true));
        StopWriting();
        file = new File(filename+".CSV");
    }


    /////////////////////////////////////////////////////////////////////////////
    //methods
    public void saveValue(int value) throws IOException{
        this.writer= new BufferedWriter(new FileWriter((file),true));
        this.writer.write(value+"\n");
        StopWriting();
        file = new File(filename+".CSV");

    }


    public void saveValue(String string) throws IOException{
        this.writer= new BufferedWriter(new FileWriter((file),true));
        this.writer.write(string+"\n");
        StopWriting();
        file = new File(filename+".CSV");

    }

    public void saveIntArray(ArrayList<Integer> array) throws IOException{
        this.writer= new BufferedWriter(new FileWriter((file),true));
        this.writer.write(array.get(0).toString());

        for (int x =1; x< array.size();x++){ 
            
            this.writer.write(",");            
            this.writer.write(array.get(x).toString());

        }
        StopWriting();
        
    
    }

    public void StopWriting() throws IOException{
        this.writer.close();
    }


    public String readLastLine() throws IOException{
        RandomAccessFile reader = new RandomAccessFile(file, "r");
        
        long position= 0;
        boolean found = false;

        for ( long i = file.length()-2; i >= 0; i--){
            
            
            reader.seek(i);
            if ( ((char) reader.read()) == '\n'){
                position = i;
                found = true;
                break;
            }

        }
    
        if (found){
            reader.seek(position+1);
        }


        String value = reader.readLine();
        
        reader.close();
        
        return value;
    }

    public ArrayList<String> returnAllData() throws IOException{
        ArrayList<String> data = new ArrayList<String>();
        BufferedReader reader = new BufferedReader(new FileReader(file));
        
        for (int i = 0; i< file.length();i++){
            data.add(reader.readLine());

        }
        reader.close();

        System.out.println(data.toString());
        return data;
        
    }


    public String readline(int lineNumber) throws IOException{
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String returningLine= "";

        for (int i = 0; i < lineNumber; i++){
            returningLine = reader.readLine();
        }

        reader.close();
        return returningLine;
        
        
    }

    public void clearFile() throws IOException{
        this.writer = new BufferedWriter(new FileWriter(filename+".CSV"));
    }
}
