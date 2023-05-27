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

        // Create the main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        add(mainPanel);

        // Create the top panel with a split layout
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(1, 2));
        mainPanel.add(topPanel, BorderLayout.NORTH);

        // Add components to the top panel
        textAreaLeft.setFont(new Font("Arial", Font.PLAIN, 16));
        textAreaLeft.setText("Type Instructions Here\n1 Instruction per line");
        // textAreaLeft
        topPanel.add(new JScrollPane(textAreaLeft));

        textAreaRight.setEditable(false);
        textAreaRight.setFont(new Font("Arial", Font.PLAIN, 16));
        textAreaRight.setText("CPU Process will be displayed here");
        topPanel.add(new JScrollPane(textAreaRight));

        // Create the button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        // Add a button to the button panel
        JButton runButton = new JButton("Run");
        runButton.addActionListener(e -> {
            // Call your method here
            run();
        });

        buttonPanel.add(new JLabel("🙃🙃🙃🙃🙃🙃🙃🙃"));
        buttonPanel.add(runButton);
        buttonPanel.add(new JLabel("🙃🙃🙃🙃🙃🙃🙃🙃"));

        // Create the content panel with GridBagLayout
        JPanel contentPanel = new JPanel(new GridBagLayout());

        // Create GridBagConstraints for positioning components
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Add content to the content panel (example labels and text areas)
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
