package br.com.mangarosa.collections;

public class BalancedTree <T extends Comparable<T>> extends BinaryTree<T> {

    @Override
    public void add(T value) {
        super.add(value);
        balanceTree();
    }

    @Override
    public void remove(T value) {
        super.remove(value);
        balanceTree();
    }

    // Balancear a árvore 
    private void balanceTree() {
        //updateHeights(this.root());
        BinaryTreeNode<T> root = balance(this.root());
        this.setRoot(root);
    }   

    // Método recursivo de balanceamento
    private BinaryTreeNode<T> balance (BinaryTreeNode<T> node) {
        if (node == null) {
            return null;
        }
        
        else {
            BinaryTreeNode<T> leftNode = node.getLeftChild();
            BinaryTreeNode<T> rightNode = node.getRightChild();

            node.setLeftChild(balance(leftNode));
            node.setRightChild(balance(rightNode));

            updateHeights(node);

            int balanceFactor = height(leftNode) - height(rightNode);

            int leftBalance = balanceFactor(leftNode);
            int rightBalance =  balanceFactor(rightNode);

            // Rotação simples à esquerda
            if (balanceFactor < -1 && rightBalance <= 0) {
                return leftRotate(node);
            }

            // Rotação simples à direita
            if (balanceFactor > 1 && leftBalance >= 0) {
                return rightRotate(node);
            }
            
            // Rotação dupla à esquerda (direita da esquerda)
            if (balanceFactor < -1 && rightBalance > 0) {
                node.setRightChild(rightRotate(node.getRightChild()));
                return leftRotate(node);
            }

            // Rotação dupla à direita (esquerda da direita)
            if (balanceFactor > 1 && leftBalance < 0) {
                node.setLeftChild(leftRotate(node.getLeftChild()));
                return rightRotate(node);
            }
        }
        
        return node;
    }

    // Método de rotação à esquerda
    private BinaryTreeNode<T> leftRotate(BinaryTreeNode<T> node) {
        BinaryTreeNode<T> right = node.getRightChild();
        BinaryTreeNode<T> leftRight = right.getLeftChild();

        right.setLeftChild(node);
        node.setRightChild(leftRight);

        int nodeHeight = Math.max(height(node.getLeftChild()), height(node.getRightChild())) + 1;
        int rightHeight = Math.max(height(right.getLeftChild()), height(right.getRightChild())) + 1;

        node.setHeight(nodeHeight);
        right.setHeight(rightHeight);

        return right;
    }

    // Método de rotação à direita
    private BinaryTreeNode<T> rightRotate(BinaryTreeNode<T> node) {
        BinaryTreeNode<T> left = node.getLeftChild();
        BinaryTreeNode<T> rightLeft = left.getRightChild();

        left.setRightChild(node);
        node.setLeftChild(rightLeft);

        int nodeHeight = Math.max(height(node.getLeftChild()), height(node.getRightChild())) + 1;
        int leftHeight = Math.max(height(left.getLeftChild()), height(left.getRightChild())) + 1;

        node.setHeight(nodeHeight);
        left.setHeight(leftHeight);

        return left;
    }

    // Cálculo do fator de balanceamento
    private int balanceFactor (BinaryTreeNode<T> node) {
        return node != null ? height(node.getLeftChild()) - height(node.getRightChild()) : 0;
    }

    // Retorna a altura de um nó
    private int height(BinaryTreeNode<T> node) {
        return (node != null) ? node.getHeight() : 0;
    }

    // Atualiza a altura de um nó 
    private int updateHeights(BinaryTreeNode<T> node) {
        if (node == null) {
            return 0;
        }
        else {
            int leftHeight = updateHeights(node.getLeftChild());
            int rightHeight = updateHeights(node.getRightChild());
            int height = Math.max(leftHeight, rightHeight) + 1;
            node.setHeight(height);

            return height;
        }
    }
}
