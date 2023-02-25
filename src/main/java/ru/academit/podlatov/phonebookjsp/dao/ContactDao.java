package ru.academit.podlatov.phonebookjsp.dao;

import ru.academit.podlatov.phonebookjsp.model.Contact;
import ru.academit.podlatov.phonebookjsp.service.ContactValidation;
import ru.academit.podlatov.phonebookjsp.service.RemovingActionResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;

public class ContactDao {
    private final List<Contact> contacts = new ArrayList<>();
    private final AtomicInteger idSequence = new AtomicInteger(0);

    private Contact lastContact = new Contact();
    private ContactValidation lastContactValidation = new ContactValidation();
    private RemovingActionResponse lastRemovingResponse = new RemovingActionResponse();

    public ContactDao() {
        contacts.addAll(List.of(
                new Contact(getNewId(), "Leonid", "Stepanov", "9823236723"),
                new Contact(getNewId(), "Massimo", "Magrini", "1239812312"),
                new Contact(getNewId(), "Marcos", "Ortega", "9143236723"),
                new Contact(getNewId(), "Rodger", "Williams", "12348934555"),
                new Contact(getNewId(), "Gomer", "Gram", "12354093344")
        ));
    }

    private int getNewId() {
        return idSequence.addAndGet(1);
    }

    public List<Contact> getAllContacts() {
        return contacts;
    }

    public void add(Contact contact) {
        contact.setId(getNewId());
        contacts.add(contact);
    }

    public void saveLastContact(Contact contact) {
        this.lastContact = contact;
    }

    public Contact getLastContact() {
        return lastContact;
    }

    public void saveLastContactValidation(ContactValidation contactValidation) {
        this.lastContactValidation = contactValidation;
    }

    public ContactValidation getLastContactValidation() {
        return lastContactValidation;
    }

    public List<Contact> getAllContacts(String filter) {
        if (filter == null || filter.length() == 0) {
            return contacts;
        }

        Predicate<Contact> contactAnyFieldContainsTerm = contact ->
                contact.getFirstName().toLowerCase().contains(filter.toLowerCase()) ||
                        contact.getLastName().toLowerCase().contains(filter.toLowerCase()) ||
                        contact.getPhone().toLowerCase().contains(filter.toLowerCase());

        return contacts.stream().filter(contactAnyFieldContainsTerm).toList();
    }

    public List<Integer> remove(List<Integer> ids) {
        List<Integer> removedContactsIds = new ArrayList<>();

        contacts.removeIf(contact -> {
            if (ids.contains(contact.getId())) {
                removedContactsIds.add(contact.getId());
                return true;
            }
            return false;
        });

        return removedContactsIds;
    }

    public void saveLastRemovingResponse(RemovingActionResponse removingResponse) {
        this.lastRemovingResponse = removingResponse;
    }

    public RemovingActionResponse getLastRemovingResponse() {
        return lastRemovingResponse;
    }
}
