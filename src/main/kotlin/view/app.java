package view;

import SQL.StudentsListDB;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.Objects;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Comparator;
import javax.swing.table.TableRowSorter;

import model.Student;
import datalist.*;
import model.StudentShort;
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
        addFilters(panel);
        String[] columnNames = { "ID", "Фамилия Инициалы", "Git", "Email", "Телефон", "Telegram" };

        // Инициализация tableModel
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(tableModel);
        table.setRowSorter(sorter);

        sorter.setComparator(1, Comparator.comparing(String::toString));
        table.getTableHeader().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int column = table.columnAtPoint(e.getPoint());
                if (column == 1) {
                    sorter.toggleSortOrder(column);
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(table);

        JPanel buttonPanel = new JPanel();

        editButton.setEnabled(false);
        deleteButton.setEnabled(false);

        table.getSelectionModel().addListSelectionListener(e -> {
            boolean rowSelected = table.getSelectedRow() >= 0;
            int selectedRowCount = table.getSelectedRowCount();
            editButton.setEnabled(selectedRowCount == 1);
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

            if (selectedRow != -1) {
                int id = (int) tableModel.getValueAt(selectedRow, 0);

                Student student = uc.getStudentById(id);
                System.out.println(student.getMiddleName());

                modal.create(student, "Обновление студента");
            } else {
                JOptionPane.showMessageDialog(null, "Пожалуйста, выберите одну строку для редактирования.");
            }
        });

        deleteButton.addActionListener(e -> {
            int[] selectedRows = table.getSelectedRows();
            if (selectedRows.length > 0) {
                int confirm = JOptionPane.showConfirmDialog(
                        panel,
                        "Вы уверены, что хотите удалить выбранные строки?",
                        "Подтверждение удаления",
                        JOptionPane.YES_NO_OPTION
                );
                if (confirm == JOptionPane.YES_OPTION) {
                    boolean success = true;
                    for (int i = selectedRows.length - 1; i >= 0; i--) {
                        int id = (int) tableModel.getValueAt(selectedRows[i], 0);
                        if (controller.deleteStudent(id)) {
                            success = false;
                        }
                    }
                    controller.refresh_data();
                }
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
            updateFilter();
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

    public void updateFilter() {
        String nameFilter = nameField.getText().trim();
        Params gitSearch = Params.create(
                (String) Objects.requireNonNull(gitComboBox.getSelectedItem())
        );
        String gitFilter = gitField.getText().trim();
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

        Filter studentFilter = new Filter(
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
        controller.refresh_data(PAGE_SIZE, currentPage, studentFilter);
    }

    private void addFilters(JPanel panel) {

        JPanel filterPanel = new JPanel(new GridLayout(5, 3));
        filterPanel.setBorder(BorderFactory.createTitledBorder("Фильтрация"));

        setupFilter(gitComboBox, gitField);
        setupFilter(emailComboBox, emailField);
        setupFilter(phoneComboBox, phoneField);
        setupFilter(telegramComboBox, telegramField);

        filterPanel.add(new JLabel("Фамилия и инициалы:"));
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
            textField.setEnabled(Objects.equals(comboBox.getSelectedItem(), "Да"));
        });
    }
}
