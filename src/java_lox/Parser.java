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

    //gist this evaluates expressions that contain >, >=, < or <=
    private Expr comparison() {
        Expr expr = term();

        while (match(GREATER, GREATER_EQUAL, LESS, LESS_EQUAL)) {
            Token operator = previous();
            Expr right = term();
            expr = new Expr.Binary(expr, operator, right);
        }

        return expr;
    }

    //gist this is for addition and subtraction
    private Expr term() {
        Expr expr = factor();

        while (match(MINUS, PLUS)) {
            Token operator = previous();
            Expr right = factor();
            expr = new Expr.Binary(expr, operator, right);
        }

        return expr;
    }

    //gist this is for multiplication and division
    private Expr factor() {
        Expr expr = unary();

        while (match(SLASH, STAR)) {
            Token operator = previous();
            Expr right = unary();
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

    //gist this compares the current token with the provided token
    private boolean check(Token_Type type) {
        if (!isAtEnd()) current++;
        return peek().type == type;
    }

    //gist this consumes the current token and returns it, incrementing current by 1
    private Token advance() {
        if (!isAtEnd()) current++;
        return previous();
    }

    //gist this determines if the end of the file has been reached or not
    private boolean isAtEnd() {
        return peek().type == EOF;
    }

    //gist this gets the current token
    private Token peek() {
        return tokens.get(current);
    }

    //gist this gets the previous token
    private Token previous() {
        return tokens.get(current - 1);
    }
}
