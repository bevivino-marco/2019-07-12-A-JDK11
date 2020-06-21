package it.polito.tdp.food.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.food.model.Condiment;
import it.polito.tdp.food.model.Food;
import it.polito.tdp.food.model.Portion;
import it.polito.tdp.food.model.Relazioni;

public class FoodDao {
	public List<Food> listFoods(int porzioni){
		String sql = "SELECT DISTINCT f.food_code ,f.display_name " + 
				"FROM food_pyramid_mod.portion p, food f " + 
				"WHERE p.food_code=f.food_code AND p.portion_amount<=? " ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			st.setInt(1, porzioni);
			List<Food> list = new LinkedList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(new Food(res.getInt("food_code"),
							res.getString("display_name")
							));
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}

	}
	
	public List<Condiment> listAllCondiments(){
		String sql = "SELECT * FROM condiment" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Condiment> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(new Condiment(res.getInt("condiment_code"),
							res.getString("display_name"),
							res.getDouble("condiment_calories"), 
							res.getDouble("condiment_saturated_fats")
							));
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}
	}
	
	public List<Portion> listAllPortions(){
		String sql = "SELECT * FROM portion" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Portion> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(new Portion(res.getInt("portion_id"),
							res.getDouble("portion_amount"),
							res.getString("portion_display_name"), 
							res.getDouble("calories"),
							res.getDouble("saturated_fats"),
							res.getInt("food_code")
							));
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}

	}

	public List<Relazioni> getRel(Map<Integer, Food> cibi) {
		String sql = "SELECT fc1.food_code,fc2.food_code,c1.condiment_code,SUM(c1.condiment_calories) " + 
				"FROM condiment c1 , food_condiment fc1, condiment c2 , food_condiment fc2 " + 
				"WHERE fc1.food_code!=fc2.food_code AND fc1.condiment_code=fc2.condiment_code " + 
				"AND fc1.condiment_code=c1.condiment_code AND c1.condiment_code=c2.condiment_code " + 
				"GROUP BY fc1.food_code,fc2.food_code " ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Relazioni> list = new LinkedList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					if (cibi.containsKey(res.getInt("fc1.food_code")) && cibi.containsKey(res.getInt("fc2.food_code"))&&
							res.getDouble("SUM(c1.condiment_calories)")>0.0) {
					list.add(new Relazioni(
					cibi.get(res.getInt("fc1.food_code")),
							cibi.get(res.getInt("fc2.food_code")),
							res.getDouble("SUM(c1.condiment_calories)")
							));}
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}
	}
}
