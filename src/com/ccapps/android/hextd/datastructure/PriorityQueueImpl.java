package com.ccapps.android.hextd.datastructure;

/**
 * Created with IntelliJ IDEA.
 * User: charles
 * Date: 10/21/12
 * Time: 10:01 AM
 * To change this template use File | Settings | File Templates.
 */
public class PriorityQueueImpl implements PriorityQueue //min heap
{
    private HeapNode root;

    public void insert(HeapNode h)
    {
        if (root == null)
        {
            root = h;
            h.setDepth(0);
            h.setParent(null);
            return;
        }

        HeapNode temp = root;

        while (true)
        {
            if (temp.getLeft() == null)
            {
                temp.setLeft(h);
                h.setParent(temp);
                h.setDepth(0);
                incDepths(h.getParent());
                break;
            } else if (temp.getRight() == null) {
                temp.setRight(h);
                h.setParent(temp);
                h.setDepth(0);
                incDepths(h.getParent());
                break;
            }
            if (temp.getLeft().getDepth() <= temp.getRight().getDepth())
            {
                temp = temp.getLeft();
            }
            else
            {
                temp = temp.getRight();
            }

        }

        while (h.getParent() != null && h.getValue() < h.getParent().getValue())
        {
            swap(h, h.getParent());
        }
    }

    public Object removeHighestPriority()
    {
        HeapNode minValue = root;
        HeapNode last = getLast();

        if (last == root)
        {
            root = null;
            return last.getData();
        }

        //Remove last from the bottom of the tree
        if (last.getParent().getLeft() == last)
        {
            last.getParent().setLeft(null);
        }
        else
        {
            last.getParent().setRight(null);
        }

        decDepths(last.getParent());
        last.setDepth(root.getDepth());

        //Place last at top of tree
        last.setParent(null);
        last.setLeft(root.getLeft());
        last.setRight(root.getRight());
        if (root.getLeft() != null )
            root.getLeft().setParent(last);
        if (root.getRight() != null)
            root.getRight().setParent(last);
        root = last;

        HeapNode temp = root;
        int val = temp.getValue();

        while (true)
        {
            if ((temp.getLeft() == null || temp.getLeft().getValue() >= val) &&
                    (temp.getRight() == null || temp.getRight().getValue() >= val))
            {
                break;
            }

            if (temp.getLeft() == null && temp.getRight().getValue() < val)
            {
                swap(temp.getRight(), temp);
            }
            else if (temp.getRight() == null && temp.getLeft().getValue() < val)
            {
                swap(temp.getLeft(), temp);
            }
            else
            {
                if (temp.getLeft().getValue() < temp.getRight().getValue())
                {
                    swap(temp.getLeft(), temp);
                }
                else
                {
                    swap(temp.getRight(), temp);
                }
            }
        }

        return minValue.getData();
    }

    private void swap(HeapNode below, HeapNode above)
    {
        int belowDepth = below.getDepth();
        below.setDepth(above.getDepth());
        above.setDepth(belowDepth);

        below.setParent(above.getParent());

        if (above.getParent() != null)
        {
            if (above == above.getParent().getLeft())
            {
                above.getParent().setLeft(below);
            }
            else
            {
                above.getParent().setRight(below);
            }

        }
        else
        {
            root = below;
        }

        HeapNode bLeft = below.getLeft();
        HeapNode bRight = below.getRight();
        HeapNode aLeft = above.getLeft();
        HeapNode aRight = above.getRight();
        if (bLeft != null)
        {
            bLeft.setParent(above);
        }
        if (bRight != null)
        {
            bRight.setParent(above);
        }
        if (aLeft != null && aLeft != below)
        {
            aLeft.setParent(below);
        }
        if (aRight != null && aRight != below)
        {
            aRight.setParent(below);
        }

        if (below == above.getLeft())
        {
            below.setRight(above.getRight());
            below.setLeft(above);
        }
        else
        {
            below.setLeft(above.getLeft());
            below.setRight(above);
        }

        above.setLeft(bLeft);
        above.setRight(bRight);

        above.setParent(below);

    }

    private void incDepths(HeapNode h)
    {
        while (h != null)
        {
            h.setDepth(h.getDepth() + 1);
            h = h.getParent();
        }
    }

    private void decDepths(HeapNode h)
    {
        while (h != null)
        {
            h.setDepth(h.getDepth() - 1);
            h = h.getParent();
        }
    }

    public String print()
    {
        return toStringHelper(root);
    }

    private String toStringHelper(HeapNode curNode)
    {
        if (curNode == null)
        {
            return "null";
        }

        String tmp = "(" + curNode.getDepth() + ", " + curNode.getValue() + ")";

        return tmp + ", " + toStringHelper(curNode.getLeft()) + ", " + toStringHelper(curNode.getRight());
    }

    private HeapNode getLast()
    {
        return lastHelper(root);
    }

    private HeapNode lastHelper(HeapNode current) {
        if (current == null || current.getLeft() == null && current.getRight() == null)
        {
            return current;
        }

        if (current.getLeft() == null )
        {
            return lastHelper(current.getRight());
        }
        else if (current.getRight() == null)
        {
            return lastHelper(current.getLeft());
        }
        if (current.getLeft().getValue() > current.getRight().getValue())
        {
            return lastHelper(current.getLeft());
        }
        else
        {
            return lastHelper(current.getRight());
        }
    }

    public boolean contains(Object o)
    {
        return containsHelper(o, root);
    }

    private boolean containsHelper(Object o, HeapNode current)
    {
        if (current == null)
        {
            return false;
        }
        if (current.getData() == o)
        {
            return true;
        }
        return containsHelper(o, current.getLeft()) || containsHelper(o, current.getRight());

    }

    public boolean isEmpty()
    {
        return root == null;
    }

}
