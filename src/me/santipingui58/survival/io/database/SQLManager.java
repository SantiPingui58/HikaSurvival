package me.santipingui58.survival.io.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;

import me.santipingui58.survival.HikaSurvival;
import me.santipingui58.survival.game.home.HikaHome;
import me.santipingui58.survival.player.SurviAccess;
import me.santipingui58.survival.player.SurviPlayer;
import me.santipingui58.survival.utils.LuckPermsUtils;
import me.santipingui58.survival.utils.Utils;


public class SQLManager {
	 
    private final ConnectionPoolManager pool;
    
    private HikaSurvival hikaSurvival;
    public SQLManager(HikaSurvival hikaSurvival) {
    	this.hikaSurvival = hikaSurvival;
        pool = new ConnectionPoolManager(hikaSurvival);
        createTables();
    }
    
    
    public ConnectionPoolManager getPool() {
    	return this.pool;
    }
    
    public boolean isDataCreated(UUID uuid) {
    	Connection conn = null;
		PreparedStatement ps = null;
		try {
			try {
				conn = pool.getConnection();

			} catch (SQLException e) {
				System.out.println("Error al intentar conectar a la base de datos!");
				e.printStackTrace();
				return true;
			}

			String selectQuery = "SELECT uuid FROM player_data WHERE uuid = ? LIMIT 1";
			ps = conn.prepareStatement(selectQuery);
			ps.setString(1, uuid.toString());
			ResultSet resultSet = ps.executeQuery();
			pool.close(conn, ps, null);
			return resultSet.next();
			
    } catch(Exception ex) {
    	return true;
    }
    }
    
    /**
     * 
	BANK,
	SECURITY,
	PETS,
	MAIL,
	KING,
	MAGE,
     */
    
    private void createTables() {
    	  Connection conn = null;
          PreparedStatement ps = null;
          PreparedStatement ps2 = null;
          try {
              conn = pool.getConnection();
              ps = conn.prepareStatement("CREATE TABLE IF NOT EXISTS  players  (id INT NOT NULL AUTO_INCREMENT PRIMARY KEY, uuid VARCHAR(36), twitchid VARCHAR(12),bank TINYINT,"
              		+ "security TINYINT, pets TINYINT, mail TINYINT, king TINYINT, mage TINYINT, nether TINYINT, end TINYINT, warp TINYINT, msg TINYINT, chat_global TINYINT, signs INT,"
              		+ "homes TINYINT, maxhomes TINYINT, bankmoney INT);");
              ps.executeUpdate(); 
              
              ps2 = conn.prepareStatement("CREATE TABLE IF NOT EXISTS  homes  (id INT NOT NULL AUTO_INCREMENT PRIMARY KEY, playerid INT NOT NULL,name VARCHAR(48), location VARCHAR(48));");
                ps2.executeUpdate(); 
              
              
              ps.close();
              
          } catch (SQLException e) {
              e.printStackTrace();
          } finally {
          	pool.close(conn, ps, null);
          }
	}


