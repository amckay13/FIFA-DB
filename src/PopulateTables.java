import java.sql.*; // for standard JDBC programs
import java.math.*;

public class PopulateTables {
	final static String driverClass = "oracle.jdbc.driver.OracleDriver";
	final static String connectionURL = "jdbc:oracle:thin:@ginger.umd.edu:1521:dbclass1";
	final static String userID = "dbclass166";
	final static String userPassword = "OJU3rP7j";
	static Connection con = null;

	public static void connectDB() throws ClassNotFoundException, SQLException {
		Class.forName(driverClass);
		con = DriverManager.getConnection(connectionURL, userID, userPassword);

	}

	//
	public static void addCups(GatherData g1) throws SQLException {
		Statement st = con.createStatement();
		for (String s : g1.getCups()) {
			// System.out.println("Added:"+s);
			st.executeUpdate(s);
			System.out.println("Added:" + s);
		}
	}

	public static void addTeams(GatherData g1) throws SQLException {
		Statement st = con.createStatement();
		for (String s : g1.getTeams()) {
			System.out.println("Added:" + s);
			st.executeUpdate(s);
			System.out.println("Added:" + s);
		}
	}

	public static void addPlayers(String playerName, int p_id) throws SQLException {
		String out = "INSERT INTO PLAYER (P_ID,NAME) VALUES(" + p_id+", \'"+ playerName +"\')";

		System.out.println("Added Player:"+out);

		Statement st = con.createStatement();
		st.executeUpdate(out);
		System.out.println("Added Player:" + playerName+" - "+p_id);
	}
	public static void addP_ID_T_ID(int t_id, int p_id) throws SQLException {
		String out = "INSERT INTO PLAYS_ON (T_ID,P_ID) VALUES(" + t_id+", "+ p_id +")";
		Statement st = con.createStatement();
		st.executeUpdate(out);
		System.out.println("Mapped:" + p_id+" with "+t_id);
	}
	public static int getT_ID(String teamName, int year) {
		Statement st;
		try {
			connectDB();
		} catch (ClassNotFoundException | SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		int t_id = 0;
		try {
			st = con.createStatement();
			
			String search = "select * from TEAM t where t.NAME = \'" + teamName + "\' AND t.YEAR = " + year;
			System.out.println(search);
			ResultSet rs = st.executeQuery(search);
			while (rs.next()) {
				t_id = rs.getInt(5);
				System.out.println(t_id + "  ");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// System.out.println(rs.getInt("T_ID"));
		return t_id;

	}

	public static void main(String[] args) throws Exception {
		GatherData g1 = new GatherData();
		connectDB();
		// addCups(g1);
		// addTeams(g1);
		/*
		 * // ResultSet rs=stmt.executeQuery("select * from author");
		 * while(rs.next()) { System.out.println(rs.getInt(1)+"  "
		 * +rs.getString(2)+"  "); }
		 */

		con.close();

	}

	

}
