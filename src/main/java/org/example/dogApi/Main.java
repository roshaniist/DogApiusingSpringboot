package org.example.dogApi;

import org.example.dogApi.service.DogApiService;
import org.example.dogApi.service.LocalDbService;
import org.example.dogApi.service.SaveToLocalDbService;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        DogApiService apiService = new DogApiService();
        LocalDbService localService = new LocalDbService();
        SaveToLocalDbService saveService = new SaveToLocalDbService();

        while (true) {
            System.out.println("\n===================================");
            System.out.println("          MAIN MENU");
            System.out.println("===================================");
            System.out.println("1) Access Dog API");
            System.out.println("2) Local DB Operations");
            System.out.println("3) Save to Local DB (Avoid Duplicates)");
            System.out.println("4) Exit");
            System.out.println("-----------------------------------");
            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

                case 1 -> {
                    while (true) {
                        System.out.println("\n===================================");
                        System.out.println("       ACCESS DOG API MENU");
                        System.out.println("===================================");
                        System.out.println("1) List all breeds");
                        System.out.println("2) List all sub-breeds");
                        System.out.println("3) List sub-breeds of a specific breed");
                        System.out.println("4) Random image");
                        System.out.println("5) Image of a breed");
                        System.out.println("6) Multiple images of a breed");
                        System.out.println("7) Image of a sub-breed");
                        System.out.println("8) Multiple images of a sub-breed");
                        System.out.println("9) Return to main menu");
                        System.out.print("Enter option: ");
                        int opt = sc.nextInt();
                        sc.nextLine();

                        switch (opt) {
                            case 1 -> apiService.listAllBreeds();
                            case 2 -> apiService.listAllSubBreeds();
                            case 3 -> {
                                System.out.print("Enter breed name: ");
                                apiService.listSubBreedsOfBreed(sc.nextLine());
                            }
                            case 4 -> apiService.randomImage();
                            case 5 -> {
                                System.out.print("Enter breed: ");
                                apiService.imageOfBreed(sc.nextLine());
                            }
                            case 6 -> {
                                System.out.print("Enter breed: ");
                                String breed = sc.nextLine();
                                System.out.print("Enter count: ");
                                int count = sc.nextInt();
                                sc.nextLine();
                                apiService.multipleImagesOfBreed(breed, count);
                            }
                            case 7 -> {
                                System.out.print("Enter breed: ");
                                String breed = sc.nextLine();
                                System.out.print("Enter sub-breed: ");
                                String subBreed = sc.nextLine();
                                apiService.imageOfSubBreed(breed, subBreed);
                            }
                            case 8 -> {
                                System.out.print("Enter breed: ");
                                String breed = sc.nextLine();
                                System.out.print("Enter sub-breed: ");
                                String subBreed = sc.nextLine();
                                System.out.print("Enter count: ");
                                int count = sc.nextInt();
                                sc.nextLine();
                                apiService.multipleImagesOfSubBreed(breed, subBreed, count);
                            }
                            case 9 -> {
                                System.out.println("Returning to main menu...");
                                break;
                            }
                            default -> System.out.println("Invalid option!");
                        }
                        if (opt == 9) break;
                    }
                }

                case 2 -> {
                    while (true) {
                        System.out.println("\n===================================");
                        System.out.println("      LOCAL DB OPERATIONS MENU");
                        System.out.println("===================================");
                        System.out.println("1) View all breeds");
                        System.out.println("2) View all sub-breeds");
                        System.out.println("3) View sub-breeds of a breed");
                        System.out.println("4) View all images");
                        System.out.println("5) View images of a breed");
                        System.out.println("6) View images of a sub-breed");
                        System.out.println("7) Count statistics");
                        System.out.println("8) Return to main menu");
                        System.out.print("Enter option: ");
                        int opt2 = sc.nextInt();
                        sc.nextLine();

                        switch (opt2) {
                            case 1 -> localService.viewAllBreeds();
                            case 2 -> localService.viewAllSubBreeds();
                            case 3 -> {
                                System.out.print("Enter breed name: ");
                                localService.viewSubBreedsOfBreed(sc.nextLine());
                            }
                            case 4 -> localService.viewAllImages();
                            case 5 -> {
                                System.out.print("Enter breed name: ");
                                localService.viewImagesOfBreed(sc.nextLine());
                            }
                            case 6 -> {
                                System.out.print("Enter breed name: ");
                                String breed = sc.nextLine();
                                System.out.print("Enter sub-breed name: ");
                                String sub = sc.nextLine();
                                localService.viewImagesOfSubBreed(breed, sub);
                            }
                            case 7 -> localService.countStatistics();
                            case 8 -> {
                                System.out.println("Returning to main menu...");
                                break;
                            }
                            default -> System.out.println("Invalid option!");
                        }
                        if (opt2 == 8) break;
                    }
                }

                case 3 -> {
                    while (true) {
                        System.out.println("\n===================================");
                        System.out.println("     SAVE TO LOCAL DB MENU");
                        System.out.println("===================================");
                        System.out.println("1) Save List of Breeds");
                        System.out.println("2) Save List of Sub-Breeds");
                        System.out.println("3) Save Sub-Breeds of a Breed");
                        System.out.println("4) Save Random Image");
                        System.out.println("5) Save Image of a Breed");
                        System.out.println("6) Save Multiple Images of a Breed");
                        System.out.println("7) Save Image of a Sub-Breed");
                        System.out.println("8) Save Multiple Images of a Sub-Breed");
                        System.out.println("9) Return to Main Menu");
                        System.out.print("Enter option: ");
                        int opt3 = sc.nextInt();
                        sc.nextLine();

                        switch (opt3) {
                            case 1 -> saveService.saveListOfBreeds();
                            case 2 -> saveService.saveListOfSubBreeds();
                            case 3 -> {
                                System.out.print("Enter breed name: ");
                                saveService.saveSubBreedsOfBreed(sc.nextLine());
                            }
                            case 4 -> saveService.saveRandomImage();
                            case 5 -> {
                                System.out.print("Enter breed: ");
                                saveService.saveImageOfBreed(sc.nextLine());
                            }
                            case 6 -> {
                                System.out.print("Enter breed: ");
                                String breed = sc.nextLine();
                                System.out.print("Enter count: ");
                                int count = sc.nextInt();
                                sc.nextLine();
                                saveService.saveMultipleImagesOfBreed(breed, count);
                            }
                            case 7 -> {
                                System.out.print("Enter breed: ");
                                String breed = sc.nextLine();
                                System.out.print("Enter sub-breed: ");
                                String sub = sc.nextLine();
                                saveService.saveImageOfSubBreed(breed, sub);
                            }
                            case 8 -> {
                                System.out.print("Enter breed: ");
                                String breed = sc.nextLine();
                                System.out.print("Enter sub-breed: ");
                                String sub = sc.nextLine();
                                System.out.print("Enter count: ");
                                int count = sc.nextInt();
                                sc.nextLine();
                                saveService.saveMultipleImagesOfSubBreed(breed, sub, count);
                            }
                            case 9 -> {
                                System.out.println("Returning to main menu...");
                                break;
                            }
                            default -> System.out.println("Invalid option!");
                        }
                        if (opt3 == 9) break;
                    }
                }

                case 4 -> {
                    System.out.println("Exiting...");
                    System.exit(0);
                }

                default -> System.out.println("Invalid choice!");
            }
        }
    }
}
