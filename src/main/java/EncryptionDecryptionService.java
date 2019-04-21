import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import java.io.FileNotFoundException;
import java.io.IOException;

public interface EncryptionDecryptionService {
    String encryptString(String s);
    String decryptString(String s);
    void encryptFile(String s) throws IOException;
    void decryptFile(String s) throws IOException;
    void encryptImage(String s) throws IOException;
    void decryptImage(String s) throws IOException;
}
