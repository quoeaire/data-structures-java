import AVLTree.*;
import java.util.Scanner;

class App {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println("Welcome to Midterm Review");
        System.out.println("===================================\n");

        AVLTree<Integer> tree = new AVLTree<Integer>();
        tree.insert(10);
        tree.insert(5);
        tree.insert(15);
        tree.insert(8);
        tree.insert(18);
        System.out.println("size: " + tree.size());

    }
}