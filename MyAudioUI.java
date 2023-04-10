import java.util.Scanner;


// Simulation of a Simple Text-based Music App (like Apple Music)

public class MyAudioUI
{
	public static void main(String[] args)
	{
		// Simulation of audio content in an online store
		// The songs, podcasts, audiobooks in the store can be downloaded to your mylibrary
		AudioContentStore store = new AudioContentStore();
		
		// Create my music mylibrary
		Library mylibrary = new Library();

		Scanner scanner = new Scanner(System.in);
		System.out.print(">");

		// Process keyboard actions
		while (scanner.hasNextLine())
		{
			String action = scanner.nextLine();
			try {
				if (action == null || action.equals("")) 
				{
					System.out.print("\n>");
					continue;
				}
				else if (action.equalsIgnoreCase("Q") || action.equalsIgnoreCase("QUIT"))
					return;
				
				else if (action.equalsIgnoreCase("STORE"))	// List all songs
				{
					store.listAll(); 
				}
				else if (action.equalsIgnoreCase("SONGS"))	// List all songs
				{
					mylibrary.listAllSongs(); 
				}
				else if (action.equalsIgnoreCase("BOOKS"))	// List all songs
				{
					mylibrary.listAllAudioBooks(); 
				}
				else if (action.equalsIgnoreCase("PODCASTS"))	// List all songs
				{
					mylibrary.listAllPodcasts(); 
				}
				else if (action.equalsIgnoreCase("ARTISTS"))	// List all songs
				{
					mylibrary.listAllArtists(); 
				}
				else if (action.equalsIgnoreCase("PLAYLISTS"))	// List all play lists
				{
					mylibrary.listAllPlaylists(); 
				}
				// Download audiocontent (song/audiobook/podcast) from the store 
				// Specify the index of the content
				else if (action.equalsIgnoreCase("SEARCH"))
				{
					String title = "";
					System.out.print("Title: ");
					if (scanner.hasNextLine()) {
						title = scanner.nextLine();
					}
					store.search(title);
				}
				//search all songs/audiobooks under artist/author's name
				else if (action.equalsIgnoreCase("SEARCHA"))
				{
					String artist = "";
					System.out.print("Artist: ");
					if (scanner.hasNextLine()) {
						artist = scanner.nextLine();
					}
					store.searcha(artist);
				}
				//search all songs under a type of genre
				else if (action.equalsIgnoreCase("SEARCHG"))
				{
					String genre = "";
					System.out.print("Genre [POP, ROCK, JAZZ, HIPHOP, RAP, CLASSICAL]: ");
					if (scanner.hasNextLine()) {
						genre = scanner.nextLine();
					}
					store.searchg(genre);
				}
				//download all songs/audiobooks under artist/author's name
				else if (action.equalsIgnoreCase("DOWNLOADA"))
				{
					String artist = "";
					System.out.print("Artist Name: ");
					if (scanner.hasNextLine()) {
						artist = scanner.nextLine();
					}
					for (int index : store.getSearchA().get(artist)) {
						try {
							AudioContent content = store.getContent(index+1);
							mylibrary.download(content);
						} catch (AudioContentNotFoundException e) {
							System.out.println("Content Not Found in Store");
						} catch (AlreadyDownloadedException e) {
							System.out.println(e.getMessage());
							continue; // continues for loop if a song is already downloaded
						} catch (NullPointerException e) {
							System.out.print("Content Not Found in Store");
						}
					}
				}
				//download all songs under a type of genre
				else if (action.equalsIgnoreCase("DOWNLOADG"))
				{
					String genre = "";
					System.out.print("Genre: ");
					if (scanner.hasNextLine()) {
						genre = scanner.nextLine();
					}
					for (int index : store.getSearchG().get(genre)) {
						try {
							AudioContent content = store.getContent(index+1);
							mylibrary.download(content);
						} catch (AlreadyDownloadedException e) {
							System.out.println(e.getMessage());
							continue; // continues for loop if a song is already downloaded
						} catch (NullPointerException e) {
							System.out.print("Invalid Input");
						}
					}
				}
				else if (action.equalsIgnoreCase("DOWNLOAD")) 
				{
					int fromIndex = 0;
					int toIndex = 0;
					
					System.out.print("From Store Content #: ");

					if (scanner.hasNextInt())
					{
						System.out.print("From Store Content #: ");
						fromIndex = scanner.nextInt();
						scanner.nextLine(); // "consume" nl character (necessary when mixing nextLine() and nextInt())
						
						System.out.print("To Store Content #: ");
						if (scanner.hasNextInt())
						{
							toIndex = scanner.nextInt();
							scanner.nextLine();
						}
					}
					
					try {
						if (store.getContent(toIndex) == null || store.getContent(fromIndex) == null)
							throw new NullPointerException();
						for (int i = fromIndex; i < toIndex+1; i++) {
							try {
								AudioContent content = store.getContent(i);
								mylibrary.download(content);
							} catch (AlreadyDownloadedException e ) {
								System.out.println(e.getMessage());
								continue; // continues for loop if a song is already downloaded
							}
						}
					} catch (NullPointerException e) {
						System.out.println("Invalid Input");
					}
				}
				// Get the *library* index (index of a song based on the songs list)
				// of a song from the keyboard and play the song 
				else if (action.equalsIgnoreCase("PLAYSONG")) 
				{
					// Print error message if the song doesn't exist in the library
					int index = 0;
					System.out.print("Song Number: ");
					if (scanner.hasNextInt()) {
						index = scanner.nextInt();
						scanner.nextLine();
					}
					mylibrary.playSong(index);
					
					
				}
				// Print the table of contents (TOC) of an audiobook that
				// has been downloaded to the library. Get the desired book index
				// from the keyboard - the index is based on the list of books in the library
				else if (action.equalsIgnoreCase("BOOKTOC")) 
				{
				// Print error message if the book doesn't exist in the library
					int index = 0;
					System.out.print("Audio Book Number: ");
					if (scanner.hasNextInt()) {
						index = scanner.nextInt();
						scanner.nextLine();
					}
					mylibrary.printAudioBookTOC(index);

					
				}
				// Similar to playsong above except for audio book
				// In addition to the book index, read the chapter 
				// number from the keyboard - see class Library
				else if (action.equalsIgnoreCase("PLAYBOOK")) 
				{
					int index = 0;
					int chapter = 0;
					System.out.print("Audio Book Number: ");
					if (scanner.hasNextInt()) {
						index = scanner.nextInt();
					}
					System.out.print("Chapter: ");
					if (scanner.hasNextInt()) {
						chapter = scanner.nextInt();
						scanner.nextLine();
					}
					mylibrary.playAudioBook(index, chapter);
					
				}
				// Print the episode titles for the given season of the given podcast
				// In addition to the podcast index from the list of podcasts, 
				// read the season number from the keyboard
				// see class Library for the method to call
				else if (action.equalsIgnoreCase("PODTOC")) 
				{
					
				}
				// Similar to playsong above except for podcast
				// In addition to the podcast index from the list of podcasts, 
				// read the season number and the episode number from the keyboard
				// see class Library for the method to call
				else if (action.equalsIgnoreCase("PLAYPOD")) 
				{
					
				}
				// Specify a playlist title (string) 
				// Play all the audio content (songs, audiobooks, podcasts) of the playlist 
				// see class Library for the method to call
				else if (action.equalsIgnoreCase("PLAYALLPL")) 
				{
					String playlistTitle = "";
					System.out.print("Playlist Title: ");
					if (scanner.hasNext()) {
						playlistTitle = scanner.next();
						scanner.nextLine();
					}
					mylibrary.playPlaylist(playlistTitle);					

					
				}
				// Specify a playlist title (string) 
				// Read the index of a song/audiobook/podcast in the playist from the keyboard 
				// Play all the audio content 
				// see class Library for the method to call
				else if (action.equalsIgnoreCase("PLAYPL")) 
				{
					String playlistTitle = "";
					int index = 0;
					System.out.print("Playlist Title: ");
					if (scanner.hasNext()) {
						playlistTitle = scanner.next();
					}
					System.out.print("Content Number: ");
					if (scanner.hasNextInt()) {
						index = scanner.nextInt();
						scanner.nextLine();
					}
					mylibrary.playPlaylist(playlistTitle, index);
					
				}
				// Delete a song from the list of songs in mylibrary and any play lists it belongs to
				// Read a song index from the keyboard
				// see class Library for the method to call
				else if (action.equalsIgnoreCase("DELSONG")) 
				{
					int index = 0;
					System.out.print("Library Song #: ");
					if (scanner.hasNextInt()) {
						index = scanner.nextInt();
						scanner.nextLine();
					}
					mylibrary.deleteSong(index);
				}
				// Read a title string from the keyboard and make a playlist
				// see class Library for the method to call
				else if (action.equalsIgnoreCase("MAKEPL")) 
				{
					String title = "";
					System.out.print("Playlist Title: ");
					if (scanner.hasNext()) {
						title = scanner.next();
						scanner.nextLine();
					}
					mylibrary.makePlaylist(title);
				}
				// Print the content information (songs, audiobooks, podcasts) in the playlist
				// Read a playlist title string from the keyboard
			// see class Library for the method to call
				else if (action.equalsIgnoreCase("PRINTPL"))	// print playlist content
				{
					String title = "";
					System.out.print("Playlist Title: ");
					if (scanner.hasNext()) {
						title = scanner.next();
						scanner.nextLine();
					}
					mylibrary.printPlaylist(title);
				}
				// Add content (song, audiobook, podcast) from mylibrary (via index) to a playlist
				// Read the playlist title, the type of content ("song" "audiobook" "podcast")
				// and the index of the content (based on song list, audiobook list etc) from the keyboard
			// see class Library for the method to call
				else if (action.equalsIgnoreCase("ADDTOPL")) 
				{
					String playlistTitle = "";
					String content = "";
					int index = 0;
					System.out.print("Playlist Title: ");
					if (scanner.hasNext()) {
						playlistTitle = scanner.next();
					}
					System.out.print("Content Type [SONG, PODCAST, AUDIOBOOK]: ");
					if (scanner.hasNext()) {
						content = scanner.next();
					}
					System.out.print("Library Content #: ");
					if (scanner.hasNextInt()) {
						index = scanner.nextInt();
						scanner.nextLine();
					}
					mylibrary.addContentToPlaylist(content.toUpperCase(), index, playlistTitle);
					
				}
				// Delete content from play list based on index from the playlist
				// Read the playlist title string and the playlist index
			// see class Library for the method to call
				else if (action.equalsIgnoreCase("DELFROMPL")) 
				{
					String playlistTitle = "";
					int index = 0;
					System.out.print("Playlist Title: ");
					if (scanner.hasNext()) {
						playlistTitle = scanner.next();
						scanner.nextLine();
					}
					System.out.print("Playlist Content #: ");
					if (scanner.hasNextInt()) {
						index = scanner.nextInt();
						scanner.nextLine();
					}
					mylibrary.delContentFromPlaylist(index, playlistTitle);
				}
				
				else if (action.equalsIgnoreCase("SORTBYYEAR")) // sort songs by year
				{
					mylibrary.sortSongsByYear();
				}
				else if (action.equalsIgnoreCase("SORTBYNAME")) // sort songs by name (alphabetic)
				{
					mylibrary.sortSongsByName();
				}
				else if (action.equalsIgnoreCase("SORTBYLENGTH")) // sort songs by length
				{
					mylibrary.sortSongsByLength();
				}

			} catch(AudioContentNotFoundException e) {
				System.out.println(e.getMessage());
			} catch(PlaylistException e) {
				System.out.println(e.getMessage());
			} catch(AlreadyDownloadedException e) {
				System.out.println(e.getMessage());
			} catch(NullPointerException e) {
				System.out.println(e.getMessage());
			}
			System.out.print("\n>");
		}
		scanner.close();
	}
}
