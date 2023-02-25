package ru.academit.podlatov.phonebookjsp.servlet;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.academit.podlatov.phonebookjsp.PhoneBook;
import ru.academit.podlatov.phonebookjsp.service.ContactService;
import ru.academit.podlatov.phonebookjsp.service.RemovingActionResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Serial;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@WebServlet(name = "RemoveContactServlet", value = "/remove")
public class RemoveContactServlet extends HttpServlet {
    private final static ContactService contactService = PhoneBook.contactService;

    @Serial
    private static final long serialVersionUID = 1134134141L;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) {
        try (BufferedReader reader = req.getReader()) {
            String idsParams = reader.lines().collect(Collectors.joining(System.lineSeparator()));
            RemovingActionResponse removingResponse;

            if (idsParams.isBlank()) {
                removingResponse = new RemovingActionResponse(true, "Нужно передать список контактов для удаления.");
            } else {
                List<Integer> ids = Arrays.stream(idsParams.split("&"))
                        .map(s -> Integer.valueOf(s.substring(s.indexOf("=") + 1)))
                        .toList();

                removingResponse = contactService.remove(ids);
            }

            if (removingResponse.isError()) {
                contactService.saveLastRemovingResponse(removingResponse);
            } else {
                contactService.saveLastRemovingResponse(new RemovingActionResponse());
            }

            res.sendRedirect("/phonebook");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}