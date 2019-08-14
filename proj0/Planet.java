public class Planet {
    public double xxPos;
    public double yyPos;
    public double xxVel;
    public double yyVel;
    public double mass;
    public String imgFileName;
    private static final double G = 6.67e-11;

    public Planet(double xP, double yP, double xV, double yV, double m, String img) {
        xxPos = xP;
        yyPos = yP;
        xxVel = xV;
        yyVel = yV;
        mass = m;
        imgFileName = img;
    }

    public Planet(Planet b) {
        xxPos = b.xxPos;
        yyPos = b.yyPos;
        xxVel = b.xxVel;
        yyVel = b.yyVel;
        mass = b.mass;
        imgFileName = b.imgFileName;
    }

    public double calcDistance(Planet b ) {
        double d = Math.sqrt(Math.pow(xxPos - b.xxPos, 2) + Math.pow(yyPos - b.yyPos, 2));
        return d;
    }

    public double calcForceExertedBy(Planet b) {
        return G * mass * b.mass / Math.pow(this.calcDistance(b), 2);
    }

    public double calcForceExertedByX(Planet b) {
        double d = this.calcDistance(b);
        double dx = (b.xxPos - xxPos) / d;
        double ForceByX = this.calcForceExertedBy(b) * dx;
        return ForceByX;
    }

    public double calcForceExertedByY(Planet b) {
        double d = this.calcDistance(b);
        double dy = (b.yyPos - yyPos) / d;
        double ForceByY = this.calcForceExertedBy(b) * dy;
        return ForceByY;
    }

    public double calcNetForceExertedByX(Planet[] b) {
        double NetForceByX = 0.0;
        for (Planet body : b) {
            if (!this.equals(body)) {
                NetForceByX += this.calcForceExertedByX(body);
            }
        }
        return NetForceByX;
    }

    public double calcNetForceExertedByY(Planet[] b) {
        double NetForceByY = 0.0;
        for (Planet body : b) {
            if (!this.equals(body)) {
                NetForceByY += this.calcForceExertedByY(body);
            }
        }
        return NetForceByY;
    }

    public void update(double t, double ForceX, double ForceY) {
        double aX = ForceX / mass;
        double aY = ForceY / mass;
        xxVel = xxVel + t * aX;
        yyVel = yyVel + t * aY;
        xxPos = xxPos + xxVel * t;
        yyPos = yyPos + yyVel * t;
    }

    public void draw() {
//        StdDraw.setScale(-100, 100);
//        StdDraw.enableDoubleBuffering();
//        StdDraw.clear();
        StdDraw.picture(xxPos, yyPos, imgFileName);
    }
}
