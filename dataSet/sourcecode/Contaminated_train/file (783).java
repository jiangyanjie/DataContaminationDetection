package com.ahormoz;

import org.junit.*;
import org.junit.rules.ExpectedException;

import java.io.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import static junit.framework.Assert.*;


/**
 * Test Class for ContactManagerImpl
 */
public class ContactManagerImplTest {

    /**
     * Declares all variables needed for the tests.
     */
    private ContactManager testContactManager;
    private Contact falseContact;
    private Contact tempContact;
    private Contact contact0;
    private Contact contact1;
    private Contact contact2;
    private Contact contact3;
    private Contact contact4;
    private Contact contact5;
    private Contact contact6;
    private Contact contact7;
    private Contact contact8;
    private Set<Contact> tempContactSet;
    private Object[] tempObjectsArray;
    private final Set<Contact> TEST_CONTACT_SET = new HashSet<>();
    private final Set<Contact> TEST_CONTACT_SET_WITH_FALSE_CONTACT = new HashSet<>();
    int presentYear = GregorianCalendar.getInstance().get(Calendar.YEAR);
    private Calendar futureDate1;
    private Calendar futureDate3;
    private final Calendar TEST_PAST_DATE = new GregorianCalendar(presentYear - 2, 1, 1, 12, 0);
    private final Calendar TEST_FUTURE_DATE = new GregorianCalendar(presentYear + 2, 1, 1, 12, 0);
    private AtomicInteger meetingId;
    private PastMeeting pMeeting0;
    private PastMeeting pMeeting1;
    private PastMeeting pMeeting2;
    private PastMeeting pMeeting2Duplicate;
    private PastMeeting pMeeting3;
    private Meeting fMeeting0;
    private Meeting fMeeting1;
    private Meeting fMeeting2;
    private Meeting fMeeting2Duplicate;
    private Meeting fMeeting3;
    private Meeting tempMeeting;
    private List<PastMeeting> pMeetingList;
    private List<Meeting> fMeetingList;
    Map<Integer, Contact> testContactsIDMap;
    Map<Integer, Meeting> testMeetingsIDMap;
    AtomicInteger testContactIDGenerator;
    AtomicInteger testMeetingIDGenerator;
    Object[] tempObjectsArray2;
    Contact expectedContact;
    Meeting expectedMeeting;
    private final String TEST_NOTES = "Test notes for meeting/contact.";
    private final String TEST_NOTES_2 = "More Notes!";
    private final String NOTES_SEPARATOR = "\n----------\n";
    private final String TEST_NAME = "New Contact";
    private final String DATA_FILE_NAME = "data.ser";


