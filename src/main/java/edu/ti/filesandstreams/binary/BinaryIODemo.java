package edu.ti.filesandstreams.binary;

import edu.ti.filesandstreams.dataobject.Species;
import java.util.*;
import java.io.*;

public class BinaryIODemo {
    //TODO -- read species data from an input file
    static Species[] initSpecies;

    public static void initSpecies() {
        Scanner input = null;

        try {
            File file = new File("src/main/resources/Species.txt");
            input = new Scanner(file);
            initSpecies = new Species[3];
            int i = -1;

            while (input.hasNextLine()) {
                i++;
                String[] data = input.nextLine().split(",");
                String name = data[0];
                int population = Integer.parseInt(data[1]);
                double gorwthRate = Double.parseDouble(data[2]);
                Species mySpecies = new Species(name, population, gorwthRate);
                initSpecies[i] = mySpecies;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (input != null) {
                input.close();
            }
        }
    }

    public static void main(String[] args) {
        BinaryIODemo.initSpecies();
        //TODO -- get the fileName from a command line argument
        String resourceFolder = "src/main/resources";
        String fileName = resourceFolder + "/" + args[0];
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(fileName))) {
            objectOutputStream.writeObject(initSpecies);
        } catch (FileNotFoundException e) {
            System.out.println("FileNotFoundException thrown writing to file " + fileName + ":" + e.getMessage());
        } catch (IOException e) {
            System.out.println("IOException thrown writing to file " + fileName + ":" + e.getMessage());
        }
        System.out.println("Array written to file " + fileName + " and file is closed.");
        System.out.println("Open the file for input and echo the array.");

        Species[] readFromFileSpecies = null;
        try (ObjectInputStream inputStream =
                     new ObjectInputStream(
                             new FileInputStream(fileName))) {
            readFromFileSpecies = (Species[]) inputStream.readObject();
        } catch (Exception e) {
            System.out.println("Error reading file " + fileName + ": " + e.getMessage());
        }

        System.out.println("The following were read from the file " + fileName + ":");
        assert readFromFileSpecies != null;
        for (Species readFromFileSpecy : readFromFileSpecies) {
            System.out.println(readFromFileSpecy.toString());
        }
    }
}
