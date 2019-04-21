import java.io.IOException;
import java.util.Scanner;

import static java.lang.System.exit;
import static java.lang.System.setOut;

public class Runner {
    public static void main(String[] args) throws IOException {
        System.out.println();
        System.out.println("---- Data Protection in Cloud using AWS Key Management Service ----");
        Scanner sc = new Scanner(System.in);
        int input=0;

        do {
            System.out.println();
            System.out.println("*****************Main Menu*****************");
            System.out.println("Please enter : ");
            System.out.println(" -> 1 for String Encryption and Decryption");
            System.out.println(" -> 2 for File Encryption");
            System.out.println(" -> 3 for File Decryption");
            System.out.println(" -> 4 for Image Encryption");
            System.out.println(" -> 5 for Image Decryption");
            System.out.println(" -> 0 to Exit");
            System.out.println("********************************************");

            input=Integer.parseInt(sc.nextLine());


        switch (input) {

            case 1:
                System.out.print("Enter String to Encrypt :  ");
                String data = sc.nextLine();
                System.out.println();
                System.out.println("Starting Encryption...");
                EncryptionDecryptionService encryptionDecryptionService1 = new EncryptionDecryptionServiceExecuter();


                String encryptedString;
                encryptedString = encryptionDecryptionService1.encryptString(data);
                System.out.println("Encryption finished!");
                System.out.println("Encrypted String : " + encryptedString);
                System.out.println();

                String decryptedString;
                System.out.println("Starting Decryption...");
                decryptedString = encryptionDecryptionService1.decryptString(encryptedString);
                System.out.println("Decryption finished!");
                System.out.println("Decrypted String : " + decryptedString+"\n");
                break;

            case 2:
                String encryptFilePath;
                System.out.println("Enter file path : ");
                encryptFilePath = sc.nextLine();
                System.out.println("Starting File Encryption...");
                EncryptionDecryptionService encryptionDecryptionService2 = new EncryptionDecryptionServiceExecuter();
                encryptionDecryptionService2.encryptFile(encryptFilePath);
                break;


            case 3:
                String decryptFilePath;
                System.out.println("Enter the path of the file to be decrypted : ");
                decryptFilePath = sc.nextLine();
                System.out.println("Starting Decryption...");
                EncryptionDecryptionService encryptionDecryptionService3 = new EncryptionDecryptionServiceExecuter();
                encryptionDecryptionService3.decryptFile(decryptFilePath);
                break;

            case 4:
                String encryptImagePath;
                System.out.println("Enter path of image to be encrypted : ");
                encryptImagePath = sc.nextLine();
                System.out.println("Starting Image Encryption...");
                EncryptionDecryptionService encryptionDecryptionService4 = new EncryptionDecryptionServiceExecuter();
                encryptionDecryptionService4.encryptImage(encryptImagePath);
                break;

            case 5:
                String decryptImagePath;
                System.out.println("Enter path of image to be decrypted : ");
                decryptImagePath = sc.nextLine();
                System.out.println("Starting Image Decryption...");
                EncryptionDecryptionService encryptionDecryptionService5 = new EncryptionDecryptionServiceExecuter();
                encryptionDecryptionService5.decryptImage(decryptImagePath);
                break;

            case 0:
                exit(0);
                break;

            default:
                System.out.println("Please enter a valid option");

        }
        }while(input!=0);
    }
}
