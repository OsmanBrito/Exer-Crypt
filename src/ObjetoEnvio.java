import java.io.Serializable;
import java.security.PublicKey;

public class ObjetoEnvio implements Serializable{

    private PublicKey publicKey;
    private byte[] arquivo;
    private String nomeArquivo;

    public ObjetoEnvio() {
    }

    public ObjetoEnvio(PublicKey publicKey, byte[] arquivo, String nomeArquivo) {
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
}
