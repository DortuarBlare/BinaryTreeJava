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
}