import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/*
 * This class manages, stores, and plays audio content such as songs, podcasts and audiobooks. 
 */
public class Library
{
	private ArrayList<Song> 			songs; 
	private ArrayList<AudioBook> 	audiobooks;
	private ArrayList<Playlist> 	playlists; 
  	//private ArrayList<Podcast> 		podcasts;
	
	// Public methods in this class set errorMesg string 
	// Error Messages can be retrieved from main in class MyAudioUI by calling  getErrorMessage()
	// In assignment 2 we will replace this with Java Exceptions


	public Library()
	{
		songs 			= new ArrayList<Song>(); 
		audiobooks 	= new ArrayList<AudioBook>(); ;
		playlists   = new ArrayList<Playlist>();
	  	//podcasts		= new ArrayList<Podcast>(); ;
	}
	/*
	 * Download audio content from the store. Since we have decided (design decision) to keep 3 separate lists in our library
	 * to store our songs, podcasts and audiobooks (we could have used one list) then we need to look at the type of
	 * audio content (hint: use the getType() method and compare to Song.TYPENAME or AudioBook.TYPENAME etc)
	 * to determine which list it belongs to above
	 * 
	 * Make sure you do not add song/podcast/audiobook to a list if it is already there. Hint: use the equals() method
	 * If it is already in a list, set the errorMsg string and return false. Otherwise add it to the list and return true
	 * See the video
	 */
	public void download(AudioContent content)
	{	// if content is a song, add to songs arraylist. checks if song is already in songs arraylist before adding
		if (content.getType() == Song.TYPENAME) {
			Song contentSong = (Song) content;
			if (songs.indexOf(contentSong) == -1) {
				songs.add(contentSong);
				System.out.println("SONG " + contentSong.getTitle() + " Added to Library");
			}
			else {
				throw new AlreadyDownloadedException("Song " + contentSong.getTitle() + " already downloaded");
			}
		}// if content is a audiobook, add to audiobooks arraylist. checks if audiobook is already in audiobooks arraylist before adding
		else if (content.getType() == AudioBook.TYPENAME) {
			AudioBook contentAudio = (AudioBook) content;
			if (audiobooks.indexOf(contentAudio) == -1) {
				audiobooks.add(contentAudio);
				System.out.println("AUDIOBOOK " + contentAudio.getTitle() + " Added to Library");
			}
			else {
				throw new AlreadyDownloadedException("AudioBook " + contentAudio.getTitle() + " already downloaded");
			}
		}
	}
	
	// Print Information (printInfo()) about all songs in the array list
	public void listAllSongs()
	{
		int index = 1;
		for (Song song : songs)
		{
			System.out.print(index + ". ");
			song.printInfo();
			System.out.println();
			index++;	
		}
	}
	
	// Print Information (printInfo()) about all audiobooks in the array list
	public void listAllAudioBooks()
	{
		int index = 1;
		for (AudioBook audiobook : audiobooks) {
		
			System.out.print(index + ". ");
			audiobook.printInfo();
			System.out.println();
			index++;
		}
	}
	
  // Print Information (printInfo()) about all podcasts in the array list
	public void listAllPodcasts()
	{
		/* 
		for (int i = 0; i < podcasts.size(); i++) {
			int index = i + 1;
			System.out.print(index + ". ");
			podcasts.get(i).printInfo();
			System.out.println();
		}
		*/
	}
	
  // Print the name of all playlists in the playlists array list
	// First print the index number as in listAllSongs() above
	public void listAllPlaylists()
	{
		int index = 1;
		for (Playlist playlist : playlists) {
			System.out.println(index + ". " + playlist.getTitle());
			index++;
		}
	}
	
  // Print the name of all artists. 
	public void listAllArtists()
	{
		// First create a new (empty) array list of string 
		// Go through the songs array list and add the artist name to the new arraylist only if it is
		// not already there. Once the artist arrayl ist is complete, print the artists names
		ArrayList<String> artists = new ArrayList<>();
		// loops through songs arraylist and gets artist and adds it to artists arraylist
		for (Song song : songs) {
			if (artists.contains(song.getArtist())) {
				continue;
			}
			else {
				artists.add(song.getArtist());
			}
		}
		int index = 1;
		for (String artist : artists) {
			System.out.println(index + ". " + artist);
			index++;
		}
	}

	// Delete a song from the library (i.e. the songs list) - 
	// also go through all playlists and remove it from any playlist as well if it is part of the playlist
	public void deleteSong(int index)
	{	// checks if song at index in songs arraylist is in any playlist and removes it. After, it removes the song from songs arraylist.
		if (index >= 1 || index <= songs.size()) {

			for (Playlist playlist : playlists) {
				if (playlist.getContent().contains(songs.get(index-1))) {
					playlist.deleteContent(playlist.getContent().indexOf(songs.get(index-1))+1);
				}
			}
			songs.remove(index-1);
		}
		throw new AudioContentNotFoundException("Song not in playlist");
	}

	
  //Sort songs in library by year
	public void sortSongsByYear()
	{
		// Use Collections.sort() 
		Collections.sort(songs, new SongYearComparator());
	}
  // Write a class SongYearComparator that implements
	// the Comparator interface and compare two songs based on year
	private class SongYearComparator implements Comparator<Song>
	{
		public int compare(Song s1, Song s2) {
			return s1.getYear() - s2.getYear();
		}
	}

	// Sort songs by length
	public void sortSongsByLength()
	{
	 // Use Collections.sort() 
	 Collections.sort(songs, new SongLengthComparator());
	}
  // Write a class SongLengthComparator that implements
	// the Comparator interface and compare two songs based on length
	private class SongLengthComparator implements Comparator<Song>
	{
		public int compare(Song s1, Song s2) {
			return s1.getLength() - s2.getLength();
		}
	}

