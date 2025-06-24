package topCoder.red.chapter5;

import java.util.ArrayList;
import java.util.List;

public class CrazyBot {

    static final Point START = new Point(0, 0);

    public double getProbability(int n, int east, int west, int south, int north) {
        Calc cals = new Calc(n, east, west, south, north);
        return cals.getProbability(new ArrayList<CrazyBot.Point>(), START, 1.0);
    }

    class Calc {

        int n;
        double east;
        double west;
        double south;
        double north;

        Calc(int n, double east, double west, double south, double north) {
            this.n = n;
            this.east = east / 100;
            this.west = west / 100;
            this.south = south / 100;
            this.north = north / 100;
        }

        private double getProbability(List<Point> historyList, Point point, double percent) {

            if (historyList.contains(point)) {
                // historyListがpointを含んでいるかどうか
                return 0;
            }

            if (this.n <= historyList.size()) {
                return 1 * percent;
            }

            historyList.add(point);

            double percentSum = 0;

            // 北に行った場合
            percentSum = percentSum
                    + getProbability(new ArrayList<CrazyBot.Point>(historyList), point.move(1, 0), percent * this.north);
            // 東に行った場合
            percentSum = percentSum
                    + getProbability(new ArrayList<CrazyBot.Point>(historyList), point.move(0, 1), percent * this.east);
            // 南に行った場合
            percentSum = percentSum
                    + getProbability(new ArrayList<CrazyBot.Point>(historyList), point.move(-1, 0), percent
                            * this.south);
            // 西に行った場合
            percentSum = percentSum
                    + getProbability(new ArrayList<CrazyBot.Point>(historyList), point.move(0, -1), percent * this.west);

            return percentSum;
        }
    }

    static class Point {

        public int ns;
        public int ew;

        public Point(int ns, int ew) {
            this.ns = ns;
            this.ew = ew;
        }

        Point move(int ns, int ew) {
            return new Point(this.ns + ns, this.ew + ew);
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof Point) {
                Point other = (Point) obj;
                if (this.ns == other.ns && this.ew == other.ew) {
                    return true;
                }
            }
            return false;
        }
    }
}
