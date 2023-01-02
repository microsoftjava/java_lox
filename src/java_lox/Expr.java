package java_lox;

//gist this is for expressions
abstract class Expr {
    //gist this is for binary expressions
    static class Binary extends Expr {
        //gist this is the constructor for binary expressions
        Binary(Expr left, Token operator, Expr right) {
            this.left = left;
            this.operator = operator;
            this.right = right;}
        
        final Expr left;
        final Token operator;
        final Expr right;
    }
}
