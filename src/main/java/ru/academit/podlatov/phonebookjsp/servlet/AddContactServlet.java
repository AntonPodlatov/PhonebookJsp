package ru.academit.podlatov.phonebookjsp.servlet;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.academit.podlatov.phonebookjsp.PhoneBook;
import ru.academit.podlatov.phonebookjsp.converter.ContactConverter;
import ru.academit.podlatov.phonebookjsp.model.Contact;
import ru.academit.podlatov.phonebookjsp.service.ContactService;
import ru.academit.podlatov.phonebookjsp.service.ContactValidation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Serial;
import java.util.stream.Collectors;

@WebServlet(name = "AddContactServlet", value = "/add")
public class AddContactServlet extends HttpServlet {
    @Serial
    private static final long serialVersionUID = 113412326896L;

    private final ContactService contactService = PhoneBook.contactService;
    private final ContactConverter contactConverter = PhoneBook.contactConverter;

    protected void doPost(HttpServletRequest req, HttpServletResponse res) {
        try (BufferedReader reader = req.getReader()) {
            String contactParams = reader.lines().collect(Collectors.joining(System.lineSeparator()));
            Contact contact = contactConverter.convertFormStringParam(contactParams);

            ContactValidation contactValidation = contactService.addContact(contact);
            contactService.saveLastContactValidation(contactValidation);

            if (contactValidation.isValid()) {
                contactService.saveLastContact(new Contact());
            } else {
                contactService.saveLastContact(contact);
            }

            res.sendRedirect("/phonebook");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}