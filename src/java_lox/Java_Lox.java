package java_lox;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Java_Lox {
    //* this is self-explanatory
    static boolean hadError = false;
    
    //gist this is the command-line tool
    public static void main(String[] args) throws IOException {
        if (args.length > 1) {
            System.out.println("Usage: java_lox [script]"); //* this tells users to only provide 1 file
            System.exit(64); //desc this is the unix exit code for incorrect no. of parameters
        }
        else if (args.length == 1) {runFile(args[0]);} //desc this compiles the given file
        else {runPrompt();} //desc users will input their code line by line
    }

    //gist this compiles the given file
    private static void runFile(String path) throws IOException {
        byte[] bytes = Files.readAllBytes(Paths.get(path)); //* this gets the bytes in the file

        //desc this converts the bytes to UTF-8 then compiles
        run(new String(bytes, Charset.defaultCharset()));
        
        //desc this means the file cannot be compiled
        if (hadError) System.exit(65);
    }
    
    //gist this is the interactive prompt for the command-line tool
    private static void runPrompt() throws IOException {
        InputStreamReader input = new InputStreamReader(System.in); //* this is for user input
        BufferedReader reader = new BufferedReader(input); //* this is also for user input

        for (;;) {
            System.out.print("> "); //* this is for prompting input from user
            String line = reader.readLine(); //* this reads user input
            if (line == null) break; //* if user inputs nothing then stop the loop
            run(line); //* this compiles user input
            hadError = false; //desc this ensures that the user can still input code when errors are detected
        }
    }
    
    //gist this compiles the code
    private static void run(String source) {
        Scanner scanner = new Scanner(source); //* this scans the provided code

        List<Token> tokens = scanner.scanTokens(); //* this turns the code into a list of tokens
        Parser parser = new Parser(tokens);
        Expr expression = parser.parse();

        if (hadError) return;

        System.out.println(new AstPrinter().print(expression));
    }
    
    //gist this is for reporting errors
    static void error(int line, String message) {
        report(line, "", message); //desc this reports errors
    }
    
    //gist this generates error messages
    private static void report(int line, String where, String message) {
        //desc this prints an error message
        System.err.println("[line " + line + "] Error" + where + ": " + message);
        hadError = true; //* this is self-explanatory
    }

    //gist this is also for reporting errors
    static void error(Token token, String message) {
        if (token.type == Token_Type.EOF)
        {report(token.line, " at end", message);}
        else
        {report(token.line, " at '" + token.lexeme + "'", message);}
    }
}