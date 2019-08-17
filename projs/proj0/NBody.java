public class NBody {
    public static double readRadius(String filePath) {
        In in = new In(filePath);
        int firstItemInFile = in.readInt();
        double radius = in.readDouble();
        return radius;
    }

    public static Body[] readBodies(String filePath) {
        In in = new In(filePath);
        int firstItemInFile = in.readInt();
        double radius = in.readDouble();
        int N = 5;
        Body[] b = new Body[N];
        for (int i = 0; i < N; i++) {
            double xxPos = in.readDouble();
            double yyPos = in.readDouble();
            double xxVel = in.readDouble();
            double yyVel = in.readDouble();
            double mass = in.readDouble();
            String imgFileName = "./images/" + in.readString();
            b[i] = new Body(xxPos, yyPos, xxVel, yyVel, mass, imgFileName);
        }
        return b;
    }

    public static void main(String[] args) {
        double T = Double.parseDouble(args[0]);
        double dt = Double.parseDouble(args[1]);
        String filename = args[2];
        double radius = readRadius(filename);
        Body[] bodies = readBodies(filename);
        String backgroud = "./images/starfield.jpg";
        StdDraw.setScale(-radius, radius);
        StdDraw.enableDoubleBuffering();
        int N = 5;
        Double[] xForces = new Double[N];
        Double[] yForces = new Double[N];
        for (double i = 0; i < T; i = i + dt) {
            StdDraw.clear();
            for (int j = 0; j < N; j++) {
                xForces[j] = bodies[j].calcNetForceExertedByX(bodies);
                yForces[j] = bodies[j].calcNetForceExertedByY(bodies);
            }
            for (int j = 0; j < N; j++) {
                bodies[j].update(dt, xForces[j], yForces[j]);
            }
            StdDraw.picture(0, 0, backgroud);
            for (Body b : bodies) {
                b.draw();
            }
            StdDraw.show();
            StdDraw.pause(10);
        }

        StdOut.printf("%d\n", bodies.length);
        StdOut.printf("%.2e\n", radius);
        for (int i = 0; i < bodies.length; i++) {
            StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                    bodies[i].xxPos, bodies[i].yyPos, bodies[i].xxVel,
                    bodies[i].yyVel, bodies[i].mass, bodies[i].imgFileName);
        }
    }
}
