/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plugins.quorum.Libraries.Sound.IOS;

import java.lang.Object;
import sun.audio;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.TargetDataLine;

//import org.robovm.apple.coreaudio.AudioFormat;
import plugins.quorum.Libraries.Sound.Audio;
import plugins.quorum.Libraries.Sound.Data;
import plugins.quorum.Libraries.Sound.DataLoader;
import plugins.quorum.Libraries.Sound.Desktop.AudioData;
import quorum.Libraries.System.File_;


/**
 *
 * @author alleew
 */
public class IOSLoader implements DataLoader
{

    @Override
    public Data Load(File_ quorumFile) 
    {
        return new IOSSoundData(quorumFile);
    }

    @Override
    public Data LoadToStream(File_ quorumFile) 
    {
        String path = quorumFile.GetPath().replace('\\', '/');
        OALAudioTrack track = OALAudioTrack.create();
        if (track != null) 
        {
            if (track.preloadFile(path)) 
            {
                return new IOSStreamData(track);
            }
        }
        throw new RuntimeException("Error opening audio file at " + path);
    }
    
    @Override
// *          Object me_
// *          int MAX_SHORT
// *          short[] buffer;
// *          int samplesPerSecond = 44100;
// *          int channels = 1;
    public Data Load(quorum.Libraries.Sound.AudioSamples_ audioSamples)
    {

//        1. convert short[] to byte[]
//        2. read the byte array to the speakers

        // initialize audio format
        AudioFormat format = new AudioFormat(8000.0f, 16, 1, true, true);
        SourceDataLine speakers;

        int shortRead = 0;
        int bufferSize = 0;
        int i = 0;

        short[] buffer = audioSamples.GetBufferShort(); // this returns the buffer

        // convert shortBuffer to byteBuffer
        bufferSize = audioSamples.GetTotalSize();
        short[] short_buffer_array = audioSamples.GetBufferShort();
        ByteBuffer byte_buffer = ByteBuffer.allocate((bufferSize*2));  // return buffer size
        while(bufferSize>=i)
        {
            byte_buffer=putShort(short_buffer_array[i]);
            i++;
        }

        byte[] byte_buffer_array = new byte[bufferSize*2];
        byte_buffer_array = byte_buffer.array();
        // finished converting

        // read audio from the byte array using sun.audio package
        AudioData audioData = new AudioData(byte_buffer_array);
        AudioDataStream audioStream = new AudioDataStream(audioData);
        AudioPlayer.player.start(audioStream);

        return null;
    }
    
    @Override
    public Data LoadToStream(quorum.Libraries.Sound.AudioSamples_ audioSamples)
    {
        throw new UnsupportedOperationException("Direct AudioBuffer manipulation is not currently supported on this platform.");
    }
    
}
