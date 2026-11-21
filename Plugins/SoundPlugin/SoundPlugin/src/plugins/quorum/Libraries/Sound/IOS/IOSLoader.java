/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plugins.quorum.Libraries.Sound.IOS;

import java.lang.Object;
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

import plugins.quorum.Libraries.Sound.IOS.IOSSoundData;


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
    public Data Load(quorum.Libraries.Sound.AudioSamples_ audioSamples)
    {
        return new IOSSoundData(audioSamples);
    }
    
    @Override
    public Data LoadToStream(quorum.Libraries.Sound.AudioSamples_ audioSamples)
    {
        // throw new UnsupportedOperationException("Direct AudioBuffer manipulation is not currently supported on this platform.");
    
        // trying the stream class in IOSSoundData that james made
        
        IOSSoundData stream = new IOSSoundData(audioSamples);

        // queue up stream container -
        stream.QueueSamples(audioSamples);
//        stream.PlayAudioSamples(audioSamples); - dont think we need to play audio here
        return stream;
    }
    
}
