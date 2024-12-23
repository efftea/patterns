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
        dialog.setSize(600, 400);
        dialog.setLayout(new GridLayout(8, 2));



        // Создание текстовых полей с учетом существующего студента
        JTextField lastNameField = createTextField(existingStudent != null ? existingStudent.getLastName() : "");
        JTextField firstNameField = createTextField(existingStudent != null ? existingStudent.getFirstName() : "");
        JTextField middleNameField = createTextField(existingStudent != null ? existingStudent.getMiddleName() : "");
        JTextField phoneField = createTextField(existingStudent != null ? existingStudent.getPhone() : "");
        JTextField telegramField = createTextField(existingStudent != null ? existingStudent.getTelegram() : "");
        JTextField gitField = createTextField(existingStudent != null ? existingStudent.getGithub() : "");
        JTextField emailField = createTextField(existingStudent != null ? existingStudent.getEmail() : "");

        // Добавление меток и полей в диалог
        dialog.add(new JLabel("Фамилия:"));
        dialog.add(lastNameField);

        dialog.add(new JLabel("Имя:"));
        dialog.add(firstNameField);

        dialog.add(new JLabel("Отчество:"));
        dialog.add(middleNameField);

        dialog.add(new JLabel("Телефон:"));
        dialog.add(phoneField);

        dialog.add(new JLabel("Telegram:"));
        dialog.add(telegramField);

        dialog.add(new JLabel("GitHub:"));
        dialog.add(gitField);

        dialog.add(new JLabel("Email:"));
        dialog.add(emailField);

        // Кнопки
        JButton saveButton = new JButton("Сохранить");
        JButton cancelButton = new JButton("Отмена");

        dialog.add(saveButton);
        dialog.add(cancelButton);

        JTextField finalMiddleNameField = middleNameField;
        saveButton.addActionListener(e -> {
            try {
                Student student = controller.processForm(
                        existingStudent,
                        lastNameField.getText().trim(),
                        firstNameField.getText().trim(),
                        finalMiddleNameField.getText().trim(),
                        phoneField.getText().trim(),
                        telegramField.getText().trim(),
                        gitField.getText().trim(),
                        emailField.getText().trim()
                );
                String resultMessage = controller.saveProcessedStudent(student, existingStudent != null ? existingStudent.getId() : null);
                JOptionPane.showMessageDialog(dialog, resultMessage);
                dialog.dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, "Ошибка при сохранении данных: " + ex.getMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
        });

        cancelButton.addActionListener(e -> dialog.dispose());

        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }

    // Вспомогательный метод для создания текстового поля
    private JTextField createTextField(String text) {
        JTextField textField = new JTextField(text);
        textField.setEnabled(true); // Делаем поле доступным
        return textField;
    }
}
