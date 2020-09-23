import java.io.*;
import java.util.*;
class bytecode {
    public static final int HALT = 0;
    public static final int JMP = 36;
    public static final int JMPC = 40;
    public static final int PUSHI = 70;
    public static final int PUSHVI = 74;
    public static final int POPM = 76;
    public static final int POPA = 77;
    public static final int POPV = 80;
    public static final int PEEKI = 86;
    public static final int POKEI = 90;
    public static final int SWP = 94;
    public static final int ADD = 100;
    public static final int SUB = 104;
    public static final int MUL = 108;
    public static final int DIV = 112;
    public static final int CMPE = 132;
    public static final int CMPLT = 136;
    public static final int CMPGT = 140;
    public static final int PRINTI = 146;
    public static final int CALL = 44;
    public static final int RET = 48;

    public static final int INT = 1;


    int sc = 0; // current line being compiled
    int pc = -1; // program counter
    int fo = -1; // number of local variables in a function

    ArrayList<String> source = new ArrayList<String>(); // lines
    ArrayList<Integer> mem = new ArrayList<Integer>(); // opcodes
    Map<String, Integer> symbol_table = new HashMap<>();
    Map<String, Integer> unknown_labels;


    ArrayList<Integer> compile(ArrayList<Integer> des) {
        des = mem;
//                for(int i = 0; i < des.size(); i++)
//        {
////            b_array[i] = destination.get(i);
//           System.out.println(des.get(i));
//        }
        return des;
    }

    void parse(String[] line) {
        sc++;
        String operation = line[0];
        if (line.length == 3) {
            // decl, subr, peek, poke
            switch (operation) {
                case "decl":
                    decl(line[1], line[2]);
                    break;
                case "subr":
                    subr(line[1], line[2]);
                    break;
                case "peek":
                    peek(line[1], line[2]);
                    break;
                case "poke":
                    poke(line[1], line[2]);
                    break;
            }
        } else if (line.length == 2) {
            // lab, printi, jmp, jmpc, pushi, pushvi, popm, popv
            switch (operation) {
                case "lab":
                    lab(line[1]);
                    break;
                case "printi":
                    printi(line[1]);
                    break;
                case "printv":
                    printv(line[1]);
                    break;
                case "jmp":
                    jmp(line[1]);
                    break;
                case "jmpc":
                    jmpc(line[1]);
                    break;
                case "pushi":
                    int value = Integer.parseInt(line[1]);
                    pushi(value);
                    break;
                case "pushvi":
                case "pushv":
                    pushvi(line[1]);
                    break;
                case "popm":
                    int value2 = Integer.parseInt(line[1]);
                    popm(value2);
                    break;
                case "popv":
                    popv(line[1]);
                    break;
            }
        } else if (line.length == 1) {
            // cmpe, cmplt, cmpgt, swp, add, sub, mul, div
            switch (operation) {
                case "ret":
                    ret();
                    break;
                case "cmpe":
                    cmpe();
                    break;
                case "cmplt":
                    cmplt();
                    break;
                case "cmpgt":
                    cmpgt();
                    break;
                case "swp":
                    swp();
                    break;
                case "add":
                    add();
                    break;
                case "sub":
                    sub();
                    break;
                case "mul":
                    mul();
                    break;
                case "div":
                    div();
                    break;
            }
        }
        symbol_table.forEach((key,value) -> System.out.println(key+ ":" + value));
    }

    public int decl(String var, String type) {
        String var_name = var;
//        System.out.println(var_name);
//        int value = Integer.parseInt(type);
//        ++fo;
        symbol_table.put(var_name, ++fo);
        pushi(0);
        // the key will be the concatenation of the function label of the function being compiled
        // and var
        // the value will be an object containing the stack offset within the stack frame of the
        // function that will hold the variable and the type.
        return 0;
    }

    public int lab(String label) {
        String var_name = label;
        int size = mem.size();
        symbol_table.put(var_name, size);
        return 0;
        // the key will be the label
        // the value will be an object containing the stack offset of the functions stack
    }

    public int subr(String count, String flabel) {
        pushi(16);
        pushi(17);
        pushi(1);
        mem.add(CALL);
        mem.add(HALT);
        pc += 1;
        return 0;

        // cnt is the number of arguments taken in
        // Create an entry in the symbol table (see above). The entry will be of the form <key,
        // value>, where the key will be the flabel and the value will be the offset within the program code
        // generated that the label appears and the value of cnt. Note that variables and flabels may have the
        // same string since the variable is always part of a function, and the function name is part of the
        // variable’s key.
    }

