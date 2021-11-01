package Classes;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Consumer;

public class BinaryTreeIterator implements Iterator<Node> {
    private BinaryTree binaryTree;
    private Node currentNode;
    private int size;
    private int index = 0;
    private boolean foundFirstNode;

    public BinaryTreeIterator(BinaryTree binaryTree) {
        this.binaryTree = binaryTree;
        this.size = binaryTree.getSize();
        foundFirstNode = false;
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
            if (!foundFirstNode) {
                foundFirstNode = true;
                currentNode = binaryTree.findByIndex(0);
            }
            else {
                // Если есть правый потомок, тогда либо он,
                // либо последний его левый потомок будет следующим узлом
                if (currentNode.getRightChild() != null) {
                    currentNode = currentNode.getRightChild();
                    while (currentNode.getLeftChild() != null)
                        currentNode = currentNode.getLeftChild();
                }
                else if (currentNode.getParent() != null) {
                    while (binaryTree.getComparator().compare(currentNode.getValue(), currentNode.getParent().getValue()) > 0)
                        currentNode = currentNode.getParent();

                    if (binaryTree.getComparator().compare(currentNode.getValue(), currentNode.getParent().getValue()) < 0)
                        currentNode = currentNode.getParent();
                }
            }

            index++;
            return currentNode;
        }
        finally { size--; }
    }

    public void forEach(Consumer<? super Node> action) {
        Iterator.super.forEachRemaining(action);
    }

    @Override
    public void forEachRemaining(Consumer<? super Node> action) {
        Iterator.super.forEachRemaining(action);
    }
}
