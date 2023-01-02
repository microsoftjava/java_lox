package java_lox;

import java.util.List;

import static java_lox.Token_Type.*;

//gist this is for parsing
public class Parser {
    private final List<Token> tokens;
    private int current = 0;

    //gist this is the constructor for the parser
    Parser(List<Token> tokens) {
        this.tokens = tokens;
    }
}
