package com.example.belajarRestApi.controllers;

import com.example.belajarRestApi.model.Contact;
import com.example.belajarRestApi.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/contacts")
public class ContactController {

    @Autowired
    private ContactService contactService;

    @GetMapping
    public List<Contact> getAllContacts() {
        return contactService.getAllContacts();
    }

    @GetMapping("/{id}")
    public Optional<Contact> getContactById(@PathVariable Long id) {
        return contactService.getContactById(id);
    }

    @PostMapping
    public Contact saveContact(@RequestBody Contact contact) {
        return contactService.saveContact(contact);
    }

    @PutMapping("/{id}")
    public Contact updateContact(@PathVariable Long id, @RequestBody Contact contact) {
        contact.setId(id);
        return contactService.saveContact(contact);
    }

    @DeleteMapping("/{id}")
    public void deleteContact(@PathVariable Long id) {
        contactService.deleteContact(id);
    }
}
