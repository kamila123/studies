import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        System.out.println("Welcome to the studies");

        BinaryTree binaryTree = new BinaryTree();
        BinaryTree.Node root = binaryTree.createNewNode(2);
        root.left = binaryTree.createNewNode(7);
        root.right = binaryTree.createNewNode(15);
        root.left.left = binaryTree.createNewNode(3);
        root.left.right = binaryTree.createNewNode(8);
        root.left.right.left = binaryTree.createNewNode(5);
        root.left.right.right = binaryTree.createNewNode(10);
        root.right.right = binaryTree.createNewNode(20);
        root.right.right.right = binaryTree.createNewNode(25);

        binaryTree.printBoundary(root);

        BinaryTree.Node rootMirror = binaryTree.createNewNode(2);
        rootMirror.left = binaryTree.createNewNode(3);
        rootMirror.right = binaryTree.createNewNode(3);
        rootMirror.left.left = binaryTree.createNewNode(3);
        rootMirror.left.right = binaryTree.createNewNode(3);
        rootMirror.right.left = binaryTree.createNewNode(3);
        rootMirror.right.right = binaryTree.createNewNode(3);

        boolean isMirror = binaryTree.ifMirrorTree(rootMirror.left, rootMirror.right);

        System.out.println("Binary tree is mirror: " + isMirror);

        System.out.println("Printing left side of tree: ");

        binaryTree.printLeftView(root);

        System.out.println("Printing left side of tree: ");

        binaryTree.printLeftView(root);
        
        carParkingRoof(new int[]{2,5,9,10} , 9);
    }

    public static String findNumber(List<Integer> arr, int k) {
        boolean exist = arr.contains(k);

        return exist ? "YES" : "NO";
    }

    public static long carParkingRoof(int[] cars, int k) {
        Arrays.sort(cars);
        
        int start = 0;
        int minRoofLength = Integer.MAX_VALUE;
        
        for (int end = 0; end < cars.length; end++) {
            
            if (end < k - 1) continue;
            
            int currentRoofLength = cars[end] - cars[start++] + 1;
            
            minRoofLength = Math.min(minRoofLength, currentRoofLength);
        }
        
        return minRoofLength;
    }
}
