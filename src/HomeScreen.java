import javax.swing.*;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class HomeScreen extends JFrame {
    static JTextArea textAreaLeft = new JTextArea(20, 20);
    static JTextArea textAreaRight = new JTextArea(20, 20);
    static JTextArea textAreaReg = new JTextArea(20, 30);
    static JTextArea textAreaInstMem = new JTextArea(20, 30);
    static JTextArea textAreaDataMem = new JTextArea(20, 30);

    public HomeScreen() {
        setTitle("Swing Layout Example");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(1900, 1200));
        setExtendedState(MAXIMIZED_BOTH);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        add(mainPanel);

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(1, 2));
        mainPanel.add(topPanel, BorderLayout.NORTH);

        textAreaLeft.setFont(new Font("Arial", Font.PLAIN, 16));
        textAreaLeft.setText("Type Instructions Here\n1 Instruction per line");
        topPanel.add(new JScrollPane(textAreaLeft));

        textAreaRight.setEditable(false);
        textAreaRight.setFont(new Font("Arial", Font.PLAIN, 16));
        textAreaRight.setText("CPU Process will be displayed here");
        topPanel.add(new JScrollPane(textAreaRight));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        JButton runButton = new JButton("Run");
        runButton.addActionListener(e -> {
            run();
        });

        runButton.setFont(new Font("Arial", Font.BOLD, 18));
        runButton.setPreferredSize(new Dimension(150, 50));

        runButton.setUI(new javax.swing.plaf.basic.BasicButtonUI() {
            @Override
            public void paint(Graphics g, JComponent c) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Color.BLACK);
                g2.fillRoundRect(0, 0, c.getWidth(), c.getHeight(), 10, 10);
                g2.setColor(Color.WHITE);
                g2.setFont(c.getFont());
                FontMetrics fm = g2.getFontMetrics();
                String text = ((AbstractButton) c).getText();
                int x = (c.getWidth() - fm.stringWidth(text)) / 2;
                int y = (c.getHeight() + fm.getAscent()) / 2 - 2;
                g2.drawString(text, x, y);
                g2.dispose();
            }
        });

        JLabel fire1 = new JLabel("🔥🔥🔥🔥🔥🔥🔥🔥");
        fire1.setFont(new Font("Arial", Font.BOLD, 22));
        JLabel fire2 = new JLabel("🔥🔥🔥🔥🔥🔥🔥🔥");
        fire2.setFont(new Font("Arial", Font.BOLD, 22));

        buttonPanel.add(fire1);
        buttonPanel.add(runButton);
        buttonPanel.add(fire2);

        JPanel contentPanel = new JPanel(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0;
        gbc.gridy = 0;
        contentPanel.add(new JLabel("Register File"), gbc);

        gbc.gridx = 1;
        contentPanel.add(new JLabel("Instruction Memory"), gbc);

        gbc.gridx = 2;
        contentPanel.add(new JLabel("Data Memory"), gbc);

        gbc.gridy = 1;
        gbc.gridx = 0;
        textAreaReg.setEditable(false);
        textAreaReg.setFont(new Font("Arial", Font.PLAIN, 16));
        contentPanel.add(new JScrollPane(textAreaReg), gbc);

        gbc.gridx = 1;
        textAreaInstMem.setEditable(false);
        textAreaInstMem.setFont(new Font("Arial", Font.PLAIN, 16));
        contentPanel.add(new JScrollPane(textAreaInstMem), gbc);

        gbc.gridx = 2;
        textAreaDataMem.setEditable(false);
        textAreaDataMem.setFont(new Font("Arial", Font.PLAIN, 16));
        contentPanel.add(new JScrollPane(textAreaDataMem), gbc);

        mainPanel.add(contentPanel, BorderLayout.SOUTH);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);

        pack();
        setVisible(true);
    }

    public static void run() {
        File file = new File("instructions.txt");
        textAreaDataMem.setText("");
        textAreaInstMem.setText("");
        textAreaReg.setText("");
        textAreaRight.setText("");
        try {
            FileWriter fileWriter = new FileWriter(file);
            BufferedWriter writer = new BufferedWriter(fileWriter);

            writer.write(textAreaLeft.getText());
            writer.close();

            CPU cpu = new CPU();
            cpu.instructionMemory.loadMemory("instructions.txt");
            cpu.run();

        } catch (IOException e) {
            textAreaRight.append(e.getMessage());
        } catch (CpuException e) {
            textAreaRight.append(e.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new HomeScreen();
        });
    }
}
