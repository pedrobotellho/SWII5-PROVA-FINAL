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

@WebServlet("/Salesman/*")
public class SalesmanFormServlet extends HttpServlet {
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
        RequestDispatcher dispatcher = request.getRequestDispatcher("/formSalesman.jsp");
        dispatcher.forward(request, response);
    }

    private void editForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Salesman salesman = getById(id);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/formSalesman.jsp");
        request.setAttribute("salesman", salesman);
        dispatcher.forward(request, response);
    }

    private void insert(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        String name = request.getParameter("name");
        String city = request.getParameter("city");
        double commission = Double.parseDouble(request.getParameter("commission"));

        Salesman entity = new Salesman(name, city, commission);
        insertSalesman(entity);
        response.sendRedirect("/prova2");
    }

    private void update(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("salesmanId"));
        String name = request.getParameter("name");
        String city = request.getParameter("city");
        double commission = Double.parseDouble(request.getParameter("commission"));

        Salesman entity = new Salesman(id, name, city, commission);

        updateSalesman(entity);
        response.sendRedirect("/prova2");
    }

    private void delete(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));

        Salesman entity = new Salesman(id);
        deleteSalesman(entity);

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

    private Salesman getById(int id) throws SQLException {
        Salesman salesman = null;
        String sql = "SELECT * FROM salesman WHERE salesman_id = ?";

        connect();

        PreparedStatement statement = jdbcConnection.prepareStatement(sql);
        statement.setInt(1, id);

        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            String name = resultSet.getString("name");
            String city = resultSet.getString("city");
            double commission = resultSet.getDouble("commission");

            salesman = new Salesman(id, name, city, commission);
        }

        resultSet.close();
        statement.close();
        disconnect();
        return salesman;
    }

    private boolean insertSalesman(Salesman entity) throws SQLException {
        String sql = "INSERT INTO salesman (salesman_id, name, city, commission) VALUES (?, ?, ?, ?)";
        connect();

        PreparedStatement statement = jdbcConnection.prepareStatement(sql);
        statement.setInt(1, entity.getSalesmanId());
        statement.setString(2, entity.getName());
        statement.setString(3, entity.getCity());
        statement.setDouble(4, entity.getCommission());

        boolean rowInserted = statement.executeUpdate() > 0;
        statement.close();
        disconnect();
        return rowInserted;
    }

    private boolean deleteSalesman(Salesman entity) throws SQLException {
        String sql = "DELETE FROM salesman WHERE salesman_id = ?";

        connect();

        PreparedStatement statement = jdbcConnection.prepareStatement(sql);
        statement.setInt(1, entity.getSalesmanId());

        boolean rowDeleted = statement.executeUpdate() > 0;
        statement.close();
        disconnect();
        return rowDeleted;
    }

    private boolean updateSalesman(Salesman entity) throws SQLException {
        String sql = "UPDATE salesman SET name = ?, city = ?, commission = ?";
        sql += " WHERE salesman_id = ?";
        connect();

        PreparedStatement statement = jdbcConnection.prepareStatement(sql);
        statement.setString(1, entity.getName());
        statement.setString(2, entity.getCity());
        statement.setDouble(3, entity.getCommission());
        statement.setInt(4, entity.getSalesmanId());

        boolean rowUpdated = statement.executeUpdate() > 0;
        statement.close();
        disconnect();
        return rowUpdated;
    }
}
