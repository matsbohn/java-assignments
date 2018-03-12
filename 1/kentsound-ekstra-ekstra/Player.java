
/**
 * Write a description of class Player here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Player
{
    private PlayList playList;
    private Track track;
    private int count;
    private int duration;
    /**
     * Constructor ...
     */
    public Player()
    {
        playList = new PlayList("audio");
    }
    
    /**
     * Return the track collection currently loaded in this player.
     */
    public PlayList getPlayList()
    {
        return playList;
    }
    
    /**
     * Plays the selected track.
     * Rewinds the track if the selected track is played again while playing.
     */
    public void play()
    {
        if(track != null)
        {
            track.play();
            count ++;
            track.rewind();
            duration += track.getDuration();
        }
    }
    
    /**
     * Stops the current track playing.
     */
    public void stop()
    {
        if(track != null)
        {
            track.stop();
        }
        
    }
    
    /**
     * Loads a track into the player.
     * Stops the the current track playing when another track is loaded.
     */
    public void setTrack(int trackNumber)
    {
        stop();
        track = playList.getTrack(trackNumber);
    }
    
    /**
     * Returns the track name. 
     * Return an empty string if a track is not selected.
     */
    public String getTrackName()
    {
        if(track != null)
        {
            return track.getName();
        }
        else
        {
            return "";
        }
    }  

    /**
     * Return information about the currently selected track. The information
     * contains the track name and playing time, in the format
     *    track-name (playing time)
     * Return an empty string if no track is selected.
     */
    public String getTrackInfo()
    {
        if(track != null)
        {
            return track.getName() + 
            " (" +track.getDuration() + ".s)";
        }
        else
        {
            return "";
        }
    }
    
    /**
     * Returns the total number of tracks played.
     */
    public int getNumberOfTracksPlayed()
    {
        return count;
    }
    
    /**
     * Returns the total length of all tracks played.
     */
    public int getTotalPlayedTrackLength()
    
    {
        return duration;
    }
    
    /**
     * Returns the average track length.
     */
    public double averageTrackLength()
    {
        if(track != null)
        {
            return duration / count;
        }
        else
        {
            return 0f;
        }
    }  
}
