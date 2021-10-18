import Classes.*;
import Interfaces.TypeBuilder;

public class Main {

    public static void main(String[] args) {
        TypeBuilder builder = TypeFactory.getTypeBuilderByName("String");
        if (builder == null) return;

        BinaryTree<Object> binaryTree = new BinaryTree<>(builder.getComparator());
        for(int i = 0; i < 5; i++)
            binaryTree.add(builder.create());
        binaryTree.print("index");

        binaryTree.forEach(System.out::println);
        // 🢁 эквиваленты 🢃
        //for(Node i : binaryTree) System.out.println(i);

        binaryTree.deleteByIndex(5);
        System.out.println("\nTree after deleting node with index 5:");
        binaryTree.print("index");
    }
}