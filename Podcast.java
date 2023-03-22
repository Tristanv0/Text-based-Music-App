import java.util.ArrayList;

public class Podcast extends AudioContent
{
    public static final String TYPENAME =   "PODCAST";
    private String author;
    private int currentSeason = 0;
    private ArrayList<Season> seasons;

    
    
    public Podcast(String title, int year, String id, String type, String audioFile, int length, 
                                String author, ArrayList<Season> seasons) 
    {
        //Initialize additional Podcast instance variables.
        super(title, year, id, type, audioFile, length);
        this.author = author;
        this.seasons = seasons;
    }

    public String getType()
    {
        return TYPENAME;
    }

    //Print information about the podcast.
    public void printInfo()
    {
        super.printInfo();
        System.out.println("Author: " + this.author + "\nSeasons: " + this.seasons.size());
        System.out.println();
    }

    public void play()
    {
        super.setAudioFile(this.seasons.get(currentSeason) + "\n" + this.episodes.get(currentEpisode) + "\n" + this.episodesTitles.get(currentEpisode));
        super.play();
    }

    //Print the table of contents of the podcast - i.e. the list of episode titles
    public void printTOC()
    {
        for (int i = 0; i < seasons.get(currentSeason).episodeTitles.size(); i++) {
            System.out.println(i+1 + ". " + seasons.get(currentSeason).episodeTitle(i) + "\n");
        }
    }

    public void selectSeason(int season)
    {
        if (season >= 1 && season <= seasons.size())
        {
            currentSeason = season -1;
        }
    }

    public boolean equals(Object other)
    {
        Podcast otherP = (Podcast) other;
        return super.equals(otherP) && this.author.equals(otherP.author);
    }

    public int getNumberofSeasons() 
    {
        return seasons.size();
    }

    public String getAuthor()
    {
        return author;
    }

    public void setAuthor(String author) 
    {
        this.author = author;
    }

    public ArrayList<Season> getSeasons()
    {
        return seasons;
    }

    public void setSeasons(ArrayList<Season> seasons)
    {
        this.seasons = seasons;
    }
}