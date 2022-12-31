package java_lox;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

public class Java_Lox {
    //* this is self-explanatory
    static boolean hadError = false;
    public static void main(String[] args) throws IOException {
        if (args.length > 1) {
            System.out.println("Usage: java_lox [script]"); //* this tells users to only provide 1 file
            System.exit(64);} //desc this is the unix exit code for incorrect no. of parameters
        else if (args.length == 1) {
            runFile(args[0]);} //desc this compiles the given file
        else {
            runPrompt();}} //desc users will input -and have- their code compiled line by line

    private static void runFile(String path) throws IOException {
        byte[] bytes = Files.readAllBytes(Paths.get(path)); //* this gets the bytes in the file

        //desc this converts the bytes to UTF-8 then compiles
        run(new String(bytes, Charset.defaultCharset()));
        
        //desc this means the file cannot be compiled
        if (hadError) System.exit(65);}
    
    private static void runPrompt() throws IOException {
        InputStreamReader input = new InputStreamReader(System.in); //* this is for user input
        BufferedReader reader = new BufferedReader(input); //* this is also for user input

        for (;;) {
            System.out.println("> "); //* this is for prompting input from user
            String line = reader.readLine(); //* this reads user input
            if (line == null) break; //* if user inputs nothing then stop the loop
            run(line); //* this compiles user input
            hadError = false;}} //desc this ensures that the user can still input code when errors are detected
    
    private static void run(String source) {
        Scanner scanner = new Scanner(source); //* this scans the provided code

        //todo need to implement Token type and scanTokens() function
        List<Token> tokens = scanner.scanTokens(); //* this turns the code into a list of tokens

        //todo need to implement Token type
        for (Token token : tokens) { 
            System.out.println(token);}} //* this prints each token in the tokens list
    
    static void error(int line, String message) {
        report(line, "", message);} //desc this reports errors
    
    private static void report(int line, String where, String message) {
        //desc this prints an error message
        System.err.println("[line " + line + "] Error" + where + ": " + message);
        hadError = true;} //* this is self-explanatory
}