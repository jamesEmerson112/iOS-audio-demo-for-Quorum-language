/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plugins.quorum.Libraries.Sound;

import plugins.quorum.Libraries.Sound.Desktop.WavInputStream;
import plugins.quorum.Libraries.Sound.Desktop.OggInputStream;
import plugins.quorum.Libraries.Sound.Desktop.WavData;
import plugins.quorum.Libraries.Sound.Desktop.OggData;
import plugins.quorum.Libraries.System.QuorumFile;

import java.io.File;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.ShortBuffer;

/**
 *
 * @author alleew
 */

/**
 * @contributors: An Vo, Tony
 */

/**
 * @classDescription: N/A, essential for white-noise generator for iOS
 * constructors:
 *          public quorum.Libraries.Sound.AudioSamples_ Copy()
 *          public quorum.Libraries.Sound.AudioSamples_ Copy(int start, int end)
 *          public quorum.Libraries.Sound.AudioSamples_ CopyChannel(int channel)
 *          public quorum.Libraries.Sound.AudioSamples_ CopyChannel(int channel, int start, int end)
 *
 * Static member:
 *          {create an audio class Audio
 *          construct AudioSamples_ samples to the AudioSamples class
 *          set samples size to 0
 *          load samples
 *          dispose audio}
 *
 *          Object me_
 *          int MAX_SHORT
 *          short[] buffer;
 *          int samplesPerSecond = 44100;
 *          int channels = 1;
 *
 * Functions:
 *      public void SetChannels(int c)
 *      public int GetChannels()
 *      public void SetSamplesPerSecond(int samples)
 *      public int GetSamplesPerSecond()
 *      public void SetSizeInSeconds(double seconds)
 *      public double GetSizeInSeconds()
 *      public void SetTotalSize(int samples)
 *      public int GetTotalSize()
 *      public void SetBuffer(int index, double value, int channel)
 *      public double GetBuffer(int index, int channel)
 *      public void Load(quorum.Libraries.System.File_ quorumFile)
 *
 */
public class AudioSamples
{
    static
    {
        // Ensure OpenAL is initialized by loading dummy audio.
        Audio audio = new Audio();
        quorum.Libraries.Sound.AudioSamples_ samples = new quorum.Libraries.Sound.AudioSamples();
        samples.SetTotalSize(0);
        audio.Load(samples);
        audio.Dispose();
    }

    /**
     * function copy is a constructor for AudioSamples_
     * it constructing a new AudioSamples, and copied into a newly declared AudioSamples_
     * @return: copy
     */
    public quorum.Libraries.Sound.AudioSamples_ Copy()
    {
        quorum.Libraries.Sound.AudioSamples_ copy = new quorum.Libraries.Sound.AudioSamples();
        copy.plugin_.samplesPerSecond = samplesPerSecond;
        copy.plugin_.channels = channels;
        short[] array = buffer.clone();
        copy.plugin_.buffer = array;
        return copy;
    }

    /**
     * function copy is a constructor for AudioSamples_
     * it constructing a new AudioSamples, and copied into a newly declared AudioSamples
     * parameters: int start (of a sound channel?), int end (of a sound channel)
     * @return: copy
     */
    public quorum.Libraries.Sound.AudioSamples_ Copy(int start, int end)
    {
        if (start > end)
        {
            int temp = start;
            start = end;
            end = temp;
        }
        quorum.Libraries.Sound.AudioSamples_ copy = new quorum.Libraries.Sound.AudioSamples();
        copy.plugin_.samplesPerSecond = samplesPerSecond;
        copy.plugin_.channels = channels;
        short[] array = new short[(end - start + 1) * channels];
        System.arraycopy(buffer, start * channels, array, 0, (end - start + 1) * channels);
        copy.plugin_.buffer = array;
        return copy;
    }

    /**
     * function copy is a constructor for AudioSamples_
     * it constructing a new AudioSamples, and copied into a newly declared AudioSamples
     * parameters: int channel
     * @return: copy
     */
    public quorum.Libraries.Sound.AudioSamples_ CopyChannel(int channel)
    {
        quorum.Libraries.Sound.AudioSamples_ copy = new quorum.Libraries.Sound.AudioSamples();
        copy.plugin_.samplesPerSecond = samplesPerSecond;
        copy.plugin_.channels = 1;
        short[] array = new short[buffer.length / channels];
        for (int i = 0; i < array.length; i++)
        {
            array[i] = buffer[i * channels + channel];
        }
        copy.plugin_.buffer = array;
        return copy;
    }

