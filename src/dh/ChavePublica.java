package dh;

import java.io.Serializable;
import java.security.PublicKey;

public class ChavePublica implements Serializable {

    private PublicKey publicKey;

    public ChavePublica(PublicKey publicKey) {
        this.publicKey = publicKey;
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(PublicKey publicKey) {
        this.publicKey = publicKey;
    }
}
