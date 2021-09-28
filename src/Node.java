public class Node<Type> implements Comparable<Node> {
    private Type value; // Значение (содержимое) узла
    private int weight = 1; // Вес узла (По умолчанию у всех равен 1)
    private Node parent; // Узел родитель
    private Node leftChild; // Левый узел потомок
    private Node rightChild; // Правый узел потомок

    public Node(Type value) {
        this.value = value;
    }

    public void printNode() { // Вывод значения узла в консоль
        System.out.println(" Выбранный узел имеет значение :" + value);
    }

    public Type getValue() {
        return this.value;
    }

    public void setValue(final Type value) {
        this.value = value;
    }

    public int getWeight() { return this.weight; }

    public void setWeight(int weight) { this.weight = weight; }

    public Node getParent() { return this.parent; }

    public void setParent(final Node parent) { this.parent = parent; }

    public Node getLeftChild() {
        return this.leftChild;
    }

    public void setLeftChild(final Node leftChild) {
        this.leftChild = leftChild;
    }

    public Node getRightChild() {
        return this.rightChild;
    }

    public void setRightChild(final Node rightChild) {
        this.rightChild = rightChild;
    }

    @Override
    public String toString() {
        return "Node {" +
                "value = " + value +
                ", weight = " + weight +
                ", parent = " + (parent != null ? parent.getValue() : "null") +
                ", leftChild = " + (leftChild != null ? leftChild.getValue() : "null") +
                ", rightChild = " + (rightChild != null ? rightChild.getValue() : "null") +
                '}';
    }

    @Override
    public int compareTo(Node o) { // 0 - Объекты равны, <0 - меньше, >0 - больше
        if (this.value instanceof Integer) {
            if (this.value == o.value) return 0;
            else if ((int) this.value < (int) o.value) return -1;
            else if ((int) this.value > (int) o.value) return 1;
        }
        if (this.value instanceof String) return this.value.toString().compareTo(o.value.toString());
        return 0;
    }
}