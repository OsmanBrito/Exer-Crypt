import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.*;

public class Alice {

    public static void main(String[] args) throws IOException, NoSuchAlgorithmException, ClassNotFoundException, NoSuchPaddingException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {

        ServerSocket ss = new ServerSocket(3333);
        while (true) {
            Socket s = ss.accept();
            System.out.println("Conectado!");

            KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
            KeyPair myPair = kpg.generateKeyPair();
            PublicKey pubKey = myPair.getPublic();
            PrivateKey privateKey = myPair.getPrivate();

            ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());
            ChavePublica chavePublica = new ChavePublica(pubKey);
            out.writeObject(chavePublica);

            ObjectInputStream in = new ObjectInputStream(s.getInputStream());
            ObjetoEnvio objetoEnvio = (ObjetoEnvio) in.readObject();
            System.out.println("3. Leu o arquivo do socket.");
            System.out.println("NOME\n"+objetoEnvio.getNomeArquivo());
//
            //4. Criar o desencriptador
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] docDecriptografado = cipher.doFinal(objetoEnvio.getArquivo());
            File f = new File(objetoEnvio.getNomeArquivo());
            FileOutputStream fout = new FileOutputStream(f);
            fout.write(docDecriptografado);
            s.close();
        }
    }

}
