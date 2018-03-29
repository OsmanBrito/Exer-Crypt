import javax.crypto.*;
import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.security.*;
import java.util.Base64;

//usar BIGINT// encontrar numero primo e raiz primitiva do numero primo

public class Bob {

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
        ChavePublica chavePublica = (ChavePublica) in.readObject();
//        KeyGenerator kgen = KeyGenerator.getInstance("AES");
//        kgen.init(128);
//        Key aesKey = kgen.generateKey();


        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, chavePublica.getPublicKey());
//        System.out.println("arquivo antes = "+barquivo.toString());
        byte[] b_cripto = cipher.doFinal(barquivo);
        System.out.println("criptografado = "+b_cripto.toString());
////
        ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());
        ObjetoEnvio obj = new ObjetoEnvio(chavePublica.getPublicKey(), b_cripto, arquivo.getName());
        out.writeObject(obj);
        out.close();
        s.close();


    }

}
