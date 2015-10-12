import java.awt.geom.Point2D;
import java.util.ArrayList;
public class PointSet {

	public static String closestPair(Point2D.Double[] points){
		if (points.length <= 1) {
			return "Sorry, there are not enough points";
		}

		PointPair closest = new PointPair(points[0],points[1]);
		if (points.length == 2) {
			return closest.toString();
		}
		else if (points.length == 3) {
			return bruteForce(points).toString();
		}
		else {
			Point2D.Double[] xsorted = MergeSort.mergeSort(points, 0);
			Point2D.Double[] ysorted = MergeSort.mergeSort(points, 1);
			closest = cp(xsorted, ysorted);
		}
		closest = closest.normalize();
		return closest.toString();


	}

	public static PointPair cp(Point2D.Double[] left, Point2D.Double[] right) {
		PointPair closestleft = new PointPair(left[0], left[1]);
		PointPair closestright = new PointPair(right[0], right[1]);;
		PointPair closest = closestleft;
		if (left.length <= 3) {
			return bruteForce(left);
		}

		else {

			int split = left.length/2;
			Point2D.Double[] xleft = new Point2D.Double[split];
			Point2D.Double[] xright = new Point2D.Double[left.length-split];
			for (int i=0; i<split; i++) {
				xleft[i] = left[i];
			}
			for (int j=split; j<left.length; j++) {
				xright[j-split] = left[j];
			}
			Point2D.Double[] yleft = MergeSort.mergeSort(xleft, 1);
			Point2D.Double[] yright = MergeSort.mergeSort(xright, 1);
			closestleft = cp(xleft, yleft);
			closestright = cp(xright, yright);

			if (closestleft.closerThan(closestright) > 0) {
				closest = closestright;
			}
			else {
				closest = closestleft;
			}

			//check middle strip
			double d = Math.abs(closest.getDistance());
			double farx = xleft[xleft.length-1].x;
			ArrayList<Point2D.Double> yprime = new ArrayList<Point2D.Double>();
			int k = split;
			double xvalue = left[k].x;
			while ((xvalue >= (farx-d)) && (k > 0)) {
				yprime.add(left[k]);
				k--;
				xvalue=left[k].x;
			}
			k=split+1;
			xvalue = left[k].x;
			while ((xvalue <= (farx+d)) && ((k+1) < left.length)) {
				yprime.add(left[k]);
				k++;
				xvalue=left[k].x;
			}

			Point2D.Double[] yprime2 = new Point2D.Double[yprime.size()];
			yprime.toArray(yprime2);
			Point2D.Double[] yprimesorted = MergeSort.mergeSort(yprime2, 1);
			for (int l=0; l<yprimesorted.length-1; l++) {
				double yvalue = yprimesorted[l].y;
				int m=l+1;
				double check = yprimesorted[m].y;

				while ((yvalue < (check + d)) && (m < (yprimesorted.length-1))) {
					PointPair p = new PointPair(yprimesorted[l],yprimesorted[m]);
					if (closest.closerThan(p) > 0) {
						closest = p;
						d = Math.abs(closest.getDistance());
					}
					m++;
					yvalue = yprimesorted[m].y;
				}
			}
		}
		return closest;

	}

	public static PointPair bruteForce(Point2D.Double[] points) {
		PointPair closest = new PointPair(points[0], points[1]);
		if (points.length==2) {
			return closest;
		}
		else {

			PointPair p = new PointPair(points[1],points[2]);

			if (closest.closerThan(p) > 0) {
				closest = p;
			}

			PointPair q = new PointPair(points[0],points[2]);

			if (closest.closerThan(q) > 0) {
				closest = q;
			}

		}
		return closest;
	}

}
