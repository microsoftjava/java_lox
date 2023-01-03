package java_lox;

public class Token {
    final Token_Type type;
    final String lexeme;
    final Object literal;
    final int line;

    //gist this is the constructor for Token instances
    Token(Token_Type type, String lexeme, Object literal, int line) {
        this.type = type;
        this.lexeme = lexeme;
        this.literal = literal;
        this.line = line;
    }
    
    //gist this is for printing token information
    public String toString()
    {return type + " " + lexeme + " " + literal;}
}
