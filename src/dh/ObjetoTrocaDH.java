package dh;

import java.io.Serializable;
import java.math.BigInteger;
import java.security.PublicKey;

public class ObjetoTrocaDH implements Serializable{

    private byte[] arquivoCriptografado;
    private BigInteger Q;
    private BigInteger A;
    private BigInteger Y;
    private PublicKey publicKey;
    private String nomeArquivo;
    private byte[] secretKeyAES;

    public ObjetoTrocaDH() {
    }

    public String getNomeArquivo() {
        return nomeArquivo;
    }

    public void setNomeArquivo(String nomeArquivo) {
        this.nomeArquivo = nomeArquivo;
    }

    public byte[] getArquivoCriptografado() {
        return arquivoCriptografado;
    }

    public void setArquivoCriptografado(byte[] arquivoCriptografado) {
        this.arquivoCriptografado = arquivoCriptografado;
    }

    public byte[] getSecretKeyAES() {
        return secretKeyAES;
    }

    public void setSecretKeyAES(byte[] secretKeyAES) {
        this.secretKeyAES = secretKeyAES;
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(PublicKey publicKey) {
        this.publicKey = publicKey;
    }

    public BigInteger getQ() {
        return Q;
    }

    public void setQ(BigInteger q) {
        Q = q;
    }

    public BigInteger getA() {
        return A;
    }

    public void setA(BigInteger a) {
        A = a;
    }

    public BigInteger getY() {
        return Y;
    }

    public void setY(BigInteger y) {
        Y = y;
    }
}
