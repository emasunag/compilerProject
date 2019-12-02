import java.io.*;
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
//                System.out.println("_________LINE_________");
//                for (int i = 0; i < parts.length; i++) {
//                    System.out.println(parts[i]);
//                }
                bc.parse(parts);

            }
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // write all of memory at end
        ArrayList<byte> destination = new ArrayList<byte>();
        bc.compile(destination);

        // _________________Program still needs:__________________
        // - symbol table
        // - all of these below:
        //        program counter
        //        stack pointer
        //        runtime stack
        //        frame pointer stack pointer
        //        frame pointer stack

        // Using a symbol table:
            // utilizes key and data
            // for labels the key is the label string, the value is the position in byte code where the label is
            // for vars the key is the var name, the value is the offset from sp (# of things pushed onto sp before)

    }


}