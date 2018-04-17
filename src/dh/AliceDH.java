package DH;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class AliceDH{

    public static void main(String[] args) throws IOException, ClassNotFoundException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        BigInteger[] QA = UtilDH.geraQA(64);
        BigInteger Q = QA[0];
        BigInteger A = QA[1];
        System.out.println("");

        BigInteger XAlice = UtilDH.geraNumeroMenorQue(Q);
        BigInteger YAlice = A.modPow(XAlice, Q);

        ServerSocket ss = new ServerSocket(3333);
        Socket s = ss.accept();

        ObjetoTrocaDH objetoTrocaDH = new ObjetoTrocaDH();
        objetoTrocaDH.setA(A);
        objetoTrocaDH.setQ(Q);
        objetoTrocaDH.setY(YAlice);
        ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());
        out.writeObject(objetoTrocaDH);

        ObjectInputStream in = new ObjectInputStream(s.getInputStream());
        objetoTrocaDH = (ObjetoTrocaDH) in.readObject();


        BigInteger YBob = objetoTrocaDH.getY();
        BigInteger K = YBob.modPow(XAlice, Q);

        System.out.println("[ALICE] K = "+K);

        byte[] arquivoCripto = objetoTrocaDH.getArquivoCriptografado();

        byte[] KBarr = K.toByteArray();

        byte[] chaveAES = new byte[] { KBarr[0], KBarr[1], KBarr[2], KBarr[3], KBarr[4], KBarr[5], KBarr[6], KBarr[7],
                KBarr[0], KBarr[1], KBarr[2], KBarr[3], KBarr[4], KBarr[5], KBarr[6], KBarr[7]};

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
