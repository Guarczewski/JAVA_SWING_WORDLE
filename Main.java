import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
public class Main extends JFrame implements ActionListener {
    public static String password = "22222";
    int Try = 0, WordLength = password.length(), WindowSize = 60 * WordLength;
    static List<String> PossibleWords = new ArrayList<>();
    static Main me;
    JButton[][] jButtons;
    JButton Check;
    JPanel mainAnswerPanel;
    JTextField Answer;
    JLabel Proby;
    Main(){
        super("Title");

        JPanel mainPanel = new JPanel(new BorderLayout());
        JPanel actionPanel = new JPanel(new GridLayout(3,0));

        mainAnswerPanel = new JPanel(new GridLayout(5,0));

        JPanel subAnswerPanel = new JPanel(new GridLayout(0,WordLength));
        jButtons = new JButton[5][WordLength];

        for (int i = 0; i < WordLength; i++) {
            jButtons[Try][i] = new JButton();
            subAnswerPanel.add(jButtons[Try][i]);
        }

        mainAnswerPanel.add(subAnswerPanel);

        Try++;

        Check = new JButton("Sprawdz");
        Check.addActionListener(this);

        Answer = new JTextField();
        Answer.setColumns(WordLength);

        Proby = new JLabel("Pozosało prób: 5");

        actionPanel.add(Proby);
        actionPanel.add(Answer);
        actionPanel.add(Check);

        mainPanel.add(mainAnswerPanel, BorderLayout.CENTER);
        mainPanel.add(actionPanel, BorderLayout.SOUTH);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100,100,WindowSize,400);
        setContentPane(mainPanel);
        setVisible(true);
    }

    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(new File("Sample.txt"));
            while (scanner.hasNextLine()) {
                PossibleWords.add(scanner.nextLine());
            }
            scanner.close();
        } catch (Exception ignored) {}
        password = PossibleWords.get(new Random().nextInt(PossibleWords.size()));
        me = new Main();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String temp = Answer.getText();
        for (int i = 0; i < WordLength; i++) {
            jButtons[Try - 1][i].setText("" + temp.charAt(i));
            if (temp.charAt(i) == password.charAt(i)) {
                jButtons[Try - 1][i].setBackground(Color.GREEN);
            }
            else {
                boolean charExist = false;
                for (int j = 0; j < password.length(); j++) {
                    if (temp.charAt(i) == password.charAt(j)) {
                        charExist = true;
                        jButtons[Try - 1][i].setBackground(Color.YELLOW);
                        break;
                    }
                }
                if (!charExist) {
                    jButtons[Try - 1][i].setBackground(Color.RED);
                }
            }
        }
        Proby.setText(("Pozosało prób: " + (5 - Try)));
        if (Try < 5) {
            JPanel subAnswerPanel = new JPanel(new GridLayout(0,WordLength));
            for (int i = 0; i < WordLength; i++) {
                jButtons[Try][i] = new JButton();
                subAnswerPanel.add(jButtons[Try][i]);
            }
            mainAnswerPanel.add(subAnswerPanel);
            Try++;
        }
        me.invalidate();
        me.validate();
        me.repaint();
    }
}