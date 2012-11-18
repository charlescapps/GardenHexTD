package com.ccapps.android.hextd.datastructure;

/*****************************************************
 Garden Hex Tower Defense
 Charles Capps & Joseph Lee
 ID:  920474106
 CS 313 AI and Game Design
 Fall 2012
 *****************************************************/

//CLC: Original Code Begin
public class PriorityQueueFast implements PriorityQueue //min heap
{
    private int MAX_CAPACITY;
    private HeapNode[] array;
    private int lastIndex;

    public PriorityQueueFast(int MAX_CAPACITY) {
        this.MAX_CAPACITY = MAX_CAPACITY;
        this.array = new HeapNode[MAX_CAPACITY];
        this.lastIndex = -1;
    }

    public void insert(HeapNode h)
    {
      if (lastIndex < 0) {
          array[++lastIndex] = h;
          return;
      }
      array[++lastIndex] = h;
      int currentIndex = lastIndex;
      int parent = parent(currentIndex);
      while (array[currentIndex].getValue() < array[parent].getValue()) {
        swap(currentIndex, parent);
        currentIndex = parent;
        parent = parent(currentIndex);
      }
    }

    private void swap(int i, int j) {
        HeapNode tmp = array[i];
        array[i] = array[j];
        array[j] = tmp;
    }

    private int left(int i) {
        return i*2 + 1;
    }

    private int right(int i) {
        return i*2 + 2;
    }

    private HeapNode getLeft(int i) {
        return array[i*2+1];
    }

    private HeapNode getRight(int i) {
        return array[i*2+2];
    }

    private HeapNode getParent(int i) {
        return array[(i-1)/2];
    }

    private int parent(int i) {
        return (i-1)/2;
    }

    public Object removeHighestPriority()
    {
        Object minValue = array[0].getData();
        array[0] = array[lastIndex--];

        int currentIndex = 0;
        while ( true ) {

            int left = left(currentIndex);
            if (left > lastIndex) {
                break;
            }
            int curVal = array[currentIndex].getValue();
            int right = right(currentIndex);
            int leftVal = array[left].getValue();
            int rightVal = right > lastIndex ? Integer.MAX_VALUE : array[right].getValue();
            int minIndex;
            if (leftVal < rightVal) {
                minIndex = left;
            }
            else {
                minIndex = right;
            }
            if (array[minIndex].getValue() < curVal) {
                swap(minIndex, currentIndex);
                currentIndex = minIndex;
            }
            else {
                break;
            }
        }

        return minValue;
    }

    public String print()
    {
        StringBuffer sb = new StringBuffer();
        if (lastIndex > 0) {
            sb.append(array[0].getValue());
        }
        for (int i = 1; i <= lastIndex; i++) {
            sb.append(", " + array[i].getValue());
        }
        return sb.toString();
    }

    public boolean contains(Object o)
    {
        for (int i = 0; i <= lastIndex; i++) {
            if (array[i].getData() == o) {
                return true;
            }
        }
        return false;
    }

    public boolean isEmpty()
    {
        return lastIndex < 0;
    }

}
//CLC: Original Code End
