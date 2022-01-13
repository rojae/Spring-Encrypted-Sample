package com.encrypt.demo;

import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import com.encrypt.demo.utils.HexUtil;
import com.encrypt.demo.encrypt.CryptoUtil;
import com.encrypt.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.encrypt.demo.model.EncryptedTransaction;
import com.encrypt.demo.model.Transaction;
import com.encrypt.demo.model.User;
import com.google.gson.Gson;

@Controller
@RequestMapping(path = "/crypto")
public class EncrypController {
	
	@Autowired
    UserService userService;
	
	@CrossOrigin
	@GetMapping("/publickey")
    public @ResponseBody User getPublicKey(@RequestParam(value = "userId") String userId) throws Exception {
        User user = userService.getUser(userId);

        //server generates RSA key pair - public and private keys
        generateRsaKeyPair(user);

        userService.updateUser(user);

        //to simplify our example, User object is returned with generated RSA public key
        //RSA private key is not included in response because it should be kept as secret
        return user;
    }
	
	@CrossOrigin
    @PostMapping(path = "/transaction")
    public ResponseEntity<?> doTransaction(@RequestBody EncryptedTransaction encryptedTransaction) throws Exception {
        User user = userService.getUser(encryptedTransaction.getUserId());

        String encAesKeyBase64 = encryptedTransaction.getEncAesKey();
        System.out.printf("RSA encrypted Aes Key Base64 [len=%d]: %s\n", encAesKeyBase64.length(),encAesKeyBase64);
        //decode from Base64 format
        byte[] encAesKeyBytes = Base64.getDecoder().decode(encAesKeyBase64);

        System.out.printf("RSA encrypted Aes Key [len=%d]: \n", encAesKeyBytes.length);

        //decrypt AES key with private RSA key
        byte[] decryptedAesKeyHex =
                CryptoUtil.decryptWithPrivateRsaKey(encAesKeyBytes, user.getRsaPrivateKey());
        System.out.printf("Decrypted Aes Key [hex]: %s\n ", new String(decryptedAesKeyHex));

        byte[] decryptedAesKey = HexUtil.convertHexStringToBytes(new String(decryptedAesKeyHex));
        System.out.printf("Decrypted Aes Key [len=%d]: %s\n", decryptedAesKey.length, new String(decryptedAesKey));
        //initialization vector - 1st 16 chars of userId
        byte []iv = user.getId().substring(0,16).getBytes();

        System.out.printf("Encrypted transaction BASE64 [len=%d]: %s\n", encryptedTransaction.getPayload().length(), encryptedTransaction.getPayload());

        byte[] encTransBytes = Base64.getDecoder().decode(encryptedTransaction.getPayload());

        //decrypt transaction payload with AES key
        byte[] decrypted = CryptoUtil.decryptWithAes(encTransBytes, decryptedAesKey, iv);
        System.out.println("Decrypted transaction: " + new String(decrypted));

        //cast JSON string to Transaction object
        Transaction transaction = new Gson().fromJson(new String(decrypted), Transaction.class);

        //for example, call payment gateway with provided information
        executeTransaction(user.getId(), transaction);
        return ResponseEntity.accepted().build();
    }
	
    private void executeTransaction(String id, Transaction transaction) {
    	System.out.println("=================== Decrypted Data =========================");
		System.out.println("UserID : " + transaction.getUserId());
		System.out.println("Account : " + transaction.getAmount());
		System.out.println("Type : " + transaction.getType());
		System.out.println("CreditCard : " + transaction.getCreditCard());
    	System.out.println("=============================================================");
	}

	private void generateRsaKeyPair(User user) throws NoSuchAlgorithmException {
        KeyPair keyPair = CryptoUtil.generateRsaKeyPair();

        byte[] publicKey = keyPair.getPublic().getEncoded();
        byte[] privateKey = keyPair.getPrivate().getEncoded();

        // encoding keys to Base64 text format so that we can send public key via REST API
        String rsaPublicKeyBase64 = new String(Base64.getEncoder().encode(publicKey));
        String rsaPrivateKeyBase64 = new String(Base64.getEncoder().encode(privateKey));

        System.out.println("RSA PublicKey Base 64 " + rsaPublicKeyBase64);
        System.out.println("RSA PrivateKey Base 64 " + rsaPrivateKeyBase64);
        
        // saving keys to user object for later use
        user.setRsaPublicKey(rsaPublicKeyBase64);
        user.setRsaPrivateKey(rsaPrivateKeyBase64);
    }
    
}
