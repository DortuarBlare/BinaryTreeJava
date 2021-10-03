import java.util.Random;
import java.util.TreeSet;

public class Main {

    public static void main(String[] args) {
        Random randomGenerator = new Random();
        BinaryTree<Integer> binaryTree = new BinaryTree<>();
        for (int i = 0; i < 10; i++)
            binaryTree.add(randomGenerator.nextInt(100) + 1);
        binaryTree.print("index");
        /*int rootIndex, temp;
        for(int i = 0; i < 2; i++) {
            rootIndex = binaryTree.getRoot().getLeftChild() != null ? binaryTree.getRoot().getLeftChild().getWeight() : 0;
            temp = binaryTree.getRoot().getValue();
            binaryTree.deleteByIndex(rootIndex);
            binaryTree.add(temp);
            binaryTree.print();
        }*/
    }
}