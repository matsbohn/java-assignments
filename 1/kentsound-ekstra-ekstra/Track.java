import java.io.*;
import javax.sound.sampled.*;

/**
 * A sound track. The track can be instantiated with a valid sound file,
 * and the track object can then be used to control the sound.
 * 
 * The track accepts files in WAV, AIFF, and AU formats (although 
 * not all WAV files - it depends on the encoding in the file).
 * 
 * Watch out, once a track has been played, it can only be played again 
 * if it has been stopped first. So the sequence  play-play does not work,
 * not play-stop-play does.
 * 
 * @author mik
 * @version 2008-10-13
 */
public class Track
{
    private Clip soundClip;
    private String name;
    /**
     * Create a track from an audio file.
     */
    public Track(File soundFile)
    {
        soundClip = loadSound(soundFile);
        name = soundFile.getName();
    }

    /**
     * Play this sound track. (The sound will play asynchronously, until 
     * it is stopped or reaches the end.)
     */
    public void play()
    {
        if(soundClip != null) {
            soundClip.start();
        }
    }

    /**
     * Stop this track playing. (This method has no effect if the track is not
     * currently playing.)
     */
    public void stop()
    {
        if(soundClip != null) {
            soundClip.stop();
        }
    }

    /**
     * Reset this track to its start.
     */
    public void rewind()
    {
        if(soundClip != null) {
            soundClip.setFramePosition(0);
        }
    }
        
    /**
     * Return the name of this track.
     */
    public String getName()
    {
        return name;
    }
    
    /**
     * Return the duration of this track, in seconds.
     */
    public int getDuration()
    {
        if (soundClip == null) {
            return 0;
        }
        else {
            return (int) soundClip.getMicrosecondLength()/1000000;
        }
    }
    
    /**
     * Set the playback volume of the current track.
     * 
     * @param vol  Volume level as a percentage (0..100).
     */
    public void setVolume(int vol)
    {
        if(soundClip == null) {
            return;
        }
        if(vol < 0 || vol > 100) {
            vol = 100;
        }

        double val = vol / 100.0;

        try {
            FloatControl volControl =
              (FloatControl) soundClip.getControl(FloatControl.Type.MASTER_GAIN);
            float dB = (float)(Math.log(val == 0.0 ? 0.0001 : val) / Math.log(10.0) * 20.0);
            volControl.setValue(dB);
        } catch (Exception ex) {
            System.err.println("Error: could not set volume");
        }
    }

    /**
     * Return true if this track has successfully loaded and can be played.
     */
    public boolean isValid()
    {
        return soundClip != null;
    }
    
    /**
     * Load the sound file supplied by the parameter.
     * 
     * @return  The sound clip if successful, null if the file could not be decoded.
     */
    private Clip loadSound(File file) 
    {
        Clip newClip;

        try {
            AudioInputStream stream = AudioSystem.getAudioInputStream(file);
            AudioFormat format = stream.getFormat();

            // we cannot play ALAW/ULAW, so we convert them to PCM
            //
            if ((format.getEncoding() == AudioFormat.Encoding.ULAW) ||
                (format.getEncoding() == AudioFormat.Encoding.ALAW)) 
            {
                AudioFormat tmp = new AudioFormat(
                                          AudioFormat.Encoding.PCM_SIGNED, 
                                          format.getSampleRate(),
                                          format.getSampleSizeInBits() * 2,
                                          format.getChannels(),
                                          format.getFrameSize() * 2,
                                          format.getFrameRate(),
                                          true);
                stream = AudioSystem.getAudioInputStream(tmp, stream);
                format = tmp;
            }
            DataLine.Info info = new DataLine.Info(Clip.class, 
                                           stream.getFormat(),
                                           ((int) stream.getFrameLength() *
                                           format.getFrameSize()));

            newClip = (Clip) AudioSystem.getLine(info);
            newClip.open(stream);
            return newClip;
        } catch (Exception ex) {
            return null;
        }
    }
}
