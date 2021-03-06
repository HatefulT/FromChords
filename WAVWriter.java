import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

class WAVWriter {
  BufferedOutputStream bf;

  int numChannels = 1;
  int sampleRate = 44100;
  int byteRate = sampleRate;
  int blockAlign = 1;
  int bitsPerSample = 8;

  int numSamples;

  WAVWriter(String filename, int _sampleRate, int _byteRate, int _blockAlign, int _bitsPerSample, int _numSamples) {
  	sampleRate = _sampleRate;
  	byteRate = _byteRate;
  	blockAlign = _blockAlign;
  	bitsPerSample = _bitsPerSample;
    numSamples = _numSamples;
    try {
      bf = new BufferedOutputStream(new FileOutputStream(filename), 1024);
      bf.write(new byte[]{ 0x52, 0x49, 0x46, 0x46 }, 0, 4); // word RIFF
      bf.write(getBytesLE(36+numSamples*numChannels*bitsPerSample/8, 4), 0, 4);
      bf.write(new byte[]{ 0x57, 0x41, 0x56, 0x45 }, 0, 4); // word WAVE
      bf.write(new byte[]{ 0x66, 0x6d, 0x74, 0x20 }, 0, 4); // word "fmt "
      bf.write(new byte[]{ 0x10, 0x00, 0x00, 0x00 }, 0, 4); // chunk size
      bf.write(new byte[]{ 0x01, 0x00 }, 0, 2); // format
      bf.write(getBytesLE(numChannels, 2), 0, 2); 
      bf.write(getBytesLE(sampleRate, 4), 0, 4);
      bf.write(getBytesLE(byteRate, 4), 0, 4);
      bf.write(getBytesLE(blockAlign, 2), 0, 2);
      bf.write(getBytesLE(bitsPerSample, 2), 0, 2);
      bf.write(new byte[]{ 0x64, 0x61, 0x74, 0x61 }, 0, 4);
      bf.write(getBytesLE(numSamples*numChannels*bitsPerSample/8, 4), 0, 4);
      bf.flush();
    } catch(FileNotFoundException e) {
      e.printStackTrace();
    } catch(IOException e) {
      e.printStackTrace();
    }
  }

  public static byte[] getBytesLE(int le, int b) {
    byte[] out = new byte[b];
    for(int i=0; i<b; i++)
      out[i] = (byte) ((le >>> (8*i)) & 0xFF);
    return out;
  }
  public static byte[] getBytesBE(int be, int b) {
    byte[] out = new byte[b];
    for(int i=0; i<b; i++)
      out[i] = (byte) ((be >>> (b-1-8*i)) & 0xFF);
    return out;
  }
}
