import java.io.*;
import java.util.Collections;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.encryptionsdk.AwsCrypto;
import com.amazonaws.encryptionsdk.CryptoResult;
import com.amazonaws.encryptionsdk.kms.KmsMasterKey;
import com.amazonaws.encryptionsdk.kms.KmsMasterKeyProvider;



import javax.imageio.ImageIO;

public class EncryptionDecryptionServiceExecuter implements EncryptionDecryptionService {
    private static String keyArn;

    public String encryptString(String data) {

    BasicAWSCredentials awsCreds = new BasicAWSCredentials("#AccessKey", "#SecretKey");

        keyArn = "#KeyArn";

        final AwsCrypto crypto = new AwsCrypto();

        final KmsMasterKeyProvider prov = new KmsMasterKeyProvider(keyArn);

        final Map<String, String> context = Collections.singletonMap("Example", "String");

        final String ciphertext = crypto.encryptString(prov, data, context).getResult();

        return ciphertext;
    }

    public String decryptString(String s){
        BasicAWSCredentials awsCreds = new BasicAWSCredentials("#AccessKey", "#SecretKey");

        keyArn = "#KeyArn";

        final AwsCrypto crypto = new AwsCrypto();

        final KmsMasterKeyProvider prov = new KmsMasterKeyProvider(keyArn);

        final Map<String, String> context = Collections.singletonMap("Example", "String");

        final CryptoResult<String, KmsMasterKey> decryptResult = crypto.decryptString(prov, s);

        if (!decryptResult.getMasterKeyIds().get(0).equals(keyArn)) {
            throw new IllegalStateException("Wrong key ID!");
        }
        for (final Map.Entry<String, String> e : context.entrySet()) {
            if (!e.getValue().equals(decryptResult.getEncryptionContext().get(e.getKey()))) {
                throw new IllegalStateException("Wrong Encryption Context!");
            }
        }


        return decryptResult.getResult();
    }


    public void encryptFile(String path) throws IOException{
        int numCharsRead;
        String data,ciphertext;

        try {
            File file = new File(path);
            FileReader fileReader = new FileReader(file);
            path=path.substring(0,path.lastIndexOf(file.separator));
            FileWriter fileWriter = new FileWriter(path+"\\Encrypted"+file.getName());
            StringBuffer stringBuffer = new StringBuffer();

            char[] charArray = new char[1024];
            while ((numCharsRead = fileReader.read(charArray)) > 0) {
                stringBuffer.append(charArray, 0, numCharsRead);
            }
            //System.out.println(stringBuffer.toString());
            data=stringBuffer.toString();
            ciphertext=encryptString(data);
            //System.out.println(ciphertext);
            fileWriter.write(ciphertext);
            fileWriter.flush();

            fileReader.close();
            fileWriter.close();
            System.out.println("Encrypted data stored at "+path+"\\Encrypted"+file.getName());
            System.out.println("File Encryption successful!\n");
        }
        catch (IOException ie){
            ie.printStackTrace();
        }


    }

    public void decryptFile(String path) throws IOException{
        int numCharsRead;
        String data,decryptedText;

        try {
            File file = new File(path);
            FileReader fileReader = new FileReader(file);
            StringBuffer stringBuffer = new StringBuffer();

            char[] charArray = new char[1024];
            while ((numCharsRead = fileReader.read(charArray)) > 0) {
                stringBuffer.append(charArray, 0, numCharsRead);
            }
            //System.out.println(stringBuffer.toString());
            data=stringBuffer.toString();
            decryptedText=decryptString(data);
            //System.out.println(decryptedText);

            path=path.substring(0,path.lastIndexOf(file.separator));
            String filename=(file.getName()).replaceAll("Encrypted","");
            FileWriter fileWriter = new FileWriter(path+"\\Decrypted"+filename);
            fileWriter.write(decryptedText);
            fileWriter.flush();
            fileReader.close();
            System.out.println("Decrypted data stored at "+path+"\\Decrypted"+filename);
            System.out.println("File Decryption successful!\n");


        }
        catch (IOException ie){
            ie.printStackTrace();
        }

    }

    public void encryptImage(String path) throws IOException {
        try {
            File file = new File(path);

            FileInputStream fis = new FileInputStream(file);
            byte byteArray[] = new byte[(int) file.length()];
            fis.read(byteArray);
            String imageString = Base64.encodeBase64String(byteArray);
          // System.out.println(imageString);

           String ciphertext=encryptString(imageString);

            path=path.substring(0,path.lastIndexOf(file.separator));
            String filename=file.getName().substring(0,(file.getName()).lastIndexOf('.'));
            FileWriter fileWriter=new FileWriter(path+"\\Encrypted"+filename+".txt");
            fileWriter.write(ciphertext);
            fileWriter.flush();
            fis.close();
            fileWriter.close();
            System.out.println("Encrypted image stored at "+path+"\\Encrypted"+filename+".txt");
            System.out.println("Image encrypted successfully!\n");
        }
        catch(IOException ie){
            ie.printStackTrace();
        }

    }



    public void decryptImage(String path) throws IOException {
        int numCharsRead;
        String data,ciphertext;
        try {
            File file = new File(path);
            byte byteArray[] = new byte[(int) file.length()];
            FileReader fileReader = new FileReader(file);
            StringBuffer stringBuffer = new StringBuffer();

            char[] charArray = new char[1024];
            while ((numCharsRead = fileReader.read(charArray)) > 0) {
                stringBuffer.append(charArray, 0, numCharsRead);
            }
            //System.out.println(stringBuffer.toString());
            data=stringBuffer.toString();
            String imageString=decryptString(data);
            //System.out.println(imageString);
            String fname=(file.getName()).replaceAll("Encrypted","");
            String filename=fname.substring(0,(fname).lastIndexOf('.'));
            path=path.substring(0,path.lastIndexOf(file.separator));
            FileOutputStream fos = new FileOutputStream(path+"\\Decrypted"+filename+".png"); //change path of image according to you
            byteArray = Base64.decodeBase64(imageString);
            fos.write(byteArray);

            fileReader.close();
            fos.close();
            System.out.println("Decrypted Image stored at "+path+"\\Decrypted"+filename+".png");
            System.out.println("Image decrypted successfully!\n");
        }
        catch(IOException ie){
            ie.printStackTrace();
        }
    }


}
