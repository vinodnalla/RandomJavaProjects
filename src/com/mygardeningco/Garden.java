package com.mygardeningco;

final public class Garden {

    public Garden(int M, int N) {
        this.M = M;
        this.N = N;
        data = new char[M][N];
    }

    public Garden(char[][] data) {
        M = data.length;
        N = data[0].length;
        this.data = new char[M][N];
        for (int i = 0; i < M; i++)
            for (int j = 0; j < N; j++)
                this.data[i][j] = data[i][j];
    }

    public void show() {
        System.out.println();
        for (int i = 0; i < M; i++) {
            for (int j = 0; j < N; j++)
                if(data[i][j] == ' ')
                    System.out.print('-');
                else
                    System.out.print(data[i][j]);
            System.out.println();
        }
    }

    public int gardenX(){
        return N;
    }

    public int gardenY(){
        return M;
    }

    public Cell plantFlowerInNextEmptyCell(Cell seed, char flowerName){
        // At every cell, there are 3 possible directions it can search for an empty cell
        // Horizontal(x+1, y), Vertical(x, y+1) & Diagonal (x+1, y+1)
        // This doesn't know/bother about planting strategy or flower restrictions, Gardner needs to take care of it

        int seedX = seed.X();
        int seedY = seed.Y();

        if(seedX >= N || seedY >= M || (!seed.IsValid())){
            return new Cell();
        }

        // Look for next available empty space closest to Seed,
        for (int i = 0; i < MAX_SEARCH_DISTANCE; ++i) {
            for (int j = 0; j < MAX_SEARCH_DISTANCE; ++j) {
                if ((seedX + i) < N && (seedY + j) < M && data[seedY + j][seedX + i] == EMPTY_CELL) {
                    seed.setX(seedX + i);
                    seed.setY(seedY + j);
                    //Plant & return planned location
                    //System.out.println("Planted Flower: " + flowerName + " at: (" + seed.X() + " ," + seed.Y() + ")");
                    data[seed.Y()][seed.X()] = flowerName;
                    return seed;
                }
            }
        }
        //Failed to plant
        return new Cell();
    }

    private final char EMPTY_CELL = ' ';
    private   int M;
    private   int N;

    private final char[][] data;
    final int MAX_SEARCH_DISTANCE = 10;

    private Garden(Garden A) {
        this(A.data);
    }
}