    /**
     * Creates objects needed to run the tests in this class.
     */
    @Before
    public void setUp() {
        new File("data.ser").delete();
        testContactManager = new ContactManagerImpl();
    // Sets up contact related objects.
        AtomicInteger contactId = new AtomicInteger();
        tempContactSet = new HashSet<>();
        falseContact = new ContactImpl(99, "Danny Boyle");
        contact0 = new ContactImpl(contactId.getAndIncrement(), "Carey Mulligan");
        contact0.addNotes("notes");
        contact1 = new ContactImpl(contactId.getAndIncrement(), "Marlon Harrington");
        contact1.addNotes("0208 839 5027");
        contact2 = new ContactImpl(contactId.getAndIncrement(), "Jason Bryan");
        contact2.addNotes("");
        contact3 = new ContactImpl(contactId.getAndIncrement(), "Edward Curry");
        contact3.addNotes("Gets hungary in meetings - bring sandwiches");
        contact4 = new ContactImpl(contactId.getAndIncrement(), "Roxanne Norman");
        contact4.addNotes("info@roxannnenorman.com");
        testContactManager.addNewContact(contact0.getName(), "notes");
        testContactManager.addNewContact(contact1.getName(), "0208 839 5027");
        testContactManager.addNewContact(contact2.getName(), "");
        testContactManager.addNewContact(contact3.getName(), "Gets hungary in meetings - bring sandwiches");
        testContactManager.addNewContact(contact4.getName(), "info@roxannnenorman.com");
        //Following code ensures contact objects 5, 6, 7 & 8 in test class are identical
        // to those in the Impl class being tested. This is needed for the tests of
        // getFutureMeetingList(Contact contact) & getPastMeetingList(Contact contact) methods.
        testContactManager.addNewContact("Jason Bryan", "A New Jason Bryan");
        tempContactSet = testContactManager.getContacts(5);
        tempObjectsArray = tempContactSet.toArray();
        contact5 = ((Contact) tempObjectsArray[0]);
        testContactManager.addNewContact("Shari Murray", "Notes about Shari");
        tempContactSet = testContactManager.getContacts(6);
        tempObjectsArray = tempContactSet.toArray();
        contact6 = ((Contact) tempObjectsArray[0]);
        testContactManager.addNewContact("Carla Padilla", "Some notes here.");
        tempContactSet = testContactManager.getContacts(7);
        tempObjectsArray = tempContactSet.toArray();
        contact7 = ((Contact) tempObjectsArray[0]);
        testContactManager.addNewContact("Van Harvey", "Notes about Van");
        tempContactSet = testContactManager.getContacts(8);
        tempObjectsArray = tempContactSet.toArray();
        contact8 = ((Contact) tempObjectsArray[0]);
        TEST_CONTACT_SET.addAll(testContactManager.getContacts(2, 4, 5));
        TEST_CONTACT_SET_WITH_FALSE_CONTACT.addAll(testContactManager.getContacts(2, 4, 5));
        TEST_CONTACT_SET_WITH_FALSE_CONTACT.add(falseContact);
    // Sets up meeting & date related objects.
        meetingId = new AtomicInteger();
        //Sets up date objects to ensure tests will run correctly at any time in the future.
        futureDate1 = new GregorianCalendar(presentYear + 2, 10, 1, 10, 30);
        Calendar futureDate2 = new GregorianCalendar(presentYear + 2, 10, 1, 23, 30);
        futureDate3 = new GregorianCalendar(presentYear + 2, 12, 1, 10, 30);
        Calendar pastDate1 = new GregorianCalendar(presentYear - 2, 12, 1, 10, 30);
        Calendar pastDate2 = new GregorianCalendar(presentYear - 2, 12, 1, 23, 30);
        Calendar pastDate3 = new GregorianCalendar(presentYear - 4, 10, 1, 12, 0);
        // Sets up future meeting objects.
        // Adds five new future meetings two of which are the same (have the same date & contact set),
        // and another which takes place on the same day with the same contacts as
        // fMeeting2/fMeeting2Duplicate, but at a different time. These are used to test the deletion
        // of duplicates in the getFutureMeetingList methods.
        tempContactSet = testContactManager.getContacts(0, 2, 3, 5, 7, 8);
        fMeeting0 = new FutureMeetingImpl(meetingId.getAndIncrement(), futureDate1, tempContactSet);
        testContactManager.addFutureMeeting(fMeeting0.getContacts(), fMeeting0.getDate());

        tempContactSet = testContactManager.getContacts(2, 3, 7, 8);
        fMeeting1 = new FutureMeetingImpl(meetingId.getAndIncrement(), futureDate3, tempContactSet);
        testContactManager.addFutureMeeting(tempContactSet, futureDate3);

        tempContactSet = testContactManager.getContacts(3, 8);
        fMeeting2 = new FutureMeetingImpl(meetingId.getAndIncrement(), futureDate1, tempContactSet);
        testContactManager.addFutureMeeting(tempContactSet, futureDate1);

        fMeeting2Duplicate = new FutureMeetingImpl(meetingId.getAndIncrement(), futureDate1, tempContactSet);
        testContactManager.addFutureMeeting(tempContactSet, futureDate1);

        fMeeting3 = new FutureMeetingImpl(meetingId.getAndIncrement(), futureDate2, tempContactSet);
        testContactManager.addFutureMeeting(tempContactSet, futureDate2);
        // Sets up past meeting objects.
        // Adds five new past meetings two of which are the same (have the same date & contact set),
        // and another which takes place on the same day with the same contacts as
        // pMeeting2/pMeeting2Duplicate, but at a different time. These are used to test the deletion
        // of duplicates in the getFutureMeetingList methods.
        tempContactSet = testContactManager.getContacts(0, 2, 3, 5, 7, 8);
        pMeeting0 = new PastMeetingImpl(meetingId.getAndIncrement(), pastDate1, tempContactSet, TEST_NOTES);
        testContactManager.addNewPastMeeting(pMeeting0.getContacts(), pMeeting0.getDate(), TEST_NOTES);

        tempContactSet = testContactManager.getContacts(2, 3, 7, 8);
        pMeeting1 = new PastMeetingImpl(meetingId.getAndIncrement(), pastDate3, tempContactSet, TEST_NOTES);
        testContactManager.addNewPastMeeting(tempContactSet, pastDate3, TEST_NOTES);

        tempContactSet = testContactManager.getContacts(3, 8);
        pMeeting2 = new PastMeetingImpl(meetingId.getAndIncrement(), pastDate1, tempContactSet, TEST_NOTES);
        testContactManager.addNewPastMeeting(tempContactSet, pastDate1, TEST_NOTES);

        pMeeting2Duplicate = new PastMeetingImpl(meetingId.getAndIncrement(), pastDate1, tempContactSet, TEST_NOTES);
        testContactManager.addNewPastMeeting(tempContactSet, pastDate1, TEST_NOTES);

        pMeeting3 = new PastMeetingImpl(meetingId.getAndIncrement(), pastDate2, tempContactSet, TEST_NOTES);
        testContactManager.addNewPastMeeting(tempContactSet, pastDate2, TEST_NOTES);
        // Sets up Data file for testing.
        testContactManager.flush();
    }


