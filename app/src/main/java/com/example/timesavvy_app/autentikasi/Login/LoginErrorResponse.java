package com.example.timesavvy_app.autentikasi.Login;

import java.util.List;

public class LoginErrorResponse {
    private String message;
    private Errors errors;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Errors getErrors() {
        return errors;
    }

    public void setErrors(Errors errors) {
        this.errors = errors;
    }

    public class Errors {
        private List<String> email;

        public List<String> getEmail() {
            return email;
        }

        public void setEmail(List<String> email) {
            this.email = email;
        }
    }
}

