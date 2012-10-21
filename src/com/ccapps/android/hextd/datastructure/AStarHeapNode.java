package com.ccapps.android.hextd.datastructure;

import com.ccapps.android.hextd.draw.Hexagon;

/**
 * Created with IntelliJ IDEA.
 * User: charles
 * Date: 10/21/12
 * Time: 9:54 AM
 * To change this template use File | Settings | File Templates.
 */
public class AStarHeapNode implements HeapNode {
    private HeapNode left;
    private HeapNode right;
    private HeapNode parent;
    private AStarNode data;
    private int depth;

    public AStarHeapNode(AStarNode data) {
        this.data = data;
    }

    @Override
    public int getValue() {
        return data.getTotalCost();  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void setLeft(HeapNode h) {
        this.left = h;
    }

    @Override
    public HeapNode getLeft() {
        return left;
    }

    @Override
    public void setRight(HeapNode h) {
        this.right = h;
    }

    @Override
    public HeapNode getRight() {
        return right;
    }

    @Override
    public void setParent(HeapNode p) {
        this.parent = p;
    }

    @Override
    public HeapNode getParent() {
        return parent;
    }

    @Override
    public void setDepth(int d) {
        this.depth = d;
    }

    @Override
    public int getDepth() {
        return depth;
    }

    @Override
    public Object getData() {
        return data;
    }

}