    // Beginning of tests for whole class.
    // The first tests are for the class constructor, after which the
    // order of the tests reflect the order of the methods in the interface.
    @Rule
    public ExpectedException exception = ExpectedException.none();

    /**
     * Tests the class constructor method can read the saved data
     * related to contact objects.
     */
    @Test
    public void testConstructorReadsDataFileTestsContacts() {
        FileOutputStream fileOut;
        ObjectOutputStream objectOut;

        testContactsIDMap = new HashMap<>();
        testContactIDGenerator = new AtomicInteger();

        testContactsIDMap.put(testContactIDGenerator.getAndIncrement(), contact0);
        testContactsIDMap.put(testContactIDGenerator.getAndIncrement(), contact1);
        testContactsIDMap.put(testContactIDGenerator.getAndIncrement(), contact2);

        try {
            fileOut = new FileOutputStream("data.ser");
            objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(testContactsIDMap);
            objectOut.writeObject(new HashMap<Integer, Meeting>());
            objectOut.writeObject(testContactIDGenerator);
            objectOut.writeObject(new AtomicInteger());
            objectOut.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        testContactManager = new ContactManagerImpl();

        tempObjectsArray = testContactsIDMap.values().toArray();
        assertEquals(3, tempObjectsArray.length);
        for (Object item : tempObjectsArray) {
            expectedContact = (Contact) item;
            tempObjectsArray2 = testContactManager.getContacts(expectedContact.getId()).toArray();
            tempContact = (Contact) tempObjectsArray2[0];
            assertEquals(expectedContact.getName(), tempContact.getName());
            assertEquals(expectedContact.getNotes(), tempContact.getNotes());
        }
        exception.expect(IllegalArgumentException.class);
        testContactManager.getContacts(3);
    }

    /**
     * Tests the class constructor method can read the saved data
     * related to meeting objects.
     */
    @Test
    public void testConstructorReadsDataFileTestsMeetings() {
        FileOutputStream fileOut;
        ObjectOutputStream objectOut;

        testMeetingsIDMap = new HashMap<>();
        testMeetingIDGenerator = new AtomicInteger();

        testMeetingsIDMap.put(testMeetingIDGenerator.getAndIncrement(), fMeeting0);
        testMeetingsIDMap.put(testMeetingIDGenerator.getAndIncrement(), fMeeting1);
        testMeetingsIDMap.put(testMeetingIDGenerator.getAndIncrement(), fMeeting2);

        try {
            fileOut = new FileOutputStream("data.ser");
            objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(new HashMap<Integer, Contact>());
            objectOut.writeObject(testMeetingsIDMap);
            objectOut.writeObject(new AtomicInteger());
            objectOut.writeObject(testMeetingIDGenerator);
            objectOut.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        testContactManager = new ContactManagerImpl();

        tempObjectsArray = testMeetingsIDMap.values().toArray();
        assertEquals(3, tempObjectsArray.length);
        for (Object item : tempObjectsArray) {
            expectedMeeting = (Meeting) item;
            tempMeeting = testContactManager.getMeeting(expectedMeeting.getId());
            assertEquals(expectedMeeting.getDate(), tempMeeting.getDate());
            assertEquals(expectedMeeting.getContacts().size(), tempMeeting.getContacts().size());
        }
        assertNull(testContactManager.getMeeting(3));
    }

    /**
     * Tests the addFutureMeeting method can be called with acceptable arguments.
     */
    @Test
    public void testAddFutureMeeting() {
        testContactManager.addFutureMeeting(TEST_CONTACT_SET, TEST_FUTURE_DATE);
    }

    /**
     * Tests the addFutureMeeting method throws an IllegalArgumentException
     * when called with a past date.
     */
    @Test
    public void testAddFutureMeetingWithPastDate() {
        exception.expect(IllegalArgumentException.class);
        testContactManager.addFutureMeeting(TEST_CONTACT_SET,TEST_PAST_DATE);
    }

    /**
     * Tests the addFutureMeeting method throws an IllegalArgumentException
     * when called with a contact that doesn't exist.
     */
    @Test
    public void testAddFutureMeetingWithFalseContact() {
        exception.expect(IllegalArgumentException.class);
        testContactManager.addFutureMeeting(TEST_CONTACT_SET_WITH_FALSE_CONTACT, TEST_FUTURE_DATE);
    }

    /**
     * Tests the getFutureMeeting method returns the correct meeting when called.
     */
    @Test
    public void testGetFutureMeeting() {
        tempMeeting = testContactManager.getFutureMeeting(fMeeting0.getId());
        assertEquals(fMeeting0.getDate(), tempMeeting.getDate());
        assertTrue(fMeeting0.getContacts().equals(tempMeeting.getContacts()));
    }

    /**
     * Tests the getFutureMeeting method returns null, when a meeting
     * which doesn't exists is queried.
     */
    @Test
    public void testGetFutureMeetingWithFalseMeeting() {
        assertNull(testContactManager.getFutureMeeting(100));
    }

    /**
     * Tests the getFutureMeeting method throws an IllegalArgumentException
     * when a meeting with a past date is queried.
     */
    @Test
    public void testGetFutureMeetingWithPastMeeting() {
        exception.expect(IllegalArgumentException.class);
        testContactManager.getFutureMeeting(pMeeting0.getId());
    }

    /**
     * Tests the getMeeting method returns the correct meeting
     * when a meeting with a future date is queried.
     */
    @Test
    public void testGetMeetingWithFutureMeeting() {
        tempMeeting = testContactManager.getMeeting(fMeeting0.getId());
        assertEquals(fMeeting0.getDate(), tempMeeting.getDate());
        assertTrue(fMeeting0.getContacts().equals(tempMeeting.getContacts()));
    }

    /**
     * Tests the getMeeting method returns the correct meeting
     * when a meeting with a paste date is queried.
     */
    @Test
    public void testGetMeetingWithPastMeeting() {
        PastMeeting tempPMeeting = (PastMeeting) testContactManager.getMeeting(pMeeting0.getId());
        assertEquals(pMeeting0.getDate(), tempPMeeting.getDate());
        assertTrue(pMeeting0.getContacts().equals(tempPMeeting.getContacts()));
        assertEquals(pMeeting0.getNotes(), tempPMeeting.getNotes());
    }

    /**
     * Tests the getMeeting method returns null, when a meeting
     * which doesn't exists is queried.
     */
    @Test
    public void testGetMeetingWithFalseMeeting() {
        assertNull(testContactManager.getMeeting(100));
    }

    /**
     * Tests the getFutureMeetingList(Contact contact) method returns an empty list,
     * when a contact is queried who doesn't have any meetings scheduled.
     */
    @Test
    public void testGetFutureMeetingListByContactReturnsEmptyList() {
        //Ensures no meetings are returned for contact6
        fMeetingList = testContactManager.getFutureMeetingList(contact6);
        assertTrue(fMeetingList.isEmpty());
    }

    /**
     * Tests the getFutureMeetingList(Contact contact) method returns the
     * correct list, when a contact is queried.
     */
    @Test
    public void testGetFutureMeetingListByContactSizeOfList() {
        //Ensures the correct amount of meetings are returned for contact5
        //and that they contain the expected meeting.
        fMeetingList = testContactManager.getFutureMeetingList(contact5);
        assertEquals(1, fMeetingList.size());
        assertTrue(fMeetingList.contains(testContactManager.getFutureMeeting(fMeeting0.getId())));
    }

    /**
     * Tests the getFutureMeetingList(Contact contact) method returns the
     * correct list when a contact is queried and that the list is chronologically
     * sorted.
     */
    @Test
    public void testGetFutureMeetingListByContactChronologicalSort() {
        //Ensures the correct amount of meetings are returned for contact7,
        //that they contain the expected meetings and the meetings are in
        //chronological order.
        fMeetingList = testContactManager.getFutureMeetingList(contact7);
        assertEquals(2, fMeetingList.size());
        assertTrue(fMeetingList.contains(testContactManager.getFutureMeeting(fMeeting0.getId())));
        assertTrue(fMeetingList.contains(testContactManager.getFutureMeeting(fMeeting1.getId())));
        assertTrue(fMeetingList.get(0).getDate().before(fMeetingList.get(1).getDate()));
    }

    /**
     * Tests the getFutureMeetingList(Contact contact) method returns the
     * correct list when a contact is queried, that the list is chronologically
     * sorted and any duplicates have been deleted.
     */
    @Test
    public void testGetFutureMeetingListByContactDeletesDuplicates() {
        //Ensures the correct amount of meetings are returned for contact8,
        //that they contain the expected meetings and any duplicates have
        //been deleted. Also ensures the meetings are in chronological order.
        fMeetingList = testContactManager.getFutureMeetingList(contact8);
        assertEquals(4, fMeetingList.size());
        assertTrue(fMeetingList.contains(testContactManager.getFutureMeeting(fMeeting0.getId())));
        assertTrue(fMeetingList.contains(testContactManager.getFutureMeeting(fMeeting1.getId())));
        assertTrue(fMeetingList.contains(testContactManager.getFutureMeeting(fMeeting3.getId())));
        //Checks that one (and only one) of the duplicate meetings added is in the returned list.
        assertTrue(fMeetingList.contains(testContactManager.getFutureMeeting(fMeeting2.getId()))
                != fMeetingList.contains(testContactManager.getFutureMeeting(fMeeting2Duplicate.getId())));
        assertEquals(fMeetingList.get(0).getDate(), fMeetingList.get(1).getDate());
        assertTrue(fMeetingList.get(1).getDate().before(fMeetingList.get(2).getDate()));
        assertTrue(fMeetingList.get(2).getDate().before(fMeetingList.get(3).getDate()));
    }

    /**
     * Tests the getFutureMeetingList(Contact contact) method throws an
     * IllegalArgumentException when a contact which doesn't exist is queried.
     */
    @Test
    public void testGetFutureMeetingListByContactFalseContact() {
        //Ensures IllegalArgumentException is thrown when a non-existent
        //contact is queried.
        exception.expect(IllegalArgumentException.class);
        testContactManager.getFutureMeetingList(falseContact);
    }

    /**
     * Tests the getFutureMeetingList(Calendar date) method returns the
     * correct meetings when a date is queried.
     */
    @Test
    public void testGetFutureMeetingListByDateSizeOfList() {
        //Ensures the correct amount of meetings are returned for futureDate3
        //and that they contain the expected meeting.
        fMeetingList = testContactManager.getFutureMeetingList(futureDate3);
        assertEquals(1, fMeetingList.size());
        assertTrue(fMeetingList.contains(testContactManager.getFutureMeeting(fMeeting1.getId())));
    }

    /**
     * Tests the getFutureMeetingList(Calendar date) method returns an
     * empty list when a date with no meetings scheduled is queried.
     */
    @Test
    public void testGetFutureMeetingListByDateReturnsEmptyList() {
        //Ensures no meetings are returned for a false date.
        fMeetingList = testContactManager.getFutureMeetingList(TEST_FUTURE_DATE);
        assertTrue(fMeetingList.isEmpty());
    }


    /**
     * Tests the getFutureMeetingList(Calendar date) method returns the
     * correct meetings when a date is queried, they are in chronological order
     * and any duplicates have been deleted before the list is returned.
     */
    @Test
    public void testGetFutureMeetingListByDate() {
        //Ensures the correct amount of meetings are returned for futureDate1 (inc. fMeeting3),
        //that they contain the expected meetings and any duplicates have
        //been deleted. Also ensures the meetings are in chronological order.
        fMeetingList = testContactManager.getFutureMeetingList(futureDate1);
        assertEquals(3, fMeetingList.size());
        assertTrue(fMeetingList.contains(testContactManager.getFutureMeeting(fMeeting0.getId())));
        assertTrue(fMeetingList.contains(testContactManager.getFutureMeeting(fMeeting3.getId())));
        //Checks that one (and only one) of the duplicate meetings added is in the returned list.
        assertTrue(fMeetingList.contains(testContactManager.getFutureMeeting(fMeeting2.getId()))
                != fMeetingList.contains(testContactManager.getFutureMeeting(fMeeting2Duplicate.getId())));
        //Checks for chronological order.
        assertEquals(fMeetingList.get(0).getDate(), fMeetingList.get(1).getDate());
        assertTrue(fMeetingList.get(1).getDate().before(fMeetingList.get(2).getDate()));
    }

    /**
     * Tests the getPastMeetingList(Contact contact) method returns the
     * correct meetings when a contact is queried.
     */
    @Test
    public void testGetPastMeetingList() {
        //Ensures the correct amount of meetings are returned for contact5
        //and that they contain the expected meeting.
        pMeetingList = testContactManager.getPastMeetingList(contact5);
        assertEquals(1, pMeetingList.size());
        assertTrue(pMeetingList.contains(testContactManager.getMeeting(pMeeting0.getId())));
    }

    /**
     * Tests the getPastMeetingList(Contact contact) method returns an
     * empty list when a contact with no meetings scheduled is queried.
     */
    @Test
    public void testGetPastMeetingListEmptyList() {
        //Ensures no meetings are returned for contact6
        pMeetingList = testContactManager.getPastMeetingList(contact6);
        assertTrue(pMeetingList.isEmpty());
    }

    /**
     * Tests the getPastMeetingList(Contact contact) method returns the
     * correct meetings when a contact is queried, and they are in
     * chronological order.
     */
    @Test
    public void testGetPastMeetingListChronologicalSort() {
        //Ensures the correct amount of meetings are returned for contact7,
        //that they contain the expected meetings and the meetings are in
        //chronological order.
        pMeetingList = testContactManager.getPastMeetingList(contact7);
        assertEquals(2, pMeetingList.size());
        assertTrue(pMeetingList.contains(testContactManager.getMeeting(pMeeting0.getId())));
        assertTrue(pMeetingList.contains(testContactManager.getMeeting(pMeeting1.getId())));
        assertTrue(pMeetingList.get(0).getDate().before(pMeetingList.get(1).getDate()));
    }

    /**
     * Tests the getPastMeetingList(Contact contact) method returns the
     * correct meetings when a contact is queried, they are in
     * chronological order and all duplicates have been deleted.
     */
    @Test
    public void testGetPastMeetingListDeletesDuplicates() {
        //Ensures the correct amount of meetings are returned for contact8,
        //that they contain the expected meetings and any duplicates have
        //been deleted. Also ensures the meetings are in chronological order.
        pMeetingList = testContactManager.getPastMeetingList(contact8);
        assertEquals(4, pMeetingList.size());
        assertTrue(pMeetingList.contains(testContactManager.getMeeting(pMeeting0.getId())));
        assertTrue(pMeetingList.contains(testContactManager.getMeeting(pMeeting1.getId())));
        assertTrue(pMeetingList.contains(testContactManager.getMeeting(pMeeting3.getId())));
        //Checks that one (and only one) of the duplicate meetings added is in the returned list.
        assertTrue(pMeetingList.contains(testContactManager.getMeeting(pMeeting2.getId()))
                != pMeetingList.contains(testContactManager.getMeeting(pMeeting2Duplicate.getId())));
        assertTrue(pMeetingList.get(0).getDate().before(pMeetingList.get(1).getDate()));
        assertEquals(pMeetingList.get(1).getDate(), pMeetingList.get(2).getDate());
        assertTrue(pMeetingList.get(2).getDate().before(pMeetingList.get(3).getDate()));
    }

    /**
     * Tests the getPastMeetingList(Contact contact) method throws an
     * IllegalArgumentException when a contact which doesn't exist is queried.
     */
    @Test
    public void testGetPastMeetingListFalseContact() {
        //Ensures IllegalArgumentException is thrown when a non-existent
        //contact is queried.
        exception.expect(IllegalArgumentException.class);
        testContactManager.getPastMeetingList(falseContact);
    }

    /**
     * Tests the addNewPastMeeting method can be called with acceptable arguments.
     */
    @Test
    public void testAddNewPastMeeting() {
        testContactManager.addNewPastMeeting(TEST_CONTACT_SET, TEST_PAST_DATE, TEST_NOTES);
    }

    /**
     * Tests the addNewPastMeeting method throws an IllegalArgumentException
     * when called with a contact which doesn't exist.
     */
    @Test
    public void testAddNewPastMeetingWithFalseContact() {
        exception.expect(IllegalArgumentException.class);
        testContactManager.addNewPastMeeting(TEST_CONTACT_SET_WITH_FALSE_CONTACT, TEST_PAST_DATE, TEST_NOTES);
    }

    /**
     * Tests the addNewPastMeeting method throws an IllegalArgumentException
     * when called with an empty contact set.
     */
    @Test
    public void testAddNewPastMeetingEmptyContactSet() {
        tempContactSet = new HashSet<>();
        exception.expect(IllegalArgumentException.class);
        testContactManager.addNewPastMeeting(tempContactSet, TEST_PAST_DATE, TEST_NOTES);
    }

    /**
     * Tests the addNewPastMeeting method throws a NullPointerException
     * when called and null is passed as a contact value.
     */
    @Test
    public void testAddNewPastMeetingNullContactList() {
        exception.expect(NullPointerException.class);
        testContactManager.addNewPastMeeting(null, TEST_PAST_DATE, TEST_NOTES);
    }

    /**
     * Tests the addNewPastMeeting method throws a NullPointerException
     * when called and null is passed as a date value.
     */
    @Test
    public void testAddNewPastMeetingNullDate() {
        exception.expect(NullPointerException.class);
        testContactManager.addNewPastMeeting(TEST_CONTACT_SET, null, TEST_NOTES);
    }

    /**
     * Tests the addNewPastMeeting method throws a NullPointerException
     * when called and null is passed as a notes value.
     */
    @Test
    public void testAddNewPastMeetingNullNotes() {
        exception.expect(NullPointerException.class);
        testContactManager.addNewPastMeeting(TEST_CONTACT_SET, TEST_PAST_DATE, null);
    }

    /**
     * Tests the addMeetingNotes method can be called to add notes ot past meeting.
     */
    @Test
    public void testAddMeetingNotesToPastMeeting() {
        testContactManager.addMeetingNotes(pMeeting0.getId(), TEST_NOTES_2);
        String expectedNotes = pMeeting0.getNotes() + TEST_NOTES_2 + NOTES_SEPARATOR;
        assertEquals(expectedNotes, ((PastMeetingImpl)testContactManager.getMeeting(pMeeting0.getId())).getNotes());
    }

    /**
     * Tests the addMeetingNotes method throws an IllegalArgumentException
     * when called with a meeting which doesn't exist.
     */
    @Test
    public void testAddMeetingNotesToFalseMeeting() {
        exception.expect(IllegalArgumentException.class);
        testContactManager.addMeetingNotes(483, "Some Notes!");
    }

    /**
     * Tests the addMeetingNotes method converts a future meeting to a past meeting
     * when called on a future meeting with a past date.
     */
    @Test
    public void testAddMeetingNotesToFutureMeetingWithPastDate() {
        Calendar shortFutureDate = Calendar.getInstance();
        shortFutureDate.add(Calendar.MILLISECOND, 45);
        Meeting convertedMeeting = new FutureMeetingImpl(meetingId.getAndIncrement(), shortFutureDate, TEST_CONTACT_SET);
        testContactManager.addFutureMeeting(convertedMeeting.getContacts(), convertedMeeting.getDate());
        try {
            Thread.sleep(50);
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
        testContactManager.addMeetingNotes(convertedMeeting.getId(), TEST_NOTES_2);
        assertEquals(TEST_NOTES_2 + NOTES_SEPARATOR, ((PastMeeting) testContactManager.getMeeting(convertedMeeting.getId())).getNotes());
        assertTrue(testContactManager.getMeeting(convertedMeeting.getId()) instanceof PastMeeting);
    }

    /**
     * Tests the addMeetingNotes method throws an IllegalStateException
     * when called with a meeting which is scheduled for the future.
     */
    @Test
    public void testAddMeetingNotesToFutureMeetingWithFutureDate() {
        exception.expect(IllegalStateException.class);
        testContactManager.addMeetingNotes(fMeeting0.getId(), TEST_NOTES);
    }

    /**
     * Tests the addMeetingNotes method throws a NullPointerException
     * when called with null meeting notes.
     */
    @Test
    public void testAddNullMeetingNotes() {
        exception.expect(NullPointerException.class);
        testContactManager.addMeetingNotes(pMeeting0.getId(), null);
    }

    /**
     * Tests the addNewContact method can be called with acceptable arguments.
     */
    @Test
    public void testAddNewContact() {
        testContactManager.addNewContact(TEST_NAME, TEST_NOTES);
    }

    /**
     * Tests the addNewContact method throws a NullPointerException
     * when called with null notes.
     */
    @Test
    public void testAddNewContactWithNullNotes() {
        exception.expect(NullPointerException.class);
        testContactManager.addNewContact(TEST_NAME, null);
    }

    /**
     * Tests the addNewContact method throws a NullPointerException
     * when called with a null contact.
     */
    @Test
    public void testAddNewContactWithNullName() {
        exception.expect(NullPointerException.class);
        testContactManager.addNewContact(null, TEST_NOTES);
    }

    /**
     * Tests the getContact(int... ids) returns the expected value
     * when called for one contact.
     */
    @Test
    public void testGetOneContactByID() {
        tempContactSet = testContactManager.getContacts(contact1.getId());
        tempObjectsArray = tempContactSet.toArray();
        tempContact = (Contact) tempObjectsArray[0];

        assertTrue(tempObjectsArray.length == 1);
        assertEquals(contact1.getId(), tempContact.getId());
        assertEquals(contact1.getName(), tempContact.getName());
        assertEquals(contact1.getNotes(), tempContact.getNotes());
    }

    /**
     * Tests the getContact(int... ids) returns the expected value
     * when called for multiple contacts.
     */
    @Test
    public void testGetMultipleContactsByID() {
        tempContactSet = testContactManager.getContacts(0, 2, 4);
        tempObjectsArray = tempContactSet.toArray();

        boolean contact0Found = false;
        boolean contact2Found = false;
        boolean contact4Found = false;
        for (int i = 0; i < 3; i++){
            tempContact = (Contact) tempObjectsArray[i];

            if (0 == tempContact.getId()){
                assertEquals(contact0.getName(), tempContact.getName());
                assertEquals(contact0.getNotes(), tempContact.getNotes());
                contact0Found = true;
            } else if (2 == tempContact.getId()){
                assertEquals(contact2.getName(), tempContact.getName());
                assertEquals(contact2.getNotes(), tempContact.getNotes());
                contact2Found = true;
            } else {
                assertEquals(4, tempContact.getId());
                assertEquals(contact4.getName(), tempContact.getName());
                assertEquals(contact4.getNotes(), tempContact.getNotes());
                contact4Found = true;
            }
        }
        assertTrue(tempObjectsArray.length == 3);
        assertTrue(contact0Found);
        assertTrue(contact2Found);
        assertTrue(contact4Found);
    }

    /**
     * Tests the getContact(int... ids) throws an IllegalArgumentException
     * when called on a contact that doesn't exist.
     */
    @Test
    public void testGetContactsByIDWithFalseID() {
        exception.expect(IllegalArgumentException.class);
        testContactManager.getContacts(50);
    }

    /**
     * Tests the getContact(String name) returns the expected value
     * when called for one contact.
     */
    @Test
    public void testGetContactsByName() {
        tempContactSet = testContactManager.getContacts("Edward Curry");
        tempObjectsArray = tempContactSet.toArray();
        tempContact = (Contact) tempObjectsArray[0];

        assertEquals(contact3.getId(), tempContact.getId());
        assertEquals(contact3.getName(), tempContact.getName());
        assertEquals(contact3.getNotes(), tempContact.getNotes());
        assertTrue(tempObjectsArray.length == 1);
    }

    /**
     * Tests the getContact(String name) returns the expected size
     * set when called for multiple contacts with a full name as its argument.
     */
    @Test
    public void testGetMultipleContactsByName() {
        tempContactSet = testContactManager.getContacts("Jason Bryan");
        tempObjectsArray = tempContactSet.toArray();
        assertEquals(2, tempObjectsArray.length);
    }

    /**
     * Tests the getContact(String name) returns the expected size
     * set when called for multiple contacts with a name segment as it's argument.
     */
    @Test
    public void testGetMultipleContactsByNameSegment() {
        tempContactSet = testContactManager.getContacts("an");
        tempObjectsArray = tempContactSet.toArray();
        assertEquals(5, tempObjectsArray.length);
    }

    /**
     * Tests the getContact(String name) method throws a NullPointerException
     * when called with a null string as it's argument.
     */
    @Test
    public void testGetContactsByNameNullName() {
        //Necessary to ensure correct version of
        //getContacts method is called.
        String nullString = null;
        exception.expect(NullPointerException.class);
        testContactManager.getContacts(nullString);
    }

    /**
     * Tests the getContact(String name) method throws a NullPointerException
     * when called with an empty string as it's argument.
     */
    @Test
    public void testGetContactsByNameEmptyName() {
        exception.expect(NullPointerException.class);
        testContactManager.getContacts("");
    }

    /**
     * Tests the getContact(String name) method returns an empty set
     * when called with a contact the doesn't exist as it's argument.
     */
    @Test
    public void testGetContactsByFalseNameReturnsEmptySet() {
        tempContactSet = testContactManager.getContacts(falseContact.getName());
        assertTrue(tempContactSet.isEmpty());
    }

    /**
     * Tests that the flush method creates a data file.
     */
    @Test
    public void testFlushDataFileCreated() {
        //Tests if data file exists.
        testContactManager.flush();
        try
        {
            new FileInputStream(DATA_FILE_NAME);
        }
        catch (IOException ex){
            ex.printStackTrace();
            fail();
        }
    }

    /**
     * Tests that the flush method creates a data file and it contains the right objects.
     */
    @Test
    @SuppressWarnings("unchecked")
    public void testFlushDataFileHasContent() {
        testContactManager.flush();
        FileInputStream fileIn;
        ObjectInputStream objectIn;
        try
        {
            fileIn = new FileInputStream(DATA_FILE_NAME);
            objectIn = new ObjectInputStream(fileIn);
            testContactsIDMap = (HashMap<Integer, Contact>) objectIn.readObject();
            testMeetingsIDMap = (HashMap<Integer, Meeting>) objectIn.readObject();
            testContactIDGenerator = (AtomicInteger) objectIn.readObject();
            testMeetingIDGenerator = (AtomicInteger) objectIn.readObject();
            objectIn.close();
        }
        catch (IOException | ClassNotFoundException ex){
            ex.printStackTrace();
            fail();
        }
    }

    /**
     * Tests that the flush method creates a data file and it has the
     * correct content.
     */
    @Test
    @SuppressWarnings("unchecked")
    public void testFlushDataFileHasCorrectContent() {
        testContactManager.flush();
        FileInputStream fileIn;
        ObjectInputStream objectIn;
        try
        {
            fileIn = new FileInputStream(DATA_FILE_NAME);
            objectIn = new ObjectInputStream(fileIn);
            testContactsIDMap = (HashMap<Integer, Contact>) objectIn.readObject();
            testMeetingsIDMap = (HashMap<Integer, Meeting>) objectIn.readObject();
            testContactIDGenerator = (AtomicInteger) objectIn.readObject();
            testMeetingIDGenerator = (AtomicInteger) objectIn.readObject();
            objectIn.close();
        }
        catch (IOException | ClassNotFoundException ex){
            ex.printStackTrace();
            fail();
        }
        assertEquals(9, testContactsIDMap.size());
        assertEquals(10, testMeetingsIDMap.size());
        assertEquals(9, testContactIDGenerator.get());
        assertEquals(10, testMeetingIDGenerator.get());

        tempObjectsArray = testContactsIDMap.values().toArray();
        for (Object item : tempObjectsArray) {
            tempContact = (Contact) item;
            tempObjectsArray2 = testContactManager.getContacts(tempContact.getId()).toArray();
            expectedContact = (Contact) tempObjectsArray2[0];
            assertEquals(expectedContact.getName(), tempContact.getName());
            assertEquals(expectedContact.getNotes(), tempContact.getNotes());
        }

        tempObjectsArray = testMeetingsIDMap.values().toArray();
        for (Object item : tempObjectsArray) {
            tempMeeting = (Meeting) item;
            expectedMeeting = testContactManager.getMeeting(tempMeeting.getId());
            assertEquals(expectedMeeting.getDate(), tempMeeting.getDate());
            assertEquals(expectedMeeting.getContacts().size(), tempMeeting.getContacts().size());
        }
    }
}





























