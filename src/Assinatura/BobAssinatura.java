package Assinatura;

import javax.crypto.*;
import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.security.*;

public class BobAssinatura {

    //gerar RSA bob
    //enviar: chave public BOB, arquivo, hash criptografado COM AES, AES key criptogradado com privado,

    public static void main(String[] args) throws IOException, NoSuchAlgorithmException, NoSuchPaddingException, BadPaddingException, IllegalBlockSizeException, InvalidKeyException, ClassNotFoundException {

        JFileChooser chooserArquivo = new JFileChooser();
        int escolha = chooserArquivo.showOpenDialog(new JFrame());
        if (escolha != JFileChooser.APPROVE_OPTION) {
            return;
        }
        System.out.println("1. Selecionou arquivo.");

        System.out.println("2. Lendo o arquivo...");
        File arquivo = new File(chooserArquivo.getSelectedFile().getAbsolutePath());
        FileInputStream fin = new FileInputStream(arquivo);
        byte[] barquivo = new byte[(int) fin.getChannel().size()];
        fin.read(barquivo);
        System.out.println("2. Leu o arquivo.");

        Socket s = new Socket("localhost", 3333);
        ObjectInputStream in = new ObjectInputStream(s.getInputStream());
        ObjetoAssinatura objetoAssinatura = (ObjetoAssinatura) in.readObject();

        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        kgen.init(128);
        Key aesKey = kgen.generateKey();

        System.out.println("AES KEY bob => "+aesKey);

        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
        KeyPair myPair = kpg.generateKeyPair();
        PublicKey pubKey = myPair.getPublic();
        PrivateKey privateKey = myPair.getPrivate();

        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(barquivo);

        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, aesKey);
        byte[] hashCript = cipher.doFinal(hash);

        Cipher cipher2 = Cipher.getInstance("RSA");
        cipher2.init(Cipher.ENCRYPT_MODE, privateKey);
        byte[] hashCriptAlice  = cipher2.doFinal(hashCript);

        Cipher cipher3 = Cipher.getInstance("RSA");
        cipher3.init(Cipher.ENCRYPT_MODE, objetoAssinatura.getPublicKey());
        byte[] arquivoCript  = cipher3.doFinal(barquivo);

        Cipher cipher4 = Cipher.getInstance("RSA");
        cipher4.init(Cipher.ENCRYPT_MODE, objetoAssinatura.getPublicKey());
        byte[] aesKeyCript  = cipher4.doFinal(aesKey.getEncoded());

        ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());
        ObjetoAssinatura obj = new ObjetoAssinatura();

        obj.setArquivo(arquivoCript);
        obj.setPublicKey(pubKey);
        obj.setHash(hashCriptAlice);
        obj.setAesKey(aesKeyCript);
        obj.setNomeArquivo(arquivo.getName());
        out.writeObject(obj);
        out.close();
        s.close();
    }
}
