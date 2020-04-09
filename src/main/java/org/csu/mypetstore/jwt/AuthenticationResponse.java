package org.csu.mypetstore.jwt;

public class AuthenticationResponse {
    public final String jwt;
    public String message;
    public final boolean error = false;

    public AuthenticationResponse(String jwt) {
        this.jwt = jwt;
        this.message = "Enjoy your token";
    }

    public AuthenticationResponse(String jwt, String message) {
        this.jwt = jwt;
        this.message = message;
    }

    public String getJwt() {
        return jwt;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
