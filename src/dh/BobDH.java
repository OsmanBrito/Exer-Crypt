package DH;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.*;
import java.io.*;
import java.math.BigInteger;
import java.net.Socket;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class BobDH {

    public static void main(String[] args) throws IOException, ClassNotFoundException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
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
        ObjetoTrocaDH objetoTrocaDH  = (ObjetoTrocaDH) in.readObject();
        BigInteger A = objetoTrocaDH.getA();
        BigInteger Q = objetoTrocaDH.getQ();
        BigInteger YAlice = objetoTrocaDH.getY();

        BigInteger XBob = UtilDH.geraNumeroMenorQue(Q);
        BigInteger YBob = A.modPow(XBob, Q);
        BigInteger K = YAlice.modPow(XBob,Q);
        System.out.println("[BOB] K = "+K);
        byte[] KBarr = K.toByteArray();

        byte[] chaveAES = new byte[] { KBarr[0], KBarr[1], KBarr[2], KBarr[3], KBarr[4], KBarr[5], KBarr[6], KBarr[7],
                KBarr[0], KBarr[1], KBarr[2], KBarr[3], KBarr[4], KBarr[5], KBarr[6], KBarr[7]};

        SecretKeySpec ks = new SecretKeySpec(chaveAES, "AES");

        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, ks);
        byte[] arquivoCripto = cipher.doFinal(barquivo);

        objetoTrocaDH = new ObjetoTrocaDH();
        objetoTrocaDH.setY(YBob);
        objetoTrocaDH.setArquivoCriptografado(arquivoCripto);
        objetoTrocaDH.setNomeArquivo(chooserArquivo.getSelectedFile().getName());

        ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());
        out.writeObject(objetoTrocaDH);
    }

}
