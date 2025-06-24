/*
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

*
* Created with IntelliJ IDEA.
* User: Анна
* Date: 13.04.13
* Time: 6:38
* To change this template use File | Settings | File Templates.


public class Create_Test {

    @Test   // существует ли имя, которого нет в списке -> false
    public void NotExName() throws Exception {
        NotebookTxtDb n = new NotebookTxtDb("New.txt");
        n.addRecord("miky", "8");
        n.addRecord("miky", "8");
        n.addRecord("miky", "8");
        assertFalse(n.isNameExists("sara"));


    }

    @Test  // Удаление всех контактов с одинаковыми именами
    public void RemoveAllSameContacts() throws Exception {
        NotebookTxtDb n = new NotebookTxtDb("New.txt");
        n.addRecord("lily", "100");
        n.remove("miky");
        assertFalse(n.isNameExists("mike"));

    }

    @Test  // Удаление всех контактов с одинаковыми именами
    public void RemoveAllSameContacts2() throws Exception {
        NotebookTxtMappedDb no = new NotebookTxtMappedDb("New.txt");
        no.addRecord("lily", "100");
        no.remove("miky");
        assertFalse(no.isNameExists("mike"));
    }

    @Test  // Удаление несуществующего контакта
    public void RemoveNoExContact() throws Exception {
        NotebookTxtDb n = new NotebookTxtDb("New.txt");
        n.remove("Lily");
        assertTrue(n.isNameExists("lily"));
    }

    @Test  // Удаление несуществующего контакта
    public void RemoveNoExContact2() throws Exception {
        NotebookTxtMappedDb no = new NotebookTxtMappedDb("New.txt");
        no.addRecord("lily", "100");
        no.remove("Lily");
        assertTrue(no.isNameExists("lily"));
    }


    @Test  // поиск по имени. Такого контакта нет.
    public void SearchN() throws Exception {
        NotebookTxtDb n = new NotebookTxtDb("New.txt");
        Assert.assertEquals(null, n.searchByName("anna"));
    }


    @Test  // поиск по номеру. Такого контакта нет.
    public void SearchPh() throws Exception {
        NotebookTxtDb n = new NotebookTxtDb("New.txt");
        Assert.assertEquals(null, n.searchByPhone("199993"));
    }

    @Test  // поиск по номеру. Такого контакта нет.
    public void SearchPh2() throws Exception {
        NotebookTxtMappedDb no = new NotebookTxtMappedDb("New.txt");
        no.addRecord("olimp", "95");
        no.addRecord("j", "11");
        no.addRecord("imp", "0");
        no.addRecord("i", "99");
        no.searchByPhone("99");
        //no.remove("olimp");
        Assert.assertEquals(null, no.searchByPhone("999"));
    }

    @Test  // поиск по имени. Существование >1 контактов с таким именем.
    public void SearchOne() throws Exception {
        NotebookTxtDb n = new NotebookTxtDb("New.txt");
        n.addRecord("olimp", "11");
        n.addRecord("olimp", "0");
        n.addRecord("olimp", "8686");
        Assert.assertEquals("11", n.searchByName("olimp"));

    }

    @Test  //проверка map при создании новых объектов класса
    public void Proverka() throws Exception {
        NotebookDb db = new NotebookTxtMappedDb("db.txt");
        db.addRecord("name1", "phone");
        NotebookDb db1 = new NotebookTxtMappedDb("db.txt");
        assertTrue(db.isNameExists("name1"));
    }

    @Test   // существует ли имя, которого нет в списке -> false
    public void NotExName2() throws Exception {
        NotebookTxtMappedDb no = new NotebookTxtMappedDb("New.txt");
        no.addRecord("miky", "8");
        no.addRecord("miky", "8");
        no.addRecord("miky", "8");
        assertFalse(no.isNameExists("sara"));

    }


    @Test  // поиск по имени. Такого контакта нет.
    public void SearchN2() throws Exception {
        NotebookTxtMappedDb no = new NotebookTxtMappedDb("New.txt");
        Assert.assertEquals(null, no.searchByName("anna"));
    }

 @Test  // поиск по имени. Существование >1 контактов с таким именем.
    public void SearchOne2() throws Exception {
        NotebookTxtMappedDb no = new NotebookTxtMappedDb("New.txt");
        no.addRecord("olimp", "11");
        no.addRecord("olimp", "0");
        no.addRecord("olimp", "8686");
        Assert.assertEquals("11", no.searchByName("olimp"));

    }


}
*/
