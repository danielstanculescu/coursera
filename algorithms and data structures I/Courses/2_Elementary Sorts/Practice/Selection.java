import java.util.Comparator;

public class Selection {
    public static void sort(Comparable[] a){
        int N = a.length;
        for (int i = 0; i < N; i++) {
            int min = i;
            for (int j = i+1; j < N; j++)
                if(less(a[j], a[min]))
                    min = j;
            exch(a, i , min);
        }
    }
    public static void sort(Object[] a, Comparator comparator){
        int N = a.length;
        for (int i = 0; i < N; i++) {
            int min = i;
            for (int j = i+1; j < N; j++)
                if(less(comparator,a[j], a[min]))
                    min = j;
            exch(a, i , min);
        }
    }

    private static boolean less(Comparable v, Comparable w){
        return v.compareTo(w) < 0;
    }
    private static boolean less(Comparator c, Object v, Object w){ return c.compare(v, w) < 0;}

    private static void exch(Comparable[] a, int i, int j){
        Comparable swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }
    private static void exch(Object[] a, int i, int j){
        Object swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }

    private static boolean isSorted(Comparable[] a){
        for (int i = 1; i <= a.length-1; i++)
            if(less(a[i], a[i-1]))
                return false;
        return true;
    }
}
