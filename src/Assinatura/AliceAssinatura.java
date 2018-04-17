package Assinatura;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.*;

public class AliceAssinatura {

    public static void main(String[] args) throws IOException, NoSuchAlgorithmException, InvalidKeyException, ClassNotFoundException, NoSuchPaddingException, BadPaddingException, IllegalBlockSizeException {
        ServerSocket ss = new ServerSocket(3333);
        while (true) {
            Socket s = ss.accept();
            System.out.println("Conectado!");

            KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
            KeyPair myPair = kpg.generateKeyPair();
            PublicKey pubKey = myPair.getPublic();
            PrivateKey privateKey = myPair.getPrivate();

            ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());
            ObjetoAssinatura chavePublica = new ObjetoAssinatura();
            chavePublica.setPublicKey(pubKey);
            out.writeObject(chavePublica);

            ObjectInputStream in = new ObjectInputStream(s.getInputStream());
            ObjetoAssinatura objetoAssinatura = (ObjetoAssinatura) in.readObject();
            System.out.println("3. Leu o arquivo do socket.");
//
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, objetoAssinatura.getPublicKey());
            byte[] docDecriptografado = cipher.doFinal(objetoAssinatura.getArquivo());

            Cipher cipher2 = Cipher.getInstance("RSA");
            cipher2.init(Cipher.DECRYPT_MODE, objetoAssinatura.getPublicKey());
            byte[] hashCryptBob = cipher2.doFinal(objetoAssinatura.getHash());

            Cipher cipher4 = Cipher.getInstance("RSA");
            cipher4.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] aesBobDecrypt = cipher4.doFinal(objetoAssinatura.getAesKey());

            SecretKey originalKey = new SecretKeySpec(aesBobDecrypt, 0, aesBobDecrypt.length, "AES");

            System.out.println("AES KEY Alice => "+originalKey);

            Cipher cipher3 = Cipher.getInstance("AES");
            cipher3.init(Cipher.DECRYPT_MODE, originalKey);
            byte[] hashDecrypt  = cipher3.doFinal(objetoAssinatura.getArquivo());

            File f = new File(objetoAssinatura.getNomeArquivo());
            FileOutputStream fout = new FileOutputStream(f);
            fout.write(docDecriptografado);
            s.close();

        }
    }
}
