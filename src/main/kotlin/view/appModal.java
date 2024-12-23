package view;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import controller.*;
import model.*;

public class appModal {
    FormControll controller;
    public void create(Student existingStudent, String title) {

        JDialog dialog = new JDialog((Frame) null, title, true);
        dialog.setSize(400, 300);
        dialog.setLayout(new GridLayout(7, 2));
        // Ïîëÿ äëÿ ââîäà äàííûõ
        JTextField lastNameField = new JTextField(existingStudent != null ? existingStudent.getLastName() : "");
        JTextField firstNameField = new JTextField(existingStudent != null ? existingStudent.getFirstName() : "");
        JTextField middleNameField = new JTextField(existingStudent != null ? existingStudent.getMiddleName() : "");
        JTextField telegramField = new JTextField(existingStudent != null && existingStudent.getTelegram() != null ? existingStudent.getTelegram() : "");
        JTextField gitField = new JTextField(existingStudent != null && existingStudent.getGithub() != null ? existingStudent.getGithub() : "");
        JTextField emailField = new JTextField(existingStudent != null && existingStudent.getEmail() != null ? existingStudent.getEmail() : "");

        ArrayList<String> accessFields = controller.getAccessFields();
        System.out.println(accessFields.toString());
        dialog.add(new JLabel("Фамилия:"));
        dialog.add(lastNameField);
        if (!accessFields.contains("Фамилия:")) {
            lastNameField.setEnabled(false);
        }

        dialog.add(new JLabel("Имя:"));
        dialog.add(firstNameField);
        if (!accessFields.contains("Имя:")) {
            lastNameField.setEnabled(false);
        }

        dialog.add(new JLabel("Отчество:"));
        dialog.add(middleNameField);
        if (!accessFields.contains("Отчество:")) {
            lastNameField.setEnabled(false);
        }

        dialog.add(new JLabel("Telegram:"));
        dialog.add(telegramField);
        if (!accessFields.contains("Telegram:")) {
            lastNameField.setEnabled(false);
        }

        dialog.add(new JLabel("GitHub:"));
        dialog.add(gitField);
        if (!accessFields.contains("GitHub:")) {
            lastNameField.setEnabled(false);
        }

        dialog.add(new JLabel("Email:"));
        dialog.add(emailField);
        if (!accessFields.contains("Email:")) {
            lastNameField.setEnabled(false);
        }

        JButton saveButton = new JButton("Сохранить");
        JButton cancelButton = new JButton("Отмена");

        dialog.add(saveButton);
        dialog.add(cancelButton);

        saveButton.addActionListener(e -> {
            Student student = controller.processForm(
                    existingStudent,
                    lastNameField.getText().trim(),
                    firstNameField.getText().trim(),
                    middleNameField.getText().trim(),
                    telegramField.getText().trim(),
                    gitField.getText().trim(),
                    emailField.getText().trim()
            );
            String resultMessage = controller.saveProcessedStudent(student, existingStudent != null ? existingStudent.getId() : null);
            JOptionPane.showMessageDialog(dialog, resultMessage);
            dialog.dispose();
        });
        cancelButton.addActionListener(e -> dialog.dispose());
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }
}
