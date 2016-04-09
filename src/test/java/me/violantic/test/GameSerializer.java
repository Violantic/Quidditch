package me.violantic.test;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ethan on 4/9/2016.
 */
public class GameSerializer {

    public static List<Location> locations;

    public static class Location implements Serializable {

        public int x, y, z;

        public Location(int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        private static final long serialVersionUID = 7526471155622776147L;

        private void readObject(ObjectInputStream objectInputStream) throws ClassNotFoundException, IOException {
            objectInputStream.defaultReadObject();
        }

        private void writeObject(ObjectOutputStream outputStream) throws IOException {
            outputStream.defaultWriteObject();
        }

    }

    public static void main(String[] args) {
        locations = new ArrayList<Location>();

        // Generate some locations. //
        for(int i = 0; i < 10; i ++) {
            locations.add(new Location(i, i, i));
        }

        writeFile();
    }

    private static void writeFile() {
        try {
            File directory = new File("game");
            File gameFile = new File(directory.getParentFile(), "Net.ser");

            if (gameFile.createNewFile()) {
                // Created the file. //
                System.out.println("File was created.");
            } else {
                // File was already created. //
                System.out.println("File was already there.");
            }

            // Write serializable objects to file. //
            FileOutputStream stream = new FileOutputStream(gameFile);
            ObjectOutputStream out = new ObjectOutputStream(stream);
            for(Location location : locations) {
                out.writeObject(location);
            }
            stream.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
