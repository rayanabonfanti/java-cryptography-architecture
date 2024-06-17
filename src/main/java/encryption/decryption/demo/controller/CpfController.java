package encryption.decryption.demo.controller;

import encryption.decryption.demo.CpfService;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/cpf")
public class CpfController {

    private final CpfService cpfService;

    public CpfController(CpfService cpfService) {
        this.cpfService = cpfService;
    }

    @PostMapping("/encrypt")
    public String encryptCpf(@RequestBody String cpf) throws Exception {
        return cpfService.encrypt(cpf);
    }

    @PostMapping("/decrypt")
    public HashMap<String, String> decryptCpf(@RequestBody List<String> encryptedCpfs) throws Exception {
        HashMap<String, String> decryptedCpfs = new HashMap<>();
        for (String encryptedCpf : encryptedCpfs) {
            String decryptedCpf = cpfService.decrypt(encryptedCpf);
            decryptedCpfs.put(encryptedCpf, decryptedCpf);
        }
        return decryptedCpfs;
    }
}
