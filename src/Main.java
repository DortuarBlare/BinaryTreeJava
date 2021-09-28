import java.util.Random;

public class Main {

    public static void main(String[] args) {
        Random randomGenerator = new Random();
        BinaryTree<Integer> binaryTree = new BinaryTree<>();
        for (int i = 0; i < 10; i++)
            binaryTree.add(randomGenerator.nextInt(100) + 1);
        binaryTree.print();
        System.out.println(binaryTree.findByIndex(2));
    }
}
