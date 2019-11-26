public static void main(String args[]) {
    // basically read file and process commands and operations
    BufferedReader reader = null;
    try {
        reader = new BufferedReader(new FileReader(fileName));
        String line = reader.readLine();
        while (line != null) {
            parseLine(line);
            line = reader.readLine();
        }
    }
    String[] parts = input_line.split(" ");
    line = line.trim( );
    line = line.replaceAll(",", " , ");
    line = line.replaceAll("\\s+", " ");
    String[ ] tokens = line.split("\\s");
    token tokens[0];
    if (token != null){
        if (token.matches("decl|retr|call|add|...")) {
            Stmt stmt=StatementFactory.getStatement(token);
            stmt.genCode(tokens);
        } else {
            System.out.println(“Unknown stmt: “+token);
        }
    }

}