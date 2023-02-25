package ru.academit.podlatov.phonebookjsp.service;

import org.apache.commons.lang3.StringUtils;
import ru.academit.podlatov.phonebookjsp.PhoneBook;
import ru.academit.podlatov.phonebookjsp.dao.ContactDao;
import ru.academit.podlatov.phonebookjsp.model.Contact;

import java.util.List;


public class ContactService {
    private final ContactDao dao = PhoneBook.contactDao;

    private boolean isExistContactWithPhone(String phone) {
        return dao.getAllContacts().stream()
                .anyMatch(contact -> contact.getPhone().equals(phone));
    }

    public ContactValidation validateContact(Contact contact) {
        ContactValidation contactValidation = new ContactValidation();
        contactValidation.setValid(true);

        if (StringUtils.isEmpty(contact.getFirstName())) {
            contactValidation.setValid(false);
            contactValidation.setFirstNameError("Поле Имя должно быть заполнено.");
        }

        if (StringUtils.isEmpty(contact.getLastName())) {
            contactValidation.setValid(false);
            contactValidation.setLastNameError("Поле Фамилия должно быть заполнено.");
        }

        if (StringUtils.isEmpty(contact.getPhone())) {
            contactValidation.setValid(false);
            contactValidation.setPhoneError("Поле Телефон должно быть заполнено.");
        }

        if (isExistContactWithPhone(contact.getPhone())) {
            contactValidation.setValid(false);
            contactValidation.setGlobalError("Номер телефона не должен дублировать другие номера в телефонной книге.");
        }

        return contactValidation;
    }

    public ContactValidation addContact(Contact contact) {
        ContactValidation contactValidation = validateContact(contact);

        if (contactValidation.isValid()) {
            dao.add(contact);
        }

        return contactValidation;
    }


    public void saveLastContact(Contact contact) {
        dao.saveLastContact(contact);
    }

    public List<Contact> getAllContacts(String filter) {
        return dao.getAllContacts(filter);
    }

    public Contact getLastContact() {
        return dao.getLastContact();
    }

    public void saveLastContactValidation(ContactValidation contactValidation) {
        dao.saveLastContactValidation(contactValidation);
    }

    public ContactValidation getLastContactValidation() {
        return dao.getLastContactValidation();
    }

    public RemovingActionResponse remove(List<Integer> contactsIds) {
        RemovingActionResponse response = new RemovingActionResponse();
        List<Integer> removed = dao.remove(contactsIds);

        if (removed.size() == 0) {
            response.setError(true);
            response.setMessage("Ничего не удалено. Нет таких контактов.");
            return response;
        }

        if (removed.size() < contactsIds.size()) {
            response.setError(false);
            response.setMessage("Удалены не все контакты. Некоторых уже не было.");
            return response;
        }

        response.setMessage("Успешно удалено.");
        response.setError(false);
        return response;
    }

    public void saveLastRemovingResponse(RemovingActionResponse removingResponse) {
        dao.saveLastRemovingResponse(removingResponse);
    }

    public RemovingActionResponse getLastRemovingResponse() {
        return dao.getLastRemovingResponse();
    }
}
