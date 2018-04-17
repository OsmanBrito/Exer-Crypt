package dh;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.*;
import java.io.*;
import java.math.BigInteger;
import java.net.Socket;
import java.security.*;

public class BobDH {

    public static void main(String[] args) throws IOException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, ClassNotFoundException {
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

        Socket socket = new Socket("127.0.0.1",3333);
        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
        ObjetoTrocaDH objetoTrocaDH = (ObjetoTrocaDH) in.readObject();
        PublicKey publicKeyAlice = objetoTrocaDH.getPublicKey();

        IvParameterSpec iv = new IvParameterSpec("RandomInitVector".getBytes("UTF-8"));
        SecretKeySpec skeySpec = new SecretKeySpec("Bar1234bar12345".getBytes("UTF-8"), "AES");

        Cipher cipher= Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
        byte[] arquivoCriptografado= cipher.doFinal(barquivo);

        Cipher cipherRSA = Cipher.getInstance("RSA");
        cipherRSA.init(Cipher.ENCRYPT_MODE, publicKeyAlice);
        byte[] secretKeyAESEncrypt = cipherRSA.doFinal(chaveEAS);

        objetoTrocaDH= new ObjetoTrocaDH();
        objetoTrocaDH.setY(Ybob);
        objetoTrocaDH.setArquivoCriptografado(arquivoCriptografado);
        objetoTrocaDH.setNomeArquivo(chooserArquivo.getSelectedFile().getName());
        objetoTrocaDH.setSecretKeyAES(secretKeyAESEncrypt);
        ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
        outputStream.writeObject(objetoTrocaDH);

        socket.close();



    }
}
