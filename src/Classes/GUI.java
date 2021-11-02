package Classes;

import Interfaces.TypeBuilder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Stack;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Пользовательский графический интерфейс.
 * Функционал:
 * Выбор между двумя типами данных (целые числа или строки).
 * Сохранение структуры данных в файл и загрузка из файла.
 * Добавление элемента с вводом данных, добавление случайных элементов.
 * Удаление элемента с вводом данных.
 * Отображение индекса или веса узла над ним.
 */
public class GUI extends JFrame {
    //private ViewPanel viewPanel;
    private MenuBar menuBar;
    private JScrollPane nodesListPane;
    private JTextPane nodesListText;
    private boolean showIndexesOrWeights;
    private boolean integerOrStringType;

    //private Timer timer;
    //private TimerTask timerTask;
    private Point clickedMousePosition, releaseMousePosition;
    private TypeBuilder typeBuilder;
    private BinaryTree<Object> binaryTree;

    public GUI() {
        super("Binary Tree");
        this.setPreferredSize(new Dimension(1280, 1024));
        //this.setLayout(new BorderLayout());
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setResizable(true);
        this.setFocusable(false);
        /*this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_LEFT:
                        viewPanel.cameraPosition.x += 5;
                        break;
                    case KeyEvent.VK_RIGHT:
                        viewPanel.cameraPosition.x -= 5;
                        break;
                    case KeyEvent.VK_UP:
                        viewPanel.cameraPosition.y += 5;
                        break;
                    case KeyEvent.VK_DOWN:
                        viewPanel.cameraPosition.y -= 5;
                        break;
                }
            }
        });*/

        clickedMousePosition = new Point();
        releaseMousePosition = new Point();

        /*this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                clickedMousePosition = e.getPoint();
            }
        });
        this.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                super.mouseDragged(e);
                releaseMousePosition = e.getPoint();
                viewPanel.cameraPosition.x += releaseMousePosition.x - clickedMousePosition.x;
                viewPanel.cameraPosition.y += releaseMousePosition.y - clickedMousePosition.y;
                clickedMousePosition = releaseMousePosition;
            }
        });*/

        /*viewPanel = new ViewPanel();
        viewPanel.setPreferredSize(new Dimension(1280, 1024));
        this.add(viewPanel, BorderLayout.CENTER);
        timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                viewPanel.draw();
            }
        };*/

        menuBar = new MenuBar();
        setJMenuBar(menuBar);

        nodesListText = new JTextPane();
        nodesListText.setFont(new Font("Dialog", Font.PLAIN, 14));
        nodesListText.setEditable(false);
        nodesListPane = new JScrollPane(nodesListText);
        nodesListPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        nodesListPane.setVisible(true);
        this.add(nodesListPane, BorderLayout.CENTER);

        this.pack();
        this.setLocationRelativeTo(null);
        createDataTypeChoiceWindow();
    }

    /**
     * Создание модального окна для выбора типа данных для структуры
     */
    private void createDataTypeChoiceWindow() {
        JDialog jDialog = new JDialog(this, "Choose data type for structure: ", true);
        JPanel jPanel = new JPanel();
        jPanel.setLayout(new FlowLayout());
        jDialog.setContentPane(jPanel);
        jDialog.setPreferredSize(new Dimension(350, 200));
        jDialog.setDefaultCloseOperation(HIDE_ON_CLOSE);
        jDialog.setResizable(false);
        jDialog.setFocusable(true);

        JButton integerButton = new JButton("INTEGER");
        integerButton.setPreferredSize(new Dimension(125, 75));
        integerButton.setFont(new Font("Dialog", Font.BOLD, 16));
        JButton stringButton = new JButton("STRING");
        stringButton.setPreferredSize(new Dimension(125, 75));
        stringButton.setFont(new Font("Dialog", Font.BOLD, 16));
        JLabel someText = new JLabel("Do not recommended to add more than 5000 elements in GUI");


        integerButton.addActionListener(e -> {
            jDialog.setVisible(false);
            jDialog.dispose();
            integerOrStringType = true;
            typeBuilder = TypeFactory.getTypeBuilderByName("integer");
            if (typeBuilder == null) return;
            binaryTree = new BinaryTree<>(typeBuilder.getComparator());
            //while (binaryTree.getSize() != 5)
                //binaryTree.add(typeBuilder.create());
            jDialog.getOwner().setFocusable(true);
            //timer.schedule(timerTask, 0, 10);
        });
        stringButton.addActionListener(e -> {
            jDialog.setVisible(false);
            jDialog.dispose();
            integerOrStringType = false;
            typeBuilder = TypeFactory.getTypeBuilderByName("string");
            if (typeBuilder == null) return;
            binaryTree = new BinaryTree<>(typeBuilder.getComparator());
            //while (binaryTree.getSize() != 5)
                //binaryTree.add(typeBuilder.create());
            jDialog.getOwner().setFocusable(true);
            //timer.schedule(timerTask, 0, 10);
        });

        jPanel.add(integerButton);
        jPanel.add(stringButton);
        jPanel.add(someText);

        jDialog.pack();
        jDialog.setLocationRelativeTo(this);
        jDialog.setVisible(true);
    }

