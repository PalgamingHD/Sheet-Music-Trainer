import abc.midi.TunePlayer;
import abc.notation.Tune;
import abc.parser.TuneBook;
import abc.ui.swing.JScoreComponent;

import javax.swing.*;
import java.io.File;
import java.io.IOException;

public class Second
{
    public static void main(String[] args) throws IOException {
        //loading from the file
        TuneBook book = new TuneBook(new File("src/RandomMusic.abc"));


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
        player.play(tune);


        // creates a component that draws the melody on a musical staff
        JScoreComponent jscore = new JScoreComponent();
        jscore.setJustification(true);
        jscore.setTune(tune);
        JFrame j = new JFrame();
        j.add(jscore);
        j.pack();
        j.setVisible(true);
        ImageIcon logo = new ImageIcon("src/azure.png");
        j.setIconImage(logo.getImage());
        // writes the score to a JPG file
        jscore.writeScoreTo(new File("spiderScore.jpg"));
    }

}
