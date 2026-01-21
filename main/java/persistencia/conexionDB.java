/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia;
 
import java.sql.*;
/**
 *
 * @author jonac
 */
public class conexionDB {
    
    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=congaDDA;encrypt=true;trustServerCertificate=true";
    private static final String USER = "admin1";               
    private static final String PASSWORD = "admin1";
    
    
    static{
        try{
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        }catch(ClassNotFoundException e){
            System.out.println("Error al cargar el controlador JDBC: " + e.getMessage());
        }
    }
    
    public static Connection getConnection(){
        
        try{
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Conexion establecida con exito");
            return conn;
        }catch (SQLException e){
            System.out.println("Error al conectar con la base de datos: " + e.getMessage());
            return null;
        }
    }
}
