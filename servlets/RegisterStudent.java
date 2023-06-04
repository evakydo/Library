/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import database.DB_Connection;
import database.tables.EditStudentsTable;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import mainClasses.JSON_Converter;
import mainClasses.Student;

/**
 *
 * @author evakydo
 */
public class RegisterStudent extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet RegisterStudent</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet RegisterStudent at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {

        JSON_Converter json_con = new JSON_Converter();
        Gson gson = new Gson();
        Student s = gson.fromJson(request.getReader(), Student.class);
        String JsonFromStudent = json_con.studentToJSON(s);
        
        try {
            Connection con = DB_Connection.getConnection();

            Statement stmt = con.createStatement();

            String insertQuery = "INSERT INTO "
                    + " students (username,email,password,firstname,lastname,birthdate,gender,country,city,address,student_type,"
                    + "student_id,student_id_from_date,student_id_to_date,university,department,lat,lon,telephone,personalpage)"
                    + " VALUES ("
                    + "'" + s.getUsername() + "',"
                    + "'" + s.getEmail() + "',"
                    + "'" + s.getPassword() + "',"
                    + "'" + s.getFirstname() + "',"
                    + "'" + s.getLastname() + "',"
                    + "'" + s.getBirthdate() + "',"
                    + "'" + s.getGender() + "',"
                    + "'" + s.getCountry() + "',"
                    + "'" + s.getCity() + "',"
                    + "'" + s.getAddress() + "',"
                    + "'" + s.getStudent_type() + "',"
                    + "'" + s.getStudent_id() + "',"
                    + "'" + s.getStudent_id_from_date() + "',"
                    + "'" + s.getStudent_id_to_date()+ "',"
                    + "'" + s.getUniversity() + "',"
                    + "'" + s.getDepartment() + "',"
                    + "'" + s.getLat() + "',"
                    + "'" + s.getLon() + "',"
                    + "'" + s.getTelephone() + "',"
                    + "'" + s.getPersonalpage()+ "'"
                    + ")";
            
            stmt.executeUpdate(insertQuery);
            response.getWriter().write(JsonFromStudent);
            response.setStatus(200);
            stmt.close();

        } catch (SQLException ex) {
            Logger.getLogger(EditStudentsTable.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(RegisterStudent.class.getName()).log(Level.SEVERE, null, ex);
        }
   
    }
    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
