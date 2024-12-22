package view;

import SQL.StudentsListDB;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

import student.Student;
import datalist.*;
import student.StudentShort;
import controller.*;

public class app implements view{
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

    private studentListController controller;
    public void setController(studentListController controller) {
        this.controller = controller;
    }
    private DataListStudentShort dataList;
    public void setDataList(DataListStudentShort dataList) {
        this.dataList = dataList;
    }
    private DefaultTableModel tableModel;

    private static final JLabel pageInfoLabel = new JLabel("Страница: 1 / ?");
    private static final JButton prevPageButton = new JButton("Предыдущая");
    private static final JButton nextPageButton = new JButton("Следующа");

    private static final JButton refreshButton = new JButton("Обновить");
    private static final JButton addButton = new JButton("Добавить");
    private static final JButton editButton = new JButton("Изменить");
    private static final JButton deleteButton = new JButton("Удалить");

    public app() {}

    public void create(studentListController controller) {
        setController(controller);
        controller.firstInitDataList();
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("student.Student");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 600);
            JTabbedPane tabbedPane = new JTabbedPane();
            tabbedPane.add("Список студентов", createStudentTab());
            tabbedPane.add("Вкладка 2", new JLabel("Содержимое вкладки 2"));
            tabbedPane.add("Вкладка 3", new JLabel("Содержимое вкладки 3"));

            frame.add(tabbedPane);
            frame.setVisible(true);
            update();
        });
    }


    private JPanel createStudentTab() {
        JPanel panel = new JPanel(new BorderLayout());

        String[] columnNames = { "ID", "Фамилия Инициалы", "Git", "Email", "Телефон", "Telegram" };

        // Инициализация tableModel
        tableModel = new DefaultTableModel(columnNames, 0) {
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

        addButton.addActionListener(e -> {
            CreateControll cc = new CreateControll(this.controller);
            appModal modal = new appModal();
            modal.controller = cc;
            modal.create(null, "Создание студента");
        });

        editButton.addActionListener(e -> {
            UpdateControll uc = new UpdateControll(this.controller);
            appModal modal = new appModal();
            modal.controller = uc;
            int selectedRow = table.getSelectedRow();
            if (selectedRow >= 0) {
                int id = (int) tableModel.getValueAt(selectedRow, 0);
                Student student = uc.getStudentById(id);
                modal.create(student, "Обновлекние студента");
            }
        });

        nextPageButton.addActionListener(e -> {
            currentPage++;
            controller.refresh_data(PAGE_SIZE, currentPage, getCurrentFilter());
        });

        prevPageButton.addActionListener(e -> {
            if (currentPage > 1) {
                currentPage--;
                controller.refresh_data(PAGE_SIZE, currentPage, getCurrentFilter());
            }
        });

        refreshButton.addActionListener(e -> {
            controller.refresh_data(PAGE_SIZE, currentPage, getCurrentFilter());
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

    @Override
    public void update() {
        setParams();
        setTable();
    }

    private void setParams() {
        List<String> newColumnNames = dataList.getNames();
        tableModel.setColumnIdentifiers(newColumnNames.toArray());

        int lastPage = dataList.getPagination().getTotalPages();

        if (lastPage < currentPage) {
            currentPage = lastPage-1;
            controller.refresh_data(PAGE_SIZE, currentPage, getCurrentFilter());
            return;
        }
        updatePageControls(lastPage);
    }
    private void updatePageControls(int lastPage) {
        pageInfoLabel.setText("Страница: " + currentPage + " / " + lastPage);
        prevPageButton.setEnabled(currentPage > 1);
        nextPageButton.setEnabled(currentPage < lastPage);
    }

    private Filter getCurrentFilter() {
        return null;
    }

    private void setTable() {
        tableModel.setRowCount(0);
        List<StudentShort> students = dataList.toList();
        for (StudentShort student : students) {
            tableModel.addRow(new Object[] {
                    student.getId(),
                    student.getShortname(),
                    student.getGithub(),
                    student.getContact(),
            });
        }
    }
}