    /**
     * Создание модального окна для добавление элемента с введенными данными
     */
    private void createAddingWindow() {
        JDialog jDialog = new JDialog(this, "Enter amount of elements you want to add:", true);
        JPanel jPanel = new JPanel();
        jPanel.setLayout(new FlowLayout());
        jDialog.setContentPane(jPanel);
        jDialog.setPreferredSize(new Dimension(350, 200));
        jDialog.setDefaultCloseOperation(HIDE_ON_CLOSE);
        jDialog.setResizable(false);
        jDialog.setFocusable(true);

        JTextField dataField = new JTextField();
        dataField.setPreferredSize(new Dimension(200, 40));
        JButton addButton = new JButton("Add");
        addButton.setPreferredSize(new Dimension(150, 40));
        JButton cancelButton = new JButton("Cancel");
        cancelButton.setPreferredSize(new Dimension(150, 40));

        addButton.addActionListener(e -> {
            jDialog.setVisible(false);
            jDialog.dispose();

            int amountOfElements = Integer.parseInt(dataField.getText());
            int finalSize = binaryTree.getSize() + amountOfElements;
            while (binaryTree.getSize() != finalSize)
                binaryTree.add(typeBuilder.create());
            updateNodesListText();

            jDialog.getOwner().setFocusable(true);
        });
        cancelButton.addActionListener(e -> {
            jDialog.setVisible(false);
            jDialog.dispose();
            jDialog.getOwner().setFocusable(true);
        });

        jPanel.add(dataField);
        jPanel.add(addButton);
        jPanel.add(cancelButton);

        jDialog.pack();
        jDialog.setLocationRelativeTo(this);
        jDialog.setVisible(true);
        dataField.setFocusable(true);
    }

    /**
     * Создание модального окна для удаления элемента, хранящего введенные данные
     */
    private void createDeletingWindow() {
        JDialog jDialog = new JDialog(this, "Enter deleting index:", true);
        JPanel jPanel = new JPanel();
        jPanel.setLayout(new FlowLayout());
        jDialog.setContentPane(jPanel);
        jDialog.setPreferredSize(new Dimension(350, 200));
        jDialog.setDefaultCloseOperation(HIDE_ON_CLOSE);
        jDialog.setResizable(false);
        jDialog.setFocusable(true);

        JTextField dataField = new JTextField();
        dataField.setPreferredSize(new Dimension(200, 40));
        JButton deleteButton = new JButton("Delete");
        deleteButton.setPreferredSize(new Dimension(150, 40));
        JButton cancelButton = new JButton("Cancel");
        cancelButton.setPreferredSize(new Dimension(150, 40));

        deleteButton.addActionListener(e -> {
            jDialog.setVisible(false);
            jDialog.dispose();
            binaryTree.deleteByIndex(Integer.parseInt(dataField.getText()));
            updateNodesListText();
            jDialog.getOwner().setFocusable(true);
        });
        cancelButton.addActionListener(e -> {
            jDialog.setVisible(false);
            jDialog.dispose();
            jDialog.getOwner().setFocusable(true);
        });

        jPanel.add(dataField);
        jPanel.add(deleteButton);
        jPanel.add(cancelButton);

        jDialog.pack();
        jDialog.setLocationRelativeTo(this);
        jDialog.setVisible(true);
        dataField.setFocusable(true);
    }

