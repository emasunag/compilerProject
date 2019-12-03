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

    public static final int INT = 1;

    void parse(String [] line);
    int decl(String, int);
    int lab(String);
    int subr(int, String);
    int printi(int);
    int jmp(String);
    int jmpc(String);
    int cmpe();
    int cmplt();
    int cmpgt();
    int pushi(int);
    int popm(int);
    int popa(int);
    int popv(String);
    int peek(String, int);
    int poke(int, String);
    int swp();
    int add();
    int sub();
    int mul();
    int div();


    int sc = 0; // current line being compiled
    int pc = -1; // program counter
    int fo = -1; // number of local variables in a function

    ArrayList<String> source = new ArrayList<String>(); // lines
    ArrayList<Integer> mem = new ArrayList<Integer>(); // opcodes
    Map<string, value> symbol_table;
    Map<string, value> unknown_labels;


    void parse(String [] line){
        sc++;
        String operation = line[0];
        if (line.size() == 3){
            // decl, subr, peek, poke
            Switch(operation){
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
        }
        else if (line.size() == 2){
            // lab, printi, jmp, jmpc, pushi, pushvi, popm, popv
            Switch(operation){
                case "lab":
                    lab(line[1]);
                    break;
                case "printi":
                    printi(line[1]);
                    break;
                case "jmp":
                    jmp(line[1]);
                    break;
                case "jmpc":
                    jmpc(line[1]);
                    break;
                case "pushi":
                    pushi(line[1]);
                    break;
                case "pushvi":
                    pushvi(line[1]);
                    break;
                case "popm":
                    popm(line[1]);
                    break;
                case "popv":
                    popv(line[1]);
                    break;
            }
        }
        else if (line.size() == 1){
            // cmpe, cmplt, cmpgt, swp, add, sub, mul, div
            Switch(operation){
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
    }

    public int decl(String var, int type) {
        String var_name = 'main' + '_' + var;
        int[] value = {++fo, type};
        Symbol_table.put(var_name, value);
        pushi(0);
        pc+=1;
        // the key will be the concatenation of the function label of the function being compiled
        // and var
        // the value will be an object containing the stack offset within the stack frame of the
        // function that will hold the variable and the type.
    }
    public int lab(String label) {
        String var_name = 'main' + '_' + label;
        int[] value = {++sc, type};
        Symbol_table.put(var_name,value);
        pc+=1;
        // the key will be the label
        // the value will be an object containing the stack offset of the functions stack
    }
    public int subr(int count, String flabel){
        pushi(16);
        pushi(17);
        pushi(1);
        pc+=1;

        // cnt is the number of arguments taken in
        // Create an entry in the symbol table (see above). The entry will be of the form <key,
        // value>, where the key will be the flabel and the value will be the offset within the program code
        // generated that the label appears and the value of cnt. Note that variables and flabels may have the
        // same string since the variable is always part of a function, and the function name is part of the
        // variable’s key.
    }
    public int ret(){
        // Pop all local variables off of the stack, pop the return value off of the stack
        // and into the PC (program counter). The next statement to be executed will be the one indicated by the
        // updated PC.
    }
    public int printi(int literal){
        // increment byte code offset in memory by the length of literal in bytes
        // print the literal
        pushi(literal);
        mem.add(PRINTI);
        pc+=1;
    }
//    public int printv(var){
//        // pushi var
//        // pushv type
//        // print value of var
//    }
    public int jmp(String label){
        // jump to statement immediately following the label
        // pushi lable
        // bc.jump
    }
    public int jmpc(String label){
        // perform jump if top of stack has a 1
    }
    public int cmpe(){
        // compare two items on top of the stack (pop that shi)
        // push a 1 if t-1 == t , pop a 0 if not
        mem.add(CMPE);
        pc+=1;
    }
    public int cmplt(){
        // compare two items on top of the stack (pop that shi)
        // push a 1 if t-1 < t , pop a 0 if not
        mem.add(CMPLT);
        pc+=1;
    }
    public int cmpgt(){
        // compare two items on top of the stack (pop that shi)
        // push a 1 if t-1 > t , pop a 0 if not
        mem.add(CMPGT);
        pc+=1;
    }
    public int pushi(int val){
        Integer[] bytes = Arrays.copyof(val, val.length);
        mem.add(PUSHI);
        mem.add(bytes[0]);
        mem.add(bytes[1]);
        mem.add(bytes[2]);
        mem.add(bytes[3]);
        pc += 5;

        // push the val onto stack
    }
    public int popm(int val){
        pushi(val);
        mem.add(POPM);
        // pop val # items off the stack
        // bc.pushi val
        // bc.popm
    }
//    public int popa(int val){
//        // pop val # items off the stack
//        // bc.pushi val
//        // bc.popm
//    }
    public int popv(String var){
        // pop the current top of stack and put it into var
        // pushi (var)
        // bc.popv
    }
    public int peek(String var,int val){
        // var = stack[sp+val]
        // types of both ^ must be the same
    }
    public int poke(int val,String var){
        // stacks[sp+val] = var
        // types must be the same
    }
    public int swp(){
        mem.add(SWP);
        pc +=1;
        // swap the top two stack elements
    }
    public int add(){
        mem.add(ADD);
        pc+=1;
        // add the top two stack elements
        // *t = *(t-1) + *t
    }
    public int sub(){
        mem.add(SUB);
        pc+=1;
        // subtract the top two stack elements
        // *t = *(t-1) - *t
    }
    public int mul(){
        mem.add(MUL);
        pc+=1;
        // multiply the top two stack elements
        // *t = *(t-1) x *t
    }
    public int div(){
        mem.add(DIV);
        pc+=1;
        // divide the top two stack elements
        // *t = *(t-1) / *t
    }

}