package com.ccapps.android.hextd.datastructure;

/**
 * Created with IntelliJ IDEA.
 * User: charles
 * Date: 10/21/12
 * Time: 9:51 AM
 * To change this template use File | Settings | File Templates.
 */
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
