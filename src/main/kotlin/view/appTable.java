package view;

import SQL.StudentsListDB;
import model.Student;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

public class appTable {
    private static final int PAGE_SIZE = 20;
    private static int currentPage = 1;
    private static final StudentsListDB studentDB = new StudentsListDB();
    private static final JTextField nameField = new JTextField();
    private static final JComboBox<String> gitComboBox = new JComboBox<>(new String[] { "Не указано", "Да", "Нет" });
    private static final JTextField gitField = new JTextField();
    private static final JTextField emailField = new JTextField();
    private static final JComboBox<String> emailComboBox = new JComboBox<>(new String[] { "Не указано", "Да", "Нет" });
    private static final JTextField phoneField = new JTextField();
    private static final JComboBox<String> phoneComboBox = new JComboBox<>(new String[] { "Не указано", "Да", "Нет"  });
    private static final JTextField telegramField = new JTextField();
    private static final JComboBox<String> telegramComboBox = new JComboBox<>(new String[] { "Не указано", "Да", "Нет"  });

    private static final JLabel pageInfoLabel = new JLabel("Страница: 1 / ?");
    private static final JButton prevPageButton = new JButton("Предыдущая");
    private static final JButton nextPageButton = new JButton("Следующа");

    private static final JButton refreshButton = new JButton("Обновить");
    private static final JButton addButton = new JButton("Добавить");
    private static final JButton editButton = new JButton("Изменить");
    private static final JButton deleteButton = new JButton("Удалить");

