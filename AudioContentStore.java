import java.util.ArrayList;
import java.util.HashMap;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

// Simulation of audio content in an online store
// The songs, podcasts, audiobooks listed here can be "downloaded" to your library

public class AudioContentStore
{
		private ArrayList<AudioContent> contents; 
		HashMap<String, Integer> titles = new HashMap<>();
		HashMap<String, ArrayList<Integer>> artists = new HashMap<>();
		HashMap<String, ArrayList<Integer>> genres = new HashMap<>();
		
		public AudioContentStore()
		{
			contents = new ArrayList<AudioContent>();

			try {
				Scanner fileScanner = new Scanner(new File("store.txt"));
				while (fileScanner.hasNextLine()) {
					String keyword = fileScanner.nextLine();
					if (keyword.equals("SONG")) {
						String id = fileScanner.nextLine();
						String title = fileScanner.nextLine();
						int year = fileScanner.nextInt();	fileScanner.nextLine();
						int length = fileScanner.nextInt(); fileScanner.nextLine();
						String artist = fileScanner.nextLine();
						String composer = fileScanner.nextLine();
						String genre = fileScanner.nextLine();
						int linesOfLyrics = fileScanner.nextInt();
						String lyrics = "";
						for (int i = 0; i < linesOfLyrics; i++) {
							lyrics += fileScanner.nextLine() + "\n";
						}
						contents.add(new Song(title, year, id, Song.TYPENAME, "", length, artist, composer, Song.Genre.valueOf(genre), lyrics));
					}
					
					else if (keyword.equals("AUDIOBOOK")) {
						String id = fileScanner.nextLine();
						String title = fileScanner.nextLine();
						int year = fileScanner.nextInt();	fileScanner.nextLine();
						int length = fileScanner.nextInt();	fileScanner.nextLine();
						String author = fileScanner.nextLine();
						String narrator = fileScanner.nextLine();
						int numOfChapters = fileScanner.nextInt(); fileScanner.nextLine();
						ArrayList<String> chapterTitles = new ArrayList<String>();
						for (int i = 0; i < numOfChapters; i++) {
							chapterTitles.add(fileScanner.nextLine());
						}
						ArrayList<String> chapters = new ArrayList<String>();
						for (int i = 0; i < numOfChapters; i++) {
							int numOfLines = fileScanner.nextInt(); fileScanner.nextLine();
							String lines = "";
							for (int j = 0; j < numOfLines; j++) {
								lines += fileScanner.nextLine() + "\n";
							}
							chapters.add(lines);
						}
						contents.add(new AudioBook(title, year, id, AudioBook.TYPENAME, "", length, author, narrator, chapterTitles, chapters));
					}
				}
			//for titles hashmap
			for (int i = 0; i < contents.size(); i++) {
				titles.put(contents.get(i).getTitle(), i);
			}
			//for artists hashmap
			int contentIndex = 0;
			for (AudioContent content : contents) {
				if (content.getType() == Song.TYPENAME) {
					Song song = (Song) content;
					if (artists.containsKey(song.getArtist())) {
						artists.get(song.getArtist()).add(contentIndex);
					} else {
						ArrayList<Integer> artistIndices = new ArrayList<>();
						artistIndices.add(contentIndex);
						artists.put(song.getArtist(), artistIndices);
					}
				}
				else if (content.getType() == AudioBook.TYPENAME) {
					AudioBook abook = (AudioBook) content;
					if (artists.containsKey(abook.getAuthor())) {
						artists.get(abook.getAuthor()).add(contentIndex);
					} else {
						ArrayList<Integer> authorIndices = new ArrayList<>();
						authorIndices.add(contentIndex);
						artists.put(abook.getAuthor(), authorIndices);
					}
				}

				contentIndex++;
			}

			//for genres hashmap
			int genreIndex = 0;
			for (AudioContent content : contents) {
				if (content.getType() == Song.TYPENAME) {
					Song song = (Song) content;
					if (genres.containsKey(song.getGenre().toString())) {
						genres.get(song.getGenre().toString()).add(genreIndex);
					} else {
						ArrayList<Integer> genreIndices = new ArrayList<>();
						genreIndices.add(genreIndex);
						genres.put(song.getGenre().toString(), genreIndices);
					}
				}
			}

			} catch (IOException e) {
				e.getMessage();	
			}
			
		}
		public void search(String title) {
			if (titles.containsKey(title)) {
				System.out.print(titles.get(title) + 1 + ". ");
				contents.get(titles.get(title)).printInfo();
			} else {
				throw new AudioContentNotFoundException("No matches for " + title);
			}
		}

		public void searcha(String artist) {
			if (artists.containsKey(artist)) {
				for (int index : artists.get(artist)) {
					System.out.print(index + 1 + ". ");
					contents.get(index).printInfo();
				}
			} else {
				throw new AudioContentNotFoundException("No matches for " + artist);
			}
		}

		public void searchg(String genre) {
			if (genres.containsKey(genre)) {
				for (int index : genres.get(genre)) {
					System.out.print(index + 1 + ". ");
					contents.get(index).printInfo();
				}
			} else {
				throw new AudioContentNotFoundException("No content under genre: " + genre);
			}
		}
		
		public AudioContent getContent(int index)
		{
			if (index < 1 || index > contents.size())
			{
				return null;
			}
			return contents.get(index-1);
		}
		
		public void listAll()
		{
			for (int i = 0; i < contents.size(); i++)
			{
				int index = i + 1;
				System.out.print("" + index + ". ");
				contents.get(i).printInfo();
				System.out.println();
			}
		}
		
		
}
