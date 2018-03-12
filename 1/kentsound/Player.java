
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
     *  Plays the selected track. 
     */
    public void play()
    {
        if(track != null)
        {
            track.play();
        }
        if(track.getDuration() != 0)
        {
            track.rewind();
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
} 
