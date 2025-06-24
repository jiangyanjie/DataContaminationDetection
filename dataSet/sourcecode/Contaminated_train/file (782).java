package com.ahormoz;

import java.io.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * An implementation of the ContactManager interface.
 */
public class ContactManagerImpl implements ContactManager {

    private Map<Integer, Contact> contactsIDMap;
    private Map<Integer, Meeting> meetingsIDMap;
    private AtomicInteger contactIDGenerator;
    private AtomicInteger meetingIDGenerator;
    private final String DATA_FILE_NAME = "data.ser";


    /**
     * Class constructor initialises a new ContactManager object with saved data (if there is any).
     * Also adds a shutdown hook to save data on program exit.
     */
    @SuppressWarnings("unchecked")
    public ContactManagerImpl() {
        File dataFile = new File(DATA_FILE_NAME);

        if (dataFile.exists()) {

            try {
                FileInputStream fileIn = new FileInputStream(DATA_FILE_NAME);
                ObjectInputStream objectIn = new ObjectInputStream(fileIn);
                contactsIDMap = (HashMap<Integer, Contact>) objectIn.readObject();
                meetingsIDMap = (HashMap<Integer, Meeting>) objectIn.readObject();
                contactIDGenerator = (AtomicInteger) objectIn.readObject();
                meetingIDGenerator = (AtomicInteger) objectIn.readObject();
                objectIn.close();
            }
            catch (IOException | ClassNotFoundException ex){
                ex.printStackTrace();
            }
        } else {
            contactsIDMap = new HashMap<>();
            meetingsIDMap = new HashMap<>();
            contactIDGenerator = new AtomicInteger();
            meetingIDGenerator = new AtomicInteger();
        }
        Runtime.getRuntime().addShutdownHook(flushAtShutdown());
    }

    /**
     * @inheritDoc
     *
     * This method (addFutureMeeting) can not be used an infinite number of times due to the
     * limitations of the int variable used to ID each meeting.
     * One instance of this program should therefore not be used as a shared calendar accessed
     * by numerous people, but this should not be a problem for the purpose of this application
     * as this limitation still allows for billions of method calls.
     */
    public int addFutureMeeting(Set<Contact> contacts, Calendar date) {
        if (Calendar.getInstance().after(date) || !contactsIDMap.values().containsAll(contacts))
            throw new IllegalArgumentException();
        Meeting meeting1 = new FutureMeetingImpl(meetingIDGenerator.getAndIncrement(), date, contacts);
        meetingsIDMap.put(meeting1.getId(), meeting1);
        return meeting1.getId();
    }

    public FutureMeeting getFutureMeeting(int id) {
        Meeting outputMeeting = meetingsIDMap.get(id);

        if (outputMeeting == null)
            return null;
        if (Calendar.getInstance().after(outputMeeting.getDate()))
            throw new IllegalArgumentException();
        return (FutureMeeting) outputMeeting;
    }

    public Meeting getMeeting(int id) {
        return meetingsIDMap.get(id);
    }

    public List<Meeting> getFutureMeetingList(Contact contact) {
        // Checks contact exists
        if (!contactsIDMap.values().contains(contact))
            throw new IllegalArgumentException();

        //Creates a list with all meetings which contain the query contact and are in the future.
        List<Meeting> outputList = new ArrayList<>();
        for (Meeting item : meetingsIDMap.values()) {
            if (item.getContacts().contains(contact) && Calendar.getInstance().before(item.getDate()))
                outputList.add(item);
        }

        //Deletes any duplicates (meetings with the same set of contacts and the same date but possibly
        //different ids)
        List <Integer> toDelete = new ArrayList<>();

        for (int i = 0; i < outputList.size(); i++){
            for (int n = 0; n < outputList.size(); n++){
                if (i != n && !toDelete.contains(i)) {
                    if (outputList.get(i).getDate().compareTo(outputList.get(n).getDate()) == 0){
                        if (outputList.get(i).getContacts().equals(outputList.get(n).getContacts()))
                            toDelete.add(n);
                    }
                }
            }
        }

        for (Integer n : toDelete)
            outputList.remove((int)n);

        //Sorts the list into chronological order.
        if (!outputList.isEmpty()){
            Collections.sort(outputList, new Comparator<Meeting>() {
                @Override
                public int compare(Meeting meeting0, Meeting meeting1) {
                    return meeting0.getDate().compareTo(meeting1.getDate());
                }
            });
        }
        return outputList;
    }

    public List<Meeting> getFutureMeetingList(Calendar date) {
        //Creates a list with all meetings which take place on the query date (excluding time values).
        List<Meeting> outputList = new ArrayList<>();
        for (Meeting item : meetingsIDMap.values()) {
            if (item.getDate().get(Calendar.YEAR) == date.get(Calendar.YEAR)){
                if (item.getDate().get(Calendar.MONTH) == date.get(Calendar.MONTH)){
                    if (item.getDate().get(Calendar.DAY_OF_MONTH) == date.get(Calendar.DAY_OF_MONTH))
                        outputList.add(item);
                }
            }
        }
        //Deletes any duplicates (meetings with the same set of contacts and the same date but possibly
        //different ids)
        List <Integer> toDelete = new ArrayList<>();

        for (int i = 0; i < outputList.size(); i++){
            for (int n = 0; n < outputList.size(); n++){
                if (i != n && !toDelete.contains(i)) {
                    if (outputList.get(i).getDate().compareTo(outputList.get(n).getDate()) == 0){
                        if (outputList.get(i).getContacts().equals(outputList.get(n).getContacts()))
                            toDelete.add(n);
                    }
                }
            }
        }

        for (Integer n : toDelete)
            outputList.remove((int)n);

        //Sorts the list into chronological order.
        if (!outputList.isEmpty()){
            Collections.sort(outputList, new Comparator<Meeting>() {
                @Override
                public int compare(Meeting meeting0, Meeting meeting1) {
                    return meeting0.getDate().compareTo(meeting1.getDate());
                }
            });
        }
        return outputList;
    }

