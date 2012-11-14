package com.ccapps.android.hextd.datastructure;

/*****************************************************
 Garden Hex Tower Defense
 Charles Capps & Joseph Lee
 ID:  920474106
 CS 313 AI and Game Design
 Fall 2012
 *****************************************************/

//CLC: Original Code Begin
public interface PriorityQueue {
    public void insert(HeapNode n);
    public Object removeHighestPriority();
    public String print();
    public boolean contains(Object o);
    public boolean isEmpty();
}
//CLC: Original Code End
