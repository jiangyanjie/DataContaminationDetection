package ru.vladambulance.crew;

import ru.vladambulance.sql.AmbulanceSQL;
import ru.vladambulance.workers.Worker;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: sergei
 * Date: 2/12/13
 * Time: 1:10 PM
 * To change this template use File | Settings | File Templates.
 */
public class Crew {
    private static Connection connection = AmbulanceSQL.getInstance().getConnection();
    private Integer id;
    private Date date;
    private ArrayList<Worker> workers = new ArrayList<Worker>();
    private String name;

    public Crew(Integer id){
        if(id != null){
            this.id = id;
            this.retrieve();
        }else{
            //todo create new crew
            this.setDate(new Date());
        }
    }

    public static Connection getConnection() {
        return connection;
    }

    public static void setConnection(Connection connection) {
        Crew.connection = connection;
    }

    private void retrieve(){
        String sql = "SELECT crew.id," +
                "crew.name as name," +
                "crew.date as date," +
                "workers.id as worker " +
                "FROM crew " +
                "LEFT JOIN crew_log ON crew_log.crew = crew.id " +
                "LEFT JOIN workers ON workers.id = crew_log.worker " +
                "WHERE crew.id = ?";
        try {
            PreparedStatement preparedStatement = Crew.getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, this.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                this.setName(resultSet.getString("name"));
                this.setDate(new Date(
                        resultSet.getTimestamp("date").getTime()
                ));
                workers.add(
                        new Worker(
                                resultSet.getInt("worker")
                        )
                );
            }
            while(resultSet.next()){
                workers.add(
                        new Worker(
                                resultSet.getInt("worker")
                        )
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public void save(){
        if(this.getId() != null){
            String sql = "DELETE FROM crew_log " +
                    "WHERE crew_log.crew = ?";
            try {
                PreparedStatement preparedStatement = Crew.getConnection().prepareStatement(sql);
                preparedStatement.setInt(1, this.getId());
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }else{
            String sql = "INSERT INTO crew (" +
                    "name," +
                    "date" +
                    ")" +
                    "VALUES (" +
                    "?,?" +
                    ")";
            try {
                PreparedStatement preparedStatement = Crew.getConnection().prepareStatement(
                        sql,
                        Statement.RETURN_GENERATED_KEYS
                );
                preparedStatement.setString(1, this.getName());
                preparedStatement.setTimestamp(2,
                        new Timestamp(
                                this.getDate().getTime()
                        ));
                preparedStatement.executeUpdate();
                ResultSet resultSet = preparedStatement.getGeneratedKeys();
                if(resultSet.next()){
                    this.setId(resultSet.getInt(1));
                }
                resultSet.close();
            }catch (SQLException ex){
                ex.printStackTrace();
            }
        }
        try{
            String sql = "INSERT INTO crew_log (crew, worker) " +
                    "VALUES (?,?)";
            PreparedStatement preparedStatement = Crew.getConnection().prepareStatement(sql);
            for(int i = 0; i < workers.size(); i++){
                preparedStatement.setInt(1, this.getId());
                preparedStatement.setInt(2, workers.get(i).getId());
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public void delete(){
        String sql = "DELETE FROM crew_log " +
                "WHERE crew_log.crew = ?";
        try {
            PreparedStatement preparedStatement = Crew.getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, this.getId());
            preparedStatement.executeUpdate();

            sql = "DELETE FROM crew " +
                    "WHERE crew.id = ?";
            preparedStatement = Crew.getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, this.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public static ArrayList<Crew> getCrews(Date startDate, Date endDate){
        ArrayList<Crew> crews = new ArrayList<Crew>();
        String sql = "SELECT id FROM crew " +
                "WHERE date >= ? AND " +
                "date <= ? " +
                "ORDER BY date";
        try {
            PreparedStatement preparedStatement = Crew.getConnection().prepareStatement(sql);
            preparedStatement.setDate(1, new java.sql.Date(startDate.getTime()));
            preparedStatement.setDate(2, new java.sql.Date(endDate.getTime()));
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                crews.add(new Crew(resultSet.getInt("id")));
            }
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return crews;
    }

    public String getContents(){
        StringBuilder stringBuilder = new StringBuilder();
        for(Worker worker: this.getWorkers()){
            stringBuilder.append(worker.toString() + ", ");
        }
        return stringBuilder.toString();
    }

    public String toString(){
        return this.getName();
    }

    public void addWorker(Worker w){
        this.getWorkers().add(w);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public ArrayList<Worker> getWorkers() {
        return workers;
    }

    public void setWorkers(ArrayList<Worker> workers) {
        this.workers = workers;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
