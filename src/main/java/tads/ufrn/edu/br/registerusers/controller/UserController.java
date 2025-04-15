package tads.ufrn.edu.br.registerusers.controller;
import tads.ufrn.edu.br.registerusers.model.User;
import tads.ufrn.edu.br.registerusers.repository.UserRepository;

import jakarta.servlet.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@Controller
public class UserController {

    @GetMapping("/cadastro")
    public void showForm(HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<html><head><title>User Registration</title></head><body>");
        out.println("<h2>Register a new user</h2>");
        out.println("<form method='post' action='/cadastro'>");
        out.println("Name: <input type='text' name='name'><br>");
        out.println("Email: <input type='email' name='email'><br>");
        out.println("Password: <input type='password' name='password'><br>");
        out.println("<input type='submit' value='Register'>");
        out.println("</form>");
        out.println("</body></html>");
    }

    @PostMapping("/cadastro")
    public void registerUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
    
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
  
        if (email == null || !email.matches("^[\\w\\.-]+@[\\w\\.-]+\\.\\w+$")) {
            out.println("<html><body>");
            out.println("<h2>Invalid email format!</h2>");
            out.println("<a href='/cadastro'>Back</a>");
            out.println("</body></html>");
            return;
        }

        if (UserRepository.existsByEmail(email)) {
            out.println("<html><body>");
            out.println("<h2>Email already registered!</h2>");
            out.println("<a href='/cadastro'>Try again</a>");
            out.println("</body></html>");
            return;
        }
    
        User user = new User(name, email, password);
        UserRepository.save(user);
    
        HttpSession session = request.getSession(true);
        session.setAttribute("user", user);
    
        Cookie sessionCookie = new Cookie("sessionId", session.getId());
        sessionCookie.setMaxAge(30 * 60);
        sessionCookie.setHttpOnly(true);
        response.addCookie(sessionCookie);
    
        response.sendRedirect("/dashboard");
    }

    @GetMapping("/dashboard")
    public void dashboard(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);
        PrintWriter out = response.getWriter();
    
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("/cadastro");
            return;
        }
    
        User user = (User) session.getAttribute("user");
    
        response.setContentType("text/html");
        out.println("<html><head><title>Dashboard</title></head><body>");
        out.println("<h2>Welcome, " + user.getName() + "!</h2>");
        out.println("<p>Your email: " + user.getEmail() + "</p>");
        out.println("<a href='/users'>See all users</a><br>");
        out.println("<a href='/cadastro'>Back</a>");
        out.println("</body></html>");
    }

    @GetMapping("/users")
    public void listUsers(HttpServletResponse response) throws IOException {
        List<User> users = UserRepository.findAll();

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html><head><title>All Users</title></head><body>");
        out.println("<h2>Registered Users</h2>");
        out.println("<ul>");

        for (User user : users) {
            out.println("<li>" + user.getName() + " - " + user.getEmail() + "</li>");
        }

        out.println("</ul>");
        out.println("<a href='/dashboard'>Back</a>");
        out.println("</body></html>");
    }
}
