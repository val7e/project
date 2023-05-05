/**
 * 
 */
package jtrash.model.players;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.*;

/**
 * @author val7e
 *
 */
public class Database {
	
	public void saveOnFile(File data, String nickname, String avatar, boolean isBot) throws IOException {
		try {
			if (!checkPlayer(data, nickname)) {
				FileWriter fileWriter = new FileWriter(data, true);
				BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
				bufferedWriter.append(new Player(nickname, avatar, isBot).toString()+"\n");
				
				bufferedWriter.close();
				fileWriter.close();
			}
			else System.out.println("Nickname già in uso: riprova.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public boolean checkPlayer(File data, String nickname) throws IOException {
		Path path = Path.of(data.getAbsolutePath());
		return Files.lines(path).anyMatch(x -> x.startsWith(nickname));
	}
	
	public String getFromFile(File data, String nickname) throws IOException {
		if(checkPlayer(data, nickname)) {
			try {
				List<String> playersArray = Files.lines(Path.of(data.getAbsolutePath())).filter(x -> x.startsWith(nickname)).collect(Collectors.toList());
				return playersArray.get(0);
			} catch (IOException e){
				e.printStackTrace();
			}
		} else return "Errore: Player non esistente";
		return null;
	}
}
