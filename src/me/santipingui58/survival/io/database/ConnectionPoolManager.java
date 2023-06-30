package me.santipingui58.survival.io.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.configuration.file.FileConfiguration;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import me.santipingui58.survival.HikaSurvival;


public class ConnectionPoolManager {

	
	
	 	private HikaSurvival hikaSurvival;
	    private String hostname;
	    private String port;
	    private String database;
	    private String username;
	    private String password;
	    
	    private int minimumConnections;
	    private int maximumConnections;
	    private long connectionTimeout;
	    private String testQuery;
	    private HikariDataSource dataSource;
	    
	    public ConnectionPoolManager(HikaSurvival HikaSurvival) {
	    	 this.hikaSurvival = HikaSurvival;
	        init();
	        setupPool();
	    }
	
	
	
	
	private void init() {
		FileConfiguration config = hikaSurvival.getConfig();
		hostname = config.getString("database.hostname");
        port = config.getString("database.port");
        database = config.getString("database.database");
        username = config.getString("database.username");
        password = config.getString("database.password");

        minimumConnections = config.getInt("database.minimumConnections");
        maximumConnections = config.getInt("database.maximumConnections");
        connectionTimeout = config.getInt("database.connectionTimeout");
        testQuery = config.getString("database.testQuery");
        
    }
	

	
	private void setupPool() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(
                "jdbc:mysql://" +
                        hostname +
                        ":" +
                        port +
                        "/" +
                        database+"?useSSL=false&allowMultiQueries=true&allowPublicKeyRetrieval=true"
        );
        config.setDriverClassName("com.mysql.jdbc.Driver");
        config.setUsername(username);
        config.setPassword(password);
        config.setMinimumIdle(minimumConnections);
        config.setMaximumPoolSize(maximumConnections);
        config.setConnectionTimeout(connectionTimeout);
        config.setConnectionTestQuery(testQuery);
        dataSource = new HikariDataSource(config);
    }
	
	
	
	 public Connection getConnection() throws SQLException {
	        return dataSource.getConnection();
	    }
	
	
	 public void close(Connection conn, PreparedStatement ps, ResultSet res) {
	        if (conn != null) try { conn.close(); } catch (SQLException ignored) {}
	        if (ps != null) try { ps.close(); } catch (SQLException ignored) {}
	        if (res != null) try { res.close(); } catch (SQLException ignored) {}
	    }
	 
	 
	 public void closePool() {
	        if (dataSource != null && !dataSource.isClosed()) {
	            dataSource.close();
	        }
	    }
	
}
