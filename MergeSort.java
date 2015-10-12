import java.awt.geom.Point2D;


public class MergeSort {

    private Point2D.Double[] array;
    private Point2D.Double[] temp;
    private int length;
    private int xy;

    public static Point2D.Double[] mergeSort(Point2D.Double[] points, int flip){

        Point2D.Double[] inputArr = points;
        MergeSort mms = new MergeSort();
        mms.sort(inputArr, flip);
        return inputArr;
    }

    public void sort(Point2D.Double[] inputArr, int flip) {
    	this.xy = flip;
        this.array = inputArr;
        this.length = inputArr.length;
        this.temp = new Point2D.Double[length];
        doMergeSort(0, length - 1);
    }

    private void doMergeSort(int low, int high) {

        if (low < high) {
            int middle = low + (high - low) / 2;
            doMergeSort(low, middle);
            doMergeSort(middle + 1, high);
            mergeParts(low, middle, high);
        }
    }

    private void mergeParts(int low, int middle, int high) {

        for (int i = low; i <= high; i++) {
            temp[i] = array[i];
        }
        int i = low;
        int j = middle + 1;
        int k = low;
        while (i <= middle && j <= high) {
        	if (xy==0) {
	            if (temp[i].x <= temp[j].x) {
	                array[k] = temp[i];
	                i++;
	            } else {
	                array[k] = temp[j];
	                j++;
	            }
	            k++;
        	}

        	else if (xy==1) {
        		if (temp[i].y <= temp[j].y) {
	                array[k] = temp[i];
	                i++;
	            } else {
	                array[k] = temp[j];
	                j++;
	            }
	            k++;
        	}
        }
        while (i <= middle) {
            array[k] = temp[i];
            k++;
            i++;
        }

    }
}
