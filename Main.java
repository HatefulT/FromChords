class Main {
  public static void main(String... args) {
    int N = 0;
    // String[] part = "C11 *30 Em11 G11 D11 *40".split(" ");
    String[] part = "C18 *30 Em17 G17 D18 *40".split(" ");
    String[] chordsStringArray = new String[part.length * 10];
    N = chordsStringArray.length;
    for(int i=0; i<N; i++) {
      chordsStringArray[i] = part[i%part.length];
    }

    Chord[] chords = new Chord[N];
    for(int i=0; i<N; i++) {
      chords[i] = new Chord(chordsStringArray[i]);
    }

    int samples = 0;
    for(int i=0; i<N; i++)
      samples += chords[i].samples;

    int bitsPerSample = 8;
    WAVWriter wr = new WAVWriter("output.wav", 44100, 44100*bitsPerSample/8, bitsPerSample/8, bitsPerSample, samples);
    try {
      for(int i=0; i<N; i++) {
        for(double t=0; t<chords[i].samples; t++) {
          wr.bf.write(wr.getBytesLE( F(chords[i].freqs, t/44100, bitsPerSample/8, chords[i].samples, chords[i].tuktuk), bitsPerSample/8 ));
          if(t % 2000 == 0)
            wr.bf.flush();
        }
        wr.bf.flush();
      }
      wr.bf.close();
    } catch(Exception e) {
      e.printStackTrace();
    }
    //*/

    /*String c0 = "C21";
    int bitsPerSample = 8;
    Chord c = new Chord(c0);
    int ss = c.samples;
    double[] samples = new double[ss];
    for(int t=0; t<ss; t++) {
      samples[t] = F(c.freqs, t/44100, 1, c.samples, c.tuktuk);
    }//*/

    /*Complex[] wavetable = Fourier.ft(samples);
    Complex[] samples10 = Fourier.ift(wavetable);
    double[] samples1 = new double[samples10.length];
    for(int t=0; t<samples10.length; t++) {
      samples1[t] = samples10[t].re;
    }//*/

    /*WAVWriter wr = new WAVWriter("output.wav", 44100, 44100*bitsPerSample/8, bitsPerSample/8, bitsPerSample, ss);
    try {
      for(int t=0; t<ss; t++)
        wr.bf.write(wr.getBytesLE( (int) (samples[t]), bitsPerSample/8 ));
      wr.bf.flush();
      wr.bf.close();
    } catch(Exception e) {
      e.printStackTrace();
    }//*/

  }
  public static int F(double[] freqs, double help, int bytes, int samples, int tuktuk) {
    double idk = (2*Math.PI);
    double f = 3;
    for(int i=0; i<3; i++)
      f += shit(idk*freqs[i]*help);
    double f1 = 1; //(Math.sin(idk*help*44100/samples*tuktuk-Math.PI/2)+1)/2;
    //Math.exp(-help);
    return (int) ( f * f1 * Math.pow(256, bytes) / 6.);
  }
  public static double shit(double x) {
    double y = x%(2*Math.PI);
    return (y<=Math.PI) ? 1: -1;
  }
  public static double sawtooth(double x) {
    return 2*(((x-Math.PI/2.)/(2.*Math.PI)) % 1D) - 1;
  }
}

/*
Cool sh1t
C18 *30 Em17 G17 D18 *40



*/
