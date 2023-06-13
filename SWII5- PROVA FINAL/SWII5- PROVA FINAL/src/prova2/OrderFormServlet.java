package prova2;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Order/*")
public class OrderFormServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private String jdbcURL;
    private String jdbcUsername;
    private String jdbcPassword;
    private Connection jdbcConnection;

    public void init() {
        jdbcURL = getServletContext().getInitParameter("jdbcURL");
        jdbcUsername = getServletContext().getInitParameter("jdbcUsername");
        jdbcPassword = getServletContext().getInitParameter("jdbcPassword");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getPathInfo();

        if (action == null) {
            action = "";
        }

        try {
            switch (action) {
                case "/new":
                    newForm(request, response);
                    break;
                case "/insert":
                    insert(request, response);
                    break;
                case "/delete":
                    delete(request, response);
                    break;
                case "/edit":
                    editForm(request, response);
                    break;
                case "/update":
                    update(request, response);
                    break;
                default:
                	response.sendRedirect("/prova2");
                    break;
            }
        } catch (SQLException | ParseException ex) {
            throw new ServletException(ex);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    private void newForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/formOrder.jsp");
        dispatcher.forward(request, response);
    }

    private void editForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        int ordNo = Integer.parseInt(request.getParameter("id"));
        Order order = getByOrdNo(ordNo);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/formOrder.jsp");
        request.setAttribute("order", order);
        dispatcher.forward(request, response);
    }

    private void insert(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ParseException {
        double purchAmt = Double.parseDouble(request.getParameter("purchAmt"));
        Date ordDate = new SimpleDateFormat("dd/MM/yyyy").parse(request.getParameter("ordDate"));
        int customerId = Integer.parseInt(request.getParameter("customerId"));
        int salesmanId = Integer.parseInt(request.getParameter("salesmanId"));

        Order entity = new Order(purchAmt, ordDate, customerId, salesmanId);
        insertOrder(entity);
        response.sendRedirect("/prova2");
    }

    private void update(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ParseException {
        int ordNo = Integer.parseInt(request.getParameter("ordNo"));
        double purchAmt = Double.parseDouble(request.getParameter("purchAmt"));
        Date ordDate = new SimpleDateFormat("dd/MM/yyyy").parse(request.getParameter("ordDate"));
        int customerId = Integer.parseInt(request.getParameter("customerId"));
        int salesmanId = Integer.parseInt(request.getParameter("salesmanId"));

        Order entity = new Order(ordNo, purchAmt, ordDate, customerId, salesmanId);
        updateOrder(entity);
        response.sendRedirect("/prova2");
    }

    private void delete(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int ordNo = Integer.parseInt(request.getParameter("id"));

        Order entity = new Order(ordNo);
        deleteOrder(entity);
        response.sendRedirect("/prova2");
    }

    private void connect() throws SQLException {
        if (jdbcConnection == null || jdbcConnection.isClosed()) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                throw new SQLException(e);
            }
            jdbcConnection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
        }
    }

    private void disconnect() throws SQLException {
        if (jdbcConnection != null && !jdbcConnection.isClosed()) {
            jdbcConnection.close();
        }
    }

    private Order getByOrdNo(int ordNo) throws SQLException {
        Order order = null;
        String sql = "SELECT * FROM orders WHERE ord_no = ?";

        connect();

        PreparedStatement statement = jdbcConnection.prepareStatement(sql);
        statement.setInt(1, ordNo);

        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            double purchAmt = resultSet.getDouble("purch_amt");
            Date ordDate = resultSet.getDate("ord_date");
            int customerId = resultSet.getInt("customer_id");
            int salesmanId = resultSet.getInt("salesman_id");

            order = new Order(ordNo, purchAmt, ordDate, customerId, salesmanId);
        }

        resultSet.close();
        statement.close();
        disconnect();
        return order;
    }

    private boolean insertOrder(Order entity) throws SQLException {
        String sql = "INSERT INTO orders (purch_amt, ord_date, customer_id, salesman_id) VALUES (?, ?, ?, ?)";
        connect();

        PreparedStatement statement = jdbcConnection.prepareStatement(sql);
        statement.setDouble(1, entity.getPurchAmt());
        statement.setDate(2, new java.sql.Date(entity.getOrdDate().getTime()));
        statement.setInt(3, entity.getCustomerId());
        statement.setInt(4, entity.getSalesmanId());

        boolean rowInserted = statement.executeUpdate() > 0;
        statement.close();
        disconnect();
        return rowInserted;
    }

    private boolean deleteOrder(Order entity) throws SQLException {
        String sql = "DELETE FROM orders WHERE ord_no = ?";

        connect();

        PreparedStatement statement = jdbcConnection.prepareStatement(sql);
        statement.setInt(1, entity.getOrdNo());

        boolean rowDeleted = statement.executeUpdate() > 0;
        statement.close();
        disconnect();
        return rowDeleted;
    }

    private boolean updateOrder(Order entity) throws SQLException {
        String sql = "UPDATE orders SET purch_amt = ?, ord_date = ?, customer_id = ?, salesman_id = ?";
        sql += " WHERE ord_no = ?";
        connect();

        PreparedStatement statement = jdbcConnection.prepareStatement(sql);
        statement.setDouble(1, entity.getPurchAmt());
        statement.setDate(2, new java.sql.Date(entity.getOrdDate().getTime()));
        statement.setInt(3, entity.getCustomerId());
        statement.setInt(4, entity.getSalesmanId());
        statement.setInt(5, entity.getOrdNo());

        boolean rowUpdated = statement.executeUpdate() > 0;
        statement.close();
        disconnect();
        return rowUpdated;
    }
}
