import java.util.*;

public class CustomArrayList<E> {
    private static final int DEFAULT_CAPACITY = 10;

    private Object[] elements;

    private int size;

    private Comparator<? super E> comparator;

    public CustomArrayList() {
        this.elements = new Object[DEFAULT_CAPACITY];
    }

    public CustomArrayList(int capacity) {
        if (capacity >= 0) {
            this.elements = new Object[capacity];
        } else {
            throw new IllegalArgumentException("Capacity must be not negative.");
        }
    }

    public int size() {
        return size;
    }

    public void add(int index, E element) {
        rangeCheckForAdd(index);
        checkCapacity();
        System.arraycopy(elements, index, elements, index + 1, size - index);
        elements[index] = element;
        size++;
    }

    public void add(E element) {
        add(size, element);
    }

    private void checkCapacity() {
        if (size == elements.length) {
            grow();
        }
    }

    private void grow() {
        elements = Arrays.copyOf(elements, elements.length + elements.length / 2);
    }

    private void rangeCheckForAdd(int index) {
        if (index > size || index < 0)
            throw new IndexOutOfBoundsException("Index must be between 0 and " + size);
    }

    public void trimToSize() {
        if (size < elements.length) {
            elements = Arrays.copyOf(elements, size);
        }
    }

    @SuppressWarnings("unchecked")
    public E set(int index, E element) {
        rangeCheckForSet(index);
        E oldValue = (E) elements[index];
        elements[index] = element;
        return oldValue;
    }

    private void rangeCheckForSet(int index) {
        if (index >= size || index < 0)
            throw new IndexOutOfBoundsException("Index must be between 0 and " + (size - 1));
    }

    public boolean addAll(Collection<? extends E> c) {
        if (c == null) {
            return false;
        }
        Object[] additionalItems = c.toArray();
        int additionalCount = additionalItems.length;
        int oldArraySize = elements.length;
        if (oldArraySize < size + additionalCount) {
            elements = Arrays.copyOf(elements, oldArraySize + additionalCount);
        }
        System.arraycopy(additionalItems, 0, elements, size, additionalCount);
        size += additionalCount;
        return true;
    }

    public void clear() {
        for (int i = 0; i < size; i++) {
            elements[i] = null;
        }
        size = 0;
    }

    @SuppressWarnings("unchecked")
    public E get(int index) {
        return (E) elements[index];
    }

    public boolean isEmpty() {
        return size == 0;
    }

    @SuppressWarnings("unchecked")
    public E remove(int index) {
        rangeCheckForSet(index);
        E oldValue = (E) elements[index];
        System.arraycopy(elements, index + 1, elements, index, size - index);
        size--;
        return oldValue;
    }

    public boolean remove(Object o) {
        for (int i = 0; i < size; i++) {
            if (elements[i].equals(o)) {
                remove(i);
                return true;
            }
        }
        return false;
    }

    public Object[] toArray() {
        return Arrays.copyOf(elements, size);
    }

    public void sort(Comparator<? super E> c) {
        this.comparator = c;
        elements = this.mergeSort(this.toArray());
    }

    private Object[] mergeSort(Object[] items) {
        if (items.length < 2) {
            return items;
        }
        Object[] left = new Object[items.length / 2 + items.length % 2];
        Object[] right = new Object[items.length / 2];
        System.arraycopy(items, 0, left, 0, items.length / 2 + items.length % 2);
        System.arraycopy(items, items.length / 2 + items.length % 2, right, 0, items.length / 2);
        left = mergeSort(left);
        right = mergeSort(right);
        return merge(left, right);
    }

    @SuppressWarnings("unchecked")
    private Object[] merge(Object[] left, Object[] right) {
        System.out.println(Arrays.toString(left));
        System.out.println(Arrays.toString(right));
        int leftIndex = 0;
        int rightIndex = 0;
        int itemIndex = 0;
        Object[] items = new Object[left.length + right.length];
        while (true) {
            int result = 0;
            if((leftIndex + rightIndex) == (left.length + right.length)) {
                break;
            }
            if(leftIndex == left.length) {
                result = 1;
            } else if(rightIndex == right.length) {
                result = -1;
            } else {
                result = comparator.compare((E) left[leftIndex], (E) right[rightIndex]);
            }
            if (result > 0) {
                items[itemIndex] = right[rightIndex];
                itemIndex++;
                rightIndex++;
            } else if (result < 0) {
                items[itemIndex] = left[leftIndex];
                itemIndex++;
                leftIndex++;
            } else {
                items[itemIndex] = left[leftIndex];
                itemIndex++;
                leftIndex++;

                items[itemIndex] = right[rightIndex];
                itemIndex++;
                rightIndex++;
            }
        }
        return items;
    }

    @Override
    public String toString() {
        return "CustomArrayList{" +
                "elements=" + Arrays.toString(elements) +
                ", size=" + size +
                '}';
    }
}