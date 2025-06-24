package com.movieexpress.application.dao.myql;

import java.util.ArrayList;
import java.util.List;

import com.moiveexpress.common.BaseDao;
import com.movieexpress.application.dto.Contact;
import com.movieexpress.auth.dto.User;

public class ContactDaoMysql extends BaseDao {

    public void addContact(Contact contact) throws Exception {
        List contacts = new ArrayList();
        query = "insert into contact (first_name,middle_name,last_name,address,phone,user_id) values (?,?,?,?,?,?) ";

        try {
            connect();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, contact.getFirstName());
            preparedStatement.setString(2, contact.getMiddleName());
            preparedStatement.setString(3, contact.getLastName());
            preparedStatement.setString(4, contact.getAddress());
            preparedStatement.setString(5, contact.getPhone());
            preparedStatement.setInt(6, contact.getUserId());

            System.out.println(preparedStatement);

            preparedStatement.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            close();
        }

    }

    public Contact getContact(Integer id, User user) throws Exception {
        Contact contact = null;
        query = "Select * from contact where id=? and user_id=?";
        try {
            connect();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            preparedStatement.setInt(2, user.getId());

            System.out.println(preparedStatement);

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                contact = new Contact();
                contact.setId(resultSet.getInt("id"));
                contact.setFirstName(resultSet.getString("first_name"));
                contact.setMiddleName(resultSet.getString("middle_name"));
                contact.setLastName(resultSet.getString("last_name"));
                contact.setAddress(resultSet.getString("address"));
                contact.setPhone(resultSet.getString("phone"));
                contact.setUserId(resultSet.getInt("user_id"));
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            close();
        }

        return contact;
    }

    public void updateContact(Contact contact, User user) throws Exception {

        query = "update contact set first_name =? ,middle_name =?,last_name = ?, address = ?, phone = ? where id=? and user_id=?";

        try {
            connect();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, contact.getFirstName());
            preparedStatement.setString(2, contact.getMiddleName());
            preparedStatement.setString(3, contact.getLastName());
            preparedStatement.setString(4, contact.getAddress());
            preparedStatement.setString(5, contact.getPhone());
            preparedStatement.setInt(6, contact.getId());
            preparedStatement.setInt(7, user.getId());


            System.out.println(preparedStatement);


            preparedStatement.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            close();
        }
    }

    public void deleteContact(Contact contact, User user) throws Exception {

        query = "delete from contact where id=? and user_id=?";

        try {
            connect();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, contact.getId());
            preparedStatement.setInt(2, user.getId());

            System.out.println(preparedStatement);


            preparedStatement.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            close();
        }
    }
}
