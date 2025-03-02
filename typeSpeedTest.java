import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class typeSpeedTest extends JFrame {
    private String[] sentences = {
        "The quick brown fox jumps over the lazy dog.",
        "Technology is evolving at an incredible pace, transforming the way we communicate and work.",
        "A journey of a thousand miles begins with a single step, emphasizing the importance of persistence.",
        "Artificial intelligence is revolutionizing industries, from healthcare to finance and beyond.",
        "Reading books not only expands our knowledge but also enhances our imagination and creativity.",
        "The universe is vast and mysterious, with countless galaxies yet to be explored by humankind.",
        "The beauty of nature lies in its diversity, from towering mountains to deep, blue oceans."
    };
    
    private JLabel sentenceLabel, resultLabel;
    private JTextArea inputArea;
    private JButton startButton;
    private long startTime;
    private String currentSentence;
    
    public typeSpeedTest() {
        setTitle("Typing Speed Test");
        setSize(500, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());
        getContentPane().setBackground(new Color(45, 45, 45)); // Dark background
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        
        sentenceLabel = new JLabel("Click Start to begin.", SwingConstants.CENTER);
        sentenceLabel.setFont(new Font("Arial", Font.BOLD, 16));
        sentenceLabel.setForeground(Color.WHITE);
        add(sentenceLabel, gbc);
        
        
        gbc.gridy++;
        inputArea = new JTextArea(4, 40);
        inputArea.setFont(new Font("Arial", Font.PLAIN, 20));
        inputArea.setBackground(new Color(60, 60, 60)); // Dark text area
        inputArea.setForeground(Color.WHITE);
        inputArea.setEnabled(false);
        inputArea.setLineWrap(true);
        inputArea.setWrapStyleWord(true);
        add(new JScrollPane(inputArea), gbc);
        
        gbc.gridy++;
        startButton = new JButton("Start");
        startButton.setFont(new Font("Arial", Font.BOLD, 14));
        startButton.setBackground(new Color(70, 130, 180)); // Stylish button color
        startButton.setForeground(Color.WHITE);
        add(startButton, gbc);
        
        gbc.gridy++;
        resultLabel = new JLabel("", SwingConstants.CENTER);
        resultLabel.setFont(new Font("Arial", Font.BOLD, 30));
        resultLabel.setForeground(Color.WHITE);
        add(resultLabel, gbc);
        
        startButton.addActionListener(e -> startTest());
        inputArea.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    endTest();
                }
            }
        });
        
        setVisible(true);
    }
    
    private void startTest() {
        Random rand = new Random();
        currentSentence = sentences[rand.nextInt(sentences.length)];
        sentenceLabel.setText("Type this: " + currentSentence);
        inputArea.setText("");
        inputArea.setEnabled(true);
        inputArea.requestFocus();
        startTime = System.currentTimeMillis();
    }
    
    private void endTest() {
        long timeTaken = (System.currentTimeMillis() - startTime) / 1000;
        int wordCount = currentSentence.split(" ").length;
        int wpm = (int) ((wordCount / (double) timeTaken) * 60);
        
        String userInput = inputArea.getText().trim();
        int errors = calculateErrors(userInput, currentSentence);
        double accuracy = ((double) (currentSentence.length() - errors) / currentSentence.length()) * 100;
        
        resultLabel.setText("WPM: " + wpm + " | Accuracy: " + String.format("%.2f", accuracy) + "%");
        inputArea.setEnabled(false);
    }
    
    private int calculateErrors(String userInput, String original) {
        int errors = 0;
        int minLength = Math.min(userInput.length(), original.length());
        
        for (int i = 0; i < minLength; i++) {
            if (userInput.charAt(i) != original.charAt(i)) {
                errors++;
            }
        }
        errors += Math.abs(userInput.length() - original.length());
        return errors;
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new typeSpeedTest().setVisible(true));
    }
}
