package java_lox;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java_lox.Token_Type.*;

public class Scanner {
    private final String source; //* this is the code that will be scanned
    private final List<Token> tokens = new ArrayList<>(); //* this is for the tokens generated by the scanner
    private int start = 0; //desc this is the index of the first character in the lexeme being scanned
    private int current = 0; //desc this is the index of the character being considered
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
            
            default:
                Java_Lox.error(line, "Unexpected character.");
                break;}}
    
    //gist this scans a character then sets current to the index of the next character
    private char advance() {
        return source.charAt(current++);}
    
    //gist this turns text into tokens
    private void addToken(Token_Type type) {
        addToken(type, null);}
    
    //gist this turns text into tokens
    private void addToken(Token_Type type, Object literal) {
        String text = source.substring(start, current);
        tokens.add(new Token(type, text, literal, line));}
    
    //gist this matches the current character with the expected character
    private boolean match(char expected) {
        if (isAtEnd()) return false;
        if (source.charAt(current) != expected) return false;

        current++;
        return true;}
}
