import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class TrackInfo {

    public static void main(String[] args) {

      String fileName = args[0];

      Track track = new Track(fileName);
      int numPoints = track.size();

        if (args.length == 0) {
            System.err.println("Please provide the filename as a command line argument");
            System.exit(0);
        }

        String filename = args[0];

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            Track track = new Track(br);
            System.out.printf("%d points in track\n", track.getNumPoints());
            System.out.printf("Lowest point is (%f, %f), %f m\n", Track.lowestPoint(), track.getMinLat(), track.getMinEle());
            System.out.printf("Highest point is (%f, %f), %f m\n", track.getMaxLon(), track.getMaxLat(), track.getMaxEle());
            System.out.printf("Total distance = %.3f km\n", track.getTotalDistance() / 1000.0);
            System.out.printf("Average speed = %.3f m/s\n", track.getAvgSpeed());
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
            System.exit(1);
        } catch (NumberFormatException e) {
            System.err.println("Error parsing track data: " + e.getMessage());
            System.exit(1);
        }
    }
}
