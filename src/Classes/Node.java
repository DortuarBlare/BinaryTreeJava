package Classes;

import java.io.Serializable;

public class Node<Type> implements Serializable {
    private Type value; // Значение (содержимое) узла
    private int weight; // Вес узла (По умолчанию у всех равен 1)
    private Node parent; // Узел родитель
    private Node leftChild; // Левый узел потомок
    private Node rightChild; // Правый узел потомок

    public Node(Type value) {
        this.value = value;
        this.weight = 1;
        parent = leftChild = rightChild = null;
    }

    public Type getValue() {
        return this.value;
    }

    public void setValue(final Type value) {
        this.value = value;
    }

    public int getWeight() { return this.weight; }

    public void setWeight(int weight) { this.weight = weight; }

    public Node<Type> getParent() { return this.parent; }

    public void setParent(final Node<Type> parent) { this.parent = parent; }

    public Node<Type> getLeftChild() {
        return this.leftChild;
    }

    public void setLeftChild(final Node<Type> leftChild) {
        this.leftChild = leftChild;
    }

    public Node<Type> getRightChild() {
        return this.rightChild;
    }

    public void setRightChild(final Node<Type> rightChild) {
        this.rightChild = rightChild;
    }

    @Override
    public String toString() {
        return "Classes.Node {" +
                "value = " + value +
                ", weight = " + weight +
                ", parent = " + (parent != null ? parent.getValue() : "null") +
                ", leftChild = " + (leftChild != null ? leftChild.getValue() : "null") +
                ", rightChild = " + (rightChild != null ? rightChild.getValue() : "null") +
                '}';
    }
}