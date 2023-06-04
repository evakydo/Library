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
import java.sql.Connection;
import java.sql.Statement;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import mainClasses.JSON_Converter;
import mainClasses.Librarian;
import mainClasses.Student;

/**
 *
 * @author evakydo
 */
public class updateuser extends HttpServlet {

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
            out.println("<title>Servlet updateuser</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet updateuser at " + request.getContextPath() + "</h1>");
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
        /*Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        String update="UPDATE students SET personalpage='"+personalpage+"' WHERE username = '"+username+"'";
        stmt.executeUpdate(update);*/
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        JSON_Converter json_con = new JSON_Converter();
        Gson gson = new Gson();
        HttpSession session=request.getSession();
        String type = (String)session.getAttribute("usertype");
        String username = (String)session.getAttribute("loggedIn");
        String update, Json;
        
        try {
            Connection con = DB_Connection.getConnection();
            Statement stmt = con.createStatement();
            
            if(type == "student"){
                Student s = gson.fromJson(request.getReader(), Student.class);
                Json = json_con.studentToJSON(s);
                update="UPDATE students SET personalpage='"+s.getPersonalpage()+"',password='"+s.getPassword()+"',firstname='"+s.getFirstname()+"',lastname='"+s.getLastname()+"',country='"+s.getCountry()+"',birthdate='"+s.getBirthdate()+"',city='"+s.getCity()+"',address='"+s.getAddress()+"',telephone='"+s.getTelephone()+"' WHERE username = '"+username+"'";
            }
            else{
                Librarian l = gson.fromJson(request.getReader(), Librarian.class);
                Json = json_con.librarianToJSON(l);
                update="UPDATE librarians SET personalpage='"+l.getPersonalpage()+"',password='"+l.getPassword()+"',firstname='"+l.getFirstname()+"',lastname='"+l.getLastname()+"',country='"+l.getCountry()+"',birthdate='"+l.getBirthdate()+"',city='"+l.getCity()+"',address='"+l.getAddress()+"',telephone='"+l.getTelephone()+"',libraryname='"+l.getLibraryname()+"',libraryinfo='"+l.getLibraryinfo()+"' WHERE username = '"+username+"'";
            }

            stmt.executeUpdate(update);
            response.getWriter().write(Json);
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
