import java.awt.Point;
import java.util.LinkedList;

public class Najboljsi {
	Point center = new Point();
	LinkedList<Point> chunki = new LinkedList<Point>();
	public String toString() {
		return "Chounks found: " + chunki.size() + ", Center chunk: " + center.x + ", " + center.y + ".";
	}
}
