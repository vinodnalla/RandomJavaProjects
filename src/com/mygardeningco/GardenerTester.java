package com.mygardeningco;

import java.io.IOException;

public class GardenerTester {

    public static void main(String[] args) {

        if(args.length != 2){
            System.out.println("Usage: GardenerTester <Garden_file_name> <Flowers_file_name>");
            return;
        }

        try {
            Gardener MyGardener = new Gardener();
            MyGardener.createGarden(args[0]);
            MyGardener.displayGarden();
            MyGardener.plantInGarden(args[1]);
            MyGardener.displayGarden();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InternalGardenException e){
            e.getMessage();
        }

    }
}
