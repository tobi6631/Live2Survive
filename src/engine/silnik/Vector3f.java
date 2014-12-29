/*
 * (C) Tobias Development 2014
 */

package engine.silnik;

    public class Vector3f {
        private float x;
        private float y;
        private float z;
        
        public Vector3f(float x, float y, float z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }
        
        public float getX() {return this.x;}
        public float getY() {return this.y;}
        public float getZ() {return this.z;}
    }
