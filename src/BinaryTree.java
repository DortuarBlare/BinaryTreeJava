import java.util.Comparator;
import java.util.Iterator;
import java.util.Stack;

public class BinaryTree<Type> implements Iterable<Node> {
    private Node<Type> root;
    private NodeComparator nodeComparator;

    public BinaryTree() {
        root = null;
        nodeComparator = new NodeComparator();
    }

    public void add(Type value) {
        Node newNode = new Node(value);
        if (root == null) root = newNode;
        else if (findByValue(value) == null) {
            root.setWeight(root.getWeight() + 1);
            Node currentNode = root;
            Node parentNode;
            while (true) {
                parentNode = currentNode;
                if (newNode.compareTo(currentNode) < 0) {   // движение влево?
                    currentNode = currentNode.getLeftChild();
                    if (currentNode == null) { // если был достигнут конец цепочки,
                        parentNode.setLeftChild(newNode); //  вставить слева
                        parentNode.getLeftChild().setParent(parentNode); // Назначить родителя
                        return;
                    }
                    else currentNode.setWeight(currentNode.getWeight() + 1);
                }
                else { // Или направо?
                    currentNode = currentNode.getRightChild();
                    if (currentNode == null) { // если был достигнут конец цепочки,
                        parentNode.setRightChild(newNode);  // вставить справа
                        parentNode.getRightChild().setParent(parentNode); // Назначить родителя
                        return;
                    }
                    else currentNode.setWeight(currentNode.getWeight() + 1);
                }
            }
        }
    }

    public Node findByValue(Type value) {
        Node nodeWithComparableValue = new Node(value);
        Node currentNode = root;
        while (value != currentNode.getValue()) {
            if (nodeWithComparableValue.compareTo(currentNode) < 0) currentNode = currentNode.getLeftChild();
            else currentNode = currentNode.getRightChild();

            if(currentNode == null) return null;
        }
        return currentNode;
    }

    public Node findByIndex(int index) {
        Node currentNode = root;
        int currentIndex = (currentNode.getLeftChild() != null ? currentNode.getLeftChild().getWeight() : 0);

        while (index != currentIndex) {
            if (index < currentIndex) {
                currentNode = currentNode.getLeftChild();
                if (currentNode == null) return null;
                currentIndex -= (currentNode.getRightChild() != null ? currentNode.getRightChild().getWeight() : 0) + 1;
            }
            else {
                currentNode = currentNode.getRightChild();
                if (currentNode == null) return null;
                currentIndex += (currentNode.getLeftChild() != null ? currentNode.getLeftChild().getWeight() : 0) + 1;
            }
        }
        return currentNode;
    }

    public void deleteByIndex(int index) {
        root.setWeight(root.getWeight() - 1);
        Node currentNode = root;
        int currentIndex = (currentNode.getLeftChild() != null ? currentNode.getLeftChild().getWeight() : 0);
        boolean isLeftChild = true;

        while (index != currentIndex) { // Поиск удаляемого узла с заданным индексом
            if (index < currentIndex) {
                isLeftChild = true;
                currentNode = currentNode.getLeftChild();
                if (currentNode == null) return;
                currentIndex -= (currentNode.getRightChild() != null ? currentNode.getRightChild().getWeight() : 0) + 1;
            }
            else {
                isLeftChild = false;
                currentNode = currentNode.getRightChild();
                if (currentNode == null) return;
                currentIndex += (currentNode.getLeftChild() != null ? currentNode.getLeftChild().getWeight() : 0) + 1;
            }
            currentNode.setWeight(currentNode.getWeight() - 1);
        }

        if (currentNode.getLeftChild() == null && currentNode.getRightChild() == null) { // Если у узла нет потомков
            if (currentNode == root) root = null;
            else if (isLeftChild) currentNode.getParent().setLeftChild(null);
            else currentNode.getParent().setRightChild(null);
        }
        else if (currentNode.getRightChild() == null) { // Если у узла нет правого потомка(замена левым поддеревом)
            if (currentNode == root) root = currentNode.getLeftChild();
            else if (isLeftChild) currentNode.getParent().setLeftChild(currentNode.getLeftChild());
            else currentNode.getParent().setRightChild(currentNode.getLeftChild());
        }
        else if (currentNode.getLeftChild() == null) { // Если у узла нет левого потомка(замена правым поддеревом)
            if (currentNode == root) root = currentNode.getRightChild();
            else if (isLeftChild) currentNode.getParent().setLeftChild(currentNode.getRightChild());
            else currentNode.getParent().setRightChild(currentNode.getRightChild());
        }
        else { // Если у узла два потомка
            Node heir = findHeir(currentNode);
            if (currentNode == root) {
                heir.setWeight(root.getWeight());
                root = heir;
            }
            else if (isLeftChild) currentNode.getParent().setLeftChild(heir);
            else currentNode.getParent().setRightChild(heir);
        }
    }

