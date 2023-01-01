package java_lox;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java_lox.Token_Type.*;

public class Scanner {
    private final String source; //* this is the code that will be scanned
    private final List<Token> tokens = new ArrayList<>(); //* this is for the tokens generated by the scanner
    private int start = 0; //desc this is the first character in the lexeme being scanned
    private int current = 0; //desc this is the character being considered
    private int line = 1; //desc this provides the tokens' line no.s

    //gist this is the constructor for Scanner instances
    Scanner(String source) {
        this.source = source;}
    
    //gist this turns the whole code into tokens
    List<Token> scanTokens() {
        while (!isAtEnd()) {
            start = current;
            scanToken();}
        
        tokens.add(new Token(EOF, "", null, line));
        return tokens;}
    
    //gist this is to know if all the characters in the code have been scanned
    private boolean isAtEnd() {
        return current >= source.length();}
    
    //gist this scans a single character and turns it into a token
    private void scanToken() {
        char c = advance();
        switch (c) {
            case '(': addToken(LEFT_PAREN); break;
            case ')': addToken(RIGHT_PAREN); break;
            case '{': addToken(LEFT_BRACE); break;
            case '}': addToken(RIGHT_BRACE); break;
            case ',': addToken(COMMA); break;
            case '.': addToken(DOT); break;
            case '-': addToken(MINUS); break;
            case '+': addToken(PLUS); break;
            case ';': addToken(SEMICOLON); break;
            case '*': addToken(STAR); break;
            case '!':
                addToken(match('=') ? BANG_EQUAL : BANG);
                break;
            case '=':
                addToken(match('=') ? EQUAL_EQUAL : EQUAL);
                break;
            case '<':
                addToken(match('=') ? LESS_EQUAL : LESS);
                break;
            case '>':
                addToken(match('=') ? GREATER_EQUAL : GREATER);
                break;
            case '/':
                if (match('/')) {
                    while (peek() != '\n' && !isAtEnd()) advance();}
                else {
                    addToken(SLASH);}
                break;
            case ' ':
            case '\r':
            case '\t': break;

            case '\n': line++; break;

            case '\'': string(); break;
            
            default:
                if (isDigit(c)) {
                    number();}
                else {
                    Java_Lox.error(line, "Unexpected character.");}
                break;}}
    
    //gist this scans the current character then sets current to the next character
    //desc current is incremented here after being used
    private char advance() {
        return source.charAt(current++);}
    
    //gist this turns text into tokens
    private void addToken(Token_Type type) {
        addToken(type, null);}
    
    //gist this turns text into tokens
    private void addToken(Token_Type type, Object literal) {
        String text = source.substring(start, current);
        tokens.add(new Token(type, text, literal, line));}
    
    //gist this compares the current character with the expected character
    private boolean match(char expected) {
        if (isAtEnd()) return false;
        if (source.charAt(current) != expected) return false;

        current++;
        return true;}
    
    //gist this returns the current character
    //desc this looks ahead 1 character because advance() auto-increments current
    private char peek() {
        if (isAtEnd()) return '\0';
        return source.charAt(current);}
    
    //gist this returns the next character
    //desc this looks ahead 2 characters because advance() auto-increments current
    private char peekNext() {
        if (current + 1 >= source.length()) return '\0';
        return source.charAt(current + 1);}
    
    //gist this adds strings to the list of tokens
    private void string() {
        while (peek() != '\'' && !isAtEnd()) {
            if (peek() == '\n') line++;
            advance();}
        
        if (isAtEnd()) {
            Java_Lox.error(line, "Unterminated string.");
            return;}
        
        advance();

        String value = source.substring(start + 1, current - 1);
        addToken(STRING, value);}
    
    //gist this detects digits
    private boolean isDigit(char c) {
        return c >= '0' && c <= '9';}
    
    //gist this adds numbers to the list of tokens
    private void number() {
        while (isDigit(peek())) advance();

        if (peek() == '.' && isDigit(peekNext())) {
            advance();

            while (isDigit(peek())) advance();}
        
        addToken(NUMBER, Double.parseDouble(source.substring(start, current)));}
}
