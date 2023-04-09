import java.util.ArrayList;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

// Simulation of audio content in an online store
// The songs, podcasts, audiobooks listed here can be "downloaded" to your library

public class AudioContentStore
{
		private ArrayList<AudioContent> contents; 
		
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
						int length = fileScanner.nextInt();
						String author = fileScanner.nextLine();
						String narrator = fileScanner.nextLine();
						int numOfChapters = fileScanner.nextInt();	fileScanner.nextLine();
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
			} catch (IOException e) {
				e.getMessage();	
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
