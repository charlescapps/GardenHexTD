package com.ccapps.android.hextd.datastructure;

/*****************************************************
 Garden Hex Tower Defense
 Charles Capps & Joseph Lee
 ID:  920474106
 CS 313 AI and Game Design
 Fall 2012
 *****************************************************/

//CLC: Original Code Begin
public interface HeapNode {
    public int getValue();

    public void setLeft(HeapNode h);
    public HeapNode getLeft();

    public void setRight(HeapNode h);
    public HeapNode getRight();

    public void setParent(HeapNode p);
    public HeapNode getParent();

    public void setDepth(int d);
    public int getDepth();

    public Object getData();
}
//CLC: Original Code End
