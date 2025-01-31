package com.suraj.todo.todowebapp.model;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class SignUpDTO {
    @NotBlank(message = "Name cannot be blank!")
    @Size(min = 3, max = 15, message = "Name must be 3-15 characters long!")
    private String name;
    @NotBlank(message = "Email cannot be blank!")
    @Email(message = "Invalid Email!")
    private String email;
    @NotBlank(message = "Password cannot be blank!")
    @Size(min = 5, message = "Password must be minimum 5 characters long!")
    private String password;
    @AssertTrue(message = "You must agree to terms and conditions!")
    private boolean agreed;

    public SignUpDTO() {
    }

    public SignUpDTO(String name, String email, String password, boolean agreed) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.agreed = agreed;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAgreed() {
        return agreed;
    }

    public void setAgreed(boolean agreed) {
        this.agreed = agreed;
    }

    @Override
    public String toString() {
        return "SignUpDTO{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", agreed=" + agreed +
                '}';
    }
}
