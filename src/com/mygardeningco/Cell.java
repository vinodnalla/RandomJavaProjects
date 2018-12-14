package com.mygardeningco;

public class Cell {

    public Cell(int x, int y){
        this.x = x;
        this.y = y;
    }

    public Cell(){
        this.x = -1;
        this.y = -1;
    }

    public Cell(Cell c){
        this.x = c.X();
        this.y = c.Y();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * (result + x + y);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Cell) {
            final Cell cell = (Cell) obj;
            return (this.x == cell.x && this.y == cell.y);
        } else {
            return false;
        }

    }

    public boolean IsValid(){
        if(this.x == -1 || this.y == -1 )
            return false;
        else
            return true;
    }

    public int X(){return x;}

    public int Y(){return y;}

    public void setX(int x){ this.x = x;}

    public void setY(int y){ this.y = y;}

    private int x;
    private int y;
}
