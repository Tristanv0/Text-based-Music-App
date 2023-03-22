import java.util.ArrayList;

public class Season extends AudioContent
{   
     ArrayList<String> episodeFiles;
     ArrayList<Integer> episodeLengths;
     ArrayList<String> episodeTitles;
     int currentEpisode = 0;
     private String type;

    public Season()
    {
        episodeFiles = new ArrayList<String>();
        episodeLengths = new ArrayList<Integer>();
        episodeTitles = new ArrayList<String>();
        this.type = "SEASON";
    }

    public void selectEpisode(int episode)
    {
        if (episode >= 1 && episode <= episodeFiles.size())
        {
            currentEpisode = episode - 1;
        }
    }

    public boolean equals(Object other)
    {
        Season otherS = (Season) other;
        return super.equals(otherS) && this.episodeFiles.equals(otherS.episodeFiles) && this.episodeLengths.equals(otherS.episodeLengths) && this.episodeTitles.equals(otherS.episodeTitles);
    }

    public int getNumberOfEpisodes()
    {
        return episodeFiles.size();
    }

    public ArrayList<String> getEpisodeTitles()
    {
        return episodeTitles;
    }

    public void setEpisodeTitles(ArrayList<String> episodeTitles)
    {
        this.episodeTitles = episodeTitles;
    }

    public ArrayList<String> getEpisodeFiles()
    {
        return episodeFiles;
    }

    public void setEpisodeFiles(ArrayList<String> episodeFiles) 
    {
        this.episodeFiles = episodeFiles;
    }

    public ArrayList<Integer> getEpisodeLength()
    {
        return episodeLengths;
    }
    
    public String getType()
    {
        return type;
    }
}