    public static void create() {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Student");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 600);
            JTabbedPane tabbedPane = new JTabbedPane();
            tabbedPane.add("Список студентов", createStudentTab());
            tabbedPane.add("Вкладка 2", new JLabel("Содержимое вкладки 2"));
            tabbedPane.add("Вкладка 3", new JLabel("Содержимое вкладки 3"));
            frame.add(tabbedPane);
            frame.setVisible(true);
        });
    }


    private static JPanel createStudentTab() {
        JPanel panel = new JPanel(new BorderLayout());
        addFilters(panel);
        String[] columnNames = { "ID", "Фамилия Инициалы", "Git", "Email", "Телефон", "Telegram" };
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(table);

        JPanel buttonPanel = new JPanel();
        editButton.setEnabled(false);
        deleteButton.setEnabled(false);
        table.getSelectionModel().addListSelectionListener(e -> {
            boolean rowSelected = table.getSelectedRow() >= 0;
            editButton.setEnabled(rowSelected);
            deleteButton.setEnabled(rowSelected);
        });


        refreshInfo(tableModel);
        addButton.addActionListener(e -> {

            Student student = new Student(0, "Иван", "Иванов", "Иванович", "@ivan", "git", "123-456", "ivan@mail.com");

            if (studentDB.addStudent(student) != null) {
                JOptionPane.showMessageDialog(panel, "Состояние добавлено!");
                refreshInfo(tableModel);
            } else {
                JOptionPane.showMessageDialog(panel, "Ошибка при добавлении состояния.");
            }
        });
        editButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow >= 0) {
                int id = (int) tableModel.getValueAt(selectedRow, 0);
                Student student = studentDB.getById(id);
                if (student != null) {
                    student.setFirstName("Изменено");
                    studentDB.updateStudent(id ,student);
                    JOptionPane.showMessageDialog(panel, "Состояние изменилось!");
                    refreshInfo(tableModel);
                }
            }
        });
        deleteButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow >= 0) {
                int id = (int) tableModel.getValueAt(selectedRow, 0);
                studentDB.deleteStudent(id);
                JOptionPane.showMessageDialog(panel, "Состояние изменилось!");
                refreshInfo(tableModel);
            }
        });
        nextPageButton.addActionListener(e -> {
            currentPage++;
            refreshInfo(tableModel);
        });
        prevPageButton.addActionListener(e -> {
            if (currentPage > 1) {
                currentPage--;
                refreshInfo(tableModel);
            }
        });
        refreshButton.addActionListener(e -> {
            refreshInfo(tableModel);
        });

        buttonPanel.add(pageInfoLabel);
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(prevPageButton);
        buttonPanel.add(nextPageButton);
        buttonPanel.add(refreshButton);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        return panel;
    }
    private static void addFilters(JPanel panel) {

        JPanel filterPanel = new JPanel(new GridLayout(5, 3));
        filterPanel.setBorder(BorderFactory.createTitledBorder("Фильтры"));

        setupFilter(gitComboBox, gitField);
        setupFilter(emailComboBox, emailField);
        setupFilter(phoneComboBox, phoneField);
        setupFilter(telegramComboBox, telegramField);

        filterPanel.add(new JLabel("Фамилия Инициалы:"));
        filterPanel.add(nameField);
        filterPanel.add(new JLabel());
        filterPanel.add(new JLabel("GitHub:"));
        filterPanel.add(gitComboBox);
        filterPanel.add(gitField);
        filterPanel.add(new JLabel("Email:"));
        filterPanel.add(emailComboBox);
        filterPanel.add(emailField);
        filterPanel.add(new JLabel("Телефон:"));
        filterPanel.add(phoneComboBox);
        filterPanel.add(phoneField);
        filterPanel.add(new JLabel("Telegram:"));
        filterPanel.add(telegramComboBox);
        filterPanel.add(telegramField);
        panel.add(filterPanel, BorderLayout.NORTH);
    }
    private static void setupFilter(JComboBox<String> comboBox, JTextField textField) {
        textField.setEnabled(false);
        comboBox.addActionListener(e -> {
            textField.setEnabled(Objects.equals(comboBox.getSelectedItem(), ("Да")));
        });
    }
    private static void refreshInfo(DefaultTableModel tableModel) {
        String nameFilter = nameField.getText().trim();
        Params gitSearch = Params.create(
                (String) Objects.requireNonNull(gitComboBox.getSelectedItem())
        );
        String gitFilter =gitField.getText().trim();
        Params emailSearch = Params.create(
                (String) Objects.requireNonNull(emailComboBox.getSelectedItem())
        );
        String emailFilter = emailField.getText().trim();
        Params phoneSearch = Params.create(
                (String) Objects.requireNonNull(phoneComboBox.getSelectedItem())
        );
        String phoneFilter = phoneField.getText().trim();
        Params telegramSearch = Params.create(
                (String) Objects.requireNonNull(telegramComboBox.getSelectedItem())
        );
        String telegramFilter = telegramField.getText().trim();
        Filter filter = new Filter(
                nameFilter,
                gitFilter,
                emailFilter,
                phoneFilter,
                telegramFilter,
                gitSearch,
                phoneSearch,
                telegramSearch,
                emailSearch
        );
        int totalItems = studentDB.getFilterCount(filter);
        updatePage(totalItems);
        loadStudents(tableModel, filter);
    }
    private static void loadStudents(
            DefaultTableModel tableModel,
            Filter studentFilter
    ) {
        tableModel.setRowCount(0);

        List<Student> students = studentDB.getFilterStudentList(
                currentPage, PAGE_SIZE,
                studentFilter
        );
        for (Student student : students) {
            tableModel.addRow(new Object[] {
                    student.getId(),
                    student.getShortName(),
                    student.getGithub(),
                    student.getEmail(),
                    student.getPhone(),
                    student.getTelegram(),
            });
        }
    }

    private static void updatePage(int totalItems) {
        int lastPage = LastPage(totalItems);

        pageInfoLabel.setText("Страница: " + currentPage + " / " + lastPage);

        prevPageButton.setEnabled(currentPage > 1);
        nextPageButton.setEnabled(currentPage < lastPage);
    }
    private static int LastPage(int totalItems) {
        int page = (int) Math.ceil((double) totalItems / PAGE_SIZE);
        return page == 0 ? 1 : page;
    }

    private static void showStudentForm(Student existingStudent, String title, Consumer<Student> onSave) {
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

        // Äîáàâëÿåì êîìïîíåíòû
        dialog.add(new JLabel("Ôàìèëèÿ:"));
        dialog.add(lastNameField);

        dialog.add(new JLabel("Èìÿ:"));
        dialog.add(firstNameField);

        dialog.add(new JLabel("Îò÷åñòâî:"));
        dialog.add(middleNameField);

        dialog.add(new JLabel("Telegram:"));
        dialog.add(telegramField);

        dialog.add(new JLabel("GitHub:"));
        dialog.add(gitField);

        dialog.add(new JLabel("Email:"));
        dialog.add(emailField);

        // Êíîïêè
        JButton saveButton = new JButton("Ñîõðàíèòü");
        JButton cancelButton = new JButton("Îòìåíà");

        dialog.add(saveButton);
        dialog.add(cancelButton);

        // Îáðàáîò÷èêè êíîïîê
        saveButton.addActionListener(e -> {
            // Ïðîñòàÿ âàëèäàöèÿ
            String lastName = lastNameField.getText().trim();
            String firstName = firstNameField.getText().trim();

            if (lastName.isEmpty() || firstName.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Ôàìèëèÿ, èìÿ è îò÷åñòâî îáÿçàòåëüíû äëÿ çàïîëíåíèÿ!", "Îøèáêà", JOptionPane.ERROR_MESSAGE);
                return;
            }


            Student student = existingStudent != null ? existingStudent : new Student();
            student.setLastName(lastName);
            student.setFirstName(firstName);
            student.setTelegram(telegramField.getText().trim());
            student.setGithub(gitField.getText().trim());
            student.setEmail(emailField.getText().trim());
            student.validate();

            dialog.dispose();
        });

        cancelButton.addActionListener(e -> dialog.dispose());

        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }


}
