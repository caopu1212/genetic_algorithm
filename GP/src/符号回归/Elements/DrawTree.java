package src.符号回归.Elements;


public class DrawTree {

    public static class Trunk {
        Trunk prev;
        String str;

        public Trunk(Trunk prev, String str) {
            this.prev = prev;
            this.str = str;
        }
    }
}