    public SurviPlayer createData(UUID uuid) {
		Connection conn = null;
		PreparedStatement ps = null;
		PreparedStatement ps2 = null;
		try {
			try {
				conn = pool.getConnection();

			} catch (SQLException e) {
				System.out.println("Error al intentar conectar a la base de datos!");
				e.printStackTrace();
				return null;
			}

			String selectQuery = "SELECT uuid FROM players WHERE uuid = ? LIMIT 1";
			ps = conn.prepareStatement(selectQuery);
			ps.setString(1, uuid.toString());
			ResultSet resultSet = ps.executeQuery();
			if (!resultSet.next()) {
				String insertQuery = "INSERT INTO  players  ( id , uuid, twitchid, bank, security, pets, mail, king, mage,nether,end,warp,msg,chat_global,homes,signs,maxhomes,bankmoney)"
						+ " VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
				
				ps2 = conn.prepareStatement(insertQuery);
				ps2.setInt(1, 0);
				ps2.setString(2, uuid.toString());  
				OfflinePlayer off = Bukkit.getOfflinePlayer(uuid);
				String twitchId = hikaSurvival.data.getConfig().contains("minecraft-twitch."+off.getName()) ? 
								hikaSurvival.data.getConfig().getString("minecraft-twitch."+off.getName()):"";
				ps2.setString(3, twitchId);
				ps2.setInt(4, 0);  
				ps2.setInt(5, 0);  
				ps2.setInt(6, 0);  
				ps2.setInt(7, 0);  
				ps2.setInt(8, 0);  
				ps2.setInt(9, 0);  
				ps2.setInt(10, 0);  
				ps2.setInt(11, 0);  
				ps2.setInt(12, 0);  
				ps2.setInt(13, 0);  
				ps2.setInt(14, 0);  
				ps2.setInt(15, 0);  
				ps2.setInt(16, 0);
				ps2.setInt(17, 0);
				ps2.setInt(18, 0);
				ps2.executeUpdate();
				ps2.close();
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
			pool.close(conn, ps, null);
			pool.close(conn, ps2, null);
			return loadData(uuid);

	}
	
		
	public void saveData(SurviPlayer player,boolean disconnected) {
		
		Connection conn = null;
		PreparedStatement ps = null;
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("UPDATE `players` SET twitchid = ?, name = ?,");
		queryBuilder.append(" bank  = ?,  security  = ?, ");
		queryBuilder.append(" pets  = ?, mail = ?, king = ?, mage = ?, nether = ?, end = ?, warp = ?, msg = ?, chat_global = ?,homes = ?, signs = ?, ");
		queryBuilder.append(" maxhomes = ?, bankmoney = ? ");
		queryBuilder.append(" WHERE uuid = ?;");

		try {
			conn = pool.getConnection();
		} catch (SQLException e) {
			System.out.println("Error al intentar conectar a la base de datos!");
			e.printStackTrace();
		}
		
		try {
			ps = conn.prepareStatement(queryBuilder.toString());
		ps.setString(1, player.getTwitchId());
		ps.setString(2, player.getOfflinePlayer().getName());
		ps.setInt(3, player.hasAccess(SurviAccess.BANCO) ? 1 : 0);
		ps.setInt(4, player.hasAccess(SurviAccess.CASA_SEGURIDAD) ? 1 : 0);
		ps.setInt(5, player.hasAccess(SurviAccess.MASCOTAS) ? 1 : 0);
		ps.setInt(6, player.hasAccess(SurviAccess.CASA_MENSAJERO) ? 1 : 0);
		ps.setInt(7, player.hasAccess(SurviAccess.CASTILLO_REY) ? 1 : 0);
		ps.setInt(8, player.hasAccess(SurviAccess.CASA_MAGO) ? 1 : 0);
		ps.setInt(9, player.hasAccess(SurviAccess.NETHER) ? 1 : 0);
		ps.setInt(10, player.hasAccess(SurviAccess.END) ? 1 : 0);
		ps.setInt(11, player.hasAccess(SurviAccess.WARP) ? 1 : 0);
		ps.setInt(12, player.hasAccess(SurviAccess.MSG) ? 1 : 0);
		ps.setInt(13, player.hasAccess(SurviAccess.CHAT_GLOBAL) ? 1 : 0);
		ps.setInt(14, player.hasAccess(SurviAccess.HOME) ? 1 : 0);
		ps.setInt(15, player.getSignsAmount());
		ps.setInt(16, player.getMaxHomes());
		ps.setInt(17, player.getBankMoney());
		ps.setString(18, player.getUuid().toString());
		ps.executeUpdate();
	
		
		} catch (final SQLException sqlException) {
			sqlException.printStackTrace();
		} finally {
			pool.close(conn, ps, null);
			if (disconnected) hikaSurvival.getPlayers().remove(player);
		}
	}

	
    public void onDisable() {
        pool.closePool();
    }


    
    
	
	public SurviPlayer loadData(String twitchId) {
		Connection conn = null;
		PreparedStatement ps = null;
		PreparedStatement ps2 = null;


	
		try {
			conn = pool.getConnection();
		} catch (SQLException e) {
			System.out.println("Error al intentar conectar a la base de datos!");
			e.printStackTrace();
			return null;
		}

		ResultSet resultSet = null;
		UUID uuid = null;
		int bank =0;
		int security = 0;
		int pets = 0;
		int mail = 0;
		int king = 0;
		int mage = 0;
		int nether = 0;
		int end = 0;
		int warp = 0;
		int msg = 0;
		int chat_global = 0;
		int signs = 0;
		int maxhomes= 0;
		int home = 0;
		int id = 0;
		int bankmoney = 0;
		Set<HikaHome> homes = new HashSet<HikaHome>();	
		
		try {
			StringBuilder queryBuilder = new StringBuilder();
			queryBuilder.append("SELECT *");
			queryBuilder.append(" FROM `players` ");
			queryBuilder.append("WHERE `twitchId` = ? ");
			queryBuilder.append("LIMIT 1;");

			ps = conn.prepareStatement(queryBuilder.toString());
			ps.setString(1,twitchId);

			resultSet = ps.executeQuery();


			if (resultSet != null && resultSet.next()) {
				id = resultSet.getInt("id");
				uuid = UUID.fromString(resultSet.getString("uuid"));
				bank= resultSet.getInt("bank");
				security= resultSet.getInt("security");
				pets= resultSet.getInt("pets");
				mail= resultSet.getInt("mail");
				king= resultSet.getInt("king");
				mage= resultSet.getInt("mage");
				nether = resultSet.getInt("nether");
				end = resultSet.getInt("end");
				warp = resultSet.getInt("warp");
				msg = resultSet.getInt("msg");
				chat_global = resultSet.getInt("chat_global");
				signs = resultSet.getInt("signs");
				home = resultSet.getInt("homes");
				maxhomes = resultSet.getInt("maxhomes");
				bankmoney = resultSet.getInt("bankmoney");
			} 
			
			
			 queryBuilder = new StringBuilder();
			queryBuilder.append("SELECT *");
			queryBuilder.append(" FROM `homes` ");
			queryBuilder.append("WHERE `playerid` = "+id);
			ps2 = conn.prepareStatement(queryBuilder.toString());
			resultSet = ps2.executeQuery();
			
			while (resultSet.next()) {
				String name = resultSet.getString("name");
				Location location = Utils.getLoc(resultSet.getString("location"),true);
			   HikaHome h = new HikaHome(uuid, name, location);
			   homes.add(h);
			    }

			

		} catch (final SQLException sqlException) {
			sqlException.printStackTrace();
		}  finally {
			pool.close(conn, ps, null);
		}
	
		SurviPlayer surviPlayer = new SurviPlayer(hikaSurvival, uuid);
		surviPlayer.setId(id);
		surviPlayer.setTwitchId(twitchId);
		if (bank==1) surviPlayer.addAccess(SurviAccess.BANCO);
		if (security==1) surviPlayer.addAccess(SurviAccess.CASA_SEGURIDAD);
		if (pets==1) surviPlayer.addAccess(SurviAccess.MASCOTAS);
		if (mail==1) surviPlayer.addAccess(SurviAccess.CASA_MENSAJERO);
		if (king==1) surviPlayer.addAccess(SurviAccess.CASTILLO_REY);
		if (mage==1) surviPlayer.addAccess(SurviAccess.CASA_MAGO);
		if (nether==1) surviPlayer.addAccess(SurviAccess.NETHER);
		if (end==1) surviPlayer.addAccess(SurviAccess.END);
		if (warp==1) surviPlayer.addAccess(SurviAccess.WARP);
		if (msg==1) surviPlayer.addAccess(SurviAccess.MSG);
		if (chat_global==1) surviPlayer.addAccess(SurviAccess.CHAT_GLOBAL);
		if (home==1) surviPlayer.addAccess(SurviAccess.HOME);
		surviPlayer.setSignsAmount(signs);
		surviPlayer.setMaxHomes(maxhomes);
		surviPlayer.setHomes(homes);
		surviPlayer.setBankMoney(bankmoney);
		hikaSurvival.getPlayers().add(surviPlayer);
		
		return surviPlayer;
			}

	
	public SurviPlayer loadData(UUID uuid) {
		Connection conn = null;
		PreparedStatement ps = null;
		PreparedStatement ps2 = null;

	
		try {
			conn = pool.getConnection();
		} catch (SQLException e) {
			System.out.println("Error al intentar conectar a la base de datos!");
			e.printStackTrace();
			return null;
		}

		ResultSet resultSet = null;
		
		String twitchId = null;
		int id = 0;
		int bank =0;
		int security = 0;
		int pets = 0;
		int mail = 0;
		int king = 0;
		int mage = 0;
		int nether = 0;
		int end = 0;
		int warp = 0;
		int msg = 0;
		int chat_global = 0;
		int home = 0;
		int signs = 0;
		int maxhomes = 0;
		int bankmoney = 0;
		Set<HikaHome> homes = new HashSet<HikaHome>();
		try {
			StringBuilder queryBuilder = new StringBuilder();
			queryBuilder = new StringBuilder();
			queryBuilder.append("SELECT *");
			queryBuilder.append(" FROM `players` ");
			queryBuilder.append("WHERE `uuid` = ? ");
			queryBuilder.append("LIMIT 1;");

			ps = conn.prepareStatement(queryBuilder.toString());
			ps.setString(1,uuid.toString());

			resultSet = ps.executeQuery();

					
			if (resultSet != null && resultSet.next()) {
				
				id = resultSet.getInt("id");
				twitchId = resultSet.getString("twitchId");
				bank= resultSet.getInt("bank");
				security= resultSet.getInt("security");
				pets= resultSet.getInt("pets");
				mail= resultSet.getInt("mail");
				king= resultSet.getInt("king");
				mage= resultSet.getInt("mage");
				nether = resultSet.getInt("nether");
				end = resultSet.getInt("end");
				warp = resultSet.getInt("warp");
				msg = resultSet.getInt("msg");
				chat_global = resultSet.getInt("chat_global");
				home = resultSet.getInt("homes");
				signs = resultSet.getInt("signs");
				maxhomes = resultSet.getInt("maxhomes");
				bankmoney = resultSet.getInt("bankmoney");
			} 
			
			 queryBuilder = new StringBuilder();
				queryBuilder.append("SELECT *");
				queryBuilder.append(" FROM `homes` ");
				queryBuilder.append("WHERE `playerid` = "+id);
				ps2 = conn.prepareStatement(queryBuilder.toString());
				resultSet = ps2.executeQuery();
				
				while (resultSet.next()) {
					String name = resultSet.getString("name");
					Location location = Utils.getLoc(resultSet.getString("location"),true);
				   HikaHome h = new HikaHome(uuid, name, location);
				   homes.add(h);
				    }
			
			
		} catch (final SQLException sqlException) {
			sqlException.printStackTrace();
		}  finally {
			pool.close(conn, ps, null);
		}
	
		SurviPlayer surviPlayer = new SurviPlayer(hikaSurvival, uuid);
		surviPlayer.setId(id);
		surviPlayer.setTwitchId(twitchId);
		if (bank==1) surviPlayer.addAccess(SurviAccess.BANCO);
		if (security==1) surviPlayer.addAccess(SurviAccess.CASA_SEGURIDAD);
		if (pets==1) surviPlayer.addAccess(SurviAccess.MASCOTAS);
		if (mail==1) surviPlayer.addAccess(SurviAccess.CASA_MENSAJERO);
		if (king==1) surviPlayer.addAccess(SurviAccess.CASTILLO_REY);
		if (mage==1) surviPlayer.addAccess(SurviAccess.CASA_MAGO);
		if (nether==1) surviPlayer.addAccess(SurviAccess.NETHER);
		if (end==1) surviPlayer.addAccess(SurviAccess.END);
		if (warp==1) surviPlayer.addAccess(SurviAccess.WARP);
		if (msg==1) surviPlayer.addAccess(SurviAccess.MSG);
		if (chat_global==1) surviPlayer.addAccess(SurviAccess.CHAT_GLOBAL);
		if (home==1) surviPlayer.addAccess(SurviAccess.HOME);
		surviPlayer.setSignsAmount(signs);
		surviPlayer.setMaxHomes(maxhomes);
		surviPlayer.setHomes(homes);
		surviPlayer.setBankMoney(bankmoney);
		hikaSurvival.getPlayers().add(surviPlayer);
		return surviPlayer;
			}

	
	public int createHome(SurviPlayer sp,HikaHome home) {
		Connection conn = null;
		PreparedStatement ps = null;
		PreparedStatement ps2 = null;
		ResultSet resultSet = null;
		int id = 0;
		try {
			try {
				conn = pool.getConnection();

			} catch (SQLException e) {
				System.out.println("Error al intentar conectar a la base de datos!");
				e.printStackTrace();
				return -1;
			}

				String insertQuery = "INSERT INTO  homes  (id , playerid,name,location) values (?,?,?,?);";
				ps = conn.prepareStatement(insertQuery);
				ps.setInt(1, 0);
				ps.setInt(2, sp.getId());  
				ps.setString(3, home.getName());
				ps.setString(4, Utils.setLoc(home.getLocation(),true));
				ps.executeUpdate();

				
				
				ps2 = conn.prepareStatement("select id from homes where playerid = " + sp.getId() + " and name= '" + home.getName()
				+ "' and location='" + Utils.setLoc(home.getLocation(), true) + "' limit 1;");
				
				resultSet = ps2.executeQuery();
				if (resultSet != null && resultSet.next()) {
					id = resultSet.getInt("id");
				}

				ps.close();
				ps2.close();
		} catch (Exception e) {
			e.printStackTrace();
		} 
			pool.close(conn, ps, null);
			return id;

	}
	  
	
	public void deleteHome(SurviPlayer sp,HikaHome home) {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			try {
				conn = pool.getConnection();

			} catch (SQLException e) {
				System.out.println("Error al intentar conectar a la base de datos!");
				e.printStackTrace();
				return;
			}

				String delQuery = "DELETE FROM homes where id = " + home.getId();
				ps = conn.prepareStatement(delQuery);
				ps.execute();
				ps.close();
		} catch (Exception e) {
			e.printStackTrace();
		} 
			pool.close(conn, ps, null);

	}
	
	
	public void interest() {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			try {
				conn = pool.getConnection();

			} catch (SQLException e) {
				System.out.println("Error al intentar conectar a la base de datos!");
				e.printStackTrace();
			}

			String selectQuery = "SELECT uuid FROM players";
			ps = conn.prepareStatement(selectQuery);
			ResultSet resultSet = ps.executeQuery();
			
			while (resultSet.next()) {
				UUID uuid = UUID.fromString(resultSet.getString("uuid"));
				OfflinePlayer off = Bukkit.getOfflinePlayer(uuid);
				double interest = LuckPermsUtils.hasPermission(uuid,"hika.vip") ? 1.0175 : 1.01;
				if (off.isOnline()) {
					SurviPlayer sp = hikaSurvival.getPlayer(off.getPlayer());
					int money = sp.getBankMoney();
					money = (int) (money*interest);
					sp.setBankMoney(money);
				} else {				
				PreparedStatement ps2 = null;
				String updateQuery = "UPDATE players set bankmoney = bankmoney*"+ interest + " where uuid='"+uuid+"';";
				ps2 = conn.prepareStatement(updateQuery);
				ps2.executeUpdate();
				ps2.close();
				}
			}
			
			
    } catch(Exception ex) {

    } finally {
    	pool.close(conn, ps, null);
    }

	}
	
	
	public int getBankMoney() {
		Connection conn = null;
		PreparedStatement ps = null;
		int bankMoney = 0;
		try {
			try {
				conn = pool.getConnection();

			} catch (SQLException e) {
				System.out.println("Error al intentar conectar a la base de datos!");
				e.printStackTrace();
			}

			String selectQuery = "SELECT SUM(bankmoney) as 'bankmoney' from players";
			ps = conn.prepareStatement(selectQuery);
			ResultSet resultSet = ps.executeQuery();
				if (resultSet != null && resultSet.next()) {
					bankMoney = resultSet.getInt("bankmoney");
				}
			
    } catch(Exception ex) {

    } finally {
    	pool.close(conn, ps, null);
    }
		
		return bankMoney;
	}
	
	public LinkedHashMap<UUID,Integer> getBankTop() {
		Connection conn = null;
		PreparedStatement ps = null;
		LinkedHashMap<UUID,Integer> bankTop = new LinkedHashMap<UUID,Integer>();
		try {
			try {
				conn = pool.getConnection();

			} catch (SQLException e) {
				System.out.println("Error al intentar conectar a la base de datos!");
				e.printStackTrace();
			}

			String selectQuery = "SELECT uuid,bankmoney from players order by bankmoney desc;";
			ps = conn.prepareStatement(selectQuery);
			ResultSet resultSet = ps.executeQuery();
				while(resultSet.next()) 
					bankTop.put(UUID.fromString(resultSet.getString("uuid")), resultSet.getInt("bankmoney"));
			
    } catch(Exception ex) {

    } finally {
    	pool.close(conn, ps, null);
    }
		
		return bankTop;
	}
	
}





 