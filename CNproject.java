import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

public class CNproject extends JFrame {
    private JTextField input1Field, input2Field, resultField;
    private JTextArea historyTextArea;
    private JComboBox<String> operationComboBox;
    private LinkedList<String> historyList;

    public CNproject() {
        // Set up the frame
        setTitle("CN Project");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 250);
        setLayout(new GridLayout(6, 2));

        // Create and add components
        add(new JLabel("Input 1:"));
        input1Field = new JTextField();
        add(input1Field);

        add(new JLabel("Input 2:"));
        input2Field = new JTextField();
        add(input2Field);

        add(new JLabel("Operation:"));
        String[] operations = {"XOR", "Hamming Distance"};
        operationComboBox = new JComboBox<>(operations);
        add(operationComboBox);

        add(new JLabel("Result:"));
        resultField = new JTextField();
        resultField.setEditable(false);
        add(resultField);

        add(new JLabel("History:"));
        historyTextArea = new JTextArea();
        historyTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(historyTextArea);
        add(scrollPane);

        JButton calculateButton = new JButton("Calculate");
        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performCalculation();
            }
        });
        add(calculateButton);

        JButton clearButton = new JButton("Clear");
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearEntries();
            }
        });
        add(clearButton);

        // Initialize the history list
        historyList = new LinkedList<>();

        // Set up the frame visibility
        setVisible(true);
    }

    private void performCalculation() {
        try {
            String input1 = input1Field.getText();
            String input2 = input2Field.getText();
            String selectedOperation = (String) operationComboBox.getSelectedItem();

            if (selectedOperation.equals("XOR")) {
                calculateXOR(input1, input2);
            } else if (selectedOperation.equals("Hamming Distance")) {
                calculateHammingDistance(input1, input2);
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error performing calculation. Please check your input.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void calculateXOR(String input1, String input2) {
        if (input1.matches("[01]+") && input2.matches("[01]+")) {
            int intInput1 = Integer.parseInt(input1, 2);
            int intInput2 = Integer.parseInt(input2, 2);

            int result = intInput1 ^ intInput2;

            resultField.setText(Integer.toBinaryString(result));
            historyList.addFirst("XOR - Input 1: " + input1 + ", Input 2: " + input2 + ", Result: " + Integer.toBinaryString(result));
            updateHistoryTextArea();
        } else {
            JOptionPane.showMessageDialog(this, "Invalid binary input. Please enter valid binary numbers (0s and 1s).", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void calculateHammingDistance(String input1, String input2) {
        if (input1.matches("[01]+") && input2.matches("[01]+")) {
            if (input1.length() == input2.length()) {
                int hammingDistance = 0;
                for (int i = 0; i < input1.length(); i++) {
                    if (input1.charAt(i) != input2.charAt(i)) {
                        hammingDistance++;
                    }
                }

                resultField.setText(Integer.toString(hammingDistance));
                historyList.addFirst("Hamming Distance - Input 1: " + input1 + ", Input 2: " + input2 + ", Distance: " + hammingDistance);
                updateHistoryTextArea();
            } else {
                JOptionPane.showMessageDialog(this, "Inputs must have the same length for Hamming distance calculation.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Invalid binary input. Please enter valid binary numbers (0s and 1s).", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearEntries() {
        input1Field.setText("");
        input2Field.setText("");
        resultField.setText("");
        historyList.clear();
        updateHistoryTextArea();
    }

    private void updateHistoryTextArea() {
        historyTextArea.setText("");
        int count = 0;
        for (String entry : historyList) {
            if (count >= 5) {
                break;
            }
            historyTextArea.append(entry + "\n");
            count++;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CNproject());
    }
}

