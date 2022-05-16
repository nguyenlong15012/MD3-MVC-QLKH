package com.example.qlkh.controller;

import com.example.qlkh.model.Custormer;
import com.example.qlkh.service.CustomerService;
import com.example.qlkh.service.CustomerServiceimpl;
import jdk.nashorn.internal.ir.RuntimeNode;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "CustomerServlet", urlPatterns = "/customers")
public class CustomerServlet extends HttpServlet {
    private CustomerService customerService = new CustomerServiceimpl();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null){
            action = "";
        }

        switch (action){
            case "create":
                createCustomer(req, resp);
                break;
            case "edit":
                updateCustomer(req, resp);
                break;
            case "delete":
                deleteCustomer(req,resp);
                break;
            default:
                break;
        }
    }

    private void deleteCustomer(HttpServletRequest req, HttpServletResponse resp) {
        int id = Integer.parseInt(req.getParameter("id"));
        Custormer custormer = this.customerService.findById(id);
        RequestDispatcher dispatcher;
        if (custormer == null){
            dispatcher = req.getRequestDispatcher("error-404.jsp");
        }else {
            this.customerService.remove(id);
            try {
                resp.sendRedirect("/customers");
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    private void updateCustomer(HttpServletRequest req, HttpServletResponse resp) {
        int id = Integer.parseInt(req.getParameter("id"));
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String address = req.getParameter("address");

        Custormer custormer = this.customerService.findById(id);
        RequestDispatcher dispatcher;

        if (custormer == null){
            dispatcher = req.getRequestDispatcher("error-404.jsp");
        }else {
            custormer.setName(name);
            custormer.setEmail(email);
            custormer.setAddress(address);
            this.customerService.update(id, custormer);
            req.setAttribute("customer", custormer);
            req.setAttribute("message", "Customer information was updated");
            dispatcher = req.getRequestDispatcher("customer/edit.jsp");
        }
        try {
            dispatcher.forward(req,resp);
        }catch (ServletException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private void createCustomer(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String address = req.getParameter("address");
        int id = (int) (Math.random()*10000);

        Custormer custormer = new Custormer(id, name, email, address);
        this.customerService.save(custormer);
        RequestDispatcher dispatcher = req.getRequestDispatcher("customer/create.jsp");
        req.setAttribute("message","New customer was created");

        try {
            dispatcher.forward(req,resp);
        }catch (ServletException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null){
            action = "";
        }
        switch (action){
            case "create":
                showCreateForm(req,resp);
                break;
            case "edit":
                showEditForm(req, resp);
                break;
            case "delete":
                showDeleteForm(req, resp);
                break;
            case "view":
                viewCustomer(req,resp);
                break;
            default:
                listCustomers(req,resp);
                break;
        }
    }

    private void viewCustomer(HttpServletRequest req, HttpServletResponse resp) {
        int id = Integer.parseInt(req.getParameter("id"));
        Custormer custormer = this.customerService.findById(id);
        RequestDispatcher dispatcher;
        if (custormer == null){
            dispatcher = req.getRequestDispatcher("error-404.jsp");
        }else {
            req.setAttribute("customer", custormer);
            dispatcher = req.getRequestDispatcher("customer/view.jsp");
        }
        try {
            dispatcher.forward(req, resp);
        }catch (ServletException | IOException e){
            e.printStackTrace();
        }
    }

    private void showDeleteForm(HttpServletRequest req, HttpServletResponse resp) {
        int id = Integer.parseInt(req.getParameter("id"));
        Custormer custormer = this.customerService.findById(id);
        RequestDispatcher dispatcher;
        if (custormer == null){
            dispatcher = req.getRequestDispatcher("error-404.jsp");
        }else {
            req.setAttribute("customer", custormer);
            dispatcher = req.getRequestDispatcher("customer/delete.jsp");
        }

        try {
            dispatcher.forward(req, resp);
        }catch (ServletException | IOException e){
            e.printStackTrace();
        }
    }

    private void showEditForm(HttpServletRequest req, HttpServletResponse resp) {
        int id = Integer.parseInt(req.getParameter("id"));
        Custormer custormer = this.customerService.findById(id);
        RequestDispatcher dispatcher;
        if (custormer == null){
            dispatcher = req.getRequestDispatcher("error-404.jsp");
        }else {
            req.setAttribute("customer", custormer);
            dispatcher = req.getRequestDispatcher("customer/edit.jsp");
        }

        try {
            dispatcher.forward(req, resp);
        }catch (ServletException | IOException e){
            e.printStackTrace();
        }
    }

    private void showCreateForm(HttpServletRequest req, HttpServletResponse resp) {
        RequestDispatcher dispatcher = req.getRequestDispatcher("customer/create.jsp");
        try {
            dispatcher.forward(req,resp);
        }catch (ServletException | IOException e){
            e.printStackTrace();
        }
    }

    private void listCustomers(HttpServletRequest req, HttpServletResponse resp) {
        List<Custormer> customers = this.customerService.findAll();
        req.setAttribute("customers", customers);

        RequestDispatcher dispatcher = req.getRequestDispatcher("customer/list.jsp");
        try {
            dispatcher.forward(req, resp);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
