import java.util.Iterator;
import java.util.function.Consumer;

public class BinaryTreeIterator implements Iterator<Node> {
    private Node currentNode;
    private int size;

    public BinaryTreeIterator(Node currentNode, int size) {
        this.currentNode = currentNode;
        this.size = size;
    }

    @Override
    public boolean hasNext() {

        return false;
    }

    @Override
    public Node next() {
        return null;
    }

    @Override
    public void forEachRemaining(Consumer<? super Node> action) {
        Iterator.super.forEachRemaining(action);
    }
}
