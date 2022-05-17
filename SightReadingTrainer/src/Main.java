        import abc.midi.TunePlayer;
        import abc.notation.Tune;

        import abc.parser.TuneBook;
        import abc.ui.swing.JScoreComponent;

        import javax.swing.*;
        import javax.swing.border.Border;
        import java.awt.*;
        import java.awt.event.ActionEvent;
        import java.awt.event.ActionListener;
        import java.io.BufferedWriter;
        import java.io.File;
        import java.io.FileWriter;
        import java.io.IOException;
        import java.util.Random;
        import java.util.Scanner;

        public class Main
        {
            // maxNote = 16
//            public static String[] CreateMelody(String noteKey)
//            {
//                Random melody = new Random();
//                Character noteKeyKey = noteKey.charAt(0);
//                int thisMelody = melody.nextInt(0,16);
//                if (thisMelody == 0)
//                {
//                    String[] melodyArray = new String[15];
//                    melodyArray[0] = noteKeyKey.toString();
//                    melodyArray[0] = noteKeyKey.toString();
//
//
//                }
//
//            }
            public static String CreateBar(String noteKey)
            {
                String bar1 = "";
                int noteCount = 0;
                for(int i = 16; i > 0;)
                {
                    if (i >= 4) {
                        Random bar = new Random();
                        int thisBar = bar.nextInt(0, 3);
                        if (thisBar == 0) {
                            i = i - 4;
                            bar1 = bar1 + noteKey + 4 + " ";
                        }
                        if (thisBar == 1) {
                            i = i - 2;
                            bar1 = bar1 + noteKey + 2;
                        }
                        if (thisBar == 2) {
                            i = i - 1;
                            bar1 = bar1 + noteKey + 1 + " ";
                        }
                    } else {
                        bar1 = bar1 + noteKey + i;
                        i = 0;
                    }
                    noteCount++;
                }
                return bar1;
        }
            public static void main(String[] args) throws IOException {
                //loading from the file

                File randomlyGeneratedMayham = new File("RandomMusic.abc");
                randomlyGeneratedMayham.createNewFile();
                FileWriter fw = new FileWriter(randomlyGeneratedMayham);
                BufferedWriter bw = new BufferedWriter(fw);
                bw.write("X:1");
                bw.newLine();
                bw.write("T:Randomly Generator");
                bw.newLine();
                bw.write("M:" + "4/4");
                bw.newLine();
                bw.write("L:" + "1/16");
                bw.newLine();
                Scanner tempoScanner = new Scanner(System.in);
                int tempo = tempoScanner.nextInt();
                bw.write("Q:" + (tempo*4));
                bw.newLine();
                Scanner keyScanner = new Scanner(System.in);
                String noteKey = keyScanner.nextLine();
                bw.write("K:" + noteKey);
                bw.newLine();

                String bar1 = CreateBar(noteKey);
                String bar2 = CreateBar(noteKey);
                String bar3 = CreateBar(noteKey);
                String bar4 = CreateBar(noteKey);
                bw.write("[| " + bar1 + "|\\" + "| " + bar2 + "|\\" +"[| " + bar3 + "|\\" + "| " + bar1 + "|\\");
                bw.newLine();
                bw.write("\\]");
                bw.flush();
                bw.close();

                TuneBook book = new TuneBook(new File("RandomMusic.abc"));


                // show details about the tunes that are loaded
                System.out.println("# of tunes in RandomMusic.abc : " + book.size());

                // retrieve the specific tune by reference number
                Tune tune = book.getTune(1);


                // display its title
                System.out.print("Title of #1 is " + tune.getTitles()[0]);
                // and its key
                System.out.println(" and is in the key of " + tune.getKey().toLitteralNotation());

                // can export to a file (abc notation)
                book.saveTo(new File("out.abc"));


                // creates a simple midi player to play the melody
                TunePlayer player = new TunePlayer();
                player.start();


                // creates a component that draws the melody on a musical staff
                ImageIcon logo = new ImageIcon("src/azure.jpeg");
                JScoreComponent jscore = new JScoreComponent();
                jscore.setJustification(true);
                jscore.setTune(tune);

                JFrame j = new JFrame();
                j.add(jscore);
                j.pack();

                j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                JPanel musicControl = new JPanel();
                JButton playAgain = new JButton();
                playAgain.setText("Play!");
                musicControl.setBackground(Color.WHITE);
                musicControl.setLayout(null);


                playAgain.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (player.isPlaying())
                        {
                            player.stopPlaying();
                            playAgain.setText("Play!");

                        }
                        else {
                            player.play(tune);
                            playAgain.setText("Stop!");

                        }

                    }
                });

                playAgain.setBounds(1,1,100,30);
                musicControl.setBounds(1,1,100,40);

                musicControl.add(playAgain);
                j.add(musicControl);

                musicControl.setVisible(true);

                j.setVisible(true);


                j.setIconImage(logo.getImage());
                // writes the score to a JPG file
                jscore.writeScoreTo(new File("spiderScore.jpg"));
            }

        }

