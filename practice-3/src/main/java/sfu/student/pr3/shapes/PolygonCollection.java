package sfu.student.pr3.shapes;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

public class PolygonCollection {

  private final List<? super Polygon> polygons;

  public PolygonCollection() {
    polygons = new ArrayList<>();
  }

  public void addPolygon(Polygon polygon) {
    polygons.add(polygon);
  }

  public void removePolygon(int idx) {
    polygons.remove(idx);
  }

  public boolean isEqualPolygons(int idx, int otherIdx) {
    Polygon polygon = getPolygon(idx);
    Polygon otherPolygon = getPolygon(otherIdx);
    return polygon.equals(otherPolygon);
  }

  public boolean isOutOfBoundsIdx(int idx) {
    return idx < 0 || idx >= polygons.size();
  }

  public String getAllAsString() {
    StringJoiner sj = new StringJoiner(System.lineSeparator());
    int idx = 1;
    for (Object polygon : polygons) {
      sj.add("%s) %s".formatted(idx++, polygon.toString()));
    }
    return sj.toString();
  }

  public String getAsString(int idx) {
    return getPolygon(idx).toString();
  }

  public boolean isEmpty() {
    return polygons.isEmpty();
  }

  private Polygon getPolygon(int idx) {
    return (Polygon) polygons.get(idx);
  }
}
