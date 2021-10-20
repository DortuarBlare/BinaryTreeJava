import Classes.*;
import Interfaces.TypeBuilder;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        GUI gui = new GUI();
        /*int choice;
        Scanner in = new Scanner(System.in);
        System.out.print("Enter type of data, that you want to use in data structure(INTEGER or STRING): ");

        TypeBuilder builder = TypeFactory.getTypeBuilderByName(in.next());
        if (builder == null) return;

        BinaryTree<Object> binaryTree = new BinaryTree<>(builder.getComparator());

        while (true) {
            printMenu(binaryTree.getSize());
            choice = in.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter type of printing(INDEX or WEIGHT): ");
                    binaryTree.print(in.next());
                    break;
                case 2:
                    System.out.print("Enter NUMBER of elements you want to add: ");
                    int size = in.nextInt();
                    for (int i = 0; i < size; i++)
                        binaryTree.add(builder.create());
                    break;
                case 3:
                    System.out.print("Enter INDEX of node you want to find: ");
                    System.out.println(binaryTree.findByIndex(in.nextInt()));
                    break;
                case 4:
                    System.out.print("Enter INDEX of node you want to delete: ");
                    binaryTree.deleteByIndex(in.nextInt());
                    break;
                case 5:
                    //binaryTree.forEach(System.out::println);
                    // 🢁 эквиваленты 🢃
                    for (Node i : binaryTree) System.out.println(i);
                    break;
                case 6:
                    System.out.print("Enter INDEX of node for getting depth: ");
                    System.out.println(binaryTree.getDepth(binaryTree.findByIndex(in.nextInt())));
                    break;
                case 0:
                    return;
                default:
                    break;
            }
        }*/
    }

    public static void printMenu(int size) {
        System.out.println("==============================");
        System.out.print("= Tree size: " + size);
        for (int i = 0; i < 16 - String.valueOf(size).length(); i++)
            System.out.print(' ');
        System.out.println('=');
        System.out.println("= 1) Print tree              =");
        System.out.println("= 2) Add node                =");
        System.out.println("= 3) Find node by index      =");
        System.out.println("= 4) Delete node by index    =");
        System.out.println("= 5) Foreach test(println)   =");
        System.out.println("= 6) Get depth by index      =");
        System.out.println("= 0) Exit                    =");
        System.out.println("==============================");
    }
}