	// Sort songs by title 
	public void sortSongsByName()
	{
	  // Use Collections.sort()
		// class Song should implement the Comparable interface
		// see class Song code
		Collections.sort(songs);
	}
	
	/*
	 * Play Content
	 */
	
	// Play song from songs list
	public void playSong(int index)
	{
		if (index < 1 || index > songs.size())
		{
			throw new AudioContentNotFoundException("Song Not Found");
		}
		songs.get(index-1).play();
	}
	
	// Play podcast from list (specify season and episode)
	// Bonus
	public boolean playPodcast(int index, int season, int episode)
	{
		return false;
	}
	
	// Print the episode titles of a specified season
	// Bonus 
	public boolean printPodcastEpisodes(int index, int season)
	{
		return false;
	}
	
	// Play a chapter of an audio book from list of audiobooks
	public void playAudioBook(int index, int chapter)
	{
		if (index < 1 || index > audiobooks.size()) {
			throw new AudioContentNotFoundException("AudioBook Not Found");
		}
		audiobooks.get(index-1).selectChapter(chapter);
			audiobooks.get(index-1).play();
	}
	
	// Print the chapter titles (Table Of Contents) of an audiobook
	// see class AudioBook
	public void printAudioBookTOC(int index)
	{
		if (index > 1 || index < audiobooks.size()) {
			audiobooks.get(index-1).printTOC();
		}
		throw new AudioContentNotFoundException("AudioBook does not exist");
	}
	
  /*
   * Playlist Related Methods
   */
	
	// Make a new playlist and add to playlists array list
	// Make sure a playlist with the same title doesn't already exist
	public void makePlaylist(String title)
	{	//creates newPlaylist object and checks all playlists if it has the same name. if not then it adds it to the playlists arraylist
		Playlist newPlaylist = new Playlist(title);
		if (playlists.contains(newPlaylist)) {
			throw new PlaylistException("Playlist " + title + " Already Exists");
		}
		else {
			playlists.add(newPlaylist);
		}
	}
	
	// Print list of content information (songs, audiobooks etc) in playlist named title from list of playlists
	public void printPlaylist(String title)
	{	
		for (Playlist playlist : playlists) {
			if (playlist.getTitle().equals(title)) {
				playlist.printContents();

			}
		}
		throw new PlaylistException("Playlist does not exist");
	}
	
	// Play all content in a playlist
	public void playPlaylist(String playlistTitle)
	{
		for (Playlist playlist : playlists) {
			if (playlist.getTitle().equals(playlistTitle)) {
				playlist.playAll();

			}
		}
		throw new PlaylistException("Playlist does not exist");
	}
	
	// Play a specific song/audiobook in a playlist
	public void playPlaylist(String playlistTitle, int indexInPL)
	{
		for (Playlist playlist : playlists) {
			// if the given playlist title is in playlists
			if (playlist.getTitle().equals(playlistTitle)) {
				//checks if index is within playlist length.
				if (playlist.contains(indexInPL) == false) {
					throw new AudioContentNotFoundException("Song does not exist");
				}
				else {
					//plays playlist content at specific index
					playlist.getTitle();
					playlist.play(indexInPL);
				}
			}
		}
		throw new PlaylistException("Playlist does not exist");
	}
	
	// Add a song/audiobook/podcast from library lists at top to a playlist
	// Use the type parameter and compare to Song.TYPENAME etc
	// to determine which array list it comes from then use the given index
	// for that list
	public void addContentToPlaylist(String type, int index, String playlistTitle)
    {
        for(Playlist playlist : playlists){
			//checks if playlist title exists
            if(playlist.getTitle().equals(playlistTitle)){
				//checks for type
                if(type.equals("SONG")){
					//checks if index is within songs arraylist
                    if (index < 1 || index > songs.size()){
						throw new AudioContentNotFoundException("Song Not Found");
                    }else{
						// if all true then adds song to playlist
                        playlist.addContent(songs.get(index-1));
                    }
                }
				//checks for type
				else if(type.equals("AUDIOBOOK")){
					//checks if index is within audiobooks arraylist
                    if (index < 1 || index > audiobooks.size()){
    
						throw new AudioContentNotFoundException("Audiobook Not Found");
                    }else{
						// if all true then adds audiobook to playlist
                        playlist.addContent(audiobooks.get(index-1));
                    }
                }else if(type.equals("SONG") == false || type.equals("AUDIOBOOK") == false){
					throw new AudioContentNotFoundException("Content type not found");
                }
            }
    }
	throw new PlaylistException("Playlist does not exist");
}

  // Delete a song/audiobook/podcast from a playlist with the given title
	// Make sure the given index of the song/audiobook/podcast in the playlist is valid 
	public boolean delContentFromPlaylist(int index, String title)
	{
		for (Playlist playlist : playlists) {
			//checks if playlist title exists in playlists
			if (playlist.getTitle().equals(title)) {
				//checks if index is within playlists arraylist
				if (index < playlists.size() || index > 0) {
					playlist.deleteContent(index);
					return true;
				}
				else {
					throw new AudioContentNotFoundException("Audio content does not exist in playlist");
				}
			}
		}
		throw new PlaylistException("Playlist does not exist");
	}
	
}

class AudioContentNotFoundException extends RuntimeException {
	public AudioContentNotFoundException(String message) {
		super(message);
	}
}

class AlreadyDownloadedException extends RuntimeException {
	public AlreadyDownloadedException(String message) {
		super(message);
	}
}

class PlaylistException extends RuntimeException {
	public PlaylistException(String message) {
		super(message);
	}
}