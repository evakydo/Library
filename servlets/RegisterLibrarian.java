/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import com.google.gson.Gson;
import database.DB_Connection;
import database.tables.EditStudentsTable;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import mainClasses.JSON_Converter;
import mainClasses.Librarian;

/**
 *
 * @author evakydo
 */
public class RegisterLibrarian extends HttpServlet {

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
            out.println("<title>Servlet RegisterLibrarian</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet RegisterLibrarian at " + request.getContextPath() + "</h1>");
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        JSON_Converter json_con = new JSON_Converter();
        Gson gson = new Gson();
        Librarian s = gson.fromJson(request.getReader(), Librarian.class);
        String JsonFromLibrarian = json_con.librarianToJSON(s);
        try {
            Connection con = DB_Connection.getConnection();

            Statement stmt = con.createStatement();

            String insertQuery = "INSERT INTO "
                    + " librarians (username,email,password,firstname,lastname,birthdate,gender,country,city,address,libraryname,"
                    + "library_id,libraryinfo,lat,lon,telephone,personalpage)"
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
                    + "'" + s.getLibraryname() + "',"
                    + "'" + s.getLibrary_id() + "',"
                    + "'" + s.getLibraryinfo()+ "',"
                    + "'" + s.getLat() + "',"
                    + "'" + s.getLon() + "',"
                    + "'" + s.getTelephone() + "',"
                    + "'" + s.getPersonalpage()+ "'"
                    + ")";
            
            stmt.executeUpdate(insertQuery);
            response.getWriter().write(JsonFromLibrarian);
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
