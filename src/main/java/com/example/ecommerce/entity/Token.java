package com.example.ecommerce.entity;

import com.example.ecommerce.enums.TokenType;
import jakarta.persistence.*;

@Entity
@Table(name = "tokens", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"accessToken"}),
        @UniqueConstraint(columnNames = {"refreshToken"})
})
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false, length = 512)
    private String accessToken;

    @Column(unique = true, nullable = false, length = 512)
    private String refreshToken;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TokenType tokenType = TokenType.BEARER;

    @Column(nullable = false)
    private boolean revoked;

    @Column(nullable = false)
    private boolean expired;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    protected Token() {}

    public Token(String accessToken, String refreshToken, TokenType tokenType, boolean revoked, boolean expired, User user) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.tokenType = tokenType;
        this.revoked = revoked;
        this.expired = expired;
        this.user = user;
    }

    public Integer getId() {
        return id;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public TokenType getTokenType() {
        return tokenType;
    }

    public boolean isRevoked() {
        return revoked;
    }

    public boolean isExpired() {
        return expired;
    }

    public User getUser() {
        return user;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public void setTokenType(TokenType tokenType) {
        this.tokenType = tokenType;
    }

    public void setRevoked(boolean revoked) {
        this.revoked = revoked;
    }

    public void setExpired(boolean expired) {
        this.expired = expired;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Token{id=" + id +
                ", tokenType=" + tokenType +
                ", revoked=" + revoked +
                ", expired=" + expired +
                ", userId=" + (user != null ? user.getId() : "null") + "}";
    }
}
