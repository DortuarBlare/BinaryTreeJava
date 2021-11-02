package Classes;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Consumer;

public class BinaryTreeIterator implements Iterator<Node> {
    private BinaryTree binaryTree;
    private Node nextNode;
    private Object currentNodeValue;
    private int size;
    private int index = 0;
    private int hasNextNumber = 0;
    private boolean foundFirstNode;

    public BinaryTreeIterator(BinaryTree binaryTree) {
        this.binaryTree = binaryTree;
        this.size = binaryTree.getSize();
        foundFirstNode = false;
    }

    @Override
    public boolean hasNext() {
        if (size == 0) return false;
        if (!foundFirstNode) {
            foundFirstNode = true;
            nextNode = binaryTree.findByIndex(0);
            hasNextNumber++;
            return true;
        }
        else {
            currentNodeValue = nextNode.getValue();

            // Если есть правый потомок, тогда либо он,
            // либо последний его левый потомок будет следующим узлом
            if (nextNode.getRightChild() != null) {
                nextNode = nextNode.getRightChild();
                while (nextNode.getLeftChild() != null)
                    nextNode = nextNode.getLeftChild();

                if (binaryTree.getComparator().compare(currentNodeValue, nextNode.getValue()) < 0) {
                    //hasNextNumber++;
                    return true;
                }
            }
            // Иначе поднимаемся по родителям пока не найдем узел с бОльшим значением
            else if (nextNode.getParent() != null) {
                while (binaryTree.getComparator().compare(currentNodeValue, nextNode.getParent().getValue()) > 0) {
                    nextNode = nextNode.getParent();
                    if (nextNode.getParent() == null) break;
                }
                if (nextNode.getParent() != null) {
                    if (binaryTree.getComparator().compare(currentNodeValue, nextNode.getParent().getValue()) < 0) {
                        nextNode = nextNode.getParent();
                        //hasNextNumber++;
                        return true;
                    }
                }
            }
        }
        //System.out.println("HasNextNumber: " + hasNextNumber);

        return false;

        /*if (binaryTree.findByIndex(index) != null) return true;
        return false;*/
    }

    @Override
    public Node next() throws NoSuchElementException {
        if (size <= 0) throw new NoSuchElementException("No more nodes in tree!");
        try {
            /*if (!foundFirstNode) {
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
                // Иначе поднимаемся по родителям пока не найдем узел с бОльшим значением
                else if (currentNode.getParent() != null) {
                    while (binaryTree.getComparator().compare(currentNode.getValue(), currentNode.getParent().getValue()) > 0)
                        currentNode = currentNode.getParent();

                    if (binaryTree.getComparator().compare(currentNode.getValue(), currentNode.getParent().getValue()) < 0) {
                        currentNode = currentNode.getParent();
                    }
                }
            }

            return currentNode;*/

            return nextNode;

            //return binaryTree.findByIndex(index++);
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
