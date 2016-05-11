import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class GatherData {
	String mainfolder = "/Users/Nati/untitled folder/CMSC424-FIFA-DB/world-cup-master";

	public ArrayList<String> getCups() {
		int c_id = 110;
		int year;
		ArrayList<String> results = new ArrayList<String>();
		String country = null;
		File dir = new File(mainfolder);
		File[] directoryListing = dir.listFiles();
		if (directoryListing != null) {
			for (File child : directoryListing) {
				if (child.getName().contains("--")) {
					year = Integer.parseInt((String) (child.getName().substring(0, 4)));
					country = child.getName().substring(6, child.getName().length());
					String out = "INSERT INTO CUP (ATTENDANCE,C_ID,HOST,YEAR) VALUES(" + "2000, " + c_id + ",\'"
							+ country + "\'," + year + ")";
					results.add(out);
					c_id++;

					// System.out.println(out);
				}

			}
		}
		return results;
	}
	public ArrayList<String> getTeams() {
		int c_id = 1100;
		ArrayList<String> results = new ArrayList<String>();
		int year;
		String teamName = null;
		File dir = new File(mainfolder);
		File[] directoryListing = dir.listFiles();
	
		if (directoryListing != null) {
			for (File child : directoryListing) {
				if (child.getName().contains("--")) {
					year = Integer.parseInt((String) (child.getName().substring(0, 4)));
					File subFile = new File(child.getAbsolutePath() + "/squads");
					if(subFile.exists()){
						File[] subDir = subFile.listFiles();
						for (File son : subDir) {
							teamName = son.getName().substring(3, son.getName().length() - 4);
							String out = "INSERT INTO TEAM (NAME,NUM_VIEWERS,RANK,T_ID,YEAR) VALUES (\'" + teamName
									+ "\',2000,0," + c_id + "," + year + ")";
							results.add(out);
							c_id++;
	
							//System.out.println(out);
						}
					}
				}

			}
		}
		return results;
	}
	
	public ArrayList<String> getPlayers() throws IOException {
		int p_id = 11000;
		ArrayList<String> results = new ArrayList<String>();
		int year;
		String teamName = null;
		File dir = new File(mainfolder);
		File[] directoryListing = dir.listFiles(); // Contains 1930-uraguay
		if (directoryListing != null) {
			for (File child : directoryListing) {
				if (child.getName().contains("--")) {
					year = Integer.parseInt((String) (child.getName().substring(0, 4))); // The year of the CUP
					File subFile = new File(child.getAbsolutePath() + "/squads");
					if(subFile.exists()){
						File[] subDir = subFile.listFiles(); // All the teams that played in the cup
						for (File son : subDir) {
							teamName = son.getName().substring(son.getName().indexOf('-')+1, son.getName().length() - 4); // This is the team Name	
							
							int t_id = PopulateTables.getT_ID(teamName,year); // Returns the t-ID * 1. Need to Query the Team Table for the T_ID for the based on the Year and TeamName
							
							BufferedReader br = new BufferedReader(new FileReader(son));
							System.out.println(teamName+year);
							try {
							    StringBuilder sb = new StringBuilder();
							    String line = br.readLine();
							    while (line != null) {
							        sb.append(line);
							        String pattern = "[A-Z]{2}\\s.*(##)";

							        // Create a Pattern object
							        Pattern r = Pattern.compile(pattern);

							        // Now create matcher object.
							        Matcher m = r.matcher(line);
							        if (m.find( )) {
							        	String n = m.group(0).trim();
							        	
							         n = n.substring(4, n.indexOf("#")).trim();
							       //   System.out.println(year+": "+" -"+p_id);
							          
							         //Add the player name and p_id
							         PopulateTables.addPlayers(n,p_id);  
							         
							         //Add the p_id and t_id
							         PopulateTables.addP_ID_T_ID(t_id,p_id);
							         p_id++;
							        } 
							       line = br.readLine();
							    }
							//    String everything = sb.toString();
							} catch (IOException | SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} 
						}
					}
				}

			}
		}
		return results;
	}

	public static void main(String[] args) {
		GatherData g1 = new GatherData();
		// g1.getCups(); //Add all the cups
		try {
			g1.getPlayers();
		} catch (NullPointerException | IOException d) {
			System.err.println("There was an erro" + d);
		}
	}

}
