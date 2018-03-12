import java.util.List;
import java.util.ArrayList;
import java.io.File;

/**
 * A PlayList is a list of playable audio tracks. On creation, a directory
 * name is specified, and all audio tracks in that directory will be
 * automatically added to the play list. Files in the directory that cannot
 * be decoded are ignored.
 * 
 * @author Michael Kolling
 * @version 2006-10-09
 */
public class PlayList
{
    private List<Track> tracks; 
    
    /**
     * Constructor for objects of class TrackCollection
     */
    public PlayList(String directoryName)
    {
        tracks = loadTracks(directoryName);
    }
    
    /**
     * Return a track from this collection.
     */
    public Track getTrack(int trackNumber)
    {
        return tracks.get(trackNumber);
    }

    /**
     * Return the number of tracks in this collection.
     */
    public int numberOfTracks()
    {
        return tracks.size();
    }
    
    /**
     * Load the file names of all files in the given directory.
     * @param dirName Directory (folder) name.
     * @param suffix File suffix of interest.
     * @return The names of files found.
     */
    private List<Track> loadTracks(String dirName)
    {
        File dir = new File(dirName);
        if(dir.isDirectory()) {
            File[] allFiles = dir.listFiles();
            List<Track> foundTracks = new ArrayList<Track>();

            for(File file : allFiles) {
                //System.out.println("found: " + file);
                Track track = new Track(file);
                if(track.isValid()) {
                    foundTracks.add(track);
                }
            }
            return foundTracks;
        }
        else {
            System.err.println("Error: " + dirName + " must be a directory");
            return null;
        }
    }

    /**
     * Return this playlist as an array of strings with the track names.
     */
    public String[] asStrings()
    {
        String[] names = new String[tracks.size()];
        int i = 0;
        for(Track track : tracks) {
            names[i++] = track.getName();
        }
        return names;
    }
}
