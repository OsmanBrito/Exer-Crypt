package Assinatura;

import java.security.Key;
import java.security.PublicKey;

public class ObjetoAssinatura {

    private PublicKey publicKey;
    private byte[] arquivo;
    private byte[] hash;
    private String nomeArquivo;
    private byte [] aesKey;

    public ObjetoAssinatura() {}

    public ObjetoAssinatura(PublicKey publicKey, byte[] arquivo, String nomeArquivo) {
        this.publicKey = publicKey;
        this.arquivo = arquivo;
        this.nomeArquivo = nomeArquivo;
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(PublicKey publicKey) {
        this.publicKey = publicKey;
    }

    public byte[] getArquivo() {
        return arquivo;
    }

    public void setArquivo(byte[] arquivo) {
        this.arquivo = arquivo;
    }

    public String getNomeArquivo() {
        return nomeArquivo;
    }

    public void setNomeArquivo(String nomeArquivo) {
        this.nomeArquivo = nomeArquivo;
    }

    public byte[] getAesKey() {
        return aesKey;
    }

    public void setAesKey(byte[] aesKey) {
        this.aesKey = aesKey;
    }

    public byte[] getHash() {
        return hash;
    }

    public void setHash(byte[] hash) {
        this.hash = hash;
    }
}