    public List<PastMeeting> getPastMeetingList(Contact contact) {
        // Checks contact exists
        if (!contactsIDMap.values().contains(contact))
            throw new IllegalArgumentException();

        //Creates a list with all meetings which contain the query contact and are in the past.
        List<PastMeeting> outputList = new ArrayList<>();
        for (Meeting item : meetingsIDMap.values()) {
            if (item.getContacts().contains(contact) && Calendar.getInstance().after(item.getDate()))
                outputList.add((PastMeetingImpl)item);
        }

        //Deletes any duplicates (meetings with the same set of contacts and the same date but possibly
        //different ids)
        List <Integer> toDelete = new ArrayList<>();

        for (int i = 0; i < outputList.size(); i++){
            for (int n = 0; n < outputList.size(); n++){
                if (i != n && !toDelete.contains(i)) {
                    if (outputList.get(i).getDate().compareTo(outputList.get(n).getDate()) == 0){
                        if (outputList.get(i).getContacts().equals(outputList.get(n).getContacts()))
                            toDelete.add(n);
                    }
                }
            }
        }

        for (Integer n : toDelete)
            outputList.remove((int)n);

        //Sorts the list into chronological order.
        if (!outputList.isEmpty()){
            Collections.sort(outputList, new Comparator<Meeting>() {
                @Override
                public int compare(Meeting meeting0, Meeting meeting1) {
                    return meeting0.getDate().compareTo(meeting1.getDate());
                }
            });
        }
        return outputList;
    }

    /**
     * @inheritDoc
     *
     * This method (addNewPastMeeting) can not be used an infinite number of times due to the
     * limitations of the int variable used to ID each meeting.
     * One instance of this program should therefore not be used as a shared calendar accessed
     * by numerous people, but this should not be a problem for the purpose of this application
     * as this limitation still allows for billions of method calls.
     */
    public void addNewPastMeeting(Set<Contact> contacts, Calendar date, String text) {
        if (contacts == null || date == null || text == null)
            throw new NullPointerException();

        if (contacts.isEmpty() || !contactsIDMap.values().containsAll(contacts))
            throw new IllegalArgumentException();

        Meeting outputMeeting = new PastMeetingImpl(meetingIDGenerator.getAndIncrement(), date, contacts, text);
        meetingsIDMap.put(outputMeeting.getId(), outputMeeting);
    }

    public void addMeetingNotes(int id, String text) {
        if (text == null)
            throw new NullPointerException();
        if (!meetingsIDMap.containsKey(id))
            throw new IllegalArgumentException();
        Meeting meeting = meetingsIDMap.get(id);
        if (Calendar.getInstance().before(meeting.getDate()))
            throw new IllegalStateException();
        Meeting outputMeeting;
        if (meeting instanceof FutureMeetingImpl)
            outputMeeting = new PastMeetingImpl(meeting.getId(), meeting.getDate(), meeting.getContacts(), text);
        else
            outputMeeting = new PastMeetingImpl(meeting.getId(), meeting.getDate(), meeting.getContacts(), ((PastMeetingImpl) meeting).getNotes() + text);
        meetingsIDMap.put(outputMeeting.getId(), outputMeeting);
    }

    /**
     * @inheritDoc
     *
     * This method (addContact) can not be used an infinite number of times due to the
     * limitations of the int variable used to ID each contact.
     * One instance of this program should therefore not be used as a shared calendar accessed
     * by numerous people, but this should not be a problem for the purpose of this application
     * as this limitation still allows for billions of method calls.
     */
    public void addNewContact(String name, String notes) {
        if (name == null || notes == null)
            throw  new NullPointerException();
        Contact contact1 = new ContactImpl(contactIDGenerator.getAndIncrement(), name);
        contact1.addNotes(notes);
        contactsIDMap.put(contact1.getId(), contact1);
    }


    public Set<Contact> getContacts(int... ids) {
        Set<Contact> output = new HashSet<>();
        for (int id : ids){

            if (!contactsIDMap.containsKey(id))
                throw new IllegalArgumentException();
            output.add(contactsIDMap.get(id));
        }
        return output;
    }

    public Set<Contact> getContacts(String name) {
        if (name == null || name.equals(""))
            throw new NullPointerException();
        Set<Contact> output = new HashSet<>();

        Contact[] contactsArray = contactsIDMap.values().toArray(new Contact[contactsIDMap.size()]);

        for (Contact value : contactsArray){
           if (value.getName().contains(name)){
                output.add(value);
            }

        }
        return output;
  }

    public void flush() {
        FileOutputStream fileOut;
        ObjectOutputStream objectOut;

        try {
            fileOut = new FileOutputStream(DATA_FILE_NAME);
            objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(contactsIDMap);
            objectOut.writeObject(meetingsIDMap);
            objectOut.writeObject(contactIDGenerator);
            objectOut.writeObject(meetingIDGenerator);
            objectOut.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private Thread flushAtShutdown(){
        return new Thread(new Runnable() {
            @Override
            public void run() {
                flush();
            }
        });
    }


}
