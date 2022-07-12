package it.polito.tdp.genes.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.genes.model.Coppia;
import it.polito.tdp.genes.model.Genes;


public class GenesDao {
	
	public void getAllGenes(Map<String, Genes> idMap){
		String sql = "SELECT DISTINCT GeneID, Essential, Chromosome FROM Genes";
		//List<Genes> result = new ArrayList<Genes>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {
				if(!idMap.containsKey(res.getString("GeneID"))) {
				Genes genes = new Genes(res.getString("GeneID"), 
						res.getString("Essential"), 
						res.getInt("Chromosome"));
				idMap.put(res.getString("GeneID"), genes);
				}
			}
			res.close();
			st.close();
			conn.close();
			
			
		} catch (SQLException e) {
			e.printStackTrace();
			return ;
		}		
	}
	
	public List<Genes> getVertici(Map<String,Genes> idMap){
		String sql ="SELECT distinct g.GeneID AS g "
				+ "FROM genes g "
				+ "WHERE g.Essential='Essential' ";
		List<Genes> result = new ArrayList<Genes>();
				Connection conn = DBConnect.getConnection();

				try {
					PreparedStatement st = conn.prepareStatement(sql);
					ResultSet res = st.executeQuery();
					while (res.next()) {
						result.add(idMap.get(res.getString("g")));
						
					}
					res.close();
					st.close();
					conn.close();
					return result;
					
				} catch (SQLException e) {
					e.printStackTrace();
					return null;
				}		
	}
	
	
	
	
	public List<Coppia> getArchi(Map<String,Genes> idMap){
		String sql ="SELECT DISTINCT g1.GeneID AS g1, g2.GeneID AS g2, g1.Chromosome AS c1, "
				+ " g2.Chromosome AS c2, ABS(i.Expression_Corr) AS peso "
				+ "FROM genes g1, genes g2, interactions i "
				+ "WHERE g1.Essential='Essential' "
				+ "		AND g1.Essential = g1.Essential "
				+ "		AND g1.GeneID<>g2.GeneID "
				+ "		AND i.GeneID1 = g1.GeneID "
				+ "		AND i.GeneID2 = g2.GeneID "
				+ "GROUP BY c1, c2, g1, g2, peso";
		List<Coppia> result = new ArrayList<>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {
				Genes g1 = idMap.get(res.getString("g1"));
				Genes g2 = idMap.get(res.getString("g2"));
				int c1 = res.getInt("c1");
				int c2 = res.getInt("c2");
				double peso = res.getDouble("peso");
				
				if(c1==c2) {
					peso = 2*peso;
				}
				
				result.add(new Coppia(g1,g2,peso));
				
			}
			res.close();
			st.close();
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
/*	
	public List<Genes> getVertici(Map<String,Genes> idMap){
		String sql = "SELECT DISTINCT g.GeneID AS g "
				+ "FROM genes g "
				+ "WHERE g.Essential = 'Essential' ";
		List<Genes> result = new ArrayList<>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				result.add(idMap.get(res.getString("g")));
			}
			res.close();
			st.close();
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	public List<Coppia> getArchi(Map<String, Genes> idMap){
		String sql = "SELECT g1.GeneID AS g1, g2.GeneID AS g2,g1.Chromosome AS c1, g2.Chromosome AS c2, i.Expression_Corr AS peso "
				+ "FROM genes g1, genes g2, interactions i "
				+ "WHERE g1.GeneID<>g2.GeneID "
				+ "		AND i.GeneID1= g1.GeneID "
				+ "		AND i.GeneID2= g2.GeneID "
				+ "		AND g1.Essential = 'Essential' "
				+ "		AND g2.Essential = 'Essential' "
				+ "GROUP BY g1,g2,c1,c2,peso ";
		
		List<Coppia> result = new ArrayList<>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Genes g1 = idMap.get(res.getString("g1"));
				Genes g2 = idMap.get(res.getString("g2"));
				int c1 = res.getInt("c1");
				int c2 = res.getInt("c2");
				double peso = res.getDouble("peso");
				
				if(c1==c2) {
					peso = 2*peso;
				}
				result.add(new Coppia(g1,g2,peso));
			}
			res.close();
			st.close();
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public List<String> getVertici(){
		String sql = "SELECT DISTINCT  Essential FROM Genes ";
		List<String> result = new ArrayList<String>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				String e = new String(res.getString("Essential"));
				result.add(e);
			}
			res.close();
			st.close();
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
	}

	*/
}
