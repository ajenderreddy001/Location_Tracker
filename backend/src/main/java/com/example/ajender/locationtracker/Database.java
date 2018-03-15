package com.example.ajender.locationtracker;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
//import com.google.appengine.api.utils.SystemProperty;

public class Database extends HttpServlet {
    ObjectOutputStream oos;
    ObjectInputStream ois;
    int queryupadted=-1;
    public String[] frnd=new String[10];
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            Class.forName("com.mysql.jdbc.GoogleDriver");

                // Local MySQL instance to use during development.
               // Class.forName("com.mysql.jdbc.Driver");
               // url = "jdbc:mysql://127.0.0.1:3306/guestbook?user=root";

                // Alternatively, connect to a Google Cloud SQL instance using:
                // jdbc:mysql://ip-address-of-google-cloud-sql-instance:3306/guestbook?user=root
        } catch (Exception e) {
            e.printStackTrace();
            resp.getWriter().println("class not loaded");
        }
        try {
            int exist=-1;
            Connection conn = DriverManager.getConnection("jdbc:google:mysql://strong-aegis-106207:mysqlinstance1/database1","ajender","12345678");
            try {

                ResultSet rs;
                ois=new ObjectInputStream(new BufferedInputStream(req.getInputStream()));
                String[] datareceive=(String[])ois.readObject();
                String phone=datareceive[2];
                String statement="select count(*) from register where phone="+"'"+phone+"'";
                PreparedStatement stmt = conn.prepareStatement(statement);
                rs= stmt.executeQuery();
                rs.next();
                exist=rs.getInt(1);
                rs.close();
                if(exist==0){
                    statement="insert into register values("+"'"+datareceive[0]+"'"+",'"+datareceive[1]+"'"+",'"+datareceive[2]+"'"+",'"+0+"'"+",'"+0+"'"+","+0+","+0+")";
                    stmt=conn.prepareStatement(statement);
                    queryupadted=stmt.executeUpdate();
                }
                statement="select name from register where phone!="+"'"+phone+"'";
                stmt = conn.prepareStatement(statement);
                rs= stmt.executeQuery();
                int j=0;
                while (rs.next()){
                    j++;
                }
                rs.beforeFirst();
                frnd=new String[j];
                j=0;
                while(rs.next()){
                    String name=rs.getString("name");
                    frnd[j]=name;
                    j++;
                }
                rs.close();
                oos=new ObjectOutputStream(new BufferedOutputStream(resp.getOutputStream()));
                oos.reset();
                oos.writeObject(frnd);
                oos.flush();
                ois.close();
                oos.close();
                //statement="commit";
                //stmt=conn.prepareStatement(statement);
                //stmt.executeQuery();
                resp.getWriter().println("statement executed");
            }
            catch(ClassNotFoundException e){
                resp.getWriter().println("Class Not exception"+e.getMessage());
            }
            finally {
                conn.close();
            }
        } catch (SQLException e) {
            resp.getWriter().println("connection problem"+""+queryupadted+e.getMessage());
            e.printStackTrace();
        }
    }
}