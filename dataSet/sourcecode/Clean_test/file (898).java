package com.mongodbclass;

import com.mongodb.*;
import com.mongodb.util.Base64Codec;
import org.bson.types.ObjectId;
import sun.awt.SunToolkit;
import sun.misc.Cleaner;

import javax.sound.midi.Soundbank;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Insert (DBObject) --> BasicDBObject
 * Find
 * Update
 * Remove
 * <p/>
 * Created by Michael.Shreiber on 3/30/14.
 */
public class CRUD_demo {

    //Java document representation
    static void createDoc() {
        BasicDBObject doc = new BasicDBObject();

        //String : String
        doc.put("userName", "jeremy");
        //String : Date
        doc.put("birthDate", new Date(20140330));
        //String : Array
        doc.put("languages", Arrays.asList("Java", "Python"));
        //String : BasicDBObject
        doc.put("address", new BasicDBObject("street", "20 main")
                .append("town", "Westfield")
                .append("zip", "56789"));

    }

    //Insert
    static void insertTest() throws UnknownHostException {
        MongoClient client = new MongoClient();
        DB courseDB = client.getDB("course");
        DBCollection collection = courseDB.getCollection("isertTest");

        //DBObject doc = new BasicDBObject("_id",new ObjectId()).append("x", 1);
        DBObject doc = new BasicDBObject().append("x", 1);
        System.out.println(doc);
        collection.insert(doc);
        //adds "_id"
        System.out.println(doc);

        //Inserting a list of docs
        DBObject doc2 = new BasicDBObject().append("x", 2);
        collection.insert(Arrays.asList(doc, doc2));

    }

    //Find general
    static void findTest() throws UnknownHostException {
        MongoClient client = new MongoClient();
        DB courseDB = client.getDB("course");
        DBCollection collection = courseDB.getCollection("isertTest");

        for (int i = 0; i < 10; i++) {
            collection.insert(new BasicDBObject("x", new Random().nextInt()));
        }

        System.out.println("findOne: ");
        DBObject doc = collection.findOne();
        System.out.println(doc);

        //important to close the cursor
        System.out.println("find: ");
        DBCursor cursor = collection.find();
        try {
            while (cursor.hasNext()) {
                System.out.println(cursor.next());
            }
        } finally {
            cursor.close();
        }

        System.out.println("count:");
        long count = collection.count();
        System.out.println(count);


    }

    //Find by selecting
    static void findQueriesTest() throws UnknownHostException {
        MongoClient client = new MongoClient();
        DB courseDB = client.getDB("course");
        DBCollection collection = courseDB.getCollection("fieldSelectionTest");

        collection.drop();

        //insert 10 documents with 2 random integers
        for (int i = 0; i < 10; i++) {
            collection.insert(
                    new BasicDBObject("x", new Random().nextInt(2))
                            .append("y", new Random().nextInt(100))
                            .append("z", new Random().nextInt(1000)));
        }

        DBObject query = QueryBuilder.start("x").is(0)
                .and("y").greaterThan(10).lessThan(70).get();

        //the second parameter defines the field that won't be displayed
        //DBCursor cursor = collection.find(query, new BasicDBObject("x", false));

        //display only "y" field, and don't display "_id"
        DBCursor cursor = collection.find(query, new BasicDBObject("y", true).append("_id", false));

        try {
            while (cursor.hasNext()) {
                System.out.println(cursor.next());
            }
        } finally {
            cursor.close();
        }

    }

    static void queryDotNotation() throws UnknownHostException {
        MongoClient client = new MongoClient();
        DB courseDB = client.getDB("course");
        DBCollection lines = courseDB.getCollection("dotNotationTest");
        lines.drop();
        Random rand = new Random();



        for (int i = 0; i < 10; i++) {
            lines.insert(
                    new BasicDBObject("_id", i)
                            .append("start", new BasicDBObject("x", rand.nextInt(90) + 10)
                                    .append("y", rand.nextInt(90) + 10)

                            )
                            .append("end", new BasicDBObject("x", rand.nextInt(90) + 10)
                                    .append("y", rand.nextInt(90) + 10)
                            )
            );
        }

        QueryBuilder builder = QueryBuilder.start("strat.x").greaterThan(50);
        DBCursor cursor = lines.find(builder.get());

        try {
            while (cursor.hasNext()) {
                System.out.println(cursor.next());
            }
        } finally {
            cursor.close();
        }
    }

