public class BinaryTree {

    void printTopView(Node node) {
        if (node == null) {
            return;
        }
        
        System.out.println(node.val);

//        if (node.left != null) {
//            printTopView(node.left);
//        }
//
//        if (node.right != null) {
//            printTopView(node.right);
//        }

    }

    void printLeftView(Node node) {
        if (node == null) {
            return;
        }

        System.out.println(node.val);

        printLeftView(node.left);
    }

    public boolean ifMirrorTree(Node node1, Node node2) {
        if (node1 == null && node2 == null) {
            return true;
        }

        if (node1 == null || node2 == null) {
            return false;
        }

        return node1.val == node2.val
                && ifMirrorTree(node1.left, node2.right)
                && ifMirrorTree(node1.right, node2.left);
    }

    void printBoundary(Node node) {

        if (node == null) {
            return;
        }

        System.out.println("Root :");
        System.out.println(node.val);

        System.out.println("Left boundary :");
        printBoundaryLeft(node.left);

        System.out.println("Leaves :");
        printLeaves(node.left);
        printLeaves(node.right);

        System.out.println("Right boundary :");
        printBoundaryRight(node.right);

    }

    private void printBoundaryLeft(Node node) {
        if (node == null) {
            return;
        }

        if (node.left != null) {
            System.out.println(node.val);
            printBoundaryLeft(node.left);
        } else if (node.right != null) {
            System.out.println(node.val);
            printBoundaryLeft(node.right);
        }
    }

    private void printBoundaryRight(Node node) {
        if (node == null) {
            return;
        }

        if (node.right != null) {
            System.out.println(node.val);
            printBoundaryRight(node.right);
        } else if (node.left != null) {
            printBoundaryRight(node.left);
            System.out.println(node.val);
        }
    }

    private void printLeaves(Node node) {
        if (node == null) {
            return;
        }

        printLeaves(node.left);

        if (node.left == null && node.right == null) {
            System.out.print(node.val + " ");
        }

        printLeaves(node.right);
    }

    public Node createNewNode(int val) {
        Node newNode = new Node();
        newNode.val = val;
        newNode.left = null;
        newNode.right = null;
        return newNode;
    }

    class Node {
        Node left;
        Node right;
        int val;
    }
}
