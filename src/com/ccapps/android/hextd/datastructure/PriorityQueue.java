package com.ccapps.android.hextd.datastructure;

/**
 * Created with IntelliJ IDEA.
 * User: charles
 * Date: 10/21/12
 * Time: 9:50 AM
 * To change this template use File | Settings | File Templates.
 */
public interface PriorityQueue {
    public void insert(HeapNode n);
    public Object removeHighestPriority();
    public String print();
    public boolean contains(Object o);
    public boolean isEmpty();
}
