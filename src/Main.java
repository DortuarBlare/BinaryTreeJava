import Classes.GUI;

public class Main {
    public static void main(String[] args) {
        GUI gui = new GUI(); // Пользовательский интерфейс
        gui.setVisible(false);

        gui.binaryTreeTest(1000);
        gui.binaryTreeTest(5000);
        gui.binaryTreeTest(10000);
        gui.binaryTreeTest(25000);
        gui.binaryTreeTest(50000);
        gui.updateNodesListText();
        gui.setVisible(true);

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