    /**
     * Метод сохранения структуры данных в файл
     */
    public synchronized void save(File file) {
        ObjectOutputStream objectOutputStream = null;

        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            if (fileOutputStream != null) {
                objectOutputStream = new ObjectOutputStream(fileOutputStream);
                objectOutputStream.writeObject(binaryTree);
            }
        }
        catch (FileNotFoundException e) { e.printStackTrace(); }
        catch (IOException e) { e.printStackTrace(); }
        finally {
            if (objectOutputStream != null) {
                try { objectOutputStream.close(); }
                catch (IOException e) { e.printStackTrace(); }
            }
        }
    }

    /**
     * Метод загрузки структуры данных из файла
     */
    public synchronized void load(File file) {
        ObjectInputStream objectInputStream = null;

        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            if (fileInputStream != null) {
                objectInputStream = new ObjectInputStream(fileInputStream);
                binaryTree = (BinaryTree<Object>) objectInputStream.readObject();
                if (binaryTree.getRoot().getValue() instanceof Integer) {
                    typeBuilder = TypeFactory.getTypeBuilderByName("integer");
                    integerOrStringType = true;
                }
                else {
                    typeBuilder = TypeFactory.getTypeBuilderByName("string");
                    integerOrStringType = false;
                }
                binaryTree.setComparator(typeBuilder.getComparator());
            }
        }
        catch (FileNotFoundException e) { e.printStackTrace(); }
        catch (IOException e) { e.printStackTrace(); }
        catch (ClassNotFoundException e) { e.printStackTrace(); }
        finally {
            try { objectInputStream.close(); }
            catch (IOException e) { e.printStackTrace(); }
        }
    }

    public void updateNodesListText() {
        int leftSubtreeDepth;
        int rightSubtreeDepth;
        if (binaryTree.getRoot() == null) leftSubtreeDepth = rightSubtreeDepth = 0;
        else {
            leftSubtreeDepth = binaryTree.getRoot().getLeftChild() == null ? 0 :
                    binaryTree.getDepth(binaryTree.getRoot().getLeftChild());
            rightSubtreeDepth = binaryTree.getRoot().getRightChild() == null ? 0 :
                    binaryTree.getDepth(binaryTree.getRoot().getRightChild());
        }
        String text = "Binary Tree Size: " + binaryTree.getSize() + "\t\t" +
                      "Left subtree depth: " + leftSubtreeDepth + "\t\t" +
                      "Right subtree depth: " + rightSubtreeDepth + '\n' +
                      "Binary Tree Nodes Ordered By Indexes:\n\n";
        int index = 0;
        for (Node node : GUI.this.binaryTree) {
            text += index++;
            text += ": ";
            text += node;
            text += '\n';
        }
        nodesListText.setText(text);
    }

    public void binaryTreeTest(int size) {
        System.out.println("===============================================================");
        long lastTime = System.currentTimeMillis();
        while (binaryTree.getSize() != size)
            binaryTree.add(typeBuilder.create());
        System.out.println("Затрачено секунд на добавление " + binaryTree.getSize() + " узлов: " +
                (((double) (System.currentTimeMillis() - lastTime))) / 1000);

        System.out.println("Глубина левого поддерева: " +
                binaryTree.getDepth(binaryTree.getRoot().getLeftChild()));
        System.out.println("Глубина правого поддерева: " +
                binaryTree.getDepth(binaryTree.getRoot().getRightChild()));


        lastTime = System.currentTimeMillis();
        binaryTree.balance();
        System.out.println("\nЗатрачено секунд на балансировку: " +
                (((double) (System.currentTimeMillis() - lastTime))) / 1000);

        System.out.println("Глубина левого поддерева после балансировки: " +
                binaryTree.getDepth(binaryTree.getRoot().getLeftChild()));
        System.out.println("Глубина правого поддерева после балансировки: " +
                binaryTree.getDepth(binaryTree.getRoot().getRightChild()));


        lastTime = System.currentTimeMillis();
        binaryTree.forEach(Node::getValue);
        System.out.println("\nЗатрачено секунд на forEach: " +
                (((double) (System.currentTimeMillis() - lastTime))) / 1000);

        lastTime = System.currentTimeMillis();
        for (int i = 0; i < binaryTree.getSize(); i++)
            binaryTree.findByIndex(i);
        System.out.println("\nЗатрачено секунд на нахождение всех узлов: " +
                (((double) (System.currentTimeMillis() - lastTime))) / 1000);

        while (this.getBinaryTree().getSize() != 0)
            binaryTree.deleteByIndex(0);
        System.out.println("\nЗатрачено секунд на удаление всех узлов: " +
                (((double) (System.currentTimeMillis() - lastTime))) / 1000);
        System.out.println("===============================================================\n");
    }

    public TypeBuilder getTypeBuilder() { return typeBuilder; }

    public BinaryTree<Object> getBinaryTree() { return binaryTree; }

    /**
     * Графическая панель, на которой отображается структура данных.
     * Перемещение камеры возможно с помощью стрелок или перетаскивания мышки с любой зажатой кнопкой мышки.
     * Примерно с 9 уровня узлы начинают криво отображаться, поэтому лучше обойтись 8 уровнями :)
     */
    private class ViewPanel extends JPanel {
        private Point cameraPosition;

        public ViewPanel() {
            super();
            cameraPosition = new Point(640, 30);
        }

        public synchronized void draw() {
            if (binaryTree.getSize() <= 200)
                repaint();
            else this.setVisible(false);
        }

        @Override
        public void paint(Graphics g) {
            super.paint(g);
            g.translate(cameraPosition.x, cameraPosition.y);
            g.setFont(new Font("Dialog", Font.BOLD, 12));

            if (binaryTree != null) {
                Stack globalStack = new Stack(); // общий стек для значений дерева
                globalStack.push(binaryTree.getRoot());
                Point nodePosition = new Point();
                int height = 0;
                int leftCornerX = 0;
                int treeDepth = binaryTree.getDepth(binaryTree.getRoot());
                int distanceBetweenNodes = 1440 * treeDepth;
                boolean rowEmpty = false;
                boolean forDrawRoot = true;

                while (!rowEmpty) {
                    Stack localStack = new Stack(); // локальный стек для задания потомков элемента
                    rowEmpty = true;
                    height++;
                    if (height >= 2) {
                        leftCornerX -= distanceBetweenNodes / 2;
                        nodePosition.x = leftCornerX;
                        nodePosition.y += 80;
                    }

                    while (!globalStack.isEmpty()) { // Пока в общем стеке есть элементы
                        Node temp = (Node) globalStack.pop(); // Берем элемент, удаляя его из стека
                        if (temp != null) {
                            // Если корень, то закрашиваем его красным цветом для наглядности
                            if (forDrawRoot) {
                                g.setColor(new Color(139, 0, 0));
                                g.fillOval(nodePosition.x, nodePosition.y, 30, 30);
                                forDrawRoot = false;
                            }
                            else {
                                g.setColor(new Color(90, 90, 90));
                                g.fillOval(nodePosition.x, nodePosition.y, 30, 30);
                            }

                            // Рисуем ребра к узлам
                            if (height == 1) {
                                // Ребро к левому потомку
                                if (temp.getLeftChild() != null)
                                    g.drawLine(nodePosition.x, nodePosition.y + 15,
                                            nodePosition.x - (distanceBetweenNodes / 2) + 15, nodePosition.y + 80);

                                // Ребро к правому потомку
                                if (temp.getRightChild() != null)
                                    g.drawLine(nodePosition.x + 30, nodePosition.y + 15,
                                            nodePosition.x + (distanceBetweenNodes / 2) + 15, nodePosition.y + 80);
                            }
                            else {
                                // Ребро к левому потомку
                                if (temp.getLeftChild() != null)
                                    g.drawLine(nodePosition.x, nodePosition.y + 15,
                                            nodePosition.x - (distanceBetweenNodes / 4) + 15, nodePosition.y + 80);

                                // Ребро к правому потомку
                                if (temp.getRightChild() != null)
                                    g.drawLine(nodePosition.x + 30, nodePosition.y + 15,
                                            nodePosition.x + (distanceBetweenNodes / 4) + 15, nodePosition.y + 80);
                            }

                            // Рисуем значение узла
                            g.setColor(new Color(255, 255, 255));
                            g.drawString(temp.getValue() + "", nodePosition.x + 5, nodePosition.y + 19);

                            // Рисуем индекс или вес узла
                            g.setColor(new Color(90, 90, 90));
                            if (showIndexesOrWeights)
                                g.drawString(binaryTree.getIndex(temp) + "",
                                        nodePosition.x + 10, nodePosition.y - 10);
                            else
                                g.drawString(temp.getWeight() + "",
                                        nodePosition.x + 10, nodePosition.y - 10);

                            // Изменяем положение по горизонтали для рисования следующего узла справа
                            if (height >= 2) nodePosition.x += distanceBetweenNodes;
                            localStack.push(temp.getLeftChild()); // соохраняем в локальный стек, наследники текущего элемента
                            localStack.push(temp.getRightChild());
                            if (temp.getLeftChild() != null || temp.getRightChild() != null) rowEmpty = false;
                        }
                        else {
                            localStack.push(null);
                            localStack.push(null);
                            nodePosition.x += distanceBetweenNodes;
                        }
                    }

                    while (!localStack.isEmpty())
                        globalStack.push(localStack.pop()); // перемещаем все элементы из локального стека в глобальный

                    if (height >= 2) distanceBetweenNodes /= 2;
                }
            }
        }
    }

    /**
     * Панель управления, на которой содержится весь основной функционал вьюшки
     */
    private class MenuBar extends JMenuBar {
        private JMenu jMenuAdding;
        private JMenu jMenuDeleting;
        private JMenu jMenuShowing;
        private JMenu jMenuFile;
        private JMenuItem addingItem, adding1RandItem, adding3RandItem, adding5RandItem;
        private JMenuItem deleteItem, deleteAllItem, saveItem, loadItem, showRootItem;
        private ButtonGroup groupButton;
        private JRadioButton indexRadioButton, weightRadioButton;
        private JFileChooser fileChooser;

        public MenuBar() {
            super();
            this.setSize(200, 200);
            this.setVisible(true);

            jMenuFile = new JMenu("File");
            saveItem = new JMenuItem("Save");
            saveItem.addActionListener(e -> {
                fileChooser = new JFileChooser("src");
                fileChooser.setDialogTitle("Save data structure");
                fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION)
                    save(fileChooser.getSelectedFile());
            });
            loadItem = new JMenuItem("Load");
            loadItem.addActionListener(e -> {
                fileChooser = new JFileChooser("src");
                fileChooser.setDialogTitle("Load data structure");
                fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

                if (fileChooser.showDialog(this, "Load") == JFileChooser.APPROVE_OPTION)
                    load(fileChooser.getSelectedFile());
            });
            jMenuFile.add(saveItem);
            jMenuFile.add(loadItem);

            jMenuAdding = new JMenu("Adding");
            addingItem = new JMenuItem("Add");
            addingItem.addActionListener(e -> createAddingWindow());
            adding1RandItem = new JMenuItem("Add 1 random");
            adding1RandItem.addActionListener(e -> binaryTree.add(typeBuilder.create()));
            adding3RandItem = new JMenuItem("Add 3 random");
            adding3RandItem.addActionListener(e -> {
                for (int i = 0; i < 3; i++)
                    binaryTree.add(typeBuilder.create());
                updateNodesListText();
            });
            adding5RandItem = new JMenuItem("Add 5 random");
            adding5RandItem.addActionListener(e -> {
                for (int i = 0; i < 5; i++)
                    binaryTree.add(typeBuilder.create());
                updateNodesListText();
            });
            jMenuAdding.add(addingItem);
            jMenuAdding.add(adding1RandItem);
            jMenuAdding.add(adding3RandItem);
            jMenuAdding.add(adding5RandItem);

            jMenuDeleting = new JMenu("Deleting");
            deleteItem = new JMenuItem("Delete");
            deleteItem.addActionListener(e -> createDeletingWindow());
            deleteAllItem = new JMenuItem("Delete all");
            deleteAllItem.addActionListener(e -> {
                while (binaryTree.getSize() != 0)
                    binaryTree.deleteByIndex(0);
                updateNodesListText();
            });
            jMenuDeleting.add(deleteItem);
            jMenuDeleting.add(deleteAllItem);

            jMenuShowing = new JMenu("Showing");
            indexRadioButton = new JRadioButton("Show indexes");
            indexRadioButton.setSelected(true);
            indexRadioButton.addActionListener(e -> showIndexesOrWeights = true);
            weightRadioButton = new JRadioButton("Show weights");
            weightRadioButton.addActionListener(e -> showIndexesOrWeights = false);
            showIndexesOrWeights = true;
            //showRootItem = new JMenuItem("Get camera to root");
            //showRootItem.addActionListener(e -> viewPanel.cameraPosition.setLocation(GUI.this.getWidth() / 2, 0));
            groupButton = new ButtonGroup();
            groupButton.add(indexRadioButton);
            groupButton.add(weightRadioButton);
            jMenuShowing.add(indexRadioButton);
            jMenuShowing.add(weightRadioButton);
            //jMenuShowing.add(showRootItem);


            this.add(jMenuFile);
            this.add(jMenuAdding);
            this.add(jMenuDeleting);
            this.add(jMenuShowing);
        }
    }
}
