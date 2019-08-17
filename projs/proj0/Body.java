public class Body {
    public double xxPos;
    public double yyPos;
    public double xxVel;
    public double yyVel;
    public double mass;
    public String imgFileName;
    private static final double G = 6.67e-11;

    public Body(double xP, double yP, double xV, double yV,
                double m, String img) {
        xxPos = xP;
        yyPos = yP;
        xxVel = xV;
        yyVel = yV;
        mass = m;
        imgFileName = img;
    }

    public Body(Body b) {
        xxPos = b.xxPos;
        yyPos = b.yyPos;
        xxVel = b.xxVel;
        yyVel = b.yyVel;
        mass = b.mass;
        imgFileName = b.imgFileName;
    }

    public double calcDistance(Body b) {
        double d = Math.sqrt(Math.pow(xxPos - b.xxPos, 2)
                + Math.pow(yyPos - b.yyPos, 2));
        return d;
    }

    public double calcForceExertedBy(Body b) {
        return G * mass * b.mass / Math.pow(this.calcDistance(b), 2);
    }

    public double calcForceExertedByX(Body b) {
        double d = this.calcDistance(b);
        double dx = (b.xxPos - xxPos) / d;
        double forceByX = this.calcForceExertedBy(b) * dx;
        return forceByX;
    }

    public double calcForceExertedByY(Body b) {
        double d = this.calcDistance(b);
        double dy = (b.yyPos - yyPos) / d;
        double forceByY = this.calcForceExertedBy(b) * dy;
        return forceByY;
    }

    public double calcNetForceExertedByX(Body[] b) {
        double netForceByX = 0.0;
        for (Body body : b) {
            if (!this.equals(body)) {
                netForceByX += this.calcForceExertedByX(body);
            }
        }
        return netForceByX;
    }

    public double calcNetForceExertedByY(Body[] b) {
        double netForceByY = 0.0;
        for (Body body : b) {
            if (!this.equals(body)) {
                netForceByY += this.calcForceExertedByY(body);
            }
        }
        return netForceByY;
    }

    public void update(double t, double forceX, double forceY) {
        double aX = forceX / mass;
        double aY = forceY / mass;
        xxVel = xxVel + t * aX;
        yyVel = yyVel + t * aY;
        xxPos = xxPos + xxVel * t;
        yyPos = yyPos + yyVel * t;
    }

    public void draw() {
        StdDraw.picture(xxPos, yyPos, imgFileName);
    }
}
