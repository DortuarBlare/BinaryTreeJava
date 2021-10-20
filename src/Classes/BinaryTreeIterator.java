package Classes;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Consumer;

public class BinaryTreeIterator implements Iterator<Node> {
    private BinaryTree binaryTree;
    private Node currentNode;
    private int size;
    private int index = 0;

    public BinaryTreeIterator(BinaryTree binaryTree) {
        this.binaryTree = binaryTree;
        this.size = binaryTree.getSize();
    }

    @Override
    public boolean hasNext() {
        if (binaryTree.findByIndex(index) != null) return true;
        return false;
    }

    @Override
    public Node next() throws NoSuchElementException {
        if (size <= 0) throw new NoSuchElementException("No more nodes in tree!");
        try {
            currentNode = binaryTree.findByIndex(index++);
            return currentNode;
        }
        finally { size--; }
    }

    @Override
    public void forEachRemaining(Consumer<? super Node> action) {
        Iterator.super.forEachRemaining(action);
    }
}
