package pt.shared.ServerAndClientGeneral;

import java.io.Serializable;
import java.security.PublicKey;

public class Account implements Serializable {

    private String username;
    private String busTicketCode;
    private PublicKey pubK;
    private Double sessionId; // if null not logged in, logged in otherwise

    public Account(String username, String busTicketCode, PublicKey pubK) {
        this.username = username;
        this.busTicketCode = busTicketCode;
        this.pubK = pubK;
        sessionId = null;
    }

    public boolean equals(Object obj) {

        if (!this.getClass().equals(obj.getClass())) {
            System.out.println("different classes");
            return false;
        }

        Account externAcc = (Account) obj;
        if(externAcc.getUsername().equals(this.username) &&
                externAcc.getBusTicketCode().equals(this.busTicketCode) &&
                externAcc.getPubK().toString().equals(this.getPubK().toString())) {
            return true;
        }
        return false;
    }

    public String getUsername() {
        return this.username;
    }

    public String getBusTicketCode() {
        return this.busTicketCode;
    }

    public PublicKey getPubK() {
        return this.pubK;
    }

    public void setSessionId(Double sessionId) {
        this.sessionId = sessionId;
    }

    public Double getSessionId() {
        return sessionId;
    }
}
