package java_lox;

import java.util.List;
import java.util.ArrayList;

import static java_lox.Token_Type.*;

//gist this is for parsing
public class Parser {
    //gist this is for parsing errors
    private static class ParseError extends RuntimeException {}

    private final List<Token> tokens;
    private int current = 0;

    //gist this is the constructor for the parser
    Parser(List<Token> tokens) {
        this.tokens = tokens;
    }

    //gist this is for parsing
    List<Stmt> parse() {
        List<Stmt> statements = new ArrayList<>();
        while (!isAtEnd()) {
            statements.add(declaration());
        }
    
        return statements; 
    }

    //gist this is for expressions
    private Expr expression() {
        return assignment();
    }

    private Stmt declaration() {
        try {
            if (match(VAR)) return varDeclaration();
    
            return statement();
        } catch (ParseError error) {
            synchronize();
            return null;
        }
    }

    private Stmt statement() {
        if (match(IF)) return ifStatement();
        if (match(PRINT)) return printStatement();
        if (match(LEFT_BRACE)) return new Stmt.Block(block());
    
        return expressionStatement();
    }

    private Stmt ifStatement() {
        consume(LEFT_PAREN, "Expect '(' after 'if'.");
        Expr condition = expression();
        consume(RIGHT_PAREN, "Expect ')' after if condition."); 
    
        Stmt thenBranch = statement();
        Stmt elseBranch = null;
        if (match(ELSE)) {
            elseBranch = statement();
        }
    
        return new Stmt.If(condition, thenBranch, elseBranch);
    }

    private Stmt printStatement() {
        Expr value = expression();
        consume(SEMICOLON, "Expect ';' after value.");
        return new Stmt.Print(value);
    }

    private Stmt varDeclaration() {
        Token name = consume(IDENTIFIER, "Expect variable name.");
    
        Expr initializer = null;
        if (match(EQUAL)) {
            initializer = expression();
        }
    
        consume(SEMICOLON, "Expect ';' after variable declaration.");
        return new Stmt.Var(name, initializer);
    }

    private Stmt expressionStatement() {
        Expr expr = expression();
        consume(SEMICOLON, "Expect ';' after expression.");
        return new Stmt.Expression(expr);
    }

    private List<Stmt> block() {
        List<Stmt> statements = new ArrayList<>();
    
        while (!check(RIGHT_BRACE) && !isAtEnd()) {
            statements.add(declaration());
        }
    
        consume(RIGHT_BRACE, "Expect '}' after block.");
        return statements;
    }

    private Expr assignment() {
        Expr expr = or();
    
        if (match(EQUAL)) {
            Token equals = previous();
            Expr value = assignment();
    
            if (expr instanceof Expr.Variable) {
                Token name = ((Expr.Variable)expr).name;
                return new Expr.Assign(name, value);
            }
    
            error(equals, "Invalid assignment target."); 
        }
    
        return expr;
    }

    private Expr or() {
        Expr expr = and();
    
        while (match(OR)) {
            Token operator = previous();
            Expr right = and();
            expr = new Expr.Logical(expr, operator, right);
        }
    
        return expr;
    }

    private Expr and() {
        Expr expr = equality();
    
        while (match(AND)) {
            Token operator = previous();
            Expr right = equality();
            expr = new Expr.Logical(expr, operator, right);
        }
    
        return expr;
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

    //gist this is for unary expressions
    private Expr unary() {
        if (match(BANG, MINUS)) {
            Token operator = previous();
            Expr right = unary();
            return new Expr.Unary(operator, right);
        }

        return primary();
    }

    //gist this is for primary expressions
    private Expr primary() {
        if (match(FALSE)) return new Expr.Literal(false);
        if (match(TRUE)) return new Expr.Literal(true);
        if (match(NIL)) return new Expr.Literal(null);

        if (match(NUMBER, STRING)) {
            return new Expr.Literal(previous().literal);
        }

        if (match(IDENTIFIER)) {
            return new Expr.Variable(previous());
        }

        if (match(LEFT_PAREN)) {
            Expr expr = expression();
            consume(RIGHT_PAREN, "Expect ')' after expression.");
            return new Expr.Grouping(expr);
        }

        throw error(peek(), "Expect expression.");
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

    //gist this consumes the current token
    private Token consume(Token_Type type, String message) {
        if (check(type)) return advance();

        throw error(peek(), message);
    }

    //gist this compares the current token with the provided token
    private boolean check(Token_Type type) {
        if (isAtEnd()) return false;//gist this is where I found the source of the malding
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

    //gist this reports errors
    private ParseError error(Token token, String message) {
        Java_Lox.error(token, message);
        return new ParseError();
    }

    //gist this is to parse the rest of the code after encountering errors
    private void synchronize() {
        advance();

        while (!isAtEnd()) {
            if (previous().type == SEMICOLON) return;

            switch (peek().type) {
                case CLASS:
                case FUN:
                case VAR:
                case FOR:
                case IF:
                case WHILE:
                case PRINT:
                case RETURN: return;
            }

            advance();
        }
    }
}
