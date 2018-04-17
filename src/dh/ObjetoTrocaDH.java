package DH;

import java.io.Serializable;
import java.math.BigInteger;

public class ObjetoTrocaDH implements Serializable{

    private byte[] arquivoCriptografado;
    private BigInteger Q;
    private BigInteger A;
    private BigInteger Y;
    private String nomeArquivo;

    public byte[] getArquivoCriptografado() {
        return arquivoCriptografado;
    }

    public void setArquivoCriptografado(byte[] arquivoCriptografado) {
        this.arquivoCriptografado = arquivoCriptografado;
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

    public String getNomeArquivo() {
        return nomeArquivo;
    }

    public void setNomeArquivo(String nomeArquivo) {
        this.nomeArquivo = nomeArquivo;
    }
}