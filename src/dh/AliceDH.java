package dh;


import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;

public class AliceDH{

    public static void main(String[] args) throws IOException, ClassNotFoundException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        ServerSocket ss = new ServerSocket(3333);
        Socket s = ss.accept();

        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
        KeyPair myPair = kpg.generateKeyPair();

        ObjetoTrocaDH objetoTrocaDH = new ObjetoTrocaDH();
        objetoTrocaDH.setPublicKey(myPair.getPublic());
        ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());
        out.writeObject(objetoTrocaDH);

        ObjectInputStream in = new ObjectInputStream(s.getInputStream());
        objetoTrocaDH = (ObjetoTrocaDH) in.readObject();

        byte[] arquivoCripto = objetoTrocaDH.getArquivoCriptografado();

        SecretKeySpec ks = new SecretKeySpec(chaveAES, "AES");

        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, ks);
        byte[] arquivo = cipher.doFinal(arquivoCripto);

        File saida = new File(objetoTrocaDH.getNomeArquivo());
        OutputStream fout = new FileOutputStream(saida);
        fout.write(arquivo);
        fout.close();
        s.close();

    }

}