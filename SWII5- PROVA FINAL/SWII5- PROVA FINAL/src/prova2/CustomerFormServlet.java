package prova2;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Customer/*")
public class CustomerFormServlet extends HttpServlet {
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
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    private void newForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	RequestDispatcher dispatcher = request.getRequestDispatcher("/formCustomer.jsp");
        dispatcher.forward(request, response);
    }

    private void editForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Customer customer = getById(id);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/formCustomer.jsp");
        request.setAttribute("customer", customer);
        dispatcher.forward(request, response);
    }

    private void insert(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        String custName = request.getParameter("nome");
        String city = request.getParameter("cidade");
        int grade = Integer.parseInt(request.getParameter("classificacao"));
        int salesmanId = 0;

        Customer entity = new Customer(custName, city, grade, salesmanId);
        insertCustomer(entity);
        response.sendRedirect("/prova2");
    }

    private void update(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String custName = request.getParameter("nome");
        String city = request.getParameter("cidade");
        int grade = Integer.parseInt(request.getParameter("classificacao"));
        int salesmanId = 0;

        Customer entity = new Customer(id, custName, city, grade, salesmanId);

        updateCustomer(entity);
        response.sendRedirect("/prova2");
    }

    private void delete(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));

        Customer entity = new Customer(id);
        deleteCustomer(entity);

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

    private Customer getById(int id) throws SQLException {
        Customer cliente = null;
        String sql = "SELECT * FROM customer WHERE customer_id = ?";

        connect();

        PreparedStatement statement = jdbcConnection.prepareStatement(sql);
        statement.setInt(1, id);

        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            String custName = resultSet.getString("cust_name");
            String city = resultSet.getString("city");
            int grade = resultSet.getInt("grade");
            int salesmanId = resultSet.getInt("salesman_id");

            cliente = new Customer(id, custName, city, grade, salesmanId);
        }

        resultSet.close();
        statement.close();
        disconnect();
        return cliente;
    }

    private boolean insertCustomer(Customer entity) throws SQLException {
        String sql = "INSERT INTO customer (customer_id, cust_name, city, grade, salesman_id) VALUES (?, ?, ?, ?, ?)";
        connect();

        PreparedStatement statement = jdbcConnection.prepareStatement(sql);
        statement.setInt(1, entity.getCustomerId());
        statement.setString(2, entity.getCustName());
        statement.setString(3, entity.getCity());
        statement.setInt(4, entity.getGrade());
        statement.setInt(5, entity.getSalesmanId());

        boolean rowInserted = statement.executeUpdate() > 0;
        statement.close();
        disconnect();
        return rowInserted;
    }

    private boolean deleteCustomer(Customer entity) throws SQLException {
        String sql = "DELETE FROM customer WHERE customer_id = ?";

        connect();

        PreparedStatement statement = jdbcConnection.prepareStatement(sql);
        statement.setInt(1, entity.getCustomerId());

        boolean rowDeleted = statement.executeUpdate() > 0;
        statement.close();
        disconnect();
        return rowDeleted;
    }

    private boolean updateCustomer(Customer entity) throws SQLException {
        String sql = "UPDATE customer SET cust_name = ?, city = ?, grade = ?, salesman_id = ?";
        sql += " WHERE customer_id = ?";
        connect();

        PreparedStatement statement = jdbcConnection.prepareStatement(sql);
        statement.setString(1, entity.getCustName());
        statement.setString(2, entity.getCity());
        statement.setInt(3, entity.getGrade());
        statement.setInt(4, entity.getSalesmanId());
        statement.setInt(5, entity.getCustomerId());

        boolean rowUpdated = statement.executeUpdate() > 0;
        statement.close();
        disconnect();
        return rowUpdated;
    }
}
