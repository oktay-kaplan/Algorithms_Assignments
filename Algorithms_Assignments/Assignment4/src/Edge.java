    public  class Edge implements Comparable<Edge>{
        MetroStop nextMetroStop;
        double time;

        public Edge(MetroStop s, double time) {
            this.nextMetroStop = s;
            this.time = time;
        }

        public int compareTo(Edge e) {
            return (this.time >= e.time) ? 1 : -1;
        }
    }

