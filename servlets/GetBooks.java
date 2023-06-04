/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import com.google.gson.Gson;
import database.DB_Connection;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import mainClasses.Book;

/**
 *
 * @author evakydo
 */
public class GetBooks extends HttpServlet {

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
            out.println("<title>Servlet GetBooks</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet GetBooks at " + request.getContextPath() + "</h1>");
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
        HttpSession session=request.getSession();      
        response.setStatus(200);   
            
        try (PrintWriter out = response.getWriter()) {
            
            Connection con = DB_Connection.getConnection();
            Statement stmt = con.createStatement();
            ArrayList<Book> books = new ArrayList<Book>();
            ResultSet rs; 
            String finalJson="[";
            try {            
                rs = stmt.executeQuery("SELECT * FROM books");
                while (rs.next()) {
                    String json = DB_Connection.getResultsToJSON(rs);
                    Gson gson = new Gson();
                    Book book = gson.fromJson(json, Book.class);
                    books.add(book);
                    finalJson += gson.toJson(book, Book.class);
                    finalJson += ",";
                }                  
                finalJson = finalJson.substring(0, finalJson.length() - 1);
                finalJson += "]";
                response.getWriter().write(finalJson);
            } 
            catch (Exception e) {
                System.err.println("Got an exception! ");
                System.err.println(e.getMessage());
            }   
        
        } catch (SQLException ex) {
            Logger.getLogger(GetStudent.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(GetStudent.class.getName()).log(Level.SEVERE, null, ex);
        }
          
        
            
        
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
        processRequest(request, response);
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
