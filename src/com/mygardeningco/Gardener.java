package com.mygardeningco;

import java.util.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.*;

public class Gardener {


    public void createGarden(String gardenFileName) throws IOException{
        //Reads files and creates Garden
        loadGardenSpec(gardenFileName);
    }

    public void displayGarden(){
        garden.show();
    }

    public void plantInGarden(String FlowersListFile)throws IOException, InternalGardenException {
        loadFlowers(FlowersListFile);
        plantFlowers();
    }

    public void clearGarden(){
        //TODO
    }

    private void loadGardenSpec(String gardenSpec) throws IOException{

        try {
            String[] gardenRows = Files.lines(Paths.get(gardenSpec))
                    .map(s -> s.trim())
                    .filter(s -> !s.isEmpty())
                    .toArray(size -> new String[size]);

            int M = gardenRows.length;
            int N = gardenRows[0].length();
            char[][] data = new char[M][N];

            for (int i = 0; i < gardenRows.length; i++) {
                data[i] = gardenRows[i].toCharArray();
            }

            garden = new Garden(data);

        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
    }

    private void loadFlowers(String FlowersListSpec) throws IOException{

        try {
            this.flowers = new ArrayList<>();

            ArrayList<String> flowerStrings = Files.lines(Paths.get(FlowersListSpec))
                    .map(s -> s.trim())
                    .filter(s -> !s.isEmpty())
                    .collect(Collectors.toCollection(ArrayList::new));

            for (String sFlower: flowerStrings) {
                String[] fSplit = sFlower.split(",");
                Flower fl = new Flower(fSplit[0].toCharArray()[0], Integer.parseInt(fSplit[1]), Integer.parseInt(fSplit[2]));
                this.flowers.add(fl);
            }

        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
    }

    private void InvalidateOldCells(Cell rootSeed, int constraint){
        Iterator<Cell> cellIterator = possibleCellSet.iterator();
        while (cellIterator.hasNext()) {
            Cell cell = cellIterator.next();
            if (!(Math.abs(cell.Y() - rootSeed.Y()) < constraint && Math.abs(cell.X() - rootSeed.X()) < constraint)) {
                continue;
            }
            cellIterator.remove();
        }
    }

    private void PopulateNextBestCells(Cell rootSeed, int constraint){
        //Branch out in all 3 direction (right, bottom, diagonal)

            if(!rootSeed.IsValid()) {
                return;
            }

            InvalidateOldCells(rootSeed, constraint);

            if (rootSeed.X() + constraint < garden.gardenX())
                possibleCellSet.add(new Cell(rootSeed.X() + constraint, rootSeed.Y()));
            if (rootSeed.Y() + constraint < garden.gardenY())
                possibleCellSet.add(new Cell(rootSeed.X(), rootSeed.Y() + constraint));
            if (rootSeed.X() + constraint < garden.gardenX() && rootSeed.Y() + constraint < garden.gardenY())
                possibleCellSet.add(new Cell(rootSeed.X() + constraint, rootSeed.Y() + constraint));
    }

    private void plantFlowers() throws InternalGardenException {

        Collections.sort(flowers);
        for (Flower flowerType: flowers) {

            possibleCellSet.add(new Cell(1,1));
            for(int flowerCount = 0; flowerCount < flowerType.getCount(); ++flowerCount) {

                Cell plantedCell;
                do {
                    //System.out.println("Trying Flower:" + flowerType.getName() +" - " + flowerCount );
                    Iterator<Cell> cellIterator = possibleCellSet.iterator();
                    if (!cellIterator.hasNext()){
                        System.out.println("Failed to plant Flower:" + flowerType.getName() +" , Flower Number" + flowerCount+1 );
                        break;
                    }
                    Cell seedCell = cellIterator.next();
                    possibleCellSet.remove(seedCell);

                    plantedCell = garden.plantFlowerInNextEmptyCell(seedCell, flowerType.getName());
                    PopulateNextBestCells(plantedCell, flowerType.getConstraint());

                }while(!plantedCell.IsValid() && !possibleCellSet.isEmpty());
                //System.out.println("Tried Flower:" + flowerType.getName() +" - " + flowerCount );
            }
            possibleCellSet.clear();
        }
    }

    private Garden garden;
    private List<Flower> flowers;
    private Set<Cell> possibleCellSet = new HashSet<>();
}
