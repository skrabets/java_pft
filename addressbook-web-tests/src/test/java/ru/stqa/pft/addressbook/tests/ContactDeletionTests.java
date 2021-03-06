package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertEquals;

public class ContactDeletionTests extends TestBase {

  @BeforeMethod
  public void ensurePreConditions() throws InterruptedException {
    if (app.db().groups().size()==0 && app.db().contacts().size() == 0){
      app.goTo().groupPage();
      app.group().create(new GroupData().withName("formWasEmpty"));
      Groups groups = app.db().groups();
      app.contact().create(new ContactData()
              .withFirstName("Petr").withMiddleName("Jan").withLastName("Mares")
              .withNickName("honzamares").withTitle("PHDr").inGroup(groups.iterator().next())
              .withCompany("Skoda").withAddress("Prague").withEmail("honzamares@test.com"), true);
    } else if (app.db().groups().size()==0){
      app.goTo().groupPage();
      app.group().create(new GroupData().withName("formWasEmpty"));
      Groups groups = app.db().groups();
      app.contact().create(new ContactData()
              .withFirstName("Petr").withMiddleName("Jan").withLastName("Mares")
              .withNickName("honzamares").withTitle("PHDr").inGroup(groups.iterator().next())
              .withCompany("Skoda").withAddress("Prague").withEmail("honzamares@test.com"), true);
    } else if (app.db().contacts().size() == 0){
      Groups groups = app.db().groups();
      app.contact().create(new ContactData()
              .withFirstName("Petr").withMiddleName("Jan").withLastName("Mares")
              .withNickName("honzamares").withTitle("PHDr").inGroup(groups.iterator().next())
              .withCompany("Skoda").withAddress("Prague").withEmail("honzamares@test.com"), true);
    }
  }

  @Test(enabled = false)
  public void testContactDeletion() {
    Contacts before = app.db().contacts();
    ContactData deletedContact = before.iterator().next();
    app.contact().delete(deletedContact);
    app.goTo().goToHomePage();
    Contacts after = app.db().contacts();
    assertEquals(after.size(), before.size() - 1);

    assertThat(after, equalTo(before.without(deletedContact)));
    verifyContactListInUI();
  }

}
