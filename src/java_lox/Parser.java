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

    //gist this is for expressions
    private Expr expression() {
        return equality();
    }

    //gist this evaluates expressions that contain != or ==
    private Expr equality() {
        Expr expr = comparison();

        while (match(BANG_EQUAL, EQUAL_EQUAL)) {
            Token operator = previous();
            Expr right = comparison();
            expr = new Expr.Binary(expr, operator, right);
        }

        return expr;
    }

    //gist this compares the current token with the provided tokens
    private boolean match(Token_Type... types) {
        for (Token_Type type : types) {
            if (check(type)) {
                advance();
                return true;
            }
        }
        
        return false;
    }
}
