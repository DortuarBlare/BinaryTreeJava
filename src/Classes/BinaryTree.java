package Classes;

import java.io.*;
import java.util.Iterator;
import java.util.Stack;
import java.util.function.Consumer;

/**
 * Структура данных двоичное дерево.
 * Индексы в узах не хранятся явно.
 * Вместо индексов хранится вес узла(количество потомков + 1).
 * По умолчанию у всех узлов вес = 1.
 * На основе веса узла вычисляется его индекс
 */
public class BinaryTree<Type> implements Iterable<Node>, Serializable {
    private Node<Type> root;
    private transient Interfaces.Comparator comparator;

    public BinaryTree() {
        root = null;
        comparator = null;
    }

    public BinaryTree(Interfaces.Comparator comparator) {
        root = null;
        this.comparator = comparator;
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
                if (comparator.compare(value, currentNode.getValue()) < 0) {   // Двигаемся влево
                    currentNode = currentNode.getLeftChild();
                    if (currentNode == null) { //Конец цепочки
                        parentNode.setLeftChild(newNode);
                        parentNode.getLeftChild().setParent(parentNode);
                        return;
                    }
                    else currentNode.setWeight(currentNode.getWeight() + 1);
                }
                else { // Двигаемся вправо
                    currentNode = currentNode.getRightChild();
                    if (currentNode == null) { // Конец цепочки
                        parentNode.setRightChild(newNode);
                        parentNode.getRightChild().setParent(parentNode);
                        break;
                    }
                    else currentNode.setWeight(currentNode.getWeight() + 1);
                }
            }
        }
        balance(newNode);
    }

    /**
     * Балансировка поворотом на основе весов левого и правого поддерева
     */
    public void balance(Node node) {
        if (node == null) return;

        Node child = null;
        Node parent = null;
        int leftDepth, rightDepth;

        while (true) {
            leftDepth = getDepth(node.getLeftChild());
            rightDepth = getDepth(node.getRightChild());

            if (leftDepth > rightDepth && leftDepth - rightDepth > 1) {
                // Правый поворот, так как глубина левого поддерева больше

                child = node.getLeftChild();
                parent = node.getParent();

                if (parent != null) {
                    if (parent.getRightChild() == node)
                        parent.setRightChild(child);
                    else if (parent.getLeftChild() == node)
                        parent.setLeftChild(child);
                }
                else root = child;

                child.setParent(parent);
                node.setParent(child);

                node.setLeftChild(child.getRightChild());
                if (node.getLeftChild() != null)
                    node.getLeftChild().setParent(node);

                child.setRightChild(node);

                node.setWeight(1 + (node.getLeftChild() != null ? node.getLeftChild().getWeight() : 0) +
                        (node.getRightChild() != null ? node.getRightChild().getWeight() : 0));
                child.setWeight(1 + (child.getLeftChild() != null ? child.getLeftChild().getWeight() : 0) +
                        (child.getRightChild() != null ? child.getRightChild().getWeight() : 0));

                node = child;
            }
            else if (rightDepth > leftDepth && rightDepth - leftDepth > 1) {
                // Левый поворот, так как глубина правого поддерева больше

                child = node.getRightChild();
                parent = node.getParent();

                if (parent != null) {
                    if (parent.getRightChild() == node)
                        parent.setRightChild(child);
                    else if (parent.getLeftChild() == node)
                        parent.setLeftChild(child);
                }
                else root = child;

                child.setParent(parent);
                node.setParent(child);

                node.setRightChild(child.getLeftChild());
                if (node.getRightChild() != null)
                    node.getRightChild().setParent(node);

                child.setLeftChild(node);

                node.setWeight(1 + (node.getLeftChild() != null ? node.getLeftChild().getWeight() : 0) +
                        (node.getRightChild() != null ? node.getRightChild().getWeight() : 0));
                child.setWeight(1 + (child.getLeftChild() != null ? child.getLeftChild().getWeight() : 0) +
                        (child.getRightChild() != null ? child.getRightChild().getWeight() : 0));

                node = child;
            }
            if (node.getParent() != null)
                node = node.getParent();
            else break;
        }
    }

    public Node findByValue(Type value) {
        Node currentNode = root;
        while (value != currentNode.getValue()) {
            if (comparator.compare(value, currentNode.getValue()) < 0) currentNode = currentNode.getLeftChild();
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

    /**
     * Аналог forEach у итератора.
     * Действие, которое производится над всеми элементами передается в виде параметра
     */
    public void forEach(Consumer<? super Node> action) {
        new BinaryTreeIterator(this).forEach(action);
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
        System.out.println("Удаляемый узел: " + currentNode);

        if (currentNode.getLeftChild() == null && currentNode.getRightChild() == null) { // Если у узла нет потомков
            if (currentNode == root) root = null;
            else if (isLeftChild) currentNode.getParent().setLeftChild(null);
            else currentNode.getParent().setRightChild(null);
        }
        else if (currentNode.getRightChild() == null) { // Если у узла нет правого потомка(замена левым поддеревом)
            if (currentNode == root) root = currentNode.getLeftChild();
            else if (isLeftChild) currentNode.getParent().setLeftChild(currentNode.getLeftChild());
            else currentNode.getParent().setRightChild(currentNode.getLeftChild());

            currentNode.getLeftChild().setParent(currentNode.getParent());
        }
        else if (currentNode.getLeftChild() == null) { // Если у узла нет левого потомка(замена правым поддеревом)
            if (currentNode == root) root = currentNode.getRightChild();
            else if (isLeftChild) currentNode.getParent().setLeftChild(currentNode.getRightChild());
            else currentNode.getParent().setRightChild(currentNode.getRightChild());

            currentNode.getRightChild().setParent(currentNode.getParent());
        }
        else { // Если у узла два потомка
            Node heir = findHeir(currentNode);
            System.out.println("Преемник удаляемого узла: " + heir);
            if (currentNode == root) root = heir;
            else if (isLeftChild) currentNode.getParent().setLeftChild(heir);
            else currentNode.getParent().setRightChild(heir);
        }

        currentNode.setLeftChild(null);
        currentNode.setRightChild(null);
        currentNode = null;
    }

    /**
     * Поиск преемника для удаляемого узла с двумя потомками
     */
    public Node<Type> findHeir(Node nodeThatNeedHeir) {
        Node heir = nodeThatNeedHeir.getRightChild() != null ? nodeThatNeedHeir.getRightChild() : nodeThatNeedHeir;

        while (heir.getLeftChild() != null) {
            heir.setWeight(heir.getWeight() - 1);
            heir = heir.getLeftChild();
        }

        if (heir == nodeThatNeedHeir.getRightChild()) { // Если наследник правый потомок
            heir.setLeftChild(nodeThatNeedHeir.getLeftChild());
        }
        else {
            heir.getParent().setLeftChild(heir.getRightChild());
            heir.setLeftChild(nodeThatNeedHeir.getLeftChild());
            heir.setRightChild(nodeThatNeedHeir.getRightChild());
            heir.getLeftChild().setParent(heir);
            heir.getRightChild().setParent(heir);
        }
        heir.setParent(nodeThatNeedHeir.getParent()); // Меняем родительскую связь

        heir.setWeight(nodeThatNeedHeir.getWeight());

        return heir;
    }

    public void print(String indexOrWeight) { // Вывод дерева с весом узлов в консоль
        Stack globalStack = new Stack(); // общий стек для значений дерева
        globalStack.push(root);
        int gaps = 128;
        boolean isRowEmpty = false;
        System.out.println();

        while (isRowEmpty == false) {
            Stack localStack = new Stack(); // локальный стек для задания потомков элемента
            isRowEmpty = true;

            for (int i = 0; i < gaps; i++) System.out.print(' ');

            while (globalStack.isEmpty() == false) { // Пока в общем стеке есть элементы
                Node temp = (Node) globalStack.pop(); // Берем элемент, удаляя его из стека
                if (temp != null) {
                    if (indexOrWeight.toLowerCase().compareTo("index") == 0)
                        System.out.print(temp.getValue() + "(" + getIndex(temp) + ")");
                    else System.out.print(temp.getValue() + "(" + temp.getWeight() + ")");

                    localStack.push(temp.getLeftChild()); // соохраняем в локальный стек, наследники текущего элемента
                    localStack.push(temp.getRightChild());
                    if (temp.getLeftChild() != null || temp.getRightChild() != null) isRowEmpty = false;
                }
                else {
                    System.out.print("__"); // - если элемент пустой
                    localStack.push(null);
                    localStack.push(null);
                }
                for (int i = 0; i < gaps * 2 - 2; i++) System.out.print(' ');
            }
            System.out.println();
            gaps /= 2;// при переходе на следующий уровень расстояние между элементами каждый раз уменьшается
            while (localStack.isEmpty() == false)
                globalStack.push(localStack.pop()); // перемещаем все элементы из локального стека в глобальный
        }
        System.out.println('\n');
    }

    public int getIndex(Node nodeForIndex) {
        Node currentNode = root;
        int currentIndex = (currentNode.getLeftChild() != null ? currentNode.getLeftChild().getWeight() : 0);

        while (nodeForIndex.getValue() != currentNode.getValue()) {
            if (comparator.compare(nodeForIndex.getValue(), currentNode.getValue()) < 0) {
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

    public int getDepth(Node nodeForDepth) {
        int resultDepth = 0;

        if (nodeForDepth != null) {
            int leftDepth = getDepth(nodeForDepth.getLeftChild());
            int rightDepth = getDepth(nodeForDepth.getRightChild());
            resultDepth = Math.max(leftDepth, rightDepth) + 1;
        }
        return resultDepth;
    }

    public Node<Type> getRoot() { return this.root; }

    public int getSize() { return root == null ? 0 : root.getWeight(); }

    public void setComparator(Interfaces.Comparator comparator) {
        this.comparator = comparator;
    }

    @Override
    public Iterator<Node> iterator() {
        return new BinaryTreeIterator(this);
    }
}