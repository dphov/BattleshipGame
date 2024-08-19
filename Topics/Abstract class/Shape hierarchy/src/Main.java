abstract class Shape {

    abstract double getPerimeter();

    abstract double getArea();
}
class Triangle extends Shape {
    double a, b, c;
    Triangle(double a, double b, double c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }
    @Override
    double getPerimeter() {
        return a + b + c;
    }

    double semiPerimeter() {
        return this.getPerimeter() / 2;
    }
    @Override
    double getArea() {
        return Math.sqrt(this.semiPerimeter() * (semiPerimeter() - a) * (semiPerimeter() - b) * (semiPerimeter() - c));
    }
}
class Rectangle extends Shape {

    double a, b;
    Rectangle(double a, double b) {
        this.a = a;
        this.b = b;
    }
    @Override
    double getPerimeter() {
        return 2 * a + 2 * b;
    }

    @Override
    double getArea() {
        return a * b;
    }
}
class Circle extends Shape {
    final double PI = Math.PI;
    double radius;
    Circle(double radius) {
        this.radius = radius;
    }
    @Override
    double getPerimeter() {
        return 2 * PI * radius;
    }

    @Override
    double getArea() {
        return PI * Math.pow( radius, 2);
    }
}