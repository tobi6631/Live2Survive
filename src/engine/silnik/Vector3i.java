/*
 * (C) Tobias Development 2014
 */

package engine.silnik;

    public class Vector3i {
        private int x;
        private int y;
        private int z;
        
        public Vector3i(int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }
        
        public int getX() {return this.x;}
        public int getY() {return this.y;}
        public int getZ() {return this.z;}
    }
