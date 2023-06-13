package prova2;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Servlet implementation class ListServlet
 */
@WebServlet("/")
public class ListServlet extends HttpServlet {
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


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Customer> listCustomer;
		try {
			listCustomer = listAllCustomer();
		} catch (SQLException e) {
			listCustomer = null;
			e.printStackTrace();
		}
	    request.setAttribute("listCustomer", listCustomer);
	    
	    List<Salesman> listSalesman;
		try {
			listSalesman = listAllSalesman();
		} catch (SQLException e) {
			listSalesman = null;
			e.printStackTrace();
		}
	    request.setAttribute("listSalesman", listSalesman);
	    
	    List<Order> listOrders;
		try {
			listOrders = listAllOrders();
		} catch (SQLException e) {
			listOrders = null; 
			e.printStackTrace();
		}
	    request.setAttribute("listOrders", listOrders);
	        
	    RequestDispatcher dispatcher = request.getRequestDispatcher("List.jsp");
	    dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
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

	 private List<Order> listAllOrders() throws SQLException {
		List<Order> listOrdemVenda = new ArrayList<>();

        String sql = "SELECT * FROM orders";

        connect();

        Statement statement = jdbcConnection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        while (resultSet.next()) {
            int ordNo = resultSet.getInt("ord_no");
            double purchAmt = resultSet.getDouble("purch_amt");
            java.sql.Date ordDate = resultSet.getDate("ord_date");
            int customerId = resultSet.getInt("customer_id");
            int salesmanId = resultSet.getInt("salesman_id");

            Order ordemVenda = new Order(ordNo, purchAmt, ordDate, customerId, salesmanId);
            listOrdemVenda.add(ordemVenda);
        }

        resultSet.close();
        statement.close();

        disconnect();

        return listOrdemVenda;
	}

	 private List<Salesman> listAllSalesman() throws SQLException {
		List<Salesman> listVendedor = new ArrayList<>();
        
        String sql = "SELECT * FROM salesman";
         
        connect();
         
        Statement statement = jdbcConnection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
         
        while (resultSet.next()) {
            int salesmanId = resultSet.getInt("salesman_id");
            String name = resultSet.getString("name");
            String city = resultSet.getString("city");
            double commission = resultSet.getDouble("commission");
             
            Salesman vendedor = new Salesman(salesmanId, name, city, commission);
            listVendedor.add(vendedor);
        }
         
        resultSet.close();
        statement.close();
         
        disconnect();
         
        return listVendedor;
	}

	 private List<Customer> listAllCustomer() throws SQLException {
		List<Customer> listCliente = new ArrayList<>();

	    String sql = "SELECT * FROM customer";

	    connect();

	    Statement statement = jdbcConnection.createStatement();
	    ResultSet resultSet = statement.executeQuery(sql);

	    while (resultSet.next()) {
	        int id = resultSet.getInt("customer_id");
	        String custName = resultSet.getString("cust_name");
	        String city = resultSet.getString("city");
	        int grade = resultSet.getInt("grade");
	        int salesmanId = resultSet.getInt("salesman_id");

	        Customer cliente = new Customer(id, custName, city, grade, salesmanId);
	        listCliente.add(cliente);
	    }

	    resultSet.close();
	    statement.close();

	    disconnect();

	    return listCliente;
	}

}
