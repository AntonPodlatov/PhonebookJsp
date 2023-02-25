package ru.academit.podlatov.phonebookjsp.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.academit.podlatov.phonebookjsp.PhoneBook;
import ru.academit.podlatov.phonebookjsp.service.ContactService;

import java.io.IOException;
import java.io.Serial;

@WebServlet(name = "StartServlet", value = "/phonebook")
public class StartServlet extends HttpServlet {
    @Serial
    private static final long serialVersionUID = 453312326896L;

    private final ContactService contactService = PhoneBook.contactService;

    protected void doGet(HttpServletRequest req, HttpServletResponse res) {
        String filter = req.getParameter("filter");

        req.setAttribute("filter", filter);
        req.setAttribute("currentContact", contactService.getLastContact());
        req.setAttribute("contactList", contactService.getAllContacts(filter));
        req.setAttribute("removingResponse", contactService.getLastRemovingResponse());
        System.out.println(contactService.getLastRemovingResponse().isError());
        System.out.println(contactService.getLastRemovingResponse().getMessage());
        req.setAttribute("contactValidation", contactService.getLastContactValidation());

        try {
            req.getRequestDispatcher("phonebook.jsp").forward(req, res);
        } catch (ServletException | IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
