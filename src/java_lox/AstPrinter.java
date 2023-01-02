package java_lox;

//gist this is to make sure the expressions are correct
public class AstPrinter implements Expr.Visitor<String> {
    String print(Expr expr)
    {return expr.accept(this);}
}
