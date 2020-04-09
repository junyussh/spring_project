package org.csu.mypetstore.jwt;

import io.swagger.annotations.ApiModelProperty;

public class AuthenticationRequest {
    @ApiModelProperty(value = "Username",required = true)
    private String username;
    @ApiModelProperty(value = "Password",required = true)
    private String password;

    public AuthenticationRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public AuthenticationRequest() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