    /**
     * function copy is a constructor for AudioSamples_
     * it constructing a new AudioSamples, and copied into a newly declared AudioSamples
     * parameters: int start (of a sound channel?), int end (of a sound channel), int channel
     * @return: copy
     */
    public quorum.Libraries.Sound.AudioSamples_ CopyChannel(int channel, int start, int end)
    {
        if (start > end)
        {
            int temp = start;
            start = end;
            end = temp;
        }
        quorum.Libraries.Sound.AudioSamples_ copy = new quorum.Libraries.Sound.AudioSamples();
        copy.plugin_.samplesPerSecond = samplesPerSecond;
        copy.plugin_.channels = 1;
        short[] array = new short[(end - start + 1)];
        int channelStart = (channels * start) + channel;
        for (int i = 0; i < array.length; i++)
        {
            array[i] = buffer[channelStart + i * channels];
        }
        copy.plugin_.buffer = array;
        return copy;
    }

    /**
     * legacy code
     * Unclear what me_ does
     */
    public Object me_ = null;


    public static final int MAX_SHORT = java.lang.Short.MAX_VALUE;
    public short[] buffer;
    public int samplesPerSecond = 44100;
    public int channels = 1;

    /**
     * function SetChannels sets the channel to the class AudioSamples
     * @param c
     * @return none (channels)
     */
    public void SetChannels(int c)
    {
        channels = c;
    }

    /**
     * function GetChannels returns the channels of the class AudioSamples
     * @return int channels
     */
    public int GetChannels()
    {
        return channels;
    }

    /**
     * function SetSamplesPerSecond set int samples to samplesPerSecond of AudioSamples
     * @param samples
     * @return none
     */
    public void SetSamplesPerSecond(int samples)
    {
        samplesPerSecond = samples;
    }

    /**
     * function GetSamplesPerSecond returns the samplesPerSecond of AudioSamples
     * @return samplesPerSecond
     */
    public int GetSamplesPerSecond()
    {
        return samplesPerSecond;
    }

    /**
     * function SetSizeInSeconds sets the total size of the set of samples for the buffer
     * @param seconds
     * @return none
     */
    public void SetSizeInSeconds(double seconds)
    {
        SetTotalSize((int)(seconds * samplesPerSecond) * channels);
    }

    /**
     * function GetSizeInSeconds return buffer size per samplesPerSecond per Channels
     * @return buffer.length / samplesPerSeconds / channels
     */
    public double GetSizeInSeconds()
    {
        if (buffer == null)
            return 0;
        
        return (buffer.length / (double)samplesPerSecond) / channels;
    }

    /**
     * function SetTotalSize sets the buffer size
     * @param samples
     */
    public void SetTotalSize(int samples)
    {
        buffer = new short[samples];
    }

    /**
     * function SetTotalSize sets the buffer size
     * @return buffer.length
     */
    public int GetTotalSize()
    {
        if (buffer == null)
            return 0;

        return buffer.length;
    }


    
    public void SetBuffer(int index, double value, int channel)
    {
        buffer[index * channels + channel] = (short)(value * MAX_SHORT);
    }
    
    public double GetBuffer(int index, int channel)
    {
        return buffer[index * channels + channel] / (double)MAX_SHORT;
    }

//*          Object me_
// *          int MAX_SHORT
// *          short[] buffer;
// *          int samplesPerSecond = 44100;
// *          int channels = 1;
    public quorum.Libraries.System.File_ GetQuorumFile()
    {
//        quorum.Libraries.System.File_ quorumFile = new quorum.Libraries.System.File_();
//        File file = new File(quorumFile.GetAbsolutePath());
//        String filename = file.getName().toLowerCase();
//        byte[] bytes;
//        int channels, sampleRate;
//
//        // we have no idea how the constructor of File_ works; therefore, we will handle
//        // the quorumFile with cases like Load function does
//
//        return quorumFile;
        QuorumFile quorumFile = new QuorumFile();
        return null;
    }

    /**
     * function Load loads a quorumFile into an AudioSamples
     * and convert it to AudioSamples format
     * @return buffer.length
     */
    public void Load(quorum.Libraries.System.File_ quorumFile)
    {
        File file = new File(quorumFile.GetAbsolutePath());
        String fileName = file.getName().toLowerCase();
        byte[] bytes;
        int channels, sampleRate;
        
        if (fileName.endsWith(".wav"))
        {
            WavInputStream input = new WavInputStream(file);
            bytes = WavData.GetBytes(input);
            channels = input.channels;
            sampleRate = input.sampleRate;
        }
        else if (fileName.endsWith(".ogg"))
        {
            OggInputStream input = new OggInputStream(file);
            bytes = OggData.GetBytes(input);
            channels = input.getChannels();
            sampleRate = input.getSampleRate();
        }
        else 
            throw new RuntimeException("Can't load file " + file.getAbsolutePath() + " because the file extension is unsupported!");
        
        ByteBuffer buffer = ByteBuffer.allocateDirect(bytes.length);
        buffer.order(ByteOrder.nativeOrder());
        buffer.put(bytes, 0, bytes.length);
        buffer.flip();
        
        ShortBuffer b = buffer.asShortBuffer();
        this.buffer = new short[b.limit()];
        b.get(this.buffer);
        
        this.channels = channels;
        samplesPerSecond = sampleRate;
    }
}
