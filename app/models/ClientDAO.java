
package models;

import play.db.DB;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import java.util.List;

public class ClientDAO extends DAO<Client>{

	public ClientDAO(){
		super(DB.getConnection());
	}

	public Client find(final String email, final String password){

		String sql = " SELECT * FROM IEDB.Client WHERE email = ? AND" +
					 " password = ?";

		List<Client> clients;
		clients = this.retrieveAllFromQuery(sql, new StatementConfigurator() {
                	public void configureStatement(PreparedStatement stmt) throws SQLException {
                    stmt.setString(1, email);
                    stmt.setString(2, password);
                }
            });

		return clients.size() > 0 ? clients.get(0) : null;
	}

	@Override
    protected Client buildFromResultSet(ResultSet rs) throws SQLException {
        Client client = new Client();
        client.setUsername           (rs.getString  ("username"));
        client.setEmail         	 (rs.getString  ("email"));
        client.setIsActive			 (rs.getBoolean ("active"));
        client.setIsReviewer		 (rs.getBoolean ("reviewer"));

        return client;
    }
}

		/*try{
			PreparedStatment st = connection.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			if(rs.next()){
				Client client = new Client();

			}
			else
				return null;

		}catch(SQLException e){
			throw new RuntimeException();
		}*/