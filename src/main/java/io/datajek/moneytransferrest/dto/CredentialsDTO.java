package io.datajek.moneytransferrest.dto;

public class CredentialsDTO {
    private String username;
    private String password;

    public CredentialsDTO() {
    }

    public CredentialsDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() { return username; }

    public String getPassword() { return password; }
}
