import java.io.*;
import java.util.*;
public class Compiler{
    public static void main(String[] args) throws Exception  {
        // basically read file and process commands and operations

        // need to know
        // how symbol table interacts with everything
        // how to write the files
        bytecode bc = new bytecode();


        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(args[0]));

            String st;
            while ((st = br.readLine()) != null) {
                String[] parts = st.split(" ");
                System.out.println("_________LINE_________");
                for (int i = 0; i < parts.length; i++) {
                    System.out.println(parts[i]);
                }
                bc.parse(parts);

            }
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // write all of memory at end
        ArrayList<Integer> destination = new ArrayList<Integer>();
        destination = bc.compile(destination);
//
//        FileOutputStream fos;
//        BufferedOutputStream bos;
//        fos = new FileOutputStream("/Users/daryldabreo/Documents/compilerProject/OutputTest/Hewlp.bin");
//        bos = new BufferedOutputStream(fos);
//        byte[] b_array = new byte[destination.size()];
//        byte[] b_array = new byte[3];
//        b_array[0] = (byte) 146;
//        b_array[1] = (byte) 20;
//        b_array[2] = (byte) 123;
        DataOutputStream os = new DataOutputStream(new FileOutputStream("/Users/daryldabreo/Documents/compilerProject/OutputTest/Hewlp.bin"));
         for(int i = 0; i < destination.size(); i++)
        {
            os.writeByte(destination.get(i));
          System.out.println(destination.get(i));
        }
        os.close();

//        try{
//            FileOutputStream outputStream = new FileOutputStream("test.smp");
//            outputStream.write(destination);
//            outputStream.close();
//        }
//        catch (IOException e) {
//            e.printStackTrace();
//        }

        // Using a symbol table:
            // utilizes key and data
            // for labels the key is the label string, the value is the position in byte code where the label is
            // for vars the key is the var name, the value is the offset from sp (# of things pushed onto sp before)

    }


}