    public Node<Type> findHeir(Node nodeThatNeedHeir) {
        Node heir = nodeThatNeedHeir.getRightChild() != null ? nodeThatNeedHeir.getRightChild() : nodeThatNeedHeir;

        while (heir.getLeftChild() != null) {
            heir.setWeight(heir.getWeight() - 1);
            heir = heir.getLeftChild();
        }

        if (heir != nodeThatNeedHeir.getRightChild()) { // Если наследник не правый потомок
            heir.getParent().setLeftChild(heir.getRightChild());
            heir.setLeftChild(nodeThatNeedHeir.getLeftChild());
            heir.setRightChild(nodeThatNeedHeir.getRightChild());
        }
        else heir.setLeftChild(nodeThatNeedHeir.getLeftChild());
        return heir;
    }

    public void print(String indexOrWeight) { // Вывод дерева с весом узлов в консоль
        Stack globalStack = new Stack(); // общий стек для значений дерева
        globalStack.push(root);
        int gaps = 64; // начальное значение расстояния между элементами
        boolean isRowEmpty = false;
        String separator = "-----------------------------------------------------------------";
        System.out.print(separator);
        System.out.println(separator);

        while (isRowEmpty == false) {
            Stack localStack = new Stack(); // локальный стек для задания потомков элемента
            isRowEmpty = true;

            for (int j = 0; j < gaps; j++) System.out.print(' ');

            while (globalStack.isEmpty() == false) { // покуда в общем стеке есть элементы
                Node temp = (Node) globalStack.pop(); // берем следующий, при этом удаляя его из стека
                if (temp != null) {
                    if (indexOrWeight == "index") System.out.print(temp.getValue() + "(" + getIndex(temp) + ")");
                    else System.out.print(temp.getValue() + "(" + temp.getWeight() + ")");

                    localStack.push(temp.getLeftChild()); // соохраняем в локальный стек, наследники текущего элемента
                    localStack.push(temp.getRightChild());
                    if (temp.getLeftChild() != null || temp.getRightChild() != null) isRowEmpty = false;
                }
                else {
                    System.out.print("__");// - если элемент пустой
                    localStack.push(null);
                    localStack.push(null);
                }
                for (int j = 0; j < gaps * 2 - 2; j++) System.out.print(' ');
            }
            System.out.println();
            gaps /= 2;// при переходе на следующий уровень расстояние между элементами каждый раз уменьшается
            while (localStack.isEmpty() == false)
                globalStack.push(localStack.pop()); // перемещаем все элементы из локального стека в глобальный
        }
        System.out.print(separator);
        System.out.println(separator);
    }

    public int getIndex(Node nodeForIndex) {
        Node currentNode = root;
        int currentIndex = (currentNode.getLeftChild() != null ? currentNode.getLeftChild().getWeight() : 0);

        while (nodeForIndex.getValue() != currentNode.getValue()) {
            if (nodeForIndex.compareTo(currentNode) < 0) {
                currentNode = currentNode.getLeftChild();
                currentIndex -= (currentNode.getRightChild() != null ? currentNode.getRightChild().getWeight() : 0) + 1;
            }
            else {
                currentNode = currentNode.getRightChild();
                currentIndex += (currentNode.getLeftChild() != null ? currentNode.getLeftChild().getWeight() : 0) + 1;
            }

            if(currentNode == null) return -1;
        }
        return currentIndex;
    }

    public Node<Type> getRoot() { return this.root; }

    public int getSize() { return root.getWeight(); }

    @Override
    public Iterator<Node> iterator() {
        return new BinaryTreeIterator(this);
    }

}
