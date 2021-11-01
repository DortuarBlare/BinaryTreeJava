import Classes.GUI;

import java.util.Scanner;
import java.util.TreeMap;

public class Main {
    public static void main(String[] args) {
        GUI gui = new GUI(); // Пользовательский интерфейс

        // Тестирование на 1000 элементов
        long lastTime = System.currentTimeMillis();
        while (gui.getBinaryTree().getSize() != 200)
            gui.getBinaryTree().add(gui.getTypeBuilder().create());
        System.out.println("\nЗатрачено секунд на добавление " + gui.getBinaryTree().getSize() +  ": " + (((double) (System.currentTimeMillis() - lastTime))) / 1000);

        lastTime = System.currentTimeMillis();
        gui.getBinaryTree().forEach(System.out::println);
        System.out.println("\nЗатрачено секунд на forEach: " + (((double) (System.currentTimeMillis() - lastTime))) / 1000);

        lastTime = System.currentTimeMillis();
        for (int i = 0; i < gui.getBinaryTree().getSize(); i++)
            gui.getBinaryTree().findByIndex(i);
        System.out.println("\nЗатрачено секунд на нахождение всех узлов: " + (((double) (System.currentTimeMillis() - lastTime))) / 1000);

        //int choice;
        //Scanner in = new Scanner(System.in);

        // Интерфейс для тестирования дополнительных методов
        /*while (true) {
            printMenu(gui.getBinaryTree().getSize());
            choice = in.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter type of printing(INDEX or WEIGHT): ");
                    gui.getBinaryTree().print(in.next());
                    break;
                case 2:
                    System.out.print("Enter NUMBER of elements you want to add: ");
                    int size = in.nextInt();
                    for (int i = 0; i < size; i++)
                        gui.getBinaryTree().add(gui.getTypeBuilder().create());
                    break;
                case 3:
                    System.out.print("Enter INDEX of node you want to find: ");
                    System.out.println(gui.getBinaryTree().findByIndex(in.nextInt()));
                    break;
                case 4:
                    System.out.print("Enter INDEX of node you want to delete: ");
                    gui.getBinaryTree().deleteByIndex(in.nextInt());
                    break;
                case 5:
                    gui.getBinaryTree().forEach(System.out::println);
                    // 🢁 эквиваленты 🢃
                    //for (Node i : gui.getBinaryTree()) System.out.println(i);
                    break;
                case 6:
                    System.out.print("Enter INDEX of node for getting depth: ");
                    System.out.println(gui.getBinaryTree().getDepth(gui.getBinaryTree().findByIndex(in.nextInt())));
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