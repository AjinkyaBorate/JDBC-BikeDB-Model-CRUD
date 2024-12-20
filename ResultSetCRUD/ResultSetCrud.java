package ResultSetCRUD;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Scanner;

import connection.DBConnect;

import java.sql.Statement;

public class ResultSetCrud {

	static Connection con;
	static Scanner sc = new Scanner(System.in);
	Statement st;
	ResultSet rs;

	public ResultSetCrud() {
		try {
			con = DBConnect.getConnect();
			System.out.println("Connecton done...");
			st = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			rs = st.executeQuery("select * from model");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void fetchModels() {
		try {
			rs.beforeFirst();
			while (rs.next()) {
				System.out.println(rs.getInt(1) + "\t" + rs.getString(2) + "\t" + rs.getInt(3) + "\t" + rs.getInt(4));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void fetchModelsReverse() {
		try {
			rs.afterLast();
			while (rs.previous()) {
				System.out.println(rs.getInt(1) + "\t" + rs.getString(2) + "\t" + rs.getInt(3) + "\t" + rs.getInt(4));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void insertModels() {
		try {
			System.out.println("Enter model id:");
			int id = sc.nextInt();
			System.out.println("Enter the model name:");
			String name = sc.next();
			System.out.println("Enter model cost:");
			int cost = sc.nextInt();

			rs.moveToInsertRow();
			rs.updateInt(1, id);
			rs.updateString(2, name);
			rs.updateInt(3, cost);
			rs.updateInt(4, 0);

			rs.insertRow();
			System.out.println("Model Added...");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void deleteModel() {
		System.out.println("Enter Model ID: ");
		int id = sc.nextInt();
		boolean isFound = false;
		try {
			rs.beforeFirst();
			while (rs.next()) {
				if (rs.getInt(1) == id) {
					isFound = true;
					rs.deleteRow();
					System.out.println("Model " + id + " is deleted");
				}
			}
			if (!isFound) {
				System.out.println("Model not found...");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void updateModelcost() {
		System.out.println("Enter Model name: ");
		String name = sc.next();
		boolean isFound = false;
		try {
			rs.beforeFirst();
			while (rs.next()) {
				if (rs.getString(2).equalsIgnoreCase(name)) {
					isFound = true;
					System.out.println("Old cost:" + rs.getInt(3));
					System.out.println("Enter the new cost: ");
					int cost = sc.nextInt();
					rs.updateInt(3, cost);
					rs.updateRow();
					System.out.println("Model cost updated...");
				}
			}
			if (!isFound) {
				System.out.println("Model not found...");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		ResultSetCrud obj = new ResultSetCrud();
		int choice;
		char ch;
		do {
			System.out.println("---------------------------------------------------");
			System.out.println("1. Add a model");
			System.out.println("2. Delete model");
			System.out.println("3. Update model cost");
			System.out.println("4. View all models");
			System.out.println("5. View all model in reverse");
			System.out.println("6. Exit");
			System.out.println("---------------------------------------------------");

			System.out.println("Enter your choice:");
			choice = sc.nextInt();
			switch (choice) {
			case 1:
				obj.insertModels();
				break;
			case 2:
				obj.deleteModel();
				break;
			case 3:
				obj.updateModelcost();
				break;
			case 4:
				obj.fetchModels();
				break;
			case 5:
				obj.fetchModelsReverse();
				break;
			case 6:
				System.exit(0);
				break;
			default:
				System.out.println("Wrong input");
			}

			System.out.println("Do you want perform more operation(y-yes/n-no): ");
			ch = sc.next().charAt(0);

		} while (ch == 'y' || ch == 'Y');
	}

}
