package com.eps.borais;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;

public class JavaSwingExercise extends JFrame implements ActionListener {

    // Components of the Form
    private Container c;
    private JLabel title;
    private JLabel lastName;
    private JLabel firstName;
    private JTextField tLastName;
    private JTextField tFirstName;
    private JLabel gender;
    private JComboBox gCbx;
    private JLabel dob;
    private JTextField tDob;
    private JButton addNew;
    private JButton save;
    private JButton delete;
    private JTable table;
    private JTableHeader tableHeader;
    private JScrollPane sp;

    private String genders[] = { "Male", "Female"};


    public JavaSwingExercise(){
        setTitle("Input Form");
        setBounds(300, 90, 900, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        c = getContentPane();
        c.setLayout(null);

        title = new JLabel("Input Form");
        title.setFont(new Font("Arial", Font.PLAIN, 30));
        title.setSize(300, 30);
        title.setLocation(300, 30);
        c.add(title);

        lastName = new JLabel("Last Name:");
        lastName.setFont(new Font("Arial", Font.PLAIN, 20));
        lastName.setSize(125, 20);
        lastName.setLocation(100, 100);
        c.add(lastName);

        tLastName = new JTextField();
        tLastName.setFont(new Font("Arial", Font.PLAIN, 15));
        tLastName.setSize(190, 20);
        tLastName.setLocation(215, 100);
        tLastName.setEnabled(false);
        c.add(tLastName);

        firstName = new JLabel("First Name:");
        firstName.setFont(new Font("Arial", Font.PLAIN, 20));
        firstName.setSize(125, 20);
        firstName.setLocation(100, 150);
        c.add(firstName);

        tFirstName = new JTextField();
        tFirstName.setFont(new Font("Arial", Font.PLAIN, 15));
        tFirstName.setSize(190, 20);
        tFirstName.setLocation(215, 150);
        tFirstName.setEnabled(false);
        c.add(tFirstName);

        gender = new JLabel("Gender:");
        gender.setFont(new Font("Arial", Font.PLAIN, 20));
        gender.setSize(100, 25);
        gender.setLocation(100, 200);
        c.add(gender);

        gCbx = new JComboBox(genders);
        gCbx.setFont(new Font("Arial", Font.PLAIN, 20));
        gCbx.setSize(100, 25);
        gCbx.setLocation(215, 200);
        gCbx.setEnabled(false);
        c.add(gCbx);

        dob = new JLabel("Birthdate:");
        dob.setFont(new Font("Arial", Font.PLAIN, 20));
        dob.setSize(100, 20);
        dob.setLocation(100, 250);
        c.add(dob);

        tDob = new JTextField();
        tDob.setFont(new Font("Arial", Font.PLAIN, 15));
        tDob.setSize(190, 20);
        tDob.setLocation(215, 250);
        tDob.setEnabled(false);
        c.add(tDob);

        addNew  = new JButton("Add New");
        addNew.setFont(new Font("Arial", Font.PLAIN, 15));
        addNew.setSize(100, 50);
        addNew.setLocation(100, 280);
        addNew.setBackground(Color.GREEN);
        addNew.setForeground(Color.BLACK);
        addNew.addActionListener(this);
        c.add(addNew);

        save  = new JButton("Save");
        save.setFont(new Font("Arial", Font.PLAIN, 15));
        save.setSize(100, 50);
        save.setLocation(215, 280);
        save.setEnabled(false);
        save.setForeground(Color.BLACK);
        save.setBackground(Color.BLUE);
        save.addActionListener(this);
        c.add(save);

        DefaultTableModel dataModel = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        dataModel.addColumn("ID");
        dataModel.addColumn("Last Name");
        dataModel.addColumn("First Name");
        dataModel.addColumn("Gender");
        dataModel.addColumn("Birthdate");

        table = new JTable(dataModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
            public void valueChanged(ListSelectionEvent event) {
                delete.setEnabled(true);
            }
        });
        tableHeader = table.getTableHeader();
        tableHeader.setBackground(Color.GRAY);
        tableHeader.setForeground(Color.WHITE);

        sp = new JScrollPane(table);
        sp.setSize(600,200);
        sp.setLocation(100, 350);
        c.add(sp);

        delete  = new JButton("Delete");
        delete.setFont(new Font("Arial", Font.PLAIN, 15));
        delete.setSize(100, 50);
        delete.setLocation(335, 280);
        delete.setEnabled(false);
        delete.setForeground(Color.BLACK);
        delete.setBackground(Color.RED);
        delete.addActionListener(ae -> {
            if(table.getSelectedRow() != -1) {
                dataModel.removeRow(table.getSelectedRow());
                JOptionPane.showMessageDialog(null, "Selected row deleted successfully");
            }
            delete.setEnabled(false);
        });
        c.add(delete);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == addNew){
            addNew.setEnabled(false);
            tLastName.setEnabled(true);
            tFirstName.setEnabled(true);
            gCbx.setEnabled(true);
            tDob.setEnabled(true);
            save.setEnabled(true);
        } else if(e.getSource() == save){
            String id;
            if(table.getRowCount()==0){
                id = "1";
            }else{
                ArrayList<Integer> list = new ArrayList<Integer>();
                for(int i = 0; i < table.getRowCount(); i++)
                {
                    list.add(Integer.parseInt(table.getValueAt(i, 0).toString()));
                }

                int max = Collections.max(list) + 1;
                id = Integer.toString(max);
            }
            String lastName = tLastName.getText();
            String firstName = tFirstName.getText();
            String gender = gCbx.getSelectedItem().toString();
            String birthdate = tDob.getText();

            Object[] row = { id, lastName, firstName, gender, birthdate };

            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.addRow(row);

            //reset form
            addNew.setEnabled(true);
            tLastName.setEnabled(false);
            tLastName.setText("");
            tFirstName.setEnabled(false);
            tFirstName.setText("");
            gCbx.setEnabled(false);
            gCbx.setSelectedIndex(0);
            tDob.setEnabled(false);
            tDob.setText("");
            save.setEnabled(false);
            delete.setEnabled(false);
        }
    }

    public static void main(String[] args) throws Exception
    {
        JavaSwingExercise f = new JavaSwingExercise();
    }

}
