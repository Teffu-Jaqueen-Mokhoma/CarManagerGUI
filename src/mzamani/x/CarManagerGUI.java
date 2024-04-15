package mzamani.x;


import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class CarManagerGUI extends JFrame {
    private JLabel brandLabel, modelLabel, yearLabel;
    private JTextField brandField, modelField, yearField;
    private JButton addButton, displayButton;
    private JTextArea outputArea;

    public CarManagerGUI() {
        setTitle("Car Management System");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel();
        inputPanel.setBorder(BorderFactory.createTitledBorder("Enter Car Details"));
        inputPanel.setLayout(new GridLayout(3, 2));

        brandLabel = new JLabel("Brand:");
        modelLabel = new JLabel("Model:");
        yearLabel = new JLabel("Year:");

        brandField = new JTextField(10);
        modelField = new JTextField(10);
        yearField = new JTextField(10);

        inputPanel.add(brandLabel);
        inputPanel.add(brandField);
        inputPanel.add(modelLabel);
        inputPanel.add(modelField);
        inputPanel.add(yearLabel);
        inputPanel.add(yearField);

        addButton = new JButton("Add Car");
        displayButton = new JButton("Display Cars");

        outputArea = new JTextArea(10, 30);
        outputArea.setEditable(false);

        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == addButton) {
                    String brand = brandField.getText();
                    String model = modelField.getText();
                    int year = Integer.parseInt(yearField.getText());
                    Car car = new Car(brand, model, year);
                    outputArea.append("Added Car: " + car.getInfo() + "\n");
                    // Clear input fields
                    brandField.setText("");
                    modelField.setText("");
                    yearField.setText("");

                    // Write car details to a text file
                    try (FileWriter writer = new FileWriter("cars.txt", true)) {
                        writer.write(car.getInfo() + "\n");
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        displayButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == displayButton) {
                    outputArea.setText(""); // Clear existing text
                    // Read car details from the text file and display them
                    try (BufferedReader reader = new BufferedReader(new FileReader("cars.txt"))) {
                        String line;
                        while ((line = reader.readLine()) != null) {
                            outputArea.append(line + "\n");
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(displayButton);

        mainPanel.add(inputPanel, BorderLayout.NORTH);
        mainPanel.add(new JScrollPane(outputArea), BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new CarManagerGUI();
            }
        });
    }
}