import java.util.*;
import java.io.*;

public class main6 {
    public static void displayWelcomeScreen() {
        clearConsole();
        System.out.print(
                  "\n\t\t" + " ****** WELCOME TO ATM! ****** " + "\n" +
                    "\t\t" + " _____________________________ " + "\n" +
                    "\t\t" + "|                             |" + "\n" +
                    "\t\t" + "|____ 1) Login _______________|" + "\n" +
                    "\t\t" + "|                             |" + "\n" +
                    "\t\t" + "|____ 2) Register_____________|" + "\n" +
                    "\t\t" + "|                             |" + "\n" +
                    "\t\t" + "|_____________________________|" + "\n");
        
        Scanner scan = new Scanner(System.in);
        System.out.print("\n\t\tYour Choice: ");
        
        try {
            int choice = scan.nextInt();
            scan.nextLine(); // consume the newline character

            switch (choice) {
                case 1:
                    try {
                        if (User.signIn()) {
                            displayMainMenu();
                        } else {
                            System.out.println("Invalid username or password");
                        }
                    } catch (FileNotFoundException e) {
                        System.out.println("Error: File not found.");
                    }
                    break;

                case 2:
                    registerUser(scan);
                    break;

                default:
                    System.out.println("\n\t\tInvalid Choice!");
                    displayMainMenu();
            }
        } catch (InputMismatchException e) {
            System.out.println("Error: Please enter a valid number.");
            scan.nextLine(); // consume invalid input
            displayWelcomeScreen(); // re-prompt the user
        } catch (Exception e) {
            System.out.println("An unexpected error occurred.");
            e.printStackTrace();
        }
    }

    private static void registerUser(Scanner scan) {
        try {
            System.out.println("Enter your age: ");
            int age = scan.nextInt();
            scan.nextLine(); // consume newline
            
            System.out.println("Enter your Name: ");
            String name = scan.nextLine();
            
            System.out.println("Enter your Aadhaar Number: ");
            String aadhaar = scan.nextLine();
            
            System.out.println("Enter your Email: ");
            String email = scan.nextLine();
            
            System.out.println("Enter your Phone Number: ");
            String phone = scan.nextLine();
            
            // Additional validation can be added here
            
            System.out.println("User Registered Successfully!");
        } catch (InputMismatchException e) {
            System.out.println("Error: Invalid input. Please try again.");
            scan.nextLine(); // consume invalid input
        } catch (Exception e) {
            System.out.println("An unexpected error occurred during registration.");
            e.printStackTrace();
        }
    }

    public static void clearConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static void displayMainMenu() {
        clearConsole();
        System.out.print(
                      "\n\t\t" + "*********** MAIN MENU ***********" + "\n" +
                        "\t\t" + " _______________________________ " + "\n" +
                        "\t\t" + "|                               |" + "\n" +
                        "\t\t" + "|____ 1) View Balance __________|" + "\n" +
                        "\t\t" + "|                               |" + "\n" +
                        "\t\t" + "|____ 2) Withdraw Cash _________|" + "\n" +
                        "\t\t" + "|                               |" + "\n" +
                        "\t\t" + "|____ 3) Deposit Money _________|" + "\n" +
                        "\t\t" + "|                               |" + "\n" +
                        "\t\t" + "|____ 4) Transfer to Account ___|" + "\n" +
                        "\t\t" + "|                               |" + "\n" +
                        "\t\t" + "|____ 5)Mini Statement _________|" + "\n" + 
                        "\t\t" + "|                               |" + "\n"+
                        "\t\t" + "|____ 6)Change Pin______________|" + "\n" +
                        "\t\t" + "|                               |" + "\n" +
                        "\t\t" + "|____ 7) Logout ________________|" + "\n" +
                        "\t\t" + "|                               |" + "\n" +
                        "\t\t" + "|_______________________________|" + "\n");
        System.out.print("\n\t\tYour Choice: ");
        try (Scanner scan = new Scanner(System.in)) {
            try{ 
            int choice = scan.nextInt();
            
      
            switch (choice) {
                case 1:
                try{ 
                File f = new File("./ACCOUNTS/" + User.acc + "/balance.csv");
                System.out.println("Your current balance is: " + User.getBalance(f));
                }
                 catch (IOException e) {
                    e.printStackTrace();
                    
                }
                    break;
                case 2:
                try{ 
                  User.withdraw();
                }
                catch(IOException e){
                    e.printStackTrace();
                }
                    break;
                case 3:
                    try {
                        User.deposit();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case 4:
                     try{
                        User.Transfer();
                     }
                     catch(IOException e){
                        e.printStackTrace();
                     }
                     break;
                case 5:
                      try{
                        User.printTransaction();
                      }
                      catch(IOException e){
                        e.printStackTrace();
                      }
                      break;
                case 6:
                System.out.println("Enter your previous password: ");
                String oldpass = scan.nextLine();
                System.out.println("Enter new password: ");
                String newpass = scan.nextLine();
                boolean isPinChanged = User.changePin(User.acc, oldpass, newpass);
                if (isPinChanged) {
                    System.out.println("PIN changed successfully.");
                } else {
                    System.out.println("Failed to change PIN.");
                }
                break;

                case 7:
                    System.out.print("\n\t\tLogged Out!!\n");
                    try {
                        System.out.print("\n\nPress any key to continue...");
                        System.in.read();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    displayWelcomeScreen();
                    break;
                default:
                    System.out.print("\n\t\tInvalid Choice!\n");
                    try {
                        System.out.print("\n\nPress any key to continue...");
                        System.in.read();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    displayMainMenu();
                }
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
      // scan.close();
    }

    public static void main(String[] args) {
        displayWelcomeScreen();
    }
}

