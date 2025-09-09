

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

public class MainProgram {

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        Graph graph = new Graph(400);
        String v1 , v2;
        int vresult;
        boolean vresultb;

        while (true) {
            System.out.println("Menu:");
            System.out.println("1.  Read File");
            System.out.println("2.  Print HashTable");
            System.out.println("3.  Print Graph");
            System.out.println("4.  isThereAPath(String v1, String v2)");
            System.out.println("5.  BFSfromTo(String v1, String v2)");
            System.out.println("6.  DFSfromTo(String v1, String v2)");
            System.out.println("7.  WhatIsShortestPathLength(String v1, String v2)");
            System.out.println("8.  NumberOfSimplePaths(v1, v2)");
            System.out.println("9.  Neighbors(String v1)");
            System.out.println("10. HighestDegree() ");
            System.out.println("11. IsDirected()  ");
            System.out.println("12. AreTheyAdjacent(String v1, String v2)  ");
            System.out.println("13. IsThereACycle(String v1) ");
            System.out.println("14. NumberOfVerticesInComponent(String v1)  ");
            System.out.println("99. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); 

            switch (choice) {
                case 1:
                    System.out.print("Enter the file name: ");
                   String fileName = scanner.nextLine();
                    graph.readFile(fileName);
                    break;
                case 2:
                    graph.printHashTable();
                    break;
                case 3:
                    graph.printGraphWithCities();
                    break;
                case 4:
                    System.out.print("Enter the first vertex: ");
                    v1 = scanner.nextLine();
                    System.out.print("Enter the second vertex: ");
                    v2 = scanner.nextLine();
                    boolean pathExists = graph.isThereAPath(v1, v2);
                    System.out.println("Path between " + v1 + " and " + v2 + ": " + pathExists);
                    break;
                case 5:
                    System.out.print("Enter the first vertex: ");
                    v1 = scanner.nextLine();
                    System.out.print("Enter the second vertex: ");
                    v2 = scanner.nextLine();
                    graph.BFSfromTo(v1, v2);
                    break;
                case 6:
                    System.out.print("Enter the first vertex: ");
                    v1 = scanner.nextLine();
                    System.out.print("Enter the second vertex: ");
                    v2 = scanner.nextLine();
                    graph.DFSfromTo(v1, v2);
                    break;
                case 7:
                    System.out.print("Enter the first vertex: ");
                    v1 = scanner.nextLine();
                    System.out.print("Enter the second vertex: ");
                    v2 = scanner.nextLine();
                    vresult= graph.WhatIsShortestPathLength(v1, v2);
                    System.out.println("The Shortest Path Length of  " + v1 + " and " + v2 + ": " + vresult);
                    break;
                case 8:
                    System.out.print("Enter the first vertex: ");
                    v1 = scanner.nextLine();
                    System.out.print("Enter the second vertex: ");
                    v2 = scanner.nextLine();
                    vresult= graph.NumberOfSimplePaths(v1, v2);
                    System.out.println("The number of Path between " + v1 + " and " + v2 + ": " + vresult);
                    break;
                case 9:
                    System.out.print("Enter the first vertex: ");
                    v1 = scanner.nextLine();
                    System.out.println("The neighbours of " + v1 +  " : " + graph.Neighbors(v1));
                    break;

                case 10:
                    System.out.println("Highest Degree Vertices "+ graph.HighestDegree());
                    break;

                case 11:
                    System.out.println(" Is Directed : " + graph.IsDirected() );
                    break;

                case 12:
                    System.out.print("Enter the first vertex: ");
                    v1 = scanner.nextLine();
                    System.out.print("Enter the second vertex: ");
                    v2 = scanner.nextLine();
                    vresultb= graph.AreTheyAdjacent(v1, v2);
                    System.out.println("Are they Adjacent  " + v1 + " and " + v2 + " ? : " + vresultb);
                    break;

                case 13:
                    System.out.print("Enter the first vertex: ");
                    v1 = scanner.nextLine();
                    System.out.println("Is there a cycle of  " + v1 +  " ? : " + graph.IsThereACycle(v1));
                    break;

                case 14:
                    System.out.print("Enter the first vertex: ");
                    v1 = scanner.nextLine();
                    graph.NumberOfVerticesInComponent(v1);
                    break;

                                        
                case 99:
                    System.out.println("Exiting the program. Goodbye!");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        }
    }
}
