package java_lox;

//gist this is for expressions
abstract class Expr {
    //gist this is for binary expressions
    static class Binary extends Expr {
        //gist this is the constructor for binary expressions
        Binary(Expr left, Token operator, Expr right)
        {this.left = left;this.operator = operator;this.right = right;}
        
        final Expr left;final Token operator;final Expr right;
    }

    //gist this is for groupings
    static class Grouping extends Expr {
        //gist this is the constructor for groupings
        Grouping(Expr expression) {this.expression = expression;}

        final Expr expression;
    }

    //gist this is for literals
    static class Literal extends Expr {
        //gist this is the constructor for literals
        Literal(Object value) {this.value = value;}

        final Object value;
    }

    //gist this is for unary expressions
    static class Unary extends Expr {
        //gist this is the constructor for unary expressions
        Unary(Token operator, Expr right)
        {this.operator = operator;this.right = right;}
        
        final Token operator;final Expr right;
    }
}
