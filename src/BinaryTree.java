import java.util.Comparator;
import java.util.Stack;

public class BinaryTree<Type> {
    private Node<Type> root;
    private NodeComparator nodeComparator;

    public BinaryTree() {
        root = null;
        nodeComparator = new NodeComparator();
    }

    public void add(Type value) { // метод вставки нового элемента
        Node newNode = new Node(value); // создание нового узла
        if (root == null) root = newNode; // Если нет корня
        else {
            Node currentNode = root; // Начинаем с корневого узла
            Node parentNode;
            while (true) {
                parentNode = currentNode;
                if (value == currentNode.getValue()) return;    // Если уже есть элемент с таким значением
                else if (newNode.compareTo(currentNode) < 0) {   // движение влево?
                    currentNode = currentNode.getLeftChild();
                    if (currentNode == null) { // если был достигнут конец цепочки,
                        parentNode.setLeftChild(newNode); //  то вставить слева и выйти из методы
                        return;
                    }
                }
                else { // Или направо?
                    currentNode = currentNode.getRightChild();
                    if (currentNode == null) { // если был достигнут конец цепочки,
                        parentNode.setRightChild(newNode);  //то вставить справа
                        return; // и выйти
                    }
                }
            }
        }
    }

    /*public Node find(Type value) {
        Node currentNode = root;
        //while (value != currentNode.getValue()) {
            //if (value > currentNode.getValue())
        //}
    }*/

    public void print() { // метод для вывода дерева в консоль
        Stack globalStack = new Stack(); // общий стек для значений дерева
        globalStack.push(root);
        int gaps = 90; // начальное значение расстояния между элементами
        boolean isRowEmpty = false;
        String separator = "-----------------------------------------------------------------";
        System.out.println(separator);// черта для указания начала нового дерева
        while (isRowEmpty == false) {
            Stack localStack = new Stack(); // локальный стек для задания потомков элемента
            isRowEmpty = true;

            for (int j = 0; j < gaps; j++)
                System.out.print(' ');
            while (globalStack.isEmpty() == false) { // покуда в общем стеке есть элементы
                Node temp = (Node) globalStack.pop(); // берем следующий, при этом удаляя его из стека
                if (temp != null) {
                    System.out.print(temp.getValue()); // выводим его значение в консоли
                    localStack.push(temp.getLeftChild()); // соохраняем в локальный стек, наследники текущего элемента
                    localStack.push(temp.getRightChild());
                    if (temp.getLeftChild() != null ||
                            temp.getRightChild() != null)
                        isRowEmpty = false;
                }
                else {
                    System.out.print("__");// - если элемент пустой
                    localStack.push(null);
                    localStack.push(null);
                }
                for (int j = 0; j < gaps * 2 - 2; j++)
                    System.out.print(' ');
            }
            System.out.println();
            gaps /= 2;// при переходе на следующий уровень расстояние между элементами каждый раз уменьшается
            while (localStack.isEmpty() == false)
                globalStack.push(localStack.pop()); // перемещаем все элементы из локального стека в глобальный
        }
        System.out.println(separator);// подводим черту
    }
}
