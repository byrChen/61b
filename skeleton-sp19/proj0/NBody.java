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
        Body[] b = new Body[5];
        for (int i = 0; i < 5; i++) {
            double xxPos = in.readDouble();
            double yyPos = in.readDouble();
            double xxVel = in.readDouble();
            double yyVel = in.readDouble();
            double mass = in.readDouble();
            String imgFileName = in.readString();
            b[i] = new Body(xxPos, yyPos, xxVel, yyVel, mass, imgFileName);
        }
        return b;
    }

    public static void main(String[] args) {

    }
}
