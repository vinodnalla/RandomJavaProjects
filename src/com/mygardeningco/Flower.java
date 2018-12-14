package com.mygardeningco;

public class Flower implements Comparable<Flower> {

    public Flower(char name, int count, int size) {
        this.name = name;
        this.count = count;
        this.size = size;
    }

    @Override
    public int compareTo(Flower o) {
        //Sorting by largest Cells requirements firsts
        if(o.size == this.size)
            return(o.count - this.count);
        else
            return (o.size - this.size );
    }

    @Override
    public String toString() {
        return (name+";"+count+";"+size);
    }

    public int getConstraint(){
        return size;
    }

    public int getCount(){
        return count;
    }

    public char getName(){
        return name;
    }

    private char name;
    private int count;
    private int size;
}
