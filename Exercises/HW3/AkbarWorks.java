import java.util.*;

public class AkbarWorks<T extends Comparable> {
    private ArrayList<T> tArrayList = new ArrayList<>();
    private ArrayList<T> deletedtArrayList = new ArrayList<>();

    public void add(T item) {
        tArrayList.add(item);
    }

    public T getMin() throws IllegalStateException {
        IllegalStateException e = new IllegalStateException();
        if (tArrayList.size() != 0) {
            T t = tArrayList.get(0);
            for (int i = 0; i < tArrayList.size(); i++) {
                if (tArrayList.get(i).compareTo(t) < 0) {
                    t = tArrayList.get(i);
                }
            }
            deletedtArrayList.add(t);
            tArrayList.remove(t);
            return t;
        } else throw e;
    }

    public T getLast(boolean remove) throws IllegalStateException {
        IllegalStateException e = new IllegalStateException();
        if (tArrayList.size() != 0) {
            T t = tArrayList.get(tArrayList.size() - 1);
            if (remove == true) {
                deletedtArrayList.add(t);
                tArrayList.remove(t);
            }
            return t;
        } else throw e;

    }

    public T getFirst(boolean remove) throws IllegalStateException {
        IllegalStateException e = new IllegalStateException();
        if (tArrayList.size() != 0) {
            T t = tArrayList.get(0);
            if (remove == true) {
                deletedtArrayList.add(t);
                tArrayList.remove(t);
            }
            return t;
        } else throw e;

    }

    public Comparable[] getLess(T element, boolean remove) {
        ArrayList<T> arrayListLess = new ArrayList<>();
        for (int i = 0; i < tArrayList.size(); i++) {
            if (tArrayList.get(i).compareTo(element) < 0) {
                arrayListLess.add(tArrayList.get(i));
            }
        }
        if (remove == true) {
            tArrayList.removeAll(arrayListLess);
            deletedtArrayList.addAll(arrayListLess);
        }
        Comparable[] less = new Comparable[arrayListLess.size()];
        for (int i = 0; i < arrayListLess.size(); i++) {
            less[i] = arrayListLess.get(i);
        }
        return less;
    }

    public Comparable[] getRecentlyRemoved(int n) {
        ArrayList<T> arrayListLess = new ArrayList<>();
        if (n > deletedtArrayList.size()) {
            for (int i = deletedtArrayList.size() - 1; i >= 0; i--) {
                arrayListLess.add(deletedtArrayList.get(i));
            }
        } else {
            for (int i = deletedtArrayList.size() - 1; i > deletedtArrayList.size() - n - 1; i--) {
                arrayListLess.add(deletedtArrayList.get(i));
            }
        }
        Comparable[] reverseArrayList = new Comparable[arrayListLess.size()];
        for (int i = 0; i < arrayListLess.size(); i++) {
            reverseArrayList[i] = arrayListLess.get(i);
        }
        return reverseArrayList;
    }

}
