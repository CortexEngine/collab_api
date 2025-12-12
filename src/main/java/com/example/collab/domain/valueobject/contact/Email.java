package com.example.collab.domain.valueobject.contact;

import lombok.Value;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import com.example.collab.exception.business.InvalidDocumentException;

@Value
public class Email {
    String email;

    public Email(String email) {

        if (email == null || email.isBlank()) {

            throw new InvalidDocumentException("Email address must be provided.");

        }
        if (!isValid(email)) {

            throw new InvalidDocumentException("Invalid format: " + email);

        }

        this.email = email;

    }

    private boolean isValid(String email) {

        try {

            InternetAddress address = new InternetAddress(email);

            address.validate();

            return true;

        } catch (AddressException ex) {

            return false;

        }

    }

}
