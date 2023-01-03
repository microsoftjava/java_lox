package java_lox;
/*
//gist this is to make sure the expressions are correct
public class AstPrinter implements Expr.Visitor<String> {
    //gist this returns the given expression but in brackets
    String print(Expr expr)
    {return expr.accept(this);}

    //gist this puts binary expressions in brackets
    @Override
    public String visitBinaryExpr(Expr.Binary expr) {
        return parenthesize(expr.operator.lexeme,
                            expr.left, expr.right);
    }

    //gist this puts groupings in brackets
    @Override
    public String visitGroupingExpr(Expr.Grouping expr) {
        return parenthesize("group", expr.expression);
    }

    //gist this puts literals in brackets
    @Override
    public String visitLiteralExpr(Expr.Literal expr) {
        if (expr.value == null) return "nil";
        return expr.value.toString();
    }

    //gist this puts unary expressions in brackets
    @Override
    public String visitUnaryExpr(Expr.Unary expr) {
        return parenthesize(expr.operator.lexeme, expr.right);
    }
    
    //gist this puts the given expression in brackets
    private String parenthesize(String name, Expr... exprs) {
        StringBuilder builder = new StringBuilder();

        builder.append("(").append(name);
        for (Expr expr : exprs) {
            builder.append(" ");
            builder.append(expr.accept(this));
        }
        builder.append(")");

        return builder.toString();
    }
    
    //gist this is just here because the book put it here
    //gist this is useless btw
    public static void main(String[] args) {
    Expr expression = new Expr.Binary(
        new Expr.Unary(
            new Token(Token_Type.MINUS, "-", null, 1),
            new Expr.Literal(123)),
        new Token(Token_Type.STAR, "*", null, 1),
        new Expr.Grouping(
            new Expr.Literal(45.67)));

    System.out.println(new AstPrinter().print(expression));
    }
} */
