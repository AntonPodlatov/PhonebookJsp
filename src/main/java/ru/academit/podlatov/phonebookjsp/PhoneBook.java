package ru.academit.podlatov.phonebookjsp;

import ru.academit.podlatov.phonebookjsp.converter.ContactConverter;
import ru.academit.podlatov.phonebookjsp.service.ContactService;
import ru.academit.podlatov.phonebookjsp.dao.ContactDao;

public class PhoneBook {
    public static ContactDao contactDao = new ContactDao();
    public static ContactService contactService = new ContactService();
    public static ContactConverter contactConverter = new ContactConverter();
}
