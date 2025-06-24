package com.gmail.schcrabicus.prospring3.tutorials.ch11.mybatis.service.impl;

import com.gmail.schcrabicus.prospring3.tutorials.ch11.mybatis.domain.Contact;
import com.gmail.schcrabicus.prospring3.tutorials.ch11.mybatis.domain.ContactTelDetail;
import com.gmail.schcrabicus.prospring3.tutorials.ch11.mybatis.domain.Hobby;
import com.gmail.schcrabicus.prospring3.tutorials.ch11.mybatis.domain.SearchCriteria;
import com.gmail.schcrabicus.prospring3.tutorials.ch11.mybatis.persistence.ContactHobbyDetailMapper;
import com.gmail.schcrabicus.prospring3.tutorials.ch11.mybatis.persistence.ContactMapper;
import com.gmail.schcrabicus.prospring3.tutorials.ch11.mybatis.persistence.ContactTelDetailMapper;
import com.gmail.schcrabicus.prospring3.tutorials.ch11.mybatis.persistence.HobbyMapper;
import com.gmail.schcrabicus.prospring3.tutorials.ch11.mybatis.service.ContactService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: schcrabicus
 * Date: 11.02.13
 * Time: 7:28
 * To change this template use File | Settings | File Templates.
 */
@Service("contactService")
@Repository
@Transactional
public class ContactServiceImpl implements ContactService {

    private Logger log = Logger.getLogger(ContactServiceImpl.class);

    @Autowired
    private ContactMapper contactMapper;

    @Autowired
    private HobbyMapper hobbyMapper;

    @Autowired
    private ContactTelDetailMapper contactTelDetailMapper;

    @Autowired
    private ContactHobbyDetailMapper contactHobbyDetailMapper;

    @Override
    @Transactional(readOnly = true)
    public List<Contact> findAll() {
        return contactMapper.findAll();
    }

    @Override
    public List<Contact> findAllWithDetail() {
        List<Contact> contacts = contactMapper.findAllWithDetail();
        /*for (Contact contact : contacts){
            populateContactTelDetails(contact);
        }*/

        return contacts;
    }

    @Override
    public Contact findById(Long id) {
        return contactMapper.findById(id);
    }

    @Override
    public Contact save(Contact contact) {
        contactMapper.save(contact);
        Long contactId = contact.getId();
        if (contact.getContactTelDetails() != null) {
            for (ContactTelDetail contactTelDetail:
                    contact.getContactTelDetails()) {
                contactTelDetail.setContact(contact);
                contactTelDetailMapper.save(contactTelDetail);
            }
        }
        if (contact.getHobbies() != null) {
            for (Hobby hobby: contact.getHobbies()) {
                /*hobby.setContacts(new HashSet<Contact>());
                hobby.getContacts().add(contact);*/
                if ( hobby.getContacts() == null || ! hobby.getContacts().contains(contact) ){
                    if ( hobby.getContacts() == null ){
                        hobby.setContacts(new HashSet<Contact>());
                    }
                    hobby.getContacts().add(contact);
                }
                log.info("saving hobby = " + hobby);
                if ( !hobbyMapper.exists(hobby)) {
                    hobbyMapper.saveOrUpdate(hobby);
                }
                contactHobbyDetailMapper.save(hobby);
            }
        }
        return contact;
    }

    @Override
    public void delete(Contact contact) {
        Long id = contact.getId();
        contactHobbyDetailMapper.deleteHobbyDetailForContact(id);
        contactTelDetailMapper.deleteTelDetailForContact(id);
        contactMapper.delete(contact);
    }

    @Override
    public void update(Contact contact) {
        contactMapper.update(contact);
        Long contactId = contact.getId();
        Hobby contactHobbyDetail;
// List storing orphan ids of contact tel details
        List<Long> ids = new ArrayList<Long>();
        // Retrieve existing telephones for contact
        List<ContactTelDetail> oldContactTelDetails =
                contactTelDetailMapper.selectTelDetailForContact(contactId);
        for (ContactTelDetail contactTelDetail: oldContactTelDetails) {
            ids.add(contactTelDetail.getId());
        }

        // Update telephone details
        if (contact.getContactTelDetails() != null) {
            for (ContactTelDetail contactTelDetail:
                    contact.getContactTelDetails()) {
                if (contactTelDetail.getId() == null) {
                    contactTelDetailMapper.save(contactTelDetail);
                } else {
                    contactTelDetailMapper.updateContactTelDetail(contactTelDetail);
                    ids.remove(contactTelDetail.getId());
                }
            }
            if (ids.size() > 0) {
                contactTelDetailMapper.deleteOrphanContactTelDetail(ids);
            }
        }

        // Update hobby details
        contactHobbyDetailMapper.deleteHobbyDetailForContact(contact.getId());
        if (contact.getHobbies() != null) {
            for (Hobby hobby: contact.getHobbies()) {
                contactHobbyDetail = new Hobby();
                //contactHobbyDetail.setContactId(contactId);
                contactHobbyDetail.setHobbyId(hobby.getHobbyId());
                contactHobbyDetail.setContacts(new HashSet<Contact>());
                contactHobbyDetail.getContacts().add(contact);
                if ( !hobbyMapper.exists(contactHobbyDetail)) {
                    hobbyMapper.saveOrUpdate(contactHobbyDetail);
                }
                contactHobbyDetailMapper.save(contactHobbyDetail);
            }
        }

    }

    @Override
    public List<Contact> findByFirstNameAndLastName(String firstName, String lastName) {

        SearchCriteria criteria = new SearchCriteria();
        criteria.setFirstName(firstName);
        criteria.setLastName(lastName);

        List<Contact> contacts = contactMapper.findByFirstNameAndLastName(criteria);
        for (Contact contactTemp: contacts) {
            populateContactTelDetails(contactTemp);
        }

        return contacts;
    }

    private void populateContactTelDetails(Contact contact){
        if (contact.getContactTelDetails() != null ){
            for( ContactTelDetail contactTelDetail : contact.getContactTelDetails() ){
                contactTelDetail.setContact(contact);
            }
        }
    }
}
