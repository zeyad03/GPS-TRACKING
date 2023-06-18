import java.time.ZonedDateTime;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.time.Duration;
import java.time.temporal.ChronoUnit;

/**
 * Represents a point in space and time, recorded by a GPS sensor.
 *
 * @author Zeyad Bassyouni
 */
public class Track {
  // TODO: Create a stub for the constructor
  private final List<Point> points;

  public Track() {
    this.points = new ArrayList<>();
  }

  public Track(String fileName) throws IOException {
    this.points = new ArrayList<>();
    readFile(fileName);
  }

  // TODO: Create a stub for readFile()
  public void readFile(String filename) throws IOException {
    points.clear();
    Scanner scanner = new Scanner(new File(filename));
    scanner.nextLine();
    while (scanner.hasNextLine()) {
      String line = scanner.nextLine();
      String[] parts = line.split(",");
      if (parts.length < 4) {
        throw new GPSException("Invalid data format in file.");
      }
      ZonedDateTime time = ZonedDateTime.parse(parts[0]);
      double longitude = Double.parseDouble(parts[1]);
      double latitude = Double.parseDouble(parts[2]);
      double elevation = Double.parseDouble(parts[3]);
      Point point = new Point(time, longitude, latitude, elevation);
      points.add(point);
    }
    scanner.close();
  }

  // TODO: Create a stub for add()
  public void add(Point point) {
    points.add(point);
  }

  // TODO: Create a stub for get()
  public Point get(int index) {
    if (index < 0 || index >= points.size()) {
      throw new GPSException("Invalid index");
    }
    return points.get(index);
  }

  // TODO: Create a stub for size()
  public int size() {
    return points.size();
  }

  // TODO: Create a stub for lowestPoint()
  public Point lowestPoint() {
    if (points.size() < 1) {
      throw new GPSException("Track is empty");
    }
    Point lowest = points.get(0);
    for (int i = 1; i < points.size(); i++) {
      Point current = points.get(i);
      if (current.getElevation() < lowest.getElevation()) {
        lowest = current;
      }
    }
    return lowest;
  }

  // TODO: Create a stub for highestPoint()
  public Point highestPoint() {
    if (points.size() < 1) {
      throw new GPSException("Track is empty");
    }
    Point highest = points.get(0);
    for (int i = 1; i < points.size(); i++) {
      Point current = points.get(i);
      if (current.getElevation() > highest.getElevation()) {
        highest = current;
      }
    }
    return highest;
  }

  // TODO: Create a stub for totalDistance()
  public double totalDistance() {
    if (points.size() < 2) {
      throw new GPSException("Not enough points in track to compute distance");
    }
    double total = 0;
    for (int i = 1; i < points.size(); i++) {
      Point p = points.get(i-1);
      Point q = points.get(i);
      double distance = Point.greatCircleDistance(p, q);
      total += distance;
    }
    return total;
  }

  // TODO: Create a stub for averageSpeed()
  public double averageSpeed() {
    if (points.size() < 2) {
      throw new GPSException("Not enough points in track to compute speed");
    }
    Point first = points.get(0);
    Point last = points.get(points.size()-1);
    Duration duration = Duration.between(first.getTime(), last.getTime());
    double timeInSeconds = duration.get(ChronoUnit.SECONDS);
    return totalDistance() / timeInSeconds;
  }
}
