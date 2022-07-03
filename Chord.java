import java.util.Arrays;

class Chord {
  final static String[] chordDict = "C C# D D# E F F# G G# A A# B".split(" ");
  final static String[] noteDict = "c c# d d# e f f# g g# a a# b".split(" ");
  final static double[] freqDict = new double[]{ 261.63, 277.18, 293.66, 311.13, 329.63, 349.23, 369.99, 392., 415.3, 440., 466.16, 493.88 };
  
  double[] freqs;
  double duration;     // duration - условная длительность (кол-во 1/8)
  String name;
  double tempus = .25; // tempus - то, сколько длится 1/8
  boolean isMinor = false;

  int samples = 0;

  int tuktuk = 0;

  Chord(String _name) {
    name = _name;
    tuktuk = Integer.parseInt(name.substring(name.length()-1, name.length()));
    name = name.substring(0, name.length()-1);
    duration = Integer.parseInt(name.substring(name.length()-1, name.length()));
    name = name.substring(0, name.length()-1);
    freqs = new double[3];

    if(!name.startsWith("*")) {
      isMinor = name.endsWith("m");
      if(isMinor)
        name = name.substring(0, name.length()-1);
      int n = Arrays.asList(chordDict).indexOf(name);
      freqs[0] = freqDict[n];
      freqs[1] = freqDict[(n+(isMinor ? 3 : 4)) % 12];
      freqs[2] = freqDict[(n+7) % 12];
    }
    samples = (int) (44100 * duration * tempus);
  }
}


/*
C#m15

1 - 1/16
2 - 1/8
3 - 1/4
4 - 1/2
5 - 1

last number means how much times to tuk-tuk

m - minor

C# - chord!

*/
