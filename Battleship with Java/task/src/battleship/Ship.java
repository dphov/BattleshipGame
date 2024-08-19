package battleship;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public class Ship {
    public final int size;
    private int hits;
    Integer[] xyStart = new Integer[2];
    Integer[] xyEnd = new Integer[2];
    boolean isVertical;
    String name;

    public Ship(Builder builder) {
        this.size = builder.size;
        this.hits = 0;
        this.xyStart = builder.xyStart;
        this.xyEnd = builder.xyEnd;
        this.isVertical = builder.isVertical;
        this.name = builder.name;
    }

    public void hitShip() {
        if (hits < size) {
            this.hits = this.hits + 1;
        }
    }
    public boolean isSank() {
        return hits == size;
    }

    public int getSize() {
        return size;
    }

    public String getName() {
        return name;
    }

    public void setIsVertical(boolean b) {
        this.isVertical = b;
    }

    public static class Builder {
        private int size;
        private Integer[] xyStart = new Integer[2];
        private Integer[] xyEnd = new Integer[2];
        private boolean isVertical;
        private String name;

        public Builder size(int size) {
            this.size = size;
            return this;
        }

        public Builder xyStart(Integer[] xyStart) {
            this.xyStart = xyStart.clone();
            return this;
        }

        public Builder xyEnd(Integer[] xyEnd) {
            this.xyEnd = xyEnd.clone();
            return this;
        }

        public Builder isVertical(boolean isVertical) {
            this.isVertical = isVertical;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Ship build() {
            return new Ship(this);
        }
    }

    public void setXyStart(Integer[] xyStart) {
        this.xyStart = xyStart;
    }

    public void setXyEnd(Integer[] xyEnd) {
        this.xyEnd = xyEnd;
    }

    public void printInfo() {
        StringBuilder sb = new StringBuilder();
        sb.append("Length: " + size + "\n");
        sb.append("Parts: ");

        if (!isVertical) {
            Integer i = xyStart[1];
            if (xyStart[1] > xyEnd[1]) {
                while (i >= xyEnd[1]) {
                    if (i.equals(xyEnd[1])) {
                        sb.append(Field.convertXYToPosition(xyStart[0], i));
                    } else {
                        sb.append(Field.convertXYToPosition(xyStart[0], i)).append(" ");
                    }
                    i--;
                }
            } else {
                while (i <= xyEnd[1]) {
                    if (i.equals(xyEnd[1])) {
                        sb.append(Field.convertXYToPosition(xyStart[0], i));
                    } else {
                        sb.append(Field.convertXYToPosition(xyStart[0], i) + " ");
                    }
                    i++;
                }
            }
        } else {
            Integer i = xyStart[0];
            if (xyStart[0] > xyEnd[0]) {
                while (i >= xyEnd[0]) {
                    if (i.equals(xyEnd[0])) {
                        sb.append(Field.convertXYToPosition(i, xyStart[1]));
                    } else {
                        sb.append(Field.convertXYToPosition(i, xyStart[1]) + " ");
                    }
                    i--;
                }
            } else {
                while (i <= xyEnd[0]) {
                    if (i.equals(xyEnd[0])) {
                        sb.append(Field.convertXYToPosition(i, xyStart[1]));
                    } else {
                        sb.append(Field.convertXYToPosition(i, xyStart[1]) + " ");
                    }
                    i++;
                }
            }
        }
        System.out.println(sb);
    }
}
