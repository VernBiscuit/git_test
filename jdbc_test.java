import java.io.*;
import java.sql.*;
import java.util.Properties;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import com.mysql.jdbc.Driver;


class jdbc_test {
    // Set up URL parameters
    private static final String DB_NAME = "sql-test";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "cc";
    private static final String CONNECTION_NAME = "arched-pier-398020:us-central1:sql-test";
      
    private static final String jdbcURL = String.format("jdbc:mysql:///%s", DB_NAME);
      


    static void runQueries(int count) {

	Connection conn = null;
	Statement stmt = null;
	ResultSet rs = null;
	//Class.forName("com.mysql.cj.jdbc.Driver");

	for (int i = 0; i < count; i++) {
	    try {

		// Direct jdbc connection to GCI mysql instance
		conn = DriverManager.getConnection("jdbc:mysql://sql-test?cloudSqlInstance=arched-pier-398020:us-central1:sql-test&socketFactory=com.google.cloud.sql.mysql.SocketFactory&user=janetclass&password=janetclass");


		// Connection using local proxy
		// conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/employees","root","cc");

		System.out.println("Connection Established successfully");

		stmt = conn.createStatement();

		//stmt.execute("CREATE DATABASE vern");
			      
		rs = stmt.executeQuery("SELECT last_name from employees.employees");
		while (rs.next()) {
		    rs.next();
		    String last_name = rs.getString("last_name");
		    System.out.println("Last name: " + last_name);
		}

		rs = stmt.executeQuery("select * from employees.employees where emp_no in (SELECT emp_no from employees.current_dept_emp where dept_no='d001') order by last_name");
		System.out.println("Emp no   birth date    first               last               gender   hire date");
		while (rs.next()) {
		    String emp_no = rs.getString("emp_no");
		    String birth_date = rs.getString("birth_date");
		    String last_name = rs.getString("last_name");
		    String first_name = rs.getString("first_name");
		    String gender = rs.getString("gender");
		    String hire_date = rs.getString("hire_date");
		    System.out.println(emp_no + "   " + birth_date + "    " + first_name + "   " + last_name + "   " + gender + "   " + hire_date);	    }

		rs = stmt.executeQuery("SELECT count(*) from employees.employees");
		rs.next();
		System.out.println("Count: " + rs.getString(1));

	    
		conn.close(); // close connection
		System.out.println("Connection Closed....");
	    } catch(SQLException ex) {
		System.out.println("SQL Exception:" + ex.getMessage());
		System.out.println("SQL State:" + ex.getSQLState());
		ex.printStackTrace();
	    	}
		}
    }

    public static void main(String[] args) {

	runQueries(20);
	

    }

}
