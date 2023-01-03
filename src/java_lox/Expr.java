package java_lox;

//gist this is for expressions
abstract class Expr {
    //gist this is for interactions between expressions
    interface Visitor<R> {
        R visitAssignExpr(Assign expr);
        R visitBinaryExpr(Binary expr);
        R visitGroupingExpr(Grouping expr);
        R visitLiteralExpr(Literal expr);
        R visitLogicalExpr(Logical expr);
        R visitUnaryExpr(Unary expr);
        R visitVariableExpr(Variable expr);
    }

    //gist this is also for interactions between expressions
    abstract <R> R accept(Visitor<R> visitor);

    static class Assign extends Expr {
        Assign(Token name, Expr value) {
            this.name = name;
            this.value = value;
        }
    
        @Override
        <R> R accept(Visitor<R> visitor) {
            return visitor.visitAssignExpr(this);
        }
    
        final Token name;
        final Expr value;
    }

    //gist this is for binary expressions
    static class Binary extends Expr {
        //gist this is the constructor for binary expressions
        Binary(Expr left, Token operator, Expr right)
        {this.left = left;this.operator = operator;this.right = right;}
        
        final Expr left;final Token operator;final Expr right;

        @Override
        <R> R accept(Visitor<R> visitor)
        {return visitor.visitBinaryExpr(this);}
    }

    //gist this is for groupings
    static class Grouping extends Expr {
        //gist this is the constructor for groupings
        Grouping(Expr expression) {this.expression = expression;}

        final Expr expression;

        @Override
        <R> R accept(Visitor<R> visitor)
        {return visitor.visitGroupingExpr(this);}
    }

    //gist this is for literals
    static class Literal extends Expr {
        //gist this is the constructor for literals
        Literal(Object value) {this.value = value;}

        final Object value;

        @Override
        <R> R accept(Visitor<R> visitor)
        {return visitor.visitLiteralExpr(this);}
    }

    static class Logical extends Expr {
        Logical(Expr left, Token operator, Expr right) {
            this.left = left;
            this.operator = operator;
            this.right = right;
        }
    
        @Override
        <R> R accept(Visitor<R> visitor) {
            return visitor.visitLogicalExpr(this);
        }
    
        final Expr left;
        final Token operator;
        final Expr right;
    }

    //gist this is for unary expressions
    static class Unary extends Expr {
        //gist this is the constructor for unary expressions
        Unary(Token operator, Expr right)
        {this.operator = operator;this.right = right;}
        
        final Token operator;final Expr right;

        @Override
        <R> R accept(Visitor<R> visitor)
        {return visitor.visitUnaryExpr(this);}
    }

    static class Variable extends Expr {
        Variable(Token name) {
            this.name = name;
        }
    
        @Override
        <R> R accept(Visitor<R> visitor) {
            return visitor.visitVariableExpr(this);
        }
    
        final Token name;
    }
}
