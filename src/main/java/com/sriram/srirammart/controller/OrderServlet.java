package com.sriram.srirammart.controller;

import com.sriram.srirammart.dto.OrderItemLineDTO;
import com.sriram.srirammart.dto.OrderViewDTO;
import com.sriram.srirammart.listener.AppContextListener;
import com.sriram.srirammart.model.Order;
import com.sriram.srirammart.model.OrderItem;
import com.sriram.srirammart.model.Product;
import com.sriram.srirammart.model.Role;
import com.sriram.srirammart.service.OrderService;
import com.sriram.srirammart.service.ProductService;
import com.sriram.srirammart.util.SessionUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/orders")
public class OrderServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        if (!SessionUtil.requireRole(req, resp, Role.BUYER)) return;

        OrderService orderService = (OrderService) getServletContext().getAttribute(AppContextListener.ORDER_SERVICE);
        ProductService productService = (ProductService) getServletContext().getAttribute(AppContextListener.PRODUCT_SERVICE);
        long buyerId = SessionUtil.getUserId(req);

        List<Order> orders = orderService.getOrdersByBuyer(buyerId);
        List<OrderViewDTO> views = new ArrayList<>();

        for (Order order : orders) {
            List<OrderItemLineDTO> lines = new ArrayList<>();
            for (OrderItem item : orderService.getOrderItems(order.getId())) {
                Product product = productService.getProductById(item.getProductId());
                String name = product != null ? product.getName() : "(deleted product)";
                lines.add(new OrderItemLineDTO(name, item.getQuantity(), item.getPrice()));
            }
            views.add(new OrderViewDTO(order, lines));
        }

        req.setAttribute("orderViews", views);
        req.getRequestDispatcher("/WEB-INF/views/orders/my.jsp").forward(req, resp);
    }
}