import java.io.*;
import java.util.Scanner;

public class User {
    static String acc;  
    static String pass; 
    public static boolean signIn() throws FileNotFoundException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter your account number: ");
        acc = sc.nextLine();  
        System.out.println("Enter password: ");
        pass = sc.nextLine();  
        sc.close();
        String line = "";
        File f1 = new File("./ACCOUNTS/" + acc + "/Details.csv");
        File f2 = new File("./PASSWORDS/" + acc + "/Pass.csv");

        if (f2.exists()) {
            Scanner scanner = new Scanner(f2);
            while (scanner.hasNextLine()) {
                line = scanner.nextLine();
            }
            scanner.close();
        } else {
            System.out.print("Wrong account number");
            return false;
        }

        String hash = Hash.hashPassword(pass);  
        if (f1.exists() && hash.equals(line)) {
            return true;
        } else {
            return false;
        }
    }
    public static void withdraw() throws IOException {
        File f = new File("./ACCOUNTS/" + acc + "/Balance.csv");
        double bal = getBalance(f);
        System.out.println("Enter the Amount: ");
        Scanner sc = new Scanner(System.in);
        double amount = sc.nextDouble();
        sc.close();
        if (bal >= amount) {
            updateBalance(f, bal - amount);
            System.out.println("Collect your money.");
        } else {
            System.out.println("Insufficient funds.");
        }
    }
    public static void deposit() throws IOException {
        File f = new File("./ACCOUNTS/" + acc + "/Balance.csv");
        double bal = getBalance(f);
        System.out.println("Enter the amount to Deposit: ");
        Scanner sc = new Scanner(System.in);
        double amount = sc.nextDouble();
        sc.close();
        updateBalance(f, bal + amount);
        System.out.println("Money credited successfully.");
    }

    public static double getBalance(File file) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(file));
        double balance = Double.parseDouble(br.readLine());
        br.close();
        return balance;
    }

    public static void updateBalance(File file, double newBalance) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(file));
        bw.write(String.valueOf(newBalance));
        bw.close();
    }

    public static void Transfer() throws IOException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter recipient account number: ");
        String recipientAcc = sc.nextLine();
        File recipientFile = new File("./ACCOUNTS/" + recipientAcc + "/Balance.csv");
        if (!recipientFile.exists()) {
            System.out.println("Recipient account not found.");
            sc.close();
            return;
        }
        System.out.println("Enter amount to transfer: ");
        double transferAmount = sc.nextDouble();
        File currentUserFile = new File("./ACCOUNTS/" + acc + "/Balance.csv");
        double senderBalance = getBalance(currentUserFile);
        if (senderBalance >= transferAmount) {
            updateBalance(currentUserFile, senderBalance - transferAmount);
            double recipientBalance = getBalance(recipientFile);
            updateBalance(recipientFile, recipientBalance + transferAmount);
            System.out.println("Transfer successful.");
        } else {
            System.out.println("Insufficient balance.");
        }
        sc.close();
    }


    public static void printTransaction() throws IOException {
        File file = new File("./ACCOUNTS/" + acc + "/TransactionList.csv");
        if (!file.exists()) {
            System.out.println("No transaction history available.");
            return;
        }
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;
        while ((line = br.readLine()) != null) {
            System.out.println(line);
        }
        br.close();
    }

    public static boolean changePin(String accno, String oldPin, String newPin) {
        String oldPinHashed = null;
        File passFile = new File("./PASSWORDS/" + accno + "/Pass.csv");
        if (!passFile.exists()) {
            System.out.println("Account does not have a password.");
            return false;
        }
        try (Scanner sc = new Scanner(passFile)) {
            if (sc.hasNextLine()) {
                oldPinHashed = sc.nextLine();  
            } else {
                System.out.println("No password found in the file.");
                return false;
            }
        } catch (FileNotFoundException e) {
            System.out.println("Password file not found.");
            e.printStackTrace();
            return false;
        }
        if (Hash.hashPassword(oldPin).equals(oldPinHashed)) {
            try (FileWriter writer = new FileWriter(passFile)) {
                writer.write(Hash.hashPassword(newPin));
                return true;
            } catch (IOException e) {
                System.out.println("An error occurred while updating the PIN.");
                e.printStackTrace();
                return false;
            }
        } else {
            System.out.println("Old PIN is incorrect. PIN not changed.");
            return false;
        }
    }
    
}

