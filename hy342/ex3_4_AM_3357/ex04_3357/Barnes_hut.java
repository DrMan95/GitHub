import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;

public class Barnes_hut {
    private static final int DT = 1;
    private static final double G = 6.67e-11;
    private static ForkJoinPool myPool;

    Universe u;
    Barnes_hut c1;
    Barnes_hut c2;
    Barnes_hut c3;
    Barnes_hut c4;

    Barnes_hut(Universe _u) {
        this.u = _u;
        this.c1 = null;
        this.c2 = null;
        this.c3 = null;
        this.c4 = null;
    }

    private static Barnes_hut make_BHnode(ArrayList<Planet> before, Coordinate new_center, Coordinate old_center) {
        Barnes_hut new_BH = new Barnes_hut(new Universe(new_center));
        int pos = new_center.where_is(old_center);
        new_BH.u.fill_Planets(before, pos, old_center);
        return new_BH;
    }

    private static void free_BHtree(Barnes_hut root){
        if( root == null)   return;
        free_BHtree(root.c1);
        free_BHtree(root.c2);
        free_BHtree(root.c3);
        free_BHtree(root.c4);
        root.u.delete();
        root = null;
    }

    private static Barnes_hut make_BHtree(Barnes_hut root) {
        if (root.u.has_0_or_1_Planet_()) return root;

        root.c1 = make_BHtree(make_BHnode(root.u.planets, new Coordinate(root.u.center.x - root.u.center.R / 2,
                root.u.center.y + root.u.center.R / 2, root.u.center.R / 2), root.u.center));
        root.c2 = make_BHtree(make_BHnode(root.u.planets, new Coordinate(root.u.center.x + root.u.center.R / 2,
                root.u.center.y + root.u.center.R / 2, root.u.center.R / 2), root.u.center));
        root.c3 = make_BHtree(make_BHnode(root.u.planets, new Coordinate(root.u.center.x - root.u.center.R / 2,
                root.u.center.y - root.u.center.R / 2, root.u.center.R / 2), root.u.center));
        root.c4 = make_BHtree(make_BHnode(root.u.planets, new Coordinate(root.u.center.x + root.u.center.R / 2,
                root.u.center.y - root.u.center.R / 2, root.u.center.R / 2), root.u.center));

        return root;
    }

    private static double F(double m1, double m2, double r) {
        return r == 0 ? 0 : (G * (m1 * m2)) / (r * r);
    }

    private static double Fi(double F, double i1, double i2, double r) {
        if (r == 0)
            return 0;
        double fi = F * ((i2 - i1) / r);
        return Double.isNaN(fi) ? 0 : fi;
    }

    private static void calculate_F(Planet e, Barnes_hut root) {
        if (root == null)
            return;
        if (root.u.p == 0)
            return;
        if (root.u.p == 1 && e == root.u.planets.get(0))
            return;
        double r;
        if (root.u.p == 1) {
            r = e.c.distance_from(root.u.planets.get(0).c);
            double f = F(e.m, root.u.planets.get(0).m, r);
            e.Fx += Fi(f, e.c.x, root.u.planets.get(0).c.x, r);
            e.Fy += Fi(f, e.c.y, root.u.planets.get(0).c.y, r);
            return;
        }
        r = e.c.distance_from(root.u.center_mass);
        if (r == 0)
            return;
        if (r > 2 * e.c.R && !root.u.contains(e)) {
            double f = F(e.m, root.u.m, r);
            e.Fx += Fi(f, e.c.x, root.u.center_mass.x, r);
            e.Fy += Fi(f, e.c.y, root.u.center_mass.y, r);
        } else {
            calculate_F(e, root.c1);
            calculate_F(e, root.c2);
            calculate_F(e, root.c3);
            calculate_F(e, root.c4);
        }
        return;
    }

    private static void calculate_all_F(Barnes_hut root) {
        try {
            myPool.submit(() -> root.u.planets.parallelStream().forEach((p) -> {
                p._init_F();
                calculate_F(p, root);
                p.calculate_A();
            })).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        // for(int i=0; i<root.u.planets.size() ; i++){
        //     root.u.planets.get(i)._init_F();
        //     calculate_F(root.u.planets.get(i) , root);
        //     root.u.planets.get(i).calculate_A();
        // }
    }
    
    private static void next_sec(Universe u){
        try {
            myPool.submit(() -> u.planets.parallelStream().forEach((p) -> {
                p.v.x = p.v.x + (DT * p.Ax);
                p.v.y = p.v.y + (DT * p.Ay);
                
                p.c.x = p.c.x + (DT * p.v.x);
                p.c.y = p.c.y + (DT * p.v.y);
            })).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        // for(int i=0 ; i<u.p; i++){
        //     u.planets.get(i).v.x = u.planets.get(i).v.x + (DT * u.planets.get(i).Ax);
        //     u.planets.get(i).v.y = u.planets.get(i).v.y + (DT * u.planets.get(i).Ay);
            
        //     u.planets.get(i).c.x = u.planets.get(i).c.x + (DT * u.planets.get(i).v.x);
        //     u.planets.get(i).c.y = u.planets.get(i).c.y + (DT * u.planets.get(i).v.y);
        // }
    }

    public static void main(String[] args){
        int N;
        double R;
        ArrayList<Planet> planets = new ArrayList<>();
        try {
            File input = new File(args[0]);
            Scanner myReader = new Scanner(input);
            String line = myReader.nextLine();
            N = Integer.parseInt(line);
            line = myReader.nextLine();
            R = Double.parseDouble(line);
            for(int i=0 ; i<N ; i++) {
                line = myReader.nextLine();
                String[] tmp = line.split("\\s+");
                planets.add(new Planet( tmp[5] ,
                                        Double.parseDouble(tmp[4]) ,
                                        new Coordinate( Double.parseDouble(tmp[0]) , Double.parseDouble(tmp[1]) , R) ,
                                        new Velocity( Double.parseDouble(tmp[2]) , Double.parseDouble(tmp[3]) )
                                    ));
            }
            myReader.close();
            
            Coordinate center0 = new Coordinate(0 , 0 , R);
            Universe U = new Universe(center0);
            Barnes_hut root = null;
            myPool = new ForkJoinPool(Integer.parseInt(args[2]));
            for(int i=0 ; i<Integer.parseInt(args[1]) ; i++){
                free_BHtree(root);
                U._init_planets_(planets);
                root = new Barnes_hut(U);
                root = make_BHtree(root);
                calculate_all_F(root);
                next_sec(U);
            }
            U.print_planets();


        }catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}