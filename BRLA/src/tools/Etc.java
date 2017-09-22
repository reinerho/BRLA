package tools;

import java.awt.Point;
import java.util.Arrays;
import java.util.Date;
import java.util.Vector;

public class Etc {

	public static final int VERTICAL = 0;
	public static final int HORIZONTAL = 1;

	public static Vector<String> appendVector(Vector<String> target, Vector<String> source, int direction) {
		Vector<String> out = new Vector<String>();
		if (direction == VERTICAL) {
			out = target;
			for (String s : source) {
				out.add(s);
			}
		} else {
			for (int i = 0; i < target.size(); i++) {
				out.add(target.get(i)+" "+source.get(i));
			}
		}
		return out;
	}
	
	public static Vector<String> arrayToVector(String[] s){
		return new Vector<String>(Arrays.asList(s));
	}

	public static long timestamp() {
		return new Date().getTime();
	}
	
	public static Point inc(Point p) {
		Point out = new Point(((int)p.getX())+20,((int) p.getY())+20);
		return out;
	}
	
}
