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
    //* this is self-explanatory */
    static boolean hadError = false;
    public static void main(String[] args) throws IOException {
        if (args.length > 1) {
            System.out.println("Usage: java_lox [script]"); //* this tells users to only provide 1 file */
            System.exit(64);} //* this is the unix exit code for incorrect no. of parameters */
        else if (args.length == 1) {
            runFile(args[0]);}
        else {
            runPrompt();}}

    private static void runFile(String path) throws IOException {
        byte[] bytes = Files.readAllBytes(Paths.get(path));
        run(new String(bytes, Charset.defaultCharset()));
        
        if (hadError) System.exit(65);}
    
    private static void runPrompt() throws IOException {
        InputStreamReader input = new InputStreamReader(System.in);
        BufferedReader reader = new BufferedReader(input);

        for (;;) {
            System.out.println("> ");
            String line = reader.readLine();
            if (line == null) break;
            run(line);
            hadError = false;}}
    
    private static void run(String source) {
        Scanner scanner = new Scanner(source);
        List<Token> tokens = scanner.scanTokens(); //todo need to implement Token type and scanTokens() function

        for (Token token : tokens) { //todo need to implement Token type
            System.out.println(token);}}
    
    static void error(int line, String message) {
        report(line, "", message);}
    
    private static void report(int line, String where, String message) {
        System.err.println("[line " + line + "] Error" + where + ": " + message);
        hadError = true;}
}