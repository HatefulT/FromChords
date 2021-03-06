class Main {
  final static String[] chordsDict = "C C# D D# E F F# G G# A A# B".split(" ");
  final static String[] notes = "c c# d d# e f f# g g# a a# b h".split(" ");
  final static double[] freqsDict = new double[]{ 261.63, 277.18, 293.66, 311.13, 329.63, 349.23, 369.99, 392., 415.3, 440., 466.16, 493.88, 493.88 };
  public static void main(String... args) {
    String[] chords = "C C# D D# E F F# G G# A A# B".split(" ");
    double[][] freqs = new double[chords.length][3];
    for(int i=0; i<chords.length; i++) {
      freqs[i] = fromChord(chords[i]);
//      for(int j=0; j<freqs[i].length; j++)
//        freqs[i][j] *= 0.5;
    }
    int secs = chords.length;
    
    int bitsPerSample = 16;
    WAVWriter wr = new WAVWriter("output.wav", 44100, 44100*bitsPerSample/8, bitsPerSample/8, bitsPerSample, 44100*secs);
    try {
      for(int i=0; i<chords.length; i++) {
        for(double t=0; t<44100; t++) {
          wr.bf.write(wr.getBytesLE( F(freqs[i], t, bitsPerSample/8), bitsPerSample/8 ));
          if(t % 2000 == 0)
            wr.bf.flush();
        }
        wr.bf.flush();
      }
      wr.bf.close();
    } catch(Exception e) {
      e.printStackTrace();
    }
  }
  public static int F(double[] freqs, double t, int bytes) {
    double idk = (2*Math.PI);
    double help = t / 44100.;
    double f = 3+Math.sin(idk*freqs[0]*help);
    f += Math.sin(idk*freqs[1]*help);
    f += Math.sin(idk*freqs[2]*help);
    double f1 = Math.pow(1-help, 2);
    return (int) ( f * f1 * Math.pow(256, bytes) / 6.);
  }
  public static double[] fromChord(String chord) {
    double[] out = new double[3];
    boolean isMinor = false;
    int n;
    if(chord.endsWith("m")) {
      n = chordToInt(chord.substring(0, chord.length()-1));
      isMinor = true;
    } else
      n = chordToInt(chord);
    out[0] = freqsDict[n];
    out[1] = freqsDict[(n+(isMinor ? 3 : 4)) % 13];
    out[2] = freqsDict[(n+7) % 13];
    return out;
  }
  public static int chordToInt(String chord) {
    int i;
    for(i=0; i<chordsDict.length; i++) {
      if(chord.equals(chordsDict[i]))
        break;
    }
    return i;
  }
}
