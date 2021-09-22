import java.util.Random;
import java.util.TreeSet;

public class Main {

    public static void main(String[] args) {
        Random randomGenerator = new Random();
        BinaryTree<Integer> binaryTree = new BinaryTree<>();
        //binaryTree.add("B");
        //binaryTree.add("C");
        //binaryTree.add("A");
        /*for(int i = 0; i < 25; i++)
            binaryTree.add(randomGenerator.nextInt(100) + 1);
        binaryTree.print();*/
        TreeSet<Node> treeSet = new TreeSet<>(new NodeComparator());
        treeSet.add(new Node("Y"));
        treeSet.add(new Node("B"));
        treeSet.add(new Node("C"));
        treeSet.add(new Node("R"));
        treeSet.add(new Node("T"));
        treeSet.add(new Node("X"));
        for (Node n: treeSet)
            System.out.println(n.getValue());
    }
}