    public int ret() {
        pushi(0);
        mem.add(POPA);
        mem.add(RET);
        pc+=2;
        // Pop all local variables off of the stack, pop the return value off of the stack
        // and into the PC (program counter). The next statement to be executed will be the one indicated by the
        // updated PC.
        return 0;
    }

    public int printi(String literal) {
        // increment byte code offset in memory by the length of literal in bytes
        // print the literal
        int value = Integer.parseInt(literal);
        pushi(value);
        mem.add(PRINTI);
//        pc += 1;
        return 0;
    }

    public int printv(String var) {
        int value = symbol_table.get(var);
//        pushi(value);
        pushvi(var);
//        mem.add(PUSHVI);
        mem.add(PRINTI);
        pc+=1;

        return 0;
        // pushi var
        // pushv type
        // print value of var
    }

    public int pushvi(String var) {
        int value = symbol_table.get(var);
        pushi(value);
        mem.add(PUSHVI);
        pc+=1;
        return 0;
    }

    //    public int jmp(String label){
//        // jump to statement immediately following the label
//        // pushi lable
//        // bc.jump

//    }

    public int jmp(String label) {

        if (symbol_table.get(label) == null) {
            pushi(0);
        } else {
            pushi(symbol_table.get(label));

        }
        mem.add(JMP);
        pc += 1;
        return 0;
    }

    public int jmpc(String label){

        if(symbol_table.get(label) == null)
        {
            pushi(0);
        }
        else
        {
            pushi(symbol_table.get(label));

        }
        mem.add(JMPC);
        pc +=1;
        return 0;
        // perform jump if top of stack has a 1
    }
    public int cmpe(){
        // compare two items on top of the stack (pop that shi)
        // push a 1 if t-1 == t , pop a 0 if not
        mem.add(CMPE);
        pc+=1;
        return 0;
    }
    public int cmplt(){
        // compare two items on top of the stack (pop that shi)
        // push a 1 if t-1 < t , pop a 0 if not
        mem.add(CMPLT);
        pc+=1;
        return 0;
    }
    public int cmpgt(){
        // compare two items on top of the stack (pop that shi)
        // push a 1 if t-1 > t , pop a 0 if not
        mem.add(CMPGT);
        pc+=1;
        return 0;
    }
    public int pushi(int val) {

        byte[] bytes;
        mem.add(PUSHI);
        mem.add((val));
        mem.add((val >> 8));
        mem.add((val >> 16));
        mem.add((val >> 24));
        pc += 5;

        return 0;
        // push the val onto stack
    }

        public int popm(int val){
        pushi(val);
        mem.add(POPM);
        pc += 1;
//     pop val # items off the stack
//     bc.pushi val
//     bc.popm

            return 0;
    }
//    public int popa(int val){
//        // pop val # items off the stack
//        // bc.pushi val
//        // bc.popm
//    }
    public int popv(String var) {
        int value = symbol_table.get(var);
        pushi(value);
        mem.add(POPV);
        pc+=1;

        return 0;

    }


    public int peek(String var,String val){
        // var = stack[sp+val]
        int value = symbol_table.get(var);
        pushi(value);
        int value2 = Integer.parseInt(val);
        pushi(value2);
        mem.add(PEEKI);
        pc+=1;
        return 0;

        // types of both ^ must be the same
    }
    public int poke(String val,String var){
        int value = symbol_table.get(var);
        pushi(value);
        int value2 = Integer.parseInt(val);
        pushi(value2);
        mem.add(POKEI);
        pc+=1;
        return 0;
        // stacks[sp+val] = var
        // types must be the same
    }

    public int swp(){
        mem.add(SWP);
        pc +=1;
        // swap the top two stack elements
        return 0;
    }
    public int add(){
        mem.add(ADD);
        pc+=1;
        return 0;
        // add the top two stack elements
        // *t = *(t-1) + *t
    }
    public int sub(){
        mem.add(SUB);
        pc+=1;
        return 0;
        // subtract the top two stack elements
        // *t = *(t-1) - *t
    }
    public int mul(){
        mem.add(MUL);
        pc+=1;
        return 0;
        // multiply the top two stack elements
        // *t = *(t-1) x *t
    }
    public int div() {
        mem.add(DIV);
        pc += 1;
        return 0;
    }
        // divide the top two stack elements
        // *t = *(t-1) / *t

}
