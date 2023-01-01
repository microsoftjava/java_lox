package java_lox;

public class Token {
    final Token_Type type;
    final String lexeme;
    final Object literal;
    final int line;

    Token(Token_Type type, String lexeme, Object literal, int line) {
        this.type = type;
        this.lexeme = lexeme;
        this.literal = literal;
        this.line = line;}
    
    public String toString() {
        return type + " " + lexeme + " " + literal;}
}
