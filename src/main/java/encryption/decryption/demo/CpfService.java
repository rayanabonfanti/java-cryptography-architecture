package encryption.decryption.demo;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class CpfService {
    private static final String ALGO = "AES";
    private static final byte[] keyValue = "CPF_ENCRYPTION_KEY".getBytes();
    private ObjectMapper objectMapper = new ObjectMapper();

    public String encrypt(String Data) throws Exception {
        KeyGenerator keyGen = KeyGenerator.getInstance(ALGO);
        keyGen.init(128);
        SecretKey secretKey = new SecretKeySpec(keyValue, ALGO);

        Cipher c = Cipher.getInstance(ALGO);
        c.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encVal = c.doFinal(Data.getBytes());
        return java.util.Base64.getEncoder().encodeToString(encVal);
    }

    public String decrypt(String encryptedData) throws Exception {
        SecretKey secretKey = new SecretKeySpec(keyValue, ALGO);

        Cipher c = Cipher.getInstance(ALGO);
        c.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decodedValue = java.util.Base64.getDecoder().decode(encryptedData);
        byte[] decValue = c.doFinal(decodedValue);
        String decryptCpf = new String(decValue);
        try {
            JsonNode rootNode = objectMapper.readTree(decryptCpf);
            return rootNode.get("cpf").asText();
        } catch (IOException e) {
            throw new RuntimeException("Erro ao parsear JSON", e);
        }
    }

}