    static void sortSkipLimitTest() throws UnknownHostException {
        MongoClient client = new MongoClient();
        DB courseDB = client.getDB("course");
        DBCollection lines = courseDB.getCollection("sortSkipLimitTest");
        lines.drop();
        Random rand = new Random();

        for (int i = 0; i < 10; i++) {
            lines.insert(
                    new BasicDBObject("_id", i)
                            .append("start", new BasicDBObject("x", rand.nextInt(90) + 10)
                                    .append("y", rand.nextInt(90) + 10)

                            )
                            .append("end", new BasicDBObject("x", rand.nextInt(90) + 10)
                                    .append("y", rand.nextInt(90) + 10)
                            )
            );
        }

        //DBCursor cursor = lines.find().sort(new BasicDBObject("_id", -1)).skip(2).limit(3);
        DBCursor cursor = lines.find().sort(new BasicDBObject("start.x", -1).append("start.y", -1));

        try {
            while (cursor.hasNext()) {
                System.out.println(cursor.next());
            }
        } finally {
            cursor.close();
        }

    }

    static void updateTest() throws UnknownHostException {
        MongoClient client = new MongoClient();
        DB courseDB = client.getDB("course");
        DBCollection collection = courseDB.getCollection("updateRemoveTest");
        collection.drop();

        List<String> names = Arrays.asList("alice", "bobby", "cathy", "david", "ethan");
        for (String s : names){
            collection.insert(new BasicDBObject("_id", s));
        }

        collection.update(new BasicDBObject("_id", "alice"),
                new BasicDBObject("age", 24));

        collection.update(new BasicDBObject("_id", "alice"),
                new BasicDBObject("$set", new BasicDBObject("gender", "F")));

        collection.update(new BasicDBObject("_id", "alice"),
                new BasicDBObject("$inc", new BasicDBObject("age", "1")));

        //upsert - true; multi - false.
        collection.update(new BasicDBObject("_id", "frank"),
                new BasicDBObject("$set", new BasicDBObject("age", "33")), true, false);

        //for each document: upsert - true; multi - true.
        collection.update(new BasicDBObject(),
                new BasicDBObject("$set", new BasicDBObject("title", "Dr.")), true, true);



        DBCursor cursor = collection.find();
        try {
            while (cursor.hasNext()) {
                System.out.println(cursor.next());
            }
        } finally {
            cursor.close();
        }

    }

    static void removeTest() throws UnknownHostException {
        MongoClient client = new MongoClient();
        DB courseDB = client.getDB("course");
        DBCollection collection = courseDB.getCollection("updateRemoveTest");
        collection.drop();

        List<String> names = Arrays.asList("alice", "bobby", "cathy", "david", "ethan");
        for (String s : names){
            collection.insert(new BasicDBObject("_id", s));
        }


        DBCursor cursor = collection.find();
        try {
            while (cursor.hasNext()) {
                System.out.println(cursor.next());
            }
        } finally {
            cursor.close();
        }

        //remove all documents in the collection
        //collection.remove(new BasicDBObject());

        //remove by ID
        collection.remove(new BasicDBObject("_id", "alice"));



        System.out.println();
        System.out.println("**************************");

        cursor = collection.find();
        try {
            while (cursor.hasNext()) {
                System.out.println(cursor.next());
            }
        } finally {
            cursor.close();
        }

    }

    static void findAndModifyTest(){

    }




    public static void main(String[] args) throws UnknownHostException {
        //insertTest();
        //findTest();
        //findQueriesTest();
        //queryDotNotation();
        sortSkipLimitTest();
        //updateTest();
        //removeTest();


    }


}
